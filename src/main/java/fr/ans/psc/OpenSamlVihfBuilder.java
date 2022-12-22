package fr.ans.psc;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ans.psc.exception.GenericVihfException;
import fr.ans.psc.exception.NosReferentialRetrievingException;
import fr.ans.psc.exception.WrongWorkSituationKeyException;
import fr.ans.psc.model.nos.Concept;
import fr.ans.psc.model.nos.RetrieveValueSetResponse;
import fr.ans.psc.model.prosanteconnect.Practice;
import fr.ans.psc.model.prosanteconnect.UserInfos;
import fr.ans.psc.model.vihf.CommonCode;
import fr.ans.psc.model.vihf.VihfPurposeOfUse;
import fr.ans.psc.model.vihf.VihfRole;
import org.joda.time.DateTime;
import org.opensaml.common.SAMLVersion;
import org.opensaml.saml2.core.*;
import org.opensaml.saml2.core.impl.*;
import org.opensaml.Configuration;
import org.opensaml.xml.Namespace;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.schema.XSAny;
import org.slf4j.LoggerFactory;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

import static fr.ans.psc.utils.Constants.*;

public class OpenSamlVihfBuilder {

    private final org.slf4j.Logger log = LoggerFactory.getLogger(OpenSamlVihfBuilder.class);
    private UserInfos userInfos;
    private String workSituationId;
    private String patientINS;
    private GenerateVIHFPolicyConfiguration configuration;
    private static final String USER_INFOS_PAYLOAD_KEY = "openid.userinfo.payload";
    private final ObjectMapper objectMapper;

    public OpenSamlVihfBuilder(UserInfos userInfos, String workSituationId, String patientINS,
                               GenerateVIHFPolicyConfiguration configuration) {
        this.userInfos = userInfos;
        this.workSituationId = workSituationId;
        this.patientINS = patientINS;
        this.configuration = configuration;
        this.objectMapper = new ObjectMapper();
    }


    public Assertion fetchAssertion() throws GenericVihfException {
        AssertionBuilder assertionBuilder = new AssertionBuilder();
        Assertion assertion = assertionBuilder.buildObject();
        DateTime dateTime = new DateTime();

        assertion.getNamespaceManager().registerNamespace(new Namespace(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, "xsi"));
        assertion.setIssuer(fetchIssuer());
        assertion.setIssueInstant(dateTime);
        assertion.setSubject(fetchSubject());
        assertion.setVersion(SAMLVersion.VERSION_20);
        assertion.setID(UUID.randomUUID().toString());
        assertion.getAuthnStatements().add(fetchAuthnStatement(dateTime));
        assertion.getAttributeStatements().add(fetchAttributeStatementList());

        return assertion;
    }

    private Issuer fetchIssuer() {
        Issuer issuer = new IssuerBuilder().buildObject();
        issuer.setFormat(ISSUER_FORMAT);
        issuer.setValue(configuration.getCertificateDN());

        return issuer;
    }

    private Subject fetchSubject() {
        NameID nameId = new NameIDBuilder().buildObject();
        nameId.setValue(userInfos.getSubjectNameID());
        Subject subject = new SubjectBuilder().buildObject();
        subject.setNameID(nameId);

        return subject;
    }

    private AuthnStatement fetchAuthnStatement(DateTime dateTime) {
        AuthnContext authnContext = new AuthnContextBuilder().buildObject();
        AuthnContextClassRef authnContextClassRef = new AuthnContextClassRefBuilder().buildObject();
        authnContextClassRef.setAuthnContextClassRef(AUTHN_CONTEXT_CLASS_REF);
        authnContext.setAuthnContextClassRef(authnContextClassRef);

        AuthnStatement authnStatement = new AuthnStatementBuilder().buildObject();
        authnStatement.setAuthnInstant(dateTime);
        authnStatement.setAuthnContext(authnContext);

        return authnStatement;
    }

