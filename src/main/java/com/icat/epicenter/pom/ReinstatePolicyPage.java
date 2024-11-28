/** Program Description: Object Locators and methods defined in Reinstate page
 *  Author			   : SMNetserv
 *  Date of Creation   : 05/11/2017
**/

package com.icat.epicenter.pom;

import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.CheckBoxControl;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextAreaControl;

public class ReinstatePolicyPage {
	public TextAreaControl reinstateComments;
	public CheckBoxControl additionalInformationCheckbox;
	public ButtonControl completeReinstatement;
	public ButtonControl cancelButton;
	public ButtonControl closeButton;

	public BaseWebElementControl lateTermReinstatement;
	public ButtonControl lateTermReinstateOk;
	public BaseWebElementControl reinstateSuccess;
	public ButtonControl okButton;

	public ReinstatePolicyPage() {
		PageObject pageobject = new PageObject("ReinstatePolicy");
		reinstateComments = new TextAreaControl(By.xpath(pageobject.getXpath("xp_Comments")));
		additionalInformationCheckbox = new CheckBoxControl(
				By.xpath(pageobject.getXpath("xp_AdditionalInformationCheckbox")));
		completeReinstatement = new ButtonControl(By.xpath(pageobject.getXpath("xp_CompleteReinstatement")));
		cancelButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_CancelButton")));
		closeButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_Close")));
		lateTermReinstatement = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LateTermReinstatement")));
		lateTermReinstateOk = new ButtonControl(By.xpath(pageobject.getXpath("xp_LateTermReinstateOk")));
		reinstateSuccess = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ReinstateSuccess")));
		okButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_OkButton")));
	}

	public PolicySummaryPage enterReinstatePolicyDetails(Map<String, String> Data) {
		if (lateTermReinstatement.checkIfElementIsPresent()) {
			lateTermReinstateOk.click();
			lateTermReinstateOk.waitTillInVisibilityOfElement(10);
		}

		if (!Data.get("ReInstatementComment").equals("")) {
			reinstateComments.scrollToElement();
			reinstateComments.setData(Data.get("ReInstatementComment"));
		}

		completeReinstatement.scrollToElement();
		completeReinstatement.click();
		completeReinstatement.waitTillInVisibilityOfElement(10);

		closeButton.scrollToElement();
		closeButton.click();
		return new PolicySummaryPage();
	}
}
