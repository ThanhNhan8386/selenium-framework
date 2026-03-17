package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Sample test class để kiểm tra BasePage và BaseTest hoạt động
 */
public class SampleTest extends BaseTest {
    
    @Test
    public void testGoogleHomePage() {
        getDriver().get("https://www.google.com");
        String title = getDriver().getTitle();
        Assert.assertTrue(title.contains("Google"), "Page title should contain Google");
    }
    
    @Test
    public void testFailedTest() {
        getDriver().get("https://www.google.com");
        // Test này sẽ fail để kiểm tra screenshot được chụp
        Assert.fail("This is an intentional failure to test screenshot capture");
    }
    
    @Test
    public void testAnotherPage() {
        getDriver().get("https://www.example.com");
        String title = getDriver().getTitle();
        Assert.assertTrue(title.contains("Example"), "Page title should contain Example");
    }
}