package tests;

import base.BaseTest;
import pages.SamplePage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class để demonstrate dependency vào BasePage
 * Nếu comment out bất kỳ method nào trong BasePage sẽ gây compilation error
 */
public class BasePageDependencyTest extends BaseTest {
    
    @Test
    public void testAllBasePageMethods() {
        getDriver().get("https://www.google.com");
        SamplePage samplePage = new SamplePage(getDriver());
        
        // Test này sẽ sử dụng tất cả methods từ BasePage thông qua SamplePage
        // Nếu comment out bất kỳ method nào trong BasePage → compilation error
        samplePage.performSearch("Selenium WebDriver");
        
        String title = getDriver().getTitle();
        Assert.assertTrue(title.toLowerCase().contains("selenium") || 
                         title.toLowerCase().contains("google"), 
                         "Page should contain search results or Google");
    }
}