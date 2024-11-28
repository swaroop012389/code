/** Program Description: Object Locators and methods defined in Eligilibility page
 *  Author			   : SMNetserv
 *  Date of Creation   : 06/11/2017
**/

package com.icat.epicenter.pom;

import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.RadioButtonControl;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class EligibilityPage extends BasePageControl {
	public TextFieldControl zipCode1;
	public TextFieldControl zipCode2;
	public ButtonControl addButton;
	public ButtonControl continueButton;
	public RadioButtonControl overrideEffectiveDateYes;
	public BaseWebElementControl pageName;

	public RadioButtonControl riskAppliedYes;
	public RadioButtonControl riskAppliedNo;

	public TextFieldControl zipcodeField;
	public BaseWebElementControl eligibleWarningMsg;
	public BaseWebElementControl underWritingQuestions;
	public BaseWebElementControl homeVacant1;
	public BaseWebElementControl homeVacant2;
	public BaseWebElementControl ineligibleRiskPopup;
	public BaseWebElementControl inEligibleRiskErrorMsg;
	public ButtonControl closeButton;
	public BaseWebElementControl riskQuestions;
	public BaseWebElementControl zipCodeClosedMsg;

	public EligibilityPage() {
		PageObject pageobject = new PageObject("Eligibility");
		zipCode1 = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ZipCode1")));
		zipCode2 = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ZipCode2")));
		addButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_AddZip")));
		continueButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_Continue")));
		overrideEffectiveDateYes = new RadioButtonControl(
				By.xpath((pageobject.getXpath("xp_OverrideEffectiveDateYes"))));
		pageName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PageName")));

		riskAppliedYes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_RiskAppliedYes")));
		riskAppliedNo = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_RiskAppliedNo")));
		zipcodeField = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ZipcodeField")));
		eligibleWarningMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EligibleWarningMsg")));
		underWritingQuestions = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_UnderWritingQuestions")));
		homeVacant1 = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_homeVacant1")));
		homeVacant2 = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_homeVacant2")));
		ineligibleRiskPopup = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_IneligibleRiskPopup")));
		inEligibleRiskErrorMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_IneligibleRiskErrorMsg")));
		closeButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_Close")));
		riskQuestions = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RiskQuestions")));
		zipCodeClosedMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ZipCodeclosedMsg")));
	}

	public LocationPage processSingleZip(Map<String, String> Data) {
		if (!Data.get("ZipCode").equals("")) {
			zipCode1.setData(Data.get("ZipCode"));
			zipCode1.tab();
			// wait in case override options are loaded
			waitTime(1);
			Assertions.passTest("Eligibility Page", "Zip code is " + zipCode1.getData());

			if (!Data.get("ProductSelection").contains("Commercial")) {
				if (riskAppliedYes.checkIfElementIsPresent() && riskAppliedYes.checkIfElementIsDisplayed()) {
					if (Data.get("RiskApplicable").equalsIgnoreCase("yes")) {
						riskAppliedYes.scrollToElement();
						riskAppliedYes.click();
					} else {
						riskAppliedNo.scrollToElement();
						riskAppliedNo.click();
					}
				}
			}

			if (overrideEffectiveDateYes.checkIfElementIsPresent()
					&& overrideEffectiveDateYes.checkIfElementIsDisplayed()) {
				overrideEffectiveDateYes.scrollToElement();
				overrideEffectiveDateYes.click();

			}
			continueButton.waitTillPresenceOfElement(60);
			continueButton.waitTillElementisEnabled(60);
			continueButton.click();
			continueButton.waitTillInVisibilityOfElement(60);
		} else
			Assertions.failTest("Eligibility", "Zip code is not present");
		if (pageName.getData().contains("Location")) {
			return new LocationPage();
		}
		return null;
	}
}
