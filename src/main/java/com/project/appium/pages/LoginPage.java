package com.project.appium.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;
    // --- LOCATORS ---
    private final String MENU_ICON_ID = "com.saucelabs.mydemoapp.android:id/menuIV";
    private final String USERNAME_FIELD_ID = "com.saucelabs.mydemoapp.android:id/nameET";
    private final String PASSWORD_FIELD_ID = "com.saucelabs.mydemoapp.android:id/passwordET";
    private final String LOGIN_BUTTON_ID = "com.saucelabs.mydemoapp.android:id/loginBtn";
    // Menu Items
    private final String MENU_LOGIN_XPATH = "//android.widget.TextView[@resource-id='com.saucelabs.mydemoapp.android:id/itemTV' and @text='Log In']";
    private final String MENU_LOGOUT_XPATH = "//android.widget.TextView[@resource-id='com.saucelabs.mydemoapp.android:id/itemTV' and @text='Log Out']";
    private final String CONFIRM_LOGOUT_BTN_XPATH = "//android.widget.Button[@text='LOG OUT']";

    public LoginPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void performLogin(String username, String password) {
        System.out.println("=== Starting Login Process ===");
        if (isUserLoggedIn()) {
            System.out.println("User is already logged in. Logging out first...");
            performLogout();
        }
        if (!isLoginFieldVisible()) {
            openMenuAndSelectLogin();
        }
        System.out.println("Inputting Username...");
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id(USERNAME_FIELD_ID)));
        usernameField.clear();
        usernameField.sendKeys(username);

        System.out.println("Inputting Password...");
        driver.findElement(AppiumBy.id(PASSWORD_FIELD_ID)).sendKeys(password);

        System.out.println("Clicking Login Button...");
        driver.findElement(AppiumBy.id(LOGIN_BUTTON_ID)).click();

        System.out.println("Login button clicked.");
    }

    public void performLogout() {
        try {
            System.out.println("Attempting to Logout...");
            WebElement menuIcon = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.id(MENU_ICON_ID)));
            menuIcon.click();
            waitFor(1000);

            WebElement logoutOption = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath(MENU_LOGOUT_XPATH)));
            logoutOption.click();
            try {
                WebElement confirmBtn = new WebDriverWait(driver, Duration.ofSeconds(3))
                        .until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath(CONFIRM_LOGOUT_BTN_XPATH)));
                confirmBtn.click();
            } catch (Exception e) {
            }

            waitFor(1000);
            System.out.println("Logout successful.");

        } catch (Exception e) {
            System.out.println("Logout failed/skipped: " + e.getMessage());
        }
    }

    private boolean isUserLoggedIn() {
        try {
            WebElement menuIcon = driver.findElement(AppiumBy.id(MENU_ICON_ID));
            menuIcon.click();
            waitFor(1000);

            boolean hasLogout = !driver.findElements(AppiumBy.xpath(MENU_LOGOUT_XPATH)).isEmpty();
            try {
                menuIcon.click();
            } catch (Exception ex) {}

            return hasLogout;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isLoginFieldVisible() {
        try {
            return !driver.findElements(AppiumBy.id(USERNAME_FIELD_ID)).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    private void openMenuAndSelectLogin() {
        System.out.println("Navigating to Login Screen via Menu...");
        try {
            WebElement menuIcon = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.id(MENU_ICON_ID)));
            menuIcon.click();

            waitFor(1500);

            WebElement loginOption = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath(MENU_LOGIN_XPATH)));
            loginOption.click();

            waitFor(1000);
        } catch (Exception e) {
            System.out.println("Menu navigation issue: " + e.getMessage());
        }
    }
    private void waitFor(long millis) {
        try { Thread.sleep(millis); } catch (InterruptedException e) {}
    }
}