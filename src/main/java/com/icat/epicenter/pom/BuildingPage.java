/** Program Description: Object Locators and methods defined in Building page
 *  Author			   : SMNetserv
 *  Date of Creation   : 28/10/2017
 **/

package com.icat.epicenter.pom;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.CheckBoxControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.RadioButtonControl;
import com.NetServAutomationFramework.generic.TextFieldControl;
import com.NetServAutomationFramework.generic.WebDriverManager;
import com.NetServAutomationFramework.util.FrameworkUtil;

public class BuildingPage extends BasePageControl {
	public TextFieldControl buildingName;
	public HyperLink buildingDetailsLink;
	public HyperLink buildingOccupancyLink;
	public HyperLink roofDetailsLink;
	public HyperLink additionalInfoLink;
	public HyperLink buildingValuesLink;
	public HyperLink gLInformationlink;

	public ButtonControl reviewBuilding;
	public HyperLink addSymbol;
	public HyperLink addNewLocation;
	public HyperLink addNewBuilding;
	public HyperLink copySymbol;
	public HyperLink deleteSymbol;
	public HyperLink deleteBuildingTop;
	public BaseWebElementControl deleteBuildingPopupTop;
	public HyperLink editBuilding;
	public ButtonControl addBuilding;
	public ButtonControl addBuilding1;
	public BaseWebElementControl copyBuilding;
	public HyperLink deleteBuilding;
	public ButtonControl createQuote;
	public ButtonControl leaveIncomplete;
	public ButtonControl reSubmit;
	public ButtonControl override;
	public ButtonControl leaveIneligible;
	public BaseWebElementControl closePage;

	public HyperLink deleteYes;
	public HyperLink deleteNo;

	public HyperLink locationLink;
	public HyperLink locationListName;
	public TextFieldControl address;
	public TextFieldControl address2;
	public HyperLink manualEntry;
	public TextFieldControl manualEntryAddress;
	public TextFieldControl manualEntryCity;
	public TextFieldControl manualEntryState;
	public TextFieldControl manualEntryZipCode;
	public HyperLink useAutocomplete;
	public HyperLink geocodeOverride;
	public TextFieldControl lattitude;
	public TextFieldControl longitude;

	public ButtonControl constructionTypeArrow;
	public ButtonControl constructionTypeOption;
	public BaseWebElementControl constructionTypeData;

	public ButtonControl exteriorCladdingArrow;
	public ButtonControl exteriorCladdingOption;
	public BaseWebElementControl exteriorCladdingData;

	public TextFieldControl numOfStories;
	public TextFieldControl yearBuilt;
	public TextFieldControl totalSquareFootage;
	public RadioButtonControl softStoryCharacteristics_No;
	public RadioButtonControl softStoryCharacteristics_Yes;
	public CheckBoxControl eligibilityCondition;
	public RadioButtonControl buildingShapeIrregular;
	public RadioButtonControl buildingShapeRegular;
	public RadioButtonControl setbacksOverhangs_No;
	public RadioButtonControl setbacksOverhangs_Yes;

	public ButtonControl parkingTypeArrow;
	public ButtonControl parkingTypeOption;
	public BaseWebElementControl parkingTypeData;

	public TextFieldControl primaryPercentOccupied;
	public TextFieldControl primaryOccupancy;
	public CheckBoxControl primaryOccupancyCondition;
	public ButtonControl primaryOccupancyLink;
	public ButtonControl primaryOccupancyLink1;
	public TextFieldControl primaryOccupancyText;

	public TextFieldControl secondaryOccupancy;
	public CheckBoxControl secondaryOccupancyCondition;
	public ButtonControl secondaryOccupancyLink;
	public TextFieldControl secondaryPercentOccupied;
	public TextFieldControl secondaryOccupancyText;

	public RadioButtonControl owner;
	public RadioButtonControl tenant;
	public RadioButtonControl vacant;

	public ButtonControl roofShapeArrow;
	public ButtonControl roofShapeOption;
	public BaseWebElementControl roofShapeData;

	public ButtonControl roofCladdingArrow;
	public ButtonControl roofCladdingOption;
	public BaseWebElementControl roofCladdingData;

	// jumanji release IO-20672 removes age dropdown and adds a "year roof last
	// replaced" field
	public TextFieldControl yearRoofLastReplaced;
	public BaseWebElementControl roofAgeNotes;
	public ButtonControl roofAgeArrow;
	public ButtonControl roofAgeOption;
	public BaseWebElementControl roofAgeData;
	public BaseWebElementControl oldRoofAgeData;

	public ButtonControl buildingSecurityArrow;
	public ButtonControl buildingSecurityOption;
	public BaseWebElementControl buildingSecurityData;

	public ButtonControl fireProtectionArrow;
	public ButtonControl fireProtectionoption;
	public BaseWebElementControl fireProtectionData;
	public TextFieldControl protectionClass;
	public RadioButtonControl windResistive_No;
	public RadioButtonControl windResistive_Yes;
	public RadioButtonControl aluminumWiring_No;
	public RadioButtonControl aluminumWiring_Yes;
	public TextFieldControl buildingValue;
	public TextFieldControl businessPersonalProperty;

	public BaseWebElementControl pageName;
	public CheckBoxControl ineligibleDwelling;
	public HyperLink buildingLink;
	public ButtonControl continueButton;

	public BaseWebElementControl protectionClassErrMsg;
	public BaseWebElementControl occupancyTypeErrorMessage;
	public BaseWebElementControl yearOfConstructionErrorMessage;
	public BaseWebElementControl roofAgeErrorMessage;
	public BaseWebElementControl eifsErrorMessage;
	public BaseWebElementControl aluminiumWiringErrorMessage;

	ExistingAccountPage existingAccountPage;

	public BaseWebElementControl expiredQuotePopUp;
	public ButtonControl continueWithUpdateBtn;
	public BaseWebElementControl globalError;
	public ButtonControl continueOverrideCost;

	public BaseWebElementControl errorMsgBuildingStories;
	public BaseWebElementControl roofAgeWarningMessage;
	public BaseWebElementControl yearBuiltErrormessage;
	public BaseWebElementControl wrongAddresserrorMessage;
	public BaseWebElementControl occupancyConditionText;

	public ButtonControl nextButton;
	public BaseWebElementControl addressMsg;
	public BaseWebElementControl deleteBldgPopup;
	public RadioButtonControl buildingOccupancy_yes;
	public RadioButtonControl buildingOccupancy_no;

	public HyperLink MoreThan31occupied;
	public BaseWebElementControl MoreThan31orLessThanoccupied;
	public ButtonControl CloseButton;

	public BaseWebElementControl buildingValues;
	public BaseWebElementControl buildingValuationRow;
	public BaseWebElementControl buildingPageLinksFlyerText;
	public HyperLink buildingPageLinks;
	public BaseWebElementControl exclamatorySymbol;

	public ButtonControl buildingCoverageOptions;
	public ButtonControl buildingCoverageArrow;
	public ButtonControl updateCoveragesButton;
	public BaseWebElementControl yearRoofLastReplacedLabel;
	public BaseWebElementControl yearRoofLastReplacedMessage;

