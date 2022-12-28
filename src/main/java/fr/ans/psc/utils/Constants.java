/**
 * Copyright (C) 2022 ANS (https://esante.gouv.fr)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.ans.psc.utils;

public class Constants {

    // VIHF FIELDS
    public static final String VIHF_VERSION_VALUE = "3.0";
    public static final String VIHF_VERSION = "VIHF_Version";
    public static final String ISSUER_FORMAT = "urn:oasis:names:tc:SAML:1.1:nameid-format:X509SubjectName";
    public static final String IDENTIFIANT_STRUCTURE = "Identifiant_Structure";
    public static final String SECTEUR_ACTIVITE = "Secteur_Activite";
    public static final String SUBJECT_ID = "urn:oasis:names:tc:xspa:1.0:subject:subject-id";
    public static final String AUTHENTIFICATION_MODE = "Authentification_mode";
    public static final String AUTH_MODE_VALUE = "INDIRECTE";
    public static final String RESOURCE_ID = "urn:oasis:names:tc:xacml:2.0:resource:resource-id";
    public static final String RESOURCE_URN = "Ressource_URN";
    public static final String URN_DMP = "urn:dmp";
    public static final String PURPOSE_OF_USE = "urn:oasis:names:tc:xspa:1.0:subject:purposeofuse";
    public static final String LPS_NOM = "LPS_Nom";
    public static final String LPS_VERSION = "LPS_Version";
    public static final String LPS_ID_HOMOLOGATION_DMP = "LPS_ID_HOMOLOGATION_DMP";
    public static final String CE_TYPE = "CE";
    public static final String SUBJECT_ROLE = "urn:oasis:names:tc:xacml:2.0:subject:role";
    public static final String AUTHN_CONTEXT_CLASS_REF = "urn:oasis:names:tc:SAML:2.0:ac:classes:MobileTwoFactorUnregistered";
    public static final String DOCTOR_PROFESSION_CODE = "10";
    public static final String PHARMACIST_PROFESSION_CODE = "21";

    // HEADERS AND GRAVITEE ATTRIBUTES
    public static final String REQUEST_TEMPLATE_VARIABLE = "request";
    public static final String GENERATE_VIHF_ERROR = "GENERATE_VIHF_ERROR";
    public static final String VIHF_SIGNING_ERROR = "VIHF_SIGNING_ERROR";
    public static final String WORK_SITUATION_HEADER = "X-WorkSituation";
    public static final String PATIENT_INS_HEADER = "X-PatientINS";
    public static final String CONTENT_LENGTH_HEADER = "Content-Length";
    public static final String TRANSFER_ENCODING_HEADER = "Transfer-Encoding";
    public static final String CHUNKED = "chunked";
    public static final String USER_INFOS_PAYLOAD_KEY = "openid.userinfo.payload";
    public static final int HTTP_PORT = 80;
    public static final int HTTPS_PORT = 443;

    public static final String ID_SIGN_CONF_KEY = "idSignConf";

    public static final String SIGN_SECRET_KEY = "secret";
    public static final String CONTENT_TYPE_HEADER = "content-type";

    public static final String MULTIPART_FORM_HEADER = "multipart/form-data";

    public static final String ACCEPT_HEADER = "Accept";

    public static final String JSON_HEADER = "application/json";
}
