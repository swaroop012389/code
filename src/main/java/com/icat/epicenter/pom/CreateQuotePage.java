/** Program Description: Object Locators and methods defined in CreateQuote page
 *  Author			   : SMNetserv
 *  Date of Creation   : 01/11/2017
 **/

package com.icat.epicenter.pom;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
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
//import org.openqa.selenium.remote.tracing.opentelemetry.SeleniumSpanExporter;

public class CreateQuotePage extends BasePageControl {
	public TextFieldControl quoteName;
	public RadioButtonControl chooseCoverageByPolicy;
	public RadioButtonControl chooseCoverageByLocation;

	public RadioButtonControl namedStormRadio;
	public RadioButtonControl namedHurricaneRadio;

	public HyperLink namedStormDeductibleArrow;
	public HyperLink namedStormDeductibleOption;
	public HyperLink namedStormDeductibleOption1;

	// deductible applicability and occurrence are the same for NS and NH
	public HyperLink deductibleApplicability;
	public HyperLink deductibleApplicabilityOption;
	public HyperLink deductibleOccurrence;
	public HyperLink deductibleOccurrenceOption;
	public HyperLink namedStormDeductibleAppliesByArrow;
	public HyperLink namedStormDeductibleAppliesByOption;
	public HyperLink namedStormDeductibleApplicabilityArrow;
	public HyperLink namedStormDeductibleApplicabilityOption;
	public BaseWebElementControl namedStormData;

	public ButtonControl namedHurricaneDeductibleArrow;
	public ButtonControl namedHurricaneDeductibleArrow1;
	public ButtonControl namedHurricaneDeductibleOption;
	public ButtonControl namedHurricaneDeductibleAppliesByArrow;
	public ButtonControl namedHurricaneDeductibleAppliesByOption;
	public ButtonControl namedHurricaneDeductibleApplicabilityArrow;
	public ButtonControl namedHurricaneDeductibleApplicabilityOption;
	public BaseWebElementControl namedHurricaneData;
	public BaseWebElementControl namedHurricaneValue;

	public BaseWebElementControl namedStormHurricaneAppliesByData;
	public BaseWebElementControl namedStormHurricaneApplicabilityData;

	public ButtonControl aowhDeductibleArrow;
	public ButtonControl aowhDeductibleOption;
	public ButtonControl aowhDeductibleOptionNAHO;
	public ButtonControl aowhDeductibleAppliesByArrow;
	public ButtonControl aowhDeductibleAppliesByOption;
	public BaseWebElementControl aowhDeductibleApplicabilityData;
	public BaseWebElementControl aowhDeductibleData;
	public BaseWebElementControl aowhSelectListNAHO;

	public ButtonControl aoclDeductibleArrow;
	public ButtonControl aoclDeductibleOption;
	public BaseWebElementControl aoclDeductibleData;
	public BaseWebElementControl aoclDeductibleApplicabilityData;

	public ButtonControl earthquakeDeductibleArrow;
	public HyperLink earthquakeDeductibleOption;
	public ButtonControl earthquakeDeductibleAppliesByArrow;
	public HyperLink earthquakeDeductibleAppliesByOption;
	public BaseWebElementControl earthquakeData;
	public BaseWebElementControl earthquakeDeductibleApplicabilityData;
	public ButtonControl earthquakeDeductibleOptionEQHO;

	public ButtonControl floodByPolicyArrow;
	public HyperLink floodByPolicyOption;
	public ButtonControl floodDeductibleArrow;
	public ButtonControl floodDeductibleOption;
	public BaseWebElementControl floodData;

	public ButtonControl moldArrow;
	public HyperLink moldOption;
	public BaseWebElementControl moldData;
	public BaseWebElementControl moldDataNAHO;
	public BaseWebElementControl moldCleanup;
	public BaseWebElementControl mold;

	public ButtonControl ordinanceLawArrow;
	public HyperLink ordinanceLawOption;
	public BaseWebElementControl ordinanceLawData;
	public BaseWebElementControl ordinanceLaw;

	public ButtonControl equipmentBreakdownArrow;
	public HyperLink equipmentBreakdownOption;
	public BaseWebElementControl equipmentBreakdownData;

	public ButtonControl coverageExtensionPackageArrow;
	public HyperLink coverageExtensionPackageOption;
	public BaseWebElementControl coverageExtensionPackageData;

	public ButtonControl windDrivenRainArrow;
	public HyperLink windDrivenRainOption;
	public BaseWebElementControl windDrivenRainData;

	public ButtonControl mloiArrow;
	public HyperLink mloiOption;
	public BaseWebElementControl mloiData;

	public ButtonControl personalPropertyReplacementCostArrow;
	public HyperLink personalPropertyReplacementCostOption;
	public BaseWebElementControl personalPropertyReplacementCostData;

	public ButtonControl enhancedReplacementCostArrow;
	public HyperLink enhancedReplacementCostOption;
	public BaseWebElementControl enhancedReplacementCostData;
	public BaseWebElementControl enhancedReplacementCostDataHIHO;

	public HyperLink generalLiabilityArrow;
	public HyperLink generalLiabilityOption;
	public BaseWebElementControl generalLiabilityData;

	public HyperLink sinkholeArrow;
	public HyperLink sinkholeOption;
	public BaseWebElementControl sinkholeData;

	public ButtonControl eqslByPolicyArrow;
	public HyperLink eqslByPolicyOption;
	public HyperLink earthquakeSprinklerLeakageArrow;
	public HyperLink earthquakeSprinklerLeakageOption;
	public BaseWebElementControl earthquakeSprinklerLeakageData;

	public HyperLink electronicDataArrow;
	public HyperLink electronicDataOption;
	public BaseWebElementControl electronicData;

	public HyperLink fineArtsArrow;
	public HyperLink fineArtsOption;
	public BaseWebElementControl fineArtsData;

	public HyperLink valuablePapersArrow;
	public HyperLink valuablePapersOption;
	public BaseWebElementControl valuablePapersData;

	public HyperLink accountReceivableArrow;
	public HyperLink accountReceivableOption;
	public BaseWebElementControl accountReceivableData;

	public HyperLink utilityInterruptionArrow;
	public HyperLink utilityInterruptionOption;
	public BaseWebElementControl utilityInterruptionData;

	public ButtonControl floodCoverageArrow;
	public ButtonControl floodCoverageOption;
	public BaseWebElementControl floodCoverageData;

	public ButtonControl terrorismArrow;
	public ButtonControl terrorismOption;
	public BaseWebElementControl terrorismData;

	public ButtonControl restorationArrow;
	public ButtonControl restorationOption;
	public BaseWebElementControl restorationData;

	public ButtonControl indemnityArrow;
	public ButtonControl indemnityOption;
	public BaseWebElementControl indemnityData;

	public ButtonControl foodSpoilageArrow;
	public ButtonControl foodSpoilageOption;
	public BaseWebElementControl foodSpoilageData;

	public ButtonControl previous;
	public ButtonControl internalQuote;
	public ButtonControl getAQuote;

	public BaseWebElementControl pageName;
	public ButtonControl continueButton;
	public ButtonControl goBack;
	public ButtonControl override;
	public BaseWebElementControl loading;
	public ButtonControl quoteStep3;
	public ButtonControl locationStep1;

	public ButtonControl locationNamedHurricaneDeductibleArrow;
	public ButtonControl locationNamedHurricaneDeductibleOption;
	public ButtonControl locationNamedHurricaneDeductibleOption1;
	public ButtonControl buildingNamedHurricaneDeductibleArrow;
	public ButtonControl buildingNamedHurricaneDeductibleOption;
	public ButtonControl buildingNamedStormDeductibleArrow;
	public ButtonControl buildingNamedStormDeductibleOption;
	public ButtonControl buildingAOWHDeductibleArrow;
	public ButtonControl buildingAOWHDeductibleOption;

	public ButtonControl locationNamedStormDeductibleArrow;
	public HyperLink locationNamedStormDeductibleOption;
	public ButtonControl locationEarthquakeDeductibleArrow;
	public HyperLink locationEarthquakeDeductibleOption;
	public ButtonControl locationBPPEarthquakeDeductibleArrow;
	public HyperLink locationBPPEarthquakeDeductibleOption;
	public ButtonControl locationBldgEarthquakeDeductibleArrow;
	public HyperLink locationBldgEarthquakeDeductibleOption;
	public ButtonControl buildingEarthquakeDeductibleArrow;
	public HyperLink buildingEarthquakeDeductibleOption;
	public ButtonControl locationAOWHDeductibleArrow;
	public ButtonControl locationAOWHDeductibleOption;
	public ButtonControl locationPersonalPropertyReplacementCostArrow;
	public HyperLink locationPersonalPropertyReplacementCostOption;
	public ButtonControl locationEnhancedReplacementCostArrow;
	public ButtonControl locationEnhancedReplacementCostArrow1;
	public HyperLink locationEnhancedReplacementCostOption;
	public HyperLink locationEnhancedReplacementCostOption1;
	public ButtonControl locationOridnanceLawArrow;
	public HyperLink locationOridnanceLawOption;

	public ButtonControl commLocOrdLawArrow;
	public HyperLink commLocOrdLawOption;

	public ButtonControl locationFloodArrow;
	public HyperLink locationFloodOption;
	public ButtonControl locationEQSLArrow;
	public HyperLink locationEQSLOption;

	public TextFieldControl floodCovA;
	public TextFieldControl floodCovC;
	public HyperLink returnToFlood;
	public BaseWebElementControl floodCoverageError;
	public ButtonControl continueEndorsementButton;
	public ButtonControl cancelEndorsementButton;

	public TextFieldControl covAEQ;
	public TextFieldControl covBEQ;
	public TextFieldControl covCEQ;
	public TextFieldControl covDEQ;
	public BaseWebElementControl covAhelptext;
	public HyperLink coverageOptionLink;
	public BaseWebElementControl coverageOptionHelpText;
	public BaseWebElementControl globalErr;

	// Insured Values of NH
	public TextFieldControl insuredCovANH;
	public TextFieldControl insuredCovBNH;
	public TextFieldControl insuredCovCNH;
	public TextFieldControl insuredCovDNH;

	public CheckBoxControl internalQuoteChkBox;
	public ButtonControl addFloodDetailsLink;

	public BaseWebElementControl covDNHWarning;
	public BaseWebElementControl covDEQWarning;
	public BaseWebElementControl UWReferralWarning;

	public ButtonControl covDFlood;
	public HyperLink backToAccountOverview;
	public BaseWebElementControl covAFloodMin20000_WarningMessage;
	public ButtonControl closeButton;

	public HyperLink quoteLink;
	public BaseWebElementControl quoteDetails;
	public BaseWebElementControl minimumCost;

	public BaseWebElementControl covAFloodLimitgretaer;
	public BaseWebElementControl covCFloodLimitgretaer;
	public BaseWebElementControl covAandCLimitLess;
	public BaseWebElementControl covAandCLessLimit;
	public BaseWebElementControl combinedLimitMin;
	public BaseWebElementControl locationDwelling;

	public BaseWebElementControl noLossLetterMsg;
	public BaseWebElementControl noLossLetterMsg1;

	public BaseWebElementControl referralMsg;
	public BaseWebElementControl coverageWarning;
	public ButtonControl modifyForms;
	public ButtonControl continueToForms;
	public HyperLink coverageExtensionPackageLink;

	public BaseWebElementControl packageValues;
	public BaseWebElementControl packageBAccountsReceivable;
	public BaseWebElementControl packageBAccountsReceivablevalue;
	public BaseWebElementControl packageBCustomersProperty;
	public BaseWebElementControl packageBCustomersPropertyvalue;
	public BaseWebElementControl packageBFineArts;
	public BaseWebElementControl packageBFineArtsvalue;
	public BaseWebElementControl packageBFireExtinguisher;
	public BaseWebElementControl packageBFireExtinguishervalue;
	public BaseWebElementControl packageBLockReplacement;
	public BaseWebElementControl packageBLockReplacementvalue;
	public BaseWebElementControl packageBRobbery;
	public BaseWebElementControl packageBRobberyvalue;
	public BaseWebElementControl packageBSewerDrain;
	public BaseWebElementControl packageBSewerDrainvalue;
	public BaseWebElementControl packageBSpoilage;
	public BaseWebElementControl packageBSpoilagevalue;
	public BaseWebElementControl packageBTheft;
	public BaseWebElementControl packageBTheftvalue;
	public BaseWebElementControl packageBDirectDamage;
	public BaseWebElementControl packageBDirectDamagevalue;
	public BaseWebElementControl packageBTimeElement;
	public BaseWebElementControl packageBTimeElementvalue;
	public BaseWebElementControl packageBElectronic;
	public BaseWebElementControl packageBElectronicvalue;
	public BaseWebElementControl packageBExtendedPeriod;
	public BaseWebElementControl packageBExtendedPeriodvalue;
	public BaseWebElementControl packageBFireDepartment;
	public BaseWebElementControl packageBFireDepartmentvalue;
	public BaseWebElementControl packageBNewlyAcquiredBpp;
	public BaseWebElementControl packageBNewlyAcquiredBppvalue;
	public BaseWebElementControl packageBConstructedProperty;
	public BaseWebElementControl packageBConstructedPropertyvalue;
	public BaseWebElementControl packageBOutdoorProperty;
	public BaseWebElementControl packageBOutdoorPropertyvalue;
	public BaseWebElementControl packageBPerimeterExtensionBpp;
	public BaseWebElementControl packageBPerimeterExtensionBppvalue;
	public BaseWebElementControl packageBPerimeterExtensionBuilding;
	public BaseWebElementControl packageBPerimeterExtensionBuildingvalue;
	public BaseWebElementControl packageBPersonalEffects;
	public BaseWebElementControl packageBPersonalEffectsvalue;
	public BaseWebElementControl packageBPropertyinTransit;
	public BaseWebElementControl packageBPropertyinTransitvalue;
	public BaseWebElementControl packageBPropertyOffPremises;
	public BaseWebElementControl packageBPropertyOffPremisesvalue;
	public BaseWebElementControl packageBTenantGlass;
	public BaseWebElementControl packageBTenantGlassvalue;
	public BaseWebElementControl packageBValuablePapersAndRecords;
	public BaseWebElementControl packageBValuablePapersAndRecordsvalue;

	// NAHO
	public RadioButtonControl formType_HO3;
	public RadioButtonControl formType_HO5;

	public HyperLink namedStormArrow_NAHO;
	public ButtonControl aopDeductibleArrow;
	public ButtonControl aopDeductibleOption;
	public BaseWebElementControl aopDeductibleData;

	public ButtonControl additionalDwellingCoverageArrow;
	public HyperLink additionalDwellingCoverageOption;

	public ButtonControl businessPropertyArrow;
	public HyperLink businessPropertyOption;

	public ButtonControl limitedPoolArrow;
	public HyperLink limitedPoolOption;

	public ButtonControl limitedWaterSumpArrow;
	public HyperLink limitedWaterSumpOption;

	public ButtonControl lossAssessmentArrow;
	public HyperLink lossAssessmentOption;

	public ButtonControl identityFraudArrow;
	public HyperLink identityFraudOption;

	public ButtonControl specialLimitsArrow;
	public HyperLink specialLimitsOption;

	public ButtonControl personalInjuryArrow;
	public HyperLink personalInjuryOption;

	public ButtonControl personalPropertyArrow;
	public HyperLink personalPropertyOption;

	public ButtonControl serviceLineArrow;
	public HyperLink serviceLineOption;

	public ButtonControl equipmentBreakdownArrow_NAHO;
	public ButtonControl ordinanceLawArrow_NAHO;

	public ButtonControl moldPropertyArrow;
	public HyperLink moldPropertyOption;
	public BaseWebElementControl moldPropertyText;

	public ButtonControl cancelButton;
	public ButtonControl adjustQuote;

	public TextFieldControl coverageADwelling;
	public TextFieldControl coverageBOtherStructures;
	public TextFieldControl coverageCPersonalProperty;
	public TextFieldControl coverageDFairRental;
	public ButtonControl coverageEArrow;
	public ButtonControl coverageEOption;
	public ButtonControl coverageFArrow;
	public ButtonControl coverageFOption;

	// Fetching values of drop downs
	public BaseWebElementControl additionalDwellingCovDedValue;
	public BaseWebElementControl poolDedValue;
	public BaseWebElementControl waterBackUpDedValue;
	public BaseWebElementControl lossAssessmentDedValue;
	public BaseWebElementControl identityFraudDedValue;
	public BaseWebElementControl personalInjuryDedValue;
	public BaseWebElementControl lineCoverageDedValue;
	public BaseWebElementControl eQBreakdownDedValue;
	public BaseWebElementControl ordinanceOrLawDedValue;
	public BaseWebElementControl moldPropertyDedValue;
	public BaseWebElementControl moldLiabilityDedValue;
	public BaseWebElementControl covEValue;
	public BaseWebElementControl covFValue;

	public BaseWebElementControl aopDedValue;
	public BaseWebElementControl noofUnitsReferralMessage;
	public BaseWebElementControl covAMinimumReferralMessage;
	public BaseWebElementControl roofAgeAlertMessage;
	public BaseWebElementControl warningMessages;

	public ButtonControl eqLossAssessmentArrow;
	public HyperLink eqLossAssessmentOption;
	public BaseWebElementControl eqLossAssessmentData;
	public BaseWebElementControl aopNSEQData;
	public BaseWebElementControl earthQuakePriorLossWarning;

	public BaseWebElementControl coverageEWarningmessage;
	public BaseWebElementControl warningMessagePrior1970YearOfConstruction;
	public BaseWebElementControl warningMessageForYearOfConstruction;
	public BaseWebElementControl errorMessageForYearOfConstruction;
	public ButtonControl continueEndorsementBtn;
	public BaseWebElementControl roofAgealertmessage;
	public BaseWebElementControl lossAssessmentData;
	public BaseWebElementControl dwellingCovData;
	public BaseWebElementControl errorMessage;
	public BaseWebElementControl errorMessageWarningPage;
	public TextFieldControl buildingValue;
	public CheckBoxControl includeAPCCheckbox;
	public TextFieldControl bPPValue;
	public BaseWebElementControl niDisplay;
	public BaseWebElementControl flood;
	public BaseWebElementControl covBFloodGrid;
	public TextFieldControl floodGrid1;
	public TextFieldControl floodGrid3;
	public BaseWebElementControl locDwellingLink;

	public ButtonControl greenUpgradesArrow;
	public BaseWebElementControl greenUpgradesData;
	public ButtonControl greenUpgradesYesOption;
	public ButtonControl greenUpgradesNoOption;
	public BaseWebElementControl greenUpgradesLabel;
	public BaseWebElementControl monthlyLimitOfIndemnityData;
	public BaseWebElementControl wdrWarning;
	public TextFieldControl bIValue;
	public BaseWebElementControl earthquakeAppliesByData;
	public BaseWebElementControl aowhDeductibleAppliesByData;
	public BaseWebElementControl warningMessage;
	public BaseWebElementControl warningMessageforAdjustments;
	public BaseWebElementControl alertError;
	public BaseWebElementControl globalWarning;
	public BaseWebElementControl buildingNumbersLabel;
	public BaseWebElementControl ordinancelawData;
	public HyperLink ordLawOption;
	public BaseWebElementControl quote1Status;
	public BaseWebElementControl quote2StatusDelete;
	public TextFieldControl covA_NHinputBox;
	public TextFieldControl covC_NHinputBox;
	public TextFieldControl cova_NH;
	public BaseWebElementControl enhancedRCValue;
	public BaseWebElementControl ordinanceLawValue;
	public BaseWebElementControl earthQuakeValue;
	public BaseWebElementControl minimunCostWarningMsg;
	public ButtonControl locationNamedHurricaneDedValueOption;
	public HyperLink locationEarthquakeDeductibleOption1;
	public ButtonControl aOCLDedValueOption;
	public ButtonControl aOWHNamedHurricaneDedValueArrow;
	public ButtonControl aOWHNamedHurricaneDedValueOption;
	public HyperLink locationOrdLawOption;
	public TextFieldControl insuredCovCNHName;

