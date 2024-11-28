/** Program Description: Check if application allows to add a new form by utilizing the Modify Forms feature.
 *  Author			   : Sowndarya NH
 *  Date of Creation   : 11/26/2019
**/

package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.ModifyForms;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC013 extends AbstractCommercialTest {

	public TC013() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID013.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the pages
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		ModifyForms modifyForms = new ModifyForms();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();

		// Initializing the variables
		String quoteNumber;
		String policyNumber;
		int dataValue1 = 0;
		Map<String, String> testData = data.get(dataValue1);
		boolean isTestPassed = false;

		try {
			// New Account creation
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully ", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// ZipCode EligibilityPage
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Zipcode Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "ZipCode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);

			// Entering Location 1 Dwelling 1 Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// Entering CreateQuotePage Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page Loaded successfully", false, false);
			createQuotePage.enterDeductiblesCommercialNew(testData);
			createQuotePage.addOptionalCoverageDetails(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Adding new Form by utilizing Modify Forms feature
			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();
			Assertions.passTest("Create Quote Page", "Clicked on Modify Forms link");

			// Selecting the checkBoxes
			Assertions.verify(modifyForms.override.checkIfElementIsDisplayed(), true, "Modify Forms Page",
					"Modify Forms Page loaded successfully", false, false);
			modifyForms.earthMovement.select();
			modifyForms.volcanicEruption.select();
			modifyForms.propertyWithinCondoUnitsEq.select();
			modifyForms.override.scrollToElement();
			modifyForms.override.click();
			Assertions.passTest("Modify Forms Page", "Modify Features selected successfully");

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Click on View/print Full Quote link
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);

			// Getting the quote number
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View or print full quote link");

			// Asserting Added forms from View or print full quote page
			Assertions.addInfo("View or Print Full quote Page",
					"Asserting Added forms from View or print full quote page");
			Assertions.verify(
					viewOrPrintFullQuotePage.selectedCoverageDetails.formatDynamicPath("Earth Movement", 1)
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.selectedCoverageDetails.formatDynamicPath("Earth Movement", 1)
									.checkIfElementIsDisplayed(),
					true, "View or Print Full quote Page",
					"The form Earth Movement is " + viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("Earth Movement", 1).getData(),
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.selectedCoverageDetails.formatDynamicPath("Property Within Condo Unit", 1)
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.selectedCoverageDetails
									.formatDynamicPath("Property Within Condo Unit", 1).checkIfElementIsDisplayed(),
					true, "View or Print Full quote Page",
					"The form Property Within Condo Unit is " + viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("Property Within Condo Unit", 1).getData(),
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.selectedCoverageDetails.formatDynamicPath("Volcanic Eruption", 1)
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.selectedCoverageDetails
									.formatDynamicPath("Volcanic Eruption", 1).checkIfElementIsDisplayed(),
					true, "View or Print Full quote Page",
					"The form Volcanic Eruption is " + viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("Volcanic Eruption", 1).getData(),
					false, false);
			viewOrPrintFullQuotePage.backButton.click();

			// Click on request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind ");

			// Entering details in request bind page
			// Added code for IO-19464 - Effective date entered when creating a commercial
			// account isn't pulled into the Request Bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			Assertions.verify(requestBindPage.effectiveDate.getData(), testData.get("PolicyEffDate"),
					"Request Bind Page", "Effective Date is displayed : " + requestBindPage.effectiveDate.getData(),
					false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details Entered successfully");

			// Clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home Page", "Clicked on Home button");

			// Searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// Approving referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Approval page loaded successfully", false, false);

			// carrier selection
			if (requestBindPage.carrierWarningMsg.checkIfElementIsPresent()
					&& requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed()) {
				requestBindPage.approveRequestCommercialData(testData);
			} else {
				requestBindPage.approveRequest();
			}
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);
			policySummarypage.quoteNoLink.formatDynamicPath(quoteNumber).scrollToElement();
			policySummarypage.quoteNoLink.formatDynamicPath(quoteNumber).click();
			Assertions.passTest("Policy Summary Page", "Clicked on Quote Number link");

			// Asserting Forms details from Quote Details Page
			Assertions.addInfo("Quote Details Page", "Asserting Forms details from Quote Details Page");
			Assertions.verify(
					viewOrPrintFullQuotePage.selectedCoverageDetails.formatDynamicPath("Earth Movement", 1)
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.selectedCoverageDetails.formatDynamicPath("Earth Movement", 1)
									.checkIfElementIsDisplayed(),
					true, "View or Print Full quote Page",
					"The form Earth Movement is " + viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("Earth Movement", 1).getData(),
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.selectedCoverageDetails.formatDynamicPath("Property Within Condo Unit", 1)
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.selectedCoverageDetails
									.formatDynamicPath("Property Within Condo Unit", 1).checkIfElementIsDisplayed(),
					true, "View or Print Full quote Page",
					"The form Property Within Condo Unit is " + viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("Property Within Condo Unit", 1).getData(),
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.selectedCoverageDetails.formatDynamicPath("Volcanic Eruption", 1)
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.selectedCoverageDetails
									.formatDynamicPath("Volcanic Eruption", 1).checkIfElementIsDisplayed(),
					true, "View or Print Full quote Page",
					"The form Volcanic Eruption is " + viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("Volcanic Eruption", 1).getData(),
					false, false);

			// sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 13", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 13", "Executed Successfully");
			}
		}
	}

}
