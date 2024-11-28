/** Program Description: Verify SLTF value, diligence text and referral for additional insured AI
 * 	Author			   : Pavan Mule
 *  Date of Creation   : 12/21/2021
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
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class MDTC001 extends AbstractNAHOTest {

	public MDTC001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBNB/MDTC001.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestSubmittedPage = new BindRequestSubmittedPage();

		// Initializing Variables
		Map<String, String> testData;
		int dataValue1 = 0;
		testData = data.get(dataValue1);
		int quoteLen;
		String quoteNumber;
		String premiumAmount;
		String surplusContribution;
		String icatFees;
		String actualSLTF;
		String sltfPercentage;
		double d_premiumAmount;
		double d_surplusContribution;
		double d_icatFees;
		double d_calculatedSLTF;
		double d_sltfPercentage;
		double d_actualSLTF;
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
			dwellingPage.enterDwellingDetailsNAHO(testData);
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
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Asserting Quote Number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Calculating SLTF Value with 3% of Total Premium =premium+policy fee +
			// inspection fee)
			premiumAmount = accountOverviewPage.premiumValue.getData().replace("$", " ").replace(",", "");
			surplusContribution = accountOverviewPage.surplusContibutionValue.getData().replace("$", "");
			icatFees = accountOverviewPage.feesValue.getData().replace("$", " ").replace(",", "");
			actualSLTF = accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", "");
			d_actualSLTF = Double.parseDouble(actualSLTF);
			sltfPercentage = testData.get("SLTFPercentage");

			// Printing values from account overview page
			Assertions.passTest("Account Overview Page", "Premium is $" + premiumAmount);
			Assertions.passTest("Account Overview Page", "ICAT Fees is $" + icatFees);
			Assertions.passTest("Account Overview Page", "Actual SLTF value is $" + d_actualSLTF);

			// Converting string values to double
			d_premiumAmount = Double.parseDouble(premiumAmount);
			d_surplusContribution = Double.parseDouble(surplusContribution);
			d_icatFees = Double.parseDouble(icatFees);
			d_sltfPercentage = Double.parseDouble(sltfPercentage);
			d_calculatedSLTF = (d_premiumAmount + d_icatFees + d_surplusContribution) * (d_sltfPercentage / 100);

			// Verifying Actual SLTF and Calculated SLTF Values on Account Overview Page
			Assertions.addInfo("Scenario 01",
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
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Binding the quote and creating the policy
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Clicking on answer no button
			underwritingQuestionsPage.answerNoToAllQuestions.waitTillVisibilityOfElement(60);
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.answerNoToAllQuestions.scrollToElement();
			underwritingQuestionsPage.answerNoToAllQuestions.click();
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered successfully");

			// Clicking on save button
			underwritingQuestionsPage.saveButton.scrollToElement();
			underwritingQuestionsPage.saveButton.click();

			// Navigating to Request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);

			// Asserting Diligence text
			Assertions.addInfo("Scenario 02", "Verify diligence text message is displayed on Request Bind Page");
			Assertions.verify(requestBindPage.diligenceText.checkIfElementIsDisplayed(), true, "Request Bind page",
					"Requesting bind on policy message is displayed", false, false);
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
			Assertions.verify(
					requestBindPage.dueDiligenceText.formatDynamicPath("signed Surplus Lines Disclosure")
							.checkIfElementIsDisplayed(),
					true, "Request Bind Page", "Diligence Message 3 : " + requestBindPage.dueDiligenceText
							.formatDynamicPath("signed Surplus Lines Disclosure").getData(),
					false, false);
			Assertions.verify(
					requestBindPage.dueDiligenceText.formatDynamicPath("signed diligent effort form")
							.checkIfElementIsDisplayed(),
					true, "Request Bind Page", "Diligence Message 4 : " + requestBindPage.dueDiligenceText
							.formatDynamicPath("signed diligent effort form").getData(),
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Entering bind details
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request bind page", "Bind details entered successfully");

			// Asserting bind referral message when additional interest added, AI type=
			// additional insured and AI Relationship = Limited Liability Corporation
			Assertions.addInfo("Scenario 03",
					"Asserting bind referral message when additional interest added, AI type = additional insured and AI Relationship = Limited Liability Corporation");
			Assertions
					.verify(bindRequestSubmittedPage.referralMessages
							.formatDynamicPath("Additional Insured with the Relation Type").checkIfElementIsDisplayed(),
							true, "Bind request submitted page",
							"Bind referral message displayed is " + bindRequestSubmittedPage.referralMessages
									.formatDynamicPath("Additional Insured with the Relation Type").getData(),
							false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Clicking on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("MDTC001 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("MDTC001 ", "Executed Successfully");
			}
		}
	}
}
