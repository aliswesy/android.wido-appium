package com.foodics.pages;

import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

import static java.time.Duration.ofMillis;

public class MainPage {
    private static final Logger log = LogManager.getLogger(MainPage.class);

    @FindBy(xpath = "//android.view.View[@content-desc=\"Home\"]")
    protected WebElement HomeBtn;
    @FindBy(xpath = "//android.view.View[@content-desc=\"Webview\"]")
    protected WebElement webViewBtn;
    @FindBy(xpath = "//android.view.View[@content-desc=\"Login\"]")
    protected WebElement loginPageBtn;
    @FindBy(xpath = "//android.view.View[@content-desc=\"Forms\"]")
    protected WebElement formsBtn;
    @FindBy(xpath = "//android.view.View[@content-desc=\"Swipe\"]")
    protected WebElement swipeBtn;

    @FindBy(id = "android:id/message")
    protected WebElement messageLbl;
    @FindBy(id = "android:id/button1")
    protected WebElement okBtn;

    AndroidDriver driver;

    /// ------ Main page Navigation -------///
    public MainPage(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void navigateToLogin() {
        log.info("Navigating to Login Page...");
        waitUntilElementVisible(500, loginPageBtn);
        loginPageBtn.click();
    }

    public void navigateToForms() {
        log.info("Navigating to Forms Page...");
        formsBtn.click();
    }

    public void navigateToSwipe() {
        log.info("Navigating to Swap Page...");
        swipeBtn.click();
    }

    /// ------ Common popup message methods -------///
    public String getMessage() {
        waitUntilElementVisible(500, messageLbl);
        log.info("Displayed message: {}", messageLbl.getText());
        return messageLbl.getText();
    }

    public void closeMessagePopup() {
        log.info("Clicking OK button...");
        okBtn.click();
    }

    public Boolean isMessagePopupDisplayed() {
        List<WebElement> popUpMessage = driver.findElements(By.id("android:id/content"));
        return !popUpMessage.isEmpty();
    }

    /// ------ Wait methods -------///
    public void waitUntilElementVisible(int intervalMillis, WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(ofMillis(intervalMillis))
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.visibilityOf(element));
    }

    /// ------ Gestures methods -------///

    /**
     * Scrolls the screen up or down using the Sequence class.
     *
     * @param direction "up" for scrolling up, "down" for scrolling down.
     */
    public void scroll(String direction) {
        log.info("***** Scrolling {} ******", direction);
        int screenHeight = driver.manage().window().getSize().getHeight();
        int screenWidth = driver.manage().window().getSize().getWidth();

        int startX = screenWidth / 2;
        int startY, endY;

        if (direction.equalsIgnoreCase("down")) {
            // Scroll down (from top to bottom)
            startY = (int) (screenHeight * 0.2);
            endY = (int) (screenHeight * 0.8);
        } else if (direction.equalsIgnoreCase("up")) {
            // Scroll up (from bottom to top)
            startY = (int) (screenHeight * 0.8);
            endY = (int) (screenHeight * 0.2);
        } else {
            throw new IllegalArgumentException("Direction must be 'up' or 'down'");
        }

        // Define a finger input
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        // Create the scroll sequence
        Sequence scroll = new Sequence(finger, 0)
                .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), startX, endY))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        // Perform the scroll action
        driver.perform(Collections.singletonList(scroll));
    }

    /**
     * It tries to locate a target element and if not found, swipes in a given direction repeatedly until the element
     * becomes visible or a maximum swipe count is reached (Count = 5)
     *
     * @param elementToSwipeOn WebElement object of the element to swipe on
     * @param locator          By locator of the target element to be found
     * @param direction        "up", "down", "left", or "right"
     * @param swipeDuration    Duration of the swipe in milliseconds
     */
    public Boolean swipeUntilElementVisible(WebElement elementToSwipeOn, By locator, String direction, int swipeDuration) {
        waitUntilElementVisible(5000, elementToSwipeOn);
        // Try to find the target element using its locator
        boolean isFound = false;
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10));
        int count = 0;
        while (!isFound && count < 5) {
            try {
                WebElement element = driver.findElement(locator);
                if (element.isDisplayed()) {
                    swipeOnElement(elementToSwipeOn, direction, swipeDuration);
                    // Target element found, break the loop
                    log.info("Target element found!");
                    isFound = true;
                } else {
                    // Target element not found, proceed to swipe on the given element
                    log.info("Target element not found, swiping " + direction + " on element...");
                }
            } catch (Exception ex) {
                log.info("Target element not found, swiping " + direction + " on element...");
            } finally {
                if (!isFound) {
                    swipeOnElement(elementToSwipeOn, direction, swipeDuration);
                }
            }
            count++;
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return isFound;
    }

    /**
     * swipe on a given web element in a specific direction
     *
     * @param element       element to swipe on
     * @param direction     direction to swipe in (up, down, left, right)
     * @param swipeDuration duration of the swipe in milliseconds
     */
    public void swipeOnElement(WebElement element, String direction, int swipeDuration) {
        log.info("Swiping...");
        // Get element's center point to start the swipe
        int startX = element.getLocation().getX() + (element.getSize().getWidth() / 2);
        int startY = element.getLocation().getY() + (element.getSize().getHeight() / 2);

        // Initialize endX and endY
        int endX = startX;
        int endY = startY;

        // Adjust the swipe direction
        switch (direction.toLowerCase()) {
            case "up":
                endY = startY - 400; // Swipe up
                break;
            case "down":
                endY = startY + 400; // Swipe down
                break;
            case "left":
                endX = startX - 400; // Swipe left
                break;
            case "right":
                endX = startX + 400; // Swipe right
                break;
        }

        // Perform the swipe using W3C Actions API
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(swipeDuration), PointerInput.Origin.viewport(), endX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }
}
