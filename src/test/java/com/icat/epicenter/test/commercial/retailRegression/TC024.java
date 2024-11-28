/** Program Description: Check that the endorsements with a premium change will have state-specific taxes and fees re-calculated and displayed.Added IO-20834
 *  Author			   : Yeshashwini
 *  Date of Creation   : 21/07/2021
 **/
package com.icat.epicenter.test.commercial.retailRegression;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountDetails;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC024 extends AbstractCommercialTest {

	public TC024() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID024.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		AccountDetails accountDetails = new AccountDetails();
		BindRequestSubmittedPage bindRequestSubmittedPage = new BindRequestSubmittedPage();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
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

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Prior Losses
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// IO-20834 started
			// Click on Account details link
			accountOverviewPage.accountDetailsLink.scrollToElement();
			accountOverviewPage.accountDetailsLink.click();
			accountOverviewPage.editAccountDetails.waitTillPresenceOfElement(60);
			Assertions.verify(accountOverviewPage.editAccountDetails.checkIfElementIsDisplayed(), true,
					"Account overview page", "Clicked on account details link successfully", false, false);

			// Click on edit account details
			accountOverviewPage.editAccountDetails.scrollToElement();
			accountOverviewPage.editAccountDetails.click();
			Assertions.verify(accountDetails.reviewButton.checkIfElementIsDisplayed(), true, "Account details paage",
					"Clicked on edit account detail link successfully", false, false);

			// Change effective date past or feature date
			testData = data.get(data_Value2);
			accountDetails.effectiveDate.scrollToElement();
			accountDetails.effectiveDate.clearData();
			accountDetails.effectiveDate.appendData(testData.get("PolicyEffDate"));
			accountDetails.effectiveDate.tab();
			accountOverviewPage.waitTime(data_Value2);
			if (accountOverviewPage.yesButton.checkIfElementIsPresent()
					&& accountOverviewPage.yesButton.checkIfElementIsDisplayed()) {
				accountOverviewPage.yesButton.scrollToElement();
				accountOverviewPage.yesButton.click();
			}
			if (accountOverviewPage.editAccountDetails.checkIfElementIsPresent()
					&& accountOverviewPage.editAccountDetails.checkIfElementIsDisplayed()) {
				accountOverviewPage.editAccountDetails.scrollToElement();
				accountOverviewPage.editAccountDetails.click();
			}
			accountDetails.reviewButton.checkIfElementIsPresent();
			accountDetails.reviewButton.waitTillVisibilityOfElement(60);
			accountDetails.reviewButton.scrollToElement();
			waitTime(2);
			accountDetails.reviewButton.click();

			// Checking whether the user able to change the effective date from account
			// details page
			Assertions.addInfo("Scenario 01", "Checking whether the user able to change the effective date");
			Assertions.verify(accountDetails.reviewButton.checkIfElementIsPresent(), false, "Account details paage",
					"After changing the effective date user able to proced further", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote is searched successfully");

			// Click on request bind button
			testData = data.get(data_Value1);
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Checking whether the user able to create quote after changing the effective
			// date(back date or future date) from request bind page
			Assertions.addInfo("Scenario 02",
					"User able to create quote after changing the effective date(back date or future date) from request bind page");
			Assertions.verify(bindRequestSubmittedPage.homePage.checkIfElementIsDisplayed(), true,
					"Bind request submitted page", "User able to create quote successfully", false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");
			// IO-20834 Ended

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote is searched successfully");

			// Click on Open Referral Link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

			// Approve Referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Approval page loaded successfully", false, false);

			// Click on approve button
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");
			Assertions.passTest("Referral Page", "Bind Referral Approved successfully");

			// Get policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Policy Number is " + policyNumber, false, false);

			// Click on endorse PB link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// setting Endorsement Effective Date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// clicking on edit Location/Building information hyperlink
			endorsePolicyPage.editLocOrBldgInformationLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();
			endorsePolicyPage.loading.waitTillInVisibilityOfElement(60);

			// Modifying Location Details
			testData = data.get(data_Value2);
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.modifyLocationDetailsCommercial(testData);
			Assertions.passTest("Location Page", "Location details modified successfully");

			// modifying Location 1 Building 1 Details
			Assertions.passTest("Building Page", "Building Page loaded successfully");
			buildingPage.modifyBuildingDetailsPNB_old(testData);
			Assertions.passTest("Building Page", "Building details modified successfully");

			// click on continue button
			if (buildingPage.continueButton.checkIfElementIsPresent()
					&& buildingPage.continueButton.checkIfElementIsDisplayed()) {
				buildingPage.continueButton.scrollToElement();
				buildingPage.continueButton.click();
			}

			// clicking on continue button in create quote page
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page loaded successfully", false, false);
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			Assertions.passTest("Create Quote Page", "Clicked on Continue button successfully");

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// clicking on next button in endorse policy page
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// Click on continue
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// clicking on complete button in endorse policy page
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// clicking on close button in endorse policy page
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Asserting the Premium and Transaction premiums
			Assertions.addInfo("Scenario 03",
					"Assert the endorsements with a premium change will have state-specific taxes and fees re-calculated and displayed");
			policySummaryPage.transHistReason.formatDynamicPath(2).scrollToElement();
			policySummaryPage.transHistReason.formatDynamicPath(2).click();
			Assertions.verify(policySummaryPage.PremiumFee.formatDynamicPath(1).checkIfElementIsDisplayed(), true,
					"Policy Summary Page",
					"The NB Premium is " + policySummaryPage.PremiumFee.formatDynamicPath(1).getData(), false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// click on transaction history reason
			policySummaryPage.refreshPage();
			policySummaryPage.waitTime(8);// wait time needed to click on element
			policySummaryPage.transHistReason.formatDynamicPath(3).scrollToElement();
			policySummaryPage.transHistReason.formatDynamicPath(3).click();
			Assertions.verify(policySummaryPage.PremiumFee.formatDynamicPath(2).checkIfElementIsDisplayed(), true,
					"Policy Summary Page",
					"The Endorsement Premium is " + policySummaryPage.PremiumFee.formatDynamicPath(2).getData(), false,
					false);
			String premiumAndFees = policySummaryPage.PremiumFee.formatDynamicPath(2).getData()
					.replaceAll("[^\\d-.]", "").replace(".00", "");
			String inspectionFee = policySummaryPage.PremiunInspectionFee.formatDynamicPath(2).getData()
					.replace("$", "").replace(".00", "").replace(",", "");
			String policyFee = policySummaryPage.PremiumPolicyFee.formatDynamicPath(2).getData().replace("$", "")
					.replace(".00", "").replace(",", "");
			String surplusContributionValue = policySummaryPage.surplusContributionValue.formatDynamicPath(2).getData()
					.replace("$", "").replace(".00", "").replace(",", "");
			double sltf = 0.04
					* (Integer.parseInt(premiumAndFees) + Double.parseDouble(inspectionFee)
							+ Double.parseDouble(policyFee) + Double.parseDouble(surplusContributionValue))
					+ 0.0025 * (Integer.parseInt(premiumAndFees) + Double.parseDouble(inspectionFee)
							+ Double.parseDouble(policyFee) + Double.parseDouble(surplusContributionValue))
					+ 0.03 * (Integer.parseInt(premiumAndFees) + Double.parseDouble(inspectionFee)
							+ Double.parseDouble(policyFee) + Double.parseDouble(surplusContributionValue));

			// Rounding sltf decimal value to 2 digits
			NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
			BigDecimal taxes = new BigDecimal(sltf);
			taxes = taxes.setScale(2, RoundingMode.HALF_UP);
			double d_taxes = taxes.doubleValue();
			Assertions.passTest("Actual Taxes and State Fees value for MS ",
					policySummaryPage.policyInformation.formatDynamicPath("Taxes And State Fees").getData());
			Assertions.passTest("Expected Taxes and State Fees value for MS ",
					format.format(taxes).replace("(", "-").replace(")", "") + "");
			if (Precision.round(Math
					.abs(Precision.round(Double
							.parseDouble(policySummaryPage.policyInformation.formatDynamicPath("Taxes And State Fees")
									.getData().replace("$", "").replace(",", "").replace("-", "")),
							2))
					- Precision.round(d_taxes, 2), 2) < 0.05) {
				Assertions.passTest("Account Overview Page", "Calculated Taxes and Fees : " + "$" + taxes);
				Assertions.passTest("Account Overview Page", "Actual Taxes and Fees : "
						+ policySummaryPage.policyInformation.formatDynamicPath("Taxes And State Fees").getData());
			} else {
				Assertions.passTest("Account Overview Page",
						"The Difference between actual and calculated taxes is more than 0.05");
			}

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC024 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC024 ", "Executed Successfully");
			}
		}
	}
}
