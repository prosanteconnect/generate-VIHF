//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.3.0 
// Voir <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2022.06.27 à 11:29:41 AM CEST 
//


package org.w3._2000.xmldsig_;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}CanonicalizationMethod"/&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}SignatureMethod"/&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}Reference"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "canonicalizationMethod",
    "signatureMethod",
    "reference"
})
@XmlRootElement(name = "SignedInfo")
public class SignedInfo {

    @XmlElement(name = "CanonicalizationMethod", required = true)
    protected CanonicalizationMethod canonicalizationMethod;
    @XmlElement(name = "SignatureMethod", required = true)
    protected SignatureMethod signatureMethod;
    @XmlElement(name = "Reference", required = true)
    protected Reference reference;

    /**
     * Obtient la valeur de la propriété canonicalizationMethod.
     * 
     * @return
     *     possible object is
     *     {@link CanonicalizationMethod }
     *     
     */
    public CanonicalizationMethod getCanonicalizationMethod() {
        return canonicalizationMethod;
    }

    /**
     * Définit la valeur de la propriété canonicalizationMethod.
     * 
     * @param value
     *     allowed object is
     *     {@link CanonicalizationMethod }
     *     
     */
    public void setCanonicalizationMethod(CanonicalizationMethod value) {
        this.canonicalizationMethod = value;
    }

    /**
     * Obtient la valeur de la propriété signatureMethod.
     * 
     * @return
     *     possible object is
     *     {@link SignatureMethod }
     *     
     */
    public SignatureMethod getSignatureMethod() {
        return signatureMethod;
    }

    /**
     * Définit la valeur de la propriété signatureMethod.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureMethod }
     *     
     */
    public void setSignatureMethod(SignatureMethod value) {
        this.signatureMethod = value;
    }

    /**
     * Obtient la valeur de la propriété reference.
     * 
     * @return
     *     possible object is
     *     {@link Reference }
     *     
     */
    public Reference getReference() {
        return reference;
    }

    /**
     * Définit la valeur de la propriété reference.
     * 
     * @param value
     *     allowed object is
     *     {@link Reference }
     *     
     */
    public void setReference(Reference value) {
        this.reference = value;
    }

}
