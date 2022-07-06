package fr.ans.psc.utils;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

import java.util.HashMap;
import java.util.Map;

public class CustomNamespaceMapper extends NamespacePrefixMapper {

    private Map<String, String> namespaceMap = new HashMap<>();

    public CustomNamespaceMapper() {
        namespaceMap.put("urn:oasis:names:tc:SAML:2.0:assertion", "saml2");
        namespaceMap.put("urn:hl7-org:v3", "hl7");
    }

    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        return namespaceMap.getOrDefault(namespaceUri, suggestion);
    }

}
