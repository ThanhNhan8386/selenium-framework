package bai6_retry.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import bai5_config.utils.ConfigReader;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int retryCount = 0;
    
    @Override
    public boolean retry(ITestResult result) {
        int maxRetry = ConfigReader.getInstance().getRetryCount();
        if (retryCount < maxRetry) {
            retryCount++;
            return true;
        }
        return false;
    }
}

