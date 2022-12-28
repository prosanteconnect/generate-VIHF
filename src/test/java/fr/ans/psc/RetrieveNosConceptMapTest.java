/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
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
    public void nosProfessionsReferentialTest() throws FileNotFoundException, JAXBException {

        JAXBContext context = JAXBContext.newInstance(fr.ans.psc.model.nos.ObjectFactory.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();
        File testFile = new File(Thread.currentThread().getContextClassLoader().getResource("JDV_J65-SubjectRole-DMP.xml").getPath());
        InputStream inputStream = new FileInputStream(testFile);
        RetrieveValueSetResponse retrieveValueSetResponse = (RetrieveValueSetResponse) unmarshaller.unmarshal(inputStream);

        Map<String, Concept> nosMap = new HashMap();
        retrieveValueSetResponse.getValueSet().getConceptList().getConcept().forEach(concept -> nosMap.put(concept.getCode(), concept));

        assertEquals("10", nosMap.get("10").getCode());
        assertEquals("1.2.250.1.71.1.2.7", nosMap.get("10").getCodeSystem());
        assertEquals("Médecin", nosMap.get("10").getDisplayName());

        assertEquals("96", nosMap.get("96").getCode());
        assertEquals("1.2.250.1.71.1.2.7", nosMap.get("96").getCodeSystem());
        assertEquals("Psychomotricien", nosMap.get("96").getDisplayName());

        assertEquals("SM06", nosMap.get("SM06").getCode());
        assertEquals("1.2.250.1.71.4.2.5", nosMap.get("SM06").getCodeSystem());
        assertEquals("Chirurgie maxillo-faciale (SM)", nosMap.get("SM06").getDisplayName());
    }

    @Test
    public void nosSavoirFaireRassReferentialTest() throws FileNotFoundException, JAXBException {

        JAXBContext context = JAXBContext.newInstance(fr.ans.psc.model.nos.ObjectFactory.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();
        File testFile = new File(Thread.currentThread().getContextClassLoader().getResource("JDV_J65-SubjectRole-DMP.xml").getPath());
        InputStream inputStream = new FileInputStream(testFile);
        RetrieveValueSetResponse retrieveValueSetResponse = (RetrieveValueSetResponse) unmarshaller.unmarshal(inputStream);

        Map<String, Concept> nosMap = new HashMap();
        retrieveValueSetResponse.getValueSet().getConceptList().getConcept().forEach(concept -> nosMap.put(concept.getCode(), concept));

        assertEquals("SM26", nosMap.get("SM26").getCode());
        assertEquals("1.2.250.1.71.4.2.5", nosMap.get("SM26").getCodeSystem());
        assertEquals("Qualifié en médecine générale (SM)", nosMap.get("SM26").getDisplayName());

        assertEquals("C60", nosMap.get("C60").getCode());
        assertEquals("1.2.250.1.71.4.2.5", nosMap.get("C60").getCodeSystem());
        assertEquals("Médecine physique et réadaptation (C)", nosMap.get("C60").getDisplayName());
    }
}
