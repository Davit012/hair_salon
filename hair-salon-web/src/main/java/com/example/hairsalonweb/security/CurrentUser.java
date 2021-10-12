package com.example.hairsalonweb.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class CurrentUser extends User {

    private final com.hairsaloncommon.model.User user;

    public CurrentUser(com.hairsaloncommon.model.User user) {

        super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getUserType().name()));
        this.user = user;

    }

    public com.hairsaloncommon.model.User getUser() {
        return user;
    }
}
