package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Sample Page Object để demonstrate việc sử dụng BasePage
 * Page này sẽ gây compilation error nếu comment out bất kỳ method nào trong BasePage
 */
public class SamplePage extends BasePage {
    
    // Locators
    private By searchBox = By.name("q");
    private By searchButton = By.name("btnK");
    private By resultsStats = By.id("result-stats");
    
    public SamplePage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Demonstrate sử dụng tất cả 7 methods từ BasePage
     */
    public void performSearch(String searchTerm) {
        // Sử dụng waitAndType
        waitAndType(searchBox, searchTerm);
        
        // Sử dụng waitAndClick
        waitAndClick(searchButton);
        
        // Sử dụng waitForPageLoad
        waitForPageLoad();
        
        // Sử dụng isElementVisible
        if (isElementVisible(resultsStats)) {
            // Sử dụng getText
            String stats = getText(resultsStats);
            System.out.println("Search results: " + stats);
        }
        
        // Sử dụng scrollToElement
        scrollToElement(resultsStats);
        
        // Sử dụng getAttribute
        String searchValue = getAttribute(searchBox, "value");
        System.out.println("Search term: " + searchValue);
    }
}