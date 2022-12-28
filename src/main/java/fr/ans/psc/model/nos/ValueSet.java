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
//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.3.0 
// Voir <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2022.07.06 à 01:21:02 PM CEST 
//


package fr.ans.psc.model.nos;

import javax.xml.bind.annotation.*;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{}ConceptList"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="urlFichier" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" /&gt;
 *       &lt;attribute name="typeFichier" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="JDV"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="id" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="1.2.250.1.213.1.6.1.174"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="displayName" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="JDV_J106-EnsembleProfession-RASS.tabs"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="description" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="Ensemble des professions du RASS"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="dateValid" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}long"&gt;
 *             &lt;enumeration value="20190830120000"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="dateMaj" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}long"&gt;
 *             &lt;enumeration value="20200529120000"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="dateFin" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value=""/&gt;
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
@XmlType(name = "ValueSet", propOrder = {"conceptList"})
@XmlRootElement(name = "ValueSet")
public class ValueSet {

    @XmlElement(name = "ConceptList", required = true)
    protected ConceptList conceptList;
    @XmlAttribute(name = "urlFichier", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String urlFichier;
    @XmlAttribute(name = "typeFichier", required = true)
    protected String typeFichier;
    @XmlAttribute(name = "id", required = true)
    protected String id;
    @XmlAttribute(name = "displayName", required = true)
    protected String displayName;
    @XmlAttribute(name = "description", required = true)
    protected String description;
    @XmlAttribute(name = "dateValid", required = true)
    protected long dateValid;
    @XmlAttribute(name = "dateMaj", required = true)
    protected long dateMaj;
    @XmlAttribute(name = "dateFin", required = true)
    protected String dateFin;

    /**
     * Obtient la valeur de la propriété conceptList.
     * 
     * @return
     *     possible object is
     *     {@link ConceptList }
     *     
     */
    public ConceptList getConceptList() {
        return conceptList;
    }

    /**
     * Définit la valeur de la propriété conceptList.
     * 
     * @param value
     *     allowed object is
     *     {@link ConceptList }
     *     
     */
    public void setConceptList(ConceptList value) {
        this.conceptList = value;
    }

    /**
     * Obtient la valeur de la propriété urlFichier.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlFichier() {
        return urlFichier;
    }

    /**
     * Définit la valeur de la propriété urlFichier.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlFichier(String value) {
        this.urlFichier = value;
    }

    /**
     * Obtient la valeur de la propriété typeFichier.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeFichier() {
        return typeFichier;
    }

    /**
     * Définit la valeur de la propriété typeFichier.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeFichier(String value) {
        this.typeFichier = value;
    }

    /**
     * Obtient la valeur de la propriété id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Définit la valeur de la propriété id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

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
     * Obtient la valeur de la propriété description.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Définit la valeur de la propriété description.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Obtient la valeur de la propriété dateValid.
     * 
     */
    public long getDateValid() {
        return dateValid;
    }

    /**
     * Définit la valeur de la propriété dateValid.
     * 
     */
    public void setDateValid(long value) {
        this.dateValid = value;
    }

    /**
     * Obtient la valeur de la propriété dateMaj.
     * 
     */
    public long getDateMaj() {
        return dateMaj;
    }

    /**
     * Définit la valeur de la propriété dateMaj.
     * 
     */
    public void setDateMaj(long value) {
        this.dateMaj = value;
    }

    /**
     * Obtient la valeur de la propriété dateFin.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateFin() {
        return dateFin;
    }

    /**
     * Définit la valeur de la propriété dateFin.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateFin(String value) {
        this.dateFin = value;
    }

}
