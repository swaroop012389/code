/** Program Description: Object Locators and methods defined in Binder page
 *  Author			   : Abha
 *  Date of Creation   : 07/11/2019
**/

package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.PageObject;

public class ViewPolicySnapShot extends BasePageControl {

	public BaseWebElementControl apcName;
	public BaseWebElementControl apcLimit;
	public BaseWebElementControl apcPremium;
	public BaseWebElementControl apcBusinessIncome;
	public BaseWebElementControl apcDriveways;
	public BaseWebElementControl apcFences;
	public BaseWebElementControl apcAwnings;
	public BaseWebElementControl apcLightpoles;
	public BaseWebElementControl apcBoardwalks;
	public BaseWebElementControl apcPlayGroundEquipment;
	public BaseWebElementControl apcFountains;
	public BaseWebElementControl apcOtherStructures;
	public BaseWebElementControl apcSatellitedishes;
	public BaseWebElementControl apcPoolsandwaterfalls;
	public BaseWebElementControl apcCarports;
	public BaseWebElementControl apcMachinary;
	public BaseWebElementControl apcOpennotfullyenclosed;
	public BaseWebElementControl apcUndergroundutilities;
	public BaseWebElementControl businessIncomeData;
	public BaseWebElementControl binderAccountReceivable;
	public BaseWebElementControl binderAccountReceivableData;
	public BaseWebElementControl binderCustomerProperty;
	public BaseWebElementControl binderCustomerPropertyData;
	public BaseWebElementControl binderFineArts;
	public BaseWebElementControl binderFineArtsData;
	public BaseWebElementControl binderFireExtinguisher;
	public BaseWebElementControl binderFireExtinguisherData;
	public BaseWebElementControl binderLockReplacement;
	public BaseWebElementControl binderLockReplacementData;
	public BaseWebElementControl binderRobbery;
	public BaseWebElementControl binderRobberyData;
	public BaseWebElementControl binderSewerDrain;
	public BaseWebElementControl binderSewerDrainData;
	public BaseWebElementControl binderSpoilage;
	public BaseWebElementControl binderSpoilageData;
	public BaseWebElementControl binderTheftDisappearance;
	public BaseWebElementControl binderTheftDisappearanceData;
	public BaseWebElementControl binderUtilityServicesDirectDamage;
	public BaseWebElementControl binderUtilityServicesDirectDamageData;
	public BaseWebElementControl binderUtilityServicesTimeElement;
	public BaseWebElementControl binderUtilityServicesTimeElementData;
	public BaseWebElementControl binderElectronic;
	public BaseWebElementControl binderElectronicData;
	public BaseWebElementControl binderExtendedPeriod;
	public BaseWebElementControl binderExtendedPeriodData;
	public BaseWebElementControl binderFireDepartment;
	public BaseWebElementControl binderFireDepartmentData;
	public BaseWebElementControl binderNewlyAcquiredBpp;
	public BaseWebElementControl binderNewlyAcquiredBppData;
	public BaseWebElementControl binderConstructedProperty;
	public BaseWebElementControl binderConstructedPropertyData;
	public BaseWebElementControl binderOutdoorProperty;
	public BaseWebElementControl binderOutdoorPropertyData;
	public BaseWebElementControl binderPerimeterExtension;
	public BaseWebElementControl binderPerimeterExtensionData;
	public BaseWebElementControl binderPersonalEffects;
	public BaseWebElementControl binderPersonalEffectsData;
	public BaseWebElementControl binderPropertyinTransit;
	public BaseWebElementControl binderPropertyinTransitData;
	public BaseWebElementControl binderPropertyOffPremises;
	public BaseWebElementControl binderPropertyOffPremisesData;
	public BaseWebElementControl binderTenantGlass;
	public BaseWebElementControl binderTenantGlassData;
	public BaseWebElementControl binderValuablePapersAndRecords;
	public BaseWebElementControl binderValuablePapersAndRecordsData;
	public BaseWebElementControl binderEqSpinkler;
	public BaseWebElementControl binderEqSpinklerData;
	public BaseWebElementControl binderEquipmentBreakdown;
	public BaseWebElementControl binderOrdinanceOrLaw;
	public BaseWebElementControl binderOrdinanceOrLawData;
	public BaseWebElementControl binderOrdinanceOrLawvalue;
	public BaseWebElementControl binderWindDriven;
	public BaseWebElementControl binderWindDrivenData;
	public BaseWebElementControl binderTerrorism;
	public BaseWebElementControl binderTerrorismdata;
	public BaseWebElementControl binderPackageA;
	public ButtonControl goBackButton;

