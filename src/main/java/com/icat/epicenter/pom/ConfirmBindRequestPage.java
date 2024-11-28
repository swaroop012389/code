/** Program Description: Object Locators and methods defined in Confirm bind request page
 *  Author			   : SMNetserv
 *  Date of Creation   : 30/10/2017
**/

package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.PageObject;

public class ConfirmBindRequestPage extends BasePageControl {
	public ButtonControl editInformation;
	public ButtonControl requestBind;
	public BaseWebElementControl pageName;
	public BaseWebElementControl earthQuake;
	public BaseWebElementControl quotePremium;
	public BaseWebElementControl grandTotal;
	public ButtonControl requestBindBtn;
	public BaseWebElementControl effectiveDate;

	public ConfirmBindRequestPage() {
		PageObject pageobject = new PageObject("ConfirmBindRequest");
		editInformation = new ButtonControl(By.xpath(pageobject.getXpath("xp_EditInformation")));
		requestBind = new ButtonControl(By.xpath(pageobject.getXpath("xp_RequestBind")));
		pageName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Pagename")));
		earthQuake = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EarthQuake")));
		quotePremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_QuotePremium")));
		grandTotal = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GrandTotal")));
		requestBindBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_RequestBindBtn")));
		effectiveDate = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EffectiveDate")));
	}

	public BasePageControl confirmBind() {
		requestBind.waitTillVisibilityOfElement(60);
		requestBind.scrollToElement();
		requestBind.click();
		requestBind.waitTillInVisibilityOfElement(60);
		if (pageName.getData().contains("Bind Request")) {
			return new BindRequestSubmittedPage();
		} else if (pageName.getData().contains("Policy Summary")) {
			return new PolicySummaryPage();
		}
		return null;
	}
}
