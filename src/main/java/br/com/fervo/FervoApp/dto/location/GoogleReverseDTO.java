package br.com.fervo.FervoApp.dto.location;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GoogleReverseDTO {

    @JsonProperty(value = "results")
    private List<GoogleResultDTO> results;

    public List<GoogleResultDTO> getResults() {
        return results;
    }

    public void setResults(List<GoogleResultDTO> results) {
        this.results = results;
    }
}
