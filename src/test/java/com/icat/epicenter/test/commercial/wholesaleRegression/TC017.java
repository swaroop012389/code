/** Program Description: Create an AOP Policy with additional interests.As a Producer, check if the Request Premium Change button is available and is working as expected on Renewal.
 *  Author			   : Yeshashwini TA
 *  Date of Creation   : 11/21/2019
 **/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EditAdditionalInterestInformationPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.GLInformationPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.QuoteDetailsPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC017 extends AbstractCommercialTest {

	public TC017() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID017.xls";
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
		QuoteDetailsPage quoteDetailsPage = new QuoteDetailsPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyrenewalPage = new PolicyRenewalPage();
		LoginPage loginPage = new LoginPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();

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

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			String quoteTotalPremium = accountOverviewPage.totalPremiumValue.getData().replace("$", "").replace(",",
					"");
			Assertions.passTest("Account Overview Page", "Quote Total Premium :  " + quoteNumber);
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
			accountOverviewPage.viewPrintFullQuoteLink.waitTillPresenceOfElement(60);
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View or print full quote");
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View Or Print Full Quote Page", "View Or Print Full Quote Page loaded successfully", false, false);

			// Asserting entered Details from View or print full quote page
			Assertions.addInfo("Scenario 01", "Asserting entered Details from View or print full quote page");
			String aiType1 = viewOrPrintFullQuotePage.aiDetails.formatDynamicPath(4).getData();
			Assertions.verify(aiType1.contains(testData.get("1-AIType")), true, "View Or Print Full Quote Page",
					"The Additional Interest Type Selected is " + aiType1 + " displayed is verified", false, false);
			String aiName1 = viewOrPrintFullQuotePage.aiDetails.formatDynamicPath(5).getData();
			Assertions.verify(aiName1.contains(testData.get("1-AIName")), true, "View Or Print Full Quote Page",
					"The Name Entered is " + aiName1 + " displayed is verified", false, false);
			String aiAddress1 = viewOrPrintFullQuotePage.aiDetails.formatDynamicPath(6).getData();
			Assertions.verify(aiAddress1.contains(testData.get("1-AIAddr1")), true, "View Or Print Full Quote Page",
					"The Address Entered is " + aiAddress1 + " displayed is verified", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
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
			requestBindPage.waitTime(3);

			// IO-21673 Started Jumanji
			String grandTotValue = requestBindPage.grandTotal.getData().replace("Grand Total: $", "").replace(",", "");
			double grandTotalValue = Double.parseDouble((grandTotValue));

			Assertions.addInfo("Scenario 1",
					"Verifying Account overview Page Quote Total Premium and Request Bind Page Grand Total Value");
			if (Precision.round(Math.abs(
					Precision.round(Double.parseDouble(quoteTotalPremium), 2) - Precision.round(grandTotalValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Quote Total Premium on Account overview Page is : " + "$" + quoteTotalPremium);
				Assertions.passTest("Account Overview Page",
						"Grand Total Value on Request Bind Page is : " + "$" + grandTotalValue);
			} else {
				Assertions.verify(quoteTotalPremium, grandTotalValue, "Account Overview Page",
						"The Difference between Quote Total Premium and Grand Total Value is more than 0.05", false,
						false);
			}
			// Verifying the verbiage of Grand Total on Request Bind Page/
			Assertions.verify(requestBindPage.grandTotal.getData().contains("Grand Total:"), true, "Request Bind Page",
					"Quote Premium changed to Grand Total on Request Bind Page", false, false);
			Assertions.addInfo("Scenario 1", "Scenario 1 is Ended");
			// IO-21673 Ended Jumanji

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
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

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
			Assertions.addInfo("Scenario 02", "Asserting the AI details from policy snapshot page");
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
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Click on close button
			quoteDetailsPage.closeBtn.scrollToElement();
			quoteDetailsPage.closeBtn.click();
			Assertions.passTest("Quote Details Page", "Clicked on close button successfully");

			// Click on renewal link
			policySummarypage.renewPolicy.scrollToElement();
			policySummarypage.renewPolicy.click();

			// Add expac details
			testData = data.get(dataValue1);
			if (policyrenewalPage.addExpaccButton.checkIfElementIsPresent()
					&& policyrenewalPage.addExpaccButton.checkIfElementIsDisplayed()) {
				// Click on Home Button
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();

				// Click on Expacc
				homePage.scrollToBottomPage();
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.click();

				// Enter expacc details
				Assertions.verify(expaccInfoPage.submit.checkIfElementIsDisplayed(), true, "Expacc Info Page",
						"Expacc details page loaded successfully", false, false);
				expaccInfoPage.enterExpaccInfo(testData, policyNumber);
				Assertions.passTest("Expacc Info Page", "Expacc Details entered successfully");

				// Click on Home Button
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchPolicy(policyNumber);

				// Renew policy and release to producer
				policySummarypage.renewPolicy.waitTillVisibilityOfElement(60);
				policySummarypage.renewPolicy.scrollToElement();
				policySummarypage.renewPolicy.click();
				Assertions.passTest("Policy Summary Page", "Clicked on renew policy link");

				policyrenewalPage.continueRenewal.scrollToElement();
				policyrenewalPage.continueRenewal.click();

			}

			// Click on yes button
			if (accountOverviewPage.yesButton.checkIfElementIsPresent()
					&& accountOverviewPage.yesButton.checkIfElementIsDisplayed()) {
				accountOverviewPage.yesButton.scrollToElement();
				accountOverviewPage.yesButton.click();
			}

			// Getting renewal quote number1
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account overview page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData().replace("(", "").replace(")", "");
			Assertions.passTest("Account Overview Page", "Renewal quote number1 is " + quoteNumber);

			// commenting this below code as application does not have request premium
			// change link and refer IO-21797
			/*
			 * // Verifying presence of request premium change button on renewal quote
			 * Assertions.addInfo("Scenario 03",
			 * "Verifying presence of request premium change link when premium is >$3000");
			 * Assertions.verify(accountOverviewPage.requestPremiumChangeLink.
			 * checkIfElementIsDisplayed(), true, "Account Overview Page",
			 * "Request premium change link is displayed ", false, false);
			 * Assertions.addInfo("Scenario 03", "Scenario 03 Ended");
			 */

			// click on release renewal to producer button
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Renewal to Producer button");

			// Logout as usm
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Clicked on signout button successfully");

			// Login as producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			homePage.enterPersonalLoginDetails();
			Assertions.passTest("Login Page", "Logged in as Producer successfully");
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);
			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.passTest("Home Page", "Quote Number " + quoteNumber + " searched successfully");
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// Verifying presence of request premium change button on renewal quote
			Assertions.addInfo("Scenario 04",
					"Verifying presence of request premium change link when premium is >$3000");
			Assertions.verify(accountOverviewPage.requestPremiumChangeLink.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Request premium change link is displayed ", false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Asserting premium Before changing the premium
			Assertions.addInfo("Scenario 05", "Asserting the premium value before changing the premium");
			Assertions.verify(accountOverviewPage.premiumValue.checkIfElementIsDisplayed(), true,
					"Account overview page",
					"The premium before changing is " + accountOverviewPage.premiumValue.getData(), false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Click on request premium change link
			accountOverviewPage.requestPremiumChangeLink.scrollToElement();
			accountOverviewPage.requestPremiumChangeLink.click();
			Assertions.passTest("Account overview page", "Clicked on request premium change link successfully");

			// Enter premium details
			accountOverviewPage.requestPremiumChanges(testData);
			Assertions.passTest("Request premium page", "Premium details entered successfully");

			// Verifying message when premium changed from original premium+10000 the
			// Thank you for your referral. Your Underwriting contact has been notified of
			// this request and will get back to you shortly. If you have any questions
			// about this request, please reference the following number
			Assertions.addInfo("Scenario 06", "Verifying and asserting message ");
			Assertions.verify(accountOverviewPage.dwellingSuccessfullyDeletedMsg.getData().contains(
					"Your Underwriting contact has been notified of this request and will get back to you shortly"),
					true, "Account overview page",
					"The message is " + accountOverviewPage.dwellingSuccessfullyDeletedMsg.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Log out as producer successfully");

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
			accountOverviewPage.openReferralLink.waitTillVisibilityOfElement(60);
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// approving referral
			if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
				referralPage.pickUp.scrollToElement();
				referralPage.pickUp.click();
			}
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral approved successfully");

			// Asserting referral message 'The referred quote has been approved. The system
			// sent a message to the Producer's email address.
			Assertions.addInfo("Scenario 07", "Asserting referral message when request premium changed");
			Assertions.verify(referralPage.referralCompleteMsg.checkIfElementIsDisplayed(), true, "Referral Page",
					"The referral Complete message " + referralPage.referralCompleteMsg.getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Click on home icon link
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// Asserting updated premium from renewal account overview page
			Assertions.addInfo("Scenario 08", "Asserting the Premium after changing the premium");
			Assertions.verify(accountOverviewPage.premiumValue.getData().replace("$", ""),
					testData.get("PremiumAdjustment_TargetPremium") + ".00", "Account Overview Page",
					"The updated premium is " + accountOverviewPage.premiumValue.getData(), false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 17", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 17", "Executed Successfully");
			}
		}
	}
}
