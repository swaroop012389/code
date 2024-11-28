/** Program Description: Verify SLTF value, diligence text, referral for additional insured AI and HO5 as producer and IO-21792
 * 	Author			   : Pavan Mule
 *  Date of Creation   : 12/27/2021
 **/

package com.icat.epicenter.test.naho.regression.ESBNB;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class MATC002 extends AbstractNAHOTest {

	public MATC002() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBNB/MATC002.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestSubmittedPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();

		// Initializing Variables
		Map<String, String> testData;
		int dataValue1 = 0;
		int quoteLen;
		String quoteNumber;
		String premiumAmount;
		String surplusContribution;
		String actualSLTF;
		String sltfPercentage;
		double d_premiumAmount;
		double d_surplusContribution;
		double d_calculatedSLTF;
		double d_sltfPercentage;
		double d_actualSLTF;
		testData = data.get(dataValue1);
		boolean isTestPassed = false;

		try {
			// creating New account
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
			dwellingPage.enterDwellingDetailsNAHO(testData);
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
			createQuotePage.coverageCPersonalProperty.waitTillPresenceOfElement(60);
			createQuotePage.coverageCPersonalProperty.scrollToElement();
			createQuotePage.coverageCPersonalProperty.clearData();
			createQuotePage.coverageCPersonalProperty.tab();

			// Checking HO5 form not available on create quote page
			Assertions.addInfo("Scenario 01", "Checking HO5 not displayed on create quote page");
			Assertions.verify(
					createQuotePage.formType_HO5.checkIfElementIsPresent()
							&& createQuotePage.formType_HO5.checkIfElementIsDisplayed(),
					false, "Create quote page", "HO5 form not displayed verified", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
			createQuotePage.getAQuote.checkIfElementIsPresent();
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create quote page", "Quote details entered successfully");

			// Asserting Quote Number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account overview page", "Quote number is : " + quoteNumber);

			// Calculating SLTF Value with 3% of Total Premium =premium+policy fee +
			// inspection fee)
			premiumAmount = accountOverviewPage.premiumValue.getData().replace("$", " ").replace(",", "");
			surplusContribution = accountOverviewPage.surplusContibutionValue.getData().replace("$", "");
			actualSLTF = accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", "");
			d_actualSLTF = Double.parseDouble(actualSLTF);
			sltfPercentage = testData.get("SLTFPercentage");

			// Printing values from account overview page
			Assertions.addInfo("Account overview page", "Asserting premium, Icat fees, SLTF on account overview page");
			Assertions.passTest("Account overview page", "Premium is $" + premiumAmount);
			Assertions.passTest("Account overview page", "Actual SLTF value is $" + d_actualSLTF);

			// Converting string values to double
			d_premiumAmount = Double.parseDouble(premiumAmount);
			d_surplusContribution = Double.parseDouble(surplusContribution);
			d_sltfPercentage = Double.parseDouble(sltfPercentage);
			d_calculatedSLTF = (d_premiumAmount + d_surplusContribution) * (d_sltfPercentage / 100);

			// Verifying Actual SLTF and Calculated SLTF Values on Account Overview Page
			Assertions.addInfo("Scenario 02",
					"Verifying actual SLTF and calculated SLTF values on Account Overview Page");
			if (Precision.round(Math.abs(Precision.round(d_actualSLTF, 2) - Precision.round(d_calculatedSLTF, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated SLTF Value :  " + "$" + Precision.round(d_calculatedSLTF, 2));
				Assertions.passTest("Account Overview Page",
						"Actual SLTF Value : " + "$" + Precision.round(d_calculatedSLTF, 2));
			} else {
				Assertions.verify(d_actualSLTF, d_calculatedSLTF, "Account Overview Page",
						"The Difference between actual SLTF value and calculated SLTF value is more than 0.05", false,
						false);
			}
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Binding the quote and creating the policy
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Clicking on answer no button
			underwritingQuestionsPage.answerNoToAllQuestions.waitTillVisibilityOfElement(30);
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting questions page", "Underwriting questions page loaded successfully", false, false);
			underwritingQuestionsPage.answerNoToAllQuestions.scrollToElement();
			underwritingQuestionsPage.answerNoToAllQuestions.click();
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered successfully");

			// Clicking on save button
			underwritingQuestionsPage.saveButton.scrollToElement();
			underwritingQuestionsPage.saveButton.click();

			// Navigating to Request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request bind page",
					"Request bind page loaded successfully", false, false);

			// Asserting Diligence text
			Assertions.addInfo("Scenario 03", "Verify diligence text message is displayed on Request Bind Page");
			Assertions.verify(requestBindPage.diligenceText.checkIfElementIsDisplayed(), true, "Request Bind page",
					"Requesting bind on policy message is displayed", false, false);
			Assertions.verify(
					requestBindPage.dueDiligenceText.formatDynamicPath("minimum 25%").checkIfElementIsDisplayed(), true,
					"Request Bind page",
					"Diligence Message 1 : "
							+ requestBindPage.dueDiligenceText.formatDynamicPath("minimum 25%").getData()
							+ "is displayed",
					false, false);
			Assertions.verify(
					requestBindPage.dueDiligenceText
							.formatDynamicPath("signed application").checkIfElementIsDisplayed(),
					true, "Request Bind page",
					"Diligence Message 2 : "
							+ requestBindPage.dueDiligenceText.formatDynamicPath("signed application").getData()
							+ "is displayed",
					false, false);
			Assertions.verify(
					requestBindPage.dueDiligenceText.formatDynamicPath("signed BR-7").checkIfElementIsDisplayed(), true,
					"Request Bind Page",
					"Diligence Message 3 : "
							+ requestBindPage.dueDiligenceText.formatDynamicPath("signed BR-7").getData()
							+ "is displayed",
					false, false);
			Assertions
					.verify(requestBindPage.dueDiligenceText
							.formatDynamicPath("signed diligent effort form").checkIfElementIsDisplayed(), true,
							"Request Bind Page",
							"Diligence Message 4 : " + requestBindPage.dueDiligenceText
									.formatDynamicPath("signed diligent effort form").getData() + "is displayed",
							false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Enter bind details
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request bind page", "Bind details entered successfully");

			// Asserting bind referral message when additional interest added, AI type=
			// additional insured and AI Relationship = Limited Liability Corporation
			Assertions.addInfo("Scenario 04",
					"Asserting bind referral message when additional interest added, AI type = additional insured and AI Relationship = Corporation");
			Assertions
					.verify(bindRequestSubmittedPage.referralMessages
							.formatDynamicPath("Additional Insured with the Relation Type").checkIfElementIsDisplayed(),
							true, "Bind request submitted page",
							"Bind referral message displayed is " + bindRequestSubmittedPage.referralMessages
									.formatDynamicPath("Additional Insured with the Relation Type").getData(),
							false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Signing out as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Signing in as USM
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));

			// Searching the referred quote
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Referred Quote searched successfully");

			// Opening the referral
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();

			// Approving the referral
			// IO-21792-As part of this our expectation is to validate if the referred quote
			// is assigned to USM not "Holder RMS"
			if (referralPage.assignedUser.getData().contentEquals("Sean Minn")) {

				Assertions.verify(referralPage.assignedUser.getData().contains("Sean Minn"), true, "Referral Page",
						"Quote is referred to USM " + referralPage.assignedUser.getData(), false, false);

			} else {

				Assertions.verify(referralPage.assignedUser.getData().contains("Holder RMS"), false, "Referral Page",
						"Quote is referred to USM " + referralPage.assignedUser.getData(), false, false);
			}
			// Ended

			// Clicking on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("MATC002 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("MATC002 ", "Executed Successfully");
			}
		}
	}
}
