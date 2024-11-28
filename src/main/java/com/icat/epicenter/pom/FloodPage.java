/** Program Description: Object Locators and methods defined in Floodpage
 *  Author			   : SMNetserv
 *  Date of Creation   : 10/11/2017
**/

package com.icat.epicenter.pom;

import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.RadioButtonControl;
import com.NetServAutomationFramework.generic.TextAreaControl;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class FloodPage extends BasePageControl {
	public HyperLink floodLink;
	public HyperLink editFlood;
	public TextFieldControl latitude;
	public TextFieldControl longitude;
	public RadioButtonControl priorFloodloss_yes;
	public RadioButtonControl priorFloodloss_no;
	public TextFieldControl overrideDistanceCoast;
	public TextFieldControl overrideElevation;
	public RadioButtonControl floodZoneOverrideArrow;
	public RadioButtonControl floodZoneOverrideOption;
	public TextAreaControl overrideJustification;
	public RadioButtonControl elevationCertificate_yes;
	public RadioButtonControl elevationCertificate_no;
	public TextFieldControl baseFloodElevation;
	public TextFieldControl lowestFloorElevation;
	public RadioButtonControl basement_yes;
	public RadioButtonControl basement_no;
	public RadioButtonControl elevatedRiskCredit_yes;
	public RadioButtonControl elevatedRiskCredit_no;
	public TextFieldControl originalYearOfConstruction;
	public ButtonControl reviewButton;
	public BaseWebElementControl pageName;
	public BaseWebElementControl refreshing;
	public ButtonControl createQuote;
	public BaseWebElementControl constructionYearError;
	public TextFieldControl floodAddress1;
	public TextFieldControl floodCity;
	public TextFieldControl floodZip;
	public BaseWebElementControl distToCoast;
	public BaseWebElementControl elevation;
	public BaseWebElementControl floodZone;
	public BaseWebElementControl elevationDifference;
	DwellingPage dwellingPage;

	public FloodPage() {
		PageObject pageobject = new PageObject("Flood");
		floodLink = new HyperLink(By.xpath(pageobject.getXpath("xp_FloodLink")));
		editFlood = new HyperLink(By.xpath(pageobject.getXpath("xp_EditFloodSymbol")));
		latitude = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Latitude")));
		longitude = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Longitude")));
		priorFloodloss_yes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_PriorFloodLoss_Yes")));
		priorFloodloss_no = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_PriorFloodLoss_No")));
		overrideDistanceCoast = new TextFieldControl(By.xpath(pageobject.getXpath("xp_OverrideDistanceToCoast")));
		overrideElevation = new TextFieldControl(By.xpath(pageobject.getXpath("xp_OverrideElevation")));
		floodZoneOverrideArrow = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_OverrideFloodZoneArrow")));
		floodZoneOverrideOption = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_OverrideFloodZoneOption")));
		overrideJustification = new TextAreaControl(By.xpath(pageobject.getXpath("xp_OverrideJustification")));
		elevationCertificate_yes = new RadioButtonControl(
				By.xpath(pageobject.getXpath("xp_InsuredHvElevationCertificate_Yes")));
		elevationCertificate_no = new RadioButtonControl(
				By.xpath(pageobject.getXpath("xp_InsuredHvElevationCertificate_No")));
		baseFloodElevation = new TextFieldControl(By.xpath(pageobject.getXpath("xp_BaseFloodElevation")));
		lowestFloorElevation = new TextFieldControl(By.xpath(pageobject.getXpath("xp_LowestFloorElevation")));
		basement_yes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_IsThereBasement_Yes")));
		basement_no = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_IsThereBasement_No")));
		elevatedRiskCredit_yes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_ElevatedRiskCredit_Yes")));
		elevatedRiskCredit_no = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_ElevatedRiskCredit_No")));
		originalYearOfConstruction = new TextFieldControl(By.xpath(pageobject.getXpath("xp_OriginalYearConstruction")));
		reviewButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ReviewButton")));

		pageName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Pagename")));
		refreshing = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Refreshing")));
		createQuote = new ButtonControl(By.xpath(pageobject.getXpath("xp_CreateQuote")));
		constructionYearError = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ConstructionYearError")));
		floodAddress1 = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Floodaddress1")));
		floodCity = new TextFieldControl(By.xpath(pageobject.getXpath("xp_FloodCity")));
		floodZip = new TextFieldControl(By.xpath(pageobject.getXpath("xp_FloodZip")));
		distToCoast = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DistToCoast")));
		elevation = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Elevation")));
		floodZone = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_FloodZone")));
		elevationDifference = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ElevationDifference")));
	}

	public void enterFloodDetails(Map<String, String> Data) {
		if (editFlood.checkIfElementIsPresent()) {
			editFlood.scrollToElement();
			editFlood.click();
		}
		Assertions.passTest("Flood Page", "Flood page loaded successfully");
		if (!Data.get("L1D1-ElevationOverride").equalsIgnoreCase("")) {
			overrideElevation.waitTillVisibilityOfElement(60);
			overrideElevation.scrollToElement();
			overrideElevation.setData(Data.get("L1D1-ElevationOverride"));
			overrideElevation.tab();
			refreshing.waitTillInVisibilityOfElement(60);
		}
		if (Data.get("L1D1-PriorFloodLoss").equalsIgnoreCase("yes")) {
			priorFloodloss_yes.click();
		} else
			priorFloodloss_no.click();
		if (!Data.get("L1D1-DisttoCoastOverride").equalsIgnoreCase("")) {
			waitTime(3);
			overrideDistanceCoast.scrollToElement();
			overrideDistanceCoast.setData(Data.get("L1D1-DisttoCoastOverride"));
			overrideDistanceCoast.tab();
			refreshing.waitTillInVisibilityOfElement(60);
		}
		if (!Data.get("L1D1-FloodzoneOverride").equalsIgnoreCase("")) {
			floodZoneOverrideArrow.waitTillVisibilityOfElement(60);
			floodZoneOverrideArrow.scrollToElement();
			floodZoneOverrideArrow.click();
			floodZoneOverrideOption.formatDynamicPath(Data.get("L1D1-FloodzoneOverride"))
					.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
			floodZoneOverrideOption.formatDynamicPath(Data.get("L1D1-FloodzoneOverride")).scrollToElement();
			floodZoneOverrideOption.formatDynamicPath(Data.get("L1D1-FloodzoneOverride")).click();
			refreshing.waitTillInVisibilityOfElement(60);
			overrideJustification.waitTillVisibilityOfElement(60);
			overrideJustification.scrollToElement();
			overrideJustification.setData(Data.get("L1D1-OverrideJustification"));
		}
		if (elevationCertificate_yes.checkIfElementIsDisplayed()) {
			if (Data.get("L1D1-ElevationCertificate").equalsIgnoreCase("yes")) {
				elevationCertificate_yes.scrollToElement();
				elevationCertificate_yes.click();
				refreshing.waitTillInVisibilityOfElement(60);
				baseFloodElevation.waitTillVisibilityOfElement(60);
				baseFloodElevation.setData(Data.get("L1D1-BaseFloodElevation"));
				lowestFloorElevation.setData(Data.get("L1D1-LowestfloorElevation"));
			} else {
				elevationCertificate_no.scrollToElement();
				elevationCertificate_no.click();
				refreshing.waitTillInVisibilityOfElement(60);
			}
		}
		if (Data.get("L1D1-Basement").equalsIgnoreCase("yes")) {
			basement_yes.scrollToElement();
			basement_yes.click();
		} else {
			basement_no.scrollToElement();
			basement_no.click();
		}
		if (elevatedRiskCredit_yes.checkIfElementIsPresent() && elevatedRiskCredit_yes.checkIfElementIsDisplayed()) {
			if (Data.get("L1D1-ElevatedRiskCredit").equalsIgnoreCase("yes")) {
				elevatedRiskCredit_yes.scrollToElement();
				elevatedRiskCredit_yes.click();
			} else {
				elevatedRiskCredit_no.scrollToElement();
				elevatedRiskCredit_no.click();
			}
		}
		if (!Data.get("L1D1-YearofConstruction").equalsIgnoreCase("")) {
			originalYearOfConstruction.scrollToElement();
			originalYearOfConstruction.setData(Data.get("L1D1-YearofConstruction"));
		}
		Assertions.passTest("Flood Page", "Flood details entered successfully");
		reviewButton.scrollToElement();
		reviewButton.click();
	}
}
