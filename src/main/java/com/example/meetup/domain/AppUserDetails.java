package com.example.meetup.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AppUserDetails extends User {

    private Long entityId;

    public AppUserDetails(String username, String password,
                          Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public long getEntityId() {
        return entityId;
    }

    public AppUserDetails setEntityId(Long entityId) {
        this.entityId = entityId;
        return this;
    }
}
