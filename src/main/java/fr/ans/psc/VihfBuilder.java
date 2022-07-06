package fr.ans.psc;

import fr.ans.psc.model.nos.Concept;
import fr.ans.psc.model.nos.RetrieveValueSetResponse;
import fr.ans.psc.model.prosanteconnect.Practice;
import fr.ans.psc.model.prosanteconnect.UserInfos;
import fr.ans.psc.utils.CustomNamespaceMapper;
import oasis.names.tc.saml._2_0.assertion.*;
import org.hl7.v3.Role;
import org.hl7.v3.TPurposeOfUse;

import javax.xml.bind.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

import static fr.ans.psc.utils.Constants.*;

public class VihfBuilder {

    private transient static final Logger LOGGER = Logger.getLogger(VihfBuilder.class.getName());

    private oasis.names.tc.saml._2_0.assertion.ObjectFactory assertionFactory;
    private org.hl7.v3.ObjectFactory profilFactory;
    private String dateNow;

    private UserInfos userInfos;
    private String structureTechnicalId;
    private String workSituationId;
    private String patientINS;
    private GenerateVIHFPolicyConfiguration configuration;

    public VihfBuilder(UserInfos userInfos, String structureTechnicalId, String workSituationId, String patientINS,
                       GenerateVIHFPolicyConfiguration configuration) {
        assertionFactory = new oasis.names.tc.saml._2_0.assertion.ObjectFactory();
        profilFactory = new org.hl7.v3.ObjectFactory();
        dateNow = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date());
        this.userInfos = userInfos;
        this.structureTechnicalId = structureTechnicalId;
        this.workSituationId = workSituationId;
        this.patientINS = patientINS;
        this.configuration = configuration;
    }

    public String generateVIHF() {
        String tokenVIHF = "";
        try {
            JAXBContext context = JAXBContext.newInstance(ObjectFactory.class,
                    org.hl7.v3.ObjectFactory.class);

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            try {
                marshaller.setProperty(NAMESPACE_PREFIX_MAPPER_PACKAGE, new CustomNamespaceMapper());
            } catch (PropertyException e) {
                LOGGER.warning("JAXB could not use Custom prefix mapper, will use default");
            }

            StringWriter sw = new StringWriter();
            marshaller.marshal(fetchAssertion(), sw);

            tokenVIHF = sw.toString();

        } catch (JAXBException | FileNotFoundException e) {
            // TODO add log
            e.printStackTrace();
        }

        return tokenVIHF;
    }

    private Assertion fetchAssertion() throws FileNotFoundException {
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

    private AttributeStatement fetchAttributeStatement() throws FileNotFoundException {
        AttributeStatement attributeStatement = assertionFactory.createAttributeStatement();
        // TODO : check if this is the good id : technical Id or SIRET or SIREN ?
        // TODO : contrôle DMP = contrôle de présence dans l'annuaire / contrôle de cohérence certificat & structure fournie
        attributeStatement.getAttribute().add(fetchAttribute(structureTechnicalId, IDENTIFIANT_STRUCTURE));
        attributeStatement.getAttribute().add(fetchAttribute(userInfos.getActivitySector(), SECTEUR_ACTIVITE));
        attributeStatement.getAttribute().add(fetchAttribute(userInfos.getSubjectId(), SUBJECT_ID));
        attributeStatement.getAttribute().add(fetchRoles());
        attributeStatement.getAttribute().add(fetchAttribute(VIHF_VERSION_VALUE, VIHF_VERSION));
        attributeStatement.getAttribute().add(fetchAttribute(AUTH_MODE_VALUE, AUTHENTIFICATION_MODE));

        attributeStatement.getAttribute().add(fetchAttribute(patientINS, RESOURCE_ID));
        attributeStatement.getAttribute().add(fetchAttribute(URN_DMP, RESOURCE_URN));
        TPurposeOfUse purposeOfUse = new TPurposeOfUse(true);
        attributeStatement.getAttribute().add(fetchAttribute(purposeOfUse, PURPOSE_OF_USE));

        attributeStatement.getAttribute().add(fetchAttribute(configuration.getLpsName(), LPS_NOM));
        attributeStatement.getAttribute().add(fetchAttribute(configuration.getLpsVersion(), LPS_VERSION));
        attributeStatement.getAttribute().add(fetchAttribute(configuration.getLpsHomologationNumber(), LPS_ID_HOMOLOGATION_DMP));

        return attributeStatement;
    }

    private Attribute fetchAttribute(Object attributeContent, String attributeName) {
        AttributeValue attributeValue = assertionFactory.createAttributeValue();
        attributeValue.getContent().add(attributeContent);
        Attribute attribute = assertionFactory.createAttribute();
        attribute.setName(attributeName);
        List<AttributeValue> attributeValues = new ArrayList<>();
        attributeValues.add(attributeValue);
        attribute.setAttributeValue(attributeValues);
        return attribute;
    }

    private Attribute fetchRoles() throws FileNotFoundException {
        Practice exercicePro = (Practice) userInfos.getSubjectRefPro().getExercices().stream().filter(practice ->
                workSituationId.equals(practice.getProfessionCode() + practice.getProfessionalCategoryCode())).findFirst().get();

        Map<String, Concept> nosMap = retrieveNosProfessionsMap();
        Map<String, Concept> savoirFaireMap = retrieveNosSavoirFaireMap();

        Attribute roleAttribute = assertionFactory.createAttribute();
        roleAttribute.setName(SUBJECT_ROLE);

        Role mandatoryRole = profilFactory.createRole();
        mandatoryRole.setNameSpace(HL7_NAMESPACE);
        mandatoryRole.setCode(exercicePro.getProfessionCode());
        // TODO get codeSystem & profession name in referential from professionCode
        mandatoryRole.setCodeSystem(nosMap.get(exercicePro.getProfessionCode()).getCodeSystem());
        mandatoryRole.setDisplayName(nosMap.get(exercicePro.getProfessionCode()).getDisplayName());
        mandatoryRole.setType(CE_TYPE);

        AttributeValue mandatoryRoleAttributeValue = assertionFactory.createAttributeValue();
        mandatoryRoleAttributeValue.getContent().add(mandatoryRole);
        List<AttributeValue> attributeValues = new ArrayList<>();
        attributeValues.add(mandatoryRoleAttributeValue);

        if (userInfos.getSubjectRefPro().getExercices().stream().anyMatch(practice ->
                        practice.getProfessionCode().equals(DOCTOR_PROFESSION_CODE) ||
                        practice.getProfessionCode().equals(PHARMACIST_PROFESSION_CODE))) {

            Role additionalRole = profilFactory.createRole();
            additionalRole.setNameSpace(HL7_NAMESPACE);
            additionalRole.setCode(exercicePro.getExpertiseCode());
            // TODO : get codeSystem & profession name from referential
            additionalRole.setCodeSystem(savoirFaireMap.get(exercicePro.getExpertiseCode()).getCodeSystem());
            additionalRole.setDisplayName(savoirFaireMap.get(exercicePro.getExpertiseCode()).getDisplayName());
            additionalRole.setType(CE_TYPE);

            AttributeValue additionalRoleAttributeValue = assertionFactory.createAttributeValue();
            additionalRoleAttributeValue.getContent().add(additionalRole);
            attributeValues.add(additionalRoleAttributeValue);
        }

        roleAttribute.setAttributeValue(attributeValues);
        return roleAttribute;
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

    private Map<String, Concept> retrieveNosProfessionsMap() throws FileNotFoundException {
        Map<String, Concept> nosMap = new HashMap();
        try {
            JAXBContext context = JAXBContext.newInstance(fr.ans.psc.model.nos.ObjectFactory.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();
            // TODO get
            File testFile = new File(Thread.currentThread().getContextClassLoader().getResource("NOS_Professions_RASS.xml").getPath());
            InputStream inputStream = new FileInputStream(testFile);
            RetrieveValueSetResponse retrieveValueSetResponse = (RetrieveValueSetResponse) unmarshaller.unmarshal(inputStream);

            retrieveValueSetResponse.getValueSet().getConceptList().getConcept().forEach(concept -> nosMap.put(concept.getCode(), concept));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return nosMap;
    }

    private Map<String, Concept> retrieveNosSavoirFaireMap() throws FileNotFoundException {
        Map<String, Concept> nosSavoirFaireMap = new HashMap();
        try {
            JAXBContext context = JAXBContext.newInstance(fr.ans.psc.model.nos.ObjectFactory.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();
            // TODO get
            File testFile = new File(Thread.currentThread().getContextClassLoader().getResource("NOS_SavoirFaire_RASS.xml").getPath());
            InputStream inputStream = new FileInputStream(testFile);
            RetrieveValueSetResponse retrieveValueSetResponse = (RetrieveValueSetResponse) unmarshaller.unmarshal(inputStream);

            retrieveValueSetResponse.getValueSet().getConceptList().getConcept().forEach(concept -> nosSavoirFaireMap.put(concept.getCode(), concept));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return nosSavoirFaireMap;
    }
}
