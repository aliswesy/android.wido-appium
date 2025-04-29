package com.foodics.pages;

import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SwipePage extends MainPage {
    private static final Logger log = LogManager.getLogger(SwipePage.class);

    @FindBy(xpath = "(//android.view.ViewGroup[@content-desc=\"card\"])")
    protected WebElement card;

    public SwipePage(AndroidDriver driver) {
        super(driver);
    }

   public Boolean isCardDisplayed(String title) {
        log.info("Searching for card: {}", title);
        By cardTitle = By.xpath("//android.widget.TextView[@text=\"" + title + "\"]");
        return swipeUntilElementVisible(card, cardTitle, "left", 300);
   }

}
