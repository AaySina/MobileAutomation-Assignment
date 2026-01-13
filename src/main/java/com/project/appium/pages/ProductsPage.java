package com.project.appium.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProductsPage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;
    private final String PAGE_TITLE_ID = "com.saucelabs.mydemoapp.android:id/mTvTitle";
    private final String PAGE_TITLE_TEXT_XPATH = "//android.widget.TextView[@text='Products']";
    private final String FIRST_PRODUCT_IMAGE_XPATH = "(//android.widget.ImageView[@resource-id='com.saucelabs.mydemoapp.android:id/productIV'])[1]";
    private final String ADD_TO_CART_BUTTON_ID = "com.saucelabs.mydemoapp.android:id/cartBt";
    private final String CART_ICON_ID = "com.saucelabs.mydemoapp.android:id/cartIV";
    private final String CART_BADGE_ID = "com.saucelabs.mydemoapp.android:id/cartTV";
    private final String PRODUCT_ITEMS_ID = "com.saucelabs.mydemoapp.android:id/productIV";

    public ProductsPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public boolean isProductsPageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id(PAGE_TITLE_ID)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getProductsPageTitle() {
        try {
            // Strategi 1: Tunggu teks "Products" muncul di ID title spesifik
            wait.until(ExpectedConditions.textToBePresentInElementLocated(
                    AppiumBy.id(PAGE_TITLE_ID), "Products"));
            return driver.findElement(AppiumBy.id(PAGE_TITLE_ID)).getText();

        } catch (Exception e) {
            System.out.println("Warning: Teks di ID utama kosong/timeout. Mencoba strategi cadangan...");

            // Strategi 2 (Fallback): Cari elemen APAPUN yang punya teks "Products"
            try {
                WebElement titleByText = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        AppiumBy.xpath(PAGE_TITLE_TEXT_XPATH)));
                return titleByText.getText();
            } catch (Exception ex) {
                // Jika masih gagal, berarti kita memang belum di halaman Products
                return "";
            }
        }
    }

    public void clickAddToCartFirstProduct() {
        // Tunggu produk list muncul
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath(FIRST_PRODUCT_IMAGE_XPATH)));

        // Klik produk
        WebElement firstProduct = driver.findElement(AppiumBy.xpath(FIRST_PRODUCT_IMAGE_XPATH));
        firstProduct.click();

        // Klik Add to Cart
        WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.id(ADD_TO_CART_BUTTON_ID)));
        addToCartBtn.click();
    }

    public boolean isCartBadgeDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id(CART_BADGE_ID))).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getCartBadgeCount() {
        try {
            return driver.findElement(AppiumBy.id(CART_BADGE_ID)).getText();
        } catch (Exception e) {
            return "0";
        }
    }

    public int getProductCount() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id(PRODUCT_ITEMS_ID)));
            List<WebElement> items = driver.findElements(AppiumBy.id(PRODUCT_ITEMS_ID));
            return items.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public void clickCartIcon() {
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.id(CART_ICON_ID))).click();
    }
}