/** Program Description: Object Locators and methods defined in Account Details page
 *  Author			   : SMNetserv
 *  Date of Creation   : 27/10/2017
**/

package com.icat.epicenter.pom;

import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.RadioButtonControl;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class AccountDetails {
	public RadioButtonControl retired_Yes;
	public RadioButtonControl retired_No;
	public HyperLink save;
	public ButtonControl reviewButton;
	public HyperLink accounDetailslink;
	public TextFieldControl effectiveDate;
	public BaseWebElementControl errorMsg;
	public ButtonControl reloadAccount;
	public ButtonControl createQuoteBtn;
	public ButtonControl wanttoContinue;

	public AccountDetails() {
		PageObject pageobject = new PageObject("AccountDetails");
		retired_Yes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_55RetiredYes")));
		retired_No = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_55RetiredNo")));
		save = new HyperLink(By.xpath(pageobject.getXpath("xp_saveButton")));
		reviewButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_reviewButton")));
		accounDetailslink = new HyperLink(By.xpath(pageobject.getXpath("xp_AccountDetailsLink")));
		effectiveDate = new TextFieldControl(By.xpath(pageobject.getXpath("xp_EffectiveDate")));
		errorMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ErrorMsg")));
		reloadAccount = new ButtonControl(By.xpath(pageobject.getXpath("xp_ReloadAccount")));
		createQuoteBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_CreateQuoteBtn")));
		wanttoContinue = new ButtonControl(By.xpath(pageobject.getXpath("xp_WanttoContinue")));
	}

	public void enterDiscountDetails(Map<String, String> Data) {
		accounDetailslink.scrollToElement();
		accounDetailslink.click();
		if (Data.get("Discount55Years").equals("Yes")) {
			if (retired_Yes.checkIfElementIsSelected()) {
				Assertions.addInfo("55 and retired Discount original Value : " + "Yes", "");
			}
			retired_Yes.scrollToElement();
			retired_Yes.click();
			Assertions.passTest("Dwelling Page", "55 and retired Discount Latest Value : " + "Yes");
		} else {
			if (retired_No.checkIfElementIsSelected()) {
				Assertions.addInfo("55 and retired Discount original Value : " + "No", "");
			}
			retired_No.scrollToElement();
			retired_No.click();
			Assertions.passTest("Dwelling Page", "55 and retired Discount Latest Value : " + "No");
		}
	}
}
