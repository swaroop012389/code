/** Program Description: Object Locators and methods defined in Cancel policy page
 *  Author			   : SMNetserv
 *  Date of Creation   : 30/10/2017
**/

package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.PageObject;

public class CancelPolicySuccessfulPage extends BasePageControl {

	public BaseWebElementControl newInspectionFee;
	public BaseWebElementControl newPolicyFee;
	public BaseWebElementControl newPremium;
	public BaseWebElementControl newSLTF;
	public BaseWebElementControl returnedPremium;
	public BaseWebElementControl returnedInspectionFee;
	public BaseWebElementControl returnedPolicyFee;
	public BaseWebElementControl returnedSLTF;

	public BaseWebElementControl returnedSurplusContributionValue;
	public BaseWebElementControl earnedSurplusContributionValue;

	public CancelPolicySuccessfulPage() {
		PageObject pageobject = new PageObject("CancelPolicySuccessfulPage");

		newInspectionFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NewInspectionFee")));
		newPolicyFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NewPolicyFee")));
		newPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NewPremium")));
		newSLTF = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NewSLTF")));

		returnedPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ReturnedPremium")));
		returnedInspectionFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ReturnedInspectionFee")));
		returnedPolicyFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ReturnedPolicyFee")));
		returnedSLTF = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ReturnedSLTF")));

		returnedSurplusContributionValue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_ReturnedSurplusContributionValue")));
		earnedSurplusContributionValue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_EarnedSurplusContributionValue")));
	}

}