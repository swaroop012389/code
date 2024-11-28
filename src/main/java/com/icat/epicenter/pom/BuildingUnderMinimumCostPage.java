/** Program Description: Object Locators and methods defined in Building under minimum cost page
 *  Author			   : SMNetserv
 *  Date of Creation   : 27/10/2017
**/

package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.PageObject;

public class BuildingUnderMinimumCostPage extends BasePageControl {
	public ButtonControl bringUpToCost;
	public ButtonControl override;
	public ButtonControl leaveIneligible;
	public BaseWebElementControl pageName;
	public BaseWebElementControl costcardMessage;
	public ButtonControl continueButton;
	public BaseWebElementControl minimumDwellingValueMsg;

	public BuildingUnderMinimumCostPage() {
		PageObject pageobject = new PageObject("BuildingUnderMinimumCost");
		bringUpToCost = new ButtonControl(By.xpath(pageobject.getXpath("xp_BringUpToCost")));
		override = new ButtonControl(By.xpath(pageobject.getXpath("xp_Override")));
		leaveIneligible = new ButtonControl(By.xpath(pageobject.getXpath("xp_LeaveIneligible")));
		pageName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PageName")));
		costcardMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CostcardMessage")));
		continueButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ContinueButton")));
		minimumDwellingValueMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_MinimumDwellingValueMsg")));
	}

	public void clickOnOverride() {
		if (override.checkIfElementIsPresent() && override.checkIfElementIsDisplayed()) {
			override.scrollToElement();
			override.click();
		}
	}
}
