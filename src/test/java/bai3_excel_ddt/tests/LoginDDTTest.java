package bai3_excel_ddt.tests;

import bai1_base.base.BaseTest;
import bai2_pom.pages.LoginPage;
import org.testng.Assert;
import org.testng.ITest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class LoginDDTTest extends BaseTest implements ITest {

    // ThreadLocal này sẽ lưu trữ lại description đọc từ file excel cho mục đích show lên báo cáo TestNG và Console
    private ThreadLocal<String> testName = new ThreadLocal<>();

    @BeforeMethod(alwaysRun = true)
    public void setTestName(Method method, Object[] testData) {
        if (testData != null && testData.length > 0) {
            // Giả thiết cột ngoài cùng (Last Column) của Excel sheet là Description
            testName.set(testData[testData.length - 1].toString());
        } else {
            testName.set(method.getName());
        }
    }

    // Ghi đè phương thức getTestName của Interface ITest của TestNG để tự đổi tên Test report
    @Override
    public String getTestName() {
        return testName.get();
    }

    @Test(dataProvider = "SmokeData", dataProviderClass = bai4_json_faker.dataproviders.JsonDataProvider.class, groups = {"smoke", "regression"})
    public void testLoginSmoke(String username, String password, String expectedUrl, String description) {
        System.out.println("[DDT] Đang chạy Test: " + description);
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(username, password);
        
        // Assert dựa trên URL
        Assert.assertTrue(getDriver().getCurrentUrl().contains(expectedUrl), "URL sau đăng nhập không trùng khớp");
    }

    @Test(dataProvider = "NegativeData", dataProviderClass = bai4_json_faker.dataproviders.JsonDataProvider.class, groups = {"regression"})
    public void testLoginNegative(String username, String password, String expectedError, String description) {
        System.out.println("[DDT] Đang chạy Test: " + description);
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginExpectingFailure(username, password);
        
        // Assert dựa trên Error Message
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Lỗi phải hiển thị đối với case Negative");
        Assert.assertTrue(loginPage.getErrorMessage().contains(expectedError), "Nội dung lỗi không giống mong đợi");
    }

    @Test(dataProvider = "BoundaryData", dataProviderClass = bai4_json_faker.dataproviders.JsonDataProvider.class, groups = {"regression"})
    public void testLoginBoundary(String username, String password, String expectedError, String description) {
        System.out.println("[DDT] Đang chạy Test: " + description);
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginExpectingFailure(username, password);

        // Assert dựa trên Error Message nếu như Expected error có text (tránh NullPointer).
        if(expectedError != null && !expectedError.isEmpty()) {
            Assert.assertTrue(loginPage.isErrorDisplayed(), "Lỗi phải hiển thị đối với case Boundary có ErrorMessage");
            Assert.assertTrue(loginPage.getErrorMessage().contains(expectedError), "Nội dung lỗi ở Boundary case không chuẩn");
        }
    }
}

