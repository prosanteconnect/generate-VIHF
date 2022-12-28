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
//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.3.0 
// Voir <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2022.07.06 à 01:21:02 PM CEST 
//


package fr.ans.psc.model.nos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="displayName" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="Accompagnant éducatif et social"/&gt;
 *             &lt;enumeration value="Acteur caractérisé par son rôle"/&gt;
 *             &lt;enumeration value="Aide médico-psychologique (AMP)"/&gt;
 *             &lt;enumeration value="Aide-soignant"/&gt;
 *             &lt;enumeration value="Ambulancier"/&gt;
 *             &lt;enumeration value="Assistant de service social"/&gt;
 *             &lt;enumeration value="Assistant dentaire"/&gt;
 *             &lt;enumeration value="Assistant familial"/&gt;
 *             &lt;enumeration value="Audioprothésiste"/&gt;
 *             &lt;enumeration value="Auxiliaire de puériculture"/&gt;
 *             &lt;enumeration value="Auxiliaire de vie sociale"/&gt;
 *             &lt;enumeration value="Chiropracteur"/&gt;
 *             &lt;enumeration value="Chirurgien-Dentiste"/&gt;
 *             &lt;enumeration value="Conseiller en génétique"/&gt;
 *             &lt;enumeration value="Conseiller en économie sociale et familiale"/&gt;
 *             &lt;enumeration value="Diététicien"/&gt;
 *             &lt;enumeration value="Educateur de jeunes enfants"/&gt;
 *             &lt;enumeration value="Educateur spécialisé"/&gt;
 *             &lt;enumeration value="Educateur technique spécialisé"/&gt;
 *             &lt;enumeration value="Epithésiste"/&gt;
 *             &lt;enumeration value="Ergothérapeute"/&gt;
 *             &lt;enumeration value="Infirmier"/&gt;
 *             &lt;enumeration value="Infirmier psychiatrique"/&gt;
 *             &lt;enumeration value="Manipulateur ERM"/&gt;
 *             &lt;enumeration value="Masseur-Kinésithérapeute"/&gt;
 *             &lt;enumeration value="Moniteur éducateur"/&gt;
 *             &lt;enumeration value="Médecin"/&gt;
 *             &lt;enumeration value="Médiateur familial"/&gt;
 *             &lt;enumeration value="Oculariste"/&gt;
 *             &lt;enumeration value="Opticien-Lunetier"/&gt;
 *             &lt;enumeration value="Orthophoniste"/&gt;
 *             &lt;enumeration value="Orthoprothésiste"/&gt;
 *             &lt;enumeration value="Orthoptiste"/&gt;
 *             &lt;enumeration value="Orthopédiste-Orthésiste"/&gt;
 *             &lt;enumeration value="Ostéopathe"/&gt;
 *             &lt;enumeration value="Pharmacien"/&gt;
 *             &lt;enumeration value="Physicien médical"/&gt;
 *             &lt;enumeration value="Podo-Orthésiste"/&gt;
 *             &lt;enumeration value="Préparateur en pharmacie (officine)"/&gt;
 *             &lt;enumeration value="Préparateur en pharmacie hospitalière"/&gt;
 *             &lt;enumeration value="Psychologue"/&gt;
 *             &lt;enumeration value="Psychomotricien"/&gt;
 *             &lt;enumeration value="Psychothérapeute"/&gt;
 *             &lt;enumeration value="Pédicure-Podologue"/&gt;
 *             &lt;enumeration value="Sage-Femme"/&gt;
 *             &lt;enumeration value="Technicien de l'intervention sociale et familiale"/&gt;
 *             &lt;enumeration value="Technicien de laboratoire médical"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="codeSystem" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="1.2.250.1.213.1.6.1.109"/&gt;
 *             &lt;enumeration value="1.2.250.1.213.1.6.1.140"/&gt;
 *             &lt;enumeration value="1.2.250.1.213.1.6.1.4"/&gt;
 *             &lt;enumeration value="1.2.250.1.71.1.2.7"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="code" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte"&gt;
 *             &lt;enumeration value="10"/&gt;
 *             &lt;enumeration value="21"/&gt;
 *             &lt;enumeration value="26"/&gt;
 *             &lt;enumeration value="28"/&gt;
 *             &lt;enumeration value="31"/&gt;
 *             &lt;enumeration value="32"/&gt;
 *             &lt;enumeration value="35"/&gt;
 *             &lt;enumeration value="36"/&gt;
 *             &lt;enumeration value="37"/&gt;
 *             &lt;enumeration value="38"/&gt;
 *             &lt;enumeration value="39"/&gt;
 *             &lt;enumeration value="40"/&gt;
 *             &lt;enumeration value="41"/&gt;
 *             &lt;enumeration value="42"/&gt;
 *             &lt;enumeration value="43"/&gt;
 *             &lt;enumeration value="44"/&gt;
 *             &lt;enumeration value="45"/&gt;
 *             &lt;enumeration value="46"/&gt;
 *             &lt;enumeration value="47"/&gt;
 *             &lt;enumeration value="48"/&gt;
 *             &lt;enumeration value="49"/&gt;
 *             &lt;enumeration value="50"/&gt;
 *             &lt;enumeration value="51"/&gt;
 *             &lt;enumeration value="52"/&gt;
 *             &lt;enumeration value="53"/&gt;
 *             &lt;enumeration value="60"/&gt;
 *             &lt;enumeration value="69"/&gt;
 *             &lt;enumeration value="70"/&gt;
 *             &lt;enumeration value="71"/&gt;
 *             &lt;enumeration value="72"/&gt;
 *             &lt;enumeration value="73"/&gt;
 *             &lt;enumeration value="80"/&gt;
 *             &lt;enumeration value="81"/&gt;
 *             &lt;enumeration value="82"/&gt;
 *             &lt;enumeration value="83"/&gt;
 *             &lt;enumeration value="84"/&gt;
 *             &lt;enumeration value="85"/&gt;
 *             &lt;enumeration value="86"/&gt;
 *             &lt;enumeration value="91"/&gt;
 *             &lt;enumeration value="92"/&gt;
 *             &lt;enumeration value="93"/&gt;
 *             &lt;enumeration value="94"/&gt;
 *             &lt;enumeration value="95"/&gt;
 *             &lt;enumeration value="96"/&gt;
 *             &lt;enumeration value="97"/&gt;
 *             &lt;enumeration value="98"/&gt;
 *             &lt;enumeration value="99"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Concept", propOrder = {"code", "codeSystem", "displayName"})
@XmlRootElement(name = "Concept")
public class Concept {

    @XmlAttribute(name = "displayName", required = true)
    protected String displayName;
    @XmlAttribute(name = "codeSystem", required = true)
    protected String codeSystem;
    @XmlAttribute(name = "code", required = true)
    protected String code;

    /**
     * Obtient la valeur de la propriété displayName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Définit la valeur de la propriété displayName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayName(String value) {
        this.displayName = value;
    }

    /**
     * Obtient la valeur de la propriété codeSystem.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeSystem() {
        return codeSystem;
    }

    /**
     * Définit la valeur de la propriété codeSystem.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeSystem(String value) {
        this.codeSystem = value;
    }

    /**
     * Obtient la valeur de la propriété code.
     * 
     */
    public String getCode() {
        return code;
    }

    /**
     * Définit la valeur de la propriété code.
     * 
     */
    public void setCode(String value) {
        this.code = value;
    }

    public String toString() {
        return "Code: " + getCode() + ", CodeSystem : " + getCodeSystem() + ", DisplayName : " + getDisplayName();
    }

}
