/** Program Description: Object Locators and methods defined in Existing account page
 *  Author			   : SMNetserv
 *  Date of Creation   : 09/11/2017
**/

package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;

public class ExistingAccountPage extends BasePageControl {
	public ButtonControl override;
	public ButtonControl leaveIneligible;
	public HyperLink pageName;

	public ExistingAccountPage() {
		PageObject pageobject = new PageObject("ExistingAccount");
		override = new ButtonControl(By.xpath(pageobject.getXpath("xp_Override")));
		leaveIneligible = new ButtonControl(By.xpath(pageobject.getXpath("xp_LeaveIneligible")));
		pageName = new HyperLink(By.xpath(pageobject.getXpath("xp_PageName")));
	}

	public void OverrideExistingAccount() {
		override.scrollToElement();
		override.click();
	}
}