    private AttributeStatement fetchAttributeStatementList() throws GenericVihfException {
        AttributeStatement attributeStatement = new AttributeStatementBuilder().buildObject();
        attributeStatement.getAttributes().addAll(fetchAttributeList());

        return attributeStatement;
    }

    private List<Attribute> fetchAttributeList() throws GenericVihfException {
        AttributeBuilder attributeBuilder = new AttributeBuilder();

        List<Attribute> attributeList = new ArrayList<>();
        attributeList.add(fetchAttribute(attributeBuilder, IDENTIFIANT_STRUCTURE, configuration.getStructureId()));
        attributeList.add(fetchAttribute(attributeBuilder, SECTEUR_ACTIVITE, userInfos.getActivitySector()));
        attributeList.add(fetchAttribute(attributeBuilder, SUBJECT_ID, userInfos.getSubjectId()));

        attributeList.add(fetchRoles(attributeBuilder));
        attributeList.add(fetchAttribute(attributeBuilder, VIHF_VERSION, VIHF_VERSION_VALUE));
        attributeList.add(fetchAttribute(attributeBuilder, AUTHENTIFICATION_MODE, AUTH_MODE_VALUE));

        attributeList.add(fetchAttribute(attributeBuilder, RESOURCE_ID, patientINS + "^^^&1.2.250.1.213.1.4.10&ISO^NH"));
        attributeList.add(fetchPurposeOfUse(attributeBuilder));

        attributeList.add(fetchAttribute(attributeBuilder, LPS_NOM, configuration.getLpsName()));
        attributeList.add(fetchAttribute(attributeBuilder, LPS_VERSION, configuration.getLpsVersion()));
        attributeList.add(fetchAttribute(attributeBuilder, LPS_ID_HOMOLOGATION_DMP, configuration.getLpsHomologationNumber()));

        return attributeList;
    }

    @SuppressWarnings("unchecked")
    private Attribute fetchAttribute(AttributeBuilder attributeBuilder, String attributeName, String attributeContent) {
        XMLObjectBuilder<XSAny> builder = Configuration.getBuilderFactory().getBuilder(XSAny.TYPE_NAME);
        XSAny attributeValue = builder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME);
        attributeValue.setTextContent(attributeContent);

        Attribute attribute = attributeBuilder.buildObject();
        attribute.setName(attributeName);
        attribute.getAttributeValues().add(attributeValue);

