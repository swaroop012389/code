/** Program Description: Create an AOP Policy with additional interests.
 *  Author			   : Yeshashwini TA
 *  Date of Creation   : 11/21/2019
 **/
package com.icat.epicenter.test.commercial.nb;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EditAdditionalInterestInformationPage;
import com.icat.epicenter.pom.EligibilityPage;
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

public class AOPPolicyAddIntTest extends AbstractCommercialTest {

	public AOPPolicyAddIntTest() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/nb/NBTCID17.xls";
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
		EditAdditionalInterestInformationPage editAdditionalInterestInformationPage = new EditAdditionalInterestInformationPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		GLInformationPage glInformationPage = new GLInformationPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();

		// Initializing the variables
		String quoteNumber;
		String policyNumber;
		int dataValue1 = 0;
		int dataValue2 = 1;
		Map<String, String> testData = data.get(dataValue1);

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

		// entering general liability details
		if (!testData.get("L1-GLLocationClass").equals("")) {
			Assertions.verify(glInformationPage.continueButton.checkIfElementIsDisplayed(), true, "GL Information Page",
					"GL Information Page loaded successfully", false, false);
			glInformationPage.enterGLInformation(testData);
			Assertions.passTest("GL Information Page", "GL Information details entered successfully");
		}

		// Entering Create quote page Details
		Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Create Quote Page loaded successfully", false, false);
		createQuotePage.enterQuoteDetailsCommercialSmoke(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Account Overview Page loaded successfully", false, false);

		// getting the quote number
		quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
		Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

		// Entering AI Details from Account Overview Page
		accountOverviewPage.editAdditionalIntersets.scrollToElement();
		accountOverviewPage.editAdditionalIntersets.click();
		Assertions.passTest("Account Overview page", "Clicked on Edit Additional Interests");
		editAdditionalInterestInformationPage.addAdditionalInterest(testData);
		Assertions.passTest("Edit Additional Interest Information Page",
				"Additional Interest Details entered successfully");
		editAdditionalInterestInformationPage.update.scrollToElement();
		editAdditionalInterestInformationPage.update.click();

		// Click on View print full quote link
		accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
		accountOverviewPage.viewPrintFullQuoteLink.click();
		Assertions.passTest("Account Overview Page", "Clicked on View or print full quote");
		Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
				"View Or Print Full Quote Page", "View Or Print Full Quote Page loaded successfully", false, false);

		// Asserting entered Details from View or print full quote page
		Assertions.addInfo("View Or Print Full Quote Page",
				"Asserting entered Details from View or print full quote page");
		String aiType1 = viewOrPrintFullQuotePage.aiDetails.formatDynamicPath(4).getData();
		Assertions.verify(aiType1.contains(testData.get("1-AIType")), true, "View Or Print Full Quote Page",
				"The Additional Interest Type Selected is " + aiType1 + " displayed is verified", false, false);
		String aiName1 = viewOrPrintFullQuotePage.aiDetails.formatDynamicPath(5).getData();
		Assertions.verify(aiName1.contains(testData.get("1-AIName")), true, "View Or Print Full Quote Page",
				"The Name Entered is " + aiName1 + " displayed is verified", false, false);
		String aiAddress1 = viewOrPrintFullQuotePage.aiDetails.formatDynamicPath(6).getData();
		Assertions.verify(aiAddress1.contains(testData.get("1-AIAddr1")), true, "View Or Print Full Quote Page",
				"The Address Entered is " + aiAddress1 + " displayed is verified", false, false);
		viewOrPrintFullQuotePage.backButton.click();

		// clicking on request bind button
		accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

		// entering details in request bind page
		Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
				"Request Bind Page loaded successfully", false, false);
		requestBindPage.enterPolicyDetails(testData);
		requestBindPage.enterPaymentInformation(testData);
		requestBindPage.addInspectionContact(testData);
		testData = data.get(dataValue2);
		requestBindPage.addAdditionalInterest(testData);
		testData = data.get(dataValue1);
		requestBindPage.addContactInformation(testData);
		requestBindPage.submit.waitTillButtonIsClickable(60);
		requestBindPage.submit.click();
		requestBindPage.confirmBind();
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");

		// clicking on home button
		Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
				"Bind Request Page loaded successfully", false, false);
		bindRequestPage.clickOnHomepagebutton();
		Assertions.passTest("Home Page", "Clicked on Home button");

		// searching the quote number in grid and clicking on the quote link
		Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
				"Home page loaded successfully", false, false);
		homePage.searchReferral(quoteNumber);
		Assertions.passTest("Home Page", "Searched Submitted Bind Request successfullly");

		// approving referral
		Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
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

		// Asserting the AI details from policy snapshot page
		policySummarypage.quoteNoLink.formatDynamicPath(quoteNumber).scrollToElement();
		policySummarypage.quoteNoLink.formatDynamicPath(quoteNumber).click();
		Assertions.addInfo("Quote Details Page", "Asserting the AI details from policy snapshot page");
		String aiType = viewOrPrintFullQuotePage.aiDetails.formatDynamicPath(4).getData();
		Assertions.verify(aiType.contains(testData.get("1-AIType")), true, "Quote Details Page",
				"The Additional Interest Type Selected is " + aiType + " displayed is verified", false, false);
		String aiName = viewOrPrintFullQuotePage.aiDetails.formatDynamicPath(5).getData();
		Assertions.verify(aiName.contains(testData.get("1-AIName")), true, "Quote Details Page",
				"The Name Entered is " + aiName + " displayed is verified", false, false);
		String aiAddress = viewOrPrintFullQuotePage.aiDetails.formatDynamicPath(6).getData();
		Assertions.verify(aiAddress.contains(testData.get("1-AIAddr1")), true, "Quote Details Page",
				"The Address Entered is " + aiAddress + " displayed is verified", false, false);
		testData = data.get(dataValue2);
		String aiType2 = viewOrPrintFullQuotePage.aiDetails.formatDynamicPath(7).getData();
		Assertions.verify(aiType2.contains(testData.get("2-AIType")), true, "Quote Details Page",
				"The Additional Interest Type Selected is " + aiType2 + " displayed is verified", false, false);
		String aiName2 = viewOrPrintFullQuotePage.aiDetails.formatDynamicPath(8).getData();
		Assertions.verify(aiName2.contains(testData.get("2-AIName")), true, "Quote Details Page",
				"The Name Entered is " + aiName2 + " displayed is verified", false, false);
		String aiAddress2 = viewOrPrintFullQuotePage.aiDetails.formatDynamicPath(9).getData();
		Assertions.verify(aiAddress2.contains(testData.get("2-AIAddr1")), true, "Quote Details Page",
				"The Address Entered is " + aiAddress2 + " displayed is verified", false, false);

		// signout
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();

		Assertions.passTest("NB Regression TC017", "Executed Successfully");
	}
}