	public CreateQuotePage() {
		PageObject pageobject = new PageObject("CreateQuote");
		quoteName = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Quotename")));
		chooseCoverageByPolicy = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_CoverageByPolicy")));
		chooseCoverageByLocation = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_CoverageByLocation")));

		namedStormRadio = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_NamedStorm")));
		namedHurricaneRadio = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_NamedHurricane")));

		deductibleApplicability = new HyperLink(By.xpath(pageobject.getXpath("xp_DeductibleApplicabilityArrow")));
		deductibleApplicabilityOption = new HyperLink(
				By.xpath(pageobject.getXpath("xp_DeductibleApplicabilityOption")));
		deductibleOccurrence = new HyperLink(By.xpath(pageobject.getXpath("xp_DeductibleOccurrenceArrow")));
		deductibleOccurrenceOption = new HyperLink(By.xpath(pageobject.getXpath("xp_DeductibleOccurrenceOption")));

		namedStormDeductibleArrow = new HyperLink(By.xpath(pageobject.getXpath("xp_NamedStormArrow")));
		namedStormDeductibleOption = new HyperLink(By.xpath(pageobject.getXpath("xp_NamedStormOption")));
		namedStormDeductibleOption1 = new HyperLink(By.xpath(pageobject.getXpath("xp_NamedStormOption1")));

		namedStormDeductibleAppliesByArrow = new HyperLink(
				By.xpath(pageobject.getXpath("xp_NamedStormAppliesByArrow")));
		namedStormDeductibleAppliesByOption = new HyperLink(
				By.xpath(pageobject.getXpath("xp_NamedStrormAppliesByOption")));
		namedStormDeductibleApplicabilityArrow = new HyperLink(
				By.xpath(pageobject.getXpath("xp_NamedStormDeductibleApplicabilityArrow")));
		namedStormDeductibleApplicabilityOption = new HyperLink(
				By.xpath(pageobject.getXpath("xp_NamedStormDeductibleApplicabilityOption")));
		namedStormData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NamedStormData")));

		namedHurricaneDeductibleArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_NamedHurricaneArrow")));
		namedHurricaneDeductibleArrow1 = new ButtonControl(By.xpath(pageobject.getXpath("xp_NamedHurricaneArrow1")));
		namedHurricaneDeductibleOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_NamedHurricaneOption")));
		namedHurricaneDeductibleAppliesByArrow = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_NamedHurricaneAppliesByArrow")));
		namedHurricaneDeductibleAppliesByOption = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_NamedHurricaneAppliesByOption")));
		namedHurricaneDeductibleApplicabilityArrow = new HyperLink(
				By.xpath(pageobject.getXpath("xp_NamedHurricaneDeductibleApplicabilityArrow")));
		namedHurricaneDeductibleApplicabilityOption = new HyperLink(
				By.xpath(pageobject.getXpath("xp_NamedHurricaneDeductibleApplicabilityOption")));
		namedHurricaneData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NamedHurricanceData")));
		namedHurricaneValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NamedHurricaneValue")));

		namedStormHurricaneAppliesByData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_NamedStormAndHurricanceAppliesByData")));
		namedStormHurricaneApplicabilityData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_NamedStormAndHurricanceApplicabilityData")));

		aowhDeductibleArrow = new HyperLink(By.xpath(pageobject.getXpath("xp_AOWHArrow")));
		aowhDeductibleOption = new HyperLink(By.xpath(pageobject.getXpath("xp_AOWHOption")));
		aowhDeductibleOptionNAHO = new HyperLink(By.xpath(pageobject.getXpath("xp_AOWHOptionNAHO")));
		aowhDeductibleAppliesByArrow = new HyperLink(By.xpath(pageobject.getXpath("xp_AOWHAppliesByArrow")));
		aowhDeductibleAppliesByOption = new HyperLink(By.xpath(pageobject.getXpath("xp_AOWHAppliesByOption")));
		aowhDeductibleApplicabilityData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_AOWHApplicabilityData")));
		aowhDeductibleData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AOWHDeductibleData")));
		aowhSelectListNAHO = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AOWHSelectListNAHO")));

		aoclDeductibleArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_AOCLArrow")));
		aoclDeductibleOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_AOCLOption")));
		aoclDeductibleData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AOCLDeductibleData")));
		aoclDeductibleApplicabilityData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_AOCLDeductibleApplicabilityData")));

		earthquakeDeductibleArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_EarthquakeArrow")));
		earthquakeDeductibleOption = new HyperLink(By.xpath(pageobject.getXpath("xp_EarthquakeOption")));
		earthquakeDeductibleAppliesByArrow = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_EarthquakeAppliesByArrow")));
		earthquakeDeductibleAppliesByOption = new HyperLink(
				By.xpath(pageobject.getXpath("xp_EarthquakeAppliesByOption")));
		earthquakeDeductibleApplicabilityData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_EarthquakeApplicabilityData")));
		earthquakeData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EarthquakeData")));
		earthquakeDeductibleOptionEQHO = new ButtonControl(By.xpath(pageobject.getXpath("xp_EarthquakeOptionEQHO")));

		floodByPolicyArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_FloodByPolicyArrow")));
		floodByPolicyOption = new HyperLink(By.xpath(pageobject.getXpath("xp_FloodByPolicyOption")));
		floodDeductibleArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_FloodDeductibleArrow")));
		floodDeductibleOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_FloodDeductibleOption")));
		floodData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_FloodData")));

		moldArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_MoldArrow")));
		moldOption = new HyperLink(By.xpath(pageobject.getXpath("xp_MoldOption")));
		moldData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_MoldData")));
		moldDataNAHO = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_MoldDataNAHO")));
		moldCleanup = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_MoldCleanup")));
		mold = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Mold")));

		ordinanceLawArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_OrdinanceLawArrow")));
		ordinanceLawOption = new HyperLink(By.xpath(pageobject.getXpath("xp_OrdinanceLawOption")));
		ordinanceLawData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OrdinanceLawData")));
		ordinanceLaw = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OrdinanceLaw")));

		equipmentBreakdownArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_EquipmentBreakdownArrow")));
		equipmentBreakdownOption = new HyperLink(By.xpath(pageobject.getXpath("xp_EquipmentBreakdownOption")));
		equipmentBreakdownData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EquipmentBreakdownData")));

		coverageExtensionPackageArrow = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_CoverageExtensionPackageArrow")));
		coverageExtensionPackageOption = new HyperLink(
				By.xpath(pageobject.getXpath("xp_CoverageExtensionPackageOption")));
		coverageExtensionPackageData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_CoverageExtensionPackageData")));

		windDrivenRainArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_WindDrivenArrow")));
		windDrivenRainOption = new HyperLink(By.xpath(pageobject.getXpath("xp_WindDrivenOption")));
		windDrivenRainData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_WindDrivenData")));

		mloiArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_MLOIArrow")));
		mloiOption = new HyperLink(By.xpath(pageobject.getXpath("xp_MLOIOption")));
		mloiData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_MLOIData")));

		personalPropertyReplacementCostArrow = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_PersonalPropertyReplacementCostArrow")));
		personalPropertyReplacementCostOption = new HyperLink(
				By.xpath(pageobject.getXpath("xp_PersonalPropertyReplacementCostOption")));
		personalPropertyReplacementCostData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PersonalPropertyReplacementCostData")));

		enhancedReplacementCostArrow = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_EnhancedReplacementCostArrow")));
		enhancedReplacementCostOption = new HyperLink(
				By.xpath(pageobject.getXpath("xp_EnhancedReplacementCostOption")));
		enhancedReplacementCostData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_EnhancedReplacementCostData")));
		enhancedReplacementCostDataHIHO = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_EnhancedReplacementCostDataHIHO")));

		generalLiabilityArrow = new HyperLink(By.xpath(pageobject.getXpath("xp_GeneralLiabilityArrow")));
		generalLiabilityOption = new HyperLink(By.xpath(pageobject.getXpath("xp_GeneralLiabilityOption")));
		generalLiabilityData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GeneralLiabilityData")));

		sinkholeArrow = new HyperLink(By.xpath(pageobject.getXpath("xp_SinkholeArrow")));
		sinkholeOption = new HyperLink(By.xpath(pageobject.getXpath("xp_SinkholeOption")));
		sinkholeData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_SinkholeData")));

		earthquakeSprinklerLeakageArrow = new HyperLink(
				By.xpath(pageobject.getXpath("xp_EarthquakeSprinklerLeakageArrow")));
		earthquakeSprinklerLeakageOption = new HyperLink(
				By.xpath(pageobject.getXpath("xp_EarthquakeSprinklerLeakageOption")));
		earthquakeSprinklerLeakageData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_EarthquakeSprinklerData")));
		eqslByPolicyArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_EQSLByPolicyArrow")));
		eqslByPolicyOption = new HyperLink(By.xpath(pageobject.getXpath("xp_EQSLByPolicyOption")));
		earthquakeSprinklerLeakageArrow = new HyperLink(
				By.xpath(pageobject.getXpath("xp_EarthquakeSprinklerLeakageArrow")));
		earthquakeSprinklerLeakageOption = new HyperLink(
				By.xpath(pageobject.getXpath("xp_EarthquakeSprinklerLeakageOption")));
		earthquakeSprinklerLeakageData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_EarthquakeSprinklerData")));

		electronicDataArrow = new HyperLink(By.xpath(pageobject.getXpath("xp_ElectronicDataArrow")));
		electronicDataOption = new HyperLink(By.xpath(pageobject.getXpath("xp_ElectronicDataOption")));
		electronicData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ElectronicData")));

		fineArtsArrow = new HyperLink(By.xpath(pageobject.getXpath("xp_FineArtsArrow")));
		fineArtsOption = new HyperLink(By.xpath(pageobject.getXpath("xp_FineArtsOption")));
		fineArtsData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_FineArtsData")));

		valuablePapersArrow = new HyperLink(By.xpath(pageobject.getXpath("xp_ValuablePapersArrow")));
		valuablePapersOption = new HyperLink(By.xpath(pageobject.getXpath("xp_ValuablePapersOption")));
		valuablePapersData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ValuablePapersData")));

		accountReceivableArrow = new HyperLink(By.xpath(pageobject.getXpath("xp_AccountReceivableArrow")));
		accountReceivableOption = new HyperLink(By.xpath(pageobject.getXpath("xp_AccountReceivableOption")));
		accountReceivableData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AccountsReceivableData")));

		utilityInterruptionArrow = new HyperLink(By.xpath(pageobject.getXpath("xp_UtilityInterruptionArrow")));
		utilityInterruptionOption = new HyperLink(By.xpath(pageobject.getXpath("xp_UtilityInterruptionOption")));
		utilityInterruptionData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_UtilityInterruptionData")));

		floodCoverageArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_FloodCoverageArrow")));
		floodCoverageOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_FloodCoverageOption")));
		floodCoverageData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_FloodCoverageData")));

		terrorismArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_TerrorismArrow")));
		terrorismOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_TerrorismOption")));
		terrorismData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TerrorismData")));

		restorationArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_IncreasedPeriodofRestorationArrow")));
		restorationOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_IncreasedPeriodofRestorationOption")));
		restorationData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_IncreasedPeriodofRestorationData")));

		indemnityArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_ExtendedIndemnityArrow")));
		indemnityOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_ExtendedIndemnityOption")));
		indemnityData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ExtendedIndemnityData")));

		foodSpoilageArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_FoodSpoilageArrow")));
		foodSpoilageOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_FoodSpoilageOption")));
		foodSpoilageData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_FoodSpoilageData")));

		previous = new ButtonControl(By.xpath(pageobject.getXpath("xp_Previous")));
		internalQuote = new ButtonControl(By.xpath(pageobject.getXpath("xp_InternalQuote")));
		getAQuote = new ButtonControl(By.xpath(pageobject.getXpath("xp_GetAQuote")));

		pageName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PageName")));
		continueButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_Continue")));
		goBack = new ButtonControl(By.xpath(pageobject.getXpath("xp_GoBack")));
		override = new ButtonControl(By.xpath(pageobject.getXpath("xp_Override")));
		loading = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Loading")));
		quoteStep3 = new ButtonControl(By.xpath(pageobject.getXpath("xp_QuoteStep3")));
		locationStep1 = new ButtonControl(By.xpath(pageobject.getXpath("xp_LocationStep1")));

		// NH deductible applicability by location, set by location
		locationNamedHurricaneDeductibleArrow = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_LocationNamedHurricaneArrow")));
		locationNamedHurricaneDeductibleOption = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_LocationNamedHurricaneOption")));
		locationNamedHurricaneDeductibleOption1 = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_LocationNamedHurricaneOption1")));

		// NH deductible applicability by building, set by location
		buildingNamedHurricaneDeductibleArrow = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_BuildingNamedHurricaneArrow")));
		buildingNamedHurricaneDeductibleOption = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_BuildingNamedHurricaneOption")));

		// NS deductible applicability by location, set by location
		locationNamedStormDeductibleArrow = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_LocationNamedStormArrow")));
		locationNamedStormDeductibleOption = new HyperLink(
				By.xpath(pageobject.getXpath("xp_LocationNamedStormOption")));

		// NS deductible applicability by building, set by location
		buildingNamedStormDeductibleArrow = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_BuildingNamedStormArrow")));
		buildingNamedStormDeductibleOption = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_BuildingNamedStormOption")));

		// AOWH deductible applicability by location, set by location
		locationAOWHDeductibleArrow = new HyperLink(By.xpath(pageobject.getXpath("xp_LocationAOWHArrow")));
		locationAOWHDeductibleOption = new HyperLink(By.xpath(pageobject.getXpath("xp_LocationAOWHOption")));

		// AOWH deductible applicability by building, set by location
		buildingAOWHDeductibleArrow = new HyperLink(By.xpath(pageobject.getXpath("xp_BuildingAOWHArrow")));
		buildingAOWHDeductibleOption = new HyperLink(By.xpath(pageobject.getXpath("xp_BuildingAOWHOption")));

		// EQ deductible - follows AOWN
		locationEarthquakeDeductibleArrow = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_LocationEarthquakeArrow")));
		locationEarthquakeDeductibleOption = new HyperLink(
				By.xpath(pageobject.getXpath("xp_LocationEarthquakeOption")));

		buildingEarthquakeDeductibleArrow = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_BuildingEarthquakeArrow")));
		buildingEarthquakeDeductibleOption = new HyperLink(
				By.xpath(pageobject.getXpath("xp_BuildingEarthquakeOption")));

		locationBPPEarthquakeDeductibleArrow = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_LocationBPPEarthquakeArrow")));
		locationBPPEarthquakeDeductibleOption = new HyperLink(
				By.xpath(pageobject.getXpath("xp_LocationBPPEarthquakeOption")));
		locationBldgEarthquakeDeductibleArrow = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_LocationBldgEarthquakeArrow")));
		locationBldgEarthquakeDeductibleOption = new HyperLink(
				By.xpath(pageobject.getXpath("xp_LocationBldgEarthquakeOption")));

		locationPersonalPropertyReplacementCostArrow = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_LocationPersonalPropertyArrow")));
		locationPersonalPropertyReplacementCostOption = new HyperLink(
				By.xpath(pageobject.getXpath("xp_LocationPersonalPropertyOption")));

		locationEnhancedReplacementCostArrow = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_LocationEnhancedreplacementCostArrow")));
		locationEnhancedReplacementCostArrow1 = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_LocationEnhancedreplacementCostArrow1")));
		locationEnhancedReplacementCostOption = new HyperLink(
				By.xpath(pageobject.getXpath("xp_LocationEnhancedreplacementCostOption")));
		locationEnhancedReplacementCostOption1 = new HyperLink(
				By.xpath(pageobject.getXpath("xp_LocationEnhancedreplacementCostOption1")));

		locationOridnanceLawArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_LocationOridnanceLawArrow")));
		locationOridnanceLawOption = new HyperLink(By.xpath(pageobject.getXpath("xp_LocationOridnanceLawOption")));

		locationFloodArrow = new ButtonControl(By.xpath((pageobject.getXpath("xp_LocationFloodArrow"))));
		locationFloodOption = new HyperLink(By.xpath((pageobject.getXpath("xp_LocationFloodOption"))));
		locationEQSLArrow = new ButtonControl(By.xpath((pageobject.getXpath("xp_LocationEQSLArrow"))));
		locationEQSLOption = new HyperLink(By.xpath((pageobject.getXpath("xp_LocationEQSLOption"))));

		commLocOrdLawArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_LocationOrdLaw")));
		commLocOrdLawOption = new HyperLink(By.xpath(pageobject.getXpath("xp_LocationOrdLawOption")));

		floodCovA = new TextFieldControl(By.xpath(pageobject.getXpath("xp_FloodCovA")));
		floodCovC = new TextFieldControl(By.xpath(pageobject.getXpath("xp_FloodCovC")));
		returnToFlood = new HyperLink(By.xpath(pageobject.getXpath("xp_ReturnToFlood")));
		floodCoverageError = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_FloodCoverageError")));
		continueEndorsementButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ContinueEndorsementChanges")));
		cancelEndorsementButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_CancelEndorsementChanges")));

		covAEQ = new TextFieldControl(By.xpath(pageobject.getXpath("xp_CovAEQ")));
		covBEQ = new TextFieldControl(By.xpath(pageobject.getXpath("xp_CovBEQ")));
		covCEQ = new TextFieldControl(By.xpath(pageobject.getXpath("xp_CovCEQ")));
		covDEQ = new TextFieldControl(By.xpath(pageobject.getXpath("xp_CovDEQ")));
		covAhelptext = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_covAhelptext")));
		coverageOptionLink = new HyperLink(By.xpath(pageobject.getXpath("xp_coverageOptionLink")));
		coverageOptionHelpText = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_coverageOptionHelptext")));
		globalErr = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GlobalErr")));

		// Insured Values of NH
		insuredCovANH = new TextFieldControl(By.xpath(pageobject.getXpath("xp_InsuredCovA_NH")));
		insuredCovBNH = new TextFieldControl(By.xpath(pageobject.getXpath("xp_InsuredCovB_NH")));
		insuredCovCNH = new TextFieldControl(By.xpath(pageobject.getXpath("xp_InsuredCovC_NH")));
		insuredCovDNH = new TextFieldControl(By.xpath(pageobject.getXpath("xp_InsuredCovD_NH")));

		internalQuoteChkBox = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_InternalQuoteChkBox")));
		addFloodDetailsLink = new ButtonControl(By.xpath(pageobject.getXpath("xp_AddFloodDetailsLink")));
		covDNHWarning = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CovDNHWarning")));
		covDEQWarning = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CovDEQWarning")));
		UWReferralWarning = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_UWReferralWarning")));

		covDFlood = new ButtonControl(By.xpath(pageobject.getXpath("xp_CovDFlood")));
		backToAccountOverview = new HyperLink(By.xpath(pageobject.getXpath("xp_BackToAccountOverview")));
		covAFloodMin20000_WarningMessage = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_CovAFloodMin20000_WarningMessage")));
		closeButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_CloseButton")));

		quoteLink = new HyperLink(By.xpath(pageobject.getXpath("xp_QuoteLink")));
		quoteDetails = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_QuoteDetails")));
		minimumCost = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_MinimumCost")));

		covAFloodLimitgretaer = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CovAFloodLimitgretaer")));
		covCFloodLimitgretaer = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CovCFloodLimitgretaer")));
		covAandCLimitLess = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CovAandCLimitLess")));
		covAandCLessLimit = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CovAandCLessLimit")));
		combinedLimitMin = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CombinedLimitMin")));
		locationDwelling = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LocationDwelling")));

		noLossLetterMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NoLossLetterMsg")));
		noLossLetterMsg1 = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NoLossLetterMsg1")));

		referralMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ReferralMsg")));
		coverageWarning = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CoverageWarning")));
		modifyForms = new ButtonControl(By.xpath(pageobject.getXpath("xp_ModifyFormsButton")));
		continueToForms = new ButtonControl(By.xpath(pageobject.getXpath("xp_ContinueToFormsButton")));
		coverageExtensionPackageLink = new HyperLink(By.xpath(pageobject.getXpath("xp_CoverageExtensionPackageLink")));

		packageBAccountsReceivable = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBAccountsReceivable")));
		packageBAccountsReceivablevalue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBAccountsReceivablevalue")));
		packageBCustomersProperty = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBCustomersProperty")));
		packageBCustomersPropertyvalue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBCustomersPropertyvalue")));
		packageBFineArts = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PackageBFineArts")));
		packageBFineArtsvalue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PackageBFineArtsvalue")));
		packageBFireExtinguisher = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBFireExtinguisher")));
		packageBFireExtinguishervalue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBFireExtinguishervalue")));
		packageBLockReplacement = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBLockReplacement")));
		packageBLockReplacementvalue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBLockReplacementvalue")));
		packageBRobbery = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PackageBRobbery")));
		packageBRobberyvalue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PackageBRobberyvalue")));
		packageBSewerDrain = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PackageBSewerDrain")));
		packageBSewerDrainvalue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBSewerDrainvalue")));
		packageBSpoilage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PackageBSpoilage")));
		packageBSpoilagevalue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PackageBSpoilagevalue")));
		packageBTheft = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PackageBTheft")));
		packageBTheftvalue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PackageBTheftvalue")));
		packageBDirectDamage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PackageBDirectDamage")));
		packageBDirectDamagevalue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBDirectDamagevalue")));
		packageBTimeElement = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PackageBTimeElement")));
		packageBTimeElementvalue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBTimeElementvalue")));
		packageBElectronic = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PackageBElectronic")));
		packageBElectronicvalue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBElectronicvalue")));
		packageBExtendedPeriod = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PackageBExtendedPeriod")));
		packageBExtendedPeriodvalue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBExtendedPeriodvalue")));
		packageBFireDepartment = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PackageBFireDepartment")));
		packageBFireDepartmentvalue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBFireDepartmentvalue")));
		packageBNewlyAcquiredBpp = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBNewlyAcquiredBpp")));
		packageBNewlyAcquiredBppvalue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBNewlyAcquiredBppvalue")));
		packageBConstructedProperty = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBConstructedProperty")));
		packageBConstructedPropertyvalue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBConstructedPropertyvalue")));
		packageBOutdoorProperty = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBOutdoorProperty")));
		packageBOutdoorPropertyvalue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBOutdoorPropertyvalue")));
		packageBPerimeterExtensionBpp = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBPerimeterExtensionBpp")));
		packageBPerimeterExtensionBppvalue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBPerimeterExtensionBppvalue")));
		packageBPerimeterExtensionBuilding = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBPerimeterExtensionBuilding")));
		packageBPerimeterExtensionBuildingvalue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBPerimeterExtensionBuildingvalue")));
		packageBPersonalEffects = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBPersonalEffects")));
		packageBPersonalEffectsvalue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBPersonalEffectsvalue")));
		packageBPropertyinTransit = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBPropertyinTransit")));
		packageBPropertyinTransitvalue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBPropertyinTransitvalue")));
		packageBPropertyOffPremises = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBPropertyOffPremises")));
		packageBPropertyOffPremisesvalue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBPropertyOffPremisesvalue")));
		packageBTenantGlass = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PackageBTenantGlass")));
		packageBTenantGlassvalue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBTenantGlassvalue")));
		packageBValuablePapersAndRecords = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBValuablePapersAndRecords")));
		packageBValuablePapersAndRecordsvalue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PackageBValuablePapersAndRecordsvalue")));

		// NAHO
		formType_HO3 = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_HO3")));
		formType_HO5 = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_HO5")));

		namedStormArrow_NAHO = new HyperLink(By.xpath(pageobject.getXpath("xp_NamedStormArrow_NAHO")));
		aopDeductibleArrow = new HyperLink(By.xpath(pageobject.getXpath("xp_AOPArrow")));
		aopDeductibleOption = new HyperLink(By.xpath(pageobject.getXpath("xp_AOPOption")));
		aopDeductibleData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AOPData")));

		additionalDwellingCoverageArrow = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_AdditionalDwellingCoverageArrow")));
		additionalDwellingCoverageOption = new HyperLink(
				By.xpath(pageobject.getXpath("xp_AdditionalDwellingCoverageOption")));

		businessPropertyArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_BusinessPropertyArrow")));
		businessPropertyOption = new HyperLink(By.xpath(pageobject.getXpath("xp_BusinessPropertyOption")));

		limitedPoolArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_LimitedPoolArrow")));
		limitedPoolOption = new HyperLink(By.xpath(pageobject.getXpath("xp_LimitedPoolOption")));

		limitedWaterSumpArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_LimitedWaterSumpArrow")));
		limitedWaterSumpOption = new HyperLink(By.xpath(pageobject.getXpath("xp_LimitedWaterSumpOption")));

		lossAssessmentArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_LossAssessmentArrow")));
		lossAssessmentOption = new HyperLink(By.xpath(pageobject.getXpath("xp_LossAssessmentOption")));

		identityFraudArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_IdentityFraudArrow")));
		identityFraudOption = new HyperLink(By.xpath(pageobject.getXpath("xp_IdentityFraudOption")));

		specialLimitsArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_IncreaseSpecialLimitsArrow")));
		specialLimitsOption = new HyperLink(By.xpath(pageobject.getXpath("xp_IncreaseSpecialLimitsOption")));

		personalInjuryArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_PersonalInjuryArrow")));
		personalInjuryOption = new HyperLink(By.xpath(pageobject.getXpath("xp_PersonalInjuryOption")));

		personalPropertyArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_SpecialPersonalPropertyArrow")));
		personalPropertyOption = new HyperLink(By.xpath(pageobject.getXpath("xp_SpecialPersonalPropertyOption")));

		serviceLineArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_ServiceLineArrow")));
		serviceLineOption = new HyperLink(By.xpath(pageobject.getXpath("xp_ServiceLineOption")));

		equipmentBreakdownArrow_NAHO = new ButtonControl(By.xpath(pageobject.getXpath("xp_EQBArrow_NAHO")));
		ordinanceLawArrow_NAHO = new HyperLink(By.xpath(pageobject.getXpath("xp_OrdinanceLawArrow_NAHO")));

		moldPropertyArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_MoldPropertyArrow")));
		moldPropertyOption = new HyperLink(By.xpath(pageobject.getXpath("xp_MoldPropertyOption")));
		moldPropertyText = new HyperLink(By.xpath(pageobject.getXpath("xp_MoldPropertyText")));

		cancelButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_Cancel")));
		adjustQuote = new ButtonControl(By.xpath(pageobject.getXpath("xp_AdjustQuote")));

		coverageADwelling = new TextFieldControl(By.xpath(pageobject.getXpath("xp_CoverageADwelling")));
		coverageBOtherStructures = new TextFieldControl(By.xpath(pageobject.getXpath("xp_CoverageBOtherStructures")));
		coverageCPersonalProperty = new TextFieldControl(By.xpath(pageobject.getXpath("xp_CoverageCPersonalProperty")));
		coverageDFairRental = new TextFieldControl(By.xpath(pageobject.getXpath("xp_CoverageDFairRental")));
		coverageEArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_CoverageEArrow")));
		coverageEOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_CoverageEOption")));
		coverageFArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_CoverageFArrow")));
		coverageFOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_CoverageFOption")));

		additionalDwellingCovDedValue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_AdditionalDwellingCovDedValue")));
		poolDedValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PoolDedValue")));
		waterBackUpDedValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_WaterBackUpDedValue")));
		lossAssessmentDedValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LossAssessmentDedValue")));
		identityFraudDedValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_IdentityFraudDedValue")));
		personalInjuryDedValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PersonalInjuryDedValue")));
		lineCoverageDedValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LineCoverageDedValue")));
		eQBreakdownDedValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EQBreakdownDedValue")));
		ordinanceOrLawDedValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OrdinanceOrLawDedValue")));
		moldPropertyDedValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_MoldPropertyDedValue")));
		moldLiabilityDedValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_MoldLiabilityDedValue")));
		covEValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CovEValue")));
		covFValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CovFValue")));

		aopDedValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AOPData")));
		noofUnitsReferralMessage = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_NoofUnitsReferralMessage")));
		covAMinimumReferralMessage = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_CovAMinimumReferralMessage")));
		roofAgeAlertMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RoofAgeAlertMessage")));
		warningMessages = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_WarningMessages")));

		eqLossAssessmentArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_EQLossAssessmentArrow")));
		eqLossAssessmentOption = new HyperLink(By.xpath(pageobject.getXpath("xp_EQLossAssessmentOption")));
		eqLossAssessmentData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EQLossAssessmentData")));
		aopNSEQData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AOPNSEQData")));
		earthQuakePriorLossWarning = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_EarthQuakePriorLossWarning")));

		coverageEWarningmessage = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_CoverageEWarningMessage")));
		warningMessagePrior1970YearOfConstruction = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_WarningMesssageForPrior1970")));

		warningMessageForYearOfConstruction = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_WarningMesssageForYearOfConstruction")));
		errorMessageForYearOfConstruction = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_ErrorMessageForYearOfConstruction")));

		continueEndorsementBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_ContinueEndorsementBtn")));

		roofAgealertmessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RoofAgealertmessage")));
		lossAssessmentData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LossAssessmentData")));
		dwellingCovData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DwellingCovData")));
		errorMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ErrorMessage")));
		errorMessageWarningPage = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_ErrorMessageWarningPage")));
		buildingValue = new TextFieldControl(By.xpath(pageobject.getXpath("xp_BuildingValue")));
		includeAPCCheckbox = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_IncludeAPCCheckbox")));
		bPPValue = new TextFieldControl(By.xpath(pageobject.getXpath("xp_BPPValue")));
		niDisplay = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NIDisplay")));
		flood = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Flood")));
		covBFloodGrid = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_FloodGrid")));
		floodGrid1 = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Floodgrid1")));
		floodGrid3 = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Floodgrid3")));
		locDwellingLink = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LocDwellingLink")));
		packageValues = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PackageValues")));
		greenUpgradesArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_GreenUpgradesArrow")));
		greenUpgradesData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GreenUpgradesData")));
		greenUpgradesYesOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_GreenUpgradesYesOption")));
		greenUpgradesNoOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_GreenUpgradesNoOption")));
		greenUpgradesLabel = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GreenUpgradesLabel")));
		monthlyLimitOfIndemnityData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_MonthlyLimitOfIndemnityData")));
		wdrWarning = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_WDRWarning")));
		bIValue = new TextFieldControl(By.xpath(pageobject.getXpath("xp_BIValue")));
		earthquakeAppliesByData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_EarthquakeAppliesByData")));
		aowhDeductibleAppliesByData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_AOWHDeductibleAppliesByData")));
		warningMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_WarningMessage")));
		warningMessageforAdjustments = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_WarningMessageforAdjustments")));
		alertError = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AlertErrore")));
		globalWarning = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GlobalWarning")));
		buildingNumbersLabel = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BuildingNumberLabel")));
		ordinancelawData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OrdinanceLawData")));
		ordLawOption = new HyperLink(By.xpath(pageobject.getXpath("xp_OridnanceLawSelection")));
		quote1Status = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Quote1Status")));
		quote2StatusDelete = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Quote2StatusDelete")));
		covA_NHinputBox = new TextFieldControl(By.xpath(pageobject.getXpath("xp_CovA_NHinputBox")));
		covC_NHinputBox = new TextFieldControl(By.xpath(pageobject.getXpath("xp_CovC_NHinputBox")));
		cova_NH = new TextFieldControl(By.xpath(pageobject.getXpath("xp_CovA_NH")));
		enhancedRCValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EnhancedRCValue")));
		ordinanceLawValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OrdinanceLawValue")));
		earthQuakeValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EarthQuakeValue")));
		minimunCostWarningMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_MinimunCostWarningMsg")));
		locationNamedHurricaneDedValueOption = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_LocationNamedHurricaneOption")));
		locationEarthquakeDeductibleOption1 = new HyperLink(
				By.xpath(pageobject.getXpath("xp_LocationEarthquakeOption1")));
		aOCLDedValueOption = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_AOCL_Deductible_PercentageOrAmt_option")));
		aOWHNamedHurricaneDedValueArrow = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_AOWH_NH_Deductible_PercentageOrAmt_Arrow")));
		aOWHNamedHurricaneDedValueOption = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_AOWH_NH_Deductible_PercentageOrAmt_option")));
		locationOrdLawOption = new HyperLink(By.xpath(pageobject.getXpath("xp_LocationOrdinanceLawOption1")));
		insuredCovCNHName = new TextFieldControl(By.xpath(pageobject.getXpath("xp_InsuredCovC_NHName")));
	}

