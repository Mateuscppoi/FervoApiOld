package br.com.fervo.FervoApp.dto.user;

public enum ConnectionType {

    FACEBOOK("Facebook"), GOOGLE("Google"), LOCAL("LocalAccount"), EMAIL("Email");

    private String description;

    ConnectionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
