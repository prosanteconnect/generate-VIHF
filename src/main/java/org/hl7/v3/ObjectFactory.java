//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.3.0 
// Voir <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2022.06.27 à 11:29:41 AM CEST 
//


package org.hl7.v3;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.hl7.v3 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _PurposeOfUse_QNAME = new QName("urn:hl7-org:v3", "PurposeOfUse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.hl7.v3
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Role }
     * 
     */
    public Role createRole() {
        return new Role();
    }

    /**
     * Create an instance of {@link PurposeOfUse }
     * 
     */
    public PurposeOfUse createTPurposeOfUse() {
        return new PurposeOfUse();
    }

    /**
     * Create an instance of {@link CE }
     * 
     */
    public CE createCE() {
        return new CE();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PurposeOfUse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PurposeOfUse }{@code >}
     */
    @XmlElementDecl(namespace = "urn:hl7-org:v3", name = "PurposeOfUse")
    public JAXBElement<PurposeOfUse> createPurposeOfUse(PurposeOfUse value) {
        return new JAXBElement<PurposeOfUse>(_PurposeOfUse_QNAME, PurposeOfUse.class, null, value);
    }

}
