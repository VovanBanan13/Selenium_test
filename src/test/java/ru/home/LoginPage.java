package ru.home;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    public WebDriver driver;
    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//*[@id=\"login_form_panel\"]/table[1]/tbody/tr[1]/td[2]/input")
    private WebElement loginField;

    @FindBy(xpath = "//*[@id=\"login_form_panel\"]/table[1]/tbody/tr[2]/td[2]/div/input")
    private WebElement passwordField;

    @FindBy(xpath = "//*[@id=\"button_submit_login_form\"]")
    private WebElement submit;

    public void inputLogin(String login) {
        loginField.sendKeys(login);
    }

    public void inputPasswd(String password) {
        passwordField.sendKeys(password);
    }

    public void clickSubmit() {
        submit.click();
    }
}
