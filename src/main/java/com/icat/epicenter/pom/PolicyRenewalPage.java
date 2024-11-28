/** Program Description: Object Locators and methods defined in Policy renew page
 *  Author			   : SMNetserv
 *  Date of Creation   : 12/11/2017
**/

package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.PageObject;

public class PolicyRenewalPage {
	public ButtonControl yesButton;
	public ButtonControl noButton;

	public ButtonControl windRenewalBtn;
	public ButtonControl aopRenewalBtn;
	public ButtonControl glRenewalBtn;

	public ButtonControl cancelRenewal;
	public ButtonControl reviewRenewal;
	public ButtonControl continueRenewal;

	public ButtonControl renewalReviewAccInfo;
	public ButtonControl renewalYes;

	public ButtonControl valuationActionArrow;
	public ButtonControl valuationActionOptionApply;
	public ButtonControl valuationActionOptionLeave;
	public BaseWebElementControl valuationActionList;
	public ButtonControl buildingValueOk;
	public BaseWebElementControl coverageWarningMessage;
	public ButtonControl addExpaccButton;

	public ButtonControl valuationActionOption;
	public BaseWebElementControl valuationActionData;

	public BaseWebElementControl rnlWarningMSG;
	public ButtonControl rnlOkButton;
	public BaseWebElementControl rnlExistingWarningMessage;

	public BaseWebElementControl rnlCancelWarningMessage;
	public ButtonControl deleteAndContinue;
	public ButtonControl lapseAndContinue;

	public PolicyRenewalPage() {
		PageObject pageobject = new PageObject("PolicyRenewal");
		yesButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_YesButton")));
		noButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_NoButton")));

		windRenewalBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_WindRenewalButton")));
		aopRenewalBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_AOPRenewalButton")));
		glRenewalBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_GLRenewalButton")));

		cancelRenewal = new ButtonControl(By.xpath(pageobject.getXpath("xp_RenewalCancel")));
		reviewRenewal = new ButtonControl(By.xpath(pageobject.getXpath("xp_RenewalReview")));
		continueRenewal = new ButtonControl(By.xpath(pageobject.getXpath("xp_RenewalContinue")));

		renewalReviewAccInfo = new ButtonControl(By.xpath(pageobject.getXpath("xp_RenewalReviewAccInfo")));
		renewalYes = new ButtonControl(By.xpath(pageobject.getXpath("xp_RenewalYes")));

		valuationActionArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_ValuationActionArrow")));
		valuationActionOptionApply = new ButtonControl(By.xpath(pageobject.getXpath("xp_ValuationActionOptionApply")));
		valuationActionOptionLeave = new ButtonControl(By.xpath(pageobject.getXpath("xp_ValuationActionOptionLeave")));
		valuationActionList = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ValuationActionList")));

		buildingValueOk = new ButtonControl(By.xpath(pageobject.getXpath("xp_BuildingValueOk")));

		coverageWarningMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CoverageWarningMessage")));
		addExpaccButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_AddExpaccButton")));

		valuationActionOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_valuationActionOption")));
		valuationActionData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_valuationActionData")));

		rnlWarningMSG = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RNLWarningMSG")));
		rnlOkButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_RNLOkButton")));
		rnlExistingWarningMessage = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_RNLExistingWarningMessage")));
		rnlCancelWarningMessage = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_RNLCancelWarningMessage")));
		deleteAndContinue = new ButtonControl(By.xpath(pageobject.getXpath("xp_DeleteAndContinue")));
		lapseAndContinue = new ButtonControl(By.xpath(pageobject.getXpath("xp_LapseAndContinue")));
	}
}
