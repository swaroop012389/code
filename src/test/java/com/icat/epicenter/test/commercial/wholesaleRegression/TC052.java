/** Program Description:  Endorse PB-Add TRIA Coverage
 *  Author			   : Yeshashwini T A
 *  Date of Creation   : 07/11/2019
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
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC052 extends AbstractCommercialTest {

	public TC052() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID052.xls";
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
		ViewPolicySnapShot viewPolicySnapShot = new ViewPolicySnapShot();

		// Initializing the variables
		String quoteNumber;
		String policyNumber;
		int dataValue1 = 0;
		int dataValue2 = 1;
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
				Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");
			}

			// entering general liability details
			if (!testData.get("L1-GLLocationClass").equals("")) {
				Assertions.verify(glInformationPage.continueButton.checkIfElementIsDisplayed(), true,
						"GL Information Page", "GL Information Page loaded successfully", false, false);
				glInformationPage.enterGLInformation(testData);
				Assertions.passTest("GL Information Page", "GL Information details entered successfully");
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
			Assertions.passTest("Home Page", "Created Policy is selected");

			// Cancel policy
			Assertions.addInfo("Policy Summary Page", "Initiating Endorsement to add TRIA Coverage");
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// setting Endorsement Effective Date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// clicking on edit Location/Building information hyperlink
			endorsePolicyPage.changeCoverageOptionsLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			endorsePolicyPage.loading.waitTillInVisibilityOfElement(60);
			testData = data.get(dataValue2);

			// modifying deductibles and coverages in create quote page
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page loaded successfully", false, false);
			createQuotePage.editOptionalCoverageDetailsPNB(testData);
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			Assertions.passTest("Create Quote Page", "Coverage details modified successfully");

			// clicking on next button in endorse policy page
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// clicking on complete button in endorse policy page
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// clicking on close button in endorse policy page
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// verifying PB Endorsement record in policy summary page
			testData = data.get(dataValue1);
			Assertions.addInfo("Policy Summary Page", "Verifying PB Endorsement record on policy summary page");
			Assertions.verify(
					policySummarypage.transactionType.formatDynamicPath(testData.get("TransactionType")).getData(),
					testData.get("TransactionType"), "Policy Summary Page", "PB Endorsement Record Verified", false,
					false);

			policySummarypage.transHistReason.formatDynamicPath(3).scrollToElement();
			policySummarypage.transHistReason.formatDynamicPath(3).click();

			// Click on Policy snapshot link
			policySummarypage.viewPolicySnapshot.scrollToElement();
			policySummarypage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Policy snapshot link");

			// Asserting the presence of TRIA coverage
			Assertions.addInfo("View Policy Snapshot Page",
					"Assert the Presence of TRIA Coverage on Policy Snapshot Page");
			Assertions.verify(viewPolicySnapShot.terrorism.checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page", "The " + viewPolicySnapShot.terrorism.getData()
							+ viewPolicySnapShot.binderTerrorismdata.getData() + " displayed is verified",
					false, false);

			// signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 52", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 52", "Executed Successfully");
			}
		}
	}
}
