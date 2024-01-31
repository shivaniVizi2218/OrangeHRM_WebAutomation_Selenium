package com.ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CreateUser {
	private WebDriver driver;
	private final By labelAdmin = By.xpath("//span[text()='Admin']");
	private final By btnAdd = By.xpath("//button[normalize-space()='Add']");
	private final By ddUserRole = By.xpath("//label[contains(text(),'User Role')]//parent::div//following::div[contains(@class,'text-input')]");
	private final By ddStatus = By.xpath("//label[contains(text(),'Status')]//parent::div//following::div[contains(@class,'text-input')]");
	private final By inputEmpName = By.xpath("//input[contains(@placeholder,'Type')]");
	private final By inputUserUsername = By.xpath("(//label[contains(text(),'Username')]//parent::div//following::div/input)[1]");
	private final By inputUserPswd = By.xpath("(//label[contains(text(),'Username')]//parent::div//following::div/input[@type='password'])[1]");
	private final By inputConfirmPswd = By.xpath("(//label[contains(text(),'Username')]//parent::div//following::div/input[@type='password'])[2]");
	private final By btnSave = By.xpath("//button[normalize-space()='Save']");
	
	public CreateUser(WebDriver driver) {
		this.driver = driver;
	}
	
	public void addingUser() {
		WebElement adminLabel = driver.findElement(labelAdmin);
		adminLabel.click();
		WebElement addBtn = driver.findElement(btnAdd);
		addBtn.click();
	}
	
	public void selectUserRole() {
		WebElement userRoleDropdown = driver.findElement(ddUserRole);
		userRoleDropdown.click();
		userRoleDropdown.sendKeys(Keys.ARROW_DOWN);
		userRoleDropdown.sendKeys(Keys.ARROW_DOWN);
		userRoleDropdown.sendKeys(Keys.ENTER);
	}
	
	public void selectStatus() {
		WebElement statusDropdown = driver.findElement(ddStatus);
		statusDropdown.click();
		statusDropdown.sendKeys(Keys.ARROW_DOWN);
		statusDropdown.sendKeys(Keys.ENTER);
	}
}