	// ##### COMMERCIAL METHODS #####

	public AccountOverviewPage enterQuoteDetailsCommercialSmoke(Map<String, String> Data) {
		enterDeductiblesCommercial(Data);
		addOptionalCoverageDetails(Data);
		if (getAQuote.checkIfElementIsPresent() && getAQuote.checkIfElementIsDisplayed()) {
			getAQuote.scrollToElement();
			getAQuote.click();
		}

		if (continueEndorsementButton.checkIfElementIsPresent()
				&& continueEndorsementButton.checkIfElementIsDisplayed()) {
			continueEndorsementButton.scrollToElement();
			continueEndorsementButton.click();
		}
		if (continueButton.checkIfElementIsPresent()) {
			continueButton.scrollToElement();
			continueButton.click();
			continueButton.waitTillInVisibilityOfElement(60);
		}

		if (pageName.getData().contains("Account Overview")) {
			return new AccountOverviewPage();
		}
		return null;
	}

	// THIS METHOD IS CALLED FROM COMMERCIAL DATA TESTS
	public AccountOverviewPage enterQuoteDetailsCommercial(Map<String, String> Data) {
		enterDeductiblesCommercial(Data);
		addAdditionalCoveragesCommercial(Data);
		if (Data.get("AddRemoveForms").equals("Yes")) {
			if (modifyForms.checkIfElementIsPresent()) {
				modifyForms.scrollToElement();
				modifyForms.click();
			} else if (continueToForms.checkIfElementIsPresent()) {
				continueToForms.scrollToElement();
				continueToForms.click();
			}
			ModifyForms userForms = new ModifyForms();
			userForms.addRemoveForms(Data);
		}

		if (getAQuote.checkIfElementIsPresent() && getAQuote.checkIfElementIsDisplayed()) {
			getAQuote.scrollToElement();
			getAQuote.click();
		}

		// coverage overrides on modify forms page
		if (override.checkIfElementIsPresent() && override.checkIfElementIsDisplayed()) {
			ModifyForms modifyFormsOverrides = new ModifyForms();
			// may need to get smart about setting this for each building at some point
			for (int i = 1; i <= 15; i++) {
				if (modifyFormsOverrides.overrideCoverageCheckbox.formatDynamicPath(i).checkIfElementIsPresent()
						&& modifyFormsOverrides.overrideCoverageCheckbox.formatDynamicPath(i)
								.checkIfElementIsDisplayed()) {
					modifyFormsOverrides.overrideCoverageCheckbox.formatDynamicPath(i).select();
				}
			}
			override.scrollToElement();
			override.click();
		}

		if (continueEndorsementButton.checkIfElementIsPresent()
				&& continueEndorsementButton.checkIfElementIsDisplayed()) {
			continueEndorsementButton.scrollToElement();
			continueEndorsementButton.click();
		}
		if (continueButton.checkIfElementIsPresent()) {
			continueButton.scrollToElement();
			continueButton.click();
			continueButton.waitTillInVisibilityOfElement(60);
		}

		if (pageName.getData().contains("Account Overview")) {
			return new AccountOverviewPage();
		}
		return null;
	}

	public AccountOverviewPage enterQuoteDetailsCommercialNew(Map<String, String> Data) {
		enterDeductiblesCommercialNew(Data);
		addAdditionalCoveragesCommercial(Data);
		if (Data.containsKey("AddRemoveForms")) {
			if (Data.get("AddRemoveForms").equals("Yes")) {
				if (modifyForms.checkIfElementIsPresent()) {
					modifyForms.scrollToElement();
					modifyForms.click();
				} else if (continueToForms.checkIfElementIsPresent()) {
					continueToForms.scrollToElement();
					continueToForms.click();
				}
				ModifyForms userForms = new ModifyForms();
				userForms.addRemoveFormsNew(Data);
			}
		}
		if (getAQuote.checkIfElementIsPresent() && getAQuote.checkIfElementIsDisplayed()) {
			getAQuote.scrollToElement();
			getAQuote.click();
		}

		if (override.checkIfElementIsPresent() && override.checkIfElementIsDisplayed()) {
			override.scrollToElement();
			override.click();
		}

		if (continueEndorsementButton.checkIfElementIsPresent()
				&& continueEndorsementButton.checkIfElementIsDisplayed()) {
			continueEndorsementButton.scrollToElement();
			continueEndorsementButton.click();
		}
		if (continueButton.checkIfElementIsPresent()) {
			continueButton.scrollToElement();
			continueButton.click();
			continueButton.waitTillInVisibilityOfElement(60);
		}

		if (pageName.getData().contains("Account Overview")) {
			return new AccountOverviewPage();
		}
		return null;
	}

	public void enterDeductiblesCommercial(Map<String, String> Data) {

		// can't change deductible applicability or occurrence in an endorsement
		if (Data.get("TransactionType").equalsIgnoreCase("new business")
				|| Data.get("TransactionType").equalsIgnoreCase("renewal")
				|| Data.get("TransactionType").equalsIgnoreCase("rewrite policy")) {
			if (!Data.get("Peril").equals("EQ")) {
				// set deductible applicability and occurrence first - elements are the same for
				// NS and NH
				System.out.println(
						"deductibleApplicability there? = " + deductibleApplicability.checkIfElementIsDisplayed());
				deductibleApplicability.scrollToElement();
				deductibleApplicability.click();
				deductibleApplicabilityOption.formatDynamicPath(Data.get("DeductibleApplicability")).click();
			}

			if (Data.get("Peril").equals("AOP")) {
				System.out.println("Setting Ded Occurrence");
				deductibleOccurrence.scrollToElement();
				deductibleOccurrence.click();
				deductibleOccurrenceOption.formatDynamicPath(Data.get("DeductibleOccurrence")).scrollToElement();
				deductibleOccurrenceOption.formatDynamicPath(Data.get("DeductibleOccurrence")).click();
			}

			if (!Data.get("Peril").equals("EQ")) {
				System.out.println("Setting AOWH Ded App");
				aowhDeductibleAppliesByArrow.scrollToElement();
				aowhDeductibleAppliesByArrow.click();
				aowhDeductibleAppliesByOption.formatDynamicPath(Data.get("AOWHDeductibleApplicability"))
						.scrollToElement();
				aowhDeductibleAppliesByOption.formatDynamicPath(Data.get("AOWHDeductibleApplicability")).click();
			}

		}

		if (Data.get("CoverageBy").equalsIgnoreCase("By Location")) {
			int locationCount = Integer.parseInt(Data.get("LocCount"));
			coverageByLocationCommercial(Data, locationCount);
		} else {
			if (chooseCoverageByPolicy.checkIfElementIsPresent()
					&& chooseCoverageByPolicy.checkIfElementIsDisplayed()) {
				chooseCoverageByPolicy.click();
			}
			if (!Data.get("DeductibleType").equalsIgnoreCase("")) {
				if (Data.get("DeductibleType").equalsIgnoreCase("Named Storm")) {

					namedStormRadio.scrollToElement();
					namedStormRadio.click();
					loading.waitTillInVisibilityOfElement(60);

					if (!Data.get("DeductibleValue").equals("")) {
						namedStormDeductibleArrow.scrollToElement();
						namedStormDeductibleArrow.click();
						namedStormDeductibleOption.formatDynamicPath(Data.get("DeductibleValue")).scrollToElement();
						namedStormDeductibleOption.formatDynamicPath(Data.get("DeductibleValue")).click();
					}

				} else if (Data.get("DeductibleType").equalsIgnoreCase("Named Hurricane")) {
					namedHurricaneRadio.scrollToElement();
					namedHurricaneRadio.click();
					loading.waitTillInVisibilityOfElement(60);

					if (!Data.get("DeductibleValue").equals("")) {
						namedHurricaneDeductibleArrow.scrollToElement();
						namedHurricaneDeductibleArrow.click();
						namedHurricaneDeductibleOption.formatDynamicPath(Data.get("DeductibleValue")).scrollToElement();
						namedHurricaneDeductibleOption.formatDynamicPath(Data.get("DeductibleValue")).click();
					}
				}
			}

			if (earthquakeDeductibleArrow.checkIfElementIsPresent()
					&& earthquakeDeductibleArrow.checkIfElementIsDisplayed()) {
				if (!Data.get("EQDeductibleValue").equals("")) {
					earthquakeDeductibleArrow.scrollToElement();
					earthquakeDeductibleArrow.click();
					earthquakeDeductibleOption.formatDynamicPath(Data.get("EQDeductibleValue")).scrollToElement();
					earthquakeDeductibleOption.formatDynamicPath(Data.get("EQDeductibleValue")).click();

					// can't change deductible applicability or occurrence in an endorsement, but
					// might add EQ coverage and need to set it
					if (earthquakeDeductibleAppliesByArrow.checkIfElementIsPresent()
							&& earthquakeDeductibleAppliesByArrow.checkIfElementIsEnabled()) {
						if (!Data.get("EQDeductibleApplicability").equals("")) {
							earthquakeDeductibleAppliesByArrow.scrollToElement();
							earthquakeDeductibleAppliesByArrow.click();
							earthquakeDeductibleAppliesByOption.formatDynamicPath(Data.get("EQDeductibleApplicability"))
									.scrollToElement();
							earthquakeDeductibleAppliesByOption.formatDynamicPath(Data.get("EQDeductibleApplicability"))
									.click();
						}
					}
				}
			}

			if (aowhDeductibleArrow.checkIfElementIsPresent() && aowhDeductibleArrow.checkIfElementIsDisplayed()) {
				if (!Data.get("AOWHDeductibleValue").equals("")) {
					aowhDeductibleArrow.scrollToElement();
					aowhDeductibleArrow.click();
					aowhDeductibleOption.formatDynamicPath(Data.get("AOWHDeductibleValue")).scrollToElement();
					aowhDeductibleOption.formatDynamicPath(Data.get("AOWHDeductibleValue")).click();
				}
			}

			// flood is yes/no
			if (floodByPolicyArrow.checkIfElementIsPresent() && floodByPolicyArrow.checkIfElementIsDisplayed()) {
				if (!Data.get("FloodDeductible").equals("")) {
					floodByPolicyArrow.scrollToElement();
					floodByPolicyArrow.click();
					floodByPolicyOption.formatDynamicPath(Data.get("FloodDeductible")).scrollToElement();
					floodByPolicyOption.formatDynamicPath(Data.get("FloodDeductible")).click();
				}
			}

			// eqsl by Policy
			if (eqslByPolicyArrow.checkIfElementIsPresent() && eqslByPolicyArrow.checkIfElementIsDisplayed()) {
				if (!Data.get("FloodDeductible").equals("")) {
					eqslByPolicyArrow.scrollToElement();
					eqslByPolicyArrow.click();
					eqslByPolicyOption.formatDynamicPath(Data.get("EarthquakeSprinklerLeakage")).scrollToElement();
					eqslByPolicyOption.formatDynamicPath(Data.get("EarthquakeSprinklerLeakage")).click();
				}
			}

			// tria is yes/no
			if (floodByPolicyArrow.checkIfElementIsPresent() && floodByPolicyArrow.checkIfElementIsDisplayed()) {
				if (!Data.get("FloodDeductible").equals("")) {
					floodByPolicyArrow.scrollToElement();
					floodByPolicyArrow.click();
					floodByPolicyOption.formatDynamicPath(Data.get("FloodDeductible")).scrollToElement();
					floodByPolicyOption.formatDynamicPath(Data.get("FloodDeductible")).click();
				}
			}
		}

		if (aoclDeductibleArrow.checkIfElementIsPresent() && aoclDeductibleArrow.checkIfElementIsDisplayed()) {
			if (!Data.get("AOCLDeductibleValue").equals("")) {
				aoclDeductibleArrow.scrollToElement();
				aoclDeductibleArrow.click();
				aoclDeductibleOption.formatDynamicPath(Data.get("AOCLDeductibleValue")).scrollToElement();
				aoclDeductibleOption.formatDynamicPath(Data.get("AOCLDeductibleValue")).click();
			}
		}

	}

	public void enterDeductiblesCommercialNew(Map<String, String> Data) {

		if (Data.get("CoverageBy").equalsIgnoreCase("By Location")) {
			String locationNumber = Data.get("LocCount");
			int locationCount = Integer.parseInt(locationNumber);
			coverageByLocationCommercialNew(Data, locationCount);
		} else {
			if (chooseCoverageByPolicy.checkIfElementIsPresent()
					&& chooseCoverageByPolicy.checkIfElementIsDisplayed()) {
				chooseCoverageByPolicy.click();
			}
			if (!Data.get("DeductibleType").equalsIgnoreCase("")) {
				if (Data.get("DeductibleType").equalsIgnoreCase("Named Storm")) {

					namedStormRadio.scrollToElement();
					namedStormRadio.click();
					loading.waitTillInVisibilityOfElement(60);

					if (!Data.get("DeductibleValue").equals("")) {
						namedStormDeductibleArrow.scrollToElement();
						namedStormDeductibleArrow.click();
						namedStormDeductibleOption.formatDynamicPath(Data.get("DeductibleValue")).scrollToElement();
						namedStormDeductibleOption.formatDynamicPath(Data.get("DeductibleValue")).click();
					}

					if (!Data.get("DeductibleApplicability").equals("")) {
						namedStormDeductibleAppliesByArrow.scrollToElement();
						namedStormDeductibleAppliesByArrow.click();
						namedStormDeductibleAppliesByOption.formatDynamicPath(Data.get("DeductibleApplicability"))
								.scrollToElement();
						namedStormDeductibleAppliesByOption.formatDynamicPath(Data.get("DeductibleApplicability"))
								.click();
					}

					// Added if condition to check the element presence - 1171
					if (namedStormDeductibleApplicabilityArrow.checkIfElementIsPresent()
							&& namedStormDeductibleApplicabilityArrow.checkIfElementIsDisplayed()) {
						if (!Data.get("DeductibleOccurence").equals("")) {
							namedStormDeductibleApplicabilityArrow.scrollToElement();
							namedStormDeductibleApplicabilityArrow.click();
							namedStormDeductibleApplicabilityOption.formatDynamicPath(Data.get("DeductibleOccurence"))
									.scrollToElement();
							namedStormDeductibleApplicabilityOption.formatDynamicPath(Data.get("DeductibleOccurence"))
									.click();
						}
					}
				} else if (Data.get("DeductibleType").equalsIgnoreCase("Named Hurricane")) {
					namedHurricaneRadio.scrollToElement();
					namedHurricaneRadio.click();
					loading.waitTillInVisibilityOfElement(60);

					if (!Data.get("DeductibleValue").equals("")) {
						namedHurricaneDeductibleArrow.scrollToElement();
						namedHurricaneDeductibleArrow.click();
						namedHurricaneDeductibleOption.formatDynamicPath(Data.get("DeductibleValue")).scrollToElement();
						namedHurricaneDeductibleOption.formatDynamicPath(Data.get("DeductibleValue")).click();
					}

					if (!Data.get("DeductibleApplicability").equals("")) {
						namedHurricaneDeductibleAppliesByArrow.scrollToElement();
						namedHurricaneDeductibleAppliesByArrow.click();
						namedHurricaneDeductibleAppliesByOption.formatDynamicPath(Data.get("DeductibleApplicability"))
								.scrollToElement();
						namedHurricaneDeductibleAppliesByOption.formatDynamicPath(Data.get("DeductibleApplicability"))
								.click();
					}

					if (namedHurricaneDeductibleApplicabilityArrow.checkIfElementIsPresent()
							&& namedHurricaneDeductibleApplicabilityArrow.checkIfElementIsDisplayed()) {
						if (!Data.get("DeductibleOccurence").equals("")) {
							namedHurricaneDeductibleApplicabilityArrow.scrollToElement();
							namedHurricaneDeductibleApplicabilityArrow.click();
							namedHurricaneDeductibleApplicabilityOption
									.formatDynamicPath(Data.get("DeductibleOccurence")).scrollToElement();
							namedHurricaneDeductibleApplicabilityOption
									.formatDynamicPath(Data.get("DeductibleOccurence")).click();
						}
					}
				}
			}

			// Assertions.passTest("Create Quote Page", "EQ Deductible Type is" +
			// Data.get("EQDeductibleValue"));

			if (earthquakeDeductibleArrow.checkIfElementIsPresent()
					&& earthquakeDeductibleArrow.checkIfElementIsDisplayed()) {
				if (!Data.get("EQDeductibleValue").equals("")) {
					earthquakeDeductibleArrow.scrollToElement();
					earthquakeDeductibleArrow.click();
					earthquakeDeductibleOption.formatDynamicPath(Data.get("EQDeductibleValue")).scrollToElement();
					earthquakeDeductibleOption.formatDynamicPath(Data.get("EQDeductibleValue")).click();
				}
			}

			if (earthquakeDeductibleAppliesByArrow.checkIfElementIsPresent()
					&& earthquakeDeductibleAppliesByArrow.checkIfElementIsDisplayed()) {
				if (!Data.get("EQDeductibleApplicability").equals("")) {
					earthquakeDeductibleAppliesByArrow.scrollToElement();
					earthquakeDeductibleAppliesByArrow.click();
					earthquakeDeductibleAppliesByOption.formatDynamicPath(Data.get("EQDeductibleApplicability"))
							.scrollToElement();
					earthquakeDeductibleAppliesByOption.formatDynamicPath(Data.get("EQDeductibleApplicability"))
							.click();
				}
			}

			if (aowhDeductibleArrow.checkIfElementIsPresent() && aowhDeductibleArrow.checkIfElementIsDisplayed()) {
				if (!Data.get("AOWHDeductibleValue").equals("")) {
					aowhDeductibleArrow.scrollToElement();
					aowhDeductibleArrow.click();
					aowhDeductibleOption.formatDynamicPath(Data.get("AOWHDeductibleValue")).scrollToElement();
					aowhDeductibleOption.formatDynamicPath(Data.get("AOWHDeductibleValue")).click();
				}
			}
			if (aowhDeductibleAppliesByArrow.checkIfElementIsPresent()
					&& aowhDeductibleAppliesByArrow.checkIfElementIsDisplayed()) {
				if (!Data.get("AOWHDeductibleApplicability").equals("")) {
					aowhDeductibleAppliesByArrow.scrollToElement();
					aowhDeductibleAppliesByArrow.click();
					aowhDeductibleAppliesByOption.formatDynamicPath(Data.get("AOWHDeductibleApplicability"))
							.scrollToElement();
					aowhDeductibleAppliesByOption.formatDynamicPath(Data.get("AOWHDeductibleApplicability")).click();
				}
			}
			waitTime(2);
			if (Data.containsKey("AOCLDeductibleValue")) {
				if (aoclDeductibleArrow.checkIfElementIsPresent() && aoclDeductibleArrow.checkIfElementIsDisplayed()) {
					if (!Data.get("AOCLDeductibleValue").equals("")) {
						aoclDeductibleArrow.scrollToElement();
						aoclDeductibleArrow.click();
						aoclDeductibleOption.formatDynamicPath(Data.get("AOCLDeductibleValue")).scrollToElement();
						aoclDeductibleOption.formatDynamicPath(Data.get("AOCLDeductibleValue")).click();
					}
				}
			}
		}

		// flood is yes/no
		if (floodDeductibleArrow.checkIfElementIsPresent() && floodDeductibleArrow.checkIfElementIsDisplayed()) {
			if (!Data.get("FloodDeductible").equals("")) {
				floodDeductibleArrow.scrollToElement();
				floodDeductibleArrow.click();
				floodDeductibleOption.formatDynamicPath(Data.get("FloodDeductible")).scrollToElement();
				floodDeductibleOption.formatDynamicPath(Data.get("FloodDeductible")).click();
			}
		}

	}

