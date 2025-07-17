package ReportTC;

import Tz.BaseSetup;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import ui.driver.CaptureHelpers;

public class TestListener implements ITestListener {
    @Override
    public void onFinish(ITestContext arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStart(ITestContext arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTestFailure(ITestResult arg0) {
        try {
            System.out.println("Failed TCs: " + arg0.getName());
            CaptureHelpers.captureScreenshot(new BaseSetup().getDriver(), arg0.getName());
        } catch (Exception e) {
            System.out.println("Exception while taking screenshot " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTestStart(ITestResult arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTestSuccess(ITestResult arg0) {
        System.out.println("Passed TCs: " + arg0.getName());

    }
}
