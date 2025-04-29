package com.foodics.test;

import com.foodics.dataModel.SwipeDM;
import com.foodics.pages.SwipePage;
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

public class SwipeTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(SwipeTest.class);

    @Test(dataProvider = "loginData")
    public void test(SwipeDM swipeDM) {
        SoftAssert sa = new SoftAssert();

        SwipePage swipePage = new SwipePage(driver);
        swipePage.navigateToSwipe();
        sa.assertTrue(swipePage.isCardDisplayed(swipeDM.getTitle()), "Card not displayed!!!");

        sa.assertAll();
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() throws IOException {
        String filePath = System.getProperty("user.dir") + "/src/test/resources/testData/swipe.json";
        String content = new String(Files.readAllBytes(Paths.get(filePath)));

        Gson gson = new Gson();
        Type listType = new TypeToken<List<SwipeDM>>() {
        }.getType();
        List<SwipeDM> userDataList = gson.fromJson(content, listType);

        Object[][] testData = new Object[userDataList.size()][1];
        for (int i = 0; i < userDataList.size(); i++) {
            testData[i][0] = userDataList.get(i);
        }

        return testData;
    }
}
