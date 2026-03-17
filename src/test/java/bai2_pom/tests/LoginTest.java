package bai2_pom.tests;

import bai1_base.base.BaseTest;
import bai2_pom.pages.InventoryPage;
import bai2_pom.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void testLoginSuccess() {
        System.out.println("[INFO] Test: Đăng nhập thành công");
        LoginPage loginPage = new LoginPage(getDriver());
        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce");
        
        Assert.assertTrue(inventoryPage.isLoaded(), "Trang Inventory không được load sau khi đăng nhập!");
    }

    @Test
    public void testLoginFail() {
        System.out.println("[INFO] Test: Đăng nhập thất bại do sai PASS");
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginExpectingFailure("standard_user", "wrong_password");
        
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Lỗi không được hiển thị");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"), "Câu lỗi không đúng");
    }

    @Test
    public void testEmptyUsername() {
        System.out.println("[INFO] Test: Đăng nhập tài khoản rỗng");
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginExpectingFailure("", "secret_sauce");
        
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Lỗi field trống không hiển thị");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username is required"), "Câu lỗi sai hoặc mất tiêu");
    }
}
