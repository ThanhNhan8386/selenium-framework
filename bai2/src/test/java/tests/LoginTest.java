package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.InventoryPage;

public class LoginTest extends BaseTest {

    @Test
    public void testValidLogin() {
        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(inventoryPage.isLoaded(), "Inventory page should be loaded after successful login");
    }

    @Test
    public void testInvalidUsername() {
        loginPage.loginExpectingFailure("invalid_user", "secret_sauce");
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"), 
                         "Error message should indicate invalid credentials");
    }

    @Test
    public void testInvalidPassword() {
        loginPage.loginExpectingFailure("standard_user", "wrong_password");
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"), 
                         "Error message should indicate invalid credentials");
    }

    @Test
    public void testEmptyCredentials() {
        loginPage.loginExpectingFailure("", "");
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username is required"), 
                         "Error message should indicate username is required");
    }
}