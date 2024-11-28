/** Program Description: Object Locators and methods defined in Dwelling page
 *  Author			   : SMNetserv
 *  Date of Creation   : 01/11/2017
 **/

package com.icat.epicenter.pom;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

public class DwellingPage extends BasePageControl {
	public TextFieldControl buildingName;

	public HyperLink dwellingDetailsLink;
	public TextFieldControl address;
	public HyperLink manualEntry;
	public TextFieldControl manualEntryAddress;
	public TextFieldControl manualEntryCity;
	public TextFieldControl manualEntryState;
	public TextFieldControl manualEntryZipCode;
	public HyperLink useAutocomplete;
	public HyperLink geocodeOverride;
	public TextFieldControl lattitude;
	public TextFieldControl longitude;
	public TextFieldControl propertyDescription;

	public ButtonControl dwellingTypeArrow;
	public ButtonControl dwellingTypeOption;
	public BaseWebElementControl dwellingTypeData;

	public ButtonControl constructionTypeArrow;
	public ButtonControl constructionTypeOption;
	public BaseWebElementControl constructionTypeData;

	public ButtonControl foundationTypeArrow;
	public ButtonControl foundationTypeOption;
	public BaseWebElementControl foundationTypeData;

	public ButtonControl OccupiedByArrow;
	public ButtonControl OccupiedByOption;
	public BaseWebElementControl occupiedByData;

	public TextFieldControl yearBuilt;
	public TextFieldControl livingSquareFootage;
	public TextFieldControl nonLivingSquareFootage;
	public TextFieldControl numOfFloors;

	public RadioButtonControl inspectionAvailable_yes;
	public RadioButtonControl inspectionAvailable_no;

	public HyperLink roofDetailsLink;
	public ButtonControl roofShapeArrow;
	public ButtonControl roofShapeOption;
	public BaseWebElementControl roofShapeData;
	public BaseWebElementControl roofShapeDataHIHO;
	public BaseWebElementControl roofShape;

	public TextFieldControl yearRoofLastReplaced;
	public BaseWebElementControl yearRoofLastReplacedBox;

	public ButtonControl windMitigationArrow;
	public ButtonControl WindMitigationOption;
	public ButtonControl WindMitigationOption1;
	public BaseWebElementControl WindMitigationData;

	public RadioButtonControl roofWallAttachment_No;
	public RadioButtonControl roofWallAttachment_Yes;

	public ButtonControl wallToFoundationAttachment_Arrow;
	public ButtonControl wallToFoundationAttachment_Option;
	public BaseWebElementControl wallToFoundationAttachmentData;

	public ButtonControl openingProtection_Arrow;
	public ButtonControl openingProtection_Option;
	public BaseWebElementControl openingProtectionData;

	public TextFieldControl refinanceYear;

	public HyperLink dwellingValuesLink;
	public TextFieldControl covANamedHurricane;
	public TextFieldControl covBNamedHurricane;
	public TextFieldControl covCNamedHurricane;
	public TextFieldControl covDNamedHurricane;

	public ButtonControl reviewDwelling;
	public HyperLink addSymbol;
	public HyperLink addNewLocation;
	public HyperLink addNewDwelling;
	public HyperLink copySymbol;
	public HyperLink deleteSymbol;
	public HyperLink addDwellingSymbol;
	public HyperLink editDwellingSymbol;
	public HyperLink copyDwellingSymbol;
	public HyperLink deleteDwellingSymbol;

	public HyperLink editBuilding;

	public ButtonControl createQuote;
	public ButtonControl leaveIncomplete;
	public ButtonControl reSubmit;
	public ButtonControl override;
	public ButtonControl leaveIneligible;
	public HyperLink closeSymbol;
	public BaseWebElementControl pageName;

	public HyperLink locationLink;
	public HyperLink dwellingLink;
	public HyperLink dwellingLink1;
	public HyperLink dwellingLink2;
	public HyperLink dwellingLink3;

	public ButtonControl newPurchase_FinanceArrow;
	public HyperLink newPurchase_FinanceOption;

	public HyperLink save;
	public HyperLink coveragesStep2;
	public BaseWebElementControl deleteBldgPopup;
	public HyperLink deleteBldgYes;
	public BaseWebElementControl deleteBldgNo;

	public HyperLink dwellingCharacteristicsLink;
	public ButtonControl continueButton;

	public BaseWebElementControl disabledDwellingType;
	public BaseWebElementControl disabledConstructionType;
	public BaseWebElementControl disabledRoofShape;
	public BaseWebElementControl disabledWindMitigation;

	// CA EQHO
	public RadioButtonControl StiltOrPostPier_Yes;
	public RadioButtonControl StiltOrPostPier_No;

	public TextFieldControl totalSquareFootage;
	public RadioButtonControl siesmicRetrofit_Yes;
	public RadioButtonControl siesmicRetrofit_No;
	public RadioButtonControl siesmicRetrofit_Unknown;

	public HyperLink geohazardDetailsLink;
	public HyperLink constructionDetailsLink;

	public ButtonControl soilTypeOverride_Arrow;
	public ButtonControl soilTypeOverride_Option;
	public ButtonControl soilTypeOverrideData;

	public ButtonControl liquefactionOverride_Arrow;
	public ButtonControl liquefactionOverride_Option;
	public ButtonControl liquefactionOverrideData;

	public TextFieldControl covAEQ;
	public TextFieldControl covBEQ;
	public TextFieldControl covCEQ;
	public TextFieldControl covDEQ;

	public BaseWebElementControl dwellingAddress1;
	public BaseWebElementControl dwellingYearBuilt;
	public BaseWebElementControl dwellingType;
	public BaseWebElementControl constructionType;
	public BaseWebElementControl dwellingStilts;
	public BaseWebElementControl dwellingFloors;
	public BaseWebElementControl dwellingTotalSquareFootage;
	public BaseWebElementControl dwellingcovAEQLimit;
	public BaseWebElementControl propertyDescriptionData;
	public BaseWebElementControl livingSqFootageData;
	public BaseWebElementControl nonLivingSqFootageData;
	public BaseWebElementControl inspectionData;
	public BaseWebElementControl roofShapeDetailsData;
	public BaseWebElementControl roofLastReplacedData;
	public BaseWebElementControl windMitigationData;

	public HyperLink retrofitLink;
	public ButtonControl retrofitPopUpCloseBtn;

	public HyperLink constructionHelpTextlink;
	public BaseWebElementControl constructionHelpText;
	public ButtonControl constructionHelpTextClose;

	public HyperLink stiltsHelpTextlink;
	public BaseWebElementControl stiltsHelpText;
	public ButtonControl stiltsHelpTextClose;

	public BaseWebElementControl warningMessage;

	public BaseWebElementControl covA;
	public BaseWebElementControl covB;
	public BaseWebElementControl covC;
	public BaseWebElementControl covD;

	public BaseWebElementControl quoteExpiredPopup;
	public CheckBoxControl uncheckDwelling;

	public BaseWebElementControl dollarSymbol;
	public BaseWebElementControl exclamatorySignDwellin1;
	public BaseWebElementControl dwellingTickSign;

	public BaseWebElementControl addedLocation;
	public BaseWebElementControl addedDwelling;
	public BaseWebElementControl addressChanged;
	public BaseWebElementControl emailChanged;
	public BaseWebElementControl contactChanged;

	public HyperLink deleteLoc;
	public ButtonControl quoteSomeDwellings;
	public BaseWebElementControl ineligibleWarning;

	public HyperLink accountDetailsLink;
	public TextFieldControl effectiveDate;
	public ButtonControl reviewBtn;

	public TextFieldControl locationName;
	public ButtonControl reviewLoc;
	public HyperLink floodLink;
	public BaseWebElementControl expiredQuotePopUp;
	public ButtonControl continueWithUpdateBtn;

	// Additional Elements for NAHO
	public RadioButtonControl hardiePlankNo;
	public RadioButtonControl hardiePlankYes;

	public TextFieldControl noOfUnits;

	public RadioButtonControl shortTermRentalNo;
	public RadioButtonControl shortTermRentalYes;

	public RadioButtonControl exoticAnimalNo;
	public RadioButtonControl exoticAnimalYes;

	public RadioButtonControl unfencedSwimmingPoolNo;
	public RadioButtonControl unfencedSwimmingPoolYes;

	public ButtonControl roofCladdingArrow;
	public ButtonControl roofCladdingOption;

	public TextFieldControl coverageADwelling;
	public TextFieldControl coverageBOtherStructures;
	public TextFieldControl coverageCPersonalProperty;
	public TextFieldControl coverageDFairRental;
	public ButtonControl coverageEArrow;
	public ButtonControl coverageEOption;
	public ButtonControl coverageFArrow;
	public ButtonControl coverageFOption;

	public HyperLink protectionDiscounts;
	public TextFieldControl yearPlumbingUpdated;
	public TextFieldControl yearElectricalUpdated;
	public TextFieldControl yearHVACUpdated;

	public ButtonControl centralStationAlarmArrow;
	public ButtonControl centralStationAlarmOption;

	public RadioButtonControl wholeSprinklerNo;
	public RadioButtonControl wholeSprinklerYes;

	public RadioButtonControl waterDetectionNo;
	public RadioButtonControl waterDetectionYes;

	public RadioButtonControl gatedCommunityNo;
	public RadioButtonControl gatedCommunityYes;

	public RadioButtonControl newPurchaseNo;
	public RadioButtonControl newPurchaseYes;

	public RadioButtonControl companionPolicyNo;
	public RadioButtonControl companionPolicyYes;

	public RadioButtonControl lapseInCoverageNo;
	public RadioButtonControl lapseInCoverageYes;

	public HyperLink internalSection;
	public TextFieldControl protectionClassOverride;
	public TextFieldControl distanceToCoastOverride;
	public BaseWebElementControl distanceToFireStationData;
	public BaseWebElementControl protectionClassData;
	public BaseWebElementControl distanceToCoastData;
	public BaseWebElementControl rMSLevelMatchData;
	public BaseWebElementControl countryData;
	public BaseWebElementControl microZoneData;
	public BaseWebElementControl latitudeData;
	public BaseWebElementControl longitudeData;
	public BaseWebElementControl elevationData;

	public BaseWebElementControl buildingSavedSuccessfully;

	public ButtonControl continueOverrideCost;

	public ButtonControl mapZoomInButton;
	public BaseWebElementControl protectionClassWarMsg;

	public BaseWebElementControl addressMsg;

	public BaseWebElementControl dwellingNoOfunits;
	public BaseWebElementControl dwellingNoOfstories;
	public ButtonControl primaryResidentArrowandData;
	public ButtonControl primaryResidentOption;
	public BaseWebElementControl dwellingDetailsErrorMessages;
	public BaseWebElementControl dwellingDetailsErrorMessages1;
	public BaseWebElementControl centralStationAlarmData;
	public ButtonControl homePageButton;
	public BaseWebElementControl niDisplay;
	public BaseWebElementControl cOCWarningMsg;
	public ButtonControl bringUptoCost;
	public BaseWebElementControl aopNolongerEligible;
	public BaseWebElementControl yearRoofLastReplacedlabel;
	public HyperLink dwelling1;
	public BaseWebElementControl dwelType;
	public BaseWebElementControl constType;
	public BaseWebElementControl windMitigation1;
	public BaseWebElementControl covAValue;
	public HyperLink location1;
	public BaseWebElementControl dwellingAddress;
	public BaseWebElementControl dwelling23;
	public BaseWebElementControl dwelling32;
	public BaseWebElementControl coverageA;
	public BaseWebElementControl coverageB;
	public BaseWebElementControl coverageC;
	public BaseWebElementControl coverageD;
	public BaseWebElementControl copyDwelling2;
	public BaseWebElementControl copyDwelling3;
	public CheckBoxControl uncheckDwelling2n3;
	public CheckBoxControl uncheckDwelling2n2;
	public HyperLink location3;
	public HyperLink location2;
	public BaseWebElementControl dwellingYearBuilt1;
	FloodPage floodPage;

	public int locNo = 1;
	public int bldgNo = 1;
	BuildingNoLongerQuoteablePage buildingNoLongerQuotablePage = new BuildingNoLongerQuoteablePage();

