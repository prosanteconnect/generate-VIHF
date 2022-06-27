//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.3.0 
// Voir <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2022.06.27 à 11:29:41 AM CEST 
//


package oasis.names.tc.saml._2_0.assertion;

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
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:assertion}AuthnContext"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="AuthnInstant" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="YYYY-MM-ddTHH:MM:ssZ"/&gt;
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
    "authnContext"
})
@XmlRootElement(name = "AuthnStatement")
public class AuthnStatement {

    @XmlElement(name = "AuthnContext", required = true)
    protected AuthnContext authnContext;
    @XmlAttribute(name = "AuthnInstant", required = true)
    protected String authnInstant;

    /**
     * Obtient la valeur de la propriété authnContext.
     * 
     * @return
     *     possible object is
     *     {@link AuthnContext }
     *     
     */
    public AuthnContext getAuthnContext() {
        return authnContext;
    }

    /**
     * Définit la valeur de la propriété authnContext.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthnContext }
     *     
     */
    public void setAuthnContext(AuthnContext value) {
        this.authnContext = value;
    }

    /**
     * Obtient la valeur de la propriété authnInstant.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthnInstant() {
        return authnInstant;
    }

    /**
     * Définit la valeur de la propriété authnInstant.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthnInstant(String value) {
        this.authnInstant = value;
    }

}
