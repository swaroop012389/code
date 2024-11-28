package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;

public class ViewOrPrintFullQuotePage extends BasePageControl {

	public BaseWebElementControl packageValues;
	public ButtonControl backButton;
	public BaseWebElementControl selectedCoverageDetails;
	public BaseWebElementControl forms;
	public BaseWebElementControl premiumDetails;
	public BaseWebElementControl aiDetails;
	public BaseWebElementControl aiDetailsHIHO;
	public BaseWebElementControl terrorismCoverage;
	public BaseWebElementControl standardCoverageValues;
	public BaseWebElementControl customerFeeName;
	public BaseWebElementControl customerFeeValue;
	public BaseWebElementControl deductibleValues;
	public BaseWebElementControl commAopDetails;
	public BaseWebElementControl glCoverageDetails;
	public BaseWebElementControl mainSections;
	public BaseWebElementControl coverageSublimitsDetails;
	public BaseWebElementControl buildingDetails;
	public BaseWebElementControl quoteNumber;
	public BaseWebElementControl eqPremiumDetails;

	public BaseWebElementControl inspectionFee;
	public BaseWebElementControl policyFee;
	public BaseWebElementControl surplusLinesTaxesValue;
	public BaseWebElementControl brokerFeeValue;
	public BaseWebElementControl stampingFeeValue;
	public BaseWebElementControl mwuaValue;
	public BaseWebElementControl filingFee;
	public BaseWebElementControl maintenanceAssessmentValue;
	public BaseWebElementControl slasClearinghouseTrxnFee;
	public BaseWebElementControl fireMarshallTax;
	public BaseWebElementControl surplusLinesServiceCharge;
	public BaseWebElementControl premiumSurcharge;
	public BaseWebElementControl termsAndConditions;
	public BaseWebElementControl proposedEffectiveDate;
	public BaseWebElementControl termsAndConditionsWordings;
	public BaseWebElementControl fslsoServiceFee;
	public BaseWebElementControl premiumValue;
	public BaseWebElementControl transactionFee;
	public BaseWebElementControl empaValue;

	public BaseWebElementControl sl3FormHeader;
	public BaseWebElementControl sl3FormBody;
	public BaseWebElementControl scWording;

	public BaseWebElementControl viewOrPrintFullQuoteWording;
	public BaseWebElementControl statementData;
	public BaseWebElementControl labelData;
	public BaseWebElementControl labelData1;
	public BaseWebElementControl subjectToWordings;
	public BaseWebElementControl premiumSubTotal;
	public BaseWebElementControl tRIAValue;
	public BaseWebElementControl dueDiligenceCertificate;
	public BaseWebElementControl dueDiligenceDetails;
	public HyperLink homePageLink;
	public BaseWebElementControl statesWording;
	public BaseWebElementControl customFeeValue;
	public BaseWebElementControl surplusContributionValue;

	public BaseWebElementControl greenUpgradesOption;
	public BaseWebElementControl greenUpgradesInclude;
	public BaseWebElementControl greenUpgradesLable;
	public BaseWebElementControl slasClearinghouseTrxnFeeLabel;
	public BaseWebElementControl biLimitationVerbiageWordings;
	public BaseWebElementControl grandTotal1;
	public ButtonControl requestBind;
	public BaseWebElementControl grandTotal;
	public BaseWebElementControl yearRoofLastReplacedValue;
	public BaseWebElementControl yearRoofLastReplacedLabel;
	public BaseWebElementControl totalPremiumValue;
	public BaseWebElementControl healthyHomesFund;
	public BaseWebElementControl surplusLineInsurers;
	public BaseWebElementControl surplusLineInsurersVA_RI;
	public BaseWebElementControl surplusLineInsurersCT;
	public BaseWebElementControl stateSpecificwords;
	public BaseWebElementControl nhAndAOPValue;
	public BaseWebElementControl inspFeeHeader;
	public BaseWebElementControl policyFeeHeader;
	public BaseWebElementControl maintenanceFund;
	public BaseWebElementControl insurerPolFeeVlaue;
	public BaseWebElementControl insurerInspectionFeeValue;
	public BaseWebElementControl discountsText;
	public BaseWebElementControl discountsValue;
	public BaseWebElementControl buildingTaxesAndFeesValue;
	public ButtonControl backButton1;
	public ButtonControl closeButton;
	public BaseWebElementControl declinationsData;
	public BaseWebElementControl affidavitScetion;
	public BaseWebElementControl headerValue;
	public BaseWebElementControl dueDiligenceSection;
	public BaseWebElementControl carrierBoxText;
	public BaseWebElementControl carrierBoxText2;
	public BaseWebElementControl animalExclusion;
	public BaseWebElementControl UnfencedPool;
	public BaseWebElementControl formsIncluded;
	public BaseWebElementControl underwritingQuestions;
	public BaseWebElementControl address;
	public BaseWebElementControl riskCharacters;
	public BaseWebElementControl payPlan;
	public BaseWebElementControl method;

