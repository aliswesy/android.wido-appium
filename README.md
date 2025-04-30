# Android Appium Automation Project

This project automates UI testing for a native Android application using **Appium**, **Java**, **TestNG**, and **Maven**.

---

## Project Structure

android.wido-appium/
├── src
│   └── test
│       ├── java                # Test classes and utilities
│       └── resources           # App APK and JSON data files
│   └── main
│       └── java                # page clases and POJO files
└── test-output                  
│   └── test
│       └── logs                # run log files
├── pom.xml                     # Maven dependencies and project configuration
└── README.md                   # Project documentation

---

## How to run
**Setup configuration** 
  - reload maven project to install all necessary dependancies through `pom.xml` file
  - add the device capabilities in the JSON file `desiredCapabilities.json` in this path `src/test/resources/desiredCapabilities.json`
  - in the desiredCapabilities file leave the app to it's value `${appPath}` as it will be replaced with the path to the app.apk

**Run project**
  - strat emulator device and appium server `appium`
  - run the project through the `testng.xml` file in this path `src/test/resources/testng.xml`
  - log files will be generated at the end of suit run at `test-output/logs`

---

## Dependencies

Key libraries:
- Appium Java Client
- selenium-java
- Selenium webdrivermanager
- TestNG
- json-simple / Gson
- log4j-api / core

See pom.xml for complete dependency info.