	public BaseWebElementControl propertyCovBldgLimit;
	public BaseWebElementControl propertyCovBldgPremium;
	public BaseWebElementControl propertyCovBPPLimit;
	public BaseWebElementControl propertyCovBPPPremium;
	public BaseWebElementControl propertyCovOrdinanceLimit;
	public BaseWebElementControl propertyCovOrdinancePremium;
	public BaseWebElementControl building;
	public BaseWebElementControl location;
	public BaseWebElementControl locationLevelCovLimit;
	public BaseWebElementControl locationLevelCovPremium;

	public BaseWebElementControl gLInfoClass;
	public BaseWebElementControl gLInfoRatingBaseCount;
	public BaseWebElementControl gLInfoPremium;
	public BaseWebElementControl policyDeductibles;
	public BaseWebElementControl policyOtherDeductibles;
	public BaseWebElementControl locationName;
	public BaseWebElementControl buildingName;
	public BaseWebElementControl priorLossDetail;
	public BaseWebElementControl priorLossData;
	public BaseWebElementControl policyCoverages;
	public BaseWebElementControl equipmentBreakdown;
	public BaseWebElementControl terrorism;
	public BaseWebElementControl policyDeductiblesValues;
	public BaseWebElementControl policyNumberData;
	public BaseWebElementControl buildingNumber;
	public BaseWebElementControl surplusLinesTaxesValue;
	public BaseWebElementControl brokerFeeValue;
	public BaseWebElementControl termSurplusContribution;
	public BaseWebElementControl termSurplusContributionData;
	public BaseWebElementControl sltfValue;
	public BaseWebElementControl wordingSurplusLineInsurer;
	public BaseWebElementControl identityFraud;
	public BaseWebElementControl additionalAOI;
	public BaseWebElementControl lossAssessment;
	public BaseWebElementControl personalInjury;
	public BaseWebElementControl moldBuyUp;
	public BaseWebElementControl limitedWaterBackup;
	public BaseWebElementControl renovatedHome;
	public BaseWebElementControl stampingFeeValue;
	public BaseWebElementControl mwuaValue;
	public BaseWebElementControl endorsementValues;
	public BaseWebElementControl coveragesAndPremiumValues;
	public BaseWebElementControl insuredDetails;
	public BaseWebElementControl homeownerQuoteDetails;
	public BaseWebElementControl greenUpgradesValue;
	public BaseWebElementControl inspectionName;
	public BaseWebElementControl viewPolicySnapshotDetails;
	public BaseWebElementControl surplusContributionValue;
	public BaseWebElementControl declinationsData;
	public BaseWebElementControl insurerWording;
	public BaseWebElementControl discountsValue;
	public BaseWebElementControl empaSurcharge;

	public BaseWebElementControl insuredNameNAHO;

	public BaseWebElementControl fSLSOServeceFee;

