//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.3.0
// Voir <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a>
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source.
// Généré le : 2022.08.22 à 01:52:36 PM CEST
//


package org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_secext_1_0;

import oasis.names.tc.saml._2_0.assertion.Assertion;

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
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:assertion}Assertion"/&gt;
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
        "assertion"
})
@XmlRootElement(name = "Security")
public class Security {

    @XmlElement(name = "Assertion", namespace = "urn:oasis:names:tc:SAML:2.0:assertion", required = true)
    protected Assertion assertion;

    /**
     * Obtient la valeur de la propriété assertion.
     *
     * @return
     *     possible object is
     *     {@link Assertion }
     *
     */
    public Assertion getAssertion() {
        return assertion;
    }

    /**
     * Définit la valeur de la propriété assertion.
     *
     * @param value
     *     allowed object is
     *     {@link Assertion }
     *
     */
    public void setAssertion(Assertion value) {
        this.assertion = value;
    }

}
