/** Program Description: Object Locators and methods defined in endorse summary details page
 *  Author			   : Arun
 *  Date of Creation   : 12/11/2017
**/

package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.PageObject;

public class EndorseSummaryDetailsPage extends BasePageControl {
	public BaseWebElementControl policyLevelChangesTo1;
	public BaseWebElementControl policyLevelChangesTo2;
	public BaseWebElementControl policyLevelChangesFrom1;
	public BaseWebElementControl policyLevelChangesFrom2;

	public BaseWebElementControl locationLevelChangesTo;

	public BaseWebElementControl buildingLevelCoverageBTo;
	public BaseWebElementControl buildingLevelCoverageCTo;
	public BaseWebElementControl buildingLevelCoverageDTo;
	public BaseWebElementControl buildingLevelEQdeductibles;
	public BaseWebElementControl buildingLevelOrdinance;

	public BaseWebElementControl polLevelMailingAddr;
	public BaseWebElementControl polLevelInsuredEmail;
	public BaseWebElementControl polLevelPhoneNo;
	public BaseWebElementControl bldgLevelNoOfStories;
	public BaseWebElementControl bldgLevelSqFootage;

	public BaseWebElementControl polLevelEmail;
	public ButtonControl policyLevelChangesToCol;
	public BaseWebElementControl policyLevelChangesToColNAHO;
	public ButtonControl locationLevelChangesToCol;
	public ButtonControl buildingLevelChangesToCol;
	public ButtonControl locationLevelChangesToColNAHO;
	public ButtonControl buildingLevelChangesToColNAHO;

	public BaseWebElementControl transactionPremiumFee;
	public BaseWebElementControl inspectionFee;
	public BaseWebElementControl policyFee;
	public BaseWebElementControl otherFees;
	public BaseWebElementControl totalTerm;
	public BaseWebElementControl surplusContributionValue;
	public BaseWebElementControl dwellingAddress;
	public BaseWebElementControl coverageAndPremiumValues;
	public ButtonControl closeBtn;
	public BaseWebElementControl endorsementsValues;
	public BaseWebElementControl premiumDetails;

	public EndorseSummaryDetailsPage() {
		PageObject pageobject = new PageObject("EndorseSummaryDetails");
		policyLevelChangesTo1 = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolicyLevelChangesTo1")));
		policyLevelChangesTo2 = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolicyLevelChangesTo2")));
		policyLevelChangesFrom1 = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PolicyLevelChangesFrom1")));
		policyLevelChangesFrom2 = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PolicyLevelChangesFrom2")));

		locationLevelChangesTo = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LocationLevelChangesTo")));

		buildingLevelCoverageBTo = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BuildingLevelCoverageBTo")));
		buildingLevelCoverageCTo = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BuildingLevelCoverageCTo")));
		buildingLevelCoverageDTo = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BuildingLevelCoverageDTo")));
		buildingLevelEQdeductibles = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BuildingLevelEQdeductibles")));
		buildingLevelOrdinance = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BuildingLevelOrdinance")));

		polLevelMailingAddr = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolLevelMailingAddr")));
		polLevelInsuredEmail = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolLevelInsuredEmail")));
		polLevelPhoneNo = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolLevelPhoneNo")));
		bldgLevelNoOfStories = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BldgLevelNoOfStories")));
		bldgLevelSqFootage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BldgLevelSqFootage")));

		polLevelEmail = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolLevelEmail")));
		policyLevelChangesToCol = new ButtonControl(By.xpath(pageobject.getXpath("xp_PolicyLevelChangesToCol")));
		policyLevelChangesToColNAHO = new ButtonControl(By.xpath(pageobject.getXpath("xp_PolicyLevelChangesToColNAHO")));
		locationLevelChangesToCol = new ButtonControl(By.xpath(pageobject.getXpath("xp_LocationLevelChangesToCol")));
		buildingLevelChangesToCol = new ButtonControl(By.xpath(pageobject.getXpath("xp_BuildingLevelChangesToCol")));
		locationLevelChangesToColNAHO = new ButtonControl(By.xpath(pageobject.getXpath("xp_LocationLevelChangesToColNAHO")));
		buildingLevelChangesToColNAHO = new ButtonControl(By.xpath(pageobject.getXpath("xp_BuildingLevelChangesToColNAHO")));
		transactionPremiumFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TransactionPremiumFee")));
		inspectionFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_InspectionFee")));
		policyFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolicyFee")));
		otherFees = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OtherFees")));
		totalTerm = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TotalTerm")));
		surplusContributionValue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_SurplusContributionValue")));
		dwellingAddress = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DwellingAddress")));
		coverageAndPremiumValues = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CoverageAndPremiumValues")));
		closeBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_CloseBtn")));
		endorsementsValues = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EndorsementsValues")));
		premiumDetails = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PremiumDetails")));
	}
}