	public BuildingPage() {
		PageObject pageobject = new PageObject("Building");
		buildingName = new TextFieldControl(By.xpath(pageobject.getXpath("xp_BuildingName")));
		buildingDetailsLink = new HyperLink(By.xpath(pageobject.getXpath("xp_BuildingDetailsLink")));
		buildingOccupancyLink = new HyperLink(By.xpath(pageobject.getXpath("xp_BuildingOccupancyLink")));
		roofDetailsLink = new HyperLink(By.xpath(pageobject.getXpath("xp_RoofDetailsLink")));
		additionalInfoLink = new HyperLink(By.xpath(pageobject.getXpath("xp_AdditionalBuildingInformationLink")));
		buildingValuesLink = new HyperLink(By.xpath(pageobject.getXpath("xp_BuildingValuesLink")));
		gLInformationlink = new HyperLink(By.xpath(pageobject.getXpath("xp_GLInformationLink")));

		reviewBuilding = new ButtonControl(By.xpath(pageobject.getXpath("xp_ReviewBuilding")));
		addSymbol = new HyperLink(By.xpath(pageobject.getXpath("xp_AddSymbol")));
		addNewLocation = new HyperLink(By.xpath(pageobject.getXpath("xp_AddNewLocation")));
		addNewBuilding = new HyperLink(By.xpath(pageobject.getXpath("xp_AddNewBuilding")));
		copySymbol = new HyperLink(By.xpath(pageobject.getXpath("xp_CopySymbol")));
		deleteSymbol = new HyperLink(By.xpath(pageobject.getXpath("xp_DeleteSymbol")));
		deleteBuildingTop = new HyperLink(By.xpath(pageobject.getXpath("xp_DeleteBuildingTop")));
		deleteBuildingPopupTop = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DeleteBldgPopupTop")));
		editBuilding = new HyperLink(By.xpath(pageobject.getXpath("xp_EditBuildingSymbol")));
		addBuilding = new ButtonControl(By.xpath(pageobject.getXpath("xp_AddBuildingSymbol")));
		copyBuilding = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CopyBuildingSymbol")));
		deleteBuilding = new HyperLink(By.xpath(pageobject.getXpath("xp_DeleteSymbol")));
		addBuilding1 = new ButtonControl(By.xpath(pageobject.getXpath("xp_AddBuilding1")));

		createQuote = new ButtonControl(By.xpath(pageobject.getXpath("xp_CreateQuote")));
		leaveIncomplete = new ButtonControl(By.xpath(pageobject.getXpath("xp_LeaveIncomplete")));
		reSubmit = new ButtonControl(By.xpath(pageobject.getXpath("xp_Resubmit")));
		override = new ButtonControl(By.xpath(pageobject.getXpath("xp_Override")));
		leaveIneligible = new ButtonControl(By.xpath(pageobject.getXpath("xp_LeaveIneligible")));
		closePage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CloseSymbol")));
		deleteYes = new HyperLink(By.xpath(pageobject.getXpath("xp_DeleteYes")));
		deleteNo = new HyperLink(By.xpath(pageobject.getXpath("xp_DeleteNo")));

		locationLink = new HyperLink(By.xpath(pageobject.getXpath("xp_LocationLink")));
		locationListName = new HyperLink(By.xpath(pageobject.getXpath("xp_LocationListName")));
		address = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Address")));
		address2 = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Address2")));
		manualEntry = new HyperLink(By.xpath(pageobject.getXpath("xp_ManualEntry")));
		manualEntryAddress = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ManualEntryAddress")));
		manualEntryCity = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ManualEntryCity")));
		manualEntryState = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ManualEntryState")));
		manualEntryZipCode = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ManualEntryZipcode")));
		useAutocomplete = new HyperLink(By.xpath(pageobject.getXpath("xp_Autocomplete")));
		geocodeOverride = new HyperLink(By.xpath(pageobject.getXpath("xp_GeocodeOverride")));
		lattitude = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Latitude")));
		longitude = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Longitude")));

		constructionTypeArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_constructionTypeArrow")));
		constructionTypeOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_constructionTypeOption")));
		constructionTypeData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ConstructionTypeData")));

		exteriorCladdingArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_ExteriorCladdingArrow")));
		exteriorCladdingOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_ExteriorCladdingOption")));
		exteriorCladdingData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ExteriorCladdingData")));

		numOfStories = new TextFieldControl(By.xpath(pageobject.getXpath("xp_NumberOfStories")));
		yearBuilt = new TextFieldControl(By.xpath(pageobject.getXpath("xp_YearBuilt")));
		totalSquareFootage = new TextFieldControl(By.xpath(pageobject.getXpath("xp_TotalSquareFootage")));
		softStoryCharacteristics_No = new RadioButtonControl(
				By.xpath(pageobject.getXpath("xp_SoftStoryCharacteristics_No")));
		softStoryCharacteristics_Yes = new RadioButtonControl(
				By.xpath(pageobject.getXpath("xp_SoftStoryCharacteristics_Yes")));
		eligibilityCondition = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_EligibilityConditionCheckbox")));
		buildingShapeIrregular = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_BuildingShape_Irregular")));
		buildingShapeRegular = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_BuildingShape_Regular")));
		setbacksOverhangs_No = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_Setbacks/Overhangs_No")));
		setbacksOverhangs_Yes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_Setbacks/Overhangs_Yes")));

		parkingTypeArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_ParkingType_Arrow")));
		parkingTypeOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_ParkingType_Option")));
		parkingTypeData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ParkingTypeData")));

		primaryPercentOccupied = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Percentoccupied")));
		primaryOccupancy = new TextFieldControl(By.xpath(pageobject.getXpath("xp_PrimaryOccupancy")));
		primaryOccupancyCondition = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_PrimaryOccupancyCheckbox")));
		primaryOccupancyLink = new ButtonControl(By.xpath(pageobject.getXpath("xp_PrimaryoccupancyLink")));
		primaryOccupancyLink1 = new ButtonControl(By.xpath(pageobject.getXpath("xp_PrimaryoccupancyLink1")));
		primaryOccupancyText = new TextFieldControl(By.xpath(pageobject.getXpath("xp_PrimaryOccupancyText")));

		secondaryOccupancy = new TextFieldControl(By.xpath(pageobject.getXpath("xp_SecondaryOccupancy")));
		secondaryOccupancyCondition = new CheckBoxControl(
				By.xpath(pageobject.getXpath("xp_SecondaryOccupancyConditionCheckbox")));
		secondaryOccupancyLink = new HyperLink(By.xpath(pageobject.getXpath("xp_SecondaryOccupancyLink")));
		secondaryPercentOccupied = new TextFieldControl(By.xpath(pageobject.getXpath("xp_SecondaryOccupancyPercent")));
		secondaryOccupancyText = new TextFieldControl(By.xpath(pageobject.getXpath("xp_SecondaryOccupancyText")));

		owner = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_OccupiedBy_Owner")));
		tenant = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_OccupiedBy_Tenant")));
		vacant = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_OccupiedBy_Vacant")));

		roofShapeArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_RoofShapeArrow")));
		roofShapeOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_RoofShapeOption")));
		roofShapeData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RoofShapeData")));

		roofCladdingArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_RoofCladdingArrow")));
		roofCladdingOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_RoofCladdingOption")));
		roofCladdingData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RoofCladdingData")));

		yearRoofLastReplaced = new TextFieldControl(By.xpath(pageobject.getXpath("xp_YearRoofLastReplaced")));
		roofAgeNotes = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RoofAgeNotes")));
		roofAgeArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_RoofAgeArrow")));
		roofAgeOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_RoofAgeOption")));
		roofAgeData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RoofAgeData")));
		oldRoofAgeData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OldRoofAgeData")));

		buildingSecurityArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_BuildingSecurityArrow")));
		buildingSecurityOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_BuildingSecurityOption")));
		buildingSecurityData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BuildingSecurityData")));

		fireProtectionArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_FireProtectionArrow")));
		fireProtectionoption = new ButtonControl(By.xpath(pageobject.getXpath("xp_FireProtectionOption")));
		fireProtectionData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_FireProtectionData")));

		protectionClass = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ProtectionClass")));
		windResistive_No = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_WindResistive_No")));
		windResistive_Yes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_WindResistive_Yes")));
		aluminumWiring_No = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_AluminiumWiring_No")));
		aluminumWiring_Yes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_AluminiumWiring_Yes")));
		buildingValue = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Building")));
		businessPersonalProperty = new TextFieldControl(By.xpath(pageobject.getXpath("xp_BusinessPersonalProperty")));

		pageName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Pagename")));
		ineligibleDwelling = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_IneligibleDwelling")));
		buildingLink = new HyperLink(By.xpath(pageobject.getXpath("xp_BuildingLink")));
		continueButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ContinueButton")));

		protectionClassErrMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ErrorMsgProtectionClass")));
		occupancyTypeErrorMessage = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_OccupancyTypeErrorMessage")));
		yearOfConstructionErrorMessage = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_YearOfConstructionErrorMessage")));
		roofAgeErrorMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RoofAgeErrorMessage")));
		eifsErrorMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EifsErrorMessage")));
		aluminiumWiringErrorMessage = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_AluminiumWiringErrorMessage")));

		expiredQuotePopUp = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ExpiredQuotePopUp")));
		continueWithUpdateBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_ContinueWithUpdate")));
		globalError = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GlobalErr")));
		continueOverrideCost = new ButtonControl(By.xpath(pageobject.getXpath("xp_ContinueCostOverride")));
		errorMsgBuildingStories = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_ErrorMsgBuildingStories")));
		roofAgeWarningMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RoofAgeWarningMessage")));
		yearBuiltErrormessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_YearBuiltErrormessage")));
		wrongAddresserrorMessage = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_WrongAddresserrorMessage")));
		occupancyConditionText = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OccupancyConditionText")));
		nextButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_NextButton")));

		addressMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AddressMsg")));
		deleteBldgPopup = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DeleteBldgPopup")));
		buildingOccupancy_yes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_BuildingOccupancy_yes")));
		buildingOccupancy_no = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_BuildingOccupancy_no")));

		MoreThan31occupied = new HyperLink(By.xpath(pageobject.getXpath("xp_MoreThan31occupied")));
		MoreThan31orLessThanoccupied = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_MoreThan31orLessThanoccupied")));
		CloseButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_CloseButton")));
		buildingValues = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BuildingValues")));
		buildingValuationRow = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BuildingValuationRow")));
		buildingPageLinksFlyerText = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BuildingPageLinksFlyerText")));
		buildingPageLinks = new HyperLink(By.xpath(pageobject.getXpath("xp_BuildingPageLinks")));
		yearRoofLastReplaced = new TextFieldControl(By.xpath(pageobject.getXpath("xp_YearRoofLastReplaced")));
		exclamatorySymbol = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ExclamatorySymbol")));
		buildingCoverageOptions = new ButtonControl(By.xpath(pageobject.getXpath("xp_BuildingCoverageOptions")));
		buildingCoverageArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_BuildingCoverageArrow")));
		updateCoveragesButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_UpdateCoveragesButton")));
		yearRoofLastReplacedLabel = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_YearRoofLastReplacedLabel")));
		yearRoofLastReplacedMessage = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_YearRoofLastReplacedMessage")));

	}

	public void addBuildingDetails(Map<String, String> Data, int locNo, int bldgNo) {
		manualEntry.click();
		manualEntryAddress.waitTillVisibilityOfElement(60);
		manualEntryAddress.scrollToElement();
		manualEntryAddress.clearData();
		waitTime(3); // need this wait time to click on continue with update pop up
		if (expiredQuotePopUp.checkIfElementIsPresent() && expiredQuotePopUp.checkIfElementIsDisplayed()) {
			expiredQuotePopUp.scrollToElement();
			continueWithUpdateBtn.waitTillVisibilityOfElement(60);
			continueWithUpdateBtn.scrollToElement();
			continueWithUpdateBtn.click();
			continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
		}
		manualEntryAddress.setData(Data.get("L" + locNo + "B" + bldgNo + "-BldgAddr1"));
		if (expiredQuotePopUp.checkIfElementIsPresent() && expiredQuotePopUp.checkIfElementIsDisplayed()) {
			expiredQuotePopUp.scrollToElement();
			continueWithUpdateBtn.waitTillVisibilityOfElement(60);
			continueWithUpdateBtn.scrollToElement();
			continueWithUpdateBtn.click();
			continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
		}
		manualEntryAddress.tab();
		if (expiredQuotePopUp.checkIfElementIsPresent() && expiredQuotePopUp.checkIfElementIsDisplayed()) {
			expiredQuotePopUp.scrollToElement();
			continueWithUpdateBtn.waitTillVisibilityOfElement(60);
			continueWithUpdateBtn.scrollToElement();
			continueWithUpdateBtn.click();
			continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
		}
		manualEntryCity.setData(Data.get("L" + locNo + "B" + bldgNo + "-BldgCity"));
		manualEntryZipCode.setData(Data.get("L" + locNo + "B" + bldgNo + "-BldgZIP"));
		/*
		 * geocodeOverride.scrollToElement(); geocodeOverride.click();
		 * lattitude.scrollToElement(); lattitude.setData(Data.get("OverrideLatitude"));
		 * lattitude.tab(); longitude.scrollToElement();
		 * longitude.setData(Data.get("OverrideLongitude")); longitude.tab();
		 */
		address2.setData(Data.get("L" + locNo + "B" + bldgNo + "-BldgAddr2"));

		constructionTypeArrow.waitTillVisibilityOfElement(60);
		constructionTypeArrow.scrollToElement();
		constructionTypeArrow.click();
		constructionTypeOption.formatDynamicPath(Data.get("L" + locNo + "B" + bldgNo + "-BldgConstType"))
				.scrollToElement();
		constructionTypeOption.formatDynamicPath(Data.get("L" + locNo + "B" + bldgNo + "-BldgConstType")).click();

		if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgCladding").equals("")) {
			exteriorCladdingArrow.scrollToElement();
			exteriorCladdingArrow.click();
			exteriorCladdingOption.formatDynamicPath(Data.get("L" + locNo + "B" + bldgNo + "-BldgCladding"))
					.scrollToElement();
			exteriorCladdingOption.formatDynamicPath(Data.get("L" + locNo + "B" + bldgNo + "-BldgCladding")).click();
		}

