package com.example.meetup.domain.dto.views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserAccountView {

    private Long id;
    private String profilePicUrl;
    private String fullName;
    private Integer meetsAmnt;
    private List<MeetIndexView> meets;

    public UserAccountView setId(Long id) {
        this.id = id;
        return this;
    }

    public UserAccountView setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
        return this;
    }

    public UserAccountView setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public UserAccountView setMeetsAmnt(Integer meetsAmnt) {
        this.meetsAmnt = meetsAmnt;
        return this;
    }

    public UserAccountView setMeets(List<MeetIndexView> meets) {
        this.meets = meets;
        return this;
    }
}
