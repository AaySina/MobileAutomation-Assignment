package com.project.appium.tests;

import com.project.appium.base.BaseTest;
import com.project.appium.pages.LoginPage;
import com.project.appium.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AddToCartTest extends BaseTest {

    private LoginPage loginPage;
    private ProductsPage productsPage;
    @BeforeMethod
    public void loginBeforeTest() {
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);

        System.out.println("--- Setup: Login for Test ---");
        loginPage.performLogin("bob@example.com", "10203040");

        Assert.assertTrue(productsPage.isProductsPageDisplayed(), "Gagal Login");
    }

    @Test(priority = 1, description = "Test menambahkan produk pertama ke cart")
    public void testAddFirstProductToCart() {
        productsPage.clickAddToCartFirstProduct();
        Assert.assertTrue(productsPage.isCartBadgeDisplayed(), "Badge cart tidak muncul");
        Assert.assertEquals(productsPage.getCartBadgeCount(), "1", "Jumlah item di cart salah");
    }

    @Test(priority = 2, description = "Test complete add to cart flow")
    public void testCompleteAddToCartFlow() {
        if (productsPage.getProductCount() > 0) {
            productsPage.clickAddToCartFirstProduct();
            productsPage.clickCartIcon();
            System.out.println("Masuk ke Cart Page");
        } else {
            Assert.fail("Tidak ada produk tersedia");
        }
    }
}