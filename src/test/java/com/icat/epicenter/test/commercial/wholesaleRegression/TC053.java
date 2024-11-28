/** Program Description: Endorse PB-Edit Prior Losses and Asserting flood warranty wording in quote document under Terms and Conditions section
 *  Author			   : Yeshashwini T A
 *  Date of Creation   : 13/11/2019
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
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.GLInformationPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC053 extends AbstractCommercialTest {

	public TC053() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID053.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		GLInformationPage glInformationPage = new GLInformationPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();

		// Initializing the Variables
		String quoteNumber;
		String policyNumber;
		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
		int dataValue4 = 3;
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
			locationPage = eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);

			// Entering Location 1 Dwelling 1 Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// selecting peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}

			// enter prior loss details
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

			// clicking on request bind button
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// getting the quote number
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Click on Request Bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home Page", "Clicked on Home button");

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Find the policy by entering policy Number
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Policy Number : " + policyNumber + " searched successfully");

			// endorse policy
			Assertions.addInfo("Policy Summary Page", "Initiating Endorsement to add Prior Loss");
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// setting Endoresment Effective Date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// clicking on edit prior loss hyperlink
			endorsePolicyPage.editPriorLoss.waitTillVisibilityOfElement(60);
			endorsePolicyPage.editPriorLoss.scrollToElement();
			endorsePolicyPage.editPriorLoss.click();
			endorsePolicyPage.loading.waitTillInVisibilityOfElement(60);

			// Modifying prior loss Details
			testData = data.get(dataValue2);
			Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossPage.editPriorLossesInformation(testData);
			Assertions.passTest("Prior Loss Page", "Prior Loss details modified successfully");

			// clicking on next button in endorse policy page
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// Asserting Repair question No Referral message
			Assertions.addInfo("Endorse Policy Page", "Asserting Repair question No Referral message");
			Assertions.verify(endorsePolicyPage.repairedQuestionNoMsg.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "The message displayed for damages been repaired No is : "
							+ endorsePolicyPage.repairedQuestionNoMsg.getData(),
					false, false);

			// click on continue button
			endorsePolicyPage.continueButton.scrollToElement();
			endorsePolicyPage.continueButton.click();

			// clicking on complete button in endorse policy page
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// clicking on close button in endorse policy page
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// verifying PB Endorsement record in policy summary page
			testData = data.get(dataValue1);
			Assertions.addInfo("Policy Summary Page", "Verifying PB Endorsement record in Policy Summary Page");
			Assertions.verify(
					policySummarypage.transactionType.formatDynamicPath(testData.get("TransactionType")).getData(),
					testData.get("TransactionType"), "Policy Summary Page", "PB Endorsement Record Verified", false,
					false);

			// Click on Home Page Button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Go To Home page Button successfully");

			testData = data.get(dataValue3);
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
			testData = data.get(dataValue3);
			buildingPage.enterBuildingDetails(testData);
			Assertions.passTest("Building Page", "Building L1 B1 Address is :" + testData.get("L1B1-BldgAddr1") + ","
					+ testData.get("L1B1-BldgCity") + "," + testData.get("L1B1-BldgZIP"));
			Assertions.passTest("Building Page", "Clicked on Create Quote button successfully");

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));
			Assertions.passTest("Select Peril Page", "Clicked on Continue button successfully");

			// Entering Prior Losses
			Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossPage.selectPriorLossesInformation(testData);
			Assertions.passTest("Prior Loss Page", "Clicked on Continue button successfully");

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");
			Assertions.passTest("Create Quote Page", "Clicked on Get a Quote button successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number1 :  " + quoteNumber);

			// Click on Building One and location 1
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).checkIfElementIsDisplayed();
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).click();
			Assertions.passTest("Account Overview Page", "Clicked on Building 1-1 link successfully");
			Assertions.verify(accountOverviewPage.distanceToCoast.formatDynamicPath(1).checkIfElementIsDisplayed(),
					true, "Account Overview Page",
					"Distance To Coast is:" + accountOverviewPage.distanceToCoast.formatDynamicPath(1).getData(), false,
					false);
			Assertions.verify(accountOverviewPage.floodZone.formatDynamicPath(1).checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"Flood Zone is: " + accountOverviewPage.floodZone.formatDynamicPath(1).getData(), false, false);
			// Click on View/Print full Quote
			accountOverviewPage.viewPrintFullQuoteLink.checkIfElementIsDisplayed();
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View/PrintFull Quote link successfully");

			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View Or Print Full Quote Page", "View Or Print Full Quote Page loaded successfully", false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.termsAndConditions.formatDynamicPath(2, 7).checkIfElementIsDisplayed(),
					true, "View Or Print Full Quote Page",
					"Term and Condition message is: "
							+ viewOrPrintFullQuotePage.termsAndConditions.formatDynamicPath(2, 7).getData()
							+ "Verified",
					false, false);

			viewOrPrintFullQuotePage.backButton.waitTillVisibilityOfElement(60);
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.passTest("View Or Print Full Quote Page",
					"Clicked on back button successfully and Navigated to Account Overview page");

			// Adding Another location
			accountOverviewPage.editLocation.waitTillVisibilityOfElement(60);
			accountOverviewPage.editLocation.scrollToElement();
			accountOverviewPage.editLocation.click();
			Assertions.passTest("Account Overview Page",
					"Clicked edit location link successfully Navigated to location page");
			Assertions.passTest("Location Page", "Clicked on addlocation link successfully");
			Assertions.passTest("Location Page", " Clicked on add building link successfully");

			testData = data.get(dataValue4);
			locationPage.enterLocationDetails(testData);
			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);
			Assertions.passTest("Building Page", "Location 2 and Address 1 :" + testData.get("L2B1-BldgAddr1") + ","
					+ testData.get("L2B1-BldgCity") + "," + testData.get("L2B1-BldgZIP"));
			Assertions.passTest("Building Page", "Clicked on Create Quote button successfully");

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));
			Assertions.passTest("Select Peril Page", "Clicked on Continue button successfully");

			// Enter GL Details
			testData = data.get(dataValue1);
			Assertions.verify(glInformationPage.continueButton.checkIfElementIsDisplayed(), true, "GL Information Page",
					"GL Information Page loaded successfully", false, false);
			glInformationPage.enterGLInformation(testData);
			Assertions.passTest("GL Information Page",
					"GL Details entered successfully and Clicked on continue button successfully");

			// Entering Create quote page Details
			testData = data.get(dataValue3);
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");
			Assertions.passTest("Create Quote Page", "Clicked on  Get a Quote button successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number2 :  " + quoteNumber);

			// Click on Building 2 and location 1
			accountOverviewPage.buildingLink.formatDynamicPath(2, 1).checkIfElementIsDisplayed();
			accountOverviewPage.buildingLink.formatDynamicPath(2, 1).scrollToElement();
			accountOverviewPage.buildingLink.formatDynamicPath(2, 1).click();
			Assertions.passTest("Account Overview Page", "Clicked on Building L2-B1 link successfully");

			Assertions.verify(accountOverviewPage.distanceToCoast.formatDynamicPath(2).checkIfElementIsDisplayed(),
					true, "Account Overview Page",
					"Distance To Coast is:" + accountOverviewPage.distanceToCoast.formatDynamicPath(2).getData(), false,
					false);
			Assertions.verify(accountOverviewPage.floodZone.formatDynamicPath(2).checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"Flood Zone is: " + accountOverviewPage.floodZone.formatDynamicPath(2).getData(), false, false);
			// Click on View/Print full Quote
			accountOverviewPage.viewPrintFullQuoteLink.checkIfElementIsDisplayed();
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View/PrintFull Quote link successfully");

			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View Or Print Full Quote Page", "View Or Print Full Quote Page loaded successfully", false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.termsAndConditions.formatDynamicPath(2, 7).checkIfElementIsDisplayed(),
					true, "View Or Print Full Quote Page",
					"Term and Condition message is : "
							+ viewOrPrintFullQuotePage.termsAndConditions.formatDynamicPath(2, 7).getData()
							+ "Verified",
					false, false);

			viewOrPrintFullQuotePage.backButton.waitTillVisibilityOfElement(60);
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.passTest("View Or Print Full Quote Page",
					"Clicked on back button successfully and Navigated to account overview page");

			// Click on Building 1-1
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).click();
			Assertions.passTest("Account Overview Page", "Clicked on building 1-1 link successfully");
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit building link successfully");
			Assertions.passTest("Building Page", "Building page loaded successfully");

			testData = data.get(dataValue4);
			buildingPage.addBuildingDetails(testData, 1, 1);
			Assertions.passTest("Building Page", "Building details entered successfully");

			testData = data.get(dataValue3);
			Assertions.passTest("Building Page",
					"Before changing Location 1 Building Address is :" + testData.get("L1B1-BldgAddr1") + ","
							+ testData.get("L1B1-BldgCity") + "," + testData.get("L1B1-BldgZIP"));

			testData = data.get(dataValue4);
			Assertions.passTest("Building Page",
					"After changing Location 1 Building Address is :" + testData.get("L1B1-BldgAddr1") + ","
							+ testData.get("L1B1-BldgCity") + "," + testData.get("L1B1-BldgZIP"));

			buildingPage.reviewBuilding();
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();
			Assertions.passTest("Building Page", "Clicked on Create Quote button successfully");

			// Selecting a peril
			testData = data.get(dataValue3);
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));
			Assertions.passTest("Select Peril Page", "Clicked on continue button successfully");

			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");
			Assertions.passTest("Create Quote Page", "Clicked on Get a Quote button successfully");
			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number3 :  " + quoteNumber);

			// Click on Building 1 and location 1
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).checkIfElementIsDisplayed();
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).click();
			Assertions.passTest("Account Overview Page", "Clicked on Building link successfully");
			Assertions.verify(accountOverviewPage.distanceToCoast.formatDynamicPath(1).checkIfElementIsDisplayed(),
					true, "Account Overview Page",
					"Distance To Coast is:" + accountOverviewPage.distanceToCoast.formatDynamicPath(1).getData(), false,
					false);
			Assertions.verify(accountOverviewPage.floodZone.formatDynamicPath(1).checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"Flood Zone is: " + accountOverviewPage.floodZone.formatDynamicPath(1).getData(), false, false);
			// Click on View/Print full Quote
			accountOverviewPage.viewPrintFullQuoteLink.checkIfElementIsDisplayed();
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View/PrintFull Quote link successfully");
			Assertions.addInfo("View Or Print Full Quote Page",
					"Assert Terms and Condition Wordings in Quote Document");
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View Or Print Full Quote Page", "View Or Print Full Quote Page loaded successfully", false, false);

			Assertions.verify(viewOrPrintFullQuotePage.termsAndConditions.formatDynamicPath(2, 7).getData().contains(
					"Named Windstorm, or Named Hurricane coverage for property located in a Special Flood Hazard Area"),
					false, "View Or Print Full Quote Page", "Term and Condition message is not displayed verified",
					false, false);

			// Click on Back
			viewOrPrintFullQuotePage.backButton.waitTillVisibilityOfElement(60);
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.passTest("View Or Print Full Quote Page",
					"Clicked on back button successfully and Navigated to account overview page");

			// click on signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 53", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 53", "Executed Successfully");
			}
		}
	}
}
