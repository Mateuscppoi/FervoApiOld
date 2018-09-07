package br.com.fervo.FervoApp.user;

public class UserLoginDTO {

    private Long id;

    private String username;

    private String password;

    private UserDTO user;

    public UserLoginDTO(UserDTO user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDTO getUser() {
        return user;
    }
}
