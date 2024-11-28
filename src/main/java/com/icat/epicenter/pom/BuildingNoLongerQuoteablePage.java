/** Program Description: Object Locators and methods defined in Building no longer quotable page
 *  Author			   : SMNetserv
 *  Date of Creation   : 28/10/2017
**/

package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;

public class BuildingNoLongerQuoteablePage extends BasePageControl {
	public HyperLink pageName;
	public ButtonControl override;
	public ButtonControl cancel;
	public ButtonControl quoteAsWind;
	public ButtonControl okButton;
	public ButtonControl Continue;
	public HyperLink modalBody;

	public BuildingNoLongerQuoteablePage() {
		PageObject pageobject = new PageObject("BuildingNoLongerQuoteable");
		pageName = new HyperLink(By.xpath(pageobject.getXpath("xp_PageName")));
		override = new ButtonControl(By.xpath(pageobject.getXpath("xp_Override")));
		cancel = new ButtonControl(By.xpath(pageobject.getXpath("xp_Cancel")));
		quoteAsWind = new ButtonControl(By.xpath(pageobject.getXpath("xp_QuoteAsWind")));
		okButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_OkButton")));

		Continue = new ButtonControl(By.xpath(pageobject.getXpath("xp_Continue")));
		modalBody = new HyperLink(By.xpath(pageobject.getXpath("xp_ModalBody")));

	}

	public void overrideNoLongerQuotableBuildings() {

		if (Continue.checkIfElementIsPresent() && Continue.checkIfElementIsDisplayed()) {
			Continue.scrollToElement();
			Continue.click();
		}

		if (override.checkIfElementIsPresent() && override.checkIfElementIsDisplayed()) {
			override.scrollToElement();
			override.click();
		}
	}
}
