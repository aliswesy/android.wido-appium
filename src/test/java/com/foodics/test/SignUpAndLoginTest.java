package com.foodics.test;

import com.foodics.dataModel.LoginDM;
import com.foodics.pages.LoginPage;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SignUpAndLoginTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(SignUpAndLoginTest.class);

    @Test(dataProvider = "loginData")
    public void test(LoginDM loginDM) {
        SoftAssert sa = new SoftAssert();

        log.info("username: {}", loginDM.getUsername());
        log.info("password: {}", loginDM.getPassword());

        LoginPage loginPage = new LoginPage(driver);

        loginPage.navigateToLogin(); // navigate to register and login page

        // sign up steps and assertion
        loginPage.clickSignUpTab();
        loginPage.fillSignUpData(loginDM.getUsername(), loginDM.getPassword());
        loginPage.clickSignUp();
        String signUpMessage = loginPage.getMessage();
        loginPage.closeMessagePopup();
        sa.assertEquals(signUpMessage, loginDM.getSignUpMessage(), "Sign up failed!!!");

        // login steps and assertion
        loginPage.clickLoginTab();
        loginPage.fillLoginData(loginDM.getUsername(), loginDM.getPassword());
        loginPage.clickLogin();
        String loginMessage = loginPage.getMessage();
        loginPage.closeMessagePopup();
        sa.assertEquals(loginMessage, loginDM.getLoginMessage(), "Login failed!!!");

        sa.assertAll();
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() throws IOException {
        String filePath = System.getProperty("user.dir") + "/src/test/resources/testData/login.json";
        String content = new String(Files.readAllBytes(Paths.get(filePath)));

        Gson gson = new Gson();
        Type listType = new TypeToken<List<LoginDM>>() {
        }.getType();
        List<LoginDM> userDataList = gson.fromJson(content, listType);

        Object[][] testData = new Object[userDataList.size()][1];
        for (int i = 0; i < userDataList.size(); i++) {
            testData[i][0] = userDataList.get(i);
        }

        return testData;
    }
}
