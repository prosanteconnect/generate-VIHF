/**
 * Copyright (C) 2022 ANS (https://esante.gouv.fr)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.ans.psc;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ans.psc.exception.GenericVihfException;
import fr.ans.psc.model.prosanteconnect.UserInfos;
import org.junit.Before;
import org.junit.Test;
import org.opensaml.DefaultBootstrap;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.xml.ConfigurationException;

import java.io.IOException;

import static org.junit.Assert.*;

public class VihfBuilderTest {

    @Before
    public void init() throws ConfigurationException {
        DefaultBootstrap.bootstrap();
    }

    @Test
    public void generateVIHFTest() throws IOException, GenericVihfException {
        ObjectMapper objectMapper = new ObjectMapper();
        UserInfos userInfos = objectMapper.readValue(userInfosMock(), UserInfos.class);
        GenerateVIHFPolicyConfiguration configuration = new GenerateVIHFPolicyConfiguration();
        configuration.setCertificateDN("CN=serviceps.sesam-vitale.fr,OU=339172288100052,O=GIE SESAM VITALE,ST=Sarthe (72),C=FR");
        configuration.setStructureId("136 788 596 476");
        configuration.setLpsName("PROSANTECONNECT_API_PROXY");
        configuration.setLpsVersion("1.0");
        configuration.setLpsHomologationNumber("123");

        VihfBuilder builder = new VihfBuilder(userInfos, "10C", "2 88 09 17 202 203 71", configuration);
        Assertion genVIHF = builder.fetchAssertion();
        assertNotNull(configuration);
        assertNotEquals(null, genVIHF);
        assertEquals("CN=serviceps.sesam-vitale.fr,OU=339172288100052,O=GIE SESAM VITALE,ST=Sarthe (72),C=FR", genVIHF.getIssuer().getValue());
        assertEquals("899700366240", genVIHF.getSubject().getNameID().getValue());
    }

    @Test(expected = GenericVihfException.class)
    public void workSituationCheckFails() throws IOException, GenericVihfException {
        ObjectMapper objectMapper = new ObjectMapper();
        UserInfos userInfos = objectMapper.readValue(userInfosMock(), UserInfos.class);
        GenerateVIHFPolicyConfiguration configuration = new GenerateVIHFPolicyConfiguration();
        configuration.setCertificateDN("CN=serviceps.sesam-vitale.fr,OU=339172288100052,O=GIE SESAM VITALE,ST=Sarthe (72),C=FR");
        configuration.setStructureId("136 788 596 476");
        configuration.setLpsName("PROSANTECONNECT_API_PROXY");
        configuration.setLpsVersion("1.0");
        configuration.setLpsHomologationNumber("123");

        assertNotNull(configuration);
        VihfBuilder builder = new VihfBuilder(userInfos, "60C", "2 88 09 17 202 203 71", configuration);
        builder.fetchAssertion();
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
}
