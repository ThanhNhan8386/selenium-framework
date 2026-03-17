package bai2_pom.pages;

import bai1_base.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(id = "user-name")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public InventoryPage login(String username, String password) {
        waitAndType(usernameInput, username);
        waitAndType(passwordInput, password);
        waitAndClick(loginButton);
        return new InventoryPage(driver);
    }

    public LoginPage loginExpectingFailure(String username, String password) {
        waitAndType(usernameInput, username);
        waitAndType(passwordInput, password);
        waitAndClick(loginButton);
        return this;
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isErrorDisplayed() {
        return isElementVisible(By.cssSelector("[data-test='error']"));
    }
}

