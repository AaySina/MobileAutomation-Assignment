package com.project.appium.base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.net.URI;
import java.net.URL;
import java.time.Duration;

public class BaseTest {

    protected AndroidDriver driver;
    private static final String APPIUM_SERVER_URL = "http://127.0.0.1:4723";
    private static final String APP_PACKAGE = "com.saucelabs.mydemoapp.android";
    private static final String APP_ACTIVITY = "com.saucelabs.mydemoapp.android.view.activities.SplashActivity";
    @BeforeMethod(alwaysRun = true)
    public void setUp() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options();

        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setDeviceName("Pixel_9_Pro_XL_API_35");
        options.setAvd("Pixel_9_Pro_XL_API_35");

        options.setApp(System.getProperty("user.dir") + "/apk/demo.apk");

        options.setAppPackage(APP_PACKAGE);
        options.setAppActivity(APP_ACTIVITY);
        options.setAppWaitActivity("*");

        options.setAutoGrantPermissions(true);
        options.setNoReset(false);
        options.setNewCommandTimeout(Duration.ofSeconds(120));
        options.setAdbExecTimeout(Duration.ofSeconds(60));

        URL url = URI.create(APPIUM_SERVER_URL).toURL();

        driver = new AndroidDriver(url, options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}