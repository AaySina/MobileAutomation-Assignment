package com.project.appium.tests;

import com.project.appium.base.BaseTest;
import com.project.appium.pages.LoginPage;
import com.project.appium.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(priority = 1, description = "Test login berhasil")
    public void testLoginSuccess() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);

        loginPage.performLogin("bob@example.com", "10203040");

        Assert.assertTrue(productsPage.isProductsPageDisplayed(), "Halaman produk tidak muncul");
        Assert.assertEquals(productsPage.getProductsPageTitle(), "Products", "Judul halaman salah");
    }

    @Test(priority = 2, description = "Test ketersediaan produk")
    public void testProductAvailabilityAfterLogin() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);

        loginPage.performLogin("bob@example.com", "10203040");

        Assert.assertTrue(productsPage.getProductCount() > 0, "List produk kosong");
    }
}