	public void setCoveragesByLocation(Map<String, String> Data, int locationCount) {
		chooseCoverageByLocation.scrollToElement();
		chooseCoverageByLocation.click();
		waitTime(1);
		for (int i = 1; i <= locationCount; i++) {
			if (locationNamedHurricaneDeductibleArrow.checkIfElementIsPresent()
					&& !Data.get("L" + i + "-NamedHurricaneDed").equals("")) {
				locationNamedHurricaneDeductibleArrow.formatDynamicPath(i).scrollToElement();
				locationNamedHurricaneDeductibleArrow.formatDynamicPath(i).click();
				locationNamedHurricaneDeductibleOption.formatDynamicPath(i, Data.get("L" + i + "-NamedHurricaneDed"))
						.click();
			}
			if (locationEarthquakeDeductibleArrow.checkIfElementIsPresent()
					&& !Data.get("L" + i + "-EarthquakeDed").equals("")) {
				locationEarthquakeDeductibleArrow.formatDynamicPath(i).scrollToElement();
				locationEarthquakeDeductibleArrow.formatDynamicPath(i).click();
				locationEarthquakeDeductibleOption.formatDynamicPath(i, Data.get("L" + i + "-EarthquakeDed")).click();
				loading.waitTillInVisibilityOfElement(60);
			}
			if (aoclDeductibleArrow.checkIfElementIsPresent() && !Data.get("L" + i + "-AOCLDed").equals("")) {
				aoclDeductibleArrow.scrollToElement();
				aoclDeductibleArrow.click();
				aoclDeductibleOption.formatDynamicPath(Data.get("L" + i + "-AOCLDed")).click();
			}
			if (aowhDeductibleArrow.checkIfElementIsPresent() && !Data.get("L" + i + "-AOWHDed").equals("")) {
				aowhDeductibleArrow.scrollToElement();
				aowhDeductibleArrow.click();
				aowhDeductibleOption.formatDynamicPath(Data.get("L" + i + "-AOWHDed")).click();
			}
			if (floodDeductibleArrow.checkIfElementIsPresent() && !Data.get("L" + i + "-FloodDed").equals("")) {
				floodDeductibleArrow.scrollToElement();
				floodDeductibleArrow.click();
				floodDeductibleOption.formatDynamicPath(Data.get("L" + i + "-FloodDed")).click();
			}

			if (locationOridnanceLawArrow.formatDynamicPath(i - 1).checkIfElementIsPresent()) {
				locationOridnanceLawArrow.formatDynamicPath(i - 1).scrollToElement();
				locationOridnanceLawArrow.formatDynamicPath(i - 1).click();
				if (Data.get("L" + i + "-OrdinanceLaw").equals("")) {
					locationOridnanceLawOption.formatDynamicPath(i - 1, "None").click();
				} else {
					locationOridnanceLawOption.formatDynamicPath(i, Data.get("L" + i + "-OrdinanceLaw")).click();
				}
			}

			if (Data.containsKey("PropertyReplCost")) {
				if (locationPersonalPropertyReplacementCostArrow.checkIfElementIsPresent()
						&& !Data.get("L" + i + "-PropertyReplCost").equals("")) {
					locationPersonalPropertyReplacementCostArrow.formatDynamicPath(i).scrollToElement();
					locationPersonalPropertyReplacementCostArrow.formatDynamicPath(i).click();
					locationPersonalPropertyReplacementCostOption
							.formatDynamicPath(i, Data.get("L" + i + "-PropertyReplCost")).click();
				}
			}
			if (Data.containsKey("EnhancedReplCost")) {
				if (locationEnhancedReplacementCostArrow.checkIfElementIsPresent()
						&& !Data.get("L" + i + "-EnhancedReplCost").equals("")) {
					locationEnhancedReplacementCostArrow.formatDynamicPath(i).scrollToElement();
					locationEnhancedReplacementCostArrow.formatDynamicPath(i).click();
					locationEnhancedReplacementCostOption.formatDynamicPath(i, Data.get("L" + i + "-EnhancedReplCost"))
							.click();
				}
			}
		}
	}

	public void coverageByLocationCommercial(Map<String, String> Data, int locationCount) {
		chooseCoverageByLocation.scrollToElement();
		chooseCoverageByLocation.click();
		loading.waitTillInVisibilityOfElement(60);

		System.out.println("locationCount = " + locationCount);

		if (Data.get("DeductibleType").equalsIgnoreCase("Named Hurricane")) {
			namedHurricaneRadio.scrollToElement();
			namedHurricaneRadio.click();
			loading.waitTillInVisibilityOfElement(60);

		} else if (Data.get("DeductibleType").equalsIgnoreCase("Named Storm")) {
			namedStormRadio.scrollToElement();
			namedStormRadio.click();
			loading.waitTillInVisibilityOfElement(8);
		}

		for (int i = 1; i <= locationCount; i++) {
			// named storm/named hurricane
			if (Data.get("DeductibleApplicability").equals("By Building")) {
				if (Data.get("DeductibleType").equalsIgnoreCase("Named Hurricane")) {
					if (buildingNamedHurricaneDeductibleArrow.formatDynamicPath(i - 1).checkIfElementIsPresent()
							&& buildingNamedHurricaneDeductibleArrow.formatDynamicPath(i - 1)
									.checkIfElementIsDisplayed()) {
						if (!Data.get("L" + i + "-Deductible").equals("")) {
							buildingNamedHurricaneDeductibleArrow.formatDynamicPath(i - 1).scrollToElement();
							buildingNamedHurricaneDeductibleArrow.formatDynamicPath(i - 1).click();
							buildingNamedHurricaneDeductibleOption
									.formatDynamicPath(i, Data.get("L" + i + "-Deductible")).click();
						}
					}
				} else if (Data.get("DeductibleType").equalsIgnoreCase("Named Storm")) {
					if (buildingNamedStormDeductibleArrow.formatDynamicPath(i - 1).checkIfElementIsPresent()
							&& buildingNamedStormDeductibleArrow.formatDynamicPath(i - 1).checkIfElementIsDisplayed()) {
						if (!Data.get("L" + i + "-Deductible").equals("")) {
							buildingNamedStormDeductibleArrow.formatDynamicPath(i - 1).scrollToElement();
							buildingNamedStormDeductibleArrow.formatDynamicPath(i - 1).click();
							buildingNamedStormDeductibleOption
									.formatDynamicPath(i - 1, Data.get("L" + i + "-Deductible")).click();
						}
					}
				}

			} else if (Data.get("DeductibleApplicability").equals("By Location")) {
				// by location dropdowns
				if (Data.get("DeductibleType").equalsIgnoreCase("Named Hurricane")) {
					if (locationNamedHurricaneDeductibleArrow.formatDynamicPath(i - 1).checkIfElementIsPresent()
							&& locationNamedHurricaneDeductibleArrow.formatDynamicPath(i - 1)
									.checkIfElementIsDisplayed()) {
						if (!Data.get("L" + i + "-Deductible").equals("")) {
							locationNamedHurricaneDeductibleArrow.formatDynamicPath(i - 1).scrollToElement();
							locationNamedHurricaneDeductibleArrow.formatDynamicPath(i - 1).click();
							locationNamedHurricaneDeductibleOption
									.formatDynamicPath(i - 1, Data.get("L" + i + "-Deductible")).click();
						}
					}
				} else if (Data.get("DeductibleType").equalsIgnoreCase("Named Storm")) {
					if (locationNamedStormDeductibleArrow.formatDynamicPath(i - 1).checkIfElementIsPresent()
							&& locationNamedStormDeductibleArrow.formatDynamicPath(i - 1).checkIfElementIsDisplayed()) {
						if (!Data.get("L" + i + "-Deductible").equals("")) {
							locationNamedStormDeductibleArrow.formatDynamicPath(i - 1).scrollToElement();
							locationNamedStormDeductibleArrow.formatDynamicPath(i - 1).click();
							locationNamedStormDeductibleOption
									.formatDynamicPath(i - 1, Data.get("L" + i + "-Deductible")).click();
						}
					}
				}

			}

			// aowh
			if (Data.get("AOWHDeductibleApplicability").equals("By Building")) {
				if (buildingAOWHDeductibleArrow.formatDynamicPath(i - 1).checkIfElementIsPresent()
						&& buildingAOWHDeductibleArrow.formatDynamicPath(i - 1).checkIfElementIsDisplayed()) {
					if (!Data.get("L" + i + "-AOWHDed").equals("")) {
						buildingAOWHDeductibleArrow.formatDynamicPath(i - 1).scrollToElement();
						buildingAOWHDeductibleArrow.formatDynamicPath(i - 1).click();
						buildingAOWHDeductibleOption.formatDynamicPath(i - 1, Data.get("L" + i + "-AOWHDed")).click();
					}
				}

			} else if (Data.get("AOWHDeductibleApplicability").equals("By Location")) {
				if (locationAOWHDeductibleArrow.formatDynamicPath(i - 1).checkIfElementIsPresent()
						&& locationAOWHDeductibleArrow.formatDynamicPath(i - 1).checkIfElementIsDisplayed()) {
					if (!Data.get("L" + i + "-AOWHDed").equals("")) {
						locationAOWHDeductibleArrow.formatDynamicPath(i - 1).scrollToElement();
						locationAOWHDeductibleArrow.formatDynamicPath(i - 1).click();
						locationAOWHDeductibleOption.formatDynamicPath(i - 1, Data.get("L" + i + "-AOWHDed")).click();
					}
				}

			} else if (Data.get("AOWHDeductibleApplicability").equals("By Policy")) {
				if (aowhDeductibleArrow.checkIfElementIsPresent() && aowhDeductibleArrow.checkIfElementIsDisplayed()) {
					if (!Data.get("L" + i + "-AOWHDed").equals("")) {
						aowhDeductibleArrow.scrollToElement();
						aowhDeductibleArrow.click();
						aowhDeductibleOption.formatDynamicPath(Data.get("L" + i + "-AOWHDed")).click();
					}
				}

			}

			// eq - on quake accounts
			if (Data.get("Peril").equals("EQ")) {
				if (locationBPPEarthquakeDeductibleArrow.formatDynamicPath(i - 1).checkIfElementIsPresent()
						&& locationBPPEarthquakeDeductibleArrow.formatDynamicPath(i - 1).checkIfElementIsDisplayed()) {
					if (!Data.get("L" + i + "-EarthquakeDed").equals("")) {
						System.out.println("Setting BPP EQ loc ded for i = " + i);
						locationBPPEarthquakeDeductibleArrow.formatDynamicPath(i - 1).scrollToElement();
						locationBPPEarthquakeDeductibleArrow.formatDynamicPath(i - 1).click();
						locationBPPEarthquakeDeductibleOption
								.formatDynamicPath(i - 1, Data.get("L" + i + "-EarthquakeDed")).click();
						loading.waitTillInVisibilityOfElement(60);
					}
				} else if (locationBldgEarthquakeDeductibleArrow.formatDynamicPath(i - 1).checkIfElementIsPresent()
						&& locationBldgEarthquakeDeductibleArrow.formatDynamicPath(i - 1).checkIfElementIsDisplayed()) {
					if (!Data.get("L" + i + "-EarthquakeDed").equals("")) {
						System.out.println("Setting Bldg EQ loc ded for i = " + i);
						locationBldgEarthquakeDeductibleArrow.formatDynamicPath(i - 1).scrollToElement();
						locationBldgEarthquakeDeductibleArrow.formatDynamicPath(i - 1).click();
						locationBldgEarthquakeDeductibleOption
								.formatDynamicPath(i - 1, Data.get("L" + i + "-EarthquakeDed")).click();
						loading.waitTillInVisibilityOfElement(60);
					}
				}

				if (locationFloodArrow.formatDynamicPath(i - 1).checkIfElementIsPresent()
						&& locationFloodArrow.formatDynamicPath(i - 1).checkIfElementIsDisplayed()) {
					if (!Data.get("L" + i + "-FloodDed").equals("")) {
						locationFloodArrow.formatDynamicPath(i - 1).scrollToElement();
						locationFloodArrow.formatDynamicPath(i - 1).click();
						locationFloodOption.formatDynamicPath(i - 1, Data.get("L" + i + "-FloodDed")).click();
						loading.waitTillInVisibilityOfElement(60);
					}
				}

				if (locationEQSLArrow.formatDynamicPath(i - 1).checkIfElementIsPresent()
						&& locationEQSLArrow.formatDynamicPath(i - 1).checkIfElementIsDisplayed()) {
					if (!Data.get("L" + i + "-EarthquakeSprinklerLeakage").equals("")) {
						locationEQSLArrow.formatDynamicPath(i - 1).scrollToElement();
						locationEQSLArrow.formatDynamicPath(i - 1).click();
						locationEQSLOption.formatDynamicPath(i - 1, Data.get("L" + i + "-EarthquakeSprinklerLeakage"))
								.click();
						loading.waitTillInVisibilityOfElement(60);
					}
				}

				// eq on wind/aop accounts
			} else if (Data.get("EQDeductibleApplicability").equals("By Building")) {

				if (buildingEarthquakeDeductibleArrow.formatDynamicPath(i - 1).checkIfElementIsPresent()
						&& buildingEarthquakeDeductibleArrow.formatDynamicPath(i - 1).checkIfElementIsDisplayed()) {
					if (!Data.get("L" + i + "-EarthquakeDed").equals("")) {
						buildingEarthquakeDeductibleArrow.formatDynamicPath(i - 1).scrollToElement();
						buildingEarthquakeDeductibleArrow.formatDynamicPath(i - 1).click();
						buildingEarthquakeDeductibleOption
								.formatDynamicPath(i - 1, Data.get("L" + i + "-EarthquakeDed")).click();
						loading.waitTillInVisibilityOfElement(60);
					}
				}

			} else if (Data.get("EQDeductibleApplicability").equals("By Location")) {

				if (locationEarthquakeDeductibleArrow.formatDynamicPath(i - 1).checkIfElementIsPresent()
						&& locationEarthquakeDeductibleArrow.formatDynamicPath(i - 1).checkIfElementIsDisplayed()) {
					if (!Data.get("L" + i + "-EarthquakeDed").equals("")) {
						locationEarthquakeDeductibleArrow.formatDynamicPath(i - 1).scrollToElement();
						locationEarthquakeDeductibleArrow.formatDynamicPath(i - 1).click();
						locationEarthquakeDeductibleOption
								.formatDynamicPath(i - 1, Data.get("L" + i + "-EarthquakeDed")).click();
						loading.waitTillInVisibilityOfElement(60);
					}
				}

			} else if (Data.get("EQDeductibleApplicability").equals("By Policy")) {
				if (earthquakeDeductibleArrow.checkIfElementIsPresent()
						&& earthquakeDeductibleArrow.checkIfElementIsDisplayed()) {
					if (!Data.get("L" + i + "-EarthquakeDed").equals("")) {
						earthquakeDeductibleArrow.scrollToElement();
						earthquakeDeductibleArrow.click();
						earthquakeDeductibleOption.formatDynamicPath(Data.get("L" + i + "-EarthquakeDed")).click();
						loading.waitTillInVisibilityOfElement(8);
					}
				}

			}

			// aocl
			if (aoclDeductibleArrow.checkIfElementIsPresent() && aoclDeductibleArrow.checkIfElementIsDisplayed()) {
				if (!Data.get("L" + i + "-AOCLDed").equals("")) {
					aoclDeductibleArrow.scrollToElement();
					aoclDeductibleArrow.click();
					aoclDeductibleOption.formatDynamicPath(Data.get("L" + i + "-AOCLDed")).click();
				}
			}

			if (commLocOrdLawArrow.formatDynamicPath(i - 1).checkIfElementIsPresent()
					&& commLocOrdLawArrow.formatDynamicPath(i - 1).checkIfElementIsDisplayed()) {
				if (!Data.get("L" + i + "-OrdinanceLaw").equals("")) {
					commLocOrdLawArrow.formatDynamicPath(i - 1).scrollToElement();
					commLocOrdLawArrow.formatDynamicPath(i - 1).click();
					commLocOrdLawOption.formatDynamicPath(i - 1, Data.get("L" + i + "-OrdinanceLaw")).click();
				}
			}
			if (Data.containsKey("PropertyReplCost")) {
				if (locationPersonalPropertyReplacementCostArrow.checkIfElementIsPresent()
						&& locationPersonalPropertyReplacementCostArrow.checkIfElementIsDisplayed()) {
					if (!Data.get("L" + i + "-PropertyReplCost").equals("")) {
						locationPersonalPropertyReplacementCostArrow.formatDynamicPath(i - 1).scrollToElement();
						locationPersonalPropertyReplacementCostArrow.formatDynamicPath(i - 1).click();
						locationPersonalPropertyReplacementCostOption
								.formatDynamicPath(i, Data.get("L" + i + "-PropertyReplCost")).click();
					}
				}
			}
			if (Data.containsKey("EnhancedReplCost")) {
				if (locationEnhancedReplacementCostArrow.checkIfElementIsPresent()
						&& locationEnhancedReplacementCostArrow.checkIfElementIsDisplayed()) {
					if (!Data.get("L" + i + "-EnhancedReplCost").equals("")) {
						locationEnhancedReplacementCostArrow.formatDynamicPath(i - 1).scrollToElement();
						locationEnhancedReplacementCostArrow.formatDynamicPath(i - 1).click();
						locationEnhancedReplacementCostOption
								.formatDynamicPath(i, Data.get("L" + i + "-EnhancedReplCost")).click();
					}
				}
			}
		}
	}

