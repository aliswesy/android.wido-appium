package com.foodics.pages;

import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class FormsPage extends MainPage {
    private static final Logger log = LogManager.getLogger(FormsPage.class);

    @FindBy(xpath = "//android.widget.EditText[@content-desc=\"text-input\"]")
    protected WebElement textInput;
    @FindBy(xpath = "//android.widget.TextView[@content-desc=\"input-text-result\"]")
    protected WebElement inputTextResult;

    @FindBy(xpath = "//android.widget.Switch[@content-desc=\"switch\"]")
    protected WebElement switchBtn;
    @FindBy(xpath = "//android.widget.TextView[@content-desc=\"switch-text\"]")
    protected WebElement switchText;

    @FindBy(xpath = "//android.widget.EditText[@resource-id=\"text_input\"]")
    protected WebElement dropDownList;

    @FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"button-Active\"]")
    protected WebElement activeBtn;
    @FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"button-Inactive\"]")
    protected WebElement inactiveBtn;

    public FormsPage(AndroidDriver driver) {
        super(driver);
    }

    public String inputText(String text) {
        log.info("Input Text: {}", text);
        textInput.clear();
        textInput.sendKeys(text);
        return inputTextResult.getText();
    }

    public String switchText() {
        log.info("Clicking Switch button...");
        switchBtn.click();
        return switchText.getText();
    }

    public String dropDownList(String option) {
        scroll("up");
        dropDownList.click();
        WebElement selectedOption = driver.findElement(By.xpath("//android.widget.CheckedTextView[@resource-id=\"android:id/text1\" and @text=\"" + option + "\"]"));
        selectedOption.click();
        return dropDownList.getText();
    }

    public void activeBtn() {
        log.info("Clicking Active button...");
        activeBtn.click();
    }

    public void inactiveBtn() {
        log.info("Clicking Inactive button...");
        inactiveBtn.click();
    }

}
