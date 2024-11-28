/** Program Description: Create GL Policy-->Initiate Endt as USM change NS/NH/EQ deductibles,change TRIA and GL information.Click on Relase to producer,Login as producer Complete the endt.Click on Reverse Last Endorsement complete the work flow and IO-21497
 *  Author			   : Sowndarya
 *  Date of Modified   : 09/29/2020
 **/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.DateConversions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.GLInformationPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC088 extends AbstractCommercialTest {

	public TC088() {
		super(LoginType.PRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID088.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		GLInformationPage gLInformationPage = new GLInformationPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();

		// Initializing the variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
		DateConversions date = new DateConversions();
		String insuredName = testData.get("InsuredName") + date.getCurrentDate("MM/dd/YYYY_hh:mm:ss");
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// creating New account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.createNewAccountProducer.click();
			homePage.namedInsured.waitTillVisibilityOfElement(60);
			homePage.namedInsured.scrollToElement();
			homePage.namedInsured.setData(insuredName);
			Assertions.passTest("Home Page", "Insured Name is " + testData.get("InsuredName"));
			homePage.waitTime(1);// need wait time to load the page
			if (homePage.productArrow.checkIfElementIsPresent() && homePage.productArrow.checkIfElementIsDisplayed()) {
				homePage.productArrow.waitTillVisibilityOfElement(60);
				homePage.productArrow.scrollToElement();
				homePage.productArrow.click();
				homePage.productSelection.formatDynamicPath(testData.get("ProductSelection")).click();
			}
			if (homePage.effectiveDate.formatDynamicPath(1).checkIfElementIsPresent()
					&& homePage.effectiveDate.formatDynamicPath(1).checkIfElementIsDisplayed()) {
				homePage.effectiveDate.formatDynamicPath(1).scrollToElement();
				homePage.effectiveDate.formatDynamicPath(1).waitTillVisibilityOfElement(60);
				homePage.effectiveDate.formatDynamicPath(1).setData(testData.get("PolicyEffDate"));
			}
			homePage.goButton.click();
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

			// selecting peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}

			// Entering Prior Losses
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Refer Quote for binding
			if (referQuotePage.referQuote.checkIfElementIsPresent()
					&& referQuotePage.referQuote.checkIfElementIsDisplayed()) {
				referQuotePage.contactName.setData(testData.get("ProducerName"));
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.click();
				quoteNumber = referQuotePage.quoteNum.getData();

				// Signing out as producer
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();

				// Login as USM
				loginPage.refreshPage();
				loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
				Assertions.passTest("Login", "Logged in to application successfully");

				// Go to HomePage
				homePage.goToHomepage.click();
				homePage.searchQuote(quoteNumber);
				Assertions.passTest("Home Page", "Quote for referral is searched successfully");

				// Asserting the Quote Status
				Assertions.verify(accountOverviewPage.quoteStatus.formatDynamicPath("1").getData().contains("Referred"),
						true, "Account Overview Page", "Quote " + quoteNumber + " Status is "
								+ accountOverviewPage.quoteStatus.formatDynamicPath("1").getData(),
						false, false);

				// Click on Open referral link
				accountOverviewPage.openReferralLink.scrollToElement();
				accountOverviewPage.openReferralLink.click();

				// Approve Referral
				if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
					referralPage.pickUp.scrollToElement();
					referralPage.pickUp.click();
				}
				referralPage.approveOrDeclineRequest.waitTillButtonIsClickable(60);
				referralPage.approveOrDeclineRequest.scrollToElement();
				referralPage.approveOrDeclineRequest.click();
				referralPage.scrollToBottomPage();
				referralPage.internalComments.setData(testData.get("PremiumAdjustment_InternalComments"));
				referralPage.externalComments.setData(testData.get("PremiumAdjustment_InternalComments"));
				referralPage.approveRequest.waitTillButtonIsClickable(60);
				referralPage.approveRequest.scrollToElement();
				referralPage.approveRequest.click();
				Assertions.passTest("Referral Page", "Bind Referral Approved successfully");

				// Searching the account
				homePage.goToHomepage.click();

				// Signing out as USM
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();

				// Login as USM
				loginPage.refreshPage();
				loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
				Assertions.passTest("Login", "Logged in to application successfully");
				homePage.searchQuoteByProducer(quoteNumber);
			}

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Adding the ticket IO-20835
			// click on quote specifics
			accountOverviewPage.quoteSpecifics.scrollToElement();
			accountOverviewPage.quoteSpecifics.click();
			Assertions.passTest("Account Overview Page", "Clicked on Quote Specifics");

			// Verifying the absence of Vie participation and vie contribution charge
			Assertions.addInfo("Scenario 01", "Verifying the absence of Vie participation and vie contribution charge");
			Assertions.verify(accountOverviewPage.vieParicipation.checkIfElementIsPresent(), false,
					"Account Overview Page", "VIE Participation is not displayed for producer is verified", false,
					false);
			Assertions.verify(accountOverviewPage.vieContributionCharge.checkIfElementIsPresent(), false,
					"Account Overview Page", "VIE Contribution charge is not displayed for producer is verified", false,
					false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// entering details in request bind page
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
			requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Signing out as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Producer successfully");

			// Login as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in as USM successfully");

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Assert the Subscription Agreement message
			// Adding the ticket IO-20879
			Assertions.addInfo("Scenario 02",
					"Verifying subscription agreement message before binding the quote as USM");
			Assertions.verify(requestBindPage.reciprocalAgreementMsg.checkIfElementIsDisplayed(), true,
					"Request Bind Page",
					"The Message " + requestBindPage.reciprocalAgreementMsg.getData() + " displayed is verified", false,
					false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Verifying the doc approve links on request bind page not broken
			// IO-21497
			Assertions.addInfo("Scenario 03", "Verifying the doc approve links on request bind page not broken");
			Assertions.verify(requestBindPage.reciprocalAgreementMsg.getData().contains("a target=\"_blank"), false,
					"Request Bind Page",
					"The Message " + requestBindPage.reciprocalAgreementMsg.getData() + " displayed is verified", false,
					false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// search quote
			homePage.searchQuote(quoteNumber);

			// Approve prebind documents
			accountOverviewPage.uploadPreBindApproveAsUSM();
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
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterPolicyOption.scrollToElement();
			homePage.findFilterPolicyOption.click();
			homePage.policyNumber.scrollToElement();
			homePage.policyNumber.setData(policyNumber);
			homePage.findPolicyButton.scrollToElement();
			homePage.findPolicyButton.click();
			Assertions.passTest("Home Page", "Policy Number : " + policyNumber + " searched successfully");

			// endorse policy
			Assertions.addInfo("Policy Summary Page", "Initiating Endorsement to change the NS/NH/EQ deductibles");
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// setting Endoresment Effective Date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered the Endorsement Effective Date");

			// Click on Change coverage options link
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Coverage Options link");

			// Changing the coverage details
			testData = data.get(data_Value2);
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page loaded successfully", false, false);
			createQuotePage.editDeductiblesCommercialPNB(testData);
			Assertions.passTest("Create Quote Page", "Changed the Deductible values successfully");

			// Click on continue endorsement button
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();

			// click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Click on Next button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// click on continue
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// click on Revise button
			endorsePolicyPage.reviseButton.scrollToElement();
			endorsePolicyPage.reviseButton.click();
			Assertions.passTest("Endorse Summary Page", "Clicked on Revise Button");
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);

			// Click on Change coverage options link
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Coverage Options link");
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page loaded successfully", false, false);

			// Changing the TRIA Coverage
			Assertions.addInfo("Create Quote Page", "Changing the TRIA Coverage");
			createQuotePage.editOptionalCoverageDetailsPNB(testData);
			Assertions.passTest("Create Quote Page", "Changed the TRIA Coverage successfully");

			// Click on continue endorsement button
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();

			// click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// click on edit GL Information details
			endorsePolicyPage.editGLInformationLink.scrollToElement();
			endorsePolicyPage.editGLInformationLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Edit GL Information link");
			gLInformationPage.enterGLInformation(testData);
			gLInformationPage.continueEndorsementButton.scrollToElement();
			gLInformationPage.continueEndorsementButton.click();

			// Click on continue endorsement button
			if (createQuotePage.continueEndorsementButton.checkIfElementIsPresent()
					&& createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed()) {
				Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
						"Create Quote Page", "Create Quote Page loaded sccessfully", false, false);
				createQuotePage.continueEndorsementButton.scrollToElement();
				createQuotePage.continueEndorsementButton.click();
			}

			// click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Click on Next button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("GL Information Page", "Changed the GL Details successfully");

			// click on continue
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// click on View Endorsement quote
			endorsePolicyPage.viewEndtQuoteButton.scrollToElement();
			endorsePolicyPage.viewEndtQuoteButton.click();
			Assertions.passTest("Endorse Summary Page", "Clicked on View Endorsement Quote Button");

			// click on close button
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorsement Quote Page", "Clicked on Close button");

			// click on Release to producer button
			endorsePolicyPage.releasetoProducerButton.scrollToElement();
			endorsePolicyPage.releasetoProducerButton.click();
			Assertions.passTest("Endorse Summary Page", "Clicked on Release to Producer Button");

			// Signing out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as USM successfully");

			// Login as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in as Producer successfully");

			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);
			homePage.searchPolicyByProducer(policyNumber);
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);

			// Click on Endorsepolicy link
			policySummarypage.producerEndorsePolicyLink.scrollToElement();
			policySummarypage.producerEndorsePolicyLink.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse Policy Link");

			// click on all other changes link
			endorsePolicyPage.allOtherChanges.waitTillPresenceOfElement(60);
			endorsePolicyPage.allOtherChanges.waitTillVisibilityOfElement(60);
			endorsePolicyPage.allOtherChanges.scrollToElement();
			endorsePolicyPage.allOtherChanges.click();

			// click on edit existing endt button
			endorsePolicyPage.editExistingEnmtButton.scrollToElement();
			endorsePolicyPage.editExistingEnmtButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Edit Existing Endorsement button");

			// click on submit button
			endorsePolicyPage.submitButton.scrollToElement();
			endorsePolicyPage.submitButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Submit button");

			// Enter Referral Contact Details
			testData = data.get(data_Value1);
			referQuotePage.contactName.setData(testData.get("ProducerName"));
			referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
			referQuotePage.referQuote.click();
			Assertions.passTest("Referral Page", "Referral Contact Details entered successfully");

			Assertions.addInfo("Scenario 04",
					"Assert the Referral Message when the external user Initiate the endorsement");
			Assertions.verify(referralPage.referalReceivedMsg.checkIfElementIsDisplayed(), true, "Referral Page",
					"The Message " + referralPage.referalReceivedMsg.getData() + " displayed is verified", false,
					false);
			Assertions.addInfo("Scenario 04", "Sceanrio 04 Ended");

			// Signing out as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Producer successfully");

			// Login as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in as USM successfully");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			// homePage.searchReferral(insuredName);
			homePage.searchReferral(policyNumber);
			Assertions.passTest("Home Page", "Searched the Account successfully");

			// approving referral
			if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
				referralPage.pickUp.scrollToElement();
				referralPage.pickUp.click();
			}
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Referral Page", "Endorsement Referral request approved successfully");

			// Asserting referral complete message
			Assertions.addInfo("Scenario 05", "Asserting Referral Complete Message");
			Assertions.verify(referralPage.referralCompleteMsg.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral Complete message " + referralPage.referralCompleteMsg.getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 05", "Sceanrio 05 Ended");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Search the policy
			homePage.searchPolicy(policyNumber);
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page Loaded successfully", false, false);

			Assertions.addInfo("Scenario 06", "Asserting Tranaction History Reason on Policy summary page");
			Assertions.verify(policySummarypage.transHistReason.formatDynamicPath(3).checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "The Transaction History reason "
							+ policySummarypage.transHistReason.formatDynamicPath(3).getData() + " displayed ",
					false, false);
			Assertions.addInfo("Scenario 06", "Sceanrio 06 Ended");

			// click on reverse last endorsement link
			Assertions.addInfo("Policy Summary Page", "Reverse the Last Endorsement");
			policySummarypage.reversePreviousEndorsementLink.scrollToElement();
			policySummarypage.reversePreviousEndorsementLink.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Reverse Last Endorsement Link");
			Assertions.verify(endorsePolicyPage.completeButton.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Endorse Policy Page loaded successfully", false, false);

			// click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Complete button");

			// click on close button
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Assert the transaction history reason
			Assertions.addInfo("Scenario 07",
					"Asserting the Transaction History Reason After Reversing the Last Endorsement");
			Assertions.verify(policySummarypage.transHistReason.formatDynamicPath(4).checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "The Transaction History reason "
							+ policySummarypage.transHistReason.formatDynamicPath(4).getData() + " displayed ",
					false, false);
			Assertions.addInfo("Scenario 07", "Sceanrio 07 Ended");

			// Signout and close the browser
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 88", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 88", "Executed Successfully");
			}
		}
	}
}