	public void coverageByLocationCommercialNew(Map<String, String> Data, int locationCount) {
		chooseCoverageByLocation.scrollToElement();
		chooseCoverageByLocation.click();
		quoteName.waitTillVisibilityOfElement(60);

		System.out.println("locationCount = " + locationCount);

		if (Data.get("DeductibleType").equalsIgnoreCase("Named Hurricane")) {
			namedHurricaneRadio.scrollToElement();
			namedHurricaneRadio.click();
			loading.waitTillInVisibilityOfElement(60);
		} else if (Data.get("DeductibleType").equalsIgnoreCase("Named Storm")) {
			namedStormRadio.scrollToElement();
			namedStormRadio.click();
			loading.waitTillInVisibilityOfElement(60);
		}

		// TODO - named storm and aowh deductible dropdowns change based on deductible
		// applicability

		for (int i = 1; i <= locationCount; i++) {
			System.out.println("DeductibleApplicability = " + Data.get("DeductibleApplicability"));
			System.out.println("DeductibleType = " + Data.get("DeductibleType"));
			if (Data.get("DeductibleApplicability").equals("By Building")) {
				if (Data.get("DeductibleType").equalsIgnoreCase("Named Hurricane")) {
					System.out.println("L" + i + " Deductible = " + Data.get("L" + i + "-Deductible"));
					if (locationNamedHurricaneDeductibleArrow.formatDynamicPath(i).checkIfElementIsPresent()
							&& locationNamedHurricaneDeductibleArrow.formatDynamicPath(i).checkIfElementIsDisplayed()) {
						if (!Data.get("L" + i + "-Deductible").equals("")) {
							locationNamedHurricaneDeductibleArrow.formatDynamicPath(i).scrollToElement();
							locationNamedHurricaneDeductibleArrow.formatDynamicPath(i).click();
							locationNamedHurricaneDeductibleOption
									.formatDynamicPath(i, Data.get("L" + i + "-Deductible")).click();
						}
					}
				} else if (Data.get("DeductibleType").equalsIgnoreCase("Named Storm")) {
					System.out.println("L" + i + " Deductible = " + Data.get("L" + i + "-Deductible"));
					if (locationNamedStormDeductibleArrow.formatDynamicPath(i).checkIfElementIsPresent()
							&& locationNamedStormDeductibleArrow.formatDynamicPath(i).checkIfElementIsDisplayed()) {
						System.out.println("NSDed arrow is there");
						System.out.println("Data.get(L + i + -Deductible) = " + Data.get("L" + i + "-Deductible"));
						if (!Data.get("L" + i + "-Deductible").equals("")) {
							locationNamedStormDeductibleArrow.formatDynamicPath(i).scrollToElement();
							locationNamedStormDeductibleArrow.formatDynamicPath(i).click();
							System.out.println("L" + i + " Deductible = " + Data.get("L" + i + "-Deductible"));
							locationNamedStormDeductibleOption.formatDynamicPath(i, Data.get("L" + i + "-Deductible"))
									.click();
						}
					}
				}
				if (locationAOWHDeductibleArrow.formatDynamicPath(i).checkIfElementIsPresent()
						&& locationAOWHDeductibleArrow.formatDynamicPath(i).checkIfElementIsDisplayed()) {
					if (!Data.get("L" + i + "-AOWHDed").equals("")) {
						locationAOWHDeductibleArrow.formatDynamicPath(i).scrollToElement();
						locationAOWHDeductibleArrow.formatDynamicPath(i).click();
						locationAOWHDeductibleOption.formatDynamicPath(i, Data.get("L" + i + "-AOWHDed")).click();
					}
				}
			} else if (Data.get("DeductibleApplicability").equals("By Location")) {
				// by location dropdowns
			} else {
				// by policy dropdowns
			}

			if (locationEarthquakeDeductibleArrow.formatDynamicPath(i).checkIfElementIsPresent()
					&& locationEarthquakeDeductibleArrow.formatDynamicPath(i).checkIfElementIsDisplayed()) {
				if (!Data.get("L" + i + "-EarthquakeDed").equals("")) {
					locationEarthquakeDeductibleArrow.formatDynamicPath(i).scrollToElement();
					locationEarthquakeDeductibleArrow.formatDynamicPath(i).click();
					locationEarthquakeDeductibleOption.formatDynamicPath(i, Data.get("L" + i + "-EarthquakeDed"))
							.click();
					loading.waitTillInVisibilityOfElement(60);
				}
			}
			if (aoclDeductibleArrow.checkIfElementIsPresent() && aoclDeductibleArrow.checkIfElementIsDisplayed()) {
				if (!Data.get("L" + i + "-AOCLDed").equals("")) {
					aoclDeductibleArrow.scrollToElement();
					aoclDeductibleArrow.click();
					aoclDeductibleOption.formatDynamicPath(Data.get("L" + i + "-AOCLDed")).click();
				}
			}

			/*
			 * if (locationFloodArrow.formatDynamicPath(i).checkIfElementIsPresent() &&
			 * locationFloodArrow.formatDynamicPath(i).checkIfElementIsDisplayed()) { if
			 * (!Data.get("L" + i + "-FloodDed").equals("")) {
			 * locationFloodArrow.formatDynamicPath(i).scrollToElement();
			 * locationFloodArrow.formatDynamicPath(i).click();
			 * locationFloodOption.formatDynamicPath(i, Data.get("L" + i +
			 * "-FloodDed")).click(); } }
			 */
			if (locationOridnanceLawArrow.checkIfElementIsPresent()
					&& locationOridnanceLawArrow.checkIfElementIsDisplayed()) {
				if (!Data.get("L" + i + "-OrdinanceLaw").equals("")) {
					locationOridnanceLawArrow.formatDynamicPath(i - 1).scrollToElement();
					locationOridnanceLawArrow.formatDynamicPath(i - 1).click();
					locationOridnanceLawOption.formatDynamicPath(i, Data.get("L" + i + "-OrdinanceLaw")).click();
				}
			}
			if (Data.containsKey("PropertyReplCost")) {
				if (locationPersonalPropertyReplacementCostArrow.checkIfElementIsPresent()
						&& locationPersonalPropertyReplacementCostArrow.checkIfElementIsDisplayed()) {
					if (!Data.get("L" + i + "-PropertyReplCost").equals("")) {
						locationPersonalPropertyReplacementCostArrow.formatDynamicPath(i - 1).scrollToElement();
						locationPersonalPropertyReplacementCostArrow.formatDynamicPath(i - 1).click();
						locationPersonalPropertyReplacementCostOption
								.formatDynamicPath(i, Data.get("L" + i + "-PropertyReplCost")).click();
					}
				}
			}
			if (Data.containsKey("EnhancedReplCost")) {
				if (locationEnhancedReplacementCostArrow.checkIfElementIsPresent()
						&& locationEnhancedReplacementCostArrow.checkIfElementIsDisplayed()) {
					if (!Data.get("L" + i + "-EnhancedReplCost").equals("")) {
						locationEnhancedReplacementCostArrow.formatDynamicPath(i - 1).scrollToElement();
						locationEnhancedReplacementCostArrow.formatDynamicPath(i - 1).click();
						locationEnhancedReplacementCostOption
								.formatDynamicPath(i, Data.get("L" + i + "-EnhancedReplCost")).click();
					}
				}
			}
		}
	}

	public void setCoveragesByPolicy(Map<String, String> Data) {

		if (Data.get("DeductibleType").equalsIgnoreCase("Named Storm")) {
			namedStormDeductibleArrow.scrollToElement();
			namedStormDeductibleArrow.click();
			namedStormDeductibleOption.formatDynamicPath(Data.get("DeductibleValue")).scrollToElement();
			namedStormDeductibleOption.formatDynamicPath(Data.get("DeductibleValue")).click();

		} else if (Data.get("DeductibleType").equalsIgnoreCase("Named Hurricane")) {
			namedHurricaneDeductibleArrow.scrollToElement();
			namedHurricaneDeductibleArrow.click();
			namedHurricaneDeductibleOption.formatDynamicPath(Data.get("DeductibleValue")).scrollToElement();
			namedHurricaneDeductibleOption.formatDynamicPath(Data.get("DeductibleValue")).click();

		}

		if (StringUtils.isNotBlank(Data.get("AOWHDeductibleValue"))) {
			aowhDeductibleArrow.scrollToElement();
			aowhDeductibleArrow.click();
			aowhDeductibleOption.formatDynamicPath(Data.get("AOWHDeductibleValue")).scrollToElement();
			aowhDeductibleOption.formatDynamicPath(Data.get("AOWHDeductibleValue")).click();

			aowhDeductibleAppliesByArrow.scrollToElement();
			aowhDeductibleAppliesByArrow.click();
			aowhDeductibleAppliesByOption.formatDynamicPath(Data.get("AOWHDeductibleApplicability")).scrollToElement();
			aowhDeductibleAppliesByOption.formatDynamicPath(Data.get("AOWHDeductibleApplicability")).click();
		}

		if (StringUtils.isNotBlank(Data.get("EQDeductibleValue"))) {
			earthquakeDeductibleArrow.scrollToElement();
			earthquakeDeductibleArrow.click();
			earthquakeDeductibleOption.formatDynamicPath(Data.get("EQDeductibleValue")).scrollToElement();
			earthquakeDeductibleOption.formatDynamicPath(Data.get("EQDeductibleValue")).click();
		}

		if (!Data.get("OrdinanceOrLaw").equals("")) {
			ordinanceLawArrow.scrollToElement();
			ordinanceLawArrow.click();
			ordinanceLawOption.formatDynamicPath(Data.get("OrdinanceOrLaw")).click();
			Assertions.passTest("Create Quote Page", "Ordinance Or Law value is " + Data.get("OrdinanceOrLaw"));
		}

		if (!Data.get("EarthquakeSprinklerLeakage").equals("")) {
			earthquakeSprinklerLeakageArrow.scrollToElement();
			earthquakeSprinklerLeakageArrow.click();
			earthquakeSprinklerLeakageOption.formatDynamicPath(Data.get("EarthquakeSprinklerLeakage")).click();
			Assertions.passTest("Create Quote Page",
					"Earthquake Sprinkler Leakage value is " + Data.get("EarthquakeSprinklerLeakage"));
		}

	}

	public void addOptionalCoverageDetails(Map<String, String> Data) {

		if (Data.get("EquipmentBreakdown") != null) {
			if (equipmentBreakdownArrow.checkIfElementIsPresent() && !Data.get("EquipmentBreakdown").equals("")) {
				equipmentBreakdownArrow.scrollToElement();
				equipmentBreakdownArrow.click();
				equipmentBreakdownOption.formatDynamicPath(Data.get("EquipmentBreakdown")).scrollToElement();
				equipmentBreakdownOption.formatDynamicPath(Data.get("EquipmentBreakdown")).click();
			}
		}

		if (Data.get("OrdinanceOrLaw") != null) {
			if (ordinanceLawArrow.checkIfElementIsPresent() && !Data.get("OrdinanceOrLaw").equals("")) {
				if (!ordinanceLawArrow.getAttrributeValue("unselectable").equalsIgnoreCase("on")) {
					ordinanceLawArrow.scrollToElement();
					ordinanceLawArrow.click();
					ordinanceLawOption.formatDynamicPath(Data.get("OrdinanceOrLaw")).scrollToElement();
					ordinanceLawOption.formatDynamicPath(Data.get("OrdinanceOrLaw")).click();
				}
			}
		}

		if (Data.get("WindDrivenRain") != null) {
			if (windDrivenRainArrow.checkIfElementIsPresent() && !Data.get("WindDrivenRain").equals("")) {
				windDrivenRainArrow.click();
				windDrivenRainOption.formatDynamicPath(Data.get("WindDrivenRain")).click();
			}
		}
		// TODO - add MLOI here

		if (Data.get("Mold") != null) {
			if (moldArrow.checkIfElementIsPresent() && !Data.get("Mold").equals("")) {
				if (!moldArrow.getAttrributeValue("unselectable").equalsIgnoreCase("on")) {
					moldArrow.scrollToElement();
					moldArrow.click();
					moldOption.formatDynamicPath(Data.get("Mold")).click();
				}
			}
		}

		if (Data.get("CoveragePackage") != null) {
			if (coverageExtensionPackageArrow.checkIfElementIsPresent() && !Data.get("CoveragePackage").equals("")) {
				coverageExtensionPackageArrow.scrollToElement();
				coverageExtensionPackageArrow.click();
				coverageExtensionPackageOption.formatDynamicPath(Data.get("CoveragePackage")).scrollToElement();
				coverageExtensionPackageOption.formatDynamicPath(Data.get("CoveragePackage")).click();
			}
		}

		if (Data.get("ReplacementCost") != null) {
			if (personalPropertyReplacementCostArrow.checkIfElementIsPresent()
					&& !Data.get("ReplacementCost").equals("")) {
				personalPropertyReplacementCostArrow.scrollToElement();
				personalPropertyReplacementCostArrow.click();
				personalPropertyReplacementCostOption.formatDynamicPath(Data.get("ReplacementCost")).scrollToElement();
				personalPropertyReplacementCostOption.formatDynamicPath(Data.get("ReplacementCost")).click();
			}
		}
		if (Data.get("EnhancedReplCost") != null) {
			if (enhancedReplacementCostArrow.checkIfElementIsPresent() && !Data.get("EnhancedReplCost").equals("")) {
				enhancedReplacementCostArrow.scrollToElement();
				enhancedReplacementCostArrow.click();
				enhancedReplacementCostOption.formatDynamicPath(Data.get("EnhancedReplCost")).scrollToElement();
				enhancedReplacementCostOption.formatDynamicPath(Data.get("EnhancedReplCost")).click();
			}
		}
	}

	public void enterQuoteDetailsCommercialPNB(Map<String, String> Data) {
		editDeductiblesCommercialPNB(Data);
		editOptionalCoverageDetailsPNB(Data);
		if (getAQuote.checkIfElementIsPresent() && getAQuote.checkIfElementIsDisplayed()) {
			getAQuote.scrollToElement();
			getAQuote.click();
		}

		if (continueEndorsementButton.checkIfElementIsPresent()
				&& continueEndorsementButton.checkIfElementIsDisplayed()) {
			continueEndorsementButton.scrollToElement();
			continueEndorsementButton.click();
		}

	}

	public void editDeductiblesCommercialPNB(Map<String, String> Data) {
		int locationCount = Integer.parseInt(Data.get("LocCount"));

		if (!Data.get("CoverageBy").equalsIgnoreCase("")) {
			if (Data.get("CoverageBy").equalsIgnoreCase("By Policy")) {
				if (chooseCoverageByPolicy.checkIfElementIsPresent()
						&& chooseCoverageByPolicy.checkIfElementIsDisplayed())
					chooseCoverageByPolicy.click();
			}
		}

		if (!Data.get("DeductibleType").equalsIgnoreCase("")) {
			if (Data.get("DeductibleType").equalsIgnoreCase("Named Storm")) {

				namedStormRadio.scrollToElement();
				namedStormRadio.click();
				loading.waitTillInVisibilityOfElement(60);

				if (!Data.get("DeductibleValue").equals("")) {
					Assertions.addInfo("Create Quote Page",
							"Named Storm Deductible original Value : " + namedStormData.getData());
					namedStormDeductibleArrow.scrollToElement();
					namedStormDeductibleArrow.click();
					namedStormDeductibleOption.formatDynamicPath(Data.get("DeductibleValue")).scrollToElement();
					namedStormDeductibleOption.formatDynamicPath(Data.get("DeductibleValue")).click();
					Assertions.addInfo("Create Quote Page",
							"Named Storm Deductible Latest Value : " + namedStormData.getData());
				}

				if (!Data.get("DeductibleApplicability").equals("")) {
					namedStormDeductibleAppliesByArrow.scrollToElement();
					namedStormDeductibleAppliesByArrow.click();
					namedStormDeductibleAppliesByOption.formatDynamicPath(Data.get("DeductibleApplicability"))
							.scrollToElement();
					namedStormDeductibleAppliesByOption.formatDynamicPath(Data.get("DeductibleApplicability")).click();
				}

				if (Data.containsKey("DeductibleOccurrence")) {
					if (!Data.get("DeductibleOccurrence").equals("")) {
						namedStormDeductibleApplicabilityArrow.scrollToElement();
						namedStormDeductibleApplicabilityArrow.click();
						namedStormDeductibleApplicabilityOption.formatDynamicPath(Data.get("DeductibleOccurrence"))
								.scrollToElement();
						namedStormDeductibleApplicabilityOption.formatDynamicPath(Data.get("DeductibleOccurrence"))
								.click();
					}
				}
			}

			else {
				namedHurricaneRadio.scrollToElement();
				namedHurricaneRadio.click();
				if (!Data.get("DeductibleValue").equals("")) {
					namedHurricaneDeductibleArrow.scrollToElement();
					namedHurricaneDeductibleArrow.click();
					namedHurricaneDeductibleOption.formatDynamicPath(Data.get("DeductibleValue")).scrollToElement();
					namedHurricaneDeductibleOption.formatDynamicPath(Data.get("DeductibleValue")).click();
				}
				if (!Data.get("DeductibleApplicability").equals("")) {
					namedHurricaneDeductibleAppliesByArrow.scrollToElement();
					namedHurricaneDeductibleAppliesByArrow.click();
					namedHurricaneDeductibleAppliesByOption.formatDynamicPath(Data.get("DeductibleApplicability"))
							.scrollToElement();
					namedHurricaneDeductibleAppliesByOption.formatDynamicPath(Data.get("DeductibleApplicability"))
							.click();
				}
				if (Data.containsKey("DeductibleOccurrence")) {
					if (!Data.get("DeductibleOccurrence").equals("")) {
						namedHurricaneDeductibleApplicabilityArrow.scrollToElement();
						namedHurricaneDeductibleApplicabilityArrow.click();
						namedHurricaneDeductibleApplicabilityOption.formatDynamicPath(Data.get("DeductibleOccurrence"))
								.scrollToElement();
						namedHurricaneDeductibleApplicabilityOption.formatDynamicPath(Data.get("DeductibleOccurrence"))
								.click();
					}
				}
			}
		}

		if (!Data.get("EQDeductibleValue").equals("")) {
			Assertions.addInfo("Create Quote Page",
					"Earthquake Deductible original Value : " + earthquakeData.getData());
			earthquakeDeductibleArrow.scrollToElement();
			earthquakeDeductibleArrow.click();
			earthquakeDeductibleOption.formatDynamicPath(Data.get("EQDeductibleValue")).scrollToElement();
			earthquakeDeductibleOption.formatDynamicPath(Data.get("EQDeductibleValue")).click();
			Assertions.passTest("Create Quote Page",
					"Earthquake Deductible Latest Value : " + earthquakeData.getData());
		}
		if (!Data.get("AOWHDeductibleValue").equals("")) {
			Assertions.addInfo("Create Quote Page", "AOWH Deductible original Value : " + aowhDeductibleData.getData());
			aowhDeductibleArrow.scrollToElement();
			aowhDeductibleArrow.click();
			aowhDeductibleOption.formatDynamicPath(Data.get("AOWHDeductibleValue")).scrollToElement();
			aowhDeductibleOption.formatDynamicPath(Data.get("AOWHDeductibleValue")).click();
			Assertions.addInfo("Create Quote Page", "AOWH Deductible latest Value : " + aowhDeductibleData.getData());
		}
		if (!Data.get("AOWHDeductibleApplicability").equals("")) {
			aowhDeductibleAppliesByArrow.scrollToElement();
			aowhDeductibleAppliesByArrow.click();
			aowhDeductibleAppliesByOption.formatDynamicPath(Data.get("AOWHDeductibleApplicability")).scrollToElement();
			aowhDeductibleAppliesByOption.formatDynamicPath(Data.get("AOWHDeductibleApplicability")).click();
		}

		if (!Data.get("AOCLDeductibleValue").equals("")) {
			Assertions.addInfo("Create Quote Page", "AOCL Deductible original Value : " + aoclDeductibleData.getData());
			aoclDeductibleArrow.scrollToElement();
			aoclDeductibleArrow.click();
			aoclDeductibleOption.formatDynamicPath(Data.get("AOCLDeductibleValue")).scrollToElement();
			aoclDeductibleOption.formatDynamicPath(Data.get("AOCLDeductibleValue")).click();
			Assertions.addInfo("Create Quote Page", "AOCL Deductible Latest Value : " + aoclDeductibleData.getData());
		}

		if (!Data.get("CoverageBy").equalsIgnoreCase("")) {
			if (Data.get("CoverageBy").equalsIgnoreCase("By Location")) {
				setCoveragesByLocation(Data, locationCount);
			}
		}
	}

	public void editOptionalCoverageDetailsPNB(Map<String, String> Data) {
		if (!Data.get("ElectronicData").equals("")) {
			Assertions.addInfo("Create Quote Page", "Electronic Data original Value : " + electronicData.getData());
			electronicDataArrow.scrollToElement();
			electronicDataArrow.click();
			electronicDataOption.formatDynamicPath(Data.get("ElectronicData")).scrollToElement();
			electronicDataOption.formatDynamicPath(Data.get("ElectronicData")).click();
			Assertions.passTest("Create Quote Page", "Electronic Data Latest Value : " + electronicData.getData());
		}

		if (!Data.get("FineArts").equals("")) {
			Assertions.addInfo("Create Quote Page", "Fine Arts original Value : " + fineArtsData.getData());
			fineArtsArrow.scrollToElement();
			fineArtsArrow.click();
			fineArtsOption.formatDynamicPath(Data.get("FineArts")).scrollToElement();
			fineArtsOption.formatDynamicPath(Data.get("FineArts")).click();
			Assertions.passTest("Create Quote Page", "Fine Arts original Value : " + fineArtsData.getData());
		}

		if (!Data.get("ValuablePapers").equals("")) {
			Assertions.addInfo("Create Quote Page", "Valuable Papers original Value : " + valuablePapersData.getData());
			valuablePapersArrow.scrollToElement();
			valuablePapersArrow.click();
			valuablePapersOption.formatDynamicPath(Data.get("ValuablePapers")).scrollToElement();
			valuablePapersOption.formatDynamicPath(Data.get("ValuablePapers")).click();
			Assertions.passTest("Create Quote Page", "Valuable Papers Latest Value : " + valuablePapersData.getData());
		}

		if (!Data.get("AccountsReceivable").equals("")) {
			Assertions.addInfo("Create Quote Page",
					"Account Receivable original Value : " + accountReceivableData.getData());
			accountReceivableArrow.scrollToElement();
			accountReceivableArrow.click();
			accountReceivableOption.formatDynamicPath(Data.get("AccountsReceivable")).scrollToElement();
			accountReceivableOption.formatDynamicPath(Data.get("AccountsReceivable")).click();
			Assertions.passTest("Create Quote Page",
					"Account Receivable Latest Value : " + accountReceivableData.getData());
		}

		if (!Data.get("FoodSpoilage").equals("")) {
			Assertions.addInfo("Create Quote Page", "Food Spoilage original Value : " + foodSpoilageData.getData());
			foodSpoilageArrow.scrollToElement();
			foodSpoilageArrow.click();
			foodSpoilageOption.formatDynamicPath(Data.get("FoodSpoilage")).scrollToElement();
			foodSpoilageOption.formatDynamicPath(Data.get("FoodSpoilage")).click();
			Assertions.passTest("Create Quote Page", "Food Spoilage Latest Value : " + foodSpoilageData.getData());
		}

		if (!Data.get("UtilityInterruption").equals("")) {
			Assertions.addInfo("Create Quote Page",
					"Utility Interruption original Value : " + utilityInterruptionData.getData());
			utilityInterruptionArrow.scrollToElement();
			utilityInterruptionArrow.click();
			utilityInterruptionOption.formatDynamicPath(Data.get("UtilityInterruption")).scrollToElement();
			utilityInterruptionOption.formatDynamicPath(Data.get("UtilityInterruption")).click();
			Assertions.passTest("Create Quote Page",
					"Utility Interruption Latest Value : " + utilityInterruptionData.getData());
		}

		if (!Data.get("IncreasedPeriodOfRestoration").equals("")) {
			Assertions.addInfo("Create Quote Page",
					"Increased Period Of Restoration original Value : " + restorationData.getData());
			restorationArrow.scrollToElement();
			restorationArrow.click();
			restorationOption.formatDynamicPath(Data.get("IncreasedPeriodOfRestoration")).scrollToElement();
			restorationOption.formatDynamicPath(Data.get("IncreasedPeriodOfRestoration")).click();
			Assertions.passTest("Create Quote Page",
					"Increased Period Of Restoration Latest Value : " + restorationData.getData());
		}

		if (!Data.get("ExtendedPeriodOfIndemnity").equals("")) {
			Assertions.addInfo("Create Quote Page",
					"Extended Period Of Indemnity original Value : " + indemnityData.getData());
			indemnityArrow.scrollToElement();
			indemnityArrow.click();
			indemnityOption.formatDynamicPath(Data.get("ExtendedPeriodOfIndemnity")).scrollToElement();
			indemnityOption.formatDynamicPath(Data.get("ExtendedPeriodOfIndemnity")).click();
			Assertions.passTest("Create Quote Page",
					"Extended Period Of Indemnity Latest Value : " + indemnityData.getData());
		}

		if (!Data.get("EarthquakeSprinklerLeakage").equals("")) {
			Assertions.addInfo("Create Quote Page",
					"Earthquake Sprinkler Leakage original Value : " + earthquakeSprinklerLeakageData.getData());
			earthquakeSprinklerLeakageArrow.scrollToElement();
			earthquakeSprinklerLeakageArrow.click();
			earthquakeSprinklerLeakageOption.formatDynamicPath(Data.get("EarthquakeSprinklerLeakage")).click();
			Assertions.passTest("Create Quote Page",
					"Earthquake Sprinkler Leakage Latest Value : " + earthquakeSprinklerLeakageData.getData());
		}
		if (!Data.get("GeneralLiabilityCovValue").equals("")) {
			Assertions.addInfo("Create Quote Page",
					"Genearal Liability coverage original Value : " + generalLiabilityArrow.getData());
			generalLiabilityArrow.scrollToElement();
			generalLiabilityArrow.click();
			generalLiabilityOption.formatDynamicPath(Data.get("GeneralLiabilityCovValue")).click();
			Assertions.passTest("Create Quote Page",
					"Genearal Liability coverage Latest Value : " + generalLiabilityArrow.getData());
		}
		if (!Data.get("EquipmentBreakdown").equals("")) {
			Assertions.addInfo("Create Quote Page",
					"Equipment Breakdown original Value : " + equipmentBreakdownData.getData());
			equipmentBreakdownArrow.scrollToElement();
			equipmentBreakdownArrow.click();
			equipmentBreakdownOption.formatDynamicPath(Data.get("EquipmentBreakdown")).click();
			Assertions.passTest("Create Quote Page",
					"Equipment Breakdown Latest Value : " + equipmentBreakdownData.getData());
		}
		if (!Data.get("OrdinanceOrLaw").equals("")) {
			Assertions.addInfo("Create Quote Page", "Ordinance Or Law original Value : " + ordinanceLawArrow.getData());
			ordinanceLawArrow.scrollToElement();
			ordinanceLawArrow.click();
			ordinanceLawOption.formatDynamicPath(Data.get("OrdinanceOrLaw")).scrollToElement();
			ordinanceLawOption.formatDynamicPath(Data.get("OrdinanceOrLaw")).click();
			Assertions.passTest("Create Quote Page", "Ordinance Or Law Latest Value : " + ordinanceLawArrow.getData());
		}

		if (!Data.get("WindDrivenRain").equals("")) {
			Assertions.addInfo("Create Quote Page",
					"Wind Driven Rain original Value : " + windDrivenRainArrow.getData());
			windDrivenRainArrow.scrollToElement();
			windDrivenRainArrow.click();
			windDrivenRainOption.formatDynamicPath(Data.get("WindDrivenRain")).click();
			Assertions.passTest("Create Quote Page",
					"Wind Driven Rain Latest Value : " + windDrivenRainArrow.getData());
		}

		if (!Data.get("Sinkhole").equals("")) {
			Assertions.addInfo("Create Quote Page", "Sinkhole original Value : " + sinkholeData.getData());
			sinkholeArrow.scrollToElement();
			sinkholeArrow.click();
			sinkholeOption.formatDynamicPath(Data.get("Sinkhole")).click();
			Assertions.passTest("Create Quote Page", "Sinkhole Latest Value : " + sinkholeData.getData());
		}

		if (!Data.get("MoldCleanUpRemoval").equals("")) {
			Assertions.addInfo("Create Quote Page", "Mold clean up and Removal original Value : " + moldData.getData());
			moldArrow.scrollToElement();
			moldArrow.click();
			moldOption.formatDynamicPath(Data.get("MoldCleanUpRemoval")).scrollToElement();
			moldOption.formatDynamicPath(Data.get("MoldCleanUpRemoval")).click();
			Assertions.passTest("Create Quote Page", "Mold clean up and Removal Latest Value : " + moldData.getData());
		}

		if (!Data.get("FloodCoverage").equals("")) {
			Assertions.addInfo("Create Quote Page", "Flood Coverage original Value : " + floodCoverageData.getData());
			floodCoverageArrow.scrollToElement();
			floodCoverageArrow.click();
			floodCoverageOption.formatDynamicPath(Data.get("FloodCoverage")).scrollToElement();
			floodCoverageOption.formatDynamicPath(Data.get("FloodCoverage")).click();
			loading.waitTillInVisibilityOfElement(60);

			if (Data.get("Peril").equalsIgnoreCase("EQ")) {
				terrorismArrow.waitTillVisibilityOfElement(60);
			}
			Assertions.passTest("Create Quote Page", "Flood Coverage Latest Value : " + floodCoverageData.getData());
		}

		if (!Data.get("Terrorism").equals("") && terrorismArrow.checkIfElementIsDisplayed()) {
			Assertions.addInfo("Create Quote Page", "Terrorism original Value : " + terrorismData.getData());
			terrorismArrow.waitTillVisibilityOfElement(60);
			terrorismArrow.scrollToElement();
			terrorismArrow.click();
			terrorismOption.formatDynamicPath(Data.get("Terrorism")).scrollToElement();
			terrorismOption.formatDynamicPath(Data.get("Terrorism")).click();
			Assertions.passTest("Create Quote Page", "Terrorism Latest Value : " + terrorismData.getData());
		}

		if (!Data.get("CoverageExtensionPackage").equals("")) {
			Assertions.addInfo("Create Quote Page",
					"Coverage Extension Package original Value : " + coverageExtensionPackageData.getData());
			coverageExtensionPackageArrow.scrollToElement();
			coverageExtensionPackageArrow.click();
			coverageExtensionPackageOption.formatDynamicPath(Data.get("CoverageExtensionPackage")).click();
			Assertions.passTest("Create Quote Page",
					"Coverage Extension Package Latest Value : " + coverageExtensionPackageData.getData());
		}
	}

