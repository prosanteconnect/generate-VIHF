//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.3.0 
// Voir <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2022.06.27 à 11:29:41 AM CEST 
//


package org.w3._2000.xmldsig_;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
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
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}Transforms"/&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}DigestMethod"/&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}DigestValue"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="URI" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="#_65db4fa8-b44a-4c11-adc5-135dcc327b24"/&gt;
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
    "transforms",
    "digestMethod",
    "digestValue"
})
@XmlRootElement(name = "Reference")
public class Reference {

    @XmlElement(name = "Transforms", required = true)
    protected Transforms transforms;
    @XmlElement(name = "DigestMethod", required = true)
    protected DigestMethod digestMethod;
    @XmlElement(name = "DigestValue", required = true)
    protected String digestValue;
    @XmlAttribute(name = "URI", required = true)
    protected String uri;

    /**
     * Obtient la valeur de la propriété transforms.
     * 
     * @return
     *     possible object is
     *     {@link Transforms }
     *     
     */
    public Transforms getTransforms() {
        return transforms;
    }

    /**
     * Définit la valeur de la propriété transforms.
     * 
     * @param value
     *     allowed object is
     *     {@link Transforms }
     *     
     */
    public void setTransforms(Transforms value) {
        this.transforms = value;
    }

    /**
     * Obtient la valeur de la propriété digestMethod.
     * 
     * @return
     *     possible object is
     *     {@link DigestMethod }
     *     
     */
    public DigestMethod getDigestMethod() {
        return digestMethod;
    }

    /**
     * Définit la valeur de la propriété digestMethod.
     * 
     * @param value
     *     allowed object is
     *     {@link DigestMethod }
     *     
     */
    public void setDigestMethod(DigestMethod value) {
        this.digestMethod = value;
    }

    /**
     * Obtient la valeur de la propriété digestValue.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDigestValue() {
        return digestValue;
    }

    /**
     * Définit la valeur de la propriété digestValue.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDigestValue(String value) {
        this.digestValue = value;
    }

    /**
     * Obtient la valeur de la propriété uri.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getURI() {
        return uri;
    }

    /**
     * Définit la valeur de la propriété uri.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setURI(String value) {
        this.uri = value;
    }

}
