/** Program Description: Object Locators and methods defined in Change payment plan page
 *  Author			   : SMNetserv
 *  Date of Creation   : 30/10/2017
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

public class ChangePaymentPlanPage extends BasePageControl {
	public RadioButtonControl insuredFullPay;
	public RadioButtonControl mortgageeFullPay;
	public RadioButtonControl insured4Pay;
	public RadioButtonControl insured10Pay;
	public RadioButtonControl insured3Pay;

	public RadioButtonControl renewalInsuredFullPay;
	public RadioButtonControl renewalMortgageeFullPay;
	public RadioButtonControl renewalInsured4Pay;
	public RadioButtonControl renewalInsured10Pay;
	public RadioButtonControl renewalInsured3Pay;

	public ButtonControl cancelButton;
	public ButtonControl okButton;
	public BaseWebElementControl paymentPlanWarningMsg;
	public BaseWebElementControl renewalPaymentPlanWarningmessage;

	public ChangePaymentPlanPage() {
		PageObject pageObject = new PageObject("ChangePaymentPlan");
		insuredFullPay = new RadioButtonControl(By.xpath(pageObject.getXpath("xp_InsuredFullPay")));
		mortgageeFullPay = new RadioButtonControl(By.xpath(pageObject.getXpath("xp_MortgageePay")));
		insured4Pay = new RadioButtonControl(By.xpath(pageObject.getXpath("xp_Insured4Pay")));
		insured10Pay = new RadioButtonControl(By.xpath(pageObject.getXpath("xp_Insured10Pay")));
		insured3Pay = new RadioButtonControl(By.xpath(pageObject.getXpath("xp_Insured3Pay")));

		renewalInsuredFullPay = new RadioButtonControl(By.xpath(pageObject.getXpath("xp_RenewalInsuredFullPay")));
		renewalMortgageeFullPay = new RadioButtonControl(By.xpath(pageObject.getXpath("xp_RenewalMortgageePay")));
		renewalInsured4Pay = new RadioButtonControl(By.xpath(pageObject.getXpath("xp_RenewalInsured4Pay")));
		renewalInsured10Pay = new RadioButtonControl(By.xpath(pageObject.getXpath("xp_RenewalInsured10Pay")));
		renewalInsured3Pay = new RadioButtonControl(By.xpath(pageObject.getXpath("xp_RenewalInsured3Pay")));

		cancelButton = new ButtonControl(By.xpath(pageObject.getXpath("xp_Cancelbutton")));
		okButton = new ButtonControl(By.xpath(pageObject.getXpath("xp_okButton")));
		paymentPlanWarningMsg  =  new BaseWebElementControl(By.xpath(pageObject.getXpath("xp_PaymentPlanWarningMsg")));
		renewalPaymentPlanWarningmessage  =  new BaseWebElementControl(By.xpath(pageObject.getXpath("xp_RenewalPaymentPlanWarningmessage")));
	}

	public EndorsePolicyPage enterChangePaymentPlanPB(Map<String, String> Data) {
		if (insuredFullPay.checkIfElementIsPresent() && insuredFullPay.checkIfElementIsSelected()) {
			Assertions.addInfo("Original Value : " + "Insured Full Pay ", "");
		} else if (mortgageeFullPay.checkIfElementIsPresent() && mortgageeFullPay.checkIfElementIsSelected()) {
			Assertions.addInfo("Original Value : " + "Mortgagee Pay ", "");
		} else {
			Assertions.addInfo("Original Value : " + "Four Pay ", "");
		}
		if (Data.get("SinglePay").equalsIgnoreCase("Yes")) {
			insuredFullPay.click();
			Assertions.passTest("Change payment plan Page", "Latest Value : " + "Insured Full Pay ");
		} else if (Data.get("MortgageePay").equalsIgnoreCase("Yes")) {
			mortgageeFullPay.click();
			Assertions.passTest("Change payment plan Page", "Latest Value : " + "Mortgagee Pay ");
		} else if (Data.get("4Pay").equalsIgnoreCase("Yes")) {
			insured4Pay.click();
			Assertions.passTest("Change payment plan Page", "Latest Value : " + "Four Pay ");
		}
		if (renewalInsuredFullPay.checkIfElementIsPresent() && renewalInsuredFullPay.checkIfElementIsSelected()) {
			Assertions.addInfo("Original Value : " + "Renewal Insured Full Pay ", "");
		} else if (renewalMortgageeFullPay.checkIfElementIsPresent()
				&& renewalMortgageeFullPay.checkIfElementIsSelected()) {
			Assertions.addInfo("Original Value : " + "Renewal Mortgagee Pay ", "");
		} else {
			Assertions.addInfo("Original Value : " + "Renewal Four Pay ", "");
		}
		if (Data.get("RNL_SinglePay").equalsIgnoreCase("Yes")) {
			renewalInsuredFullPay.click();
			Assertions.passTest("Change payment plan Page", "Latest Value : " + "Renewal Insured Full Pay ");
		} else if (Data.get("RNL_MortgageePay").equalsIgnoreCase("Yes")) {
			mortgageeFullPay.click();
			Assertions.passTest("Change payment plan Page", "Latest Value : " + "Renewal Mortgagee Pay ");
		} else if (Data.get("RNL_4Pay").equalsIgnoreCase("Yes")) {
			renewalInsured4Pay.click();
			Assertions.passTest("Change payment plan Page", "Latest Value : " + "Renewal Four Pay ");
		}
		okButton.scrollToElement();
		okButton.click();
		return new EndorsePolicyPage();
	}
}
