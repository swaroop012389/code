/** Program Description: Object Locators and methods defined in Edit insured contact info page
 *  Author			   : SMNetserv
 *  Date of Creation   : 05/10/2017
**/

package com.icat.epicenter.pom;

import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class EditInsuredContactInfoPage {
	public TextFieldControl namedInsured;
	public TextFieldControl insuredEmail;
	public TextFieldControl insuredPhoneNoAreaCode;
	public TextFieldControl insuredPhoneNoPrefix;
	public TextFieldControl insuredPhoneNoEnd;
	public ButtonControl useExistingAddress;
	public TextFieldControl country;
	public ButtonControl countryLink;
	public TextFieldControl mailingAddress;
	public TextFieldControl addressLine2;
	public HyperLink enterAddressLink;
	public TextFieldControl manualAddressLine1;
	public TextFieldControl manualAddressLine2;
	public TextFieldControl city;
	public TextFieldControl state;
	public TextFieldControl zipCode;
	public HyperLink useAutocomplete;

	public ButtonControl cancel;
	public ButtonControl update;
	public ButtonControl existingAddressOption;
	public ButtonControl OkBtn;
	public ButtonControl dweSoldYesBtn;
	public ButtonControl dweSoldNoBtn;

	public EditInsuredContactInfoPage() {
		PageObject pageobject = new PageObject("EditInsuredContactInfo");
		namedInsured = new TextFieldControl(By.xpath(pageobject.getXpath("xp_NamedImsured")));
		insuredEmail = new TextFieldControl(By.xpath(pageobject.getXpath("xp_InsuredEmail")));
		insuredPhoneNoAreaCode = new TextFieldControl(By.xpath(pageobject.getXpath("xp_InsuredPhoneNoAreaCode")));
		insuredPhoneNoPrefix = new TextFieldControl(By.xpath(pageobject.getXpath("xp_InsuredPhoneNoPrefix")));
		insuredPhoneNoEnd = new TextFieldControl(By.xpath(pageobject.getXpath("xp_InsuredPhoneNoEnd")));
		useExistingAddress = new ButtonControl(By.xpath(pageobject.getXpath("xp_UseExistingAddressArrow")));
		country = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Country")));
		countryLink = new ButtonControl(By.xpath(pageobject.getXpath("xp_CountryLink")));
		mailingAddress = new TextFieldControl(By.xpath(pageobject.getXpath("xp_MailingAddress")));
		addressLine2 = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AddressLine2")));
		enterAddressLink = new HyperLink(By.xpath(pageobject.getXpath("xp_EnterAddressManually/InternationalAddress")));
		manualAddressLine1 = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AddressLine1")));
		manualAddressLine2 = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AddressLine2")));
		city = new TextFieldControl(By.xpath(pageobject.getXpath("xp_City")));
		state = new TextFieldControl(By.xpath(pageobject.getXpath("xp_State")));
		zipCode = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ZipCode")));
		useAutocomplete = new HyperLink(By.xpath(pageobject.getXpath("xp_AutoComplete")));

		cancel = new ButtonControl(By.xpath(pageobject.getXpath("xp_Cancel")));
		update = new ButtonControl(By.xpath(pageobject.getXpath("xp_Update")));
		existingAddressOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_ExistingAddressOption")));
		OkBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_OkBtn")));
		dweSoldYesBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_DweSoldYesBtn")));
		dweSoldNoBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_DweSoldNoBtn")));
	}

	public void enterContactInfoDetails(Map<String, String> Data) {
		namedInsured.waitTillVisibilityOfElement(60);
		namedInsured.scrollToElement();
		namedInsured.setData(Data.get("InsuredName"));
		insuredEmail.setData(Data.get("InsuredEmail"));
		insuredPhoneNoAreaCode.setData(Data.get("InsuredPhoneNumAreaCode"));
		insuredPhoneNoPrefix.setData(Data.get("InsuredPhoneNumPrefix"));
		insuredPhoneNoEnd.setData(Data.get("InsuredPhoneNum"));
		country.scrollToElement();
		country.clearData();
		country.setData(Data.get("InsuredCountry"));
		country.tab();
		if (enterAddressLink.checkIfElementIsDisplayed()) {
			enterAddressLink.scrollToElement();
			enterAddressLink.click();
		}
		manualAddressLine1.waitTillVisibilityOfElement(60);
		manualAddressLine1.setData(Data.get("InsuredAddr1"));
		if (addressLine2.checkIfElementIsDisplayed() && !Data.get("InsuredAddr1").equals("")) {
			addressLine2.scrollToElement();
			addressLine2.setData(Data.get("InsuredAddr1"));
		}
		if (city.checkIfElementIsDisplayed()) {
			city.scrollToElement();
			city.setData(Data.get("InsuredCity"));
		}
		if (state.checkIfElementIsPresent() && state.checkIfElementIsDisplayed()) {
			state.scrollToElement();
			state.setData(Data.get("InsuredState"));
			zipCode.setData(Data.get("InsuredZIP"));
		}
		update.scrollToElement();
		update.click();
		update.waitTillInVisibilityOfElement(60);
	}
}
