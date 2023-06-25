package com.softwaretestingboard.magento.application;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Homepage {
    private WebDriver driver;

    @FindBy(linkText = "Create an Account")
    WebElement createAccountElem;

    @FindBy(linkText = "Sign In ")
    WebElement signInElem;

    @FindBy(id = "firstname")
    WebElement firstNameElem;

    @FindBy(id= "lastname")
    WebElement lastnameElem;

    @FindBy(id = "email_address")
    WebElement createAccountEmailElem;

    @FindBy(id = "password")
    WebElement createAccountPasswordElem;

    @FindBy(id = "password-confirmation")
    WebElement confirmationPasswordElem;

    @FindBy(css = ".action.submit.primary")
    WebElement createAccountBtn;
    @FindBy(id = "email")
    WebElement emailInput;

    @FindBy(id = "pass")
    WebElement passwordInput;

    @FindBy(css = ".action.login.primary")
    WebElement loginBtn;

    public Homepage(WebDriver driver) {
        this.driver = driver;
    }

    public void Login(String email, String password){
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        loginBtn.click();
    }

    public void CreateAccount(String email, String password, String firstName, String lastName, String confirmPassword){
        firstNameElem.sendKeys(firstName);
        lastnameElem.sendKeys(lastName);
        createAccountEmailElem.sendKeys(email);
        createAccountPasswordElem.sendKeys(password);
        confirmationPasswordElem.sendKeys(confirmPassword);
        createAccountBtn.click();
    }
}
