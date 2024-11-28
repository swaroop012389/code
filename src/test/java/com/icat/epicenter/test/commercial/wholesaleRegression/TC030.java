/** Program Description: Add more than 5 losses assert the referral message and click on endorse PB link add 6 prior losses,assert the referral message and Added CR IO-19746 and CR IO-19840
 *  Author			   : John
 *  Date of Creation   : 11/28/2019
 **/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC030 extends AbstractCommercialTest {

	public TC030() {
		super(LoginType.PRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID030.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		DwellingPage dwellingPage = new DwellingPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		LoginPage loginPage = new LoginPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		ViewPolicySnapShot viewPolicySnapShot = new ViewPolicySnapShot();

		// Initializing the variables
		Map<String, String> testData;
		int dataValue1 = 0;
		int dataValue2 = 1;
		testData = data.get(dataValue1);
		int quotelength;
		String quoteNumber;
		String policyNumber;
		boolean isTestPassed = false;

		try {
			// creating New account
			homePage.enterPersonalLoginDetails();
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zipcode in Eligibility page;
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.addBuildingsButton.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);
			Assertions.passTest("Location Page", "Location details entered successfully");

			// Entering Location 1 Dwelling 1 Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// selecting peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// enter prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
				Assertions.passTest("Prior Loss Page", "6 Prior Losses entered successfully");
			}

			// Asserting Aop referral message
			Assertions.addInfo("AOP No Longer Eligible Page", "Asserting Aop referral message");
			String aopReferralMsg = dwellingPage.aopNolongerEligible.getData();
			Assertions.verify(dwellingPage.aopNolongerEligible.checkIfElementIsDisplayed(), true,
					"AOP No Longer Eligible Page", "The Message displayed is " + aopReferralMsg, false, false);

			// click on cancel button
			buildingNoLongerQuoteablePage.cancel.scrollToElement();
			buildingNoLongerQuoteablePage.cancel.click();
			Assertions.passTest("AOP No Longer Eligible Page", "Clicked on cancel button");

			// Removing the Prior losses
			accountOverviewPage.editPriorLoss.scrollToElement();
			accountOverviewPage.editPriorLoss.click();
			Assertions.passTest("Prior Losses Page", "Prior Losses page loaded successfully");
			for (int i = 5; i >= 1; i--) {
				priorLossPage.deletePriorLoss.formatDynamicPath(i).scrollToElement();
				priorLossPage.deletePriorLoss.formatDynamicPath(i).click();
			}
			Assertions.passTest("Prior Losses Page", "Removed all the Prior Losses successfully");
			priorLossPage.lossesInThreeYearsNo.scrollToElement();
			priorLossPage.lossesInThreeYearsNo.click();

			// click on continue button
			priorLossPage.continueButton.scrollToElement();
			priorLossPage.continueButton.click();

			// click on quote account button
			accountOverviewPage.quoteAccountButton.scrollToElement();
			accountOverviewPage.quoteAccountButton.click();

			// Select peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Navigate to account overview page
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quotelength = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quotelength - 1);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login", "Logged in to application successfully");

			// search the quote
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Searched the Quote successfully");

			// entering details in request bind page
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home Page", "Clicked on Home button");

			// Go to HomePage
			homePage.goToHomepage.click();
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
			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Approval page loaded successfully", false, false);
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// Adding Below code for CR IO-19840
			// Verifying the presence of Back button on View Policy snapshot page
			// Click on view policy snap shot link
			policySummarypage.viewPolicySnapshot.scrollToElement();
			policySummarypage.viewPolicySnapshot.click();
			Assertions.addInfo("Scenario 01", "Verifying the presence of Back button on View Policy snapshot page");
			Assertions.verify(
					viewPolicySnapShot.goBackButton.checkIfElementIsPresent()
							&& viewPolicySnapShot.goBackButton.checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page", "View Policy Snapshot loaded successfully", false, false);
			Assertions.passTest("Scenario 01", "Scenario 01 Ended");

			// Click on go back button
			viewPolicySnapShot.goBackButton.scrollToElement();
			viewPolicySnapShot.goBackButton.click();
			// CR IO-19840 ended

			// click on endorse PB link
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// Click on Edit Prior loss
			endorsePolicyPage.editPriorLoss.scrollToElement();
			endorsePolicyPage.editPriorLoss.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Edit prior losses");

			// Adding the CR IO-20630
			Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossPage.lossesInThreeYearsYes.scrollToElement();
			priorLossPage.lossesInThreeYearsYes.click();
			priorLossPage.typeOfLossArrow.scrollToElement();
			priorLossPage.typeOfLossArrow.click();

			// Verify the presence of Commercial General Liability Option in the dropdown
			testData = data.get(dataValue2);
			Assertions.verify(
					priorLossPage.typeOfLossOption.formatDynamicPath(dataValue1, testData.get("PriorLossType1"))
							.checkIfElementIsDisplayed(),
					true, "Prior Losses Page", "The Option " + testData.get("PriorLossType1") + " present is verified",
					false, false);
			priorLossPage.typeOfLossOption.formatDynamicPath(dataValue1, testData.get("PriorLossType1"))
					.scrollToElement();
			priorLossPage.typeOfLossOption.formatDynamicPath(dataValue1, testData.get("PriorLossType1")).click();

			// click on cancel
			priorLossPage.cancelButton.waitTillVisibilityOfElement(60);
			priorLossPage.cancelButton.scrollToElement();
			priorLossPage.cancelButton.click();

			// Click on Edit Prior loss
			endorsePolicyPage.editPriorLoss.scrollToElement();
			endorsePolicyPage.editPriorLoss.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Edit prior losses");

			// Entering prior loss as endorsement
			testData = data.get(dataValue1);
			if (!testData.get("PriorLoss1").equals("")) {
				priorLossPage.selectPriorLossesInformation(testData);
				Assertions.passTest("Prior Loss Page", "6 Prior Losses entered successfully");
			}
			if (endorsePolicyPage.nextButton.checkIfElementIsPresent()
					&& endorsePolicyPage.nextButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.nextButton.scrollToElement();
				endorsePolicyPage.nextButton.click();
				endorsePolicyPage.nextButton.waitTillInVisibilityOfElement(60);
			}

			// Asserting the referral messages
			Assertions.addInfo("Endorse Policy Page", "Asserting the prior loss referral messages");
			Assertions.verify(endorsePolicyPage.priorLossReferralMsg1.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page",
					"The Referral  message displayed is " + endorsePolicyPage.priorLossReferralMsg1.getData(), false,
					false);
			Assertions.verify(endorsePolicyPage.priorLossReferralMsg2.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page",
					"The Referral message displayed is " + endorsePolicyPage.priorLossReferralMsg2.getData(), false,
					false);
			Assertions.verify(endorsePolicyPage.priorLossReferralMsg3.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page",
					"The Referral message displayed is " + endorsePolicyPage.priorLossReferralMsg3.getData(), false,
					false);

			// Adding below code for CR IO-19746
			// Click on continue
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on complete button successfully");

			// Click on close
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.verify(policySummarypage.cancelPolicy.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);

			// Click on cancel policy link
			policySummarypage.cancelPolicy.scrollToElement();
			policySummarypage.cancelPolicy.click();

			// Select cancellation reason
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).scrollToElement();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			Assertions.passTest("Cancel Policy Page", "Cancellation reason is " + testData.get("CancellationReason"));

			// Enter legal notice wording
			cancelPolicyPage.legalNoticeWording.waitTillPresenceOfElement(60);
			cancelPolicyPage.legalNoticeWording.scrollToElement();
			cancelPolicyPage.legalNoticeWording.appendData(testData.get("Cancellation_LegalNoticeWording"));

			// Enter cancellation effective date
			cancelPolicyPage.cancellationEffectiveDate.waitTillPresenceOfElement(60);
			cancelPolicyPage.cancellationEffectiveDate.clearData();
			cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
			cancelPolicyPage.cancellationEffectiveDate.appendData(testData.get("CancellationEffectiveDate"));
			cancelPolicyPage.cancellationEffectiveDate.tab();

			// Click on next
			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();

			// Checking out of sequence while processing cancellation
			Assertions.addInfo("Scenario 02", "Verifying Out Of Sequence for cancellation");
			Assertions.verify(cancelPolicyPage.pageName.getData().contains("Out Of Sequence"), true,
					"Cancel Policy Page", "Out Of sequence verified while processing cancellation", false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");
			cancelPolicyPage.continueButton.scrollToElement();
			cancelPolicyPage.continueButton.click();

			// Enter underwriter comment and complete the cancellation process
			cancelPolicyPage.underwriterComment.scrollToElement();
			cancelPolicyPage.underwriterComment.appendData(testData.get("TransactionDescription"));
			cancelPolicyPage.completeTransactionButton.scrollToElement();
			cancelPolicyPage.completeTransactionButton.click();

			// Click on close
			cancelPolicyPage.closeButton.scrollToElement();
			cancelPolicyPage.closeButton.click();
			Assertions.passTest("Cancel Policy Page", "Cancellation process completed successfully");

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 30", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 30", "Executed Successfully");
			}
		}
	}
}
