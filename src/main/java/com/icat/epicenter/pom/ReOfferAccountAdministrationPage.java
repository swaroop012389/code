package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class ReOfferAccountAdministrationPage {

	public TextFieldControl accountNumber;
	public TextFieldControl newCreatedDate;
	public ButtonControl  commitButton;
	public TextFieldControl accountID;
	public ButtonControl  reOfferAccountButton;
	public BaseWebElementControl newAccountID;

	public ReOfferAccountAdministrationPage() {
		PageObject pageobject = new PageObject("ReOfferAccountAdministrationPage");
		accountNumber= new TextFieldControl(By.xpath(pageobject.getXpath("xp_accountNumber")));
		newCreatedDate = new TextFieldControl(By.xpath(pageobject.getXpath("xp_newCreatedDate")));
		commitButton =  new ButtonControl(By.xpath(pageobject.getXpath("xp_commitButton")));
		accountID  =  new TextFieldControl(By.xpath(pageobject.getXpath("xp_accountId")));
		reOfferAccountButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_reOfferAccountButton")));
		newAccountID = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_newAccountId")));
	}
}
