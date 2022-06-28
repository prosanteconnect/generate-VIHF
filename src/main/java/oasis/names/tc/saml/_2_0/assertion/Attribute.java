//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.3.0 
// Voir <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2022.06.27 à 11:29:41 AM CEST 
//


package oasis.names.tc.saml._2_0.assertion;

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
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:assertion}AttributeValue" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="Name" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="Authentification_mode"/&gt;
 *             &lt;enumeration value="Identifiant_Structure"/&gt;
 *             &lt;enumeration value="LPS_ID"/&gt;
 *             &lt;enumeration value="LPS_ID_HOMOLOGATION_DMP"/&gt;
 *             &lt;enumeration value="LPS_Nom"/&gt;
 *             &lt;enumeration value="LPS_Version"/&gt;
 *             &lt;enumeration value="Ressource_URN"/&gt;
 *             &lt;enumeration value="Secteur_Activite"/&gt;
 *             &lt;enumeration value="VIHF_Version"/&gt;
 *             &lt;enumeration value="urn:oasis:names:tc:xacml:2.0:resource:resource-id"/&gt;
 *             &lt;enumeration value="hl7:Role"/&gt;
 *             &lt;enumeration value="urn:oasis:names:tc:xspa:1.0:subject:purposeofuse"/&gt;
 *             &lt;enumeration value="urn:oasis:names:tc:xspa:1.0:subject:subject-id"/&gt;
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
@XmlType(name = "", propOrder = {
    "attributeValue"
})
@XmlRootElement(name = "Attribute")
public class Attribute {

    @XmlElement(name = "AttributeValue", required = true)
    protected AttributeValue attributeValue;
    @XmlAttribute(name = "Name", required = true)
    protected String name;

    /**
     * Gets the value of the attributeValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attributeValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributeValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public AttributeValue getAttributeValue() {
        return this.attributeValue;
    }

    public void setAttributeValue(AttributeValue value) {
        this.attributeValue = value;
    }

    /**
     * Obtient la valeur de la propriété name.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Définit la valeur de la propriété name.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

}
