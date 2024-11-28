package com.icat.epicenter.test.naho.regression.NSNB;

import java.text.DecimalFormat;
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
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class NBSCTC002 extends AbstractNAHOTest {

	public NBSCTC002() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSNB/SC002.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		LoginPage loginPage = new LoginPage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		int data_Value1 = 0;
		int data_Value2 = 1;
		String quoteNumber;
		Map<String, String> testData = data.get(data_Value1);
		DecimalFormat df = new DecimalFormat("0.00");
		boolean isTestPassed = false;

		try {
			// Creating New Account
			homePage.enterPersonalLoginDetails();
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account Created successfully");

			// Entering Zipcode
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling  Page",
					"Dwelling Page Loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);

			// click on cancel button
			priorLossesPage.cancelButton.scrollToElement();
			priorLossesPage.cancelButton.click();
			dwellingPage.editBuilding.scrollToElement();
			dwellingPage.editBuilding.click();

			// Updating year built 2017 to 1969
			testData = data.get(data_Value2);
			dwellingPage.yearBuilt.scrollToElement();
			dwellingPage.yearBuilt.clearData();
			dwellingPage.yearBuilt.appendData(testData.get("L1D1-DwellingYearBuilt"));
			dwellingPage.yearBuilt.tab();
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();

			// IO-20810
			// Asserting hard stop message NC State for producer login when YOC less than
			// 1970, The hard stop
			// message is 'Due to a year built prior to 1970 this risk is ineligible for
			// coverage. If the home has undergone a gut rehab and supporting documentation
			// can be provided, we will reconsider offering terms. Please reach out to your
			// assigned UW.
			Assertions.addInfo("Scenario 01",
					"Asserting hard stop message for NC when yoc less than 1970 for prodcuer login");
			Assertions.verify(
					dwellingPage.dwellingDetailsErrorMessages
							.formatDynamicPath(
									"Due to a year built prior to 1970 this risk is ineligible for coverage.")
							.getData()
							.contains("Due to a year built prior to 1970 this risk is ineligible for coverage."),
					true, "Dwelling page",
					"The hard stop message, "
							+ dwellingPage.dwellingDetailsErrorMessages
									.formatDynamicPath(
											"Due to a year built prior to 1970 this risk is ineligible for coverage.")
									.getData()
							+ " is displayed",
					false, false);
			Assertions.passTest("Scenario 01", "Scenario 01 Ended");
			// IO-20810 ended

			// Updating year built 1969 to 2017
			testData = data.get(data_Value1);
			dwellingPage.yearBuilt.scrollToElement();
			dwellingPage.yearBuilt.clearData();
			dwellingPage.yearBuilt.appendData(testData.get("L1D1-DwellingYearBuilt"));
			dwellingPage.yearBuilt.tab();
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Enter quote details
			testData = data.get(data_Value1);
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);

			// Asserting NS and EQ Default Value in Create Quote Page
			Assertions.addInfo("Scenario 02", "Asserting NS and EQ Default Value in Create Quote Page");
			Assertions.verify(createQuotePage.namedStormData.getData(), testData.get("NamedStormValue"),
					"Create Quote Page",
					"Named Storm minimum deductible value is " + createQuotePage.namedStormData.getData(), false,
					false);
			Assertions.verify(createQuotePage.earthquakeDeductibleArrow.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "EQ Deductible Dropdown is displayed and default value is "
							+ createQuotePage.earthquakeData.getData(),
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Asserting Presence of EQ deductible[Values],EQ Loss Assessment
			// coverage[Values] and AOP/NS/EQ data in Create Quote Page
			testData = data.get(data_Value2);
			Assertions.addInfo("Scenario 03",
					"Asserting Presence of EQ deductible[Values],EQ Loss Assessment coverage[Values] and AOP/NS/EQ data in Create Quote Page");
			Assertions.verify(
					createQuotePage.eqLossAssessmentArrow.checkIfElementIsPresent()
							&& createQuotePage.eqLossAssessmentArrow.checkIfElementIsDisplayed(),
					false, "Create Quote Page",
					"EQ Loss Assessent Dropdown is not displayed when EQ Deductible value is "
							+ createQuotePage.earthquakeData.getData(),
					false, false);
			Assertions.verify(
					createQuotePage.aopNSEQData.formatDynamicPath("AOP/NS/EQ").checkIfElementIsPresent()
							&& createQuotePage.aopNSEQData.formatDynamicPath("AOP/NS/EQ").checkIfElementIsDisplayed(),
					false, "Create Quote Page", createQuotePage.aopNSEQData.formatDynamicPath("AOP/NS").getData()
							+ " is displayed when EQ is not selected",
					false, false);
			createQuotePage.earthquakeDeductibleArrow.scrollToElement();
			createQuotePage.earthquakeDeductibleArrow.click();

			// As per the EQDeductible values changes from None to Not Selected
			Assertions.verify(
					createQuotePage.earthquakeDeductibleOptionEQHO.formatDynamicPath("Not Selected")
							.checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					createQuotePage.earthquakeDeductibleOptionEQHO.formatDynamicPath("Not Selected").getData()
							+ " is displayed under EQ Deductible Options",
					false, false);
			for (int i = 0; i <= 4; i++) {
				testData = data.get(i);
				Assertions.verify(
						createQuotePage.earthquakeDeductibleOptionEQHO
								.formatDynamicPath(testData.get("EQDeductibleValue")).checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						createQuotePage.earthquakeDeductibleOptionEQHO
								.formatDynamicPath(testData.get("EQDeductibleValue")).getData()
								+ " EQ Deductible value is displayed",
						false, false);
			}
			testData = data.get(data_Value2);
			createQuotePage.earthquakeDeductibleOptionEQHO.formatDynamicPath(testData.get("EQDeductibleValue"))
					.scrollToElement();
			createQuotePage.earthquakeDeductibleOptionEQHO.formatDynamicPath(testData.get("EQDeductibleValue")).click();
			Assertions.verify(createQuotePage.eqLossAssessmentArrow.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "EQ Loss Assessent Dropdown is displayed when EQ Deductible value is Selected",
					false, false);
			Assertions.verify(
					createQuotePage.aopNSEQData.formatDynamicPath("AOP/NS/EQ").checkIfElementIsPresent()
							&& createQuotePage.aopNSEQData.formatDynamicPath("AOP/NS/EQ").checkIfElementIsDisplayed(),
					true, "Create Quote Page", createQuotePage.aopNSEQData.formatDynamicPath("AOP/NS/EQ").getData()
							+ " is displayed when EQ is selected",
					false, false);
			createQuotePage.eqLossAssessmentArrow.scrollToElement();
			createQuotePage.eqLossAssessmentArrow.click();
			Assertions.verify(
					createQuotePage.eqLossAssessmentOption.formatDynamicPath("None").checkIfElementIsDisplayed(), true,
					"Create Quote Page", createQuotePage.eqLossAssessmentOption.formatDynamicPath("None").getData()
							+ " is displayed under EQ Loss Assessment Options",
					false, false);
			for (int i = 1; i <= 4; i++) {
				testData = data.get(i);
				Assertions
						.verify(createQuotePage.eqLossAssessmentOption
								.formatDynamicPath(testData.get("EQLossAssessment")).checkIfElementIsDisplayed(), true,
								"Create Quote Page",
								createQuotePage.eqLossAssessmentOption
										.formatDynamicPath(testData.get("EQLossAssessment")).getData()
										+ " EQ Loss Assessment value is displayed",
								false, false);
			}
			createQuotePage.eqLossAssessmentOption.formatDynamicPath(testData.get("EQLossAssessment"))
					.scrollToElement();
			createQuotePage.eqLossAssessmentOption.formatDynamicPath(testData.get("EQLossAssessment")).click();
			createQuotePage.refreshPage();
			testData = data.get(data_Value1);
			int eqDed = Integer.parseInt(testData.get("EQDeductibleValue").replace("%", ""));
			int covADed = Integer.parseInt(testData.get("L1D1-DwellingCovA"));
			String calculatedeqDedValue = String.valueOf(covADed / 100 * eqDed);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on Continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// the below code commenting because of naho changes IO-20813
			// Assert Eq prior loss warning message
			/*
			 * Assertions.verify(referQuotePage.referQuote.checkIfElementIsDisplayed(),
			 * true, "Refer Quote Page", "Refer Quote page loaded successfully", false,
			 * false); Assertions.addInfo("Scenario 03",
			 * "Asserting Prior Loss Referral Message"); Assertions.verify(
			 * referQuotePage.referralMessages.formatDynamicPath("prior Earthquake loss").
			 * checkIfElementIsPresent() &&
			 * referQuotePage.referralMessages.formatDynamicPath("prior Earthquake loss")
			 * .checkIfElementIsDisplayed(), true, "Refer Quote Page",
			 * referQuotePage.referralMessages.formatDynamicPath("prior Earthquake loss").
			 * getData() + " is displayed", false, false); Assertions.addInfo("Scenario 03",
			 * "Scenario 03 Ended");
			 */

			// Entering details in refer quote page
			if (referQuotePage.referQuote.checkIfElementIsPresent()
					&& referQuotePage.referQuote.checkIfElementIsDisplayed()) {
				referQuotePage.contactName.scrollToElement();
				referQuotePage.contactName.setData(testData.get("ProducerName"));
				referQuotePage.contactEmail.scrollToElement();
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.scrollToElement();
				referQuotePage.referQuote.click();

				// verifying referral message
				Assertions.verify(referQuotePage.quoteNumberforReferral.checkIfElementIsDisplayed(), true,
						"Referral Page", "Quote " + referQuotePage.quoteNumberforReferral.getData()
								+ " referring to USM " + " is verified",
						false, false);
				quoteNumber = referQuotePage.quoteNumberforReferral.getData();

				// Logout as producer
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Login Page", "Logged Out as Producer Successfully");

				// Login as USM
				loginPage.refreshPage();
				loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
				Assertions.passTest("Home Page", "Logged in as USM Successfully");

				// Approve referral
				homePage.searchReferral(quoteNumber);

				// approving referral
				referralPage.clickOnApprove();

				// click on approve in Approve Decline Quote page
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");

				// Logout as USM
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Home Page", "Logged out as USM Successfully");

				// Login as Producer loginPage.refreshPage();
				loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
				Assertions.passTest("Home Page", "Logged in as Producer Successfully");

				// Search quote as producer
				homePage.enterPersonalLoginDetails();
				homePage.searchQuoteByProducer(quoteNumber);
			}

			// Asserting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Asserting Override Premium
			Assertions.addInfo("Scenario 04", "Asserting Absence of Override Premium Link");
			Assertions.verify(
					accountOverviewPage.overridePremiumLink.checkIfElementIsPresent()
							&& accountOverviewPage.overridePremiumLink.checkIfElementIsDisplayed(),
					false, "Account Overview Page", "Override Premium is not available for Producer", false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Asserting SLTF value on account overview page
			Assertions.addInfo("Scenario 05", "Asserting SLTF Values on Account Overview Page");
			String premiumOnAccountOverviewPage = accountOverviewPage.premiumValue.getData();
			String feesOnAccountOverviewPage = accountOverviewPage.feesValue.getData();
			String sltfValueAccountOverviewPage = accountOverviewPage.sltfValue.getData().replace(",", "");
			String surplusContributionAccountOverviewPage = accountOverviewPage.surplusContibutionValue.getData();

			double d_premiumValueOnAccountOverviewPage = Double
					.parseDouble(premiumOnAccountOverviewPage.replace("$", "").replace(",", ""));
			double d_FeeValueOnAccountOverviewPage = Double
					.parseDouble(feesOnAccountOverviewPage.replace("$", "").replace(",", ""));
			double d_surplusContributionAccountOverviewPage = Double.parseDouble(
					surplusContributionAccountOverviewPage.replace("$", "").replace(",", "").replace("%", ""));

			double d_sltfPercentageValue = Double.parseDouble(testData.get("SLTFPercentage"));
			double d_sltfValueOnAccountOverviewPage = (d_premiumValueOnAccountOverviewPage
					+ d_FeeValueOnAccountOverviewPage + d_surplusContributionAccountOverviewPage)
					* d_sltfPercentageValue;

			Assertions.verify(sltfValueAccountOverviewPage, "$" + df.format(d_sltfValueOnAccountOverviewPage),
					"Account Overview Page",
					"Actual and Calculated SLTF Values are matching and calculated according to 6% SC SLTF value "
							+ sltfValueAccountOverviewPage,
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Click on viewprint full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();

			// Asserting declinations column on view print full quote
			Assertions.addInfo("Scenario 06", "Asserting SLTF Values on View Print Full Quote Page");
			String premiumValue = viewOrPrintFullQuotePage.premiumValue.getData();
			String inspectionFeeValue = viewOrPrintFullQuotePage.insurerInspectionFeeValue.getData();
			String policyFeeValue = viewOrPrintFullQuotePage.insurerPolFeeVlaue.getData();
			String sltfValue = viewOrPrintFullQuotePage.surplusLinesTaxNaho.getData().replace(",", "");
			String surplusContribution = viewOrPrintFullQuotePage.surplusContributionValue.getData();

			double d_premiumValue = Double.parseDouble(premiumValue.replace("$", "").replace(",", ""));
			double d_inspectionFeeValue = Double.parseDouble(inspectionFeeValue.replace("$", "").replace(",", ""));
			double d_policyFeeValue = Double.parseDouble(policyFeeValue.replace("$", "").replace(",", ""));
			double d_surplusContribution = Double
					.parseDouble(surplusContribution.replace("$", "").replace(",", "").replace("%", ""));
			double d_sltfValue = (d_premiumValue + d_inspectionFeeValue + d_policyFeeValue + d_surplusContribution)
					* d_sltfPercentageValue;

			Assertions.verify(sltfValue, "$" + df.format(d_sltfValue), "View/Print Full Quote Page",
					"Actual and Calculated SLTF Values are matching and calculated according to 6% SC SLTF value "
							+ sltfValue,
					false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Asserting Declination Wording in View/Print Full Quote Page
			Assertions.addInfo("Scenario 07", "Assertiong Declination Wording in View/Print Full Quote Page");
			Assertions.verify(
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Declining Company")
							.getNoOfWebElements(),
					3, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Declining Company").getData()
							+ " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("NAIC").getNoOfWebElements(), 3,
					"View/Print Full Quote Page",
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("NAIC").getData() + " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Reason for Declination")
							.getNoOfWebElements(),
					3, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Reason for Declination").getData()
							+ " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Date Declined").getNoOfWebElements(),
					3, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Date Declined").getData()
							+ " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Producing Agent Name")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Producing Agent Name")
									.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Producing Agent Name").getData()
							+ " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Producing Agent Signature")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Producing Agent Signature")
									.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Producing Agent Signature").getData()
							+ " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Date").checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Date")
									.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Date").getData() + " is displayed",
					false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Asserting Hardcoded Insured Wording in View/Print Full Quote Page
			Assertions.addInfo("Scenario 08", "Asserting Hardcoded Insured Wording in View/Print Full Quote Page");
			Assertions.verify(
					viewOrPrintFullQuotePage.insurerWording.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.insurerWording.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.insurerWording.getData() + " is displayed", false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// Asserting EQ Deductible Value is calculated correctly in View/Print Full
			// Quote Page
			Assertions.addInfo("Scenario 09",
					"Asserting EQ Deductible Value is calculated correctly in View/Print Full Quote Page");
			Assertions.verify(
					viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Earthquake").getData()
							.substring(5,
									viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Earthquake").getData()
											.length() - 1)
							.replace(",", "").contains(calculatedeqDedValue),
					true, "View/Print Full Quote Page", "EQ Deductible is calculated based on EQ% * CoverageA value",
					false, false);
			String eqDeductibleValue = viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Earthquake")
					.getData();
			Assertions.verify(
					viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Earthquake").checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Earthquake")
									.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"EQ Deductible" + viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Earthquake").getData()
							+ " is displayed for quote 1",
					false, false);
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			// Click on back button
			viewOrPrintFullQuotePage.scrollToTopPage();
			viewOrPrintFullQuotePage.waitTime(2);
			viewOrPrintFullQuotePage.backButton.click();

			// Asserting EQ Deductible is same as original in Alt Quote
			// Select Alt quote 1
			Assertions.addInfo("Scenario 10", "Asserting EQ Deductible is same as original in Alt Quote");
			accountOverviewPage.quoteOptions1TotalPremium.scrollToElement();
			accountOverviewPage.quoteOptions1TotalPremium.click();
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.verify(viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Earthquake").getData(),
					eqDeductibleValue, "View/Print Full Quote Page",
					"EQ Deductible for Alt quote 1, "
							+ viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Earthquake").getData()
							+ " is same as quote 1",
					false, false);
			viewOrPrintFullQuotePage.scrollToTopPage();
			viewOrPrintFullQuotePage.waitTime(2);
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			accountOverviewPage.quoteNumberLink.formatDynamicPath(quoteNumber).scrollToElement();
			accountOverviewPage.quoteNumberLink.formatDynamicPath(quoteNumber).click();

			// Select Alt quote 2
			accountOverviewPage.quoteOptions2TotalPremium.scrollToElement();
			accountOverviewPage.quoteOptions2TotalPremium.click();
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.verify(viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Earthquake").getData(),
					eqDeductibleValue, "View/Print Full Quote Page",
					"EQ Deductible for Alt quote 2, "
							+ viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Earthquake").getData()
							+ " is same as quote 1",
					false, false);
			Assertions.addInfo("Scenario 10", "Scenario 10 Ended");

			// Click on back button
			viewOrPrintFullQuotePage.scrollToTopPage();
			viewOrPrintFullQuotePage.waitTime(2);
			viewOrPrintFullQuotePage.backButton.click();
			accountOverviewPage.quoteNumberLink.formatDynamicPath(quoteNumber, 1).scrollToElement();
			accountOverviewPage.quoteNumberLink.formatDynamicPath(quoteNumber, 1).click();

			// Binding the quote and creating the policy
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

			// Clicking on answer no button
			underwritingQuestionsPage.answerNoToAllQuestions.waitTillVisibilityOfElement(30);
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.answerNoToAllQuestions.scrollToElement();
			underwritingQuestionsPage.answerNoToAllQuestions.click();

			// Clicking on save button
			underwritingQuestionsPage.saveButton.scrollToElement();
			underwritingQuestionsPage.saveButton.click();

			// Entering bind details
			quoteNumber = requestBindPage.quoteNumber.getData();
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Values Entered Successfully");

			// SignOut as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Producer successfully");

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");

			// searching the quote number in grid and clicking on the quote number link
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Searched Submitted Quote " + quoteNumber + " successfullly");

			// approving referral
			accountOverviewPage.uploadPreBindApproveAsUSM();
			referralPage.clickOnApprove();

			// click on approve in Approve Decline Quote page
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			// Asserting policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Number is " + policySummaryPage.policyNumber.getData(), false, false);

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBSCTC002 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBSCTC002 ", "Executed Successfully");
			}
		}
	}
}
