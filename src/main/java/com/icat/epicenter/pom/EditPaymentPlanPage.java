/** Program Description: Object Locators and methods defined in Edit payment plan page
 *  Author			   : SMNetserv
 *  Date of Creation   : 05/11/2017
**/

package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.RadioButtonControl;

public class EditPaymentPlanPage {
	public RadioButtonControl singlePay;
	public RadioButtonControl fourPay;
	public RadioButtonControl mortgageePay;
	public ButtonControl cancel;
	public ButtonControl update;

	public EditPaymentPlanPage() {
		PageObject pageobject = new PageObject("EditPaymentPlan");
		singlePay = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_SinglePay")));
		fourPay = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_4Pay")));
		mortgageePay = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_MortgageePay")));
		cancel = new ButtonControl(By.xpath(pageobject.getXpath("xp_Cancel")));
		update = new ButtonControl(By.xpath(pageobject.getXpath("xp_Update")));
	}
}