	public BaseWebElementControl bindPolicyCertificationMsg;
	public BaseWebElementControl declineCompany;
	public BaseWebElementControl underwriterName;
	public BaseWebElementControl decliningDate;

	public BaseWebElementControl naicNumber;
	public BaseWebElementControl reasonforDeclination;
	public BaseWebElementControl producingAgentName;
	public BaseWebElementControl producingAgentSignature;
	public BaseWebElementControl date;
	public BaseWebElementControl aiType;
	public BaseWebElementControl roofCoverage;
	public BaseWebElementControl coveragesValues;
	public BaseWebElementControl viewPrintFullQuoteDetails;
	public BaseWebElementControl greenUpgradesValue;
	//public BaseWebElementControl fSLSOServiceFees;
	public BaseWebElementControl producerDetails;
	public BaseWebElementControl installmentFee;
	public BaseWebElementControl minimumEarnedPremium;
	public BaseWebElementControl empaServiceFee;
	public BaseWebElementControl empaSurcharge;
	public BaseWebElementControl formNumber;
	public BaseWebElementControl njStateLabels;
	public BaseWebElementControl njCertificationLabels;
	public BaseWebElementControl certificationWording;
	public BaseWebElementControl njAcknowledgementLabels;
	public BaseWebElementControl quoteStatement;
	public BaseWebElementControl acknowledgementWording;
	public BaseWebElementControl insurerWording;
	public BaseWebElementControl premValue;
	public BaseWebElementControl totalPremValue;
	public BaseWebElementControl stampingFeeNaho;
	public BaseWebElementControl surplusLinesTaxNaho;
	public BaseWebElementControl policyFeeNaho;
	public BaseWebElementControl  inspectionFeeNaho;
	public BaseWebElementControl aiAddInsured;
	public BaseWebElementControl aiMortgagee;
	public BaseWebElementControl mortAIDetails;
	public BaseWebElementControl mailingAddress;

