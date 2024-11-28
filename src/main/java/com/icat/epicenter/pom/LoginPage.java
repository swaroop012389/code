/** Program Description: Object Locators and methods defined in Login page
 *  Author			   : SMNetserv
 *  Date of Creation   : 27/10/2017
**/

package com.icat.epicenter.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;

import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.CheckBoxControl;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextFieldControl;
import com.NetServAutomationFramework.generic.WebDriverManager;

public class LoginPage extends BasePageControl {
	public TextFieldControl userName;
	public TextFieldControl password;
	public CheckBoxControl rememberMe;
	public ButtonControl signInButton;
	public ButtonControl closeCookieMonster;
	public BaseWebElementControl pageName;
	public BaseWebElementControl phoneNo;
	public BaseWebElementControl icatLogo;
	public TextFieldControl binUsername;
	public TextFieldControl binPassword;
	public ButtonControl binSignInButton;

	public LoginPage() {
		PageObject pageobject = new PageObject("Login");
		userName = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Username")));
		password = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Password")));
		rememberMe = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_RememberMe")));
		signInButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_SignIn")));
		closeCookieMonster = new ButtonControl(By.xpath(pageobject.getXpath("xp_CloseCookieMonster")));
		pageName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PageName")));
		phoneNo = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PhoneNos")));
		icatLogo = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_IcatLogo")));
		binUsername = new TextFieldControl(By.xpath(pageobject.getXpath("xp_BinUserName")));
		binPassword = new TextFieldControl(By.xpath(pageobject.getXpath("xp_BinPassword")));
		binSignInButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_BinSignIn")));
	}

	public HomePage enterLoginDetails(String username, String Password) {
		PageFactory.initElements(WebDriverManager.getCurrentDriver(), this);
		refreshPage();
		checkForCookieMessage();
		waitTime(3);
		signInButton.waitTillPresenceOfElement(60);
		signInButton.waitTillVisibilityOfElement(60);
		refreshPage();
		userName.setData(username);
		password.setData(Password);
		signInButton.checkIfElementIsPresent();
		signInButton.checkIfElementIsDisplayed();
		signInButton.scrollToElement();
		signInButton.click();
		signInButton.waitTillInVisibilityOfElement(80);
		if (pageName.getData().contains("Welcome")) {
			return new HomePage();
		}
		return null;
	}

	/**
	 * In case the cookie banner is in the way
	 */
	private void checkForCookieMessage() {
		try {
			if (closeCookieMonster.checkIfElementIsPresent()) {
				closeCookieMonster.click();
			}
		} catch (NoSuchElementException ex) {
			// this will happen most times so ignore
		}
	}
}
