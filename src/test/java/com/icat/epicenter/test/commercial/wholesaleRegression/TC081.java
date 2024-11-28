/** Program Description: Create an account assert the available elements and mandatory fields from request bind page
 *  Author			   : Sowndarya
 *  Date of Modified   : 09/28/2021
**/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC081 extends AbstractCommercialTest {

	public TC081() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID081.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();

		// Initializing the variables
		String premiumAmount;
		String otherFees;
		String icatFees;
		String actualSLTFValue;
		String actualTotalPremium;
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

			// Added IO-21612
			// Editing the fees on the account overview page
			accountOverviewPage.editFees.scrollToElement();
			accountOverviewPage.editFees.click();

			accountOverviewPage.customFieldValue.formatDynamicPath(1).checkIfElementIsPresent();
			accountOverviewPage.customFieldValue.formatDynamicPath(1).scrollToElement();
			accountOverviewPage.customFieldValue.formatDynamicPath(1).clearData();
			accountOverviewPage.customFieldValue.formatDynamicPath(1).appendData("200");

			accountOverviewPage.customeFeeSave.scrollToElement();
			accountOverviewPage.customeFeeSave.click();

			// Fetching Premium Amount from Account Overview Page
			premiumAmount = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("Account Overview Page", "Actual Premium Amount : " + "$" + premiumAmount);

			// Fetching ICAT fees value from Account Overview Page
			icatFees = accountOverviewPage.feesValue.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("Account Overview Page", "Actual ICAT Fees : " + "$" + icatFees);

			// Fetching Actual Other Fees or Broker fees from account overview page
			otherFees = accountOverviewPage.otherFees.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("Account Overview Page", "Actual Other Fees : " + "$" + otherFees);

			// Fetching Surplus contribution value from account overview page
			String surplusContributionValue = accountOverviewPage.surplusContributionValue.getData()
					.replaceAll("[^\\d-.]", "");
			Assertions.passTest("Account Overview Page",
					"Actual Surplus Contribution Value : " + "$" + surplusContributionValue);

			// Actual SLTF value from Account Overview Page
			actualSLTFValue = accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", "");

			double calTotalPremium = ((Double.parseDouble(premiumAmount) + Double.parseDouble(icatFees)
					+ Double.parseDouble(otherFees) + Double.parseDouble(surplusContributionValue))
					+ Double.parseDouble(actualSLTFValue));

			// Go to view/print full quote page
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();

			viewOrPrintFullQuotePage.grandTotal1.checkIfElementIsPresent();
			// Fetching the grand total premium from view/print full quote page.
			actualTotalPremium = viewOrPrintFullQuotePage.grandTotal1.getData().replace("$", "").replace(",", "");

			Assertions.addInfo("Scenario",
					"Verifying the grand total premium on the view/print full quote page once after upadting the other fees on the account overview page");
			Assertions.verify(Double.parseDouble(actualTotalPremium), calTotalPremium, "View/Print Full Quote Page",
					"Actual and Calculated total premiums are matching", false, false);
			Assertions.passTest("View/Print Full Quote Page",
					"Actual Total Premium Value : " + "$" + actualTotalPremium);
			Assertions.addInfo("Scenario", "Scenario is Ended");

			// Going back to Account Overview Page
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// Ticket IO-21612 Ended

			// Click on request bind
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
			requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);

			// Asserting the presence of Elements in Request bind page
			Assertions.addInfo("Request Bind Page", "Asserting the presence of Elements in Request bind page");
			Assertions.verify(requestBindPage.effectiveDate.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Effective Date field displayed is verified", false, false);
			Assertions.verify(requestBindPage.expirationDate.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Expiration Date field displayed is verified", false, false);
			Assertions.verify(requestBindPage.namedInsured.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Named Insured field displayed is verified", false, false);
			Assertions.verify(requestBindPage.extendedNamedInsured.checkIfElementIsDisplayed(), true,
					"Request Bind Page", "Extended named insured field displayed is verified", false, false);
			Assertions.verify(requestBindPage.insuredMailingAddressArrow.checkIfElementIsDisplayed(), true,
					"Request Bind Page", "Mailing address arrow displayed is verified", false, false);
			if (requestBindPage.insuredCountry.checkIfElementIsPresent()
					&& requestBindPage.insuredCountry.checkIfElementIsDisplayed()) {
				Assertions.verify(requestBindPage.insuredCountry.checkIfElementIsDisplayed(), true, "Request Bind Page",
						"Insured country field displayed is verified", false, false);
			} else if (requestBindPage.insuredCountrySelect.checkIfElementIsPresent()
					&& requestBindPage.insuredCountrySelect.checkIfElementIsDisplayed()) {
				Assertions.verify(requestBindPage.insuredCountrySelect.checkIfElementIsDisplayed(), true,
						"Request Bind Page", "Insured country field displayed is verified", false, false);
			}
			Assertions.verify(requestBindPage.mailingAddress.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Mailing Address field displayed is verified", false, false);
			Assertions.verify(requestBindPage.address2.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Address Line 2 displayed is verified", false, false);
			Assertions.verify(requestBindPage.enterAddressManuallLink.checkIfElementIsDisplayed(), true,
					"Request Bind Page", "Enter Address manually link displayed is verified", false, false);
			Assertions.verify(requestBindPage.inspectionName.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Inspection Contact Name field displayed is verified", false, false);
			Assertions.verify(requestBindPage.inspectionAreaCode.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Inspection Areacode field displayed is verified", false, false);
			Assertions.verify(requestBindPage.inspectionPrefix.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Inspection Prefix field displayed is verified", false, false);
			Assertions.verify(requestBindPage.inspectionNumber.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Inspection Number field displayed is verified", false, false);
			Assertions.verify(requestBindPage.aITypeArrow.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"AI Type dropdown displayed is verified", false, false);
			Assertions.verify(requestBindPage.aILoanNumber.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"AI Loan Number field displayed is verified", false, false);
			Assertions.verify(requestBindPage.aIName.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"AI Name filed displayed is verified", false, false);
			if (requestBindPage.aICountry.checkIfElementIsPresent()
					&& requestBindPage.aICountry.checkIfElementIsDisplayed()) {
				Assertions.verify(requestBindPage.aICountry.checkIfElementIsDisplayed(), true, "Request Bind Page",
						"AI Country field displayed is verified", false, false);
			} else if (requestBindPage.aiCountrySelect.formatDynamicPath("0").checkIfElementIsPresent()
					&& requestBindPage.aiCountrySelect.formatDynamicPath("0").checkIfElementIsDisplayed()) {
				Assertions.verify(requestBindPage.aiCountrySelect.formatDynamicPath("0").checkIfElementIsDisplayed(),
						true, "Request Bind Page", "AI Country field displayed is verified", false, false);
			}

			Assertions.verify(requestBindPage.aIAddress.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"AI Address field displayed is verified", false, false);
			Assertions.verify(requestBindPage.aIAddressLine2.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"AI Address Line 2 displayed is verified", false, false);
			Assertions.verify(requestBindPage.aIEnterAddressManuallyLink.checkIfElementIsDisplayed(), true,
					"Request Bind Page", "AI Enter Address manully link displayed is verified", false, false);
			Assertions.verify(requestBindPage.aIAddSymbol.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"AI Add button displayed is verified", false, false);
			Assertions.verify(requestBindPage.contactName.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Contact Name field displayed is verified", false, false);
			Assertions.verify(requestBindPage.contactEmailAddress.checkIfElementIsDisplayed(), true,
					"Request Bind Page", "Contact Email address field displayed is verified", false, false);
			Assertions.verify(requestBindPage.contactSurplusLicenseNumber.checkIfElementIsDisplayed(), true,
					"Request Bind Page", "Surplus Lines License Number field displayed is verified", false, false);
			Assertions.verify(requestBindPage.cancel.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Cancel Button displayed is verified", false, false);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Submit button displayed is verified", false, false);

			// Entering Different format effective date
			testData = data.get(data_Value2);
			Assertions.addInfo("Request Bind Page", "Entering Effective date in different format");
			requestBindPage.effectiveDate.scrollToElement();
			requestBindPage.effectiveDate.clearData();
			requestBindPage.effectiveDate.setData(testData.get("PolicyEffDate"));
			requestBindPage.effectiveDate.tab();
			Assertions.passTest("Request Bind Page", "The Effective Date entered is " + testData.get("PolicyEffDate"));

			// added this assertion once after entering the effective date and this update
			// done due to new change in UI design for effective date warning message
			// if this fails in any other environment then comment this code and uncomment
			// the below code.
			Assertions.addInfo("Request Bind Page",
					"Asserting  Effective date error message when the effective date is in different format(combination of special characters)");
			Assertions.verify(requestBindPage.effectiveDateErrorMsg1.checkIfElementIsDisplayed(), true,
					"Request Bind Page",
					"The error message displayed is " + requestBindPage.effectiveDateErrorMsg1.getData(), false, false);
			Assertions.verify(requestBindPage.effectiveDateErrorMsg1.getLocator().contains("warningMessages"), true,
					"Request Bind Page", "The effective date entered is invalid is verified", false, false);

			requestBindPage.okButton.scrollToElement();
			requestBindPage.okButton.click();
			// update of new changes are ended here.

			// clearing contact SurplusLicense Number
			requestBindPage.contactSurplusLicenseNumber.scrollToElement();
			requestBindPage.contactSurplusLicenseNumber.clearData();

			// click on submit
			requestBindPage.submit.click();
			requestBindPage.confirmBind();

			// Asserting the error message
			testData = data.get(data_Value1);
			// Below Commented code inserted above
			/*
			 * Assertions.addInfo("Request Bind Page",
			 * "Asserting  Effective date error message when the effective date is in different format(combination of special characters)"
			 * ); Assertions.verify(requestBindPage.effectiveDateErrorMsg.
			 * checkIfElementIsDisplayed(), true, "Request Bind Page",
			 * "The error message displayed is " +
			 * requestBindPage.effectiveDateErrorMsg.getData(), false, false);
			 * Assertions.verify(requestBindPage.effectiveDateErrorMsg.getLocator().contains
			 * ("error"), true, "Request Bind Page",
			 * "The effective date entered is invalid is verified", false, false);
			 */

			// Asserting Mandatory fields
			Assertions.addInfo("Request Bind Page", "Verifying the Mandatory fields");
			Assertions.verify(requestBindPage.mailingAddressMandatoryMsg.getLocator().contains("error"), true,
					"Request Bind Page", "Mailing Address field is mandatory is verfied", false, false);
			Assertions.verify(requestBindPage.inspectionContactNameMandatoryMsg.getLocator().contains("error"), true,
					"Request Bind Page", "Inspection Contact Name field is mandatory is verfied", false, false);
			Assertions.verify(requestBindPage.inspectionContactPhoneMandatoryMsg.getLocator().contains("error"), true,
					"Request Bind Page", "Inspection Contact Phone field is mandatory is verfied", false, false);
			Assertions.verify(requestBindPage.contactEmailMandatoryMsg.getLocator().contains("error"), true,
					"Request Bind Page", "Contact Name field is mandatory is verfied", false, false);
			Assertions.verify(requestBindPage.contactEmailMandatoryMsg.getLocator().contains("error"), true,
					"Request Bind Page", "Contact Email field is mandatory is verfied", false, false);
			Assertions.verify(requestBindPage.surplusLicenseNumMandatorymsg.getLocator().contains("error"), true,
					"Request Bind Page", "Surplus License Number field is mandatory is verfied", false, false);

			// Click on Cancel Button
			requestBindPage.cancel.scrollToElement();
			requestBindPage.cancel.click();
			Assertions.passTest("Request Bind Page", "Clicked on cancel button");

			// click on request bind
			accountOverviewPage.requestBind.scrollToElement();
			accountOverviewPage.requestBind.click();
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Enter Bind Details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// Approve Referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Approval page loaded successfully", false, false);
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");
			Assertions.passTest("Referral Page", "Bind Referral Approved successfully");

			// Get policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Policy Number is " + policyNumber, false, false);

			// signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 81", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 81", "Executed Successfully");
			}
		}
	}
}
