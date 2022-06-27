//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.3.0 
// Voir <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2022.06.27 à 11:29:41 AM CEST 
//


package oasis.names.tc.saml._2_0.assertion;

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
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:assertion}AuthnContextClassRef"/&gt;
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
    "authnContextClassRef"
})
@XmlRootElement(name = "AuthnContext")
public class AuthnContext {

    @XmlElement(name = "AuthnContextClassRef", required = true)
    protected String authnContextClassRef;

    /**
     * Obtient la valeur de la propriété authnContextClassRef.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthnContextClassRef() {
        return authnContextClassRef;
    }

    /**
     * Définit la valeur de la propriété authnContextClassRef.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthnContextClassRef(String value) {
        this.authnContextClassRef = value;
    }

}
