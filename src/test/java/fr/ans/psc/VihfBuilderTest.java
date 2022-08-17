package fr.ans.psc;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ans.psc.exception.WrongWorkSituationKeyException;
import fr.ans.psc.model.prosanteconnect.UserInfos;
import oasis.names.tc.saml._2_0.assertion.Assertion;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.Assert.*;

public class VihfBuilderTest {

    @Test
    public void generateVIHFTest() throws IOException, WrongWorkSituationKeyException {
        ObjectMapper objectMapper = new ObjectMapper();
        UserInfos userInfos = objectMapper.readValue(userInfosMock(), UserInfos.class);
        GenerateVIHFPolicyConfiguration configuration = new GenerateVIHFPolicyConfiguration();
        configuration.setCertificateDN("CN=serviceps.sesam-vitale.fr,OU=339172288100052,O=GIE SESAM VITALE,ST=Sarthe (72),C=FR");
        configuration.setStructureId("136 788 596 476");
        configuration.setLpsName("PROSANTECONNECT_API_PROXY");
        configuration.setLpsVersion("1.0");
        configuration.setLpsHomologationNumber("123");

        VihfBuilder vihfBuilder = new VihfBuilder(userInfos, "10C", "2 88 09 17 202 203 71", configuration);
        assertNotNull(configuration);
        String generatedVihf = vihfBuilder.generateVIHF();
        assertNotEquals(null, generatedVihf);

//        String expected = adaptAssertionTimestamps(generatedVihf, expectedVIHF());
//        assertEquals(expected, generatedVihf);
    }

    @Test(expected = WrongWorkSituationKeyException.class)
    public void workSituationCheckFails() throws IOException, WrongWorkSituationKeyException {
        ObjectMapper objectMapper = new ObjectMapper();
        UserInfos userInfos = objectMapper.readValue(userInfosMock(), UserInfos.class);
        GenerateVIHFPolicyConfiguration configuration = new GenerateVIHFPolicyConfiguration();
        configuration.setCertificateDN("CN=serviceps.sesam-vitale.fr,OU=339172288100052,O=GIE SESAM VITALE,ST=Sarthe (72),C=FR");
        configuration.setStructureId("136 788 596 476");
        configuration.setLpsName("PROSANTECONNECT_API_PROXY");
        configuration.setLpsVersion("1.0");
        configuration.setLpsHomologationNumber("123");

        VihfBuilder vihfBuilder = new VihfBuilder(userInfos, "60C", "2 88 09 17 202 203 71", configuration);
        assertNotNull(configuration);
        String generatedVihf = vihfBuilder.generateVIHF();

    }

    private String adaptAssertionTimestamps(String origin, String target) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(oasis.names.tc.saml._2_0.assertion.ObjectFactory.class, org.hl7.v3.ObjectFactory.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        Assertion generatedAssertion = (Assertion) unmarshaller.unmarshal(new StringReader(origin));
        Assertion expectedAssertion = (Assertion) unmarshaller.unmarshal(new StringReader(target));

        expectedAssertion.setIssueInstant(generatedAssertion.getIssueInstant());
        expectedAssertion.setID(generatedAssertion.getID());
        expectedAssertion.getAuthnStatement().setAuthnInstant(generatedAssertion.getAuthnStatement().getAuthnInstant());

        Marshaller marshaller = context.createMarshaller();
        StringWriter sw = new StringWriter();
        marshaller.marshal(expectedAssertion, sw);
        return sw.toString();
    }