	public ViewPolicySnapShot() {
		PageObject pageobject = new PageObject("ViewPolicySnapshot");
		apcName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_APCName")));
		apcLimit = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_APCLimit")));
		apcPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_APCPremium")));
		apcBusinessIncome = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BusinessIncome")));
		apcDriveways = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Driveways")));
		apcFences = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Fences")));
		apcAwnings = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Awnings")));
		apcLightpoles = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Lightpoles")));
		apcBoardwalks = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Boardwalks")));
		apcPlayGroundEquipment = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PlayGroundEquipment")));
		apcFountains = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Fountains")));
		apcOtherStructures = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Otherstructures")));
		apcSatellitedishes = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Satellitedishes")));
		apcPoolsandwaterfalls = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Poolsandwaterfalls")));
		apcCarports = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Carports")));
		apcMachinary = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Machinary")));
		apcOpennotfullyenclosed = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Open_notfullyenclosed")));
		apcUndergroundutilities = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Undergroundutilities")));
		businessIncomeData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BusinessIncomeData")));
		binderAccountReceivable = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BinderAccountReceivable")));
		binderAccountReceivableData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BinderAccountReceivableData")));
		binderCustomerProperty = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BinderCustomerProperty")));
		binderCustomerPropertyData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BinderCustomerPropertyData")));
		binderFineArts = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BinderFineArts")));
		binderFineArtsData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BinderFineArtsData")));
		binderFireExtinguisher = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BinderFireExtinguisher")));
		binderFireExtinguisherData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BinderFireExtinguisherData")));
		binderLockReplacement = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BinderLockReplacement")));
		binderLockReplacementData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BinderLockReplacementData")));
		binderRobbery = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BinderRobbery")));
		binderRobberyData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BinderRobberyData")));
		binderSewerDrain = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BinderSewerDrain")));
		binderSewerDrainData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BinderSewerDrainData")));
		binderSpoilage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BinderSpoilage")));
		binderSpoilageData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BinderSpoilageData")));
		binderTheftDisappearance = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BinderTheftDisappearance")));
		binderTheftDisappearanceData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BinderTheftDisappearanceData")));
		binderUtilityServicesDirectDamage = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BinderUtilityServicesDirectDamage")));
		binderUtilityServicesDirectDamageData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BinderUtilityServicesDirectDamageData")));
		binderUtilityServicesTimeElement = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BinderUtilityServicesTimeElement")));
		binderUtilityServicesTimeElementData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BinderUtilityServicesTimeElementData")));
		binderElectronic = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BinderElectronic")));
		binderElectronicData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BinderElectronicData")));
		binderExtendedPeriod = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BinderExtendedPeriod")));
		binderExtendedPeriodData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BinderExtendedPeriodData")));
		binderFireDepartment = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BinderFireDepartment")));
		binderFireDepartmentData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BinderFireDepartmentData")));
		binderNewlyAcquiredBpp = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BinderNewlyAcquiredBpp")));
		binderNewlyAcquiredBppData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BinderNewlyAcquiredBppData")));
		binderConstructedProperty = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BinderConstructedProperty")));
		binderConstructedPropertyData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BinderConstructedPropertyData")));
		binderOutdoorProperty = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BinderOutdoorProperty")));
		binderOutdoorPropertyData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BinderOutdoorPropertyData")));
		binderPerimeterExtension = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BinderPerimeterExtension")));
		binderPerimeterExtensionData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BinderPerimeterExtensionData")));
		binderPersonalEffects = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BinderPersonalEffects")));
		binderPersonalEffectsData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BinderPersonalEffectsData")));
		binderPropertyinTransit = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BinderPropertyinTransit")));
		binderPropertyinTransitData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BinderPropertyinTransitData")));
		binderPropertyOffPremises = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BinderPropertyOffPremises")));
		binderPropertyOffPremisesData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BinderPropertyOffPremisesData")));
		binderTenantGlass = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BinderTenantGlass")));
		binderTenantGlassData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BinderTenantGlassData")));
		binderValuablePapersAndRecords = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BinderValuablePapersAndRecords")));
		binderValuablePapersAndRecordsData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BinderValuablePapersAndRecordsData")));
		binderEqSpinkler = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BinderEqSpinkler")));
		binderEqSpinklerData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BinderEqSpinklerData")));
		binderEquipmentBreakdown = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BinderEquipmentBreakdown")));
		binderOrdinanceOrLaw = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BinderOrdinanceOrLaw")));
		binderOrdinanceOrLawData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BinderOrdinanceOrLawData")));
		binderOrdinanceOrLawvalue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BinderOrdinanceOrLawvalue")));
		binderWindDriven = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BinderWindDriven")));
		binderWindDrivenData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BinderWindDrivenData")));
		binderTerrorism = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BinderTerrorism")));
		binderTerrorismdata = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BinderTerrorismdata")));
		binderPackageA = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BinderPackageA")));
		goBackButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_BackButton")));

		propertyCovBldgLimit = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PropertyCovBldgLimit")));
		propertyCovBldgPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PropertyCovBldgPremium")));
		propertyCovBPPLimit = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PropertyCovBPPLimit")));
		propertyCovBPPPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PropertyCovBPPPremium")));
		propertyCovOrdinanceLimit = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PropertyCovOrdinanceLimit")));
		propertyCovOrdinancePremium = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PropertyCovOrdinancePremium")));
		locationLevelCovLimit = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LocationLevelCovLimit")));
		locationLevelCovPremium = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_LocationLevelCovPremium")));

		building = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Building")));
		location = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Location")));
		gLInfoClass = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GLInfoClass")));
		gLInfoRatingBaseCount = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GLInfoRatingBaseCount")));
		gLInfoPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GLInfoPremium")));
		policyDeductibles = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolicyDeductibles")));
		policyOtherDeductibles = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolicyOtherDeductibles")));
		locationLevelCovPremium = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_LocationLevelCovPremium")));
		buildingName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BuidingName")));
		locationName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LocationName")));
		priorLossDetail = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PriorLossDetail")));
		priorLossData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PriorLossData")));
		policyCoverages = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolicyCoverages")));
		equipmentBreakdown = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EquipmentBreakdown")));
		terrorism = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Terrorism")));
		policyDeductiblesValues = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PolicyDeductiblesValues")));
		policyNumberData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolicyNumberData")));
		buildingNumber = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BuildingNumber")));

		surplusLinesTaxesValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_SurplusLinesTaxesValue")));
		brokerFeeValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BrokerFeeValue")));
		termSurplusContribution = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_TermSurplusContribution")));
		termSurplusContributionData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_TermSurplusContributionData")));
		sltfValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_SltfValues")));
		wordingSurplusLineInsurer = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_WordingSurpluslineinsurer")));
		identityFraud = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_IdentityFraud")));
		additionalAOI = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AdditionalAOI")));
		lossAssessment = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LossAssessment")));
		personalInjury = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PersonalInjury")));
		moldBuyUp = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_MoldBuyUp")));
		limitedWaterBackup = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LimitedWaterBackup")));
		renovatedHome = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RenovatedHome")));
		stampingFeeValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_StampingFeeValue")));
		mwuaValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_MWUAValue")));
		endorsementValues = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EndorsementValues")));
		coveragesAndPremiumValues = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_CoveragesAndPremiumValue")));
		insuredDetails = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_InsuredDetails")));
		homeownerQuoteDetails = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_HomeownerQuoteDetails")));
		greenUpgradesValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GreenUpgradesValue")));
		viewPolicySnapshotDetails = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_ViewPolicySnapshotDetails")));
		inspectionName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_InspectionName")));
		surplusContributionValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_surplusContributionValue")));
		declinationsData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DeclinationsData")));
		insurerWording = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_InsurerWording")));
		discountsValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DiscountsValue")));
		empaSurcharge = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EMPASurcharge")));

		insuredNameNAHO = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_InsuredNameNAHO")));

		fSLSOServeceFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_FSLSOServeceFee")));
	}
}
