package com.example.ex4;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;

/**
 *In order to track each user, user info is held in this component-
 * which is scoped to the session scope.
 *The info being stored is the following:
 *name- users name.
 *isLoggedIn- Boolean for- is user logged in.
 *NewMessageId- will indicate if new messages were added.
 *profileIndex- to apply a profile picture.
 */

@Component
@SessionScope
public class DataSessionScope implements Serializable {

    private String name;
    private boolean isLoggedIn = false;
    private Long NewMessageId;
    private int profileIndex;

    public DataSessionScope() { }

    public String getName() {
            return name;
        }

    public void setName(String name) {
            this.name = name;
        }


    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }


    public Long getNewMessageId() {
        return NewMessageId;
    }

    public void setNewMessageId(Long newMessageId) {
        NewMessageId = newMessageId;
    }

    public int getProfileIndex() {
        return profileIndex;
    }

    public void setProfileIndex(int profileIndex) {
        this.profileIndex = profileIndex;
    }

}

