package br.com.fervo.FervoApp.dto.location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties
public class GoogleResultDTO {

    @JsonProperty(value = "address_components")
    private List<AddressComponentsDTO> addressComponents;

    public List<AddressComponentsDTO> getAddressComponents() {
        return addressComponents;
    }

    public void setAddressComponents(List<AddressComponentsDTO> addressComponents) {
        this.addressComponents = addressComponents;
    }
}
