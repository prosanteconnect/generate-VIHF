package fr.ans.psc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import fr.ans.psc.exception.GenericVihfException;
import fr.ans.psc.exception.JaxbMarshallingException;
import fr.ans.psc.exception.NosReferentialRetrievingException;
import fr.ans.psc.exception.WrongWorkSituationKeyException;
import fr.ans.psc.model.nos.Concept;
import fr.ans.psc.model.nos.RetrieveValueSetResponse;
import fr.ans.psc.model.prosanteconnect.Practice;
import fr.ans.psc.model.prosanteconnect.UserInfos;
import fr.ans.psc.utils.CustomNamespaceMapper;
import fr.ans.psc.vihf.*;
import org.slf4j.LoggerFactory;

import javax.xml.bind.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static fr.ans.psc.utils.Constants.*;

public class VihfBuilder {

    private final org.slf4j.Logger log = LoggerFactory.getLogger(VihfBuilder.class);

    private ObjectFactory assertionFactory;

    private String dateNow;

    private UserInfos userInfos;
    private String workSituationId;
    private String patientINS;
    private GenerateVIHFPolicyConfiguration configuration;

    private final ObjectMapper objectMapper;
    private static final String USER_INFOS_PAYLOAD_KEY = "openid.userinfo.payload";

    public VihfBuilder(UserInfos userInfos, String workSituationId, String insHeader,
                       GenerateVIHFPolicyConfiguration configuration) throws GenericVihfException {
        this.objectMapper = new ObjectMapper();
        this.assertionFactory = new ObjectFactory();
        this.dateNow = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date());

        this.userInfos = userInfos;
        this.workSituationId = workSituationId;
        this.patientINS = insHeader;
        this.configuration = configuration;
    }

    public String generateVIHF() throws GenericVihfException {
        String tokenVIHF = "";
        try {
            JAXBContext context = JAXBContext.newInstance(
            		fr.ans.psc.vihf.ObjectFactory.class);

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.formatted.output", Boolean.FALSE);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            marshaller.setProperty(NAMESPACE_PREFIX_MAPPER_PACKAGE, new CustomNamespaceMapper());

            StringWriter sw = new StringWriter();
            log.debug("starting assertion fetching for Ps {}...", userInfos.getSubjectNameID());
            marshaller.marshal(fetchSamlSecurity(), sw);
            log.debug("assertion for Ps {} successfully fetched", userInfos.getSubjectNameID());
            tokenVIHF = sw.toString();

        } catch (PropertyException e) {
            log.warn("JAXB could not use Custom prefix mapper, will use default");

        } catch (JAXBException e) {
            log.error("Could not marshall assertion", e);
            throw new JaxbMarshallingException("Could not marshall assertion", e);
        }

        return tokenVIHF;
    }

