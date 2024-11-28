/** Program Description: Create Policy click on cancel link and assert all the available reasons  and select  all the reasons and check for cancellatoon effective date
 *  Author			   : Sowndarya
 *  Date of Modified   : 09/28/2021
**/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.DateConversions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC083 extends AbstractCommercialTest {

	public TC083() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID083.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the pages
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();

		// Initializing the variables
		String quoteNumber;
		String policyNumber;
		int dataValue1 = 0;
		Map<String, String> testData = data.get(dataValue1);
		Date date = new Date();
		DateConversions dateconversions = new DateConversions();
		String insuredName = testData.get("InsuredName") + dateconversions.getCurrentDate("MM/dd/YYYY_hh:mm:ss");
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.createNewAccount.waitTillVisibilityOfElement(60);
			homePage.createNewAccount.scrollToElement();
			homePage.createNewAccount.click();
			homePage.namedInsured.waitTillVisibilityOfElement(60);
			homePage.namedInsured.scrollToElement();
			homePage.namedInsured.setData(insuredName);
			Assertions.passTest("Home Page", "Insured Name is " + testData.get("InsuredName"));
			homePage.producerNumber.setData("8521.1");
			homePage.productArrow.scrollToElement();
			homePage.productArrow.click();
			homePage.productSelection.formatDynamicPath(testData.get("ProductSelection")).click();
			if (homePage.effectiveDate.formatDynamicPath("1").checkIfElementIsPresent()
					&& homePage.effectiveDate.formatDynamicPath("1").checkIfElementIsDisplayed()) {
				homePage.effectiveDate.formatDynamicPath("1").scrollToElement();
				homePage.effectiveDate.formatDynamicPath("1").waitTillVisibilityOfElement(60);
				homePage.effectiveDate.formatDynamicPath("1").setData(testData.get("PolicyEffDate"));
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
			Assertions.passTest("Account Overview Page", "Quote Number is " + quoteNumber);

			// entering details in request bind page
			requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
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

			// Get policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Policy Number is " + policyNumber, false, false);

			// Click on Cancel Policy
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Cancel Policy Link");
			Assertions.verify(cancelPolicyPage.nextButton.checkIfElementIsDisplayed(), true, "Cancel Policy Page",
					"Cancel Policy Page Loaded successfully", false, false);

			// Asserting the Cancellation reasons
			Assertions.addInfo("Cancel Policy Page", "Asserting the available Cancellation reasons");
			for (int i = 0; i < 12; i++) {
				int dataValuei = i;
				Map<String, String> testDatai = data.get(dataValuei);
				cancelPolicyPage.cancelReasonArrow.scrollToElement();
				cancelPolicyPage.cancelReasonArrow.click();
				String cancelReasoni = cancelPolicyPage.cancelReasonOption
						.formatDynamicPath(testDatai.get("CancellationReason")).getData();
				Assertions.verify(
						cancelPolicyPage.cancelReasonOption.formatDynamicPath(testDatai.get("CancellationReason"))
								.checkIfElementIsDisplayed(),
						true, "Cancel Policy Page", "The Reason " + cancelReasoni + " displayed is verified", false,
						false);
				cancelPolicyPage.nextButton.scrollToElement();
				cancelPolicyPage.nextButton.click();
			}

			// Click on next button without entering anything
			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();
			Assertions.passTest("Cancel Policy Page", "Clicked on Next Button without selecting any reason");
			Assertions.addInfo("Cancel Policy Page", "Asserting the Error message when the reason is not selected");
			Assertions.verify(cancelPolicyPage.reasonErrorMessage.checkIfElementIsDisplayed(), true,
					"Cancel Policy Page",
					"The Error Message " + cancelPolicyPage.reasonErrorMessage.getData() + " displayed is verified",
					false, false);

			// Selecting Cancel Reasons
			Assertions.addInfo("Cancel Policy Page",
					"Selecting Each Reason and Assert the Populated Cancellation Effective date");
			Calendar calender = Calendar.getInstance();
			calender.setTime(date);
			boolean friday = calender.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
			if (friday) {
				for (int i = 0; i < 12; i++) {
					int dataValuei = i;
					testData = data.get(dataValuei);
					cancelPolicyPage.cancelReasonArrow.scrollToElement();
					cancelPolicyPage.cancelReasonArrow.click();
					String cancelReasoni = cancelPolicyPage.cancelReasonOption
							.formatDynamicPath(testData.get("CancellationReason")).getData();
					cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason"))
							.scrollToElement();
					cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
					Assertions.passTest("Cancel Policy Page", "The Cancel Reason Selected is " + cancelReasoni);

					// Asserting the Cancellation effective Date
					String calcEffDateReasons = testData.get("TransactionEffectiveDate");
					Assertions.passTest("Cancel Policy Page", "The Calculated date is " + calcEffDateReasons);
					Assertions.verify(calcEffDateReasons.equals(cancelPolicyPage.cancellationEffectiveDate.getData()),
							true, "Cancel Policy Page",
							"The Cancellation Efffective Date " + calcEffDateReasons + " displayed is verified", false,
							false);
				}
			} else {
				for (int i = 0; i < 12; i++) {
					int dataValuei = i;
					testData = data.get(dataValuei);
					cancelPolicyPage.cancelReasonArrow.scrollToElement();
					cancelPolicyPage.cancelReasonArrow.click();
					String cancelReasoni = cancelPolicyPage.cancelReasonOption
							.formatDynamicPath(testData.get("CancellationReason")).getData();
					cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason"))
							.scrollToElement();
					cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
					Assertions.passTest("Cancel Policy Page", "The Cancel Reason Selected is " + cancelReasoni);

					// Asserting the Cancellation effective Date
					String calcEffDateReasons = testData.get("CancellationEffectiveDate");
					Assertions.passTest("Cancel Policy Page", "The Calculated date is " + calcEffDateReasons);
					Assertions.verify(calcEffDateReasons.equals(cancelPolicyPage.cancellationEffectiveDate.getData()),
							true, "Cancel Policy Page",
							"The Cancellation Efffective Date " + calcEffDateReasons + " displayed is verified", false,
							false);
				}
			}

			// Entering 16 as Days before NOC and select Non Payment as a reason
			Assertions.addInfo("Cancel Policy Page",
					"Entering 16 as Days before NOC and select Non Payment as the reason");
			testData = data.get(dataValue1);
			cancelPolicyPage.daysBeforeNOC.scrollToElement();
			cancelPolicyPage.daysBeforeNOC.setData(testData.get("Cancellation_DaysBeforeNOC"));
			Assertions.passTest("Cancel Policy Page",
					"Cancellation Days Before NOC entered is " + testData.get("Cancellation_DaysBeforeNOC"));
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).scrollToElement();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			Assertions.passTest("Cancel Policy Page",
					"The Cancel Reason Selected is " + testData.get("CancellationReason"));

			// Asserting Warning Message
			Assertions.addInfo("Cancel Policy Page",
					"Asserting Warning Message when the reason is Non Payment of premium");
			Assertions.verify(cancelPolicyPage.reasonWarningMessage.checkIfElementIsDisplayed(), true,
					"Cancel Policy Page",
					"The Warning message " + cancelPolicyPage.reasonWarningMessage.getData() + " displayed is verified",
					false, false);

			// enter legal notice wording
			cancelPolicyPage.legalNoticeWording.scrollToElement();
			cancelPolicyPage.legalNoticeWording.setData(testData.get("Cancellation_LegalNoticeWording"));

			// click on next
			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();

			// click on complete transaction
			cancelPolicyPage.completeTransactionButton.scrollToElement();
			cancelPolicyPage.completeTransactionButton.click();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page Loaded Successfully", false, false);

			// Click on remove pending NOC
			Assertions.verify(policySummaryPage.removePendingNOCLink.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "Remove Pending NOC link displayed is verified", false, false);
			policySummaryPage.removePendingNOCLink.scrollToElement();
			policySummaryPage.removePendingNOCLink.click();

			policySummaryPage.removePendingNOCBtn.waitTillPresenceOfElement(60);
			policySummaryPage.removePendingNOCBtn.waitTillVisibilityOfElement(60);
			policySummaryPage.removePendingNOCBtn.click();
			policySummaryPage.removePendingNOCBtn.waitTillInVisibilityOfElement(60);

			Assertions.verify(policySummaryPage.removePendingNOCLink.checkIfElementIsPresent(), false,
					"Policy Summary Page", "Remove Pending NOC link Removed is verified", false, false);

			// go to home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Find quote with account name
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterAccountOption.click();
			homePage.findAccountNamedInsured.setData(insuredName);
			homePage.findBtnAccount.waitTillVisibilityOfElement(60);
			homePage.findBtnAccount.scrollToElement();
			homePage.findBtnAccount.click();
			Assertions.passTest("Home Page ", "Searched the Policy by Account Name successfully");

			// Asserting the policy status
			Assertions.addInfo("Policy Summary Page", "Asserting the Policy Status");
			Assertions.verify(policySummaryPage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Policy status is " + policySummaryPage.policyStatus.getData(), false, false);

			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 83", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 83", "Executed Successfully");
			}
		}
	}
}
