package com.foodics.test;

import com.foodics.dataModel.FormsDM;
import com.foodics.pages.FormsPage;
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

public class FormsTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(FormsTest.class);

    @Test(dataProvider = "loginData")
    public void test(FormsDM formsDM) {
        SoftAssert sa = new SoftAssert();

        FormsPage formsPage = new FormsPage(driver);

        formsPage.navigateToForms(); // navigate to Forms page

        // input Text assertion
        String inputText = formsPage.inputText(formsDM.getInputText());
        sa.assertEquals(inputText, formsDM.getInputText(), "Input text failed!!!");

        // switch button assertion
        String switchMessage = formsPage.switchText();
        sa.assertEquals(switchMessage, formsDM.getSwitchMessage(), "Switch text failed!!!");

        // drop down list assertion
        String dropDownOption = formsPage.dropDownList(formsDM.getDropDownOption());
        sa.assertEquals(dropDownOption, formsDM.getDropDownOption(), "Drop down list failed!!!");

        // buttons assertion
        formsPage.activeBtn();
        sa.assertTrue(formsPage.isMessagePopupDisplayed(), "Active button failed!!!");
        formsPage.closeMessagePopup();

        formsPage.inactiveBtn();
        sa.assertTrue(formsPage.isMessagePopupDisplayed(), "Inactive button failed!!!");

        sa.assertAll();
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() throws IOException {
        String filePath = System.getProperty("user.dir") + "/src/test/resources/testData/forms.json";
        String content = new String(Files.readAllBytes(Paths.get(filePath)));

        Gson gson = new Gson();
        Type listType = new TypeToken<List<FormsDM>>() {
        }.getType();
        List<FormsDM> userDataList = gson.fromJson(content, listType);

        Object[][] testData = new Object[userDataList.size()][1];
        for (int i = 0; i < userDataList.size(); i++) {
            testData[i][0] = userDataList.get(i);
        }

        return testData;
    }
}
