package bai4_json_faker.tests;

import bai1_base.base.BaseTest;
import bai2_pom.pages.InventoryPage;
import bai2_pom.pages.LoginPage;
import org.testng.Assert;
import org.testng.ITest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class UserLoginTest extends BaseTest implements ITest {
    private ThreadLocal<String> testName = new ThreadLocal<>();

    @BeforeMethod(alwaysRun = true)
    public void setTestName(Method method, Object[] testData) {
        if (testData != null && testData.length > 0) {
            testName.set(testData[testData.length - 1].toString());
        } else {
            testName.set(method.getName());
        }
    }

    @Override
    public String getTestName() {
        return testName.get();
    }

    @Test(dataProvider = "jsonUsers", dataProviderClass = bai4_json_faker.dataproviders.JsonDataProvider.class)
    public void testLoginWithJsonData(String username, String password, boolean expectSuccess, String description) {
        System.out.println("[JSON_DDT] Đang chạy Test: " + description);
        LoginPage loginPage = new LoginPage(getDriver());
        
        if (expectSuccess) {
            InventoryPage inventoryPage = loginPage.login(username, password);
            Assert.assertTrue(inventoryPage.isLoaded(), "Trang Inventory không load sau khi login hợp lệ.");
        } else {
            loginPage.loginExpectingFailure(username, password);
            Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message không hiển thị sau khi login lỗi.");
        }
    }
}