	public DwellingPage() {
		PageObject pageobject = new PageObject("Dwelling");
		buildingName = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Buildingname")));

		dwellingDetailsLink = new HyperLink(By.xpath(pageobject.getXpath("xp_DwellingDetailsLink")));
		address = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Address")));
		manualEntry = new HyperLink(By.xpath(pageobject.getXpath("xp_ManualEntry")));
		manualEntryAddress = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ManualEntryAddress")));
		manualEntryCity = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ManualEntryCity")));
		manualEntryState = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ManualEntryState")));
		manualEntryZipCode = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ManualEntryZipcode")));
		useAutocomplete = new HyperLink(By.xpath(pageobject.getXpath("xp_Autocomplete")));
		geocodeOverride = new HyperLink(By.xpath(pageobject.getXpath("xp_Geocode_Override")));
		lattitude = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Latitude")));
		longitude = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Longitude")));
		propertyDescription = new TextFieldControl(By.xpath(pageobject.getXpath("xp_PropertyDescription")));

		dwellingTypeArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_DwellingTypeArrow")));
		dwellingTypeOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_DwellingTypeOption")));
		dwellingTypeData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DwellingTypeData")));

		constructionTypeArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_constructionTypeArrow")));
		constructionTypeOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_constructionTypeOption")));
		constructionTypeData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ConstructionTypeData")));

		foundationTypeArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_FoundationTypeArrow")));
		foundationTypeOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_FoundationTypeOption")));
		foundationTypeData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_FoundationTypeData")));

		OccupiedByArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_OccupiedByArrow")));
		OccupiedByOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_OccupiedByOption")));
		occupiedByData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OccupiedByData")));

		yearBuilt = new TextFieldControl(By.xpath(pageobject.getXpath("xp_YearBuilt")));
		livingSquareFootage = new TextFieldControl(By.xpath(pageobject.getXpath("xp_LivingSquareFootage")));
		nonLivingSquareFootage = new TextFieldControl(By.xpath(pageobject.getXpath("xp_NonLivingSqFootage")));
		numOfFloors = new TextFieldControl(By.xpath(pageobject.getXpath("xp_NumberOfFloors")));

		inspectionAvailable_yes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_InspectionAvaibleYes")));
		inspectionAvailable_no = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_InspectionAvaibleNo")));

		roofDetailsLink = new HyperLink(By.xpath(pageobject.getXpath("xp_RoofDetailsLink")));
		roofShapeArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_RoofShapeArrow")));
		roofShapeOption = new HyperLink(By.xpath(pageobject.getXpath("xp_RoofShapeOption")));
		roofShapeData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RoofShapeData")));
		roofShapeDataHIHO = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RoofShapeDataHIHO")));
		roofShape = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RoofShape")));

		yearRoofLastReplaced = new TextFieldControl(By.xpath(pageobject.getXpath("xp_YearRoofLastReplaced")));
		yearRoofLastReplacedBox = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_YearRoofLastReplacedDialogbox")));

		windMitigationArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_WindMitigationArrow")));
		WindMitigationOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_WindMitigationOption")));
		WindMitigationOption1 = new ButtonControl(By.xpath(pageobject.getXpath("xp_WindMitigationOption1")));
		WindMitigationData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_WindMitigationData")));

		roofWallAttachment_No = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_RoofWallAttachmentNo")));
		roofWallAttachment_Yes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_RoofWallAttachmentYes")));

		wallToFoundationAttachment_Arrow = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_WallToFoundationAttachmentArrow")));
		wallToFoundationAttachment_Option = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_WallToFoundationAttachmentOption")));
		wallToFoundationAttachmentData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_WallToFoundationAttachmentData")));

		openingProtection_Arrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_OpeningProtectionArrow")));
		openingProtection_Option = new ButtonControl(By.xpath(pageobject.getXpath("xp_OpeningProtectionOption")));
		openingProtectionData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OpeningProtectionData")));

		refinanceYear = new TextFieldControl(By.xpath(pageobject.getXpath("xp_RefinanceYear")));

		dwellingValuesLink = new HyperLink(By.xpath(pageobject.getXpath("xp_DwellingValuesLink")));
		covANamedHurricane = new TextFieldControl(By.xpath(pageobject.getXpath("xp_CovA_NamedHurricane")));
		covBNamedHurricane = new TextFieldControl(By.xpath(pageobject.getXpath("xp_CovB_NamedHurricane")));
		covCNamedHurricane = new TextFieldControl(By.xpath(pageobject.getXpath("xp_CovC_NamedHurricane")));
		covDNamedHurricane = new TextFieldControl(By.xpath(pageobject.getXpath("xp_CovD_NamedHurricane")));

		reviewDwelling = new ButtonControl(By.xpath(pageobject.getXpath("xp_ReviewDwelling")));
		addSymbol = new HyperLink(By.xpath(pageobject.getXpath("xp_AddSymbol")));
		addNewLocation = new HyperLink(By.xpath(pageobject.getXpath("xp_AddNewLocation")));
		addNewDwelling = new HyperLink(By.xpath(pageobject.getXpath("xp_AddNewDwelling")));
		copySymbol = new HyperLink(By.xpath(pageobject.getXpath("xp_CopySymbol")));
		deleteSymbol = new HyperLink(By.xpath(pageobject.getXpath("xp_DeleteSymbol")));
		addDwellingSymbol = new HyperLink(By.xpath(pageobject.getXpath("xp_AddDwellingSymbol")));
		editDwellingSymbol = new HyperLink(By.xpath(pageobject.getXpath("xp_EditDwellingSymbol")));
		copyDwellingSymbol = new HyperLink(By.xpath(pageobject.getXpath("xp_CopyDwellingSymbol")));
		deleteDwellingSymbol = new HyperLink(By.xpath(pageobject.getXpath("xp_DeleteBuildingSymbol")));
		editBuilding = new HyperLink(By.xpath(pageobject.getXpath("xp_EditBuilding")));

		createQuote = new ButtonControl(By.xpath(pageobject.getXpath("xp_CreateQuote")));
		leaveIncomplete = new ButtonControl(By.xpath(pageobject.getXpath("xp_LeaveIncomplete")));
		reSubmit = new ButtonControl(By.xpath(pageobject.getXpath("xp_Resubmit")));
		override = new ButtonControl(By.xpath(pageobject.getXpath("xp_Override")));
		leaveIneligible = new ButtonControl(By.xpath(pageobject.getXpath("xp_LeaveIneligible")));
		closeSymbol = new HyperLink(By.xpath(pageobject.getXpath("xp_CloseSymbol")));
		pageName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Pagename")));

		locationLink = new HyperLink(By.xpath(pageobject.getXpath("xp_LocationLink")));
		dwellingLink = new HyperLink(By.xpath(pageobject.getXpath("xp_DwellingLink")));
		dwellingLink1 = new HyperLink(By.xpath(pageobject.getXpath("xp_DwellingLink1")));
		dwellingLink2 = new HyperLink(By.xpath(pageobject.getXpath("xp_DwellingLink2")));
		dwellingLink3 = new HyperLink(By.xpath(pageobject.getXpath("xp_DwellingLink3")));

		newPurchase_FinanceArrow = new HyperLink(By.xpath(pageobject.getXpath("xp_NewPurchaseRefinanceArrow")));
		newPurchase_FinanceOption = new HyperLink(By.xpath(pageobject.getXpath("xp_NewPurchaseRefinanceOption")));

		save = new HyperLink(By.xpath(pageobject.getXpath("xp_saveButton")));
		coveragesStep2 = new HyperLink(By.xpath(pageobject.getXpath("xp_coveragesStep2")));
		deleteBldgPopup = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DeleteBldgPopup")));
		deleteBldgYes = new HyperLink(By.xpath(pageobject.getXpath("xp_DeleteBldgYes")));
		deleteBldgNo = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DeleteBldgNo")));

		dwellingCharacteristicsLink = new HyperLink(By.xpath(pageobject.getXpath("xp_DwellingCharacteristicsLink")));
		continueButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ContinueButton")));

		disabledDwellingType = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DisabledDwellingType")));
		disabledConstructionType = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_DisabledConstructionType")));
		disabledRoofShape = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DisabledRoofShape")));
		disabledWindMitigation = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DisabledWindMitigation")));

		StiltOrPostPier_Yes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_StiltOrPostPierYes")));
		StiltOrPostPier_No = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_StiltOrPostPierNo")));

		totalSquareFootage = new TextFieldControl(By.xpath(pageobject.getXpath("xp_TotalSquareFootage")));
		siesmicRetrofit_Yes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_SiesmenYes")));
		siesmicRetrofit_No = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_SiesmenNo")));
		siesmicRetrofit_Unknown = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_SiesmenUnknown")));

		geohazardDetailsLink = new HyperLink(By.xpath(pageobject.getXpath("xp_ZeohazardLink")));
		constructionDetailsLink = new HyperLink(By.xpath(pageobject.getXpath("xp_ConstructionDetailsLink")));

		soilTypeOverride_Arrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_SoilTypeArrow")));
		soilTypeOverride_Option = new ButtonControl(By.xpath(pageobject.getXpath("xp_SoilTypeOption")));
		soilTypeOverrideData = new ButtonControl(By.xpath(pageobject.getXpath("xp_SoilTypeData")));

		liquefactionOverride_Arrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_LiquefactionArrow")));
		liquefactionOverride_Option = new ButtonControl(By.xpath(pageobject.getXpath("xp_LiquefactionOption")));
		liquefactionOverrideData = new ButtonControl(By.xpath(pageobject.getXpath("xp_LiquefactionData")));

		covAEQ = new TextFieldControl(By.xpath(pageobject.getXpath("xp_CovAEQ")));
		covBEQ = new TextFieldControl(By.xpath(pageobject.getXpath("xp_CovBEQ")));
		covCEQ = new TextFieldControl(By.xpath(pageobject.getXpath("xp_CovCEQ")));
		covDEQ = new TextFieldControl(By.xpath(pageobject.getXpath("xp_CovDEQ")));

		dwellingAddress1 = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DwellingAddress1")));
		dwellingYearBuilt = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DwellingYearBuilt")));
		dwellingType = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DwellingType")));
		constructionType = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DwellingConstructionType")));
		dwellingStilts = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DwellingStilts")));
		dwellingFloors = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DwellingNumberOfFloors")));
		dwellingTotalSquareFootage = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_DwellingtotalSquareFootage")));
		dwellingcovAEQLimit = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DwellingCovAlimit")));
		propertyDescriptionData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PropertyDescriptionData")));
		livingSqFootageData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LivingSqFootage")));
		nonLivingSqFootageData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NonLivingSqFootageData")));
		inspectionData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_InspectionData")));
		roofShapeDetailsData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RoofShapeData")));
		roofLastReplacedData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RoofLastReplacedData")));

		retrofitLink = new HyperLink(By.xpath(pageobject.getXpath("xp_RetrofitLink")));
		retrofitPopUpCloseBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_RetrofitPopUpCloseBtn")));

		constructionHelpTextlink = new HyperLink(By.xpath(pageobject.getXpath("xp_constructionHelpTextlink")));
		constructionHelpText = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_constructionHelpText")));
		constructionHelpTextClose = new ButtonControl(By.xpath(pageobject.getXpath("xp_constructionHelpTextClose")));

		stiltsHelpTextlink = new HyperLink(By.xpath(pageobject.getXpath("xp_stiltsHelpTextlink")));
		stiltsHelpText = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_stiltsHelpText")));
		stiltsHelpTextClose = new ButtonControl(By.xpath(pageobject.getXpath("xp_stiltsHelpTextClose")));

		warningMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_warningMessage")));

		covA = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_covA")));
		covB = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_covB")));
		covC = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_covC")));
		covD = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_covD")));

		quoteExpiredPopup = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_QuoteExpiredPopup")));
		uncheckDwelling = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_UncheckDwelling")));

		dollarSymbol = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DollarSymbol")));
		exclamatorySignDwellin1 = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_ExclamatorySignDwellin1")));
		dwellingTickSign = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DwellingTickSign")));

		addedLocation = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AddedLocation")));
		addedDwelling = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AddedDwelling")));
		addressChanged = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AddressChanged")));
		emailChanged = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EmailChanged")));
		contactChanged = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ContactChanged")));

		deleteLoc = new HyperLink(By.xpath(pageobject.getXpath("xp_DeleteLoc")));
		quoteSomeDwellings = new ButtonControl(By.xpath(pageobject.getXpath("xp_QuoteSomeDwellings")));
		ineligibleWarning = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_IneligibleWarning")));

		accountDetailsLink = new HyperLink(By.xpath(pageobject.getXpath("xp_AccountDetailsLink")));
		effectiveDate = new TextFieldControl(By.xpath(pageobject.getXpath("xp_EffectiveDate")));
		reviewBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_ReviewBtn")));

		locationName = new TextFieldControl(By.xpath(pageobject.getXpath("xp_LocationName")));
		reviewLoc = new ButtonControl(By.xpath(pageobject.getXpath("xp_ReviewLoc")));
		floodLink = new HyperLink(By.xpath(pageobject.getXpath("xp_FloodLink")));
		expiredQuotePopUp = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ExpiredQuotePopUp")));
		continueWithUpdateBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_ContinueWithUpdate")));

		// Additional Elements for NAHO
		hardiePlankNo = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_HardiePlankNo")));
		hardiePlankYes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_HardiePlankYes")));

		noOfUnits = new TextFieldControl(By.xpath(pageobject.getXpath("xp_NoOfUnits")));

		shortTermRentalNo = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_ShortTermRentalNo")));
		shortTermRentalYes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_ShortTermRentalYes")));

		exoticAnimalNo = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_ExoticAnimalNo")));
		exoticAnimalYes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_ExoticAnimalYes")));

		unfencedSwimmingPoolNo = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_UnfencedSwimmingPoolNo")));
		unfencedSwimmingPoolYes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_UnfencedSwimmingPoolYes")));

		roofCladdingArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_RoofCladdingArrow")));
		roofCladdingOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_RoofCladdingOption")));

		coverageADwelling = new TextFieldControl(By.xpath(pageobject.getXpath("xp_CoverageADwelling")));
		coverageBOtherStructures = new TextFieldControl(By.xpath(pageobject.getXpath("xp_CoverageBOtherStructures")));
		coverageCPersonalProperty = new TextFieldControl(By.xpath(pageobject.getXpath("xp_CoverageCPersonalProperty")));
		coverageDFairRental = new TextFieldControl(By.xpath(pageobject.getXpath("xp_CoverageDFairRental")));
		coverageEArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_CoverageEArrow")));
		coverageEOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_CoverageEOption")));
		coverageFArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_CoverageFArrow")));
		coverageFOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_CoverageFOption")));

		protectionDiscounts = new HyperLink(By.xpath(pageobject.getXpath("xp_ProtectionDiscounts")));
		yearPlumbingUpdated = new TextFieldControl(By.xpath(pageobject.getXpath("xp_YearPlumbingUpdated")));
		yearElectricalUpdated = new TextFieldControl(By.xpath(pageobject.getXpath("xp_YearElectricalUpdated")));
		yearHVACUpdated = new TextFieldControl(By.xpath(pageobject.getXpath("xp_YearHVACUpdated")));

		centralStationAlarmArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_CentralStationAlarmArrow")));
		centralStationAlarmOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_CentralStationAlarmOption")));

		wholeSprinklerNo = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_WholeSprinklerNo")));
		wholeSprinklerYes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_WholeSprinklerYes")));

		waterDetectionNo = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_WaterDetectionNo")));
		waterDetectionYes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_WaterDetectionYes")));

		gatedCommunityNo = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_GatedCommunityNo")));
		gatedCommunityYes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_GatedCommunityYes")));

		newPurchaseNo = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_NewPurchaseNo")));
		newPurchaseYes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_NewPurchaseYes")));

		companionPolicyNo = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_CompanionPolicyNo")));
		companionPolicyYes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_CompanionPolicyYes")));

		lapseInCoverageNo = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_LapseInCoverageNo")));
		lapseInCoverageYes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_LapseInCoverageYes")));

		internalSection = new HyperLink(By.xpath(pageobject.getXpath("xp_InternalSection")));
		protectionClassOverride = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ProtectionClassOverride")));
		distanceToCoastOverride = new TextFieldControl(By.xpath(pageobject.getXpath("xp_DistanceToCoastOverride")));
		distanceToFireStationData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_DistanceToFireStationData")));
		protectionClassData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ProtectionClassData")));
		distanceToCoastData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DistanceToCoastData")));
		rMSLevelMatchData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RMSLevelMatchData")));
		countryData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CountryData")));
		microZoneData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_MicroZoneData")));
		latitudeData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LatitudeData")));
		longitudeData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LongitudeData")));
		elevationData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ElevationData")));

		buildingSavedSuccessfully = new BaseWebElementControl(
				By.xpath("//div[contains(text(),'Your building was successfully saved')]"));

		continueOverrideCost = new ButtonControl(By.xpath(pageobject.getXpath("xp_ContinueCostOverride")));
		mapZoomInButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_MapZoomInButton")));
		protectionClassWarMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ProtectionClassWarMsg")));

		addressMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AddressMsg")));
		dwellingNoOfunits = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DwellingNoOfUnits")));
		dwellingNoOfstories = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DwellingNoOfStories")));
		primaryResidentArrowandData = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_PrimaryResidentArrowandData")));
		primaryResidentOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_PrimaryResidentOption")));
		dwellingDetailsErrorMessages = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_DwellingDetailsErrorMessages")));
		dwellingDetailsErrorMessages1 = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_DwellingDetailsErrorMessages1")));
		centralStationAlarmData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_CentralStationAlarmData")));
		homePageButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_HomePageButton")));
		niDisplay = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NIDisplay")));
		cOCWarningMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_COCWarningMsg")));
		bringUptoCost = new ButtonControl(By.xpath(pageobject.getXpath("xp_BringUptoCost")));
		aopNolongerEligible = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AopNolongerEligible")));
		yearRoofLastReplacedlabel = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_YearRoofLastReplacedLabel")));
		dwelling1 = new HyperLink(By.xpath(pageobject.getXpath("xp_Dwelling1")));
		dwelType = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DwelType")));
		constType = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ConstType")));
		windMitigation1 = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_WindMitigation")));
		covAValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CovAValue")));
		location1 = new HyperLink(By.xpath(pageobject.getXpath("xp_Location1")));
		dwellingAddress = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DwellingAddress")));
		dwelling23 = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Dwelling23")));
		dwelling32 = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Dwelling32")));
		coverageA = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CovAValue")));
		coverageB = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CovBValue")));
		coverageC = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CovCValue")));
		coverageD = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CovDValue")));
		copyDwelling2 = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CopyDwelling2")));
		copyDwelling3 = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CopyDwelling3")));
		uncheckDwelling2n3 = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_UncheckDwelling2n3")));
		uncheckDwelling2n2 = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_UncheckDwelling2n2")));
		location3 = new HyperLink(By.xpath(pageobject.getXpath("xp_Location3")));
		location2 = new HyperLink(By.xpath(pageobject.getXpath("xp_Location2")));
		dwellingYearBuilt1 = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DwellingYearBuilt1")));
	}

