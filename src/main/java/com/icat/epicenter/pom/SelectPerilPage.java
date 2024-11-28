/** Program Description:Object Locators and methods defined in Select peril page
 *  Author			   : SMNetserv
 *  Date of Creation   : 28/10/2017
**/

package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.RadioButtonControl;

public class SelectPerilPage extends BasePageControl {
	public RadioButtonControl windOnly;
	public RadioButtonControl allOtherPeril;
	public RadioButtonControl gLandAllOtherPerils;
	public ButtonControl previousButton;
	public ButtonControl continueButton;
	public BaseWebElementControl pageName;

	public SelectPerilPage() {
		PageObject pageobject = new PageObject("SelectPeril");
		windOnly = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_WindOnly")));
		allOtherPeril = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_AllOtherPeril")));
		gLandAllOtherPerils = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_GLandAllOtherPerils")));
		previousButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_Previous")));
		continueButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_Continue")));
		pageName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PageName")));
	}

	public BasePageControl selectPeril(String peril) {
		if (peril.equalsIgnoreCase("Wind")) {
			windOnly.waitTillVisibilityOfElement(60);
			windOnly.click();
		} else if (peril.equalsIgnoreCase("AOP")) {
			allOtherPeril.waitTillVisibilityOfElement(60);
			allOtherPeril.click();
		} else {
			gLandAllOtherPerils.waitTillVisibilityOfElement(60);
			gLandAllOtherPerils.click();
		}
		Assertions.passTest("Select Peril Page", "Selected Peril is : " + peril);
		continueButton.click();
		if (pageName.getData().contains("Quote")) {
			return new CreateQuotePage();
		}
		if (pageName.getData().contains("Prior Loss")) {
			return new PriorLossesPage();
		}
		return null;
	}
}
