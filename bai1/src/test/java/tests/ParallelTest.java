package tests;

import base.BaseTest;
import pages.SamplePage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class để kiểm tra parallel execution và sử dụng Page Objects
 */
public class ParallelTest extends BaseTest {
    
    @Test
    public void testParallelExecution1() throws InterruptedException {
        getDriver().get("https://www.google.com");
        SamplePage samplePage = new SamplePage(getDriver());
        
        // Test sẽ mất thời gian để verify parallel execution
        Thread.sleep(2000);
        
        String title = getDriver().getTitle();
        Assert.assertTrue(title.contains("Google"), "Page title should contain Google");
        System.out.println("Test 1 completed on thread: " + Thread.currentThread().getName());
    }
    
    @Test
    public void testParallelExecution2() throws InterruptedException {
        getDriver().get("https://www.example.com");
        
        Thread.sleep(3000);
        
        String title = getDriver().getTitle();
        Assert.assertTrue(title.contains("Example"), "Page title should contain Example");
        System.out.println("Test 2 completed on thread: " + Thread.currentThread().getName());
    }
    
    @Test
    public void testParallelExecution3() throws InterruptedException {
        getDriver().get("https://httpbin.org/");
        
        Thread.sleep(1500);
        
        String title = getDriver().getTitle();
        Assert.assertTrue(title.contains("httpbin"), "Page title should contain httpbin");
        System.out.println("Test 3 completed on thread: " + Thread.currentThread().getName());
    }
}