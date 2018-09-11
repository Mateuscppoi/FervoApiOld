package br.com.fervo.FervoApp.dto.user;

import br.com.fervo.FervoApp.dto.rewards.UserRewardsDTO;

import java.util.List;

public class UserDTO {

    private ProfileDTO userProfile;

    private List<UserRewardsDTO> userRewards;

    public ProfileDTO getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(ProfileDTO userProfile) {
        this.userProfile = userProfile;
    }

    public List<UserRewardsDTO> getUserRewards() {
        return userRewards;
    }

    public void setUserRewards(List<UserRewardsDTO> userRewards) {
        this.userRewards = userRewards;
    }
}