    private String userInfosMock() {
        return "{\n" +
                "\t\"Secteur_Activite\": \"SA07^1.2.250.1.71.4.2.4\",\n" +
                "\t\"sub\": \"f:550dc1c8-d97b-4b1e-ac8c-8eb4471cf9dd:899700366240\",\n" +
                "\t\"email_verified\": false,\n" +
                "\t\"SubjectOrganization\": \"CABINET M DOC0036624\",\n" +
                "\t\"Mode_Acces_Raison\": \"\",\n" +
                "\t\"preferred_username\": \"899700366240\",\n" +
                "\t\"given_name\": \"KIT\",\n" +
                "\t\"Acces_Regulation_Medicale\": \"FAUX\",\n" +
                "\t\"UITVersion\": \"1.0\",\n" +
                "\t\"Palier_Authentification\": \"APPPRIP3^1.2.250.1.213.1.5.1.1.1\",\n" +
                "\t\"SubjectRefPro\": {\n" +
                "\t\t\"codeCivilite\": \"M\",\n" +
                "\t\t\"exercices\": [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"codeProfession\": \"10\",\n" +
                "\t\t\t\t\"codeCategorieProfessionnelle\": \"C\",\n" +
                "\t\t\t\t\"codeCiviliteDexercice\": \"DR\",\n" +
                "\t\t\t\t\"nomDexercice\": \"DOC0036624\",\n" +
                "\t\t\t\t\"prenomDexercice\": \"KIT\",\n" +
                "\t\t\t\t\"codeTypeSavoirFaire\": \"S\",\n" +
                "\t\t\t\t\"codeSavoirFaire\": \"SM26\",\n" +
                "\t\t\t\t\"activities\": [\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"codeModeExercice\": \"L\",\n" +
                "\t\t\t\t\t\t\"codeSecteurDactivite\": \"SA07\",\n" +
                "\t\t\t\t\t\t\"codeSectionPharmacien\": \"\",\n" +
                "\t\t\t\t\t\t\"codeRole\": \"\",\n" +
                "\t\t\t\t\t\t\"numeroSiretSite\": \"\",\n" +
                "\t\t\t\t\t\t\"numeroSirenSite\": \"\",\n" +
                "\t\t\t\t\t\t\"numeroFinessSite\": \"\",\n" +
                "\t\t\t\t\t\t\"numeroFinessetablissementJuridique\": \"\",\n" +
                "\t\t\t\t\t\t\"identifiantTechniqueDeLaStructure\": \"R95141\",\n" +
                "\t\t\t\t\t\t\"raisonSocialeSite\": \"CABINET M DOC0036624\",\n" +
                "\t\t\t\t\t\t\"enseigneCommercialeSite\": \"\",\n" +
                "\t\t\t\t\t\t\"complementDestinataire\": \"CABINET M DOC\",\n" +
                "\t\t\t\t\t\t\"complementPointGeographique\": \"\",\n" +
                "\t\t\t\t\t\t\"numeroVoie\": \"1\",\n" +
                "\t\t\t\t\t\t\"indiceRepetitionVoie\": \"\",\n" +
                "\t\t\t\t\t\t\"codeTypeDeVoie\": \"R\",\n" +
                "\t\t\t\t\t\t\"libelleVoie\": \"NOIR\",\n" +
                "\t\t\t\t\t\t\"mentionDistribution\": \"\",\n" +
                "\t\t\t\t\t\t\"bureauCedex\": \"75009 PARIS\",\n" +
                "\t\t\t\t\t\t\"codePostal\": \"75009\",\n" +
                "\t\t\t\t\t\t\"codeCommune\": \"\",\n" +
                "\t\t\t\t\t\t\"codePays\": \"99000\",\n" +
                "\t\t\t\t\t\t\"telephone\": \"\",\n" +
                "\t\t\t\t\t\t\"telephone2\": \"\",\n" +
                "\t\t\t\t\t\t\"telecopie\": \"\",\n" +
                "\t\t\t\t\t\t\"adresseEMail\": \"\",\n" +
                "\t\t\t\t\t\t\"codeDepartement\": \"\",\n" +
                "\t\t\t\t\t\t\"ancienIdentifiantDeLaStructure\": \"499700366240007\",\n" +
                "\t\t\t\t\t\t\"autoriteDenregistrement\": \"CNOM/CNOM/CNOM\"\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"codeModeExercice\": \"S\",\n" +
                "\t\t\t\t\t\t\"codeSecteurDactivite\": \"SA01\",\n" +
                "\t\t\t\t\t\t\"codeSectionPharmacien\": \"\",\n" +
                "\t\t\t\t\t\t\"codeRole\": \"\",\n" +
                "\t\t\t\t\t\t\"numeroSiretSite\": \"\",\n" +
                "\t\t\t\t\t\t\"numeroSirenSite\": \"\",\n" +
                "\t\t\t\t\t\t\"numeroFinessSite\": \"0B0172805\",\n" +
                "\t\t\t\t\t\t\"numeroFinessetablissementJuridique\": \"1B0062023\",\n" +
                "\t\t\t\t\t\t\"identifiantTechniqueDeLaStructure\": \"F0B0172805\",\n" +
                "\t\t\t\t\t\t\"raisonSocialeSite\": \"HOPITAL GENERIQUE  FIN VARI\",\n" +
                "\t\t\t\t\t\t\"enseigneCommercialeSite\": \"\",\n" +
                "\t\t\t\t\t\t\"complementDestinataire\": \"\",\n" +
                "\t\t\t\t\t\t\"complementPointGeographique\": \"\",\n" +
                "\t\t\t\t\t\t\"numeroVoie\": \"10\",\n" +
                "\t\t\t\t\t\t\"indiceRepetitionVoie\": \"\",\n" +
                "\t\t\t\t\t\t\"codeTypeDeVoie\": \"R\",\n" +
                "\t\t\t\t\t\t\"libelleVoie\": \"DE PARIS\",\n" +
                "\t\t\t\t\t\t\"mentionDistribution\": \"\",\n" +
                "\t\t\t\t\t\t\"bureauCedex\": \"PARIS\",\n" +
                "\t\t\t\t\t\t\"codePostal\": \"75009\",\n" +
                "\t\t\t\t\t\t\"codeCommune\": \"\",\n" +
                "\t\t\t\t\t\t\"codePays\": \"\",\n" +
                "\t\t\t\t\t\t\"telephone\": \"\",\n" +
                "\t\t\t\t\t\t\"telephone2\": \"\",\n" +
                "\t\t\t\t\t\t\"telecopie\": \"\",\n" +
                "\t\t\t\t\t\t\"adresseEMail\": \"\",\n" +
                "\t\t\t\t\t\t\"codeDepartement\": \"\",\n" +
                "\t\t\t\t\t\t\"ancienIdentifiantDeLaStructure\": \"10B0172805\",\n" +
                "\t\t\t\t\t\t\"autoriteDenregistrement\": \"CNOM/CNOM/ARS\"\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t]\n" +
                "\t\t\t}\n," +
                "\t\t\t{\n" +
                "\t\t\t\t\"codeProfession\": \"10\",\n" +
                "\t\t\t\t\"codeCategorieProfessionnelle\": \"C\",\n" +
                "\t\t\t\t\"codeCiviliteDexercice\": \"DR\",\n" +
                "\t\t\t\t\"nomDexercice\": \"DOC0036624\",\n" +
                "\t\t\t\t\"prenomDexercice\": \"KIT\",\n" +
                "\t\t\t\t\"codeTypeSavoirFaire\": \"S\",\n" +
                "\t\t\t\t\"codeSavoirFaire\": \"SM26\",\n" +
                "\t\t\t\t\"activities\": [\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"codeModeExercice\": \"L\",\n" +
                "\t\t\t\t\t\t\"codeSecteurDactivite\": \"SA07\",\n" +
                "\t\t\t\t\t\t\"codeSectionPharmacien\": \"\",\n" +
                "\t\t\t\t\t\t\"codeRole\": \"\",\n" +
                "\t\t\t\t\t\t\"numeroSiretSite\": \"\",\n" +
                "\t\t\t\t\t\t\"numeroSirenSite\": \"\",\n" +
                "\t\t\t\t\t\t\"numeroFinessSite\": \"\",\n" +
                "\t\t\t\t\t\t\"numeroFinessetablissementJuridique\": \"\",\n" +
                "\t\t\t\t\t\t\"identifiantTechniqueDeLaStructure\": \"R95141\",\n" +
                "\t\t\t\t\t\t\"raisonSocialeSite\": \"CABINET M DOC0036624\",\n" +
                "\t\t\t\t\t\t\"enseigneCommercialeSite\": \"\",\n" +
                "\t\t\t\t\t\t\"complementDestinataire\": \"CABINET M DOC\",\n" +
                "\t\t\t\t\t\t\"complementPointGeographique\": \"\",\n" +
                "\t\t\t\t\t\t\"numeroVoie\": \"1\",\n" +
                "\t\t\t\t\t\t\"indiceRepetitionVoie\": \"\",\n" +
                "\t\t\t\t\t\t\"codeTypeDeVoie\": \"R\",\n" +
                "\t\t\t\t\t\t\"libelleVoie\": \"NOIR\",\n" +
                "\t\t\t\t\t\t\"mentionDistribution\": \"\",\n" +
                "\t\t\t\t\t\t\"bureauCedex\": \"75009 PARIS\",\n" +
                "\t\t\t\t\t\t\"codePostal\": \"75009\",\n" +
                "\t\t\t\t\t\t\"codeCommune\": \"\",\n" +
                "\t\t\t\t\t\t\"codePays\": \"99000\",\n" +
                "\t\t\t\t\t\t\"telephone\": \"\",\n" +
                "\t\t\t\t\t\t\"telephone2\": \"\",\n" +
                "\t\t\t\t\t\t\"telecopie\": \"\",\n" +
                "\t\t\t\t\t\t\"adresseEMail\": \"\",\n" +
                "\t\t\t\t\t\t\"codeDepartement\": \"\",\n" +
                "\t\t\t\t\t\t\"ancienIdentifiantDeLaStructure\": \"499700366240007\",\n" +
                "\t\t\t\t\t\t\"autoriteDenregistrement\": \"CNOM/CNOM/CNOM\"\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"codeModeExercice\": \"S\",\n" +
                "\t\t\t\t\t\t\"codeSecteurDactivite\": \"SA01\",\n" +
                "\t\t\t\t\t\t\"codeSectionPharmacien\": \"\",\n" +
                "\t\t\t\t\t\t\"codeRole\": \"\",\n" +
                "\t\t\t\t\t\t\"numeroSiretSite\": \"\",\n" +
                "\t\t\t\t\t\t\"numeroSirenSite\": \"\",\n" +
                "\t\t\t\t\t\t\"numeroFinessSite\": \"0B0172805\",\n" +
                "\t\t\t\t\t\t\"numeroFinessetablissementJuridique\": \"1B0062023\",\n" +
                "\t\t\t\t\t\t\"identifiantTechniqueDeLaStructure\": \"F0B0172805\",\n" +
                "\t\t\t\t\t\t\"raisonSocialeSite\": \"HOPITAL GENERIQUE  FIN VARI\",\n" +
                "\t\t\t\t\t\t\"enseigneCommercialeSite\": \"\",\n" +
                "\t\t\t\t\t\t\"complementDestinataire\": \"\",\n" +
                "\t\t\t\t\t\t\"complementPointGeographique\": \"\",\n" +
                "\t\t\t\t\t\t\"numeroVoie\": \"10\",\n" +
                "\t\t\t\t\t\t\"indiceRepetitionVoie\": \"\",\n" +
                "\t\t\t\t\t\t\"codeTypeDeVoie\": \"R\",\n" +
                "\t\t\t\t\t\t\"libelleVoie\": \"DE PARIS\",\n" +
                "\t\t\t\t\t\t\"mentionDistribution\": \"\",\n" +
                "\t\t\t\t\t\t\"bureauCedex\": \"PARIS\",\n" +
                "\t\t\t\t\t\t\"codePostal\": \"75009\",\n" +
                "\t\t\t\t\t\t\"codeCommune\": \"\",\n" +
                "\t\t\t\t\t\t\"codePays\": \"\",\n" +
                "\t\t\t\t\t\t\"telephone\": \"\",\n" +
                "\t\t\t\t\t\t\"telephone2\": \"\",\n" +
                "\t\t\t\t\t\t\"telecopie\": \"\",\n" +
                "\t\t\t\t\t\t\"adresseEMail\": \"\",\n" +
                "\t\t\t\t\t\t\"codeDepartement\": \"\",\n" +
                "\t\t\t\t\t\t\"ancienIdentifiantDeLaStructure\": \"10B0172805\",\n" +
                "\t\t\t\t\t\t\"autoriteDenregistrement\": \"CNOM/CNOM/ARS\"\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t]\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t},\n" +
                "\t\"SubjectOrganizationID\": \"R95141\",\n" +
                "\t\"SubjectRole\": [\n" +
                "\t\t\"10^1.2.250.1.213.1.1.5.5\"\n" +
                "\t],\n" +
                "\t\"PSI_Locale\": \"1.2.250.1.213.1.3.1.1\",\n" +
                "\t\"otherIds\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"identifiant\": \"899700366240\",\n" +
                "\t\t\t\"origine\": \"RPPS\",\n" +
                "\t\t\t\"qualite\": 1\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"SubjectNameID\": \"899700366240\",\n" +
                "\t\"family_name\": \"DOC0036624\"\n" +
                "}";
    }

