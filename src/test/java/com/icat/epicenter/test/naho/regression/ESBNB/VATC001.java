/** Program Description: 1. Verifying SLTF values on Quote document and Account overview page are matching, Adding 4 AI's, calculating and validating maintenance Fund value due diligence text, request received messages.
 * 						 2. Validating IO-20810(YOC Eligibility of VA)
 *  Author			   : Priyanka S
 *  Date of Creation   : 12/17/2021
 **/

package com.icat.epicenter.test.naho.regression.ESBNB;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.ConfirmBindRequestPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExistingAccountPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class VATC001 extends AbstractNAHOTest {

	public VATC001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBNB/VATC001.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		ExistingAccountPage existingAccountPage = new ExistingAccountPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ReferralPage referralPage = new ReferralPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ConfirmBindRequestPage confirmBindRequestPage = new ConfirmBindRequestPage();
		BindRequestSubmittedPage bindRequestSubmittedPage = new BindRequestSubmittedPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		LoginPage loginPage = new LoginPage();

		// Initializing Variables
		Map<String, String> testData;
		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
		int dataValue4 = 3;
		String covE = "$1,000,000";
		testData = data.get(dataValue1);
		String locationNumber = testData.get("LocCount");
		int locationCount = Integer.parseInt(locationNumber);
		String dwellingNumber = testData.get("L" + locationCount + "-DwellingCount");
		int dwellingCount = Integer.parseInt(dwellingNumber);
		String quoteNumber;
		String policyNumber;
		int locNo = 1;
		int bldgNo = 1;
		boolean isTestPassed = false;
		try {

			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.addDwellingDetails(testData, 1, 1);
			dwellingPage.addRoofDetails(testData, 1, 1);
			dwellingPage.enterProtectionDiscountsDetails(testData, 1, 1);
			if (dwellingPage.internalSection.checkIfElementIsPresent()
					&& dwellingPage.internalSection.checkIfElementIsDisplayed()) {
				dwellingPage.internalSection.scrollToElement();
				dwellingPage.internalSection.click();
			}
			if (!testData.get("L" + 1 + "D" + 1 + "-ProtectionClassOverride").equals("")) {
				dwellingPage.enterInternalSectionDetails(testData, 1, 1);
			}
			waitTime(3);

			dwellingPage.enterDwellingValuesNAHO(testData, 1, 1);
			waitTime(5); // Control is shifting to roof details link after entering
							// dwelling values instead of clicking on review
							// dwelling
			dwellingPage.reviewDwelling();
			dwellingPage.createQuote.checkIfElementIsPresent();
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Asserting the hard stop message in Dwelling page
			Assertions.addInfo("Scenario 01",
					"Verify Hard stop messages for invalid values displayed on Dwelling page");
			Assertions.verify(
					dwellingPage.dwellingDetailsErrorMessages1.formatDynamicPath("roof last replaced")
							.checkIfElementIsDisplayed(),
					true, "Dwelling page",
					"Roof Last Replaced Error Message : " + dwellingPage.dwellingDetailsErrorMessages1
							.formatDynamicPath("roof last replaced").getData(),
					false, false);
			Assertions.verify(
					dwellingPage.dwellingDetailsErrorMessages1.formatDynamicPath("cannot be in future")
							.checkIfElementIsDisplayed(),
					true, "Dwelling page",
					"Year Constructed Error Message : " + dwellingPage.dwellingDetailsErrorMessages1
							.formatDynamicPath("cannot be in future").getData(),
					false, false);
			Assertions.verify(
					dwellingPage.dwellingDetailsErrorMessages1.formatDynamicPath("Number of stories")
							.checkIfElementIsDisplayed(),
					true, "Dwelling page",
					"Number of stories Error Message : " + dwellingPage.dwellingDetailsErrorMessages1
							.formatDynamicPath("Number of stories").getData(),
					false, false);
			Assertions.verify(
					dwellingPage.dwellingDetailsErrorMessages1.formatDynamicPath("Number of units")
							.checkIfElementIsDisplayed(),
					true, "Dwelling page",
					"Number of units Error Message : "
							+ dwellingPage.dwellingDetailsErrorMessages1.formatDynamicPath("Number of units").getData(),
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Entering Location 1 Dwelling 1 Details
			testData = data.get(dataValue2);
			Assertions.verify(dwellingPage.reSubmit.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.addDwellingDetails(testData, locationCount, dwellingCount);
			dwellingPage.addRoofDetails(testData, locationCount, dwellingCount);

			// Click on reSubmit
			if (dwellingPage.reSubmit.checkIfElementIsPresent() && dwellingPage.reSubmit.checkIfElementIsDisplayed()) {
				dwellingPage.reSubmit.scrollToElement();
				dwellingPage.reSubmit.click();
			}
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");
			if (existingAccountPage.override.checkIfElementIsPresent()
					&& existingAccountPage.override.checkIfElementIsDisplayed()) {
				existingAccountPage.OverrideExistingAccount();
			}

			// Click on Create Quote button
			dwellingPage.createQuote.waitTillPresenceOfElement(60);
			dwellingPage.createQuote.waitTillVisibilityOfElement(60);
			dwellingPage.createQuote.waitTillButtonIsClickable(60);
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();

			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.overrideNoLongerQuotableBuildings();
			}

			// Entering prior loss details
			testData = data.get(dataValue1);
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
				Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);

			if (createQuotePage.getAQuote.checkIfElementIsPresent()
					&& createQuotePage.getAQuote.checkIfElementIsDisplayed()) {
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();
			}

			Assertions.verify(
					dwellingPage.dwellingDetailsErrorMessages1.formatDynamicPath("plumbing update")
							.checkIfElementIsDisplayed(),
					true, "Dwelling page",
					"Plumbing update Error Message : "
							+ dwellingPage.dwellingDetailsErrorMessages1.formatDynamicPath("plumbing update").getData(),
					false, false);
			Assertions.verify(
					dwellingPage.dwellingDetailsErrorMessages1.formatDynamicPath("electrical update")
							.checkIfElementIsDisplayed(),
					true, "Dwelling page",
					"Electrical update Error Message : " + dwellingPage.dwellingDetailsErrorMessages1
							.formatDynamicPath("electrical update").getData(),
					false, false);
			Assertions.verify(
					dwellingPage.dwellingDetailsErrorMessages1.formatDynamicPath("HVAC update")
							.checkIfElementIsDisplayed(),
					true, "Dwelling page",
					"HVAC update Error Message : "
							+ dwellingPage.dwellingDetailsErrorMessages1.formatDynamicPath("HVAC update").getData(),
					false, false);

			createQuotePage.previous.scrollToElement();
			createQuotePage.previous.click();

			dwellingPage.editBuilding.checkIfElementIsDisplayed();
			dwellingPage.editBuilding.scrollToElement();
			dwellingPage.editBuilding.click();

			testData = data.get(dataValue2);
			dwellingPage.enterProtectionDiscountsDetails(testData, locationCount, dwellingCount);

			// Click on reSubmit
			if (dwellingPage.reSubmit.checkIfElementIsPresent() && dwellingPage.reSubmit.checkIfElementIsDisplayed()) {
				dwellingPage.reSubmit.scrollToElement();
				dwellingPage.reSubmit.click();
			}
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");
			if (existingAccountPage.override.checkIfElementIsPresent()
					&& existingAccountPage.override.checkIfElementIsDisplayed()) {
				existingAccountPage.OverrideExistingAccount();
			}

			// Click on Create Quote button
			dwellingPage.createQuote.waitTillPresenceOfElement(60);
			dwellingPage.createQuote.waitTillVisibilityOfElement(60);
			dwellingPage.createQuote.waitTillButtonIsClickable(60);
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();

			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.overrideNoLongerQuotableBuildings();
			}

			// Entering Create quote page Details
			testData = data.get(dataValue1);
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			createQuotePage.enterDeductiblesNAHO(testData);
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);
			createQuotePage.coverageEArrow.scrollToElement();
			createQuotePage.coverageEArrow.waitTillButtonIsClickable(60);
			createQuotePage.coverageEArrow.click();

			Assertions.addInfo("Scenario 02",
					"Verifying if the CovE Value should have the option of $1,000,000 When Short Term Rental is Selected as Yes for VA, Beach County");
			if (createQuotePage.coverageEOption.formatDynamicPath("$1,000,000").checkIfElementIsPresent()
					&& createQuotePage.coverageEOption.formatDynamicPath("$1,000,000").checkIfElementIsDisplayed()) {
				Assertions.verify(createQuotePage.coverageEOption.formatDynamicPath(covE).getData(), covE,
						"Create Quote Page", "The highest value that can be select is $1,000,000,"
								+ " when short term rental is selected as Yes. ",
						false, false);
			} else {
				Assertions.verify(
						createQuotePage.coverageEOption.formatDynamicPath("$500,000").checkIfElementIsPresent(), true,
						"Create Quote Page",
						"The CovE Option $500,000 is not available" + " when short term rental is selected as Yes. ",
						false, false);
			}

			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}
			if (createQuotePage.pageName.getData().contains("Create Quote")) {
				createQuotePage.override.scrollToElement();
				createQuotePage.override.click();
			}
			if (createQuotePage.getAQuote.checkIfElementIsPresent()
					&& createQuotePage.getAQuote.checkIfElementIsDisplayed()) {
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();
			}
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Entering View/Print Full Quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print Full quote link");

			// Added IO-20829
			String carrIers = viewOrPrintFullQuotePage.surplusLineInsurersVA_RI.getData();
			System.out.println("Carrier is : " + carrIers);

			Assertions.addInfo("Scenario",
					"Verifying the updated Carrier/NAIC# details for NAHO- VA state on the quote document");
			Assertions.verify(
					viewOrPrintFullQuotePage.surplusLineInsurersVA_RI.getData()
							.equals("Victor Insurance Exchange, #17499"),
					true, "View Or Print FullQuote Page",
					"The quote document for the VA state has been updated with the carrier VIE and NAIC# details Verified",
					false, false);
			Assertions.addInfo("Scenario", "Scenario Ended");

			// ------ IO-20829 Ended-----

			// Validate SLTF value
			String mfund = viewOrPrintFullQuotePage.maintenanceFund.getData().replace("$", "");
			double actualmFund = Double.parseDouble(mfund);
			String premiumValue = viewOrPrintFullQuotePage.premiumValue.getData().replace("$", "").replace(",", "");
			String inspectionValue = viewOrPrintFullQuotePage.inspectionFee.getData().replace("$", "");
			String policyValue = viewOrPrintFullQuotePage.policyFee.getData().replace("$", "").replace("%", "");
			String surplusContribution = viewOrPrintFullQuotePage.surplusContributionValue.getData().replace("$", "")
					.replace("%", "");
			double sltfFee = Double.parseDouble(premiumValue) + Double.parseDouble(inspectionValue)
					+ Double.parseDouble(policyValue) + Double.parseDouble(surplusContribution);
			double sltfFeeValue = sltfFee * 2.25 / 100 + Double.parseDouble(mfund);

			// Validate Maintenance Fees
			// validateMaitananceFee();
			double maintanaceFee = Double.parseDouble(premiumValue) + Double.parseDouble(inspectionValue)
					+ Double.parseDouble(policyValue);
			double maintananceFeeValue = maintanaceFee * 0.025 / 100;
			viewOrPrintFullQuotePage.maintenanceFund.waitTillVisibilityOfElement(60);
			if (Precision.round(Math.abs(Precision.round(actualmFund, 2) - Precision.round(maintananceFeeValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated Maintenance Fund :  " + "$" + Precision.round(maintananceFeeValue, 2));
				Assertions.passTest("Account Overview Page", "Actual Maintenance Fund : " + "$" + actualmFund);
			} else {
				Assertions.passTest("Account Overview Page",
						"The Difference between actual SLTF value and calculated SLTF value is more than 0.05");
			}
			accountOverviewPage.goBackBtn.scrollToElement();
			accountOverviewPage.goBackBtn.click();

			// Asserting SLTF Value
			String sltfValue = accountOverviewPage.sltfValue.getData().replace("$", "");
			double actualSltfValue = Double.parseDouble(sltfValue);
			Assertions.addInfo("Scenario 03", "Verify the SLTF value is displayed on Account overview page");
			if (Precision.round(Math.abs(Precision.round(actualSltfValue, 2) - Precision.round(sltfFeeValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated SLTF Value :  " + "$" + Precision.round(sltfFeeValue, 2));
				Assertions.passTest("Account Overview Page",
						"Actual SLTF Value : " + "$" + Precision.round(actualSltfValue, 2));
			} else {
				Assertions.verify(actualSltfValue, sltfFeeValue, "Account Overview Page",
						"The Difference between actual SLTF value and calculated SLTF value is more than 0.05", false,
						false);
			}
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");
			Assertions.passTest("Account Overview page", "Quote Number : "
					+ accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12));

			// Click on Request Bind
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Clicking on answer No button
			testData = data.get(dataValue1);
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);

			// Entering bind details
			// Entering policy details
			requestBindPage.enterPolicyDetailsNAHO(testData);

			// Entering payment information
			requestBindPage.enterPaymentInformationNAHO(testData);

			// Entering inspection details
			requestBindPage.addInspectionContact(testData);

			// Entering AI values
			requestBindPage.addAdditionalInterest(testData);

			// Entering contact information
			requestBindPage.addContactInformation(testData);
			Assertions.addInfo("Scenario 04", "Verify Diligence Text Message is displayed on Request Bind Page");
			Assertions.verify(requestBindPage.diligenceText.checkIfElementIsDisplayed(), true, "Request Bind page",
					"Requesting bind on Policy message is displayed", false, false);
			Assertions.verify(
					requestBindPage.dueDiligenceText.formatDynamicPath("minimum 25%").checkIfElementIsDisplayed(), true,
					"Request Bind page",
					"Diligence Message 1 : "
							+ requestBindPage.dueDiligenceText.formatDynamicPath("minimum 25%").getData(),
					false, false);
			Assertions.verify(
					requestBindPage.dueDiligenceText
							.formatDynamicPath("signed application").checkIfElementIsDisplayed(),
					true, "Request Bind page",
					"Diligence Message 2 : "
							+ requestBindPage.dueDiligenceText.formatDynamicPath("signed application").getData(),
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");
			if (requestBindPage.submit.checkIfElementIsPresent()
					&& requestBindPage.submit.checkIfElementIsDisplayed()) {
				requestBindPage.submit.scrollToElement();
				requestBindPage.submit.click();
				requestBindPage.requestBind.waitTillVisibilityOfElement(60);
				requestBindPage.requestBind.scrollToElement();
				requestBindPage.requestBind.click();
				requestBindPage.requestBind.waitTillInVisibilityOfElement(60);
			}

			// Bind Request Submitted Page
			// Asserting Request Received Information
			Assertions.addInfo("Scenario 05", "Verify Request Received Message is displayed Request Submitted Page");
			if (bindRequestSubmittedPage.requestReceivedMsg.formatDynamicPath("policy effective")
					.checkIfElementIsPresent()
					&& bindRequestSubmittedPage.requestReceivedMsg.formatDynamicPath("policy effective")
							.checkIfElementIsDisplayed()) {
				Assertions.verify(
						bindRequestSubmittedPage.requestReceivedMsg.formatDynamicPath("policy effective")
								.checkIfElementIsDisplayed(),
						true, "Bind Request Submitted page",
						"Request Received 1 : " + bindRequestSubmittedPage.requestReceivedMsg
								.formatDynamicPath("Additional Insured").getData(),
						false, false);
			} else {
				Assertions.passTest("Bind Request Submitted Page",
						"Policy Effective Date - Request Received message 1 not displayed");
			}
			Assertions.verify(
					bindRequestSubmittedPage.requestReceivedMsg.formatDynamicPath("Additional Insured")
							.checkIfElementIsDisplayed(),
					true, "Bind Request Submitted page",
					"Request Received 2 : " + bindRequestSubmittedPage.requestReceivedMsg
							.formatDynamicPath("Additional Insured").getData(),
					false, false);
			Assertions.verify(
					bindRequestSubmittedPage.requestReceivedMsg
							.formatDynamicPath("three or more").checkIfElementIsDisplayed(),
					true, "Bind Request Submitted page",
					"Request Received 3 : "
							+ bindRequestSubmittedPage.requestReceivedMsg.formatDynamicPath("three or more").getData(),
					false, false);
			Assertions.verify(
					bindRequestSubmittedPage.requestReceivedMsg.formatDynamicPath("trust or corporation")
							.checkIfElementIsDisplayed(),
					true, "Bind Request Submitted page",
					"Request Received 4 : " + bindRequestSubmittedPage.requestReceivedMsg
							.formatDynamicPath("trust or corporation").getData(),
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Signing in as Producer
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));

			// creating New account
			testData = data.get(dataValue3);
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home page",
					"Producer home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New account", "New account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility page",
					"Eligibility page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling page",
					"Dwelling page loaded successfully", false, false);
			dwellingPage.addDwellingDetails(testData, 1, 1);
			dwellingPage.addRoofDetails(testData, 1, 1);
			dwellingPage.enterProtectionDiscountsDetails(testData, 1, 1);
			if (dwellingPage.internalSection.checkIfElementIsPresent()
					&& dwellingPage.internalSection.checkIfElementIsDisplayed()) {
				dwellingPage.internalSection.scrollToElement();
				dwellingPage.internalSection.click();
			}
			if (!testData.get("L" + 1 + "D" + 1 + "-ProtectionClassOverride").equals("")) {
				dwellingPage.enterInternalSectionDetails(testData, 1, 1);
			}
			dwellingPage.enterDwellingValuesNAHO(testData, 1, 1);
			waitTime(5); // Control is shifting to roof details link after entering
							// dwelling values instead of clicking on review
							// dwelling
			dwellingPage.reviewDwelling();
			dwellingPage.createQuote.checkIfElementIsPresent();
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			Assertions.passTest("Dwelling page", "Dwelling details entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior loss page",
						"Prior loss page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
				Assertions.passTest("Prior loss page", "Prior loss details entered successfully");
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create quote page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			createQuotePage.enterDeductiblesNAHO(testData);
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);
			createQuotePage.coverageEArrow.scrollToElement();
			createQuotePage.coverageEArrow.waitTillButtonIsClickable(60);
			createQuotePage.coverageEArrow.click();

			Assertions.addInfo("Scenario 06",
					"Verifying if the CovE Value should have the option of $1,000,000 When Short Term Rental is Selected as Yes for VA, Beach County");
			if (createQuotePage.coverageEOption.formatDynamicPath("$1,000,000").checkIfElementIsPresent()
					&& createQuotePage.coverageEOption.formatDynamicPath("$1,000,000").checkIfElementIsDisplayed()) {
				Assertions.verify(createQuotePage.coverageEOption.formatDynamicPath(covE).getData(), covE,
						"Create Quote Page", "The highest value that can be select is $1,000,000,"
								+ " when short term rental is selected as Yes. ",
						false, false);
			} else {
				Assertions.verify(
						createQuotePage.coverageEOption.formatDynamicPath("$500,000").checkIfElementIsPresent(), true,
						"Create Quote Page",
						"The CovE Option $500,000 is not available" + " when short term rental is selected as Yes. ",
						false, false);
			}

			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}
			if (createQuotePage.pageName.getData().contains("Create Quote")) {
				createQuotePage.override.scrollToElement();
				createQuotePage.override.click();
			}
			if (createQuotePage.getAQuote.checkIfElementIsPresent()
					&& createQuotePage.getAQuote.checkIfElementIsDisplayed()) {
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();
			}
			Assertions.passTest("Create quote page", "Quote details entered successfully");

			// Navigating to HomePage
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// creating New account
			testData = data.get(dataValue4);
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home page",
					"Producer home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New account", "New account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility page",
					"Eligibility page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.addDwellingDetails(testData, locationCount, dwellingCount);
			dwellingPage.addRoofDetails(testData, locationCount, dwellingCount);
			dwellingPage.enterProtectionDiscountsDetails(testData, locationCount, dwellingCount);
			if (dwellingPage.internalSection.checkIfElementIsPresent()
					&& dwellingPage.internalSection.checkIfElementIsDisplayed()) {
				dwellingPage.internalSection.scrollToElement();
				dwellingPage.internalSection.click();
			}
			if (!testData.get("L" + locNo + "D" + bldgNo + "-ProtectionClassOverride").equals("")) {
				dwellingPage.enterInternalSectionDetails(testData, locationCount, dwellingCount);
			}

			dwellingPage.enterDwellingValuesNAHO(testData, locationCount, dwellingCount);
			waitTime(5); // Control is shifting to roof details link after entering
							// dwelling values instead of clicking on review
							// dwelling
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			Assertions.addInfo("Scanrio 07", "Verifying Hard Stop Message Appears for the Year Built as Producer");
			Assertions.verify(
					dwellingPage.dwellingDetailsErrorMessages.formatDynamicPath("Due to a year built prior to 1970")
							.checkIfElementIsPresent()
							&& dwellingPage.dwellingDetailsErrorMessages
									.formatDynamicPath("Due to a year built prior to 1970").checkIfElementIsDisplayed(),
					true, "Dwelling Page",
					"The Hard Stop message is displayed for the YOC prior to 1970: "
							+ dwellingPage.dwellingDetailsErrorMessages
									.formatDynamicPath("Due to a year built prior to 1970").getData(),
					false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Sign in as USM
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));

			// creating New account
			testData = data.get(dataValue4);
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.addDwellingDetails(testData, 1, 1);
			dwellingPage.addRoofDetails(testData, 1, 1);
			dwellingPage.enterProtectionDiscountsDetails(testData, 1, 1);
			if (dwellingPage.internalSection.checkIfElementIsPresent()
					&& dwellingPage.internalSection.checkIfElementIsDisplayed()) {
				dwellingPage.internalSection.scrollToElement();
				dwellingPage.internalSection.click();
			}
			if (!testData.get("L" + 1 + "D" + 1 + "-ProtectionClassOverride").equals("")) {
				dwellingPage.enterInternalSectionDetails(testData, 1, 1);
			}
			waitTime(3);

			dwellingPage.enterDwellingValuesNAHO(testData, 1, 1);
			waitTime(5); // Control is shifting to roof details link after entering
							// dwelling values instead of clicking on review
							// dwelling
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			Assertions.addInfo("Scanrio 07", "Verifying Warning Message Appears for the Year Built as USM");
			Assertions.verify(
					dwellingPage.dwellingDetailsErrorMessages.formatDynamicPath("Due to a year built prior to 1970")
							.checkIfElementIsPresent()
							&& dwellingPage.dwellingDetailsErrorMessages
									.formatDynamicPath("Due to a year built prior to 1970").checkIfElementIsDisplayed(),
					true, "Dwelling Page",
					"The Hard Stop message is displayed for the YOC prior to 1970: "
							+ dwellingPage.dwellingDetailsErrorMessages
									.formatDynamicPath("Due to a year built prior to 1970").getData(),
					false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");
			dwellingPage.override.scrollToElement();
			dwellingPage.override.click();
			if (existingAccountPage.pageName.getData().contains("Existing Account")) {
				existingAccountPage.OverrideExistingAccount();
			}
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			if (buildingNoLongerQuoteablePage.pageName.getData().contains("Buildings No")) {
				buildingNoLongerQuoteablePage.overrideNoLongerQuotableBuildings();
			}
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
				Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.click();
			}
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Binding the quote and creating the policy
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Clicking on answer No button
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered successfully");

			// Entering bind details
			// Entering policy details
			requestBindPage.enterPolicyDetailsNAHO(testData);

			// Entering payment information
			testData = data.get(dataValue1);
			requestBindPage.enterPaymentInformationNAHO(testData);

			// Entering inspection details
			requestBindPage.addInspectionContact(testData);

			// Entering AI values
			requestBindPage.addAdditionalInterest(testData);

			// Entering contact information
			requestBindPage.addContactInformation(testData);

			if (requestBindPage.submit.checkIfElementIsPresent()
					&& requestBindPage.submit.checkIfElementIsDisplayed()) {
				requestBindPage.submit.scrollToElement();
				requestBindPage.submit.click();
				confirmBindRequestPage.requestBindBtn.waitTillVisibilityOfElement(60);
				confirmBindRequestPage.requestBindBtn.scrollToElement();
				confirmBindRequestPage.requestBindBtn.click();
			}

			// Navigating to HomePage
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.passTest("Home Page", "Home Page loaded Successfully");

			// Navigating to Account Overview Page
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Home Page Quote Searched Successfully");

			// Opening Referral and approving referral
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();
			referralPage.approveOrDeclineRequest.scrollToElement();
			referralPage.approveOrDeclineRequest.click();
			requestBindPage.approve.scrollToElement();
			requestBindPage.approve.click();

			// Fetching Policy Numbers
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number: " + policyNumber);

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("VATC001 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("VATC001", "Executed Successfully");
			}
		}
	}
}