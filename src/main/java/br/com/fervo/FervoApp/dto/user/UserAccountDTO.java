package br.com.fervo.FervoApp.dto.user;

public class UserAccountDTO {

    private ProfileDTO profile;
    private UserLoginDTO userLogin;

    public ProfileDTO getProfile() {
        return profile;
    }

    public void setProfile(ProfileDTO profile) {
        this.profile = profile;
    }

    public UserLoginDTO getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(UserLoginDTO userLogin) {
        this.userLogin = userLogin;
    }
}
