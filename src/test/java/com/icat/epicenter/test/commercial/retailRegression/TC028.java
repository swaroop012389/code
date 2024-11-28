/** Program Description:  Check the Renewal offers link is NOT available for Comm. Retail Account Overview page and Renewal policy is generated without any error.
Check if the below mentioned line is removed from the Terms and Conditions section of commercial retail renewal quotes
 *  Author			   : Priyanka S
 *  Date of Creation   : 27/01/2022
 **/
package com.icat.epicenter.test.commercial.retailRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExistingAccountPage;
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

public class TC028 extends AbstractCommercialTest {

	public TC028() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID028.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Pages
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		BuildingPage buildingPage = new BuildingPage();
		LocationPage locationPage = new LocationPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ExistingAccountPage existingAccountPage = new ExistingAccountPage();
		BuildingUnderMinimumCostPage buildingUnderminimumCost = new BuildingUnderMinimumCostPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuotablePage = new BuildingNoLongerQuoteablePage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);
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

			// click on add buildings button
			locationPage.addBuildingsButton.scrollToElement();
			locationPage.addBuildingsButton.click();

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building page",
					//"Building page loaded successfully", false, false);
			buildingPage.addBuildingDetails(testData, 1, 1);
			buildingPage.addBuildingOccupancy(testData, 1, 1);
			buildingPage.addRoofDetails(testData, 1, 1);
			buildingPage.enterAdditionalBuildingInformation(testData, 1, 1);
			buildingPage.enterBuildingValues(testData, 1, 1);
			Assertions.passTest("Building Page", "Construction type: " + testData.get("L1B1-BldgConstType"));
			Assertions.passTest("Building Page", "Occupancy type: " + testData.get("L1B1-PrimaryOccupancy"));
			Assertions.passTest("Building Page", "Building Square Feet: " + testData.get("L1B1-BldgSqFeet"));
			Assertions.passTest("Building Page", "Building details entered successfully");

			// Click on Create quote button
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();

			// existing account found page
			if (buildingPage.pageName.getData().contains("Existing Account Found")) {
				existingAccountPage.OverrideExistingAccount();
			}

			// Verifying Bring UpToCost button
			Assertions.passTest("Building Page", "Clicked on Create Quote button successfully");
			Assertions.verify(buildingUnderminimumCost.bringUpToCost.checkIfElementIsDisplayed(), true,
					"Building Under Minimum Cost Page", "Bring UpToCost button displayed is verified", false, false);

			// Getting Expected Cost card value
			String costCardValue = testData.get("CostCardValue");
			Assertions.passTest("CostCard value", "CostCard value: " + costCardValue);

			// Getting expected square feet value
			String squareFeet = testData.get("L1B1-BldgSqFeet");
			Assertions.passTest("SquareFeet", "Square Feet: " + squareFeet);

			// Verifying the Costcard message and verifying the actual and expected cost
			// card values
			Assertions.addInfo("Scenario 01",
					"Verifying the Costcard message and Verifying the actual and expected cost card values when Construction type: Mansoray Non Combustible, Occupancy type:  Condominium,Building Square Feet: 1000");
			Assertions.verify(
					buildingUnderminimumCost.costcardMessage
							.formatDynamicPath("Building value").checkIfElementIsDisplayed(),
					true, "Building Under Minimum Cost Page",
					"The Costcard message "
							+ buildingUnderminimumCost.costcardMessage.formatDynamicPath("Building value").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// click on BringUpToCost button
			if (buildingPage.pageName.getData().contains("Under Minimum Cost")) {
				buildingUnderminimumCost.clickOnOverride();
				Assertions.passTest("Building UnderMinimum Cost Page", "Clicked on Override button successfully");
			}

			if (buildingPage.pageName.getData().contains("Buildings No")) {
				buildingNoLongerQuotablePage.overrideNoLongerQuotableBuildings();
			}

			// selecting peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}

			// Enter prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Click on Request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home Page", "Clicked on Home button");

			// Searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote is searched successfully");

			// Click on Open Referral Link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

			// Approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			// Click on approve button
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Getting the policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// Click on Renewal Link
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();

			if (policySummaryPage.expaacMessage.checkIfElementIsPresent()
					&& policySummaryPage.expaacMessage.checkIfElementIsDisplayed()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.click();
				expaccInfoPage.enterExpaccInfo(testData, policyNumber);
				Assertions.passTest("Expacc Info Page", "Entered Expacc deatils successfully");

				// Go to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();

				// Performing Renewal Searches
				homePage.searchPolicy(policyNumber);

				// clicking on renewal policy link
				Assertions.addInfo("Policy Summary Page", "Renewing the Policy");
				policySummaryPage.renewPolicy.waitTillPresenceOfElement(60);
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();
				Assertions.passTest("Policy Summary Page", "Clicked on Renew policy link");
			}

			// click on continue
			if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
					&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
				policyRenewalPage.continueRenewal.scrollToElement();
				policyRenewalPage.continueRenewal.click();
			}

			// Click on yes button
			if (accountOverviewPage.yesButton.checkIfElementIsPresent()
					&& accountOverviewPage.yesButton.checkIfElementIsDisplayed()) {
				accountOverviewPage.yesButton.scrollToElement();
				accountOverviewPage.yesButton.click();
			}

			// Click on release renewal to producer button
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Renewal created and released to producer");

			// Getting renewal quote number
			String renewalQuoteNumber = accountOverviewPage.quoteNumber.getData().replace("(", "").replace(")", "");
			Assertions.passTest("Account Overview Page", "Renewal quote number is " + renewalQuoteNumber);

			// Verifying Renewal offers link is NOT available for Comm. Retail
			// Account Overview page
			Assertions.addInfo("Scenario 02",
					"Veryfying if renewal offers link is NOT available Account Overview page");
			Assertions.verify(accountOverviewPage.renewalQuote1.checkIfElementIsPresent(), false,
					"Account OvervView Page",
					"Renewal offers link is not displayed in account overview page is verified", false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Verifying Edit Pencil button is NOT available in the Account
			// overview page to add a new fee/own fee
			Assertions.addInfo("Scenario 03",
					"Verifying if edit pencil button is NOT available in the Account overview page to add a new fee/own fee");
			Assertions.verify(accountOverviewPage.editFees.checkIfElementIsPresent(), false, "Account OvervView Page",
					"Edit Pencil button is NOT available to add a new fee/own fee is verified", false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on view/print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View/Print Full quote link");

			// Asserting absence of terms and conditions wordings
			Assertions.addInfo("Scenario 04",
					"Assert the absence of terms and conditions wordings on commercial retail renewal quote");
			Assertions.verify(viewOrPrintFullQuotePage.termsAndConditionsWordings.checkIfElementIsPresent(), false,
					"View/Print Full Quote Page",
					"The Producer is responsible for calculating and remitting any and all surplus lines taxes that may apply to this purchase. The amounts listed above are estimates and for informational purposes only. wordings is not displayed under Terms and conditions section is verified",
					false, false);
			Assertions.addInfo("View/Print Full Quote Page",
					"Checking the bullet point under conditions in the terms and conditions section of commercial retail renewal quote");
			Assertions.verify(viewOrPrintFullQuotePage.termsAndConditions.formatDynamicPath(2, 3).getData(),
					"The surplus lines taxes and fees presented here are an estimate only and will be subject to change based on alterations to the quote.",
					"View/Print Full Quote Page",
					viewOrPrintFullQuotePage.termsAndConditions.formatDynamicPath(2, 3).getData()
							+ "wordings added under Conditions section is verified",
					false, false);
			Assertions.verify(viewOrPrintFullQuotePage.termsAndConditions.formatDynamicPath(2, 4).getData(),
					"Additional fees will apply to payments made by credit card.", "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.termsAndConditions.formatDynamicPath(2, 4).getData()
							+ "wordings added under Conditions section is verified",
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Asserting Florida Wording in Quote Page
			Assertions.addInfo("Scenario 05",
					"Assert the Florida Wordings present above the signature section on the quote");
			Assertions.verify(
					viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
							.formatDynamicPath("Florida Surplus Lines Service").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The Wordings "
							+ viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
									.formatDynamicPath("Florida Surplus Lines Service").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// click on back button
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// Click on request bind
			accountOverviewPage.clickOnRequestBind(testData, renewalQuoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);

			// asserting commission rate
			Assertions.addInfo("Scenario 06",
					"Asserting Commission Rate for state " + testData.get("QuoteState") + " for renewal quote");
			Assertions.verify(
					requestBindPage.commissionRate.getData(), "15.0%", "Request Bind Page", "Commission Rate for state "
							+ testData.get("QuoteState") + " is " + requestBindPage.commissionRate.getData(),
					false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");
			requestBindPage.renewalRequestBind(testData);

			// clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home Page", "Clicked on Home button");

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(renewalQuoteNumber);
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Home Page", "Searched Submitted quote " + quoteNumber + " successfullly");

			// approving referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// getting the policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Renewal Policy Number is : " + policyNumber);

			// Click on Endorse PB link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);

			// Asserting the PCIP Endorsement link is not present
			Assertions.addInfo("Scenario 07",
					"Verifying if PCIP ENDT link should NOT be available in EndorsePolicy Page");
			Assertions.addInfo("Endorse Policy Page", "Assert the absence of PCIPEndorsement link");
			Assertions.verify(endorsePolicyPage.processPCIPEndorsementLink.checkIfElementIsPresent(), false,
					"Endorse Policy Page", "PCIP Endorsement link Removed is verified", false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC028 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC028 ", "Executed Successfully");
			}
		}
	}
}
