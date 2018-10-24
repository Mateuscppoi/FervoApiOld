package br.com.fervo.FervoApp.dto.location;

public class GoogleMapsDTO {

    private String latitude;

    private String longitude;

    public GoogleMapsDTO(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
