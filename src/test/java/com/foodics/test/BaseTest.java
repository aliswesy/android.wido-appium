package com.foodics.test;

import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

public class BaseTest {
    private static final Logger log = LogManager.getLogger(BaseTest.class);
    public static AndroidDriver driver;

    @BeforeSuite
    public void setupDriver() throws IOException, ParseException {
        String capabilitiesPath = Paths.get(System.getProperty("user.dir"), "src", "test", "resources", "desiredCapabilities.json").toString();
        String appPath = Paths.get(System.getProperty("user.dir"), "src", "test", "resources", "android.wdio.native.app.v1.0.8.apk").toString();

        log.info("Desired Capabilities Path: {}", capabilitiesPath);
        log.info("APK Path: {}", appPath);

        // Verify APK file exists
        File appFile = new File(appPath);
        if (!appFile.exists()) {
            throw new FileNotFoundException("APK file not found at: " + appPath);
        }

        // Read JSON file
        String jsonFile = Files.readString(Paths.get(capabilitiesPath));

        // Replace placeholder with properly escaped path
        appPath = appPath.replace("\\", "\\\\"); // Escape backslashes for JSON
        jsonFile = jsonFile.replace("${appPath}", appPath);

        log.info("Desired Capabilities: \n{}", jsonFile);

        // Parse JSON and create capabilities
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(jsonFile);
        DesiredCapabilities caps = new DesiredCapabilities(object);

        // Initialize driver
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), caps);

        // Set device orientation to LANDSCAPE
//        driver.rotate(ScreenOrientation.LANDSCAPE);

        // Set implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterSuite
    public void tearDown() {
        driver.quit();
    }

}