	public void addAdditionalCoveragesCommercial(Map<String, String> Data) {
		// Wind options
		if (equipmentBreakdownArrow.checkIfElementIsPresent() && !Data.get("EquipmentBreakdown").equals("")) {
			waitTime(5);
			equipmentBreakdownArrow.scrollToElement();
			equipmentBreakdownArrow.click();
			equipmentBreakdownOption.formatDynamicPath(Data.get("EquipmentBreakdown")).click();
			Assertions.passTest("Create Quote Page", "Equipment breakdown value is " + Data.get("EquipmentBreakdown"));
		}

		// without building coverage, WDR is not available
		if (windDrivenRainArrow.checkIfElementIsPresent() && windDrivenRainArrow.checkIfElementIsEnabled() ) {
			if (!Data.get("WindDrivenRain").equals("")) {
				windDrivenRainArrow.scrollToElement();
				windDrivenRainArrow.click();
				if (windDrivenRainOption.formatDynamicPath(Data.get("WindDrivenRain")).checkIfElementIsDisplayed()) {
					windDrivenRainOption.formatDynamicPath(Data.get("WindDrivenRain")).click();
				}
			}
			Assertions.passTest("Create Quote Page", "Wind Driven Rain value is " + Data.get("WindDrivenRain"));
		}

		if (Data.containsKey("MLOI")) {
			if (Data.get("MLOI") != null) {
				if (!Data.get("MLOI").equals("") && mloiArrow.checkIfElementIsEnabled()) {
					mloiArrow.scrollToElement();
					mloiArrow.click();
					if (mloiOption.formatDynamicPath(Data.get("MLOI")).checkIfElementIsDisplayed()) {
						mloiOption.formatDynamicPath(Data.get("MLOI")).click();
					}
					Assertions.passTest("Create Quote Page", "Monthly Limit of Indemnity value is " + Data.get("MLOI"));
				}
			}
			Assertions.passTest("Create Quote Page", "Monthly Limit of Indemnity value is " + Data.get("MLOI"));
		}
		// }

		if (!Data.get("Sinkhole").equals("") && sinkholeArrow.checkIfElementIsEnabled()) {
			sinkholeArrow.scrollToElement();
			sinkholeArrow.click();
			sinkholeOption.formatDynamicPath(Data.get("Sinkhole")).click();
			Assertions.passTest("Create Quote Page", "Sinkhole value is " + Data.get("Sinkhole"));
		}

		if (!Data.get("CoverageExtensionPackage").equals("")) {
			coverageExtensionPackageArrow.scrollToElement();
			coverageExtensionPackageArrow.click();
			coverageExtensionPackageOption.formatDynamicPath(Data.get("CoverageExtensionPackage")).click();
			Assertions.passTest("Create Quote Page",
					"Coverage Package value is " + Data.get("CoverageExtensionPackage"));
		}

		// EQ options
		if (!Data.get("ElectronicData").equals("")) {
			electronicDataArrow.scrollToElement();
			electronicDataArrow.click();
			electronicDataOption.formatDynamicPath(Data.get("ElectronicData")).scrollToElement();
			electronicDataOption.formatDynamicPath(Data.get("ElectronicData")).click();
			Assertions.passTest("Create Quote Page", "Electronic Data value is " + Data.get("ElectronicData"));
		}

		if (!Data.get("FineArts").equals("")) {
			fineArtsArrow.scrollToElement();
			fineArtsArrow.click();
			fineArtsOption.formatDynamicPath(Data.get("FineArts")).click();
			Assertions.passTest("Create Quote Page", "Fine Arts value is " + Data.get("FineArts"));
		}

		if (!Data.get("ValuablePapers").equals("")) {
			valuablePapersArrow.scrollToElement();
			valuablePapersArrow.click();
			valuablePapersOption.formatDynamicPath(Data.get("ValuablePapers")).click();
			Assertions.passTest("Create Quote Page", "Valuable Papers value is " + Data.get("ValuablePapers"));
		}

		if (!Data.get("AccountsReceivable").equals("")) {
			accountReceivableArrow.scrollToElement();
			accountReceivableArrow.click();
			accountReceivableOption.formatDynamicPath(Data.get("AccountsReceivable")).click();
			Assertions.passTest("Create Quote Page", "Account Receivable value is " + Data.get("AccountsReceivable"));
		}

		if (!Data.get("FoodSpoilage").equals("")) {
			foodSpoilageArrow.scrollToElement();
			foodSpoilageArrow.click();
			foodSpoilageOption.formatDynamicPath(Data.get("FoodSpoilage")).scrollToElement();
			foodSpoilageOption.formatDynamicPath(Data.get("FoodSpoilage")).click();
			Assertions.passTest("Create Quote Page", "Food Spoilage value is " + Data.get("FoodSpoilage"));
		}

		if (!Data.get("UtilityInterruption").equals("")) {
			utilityInterruptionArrow.scrollToElement();
			utilityInterruptionArrow.click();
			utilityInterruptionOption.formatDynamicPath(Data.get("UtilityInterruption")).scrollToElement();
			utilityInterruptionOption.formatDynamicPath(Data.get("UtilityInterruption")).click();
			Assertions.passTest("Create Quote Page",
					"Utility Interruption value is " + Data.get("UtilityInterruption"));
		}

		if (!Data.get("FloodCoverage").equals("") && !Data.get("CoverageBy").equals("By Location")) {
			floodCoverageArrow.scrollToElement();
			floodCoverageArrow.click();
			floodCoverageOption.formatDynamicPath(Data.get("FloodCoverage")).scrollToElement();
			floodCoverageOption.formatDynamicPath(Data.get("FloodCoverage")).click();
			loading.waitTillInVisibilityOfElement(60);
			// TRIA option becomes available when flood is selected for EQ
			if (Data.get("Peril").equalsIgnoreCase("EQ") && Data.get("FloodCoverage").equalsIgnoreCase("yes")) {
				terrorismArrow.waitTillVisibilityOfElement(60);
			}
			Assertions.passTest("Create Quote Page", "Flood Coverage value is " + Data.get("FloodCoverage"));
		}

		if (!Data.get("IncreasedPeriodOfRestoration").equals("")) {
			restorationArrow.scrollToElement();
			restorationArrow.click();
			restorationOption.formatDynamicPath(Data.get("IncreasedPeriodOfRestoration")).scrollToElement();
			restorationOption.formatDynamicPath(Data.get("IncreasedPeriodOfRestoration")).click();
			Assertions.passTest("Create Quote Page",
					"Increased Period Of Restoration value is " + Data.get("IncreasedPeriodOfRestoration"));
		}

		if (!Data.get("ExtendedPeriodOfIndemnity").equals("")) {
			indemnityArrow.scrollToElement();
			indemnityArrow.click();
			indemnityOption.formatDynamicPath(Data.get("ExtendedPeriodOfIndemnity")).scrollToElement();
			indemnityOption.formatDynamicPath(Data.get("ExtendedPeriodOfIndemnity")).click();
			Assertions.passTest("Create Quote Page",
					"Extended Period Of Indemnity value is " + Data.get("ExtendedPeriodOfIndemnity"));
		}

		if (!Data.get("EarthquakeSprinklerLeakage").equals("") && !Data.get("CoverageBy").equals("By Location")) {
			earthquakeSprinklerLeakageArrow.scrollToElement();
			earthquakeSprinklerLeakageArrow.click();
			earthquakeSprinklerLeakageOption.formatDynamicPath(Data.get("EarthquakeSprinklerLeakage")).click();
			Assertions.passTest("Create Quote Page",
					"Earthquake Sprinkler Leakage value is " + Data.get("EarthquakeSprinklerLeakage"));
		}

		if (!Data.get("GeneralLiabilityCovValue").equals("")) {
			generalLiabilityArrow.scrollToElement();
			generalLiabilityArrow.click();
			generalLiabilityOption.formatDynamicPath(Data.get("GeneralLiabilityCovValue")).click();
			Assertions.passTest("Create Quote Page",
					"General Liability value is " + Data.get("GeneralLiabilityCovValue"));
		}

		// both EQ and wind
		if (!Data.get("OrdinanceOrLaw").equals("") && !Data.get("CoverageBy").equals("By Location")) {
			ordinanceLawArrow.scrollToElement();
			ordinanceLawArrow.click();
			ordinanceLawOption.formatDynamicPath(Data.get("OrdinanceOrLaw")).click();
			Assertions.passTest("Create Quote Page", "Ordinance Or Law value is " + Data.get("OrdinanceOrLaw"));
		}

		if (!Data.get("Terrorism").equals("") && terrorismArrow.checkIfElementIsPresent()) {
			terrorismArrow.waitTillVisibilityOfElement(60);
			terrorismArrow.scrollToElement();
			terrorismArrow.click();
			terrorismOption.formatDynamicPath(Data.get("Terrorism")).scrollToElement();
			terrorismOption.formatDynamicPath(Data.get("Terrorism")).click();
			Assertions.passTest("Create Quote Page", "Terrorism value is " + Data.get("Terrorism"));
		}

		if (moldArrow.checkIfElementIsPresent() && moldArrow.checkIfElementIsDisplayed()) {
			if (!Data.get("MoldCleanUpRemoval").equals("")) {
				moldArrow.scrollToElement();
				moldArrow.click();
				moldOption.formatDynamicPath(Data.get("MoldCleanUpRemoval")).click();
				Assertions.passTest("Create Quote Page", "Mold value is " + Data.get("MoldCleanUpRemoval"));
			}
		}

		if (greenUpgradesArrow.checkIfElementIsPresent() && greenUpgradesArrow.checkIfElementIsDisplayed()) {
			if (Data.get("GreenUpgrades") != null) {
				if (!Data.get("GreenUpgrades").equals("")) {
					if (Data.get("GreenUpgrades").equals("Yes")) {
						greenUpgradesArrow.scrollToElement();
						greenUpgradesArrow.click();
						greenUpgradesYesOption.scrollToElement();
						greenUpgradesYesOption.click();
						Assertions.passTest("Create Quote Page",
								"Green Upgrades Value Selected is  : " + greenUpgradesData.getData());
					}
					if (Data.get("GreenUpgrades").equals("No")) {
						greenUpgradesArrow.scrollToElement();
						greenUpgradesArrow.click();
						greenUpgradesNoOption.scrollToElement();
						greenUpgradesNoOption.click();
						Assertions.passTest("Create Quote Page",
								"Green Upgrades Value Selected is  : " + greenUpgradesData.getData());
					}
				}
			}
		}

	}

//======================================================================================================================

//##### RESIDENTIAL METHODS #####

	public void enterQuoteDetailsNAHO(Map<String, String> Data) {
		String locationNumber = Data.get("LocCount");
		int locationCount = Integer.parseInt(locationNumber);

		String dwellingNumber = Data.get("L" + locationCount + "-DwellingCount");
		int dwellingCount = Integer.parseInt(dwellingNumber);

		enterInsuredValuesNAHO(Data, locationCount, dwellingCount);
		enterDeductiblesNAHO(Data);
		enterOptionalcoverageDetailsNAHO(Data);

		if (!Data.get("ModifyForms").equals("")) {

			if (modifyForms.checkIfElementIsPresent() && modifyForms.checkIfElementIsDisplayed()) {
				modifyForms.scrollToElement();
				modifyForms.click();
			} else {
				continueEndorsementButton.scrollToElement();
				continueEndorsementButton.click();
			}
			ModifyForms modifyFormsPage = new ModifyForms();
			modifyFormsPage.addForms(Data);
		}
		if (continueButton.checkIfElementIsPresent() && continueButton.checkIfElementIsDisplayed()) {
			continueButton.scrollToElement();
			continueButton.click();
		}
		if (pageName.getData().contains("Create Quote")) {
			override.scrollToElement();
			override.click();
		}
		if (getAQuote.checkIfElementIsPresent() && getAQuote.checkIfElementIsDisplayed()) {
			getAQuote.scrollToElement();
			getAQuote.click();
		}

		if (continueEndorsementButton.checkIfElementIsPresent()
				&& continueEndorsementButton.checkIfElementIsDisplayed()) {

			continueEndorsementButton.scrollToElement();
			continueEndorsementButton.click();

			if (override.checkIfElementIsPresent() && override.checkIfElementIsDisplayed()) {
				ModifyForms modifyFormsPage = new ModifyForms();
				modifyFormsPage.addForms(Data);

			}
		}

		if (pageName.getData().contains("Dwelling values")) {
			if (continueButton.checkIfElementIsPresent() && continueButton.checkIfElementIsDisplayed()) {
				continueButton.scrollToElement();
				continueButton.click();
			} else {
				override.scrollToElement();
				override.click();
			}
		}

		if (continueButton.checkIfElementIsPresent() && continueButton.checkIfElementIsDisplayed()) {
			waitTime(2);
			continueButton.waitTillVisibilityOfElement(60);
			continueButton.scrollToElement();
			continueButton.click();
		}
		// Adding this condition to handle deductible minimum warning
		if (override.checkIfElementIsPresent() && override.checkIfElementIsDisplayed()) {
			override.scrollToElement();
			override.click();
		}
	}

	public void setResidentialCoveragesByLocation(Map<String, String> Data, int locationCount) {
		chooseCoverageByLocation.scrollToElement();
		chooseCoverageByLocation.click();
		waitTime(1);
		for (int i = 1; i <= locationCount; i++) {
			if (Data.get("DeductibleType").equals("Named Hurricane")) {
				if (locationNamedHurricaneDeductibleArrow.checkIfElementIsPresent()
						&& !Data.get("L" + i + "-Deductible").equals("")) {
					locationNamedHurricaneDeductibleArrow.formatDynamicPath(i).scrollToElement();
					locationNamedHurricaneDeductibleArrow.formatDynamicPath(i).click();
					locationNamedHurricaneDeductibleOption.formatDynamicPath(i, Data.get("L" + i + "-Deductible"))
							.click();
				}
			} else if (Data.get("DeductibleType").equals("Named Storm")) {
				if (locationNamedStormDeductibleArrow.checkIfElementIsPresent()
						&& !Data.get("L" + i + "-Deductible").equals("")) {
					locationNamedStormDeductibleArrow.formatDynamicPath(i).scrollToElement();
					locationNamedStormDeductibleArrow.formatDynamicPath(i).click();
					locationNamedStormDeductibleOption.formatDynamicPath(i, Data.get("L" + i + "-Deductible")).click();
				}
			}
			if (locationEarthquakeDeductibleArrow.checkIfElementIsPresent()
					&& !Data.get("L" + i + "-EarthquakeDed").equals("")) {
				locationEarthquakeDeductibleArrow.formatDynamicPath(i).scrollToElement();
				locationEarthquakeDeductibleArrow.formatDynamicPath(i).click();
				locationEarthquakeDeductibleOption.formatDynamicPath(i, Data.get("L" + i + "-EarthquakeDed")).click();
				loading.waitTillInVisibilityOfElement(60);
			}
			if (aoclDeductibleArrow.checkIfElementIsPresent() && !Data.get("L" + i + "-AOCLDed").equals("")) {
				aoclDeductibleArrow.scrollToElement();
				aoclDeductibleArrow.click();
				aoclDeductibleOption.formatDynamicPath(Data.get("L" + i + "-AOCLDed")).click();
			}
			if (aowhDeductibleArrow.checkIfElementIsPresent() && !Data.get("L" + i + "-AOWHDed").equals("")) {
				aowhDeductibleArrow.scrollToElement();
				aowhDeductibleArrow.click();
				aowhDeductibleOption.formatDynamicPath(Data.get("L" + i + "-AOWHDed")).click();
			}
			if (floodDeductibleArrow.checkIfElementIsPresent() && !Data.get("L" + i + "-FloodDed").equals("")) {
				floodDeductibleArrow.scrollToElement();
				floodDeductibleArrow.click();
				floodDeductibleOption.formatDynamicPath(Data.get("L" + i + "-FloodDed")).click();
			}
			if (locationOridnanceLawArrow.checkIfElementIsPresent()
					&& !Data.get("L" + i + "-OrdinanceLaw").equals("")) {
				locationOridnanceLawArrow.formatDynamicPath(i).scrollToElement();
				locationOridnanceLawArrow.formatDynamicPath(i).click();
				locationOridnanceLawOption.formatDynamicPath(i, Data.get("L" + i + "-OrdinanceLaw")).click();
			}
			if (locationPersonalPropertyReplacementCostArrow.checkIfElementIsPresent()
					&& !Data.get("L" + i + "-PropertyReplCost").equals("")) {
				locationPersonalPropertyReplacementCostArrow.formatDynamicPath(i).scrollToElement();
				locationPersonalPropertyReplacementCostArrow.formatDynamicPath(i).click();
				locationPersonalPropertyReplacementCostOption
						.formatDynamicPath(i, Data.get("L" + i + "-PropertyReplCost")).click();
			}
			if (locationEnhancedReplacementCostArrow.checkIfElementIsPresent()
					&& !Data.get("L" + i + "-EnhancedReplCost").equals("")) {
				locationEnhancedReplacementCostArrow.formatDynamicPath(i).scrollToElement();
				locationEnhancedReplacementCostArrow.formatDynamicPath(i).click();
				locationEnhancedReplacementCostOption.formatDynamicPath(i, Data.get("L" + i + "-EnhancedReplCost"))
						.click();
			}
		}
	}

