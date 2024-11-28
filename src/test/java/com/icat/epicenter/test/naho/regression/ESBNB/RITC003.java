/** Program Description: Verifying SLTF Value is 4% of Premium Excluding Policy Fee and Inspection Fee and Verify the presence of Due diligence text
 *  Author			   : Sowndarya NH
 *  Date of Creation   : 28/12/2021
 **/

package com.icat.epicenter.test.naho.regression.ESBNB;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class RITC003 extends AbstractNAHOTest {

	public RITC003() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBNB/RITC003.xls";
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
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();

		// Initializing the variables
		String quoteNumber;
		int dataValue1 = 0;
		Map<String, String> testData = data.get(dataValue1);
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
			createQuotePage.enterDeductiblesNAHO(testData);
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// click on get a quote
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on Get a quote");

			// Assert the warning message when year built prior to 1950 and additional
			// dwelling coverage is 150%
			Assertions.addInfo("Scenario 01",
					"Verifying the Warning message when year built prior to 1950 and additional dwelling coverage is 150%");
			Assertions.verify(
					createQuotePage.warningMessages
							.formatDynamicPath("year of construction prior to 1950").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Warning Message " + createQuotePage.warningMessages
							.formatDynamicPath("year of construction prior to 1950").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// click on continue
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
			Assertions.passTest("Create Quote Page", "Clicked on Continue Button");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			int quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Verifying the SLTF Value is 4% of Premium (Excluding Policy Fee and
			// Inspection Fee)
			Assertions.addInfo("Scenario 02",
					"Verifying SLTF Value is 4% of Premium Excluding Policy Fee and Inspection Fee on Account Overview Page");
			Assertions.verify(accountOverviewPage.premiumValue.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Premium Value is : " + accountOverviewPage.premiumValue.getData(), false,
					false);
			String premiumAmount = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "").replace("$",
					"");
			String surplusContribution = accountOverviewPage.surplusContibutionValue.getData().replace("$", "");
			String sltfValue = accountOverviewPage.sltfValue.getData().replace("$", "");
			double calcsurplusTax = (Double.parseDouble(premiumAmount) + Double.parseDouble(surplusContribution))
					* Double.parseDouble(testData.get("SLTFPercentage"));

			// comparing Actual and calculated SlTF value excluding fees
			if (Precision.round(
					Math.abs(Precision.round(Double.parseDouble(sltfValue), 2) - Precision.round(calcsurplusTax, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated Surplus Lines Taxes & Fees: " + "$" + Precision.round(calcsurplusTax, 2));
				Assertions.passTest("Account Overview Page", "Actual Surplus Lines Taxes & Fees : " + "$"
						+ Precision.round(Double.parseDouble(sltfValue), 2));
			} else {
				Assertions.verify(sltfValue, calcsurplusTax, "Account Overview Page",
						"The Difference between actual SLTF value and calculated SLTF value is more than 0.05", false,
						false);
			}
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print Full Quote link");
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page", "View/Print Full Quote Page Loaded successfully", false, false);

			// Verifying the SLTF Value is 4% of Premium (Excluding Policy Fee and
			// Inspection Fee)
			Assertions.addInfo("Scenario 03",
					"Verifying SLTF Value is 4% of Premium Excluding Policy Fee and Inspection Fee on View/Print Full Quote Page");
			Assertions.verify(viewOrPrintFullQuotePage.premiumValue.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page",
					"Premium Value is : " + viewOrPrintFullQuotePage.premiumValue.getData(), false, false);
			String premiumAmount_vpfq = viewOrPrintFullQuotePage.premiumValue.getData().replaceAll("[^\\d-.]", "")
					.replace("$", "");
			String surplusContribution_vpfq = viewOrPrintFullQuotePage.surplusContributionValue.getData()
					.replace("$", "").replace("%", "");
			String sltfValue_vpfq = viewOrPrintFullQuotePage.surplusLinesTaxNaho.getData().replace("$", "")
					.replace("%", "");
			double calculatedsurplusTax_vpfq = (Double.parseDouble(premiumAmount_vpfq)
					+ Double.parseDouble(surplusContribution_vpfq))
					* Double.parseDouble(testData.get("SLTFPercentage"));

			// comparing Actual and calculated SlTF value excluding fees
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(sltfValue_vpfq), 2)
					- Precision.round(calculatedsurplusTax_vpfq, 2)), 2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page", "Calculated Surplus Lines Taxes & Fees : " + "$"
						+ Precision.round(calculatedsurplusTax_vpfq, 2));
				Assertions.passTest("View/Print Full Quote Page", "Actual Surplus Lines Taxes & Fees : " + "$"
						+ Precision.round(Double.parseDouble(sltfValue_vpfq), 2));
			} else {
				Assertions.verify(sltfValue_vpfq, calculatedsurplusTax_vpfq, "View/Print Full Quote Page",
						"The Difference between actual Maintanance Fund and calculated Maintanance Fund is more than 0.05",
						false, false);
			}
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// click on back button
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// clicking on request bind in Account overview page
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Entering details in Underwriting Questions Page
			Assertions.verify(underwritingQuestionsPage.saveButton.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered successfully");

			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			Assertions.addInfo("Scenario 04", "Verifying the Presence of Diligence Checkbox and Wordings");
			Assertions.verify(requestBindPage.dueDiligenceCheckbox.checkIfElementIsDisplayed(), true,
					"Request Bind Page", "Due Diligence Checkbox Present is verified", false, false);
			Assertions.verify(requestBindPage.diligenceText.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"The Due Diligence Text " + requestBindPage.diligenceText.getData() + " Present is verified", false,
					false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("RITC003 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("RITC003 ", "Executed Successfully");
			}
		}
	}
}
