package fr.ans.psc;

import fr.ans.psc.model.nos.Concept;
import fr.ans.psc.model.nos.RetrieveValueSetResponse;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class RetrieveNosConceptMapTest {

    @Test
    public void nosProfessionsReferentialTest() throws FileNotFoundException {

        try {
            JAXBContext context = JAXBContext.newInstance(fr.ans.psc.model.nos.ObjectFactory.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();
            File testFile = new File(Thread.currentThread().getContextClassLoader().getResource("NOS_Professions_RASS.xml").getPath());
            InputStream inputStream = new FileInputStream(testFile);
            RetrieveValueSetResponse retrieveValueSetResponse = (RetrieveValueSetResponse) unmarshaller.unmarshal(inputStream);

            Map<String, Concept> nosMap = new HashMap();
            retrieveValueSetResponse.getValueSet().getConceptList().getConcept().forEach(concept -> nosMap.put(concept.getCode(), concept));

            assertEquals("10", nosMap.get("10").getCode());
            assertEquals("1.2.250.1.71.1.2.7", nosMap.get("10").getCodeSystem());
            assertEquals("Médecin", nosMap.get("10").getDisplayName());

            assertEquals("72", nosMap.get("72").getCode());
            assertEquals("1.2.250.1.213.1.6.1.109", nosMap.get("72").getCodeSystem());
            assertEquals("Psychothérapeute", nosMap.get("72").getDisplayName());

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void nosSavoirFaireRassReferentialTest() throws FileNotFoundException {
        try {
            JAXBContext context = JAXBContext.newInstance(fr.ans.psc.model.nos.ObjectFactory.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();
            File testFile = new File(Thread.currentThread().getContextClassLoader().getResource("NOS_SavoirFaire_RASS.xml").getPath());
            InputStream inputStream = new FileInputStream(testFile);
            RetrieveValueSetResponse retrieveValueSetResponse = (RetrieveValueSetResponse) unmarshaller.unmarshal(inputStream);

            Map<String, Concept> nosMap = new HashMap();
            retrieveValueSetResponse.getValueSet().getConceptList().getConcept().forEach(concept -> nosMap.put(concept.getCode(), concept));

            assertEquals("SM26", nosMap.get("SM26").getCode());
            assertEquals("1.2.250.1.213.2.28", nosMap.get("SM26").getCodeSystem());
            assertEquals("Qualifié en Médecine générale (SM)", nosMap.get("SM26").getDisplayName());

            assertEquals("C60", nosMap.get("C60").getCode());
            assertEquals("1.2.250.1.213.2.29", nosMap.get("C60").getCodeSystem());
            assertEquals("Médecine physique et de réadaptation (C)", nosMap.get("C60").getDisplayName());

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
