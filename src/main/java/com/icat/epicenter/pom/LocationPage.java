/** Program Description: Object Locators and methods defined in Location page
 *  Author			   : SMNetserv
 *  Date of Creation   : 07/11/2017
 **/

package com.icat.epicenter.pom;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.RadioButtonControl;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class LocationPage extends BasePageControl {
	public TextFieldControl locationName;
	public TextFieldControl locationAddress;
	public BaseWebElementControl locAddressControl;
	public RadioButtonControl additionalPropertyCoverages_Yes;
	public RadioButtonControl additionalPropertyCoverages_No;
	public TextFieldControl businessIncome;
	public ButtonControl addBuildingsButton;
	public HyperLink addSymbol;
	public HyperLink addNewLocation;
	public HyperLink addNewBuilding;
	public HyperLink delete;
	public BaseWebElementControl pageName;

	// Residential
	public HyperLink accountDetailsLink;
	public ButtonControl addressLink;
	public ButtonControl reviewLocation;
	public ButtonControl addDwellingButton;
	public HyperLink addNewDwelling;
	public HyperLink locationLink;
	public HyperLink locationListName;
	public HyperLink deleteLocation_Yes;
	public HyperLink deleteLocation_No;
	public BaseWebElementControl ineligibleWarning;
	public HyperLink editLocation;
	public HyperLink buildingLink;
	public ButtonControl createQuoteButton;
	public TextFieldControl apc_Value;
	public TextFieldControl apc_Description;
	public HyperLink copyDupBldgLink;
	public RadioButtonControl additionalPropertyCoverages_Yes_Checked;
	public HyperLink dwellingLink;
	public HyperLink coveragesStep2;
	public HyperLink dwelling1;

	public LocationPage() {
		PageObject pageobject = new PageObject("Location");
		locationName = new TextFieldControl(By.xpath(pageobject.getXpath("xp_LocationName")));
		locationAddress = new TextFieldControl(By.xpath(pageobject.getXpath("xp_LocationAddress")));
		locAddressControl = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LocationControl")));
		additionalPropertyCoverages_Yes = new RadioButtonControl(
				By.xpath(pageobject.getXpath("xp_AdditionalPropertyCoverages_Yes")));
		additionalPropertyCoverages_No = new RadioButtonControl(
				By.xpath(pageobject.getXpath("xp_AdditionalPropertyCoverages_No")));
		businessIncome = new TextFieldControl(By.xpath(pageobject.getXpath("xp_BusinessIncome")));
		addBuildingsButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_AddBuildings")));
		addSymbol = new HyperLink(By.xpath(pageobject.getXpath("xp_AddSymbol")));
		addNewLocation = new HyperLink(By.xpath(pageobject.getXpath("xp_AddNewLocation")));
		addNewBuilding = new HyperLink(By.xpath(pageobject.getXpath("xp_AddNewBuilding")));
		delete = new HyperLink(By.xpath(pageobject.getXpath("xp_Delete")));
		pageName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PageName")));

		accountDetailsLink = new HyperLink(By.xpath(pageobject.getXpath("xp_AccountDetails_Link")));
		addressLink = new ButtonControl(By.xpath(pageobject.getXpath("xp_AddressLink")));
		reviewLocation = new ButtonControl(By.xpath(pageobject.getXpath("xp_ReviewLocation")));
		addDwellingButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_AddDwelling")));
		addNewDwelling = new HyperLink(By.xpath(pageobject.getXpath("xp_AddNewDwelling")));
		locationLink = new HyperLink(By.xpath(pageobject.getXpath("xp_LocationLink")));
		locationListName = new HyperLink(By.xpath(pageobject.getXpath("xp_LocationListName")));
		deleteLocation_Yes = new HyperLink(By.xpath(pageobject.getXpath("xp_DeleteLocation_Yes")));
		deleteLocation_No = new HyperLink(By.xpath(pageobject.getXpath("xp_DeleteLocation_No")));
		ineligibleWarning = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_IneligibleWarning")));
		editLocation = new HyperLink(By.xpath(pageobject.getXpath("xp_EditLocation")));
		buildingLink = new HyperLink(By.xpath(pageobject.getXpath("xp_BuildingLink")));
		createQuoteButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_CreateQuoteButton")));
		apc_Value = new TextFieldControl(By.xpath(pageobject.getXpath("xp_APC_Value")));
		apc_Description = new TextFieldControl(By.xpath(pageobject.getXpath("xp_APC_Description")));
		copyDupBldgLink = new HyperLink(By.xpath(pageobject.getXpath("xp_CopyDupBldg")));
		additionalPropertyCoverages_Yes_Checked = new RadioButtonControl(
				By.xpath(pageobject.getXpath("xp_AdditionalPropertyCoverages_Yes_Checked")));
		dwellingLink = new HyperLink(By.xpath(pageobject.getXpath("xp_DwellingLink")));
		coveragesStep2 = new HyperLink(By.xpath(pageobject.getXpath("xp_coveragesStep2")));
		dwelling1 = new HyperLink(By.xpath(pageobject.getXpath("xp_Dwelling1")));
	}

	public void addLocationAddress(String Data) {
		if (locationAddress.checkIfElementIsPresent()) {
			locationAddress.setData(Data);
			locAddressControl.waitTillInVisibilityOfElement(60);
			addressLink.waitTillVisibilityOfElement(60);
			addressLink.click();
		}
	}

	public void addBusinessIncome(String Data) {
		businessIncome.setData(Data);
	}

	public void addAPC(Map<String, String> Data, int locNo) {
		if (Data.get("L" + locNo + "-APCSelection").equalsIgnoreCase("yes")) {
			additionalPropertyCoverages_Yes.scrollToElement();
			additionalPropertyCoverages_Yes.click();

			// wind/aop/gl/eq
			if (!Data.get("L" + locNo + "-APCAwnings").equals(""))
				apc_Value.formatDynamicPath("Awnings").setData(Data.get("L" + locNo + "-APCAwnings"));

			if (!Data.get("L" + locNo + "-APCBoardwalks").equals(""))
				apc_Value.formatDynamicPath("Catwalks").setData(Data.get("L" + locNo + "-APCBoardwalks"));

			if (!Data.get("L" + locNo + "-APCCarports").equals(""))
				apc_Value.formatDynamicPath("Carports").setData(Data.get("L" + locNo + "-APCCarports"));

			if (!Data.get("L" + locNo + "-APCFences").equals(""))
				apc_Value.formatDynamicPath("Fences").setData(Data.get("L" + locNo + "-APCFences"));

			if (!Data.get("L" + locNo + "-APCFountains").equals(""))
				apc_Value.formatDynamicPath("Fountains").setData(Data.get("L" + locNo + "-APCFountains"));

			if (!Data.get("L" + locNo + "-APCMachinery").equals(""))
				apc_Value.formatDynamicPath("Machinery").setData(Data.get("L" + locNo + "-APCMachinery"));

			if (!Data.get("L" + locNo + "-APCOtherStructures").equals(""))
				apc_Value.formatDynamicPath("Other Structures").setData(Data.get("L" + locNo + "-APCOtherStructures"));

			if (!Data.get("L" + locNo + "-APCDriveways").equals(""))
				apc_Value.formatDynamicPath("Driveways").setData(Data.get("L" + locNo + "-APCDriveways"));

			if (!Data.get("L" + locNo + "-APCPlayground").equals(""))
				apc_Value.formatDynamicPath("Playground").setData(Data.get("L" + locNo + "-APCPlayground"));

			if (!Data.get("L" + locNo + "-APCPools").equals(""))
				apc_Value.formatDynamicPath("Pools").setData(Data.get("L" + locNo + "-APCPools"));

			if (!Data.get("L" + locNo + "-APCLightPolesandUnattachedSigns").equals(""))
				apc_Value.formatDynamicPath("Light Poles")
						.setData(Data.get("L" + locNo + "-APCLightPolesandUnattachedSigns"));

			if (!Data.get("L" + locNo + "-APCUndergroundUtilities").equals(""))
				apc_Value.formatDynamicPath("Underground").setData(Data.get("L" + locNo + "-APCUndergroundUtilities"));

			if (Data.get("Peril").equals("EQ") || Data.get("Peril").equals("Quake")
					|| Data.get("Peril").equals("Earthquake")) {
				// eq
				if (!Data.get("L" + locNo + "-APCLandscaping").equals(""))
					apc_Value.formatDynamicPath("").setData(Data.get("L" + locNo + "-APCLandscaping"));

				if (!Data.get("L" + locNo + "-APCOtherStructures").equals(""))
					apc_Value.formatDynamicPath("Other Structures")
							.setData(Data.get("L" + locNo + "-APCOtherStructures"));

			} else {

				// wind/aop/gl
				if (!Data.get("L" + locNo + "-APCSatelliteDishes").equals(""))
					apc_Value.formatDynamicPath("Satellite Dishes")
							.setData(Data.get("L" + locNo + "-APCSatelliteDishes"));

				if (!Data.get("L" + locNo + "-APCOtherStructures").equals(""))
					apc_Value.formatDynamicPath("Fully Enclosed")
							.setData(Data.get("L" + locNo + "-APCOtherStructures"));

				if (!Data.get("L" + locNo + "-APCOtherStructuresOpenorNotFullyEnclosed").equals(""))
					apc_Value.formatDynamicPath("Not Fully Enclosed")
							.setData(Data.get("L" + locNo + "-APCOtherStructuresOpenorNotFullyEnclosed"));

			}

		} else {
			additionalPropertyCoverages_No.scrollToElement();
			additionalPropertyCoverages_No.click();
		}
	}

	public LocationPage enterLocationDetails(Map<String, String> Data) {
		String locationNumber = Data.get("LocCount");
		int locationCount = Integer.parseInt(locationNumber);
		for (int locNo = 1; locNo <= locationCount; locNo++) {
			locationLink.formatDynamicPath(locNo).waitTillButtonIsClickable(60);
			locationLink.formatDynamicPath(locNo).scrollToElement();
			locationLink.formatDynamicPath(locNo).click();
			if (Data.get("ProductSelection").contains("Commercial")) {
				addAPC(Data, locNo);
				addBusinessIncome(Data.get("L" + locNo + "-LocBI"));
			} else {
				addLocationAddress(Data.get("L" + locNo + "D1-DwellingAddress") + ", "
						+ Data.get("L" + locNo + "D1-DwellingCity") + ", " + Data.get("L" + locNo + "D1-DwellingZIP"));
			}

			/*
			 * if(addBuildingsButton.checkIfElementIsPresent() &&
			 * addBuildingsButton.checkIfElementIsDisplayed()){
			 * addBuildingsButton.scrollToElement(); addBuildingsButton.click(); }
			 */
			if (locationCount > locNo) {
				addSymbol.scrollToElement();
				addSymbol.click();
				addNewLocation.click();
			}

			if (!Data.get("L" + locNo + "-APCSelection").isEmpty()) {
				Assertions.passTest("Location Page", "Location L" + locNo + " details entered successfully");
			}
		}

		return this;
	}
	public LocationPage enterLocationDetailsHIHO(Map<String, String> Data) {
		String locationNumber = Data.get("LocCount");
		int locationCount = Integer.parseInt(locationNumber);
		for (int locNo = 1; locNo <= locationCount; locNo++) {
			locationLink.formatDynamicPath(locNo).waitTillButtonIsClickable(60);
			locationLink.formatDynamicPath(locNo).scrollToElement();
			locationLink.formatDynamicPath(locNo).click();
			if (Data.get("ProductSelection").equalsIgnoreCase("Commercial")) {
				addLocationAddress(Data.get("L" + locNo + "B1-BldgAddr1") + ", " + Data.get("L" + locNo + "B1-BldgCity")
						+ ", " + Data.get("L" + locNo + "B1-BldgZIP"));
				addAPC(Data, locNo);
				addBusinessIncome(Data.get("L" + locNo + "-LocBI"));
			} else {
				addLocationAddress(Data.get("L" + locNo + "D1-DwellingAddress") + ", "
						+ Data.get("L" + locNo + "D1-DwellingCity") + ", " + Data.get("L" + locNo + "D1-DwellingZIP"));
			}
			if (locationCount > locNo) {
				addSymbol.scrollToElement();
				addSymbol.click();
				addNewLocation.click();
			}
		}
		return this;
	}

	public LocationPage modifyLocationDetailsCommercial(Map<String, String> Data) {
		String locationNumber = Data.get("LocCount");
		int locationCount = Integer.parseInt(locationNumber);
		for (int locNo = 1; locNo <= locationCount; locNo++) {

			addAPC(Data, locNo);
			addBusinessIncomePNB(Data.get("L" + locNo + "-LocBI"));

			if (locationCount > locNo) {
				addSymbol.scrollToElement();
				addSymbol.click();
				addNewLocation.click();
			}
			if (addBuildingsButton.checkIfElementIsPresent() && addBuildingsButton.checkIfElementIsDisplayed()) {
				addBuildingsButton.scrollToElement();
				addBuildingsButton.click();
			}

			if (reviewLocation.checkIfElementIsPresent() && reviewLocation.checkIfElementIsDisplayed()) {
				reviewLocation.scrollToElement();
				reviewLocation.click();
			}
		}
		return this;
	}

	public void deleteLocation(Map<String, String> Data, int locNo) {
		Assertions.addInfo("Location " + locNo + " - Deleted", "");
		locationLink.formatDynamicPath(locNo).scrollToElement();
		locationLink.formatDynamicPath(locNo).click();
		delete.scrollToElement();
		delete.click();
		deleteLocation_Yes.waitTillVisibilityOfElement(60);
		deleteLocation_Yes.click();
	}

	public void addLocation(Map<String, String> Data) {
		Assertions.addInfo("Location " + Data.get("AddLocation") + " - Added", "");
		List<String> locationCount = Arrays.asList(Data.get("AddLocation").split(","));
		for (int i = 1; i <= locationCount.size(); i++) {
			addSymbol.scrollToElement();
			addSymbol.click();
			addNewLocation.click();
		}
	}

	public void modifyLocationDetails(Map<String, String> Data, int locNo) {
		Assertions.addInfo("Location " + locNo + " - Modified", "");
		if (locationLink.formatDynamicPath(locNo).checkIfElementIsPresent() && locationLink.formatDynamicPath(locNo).checkIfElementIsEnabled()) {
			locationLink.formatDynamicPath(locNo).scrollToElement();
			locationLink.formatDynamicPath(locNo).click();
		} else if (locationListName.formatDynamicPath(Data.get("L" + locNo + "-LocName")).checkIfElementIsPresent() && locationListName.formatDynamicPath(Data.get("L" + locNo + "-LocName")).checkIfElementIsEnabled()) {
			locationListName.formatDynamicPath(Data.get("L" + locNo + "-LocName")).click();
		}
		locationName.scrollToElement();
		Assertions.addInfo("Location Name original Value : " + locationName.getData(), "");
		locationName.setData(Data.get("L" + locNo + "-LocName"));
		Assertions.passTest("Change Named Insured Page", "Location Name Latest Value : " + locationName.getData());
		locationName.tab();

		addAPC(Data, locNo);
		addBusinessIncomePNB(Data.get("L" + locNo + "-LocBI"));
	}

	public void enterEndorsementLocationDetails(Map<String, String> Data) {
		for (int i = 1; i <= 4; i++) {
			System.out.println("enterEndorsementLocationDetails i = " + i);
			if (Data.get("L" + i + "-Delete").equalsIgnoreCase("yes")) {
				deleteLocation(Data, i);
			}
			if (Data.get("L" + i + "-Modify").equalsIgnoreCase("yes")) {
				modifyLocationDetails(Data, i);
			}
			if (Data.get("L" + i + "-Add").equalsIgnoreCase("yes")) {
				addSymbol.scrollToElement();
				addSymbol.click();
				addNewLocation.click();

				if (Data.get("L" + i + "-LocName") != null && !Data.get("L" + i + "-LocName").equals("")){
					System.out.println("enterEndorsementLocationDetails LocName = " + Data.get("L" + i + "-LocName") + " i = " + i);
					locationName.scrollToElement();
					Assertions.addInfo("Location Name original Value : " + locationName.getData(), "");
					locationName.setData(Data.get("L" + i + "-LocName"));
				}
				addAPC(Data, i);
				addBusinessIncomePNB(Data.get("L" + i + "-LocBI"));
			}
		}
	}

	public void enterEndorsementLocationDetailsNew(Map<String, String> Data) {
		for (int i = 1; i <= 4; i++) {
			if (Data.get("L" + i + "-Delete").equalsIgnoreCase("yes")) {
				deleteLocation(Data, i);
			}
			if (Data.get("L" + i + "-Modify").equalsIgnoreCase("yes")) {
				modifyLocationDetails(Data, i);
			}
			if (Data.get("L" + i + "-Add").equalsIgnoreCase("yes")) {
				addLocation(Data);
			}
		}
	}

	public void addBusinessIncomePNB(String Data) {
		Assertions.addInfo("Location Page", "Business Income original Value : " + "$" + businessIncome.getData());
		businessIncome.setData(Data);
		Assertions.passTest("Location Page", "Business Income Latest Value : " + "$" + businessIncome.getData());
	}
}
