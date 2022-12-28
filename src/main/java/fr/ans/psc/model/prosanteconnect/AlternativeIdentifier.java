/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
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
package fr.ans.psc.model.prosanteconnect;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AlternativeIdentifier {

    @JsonProperty("identifiant")
    private String identifier = null;

    @JsonProperty("origine")
    private String origine = null;

    @JsonProperty("qualite")
    private Integer quality = null;

    public AlternativeIdentifier identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    /**
     * Get identifier
     * @return identifier
     **/
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public AlternativeIdentifier origine(String origine) {
        this.origine = origine;
        return this;
    }

    /**
     * Get origine
     * @return origine
     **/
    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public AlternativeIdentifier quality(Integer quality) {
        this.quality = quality;
        return this;
    }

    /**
     * Get quality
     * @return quality
     **/
    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AlternativeIdentifier alternativeIdentifier = (AlternativeIdentifier) o;
        return Objects.equals(this.identifier, alternativeIdentifier.identifier) &&
                Objects.equals(this.origine, alternativeIdentifier.origine) &&
                Objects.equals(this.quality, alternativeIdentifier.quality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, origine, quality);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AlternativeIdentifier {\n");

        sb.append("    identifier: ").append(toIndentedString(identifier)).append("\n");
        sb.append("    origine: ").append(toIndentedString(origine)).append("\n");
        sb.append("    quality: ").append(toIndentedString(quality)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
