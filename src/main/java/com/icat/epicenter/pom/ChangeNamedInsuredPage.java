/** Program Description: Object Locators and methods defined in Change named insured page
 *  Author			   : SMNetserv
 *  Date of Creation   : 31/10/2017
**/

package com.icat.epicenter.pom;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.DropDownControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextAreaControl;
import com.NetServAutomationFramework.generic.TextFieldControl;
import com.NetServAutomationFramework.generic.WebDriverManager;
import com.NetServAutomationFramework.util.FrameworkUtil;

public class ChangeNamedInsuredPage extends BasePageControl {
	public TextFieldControl namedInsured;
	public TextAreaControl extendedNamedInsured;
	public TextFieldControl insuredEmail;
	public TextFieldControl insuredPhoneNoAreaCode;
	public TextFieldControl insuredPhoneNoPrefix;
	public TextFieldControl insuredPhoneNoEnd;
	public TextFieldControl insuredCountry;
	public DropDownControl insuredCountrySelect;

	public TextFieldControl insuredMailingAddress;
	public TextFieldControl addressLine2;
	public TextFieldControl insuredCity;
	public TextFieldControl insuredState;
	public TextFieldControl zipCode;
	public HyperLink enterAddressManuallLink;
	public HyperLink useAutocomplteLink;
	public TextFieldControl addressLine1;
	public ButtonControl cancelButton;
	public ButtonControl okButton;
	public ButtonControl yes_NameChange;
	public ButtonControl no_NameChange;
	public ButtonControl okBtnNameChange_Yes;
	public BaseWebElementControl warningMsgNameChange_Yes;
	public ButtonControl okbtn;
	public BaseWebElementControl policyNotTransferable;
	public BaseWebElementControl policyNotTransferableMsg;

	public ChangeNamedInsuredPage() {
		PageObject pageobject = new PageObject("ChangeNamedInsured");
		namedInsured = new TextFieldControl(By.xpath(pageobject.getXpath("xp_NamedInsured")));
		extendedNamedInsured = new TextAreaControl(By.xpath(pageobject.getXpath("xp_ExtendednamedInsured")));
		insuredEmail = new TextFieldControl(By.xpath(pageobject.getXpath("xp_InsuredEmail")));
		insuredPhoneNoAreaCode = new TextFieldControl(By.xpath(pageobject.getXpath("xp_InsuredPhoneNoAreaCode")));
		insuredPhoneNoPrefix = new TextFieldControl(By.xpath(pageobject.getXpath("xp_InsuredPhoneNoPrefix")));
		insuredPhoneNoEnd = new TextFieldControl(By.xpath(pageobject.getXpath("xp_InsuredPhoneNoEnd")));
		insuredCountry = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Country")));
		insuredCountrySelect = new DropDownControl(By.xpath(pageobject.getXpath("xp_CountrySelect")));

		insuredMailingAddress = new TextFieldControl(By.xpath(pageobject.getXpath("xp_MailingAddress")));
		addressLine2 = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AddressLine2")));
		insuredCity = new TextFieldControl(By.xpath(pageobject.getXpath("xp_City")));
		insuredState = new TextFieldControl(By.xpath(pageobject.getXpath("xp_State")));
		zipCode = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ZipCode")));
		enterAddressManuallLink = new HyperLink(By.xpath(pageobject.getXpath("xp_InsuredManualEntryLink")));
		useAutocomplteLink = new HyperLink(By.xpath(pageobject.getXpath("xp_AutoCompleteLink")));
		addressLine1 = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AddressLine1")));
		cancelButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_Cancelbutton")));
		okButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_okButton")));
		yes_NameChange = new ButtonControl(By.xpath(pageobject.getXpath("xp_NameChange_Yes")));
		no_NameChange = new ButtonControl(By.xpath(pageobject.getXpath("xp_NameChange_No")));
		okBtnNameChange_Yes = new ButtonControl(By.xpath(pageobject.getXpath("xp_okBtnNameChange_Yes")));
		warningMsgNameChange_Yes = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_warningMsgNameChange_Yes")));
		okbtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_Okbtn")));
		policyNotTransferable = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolicyNotTransferable")));
		policyNotTransferableMsg = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PolicyNotTransferableMsg")));
	}

