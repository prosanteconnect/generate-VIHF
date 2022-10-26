package oasis.names.tc.saml._2_0.assertion;

import org.hl7.v3.Role;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import java.util.ArrayList;
import java.util.List;

public class AttributeValue {

    @XmlElementRefs({
            @XmlElementRef(name = "Role", namespace = "urn:hl7-org:v3", type = Role.class)
    })
    @XmlMixed
    protected List<Object> content;

    @XmlAttribute(name = "type", namespace = "http://wwww.w3.org/2001/XMLSchema-instance", required = true)
    protected String type;
    @XmlAttribute(name = "xmlns:xs", required = false)
    protected String nameSpace;

    /**
     * Gets the value of the content property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Role }
     * {@link String }
     *
     *
     */
    public List<Object> getContent() {
        if (content == null) {
            content = new ArrayList<Object>();
        }
        return this.content;
    }

    /**
     * Définit la valeur de la propriété type.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setType(String value) {
        this.type = value;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

}