	public void enterInsuredValuesNAHO(Map<String, String> Data, int locNo, int bldgNo) {
		if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingCovA").equals("")) {
			coverageADwelling.scrollToElement();
			coverageADwelling.clearData();
			coverageADwelling.appendData(Data.get("L1D1-DwellingCovA"));
			WebElement ele = WebDriverManager.getCurrentDriver().findElement(By.xpath("//input[@id='CovA_AOP']"));
			ele.sendKeys(Keys.chord(FrameworkUtil.KEY_CMD_CTRL, "a"),
					Data.get("L" + locNo + "D" + bldgNo + "-DwellingCovA"));
			coverageADwelling.tab();
			loading.waitTillInVisibilityOfElement(60);
		}
		if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingCovB").equals("")) {
			coverageBOtherStructures.waitTillVisibilityOfElement(60);
			coverageBOtherStructures.clearData();
			coverageBOtherStructures.scrollToElement();
			coverageBOtherStructures.setData(Data.get("L" + locNo + "D" + bldgNo + "-DwellingCovB"));
			coverageBOtherStructures.tab();
			loading.waitTillInVisibilityOfElement(60);
		}
		if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingCovC").equals("")) {
			coverageCPersonalProperty.waitTillVisibilityOfElement(60);
			coverageCPersonalProperty.clearData();
			coverageCPersonalProperty.scrollToElement();
			coverageCPersonalProperty.setData(Data.get("L" + locNo + "D" + bldgNo + "-DwellingCovC"));
			coverageCPersonalProperty.tab();
			loading.waitTillInVisibilityOfElement(60);
		}
		if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingCovD").equals("")) {
			coverageDFairRental.waitTillVisibilityOfElement(60);
			coverageDFairRental.clearData();
			coverageDFairRental.scrollToElement();
			coverageDFairRental.setData(Data.get("L" + locNo + "D" + bldgNo + "-DwellingCovD"));
			coverageDFairRental.tab();
			loading.waitTillInVisibilityOfElement(60);
		}
		if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingCovE").equals("")) {
			coverageEArrow.waitTillVisibilityOfElement(60);
			coverageEArrow.scrollToElement();
			coverageEArrow.click();
			coverageEOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingCovE")).scrollToElement();
			coverageEOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingCovE")).click();
			loading.waitTillInVisibilityOfElement(60);
		}
		if (!Data.get("L" + locNo + "D" + bldgNo + "-DwellingCovF").equals("")) {
			coverageFArrow.waitTillVisibilityOfElement(60);
			coverageFArrow.scrollToElement();
			coverageFArrow.click();
			coverageFOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingCovF")).scrollToElement();
			coverageFOption.formatDynamicPath(Data.get("L" + locNo + "D" + bldgNo + "-DwellingCovF")).click();
			loading.waitTillInVisibilityOfElement(60);
		}
	}

	public void enterOptionalcoverageDetailsNAHO(Map<String, String> Data) {
		if (!Data.get("AdditionalDwellingCoverage").equals("")) {
			if (additionalDwellingCoverageArrow.checkIfElementIsPresent()
					&& additionalDwellingCoverageArrow.checkIfElementIsDisplayed()) {
				additionalDwellingCoverageArrow.scrollToElement();
				additionalDwellingCoverageArrow.click();
				additionalDwellingCoverageOption.formatDynamicPath(Data.get("AdditionalDwellingCoverage"))
						.scrollToElement();
				additionalDwellingCoverageOption.formatDynamicPath(Data.get("AdditionalDwellingCoverage")).click();
			}
		}
		if (!Data.get("IncreasedLimitsOnBusinessProperty").equals("")) {
			if (businessPropertyArrow.checkIfElementIsPresent() && businessPropertyArrow.checkIfElementIsDisplayed()) {
				businessPropertyArrow.scrollToElement();
				businessPropertyArrow.click();
				businessPropertyOption.formatDynamicPath(Data.get("IncreasedLimitsOnBusinessProperty"))
						.scrollToElement();
				businessPropertyOption.formatDynamicPath(Data.get("IncreasedLimitsOnBusinessProperty")).click();
			}
		}

		if (!Data.get("LimitedPool").equals("")) {
			if (limitedPoolArrow.checkIfElementIsPresent() && limitedPoolArrow.checkIfElementIsDisplayed()) {
				limitedPoolArrow.scrollToElement();
				limitedPoolArrow.click();
				limitedPoolOption.formatDynamicPath(Data.get("LimitedPool")).scrollToElement();
				limitedPoolOption.formatDynamicPath(Data.get("LimitedPool")).click();
			}
		}

		if (!Data.get("LimitedWaterSump").equals("")) {
			if (limitedWaterSumpArrow.checkIfElementIsPresent() && limitedWaterSumpArrow.checkIfElementIsDisplayed()) {
				limitedWaterSumpArrow.scrollToElement();
				limitedWaterSumpArrow.click();
				limitedWaterSumpOption.formatDynamicPath(Data.get("LimitedWaterSump")).scrollToElement();
				limitedWaterSumpOption.formatDynamicPath(Data.get("LimitedWaterSump")).click();
			}
		}
		if (!Data.get("LossAssessment").equals("")) {
			if (lossAssessmentArrow.checkIfElementIsPresent() && lossAssessmentArrow.checkIfElementIsDisplayed()) {
				lossAssessmentArrow.scrollToElement();
				lossAssessmentArrow.click();
				lossAssessmentOption.formatDynamicPath(Data.get("LossAssessment")).scrollToElement();
				lossAssessmentOption.formatDynamicPath(Data.get("LossAssessment")).click();
			}
		}

		if (Data.containsKey("EQLossAssessment") && !Data.get("EQLossAssessment").equals("")) {
			if (eqLossAssessmentArrow.checkIfElementIsPresent() && eqLossAssessmentArrow.checkIfElementIsDisplayed()) {
				eqLossAssessmentArrow.scrollToElement();
				eqLossAssessmentArrow.click();
				eqLossAssessmentOption.formatDynamicPath(Data.get("EQLossAssessment")).scrollToElement();
				eqLossAssessmentOption.formatDynamicPath(Data.get("EQLossAssessment")).click();
			}
		}
		if (!Data.get("IdentityFraud").equals("")) {
			if (identityFraudArrow.checkIfElementIsPresent() && identityFraudArrow.checkIfElementIsDisplayed()) {
				identityFraudArrow.scrollToElement();
				identityFraudArrow.click();
				identityFraudOption.formatDynamicPath(Data.get("IdentityFraud")).scrollToElement();
				identityFraudOption.formatDynamicPath(Data.get("IdentityFraud")).click();
			}
		}
		if (!Data.get("IncreaseSpecialLimits").equals("")) {
			if (specialLimitsArrow.checkIfElementIsPresent() && specialLimitsArrow.checkIfElementIsDisplayed()) {
				specialLimitsArrow.scrollToElement();
				specialLimitsArrow.click();
				specialLimitsOption.formatDynamicPath(Data.get("IncreaseSpecialLimits")).scrollToElement();
				specialLimitsOption.formatDynamicPath(Data.get("IncreaseSpecialLimits")).click();
			}
		}
		if (!Data.get("PersonalInjury").equals("")) {
			if (personalInjuryArrow.checkIfElementIsPresent() && personalInjuryArrow.checkIfElementIsDisplayed()) {
				personalInjuryArrow.scrollToElement();
				personalInjuryArrow.click();
				personalInjuryOption.formatDynamicPath(Data.get("PersonalInjury")).scrollToElement();
				personalInjuryOption.formatDynamicPath(Data.get("PersonalInjury")).click();
			}
		}
		if (!Data.get("SpecialPersonalProperty").equals("")) {
			if (personalPropertyArrow.checkIfElementIsPresent() && personalPropertyArrow.checkIfElementIsDisplayed()) {
				personalPropertyArrow.scrollToElement();
				personalPropertyArrow.click();
				personalPropertyOption.formatDynamicPath(Data.get("SpecialPersonalProperty")).scrollToElement();
				personalPropertyOption.formatDynamicPath(Data.get("SpecialPersonalProperty")).click();
			}
		}

		if (Data.containsKey("Sinkhole") && !Data.get("Sinkhole").equals("")) {
			if (sinkholeArrow.checkIfElementIsPresent() && sinkholeArrow.checkIfElementIsDisplayed()) {
				sinkholeArrow.scrollToElement();
				sinkholeArrow.click();
				sinkholeOption.formatDynamicPath(Data.get("Sinkhole")).scrollToElement();
				sinkholeOption.formatDynamicPath(Data.get("Sinkhole")).click();
			}
		}

		if (!Data.get("ServiceLine").equals("")) {
			if (serviceLineArrow.checkIfElementIsPresent() && serviceLineArrow.checkIfElementIsDisplayed()) {
				serviceLineArrow.scrollToElement();
				serviceLineArrow.click();
				serviceLineOption.formatDynamicPath(Data.get("ServiceLine")).scrollToElement();
				serviceLineOption.formatDynamicPath(Data.get("ServiceLine")).click();
			}
		}

		if (!Data.get("EquipmentBreakdown").equals("")) {
			if (equipmentBreakdownArrow_NAHO.checkIfElementIsPresent()
					&& equipmentBreakdownArrow_NAHO.checkIfElementIsDisplayed()) {
				equipmentBreakdownArrow_NAHO.scrollToElement();
				equipmentBreakdownArrow_NAHO.click();
				equipmentBreakdownOption.formatDynamicPath(Data.get("EquipmentBreakdown")).scrollToElement();
				equipmentBreakdownOption.formatDynamicPath(Data.get("EquipmentBreakdown")).click();
			}
		}

		if (!Data.get("OrdinanceOrLaw").equals("")) {
			if (ordinanceLawArrow_NAHO.checkIfElementIsPresent()
					&& ordinanceLawArrow_NAHO.checkIfElementIsDisplayed()) {
				ordinanceLawArrow_NAHO.scrollToElement();
				ordinanceLawArrow_NAHO.click();
				ordinanceLawOption.formatDynamicPath(Data.get("OrdinanceOrLaw")).scrollToElement();
				ordinanceLawOption.formatDynamicPath(Data.get("OrdinanceOrLaw")).click();
			}
		}
		if (Data.containsKey("GreenUpgrades") && !Data.get("GreenUpgrades").equals("")) {
			if (greenUpgradesArrow.checkIfElementIsPresent() && greenUpgradesArrow.checkIfElementIsDisplayed()) {
				if (Data.get("GreenUpgrades").equals("Yes")) {
					greenUpgradesArrow.scrollToElement();
					greenUpgradesArrow.click();
					greenUpgradesYesOption.scrollToElement();
					greenUpgradesYesOption.click();
					Assertions.passTest("Create Quote Page",
							"Green Upgrades Value Selected is  : " + greenUpgradesData.getData());
				}
			}
		}
		if (Data.containsKey("GreenUpgrades") && !Data.get("GreenUpgrades").equals("")) {
			if (Data.get("GreenUpgrades").equals("No")) {
				greenUpgradesArrow.scrollToElement();
				greenUpgradesArrow.click();
				greenUpgradesNoOption.scrollToElement();
				greenUpgradesNoOption.click();
				Assertions.passTest("Create Quote Page",
						"Green Upgrades Value Selected is  : " + greenUpgradesData.getData());
			}
		}

		if (!Data.get("MoldProperty").equals("")) {
			if (moldPropertyArrow.checkIfElementIsPresent() && moldPropertyArrow.checkIfElementIsDisplayed()) {
				moldPropertyArrow.scrollToElement();
				moldPropertyArrow.click();
				moldPropertyOption.formatDynamicPath(Data.get("MoldProperty")).scrollToElement();
				moldPropertyOption.formatDynamicPath(Data.get("MoldProperty")).click();
				Assertions.passTest("Create Quote Page", "Mold value is : " + moldPropertyText.getData());
			}

		}
	}

	public void enterDeductiblesNAHO(Map<String, String> Data) {
		if (formType_HO3.checkIfElementIsPresent() && Data.get("FormType").equalsIgnoreCase("HO3")) {
			formType_HO3.scrollToElement();
			formType_HO3.click();
		} else if (formType_HO5.checkIfElementIsPresent() && Data.get("FormType").equalsIgnoreCase("HO5")) {
			formType_HO5.scrollToElement();
			formType_HO5.click();
		}

		if (!Data.get("AOPDeductibleValue").equals("")) {
			if (!aopDeductibleArrow.getAttrributeValue("class").contains("disabled")) {
				aopDeductibleArrow.waitTillPresenceOfElement(60);
				aopDeductibleArrow.waitTillVisibilityOfElement(60);
				aopDeductibleArrow.scrollToElement();
				aopDeductibleArrow.click();
				aopDeductibleOption.formatDynamicPath(Data.get("AOPDeductibleValue")).scrollToElement();
				aopDeductibleOption.formatDynamicPath(Data.get("AOPDeductibleValue")).click();

			}
		}

		if (!Data.get("NamedStormValue").equals("") && namedStormArrow_NAHO.checkIfElementIsPresent()
				&& namedStormArrow_NAHO.checkIfElementIsDisplayed()) {
			if (!namedStormArrow_NAHO.getAttrributeValue("class").contains("disabled")) {
				namedStormArrow_NAHO.scrollToElement();
				namedStormArrow_NAHO.click();
				namedStormDeductibleOption.formatDynamicPath(Data.get("NamedStormValue"))
						.waitTillVisibilityOfElement(60);
				namedStormDeductibleOption.formatDynamicPath(Data.get("NamedStormValue")).scrollToElement();
				namedStormDeductibleOption.formatDynamicPath(Data.get("NamedStormValue")).click();
			}
		}

		if (namedHurricaneDeductibleArrow.checkIfElementIsPresent()
				&& namedHurricaneDeductibleArrow.checkIfElementIsDisplayed()) {
			if (!namedHurricaneDeductibleArrow.getAttrributeValue("unselectable").contains("on")) {
				namedHurricaneDeductibleArrow.scrollToElement();
				namedHurricaneDeductibleArrow.click();
				namedHurricaneDeductibleOption.formatDynamicPath(Data.get("NamedStormValue"))
						.waitTillVisibilityOfElement(60);
				namedHurricaneDeductibleOption.formatDynamicPath(Data.get("NamedStormValue")).scrollToElement();
				namedHurricaneDeductibleOption.formatDynamicPath(Data.get("NamedStormValue")).click();
			}
		}
		if (!Data.get("AOWHDeductibleValue").equals("")) {
			// TF 09/25/2024 - the class for the aowhDeductibleArrow does not contain
			// enabled/disabled, so the test tries to set it, even when it's disabled
			// using a new element - aowhSelectListNAHO
			if (!aowhSelectListNAHO.getAttrributeValue("class").contains("disabled")) {
				aowhDeductibleArrow.scrollToElement();
				aowhDeductibleArrow.click();
				aowhDeductibleOptionNAHO.formatDynamicPath(Data.get("AOWHDeductibleValue")).scrollToElement();
				aowhDeductibleOptionNAHO.formatDynamicPath(Data.get("AOWHDeductibleValue")).click();
			}
		}
		if (Data.containsKey("EQDeductibleValue") && !Data.get("EQDeductibleValue").equals("")) {
			earthquakeDeductibleArrow.scrollToElement();
			earthquakeDeductibleArrow.click();
			waitTime(2);
			earthquakeDeductibleOptionEQHO.formatDynamicPath(Data.get("EQDeductibleValue"))
					.waitTillVisibilityOfElement(60);
			earthquakeDeductibleOptionEQHO.formatDynamicPath(Data.get("EQDeductibleValue")).scrollToElement();
			earthquakeDeductibleOptionEQHO.formatDynamicPath(Data.get("EQDeductibleValue")).click();
		}
	}

	public AccountOverviewPage enterQuoteDetails(Map<String, String> Data) {
		enterQuoteName(Data.get("QuoteName"));
		enterDeductibles(Data);
		addOptionalCoverageDetails(Data);
		enterInsuredValues(Data);
		if (floodCovA.checkIfElementIsPresent() && floodCovA.checkIfElementIsDisplayed()
				&& !Data.get("L1D1-DwellingCovA").equals("")) {
			enterFloodCovA(Data);
		}
		if (floodCovC.checkIfElementIsPresent() && floodCovC.checkIfElementIsDisplayed()) {
			if (Data.get("Flood").equalsIgnoreCase("Yes") && !Data.get("L1D1-DwellingCovC").equals("")) {
				enterFloodCovC(Data);
			}
		}
		if (getAQuote.checkIfElementIsPresent() && getAQuote.checkIfElementIsDisplayed()) {
			getAQuote.scrollToElement();
			getAQuote.click();
		}
		if (continueEndorsementButton.checkIfElementIsPresent()
				&& continueEndorsementButton.checkIfElementIsDisplayed()) {
			continueEndorsementButton.scrollToElement();
			continueEndorsementButton.click();
		}
		while (getAQuote.checkIfElementIsPresent()) {
			getAQuote.scrollToElement();
			getAQuote.click();
			getAQuote.waitTillInVisibilityOfElement(60);
		}
		if (pageName.getData().contains("Dwelling values")) {
			override.scrollToElement();
			override.click();
			override.waitTillInVisibilityOfElement(60);
		}
		if (pageName.getData().contains("Create Quote")) {
			continueButton.scrollToElement();
			continueButton.click();
			continueButton.waitTillInVisibilityOfElement(60);
		}
		if (pageName.getData().contains("Account Overview")) {
			return new AccountOverviewPage();
		}
		return null;
	}

	public void enterInsuredValues(Map<String, String> Data) {
		if (StringUtils.isNotBlank(Data.get("DwellingCovA"))) {
			try {
				covAEQ.clearData();
				covAEQ.setData(Data.get("DwellingCovA"));
				covAEQ.tab();
				loading.waitTillInVisibilityOfElement(60);
			} catch (Exception e) {
				refreshPage();
				loading.waitTillInVisibilityOfElement(60);
				covAEQ.clearData();
				covAEQ.setData(Data.get("DwellingCovA"));
				covAEQ.tab();
				loading.waitTillInVisibilityOfElement(60);
			}
		}
		if (!"Standard".equalsIgnoreCase(Data.get("CoveragePackage"))) {
			if (StringUtils.isNotBlank(Data.get("DwellingCovB"))) {

				try {
					loading.waitTillInVisibilityOfElement(60);
					covBEQ.clearData();
					covBEQ.setData(Data.get("DwellingCovB"));
					covBEQ.tab();
					loading.waitTillInVisibilityOfElement(60);
				} catch (Exception e) {
					refreshPage();
					loading.waitTillInVisibilityOfElement(60);
					covBEQ.clearData();
					covBEQ.setData(Data.get("DwellingCovB"));
					covBEQ.tab();
					loading.waitTillInVisibilityOfElement(60);
				}
			}
			if (StringUtils.isNotBlank(Data.get("DwellingCovC"))) {
				try {
					loading.waitTillInVisibilityOfElement(60);
					covCEQ.clearData();
					covCEQ.setData(Data.get("DwellingCovC"));
					covCEQ.tab();
					loading.waitTillInVisibilityOfElement(60);
				} catch (Exception e) {
					refreshPage();
					loading.waitTillInVisibilityOfElement(60);
					covCEQ.clearData();
					covCEQ.setData(Data.get("DwellingCovC"));
					covCEQ.tab();
					loading.waitTillInVisibilityOfElement(60);
				}
			}
			if (StringUtils.isNotBlank(Data.get("DwellingCovD"))) {
				try {
					loading.waitTillInVisibilityOfElement(60);
					covDEQ.clearData();
					covDEQ.setData(Data.get("DwellingCovD"));
					covDEQ.tab();
					loading.waitTillInVisibilityOfElement(60);
				} catch (Exception e) {
					refreshPage();
					loading.waitTillInVisibilityOfElement(60);
					covDEQ.clearData();
					covDEQ.setData(Data.get("DwellingCovD"));
					covDEQ.tab();
					loading.waitTillInVisibilityOfElement(60);
				}
			}
		}
	}

	// used in HIHO smoke tests
	public void enterDeductibles(Map<String, String> Data) {
		if (Data.get("CoverageBy").equalsIgnoreCase("By Policy")) {
			if (chooseCoverageByPolicy.checkIfElementIsPresent()
					&& chooseCoverageByPolicy.checkIfElementIsDisplayed()) {
				chooseCoverageByPolicy.click();
			}
			if (namedHurricaneDeductibleArrow.checkIfElementIsPresent()
					&& !Data.get("NamedHurricaneDedValue").equals("")) {
				if (namedHurricaneRadio.checkIfElementIsPresent()
						&& !Data.get("NamedHurricaneDedApplicability").equalsIgnoreCase("")) {
					namedHurricaneRadio.click();
					namedHurricaneDeductibleAppliesByArrow.click();
					namedHurricaneDeductibleAppliesByOption
							.formatDynamicPath(Data.get("NamedHurricaneDedApplicability")).click();
				}
				namedHurricaneDeductibleArrow.scrollToElement();
				namedHurricaneDeductibleArrow.click();
				namedHurricaneDeductibleOption.formatDynamicPath(Data.get("NamedHurricaneDedValue")).scrollToElement();
				namedHurricaneDeductibleOption.formatDynamicPath(Data.get("NamedHurricaneDedValue")).click();
			}
			if (earthquakeDeductibleArrow.checkIfElementIsPresent() && !Data.get("EQDeductible").equals("")) {
				earthquakeDeductibleArrow.scrollToElement();
				earthquakeDeductibleArrow.click();
				if (earthquakeDeductibleOption.formatDynamicPath(Data.get("EQDeductible")).checkIfElementIsPresent()
						&& earthquakeDeductibleOption.formatDynamicPath(Data.get("EQDeductible"))
								.checkIfElementIsDisplayed()) {
					earthquakeDeductibleOption.formatDynamicPath(Data.get("EQDeductible")).scrollToElement();
					earthquakeDeductibleOption.formatDynamicPath(Data.get("EQDeductible")).click();
				} else {
					earthquakeDeductibleOptionEQHO.formatDynamicPath(Data.get("EQDeductible")).scrollToElement();
					earthquakeDeductibleOptionEQHO.formatDynamicPath(Data.get("EQDeductible")).click();
				}
			}
			if (aowhDeductibleArrow.checkIfElementIsPresent() && aowhDeductibleArrow.checkIfElementIsDisplayed())
				if (!Data.get("AOWHDeductible").equals("")) {
					aowhDeductibleArrow.scrollToElement();
					aowhDeductibleArrow.click();
					aowhDeductibleOption.formatDynamicPath(Data.get("AOWHDeductible")).click();
				}
			if (aoclDeductibleArrow.checkIfElementIsPresent() && !Data.get("AOCLDeductible").equals("")) {
				aoclDeductibleArrow.click();
				aoclDeductibleOption.waitTillVisibilityOfElement(60);
				aoclDeductibleOption.formatDynamicPath(Data.get("AOCLDeductible")).click();
			}
			if (floodDeductibleArrow.checkIfElementIsPresent() && !Data.get("FloodDeductible").equals("")) {
				floodDeductibleArrow.scrollToElement();
				floodDeductibleArrow.click();
				floodDeductibleOption.formatDynamicPath(Data.get("FloodDeductible")).scrollToElement();
				floodDeductibleOption.formatDynamicPath(Data.get("FloodDeductible")).click();
			}
		} else if (Data.get("CoverageBy").equalsIgnoreCase("By Location")) {
			if (Data.get("LocCount") != null) {
				int locationCount = Integer.valueOf(Data.get("LocCount"));
				setResidentialCoveragesByLocation(Data, locationCount);
			}
		}
	}

	public void enterFloodCovA(Map<String, String> Data) {
		floodCovA.scrollToElement();
		floodCovA.clearData();
		floodCovA.setData(Data.get("L1D1-DwellingCovA"));
		floodCovA.tab();
		loading.waitTillInVisibilityOfElement(60);
	}

	public void enterFloodCovC(Map<String, String> Data) {
		floodCovC.scrollToElement();
		floodCovC.clearData();
		floodCovC.setData(Data.get("L1D1-DwellingCovC"));
		floodCovC.tab();
		loading.waitTillInVisibilityOfElement(60);
	}

	public void enterFloodCovAforPNB(Map<String, String> Data) {
		floodCovA.scrollToElement();
		floodCovA.clearData();
		Assertions.addInfo("Flood Coverage A original Value : " + floodCovA.getData(), "");
		floodCovA.setData(Data.get("L1D1-DwellingCovA"));
		floodCovA.tab();
		loading.waitTillInVisibilityOfElement(60);
		Assertions.passTest("Create Quote Page", "Flood Coverage A Latest Value : " + floodCovA.getData());
	}

	public void enterFloodCovCforPNB(Map<String, String> Data) {
		floodCovC.scrollToElement();
		floodCovC.clearData();
		Assertions.addInfo("Flood Coverage C original Value : " + floodCovC.getData(), "");
		floodCovC.setData(Data.get("L1D1-DwellingCovC"));
		floodCovC.tab();
		loading.waitTillInVisibilityOfElement(60);
		Assertions.passTest("Create Quote Page", "Flood Coverage C Latest Value : " + floodCovC.getData());
	}

	public void enterQuoteName(String Data) {
		quoteName.setData(Data);
	}

	public void verifyDeductibleDetails(Map<String, String> Data) {
		Assertions.verify(aopDedValue.getData(), Data.get("AOPDeductibleValueVerify"), "Create Quote Page",
				"AOP Default Deductible value for Coverage A " + "$" + Data.get("L1D1-DwellingCovA") + " is "
						+ Data.get("AOPDeductibleValueVerify") + " is verified ",
				false, false);
		Assertions.verify(namedStormData.getData(), Data.get("NamedStormValue"), "Create Quote Page",
				"Named Storm Default Deductible value for Coverage A " + "$" + Data.get("L1D1-DwellingCovA") + " is "
						+ Data.get("NamedStormValue") + " for State " + Data.get("InsuredState") + " is verified ",
				false, false);
		Assertions.verify(aowhDeductibleData.getData(), Data.get("AOWHDeductibleValue"), "Create Quote Page",
				"AOWH Default Deductible value for Coverage A " + "$" + Data.get("L1D1-DwellingCovA") + " is "
						+ Data.get("AOWHDeductibleValue") + " for State " + Data.get("InsuredState") + " is verified ",
				false, false);
	}

	public AccountOverviewPage enterQuoteDetailsforPNB(Map<String, String> Data) {
		enterDeductiblesforPNB(Data);
		addOptionalCoverageDetailsforPNB(Data);
		enterInsuredValues(Data);
		if (floodCovA.checkIfElementIsPresent() && floodCovA.checkIfElementIsDisplayed()
				&& !Data.get("L1D1-DwellingCovA").equals("")) {
			enterFloodCovAforPNB(Data);
		}
		if (Data.get("Flood") != null) {
			if (Data.get("Flood").equalsIgnoreCase("Yes") && !Data.get("L1D1-DwellingCovC").equals("")) {
				enterFloodCovCforPNB(Data);
			}
		}
		if (getAQuote.checkIfElementIsPresent() && getAQuote.checkIfElementIsDisplayed()) {
			getAQuote.scrollToElement();
			getAQuote.click();
		}
		if (continueEndorsementButton.checkIfElementIsPresent()
				&& continueEndorsementButton.checkIfElementIsDisplayed()) {
			continueEndorsementButton.tab();
			continueEndorsementButton.scrollToElement();
			continueEndorsementButton.click();
		}
		while (getAQuote.checkIfElementIsPresent()) {
			getAQuote.scrollToElement();
			getAQuote.click();
			getAQuote.waitTillInVisibilityOfElement(60);
		}
		if (pageName.getData().contains("Dwelling values")) {
			override.scrollToElement();
			override.click();
			override.waitTillInVisibilityOfElement(60);
		}
		if (continueButton.checkIfElementIsPresent()) {
			continueButton.scrollToElement();
			continueButton.click();
			continueButton.waitTillInVisibilityOfElement(60);
		}
		if (pageName.getData().contains("Account Overview")) {
			return new AccountOverviewPage();
		}
		return null;
	}

	public void enterDeductiblesforPNB(Map<String, String> Data) {
		if (Data.get("CoverageBy").equalsIgnoreCase("By Policy")) {
			if (chooseCoverageByPolicy.checkIfElementIsPresent()
					&& chooseCoverageByPolicy.checkIfElementIsDisplayed()) {
				chooseCoverageByPolicy.click();
			}
			if (namedHurricaneRadio.checkIfElementIsPresent() && !Data.get("NamedHurricaneDedValue").equals("")) {
				if (namedHurricaneRadio.checkIfElementIsPresent()
						&& !Data.get("NamedHurricaneDedApplicability").equalsIgnoreCase("")) {
					namedHurricaneRadio.click();
					namedHurricaneDeductibleAppliesByArrow.click();
					namedHurricaneDeductibleAppliesByOption
							.formatDynamicPath(Data.get("NamedHurricaneDedApplicability")).click();
				}
				namedHurricaneData.scrollToElement();
				Assertions.addInfo("Named Hurricane Deductible original Value : " + namedHurricaneData.getData(), "");
				namedHurricaneDeductibleArrow.scrollToElement();
				namedHurricaneDeductibleArrow.click();
				namedHurricaneDeductibleOption.formatDynamicPath(Data.get("NamedHurricaneDedValue")).scrollToElement();
				namedHurricaneDeductibleOption.formatDynamicPath(Data.get("NamedHurricaneDedValue")).click();
				Assertions.passTest("Create Quote Page",
						"Named Hurricane Deductible Latest Value : " + namedHurricaneData.getData());
			}
			if (earthquakeDeductibleArrow.checkIfElementIsPresent() && !Data.get("EQDeductible").equals("")) {
				earthquakeDeductibleArrow.scrollToElement();
				earthquakeDeductibleArrow.click();
				if (earthquakeDeductibleOption.formatDynamicPath(Data.get("EQDeductible")).checkIfElementIsPresent()
						&& earthquakeDeductibleOption.formatDynamicPath(Data.get("EQDeductible"))
								.checkIfElementIsDisplayed()) {
					earthquakeDeductibleOption.formatDynamicPath(Data.get("EQDeductible")).scrollToElement();
					earthquakeDeductibleOption.formatDynamicPath(Data.get("EQDeductible")).click();
				} else {
					earthquakeDeductibleOptionEQHO.formatDynamicPath(Data.get("EQDeductible")).scrollToElement();
					earthquakeDeductibleOptionEQHO.formatDynamicPath(Data.get("EQDeductible")).click();
				}
			}
			if (aowhDeductibleArrow.checkIfElementIsPresent() && aowhDeductibleArrow.checkIfElementIsDisplayed())
				if (!Data.get("AOWHDeductible").equals("")) {
					aowhDeductibleArrow.scrollToElement();
					aowhDeductibleArrow.click();
					aowhDeductibleOption.formatDynamicPath(Data.get("AOWHDeductible")).click();
				}
			if (aoclDeductibleArrow.checkIfElementIsPresent() && !Data.get("AOCLDeductible").equals("")) {
				aoclDeductibleArrow.click();
				aoclDeductibleOption.waitTillVisibilityOfElement(60);
				aoclDeductibleOption.formatDynamicPath(Data.get("AOCLDeductible")).click();
			}
			if (floodDeductibleArrow.checkIfElementIsPresent() && !Data.get("FloodDeductible").equals("")) {
				floodData.scrollToElement();
				Assertions.addInfo("Flood Deductible original Value : " + floodData.getData(), "");
				floodDeductibleArrow.scrollToElement();
				floodDeductibleArrow.click();
				floodDeductibleOption.formatDynamicPath(Data.get("FloodDeductible")).scrollToElement();
				floodDeductibleOption.formatDynamicPath(Data.get("FloodDeductible")).click();
				Assertions.passTest("Create Quote Page", "Flood Deductible Latest Value : " + floodData.getData());
			}
		} else if (Data.get("CoverageBy").equalsIgnoreCase("By Location")) {
			if (Data.get("LocCount") != null) {
				String locationNumber = Data.get("LocCount");
				int locationCount = Integer.parseInt(locationNumber);
				coverageByLocation(Data, locationCount);
			}
		}
	}

	public void addOptionalCoverageDetailsforPNB(Map<String, String> Data) {
		if (Data.get("Mold") != null) {
			if (!Data.get("Mold").equals("")) {
				if (!moldArrow.getAttrributeValue("unselectable").equalsIgnoreCase("on")) {
					moldArrow.scrollToElement();
					moldArrow.click();
					moldOption.formatDynamicPath(Data.get("Mold")).click();
				}
			}
		}
		if (Data.get("EquipmentBreakdown") != null) {
			if (!Data.get("EquipmentBreakdown").equals("")) {
				equipmentBreakdownArrow.scrollToElement();
				equipmentBreakdownArrow.click();
				equipmentBreakdownOption.formatDynamicPath(Data.get("EquipmentBreakdown")).scrollToElement();
				equipmentBreakdownOption.formatDynamicPath(Data.get("EquipmentBreakdown")).click();
			}
		}
		if (Data.get("CoveragePackage") != null) {
			if (!Data.get("CoveragePackage").equals("")) {
				coverageExtensionPackageArrow.scrollToElement();
				coverageExtensionPackageArrow.click();
				coverageExtensionPackageOption.formatDynamicPath(Data.get("CoveragePackage")).scrollToElement();
				coverageExtensionPackageOption.formatDynamicPath(Data.get("CoveragePackage")).click();
			}
		}
		if (ordinanceLawArrow.checkIfElementIsPresent() && !Data.get("OrdinanceOrLaw").equals("")) {
			if (!ordinanceLawArrow.getAttrributeValue("unselectable").equalsIgnoreCase("on")) {
				ordinanceLawData.scrollToElement();
				Assertions.addInfo("Ordinance or Law original Value : " + ordinanceLawData.getData(), "");
				ordinanceLawArrow.scrollToElement();
				ordinanceLawArrow.click();
				ordinanceLawOption.formatDynamicPath(Data.get("OrdinanceOrLaw")).scrollToElement();
				ordinanceLawOption.formatDynamicPath(Data.get("OrdinanceOrLaw")).click();
				Assertions.passTest("Create Quote Page",
						"Ordinance or Law Latest Value : " + ordinanceLawData.getData());
			}
		}
		if (Data.get("WindDrivenRain") != null) {
			if (!Data.get("WindDrivenRain").equals("")) {
				windDrivenRainArrow.click();
				windDrivenRainOption.formatDynamicPath(Data.get("WindDrivenRain")).click();
			}
		}
		if (Data.get("ReplacementCost") != null) {
			if (!Data.get("ReplacementCost").equals("")) {
				personalPropertyReplacementCostData.scrollToElement();
				Assertions.addInfo("Personal Property Replacement Cost original Value : "
						+ personalPropertyReplacementCostData.getData(), "");
				personalPropertyReplacementCostArrow.scrollToElement();
				personalPropertyReplacementCostArrow.click();
				personalPropertyReplacementCostOption.formatDynamicPath(Data.get("ReplacementCost")).scrollToElement();
				personalPropertyReplacementCostOption.formatDynamicPath(Data.get("ReplacementCost")).click();
				Assertions.passTest("Create Quote Page", "Personal Property Replacement Cost Latest Value : "
						+ personalPropertyReplacementCostData.getData());
			}
		}
		if (Data.get("EnhancedReplCost") != null) {
			if (!Data.get("EnhancedReplCost").equals("")) {
				enhancedReplacementCostData.scrollToElement();
				Assertions.addInfo(
						"Enhanced Replacement Cost original Value : " + enhancedReplacementCostData.getData(), "");
				enhancedReplacementCostArrow.scrollToElement();
				enhancedReplacementCostArrow.click();
				enhancedReplacementCostOption.formatDynamicPath(Data.get("EnhancedReplCost")).scrollToElement();
				enhancedReplacementCostOption.formatDynamicPath(Data.get("EnhancedReplCost")).click();
				Assertions.passTest("Create Quote Page",
						"Enhanced Replacement Cost Latest Value : " + enhancedReplacementCostData.getData());
			}
		}
	}

	public void coverageByLocation(Map<String, String> Data, int locationCount) {
		chooseCoverageByLocation.scrollToElement();
		chooseCoverageByLocation.click();
		namedHurricaneDeductibleArrow.waitTillVisibilityOfElement(60);
		for (int i = 1; i <= locationCount; i++) {
			if (locationNamedHurricaneDeductibleArrow.checkIfElementIsPresent()
					&& !Data.get("L" + i + "-NamedHurricaneDed").equals("")) {
				locationNamedHurricaneDeductibleArrow.formatDynamicPath(i).scrollToElement();
				locationNamedHurricaneDeductibleArrow.formatDynamicPath(i).click();
				locationNamedHurricaneDeductibleOption.formatDynamicPath(i, Data.get("L" + i + "-NamedHurricaneDed"))
						.click();
			}
			if (locationEarthquakeDeductibleArrow.checkIfElementIsPresent()
					&& !Data.get("L" + i + "-EarthquakeDed").equals("")) {
				locationEarthquakeDeductibleArrow.formatDynamicPath(i).scrollToElement();
				locationEarthquakeDeductibleArrow.formatDynamicPath(i).click();
				locationEarthquakeDeductibleOption.formatDynamicPath(i, Data.get("L" + i + "-EarthquakeDed")).click();
				loading.waitTillInVisibilityOfElement(60);
			}
			if (aoclDeductibleArrow.checkIfElementIsPresent() && !Data.get("L" + i + "-AOCLDed").equals("")) {
				aoclDeductibleArrow.scrollToElement();
				aoclDeductibleArrow.click();
				aoclDeductibleOption.formatDynamicPath(Data.get("L" + i + "-AOCLDed")).click();
			}
			if (aowhDeductibleArrow.checkIfElementIsPresent() && !Data.get("L" + i + "-AOWHDed").equals("")) {
				aowhDeductibleArrow.scrollToElement();
				aowhDeductibleArrow.click();
				aowhDeductibleOption.formatDynamicPath(Data.get("L" + i + "-AOWHDed")).click();
			}
			if (floodDeductibleArrow.checkIfElementIsPresent() && !Data.get("L" + i + "-FloodDed").equals("")) {
				floodDeductibleArrow.scrollToElement();
				floodDeductibleArrow.click();
				floodDeductibleOption.formatDynamicPath(Data.get("L" + i + "-FloodDed")).click();
			}
			if (locationOridnanceLawArrow.checkIfElementIsPresent()
					&& !Data.get("L" + i + "-OrdinanceLaw").equals("")) {
				locationOridnanceLawArrow.formatDynamicPath(i).scrollToElement();
				locationOridnanceLawArrow.formatDynamicPath(i).click();
				locationOridnanceLawOption.formatDynamicPath(i, Data.get("L" + i + "-OrdinanceLaw")).click();
			}
			if (locationPersonalPropertyReplacementCostArrow.checkIfElementIsPresent()
					&& !Data.get("L" + i + "-PropertyReplCost").equals("")) {
				locationPersonalPropertyReplacementCostArrow.formatDynamicPath(i).scrollToElement();
				locationPersonalPropertyReplacementCostArrow.formatDynamicPath(i).click();
				locationPersonalPropertyReplacementCostOption
						.formatDynamicPath(i, Data.get("L" + i + "-PropertyReplCost")).click();
			}
			if (locationEnhancedReplacementCostArrow.checkIfElementIsPresent()
					&& !Data.get("L" + i + "-EnhancedReplCost").equals("")) {
				locationEnhancedReplacementCostArrow.formatDynamicPath(i).scrollToElement();
				locationEnhancedReplacementCostArrow.formatDynamicPath(i).click();
				locationEnhancedReplacementCostOption.formatDynamicPath(i, Data.get("L" + i + "-EnhancedReplCost"))
						.click();
			}
		}
	}

	public void coverageByLocationHIHO(Map<String, String> Data, int locationCount) {
		chooseCoverageByLocation.scrollToElement();
		chooseCoverageByLocation.click();
		namedHurricaneDeductibleArrow.waitTillVisibilityOfElement(30);
		for (int i = 1; i <= locationCount; i++) {
			if (!Data.get("L" + i + "-NamedHurricaneDed").equals("")) {
				namedHurricaneDeductibleArrow.formatDynamicPath(i).scrollToElement();
				namedHurricaneDeductibleArrow.formatDynamicPath(i).click();
				locationNamedHurricaneDeductibleOption1.formatDynamicPath(i, Data.get("L" + i + "-NamedHurricaneDed"))
						.click();
			}
			if (!Data.get("L" + i + "-EarthquakeDed").equals("")) {
				locationEarthquakeDeductibleArrow.formatDynamicPath(i).scrollToElement();
				locationEarthquakeDeductibleArrow.formatDynamicPath(i).click();
				locationEarthquakeDeductibleOption1.formatDynamicPath(i, Data.get("L" + i + "-EarthquakeDed")).click();
				loading.waitTillInVisibilityOfElement(30);
			}
			if (!Data.get("L" + i + "-AOCLDed").equals("")) {
				aoclDeductibleArrow.scrollToElement();
				aoclDeductibleArrow.click();
				aOCLDedValueOption.formatDynamicPath(Data.get("L" + i + "-AOCLDed")).click();
			}
			if (!Data.get("L" + i + "-AOWHDed").equals("")) {
				aOWHNamedHurricaneDedValueArrow.scrollToElement();
				aOWHNamedHurricaneDedValueArrow.click();
				aOWHNamedHurricaneDedValueOption.formatDynamicPath(Data.get("L" + i + "-AOWHDed")).click();
			}
			if (!Data.get("L" + i + "-FloodDed").equals("")) {
				floodDeductibleArrow.scrollToElement();
				floodDeductibleArrow.click();
				floodDeductibleOption.formatDynamicPath(Data.get("L" + i + "-FloodDed")).click();

				if (!Data.get("L" + i + "-PropertyReplCost").equals("")) {
					locationPersonalPropertyReplacementCostArrow.formatDynamicPath(i).scrollToElement();
					locationPersonalPropertyReplacementCostArrow.formatDynamicPath(i).click();
					locationPersonalPropertyReplacementCostOption
							.formatDynamicPath(i, Data.get("L" + i + "-PropertyReplCost")).click();
				}
				if (!Data.get("L" + i + "-EnhancedReplCost").equals("")) {
					locationEnhancedReplacementCostArrow.formatDynamicPath(i).scrollToElement();
					locationEnhancedReplacementCostArrow.formatDynamicPath(i).click();
					locationEnhancedReplacementCostOption.formatDynamicPath(i, Data.get("L" + i + "-EnhancedReplCost"))
							.click();
				}
			}
		}
		if (!Data.get("L" + 1 + "-OrdinanceLaw").equals("")) {
			if (locationOridnanceLawArrow.formatDynamicPath(0).checkIfElementIsPresent()
					&& locationOridnanceLawArrow.formatDynamicPath(0).checkIfElementIsDisplayed()) {
				locationOridnanceLawArrow.formatDynamicPath(0).scrollToElement();
				locationOridnanceLawArrow.formatDynamicPath(0).click();
				locationOrdLawOption.formatDynamicPath(0, Data.get("L" + 1 + "-OrdinanceLaw")).click();
			} else if (locationOridnanceLawArrow.formatDynamicPath(1).checkIfElementIsPresent()
					&& locationOridnanceLawArrow.formatDynamicPath(1).checkIfElementIsDisplayed()) {
				locationOridnanceLawArrow.formatDynamicPath(1).scrollToElement();
				locationOridnanceLawArrow.formatDynamicPath(1).click();
				locationOrdLawOption.formatDynamicPath(1, Data.get("L" + 2 + "-OrdinanceLaw")).click();
			} else if (locationOridnanceLawArrow.formatDynamicPath(2).checkIfElementIsPresent()
					&& locationOridnanceLawArrow.formatDynamicPath(2).checkIfElementIsDisplayed()) {
				locationOridnanceLawArrow.formatDynamicPath(2).scrollToElement();
				locationOridnanceLawArrow.formatDynamicPath(2).click();
				locationOrdLawOption.formatDynamicPath(2, Data.get("L" + 3 + "-OrdinanceLaw")).click();
			}
		}
	}

	public void enterInsuredValuesforNH(Map<String, String> Data) {
		int LocCount = Integer.parseInt(Data.get("LocCount"));
		for (int i = 1; i <= LocCount; i++) {
			int DwellingCount = Integer.parseInt(Data.get("L" + i + "-DwellingCount"));
			for (int j = 1; j <= DwellingCount; j++) {
				if (insuredCovANH.formatDynamicPath(i, i, j).checkIfElementIsPresent()
						&& insuredCovANH.formatDynamicPath(i, i, j).checkIfElementIsDisplayed()) {
					if (!Data.get("L" + i + "D" + j + "-DwellingCovA").equals("")) {
						insuredCovANH.formatDynamicPath(i, i, j).scrollToElement();
						insuredCovANH.formatDynamicPath(i, i, j).clearData();
						waitTime(2); // for clearing existing data
						insuredCovANH.formatDynamicPath(i, i, j).setData(Data.get("L" + i + "D" + j + "-DwellingCovA"));
						insuredCovANH.formatDynamicPath(i, i, j).tab();
						loading.waitTillInVisibilityOfElement(60);
					}
				}
				if (insuredCovBNH.formatDynamicPath(i, i, j).checkIfElementIsPresent()
						&& insuredCovBNH.formatDynamicPath(i, i, j).checkIfElementIsDisplayed()) {
					if (!Data.get("L" + i + "D" + j + "-DwellingCovB").equals("")) {
						insuredCovBNH.formatDynamicPath(i, i, j).scrollToElement();
						insuredCovBNH.formatDynamicPath(i, i, j).clearData();
						waitTime(2); // for clearing existing data
						insuredCovBNH.formatDynamicPath(i, i, j).setData(Data.get("L" + i + "D" + j + "-DwellingCovB"));
						insuredCovBNH.formatDynamicPath(i, i, j).tab();
						loading.waitTillInVisibilityOfElement(60);
					}
				}
				if (insuredCovCNH.formatDynamicPath(i, i, j).checkIfElementIsPresent()
						&& insuredCovCNH.formatDynamicPath(i, i, j).checkIfElementIsDisplayed()) {
					if (!Data.get("L" + i + "D" + j + "-DwellingCovC").equals("")) {
						insuredCovCNH.formatDynamicPath(i, i, j).scrollToElement();
						insuredCovCNH.formatDynamicPath(i, i, j).clearData();
						waitTime(2); // for clearing existing data
						insuredCovCNH.formatDynamicPath(i, i, j).setData(Data.get("L" + i + "D" + j + "-DwellingCovC"));
						insuredCovCNH.formatDynamicPath(i, i, j).tab();
						loading.waitTillInVisibilityOfElement(60);
					}
				} else {
					if (!Data.get("L" + i + "-LocName").equals("")
							&& !Data.get("L" + i + "D" + j + "-DwellingCovC").equals("")) {
						insuredCovCNHName.formatDynamicPath(Data.get("L" + i + "-LocName"), i, j).scrollToElement();
						insuredCovCNHName.formatDynamicPath(Data.get("L" + i + "-LocName"), i, j).clearData();
						waitTime(2); // for clearing existing data
						insuredCovCNHName.formatDynamicPath(Data.get("L" + i + "-LocName"), i, j)
								.setData(Data.get("L" + i + "D" + j + "-DwellingCovC"));
						insuredCovCNHName.formatDynamicPath(Data.get("L" + i + "-LocName"), i, j).tab();
						loading.waitTillInVisibilityOfElement(60);
					}
				}
				if (insuredCovDNH.formatDynamicPath(i, i, j).checkIfElementIsPresent()
						&& insuredCovDNH.formatDynamicPath(i, i, j).checkIfElementIsDisplayed()) {
					if (!Data.get("L" + i + "D" + j + "-DwellingCovD").equals("")) {
						insuredCovDNH.formatDynamicPath(i, i, j).scrollToElement();
						insuredCovDNH.formatDynamicPath(i, i, j).clearData();
						waitTime(2); // for clearing existing data
						insuredCovDNH.formatDynamicPath(i, i, j).setData(Data.get("L" + i + "D" + j + "-DwellingCovD"));
						insuredCovDNH.formatDynamicPath(i, i, j).tab();
						loading.waitTillInVisibilityOfElement(60);
					}
				}
			}
		}
	}
}
