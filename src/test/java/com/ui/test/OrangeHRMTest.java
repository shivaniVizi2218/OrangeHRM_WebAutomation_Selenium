package com.ui.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ui.base.Base;
import com.ui.page.CreateUser;
import com.ui.page.LoginPage;

public class OrangeHRMTest extends Base {

	private LoginPage loginPage;
	private CreateUser createUserPage;

	@BeforeMethod
	public void testSetUp() throws InterruptedException {
		loginPage = new LoginPage(driver);
		createUserPage = new CreateUser(driver);
		logger.info("Launching base URL");
		driver.get(configProperties.getProperty("baseURL"));
		driver.manage().window().maximize();
		Thread.sleep(2000);
	}

	@Test 
	public void forgotPasswordTest() {
		logger.info("Forgot Password Test started");
		try {
			test = extent.createTest("TC_001,002 - Forgot Password Test").assignAuthor("Renuka").assignDevice("Windows");
			//
			test.log(Status.INFO, "Verifying whether 'Forgot Password?' is visible");
			boolean isTextDisplayed = loginPage.getTextForgotPswd().isDisplayed();
			Assert.assertTrue(isTextDisplayed, "Forgot Password Text should be visible");
			test.log(isTextDisplayed ? Status.PASS : Status.FAIL,
					isTextDisplayed ? "Forgot Password is visible" : "Forgot Password is not visible");

			test.log(Status.INFO, "Clicking Forgot Password?");
			loginPage.clickForgotPswd();
			//
			String actualURL = configProperties.getProperty("resetPswdURL");
			String currentURL = driver.getCurrentUrl();
			boolean isPageNavigated = currentURL.equals(actualURL);
			Assert.assertEquals(currentURL, actualURL, "Navigated to Password Reset Page");
			test.log(isPageNavigated ? Status.PASS : Status.FAIL,
					isPageNavigated ? "Navigated to Password Reset Page" : "Didn't Navigate to Password Reset Page");
			//
			test.log(Status.INFO, "Trying Reset Password");
			String name = jsonUserData.getAsJsonObject("login").get("username").getAsString();
			loginPage.resetPassword(name);
			//
			boolean isTitleDisplayed = loginPage.sentResetMsg().isDisplayed();
			Assert.assertTrue(isTitleDisplayed, "Sent Reset Password title is displayed");
			test.log(isTitleDisplayed ? Status.PASS : Status.FAIL,
					isTitleDisplayed ? "Reset password link is generated" : "Reset password link didn't generate");

		} catch (Exception e) {
			logger.info("Forgot Password Test failed" + e.getMessage());
		}
	}

	@Test 
	public void login() {
		logger.info("Login Test started");
		try {
			test = extent.createTest("TC_003 - Login Test").assignAuthor("Renuka").assignDevice("Windows");
			String username = jsonUserData.getAsJsonObject("login").get("username").getAsString();
			String password = jsonUserData.getAsJsonObject("login").get("password").getAsString();
			loginPage.loginApplication(username, password);
			test.log(Status.PASS, "Entered valid login Credentials");
			String currentUrl = driver.getCurrentUrl();
			boolean containsTitle = currentUrl.contains("dashboard");
			Assert.assertTrue(containsTitle, "URL doesn't contain Dashboard page");
			test.log(containsTitle ? Status.PASS : Status.FAIL,
					containsTitle ? "Navigated to Dashboard page" : "Didn't navigate to Dashboard");
		} catch (Exception e) {
			logger.info("Login Test failed" + e.getMessage());
		}
	}
	
	@Test
	public void createUser() {
		logger.info("Create User Test started");
		try {
			test = extent.createTest("TC_004 - Create User Test").assignAuthor("Renuka").assignDevice("Windows");
			String username = jsonUserData.getAsJsonObject("login").get("username").getAsString();
			String password = jsonUserData.getAsJsonObject("login").get("password").getAsString();
			loginPage.loginApplication(username, password);
			test.log(Status.PASS, "Logged into the application");
			createUserPage.addingUser();
			test.log(Status.PASS, "add user passed");
			createUserPage.selectUserRole();
			test.log(Status.PASS, "select user passed");
		} catch (Exception e) {
			logger.info("Create User Test failed" + e.getMessage());
		}
	}

}
