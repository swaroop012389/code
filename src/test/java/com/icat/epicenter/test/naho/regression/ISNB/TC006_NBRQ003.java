/** Program Description: Validating quote details and IO-21540 as a producer)
 *  Author			   : pavan mule
 *  Date of Creation   : 07/15/2024
 **/
package com.icat.epicenter.test.naho.regression.ISNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC006_NBRQ003 extends AbstractNAHOTest {

	public TC006_NBRQ003() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBRQ003.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		LoginPage loginPage = new LoginPage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();

		// Initializing Variables
		Map<String, String> testData;
		int data_Value1 = 0;
		int data_Value2 = 1;
		String quoteNo;
		String quoteNo1;
		int quoteLen;
		testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Creating a new account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Checking the processing of Zipcode
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering dwelling details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering Prior Loss details
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);
			Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");

			// Entering Quote details
			Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Entering Referral Details
			if (referQuotePage.contactName.checkIfElementIsPresent()
					&& referQuotePage.contactName.checkIfElementIsDisplayed()) {
				referQuotePage.contactName.waitTillVisibilityOfElement(60);
				referQuotePage.contactName.clearData();
				referQuotePage.contactName.scrollToElement();
				referQuotePage.contactName.setData(testData.get("ProducerName"));
				referQuotePage.contactEmail.clearData();
				referQuotePage.contactEmail.scrollToElement();
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.scrollToElement();
				referQuotePage.referQuote.click();

				// Getting quote no
				quoteNo = referQuotePage.quoteNumberforReferral.getData();
				Assertions.passTest("Refer Quote Page", "The Quote Number 1 is : " + quoteNo);

				// Sign out as producer
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();

				// Sign in as USM
				loginPage.refreshPage();
				loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
				Assertions.passTest("Login", "Logged in to application successfully");

				// Go to HomePage
				homePage.goToHomepage.click();
				homePage.searchQuote(quoteNo);
				Assertions.passTest("Home Page", "Quote for referral is searched successfully");

				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();

				// Clicking on pick up button
				if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
					referralPage.pickUp.scrollToElement();
					referralPage.pickUp.click();
				}

				// Approve Referral
				Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true,
						"Referral Page", "Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");

				Assertions.verify(approveDeclineQuotePage.approveButton.checkIfElementIsDisplayed(), true,
						"Approve Decline Quote Page", "Approve Decline Quote page loaded successfully", false, false);
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Approve Decline Quote Page", "Bind Request approved successfully");
				Assertions.passTest("Approve Decline Quote Page",
						"Quote number : " + quoteNo + " approved successfully");

				// Sign out as USM
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();

				// Sign in as Producer
				loginPage.refreshPage();
				loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
				Assertions.passTest("Login", "Logged in to application successfully");

				// Handling the pop up
				homePage.enterPersonalLoginDetails();

				// Searching for Quote
				homePage.searchQuoteByProducer(quoteNo);
			} else {
				// Getting quote no
				quoteNo = accountOverviewPage.quoteNumber.getData();
				Assertions.passTest("Account Overview Page", "The Quote Number 1 is " + quoteNo);
			}

			// Clicking on create another quote
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();

			// Validating the quote details
			Assertions.addInfo("Scenario 01", "Validating the quote details");
			if (testData.get("FormType").equals("HO3")) {
				Assertions.verify(createQuotePage.formType_HO3.checkIfElementIsSelected(), true, "Create Quote Page",
						"Form type : " + testData.get("FormType") + " retained as per the created quote", false, false);
			} else {
				Assertions.verify(createQuotePage.formType_HO5.checkIfElementIsSelected(), true, "Create Quote Page",
						"Form type : " + testData.get("FormType") + " retained as per the created quote", false, false);
			}
			if (!testData.get("NamedStormValue").isEmpty()) {
				Assertions.verify(createQuotePage.namedStormData.getData(), testData.get("NamedStormValue"),
						"Create Quote Page", "Named Storm value : " + createQuotePage.namedStormData.getData()
								+ " retained as per the created quote",
						false, false);
			} else {
				Assertions.verify(createQuotePage.namedStormData.checkIfElementIsDisplayed(), true, "Create Quote Page",
						"Named Storm value : " + createQuotePage.namedStormData.getData() + " default is populated",
						false, false);
			}
			if (!testData.get("AOWHDeductibleValue").isEmpty()) {
				Assertions.verify(createQuotePage.aowhDeductibleData.getData(), testData.get("AOWHDeductibleValue"),
						"Create Quote Page", "AOWH value : " + createQuotePage.aowhDeductibleData.getData()
								+ " retained as per the created quote",
						false, false);
			} else {
				Assertions.verify(createQuotePage.aowhDeductibleData.checkIfElementIsDisplayed(), true,
						"Create Quote Page",
						"AOWH value : " + createQuotePage.aowhDeductibleData.getData() + " default is populated", false,
						false);
			}
			if (!testData.get("AOPDeductibleValue").isEmpty()) {
				Assertions.verify(createQuotePage.aopDeductibleData.getData(), testData.get("AOPDeductibleValue"),
						"Create Quote Page", "AOP value : " + createQuotePage.aopDeductibleData.getData()
								+ " retained as per the created quote",
						false, false);
			} else {
				Assertions.verify(createQuotePage.aopDeductibleData.checkIfElementIsDisplayed(), true,
						"Create Quote Page",
						"AOP value : " + createQuotePage.aopDeductibleData.getData() + " default is populated", false,
						false);
			}
			if (!testData.get("AdditionalDwellingCoverage").isEmpty()) {
				Assertions.verify(createQuotePage.additionalDwellingCovDedValue.getData(),
						testData.get("AdditionalDwellingCoverage"), "Create Quote Page",
						"Additional Dwelling Coverage value : "
								+ createQuotePage.additionalDwellingCovDedValue.getData()
								+ " retained as per the created quote",
						false, false);
			} else {
				Assertions.verify(createQuotePage.additionalDwellingCovDedValue.checkIfElementIsDisplayed(), true,
						"Create Quote Page",
						"Additional Dwelling Coverage value : "
								+ createQuotePage.additionalDwellingCovDedValue.getData() + " default is populated",
						false, false);
			}
			if (!testData.get("LimitedPool").isEmpty()) {
				Assertions.verify(createQuotePage.poolDedValue.getData(), testData.get("LimitedPool"),
						"Create Quote Page", "Pool and/or Patio Enclosures value : "
								+ createQuotePage.poolDedValue.getData() + " retained as per the created quote",
						false, false);
			} else {
				Assertions.verify(createQuotePage.poolDedValue.checkIfElementIsDisplayed(), true, "Create Quote Page",
						"Pool and/or Patio Enclosures value : " + createQuotePage.poolDedValue.getData()
								+ " default is populated",
						false, false);
			}
			if (!testData.get("LimitedWaterSump").isEmpty()) {
				Assertions.verify(createQuotePage.waterBackUpDedValue.getData(), testData.get("LimitedWaterSump"),
						"Create Quote Page", "Water Back-up value : " + createQuotePage.waterBackUpDedValue.getData()
								+ " retained as per the created quote",
						false, false);
			} else {
				Assertions
						.verify(createQuotePage.waterBackUpDedValue.checkIfElementIsDisplayed(), true,
								"Create Quote Page", "Water Back-up value : "
										+ createQuotePage.waterBackUpDedValue.getData() + " default is populated",
								false, false);
			}
			if (!testData.get("LossAssessment").isEmpty()) {
				Assertions.verify(createQuotePage.lossAssessmentDedValue.getData(), testData.get("LossAssessment"),
						"Create Quote Page",
						"Loss Assessment value : " + createQuotePage.lossAssessmentDedValue.getData()
								+ " retained as per the created quote",
						false, false);

			} else {
				Assertions.verify(createQuotePage.lossAssessmentDedValue.checkIfElementIsDisplayed(), true,
						"Create Quote Page", "Loss Assessment value : "
								+ createQuotePage.lossAssessmentDedValue.getData() + " default is populated",
						false, false);
			}
			if (!testData.get("IdentityFraud").isEmpty()) {
				Assertions.verify(createQuotePage.identityFraudDedValue.getData(), testData.get("IdentityFraud"),
						"Create Quote Page", "Identity Fraud value : " + createQuotePage.identityFraudDedValue.getData()
								+ " retained as per the created quote",
						false, false);
			} else {
				Assertions
						.verify(createQuotePage.identityFraudDedValue.checkIfElementIsDisplayed(), true,
								"Create Quote Page", "Identity Fraud value : "
										+ createQuotePage.identityFraudDedValue.getData() + " default is populated",
								false, false);
			}
			if (!testData.get("PersonalInjury").isEmpty()) {
				Assertions.verify(createQuotePage.personalInjuryDedValue.getData(), testData.get("PersonalInjury"),
						"Create Quote Page",
						"Personal Injury value : " + createQuotePage.personalInjuryDedValue.getData()
								+ " retained as per the created quote",
						false, false);
			} else {
				Assertions.verify(createQuotePage.personalInjuryDedValue.checkIfElementIsDisplayed(), true,
						"Create Quote Page", "Personal Injury value : "
								+ createQuotePage.personalInjuryDedValue.getData() + " default is populated",
						false, false);
			}
			if (!testData.get("ServiceLine").isEmpty()) {
				Assertions.verify(createQuotePage.lineCoverageDedValue.getData(), testData.get("ServiceLine"),
						"Create Quote Page", "Service/Utility Line Coverage value : "
								+ createQuotePage.lineCoverageDedValue.getData() + " retained as per the created quote",
						false, false);
			} else {
				Assertions.verify(createQuotePage.lineCoverageDedValue.checkIfElementIsDisplayed(), true,
						"Create Quote Page", "Service/Utility Line Coverage value : "
								+ createQuotePage.lineCoverageDedValue.getData() + " default is populated",
						false, false);
			}
			if (!testData.get("EquipmentBreakdown").isEmpty()) {
				Assertions.verify(createQuotePage.eQBreakdownDedValue.getData(), testData.get("EquipmentBreakdown"),
						"Create Quote Page", "Equipment Breakdown value : "
								+ createQuotePage.eQBreakdownDedValue.getData() + " retained as per the created quote",
						false, false);
			} else {
				Assertions.verify(createQuotePage.eQBreakdownDedValue.checkIfElementIsDisplayed(), true,
						"Create Quote Page", "Equipment Breakdown value : "
								+ createQuotePage.eQBreakdownDedValue.getData() + " default is populated",
						false, false);
			}
			if (!testData.get("OrdinanceOrLaw").isEmpty()) {
				Assertions.verify(createQuotePage.ordinanceOrLawDedValue.getData(), testData.get("OrdinanceOrLaw"),
						"Create Quote Page",
						"Ordinance or Law value : " + createQuotePage.ordinanceOrLawDedValue.getData()
								+ " retained as per the created quote",
						false, false);
			} else {
				Assertions.verify(createQuotePage.ordinanceOrLawDedValue.checkIfElementIsDisplayed(), true,
						"Create Quote Page", "Ordinance or Law value : "
								+ createQuotePage.ordinanceOrLawDedValue.getData() + " default is populated",
						false, false);
			}
			if (!testData.get("MoldProperty").isEmpty()) {
				Assertions.verify(createQuotePage.moldPropertyDedValue.getData(), testData.get("MoldProperty"),
						"Create Quote Page", "Mold - Property value : " + createQuotePage.moldPropertyDedValue.getData()
								+ " retained as per the created quote",
						false, false);
			} else {
				Assertions
						.verify(createQuotePage.moldPropertyDedValue.checkIfElementIsDisplayed(), true,
								"Create Quote Page", "Mold - Property value : "
										+ createQuotePage.moldPropertyDedValue.getData() + " default is populated",
								false, false);
			}
			if (!testData.get("MoldLiability").isEmpty()) {
				Assertions.verify(createQuotePage.moldLiabilityDedValue.getData(), testData.get("MoldLiability"),
						"Create Quote Page",
						"Mold - Liability value : " + createQuotePage.moldLiabilityDedValue.getData()
								+ " retained as per the created quote",
						false, false);
			} else {
				Assertions.verify(createQuotePage.moldLiabilityDedValue.checkIfElementIsDisplayed(), true,
						"Create Quote Page", "Mold - Liability value : "
								+ createQuotePage.moldLiabilityDedValue.getData() + " default is populated",
						false, false);
			}
			if (!testData.get("L1D1-DwellingCovA").isEmpty()) {
				Assertions.verify(createQuotePage.coverageADwelling.getData(), testData.get("L1D1-DwellingCovA"),
						"Create Quote Page", "Cov A - Dwelling value : " + createQuotePage.coverageADwelling.getData()
								+ " retained as per the created quote",
						false, false);
			} else {
				Assertions
						.verify(createQuotePage.coverageADwelling.checkIfElementIsDisplayed(), true,
								"Create Quote Page", "Cov A - Dwelling value : "
										+ createQuotePage.coverageADwelling.getData() + " default is populated",
								false, false);
			}
			if (!testData.get("L1D1-DwellingCovB").isEmpty()) {
				Assertions.verify(createQuotePage.coverageBOtherStructures.getData(), testData.get("L1D1-DwellingCovB"),
						"Create Quote Page",
						"Cov B - Other Structures value : " + createQuotePage.coverageBOtherStructures.getData()
								+ " retained as per the created quote",
						false, false);
			} else {
				Assertions.verify(createQuotePage.coverageBOtherStructures.checkIfElementIsDisplayed(), true,
						"Create Quote Page", "Cov B - Other Structures value : "
								+ createQuotePage.coverageBOtherStructures.getData() + " default is populated",
						false, false);
			}
			if (!testData.get("L1D1-DwellingCovC").isEmpty()) {
				Assertions.verify(createQuotePage.coverageCPersonalProperty.getData(),
						testData.get("L1D1-DwellingCovC"), "Create Quote Page",
						"Cov C - Personal Property value : " + createQuotePage.coverageCPersonalProperty.getData()
								+ " retained as per the created quote",
						false, false);
			} else {
				Assertions.verify(createQuotePage.coverageCPersonalProperty.checkIfElementIsDisplayed(), true,
						"Create Quote Page", "Cov C - Personal Property value : "
								+ createQuotePage.coverageCPersonalProperty.getData() + " default is populated",
						false, false);
			}
			if (!testData.get("L1D1-DwellingCovD").isEmpty()) {
				Assertions.verify(createQuotePage.coverageDFairRental.getData(), testData.get("L1D1-DwellingCovD"),
						"Create Quote Page", "Cov D - Loss of Use value : "
								+ createQuotePage.coverageDFairRental.getData() + " retained as per the created quote",
						false, false);
			} else {
				Assertions.verify(createQuotePage.coverageDFairRental.checkIfElementIsDisplayed(), true,
						"Create Quote Page", "Cov D - Loss of Use value : "
								+ createQuotePage.coverageDFairRental.getData() + " default is populated",
						false, false);
			}
			if (!testData.get("L1D1-DwellingCovE").isEmpty()) {
				Assertions
						.verify(createQuotePage.covEValue.getData(), testData.get("L1D1-DwellingCovE"),
								"Create Quote Page", "Cov E - Personal Liability value : "
										+ createQuotePage.covEValue.getData() + " retained as per the created quote",
								false, false);
			} else {
				Assertions.verify(createQuotePage.covEValue.checkIfElementIsDisplayed(), true, "Create Quote Page",
						"Cov E - Personal Liability value : " + createQuotePage.covEValue.getData()
								+ " default is populated",
						false, false);
			}
			if (!testData.get("L1D1-DwellingCovF").isEmpty()) {
				Assertions
						.verify(createQuotePage.covFValue.getData(), testData.get("L1D1-DwellingCovF"),
								"Create Quote Page", "Cov F - Medical Payments value : "
										+ createQuotePage.covFValue.getData() + " retained as per the created quote",
								false, false);
			} else {
				Assertions.verify(createQuotePage.covFValue.checkIfElementIsDisplayed(), true, "Create Quote Page",
						"Cov F - Medical Payments value : " + createQuotePage.covFValue.getData()
								+ " default is populated",
						false, false);
			}
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Entering Quote details
			testData = data.get(data_Value2);
			Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
			createQuotePage.enterQuoteName(testData.get("QuoteName"));
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			if (referQuotePage.contactName.checkIfElementIsPresent()
					&& referQuotePage.contactName.checkIfElementIsDisplayed()) {
				referQuotePage.contactName.waitTillVisibilityOfElement(60);
				referQuotePage.contactName.clearData();
				referQuotePage.contactName.scrollToElement();
				referQuotePage.contactName.setData(testData.get("ProducerName"));
				referQuotePage.contactEmail.clearData();
				referQuotePage.contactEmail.scrollToElement();
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.click();
			}

			// Asserting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNo1 = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNo1);

			// Validating the details of quote 2
			Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"Quote : " + quoteNo1 + " with new deductibles and coverages created successfully", false, false);

			// Adding code for IO-21540 and IO-22125
			Assertions.addInfo("Scenario 02",
					"Asserting and Validating warning message when roof age = 16-20 years and roof cladding as a producer");
			// Asserting warning message when year built 16-20 years and roof
			// cladding='Hurricane Shingle'
			// 'Normal Shingle','Steel or Metal','Built Up','Single Ply Membrane' and
			// 'Architectural Shingle'
			// Warning message is = Roof Excluded From Replacement Cost and Covered Only at
			// Actual Cash Value.
			for (int i = 2; i <= 8; i++) {
				testData = data.get(i);

				// Edit dwelling and update Year built and roof cladding
				accountOverviewPage.editDwelling.scrollToElement();
				accountOverviewPage.editDwelling.click();

				// Entering Location 1 Dwelling 1 Details
				dwellingPage.yearBuilt.scrollToElement();
				dwellingPage.yearBuilt.clearData();
				if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
					dwellingPage.expiredQuotePopUp.scrollToElement();
					dwellingPage.continueWithUpdateBtn.scrollToElement();
					dwellingPage.continueWithUpdateBtn.click();
					dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
				}
				dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));
				if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
					dwellingPage.expiredQuotePopUp.scrollToElement();
					dwellingPage.continueWithUpdateBtn.scrollToElement();
					dwellingPage.continueWithUpdateBtn.click();
					dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
				}
				dwellingPage.yearBuilt.tab();
				dwellingPage.addRoofDetails(testData, 1, 1);
				Assertions.passTest("Dwelling Page",
						"Roof Cladding is updated to " + testData.get("L1D1-DwellingRoofCladding")
								+ " and Year Built is updated to " + testData.get("L1D1-DwellingYearBuilt"));

				// Review dwelling and continue
				dwellingPage.waitTime(2);
				dwellingPage.reviewDwelling.scrollToElement();
				dwellingPage.reviewDwelling.click();
				dwellingPage.createQuote.scrollToElement();
				dwellingPage.createQuote.click();

				// Entering Create quote page Details
				createQuotePage.scrollToBottomPage();
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();
				if (createQuotePage.errorMessageWarningPage.formatDynamicPath("Actual Cash Value")
						.checkIfElementIsPresent()
						&& createQuotePage.errorMessageWarningPage.formatDynamicPath("Actual Cash Value")
								.checkIfElementIsDisplayed()) {
					createQuotePage.errorMessageWarningPage.formatDynamicPath("Actual Cash Value")
							.waitTillPresenceOfElement(60);
					Assertions.verify(
							createQuotePage.errorMessageWarningPage
									.formatDynamicPath("Actual Cash Value").getData().contains("Actual Cash Value"),
							true, "Create Quote Page",
							"Warning message is "
									+ createQuotePage.errorMessageWarningPage.formatDynamicPath("Actual Cash Value")
											.getData()
									+ " is displayed when Roof Cladding is " + testData.get("L1D1-DwellingRoofCladding")
									+ " and Year Built is " + testData.get("L1D1-DwellingYearBuilt")
									+ " and agent can continue it",
							false, false);
				}

				// roof cladding = 'Tile or Clay' and year built 16-20, warning message is =
				// 'Roof Coverage Excluded'
				else {
					Assertions.verify(createQuotePage.errorMessageWarningPage
							.formatDynamicPath("Roof Coverage Excluded").getData().contains("Roof Coverage Excluded"),
							true, "Create Quote Page",
							"Warning message is "
									+ createQuotePage.errorMessageWarningPage
											.formatDynamicPath("Roof Coverage Excluded").getData()
									+ " is displayed when Roof Cladding is " + testData.get("L1D1-DwellingRoofCladding")
									+ " and Year Built is " + testData.get("L1D1-DwellingYearBuilt")
									+ " and agent can continue it",
							false, false);
				}
				createQuotePage.goBack.scrollToElement();
				createQuotePage.goBack.click();
				createQuotePage.previous.scrollToElement();
				createQuotePage.previous.click();

			}
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Asserting and validating hard stop message when year built 21-25 years and
			// roof
			// cladding='Hurricane Shingle'
			// 'Normal Shingle','Steel or Metal','Other', and
			// 'Architectural Shingle'
			// Warning message is = The quoted building has a roof age outside of our
			// guidelines. For consideration, please provide your ICAT Online Underwriter
			// with additional information regarding the condition of the roof, such as a
			// recent inspection
			Assertions.addInfo("Scenario 03",
					"Asserting and validating referal message and hard stop message when roof age= 21-25 years and roof cladding as a producer");
			for (int i = 9; i <= 17; i++) {
				testData = data.get(i);

				// Edit dwelling and update Year built and roof cladding
				accountOverviewPage.editDwelling.scrollToElement();
				accountOverviewPage.editDwelling.click();

				// Entering Location 1 Dwelling 1 Details
				dwellingPage.yearBuilt.scrollToElement();
				dwellingPage.yearBuilt.clearData();
				if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
					dwellingPage.expiredQuotePopUp.scrollToElement();
					dwellingPage.continueWithUpdateBtn.scrollToElement();
					dwellingPage.continueWithUpdateBtn.click();
					dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
				}
				dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));
				if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
					dwellingPage.expiredQuotePopUp.scrollToElement();
					dwellingPage.continueWithUpdateBtn.scrollToElement();
					dwellingPage.continueWithUpdateBtn.click();
					dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
				}
				dwellingPage.yearBuilt.tab();
				dwellingPage.addRoofDetails(testData, 1, 1);
				Assertions.passTest("Dwelling Page",
						"Roof Cladding is updated to " + testData.get("L1D1-DwellingRoofCladding")
								+ " and Year Built is updated to " + testData.get("L1D1-DwellingYearBuilt"));

				// Review dwelling and continue
				dwellingPage.waitTime(2);
				dwellingPage.reviewDwelling.scrollToElement();
				dwellingPage.reviewDwelling.click();
				dwellingPage.createQuote.scrollToElement();
				dwellingPage.createQuote.click();

				// Entering Create quote page Details
				createQuotePage.scrollToBottomPage();
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();
				if (referQuotePage.referralMessages.formatDynamicPath("roof age").checkIfElementIsPresent()
						&& referQuotePage.referralMessages.formatDynamicPath("roof age").checkIfElementIsDisplayed()) {
					Assertions.verify(
							referQuotePage.referralMessages
									.formatDynamicPath("roof age").getData().contains("roof age"),
							true, "Refer Quote Page",
							"Referal message is "
									+ referQuotePage.referralMessages.formatDynamicPath("roof age").getData()
									+ " is displayed when Roof Cladding is " + testData.get("L1D1-DwellingRoofCladding")
									+ " and Year Built is " + testData.get("L1D1-DwellingYearBuilt")
									+ " and agent cannot override it",
							false, false);
				}

				// Asserting and verifying when year built = 21-25 and roof cladding = Built Up,
				// Single Ply Membrane, Tile or Clay, Wood Shakes or Wood Shingles
				// Hard stop message = The account is ineligible due to the roof age being
				// outside of ICAT's guidelines. If you have additional information that adjusts
				// the roof age, please email it to your ICAT Online Underwriter.
				else {
					Assertions.verify(createQuotePage.globalErr.getData().contains("roof age"), true,
							"Create Quote Page",
							"Hard stop message is " + createQuotePage.globalErr.getData()
									+ " is displayed when Roof Cladding is " + testData.get("L1D1-DwellingRoofCladding")
									+ " and Year Built is " + testData.get("L1D1-DwellingYearBuilt")
									+ " and agent cannot override it",
							false, false);
				}
				createQuotePage.locationStep1.scrollToElement();
				createQuotePage.locationStep1.click();

			}
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Asserting and verifying hard stop message when year built = 26+ and roof
			// cladding = Built Up,
			// Single Ply Membrane, Tile or Clay, Wood Shakes or Wood Shingles,Hurricane
			// Shingle'
			// 'Normal Shingle','Steel or Metal','Other', and
			// 'Architectural Shingle'
			// Hard stop message = The account is ineligible due to the roof age being
			// outside of ICAT's guidelines. If you have additional information that adjusts
			// the roof age, please email it to your ICAT Online Underwriter.
			Assertions.addInfo("Scenario 04",
					"Asserting and verifying hard stop message and referal message when year built = 26+ and roof cladding as a producer");
			for (int i = 18; i <= 27; i++) {
				testData = data.get(i);

				// Edit dwelling and update Year built and roof cladding
				accountOverviewPage.editDwelling.scrollToElement();
				accountOverviewPage.editDwelling.click();

				// Entering Location 1 Dwelling 1 Details
				dwellingPage.yearBuilt.scrollToElement();
				dwellingPage.yearBuilt.clearData();
				if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
					dwellingPage.expiredQuotePopUp.scrollToElement();
					dwellingPage.continueWithUpdateBtn.scrollToElement();
					dwellingPage.continueWithUpdateBtn.click();
					dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
				}
				dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));
				if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
					dwellingPage.expiredQuotePopUp.scrollToElement();
					dwellingPage.continueWithUpdateBtn.scrollToElement();
					dwellingPage.continueWithUpdateBtn.click();
					dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
				}
				dwellingPage.yearBuilt.tab();
				dwellingPage.addRoofDetails(testData, 1, 1);
				Assertions.passTest("Dwelling Page",
						"Roof Cladding is updated to " + testData.get("L1D1-DwellingRoofCladding")
								+ " and Year Built is updated to " + testData.get("L1D1-DwellingYearBuilt"));

				// Review dwelling and continue
				dwellingPage.waitTime(2);
				dwellingPage.reviewDwelling.scrollToElement();
				dwellingPage.reviewDwelling.click();
				dwellingPage.createQuote.scrollToElement();
				dwellingPage.createQuote.click();

				// Entering Create quote page Details
				createQuotePage.scrollToBottomPage();
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();
				if (createQuotePage.globalErr.checkIfElementIsPresent()
						&& createQuotePage.globalErr.checkIfElementIsDisplayed()) {
					Assertions.verify(createQuotePage.globalErr.getData().contains("roof age"), true,
							"Create Quote Page",
							"Hard stop message is " + createQuotePage.globalErr.getData()
									+ " is displayed when Roof Cladding is " + testData.get("L1D1-DwellingRoofCladding")
									+ " and Year Built is " + testData.get("L1D1-DwellingYearBuilt")
									+ " and agent cannot override it",
							false, false);
				} else {
					Assertions.verify(
							referQuotePage.referralMessages
									.formatDynamicPath("roof age").getData().contains("roof age"),
							true, "Refer Quote Page",
							"Referal message is "
									+ referQuotePage.referralMessages.formatDynamicPath("roof age").getData()
									+ " is displayed when Roof Cladding is " + testData.get("L1D1-DwellingRoofCladding")
									+ " and Year Built is " + testData.get("L1D1-DwellingYearBuilt")
									+ " and agent cannot override it",
							false, false);
				}

				createQuotePage.locationStep1.scrollToElement();
				createQuotePage.locationStep1.click();

			}
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC006 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC006 ", "Executed Successfully");
			}
		}
	}
}