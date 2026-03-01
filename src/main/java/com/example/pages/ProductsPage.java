package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class ProductsPage extends BasePage {

    @FindBy(className = "inventory_item")
    private List<WebElement> products;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartButton;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(id = "first-name")
    private WebElement firstNameInput;

    @FindBy(id = "last-name")
    private WebElement lastNameInput;

    @FindBy(id = "postal-code")
    private WebElement zipCodeInput;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(className = "complete-header")
    private WebElement successMessage;

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public void addProductToCart(int productIndex) {
        wait.until(ExpectedConditions.visibilityOfAllElements(products));
        products.get(productIndex).findElement(By.cssSelector(".btn_inventory")).click();
    }

    public void goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartButton)).click();
    }

    public void checkout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
    }

    public void enterCheckoutInfo(String firstName, String lastName, String zip) {
        wait.until(ExpectedConditions.visibilityOf(firstNameInput)).sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        zipCodeInput.sendKeys(zip);
        continueButton.click();
    }

    public void finishPurchase() {
        wait.until(ExpectedConditions.elementToBeClickable(finishButton)).click();
    }

    public String getSuccessMessage() {
        return wait.until(ExpectedConditions.visibilityOf(successMessage)).getText();
    }
}