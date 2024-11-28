/** Program Description: 1.Check if the NAHO Roof validation is trigerred as per the given condition on Rewrite when year built is older than 25 years and roof cladding = built up
 * 						 2.As Producer, Check if the NAHO Roof validation is trigerred as per the given condition on NB.When year built 21-25 years older and 26 years older and roof cladding = Hurricane Shingle
 						3 .Check if the NAHO Roof validation is trigerred as per the given condition on endorsement and  Renewal,year built=2000,2005 and roof cladding = still or metal and Wood Shakes or Wood Shingles
 *  Author			   : Pavan Mule
 *  Date of Creation   : 07/22/2024
 **/
package com.icat.epicenter.test.naho.regression.ISNB;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.ModifyForms;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC094_NBACVTRE002 extends AbstractNAHOTest {

	public TC094_NBACVTRE002() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBACVTRE002.xls";
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
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ModifyForms modifyForms = new ModifyForms();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		LoginPage loginPage = new LoginPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();

		// Initializing variable
		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
		int dataValue4 = 3;
		int dataValue5 = 4;
		int dataValue6 = 5;
		String quoteNumber, policyNumber;
		int quoteLen;
		Map<String, String> testData = data.get(dataValue1);
		boolean isTestPassed = false;

		try {
			Assertions.addInfo("Test Case 1",
					"Check if the NAHO Roof validation is trigerred as per the given condition on NB,when year built and year roof last replaced is older than 0-10 years and roof cladding = other and updating year built and year roof last replaced is older than 26 years and roof cladding = Built-up on rewrite");

			// Creating a new account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Checking the processing of Zipcode
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering dwelling details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering Prior Loss details
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);
			Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");

			// Click on modify form button
			Assertions.verify(createQuotePage.modifyForms.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();
			Assertions.passTest("Create Quote Page", "Clicked on modify form button successfully");

			// Verifying AVC(Actual cash value) roof form automatically attached when year
			// built 0-10 years older,roof cladding = Other and year roof last replaced same
			// as year built
			Assertions.addInfo("Scenario 01", "Verifying AVC(Actual cash value) roof form automatically attached");
			Assertions.verify(modifyForms.actualCashValueRoof.checkIfElementIsSelected(), true, "Modify Form Page",
					"Actual cash value roof form attached automaticlly", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Click on override button
			modifyForms.override.scrollToElement();
			modifyForms.override.click();
			Assertions.passTest("Modify Form Page", "Clicked on override button successfully");

			// Asserting and Verifying Actual cash value warning message when year built
			// 1-10years older,roof cladding = Other and year roof last replaced same as
			// year
			// built
			// Warning message is ='The quoted building has a roof age outside of our
			// guidelines. For consideration, please provide your ICAT Online Underwriter
			// with additional information regarding the condition of the roof, such as a
			// recent inspection
			Assertions.addInfo("Scenario 02", "Asserting and Verifying Actual cash value warning message");
			Assertions.verify(
					createQuotePage.errorMessageWarningPage
							.formatDynamicPath("The quoted building has a roof age outside of our guidelines").getData()
							.contains("The quoted building has a roof age outside of our guidelines"),
					true, "Create Quote Page",
					"Warning message is " + createQuotePage.errorMessageWarningPage
							.formatDynamicPath("The quoted building has a roof age outside of our guidelines").getData()
							+ " is displayed when Roof Cladding is " + testData.get("L1D1-DwellingRoofCladding")
							+ " and Year Built is " + testData.get("L1D1-DwellingYearBuilt"),
					false, false);
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Getting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is " + quoteNumber);

			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View Print Full Quote Page", "Clicked on view print full quote link successfully", false, false);

			// Asserting and Verifying Actual cash value warning message when year built
			// 0-10years older,roof cladding = Other and year roof last replaced same as
			// year
			// built
			// Warning message is ='The quoted building has a roof age outside of our
			// guidelines. For consideration, please provide your ICAT Online Underwriter
			// with additional information regarding the condition of the roof, such as a
			// recent inspection
			Assertions.addInfo("Scenario 03", "Asserting and verifying roof coverage as 'actual cash value'");
			Assertions.verify(
					viewOrPrintFullQuotePage.roofCoverage.formatDynamicPath(1).getData().equals("Actual Cash Value"),
					true, "View Print Full Quote Page", "Roof coverage is "
							+ viewOrPrintFullQuotePage.roofCoverage.formatDynamicPath(1).getData() + " displayed",
					false, false);
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on Request Bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on request bind button");

			// Clicking on answer no button
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Entered underwriting details successfully");

			// Entering bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request bind page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// searching the quote number in grid and clicking on the quote number link
			Assertions.passTest("Bind Request Page", "Bind Request page loaded successfully");
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home Page", "Clicked on Quote Number");
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Searched Submitted Bind Request successfullly");

			accountOverviewPage.openReferralLink.checkIfElementIsDisplayed();
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();

			// approving referral
			Assertions.passTest("Referral Page", "Referral page loaded successfully");
			requestBindPage = referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			// click on approve in Approve Decline Quote page
			policySummaryPage = requestBindPage.approveRequestNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Asserting policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is : " + policyNumber, false, false);

			// Click on Rewrite link
			policySummaryPage.rewritePolicy.scrollToElement();
			policySummaryPage.rewritePolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on rewrite policy link");

			// Click on edit dwelling link
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account overview page loaded successfully", false, false);
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit dwelling");

			// Updating year built = 1999 and roof cladding = built-up and year roof last
			// replaced same as year built
			testData = data.get(dataValue2);
			dwellingPage.yearBuilt.scrollToElement();
			dwellingPage.yearBuilt.clearData();
			if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
				dwellingPage.expiredQuotePopUp.scrollToElement();
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
				dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}
			dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));
			if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
				dwellingPage.expiredQuotePopUp.scrollToElement();
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
				dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}
			dwellingPage.yearBuilt.tab();
			dwellingPage.addRoofDetails(testData, 1, 1);
			Assertions.passTest("Dwelling Page",
					"Roof Cladding is updated to " + testData.get("L1D1-DwellingRoofCladding")
							+ " and Year Built is updated to " + testData.get("L1D1-DwellingYearBuilt"));

			// Review dwelling and continue
			dwellingPage.waitTime(2);
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.overrideNoLongerQuotableBuildings();
			}

			// Click on modify form button
			Assertions.verify(createQuotePage.modifyForms.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();
			Assertions.passTest("Create Quote Page", "Clicked on modify form button successfully");

			// Verifying TRE(total roof exclusion) attached automatically when year built 26
			// years older and roof cladding = built-up and year roof last replaced = year
			// built
			Assertions.addInfo("Scenario 04", "Verifying TRE(total roof exclusion) attached automatically");
			Assertions.verify(modifyForms.totalRoofExclusion.checkIfElementIsSelected(), true, "Modify Form Page",
					"Total roof exclusion selected successfully", false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Click on override button
			modifyForms.override.scrollToElement();
			modifyForms.override.click();
			Assertions.passTest("Modify Form Page", "Clicked on override button successfully");

			// Asserting and Verifying warning message when year built =1999 and roof
			// cladding = built-up
			// warning message is 'The account is ineligible due to the roof age being
			// outside of ICAT's guidelines. If you have additional information that adjusts
			// the roof age, please email it to your ICAT Online Underwriter.
			Assertions.addInfo("Scenario 05", "Asserting and Verifying warning message");
			createQuotePage.warningMessages.formatDynamicPath("The account is ineligible due to the roof age")
					.waitTillPresenceOfElement(60);
			Assertions.verify(
					createQuotePage.warningMessages.formatDynamicPath("The account is ineligible due to the roof age")
							.getData().contains(
									"The account is ineligible due to the roof age being outside of ICAT's guidelines"),
					true, "Create Quote Page",
					"Warning message is "
							+ createQuotePage.warningMessages
									.formatDynamicPath("The account is ineligible due to the roof age").getData()
							+ " is displayed when Roof Cladding is " + testData.get("L1D1-DwellingRoofCladding")
							+ " and Year Built is " + testData.get("L1D1-DwellingYearBuilt"),
					false, false);
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
			Assertions.passTest("Scenario 05", "Scenario 05 Ended");

			// Getting quote number
			Assertions.verify(accountOverviewPage.rewriteBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Rewrite Quote Number is " + quoteNumber);

			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View Print Full Quote Page", "Clicked on view print full quote link successfully", false, false);

			// Asserting and Verifying Roof coverage as 'excluded' on view print full quote
			// page when year built and year roof last replaced 26 years and roof cladding =
			// built-up
			Assertions.addInfo("Scenario 06", "Asserting and verifying roof coverage as 'Excluded'");
			Assertions.verify(viewOrPrintFullQuotePage.roofCoverage.formatDynamicPath(1).getData().equals("Excluded"),
					true, "View Print Full Quote Page", "Roof coverage is "
							+ viewOrPrintFullQuotePage.roofCoverage.formatDynamicPath(1).getData() + " displayed",
					false, false);
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Clicking on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Sign out as usm successfully");
			Assertions.addInfo("Test Case 1", "Test Case 1 Ended");

			// Sign in as Producer
			Assertions.addInfo("Test Case 2",
					"As Producer, Check if the NAHO Roof validation is trigerred as per the given condition on NB when year built and year roof last replaced 21-25 years old and roof cladding =Hurricane Shingle and Verifying decline message when updating year built and year roof last replaced 26 years old and.");
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in to application as a producer successfully");

			// Handling the pop up
			homePage.enterPersonalLoginDetails();
			LocalDateTime currentdate = LocalDateTime.now();
			testData = data.get(dataValue3);

			// Creating New Account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home Page loaded successfully", false, false);
			homePage.createNewAccountProducer.moveToElement();
			homePage.createNewAccountProducer.click();
			homePage.productArrow.waitTillVisibilityOfElement(60);
			homePage.productArrow.click();

			// Select product
			homePage.productSelection.formatDynamicPath(testData.get("ProductSelection"))
					.waitTillVisibilityOfElement(60);
			homePage.productSelection.formatDynamicPath(testData.get("ProductSelection")).waitTillButtonIsClickable(60);
			homePage.productSelection.formatDynamicPath(testData.get("ProductSelection")).click();
			String name = testData.get("InsuredName") + currentdate.getMinute();
			homePage.namedInsured.setData(name);
			Assertions.passTest("Home Page", "Insured Name is " + name);

			// Enter effective date
			homePage.effectiveDate.formatDynamicPath(1).scrollToElement();
			homePage.effectiveDate.formatDynamicPath(1).waitTillVisibilityOfElement(60);
			homePage.effectiveDate.formatDynamicPath(1).setData(testData.get("PolicyEffDate"));
			homePage.goButton.scrollToElement();
			homePage.goButton.click();
			Assertions.passTest("New Account", "New Account created successfully");

			// Checking the processing of Zipcode
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering dwelling details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering Prior Loss details
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);
			Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");

			// Log out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Sign out as producer successfully");

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Login", "Logged in to application as usm successfully");

			// Search for account using insured name
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			homePage.findAccountNamedInsured.scrollToElement();
			homePage.findAccountNamedInsured.appendData(name);
			homePage.findBtnAccount.scrollToElement();
			homePage.findBtnAccount.click();
			Assertions.passTest("Home Page", "Account searched successfully");

			// Click on coverage link
			Assertions.verify(accountOverviewPage.deleteAccount.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account overview page loaded successfully", false, false);
			accountOverviewPage.coverageLink.scrollToElement();
			accountOverviewPage.coverageLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on coverage link successfully");

			// Click on modify form button
			Assertions.verify(createQuotePage.modifyForms.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();

			// Verifying AVC(Actual cash value) roof form automatically attached when year
			// built 21-25 years older,roof cladding = Hurricane shingle and year roof last
			// replaced same
			// as year built
			Assertions.addInfo("Scenario 07", "Verifying AVC(Actual cash value) roof form automatically attached");
			Assertions.verify(modifyForms.actualCashValueRoof.checkIfElementIsSelected(), true, "Modify Form Page",
					"Actual cash value roof form attached automaticlly", false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Sign out as usm
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Sign out as usm successfully");

			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in to application as a producer successfully");

			// Search for account using insured name
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home Page loaded successfully", false, false);

			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.producerAccountNameSearchTextbox.scrollToElement();
			homePage.producerAccountNameSearchTextbox.setData(name);
			homePage.producerAccountFindButton.scrollToElement();
			homePage.producerAccountFindButton.click();
			homePage.producerAccountNameLink.scrollToElement();
			homePage.producerAccountNameLink.click();
			Assertions.passTest("Home Page", "Account searched successfully as a producer");

			// Click on coverage link
			Assertions.verify(accountOverviewPage.deleteAccount.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account overview page loaded successfully", false, false);
			accountOverviewPage.coverageLink.scrollToElement();
			accountOverviewPage.coverageLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on coverage link successfully");

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.scrollToBottomPage();
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Asserting and verifying referral message when year built older than between
			// 21-25 and roof cladding = Hurricane shingle and year roof last replaced same
			// as
			// year built
			// Referral message is 'The quoted building has a roof age outside of our
			// guidelines. For consideration, please provide your ICAT Online Underwriter
			// with additional information regarding the condition of the roof, such as a
			// recent inspection
			Assertions.addInfo("Scenario 08", "Asserting and verifying referral message");
			Assertions.verify(
					referQuotePage.roofReferralMessage.getData().contains(
							"The quoted building has a roof age outside of our guidelines"),
					true, "Refer Quote Page",
					"The referral message is " + referQuotePage.roofReferralMessage.getData() + " displayed", false,
					false);
			Assertions.addInfo("Scenario 08", "Senario 08 Ended");

			// Enter producer details and Getting quote no
			referQuotePage.contactName.waitTillVisibilityOfElement(60);
			referQuotePage.contactName.clearData();
			referQuotePage.contactName.scrollToElement();
			referQuotePage.contactName.setData(testData.get("ProducerName"));
			referQuotePage.contactEmail.clearData();
			referQuotePage.contactEmail.scrollToElement();
			referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
			referQuotePage.referQuote.scrollToElement();
			referQuotePage.referQuote.click();
			quoteNumber = referQuotePage.quoteNumberforReferral.getData();
			Assertions.passTest("Refer Quote Page", "The refered quote number is : " + quoteNumber);

			// Sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Sign out as producer successfully");

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Login", "Logged in to application as usm successfully");

			// Search referred quote
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();
			Assertions.passTest("Account Overview Page", "Clicked on open referral link");

			// Verifying and Asserting referral message 'The quoted building has a roof age
			// outside of our guidelines. For consideration, please provide your ICAT Online
			// Underwriter with additional information regarding the condition of the roof,
			// such as a recent inspection
			Assertions.addInfo("Scenario 09", "Verifying and Asserting referral message");
			Assertions.verify(
					referralPage.producerQuoteStatus
							.formatDynamicPath("The quoted building has a roof age outside of our guidelines").getData()
							.contains("The quoted building has a roof age outside of our guidelines"),
					true, "Referral Page",
					"The referral message is " + referralPage.producerQuoteStatus.formatDynamicPath(
							"The quoted building has a roof age outside of our guidelines") + " displayed",
					false, false);
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			// Clicking on pick up button
			if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
				referralPage.pickUp.scrollToElement();
				referralPage.pickUp.click();
			}

			// Approve Referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			Assertions.verify(approveDeclineQuotePage.approveButton.checkIfElementIsDisplayed(), true,
					"Approve Decline Quote Page", "Approve Decline Quote page loaded successfully", false, false);
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Approve Decline Quote Page", "Bind Request approved successfully");
			Assertions.passTest("Approve Decline Quote Page",
					"Quote number : " + quoteNumber + " approved successfully");

			// Search for quote
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote searched successfully");

			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View Print Full Quote Page", "Clicked on view print full quote link successfully", false, false);

			// Verifying and Asserting roof coverage as 'Actual Cash Value' when year
			// built 21-25 years older,roof cladding = Hurricane shingle and year roof last
			// replaced same
			// as year built
			Assertions.addInfo("Scenario 10", "Asserting and verifying roof coverage as 'actual cash value'");
			Assertions.verify(
					viewOrPrintFullQuotePage.roofCoverage.formatDynamicPath(1).getData().equals("Actual Cash Value"),
					true, "View Print Full Quote Page", "Roof coverage is "
							+ viewOrPrintFullQuotePage.roofCoverage.formatDynamicPath(1).getData() + " displayed",
					false, false);
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.addInfo("Scenario 10", "Scenario 10 Ended");

			// Click on edit dwelling link
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account overview page loaded successfully", false, false);
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit dwelling");

			// updating year built and year roof last replaced = 1998
			testData = data.get(dataValue4);
			dwellingPage.yearBuilt.scrollToElement();
			dwellingPage.yearBuilt.clearData();
			if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
				dwellingPage.expiredQuotePopUp.scrollToElement();
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
				dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}
			dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));
			if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
				dwellingPage.expiredQuotePopUp.scrollToElement();
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
				dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}
			dwellingPage.yearBuilt.tab();
			dwellingPage.addRoofDetails(testData, 1, 1);
			Assertions.passTest("Dwelling Page", "Year Built is updated to " + testData.get("L1D1-DwellingYearBuilt"));

			// Review dwelling and continue
			dwellingPage.waitTime(2);
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.overrideNoLongerQuotableBuildings();
			}

			// Click on modify form button
			Assertions.verify(createQuotePage.modifyForms.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();
			Assertions.passTest("Create Quote Page", "Clicked on modify form button successfully");

			// Verifying TRE(total roof exclusion) attached automatically when year built 26
			// years older and roof cladding = Hurricane Shingle and year roof last replaced
			// = year
			// built
			Assertions.addInfo("Scenario 11", "Verifying TRE(total roof exclusion) attached automatically");
			Assertions.verify(modifyForms.totalRoofExclusion.checkIfElementIsSelected(), true, "Modify Form Page",
					"Total roof exclusion selected successfully", false, false);
			Assertions.addInfo("Scenario 11", "Scenario 11 Ended");

			// Click on override button
			modifyForms.override.scrollToElement();
			modifyForms.override.click();
			Assertions.passTest("Modify Form Page", "Clicked on override button successfully");

			// Asserting and Verifying warning message when year built 26 older=1998 and
			// roof
			// cladding =Hurricane Shingle
			// warning message is 'The account is ineligible due to the roof age being
			// outside of ICAT's guidelines. If you have additional information that adjusts
			// the roof age, please email it to your ICAT Online Underwriter.
			Assertions.addInfo("Scenario 12", "Asserting and Verifying warning message");
			createQuotePage.warningMessages.formatDynamicPath("The account is ineligible due to the roof age")
					.waitTillPresenceOfElement(60);
			Assertions.verify(
					createQuotePage.warningMessages.formatDynamicPath("The account is ineligible due to the roof age")
							.getData().contains(
									"The account is ineligible due to the roof age being outside of ICAT's guidelines"),
					true, "Create Quote Page",
					"Warning message is "
							+ createQuotePage.warningMessages
									.formatDynamicPath("The account is ineligible due to the roof age").getData()
							+ " is displayed when Roof Cladding is " + testData.get("L1D1-DwellingRoofCladding")
							+ " and Year Built is " + testData.get("L1D1-DwellingYearBuilt"),
					false, false);
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
			Assertions.passTest("Scenario 12", "Scenario 12 Ended");

			// Getting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Rewrite Quote Number is " + quoteNumber);

			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View Print Full Quote Page", "Clicked on view print full quote link successfully", false, false);

			// Asserting and Verifying Roof coverage as 'excluded' on view print full quote
			// page
			Assertions.addInfo("Scenario 13", "Asserting and verifying roof coverage as 'Excluded'");
			Assertions.verify(viewOrPrintFullQuotePage.roofCoverage.formatDynamicPath(1).getData().equals("Excluded"),
					true, "View Print Full Quote Page", "Roof coverage is "
							+ viewOrPrintFullQuotePage.roofCoverage.formatDynamicPath(1).getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 13", "Scenario 13 Ended");
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.passTest("View Print Full Quote Page", "Clicked on back button");
			Assertions.addInfo("Test case 2", "Test case 2 Ended");

			// Check if the NAHO Roof validation is trigerred as per the given condition on
			// endorsement and Renewal,year built=200,2005 and roof cladding = still or
			// metal and Wood Shakes or Wood Shingles
			Assertions.addInfo("Test case 3",
					"Check if the NAHO Roof validation is trigerred as per the given condition on endorsement and  Renewal,year built=200,2005 and roof cladding = still or metal and Wood Shakes or Wood Shingles");

			// Click on home page
			for (int i = 1; i >= 1; i++) {
				testData = data.get(dataValue5);
				int r = i;

				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();

				// Searching pre-existing policy its created in 2023
				homePage.findFilterArrow.scrollToElement();
				homePage.findFilterArrow.click();
				homePage.findFilterPolicyOption.scrollToElement();
				homePage.findFilterPolicyOption.click();
				homePage.businessTypePolicyArrow.scrollToElement();
				homePage.businessTypePolicyArrow.click();
				homePage.businessTypePolicyOption.formatDynamicPath("Residential").scrollToElement();
				homePage.businessTypePolicyOption.formatDynamicPath("Residential").click();
				homePage.policyStateArrow.scrollToElement();
				homePage.policyStateArrow.click();
				homePage.policyStateOption.formatDynamicPath(testData.get("InsuredState")).scrollToElement();
				homePage.policyStateOption.formatDynamicPath(testData.get("InsuredState")).click();
				homePage.afterEffectiveDate.scrollToElement();
				homePage.afterEffectiveDate.setData(testData.get("AfterEffectiveDate"));
				homePage.beforeEffectiveDate.scrollToElement();
				homePage.beforeEffectiveDate.setData(testData.get("BeforeEffectiveDate"));
				homePage.findPolicyButton.scrollToElement();
				homePage.findPolicyButton.click();

				homePage.resultTable.formatDynamicPath("Active", r).waitTillPresenceOfElement(60);
				homePage.resultTable.formatDynamicPath("Active", r).scrollToElement();
				homePage.resultTable.formatDynamicPath("Active", r).click();

				if (policySummaryPage.renewPolicy.checkIfElementIsPresent()
						&& policySummaryPage.renewPolicy.checkIfElementIsDisplayed()) {
					policyNumber = policySummaryPage.policyNumber.getData();
					Assertions.passTest("Policy Summary Page",
							"Policy searched successfully,Policy number is " + policyNumber);
					// Click on Endorse PB
					policySummaryPage.endorsePB.scrollToElement();
					policySummaryPage.endorsePB.click();
					Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");
					break;
				}

			}
			if (policySummaryPage.okButton.checkIfElementIsPresent()
					&& policySummaryPage.okButton.checkIfElementIsDisplayed()) {
				policySummaryPage.okButton.scrollToElement();
				policySummaryPage.okButton.click();
				policySummaryPage.endorsePB.scrollToElement();
				policySummaryPage.endorsePB.click();

			}
			if (endorsePolicyPage.deleteExistingEndorsementButton.checkIfElementIsPresent()
					&& endorsePolicyPage.deleteExistingEndorsementButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.deleteExistingEndorsementButton.scrollToElement();
				endorsePolicyPage.deleteExistingEndorsementButton.click();
			}
			// Entering Endorsement effective date
			testData = data.get(dataValue5);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Transaction effective date entered successfully");

			// Click on Edit location/building information
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();
			Assertions.passTest("Endorse Policy Page",
					"Clicked on edit location or building information link successfully");

			// Updating year built 21-25 years older,that is year built =2000 and roof
			// cladding = still or metal and year roof last replaced same as year built
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling  Page",
					"Dwelling Page Loaded successfully", false, false);
			dwellingPage.yearBuilt.scrollToElement();
			dwellingPage.yearBuilt.clearData();
			if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
				dwellingPage.expiredQuotePopUp.scrollToElement();
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
				dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}
			dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));
			if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
				dwellingPage.expiredQuotePopUp.scrollToElement();
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
				dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}
			dwellingPage.yearBuilt.tab();
			testData = data.get(dataValue5);
			dwellingPage.addRoofDetails(testData, 1, 1);
			Assertions.passTest("Dwelling Page",
					"Roof Cladding is updated to " + testData.get("L1D1-DwellingRoofCladding")
							+ " and Year Built is updated to " + testData.get("L1D1-DwellingYearBuilt"));

			// Review dwelling and continue
			dwellingPage.waitTime(2);
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			dwellingPage.continueButton.scrollToElement();
			dwellingPage.continueButton.click();
			Assertions.passTest("Dwelling Page", "Clicked on continue button");

			if (createQuotePage.continueToForms.checkIfElementIsPresent()
					&& createQuotePage.continueToForms.checkIfElementIsDisplayed()) {
				createQuotePage.continueToForms.scrollToElement();
				createQuotePage.continueToForms.click();
				Assertions.passTest("Create Quote Page", "Clicked on continu form button successfully");
			}

			if (createQuotePage.modifyForms.checkIfElementIsPresent()
					&& createQuotePage.modifyForms.checkIfElementIsDisplayed()) {
				// Click on modify form button
				Assertions.verify(createQuotePage.modifyForms.checkIfElementIsDisplayed(), true, "Create Quote Page",
						"Create quote page loaded successfully", false, false);
				createQuotePage.modifyForms.scrollToElement();
				createQuotePage.modifyForms.click();
				Assertions.passTest("Create Quote Page", "Clicked on modify form button successfully");
			}

			// Verifying AVC and TRE forms are not attached automatically, when year built
			// and year roof last replaced 21-25 years older = 2000 and roof cladding =
			// Still or Metal
			Assertions.addInfo("Scenario 14", "Verifying ACV and TRE forms are not attached automatically");
			Assertions.verify(modifyForms.totalRoofExclusion.checkIfElementIsSelected(), false, "Modify Form Page",
					"Total roof exclusion not attached", false, false);
			Assertions.verify(modifyForms.actualCashValueRoof.checkIfElementIsSelected(), false, "Modify Form Page",
					"Actual cash value not attached", false, false);
			Assertions.addInfo("Scenario 14", "Scenario 14 Ended");

			// Click on override button
			modifyForms.override.scrollToElement();
			modifyForms.override.click();
			Assertions.passTest("Modify Form Page", "Clicked on override button successfully");

			// Asserting and Verifying warning message when year built 21-25 older=2000 and
			// roof
			// cladding =still or metal
			// warning message is 'The quoted building has a roof age outside of our
			// guidelines. For consideration, please provide your ICAT Online Underwriter
			// with additional information regarding the condition of the roof, such as a
			// recent inspection
			Assertions.addInfo("Scenario 15", "Asserting and Verifying warning message");
			createQuotePage.warningMessages
					.formatDynamicPath("The quoted building has a roof age outside of our guidelines")
					.waitTillPresenceOfElement(60);
			Assertions.verify(
					createQuotePage.warningMessages
							.formatDynamicPath("The quoted building has a roof age outside of our guidelines").getData()
							.contains("The quoted building has a roof age outside of our guidelines"),
					true, "Create Quote Page",
					"Warning message is " + createQuotePage.warningMessages
							.formatDynamicPath("The quoted building has a roof age outside of our guidelines").getData()
							+ " is displayed when Roof Cladding is " + testData.get("L1D1-DwellingRoofCladding")
							+ " and Year Built is " + testData.get("L1D1-DwellingYearBuilt"),
					false, false);
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
			Assertions.passTest("Scenario 15", "Scenario 15 Ended");

			// Click on next
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Click on next button");

			if (endorsePolicyPage.oKContinueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.oKContinueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.oKContinueButton.scrollToElement();
				endorsePolicyPage.oKContinueButton.click();
			}

			// Click on view endt quote button
			endorsePolicyPage.viewEndorsementQuote.scrollToElement();
			endorsePolicyPage.viewEndorsementQuote.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on view endorsement quote button");

			// Asserting and Verifying the roof coverage display as replacement Cost in Endt
			// quote document when year built 21-25 year older =2000, roof cladding= still
			// or metal
			Assertions.addInfo("Scenario 16", "Asserting and Verifying the roof coverage display as replacement Cost");
			Assertions.verify(
					viewOrPrintFullQuotePage.roofCoverage.formatDynamicPath(1).getData().equals("Replacement Cost"),
					true, "Endorse Quote Document Page", "Roof coverage is "
							+ viewOrPrintFullQuotePage.roofCoverage.formatDynamicPath(1).getData() + " displayed",
					false, false);
			viewOrPrintFullQuotePage.closeButton.scrollToElement();
			viewOrPrintFullQuotePage.closeButton.click();
			Assertions.addInfo("Scenario 16", "Scenario 16 Ended");

			// Click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on complete button successfully");

			if (endorsePolicyPage.rollForwardBtn.checkIfElementIsPresent()
					&& endorsePolicyPage.rollForwardBtn.checkIfElementIsDisplayed()) {
				endorsePolicyPage.rollForwardBtn.scrollToElement();
				endorsePolicyPage.rollForwardBtn.click();
			}

			// Click on Close
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on close button successfully");

			// clicking on renewal policy link
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();

			// Go to Home Page
			if (policySummaryPage.expaacMessage.checkIfElementIsPresent()
					&& policySummaryPage.expaacMessage.checkIfElementIsDisplayed()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.click();
				expaccInfoPage.enterExpaccInfo(testData, policyNumber);
				Assertions.passTest("ExpaccInfo Page", "ExpaccInfo details entered successfully");

				// Go to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();

				// Performing Renewal Searches
				homePage.searchPolicy(policyNumber);

				// clicking on renewal policy link
				policySummaryPage.renewPolicy.waitTillPresenceOfElement(60);
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();
			}

			// Getting renewal quote number
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account overview page", "Renewal quote number " + quoteNumber);
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();

			// Verifying and Asserting referral message 'The quoted building has a roof age
			// outside of our guidelines. For consideration, please provide your ICAT Online
			// Underwriter with additional information regarding the condition of the roof,
			// such as a recent inspection,when year built 21-25 year older =2000, roof
			// cladding= still or metal
			Assertions.addInfo("Scenario 17", "Verifying and Asserting referral message");
			Assertions.verify(
					referralPage.producerQuoteStatus.formatDynamicPath("The quoted").getData()
							.contains("The quoted building has a roof age outside of our guidelines"),
					true, "Referral Page",
					"The referral message is "
							+ referralPage.producerQuoteStatus.formatDynamicPath("The quoted").getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 17", "Scenario 17 Ended");

			// Clicking on pick up button
			if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
				referralPage.pickUp.scrollToElement();
				referralPage.pickUp.click();
			}

			// Approve Referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			Assertions.verify(approveDeclineQuotePage.approveButton.checkIfElementIsDisplayed(), true,
					"Approve Decline Quote Page", "Approve Decline Quote page loaded successfully", false, false);
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Approve Decline Quote Page", "Bind Request approved successfully");
			Assertions.passTest("Approve Decline Quote Page",
					"Quote number : " + quoteNumber + " approved successfully");

			// Search for quote
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote searched successfully");

			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View Print Full Quote Page", "Clicked on view print full quote link successfully", false, false);

			// Verifying and Asserting roof coverage as 'Actual Cash Value' when year
			// built 21-25 years older=2000,roof cladding = still or metal and year roof
			// last replaced same as year built
			Assertions.addInfo("Scenario 18", "Asserting and verifying roof coverage as 'actual cash value'");
			Assertions.verify(
					viewOrPrintFullQuotePage.roofCoverage.formatDynamicPath(1).getData().equals("Actual Cash Value"),
					true, "View Print Full Quote Page", "Roof coverage is "
							+ viewOrPrintFullQuotePage.roofCoverage.formatDynamicPath(1).getData() + " displayed",
					false, false);
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.addInfo("Scenario 18", "Scenario 18 Ended");

			// Click on edit dwelling link
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account overview page loaded successfully", false, false);
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit dwelling");

			// Updating year built 2005 and roof cladding = Wood Shakes or Wood Shingles
			testData = data.get(dataValue6);
			dwellingPage.yearBuilt.scrollToElement();
			dwellingPage.yearBuilt.clearData();
			if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
				dwellingPage.expiredQuotePopUp.scrollToElement();
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
				dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}
			dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));
			if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
				dwellingPage.expiredQuotePopUp.scrollToElement();
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
				dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}
			dwellingPage.yearBuilt.tab();
			dwellingPage.addRoofDetails(testData, 1, 1);
			Assertions.passTest("Dwelling Page",
					"Roof Cladding is updated to " + testData.get("L1D1-DwellingRoofCladding")
							+ " and Year Built is updated to " + testData.get("L1D1-DwellingYearBuilt"));

			// Review dwelling and continue
			dwellingPage.waitTime(2);
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.overrideNoLongerQuotableBuildings();
			}

			// Click on modify form button
			Assertions.verify(createQuotePage.modifyForms.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();
			Assertions.passTest("Create Quote Page", "Clicked on modify form button successfully");

			// Verifying TRE(total roof exclusion) attached automatically when year built 11
			// years older =2005 and roof cladding = Wood Shakes or Wood Shingles and year
			// roof last replaced = year built
			Assertions.addInfo("Scenario 19", "Verifying TRE(total roof exclusion) attached automatically");
			Assertions.verify(modifyForms.totalRoofExclusion.checkIfElementIsSelected(), true, "Modify Form Page",
					"Total roof exclusion selected successfully", false, false);
			Assertions.addInfo("Scenario 19", "Scenario 19 Ended");

			// Click on override button
			modifyForms.override.scrollToElement();
			modifyForms.override.click();
			Assertions.passTest("Modify Form Page", "Clicked on override button successfully");

			// Asserting and Verifying warning message when year built 11 year older=2005
			// and roof
			// cladding =Wood Shakes or Wood Shingles
			// warning message is 'The account is ineligible due to the roof age being
			// outside of ICAT's guidelines. If you have additional information that adjusts
			// the roof age, please email it to your ICAT Online Underwriter.
			Assertions.addInfo("Scenario 20", "Asserting and Verifying warning message");
			createQuotePage.warningMessages.formatDynamicPath("The account is ineligible due to the roof age")
					.waitTillPresenceOfElement(60);
			Assertions.verify(
					createQuotePage.warningMessages.formatDynamicPath("The account is ineligible due to the roof age")
							.getData().contains(
									"The account is ineligible due to the roof age being outside of ICAT's guidelines"),
					true, "Create Quote Page",
					"Warning message is "
							+ createQuotePage.warningMessages
									.formatDynamicPath("The account is ineligible due to the roof age").getData()
							+ " is displayed when Roof Cladding is " + testData.get("L1D1-DwellingRoofCladding")
							+ " and Year Built is " + testData.get("L1D1-DwellingYearBuilt"),
					false, false);
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
			Assertions.passTest("Scenario 20", "Scenario 20 Ended");

			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account overview page", "Renewal quote number2 " + quoteNumber);

			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View Print Full Quote Page", "Clicked on view print full quote link successfully", false, false);

			// Asserting and Verifying Roof coverage as 'excluded' on view print full quote
			// page,when year built 11 year older=2005 and roof
			// cladding =Wood Shakes or Wood Shingles
			Assertions.addInfo("Scenario 21", "Asserting and verifying roof coverage as 'Excluded'");
			Assertions.verify(viewOrPrintFullQuotePage.roofCoverage.formatDynamicPath(1).getData().equals("Excluded"),
					true, "View Print Full Quote Page", "Roof coverage is "
							+ viewOrPrintFullQuotePage.roofCoverage.formatDynamicPath(1).getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 21", "Scenario 21 Ended");
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.passTest("View Print Full Quote Page", "Clicked on back button");
			Assertions.addInfo("Test case 3", "Test case 3 ended");

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC094 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC094 ", "Executed Successfully");
			}
		}
	}
}
