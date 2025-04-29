package com.foodics.dataModel;

public class LoginDM {
    private String username;
    private String password;
    private String signUpMessage;
    private String loginMessage;

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

    public String getSignUpMessage() {
        return signUpMessage;
    }

    public void setSignUpMessage(String signUpMessage) {
        this.signUpMessage = signUpMessage;
    }

    public String getLoginMessage() {
        return loginMessage;
    }

    public void setLoginMessage(String loginMessage) {
        this.loginMessage = loginMessage;
    }
}
