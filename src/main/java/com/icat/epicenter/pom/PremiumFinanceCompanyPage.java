package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class PremiumFinanceCompanyPage extends BasePageControl{
	public ButtonControl newButton;
	public TextFieldControl name;
	public TextFieldControl address;
	public TextFieldControl addressOrDesc;
	public TextFieldControl city;
	public TextFieldControl state;
	public TextFieldControl zipCode;
	public ButtonControl cancelButton;
	public ButtonControl saveButton;
	public ButtonControl assignButton;
	public ButtonControl edit;
	public ButtonControl unassign;
	public HyperLink pfcNameLink;
	public ButtonControl deleteButton;
	public ButtonControl closeButton;

	public PremiumFinanceCompanyPage() {
		PageObject pageobject = new PageObject("PremiumFinanceCompany");
		newButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_NewButton")));
		name = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Name")));
		address = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Address")));
		addressOrDesc = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AddressOrDesc")));
		city = new TextFieldControl(By.xpath(pageobject.getXpath("xp_City")));
		state = new TextFieldControl(By.xpath(pageobject.getXpath("xp_State")));
		zipCode = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ZipCode")));
		cancelButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_CancelButton")));
		saveButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_SaveButton")));
		assignButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_AssignButton")));
		edit = new ButtonControl(By.xpath(pageobject.getXpath("xp_Edit")));
		unassign = new ButtonControl(By.xpath(pageobject.getXpath("xp_Unassign")));
		pfcNameLink = new HyperLink(By.xpath(pageobject.getXpath("xp_PFCNameLink")));
		deleteButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_DeleteButton")));
		closeButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_CloseButton")));
	}
}
