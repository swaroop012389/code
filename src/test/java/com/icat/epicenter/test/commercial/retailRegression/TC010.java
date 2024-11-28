/** Program Description: Check if the below mentioned line is removed from the Terms and Conditions section of commercial retail quotes:
The Producer is responsible for calculating and remitting any and all surplus lines taxes that may apply to this purchase. The amounts listed above are estimates and for informational purposes only.
Verifying presence of roll forward button on NPB endorsement and absence of roll forward button on PB endorsement for bound renewal quote
Verifying presence of Google Maps on the Referral quote screen
 *  Author			   : Yeshashwini
 *  Date of Creation   : 20/07/2021
 **/
package com.icat.epicenter.test.commercial.retailRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.ChangeNamedInsuredPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC010 extends AbstractCommercialTest {

	public TC010() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID010.xls";
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
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ChangeNamedInsuredPage changeNamedInsuredPage = new ChangeNamedInsuredPage();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New account", "New account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility page",
					"Eligibility page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location page",
					"Location page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building page",
					//"Building page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select peril page",
					"Select peril page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Prior Losses
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior loss page",
					"Prior loss page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create quote page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create quote page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account overview page", "Quote number :  " + quoteNumber);

			// Click on View print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account overview page", "Clicked on View/Print full quote link");
			Assertions.addInfo("Scenario 01", "Assert the absence of terms and conditions wordings");
			Assertions.verify(viewOrPrintFullQuotePage.termsAndConditionsWordings.checkIfElementIsPresent(), false,
					"View/Print full quote page",
					"The Producer is responsible for calculating and remitting any and all surplus lines taxes that may apply to this purchase. The amounts listed above are estimates and for informational purposes only. wordings is not displayed under Terms and conditions section is verified",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Click on back button
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// click on request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account overview page", "Clicked on request Bind");

			// entering bind details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Bind request page",
					"Bind request page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request bind page", "Bind details entered successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchReferral(quoteNumber);
			Assertions.passTest("Home page", "Quote for referral is searched successfully");

			// Verifying Google Maps is available on the Referral quote screen
			// Quote screen in Approve/Decline page
			Assertions.addInfo("Scenario 02", "Verifying Google Maps is available on the Referral quote screen");
			Assertions.verify(referralPage.googleMaps.checkIfElementIsPresent(), true, "Referral Page",
					"Presence of Google Maps on the Referral quote screen is verified", false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// approving referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral page", "Referral request approved successfully");
			requestBindPage.approveRequest();
			Assertions.passTest("Request bind page", "Bind request approved successfully");

			// Get policy number from policy summary page
			Assertions.passTest("Policy summary page", "Policy summary page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy summary page", "Policy number is " + policyNumber, false, false);

			// Click on home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Add expac details
			homePage.expaccLink.scrollToElement();
			homePage.expaccLink.click();
			expaccInfoPage.enterExpaccInfo(testData, policyNumber);
			Assertions.passTest("ExpaccInfo page", "ExpaccInfo details entered successfully");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Performing Renewal Searches
			homePage.searchPolicy(policyNumber);

			// clicking on renewal policy link
			policySummaryPage.renewPolicy.waitTillPresenceOfElement(60);
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy summary page", "Clicked on Renewal link successfully");
			if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
					&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
				policyRenewalPage.continueRenewal.scrollToElement();
				policyRenewalPage.continueRenewal.click();
			}

			// click on yes
			if (policyRenewalPage.yesButton.checkIfElementIsPresent()
					&& policyRenewalPage.yesButton.checkIfElementIsDisplayed()) {
				policyRenewalPage.yesButton.scrollToElement();
				policyRenewalPage.yesButton.click();
			}

			// Getting renewal quote number
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account overview page", "Renewal Quote Number :  " + quoteNumber);

			// click on release renewal to producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account overview page", "Clicked on Release Renewal to Producer button");

			// click on request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account overview page", "Clicked on Request Bind button");

			// enter bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Bind request page",
					"Bind request page loaded successfully", false, false);
			requestBindPage.renewalRequestBind(testData);
			Assertions.passTest("Request bind page", "Bind details entered successfully");

			// clicking on home button
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home page", "Clicked on home button");

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"Home page loaded successfully", false, false);
			homePage.searchReferral(quoteNumber);
			Assertions.passTest("Home page", "Searched submitted bind request successfullly");

			// Verifying Google Maps is available on the Referral quote screen
			// Quote screen in Approve/Decline page
			Assertions.addInfo("Scenario 03",
					"Verifying Google Maps is available on the Referral quote screen for renewal");
			Assertions.verify(referralPage.googleMaps.checkIfElementIsPresent(), true, "Referral Page",
					"Presence of Google Maps on the Referral quote screen is verified", false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();

			Assertions.passTest("Referral Page", "Referral request approved successfully");
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind request approved successfully");

			// Get policy number from policy summary page
			Assertions.passTest("Policy summary page", "Policy summary page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Renewal policy number is " + policyNumber, false, false);

			// click on previous policy number
			policySummaryPage.previousPolicyNumber.scrollToElement();
			policySummaryPage.previousPolicyNumber.click();
			Assertions.passTest("Policy Summary Page", "Clicked on previous policy number");

			// Click on NPB endorsement link
			policySummaryPage.endorseNPB.scrollToElement();
			policySummaryPage.endorseNPB.click();
			Assertions.passTest("Policy Summary Page", "Clicked endorse NPB link");

			if (endorsePolicyPage.okButton.checkIfElementIsPresent()
					&& endorsePolicyPage.okButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.okButton.scrollToElement();
				endorsePolicyPage.okButton.click();
			}

			// Enter transaction effective date
			testData = data.get(data_Value2);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse policy page", "Endorse policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.appendData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse policy page", "Entered transaction effective date");

			// Click on change named insured link and change the insured details
			endorsePolicyPage.changeNamedInsuredLink.scrollToElement();
			endorsePolicyPage.changeNamedInsuredLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on changed named insured link successfully");
			changeNamedInsuredPage.enterInsuredAddressDetailPB(testData);

			// Verifying presence of roll forward button
			Assertions.addInfo("Scenario 04",
					"Verifying presence of roll forward button on NPB endorsement for bound renewal quote");
			Assertions.verify(
					endorsePolicyPage.completeAndRollForwardBtn.checkIfElementIsPresent()
							&& endorsePolicyPage.completeAndRollForwardBtn.checkIfElementIsDisplayed(),
					true, "Endorse Policy Page", "Complete and roll forward button is displayed", false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 ended");

			// Click on complete and roll forward button
			endorsePolicyPage.completeAndRollForwardBtn.scrollToElement();
			endorsePolicyPage.completeAndRollForwardBtn.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Complete and roll forward button");
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Click on view active renewal
			policySummaryPage.viewActiveRenewal.scrollToElement();
			policySummaryPage.viewActiveRenewal.click();
			Assertions.passTest("Policy summary page", "Clicked on View Active Renewal link");

			// Verifying NPB endorsement roll forwarded under reflected under Transaction
			// History of Renewal Policy Summary Page
			Assertions.addInfo("Scenario 05",
					"NPB endorsement roll forward is successful and the changes are reflected under transaction history of renewal policy summary page");
			Assertions.verify(
					policySummaryPage.transHistReason.formatDynamicPath(3).checkIfElementIsPresent()
							&& policySummaryPage.transHistReason.formatDynamicPath(3).checkIfElementIsDisplayed(),
					true, "Renewal Policy Summary Page",
					" NPB endorsement roll forwarded successfully and changes are reflected under transaction history of renewal policy summary page",
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 ended");

			// click on previous policy number
			policySummaryPage.previousPolicyNumber.scrollToElement();
			policySummaryPage.previousPolicyNumber.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Previous Policy Number");

			// Click on PB Endorse link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked Endorse PB link");

			if (endorsePolicyPage.okButton.checkIfElementIsPresent()
					&& endorsePolicyPage.okButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.okButton.scrollToElement();
				endorsePolicyPage.okButton.click();
			}

			// Enter transaction effective date
			testData = data.get(data_Value2);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.appendData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered transaction effective date");

			// click on change coverage options
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Coverage Options link successfully");

			// changing named storm value
			testData = data.get(data_Value1);
			Assertions.passTest("Create quote page", "Named storm original value " + testData.get("DeductibleType"));
			testData = data.get(data_Value2);
			createQuotePage.namedStormDeductibleArrow.scrollToElement();
			createQuotePage.namedStormDeductibleArrow.click();
			createQuotePage.namedStormDeductibleOption.formatDynamicPath(testData.get("DeductibleValue"))
					.scrollToElement();
			createQuotePage.namedStormDeductibleOption.formatDynamicPath(testData.get("DeductibleValue")).click();
			Assertions.passTest("Create Quote Page", "Named storm latest value " + testData.get("DeductibleType"));
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			Assertions.passTest("Create Quote Page", "Clicked on continue endorsement button");

			// click on Continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// click on next
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Next button");

			// click on Continue
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Verifying absence of roll forward button on PB endorsement for bound renewal
			// quote
			Assertions.addInfo("Scenario 06",
					"Verifying the absence of roll forward button on PB endorsement for bound renewal quote");
			Assertions.verify(
					endorsePolicyPage.completeAndRollForwardBtn.checkIfElementIsPresent()
							&& endorsePolicyPage.completeAndRollForwardBtn.checkIfElementIsDisplayed(),
					false, "Endorse Policy Page", "Complete and roll forward button is displayed", false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 ended");

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC010 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC010 ", "Executed Successfully");
			}
		}
	}
}
