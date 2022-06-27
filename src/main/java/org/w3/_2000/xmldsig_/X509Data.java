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
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}X509Certificate"/&gt;
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
    "x509Certificate"
})
@XmlRootElement(name = "X509Data")
public class X509Data {

    @XmlElement(name = "X509Certificate", required = true)
    protected String x509Certificate;

    /**
     * Obtient la valeur de la propriété x509Certificate.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getX509Certificate() {
        return x509Certificate;
    }

    /**
     * Définit la valeur de la propriété x509Certificate.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setX509Certificate(String value) {
        this.x509Certificate = value;
    }

}