	public EndorsePolicyPage enterInsuredAddressDetailPB(Map<String, String> Data) {
		if (!Data.get("InsuredName").equalsIgnoreCase("")) {
			namedInsured.scrollToElement();
			Assertions.addInfo("Change Named Insured Page", "Named Insured original Value : " + namedInsured.getData());
			namedInsured.setData(Data.get("InsuredName"));
			Assertions.passTest("Change Named Insured Page", "Named Insured Latest Value : " + namedInsured.getData());
		}
		if (!Data.get("ExtendedNamedInsured").equals("")) {
			extendedNamedInsured.scrollToElement();
			Assertions.addInfo("Change Named Insured Page",
					"Extended Named Insured original Value : " + extendedNamedInsured.getData());
			extendedNamedInsured.setData(Data.get("ExtendedNamedInsured"));
			Assertions.passTest("Change Named Insured Page",
					"Extended Named Insured Latest Value : " + namedInsured.getData());
		}

		if (Data.get("InsuredEmail") != null) {
			if (!Data.get("InsuredEmail").equals("")) {
				insuredEmail.scrollToElement();
				Assertions.addInfo("Change Named Insured Page",
						"Insured Email original Value : " + insuredEmail.getData());
				insuredEmail.setData(Data.get("InsuredEmail"));
				Assertions.passTest("Change Named Insured Page",
						"Insured Email Latest Value : " + insuredEmail.getData());
			}
		}
		if (Data.get("InsuredPhoneNumAreaCode") != null) {
			if (!Data.get("InsuredPhoneNumAreaCode").equals("")) {
				insuredPhoneNoAreaCode.scrollToElement();
				String beforeAreaCode = insuredPhoneNoAreaCode.getData();
				insuredPhoneNoAreaCode.setData(Data.get("InsuredPhoneNumAreaCode"));
				String afterAreaCode = insuredPhoneNoAreaCode.getData();
				String beforePrefix = insuredPhoneNoPrefix.getData();
				insuredPhoneNoPrefix.setData(Data.get("InsuredPhoneNumPrefix"));
				String afterPrefix = insuredPhoneNoPrefix.getData();
				String beforePhonenoEnd = insuredPhoneNoEnd.getData();
				insuredPhoneNoEnd.setData(Data.get("InsuredPhoneNum"));
				String afterPhonenoEnd = insuredPhoneNoEnd.getData();
				Assertions.addInfo("Change Named Insured Page", "Insured phone Number original Value : "
						+ beforeAreaCode + "-" + beforePrefix + "-" + beforePhonenoEnd);
				Assertions.passTest("Change Named Insured Page", "Insured phone Number original Value : "
						+ afterAreaCode + "-" + afterPrefix + "-" + afterPhonenoEnd);
			}
		}
		if (!Data.get("InsuredCountry").equals("")) {
			if (insuredCountry.checkIfElementIsPresent()) {
				insuredCountry.scrollToElement();
				Assertions.addInfo("Change Named Insured Page",
						"Insured Country original Value : " + insuredCountry.getData());
				WebElement ele = WebDriverManager.getCurrentDriver()
						.findElement(By.xpath("//div[div[contains(text(),'Country')]]//input"));
				ele.sendKeys(Keys.chord(FrameworkUtil.KEY_CMD_CTRL, "a"), Data.get("InsuredCountry"));
				Assertions.passTest("Change Named Insured Page",
						"Insured Country Latest Value : " + insuredCountry.getData());
			} else if (insuredCountrySelect.checkIfElementIsPresent()) {
				insuredCountrySelect.scrollToElement();
				Assertions.addInfo("Change Named Insured Page",
						"Insured Country original Value : " + insuredCountrySelect.getData());
				insuredCountrySelect.selectByVisibleText(Data.get("InsuredCountry"));
				Assertions.passTest("Change Named Insured Page",
						"Insured Country Latest Value : " + insuredCountrySelect.getData());
			}
		}
		waitTime(2);
		if (enterAddressManuallLink.checkIfElementIsDisplayed()) {
			enterAddressManuallLink.waitTillPresenceOfElement(60);
			enterAddressManuallLink.waitTillVisibilityOfElement(60);
			enterAddressManuallLink.scrollToElement();
			enterAddressManuallLink.click();
		}
		waitTime(1);
		if (!Data.get("InsuredAddr1").equalsIgnoreCase("")) {
			addressLine1.waitTillVisibilityOfElement(60);
			addressLine1.scrollToElement();
			Assertions.addInfo("Change Named Insured Page",
					"Insured Address1 original Value : " + addressLine1.getData());
			addressLine1.setData(Data.get("InsuredAddr1"));
			Assertions.passTest("Change Named Insured Page",
					"Insured Address1 Latest Value : " + addressLine1.getData());
		}
		if (!Data.get("InsuredAddr2").equalsIgnoreCase("")) {
			addressLine2.waitTillVisibilityOfElement(60);
			addressLine2.scrollToElement();
			addressLine2.setData(Data.get("InsuredAddr2"));
		}
		if (insuredCity.checkIfElementIsDisplayed() && !Data.get("InsuredCity").equalsIgnoreCase("")) {
			insuredCity.scrollToElement();
			Assertions.addInfo("Change Named Insured Page", "Insured City original Value : " + insuredCity.getData());
			insuredCity.setData(Data.get("InsuredCity"));
			Assertions.passTest("Change Named Insured Page", "Insured City Latest Value : " + insuredCity.getData());
		}
		if (insuredState.checkIfElementIsPresent() && insuredState.checkIfElementIsDisplayed()
				&& !Data.get("InsuredState").equalsIgnoreCase("")) {
			insuredState.scrollToElement();
			Assertions.addInfo("Change Named Insured Page", "Insured State original Value : " + insuredState.getData());
			insuredState.setData(Data.get("InsuredState"));
			Assertions.passTest("Change Named Insured Page", "Insured State Latest Value : " + insuredState.getData());
		}

		if (zipCode.checkIfElementIsPresent() && zipCode.checkIfElementIsDisplayed()
				&& !Data.get("InsuredZIP").equalsIgnoreCase("")) {
			Assertions.addInfo("Change Named Insured Page", "Insured zipcode original Value : " + zipCode.getData());
			zipCode.setData(Data.get("InsuredZIP"));
			zipCode.tab();
			Assertions.passTest("Change Named Insured Page", "Insured zipcode Latest Value : " + zipCode.getData());
		}
		okButton.scrollToElement();
		okButton.click();
		if (no_NameChange.checkIfElementIsPresent() && no_NameChange.checkIfElementIsDisplayed()) {
			no_NameChange.waitTillVisibilityOfElement(60);
			no_NameChange.scrollToElement();
			no_NameChange.click();
			no_NameChange.waitTillInVisibilityOfElement(60);
		}
		return new EndorsePolicyPage();
	}
}
