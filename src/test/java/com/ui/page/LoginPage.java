package com.ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
	WebDriver driver;
	//private final By logo = By.xpath("//div[@class='orangehrm-login-logo']/img");
	//private final By titleLogin = By.xpath("//h5[text()='Login']");
	private final By inputUsername = By.xpath("//input[@name='username']");
	private final By inputPassword = By.xpath("//input[@name='password']");
	private final By btnLogin = By.xpath("//button");
	private final By labelForgotPswd = By.xpath("//div[contains(@class,'forgot')]/p");
	private final By btnResetPswd = By.xpath("//button[@type='submit']");
	private final By titleSentPswdReset = By.xpath("//h6");

public LoginPage(WebDriver driver) {
	this.driver = driver;
}

public void loginApplication(String username, String password) throws InterruptedException {
	WebElement usernameInput = driver.findElement(inputUsername);
	WebElement passwordInput = driver.findElement(inputPassword);
	WebElement loginBtn = driver.findElement(btnLogin);
	Thread.sleep(2000);
	usernameInput.sendKeys(username);
	Thread.sleep(2000);
	passwordInput.sendKeys(password);
	loginBtn.click();
} 

public WebElement getTextForgotPswd() {
	WebElement textForgotPswd = driver.findElement(labelForgotPswd);
	return textForgotPswd;
}

public void clickForgotPswd() {
	WebElement textForgotPswd = driver.findElement(labelForgotPswd);
	textForgotPswd.click();
}

public void resetPassword(String username) {
	WebElement usernameInput = driver.findElement(inputUsername);
	usernameInput.sendKeys(username);
	WebElement resetPswdBtn = driver.findElement(btnResetPswd);
	resetPswdBtn.click();
}

public WebElement sentResetMsg() {
	WebElement msgReset = driver.findElement(titleSentPswdReset);
	return msgReset;
}


}