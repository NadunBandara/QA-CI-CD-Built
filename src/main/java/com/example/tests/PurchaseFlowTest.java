package com.example.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.example.pages.LoginPage;
import com.example.pages.ProductsPage;

public class PurchaseFlowTest extends BaseTest {

    @Test(description = "Complete purchase flow with valid credentials")
    public void testCompletePurchase() {
        // Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        // Add product to cart
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addProductToCart(0);
        productsPage.goToCart();

        // Checkout
        productsPage.checkout();
        productsPage.enterCheckoutInfo("John", "Doe", "12345");
        productsPage.finishPurchase();

        // Verify success
        String successMsg = productsPage.getSuccessMessage();
        Assert.assertEquals(successMsg, "Thank you for your order!");
    }

    @Test(description = "Test login with invalid credentials")
    public void testInvalidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("invalid_user", "wrong_pass");

        String errorMsg = loginPage.getErrorMessage();
        Assert.assertTrue(errorMsg.contains("Username and password do not match"));
    }
}