		numOfStories.setData(Data.get("L" + locNo + "B" + bldgNo + "-BldgStories"));
		yearBuilt.setData(Data.get("L" + locNo + "B" + bldgNo + "-BldgYearBuilt"));
		totalSquareFootage.setData(Data.get("L" + locNo + "B" + bldgNo + "-BldgSqFeet"));

		if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgShape").equals("")) {
			if (Data.get("L" + locNo + "B" + bldgNo + "-BldgShape").equalsIgnoreCase("regular")) {
				buildingShapeRegular.scrollToElement();
				buildingShapeRegular.click();
			} else {
				buildingShapeIrregular.scrollToElement();
				buildingShapeIrregular.click();
			}
		}
		if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgSetbacks").equals("")) {
			if (Data.get("L" + locNo + "B" + bldgNo + "-BldgSetbacks").equalsIgnoreCase("yes")) {
				setbacksOverhangs_Yes.scrollToElement();
				setbacksOverhangs_Yes.click();
			} else {
				setbacksOverhangs_No.scrollToElement();
				setbacksOverhangs_No.click();
			}
		}

		if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgParkingType").equals("")) {
			parkingTypeArrow.scrollToElement();
			parkingTypeArrow.click();
			parkingTypeOption.formatDynamicPath(Data.get("L" + locNo + "B" + bldgNo + "-BldgParkingType"))
					.scrollToElement();
			parkingTypeOption.formatDynamicPath(Data.get("L" + locNo + "B" + bldgNo + "-BldgParkingType")).click();
		}

		if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgSoftStory").equalsIgnoreCase("")) {
			if (Data.get("L" + locNo + "B" + bldgNo + "-BldgSoftStory").equalsIgnoreCase("yes")) {
				softStoryCharacteristics_Yes.scrollToElement();
				softStoryCharacteristics_Yes.click();
				eligibilityCondition.waitTillElementisEnabled(60);
				eligibilityCondition.select();
			} else {
				softStoryCharacteristics_No.scrollToElement();
				softStoryCharacteristics_No.click();
			}
		}
	}

	// Secondary Transactions
	public void editBuildingDetailsPNB(Map<String, String> Data, int locNo, int bldgNo) {
		if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgAddr1").equals("")) {
			manualEntry.click();
			manualEntryAddress.waitTillVisibilityOfElement(60);
			manualEntryAddress.scrollToElement();
			manualEntryAddress.clearData();
			waitTime(3); // need this wait time to click on continue with update pop up
			if (expiredQuotePopUp.checkIfElementIsPresent() && expiredQuotePopUp.checkIfElementIsDisplayed()) {
				expiredQuotePopUp.scrollToElement();
				continueWithUpdateBtn.waitTillVisibilityOfElement(60);
				continueWithUpdateBtn.scrollToElement();
				continueWithUpdateBtn.click();
				continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}
			Assertions.addInfo("Building Page",
					"Building Address1 original Value : " + manualEntryAddress.getData().replace(",", ""));
			manualEntryAddress.setData(Data.get("L" + locNo + "B" + bldgNo + "-BldgAddr1"));

			if (expiredQuotePopUp.checkIfElementIsPresent() && expiredQuotePopUp.checkIfElementIsDisplayed()) {
				expiredQuotePopUp.scrollToElement();
				continueWithUpdateBtn.waitTillVisibilityOfElement(60);
				continueWithUpdateBtn.scrollToElement();
				continueWithUpdateBtn.click();
				continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}
			Assertions.passTest("Building Page",
					"Building Address1 Latest Value : " + manualEntryAddress.getData().replace(",", ""));

			Assertions.addInfo("Building Page", "Building City original Value : " + manualEntryCity.getData());
			manualEntryCity.setData(Data.get("L" + locNo + "B" + bldgNo + "-BldgCity"));
			Assertions.passTest("Building Page", "Building City Latest Value : " + manualEntryCity.getData());

			Assertions.addInfo("Building Page", "Building Zipcode original Value : " + manualEntryZipCode.getData());
			manualEntryZipCode.setData(Data.get("L" + locNo + "B" + bldgNo + "-BldgZIP"));
			Assertions.passTest("Building Page", "Building Zipcode Latest Value : " + manualEntryZipCode.getData());

			/*
			 * geocodeOverride.scrollToElement(); geocodeOverride.click();
			 * lattitude.scrollToElement(); lattitude.setData(Data.get("OverrideLatitude"));
			 * lattitude.tab(); longitude.scrollToElement();
			 * longitude.setData(Data.get("OverrideLongitude")); longitude.tab();
			 */
			address2.setData(Data.get("L" + locNo + "B" + bldgNo + "-BldgAddr2"));
		}

		if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgConstType").equals("")) {
			Assertions.addInfo("Building Page",
					"Building Construction Type original Value : " + constructionTypeData.getData());
			constructionTypeArrow.waitTillVisibilityOfElement(60);
			constructionTypeArrow.scrollToElement();
			constructionTypeArrow.click();

			constructionTypeOption.formatDynamicPath(Data.get("L" + locNo + "B" + bldgNo + "-BldgConstType"))
					.scrollToElement();
			constructionTypeOption.formatDynamicPath(Data.get("L" + locNo + "B" + bldgNo + "-BldgConstType")).click();
			Assertions.passTest("Building Page",
					"Building Construction Type Latest Value : " + constructionTypeData.getData());
		}

		if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgCladding").equals("")) {
			Assertions.passTest("Building Page",
					"Exterior Cladding Original Value : " + exteriorCladdingData.getData());
			exteriorCladdingArrow.scrollToElement();
			exteriorCladdingArrow.click();
			exteriorCladdingOption.formatDynamicPath(Data.get("L" + locNo + "B" + bldgNo + "-BldgCladding"))
					.scrollToElement();
			exteriorCladdingOption.formatDynamicPath(Data.get("L" + locNo + "B" + bldgNo + "-BldgCladding")).click();
			Assertions.passTest("Building Page", "Exterior Cladding Latest Value : " + exteriorCladdingData.getData());
		}

		if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgStories").equals("")) {
			Assertions.addInfo("Building Page", "Number of Stories original Value : " + numOfStories.getData());
			numOfStories.setData(Data.get("L" + locNo + "B" + bldgNo + "-BldgStories"));
			Assertions.passTest("Building Page", "Number of Stories Latest Value : " + numOfStories.getData());
		}

		if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgYearBuilt").equals("")) {
			Assertions.addInfo("Building Page", "Year Built original Value : " + yearBuilt.getData());
			yearBuilt.setData(Data.get("L" + locNo + "B" + bldgNo + "-BldgYearBuilt"));
			Assertions.passTest("Building Page", "Year Built Latest Value : " + yearBuilt.getData());
		}

		if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgSqFeet").equals("")) {
			Assertions.addInfo("Building Page",
					"Building Total Square Footage original Value : " + totalSquareFootage.getData());
			totalSquareFootage.setData(Data.get("L" + locNo + "B" + bldgNo + "-BldgSqFeet"));
			Assertions.passTest("Building Page",
					"Building Total Square Footage Latest Value : " + totalSquareFootage.getData());
		}

		if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgShape").equals("")) {
			if (buildingShapeRegular.checkIfElementIsSelected()) {
				Assertions.addInfo("Building Page", "Building Shape original Value : " + "Regular");
			}

			if (buildingShapeIrregular.checkIfElementIsSelected()) {
				Assertions.addInfo("Building Page", "Building Shape original Value : " + "IrRegular");
			}

			if (Data.get("L" + locNo + "B" + bldgNo + "-BldgShape").equalsIgnoreCase("regular")) {
				buildingShapeRegular.scrollToElement();
				buildingShapeRegular.click();
				Assertions.passTest("Building Page", "Building Shape Latest Value : " + "Regular");

			} else {
				buildingShapeIrregular.scrollToElement();
				buildingShapeIrregular.click();
				Assertions.passTest("Building Page", "Building Shape Latest Value : " + "IrRegular");
			}
		}
		if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgSetbacks").equals("")) {
			if (setbacksOverhangs_Yes.checkIfElementIsSelected()) {
				Assertions.addInfo("Building Page", "Setbacks/Ovehangs original Value : " + "Yes");
			}

			if (setbacksOverhangs_No.checkIfElementIsSelected()) {
				Assertions.addInfo("Building Page", "Setbacks/Ovehangs original Value : " + "No");
			}
			if (Data.get("L" + locNo + "B" + bldgNo + "-BldgSetbacks").equalsIgnoreCase("yes")) {
				setbacksOverhangs_Yes.scrollToElement();
				setbacksOverhangs_Yes.click();
				Assertions.passTest("Building Page", "Setbacks/Ovehangs Latest Value : " + "Yes");
			} else {
				setbacksOverhangs_No.scrollToElement();
				setbacksOverhangs_No.click();
				Assertions.passTest("Building Page", "Setbacks/Ovehangs Latest Value : " + "No");
			}
		}

		if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgParkingType").equals("")) {
			parkingTypeArrow.scrollToElement();
			parkingTypeArrow.click();
			Assertions.addInfo("Building Page", "Parking Type original Value : " + parkingTypeData.getData());
			parkingTypeOption.formatDynamicPath(Data.get("L" + locNo + "B" + bldgNo + "-BldgParkingType"))
					.scrollToElement();
			parkingTypeOption.formatDynamicPath(Data.get("L" + locNo + "B" + bldgNo + "-BldgParkingType")).click();
			Assertions.passTest("Building Page", "Parking Type Latest Value : " + parkingTypeData.getData());
		}

		if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgSoftStory").equalsIgnoreCase("")) {
			if (softStoryCharacteristics_Yes.checkIfElementIsSelected()) {
				Assertions.addInfo("Building Page", "Soft Story Characteristics original Value : " + "Yes");
			}

			if (softStoryCharacteristics_No.checkIfElementIsSelected()) {
				Assertions.addInfo("Building Page", "Soft Story Characteristics original Value : " + "No");
			}
			if (Data.get("L" + locNo + "B" + bldgNo + "-BldgSoftStory").equalsIgnoreCase("yes")) {
				softStoryCharacteristics_Yes.scrollToElement();
				softStoryCharacteristics_Yes.click();
				Assertions.passTest("Building Page", "Soft Story Characteristics Latest Value : " + "Yes");
				eligibilityCondition.waitTillElementisEnabled(60);
				eligibilityCondition.select();
			} else {
				softStoryCharacteristics_No.scrollToElement();
				softStoryCharacteristics_No.click();
				Assertions.passTest("Building Page", "Soft Story Characteristics Latest Value : " + "No");
			}
		}
	}

	public void addBuildingOccupancy(Map<String, String> Data, int locNo, int bldgNo) {
		buildingOccupancyLink.waitTillVisibilityOfElement(60);
		buildingOccupancyLink.scrollToElement();
		buildingOccupancyLink.click();
		waitTime(1);
		if (buildingOccupancy_yes.checkIfElementIsPresent() && buildingOccupancy_yes.checkIfElementIsDisplayed()) {
			String occupied31 = Data.get("L" + locNo + "B" + bldgNo + "-BuildingMorethan31%Occupied");
			if (occupied31 != null && occupied31.equalsIgnoreCase("yes")) {
				buildingOccupancy_yes.waitTillPresenceOfElement(60);
				buildingOccupancy_yes.waitTillVisibilityOfElement(60);
				buildingOccupancy_yes.scrollToElement();
				buildingOccupancy_yes.click();
			} else {
				buildingOccupancy_no.waitTillVisibilityOfElement(60);
				buildingOccupancy_no.scrollToElement();
				buildingOccupancy_no.click();
			}
		}

		if (primaryPercentOccupied.checkIfElementIsPresent() && primaryPercentOccupied.checkIfElementIsDisplayed()) {
			primaryPercentOccupied.setData(Data.get("L" + locNo + "B" + bldgNo + "-PercentOccupied"));
			primaryPercentOccupied.tab();
		}

		if (!Data.get("L" + locNo + "B" + bldgNo + "-PrimaryOccupancy").equals("")) {
			addBuildingPrimaryOccupancy(Data, locNo, bldgNo);
			// timing issue where occupant is not always set, wait for the occupancy text,
			// which shifts the page a bit
			primaryOccupancyText.waitTillVisibilityOfElement(60);
			waitTime(2);
		}

		if (tenant.checkIfElementIsPresent() && tenant.checkIfElementIsDisplayed()) {
			if (!(Data.get("L" + locNo + "B" + bldgNo + "-BldgTenancy").equals(""))) {
				if (Data.get("L" + locNo + "B" + bldgNo + "-BldgTenancy").equalsIgnoreCase("Owner")) {
					owner.waitTillPresenceOfElement(60);
					owner.waitTillVisibilityOfElement(60);
					owner.scrollToElement();
					owner.click();
				} else if (Data.get("L" + locNo + "B" + bldgNo + "-BldgTenancy").equalsIgnoreCase("Tenant")) {
					tenant.waitTillPresenceOfElement(60);
					tenant.waitTillVisibilityOfElement(60);
					tenant.scrollToElement();
					tenant.click();
				} else {
					vacant.waitTillPresenceOfElement(60);
					vacant.waitTillVisibilityOfElement(60);
					vacant.scrollToElement();
					vacant.click();
				}
			}
		}
		if (!Data.get("L" + locNo + "B" + bldgNo + "-SecondaryOccupancy").equals("")) {
			addBuildingSecondaryOccupancy(Data, locNo, bldgNo);
		}
	}

	public void addBuildingPrimaryOccupancy(Map<String, String> Data, int locNo, int bldgNo) {
		if (Data.get("L" + locNo + "B" + bldgNo + "-PrimaryOccupancyCode") != null
				&& !Data.get("L" + locNo + "B" + bldgNo + "-PrimaryOccupancyCode").equals("")) {
			if (!primaryOccupancy.getData().equals("")) {
				WebElement ele = WebDriverManager.getCurrentDriver().findElement(By.xpath(
						"//div[label[a[contains(text(),'Primary Occupancy')]]]//following-sibling::div//input[contains(@id,'primaryOccupancy')]"));
				ele.sendKeys(Keys.chord(FrameworkUtil.KEY_CMD_CTRL, "a"));
				ele.sendKeys(Keys.BACK_SPACE);
				ele.sendKeys(Data.get("L" + locNo + "B" + bldgNo + "-PrimaryOccupancy"));
				ele.sendKeys(Keys.TAB);
			}
			setOccupancyJS("primary", Data.get("L" + locNo + "B" + bldgNo + "-PrimaryOccupancyCode"), Data.get("Peril"),
					Data.get("QuoteState"));
		}
		if (Data.get("L" + locNo + "B" + bldgNo + "-PriOccupancyCondition") != null
				&& Data.get("L" + locNo + "B" + bldgNo + "-PriOccupancyCondition").equalsIgnoreCase("yes")) {
			primaryOccupancyCondition.waitTillVisibilityOfElement(60);
			if (primaryOccupancyCondition.checkIfElementIsDisplayed()
					&& primaryOccupancyCondition.checkIfElementIsEnabled()) {
				primaryOccupancyCondition.scrollToElement();
				primaryOccupancyCondition.select();
			} else {
				Assertions.failTest("Building Occupancy", "Failed to set Primary Occupancy Condition");
			}
		}

		primaryOccupancyText.waitTillPresenceOfElement(60);
		primaryOccupancyText.waitTillVisibilityOfElement(80);
	}

	public void addBuildingSecondaryOccupancy(Map<String, String> Data, int locNo, int bldgNo) {
		if (Data.get("L" + locNo + "B" + bldgNo + "-SecondaryOccupancyCode") != null
				&& !(Data.get("L" + locNo + "B" + bldgNo + "-SecondaryOccupancyCode").equals(""))) {
			if (!secondaryOccupancy.getData().equals("")) {
				WebElement ele = WebDriverManager.getCurrentDriver().findElement(By.xpath(
						"//div[label[a[contains(text(),'Secondary Occupancy')]]]//following-sibling::div//input[contains(@id,'secondaryOccupancy')]"));
				ele.sendKeys(Keys.chord(FrameworkUtil.KEY_CMD_CTRL, "a"));
				ele.sendKeys(Keys.BACK_SPACE);
			}
			setOccupancyJS("questionssecondary", Data.get("L" + locNo + "B" + bldgNo + "-SecondaryOccupancyCode"),
					Data.get("Peril"), Data.get("QuoteState"));
		}
		if (Data.get("L" + locNo + "B" + bldgNo + "-SecOccupancyCondition") != null
				&& Data.get("L" + locNo + "B" + bldgNo + "-SecOccupancyCondition").equalsIgnoreCase("yes")) {
			secondaryOccupancyCondition.waitTillVisibilityOfElement(60);
			if (secondaryOccupancyCondition.checkIfElementIsDisplayed()
					&& secondaryOccupancyCondition.checkIfElementIsEnabled()) {
				secondaryOccupancyCondition.scrollToElement();
				secondaryOccupancyCondition.select();
			} else {
				Assertions.failTest("Building Occupancy", "Failed to set Secondary Occupancy Condition");
			}

		}
		secondaryOccupancyText.waitTillVisibilityOfElement(60);
		waitTime(2);// if wait time is removed it is not going to if condition
		if (!(Data.get("L" + locNo + "B" + bldgNo + "-SecOccupancyPercent").equals(""))) {
			if (secondaryPercentOccupied.checkIfElementIsPresent()
					&& secondaryPercentOccupied.checkIfElementIsDisplayed()) {
				secondaryPercentOccupied.waitTillVisibilityOfElement(60);
				secondaryPercentOccupied.scrollToElement();
				secondaryPercentOccupied.setData(Data.get("L" + locNo + "B" + bldgNo + "-SecOccupancyPercent"));
			}
		}

	}

	public void editBuildingOccupancyPNB(Map<String, String> Data, int locNo, int bldgNo) {
		buildingOccupancyLink.waitTillVisibilityOfElement(60);
		buildingOccupancyLink.scrollToElement();
		buildingOccupancyLink.click();
		if (buildingOccupancy_yes.checkIfElementIsPresent() && buildingOccupancy_yes.checkIfElementIsDisplayed()) {
			if ("Yes".equals(Data.get("L" + locNo + "B" + bldgNo + "-BuildingMorethan31%Occupied"))) {
				buildingOccupancy_yes.waitTillPresenceOfElement(60);
				buildingOccupancy_yes.waitTillVisibilityOfElement(60);
				buildingOccupancy_yes.scrollToElement();
				waitTime(2);
				buildingOccupancy_yes.click();
			} else {
				buildingOccupancy_no.waitTillVisibilityOfElement(60);
				buildingOccupancy_no.scrollToElement();
				buildingOccupancy_no.click();
			}
		}

		//this isn't used for NAHO, HIHO or Commercial anymore.  delete when we're sure it's not being used for pre-existing testing
		/*if (!(Data.get("L" + locNo + "B" + bldgNo + "-PercentOccupied").equals(""))) {
			if (primaryPercentOccupied.checkIfElementIsPresent()
					&& primaryPercentOccupied.checkIfElementIsDisplayed()) {
				primaryPercentOccupied.setData(Data.get("L" + locNo + "B" + bldgNo + "-PercentOccupied"));
				primaryPercentOccupied.tab();
			}
		}*/

		if (!Data.get("L" + locNo + "B" + bldgNo + "-PrimaryOccupancy").equals("")) {
			editBuildingPrimaryOccupancyPNB(Data, locNo, bldgNo);
		}

		if (tenant.checkIfElementIsPresent() && tenant.checkIfElementIsDisplayed()) {
			if (!(Data.get("L" + locNo + "B" + bldgNo + "-BldgTenancy").equals(""))) {
				if (Data.get("L" + locNo + "B" + bldgNo + "-BldgTenancy").equalsIgnoreCase("Owner")) {
					owner.waitTillPresenceOfElement(60);
					owner.waitTillVisibilityOfElement(60);
					owner.scrollToElement();
					owner.click();
				} else if (Data.get("L" + locNo + "B" + bldgNo + "-BldgTenancy").equalsIgnoreCase("Tenant")) {
					tenant.waitTillPresenceOfElement(60);
					tenant.waitTillVisibilityOfElement(60);
					tenant.scrollToElement();
					tenant.click();
				} else {
					vacant.waitTillPresenceOfElement(60);
					vacant.waitTillVisibilityOfElement(60);
					vacant.scrollToElement();
					vacant.click();
				}
			}
		}
		if (!Data.get("L" + locNo + "B" + bldgNo + "-SecondaryOccupancy").equals("")) {
			editBuildingSecondaryOccupancyPNB(Data, locNo, bldgNo);
		}
	}

	public void editBuildingPrimaryOccupancyPNB(Map<String, String> Data, int locNo, int bldgNo) {
		if (Data.get("L" + locNo + "B" + bldgNo + "-PrimaryOccupancyCode") != null
				&& !Data.get("L" + locNo + "B" + bldgNo + "-PrimaryOccupancyCode").equals("")) {
			if (!primaryOccupancy.getData().equals("")) {
				WebElement ele = WebDriverManager.getCurrentDriver().findElement(By.xpath(
						"//div[label[a[contains(text(),'Primary Occupancy')]]]//following-sibling::div//input[contains(@id,'primaryOccupancy')]"));
				ele.sendKeys(Keys.chord(FrameworkUtil.KEY_CMD_CTRL, "a"), Keys.BACK_SPACE);
			}
			setOccupancyJS("primary", Data.get("L" + locNo + "B" + bldgNo + "-PrimaryOccupancyCode"), Data.get("Peril"),
					Data.get("QuoteState"));
		}
		waitTime(2);// if waittime is removed it is not going to if condition
		if (Data.get("L" + locNo + "B" + bldgNo + "-PriOccupancyCondition").equalsIgnoreCase("yes")) {
			primaryOccupancyCondition.waitTillVisibilityOfElement(60);
			if (primaryOccupancyCondition.checkIfElementIsDisplayed()
					&& primaryOccupancyCondition.checkIfElementIsEnabled()) {
				primaryOccupancyCondition.scrollToElement();
				primaryOccupancyCondition.select();
			} else {
				Assertions.failTest("Building Occupancy", "Failed to set Primary Occupancy Condition");
			}
		}

		primaryOccupancyText.waitTillVisibilityOfElement(60);
	}

	public void editBuildingSecondaryOccupancyPNB(Map<String, String> Data, int locNo, int bldgNo) {
		if (Data.get("L" + locNo + "B" + bldgNo + "-SecondaryOccupancyCode") != null
				&& !(Data.get("L" + locNo + "B" + bldgNo + "-SecondaryOccupancyCode").equals(""))) {
			if (!secondaryOccupancy.getData().equals("")) {
				WebElement ele = WebDriverManager.getCurrentDriver().findElement(By.xpath(
						"//div[label[a[contains(text(),'Secondary Occupancy')]]]//following-sibling::div//input[contains(@id,'secondaryOccupancy')]"));
				ele.sendKeys(Keys.chord(FrameworkUtil.KEY_CMD_CTRL, "a"), Keys.BACK_SPACE);
			}
			setOccupancyJS("questionssecondary", Data.get("L" + locNo + "B" + bldgNo + "-SecondaryOccupancyCode"),
					Data.get("Peril"), Data.get("QuoteState"));
		}
		if (Data.get("L" + locNo + "B" + bldgNo + "-SecOccupancyCondition").equalsIgnoreCase("yes")) {
			secondaryOccupancyCondition.waitTillVisibilityOfElement(60);
			if (secondaryOccupancyCondition.checkIfElementIsDisplayed()
					&& secondaryOccupancyCondition.checkIfElementIsEnabled()) {
				secondaryOccupancyCondition.scrollToElement();
				secondaryOccupancyCondition.select();
			} else {
				Assertions.failTest("Building Occupancy", "Failed to set Secondary Occupancy Condition");
			}

		}
		secondaryOccupancyText.waitTillVisibilityOfElement(60);
		waitTime(2);// if wait time is removed it is not going to if condition
		if (!(Data.get("L" + locNo + "B" + bldgNo + "-SecOccupancyPercent").equals(""))) {

			if (secondaryPercentOccupied.checkIfElementIsPresent()
					&& secondaryPercentOccupied.checkIfElementIsDisplayed()) {
				secondaryPercentOccupied.waitTillVisibilityOfElement(60);
				secondaryPercentOccupied.scrollToElement();
				secondaryPercentOccupied.setData(Data.get("L" + locNo + "B" + bldgNo + "-SecOccupancyPercent"));
			}
		}
	}

	public void addRoofDetails(Map<String, String> Data, int locNo, int bldgNo) {

		if (roofDetailsLink.checkIfElementIsPresent() && roofDetailsLink.checkIfElementIsDisplayed()) {
			roofDetailsLink.waitTillVisibilityOfElement(60);
			roofDetailsLink.scrollToElement();
			roofDetailsLink.click();
		}
		// set roof year replaced first to trigger the roof age notes, as it blocks the
		// additional building info link
		// hitting tab takes the user to the additional building info section in an
		// unreliable way because of the animation around the roof age notes
		// so, i'm sending shift+tab to tab backwards
		if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgRoofAge").equals("")) {
			yearRoofLastReplaced.waitTillVisibilityOfElement(60);
			yearRoofLastReplaced.scrollToElement();
			yearRoofLastReplaced.setData(Data.get("L" + locNo + "B" + bldgNo + "-BldgRoofAge"));
			WebElement ele = WebDriverManager.getCurrentDriver()
					.findElement(By.xpath("//div//input[@id='questionsyearRoofLastReplaced']"));
			ele.sendKeys(Keys.SHIFT, Keys.TAB);
			waitTime(2);
		}

		// waitTime(3); // wait for the roof year info box to show up
		if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgRoofShape").equals("")) {

			roofShapeArrow.waitTillButtonIsClickable(60);
			roofShapeArrow.waitTillVisibilityOfElement(60);
			roofShapeArrow.scrollToElement();
			roofShapeArrow.click();
			roofShapeOption.formatDynamicPath(Data.get("L" + locNo + "B" + bldgNo + "-BldgRoofShape"))
					.scrollToElement();
			roofShapeOption.formatDynamicPath(Data.get("L" + locNo + "B" + bldgNo + "-BldgRoofShape")).click();
		}

		if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgRoofCladding").equals("")) {
			roofCladdingArrow.waitTillButtonIsClickable(60);
			roofCladdingArrow.waitTillVisibilityOfElement(60);
			roofCladdingArrow.scrollToElement();
			roofCladdingArrow.click();
			roofCladdingOption.formatDynamicPath(Data.get("L" + locNo + "B" + bldgNo + "-BldgRoofCladding"))
					.scrollToElement();
			roofCladdingOption.formatDynamicPath(Data.get("L" + locNo + "B" + bldgNo + "-BldgRoofCladding")).click();
		}

	}

	public void editRoofDetailsPNB(Map<String, String> Data, int locNo, int bldgNo) {
		if (roofDetailsLink.checkIfElementIsPresent() && roofDetailsLink.checkIfElementIsDisplayed()) {
			roofDetailsLink.waitTillVisibilityOfElement(60);
			roofDetailsLink.waitTillButtonIsClickable(60);
			roofDetailsLink.scrollToElement();
			roofDetailsLink.click();
		}
		// set roof year replaced first to trigger the roof age notes
		// hitting tab takes the user to the additional building info section in an
		// unreliable way because of the animation around the roof age notes
		// so, i'm sending shift+tab to tab backwards
		// TODO add roof age dropdown for pre-existing data scenarios
		if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgRoofAge").equals("")) {
			yearRoofLastReplaced.waitTillVisibilityOfElement(60);
			yearRoofLastReplaced.scrollToElement();
			yearRoofLastReplaced.setData(Data.get("L" + locNo + "B" + bldgNo + "-BldgRoofAge"));
			WebElement ele = WebDriverManager.getCurrentDriver()
					.findElement(By.xpath("//div//input[@id='questionsyearRoofLastReplaced']"));
			ele.sendKeys(Keys.SHIFT, Keys.TAB);
			waitTime(2);
		}

		// waitTime(3); // wait for the roof year info box to show up
		if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgRoofShape").equals("")) {

			roofShapeArrow.waitTillButtonIsClickable(60);
			roofShapeArrow.waitTillVisibilityOfElement(60);
			roofShapeArrow.scrollToElement();
			roofShapeArrow.click();
			roofShapeOption.formatDynamicPath(Data.get("L" + locNo + "B" + bldgNo + "-BldgRoofShape"))
					.scrollToElement();
			roofShapeOption.formatDynamicPath(Data.get("L" + locNo + "B" + bldgNo + "-BldgRoofShape")).click();
		}

		if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgRoofCladding").equals("")) {
			Assertions.addInfo("Building Page", "Roof Cladding original Value : " + roofCladdingData.getData());
			roofCladdingArrow.waitTillPresenceOfElement(60);
			roofCladdingArrow.waitTillVisibilityOfElement(60);
			roofCladdingArrow.waitTillElementisEnabled(60);
			roofCladdingArrow.waitTillButtonIsClickable(60);
			roofCladdingArrow.scrollToElement();
			roofCladdingArrow.click();
			roofCladdingOption.formatDynamicPath(Data.get("L" + locNo + "B" + bldgNo + "-BldgRoofCladding"))
					.scrollToElement();
			roofCladdingOption.formatDynamicPath(Data.get("L" + locNo + "B" + bldgNo + "-BldgRoofCladding")).click();
			Assertions.passTest("Building Page", "Roof Cladding Latest Value : " + roofCladdingData.getData());
		}

	}

	public void enterAdditionalBuildingInformation(Map<String, String> Data, int locNo, int bldgNo) {
		if (additionalInfoLink.checkIfElementIsPresent() && additionalInfoLink.checkIfElementIsDisplayed()) {
			additionalInfoLink.waitTillVisibilityOfElement(60);
			additionalInfoLink.scrollToElement();
			additionalInfoLink.click();
		}
		waitTime(1);
		if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgSecurity").equals("")) {
			buildingSecurityArrow.waitTillPresenceOfElement(60);
			buildingSecurityArrow.waitTillElementisEnabled(60);
			buildingSecurityArrow.waitTillButtonIsClickable(60);
			buildingSecurityArrow.waitTillVisibilityOfElement(60);
			buildingSecurityArrow.scrollToElement();
			waitTime(3);
			buildingSecurityArrow.click();
			buildingSecurityOption.formatDynamicPath(Data.get("L" + locNo + "B" + bldgNo + "-BldgSecurity"))
					.waitTillVisibilityOfElement(60);
			buildingSecurityOption.formatDynamicPath(Data.get("L" + locNo + "B" + bldgNo + "-BldgSecurity"))
					.scrollToElement();
			buildingSecurityOption.formatDynamicPath(Data.get("L" + locNo + "B" + bldgNo + "-BldgSecurity")).click();
		}

		if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgFireProtection").equals("")) {
			fireProtectionArrow.scrollToElement();
			fireProtectionArrow.click();
			fireProtectionoption.formatDynamicPath(Data.get("L" + locNo + "B" + bldgNo + "-BldgFireProtection"))
					.scrollToElement();
			fireProtectionoption.formatDynamicPath(Data.get("L" + locNo + "B" + bldgNo + "-BldgFireProtection"))
					.click();
		}

		if (protectionClass.checkIfElementIsPresent() && protectionClass.checkIfElementIsDisplayed()) {
			if (!Data.get("L" + locNo + "B" + bldgNo + "-ProtectionClassOverride").equals("")) {
				if (!Data.get("L" + locNo + "B" + bldgNo + "-ProtectionClassOverride").equalsIgnoreCase(""))
					protectionClass.setData(Data.get("L" + locNo + "B" + bldgNo + "-ProtectionClassOverride"));
			}
		}

		if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgWindResistive").equals("")) {
			if (Data.get("L" + locNo + "B" + bldgNo + "-BldgWindResistive").equalsIgnoreCase("yes"))
				windResistive_Yes.click();
			else
				windResistive_No.click();
		}

		// Since element is not present in q3 if condition is added - 837
		if (aluminumWiring_Yes.checkIfElementIsPresent() && aluminumWiring_Yes.checkIfElementIsDisplayed()) {
			if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgALWiring").equals("")) {
				if (Data.get("L" + locNo + "B" + bldgNo + "-BldgALWiring").equalsIgnoreCase("yes"))
					aluminumWiring_Yes.click();
				else
					aluminumWiring_No.click();
			}
		}
	}

	public void editAdditionalBuildingInformationPNB(Map<String, String> Data, int locNo, int bldgNo) {
		if (additionalInfoLink.checkIfElementIsPresent() && additionalInfoLink.checkIfElementIsDisplayed()) {
			additionalInfoLink.waitTillVisibilityOfElement(60);
			additionalInfoLink.scrollToElement();
			additionalInfoLink.click();
		}
		waitTime(3);
		if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgSecurity").equals("")) {
			Assertions.addInfo("Building Page", "Building Security original Value : " + buildingSecurityData.getData());
			buildingSecurityArrow.waitTillPresenceOfElement(60);
			buildingSecurityArrow.waitTillElementisEnabled(60);
			buildingSecurityArrow.waitTillButtonIsClickable(60);
			buildingSecurityArrow.waitTillVisibilityOfElement(60);
			buildingSecurityArrow.scrollToElement();
			buildingSecurityArrow.click();
			buildingSecurityOption.formatDynamicPath(Data.get("L" + locNo + "B" + bldgNo + "-BldgSecurity"))
					.scrollToElement();
			buildingSecurityOption.formatDynamicPath(Data.get("L" + locNo + "B" + bldgNo + "-BldgSecurity")).click();
			Assertions.passTest("Building Page", "Building Security Latest Value : " + buildingSecurityData.getData());
		}

		if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgFireProtection").equals("")) {
			Assertions.addInfo("Building Page", "Fire Protection original Value : " + fireProtectionData.getData());
			fireProtectionArrow.scrollToElement();
			fireProtectionArrow.click();
			fireProtectionoption.formatDynamicPath(Data.get("L" + locNo + "B" + bldgNo + "-BldgFireProtection"))
					.scrollToElement();
			fireProtectionoption.formatDynamicPath(Data.get("L" + locNo + "B" + bldgNo + "-BldgFireProtection"))
					.click();
			Assertions.passTest("Building Page", "Fire Protection Latest Value : " + fireProtectionData.getData());
		}

		if (protectionClass.checkIfElementIsPresent() && protectionClass.checkIfElementIsDisplayed()) {
			if (!Data.get("L" + locNo + "B" + bldgNo + "-ProtectionClassOverride").equals("")) {
				if (!Data.get("L" + locNo + "B" + bldgNo + "-ProtectionClassOverride").equalsIgnoreCase(""))
					Assertions.addInfo("Building Page",
							"Protection Class original Value : " + protectionClass.getData());
				protectionClass.setData(Data.get("L" + locNo + "B" + bldgNo + "-ProtectionClassOverride"));
				Assertions.passTest("Building Page", "Protection Class Latest Value : " + protectionClass.getData());
			}
		}

		if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgWindResistive").equals("")) {
			if (windResistive_Yes.checkIfElementIsSelected()) {
				Assertions.addInfo("Building Page", "Wind Resistive original Value : " + "Yes");
			}
			if (windResistive_No.checkIfElementIsSelected()) {
				Assertions.addInfo("Building Page", "Wind Resistive Latest Value : " + "No");
			}

			if (Data.get("L" + locNo + "B" + bldgNo + "-BldgWindResistive").equalsIgnoreCase("yes")) {
				windResistive_Yes.click();
				Assertions.passTest("Building Page", "Wind Resistive original Value : " + "Yes");
			} else {
				windResistive_No.click();
				Assertions.passTest("Building Page", "Wind Resistive original Value : " + "No");
			}
		}
		if (aluminumWiring_Yes.checkIfElementIsPresent() && aluminumWiring_Yes.checkIfElementIsDisplayed()) {
			if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgALWiring").equals("")) {

				if (aluminumWiring_Yes.checkIfElementIsSelected()) {
					Assertions.addInfo("Building Page", "Aluminium Wiring original Value : " + "Yes");
				}
				if (aluminumWiring_No.checkIfElementIsSelected()) {
					Assertions.addInfo("Building Page", "Aluminium Wiring Latest Value : " + "No");
				}

				if (Data.get("L" + locNo + "B" + bldgNo + "-BldgALWiring").equalsIgnoreCase("yes")) {
					aluminumWiring_Yes.click();
					Assertions.passTest("Building Page", "Aluminium Wiring original Value : " + "Yes");
				} else {
					aluminumWiring_No.click();
					Assertions.passTest("Building Page", "Aluminium Wiring Latest Value : " + "No");
				}
			}
		}
	}

	public void enterBuildingValues(Map<String, String> Data, int locNo, int bldgNo) {
		buildingValuesLink.waitTillVisibilityOfElement(60);
		buildingValuesLink.scrollToElement();
		buildingValuesLink.click();

		if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgValue").equals(""))
			buildingValue.setData(Data.get("L" + locNo + "B" + bldgNo + "-BldgValue"));

		if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgBPP").equals(""))
			businessPersonalProperty.setData(Data.get("L" + locNo + "B" + bldgNo + "-BldgBPP"));
	}

	public void editBuildingValuesPNB(Map<String, String> Data, int locNo, int bldgNo) {
		buildingValuesLink.waitTillVisibilityOfElement(60);
		buildingValuesLink.scrollToElement();
		buildingValuesLink.click();

		if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgValue").equals("")) {
			Assertions.addInfo("Building Page", "Building Limit original Value : " + "$" + buildingValue.getData());
			buildingValue.setData(Data.get("L" + locNo + "B" + bldgNo + "-BldgValue"));
			Assertions.passTest("Building Page", "Building Limit Latest Value : " + "$" + buildingValue.getData());
		}

		if (!Data.get("L" + locNo + "B" + bldgNo + "-BldgBPP").equals("")) {
			Assertions.addInfo("Building Page",
					"Business Personal Property Limit original Value : " + "$" + businessPersonalProperty.getData());
			businessPersonalProperty.setData(Data.get("L" + locNo + "B" + bldgNo + "-BldgBPP"));
			Assertions.addInfo("Building Page",
					"Business Personal Property Limit Latest Value : " + "$" + businessPersonalProperty.getData());
		}
	}

	public void reviewBuilding() {
		waitTime(3); // Added wait time as review dwelling is not clicking many times
		reviewBuilding.waitTillPresenceOfElement(60);
		reviewBuilding.waitTillVisibilityOfElement(60);
		reviewBuilding.waitTillElementisEnabled(60);
		reviewBuilding.waitTillButtonIsClickable(60);
		reviewBuilding.scrollToElement();
		reviewBuilding.click();
		if (pageName.getData().contains("Existing Account Found")) {
			existingAccountPage = new ExistingAccountPage();
			existingAccountPage.OverrideExistingAccount();
		}
		if (pageName.getData().contains("Under Minimum Cost")) {
			BuildingUnderMinimumCostPage buildingUnderminimumCost = new BuildingUnderMinimumCostPage();
			buildingUnderminimumCost.clickOnOverride();
		}

		if (continueOverrideCost.checkIfElementIsPresent() && continueOverrideCost.checkIfElementIsDisplayed()) {
			BuildingUnderMinimumCostPage buildingUnderminimumCost = new BuildingUnderMinimumCostPage();
			buildingUnderminimumCost.bringUpToCost.scrollToElement();
			buildingUnderminimumCost.bringUpToCost.click();
		}
	}

	public SelectPerilPage enterBuildingDetails(Map<String, String> Data) {
		String locationNumber = Data.get("LocCount");
		int locationCount = Integer.parseInt(locationNumber);
		for (int locNo = 1; locNo <= locationCount; locNo++) {
			locationLink.formatDynamicPath(locNo).scrollToElement();
			locationLink.formatDynamicPath(locNo).click();
			String buildingNumber = Data.get("L" + locNo + "-BldgCount");
			int buildingCount = Integer.parseInt(buildingNumber);
			for (int bldgNo = 1; bldgNo <= buildingCount; bldgNo++) {
				if (buildingLink.formatDynamicPath(locNo, bldgNo).checkIfElementIsPresent()
						&& buildingLink.formatDynamicPath(locNo, bldgNo).checkIfElementIsDisplayed()
						|| buildingLink.formatDynamicPath(locNo, bldgNo).checkIfElementIsPresent()) {
					buildingLink.formatDynamicPath(locNo, bldgNo).click();
					if (editBuilding.formatDynamicPath(locNo, bldgNo).checkIfElementIsPresent()
							&& editBuilding.formatDynamicPath(locNo, bldgNo).checkIfElementIsDisplayed()) {
						editBuilding.formatDynamicPath(locNo, bldgNo).click();
					}
				} else {
					addSymbol.scrollToElement();
					addSymbol.click();
					addNewBuilding.click();
				}
				if (editBuilding.checkIfElementIsPresent() && editBuilding.checkIfElementIsDisplayed()) {
					editBuilding.scrollToElement();
					editBuilding.click();
				}
				addBuildingDetails(Data, locNo, bldgNo);
				addBuildingOccupancy(Data, locNo, bldgNo);
				waitTime(1);
				if (primaryOccupancyCondition.checkIfElementIsPresent()
						&& primaryOccupancyCondition.checkIfElementIsDisplayed()) {
					primaryOccupancyCondition.select();
				}
				if (secondaryOccupancyCondition.checkIfElementIsPresent()
						&& secondaryOccupancyCondition.checkIfElementIsDisplayed()) {
					secondaryOccupancyCondition.select();
				}
				addRoofDetails(Data, locNo, bldgNo);
				enterAdditionalBuildingInformation(Data, locNo, bldgNo);
				enterBuildingValues(Data, locNo, bldgNo);
				reviewBuilding();
				boolean addressFound = false;

				if (addressMsg.checkIfElementIsPresent() && addressMsg.checkIfElementIsDisplayed()
						&& !addressFound) {
					for (int i = 1; i <= 80; i++) {
						if (editBuilding.checkIfElementIsPresent()) {
							editBuilding.scrollToElement();
							editBuilding.click();
						}
						manualEntry.click();
						manualEntryAddress.waitTillVisibilityOfElement(60);
						manualEntryAddress.setData(manualEntryAddress.getData().replace(
								manualEntryAddress.getData().replaceAll("[^0-9]", ""),
								(Integer.parseInt(manualEntryAddress.getData().replaceAll("[^0-9]", ""))) + 2 + ""));
						manualEntryAddress.tab();
						buildingValuesLink.scrollToElement();
						buildingValuesLink.click();
						scrollToBottomPage();
						reviewBuilding();

						if (!addressMsg.checkIfElementIsPresent() || !addressMsg.checkIfElementIsPresent()) {
							addressFound = true;
							break;
						}
					}
				}

				if (override.checkIfElementIsPresent() && override.checkIfElementIsDisplayed()) {
					override.scrollToElement();
					override.click();
				}

				if (pageName.getData().contains("Existing Account Found")) {
					existingAccountPage = new ExistingAccountPage();
					existingAccountPage.OverrideExistingAccount();
				}

				if (pageName.getData().contains("Under Minimum Cost")) {
					BuildingUnderMinimumCostPage buildingUnderminimumCost = new BuildingUnderMinimumCostPage();
					buildingUnderminimumCost.clickOnOverride();
				}
				Assertions.passTest("Building Page",
						"Building L" + locNo + " B" + bldgNo + " details entered successfully");
			}
		}

		if (createQuote.checkIfElementIsPresent() && createQuote.checkIfElementIsDisplayed()
				|| createQuote.checkIfElementIsPresent()) {
			createQuote.scrollToElement();
			createQuote.click();
		}

		if (createQuote.checkIfElementIsPresent() && createQuote.checkIfElementIsDisplayed()
				|| createQuote.checkIfElementIsPresent()) {
			createQuote.scrollToElement();
			createQuote.click();
		}

		if (pageName.getData().contains("Buildings No")) {
			BuildingNoLongerQuoteablePage buildingNoLongerQuotablePage = new BuildingNoLongerQuoteablePage();
			buildingNoLongerQuotablePage.overrideNoLongerQuotableBuildings();
		}

		if (pageName.getData().contains("Select Peril")) {
			return new SelectPerilPage();
		}
		return null;
	}

	public void modifyBuildingDetailsPNB(Map<String, String> Data, int locNo, int bldgNo) {

		// click on default "Location 1", "Location 2", etc or use the LocName from the
		// spreadsheet
		if (locationLink.formatDynamicPath(locNo).checkIfElementIsPresent()
				&& locationLink.formatDynamicPath(locNo).checkIfElementIsEnabled()) {
			locationLink.formatDynamicPath(locNo).scrollToElement();
			locationLink.formatDynamicPath(locNo).click();
		} else if (locationListName.formatDynamicPath(Data.get("L" + locNo + "-LocName")).checkIfElementIsPresent()
				&& locationListName.formatDynamicPath(Data.get("L" + locNo + "-LocName")).checkIfElementIsEnabled()) {
			locationListName.formatDynamicPath(Data.get("L" + locNo + "-LocName")).scrollToElement();
			locationListName.formatDynamicPath(Data.get("L" + locNo + "-LocName")).click();
		}

		if (buildingLink.formatDynamicPath(locNo, bldgNo).checkIfElementIsPresent()
				&& buildingLink.formatDynamicPath(locNo, bldgNo).checkIfElementIsDisplayed()) {
			buildingLink.formatDynamicPath(locNo, bldgNo).click();
		} else {
			addSymbol.scrollToElement();
			addSymbol.click();
			addNewBuilding.click();
		}

		if (editBuilding.checkIfElementIsPresent() && editBuilding.checkIfElementIsDisplayed()) {
			editBuilding.scrollToElement();
			editBuilding.click();
		}

		editBuildingDetailsPNB(Data, locNo, bldgNo);
		editBuildingOccupancyPNB(Data, locNo, bldgNo);
		editRoofDetailsPNB(Data, locNo, bldgNo);
		editAdditionalBuildingInformationPNB(Data, locNo, bldgNo);
		editBuildingValuesPNB(Data, locNo, bldgNo);
		reviewBuilding();

		if (addressMsg.checkIfElementIsPresent() && addressMsg.checkIfElementIsDisplayed()) {
			override.scrollToElement();
			override.click();
		}

		if (override.checkIfElementIsPresent() && override.checkIfElementIsDisplayed()) {
			override.scrollToElement();
			override.click();
		}

		if (pageName.getData().contains("Existing Account Found")) {
			existingAccountPage = new ExistingAccountPage();
			existingAccountPage.OverrideExistingAccount();
		}

		if (pageName.getData().contains("Under Minimum Cost")) {
			BuildingUnderMinimumCostPage buildingUnderminimumCost = new BuildingUnderMinimumCostPage();
			buildingUnderminimumCost.clickOnOverride();
		}

		// TF 10/23/24 - i don't think we want to continue on to the quote page -
		// quoting should be called after all buildings are updated
		/*
		 * if (createQuote.checkIfElementIsPresent() &&
		 * createQuote.checkIfElementIsDisplayed() ||
		 * createQuote.checkIfElementIsPresent()) { createQuote.scrollToElement();
		 * createQuote.click(); }
		 */

		if (pageName.getData().contains("Buildings No")) {
			BuildingNoLongerQuoteablePage buildingNoLongerQuotablePage = new BuildingNoLongerQuoteablePage();
			buildingNoLongerQuotablePage.overrideNoLongerQuotableBuildings();
		}

	}

	public void deleteBuilding(Map<String, String> Data, int locNo, int bldgNo) {
		Assertions.addInfo("L" + locNo + "D" + bldgNo + "Dwelling - Deleted", "");
		buildingLink.formatDynamicPath(locNo, bldgNo).scrollToElement();
		buildingLink.formatDynamicPath(locNo, bldgNo).click();
		//TF 11/11/24 - it looks like sometimes the delete option is available at the bottom of the left pane and sometimes only up top
		//maybe depends on previous changes?
		if (deleteBuilding.checkIfElementIsPresent() && deleteBuilding.checkIfElementIsDisplayed()) {
			deleteBuilding.scrollToElement();
			deleteBuilding.click();
			deleteBldgPopup.waitTillVisibilityOfElement(60);
			deleteYes.scrollToElement();
			deleteYes.click();
		} else if (deleteBuildingTop.checkIfElementIsPresent() && deleteBuildingTop.checkIfElementIsDisplayed()) {
			deleteBuildingTop.scrollToElement();
			deleteBuildingTop.click();
			deleteBuildingPopupTop.waitTillVisibilityOfElement(60);
			deleteYes.scrollToElement();
			deleteYes.click();
		}


	}

	public void enterEndorsementBuildingDetails(Map<String, String> data) {
		for (int i = 1; i <= 4; i++) {
			for (int j = 1; j <= 7; j++) {
				if (data.get("L" + i + "B" + j + "-Delete") != null) {
					if (data.get("L" + i + "B" + j + "-Delete").equalsIgnoreCase("Yes")) {
						deleteBuilding(data, i, j);
					}
				}
				if (data.get("L" + i + "B" + j + "-Modify") != null) {
					if (data.get("L" + i + "B" + j + "-Modify").equalsIgnoreCase("Yes")) {
						modifyBuildingDetailsPNB(data, i, j);
					}
				}
				if (data.get("L" + i + "B" + j + "-Add") != null) {
					// System.out.println("Adding building " + data.get("L" + i + "B" + j +
					// "-Add"));
					if (data.get("L" + i + "B" + j + "-Add").equalsIgnoreCase("Yes")) {
						System.out.println("i = " + i + " j = " + j);
						addBuildingToLocation(data, i, j);
					}
				}
			}
		}
	}

	public void enterEndorsementBuildingDetailsNew(Map<String, String> data) {
		for (int i = 1; i <= 3; i++) {
			for (int j = 1; j <= 5; j++) {
				if (data.get("L" + i + "B" + j + "-Delete") != null) {
					if (data.get("L" + i + "B" + j + "-Delete").equalsIgnoreCase("Yes")) {
						deleteBuilding(data, i, j);
					}
				}
				if (data.get("L" + i + "B" + j + "-Modify") != null) {
					if (data.get("L" + i + "B" + j + "-Modify").equalsIgnoreCase("Yes")) {
						modifyBuildingDetailsPNB(data, i, j);
					}
				}
				if (data.get("L" + i + "B" + j + "-Add") != null) {
					if (data.get("L" + i + "B" + j + "-Add").equalsIgnoreCase("Yes")) {
						addBuildingToLocation(data, i, j);
					}
				}
			}
		}
	}

	public void addBuildingToLocation(Map<String, String> Data, int locNo, int bldgNo) {
		if (locationLink.formatDynamicPath(locNo).checkIfElementIsPresent()) {
			locationLink.formatDynamicPath(locNo).scrollToElement();
			locationLink.formatDynamicPath(locNo).click();
		} else {
			locationListName.formatDynamicPath(Data.get("L" + locNo + "-LocName")).checkIfElementIsPresent();
			locationListName.formatDynamicPath(Data.get("L" + locNo + "-LocName")).scrollToElement();
			locationListName.formatDynamicPath(Data.get("L" + locNo + "-LocName")).click();
		}
		if (buildingLink.formatDynamicPath(locNo, bldgNo).checkIfElementIsPresent()) {
			buildingLink.formatDynamicPath(locNo, bldgNo).click();
		} else {
			addSymbol.scrollToElement();
			addSymbol.click();
			addNewBuilding.click();
		}
		Assertions.addInfo("L" + locNo + "B" + bldgNo + " Building - Added", "");
		addBuildingDetails(Data, locNo, bldgNo);
		addBuildingOccupancy(Data, locNo, bldgNo);
		addRoofDetails(Data, locNo, bldgNo);
		enterAdditionalBuildingInformation(Data, locNo, bldgNo);
		enterBuildingValues(Data, locNo, bldgNo);
		reviewBuilding();

	}

	public void setOccupancyJS(String occType, String occCode, String peril, String stateCode) {
		JavascriptExecutor jse = (JavascriptExecutor) WebDriverManager.getCurrentDriver();
		// peril and state are required, but can be any value, adding defaults
		String perilText = "ALLRISK";
		if (peril.equalsIgnoreCase("aop") || peril.equalsIgnoreCase("gl")) {
			perilText = "ALLRISK";
			if (stateCode == null || stateCode.equals("")) {
				stateCode = "TX";
			}
		} else if (peril.equalsIgnoreCase("wind")) {
			perilText = "WIND";
			if (stateCode == null || stateCode.equals("")) {
				stateCode = "TX";
			}
		} else if (peril.equalsIgnoreCase("eq") || peril.equalsIgnoreCase("quake")) {
			perilText = "QUAKE";
			if (stateCode == null || stateCode.equals("")) {
				stateCode = "CA";
			}
		}

		String occJavascript = "EPICENTER_3TO.processSelectOccupancy({httpContext: '/icatsss/ajax/occupancy', inputId: '"
				+ occType.toLowerCase() + "Occupancy', noteDivId: '" + occType.toLowerCase()
				+ "OccupancyNoteDiv', occupancy: '" + occType.toLowerCase() + "', textId: '" + occType.toLowerCase()
				+ "OccupancyText', tier3: [" + occCode
				+ "], tierAccountCreationDate: '08/19/2019', tierBaseProductTypeId: '" + perilText + "', tierStateId: '"
				+ stateCode + "'}); ";
		jse.executeScript(occJavascript, "");
	}

	// OLD METHODS NOT CURRENTLY IN USE

	public void addBuildingForNewLocation(Map<String, String> Data) {
		List<String> locNo = Arrays.asList(Data.get("AddLocation").split(","));
		System.out.println("locNo = " + locNo);
		for (int i = 0; i < locNo.size(); i++) {
			System.out.println("i = " + i);
			int bldgCount = Integer.valueOf(Data.get("L" + locNo.get(i) + "-BldgCount"));
			for (int bldgNo = 1; bldgNo <= bldgCount; bldgNo++) {
				locationLink.formatDynamicPath(locNo.get(i)).scrollToElement();
				locationLink.formatDynamicPath(locNo.get(i)).click();
				if (buildingLink.formatDynamicPath(locNo.get(i), bldgNo).checkIfElementIsPresent()) {
					buildingLink.formatDynamicPath(locNo.get(i), bldgNo).click();
				} else {
					addSymbol.scrollToElement();
					addSymbol.click();
					addNewBuilding.click();
				}
				Assertions.addInfo("L" + locNo.get(i) + "B" + bldgNo + " Building - Added", "");
				addBuildingDetails(Data, Integer.parseInt(locNo.get(i)), bldgNo);
				addBuildingOccupancy(Data, Integer.parseInt(locNo.get(i)), bldgNo);
				addRoofDetails(Data, Integer.parseInt(locNo.get(i)), bldgNo);
				enterAdditionalBuildingInformation(Data, Integer.parseInt(locNo.get(i)), bldgNo);
				enterBuildingValues(Data, Integer.parseInt(locNo.get(i)), bldgNo);
				reviewBuilding();

			}
		}
	}

	public void modifyBuildingDetailsPNB_old(Map<String, String> Data) {
		String locationNumber = Data.get("LocCount");
		int locationCount = Integer.parseInt(locationNumber);
		for (int locNo = 1; locNo <= locationCount; locNo++) {
			locationLink.formatDynamicPath(locNo).scrollToElement();
			locationLink.formatDynamicPath(locNo).click();
			String buildingNumber = Data.get("L" + locNo + "-BldgCount");
			int buildingCount = Integer.parseInt(buildingNumber);
			for (int bldgNo = 1; bldgNo <= buildingCount; bldgNo++) {
				if (buildingLink.formatDynamicPath(locNo, bldgNo).checkIfElementIsPresent()
						&& buildingLink.formatDynamicPath(locNo, bldgNo).checkIfElementIsDisplayed()) {
					buildingLink.formatDynamicPath(locNo, bldgNo).click();
				} else {
					addSymbol.scrollToElement();
					addSymbol.click();
					addNewBuilding.click();
				}

				if (editBuilding.checkIfElementIsPresent() && editBuilding.checkIfElementIsDisplayed()) {
					editBuilding.scrollToElement();
					editBuilding.click();
				}

				editBuildingDetailsPNB(Data, locNo, bldgNo);
				editBuildingOccupancyPNB(Data, locNo, bldgNo);
				editRoofDetailsPNB(Data, locNo, bldgNo);
				editAdditionalBuildingInformationPNB(Data, locNo, bldgNo);
				editBuildingValuesPNB(Data, locNo, bldgNo);
				reviewBuilding();
			}
		}

		boolean addressFound = false;

		if (addressMsg.checkIfElementIsPresent() && addressMsg.checkIfElementIsDisplayed() && !addressFound) {
			for (int i = 1; i <= 80; i++) {
				if (editBuilding.checkIfElementIsPresent()) {
					editBuilding.scrollToElement();
					editBuilding.click();
				}
				manualEntry.click();
				manualEntryAddress.waitTillVisibilityOfElement(60);
				manualEntryAddress.setData(
						manualEntryAddress.getData().replace(manualEntryAddress.getData().replaceAll("[^0-9]", ""),
								(Integer.parseInt(manualEntryAddress.getData().replaceAll("[^0-9]", ""))) + 2 + ""));
				manualEntryAddress.tab();
				buildingValuesLink.scrollToElement();
				buildingValuesLink.click();
				scrollToBottomPage();
				reviewBuilding();

				if (!addressMsg.checkIfElementIsPresent() || !addressMsg.checkIfElementIsPresent()) {
					addressFound = true;
					break;
				}
			}
		}

		if (override.checkIfElementIsPresent() && override.checkIfElementIsDisplayed()) {
			override.scrollToElement();
			override.click();
		}

		if (pageName.getData().contains("Existing Account Found")) {
			existingAccountPage = new ExistingAccountPage();
			existingAccountPage.OverrideExistingAccount();
		}

		if (pageName.getData().contains("Under Minimum Cost")) {
			BuildingUnderMinimumCostPage buildingUnderminimumCost = new BuildingUnderMinimumCostPage();
			buildingUnderminimumCost.clickOnOverride();
		}

		if (createQuote.checkIfElementIsPresent() && createQuote.checkIfElementIsDisplayed()
				|| createQuote.checkIfElementIsPresent()) {
			createQuote.scrollToElement();
			createQuote.click();
		}

		if (pageName.getData().contains("Buildings No")) {
			BuildingNoLongerQuoteablePage buildingNoLongerQuotablePage = new BuildingNoLongerQuoteablePage();
			buildingNoLongerQuotablePage.overrideNoLongerQuotableBuildings();
		}

	}

}