        return attribute;
    }

    private Attribute fetchRoles(AttributeBuilder attributeBuilder) throws GenericVihfException {
        log.debug("getting ExercicePro");
        Practice exercicePro = getExercicePro(userInfos.getSubjectRefPro().getExercices(), workSituationId);
        log.debug("retrieving NOS DMP referential");
        Map<String, Concept> nosMap = retrieveNosDMPSubjectRoleMap();

        log.debug("setting attributes...");
        List<VihfRole> roles = new ArrayList<>();
        roles.add(getVihfRole(nosMap, exercicePro.getProfessionCode(), "professions"));
        if (userInfos.getSubjectRefPro().getExercices().stream()
                .anyMatch(practice ->
                        practice.getProfessionCode().equals(DOCTOR_PROFESSION_CODE) ||
                        practice.getProfessionCode().equals(PHARMACIST_PROFESSION_CODE))) {
            roles.add(getVihfRole(nosMap, exercicePro.getExpertiseCode(), "specialites RPPS"));
        }

        return addCommonCodeAttribute(attributeBuilder, SUBJECT_ROLE, roles, "Role");
    }

    private Attribute fetchPurposeOfUse(AttributeBuilder attributeBuilder) {
        VihfPurposeOfUse purposeOfUse = new VihfPurposeOfUse("normal", "1.2.250.1.213.1.1.4.248",
                "mode acces VIHF 2.0", "Accès normal");

        return addCommonCodeAttribute(attributeBuilder, PURPOSE_OF_USE,
                Collections.singletonList(purposeOfUse), "PurposeOfUse");
    }

    private VihfRole getVihfRole(Map<String, Concept> nosMap, String code, String codeSystemName) throws NosReferentialRetrievingException {
        if (nosMap.get(code) == null) {
            log.error("No record for NOS code {}", code);
            log.error("Nos map size is {}", nosMap.size());
            throw new NosReferentialRetrievingException("No record for NOS code " + code);
        }

        return new VihfRole(
                code,
                nosMap.get(code).getCodeSystem(),
                codeSystemName,
                nosMap.get(code).getDisplayName());
    }

    @SuppressWarnings("unchecked")
    private Attribute addCommonCodeAttribute(AttributeBuilder attributeBuilder, String attributeName, List<? extends CommonCode> commonCodeList,
                                             String name) {
        Attribute attributeGroup = attributeBuilder.buildObject();
        attributeGroup.setName(attributeName);

        for (CommonCode commonCode : commonCodeList) {
            XMLObjectBuilder<XSAny> builder = Configuration.getBuilderFactory().getBuilder(XSAny.TYPE_NAME);
            XSAny attrValue = builder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME);
            XSAny xsAnyRoleAttributeValue = builder.buildObject("urn:hl7-org:v3", name, null);
            xsAnyRoleAttributeValue.getUnknownAttributes().put(new QName(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, "type", "xsi"), CE_TYPE);
            xsAnyRoleAttributeValue.getUnknownAttributes().put(new QName("code"), commonCode.getCode());
            xsAnyRoleAttributeValue.getUnknownAttributes().put(new QName("codeSystem"), commonCode.getCodeSystem());
            xsAnyRoleAttributeValue.getUnknownAttributes().put(new QName("codeSystemName"), commonCode.getCodeSystemName());
            xsAnyRoleAttributeValue.getUnknownAttributes().put(new QName("displayName"), commonCode.getDisplayName());

            attrValue.getUnknownXMLObjects().add(xsAnyRoleAttributeValue);
            attributeGroup.getAttributeValues().add(attrValue);
        }
        return attributeGroup;
    }

    private XMLObject createSamlObject(QName qname) {
        return Configuration.getBuilderFactory().getBuilder(qname).buildObject(qname);
    }

    private Map<String, Concept> retrieveNosDMPSubjectRoleMap() throws NosReferentialRetrievingException {
        Map<String, Concept> nosMap = new HashMap<>();
        try {
            JAXBContext context = JAXBContext.newInstance(fr.ans.psc.model.nos.ObjectFactory.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            String nosFileAbsolutePath = Thread.currentThread().getContextClassLoader().getResource("JDV_J65-SubjectRole-DMP.xml").getPath();
            File nosFile = new File(nosFileAbsolutePath);
            InputStream inputStream = new FileInputStream(nosFile);
            RetrieveValueSetResponse retrieveValueSetResponse = (RetrieveValueSetResponse) unmarshaller.unmarshal(inputStream);

            retrieveValueSetResponse.getValueSet().getConceptList().getConcept().forEach(concept -> nosMap.put(concept.getCode(), concept));

        } catch (JAXBException e) {
            log.error("JAXB exception occurred when unmarshalling NOS referential", e);
            throw new NosReferentialRetrievingException("JAXB exception occurred when unmarshalling NOS referential", e);
        } catch (FileNotFoundException e) {
            log.error("Could not find NOS referential", e);
            throw new NosReferentialRetrievingException("Could not find NOS referential", e);
        }
        return nosMap;
    }

    private Practice getExercicePro(List<Practice> exercices, String workSituationKey) throws WrongWorkSituationKeyException {
        try {
            return exercices.size() > 1 ?
                    exercices.stream().filter(practice ->
                            workSituationKey.equals(practice.getProfessionCode() + practice.getProfessionalCategoryCode())).findFirst().get() :
                    exercices.get(0);
        } catch (NoSuchElementException e) {
            throw new WrongWorkSituationKeyException("Wrong WorkSituationKey : practice designed by " + workSituationKey + " key submitted in request but absent in UserInfos", e);
        }

    }

}