	public void addDwellingDetails(Map<String, String> Data, int locNo, int bldgNo) {
		if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingAddress").equals("")) {
			manualEntry.waitTillVisibilityOfElement(60);
			manualEntry.scrollToElement();
			manualEntry.click();
			manualEntryAddress.clearData();
			if (expiredQuotePopUp.checkIfElementIsPresent()) {
				expiredQuotePopUp.scrollToElement();
				continueWithUpdateBtn.scrollToElement();
				continueWithUpdateBtn.click();
				continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}
			manualEntryAddress.setData(Data.get("L" + locNo + "D" + bldgNo + "-DwellingAddress"));
			if (expiredQuotePopUp.checkIfElementIsPresent()) {
				expiredQuotePopUp.scrollToElement();
				continueWithUpdateBtn.scrollToElement();
				continueWithUpdateBtn.click();
				continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}
			manualEntryAddress.tab();
			if (expiredQuotePopUp.checkIfElementIsPresent()) {
				expiredQuotePopUp.scrollToElement();
				continueWithUpdateBtn.scrollToElement();
				continueWithUpdateBtn.click();
				continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}
			manualEntryCity.setData(Data.get("L" + locNo + "D" + bldgNo + "-DwellingCity"));
			manualEntryCity.tab();

			manualEntryZipCode.setData(Data.get("L" + locNo + "D" + bldgNo + "-DwellingZIP"));
			propertyDescription.setData(Data.get("L" + locNo + "D" + bldgNo + "-DwellingDesc"));
		}
		if (dwellingTypeArrow.checkIfElementIsPresent() && dwellingTypeArrow.checkIfElementIsDisplayed()) {
			if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingType").equals("")) {
				dwellingTypeArrow.waitTillVisibilityOfElement(60);
				dwellingTypeArrow.scrollToElement();
				dwellingTypeArrow.click();
				dwellingTypeOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingType")).click();
			}
		}
		if (dwellingCharacteristicsLink.checkIfElementIsPresent()
				&& dwellingCharacteristicsLink.checkIfElementIsDisplayed()) {
			dwellingCharacteristicsLink.scrollToElement();
			dwellingCharacteristicsLink.click();
		}
		if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingConstType").equals("")) {

			if (expiredQuotePopUp.checkIfElementIsPresent()) {
				expiredQuotePopUp.scrollToElement();
				continueWithUpdateBtn.scrollToElement();
				continueWithUpdateBtn.click();
				continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}

			constructionTypeArrow.waitTillVisibilityOfElement(60);
			constructionTypeArrow.scrollToElement();
			constructionTypeArrow.click();
			constructionTypeOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingConstType"))
					.waitTillVisibilityOfElement(60);
			constructionTypeOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingConstType"))
					.scrollToElement();
			constructionTypeOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingConstType"))
					.click();
		}

		if (hardiePlankYes.checkIfElementIsPresent() && hardiePlankYes.checkIfElementIsDisplayed()) {
			if (expiredQuotePopUp.checkIfElementIsPresent()) {
				expiredQuotePopUp.scrollToElement();
				continueWithUpdateBtn.scrollToElement();
				continueWithUpdateBtn.click();
				continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}
			if (!Data.get("L" + locNo + "D" + bldgNo + "-HardiePlankSiding").equals("")) {
				if (Data.get("L" + locNo + "D" + bldgNo + "-HardiePlankSiding").equalsIgnoreCase("Yes")) {
					hardiePlankYes.scrollToElement();
					hardiePlankYes.click();
				} else if (Data.get("L" + locNo + "D" + bldgNo + "-HardiePlankSiding").equalsIgnoreCase("No")) {
					hardiePlankNo.scrollToElement();
					hardiePlankNo.click();
				}
			}
		}

		if (noOfUnits.checkIfElementIsPresent() && noOfUnits.checkIfElementIsDisplayed()) {
			if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingUnits").equals("")) {
				noOfUnits.scrollToElement();
				noOfUnits.setData(Data.get("L" + locNo + "D" + bldgNo + "-DwellingUnits"));
			}
		}

		if (numOfFloors.checkIfElementIsPresent() && numOfFloors.checkIfElementIsDisplayed()) {
			if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingFloors").equals("")) {
				numOfFloors.setData(Data.get("L" + locNo + "D" + bldgNo + "-DwellingFloors"));
			}
		}

		if (totalSquareFootage.checkIfElementIsPresent() && totalSquareFootage.checkIfElementIsDisplayed()) {
			if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingSqFoot").equals("")) {
				totalSquareFootage.setData(Data.get("L" + locNo + "D" + bldgNo + "-DwellingSqFoot"));
			}
		}

		if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingYearBuilt").equals("")) {
			yearBuilt.scrollToElement();
			yearBuilt.setData(Data.get("L" + locNo + "D" + bldgNo + "-DwellingYearBuilt"));
			yearBuilt.tab();
		}

		if (foundationTypeArrow.checkIfElementIsPresent()
				&& !Data.get("L" + locNo + "D" + bldgNo + "-FoundationType").equals("")) {
			foundationTypeArrow.waitTillVisibilityOfElement(60);
			foundationTypeArrow.scrollToElement();
			foundationTypeArrow.click();
			foundationTypeOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-FoundationType"))
					.scrollToElement();
			foundationTypeOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-FoundationType")).click();
		}
		if (OccupiedByArrow.checkIfElementIsPresent()
				&& !Data.get("L" + locNo + "D" + bldgNo + "-OccupiedBy").equals("")) {
			OccupiedByArrow.waitTillVisibilityOfElement(60);
			OccupiedByArrow.scrollToElement();
			OccupiedByArrow.click();
			OccupiedByOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-OccupiedBy")).scrollToElement();
			OccupiedByOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-OccupiedBy")).click();
		}
		// Added to select Primary resident CA or VT 10/03/21
		if (primaryResidentArrowandData.checkIfElementIsPresent()
				&& primaryResidentArrowandData.checkIfElementIsDisplayed()) {
			if (!Data.get("L1D1-PrimaryResident").equals("")) {
				primaryResidentArrowandData.waitTillVisibilityOfElement(60);
				primaryResidentArrowandData.scrollToElement();
				primaryResidentArrowandData.click();
				primaryResidentOption.formatDynamicPath(Data.get("L1D1-PrimaryResident")).scrollToElement();
				primaryResidentOption.formatDynamicPath(Data.get("L1D1-PrimaryResident")).click();
			}
		}
		if (shortTermRentalYes.checkIfElementIsPresent() && shortTermRentalYes.checkIfElementIsDisplayed()) {
			if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingShortTermRental").equals("")) {
				if (Data.get("L" + locNo + "D" + bldgNo + "-DwellingShortTermRental").equals("Yes")) {
					shortTermRentalYes.scrollToElement();
					shortTermRentalYes.click();
				} else if (Data.get("L" + locNo + "D" + bldgNo + "-DwellingShortTermRental").equals("No")) {
					shortTermRentalNo.scrollToElement();
					shortTermRentalNo.click();
				}
			}
		}

		if (exoticAnimalYes.checkIfElementIsPresent() && exoticAnimalYes.checkIfElementIsDisplayed()) {
			if (!Data.get("L" + locNo + "D" + bldgNo + "-ExoticAnimals").equals("")) {
				if (Data.get("L" + locNo + "D" + bldgNo + "-ExoticAnimals").equals("Yes")) {
					exoticAnimalYes.scrollToElement();
					exoticAnimalYes.click();
				} else if (Data.get("L" + locNo + "D" + bldgNo + "-ExoticAnimals").equals("No")) {
					exoticAnimalNo.scrollToElement();
					exoticAnimalNo.click();
				}
			}
		}

		if (unfencedSwimmingPoolYes.checkIfElementIsPresent() && unfencedSwimmingPoolYes.checkIfElementIsDisplayed()) {
			if (!Data.get("L" + locNo + "D" + bldgNo + "-UnfencedSwimmingPool").equals("")) {
				if (Data.get("L" + locNo + "D" + bldgNo + "-UnfencedSwimmingPool").equals("Yes")) {
					unfencedSwimmingPoolYes.scrollToElement();
					unfencedSwimmingPoolYes.click();
				} else if (Data.get("L" + locNo + "D" + bldgNo + "-UnfencedSwimmingPool").equals("No")) {
					unfencedSwimmingPoolNo.scrollToElement();
					unfencedSwimmingPoolNo.click();
				}
			}

		}

		if (livingSquareFootage.checkIfElementIsPresent() && livingSquareFootage.checkIfElementIsDisplayed()) {
			if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingSqFoot").equals("")) {
				livingSquareFootage.scrollToElement();
				livingSquareFootage.setData(Data.get("L" + locNo + "D" + bldgNo + "-DwellingSqFoot"));
			}
		}

		if (nonLivingSquareFootage.checkIfElementIsPresent() && nonLivingSquareFootage.checkIfElementIsDisplayed()) {
			if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingNonLivingSqFoot").equals("")) {
				nonLivingSquareFootage.scrollToElement();
				nonLivingSquareFootage.setData(Data.get("L" + locNo + "D" + bldgNo + "-DwellingNonLivingSqFoot"));
			}

		}
		if (inspectionAvailable_yes.checkIfElementIsPresent() && inspectionAvailable_yes.checkIfElementIsDisplayed()) {
			if (!Data.get("L" + locNo + "D" + bldgNo + "-InspfromPrimaryCarrier").equals("")) {
				if (Data.get("L" + locNo + "D" + bldgNo + "-InspfromPrimaryCarrier").equalsIgnoreCase("yes")) {
					inspectionAvailable_yes.scrollToElement();
					inspectionAvailable_yes.click();
				} else {
					inspectionAvailable_no.scrollToElement();
					inspectionAvailable_no.click();
				}
			}
		}
		if (lapseInCoverageYes.checkIfElementIsPresent() && lapseInCoverageYes.checkIfElementIsDisplayed()) {
			if (Data.get("L" + locNo + "D" + bldgNo + "-LapseInCoverage").equalsIgnoreCase("Yes")) {
				lapseInCoverageYes.scrollToElement();
				lapseInCoverageYes.click();
			} else if (Data.get("L" + locNo + "D" + bldgNo + "-LapseInCoverage").equalsIgnoreCase("No")) {
				lapseInCoverageNo.scrollToElement();
				lapseInCoverageNo.click();
			}
		}
	}

	public void addRoofDetails(Map<String, String> Data, int locNo, int bldgNo) {
		WebDriverWait wait = new WebDriverWait(WebDriverManager.getCurrentDriver(), Duration.ofSeconds(5));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Roof Details')]")));
		roofDetailsLink.waitTillElementisEnabled(60);
		roofDetailsLink.waitTillVisibilityOfElement(60);
		roofDetailsLink.scrollToElement();
		roofDetailsLink.click();
		mapZoomInButton.waitTillVisibilityOfElement(60);

		if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingRoofShape").equals("")) {
			roofShapeArrow.waitTillPresenceOfElement(60);
			roofShapeArrow.waitTillVisibilityOfElement(60);
			roofShapeArrow.waitTillElementisEnabled(60);
			roofShapeArrow.waitTillButtonIsClickable(60);
			roofShapeArrow.scrollToElement();
			roofShapeArrow.click();
			roofShapeOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingRoofShape"))
					.waitTillPresenceOfElement(60);
			roofShapeOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingRoofShape"))
					.waitTillVisibilityOfElement(60);
			roofShapeOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingRoofShape"))
					.waitTillElementisEnabled(60);
			roofShapeOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingRoofShape"))
					.waitTillButtonIsClickable(60);
			roofShapeOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingRoofShape"))
					.scrollToElement();
			roofShapeOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingRoofShape")).click();
		}
		if (!StringUtils.isBlank(Data.get("L" + locNo + "D" + bldgNo + "-DwellingRoofCladding"))) {
			roofCladdingArrow.waitTillPresenceOfElement(60);
			roofCladdingArrow.waitTillVisibilityOfElement(60);
			roofCladdingArrow.waitTillButtonIsClickable(60);
			roofCladdingArrow.scrollToElement();
			waitTime(3);
			roofCladdingArrow.click();
			roofCladdingOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingRoofCladding"))
					.waitTillVisibilityOfElement(60);
			roofCladdingOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingRoofCladding"))
					.waitTillPresenceOfElement(60);
			roofCladdingOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingRoofCladding"))
					.waitTillButtonIsClickable(60);
			roofCladdingOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingRoofCladding"))
					.scrollToElement();
			roofCladdingOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingRoofCladding"))
					.click();
		}
		if (yearRoofLastReplaced.checkIfElementIsPresent() && yearRoofLastReplaced.checkIfElementIsDisplayed()) {
			if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingRoofReplacedYear").equals("")) {
				yearRoofLastReplaced.scrollToElement();
				yearRoofLastReplaced.setData(Data.get("L" + locNo + "D" + bldgNo + "-DwellingRoofReplacedYear"));
				if (roofCladdingArrow.checkIfElementIsPresent() && roofCladdingArrow.checkIfElementIsDisplayed()) {
					roofCladdingArrow.click();
				} else {
					yearRoofLastReplaced.tab();
				}
				yearRoofLastReplacedBox.waitTillVisibilityOfElement(60);
			}
		}
		if (refinanceYear.checkIfElementIsPresent() && refinanceYear.checkIfElementIsDisplayed()) {
			if (!Data.get("L" + locNo + "D" + bldgNo + "-RefinanceYear").equals("")) {
				refinanceYear.scrollToElement();
				refinanceYear.setData(Data.get("L" + locNo + "D" + bldgNo + "-RefinanceYear"));
			}
		}
		if (roofWallAttachment_Yes.checkIfElementIsPresent() && roofWallAttachment_Yes.checkIfElementIsDisplayed()) {
			if (Data.get("L" + locNo + "D" + bldgNo + "-DwellingRoofWallAttach").equalsIgnoreCase("yes")) {
				roofWallAttachment_Yes.scrollToElement();
				roofWallAttachment_Yes.click();
			} else {
				roofWallAttachment_No.waitTillVisibilityOfElement(60);
				roofWallAttachment_No.scrollToElement();
				roofWallAttachment_No.click();
			}
		}
		if (wallToFoundationAttachment_Arrow.checkIfElementIsPresent()
				&& wallToFoundationAttachment_Arrow.checkIfElementIsDisplayed()) {
			wallToFoundationAttachment_Arrow.waitTillVisibilityOfElement(60);
			wallToFoundationAttachment_Arrow.scrollToElement();
			wallToFoundationAttachment_Arrow.click();
			wallToFoundationAttachment_Option
					.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingWallFoundationAttach")).click();
		}
		if (openingProtection_Arrow.checkIfElementIsPresent() && openingProtection_Arrow.checkIfElementIsDisplayed()) {
			openingProtection_Arrow.waitTillVisibilityOfElement(60);
			openingProtection_Arrow.scrollToElement();
			openingProtection_Arrow.click();
			openingProtection_Option
					.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingOpeningProtection")).click();
		}
		if (newPurchase_FinanceArrow.checkIfElementIsPresent()
				&& newPurchase_FinanceArrow.checkIfElementIsDisplayed()) {
			newPurchase_FinanceArrow.waitTillVisibilityOfElement(60);
			newPurchase_FinanceArrow.scrollToElement();
			newPurchase_FinanceArrow.click();
			newPurchase_FinanceOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-NewPurchase")).click();
		}

		if (!Data.get("ProductSelection").equalsIgnoreCase("Residential Non-Admitted")) {
			if (windMitigationArrow.checkIfElementIsPresent() && windMitigationArrow.checkIfElementIsDisplayed()) {
				windMitigationArrow.waitTillPresenceOfElement(60);
				windMitigationArrow.waitTillVisibilityOfElement(60);
				windMitigationArrow.waitTillButtonIsClickable(60);
				windMitigationArrow.moveToElement();
				waitTime(3);
				windMitigationArrow.click();
				WindMitigationOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingWindMitigation"))
						.waitTillVisibilityOfElement(60);
				WindMitigationOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingWindMitigation"))
						.click();
			}
		}
	}

	public void enterDwellingValues(Map<String, String> Data, int locNo, int bldgNo) {
		dwellingValuesLink.waitTillVisibilityOfElement(60);
		dwellingValuesLink.scrollToElement();
		dwellingValuesLink.click();
		if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingCovA").equals("")) {
			covANamedHurricane.clearData();
			covANamedHurricane.scrollToElement();
			covANamedHurricane.setData(Data.get("L" + locNo + "D" + bldgNo + "-DwellingCovA"));
		}
		if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingCovB").equals("")) {
			covBNamedHurricane.clearData();
			covBNamedHurricane.scrollToElement();
			covBNamedHurricane.setData(Data.get("L" + locNo + "D" + bldgNo + "-DwellingCovB"));
		}
		if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingCovC").equals("")) {
			covCNamedHurricane.clearData();
			covCNamedHurricane.scrollToElement();
			covCNamedHurricane.setData(Data.get("L" + locNo + "D" + bldgNo + "-DwellingCovC"));
		}
		if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingCovD").equals("")) {
			covDNamedHurricane.clearData();
			covDNamedHurricane.scrollToElement();
			covDNamedHurricane.setData(Data.get("L" + locNo + "D" + bldgNo + "-DwellingCovD"));
		}
	}

	public void reviewDwelling() {
		reviewDwelling.waitTillPresenceOfElement(60);
		reviewDwelling.waitTillVisibilityOfElement(60);
		waitTime(2);
		reviewDwelling.scrollToElement();
		reviewDwelling.click();
		if (override.checkIfElementIsPresent() && override.checkIfElementIsDisplayed()) {
			override.scrollToElement();
			waitTime(2);
			override.click();
		}
		ExistingAccountPage existingAccountPage = new ExistingAccountPage();
		if (pageName.getData().contains("Existing Account Found"))
			existingAccountPage.OverrideExistingAccount();
		BuildingUnderMinimumCostPage buildingUnderminimumCost = new BuildingUnderMinimumCostPage();
		if (pageName.getData().contains("Dwelling Under"))
			buildingUnderminimumCost.clickOnOverride();

		if (continueOverrideCost.checkIfElementIsPresent() && continueOverrideCost.checkIfElementIsDisplayed()) {
			buildingUnderminimumCost.bringUpToCost.scrollToElement();
			buildingUnderminimumCost.bringUpToCost.click();
		}
	}

	public CreateQuotePage enterDwellingDetails(Map<String, String> Data) {
		// FloodPage floodPage = new FloodPage();
		String locationNumber = Data.get("LocCount");
		int locationCount = Integer.parseInt(locationNumber);
		for (int locNo = 1; locNo <= locationCount; locNo++) {
			locationLink.formatDynamicPath(locNo).waitTillButtonIsClickable(60);
			locationLink.formatDynamicPath(locNo).scrollToElement();
			locationLink.formatDynamicPath(locNo).click();
			String dwellingNumber = Data.get("L" + locNo + "-DwellingCount");
			int dwellingCount = Integer.parseInt(dwellingNumber);
			for (int bldgNo = 1; bldgNo <= dwellingCount; bldgNo++) {
				if (dwellingLink.formatDynamicPath(locNo, bldgNo).checkIfElementIsPresent()) {
					dwellingLink.formatDynamicPath(locNo, bldgNo).click();
				} else {
					addSymbol.scrollToElement();
					addSymbol.click();
					addNewDwelling.click();
				}
				addDwellingDetails(Data, locNo, bldgNo);
				addRoofDetails(Data, locNo, bldgNo);
				enterDwellingValues(Data, locNo, bldgNo);
				reviewDwelling();
			}
		}
		/*
		 * if (Data.get("Flood").equalsIgnoreCase("yes")) {
		 * floodPage.floodLink.waitTillVisibilityOfElement(60);
		 * floodPage.floodLink.click(); floodPage.enterFloodDetails(Data); }
		 */
		createQuote.scrollToElement();
		createQuote.click();
		BuildingNoLongerQuoteablePage buildingNoLongerQuotablePage = new BuildingNoLongerQuoteablePage();
		if (pageName.getData().contains("Buildings No")) {
			buildingNoLongerQuotablePage.overrideNoLongerQuotableBuildings();
		}
		if (pageName.getData().contains("Create")) {
			return new CreateQuotePage();
		}
		return null;
	}

	// Secondary Transactions
	public void editDwellingDetails(Map<String, String> Data, int locNo, int bldgNo) {
		if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingAddress").equals("")) {
			manualEntry.click();
			Assertions.addInfo("Dwelling Address1 original Value : " + manualEntryAddress.getData().replace(",", ""),
					"");
			manualEntryAddress.setData(Data.get("L" + locNo + "D" + bldgNo + "-DwellingAddress"));
			Assertions.passTest("Dwelling Page",
					"Dwelling Address1 Latest Value : " + manualEntryAddress.getData().replace(",", ""));
			Assertions.addInfo("Dwelling City original Value : " + manualEntryCity.getData(), "");
			manualEntryCity.setData(Data.get("L" + locNo + "D" + bldgNo + "-DwellingCity"));
			Assertions.passTest("Dwelling Page", "Dwelling City Latest Value : " + manualEntryCity.getData());
			Assertions.addInfo("Dwelling Zipcode original Value : " + manualEntryZipCode.getData(), "");
			manualEntryZipCode.setData(Data.get("L" + locNo + "D" + bldgNo + "-DwellingZIP"));
			Assertions.passTest("Dwelling Page", "Dwelling Zipcode Latest Value : " + manualEntryZipCode.getData());
			Assertions.addInfo("Dwelling Description original Value : " + propertyDescription.getData(), "");
			propertyDescription.setData(Data.get("L" + locNo + "D" + bldgNo + "-DwellingDesc"));
			Assertions.passTest("Dwelling Page",
					"Dwelling Description Latest Value : " + propertyDescription.getData());
		}
		if (dwellingTypeArrow.checkIfElementIsPresent() && dwellingTypeArrow.checkIfElementIsDisplayed()) {
			if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingType").equals("")) {
				dwellingTypeData.scrollToElement();
				Assertions.addInfo("Dwelling Type original Value : " + dwellingTypeData.getData(), "");
				dwellingTypeArrow.waitTillVisibilityOfElement(60);
				dwellingTypeArrow.scrollToElement();
				dwellingTypeArrow.click();
				dwellingTypeOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingType")).click();
				Assertions.passTest("Dwelling Page", "Dwelling Type Latest Value : " + dwellingTypeData.getData());
			}
		}

	}

	public void editDwellingCharacteristics(Map<String, String> Data, int locNo, int bldgNo) {
		dwellingCharacteristicsLink.scrollToElement();
		dwellingCharacteristicsLink.click();

		if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingConstType").equals("")) {
			constructionTypeData.waitTillPresenceOfElement(60);
			constructionTypeData.waitTillVisibilityOfElement(60);
			constructionTypeData.scrollToElement();
			Assertions.addInfo("Dwelling Construction Type original Value : " + constructionTypeData.getData(), "");
			constructionTypeArrow.waitTillPresenceOfElement(60);
			constructionTypeArrow.waitTillButtonIsClickable(60);
			constructionTypeArrow.waitTillVisibilityOfElement(60);
			constructionTypeArrow.scrollToElement();
			constructionTypeArrow.click();
			constructionTypeOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingConstType"))
					.scrollToElement();
			constructionTypeOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingConstType"))
					.click();
			Assertions.passTest("Dwelling Page",
					"Dwelling Construction Type Latest Value : " + constructionTypeData.getData());
		}
		if (foundationTypeArrow.checkIfElementIsPresent()
				&& !Data.get("L" + locNo + "D" + bldgNo + "-FoundationType").equals("")) {
			foundationTypeData.scrollToElement();
			Assertions.addInfo("Dwelling Foundation Type original Value : " + foundationTypeData.getData(), "");
			foundationTypeArrow.waitTillVisibilityOfElement(60);
			foundationTypeArrow.scrollToElement();
			foundationTypeArrow.click();
			foundationTypeOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-FoundationType"))
					.scrollToElement();
			foundationTypeOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-FoundationType")).click();
			Assertions.passTest("Dwelling Page",
					"Dwelling Foundation Type Latest Value : " + foundationTypeData.getData());
		}
		if (OccupiedByArrow.checkIfElementIsPresent()
				&& !Data.get("L" + locNo + "D" + bldgNo + "-OccupiedBy").equals("")) {
			occupiedByData.scrollToElement();
			Assertions.addInfo("Dwelling Occupied by original Value : " + occupiedByData.getData(), "");
			OccupiedByArrow.waitTillVisibilityOfElement(60);
			OccupiedByArrow.scrollToElement();
			OccupiedByArrow.click();
			OccupiedByOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-OccupiedBy")).scrollToElement();
			OccupiedByOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-OccupiedBy")).click();
			Assertions.passTest("Dwelling Page", "Dwelling Occupied by Latest Value : " + occupiedByData.getData());
		}
		if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingYearBuilt").equals("")) {
			Assertions.addInfo("Dwelling year built original Value : " + yearBuilt.getData(), "");
			yearBuilt.setData(Data.get("L" + locNo + "D" + bldgNo + "-DwellingYearBuilt"));
			Assertions.passTest("Dwelling Page", "Dwelling year built Latest Value : " + yearBuilt.getData());
		}
		if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingSqFoot").equals("")) {
			Assertions.addInfo("Dwelling living Square Footage original Value : " + livingSquareFootage.getData(), "");
			livingSquareFootage.setData(Data.get("L" + locNo + "D" + bldgNo + "-DwellingSqFoot"));
			Assertions.passTest("Dwelling Page",
					"Dwelling living Square Footage Latest Value : " + livingSquareFootage.getData());
		}
		if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingNonLivingSqFoot").equals("")) {
			Assertions.addInfo(
					"Dwelling Non living Square Footage original Value : " + nonLivingSquareFootage.getData(), "");
			nonLivingSquareFootage.setData(Data.get("L" + locNo + "D" + bldgNo + "-DwellingNonLivingSqFoot"));
			Assertions.passTest("Dwelling Page",
					"Dwelling Non living Square Footage Latest Value : " + nonLivingSquareFootage.getData());
		}
		if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingFloors").equals("")) {
			Assertions.addInfo("Dwelling Floors original Value : " + numOfFloors.getData(), "");
			numOfFloors.setData(Data.get("L" + locNo + "D" + bldgNo + "-DwellingFloors"));
			Assertions.passTest("Dwelling Page", "Dwelling Floors Latest Value : " + numOfFloors.getData());
		}
		if (!Data.get("L" + locNo + "D" + bldgNo + "-InspfromPrimaryCarrier").equalsIgnoreCase("")) {
			if (inspectionAvailable_yes.checkIfElementIsSelected()) {
				Assertions.addInfo("Inspection from primary Carrier original Value : " + "Yes", "");
			}
			if (inspectionAvailable_no.checkIfElementIsSelected()) {
				Assertions.addInfo("Inspection from primary Carrier original Value : " + "No", "");
			}
			if (Data.get("L" + locNo + "D" + bldgNo + "-InspfromPrimaryCarrier").equalsIgnoreCase("yes")) {
				inspectionAvailable_yes.scrollToElement();
				inspectionAvailable_yes.click();
				Assertions.passTest("Dwelling Page", "Inspection from primary Carrier Latest Value : " + "Yes");
			} else {
				inspectionAvailable_no.scrollToElement();
				inspectionAvailable_no.click();
				Assertions.passTest("Dwelling Page", "Inspection from primary Carrier Latest Value : " + "No");
			}
		}
	}

	public void addRoofDetailsforPNB(Map<String, String> Data, int locNo, int bldgNo) {
		WebDriverWait wait = new WebDriverWait(WebDriverManager.getCurrentDriver(), Duration.ofSeconds(5));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Roof Details')]")));
		roofDetailsLink.waitTillElementisEnabled(60);
		roofDetailsLink.waitTillVisibilityOfElement(60);
		roofDetailsLink.scrollToElement();
		roofDetailsLink.click();

		if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingRoofShape").equals("")) {
			roofShapeArrow.waitTillButtonIsClickable(60);
			roofShapeArrow.waitTillVisibilityOfElement(60);
			roofShapeArrow.scrollToElement();
			roofShapeArrow.moveToElement();
			roofShapeArrow.click();
			roofShapeOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingRoofShape"))
					.waitTillVisibilityOfElement(60);
			roofShapeOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingRoofShape")).click();
		}
		if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingRoofReplacedYear").equals("")) {
			yearRoofLastReplaced.scrollToElement();
			Assertions.addInfo("Roof last replaced year original Value : " + yearRoofLastReplaced.getData(), "");
			yearRoofLastReplaced.setData(Data.get("L" + locNo + "D" + bldgNo + "-DwellingRoofReplacedYear"));
			Assertions.passTest("Dwelling Page",
					"Roof last replaced year Latest Value : " + yearRoofLastReplaced.getData());
			yearRoofLastReplaced.tab();
			yearRoofLastReplacedBox.waitTillVisibilityOfElement(60);
		}
		if (refinanceYear.checkIfElementIsPresent()
				&& !Data.get("L" + locNo + "D" + bldgNo + "-RefinanceYear").equalsIgnoreCase("")) {
			refinanceYear.scrollToElement();
			Assertions.addInfo("Refinance Year original Value : " + refinanceYear.getData(), "");
			refinanceYear.setData(Data.get("L" + locNo + "D" + bldgNo + "-RefinanceYear"));
			Assertions.passTest("Dwelling Page", "Refinance year Latest Value : " + yearRoofLastReplaced.getData());
		}
		if (roofWallAttachment_Yes.checkIfElementIsPresent()
				&& Data.get("L" + locNo + "D" + bldgNo + "-DwellingRoofWallAttach").equalsIgnoreCase("yes")) {
			if (roofWallAttachment_Yes.checkIfElementIsSelected()) {
				Assertions.addInfo("Roof Wall Attachment original Value : " + "Yes", "");
			}
			roofWallAttachment_Yes.scrollToElement();
			roofWallAttachment_Yes.click();
			Assertions.passTest("Dwelling Page", "Roof Wall Attachment Latest Value : " + "Yes");
		}
		if (roofWallAttachment_No.checkIfElementIsPresent()
				&& Data.get("L" + locNo + "D" + bldgNo + "-DwellingRoofWallAttach").equalsIgnoreCase("no")) {
			if (roofWallAttachment_No.checkIfElementIsSelected()) {
				Assertions.addInfo("Roof Wall Attachment original Value : " + "No", "");
			}
			roofWallAttachment_No.waitTillVisibilityOfElement(60);
			roofWallAttachment_No.scrollToElement();
			roofWallAttachment_No.click();
			Assertions.passTest("Dwelling Page", "Roof Wall Attachment Latest Value : " + "No");
		}
		if (wallToFoundationAttachment_Arrow.checkIfElementIsPresent()
				&& !Data.get("L" + locNo + "D" + bldgNo + "-DwellingWallFoundationAttach").equals("")) {
			wallToFoundationAttachmentData.scrollToElement();
			Assertions.addInfo(
					"wall To Foundation Attachment original Value : " + wallToFoundationAttachmentData.getData(), "");
			wallToFoundationAttachment_Arrow.waitTillVisibilityOfElement(60);
			wallToFoundationAttachment_Arrow.scrollToElement();
			wallToFoundationAttachment_Arrow.click();
			wallToFoundationAttachment_Option
					.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingWallFoundationAttach")).click();
			Assertions.passTest("Dwelling Page",
					"wall To Foundation Attachment Latest Value : " + wallToFoundationAttachmentData.getData());
		}
		if (openingProtection_Arrow.checkIfElementIsPresent()
				&& !Data.get("L" + locNo + "D" + bldgNo + "-DwellingOpeningProtection").equals("")) {
			openingProtectionData.scrollToElement();
			Assertions.addInfo("Opening protection original Value : " + openingProtectionData.getData(), "");
			openingProtection_Arrow.waitTillVisibilityOfElement(60);
			openingProtection_Arrow.scrollToElement();
			openingProtection_Arrow.click();
			openingProtection_Option
					.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingOpeningProtection")).click();
			Assertions.passTest("Dwelling Page",
					"Opening protection Latest Value : " + openingProtectionData.getData());
		}
		if (newPurchase_FinanceArrow.checkIfElementIsPresent()
				&& !Data.get("L" + locNo + "D" + bldgNo + "-NewPurchase").equals("")) {
			newPurchase_FinanceArrow.waitTillVisibilityOfElement(60);
			newPurchase_FinanceArrow.scrollToElement();
			newPurchase_FinanceArrow.click();
			newPurchase_FinanceOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-NewPurchase")).click();
		}
		if (windMitigationArrow.checkIfElementIsPresent()
				&& !Data.get("L" + locNo + "D" + bldgNo + "-DwellingWindMitigation").equalsIgnoreCase("")) {
			windMitigationArrow.waitTillVisibilityOfElement(60);
			windMitigationArrow.scrollToElement();
			windMitigationArrow.click();
			WindMitigationOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingWindMitigation"))
					.click();
		}
	}

	public void enterDwellingValuesforPNB(Map<String, String> Data, int locNo, int bldgNo) {
		dwellingValuesLink.waitTillVisibilityOfElement(60);
		dwellingValuesLink.scrollToElement();
		dwellingValuesLink.click();
		if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingCovA").equals("")) {
			covANamedHurricane.scrollToElement();
			Assertions.addInfo("Coverage A original Value : " + covANamedHurricane.getData(), "");
			covANamedHurricane.setData(Data.get("L" + locNo + "D" + bldgNo + "-DwellingCovA"));
			Assertions.passTest("Dwelling Page", "Coverage A Latest Value : " + covANamedHurricane.getData());
		}
		if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingCovB").equals("")) {
			covBNamedHurricane.scrollToElement();
			Assertions.addInfo("Coverage B original Value : " + covBNamedHurricane.getData(), "");
			covBNamedHurricane.setData(Data.get("L" + locNo + "D" + bldgNo + "-DwellingCovB"));
			Assertions.passTest("Dwelling Page", "Coverage B Latest Value : " + covBNamedHurricane.getData());
		}
		if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingCovC").equals("")) {
			covCNamedHurricane.scrollToElement();
			Assertions.addInfo("Coverage C original Value : " + covCNamedHurricane.getData(), "");
			covCNamedHurricane.setData(Data.get("L" + locNo + "D" + bldgNo + "-DwellingCovC"));
			Assertions.passTest("Dwelling Page", "Coverage C Latest Value : " + covCNamedHurricane.getData());
		}
		if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingCovD").equals("")) {
			covDNamedHurricane.scrollToElement();
			Assertions.addInfo("Coverage D original Value : " + covDNamedHurricane.getData(), "");
			covDNamedHurricane.setData(Data.get("L" + locNo + "D" + bldgNo + "-DwellingCovD"));
			Assertions.passTest("Dwelling Page", "Coverage D Latest Value : " + covDNamedHurricane.getData());
		}
	}

	public void deleteDwelling(Map<String, String> Data, int locNo, int bldgNo) {
		Assertions.addInfo("L" + locNo + "D" + bldgNo + "Dwelling - Deleted", "");
		dwellingLink.formatDynamicPath(locNo, bldgNo).scrollToElement();
		dwellingLink.formatDynamicPath(locNo, bldgNo).click();
		deleteSymbol.scrollToElement();
		deleteSymbol.click();
		deleteBldgPopup.waitTillVisibilityOfElement(60);
		deleteBldgYes.scrollToElement();
		deleteBldgYes.click();
	}

	public void modifyDwellingDetails(Map<String, String> Data, int locNo, int bldgNo) {
		FloodPage floodPage = new FloodPage();
		locationLink.formatDynamicPath(locNo).scrollToElement();
		locationLink.formatDynamicPath(locNo).click();
		if (dwellingLink.formatDynamicPath(locNo, bldgNo).checkIfElementIsPresent()) {
			dwellingLink.formatDynamicPath(locNo, bldgNo).scrollToElement();
			dwellingLink.formatDynamicPath(locNo, bldgNo).click();
		} else {
			addSymbol.waitTillButtonIsClickable(60);
			addSymbol.waitTillVisibilityOfElement(60);
			addSymbol.scrollToElement();
			addSymbol.click();
			addNewDwelling.click();
		}
		if (editDwellingSymbol.checkIfElementIsPresent() && editDwellingSymbol.checkIfElementIsDisplayed()) {
			editDwellingSymbol.scrollToElement();
			editDwellingSymbol.click();
			editDwellingSymbol.waitTillInVisibilityOfElement(60);
		}
		Assertions.addInfo("L" + locNo + "D" + bldgNo + " Dwelling - Modified", "");
		editDwellingDetails(Data, locNo, bldgNo);
		editDwellingCharacteristics(Data, locNo, bldgNo);
		addRoofDetailsforPNB(Data, locNo, bldgNo);
		reviewDwelling();
		if (Data.get("Flood").equalsIgnoreCase("yes")) {
			floodPage.floodLink.waitTillVisibilityOfElement(60);
			floodPage.floodLink.click();
			floodPage.enterFloodDetails(Data);
		}
	}

	public void addDwellingForNewLocation(Map<String, String> Data) {
		List<String> locNo = Arrays.asList(Data.get("AddLocation").split(","));
		for (String element : locNo) {
			for (int bldgNo = 1; bldgNo <= 5; bldgNo++) {
				if (!Data.get("L" + element + "D" + bldgNo + "-DwellingAddress").equalsIgnoreCase("")) {
					locationLink.formatDynamicPath(element).scrollToElement();
					locationLink.formatDynamicPath(element).click();
					if (dwellingLink.formatDynamicPath(element, bldgNo).checkIfElementIsPresent()) {
						dwellingLink.formatDynamicPath(element, bldgNo).click();
					} else {
						addSymbol.scrollToElement();
						addSymbol.click();
						addNewDwelling.click();
					}
					Assertions.addInfo("L" + element + "D" + bldgNo + " Dwelling - Added", "");
					addDwellingDetails(Data, Integer.parseInt(element), bldgNo);
					addRoofDetails(Data, Integer.parseInt(element), bldgNo);
					reviewDwelling();
				}
			}
		}
	}

	public void enterEndorsementDwellingDetails(Map<String, String> data, String productSelection) {
		for (int i = 1; i <= 3; i++) {
			for (int j = 1; j <= 5; j++) {
				if (data.get("L" + i + "D" + j + "-DeleteDwelling") != null
						&& data.get("L" + i + "D" + j + "-DeleteDwelling").equalsIgnoreCase("Yes")) {
					deleteDwelling(data, i, j);
				}
				if (!productSelection.equals("Residential Non-Admitted")
						&& data.get("L" + i + "D" + j + "-Modify").equalsIgnoreCase("Yes")) {
					modifyDwellingDetails(data, i, j);
				}
			}
		}
		if (productSelection.equals("Residential Non-Admitted") && data.get("L1D1-Modify").equalsIgnoreCase("Yes")) {
			modifyDwellingDetailsNAHO(data);
		}
	}

	// CA EQHO
	public void enterDwellingDetailsEQHO(Map<String, String> Data) {
		manualEntry.scrollToElement();
		manualEntry.click();
		manualEntryAddress.setData(Data.get("DwellingAddress"));
		manualEntryCity.setData(Data.get("DwellingCity"));
		manualEntryZipCode.setData(Data.get("DwellingZIP"));
		propertyDescription.setData(Data.get("DwellingDesc"));
		dwellingTypeArrow.waitTillVisibilityOfElement(60);
		dwellingTypeArrow.scrollToElement();
		dwellingTypeArrow.click();
		dwellingTypeOption.formatDynamicPath(Data.get("DwellingType")).click();
		yearBuilt.setData(Data.get("DwellingYearBuilt"));
	}

	public void enterGeohazardDetailsEQHO(Map<String, String> Data) {
		geohazardDetailsLink.scrollToElement();
		geohazardDetailsLink.click();
		if (!Data.get("SoilTypeOverride").equals("")) {
			soilTypeOverride_Arrow.waitTillVisibilityOfElement(60);
			soilTypeOverride_Arrow.scrollToElement();
			soilTypeOverride_Arrow.click();
			soilTypeOverride_Option.formatDynamicPath(Data.get("SoilTypeOverride")).scrollToElement();
			soilTypeOverride_Option.formatDynamicPath(Data.get("SoilTypeOverride")).click();
		}
		if (!Data.get("LiquefactionOverride").equals("")) {
			liquefactionOverride_Arrow.waitTillVisibilityOfElement(60);
			liquefactionOverride_Arrow.scrollToElement();
			liquefactionOverride_Arrow.click();
			liquefactionOverride_Option.formatDynamicPath(Data.get("LiquefactionOverride")).scrollToElement();
			liquefactionOverride_Option.formatDynamicPath(Data.get("LiquefactionOverride")).click();
		}
	}

	public void enterConstructionDetailsEQHO(Map<String, String> Data) {
		WebElement constructionlink = WebDriverManager.getCurrentDriver()
				.findElement(By.xpath("//a[contains(text(),'Construction Details')]"));
		constructionlink.click();

		if (!Data.get("DwellingConstType").equals("")) {
			constructionTypeArrow.waitTillVisibilityOfElement(60);
			constructionTypeArrow.scrollToElement();
			constructionTypeArrow.click();
			constructionTypeOption.formatDynamicPath(Data.get("DwellingConstType")).scrollToElement();
			constructionTypeOption.formatDynamicPath(Data.get("DwellingConstType")).click();
		}
		if (StiltOrPostPier_Yes.checkIfElementIsPresent() && StiltOrPostPier_Yes.checkIfElementIsDisplayed()) {
			if (Data.get("StiltOrPost&Pier").equalsIgnoreCase("yes")) {
				StiltOrPostPier_Yes.scrollToElement();
				StiltOrPostPier_Yes.click();
			} else if (Data.get("StiltOrPost&Pier").equalsIgnoreCase("no")) {
				StiltOrPostPier_No.scrollToElement();
				StiltOrPostPier_No.click();
			}
		}
		numOfFloors.setData(Data.get("NumberOfStories"));
		totalSquareFootage.setData(Data.get("DwellingTotalSqFoot"));
		if (siesmicRetrofit_Yes.checkIfElementIsPresent() && siesmicRetrofit_Yes.checkIfElementIsDisplayed()) {
			if (Data.get("SiesmicRetrofit").equalsIgnoreCase("yes")) {
				siesmicRetrofit_Yes.scrollToElement();
				siesmicRetrofit_Yes.click();
			} else if (Data.get("SiesmicRetrofit").equalsIgnoreCase("no")) {
				siesmicRetrofit_No.scrollToElement();
				siesmicRetrofit_No.click();
			} else {
				siesmicRetrofit_Unknown.scrollToElement();
				siesmicRetrofit_Unknown.click();
			}
		}
		if (foundationTypeArrow.checkIfElementIsPresent() && !Data.get("FoundationType").equals("")) {
			foundationTypeArrow.waitTillVisibilityOfElement(60);
			foundationTypeArrow.scrollToElement();
			foundationTypeArrow.click();
			foundationTypeOption.formatDynamicPath(Data.get("FoundationType")).scrollToElement();
			foundationTypeOption.formatDynamicPath(Data.get("FoundationType")).click();
		}
	}

	public void enterDwellingValuesforEQHO(Map<String, String> Data) {
		dwellingValuesLink.waitTillVisibilityOfElement(60);
		dwellingValuesLink.scrollToElement();
		dwellingValuesLink.click();
		if (!Data.get("DwellingCovA").equals("")) {
			covAEQ.scrollToElement();
			covAEQ.setData(Data.get("DwellingCovA"));
		}
	}

	public void enterAndAssertDwellingDetails(Map<String, String> testData) {
		if (!manualEntry.checkIfElementIsPresent()) {
			Assertions.passTest("Dwelling Page", "Manual Entry link is not present");
		} else {
			if (manualEntry.checkIfElementIsEnabled()) {
				manualEntry.waitTillVisibilityOfElement(60);
				manualEntry.scrollToElement();
				manualEntry.click();
				Assertions.passTest("Dwelling Page", "Manual Entry link is present and enabled");
			} else {
				Assertions.passTest("Dwelling Page", "Manual Entry link is present and disabled");
			}
		}

		if (!manualEntryAddress.checkIfElementIsPresent()) {
			Assertions.passTest("Dwelling Page", "Manual Entry Address field is not present");
		} else {
			if (manualEntryAddress.checkIfElementIsEnabled()) {
				manualEntryAddress.clearData();
				if (expiredQuotePopUp.checkIfElementIsPresent()) {
					expiredQuotePopUp.scrollToElement();
					continueWithUpdateBtn.scrollToElement();
					continueWithUpdateBtn.click();
					continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
				}
				manualEntryAddress.setData(testData.get("L" + locNo + "D" + bldgNo + "-DwellingAddress"));
				Assertions.passTest("Dwelling Page", "Manual Entry Address field is present and enabled");
			} else {
				Assertions.passTest("Dwelling Page", "Manual Entry Address field is present and disabled");
			}
		}

		if (!manualEntryCity.checkIfElementIsPresent()) {
			Assertions.passTest("Dwelling Page", "Manual Entry City field is not present");
		} else {
			if (manualEntryCity.checkIfElementIsEnabled()) {
				manualEntryCity.setData(testData.get("L" + locNo + "D" + bldgNo + "-DwellingCity"));
				Assertions.passTest("Dwelling Page", "Manual Entry City field is present and enabled");
			} else {
				Assertions.passTest("Dwelling Page", "Manual Entry City field is present and disabled");
			}
		}

		if (!manualEntryZipCode.checkIfElementIsPresent()) {
			Assertions.passTest("Dwelling Page", "Manual Entry Zipcode field is not present");
		} else {
			if (manualEntryZipCode.checkIfElementIsEnabled()) {
				manualEntryZipCode.setData(testData.get("L" + locNo + "D" + bldgNo + "-DwellingZIP"));
				Assertions.passTest("Dwelling Page", "Manual Entry Zipcode field is present and enabled");
			} else {
				Assertions.passTest("Dwelling Page", "Manual Entry Zipcode field is present and disabled");
			}
		}

		if (!propertyDescription.checkIfElementIsPresent()) {
			Assertions.passTest("Dwelling Page", "Property description field is not present");
		} else {
			if (propertyDescription.checkIfElementIsEnabled()) {
				propertyDescription.setData(testData.get("L" + locNo + "D" + bldgNo + "-DwellingDesc"));
				Assertions.passTest("Dwelling Page", "Property Description field is present and enabled");
			} else {
				Assertions.passTest("Dwelling Page", "Property Description field is present and disabled");
			}
		}

		if (!dwellingTypeArrow.checkIfElementIsPresent()) {
			Assertions.passTest("Dwelling Page", "Dwelling Type arrow is not present");
		} else {
			if (dwellingTypeArrow.checkIfElementIsEnabled()
					&& dwellingTypeArrow.getAttrributeValue("class").contains("selectboxit-enabled")) {
				dwellingTypeArrow.waitTillVisibilityOfElement(60);
				dwellingTypeArrow.scrollToElement();
				dwellingTypeArrow.click();
				dwellingTypeOption.formatDynamicPath(testData.get("L" + locNo + "D" + bldgNo + "-DwellingType"))
						.click();
				Assertions.passTest("Dwelling Page", "Dwelling Type arrow is present and enabled");
			} else {
				Assertions.passTest("Dwelling Page", "Dwelling Type arrow is present and disabled");
			}
		}

		if (!dwellingCharacteristicsLink.checkIfElementIsPresent()) {
			Assertions.passTest("Dwelling Page", "Dwelling Characteristics link is not present");
		} else {
			if (dwellingCharacteristicsLink.checkIfElementIsEnabled()) {
				dwellingCharacteristicsLink.scrollToElement();
				dwellingCharacteristicsLink.click();
				Assertions.passTest("Dwelling Page", "Dwelling Characteristics link is present and enabled");
			} else {
				Assertions.passTest("Dwelling Page", "Dwelling Characteristics link is present and disabled");
			}
		}

		if (!constructionTypeArrow.checkIfElementIsPresent()) {
			Assertions.passTest("Dwelling Page", "Construction Type arrow is not present");
		} else {
			if (constructionTypeArrow.checkIfElementIsEnabled()
					&& constructionTypeArrow.getAttrributeValue("class").contains("selectboxit-enabled")) {
				constructionTypeArrow.waitTillVisibilityOfElement(60);
				constructionTypeArrow.scrollToElement();
				constructionTypeArrow.click();
				constructionTypeOption
						.formatDynamicPath(testData.get("L" + locNo + "D" + bldgNo + "-DwellingConstType"))
						.waitTillVisibilityOfElement(60);
				constructionTypeOption
						.formatDynamicPath(testData.get("L" + locNo + "D" + bldgNo + "-DwellingConstType"))
						.scrollToElement();
				constructionTypeOption
						.formatDynamicPath(testData.get("L" + locNo + "D" + bldgNo + "-DwellingConstType")).click();
				Assertions.passTest("Dwelling Page", "Construction Type arrow is present and enabled");
			} else {
				Assertions.passTest("Dwelling Page", "Construction Type arrow is present and disabled");
			}
		}

		if (!foundationTypeArrow.checkIfElementIsPresent()) {
			Assertions.passTest("Dwelling Page", "Foundation Type dropdown is not present");
		} else {
			if (foundationTypeArrow.checkIfElementIsEnabled()) {
				foundationTypeArrow.waitTillVisibilityOfElement(60);
				foundationTypeArrow.scrollToElement();
				foundationTypeArrow.click();
				foundationTypeOption.formatDynamicPath(testData.get("L" + locNo + "D" + bldgNo + "-FoundationType"))
						.scrollToElement();
				foundationTypeOption.formatDynamicPath(testData.get("L" + locNo + "D" + bldgNo + "-FoundationType"))
						.click();
				Assertions.passTest("Dwelling Page", "Foundation Type dropdown is present and enabled");
			} else {
				Assertions.passTest("Dwelling Page", "Foundation Type dropdown is present and disabled");
			}
		}

		if (!OccupiedByArrow.checkIfElementIsPresent()) {
			Assertions.passTest("Dwelling Page", "OccupiedBy dropdown is not present");
		} else {
			if (OccupiedByArrow.checkIfElementIsEnabled()) {
				OccupiedByArrow.waitTillVisibilityOfElement(60);
				OccupiedByArrow.scrollToElement();
				OccupiedByArrow.click();
				OccupiedByOption.formatDynamicPath(testData.get("L" + locNo + "D" + bldgNo + "-OccupiedBy"))
						.scrollToElement();
				OccupiedByOption.formatDynamicPath(testData.get("L" + locNo + "D" + bldgNo + "-OccupiedBy")).click();
				Assertions.passTest("Dwelling Page", "OccupiedBy dropdown is present and enabled");
			} else {
				Assertions.passTest("Dwelling Page", "OccupiedBy dropdown is present and disabled");
			}
		}

		if (!yearBuilt.checkIfElementIsPresent()) {
			Assertions.passTest("Dwelling Page", "Year Built field is not present");
		} else {
			if (yearBuilt.checkIfElementIsEnabled()) {
				yearBuilt.setData(testData.get("L" + locNo + "D" + bldgNo + "-DwellingYearBuilt"));
				Assertions.passTest("Dwelling Page", "Year Built field is present and enabled");
			} else {
				Assertions.passTest("Dwelling Page", "Year Built field is present and disabled");
			}
		}

		if (!livingSquareFootage.checkIfElementIsPresent()) {
			Assertions.passTest("Dwelling Page", "Living Sqft field is not present");
		} else {
			if (livingSquareFootage.checkIfElementIsEnabled()) {
				livingSquareFootage.setData(testData.get("L" + locNo + "D" + bldgNo + "-DwellingSqFoot"));
				Assertions.passTest("Dwelling Page", "Living Sqft field is present and enabled");
			} else {
				Assertions.passTest("Dwelling Page", "Living Sqft field is present and disabled");
			}
		}

		if (!nonLivingSquareFootage.checkIfElementIsPresent()) {
			Assertions.passTest("Dwelling Page", "Non Living Sqft field is not present");
		} else {
			if (nonLivingSquareFootage.checkIfElementIsEnabled()) {
				nonLivingSquareFootage.setData(testData.get("L" + locNo + "D" + bldgNo + "-DwellingNonLivingSqFoot"));
				Assertions.passTest("Dwelling Page", "Non Living Sqft field is present and enabled");
			} else {
				Assertions.passTest("Dwelling Page", "Non Living Sqft field is present and disabled");
			}
		}

		if (!numOfFloors.checkIfElementIsPresent()) {
			Assertions.passTest("Dwelling Page", "Number of floors field is not present");
		} else {
			if (numOfFloors.checkIfElementIsEnabled()) {
				numOfFloors.setData(testData.get("L" + locNo + "D" + bldgNo + "-DwellingFloors"));
				Assertions.passTest("Dwelling Page", "Number Of Floors field is present and enabled");
			} else {
				Assertions.passTest("Dwelling Page", "Number Of Floors field is present and disabled");
			}
		}

		if (!inspectionAvailable_yes.checkIfElementIsPresent()) {
			Assertions.passTest("Dwelling Page", "Primary Inspection radiobutton is not present");
		} else {
			if (inspectionAvailable_yes.checkIfElementIsEnabled() && inspectionAvailable_no.checkIfElementIsEnabled()) {
				if (testData.get("L" + locNo + "D" + bldgNo + "-InspfromPrimaryCarrier").equalsIgnoreCase("yes")) {
					inspectionAvailable_yes.scrollToElement();
					inspectionAvailable_yes.click();
				} else {
					inspectionAvailable_no.scrollToElement();
					inspectionAvailable_no.click();
				}
				Assertions.passTest("Dwelling Page", "Primary Inspection radiobutton is present and Enabled");
			} else {
				Assertions.passTest("Dwelling Page", "Primary Inspection radiobutton is present and disabled");
			}
		}

		if (!roofDetailsLink.checkIfElementIsPresent()) {
			Assertions.passTest("Dwelling Page", "Roof Details link is not present");
		} else {
			if (roofDetailsLink.checkIfElementIsEnabled()) {
				WebDriverWait wait = new WebDriverWait(WebDriverManager.getCurrentDriver(), Duration.ofSeconds(5));
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Roof Details')]")));
				roofDetailsLink.waitTillElementisEnabled(60);
				roofDetailsLink.waitTillVisibilityOfElement(60);
				roofDetailsLink.scrollToElement();
				roofDetailsLink.click();
				Assertions.passTest("Dwelling Page", "Roof Details link is present and enabled");
			} else {
				Assertions.passTest("Dwelling Page", "Roof Details link is present and disabled");
			}
		}

		if (!roofShapeArrow.checkIfElementIsPresent()) {
			Assertions.passTest("Dwelling Page", "Roof Shape dropdown is not present");
		} else {
			if (roofShapeArrow.checkIfElementIsEnabled()
					&& roofShapeArrow.getAttrributeValue("class").contains("selectboxit-enabled")) {
				roofShapeArrow.waitTillVisibilityOfElement(60);
				roofShapeArrow.waitTillPresenceOfElement(60);
				roofShapeArrow.waitTillButtonIsClickable(60);
				roofShapeArrow.moveToElement();
				roofShapeArrow.scrollToElement();
				roofShapeArrow.click();
				roofShapeOption.formatDynamicPath(testData.get("L" + locNo + "D" + bldgNo + "-DwellingRoofShape"))
						.waitTillVisibilityOfElement(60);
				roofShapeOption.formatDynamicPath(testData.get("L" + locNo + "D" + bldgNo + "-DwellingRoofShape"))
						.waitTillButtonIsClickable(60);
				roofShapeOption.formatDynamicPath(testData.get("L" + locNo + "D" + bldgNo + "-DwellingRoofShape"))
						.moveToElement();
				roofShapeOption.formatDynamicPath(testData.get("L" + locNo + "D" + bldgNo + "-DwellingRoofShape"))
						.scrollToElement();
				roofShapeOption.formatDynamicPath(testData.get("L" + locNo + "D" + bldgNo + "-DwellingRoofShape"))
						.click();
				Assertions.passTest("Dwelling Page", "Roof Shape dropdown is present and enabled");
			} else {
				Assertions.passTest("Dwelling Page", "Roof Shape dropdown is present and disabled");
			}
		}

		if (!yearRoofLastReplaced.checkIfElementIsPresent()) {
			Assertions.passTest("Dwelling Page", "Year Roof replaced  is not present");
		} else {
			if (yearRoofLastReplaced.checkIfElementIsEnabled()) {
				yearRoofLastReplaced.scrollToElement();
				yearRoofLastReplaced.setData(testData.get("L" + locNo + "D" + bldgNo + "-DwellingRoofReplacedYear"));
				yearRoofLastReplaced.tab();
				Assertions.passTest("Dwelling Page", "Year Roof replaced is present and enabled");
			} else {
				Assertions.passTest("Dwelling Page", "Year Roof replaced is present and disabled");
			}
		}

		if (!refinanceYear.checkIfElementIsPresent()) {
			Assertions.passTest("Dwelling Page", "Refinance Year field  is not present");
		} else {
			if (refinanceYear.checkIfElementIsEnabled()) {
				refinanceYear.scrollToElement();
				refinanceYear.setData(testData.get("L" + locNo + "D" + bldgNo + "-RefinanceYear"));
				Assertions.passTest("Dwelling Page", "Refinance Year  is present and enabled");
			} else {
				Assertions.passTest("Dwelling Page", "Refinance Year  is present and disabled");
			}
		}

		if (!windMitigationArrow.checkIfElementIsPresent() && !windMitigationArrow.checkIfElementIsDisplayed()) {
			Assertions.passTest("Dwelling Page", "Wind Mitigation dropdown is not present");
		} else {
			if (windMitigationArrow.checkIfElementIsPresent() && windMitigationArrow.checkIfElementIsDisplayed()) {
				windMitigationArrow.waitTillPresenceOfElement(60);
				windMitigationArrow.waitTillVisibilityOfElement(60);
				windMitigationArrow.waitTillButtonIsClickable(60);
				windMitigationArrow.moveToElement();
				windMitigationArrow.click();
				if (WindMitigationOption1.getAttrributeValue("class").contains("selectboxit-selected")) {
					WindMitigationOption
							.formatDynamicPath(testData.get("L" + locNo + "D" + bldgNo + "-DwellingWindMitigation"))
							.waitTillVisibilityOfElement(60);
					WindMitigationOption
							.formatDynamicPath(testData.get("L" + locNo + "D" + bldgNo + "-DwellingWindMitigation"))
							.click();
					Assertions.passTest("Dwelling Page", "Wind Mitigation dropdown is present and enabled");
				} else {
					Assertions.passTest("Dwelling Page", "Wind Mitigation dropdown is present and disbaled");
				}

			}
		}

		if (!dwellingValuesLink.checkIfElementIsPresent()) {
			Assertions.passTest("Dwelling Page", "Dwelling Values link is not present");
		} else {
			if (dwellingValuesLink.checkIfElementIsEnabled()) {
				dwellingValuesLink.waitTillVisibilityOfElement(60);
				dwellingValuesLink.scrollToElement();
				dwellingValuesLink.click();
				Assertions.passTest("Dwelling Page", "Dwelling Values link is present and enabled");
			} else {
				Assertions.passTest("Dwelling Page", "Dwelling Values link is present and disabled");
			}
		}

		if (!covANamedHurricane.checkIfElementIsPresent()) {
			Assertions.passTest("Dwelling Page", "Coverage A field is not present");
		} else {
			if (covANamedHurricane.checkIfElementIsEnabled()) {
				covANamedHurricane.clearData();
				covANamedHurricane.scrollToElement();
				covANamedHurricane.setData(testData.get("L" + locNo + "D" + bldgNo + "-DwellingCovA"));
				Assertions.passTest("Dwelling Page", "Coverage A field is present and enabled");
			} else {
				Assertions.passTest("Dwelling Page", "Coverage A field is present and disabled");
			}
		}

		if (!covBNamedHurricane.checkIfElementIsPresent()) {
			Assertions.passTest("Dwelling Page", "Coverage B field is not present");
		} else {
			if (covBNamedHurricane.checkIfElementIsEnabled()) {
				covBNamedHurricane.clearData();
				covBNamedHurricane.scrollToElement();
				covBNamedHurricane.setData(testData.get("L" + locNo + "D" + bldgNo + "-DwellingCovB"));
				Assertions.passTest("Dwelling Page", "Coverage A field is present and enabled");
			} else {
				Assertions.passTest("Dwelling Page", "Coverage B field is present and disabled");
			}
		}

		if (!covCNamedHurricane.checkIfElementIsPresent()) {
			Assertions.passTest("Dwelling Page", "Coverage C field is not present");
		} else {
			if (covCNamedHurricane.checkIfElementIsEnabled()) {
				covCNamedHurricane.clearData();
				covCNamedHurricane.scrollToElement();
				covCNamedHurricane.setData(testData.get("L" + locNo + "D" + bldgNo + "-DwellingCovC"));
				Assertions.passTest("Dwelling Page", "Coverage C field is present and enabled");
			} else {
				Assertions.passTest("Dwelling Page", "Coverage C field is present and disabled");
			}
		}
		if (!covDNamedHurricane.checkIfElementIsPresent()) {
			Assertions.passTest("Dwelling Page", "Coverage D field is not present");
		} else {
			if (covDNamedHurricane.checkIfElementIsEnabled()) {
				covDNamedHurricane.clearData();
				covDNamedHurricane.scrollToElement();
				covDNamedHurricane.setData(testData.get("L" + locNo + "D" + bldgNo + "-DwellingCovD"));
				Assertions.passTest("Dwelling Page", "Coverage D field is present and enabled");
			} else {
				Assertions.passTest("Dwelling Page", "Coverage D field is present and disabled");
			}
		}
	}

	public CreateQuotePage enterDwellingDetailsCAEQHO(Map<String, String> Data) {
		enterDwellingDetailsEQHO(Data);
		enterGeohazardDetailsEQHO(Data);
		enterConstructionDetailsEQHO(Data);
		enterDwellingValuesforEQHO(Data);
		reviewDwelling();
		createQuote.scrollToElement();
		createQuote.click();
		BuildingNoLongerQuoteablePage buildingNoLongerQuotablePage = new BuildingNoLongerQuoteablePage();
		if (pageName.getData().contains("Buildings No")) {
			buildingNoLongerQuotablePage.overrideNoLongerQuotableBuildings();
		}
		if (pageName.getData().contains("Create Quote")) {
			return new CreateQuotePage();
		}
		return null;
	}

	public void enterProtectionDiscountsDetails(Map<String, String> Data, int locNo, int bldgNo) {
		waitTime(3); // Added to click on the link
		protectionDiscounts.waitTillVisibilityOfElement(60);
		protectionDiscounts.scrollToElement();
		protectionDiscounts.click();
		if (yearPlumbingUpdated.checkIfElementIsPresent() && yearPlumbingUpdated.checkIfElementIsDisplayed()) {
			if (!Data.get("L" + locNo + "D" + bldgNo + "-PlumbingUpdatedYear").equals("")) {
				yearPlumbingUpdated.scrollToElement();
				yearPlumbingUpdated.setData(Data.get("L" + locNo + "D" + bldgNo + "-PlumbingUpdatedYear"));
				yearPlumbingUpdated.tab();
			} else {
				yearPlumbingUpdated.scrollToElement();
				yearPlumbingUpdated.clearData();
				yearPlumbingUpdated.tab();
			}
		}
		if (yearElectricalUpdated.checkIfElementIsPresent() && yearElectricalUpdated.checkIfElementIsDisplayed()) {
			if (!Data.get("L" + locNo + "D" + bldgNo + "-ElectricalUpdatedYear").equals("")) {
				yearElectricalUpdated.scrollToElement();
				yearElectricalUpdated.setData(Data.get("L" + locNo + "D" + bldgNo + "-ElectricalUpdatedYear"));
				yearElectricalUpdated.tab();
			} else {
				yearElectricalUpdated.scrollToElement();
				yearElectricalUpdated.clearData();
				yearElectricalUpdated.tab();
			}
		}
		if (yearHVACUpdated.checkIfElementIsPresent() && yearHVACUpdated.checkIfElementIsDisplayed()) {
			if (!Data.get("L" + locNo + "D" + bldgNo + "-HVACUpdatedYear").equals("")) {
				yearHVACUpdated.scrollToElement();
				yearHVACUpdated.setData(Data.get("L" + locNo + "D" + bldgNo + "-HVACUpdatedYear"));
				yearHVACUpdated.tab();
			} else {
				yearHVACUpdated.scrollToElement();
				yearHVACUpdated.clearData();
				yearHVACUpdated.tab();
			}
		}
		if (windMitigationArrow.checkIfElementIsPresent() && windMitigationArrow.checkIfElementIsEnabled()) {
			waitTime(3); // added waittime to click on the dropdown
			if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingWindMitigation").equals("")) {
				windMitigationArrow.scrollToElement();
				windMitigationArrow.click();
				WindMitigationOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingWindMitigation"))
						.waitTillPresenceOfElement(60);
				WindMitigationOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingWindMitigation"))
						.waitTillVisibilityOfElement(60);
				WindMitigationOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingWindMitigation"))
						.waitTillButtonIsClickable(60);
				WindMitigationOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingWindMitigation"))
						.scrollToElement();
				WindMitigationOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingWindMitigation"))
						.click();
			}
		}
		if (centralStationAlarmArrow.checkIfElementIsPresent() && centralStationAlarmArrow.checkIfElementIsEnabled()) {
			if (!Data.get("L" + locNo + "D" + bldgNo + "-CentralStationAlarm").equals("")) {
				centralStationAlarmArrow.scrollToElement();
				centralStationAlarmArrow.click();
				centralStationAlarmOption
						.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-CentralStationAlarm"))
						.waitTillVisibilityOfElement(60);
				centralStationAlarmOption
						.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-CentralStationAlarm"))
						.scrollToElement();
				centralStationAlarmOption
						.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-CentralStationAlarm")).click();
			}
		}
		if (wholeSprinklerYes.checkIfElementIsPresent() && wholeSprinklerYes.checkIfElementIsDisplayed()) {
			if (Data.get("L" + locNo + "D" + bldgNo + "-WholeHomeSprinklerSystem").equalsIgnoreCase("Yes")) {
				wholeSprinklerYes.scrollToElement();
				wholeSprinklerYes.click();
			} else if (Data.get("L" + locNo + "D" + bldgNo + "-WholeHomeSprinklerSystem").equalsIgnoreCase("No")) {
				wholeSprinklerNo.scrollToElement();
				wholeSprinklerNo.click();
			}
		}
		if (waterDetectionYes.checkIfElementIsPresent() && waterDetectionYes.checkIfElementIsDisplayed()) {
			if (Data.get("L" + locNo + "D" + bldgNo + "-WaterDetection/ShutOffSystem").equalsIgnoreCase("Yes")) {
				waterDetectionYes.scrollToElement();
				waterDetectionYes.click();
			} else if (Data.get("L" + locNo + "D" + bldgNo + "-WaterDetection/ShutOffSystem").equalsIgnoreCase("No")) {
				waterDetectionNo.scrollToElement();
				waterDetectionNo.click();
			}
		}
		if (gatedCommunityYes.checkIfElementIsPresent() && gatedCommunityYes.checkIfElementIsDisplayed()) {
			if (Data.get("L" + locNo + "D" + bldgNo + "-GatedCommunity").equalsIgnoreCase("Yes")) {
				gatedCommunityYes.scrollToElement();
				gatedCommunityYes.click();
			} else if (Data.get("L" + locNo + "D" + bldgNo + "-GatedCommunity").equalsIgnoreCase("No")) {
				gatedCommunityNo.scrollToElement();
				gatedCommunityNo.click();
			}
		}
		if (newPurchaseYes.checkIfElementIsPresent() && newPurchaseYes.checkIfElementIsDisplayed()) {
			if (Data.get("L" + locNo + "D" + bldgNo + "-NewPurchase").equalsIgnoreCase("Yes")) {
				newPurchaseYes.scrollToElement();
				newPurchaseYes.click();
			} else if (Data.get("L" + locNo + "D" + bldgNo + "-NewPurchase").equalsIgnoreCase("No")) {
				newPurchaseNo.scrollToElement();
				newPurchaseNo.click();
			}
		}
		if (companionPolicyYes.checkIfElementIsPresent() && companionPolicyYes.checkIfElementIsDisplayed()) {
			if (Data.get("L" + locNo + "D" + bldgNo + "-CompanionPolicy").equalsIgnoreCase("Yes")) {
				companionPolicyYes.scrollToElement();
				companionPolicyYes.click();
			} else if (Data.get("L" + locNo + "D" + bldgNo + "-CompanionPolicy").equalsIgnoreCase("No")) {
				companionPolicyNo.scrollToElement();
				companionPolicyNo.click();
			}
		}
	}

	public void enterInternalSectionDetails(Map<String, String> Data, int locNo, int bldgNo) {
		internalSection.waitTillVisibilityOfElement(60);
		internalSection.waitTillButtonIsClickable(60);
		internalSection.scrollToElement();
		internalSection.click();
		if (protectionClassOverride.checkIfElementIsPresent() && protectionClassOverride.checkIfElementIsDisplayed()) {
			if (!Data.get("L" + locNo + "D" + bldgNo + "-ProtectionClassOverride").equals("")) {
				protectionClassOverride.scrollToElement();
				protectionClassOverride.setData(Data.get("L" + locNo + "D" + bldgNo + "-ProtectionClassOverride"));
				protectionClassOverride.tab();
			}
		}
		if (distanceToCoastOverride.checkIfElementIsPresent() && distanceToCoastOverride.checkIfElementIsDisplayed()) {
			if (!Data.get("L" + locNo + "D" + bldgNo + "-DistanceToCoastOverride").equals("")) {
				distanceToCoastOverride.scrollToElement();
				distanceToCoastOverride.setData(Data.get("L" + locNo + "D" + bldgNo + "-DistanceToCoastOverride"));
			}
		}

	}

	public void enterDwellingValuesNAHO(Map<String, String> Data, int locNo, int bldgNo) {
		waitTime(3); // After Protection class overridden it was throwing
						// Element Click Intercepted Exception
		if (dwellingValuesLink.checkIfElementIsPresent() && dwellingValuesLink.checkIfElementIsDisplayed()) {
			dwellingValuesLink.waitTillPresenceOfElement(60);
			dwellingValuesLink.waitTillVisibilityOfElement(60);
			dwellingValuesLink.waitTillElementisEnabled(60);
			dwellingValuesLink.waitTillButtonIsClickable(60);
			dwellingValuesLink.scrollToElement();
			dwellingValuesLink.click();
		}
		if (coverageADwelling.checkIfElementIsPresent() && coverageADwelling.checkIfElementIsDisplayed()) {
			if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingCovA").equals("")) {
				coverageADwelling.waitTillPresenceOfElement(60);
				coverageADwelling.waitTillVisibilityOfElement(60);
				coverageADwelling.waitTillElementisEnabled(60);
				coverageADwelling.scrollToElement();
				coverageADwelling.setData(Data.get("L" + locNo + "D" + bldgNo + "-DwellingCovA"));
				coverageADwelling.tab();
			}
		}
	}

	public CreateQuotePage enterDwellingDetailsNAHO(Map<String, String> Data) {
		String locationNumber = Data.get("LocCount");
		int locationCount = Integer.parseInt(locationNumber);

		String dwellingNumber = Data.get("L" + locationCount + "-DwellingCount");
		int dwellingCount = Integer.parseInt(dwellingNumber);

		addDwellingDetails(Data, locationCount, dwellingCount);
		addRoofDetails(Data, locationCount, dwellingCount);
		enterProtectionDiscountsDetails(Data, locationCount, dwellingCount);
		if (internalSection.checkIfElementIsPresent() && internalSection.checkIfElementIsDisplayed()) {
			internalSection.scrollToElement();
			internalSection.click();
		}
		if (!Data.get("L" + locNo + "D" + bldgNo + "-ProtectionClassOverride").equals("")) {
			enterInternalSectionDetails(Data, locationCount, dwellingCount);
		}

		enterDwellingValuesNAHO(Data, locationCount, dwellingCount);
		waitTime(5); // Control is shifting to roof details link after entering
						// dwelling values instead of clicking on review
						// dwelling
		reviewDwelling();

		boolean addressFound = false;

		if (addressMsg.checkIfElementIsPresent() && addressMsg.checkIfElementIsDisplayed() && !addressFound) {
			for (int i = 1; i <= 20; i++) {
				if (editDwellingSymbol.checkIfElementIsPresent()) {
					editDwellingSymbol.scrollToElement();
					editDwellingSymbol.click();
				}
				manualEntry.click();
				manualEntryAddress.waitTillVisibilityOfElement(60);
				manualEntryAddress.setData(
						manualEntryAddress.getData().replace(manualEntryAddress.getData().replaceAll("[^0-9]", ""),
								(Integer.parseInt(manualEntryAddress.getData().replaceAll("[^0-9]", ""))) + 2 + ""));
				manualEntryAddress.tab();
				dwellingValuesLink.click();
				scrollToBottomPage();
				reviewDwelling();

				if (!addressMsg.checkIfElementIsPresent() || !addressMsg.checkIfElementIsPresent()) {
					addressFound = true;
					break;
				}
			}
		}
		if (createQuote.checkIfElementIsPresent() || createQuote.checkIfElementIsDisplayed()) {
			createQuote.waitTillPresenceOfElement(60);
			createQuote.waitTillVisibilityOfElement(60);
			createQuote.waitTillButtonIsClickable(60);
			createQuote.scrollToElement();
			createQuote.click();
		}
		BuildingNoLongerQuoteablePage buildingNoLongerQuotablePage = new BuildingNoLongerQuoteablePage();
		if (pageName.getData().contains("Buildings No")) {
			buildingNoLongerQuotablePage.overrideNoLongerQuotableBuildings();
		}
		if (pageName.getData().contains("Create Quote")) {
			return new CreateQuotePage();
		}
		return null;
	}

	public void modifyDwellingDetailsNAHO(Map<String, String> Data) {
		String locationNumber = Data.get("LocCount");
		int locationCount = Integer.parseInt(locationNumber);

		String dwellingNumber = Data.get("L" + locationCount + "-DwellingCount");
		int dwellingCount = Integer.parseInt(dwellingNumber);

		if (expiredQuotePopUp.checkIfElementIsPresent()) {
			expiredQuotePopUp.scrollToElement();
			continueWithUpdateBtn.scrollToElement();
			continueWithUpdateBtn.click();
			continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
		}

		if (!Data.get("ModifyDwellingDetails").equals("")) {
			addDwellingDetails(Data, locationCount, dwellingCount);
		}

		if (!Data.get("ModifyRoofDetails").equals("")) {
			addRoofDetails(Data, locationCount, dwellingCount);
		}

		if (!Data.get("ModifyProtectionDiscountDetails").equals("")) {
			enterProtectionDiscountsDetails(Data, locationCount, dwellingCount);
		}

		if (!Data.get("L" + locNo + "D" + bldgNo + "-ProtectionClassOverride").equals("")) {
			enterInternalSectionDetails(Data, locationCount, dwellingCount);
		}

		if (dwellingValuesLink.checkIfElementIsPresent() && dwellingValuesLink.checkIfElementIsDisplayed()) {
			if (expiredQuotePopUp.checkIfElementIsPresent()) {
				expiredQuotePopUp.scrollToElement();
				continueWithUpdateBtn.scrollToElement();
				continueWithUpdateBtn.click();
				continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}
			enterDwellingValuesNAHO(Data, locationCount, dwellingCount);
		}
		waitTime(5); // Control is shifting to roof details link after entering dwelling values
						// instead of clicking on review dwelling
		reviewDwelling();

		if (createQuote.checkIfElementIsPresent() && createQuote.checkIfElementIsDisplayed()) {
			createQuote.waitTillVisibilityOfElement(60);
			createQuote.waitTillButtonIsClickable(60);
			createQuote.scrollToElement();
			createQuote.click();
		}

		if (pageName.getData().contains("Buildings No")) {
			buildingNoLongerQuotablePage.overrideNoLongerQuotableBuildings();
		}
	}
}