//    private Envelope fetchSoapEnvelope() throws GenericVihfException {
//    	Envelope envelope = assertionFactory.createEnvelope();
//    	Header header = assertionFactory.createHeader();
//    	envelope.setHeader(header);
//    	header.setSecurity(fetchSamlSecurity());
//    	return envelope;
//    }

    private Security fetchSamlSecurity() throws GenericVihfException {
        Security security = assertionFactory.createSecurity();
        security.setAssertion(fetchAssertion());
        return security;
    }

    private Assertion fetchAssertion() throws GenericVihfException {
        Assertion assertion = assertionFactory.createAssertion();
        assertion.setIssuer(fetchIssuer());
        assertion.setIssueInstant(dateNow);
        assertion.setSubject(fetchSubject());
        assertion.setVersion(ASSERTION_VERSION);
        assertion.setID(UUID.randomUUID().toString());
        assertion.setAuthnStatement(fetchAuthnStatement());
        assertion.setAttributeStatement(fetchAttributeStatement());

        return assertion;
    }

    private Issuer fetchIssuer() {
        Issuer issuer = assertionFactory.createIssuer();
        issuer.setFormat(ISSUER_FORMAT);
        issuer.setValue(configuration.getCertificateDN());

        return issuer;
    }

    private Subject fetchSubject() {
        Subject subject = assertionFactory.createSubject();
        subject.setNameID(userInfos.getSubjectNameID());

        return subject;
    }

    private AttributeStatement fetchAttributeStatement() throws GenericVihfException {
        AttributeStatement attributeStatement = assertionFactory.createAttributeStatement();
        attributeStatement.getAttribute().add(fetchAttribute(configuration.getStructureId(), IDENTIFIANT_STRUCTURE));
        attributeStatement.getAttribute().add(fetchAttribute(userInfos.getActivitySector(), SECTEUR_ACTIVITE));
        attributeStatement.getAttribute().add(fetchAttribute(userInfos.getSubjectId(), SUBJECT_ID));

        attributeStatement.getAttribute().add(fetchRoles());
        attributeStatement.getAttribute().add(fetchAttribute(VIHF_VERSION_VALUE, VIHF_VERSION));
        attributeStatement.getAttribute().add(fetchAttribute(AUTH_MODE_VALUE, AUTHENTIFICATION_MODE));

        attributeStatement.getAttribute().add(fetchAttribute(patientINS, RESOURCE_ID));
        attributeStatement.getAttribute().add(fetchAttribute(URN_DMP, RESOURCE_URN));
        attributeStatement.getAttribute().add(fetchPurposeOfUse());

        attributeStatement.getAttribute().add(fetchAttribute(configuration.getLpsName(), LPS_NOM));
        attributeStatement.getAttribute().add(fetchAttribute(configuration.getLpsVersion(), LPS_VERSION));
        attributeStatement.getAttribute().add(fetchAttribute(configuration.getLpsHomologationNumber(), LPS_ID_HOMOLOGATION_DMP));

        return attributeStatement;
    }

    private Attribute fetchAttribute(Object attributeContent, String attributeName) {
        AttributeValue attributeValue = assertionFactory.createAttributeValue();
        attributeValue.getContent().add(attributeContent);
        Attribute attribute = assertionFactory.createAttribute();
        attribute.getAttributeValue().add(attributeValue);
        attribute.setName(attributeName);
        return attribute;
    }

    private Attribute fetchPurposeOfUse() {
        PurposeOfUse purposeOfUse = assertionFactory.createPurposeOfUse();
        purposeOfUse.setDisplayName("Accès normal");
        purposeOfUse.setCodeSystemName("mode acces VIHF 2.0");
        purposeOfUse.setCodeSystem("1.2.250.1.213.1.1.4.248");
        purposeOfUse.setCode("normal");
        purposeOfUse.setType(CE_TYPE);

        AttributeValue purposeOfUseAttributeValue = assertionFactory.createAttributeValue();
        purposeOfUseAttributeValue.getContent().add(purposeOfUse);

        Attribute purposeOfUseAttribute = assertionFactory.createAttribute();
        purposeOfUseAttribute.setName(PURPOSE_OF_USE);
        purposeOfUseAttribute.getAttributeValue().add(purposeOfUseAttributeValue);
        return purposeOfUseAttribute;
    }
    private Attribute fetchRoles() throws GenericVihfException {
        log.debug("getting ExercicePro");
        try {
            Practice exercicePro = getExercicePro(userInfos.getSubjectRefPro().getExercices(), workSituationId);
            log.debug("retrieving NOS DMP referential");
            Map<String, Concept> nosMap = retrieveNosDMPSubjectRoleMap();

            log.debug("setting attributes...");
            Attribute roleAttribute = assertionFactory.createAttribute();
            roleAttribute.setName(SUBJECT_ROLE);
            List<AttributeValue> attributeValues = roleAttribute.getAttributeValue();

            attributeValues.add(getRoleAttributeValue(nosMap, exercicePro.getProfessionCode()));

            if (userInfos.getSubjectRefPro().getExercices().stream().anyMatch(practice ->
                    practice.getProfessionCode().equals(DOCTOR_PROFESSION_CODE) || practice.getProfessionCode().equals(PHARMACIST_PROFESSION_CODE))) {
                attributeValues.add(getRoleAttributeValue(nosMap, exercicePro.getExpertiseCode()));
            }
            return roleAttribute;
        } catch (WrongWorkSituationKeyException | NosReferentialRetrievingException e) {
            throw new GenericVihfException(e.getMessage(), e.getCause());
        }

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

    private AttributeValue getRoleAttributeValue(Map<String, Concept> nosMap, String code) throws NosReferentialRetrievingException {
        Role role = assertionFactory.createRole();
        if (nosMap.get(code) == null) {
            log.error("No record for NOS code {}", code);
            log.error("Nos map size is {}", nosMap.size());
            throw new NosReferentialRetrievingException("No record for NOS code " + code);
        }
        role.setCode(code);
        role.setCodeSystem(nosMap.get(code).getCodeSystem());
        role.setDisplayName(nosMap.get(code).getDisplayName());
        role.setType(CE_TYPE);

        AttributeValue roleAttributeValue = assertionFactory.createAttributeValue();
        roleAttributeValue.getContent().add(role);
        return roleAttributeValue;
    }


    private AuthnStatement fetchAuthnStatement() {
        AuthnStatement authnStatement = assertionFactory.createAuthnStatement();
        authnStatement.setAuthnInstant(dateNow);
        authnStatement.setAuthnContext(fetchAuthnContext());
        return authnStatement;
    }

    private AuthnContext fetchAuthnContext() {
        AuthnContext authnContext = assertionFactory.createAuthnContext();
        authnContext.setAuthnContextClassRef(AUTHN_CONTEXT_CLASS_REF);
        return authnContext;
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
}
