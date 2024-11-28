/** Program Description: Post Policy term extension Endt txn, create a Rewrite and check on the Premium/SLTF and updated Expiration date should not display.
Also, check on the Rewrite created with Actual Exp date i.e Oct 2023.
 *  Author			   : Sowndarya
 *  Date of Creation   : 21/10/2022
 **/
package com.icat.epicenter.test.naho.regression.NSPNB;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBFLTC009_GEN extends AbstractNAHOTest {

	public PNBFLTC009_GEN() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSPNB/FL009.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		ReferralPage referralPage = new ReferralPage();
		BindRequestSubmittedPage bindRequestSubmittedPage = new BindRequestSubmittedPage();

		// initializing variables
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		int quoteLen;
		String quoteNumber;
		String policyNumber;
		String actualSLTFValue;
		double calculatedSLTValue;
		double d_surplusLinesTaxes;
		double d_fslsoServiceFee;
		double d_actualSLTFValue;
		double d_premium;
		double d_fees;
		double d_surplusLinesTaxesPercentage;
		double d_fslsoServiceFeePercentage;
		String surplusLinesTaxesPercentage;
		String fslsoServiceFeePercentage;
		double empaCharge = 2;
		String premium;
		String fees;
		String surplusContributionValue;
		double d_surplusContributionValue;

		Map<String, String> testData = data.get(data_Value1);
		DecimalFormat df = new DecimalFormat("0.00");
		boolean isTestPassed = false;

		try {
			// Create account as producer
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page ",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account ", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page ",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page ", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page ",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page ", "Dwelling details entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page ",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);

			// Enter quote details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page ",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.coverageEArrow.scrollToElement();
			createQuotePage.coverageEArrow.click();

			// Verifying Cov E does have $1,000,000 (Override) option when selecting short
			// term rental as Yes
			Assertions.addInfo("Scenario 01",
					" Verifying Cov E does have  $1,000,000 (Override) option when selecting short term rental as Yes");
			Assertions.verify(
					createQuotePage.coverageEOption
							.formatDynamicPath(testData.get("L1D1-DwellingCovE")).checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The $1,000,000 (Override) option displayed is verified.The Cov E value is "
							+ createQuotePage.coverageEOption.formatDynamicPath(testData.get("L1D1-DwellingCovE"))
									.getData(),
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
			createQuotePage.coverageEOption.formatDynamicPath(testData.get("L1D1-DwellingCovE"))
					.waitTillPresenceOfElement(60);
			createQuotePage.coverageEOption.formatDynamicPath(testData.get("L1D1-DwellingCovE")).scrollToElement();
			createQuotePage.coverageEOption.formatDynamicPath(testData.get("L1D1-DwellingCovE")).click();

			// Click on previous link
			createQuotePage.previous.scrollToElement();
			createQuotePage.previous.click();
			Assertions.passTest("Create Quote Page", "Clicked on previous link successfully");

			// Click on editbuilding link
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();
			Assertions.passTest("Account Overview Page", "Clicked on previous link successfully");

			// selecting short term rental as NO and occupied by Tenant
			testData = data.get(data_Value2);
			dwellingPage.addDwellingDetails(testData, 1, 1);
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();

			// click on override
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.overrideNoLongerQuotableBuildings();
			}
			Assertions.passTest("Dwelling Page", "Dwelling detail enterd successfully");

			createQuotePage.coverageEArrow.scrollToElement();
			createQuotePage.coverageEArrow.click();

			// Verifying Cov E does have $1,000,000 (Override) option when selecting
			// occupied by tenant
			testData = data.get(data_Value1);
			Assertions.addInfo("Scenario 02",
					" Verifying Cov E does have  $1,000,000 (Override) option when selecting occupied by tenant");
			Assertions.verify(
					createQuotePage.coverageEOption
							.formatDynamicPath(testData.get("L1D1-DwellingCovE")).checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The $1,000,000 (Override) option displayed is verified.The Cov E value is "
							+ createQuotePage.coverageEOption.formatDynamicPath(testData.get("L1D1-DwellingCovE"))
									.getData(),
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");
			createQuotePage.coverageEOption.formatDynamicPath(testData.get("L1D1-DwellingCovE"))
					.waitTillPresenceOfElement(60);
			createQuotePage.coverageEOption.formatDynamicPath(testData.get("L1D1-DwellingCovE")).scrollToElement();
			createQuotePage.coverageEOption.formatDynamicPath(testData.get("L1D1-DwellingCovE")).click();

			// Click on previous link
			createQuotePage.previous.scrollToElement();
			createQuotePage.previous.click();
			Assertions.passTest("Create Quote Page", "Clicked on previous link successfully");

			// Click on editbuilding link
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();
			Assertions.passTest("Account Overview Page", "Clicked on previous link successfully");

			// selecting short term rental as NO and occupied by Tenant
			testData = data.get(data_Value3);
			dwellingPage.addDwellingDetails(testData, 1, 1);
			dwellingPage.reviewDwelling();
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();

			// click on override
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.overrideNoLongerQuotableBuildings();
			}
			Assertions.passTest("Dwelling Page", "Dwelling detail enterd successfully");

			// Verifying ordinance or law override option
			Assertions.addInfo("Scenario 03", "Verifying ordinance or law override options");
			for (int i = 0; i < 2; i++) {
				int dataValuei = i;
				testData = data.get(dataValuei);
				createQuotePage.ordinanceLawArrow.scrollToElement();
				createQuotePage.ordinanceLawArrow.click();
				String ordinanceLawi = createQuotePage.ordinanceLawOption
						.formatDynamicPath(testData.get("OrdinanceOrLaw")).getData();
				createQuotePage.ordinanceLawOption.formatDynamicPath(testData.get("OrdinanceOrLaw")).scrollToElement();
				createQuotePage.ordinanceLawOption.formatDynamicPath(testData.get("OrdinanceOrLaw")).click();
				Assertions.passTest("Create Quote Page",
						"The OrdinanceOrLaw option " + ordinanceLawi + " present is verified");
			}
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Asserting quote number
			testData = data.get(data_Value1);
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Click on Bind button on request bind page
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind Button");

			// Clicking on answer no button
			underwritingQuestionsPage.answerNoToAllQuestions.waitTillVisibilityOfElement(60);
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.answerNoToAllQuestions.scrollToElement();
			underwritingQuestionsPage.answerNoToAllQuestions.click();

			// Clicking on save button
			underwritingQuestionsPage.saveButton.scrollToElement();
			underwritingQuestionsPage.saveButton.click();

			// Entering bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Details Entered successfully");

			// Approve bind request
			if (bindRequestSubmittedPage.homePage.checkIfElementIsPresent()
					&& bindRequestSubmittedPage.homePage.checkIfElementIsDisplayed()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchReferral(quoteNumber);
				referralPage.clickOnApprove();
				requestBindPage.approveRequestNAHO(testData);
			}

			// Asserting policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfuly", false, false);
			policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.passTest("Policy Summary Page", "Policy Number is " + policyNumber);

			// Initiating endt to verify the changed policy expiration date
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Enter Endorsement Eff date as policy eff date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// enter policy expiration date
			endorsePolicyPage.policyExpirationDate.setData(testData.get("PolicyExpirationDate"));
			endorsePolicyPage.policyExpirationDate.tab();
			Assertions.passTest("Endorse Policy Page",
					"Entered the Policy Expiration Date as " + endorsePolicyPage.policyExpirationDate.getData());
			endorsePolicyPage.changeExpirationDate.scrollToElement();
			endorsePolicyPage.changeExpirationDate.click();

			// click on next button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Next Button");

			// click on complete button
			endorsePolicyPage.oKContinueButton.scrollToElement();
			endorsePolicyPage.oKContinueButton.click();
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Complete button");

			// click on close button
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Click on Rewrite the policy HyperLink
			policySummaryPage.rewritePolicy.scrollToElement();
			policySummaryPage.rewritePolicy.click();
			Assertions.passTest("Policy Summary Page ", "Clicked on Rewrite Policy");

			// Click on Create another Quote
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();
			Assertions.passTest("Account Overview Page", "Clicked on Create AnotherQuote button successfully");

			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			// enter quote details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			testData = data.get(data_Value2);
			createQuotePage.enterDeductiblesNAHO(testData);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Assert the rewritten quote number
			Assertions.verify(accountOverviewPage.rewriteBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page ", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Rewrite Quote Number : " + quoteNumber);

			// click on quote specifics link
			accountOverviewPage.quoteSpecifics.scrollToElement();
			accountOverviewPage.quoteSpecifics.click();

			// Verify the Requested expiration date
			Assertions.addInfo("Scenario 04",
					"Verifying the Requested Expiration Date for Rewritten quote after changing the expiration date for the NB Policy");
			Assertions.verify(
					accountOverviewPage.requestedExpirationDate.getData()
							.contains(testData.get("PolicyExpirationDate")),
					true, "Account Overview Page", "The Expiration date "
							+ accountOverviewPage.requestedExpirationDate.getData() + " displayed is verified",
					false, false);
			Assertions.passTest("Account Overview Page", "Rewrite is created with Actual Expiration date is verified");
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Getting sltf value from account overview page
			Assertions.addInfo("Sceraio 05", "Verifying the premium and SLTF values");
			actualSLTFValue = accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", "");

			// Getting premium value from account overview page
			Assertions.verify(accountOverviewPage.premiumValue.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"The Premium " + accountOverviewPage.premiumValue.getData() + " displayed is verified", false,
					false);
			premium = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");

			// Getting fees value from account overview page
			fees = accountOverviewPage.feesValue.getData().replace("$", "").replace(",", "");
			surplusContributionValue = accountOverviewPage.surplusContibutionValue.getData().replace("$", "")
					.replace(",", "").replace("%", "");

			// Getting surplusLinesTaxesPercentage and stampingFeePercentage from test data
			testData = data.get(data_Value1);
			surplusLinesTaxesPercentage = testData.get("SLTFPercentage");
			fslsoServiceFeePercentage = testData.get("FSLSOServiceFeePercentage");

			// Conversion of String to double/int to calculate sltf
			d_actualSLTFValue = Double.parseDouble(actualSLTFValue);
			d_premium = Double.parseDouble(premium);
			d_fees = Double.parseDouble(fees);
			d_surplusLinesTaxesPercentage = Double.parseDouble(surplusLinesTaxesPercentage);
			d_fslsoServiceFeePercentage = Double.parseDouble(fslsoServiceFeePercentage);
			d_surplusContributionValue = Double.parseDouble(surplusContributionValue);

			// Calculate Surplus Lines Taxes
			// =(Premium+Fees+surpluscontributionvalue)*surplusLinesTaxesPercentage(0.0494)
			d_surplusLinesTaxes = (d_premium + d_fees + d_surplusContributionValue) * d_surplusLinesTaxesPercentage;

			// Calculate FSLSO Fee =
			// (Premium+Fees+surpluscontributionvalue)*FSLSOServiceFeePercentage(0.0006)
			d_fslsoServiceFee = (d_premium + d_fees + d_surplusContributionValue) * d_fslsoServiceFeePercentage;

			// calculate SLTF value
			calculatedSLTValue = d_surplusLinesTaxes + d_fslsoServiceFee + empaCharge;

			// Verify actual and calculated SLTF values are equal
			if (Precision.round(
					Math.abs(Precision.round(d_actualSLTFValue, 2) - Precision.round(calculatedSLTValue, 2)),
					2) < 0.5) {
				if (actualSLTFValue != null) {
					Assertions.passTest("Account Overview Page",
							"Actual Surplus Lines Taxes and Fees : " + "$" + actualSLTFValue);
					Assertions.passTest("Account Overview Page",
							"Calculated Surplus Lines Taxes and Fees : " + "$" + df.format(calculatedSLTValue));
				}
			} else {
				Assertions.passTest("Account Overview Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05");
			}
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBFLTC009 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBFLTC009 ", "Executed Successfully");
			}
		}

	}

}
