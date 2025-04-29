package com.foodics.pages;

import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends MainPage {
    private static final Logger log = LogManager.getLogger(LoginPage.class);

    @FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"button-login-container\"]")
    protected WebElement loginTab;
    @FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"button-sign-up-container\"]")
    protected WebElement signupTab;

    @FindBy(xpath = "//android.widget.EditText[@content-desc=\"input-email\"]")
    protected WebElement emailTxt;
    @FindBy(xpath = "//android.widget.EditText[@content-desc=\"input-password\"]")
    protected WebElement passwordTxt;
    @FindBy(xpath = "//android.widget.EditText[@content-desc=\"input-repeat-password\"]")
    protected WebElement confirmPasswordTxt;
    @FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"button-SIGN UP\"]")
    protected WebElement signupBtn;
    @FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"button-LOGIN\"]")
    protected WebElement loginBtn;

    public LoginPage(AndroidDriver driver) {
        super(driver);
    }

    public void clickSignUpTab() {
        log.info("Navigating to Sign Up Tab...");
        signupTab.click();
    }

    public void clickLoginTab() {
        log.info("Navigating to Login Tab...");
        loginTab.click();
    }

    public void fillSignUpData(String email, String password) {
        enterEmail(email);
        enterPassword(password);

        confirmPasswordTxt.clear();
        confirmPasswordTxt.sendKeys(password);
    }

    public void fillLoginData(String email, String password) {
        enterEmail(email);
        enterPassword(password);
    }

    public void enterEmail(String email) {
        log.info("Email: {}", email);
        emailTxt.clear();
        emailTxt.sendKeys(email);
    }

    public void enterPassword(String password) {
        log.info("Password: {}", password);
        passwordTxt.clear();
        passwordTxt.sendKeys(password);
    }

    public void clickSignUp() {
        log.info("Clicking Sign Up button...");
        signupBtn.click();
    }

    public void clickLogin() {
        log.info("Clicking Login button...");
        loginBtn.click();
    }

}