    private String expectedVIHF() {
        return "<saml2:Assertion Version=\"2.0\" " +
                        "IssueInstant=\"2022-08-10T13:59:45Z\" " +
                        "ID=\"9155d295-17be-4967-afc8-ae60d0e26634\" " +
                        "xmlns:hl7=\"urn:hl7-org:v3\" " +
                        "xmlns:saml2=\"urn:oasis:names:tc:SAML:2.0:assertion\" " +
                        "xmlns:xmldsig=\"http://www.w3.org/2000/09/xmldsig#\" " +
                        "xmlns:xsi=\"http://wwww.w3.org/2001/XMLSchema-instance\">" +
                    "<saml2:Issuer Format=\"urn:oasis:names:tc:SAML:1.1:nameid-format:X509SubjectName\">" +
                        "CN=serviceps.sesam-vitale.fr,OU=339172288100052,O=GIE SESAM VITALE,ST=Sarthe (72),C=FR" +
                    "</saml2:Issuer>" +
                    "<saml2:Subject>" +
                        "<saml2:NameID>899700366240</saml2:NameID>" +
                    "</saml2:Subject>" +
                    "<saml2:AuthnStatement AuthnInstant=\"2022-08-10T13:59:45Z\">" +
                        "<saml2:AuthnContext>" +
                            "<saml2:AuthnContextClassRef>urn:oasis:names:tc:SAML:2.0:ac:classes:MobileTwoFactorUnregistered</saml2:AuthnContextClassRef>" +
                        "</saml2:AuthnContext>" +
                    "</saml2:AuthnStatement>" +
                    "<saml2:AttributeStatement>" +
                        "<saml2:Attribute Name=\"Identifiant Structure\">" +
                            "<saml2:AttributeValue>136 788 596 476</saml2:AttributeValue>" +
                        "</saml2:Attribute>" +
                        "<saml2:Attribute Name=\"Secteur Activite\">" +
                            "<saml2:AttributeValue>SA07^1.2.250.1.71.4.2.4</saml2:AttributeValue>" +
                        "</saml2:Attribute>" +
                        "<saml2:Attribute Name=\"urn:oasis:names:tc:xspa:1.0:subject:subject-id\">" +
                            "<saml2:AttributeValue>DOC0036624 KIT CABINET M DOC0036624</saml2:AttributeValue>" +
                        "</saml2:Attribute>" +
                        "<saml2:Attribute Name=\"urn:oasis:names:tc:xacml:2.0:subject:role\">" +
                            "<saml2:AttributeValue>" +
                                "<hl7:Role xsi:type=\"CE\" code=\"10\" codeSystem=\"1.2.250.1.71.1.2.7\" displayName=\"Médecin\" " +
                                        "xmlns=\"urn:hl7-org:v3\"/>" +
                            "</saml2:AttributeValue>" +
                            "<saml2:AttributeValue>" +
                                "<hl7:Role xsi:type=\"CE\" code=\"SM26\" codeSystem=\"1.2.250.1.71.4.2.5\" displayName=\"Qualifié en médecine générale (SM)\"" +
                                        " xmlns=\"urn:hl7-org:v3\"/>" +
                            "</saml2:AttributeValue>" +
                        "</saml2:Attribute>" +
                        "<saml2:Attribute Name=\"VIHF_Version\">" +
                            "<saml2:AttributeValue>3.0</saml2:AttributeValue>" +
                        "</saml2:Attribute>" +
                        "<saml2:Attribute Name=\"Authentification_mode\">" +
                            "<saml2:AttributeValue>INDIRECTE</saml2:AttributeValue>" +
                        "</saml2:Attribute>" +
                        "<saml2:Attribute Name=\"urn:oasis:names:tc:xacml:2.0:resource:resource-id\">" +
                            "<saml2:AttributeValue>2 88 09 17 202 203 71</saml2:AttributeValue>" +
                        "</saml2:Attribute>" +
                        "<saml2:Attribute Name=\"Ressource_URN\">" +
                            "<saml2:AttributeValue>urn:dmp</saml2:AttributeValue>" +
                        "</saml2:Attribute>" +
                        "<saml2:Attribute Name=\"urn:oasis:names:tc:xspa:1.0:subject:purposeofuse\">" +
                            "<saml2:AttributeValue>" +
                                "<hl7:PurposeOfUse displayName=\"Accès normal\" codeSystemName=\"mode acces VIHF 2.0\" codeSystem=\"1.2.250.1.213.1.1.4.248\"" +
                                    " code=\"normal\" xsi:type=\"CE\"/>" +
                            "</saml2:AttributeValue>" +
                        "</saml2:Attribute>" +
                        "<saml2:Attribute Name=\"LPS_Nom\">" +
                            "<saml2:AttributeValue>PROSANTECONNECT_API_PROXY</saml2:AttributeValue>" +
                        "</saml2:Attribute>" +
                        "<saml2:Attribute Name=\"LPS_Version\">" +
                            "<saml2:AttributeValue>1.0</saml2:AttributeValue>" +
                        "</saml2:Attribute>" +
                        "<saml2:Attribute Name=\"LPS_ID_HOMOLOGATION_DMP\">" +
                            "<saml2:AttributeValue>123</saml2:AttributeValue>" +
                        "</saml2:Attribute>" +
                    "</saml2:AttributeStatement>" +
                "</saml2:Assertion>";
    }
}
