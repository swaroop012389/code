package com.icat.epicenter.test.naho.regression.NSNB;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

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

public class NBNCTC002 extends AbstractNAHOTest {

	public NBNCTC002() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSNB/NC002.xls";
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

		DecimalFormat df = new DecimalFormat("0.00");
		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
		String quoteNumber;
		int quoteLen;
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
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);

			// click on cancel button
			priorLossesPage.cancelButton.scrollToElement();
			priorLossesPage.cancelButton.click();
			dwellingPage.editBuilding.scrollToElement();
			dwellingPage.editBuilding.click();

			// Updating year built 2016 to 1969
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

			// Updating year built 1969 to 2016
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
			Assertions.addInfo("Scenario 02", "Asserting Default NS value in Create Quote Page");
			Assertions.verify(createQuotePage.namedStormData.getData(), testData.get("NamedStormValue"),
					"Create Quote Page", "Named Strom default value is " + testData.get("NamedStormValue"), false,
					false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Click on Continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Below code adding because of quote is referring because of modeling service
			// down message
			if (referQuotePage.referQuote.checkIfElementIsPresent()
					&& referQuotePage.referQuote.checkIfElementIsDisplayed()) {
				referQuotePage.contactEmail.scrollToElement();
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.scrollToElement();
				referQuotePage.referQuote.click();
				Assertions.passTest("Refer Quote Page", "Quote referred successfully");
				quoteNumber = referQuotePage.quoteNumberforReferral.getData();
				Assertions.passTest("Refer Quote Page", "The Quote Number is " + quoteNumber);

				// SignOut as Producer
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Home Page", "Logged out as Producer successfully");

				// Sign in as USM
				loginPage.refreshPage();
				Assertions.verify(loginPage.signInButton.checkIfElementIsDisplayed(), true, "Login Page",
						"Login page loaded successfully", false, false);
				loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
				Assertions.passTest("Home Page", "Logged in as USM Successfully");

				// searching the quote number in grid and clicking on the quote number
				// link
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home page loaded successfully", false, false);

				// homePage.searchReferral(quoteNumber);
				homePage.searchQuote(quoteNumber);
				Assertions.passTest("Home Page", "Searched Submitted Quote" + quoteNumber + " successfullly");

				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();

				// approving referral
				Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
						"Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();

				// click on approve in Approve Decline Quote page
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");

				// SignOut as USM
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Home Page", "Logged out as USM successfully");

				// Sign in as Producer
				loginPage.refreshPage();
				loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
				Assertions.passTest("Home Page", "Logged in as Producer successfully");

				// Handling the pop up
				homePage.enterPersonalLoginDetails();

				// Search for quote
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuoteByProducer(quoteNumber);
				Assertions.passTest("Home Page", "Searched the Quote " + quoteNumber + " sucessfully");

			}
			// Ended

			// Getting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Asserting SLTF value on account overview page
			String premiumOnAccountOverviewPage = accountOverviewPage.premiumValue.getData();
			String feesOnAccountOverviewPage = accountOverviewPage.feesValue.getData();
			String sltfValueAccountOverviewPage = accountOverviewPage.sltfValue.getData().replace(",", "");
			String surplusContributionAccountOverviewPage = accountOverviewPage.surplusContibutionValue.getData();

			double d_premiumValueOnAccountOverviewPage = Double
					.parseDouble(premiumOnAccountOverviewPage.replace("$", "").replace(",", ""));
			double d_FeeValueOnAccountOverviewPage = Double
					.parseDouble(feesOnAccountOverviewPage.replace("$", "").replace(",", ""));
			double d_surplusContributionAccountOverviewPage = Double
					.parseDouble(surplusContributionAccountOverviewPage.replace("$", ""));
			double d_sltfPercentageValue = Double.parseDouble(testData.get("SLTFPercentage"));
			double d_stampingFeePercentageValue = Double.parseDouble(testData.get("StampingFeePercentage"));
			double d_sltValue = (d_premiumValueOnAccountOverviewPage + d_FeeValueOnAccountOverviewPage
					+ d_surplusContributionAccountOverviewPage) * d_sltfPercentageValue;
			double d_stampingFeeValue = (d_premiumValueOnAccountOverviewPage + d_FeeValueOnAccountOverviewPage
					+ d_surplusContributionAccountOverviewPage) * d_stampingFeePercentageValue;
			double d_sltfValueOnAccountOverviewPage = d_sltValue + d_stampingFeeValue;

			Assertions.addInfo("Scenario 03", "Calculating SLTF Values in Account Overview Page");
			double actualValue = Precision.round(Double.parseDouble(sltfValueAccountOverviewPage.replace("$", "")), 2);
			double calculatedValue = Precision.round(d_sltfValueOnAccountOverviewPage, 2);

			double absoluteDifference = Precision.round(Math.abs(actualValue - calculatedValue), 4);

			if (absoluteDifference < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated SLTF according to 5% NC SLTF value : $" + calculatedValue);
				Assertions.passTest("Account Overview Page",
						"Actual SLTF according to 5% NC SLTF value: $" + actualValue);
			} else {
				Assertions.verify(calculatedValue, actualValue, "Account Overview Page",
						"The Difference between actual and calculated SLTF values is more than 0.05", false, false);
			}
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Asserting declinations column on view print full quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();

			// Asserting SLTF value on view print full quote page
			String premiumValue = viewOrPrintFullQuotePage.premiumValue.getData();
			String inspectionFeeValue = viewOrPrintFullQuotePage.insurerInspectionFeeValue.getData();
			String policyFeeValue = viewOrPrintFullQuotePage.insurerPolFeeVlaue.getData();
			String sltValue = viewOrPrintFullQuotePage.surplusLinesTaxNaho.getData().replace(",", "");
			String stampingValue = viewOrPrintFullQuotePage.stampingFeeNaho.getData().replace(",", "");
			String surplusContribution = viewOrPrintFullQuotePage.surplusContributionValue.getData();

			double d_premiumValue = Double.parseDouble(premiumValue.replace("$", "").replace(",", ""));
			double d_inspectionFeeValue = Double.parseDouble(inspectionFeeValue.replace("$", "").replace(",", ""));
			double d_policyFeeValue = Double.parseDouble(policyFeeValue.replace("$", "").replace(",", ""));
			double d_surplusContribution = Double.parseDouble(surplusContribution.replace("$", "").replace("%", ""));
			double d_sltValueQuoteDocument = (d_premiumValue + d_inspectionFeeValue + d_policyFeeValue
					+ d_surplusContribution) * d_sltfPercentageValue;
			double d_stampingFeeValueQuoteDocument = (d_premiumValue + d_inspectionFeeValue + d_policyFeeValue
					+ d_surplusContribution) * d_stampingFeePercentageValue;
			double d_SLTFValue = d_sltValueQuoteDocument + d_stampingFeeValueQuoteDocument;
			Assertions.addInfo("Scenario 04", "Calculating SLTF Values in View/Print Full Quote Page");
			Assertions.verify(sltValue, "$" + df.format(d_sltValueQuoteDocument), "View/Print Full Quote Page",
					"Actual and Calculated SLT Values are matching and calculated according to 5% NC SLT value "
							+ sltValue,
					false, false);
			Assertions.verify(stampingValue, "$" + df.format(d_stampingFeeValueQuoteDocument),
					"View/Print Full Quote Page",
					"Actual and Calculated Stamping Fee Values are matching and calculated according to 0.04% NC Stamping Fee value "
							+ stampingValue,
					false, false);

			double sltfActualValue = Precision.round(Double.parseDouble(sltfValueAccountOverviewPage.replace("$", "")),
					2);
			double sltfCalculatedValue = Precision.round(d_SLTFValue, 2);

			double absoluteDifferenceValue = Precision.round(Math.abs(sltfActualValue - calculatedValue), 4);

			if (absoluteDifferenceValue < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated SLTF according to 5% NC SLTF value : $" + sltfCalculatedValue);
				Assertions.passTest("Account Overview Page",
						"Actual SLTF according to 5% NC SLTF value: $" + sltfActualValue);
			} else {
				Assertions.verify(sltfCalculatedValue, sltfActualValue, "Account Overview Page",
						"The Difference between actual and calculated SLTF values is more than 0.05", false, false);
			}
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			Assertions.addInfo("Scenario 05", "Assertiong Declination Wording in View/Print Full Quote Page");
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
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");
			viewOrPrintFullQuotePage.scrollToTopPage();
			viewOrPrintFullQuotePage.waitTime(2);
			viewOrPrintFullQuotePage.backButton.click();

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
			Assertions.addInfo("Scenario 06", "Asserting Due Diligence Text");
			Assertions.verify(requestBindPage.diligenceText.checkIfElementIsDisplayed(), true, "Request Bind Page",
					requestBindPage.diligenceText.getData() + " is displayed", false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Entering bind details
			requestBindPage.enterBindDetailsNAHO(testData);

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

			accountOverviewPage.uploadPreBindApproveAsUSM();
			// approving referral
			referralPage.clickOnApprove();

			// click on approve in Approve Decline Quote page
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Number is " + policySummaryPage.policyNumber.getData(), false, false);

			// Clicking on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBNCTC002 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBNCTC002 ", "Executed Successfully");
			}
		}
	}
}