	public ViewOrPrintFullQuotePage() {
		PageObject pageobject = new PageObject("ViewOrPrintFullQuote");
		packageValues = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PackageValues")));
		backButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_BackButton")));
		selectedCoverageDetails = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_SelectedCoverageDetails")));
		forms = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Forms")));
		premiumDetails = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PremiumDetails")));
		aiDetails = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AIDetails")));
		aiDetailsHIHO = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AIDetailsHIHO")));
		terrorismCoverage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TerrorismCoverage")));
		standardCoverageValues = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_StandardCoverageValues")));
		customerFeeName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CustomerFeeName")));
		customerFeeValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CustomerFeeValue")));
		deductibleValues = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DeductibleValues")));
		commAopDetails = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CommAopDetails")));
		glCoverageDetails = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GlCoverageDetails")));
		mainSections = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_MainSections")));
		coverageSublimitsDetails = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_CoverageSublimitsDetails")));
		buildingDetails = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BuildingDetails")));
		quoteNumber = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_QuoteNumber")));
		eqPremiumDetails = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EQPremiumDetails")));

		inspectionFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_InspectionFee")));
		policyFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Policyfee")));
		surplusLinesTaxesValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_SurplusLinesTaxesValue")));
		brokerFeeValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BrokerFeeValue")));

		stampingFeeValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_StampingFeeValue")));
		mwuaValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_MWUAValue")));
		filingFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_FilingFee")));
		maintenanceAssessmentValue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_MaintenanceAssessment")));
		slasClearinghouseTrxnFee = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_SLASClearinghouseTrxnFee")));
		fireMarshallTax = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_FireMarshallTax")));
		surplusLinesServiceCharge = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_SurplusLinesServiceCharge")));
		termsAndConditions = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TermsAndConditions")));
		proposedEffectiveDate = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ProposedEffectiveDate")));
		termsAndConditionsWordings = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_TermsAndConditionsWordings")));
		premiumSurcharge = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PremiumSurcharge")));
		fslsoServiceFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_FSLSOServiceFee")));
		premiumValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PremiumValue")));
		transactionFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TransactionFee")));
		empaValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EMPAValue")));
		sl3FormHeader = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_SL3FormHeader")));
		sl3FormBody = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_SL3FormBody")));
		scWording = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_SCWording")));

		viewOrPrintFullQuoteWording = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_ViewOrPrintFullQuoteWording")));
		statementData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_StatementData")));
		labelData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LabelData")));
		labelData1 = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LabelData1")));

		subjectToWordings = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_SubjectToWordings")));
		premiumSubTotal = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_SubTotal")));
		tRIAValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TRIAPremiumValue")));
		dueDiligenceCertificate = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_DueDiligenceCertificate")));
		dueDiligenceDetails = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DueDiligenceDetails")));
		homePageLink = new HyperLink(By.xpath(pageobject.getXpath("xp_HomePageLink")));
		statesWording = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_StatesWording")));
		customFeeValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CustomFeeValue")));
		surplusContributionValue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_SurplusContributionValue")));
		greenUpgradesOption = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GreenUpgradesOption")));
		greenUpgradesInclude = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GreenUpgradesInclude")));
		greenUpgradesLable = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GreenUpgradesLable")));
		slasClearinghouseTrxnFeeLabel = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_SLASClearinghouseTrxnFeeLabel")));
		biLimitationVerbiageWordings = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BILimitationVerbiageWordings")));
		grandTotal1 = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GrandTotal1")));
		requestBind = new ButtonControl(By.xpath(pageobject.getXpath("xp_RequestBind")));
		grandTotal = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GrandTotal")));
		yearRoofLastReplacedValue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_YearRoofLastReplacedValue")));
		yearRoofLastReplacedLabel = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_YearRoofLastReplacedLabel")));
		totalPremiumValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TotalPremiumValue")));
		healthyHomesFund = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_HealthyHomesFund")));
		surplusLineInsurers = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_SurplusLineInsurers")));
		surplusLineInsurersVA_RI = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_SurplusLineInsurersVA_RI")));
		surplusLineInsurersCT = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_SurplusLineInsurersCT")));
		stateSpecificwords = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_StateSpecificword")));
		nhAndAOPValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NHAndAOPValue")));
		inspFeeHeader = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_InspFeeHeader")));
		policyFeeHeader = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolicyFeeHeader")));
		maintenanceFund = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_MaintenanceFund")));
		insurerPolFeeVlaue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_InsurerPolFeeVlaue")));
		insurerInspectionFeeValue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_InsurerInspectionFeeValue")));
		discountsText = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DiscountsText")));
		discountsValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DiscountsValue")));
		buildingTaxesAndFeesValue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BuildingTaxesAndFeesValue")));
		backButton1 = new ButtonControl(By.xpath(pageobject.getXpath("xp_BackButton")));
		closeButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_CloseButton")));
		declinationsData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DeclinationsData")));
		affidavitScetion = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AffidavitScetion")));
		headerValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_HeaderValue")));
		dueDiligenceSection = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DueDiligenceSection")));
		carrierBoxText = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CarrierBoxText")));
		carrierBoxText2 = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CarrierBoxText2")));
		animalExclusion = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AnimalExclsuion")));
		UnfencedPool = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_UnfencedPool")));
		formsIncluded = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_FormsIncluded")));
		underwritingQuestions = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_UnderwritingQuestionsSection")));
		address = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Address")));
		riskCharacters = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RiskCharacters")));
		payPlan = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PayPlan")));
		method = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Method")));

		bindPolicyCertificationMsg = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BindPolicyCertificationMsg")));
		declineCompany = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DeclineCompany")));
		underwriterName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_UnderwriterName")));
		decliningDate = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DecliningDate")));

		naicNumber = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NAIC")));
		reasonforDeclination = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ReasonForDeclination")));
		producingAgentName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ProducingAgentName")));
		producingAgentSignature = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_ProducingAgentSignature")));
		date = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Date")));
		aiType = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AIType")));
		roofCoverage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RoofCoverage")));
		coveragesValues = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CoveragesValue")));
		viewPrintFullQuoteDetails = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_ViewPrintFullQuoteDetails")));
		greenUpgradesValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GreenUpgradesValue")));
		//fSLSOServiceFees = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_FSLSOServiceFee")));
		producerDetails = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ProducerDetails")));
		installmentFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_InstallmentFee")));
		minimumEarnedPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_MinimumEarnedPremium")));
		empaServiceFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EMPAServiceFee")));
		empaSurcharge = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EMPASurcharge")));
		formNumber = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_FormNumber")));
		njStateLabels = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NJStateLabels")));
		njCertificationLabels = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NJCertificationLabels")));
		certificationWording = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CertificationWording")));
		njAcknowledgementLabels = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_NJAcknowledgementLabels")));
		quoteStatement = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_QuoteStatement")));
		acknowledgementWording = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AcknowledgementWording")));
		insurerWording = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_InsurerWording")));
		totalPremValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TotalPremValue")));
		premValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PremValue")));
		stampingFeeNaho = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_StampingFeeNaho")));
		surplusLinesTaxNaho = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_SurplusLinesTaxNaho")));
		policyFeeNaho = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolicyFeeNaho")));
		inspectionFeeNaho = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_InspectionFeeNaho")));
		aiAddInsured = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AIAddInsured")));
		aiMortgagee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AIMortgagee")));
		mortAIDetails = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_MortAIDetails")));
		mailingAddress = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_MailingAddress")));
	}
}
