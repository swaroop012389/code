/** Program Description: As a Producer, check if the Request Premium Change button is available and is working as expected on Renewal.
 *  Author			   : Pavan Mule
 *  Date of Creation   : 04/03/2024
 **/
package com.icat.epicenter.test.naho.regression.ESBPNB;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PremiumAdjustmentRequestPage;
import com.icat.epicenter.pom.PremiumReliefDecisionPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBDETC003 extends AbstractNAHOTest {

	public PNBDETC003() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBPNB/DETC003.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		ReferralPage referralPage = new ReferralPage();
		LoginPage loginPage = new LoginPage();
		PremiumAdjustmentRequestPage premiumAdjustmentRequestPage = new PremiumAdjustmentRequestPage();
		PremiumReliefDecisionPage premiumReliefDecisionPage = new PremiumReliefDecisionPage();

		// Initializing the variables
		int dataValue1 = 0;
		String quoteNumber;
		String policyNumber;
		String windPremium;
		String aopPremium;
		String premium;
		String liabilityPremium;
		String offeredApprovedPremium;
		double sumOfAopGLandWind;
		Map<String, String> testData = data.get(dataValue1);
		boolean isTestPassed = false;

		try {
			// Creating New Account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering Zipcode
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling  Page",
					"Dwelling Page Loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling Details Entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Asserting Quote Number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Click on Request Bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Clicking on answer no button
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered successfully");

			// Entering bind details
			quoteNumber = requestBindPage.quoteNumber.getData();
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Details Entered successfully");

			// Asserting policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is : " + policyNumber, false, false);

			// Asserting Status of Policy
			Assertions.verify(policySummaryPage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy status is : " + policySummaryPage.policyStatus.getData(), false, false);

			// Click on renew policy link
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
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();
			}

			// Getting renewal quote
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1,
					(accountOverviewPage.quoteNumber.getData().length() - 1));
			Assertions.passTest("Account Overview Page", "Renewal Quote Number is " + quoteNumber);
			premium = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");

			// Click on release renewal producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on release renewal to producer button successfully");

			// Verifying the absence of request premium change link on account overview
			// page
			Assertions.addInfo("Scenario 01", "Verifying absence of premium change link");
			Assertions.verify(accountOverviewPage.requestPremiumChangeLink.checkIfElementIsPresent(), false,
					"Account Overview Page", "Premium Change link  not displayed successfully", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Logout as usm
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home  Page ", "SignOut as usm successfully");

			// Login as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page ", "Logged in as Producer Successfully");

			// Search renewal quote as producer
			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.passTest("Home Page", "Renewal quote searched successfully as producer");

			// Verifying the presence of request premium change link on account overview
			// page
			Assertions.addInfo("Scenario 02", "Verifying presence of premium change link as producer");
			Assertions.verify(accountOverviewPage.requestPremiumChangeLink.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Premium Change link displayed successfully", false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Click on request premium change link
			accountOverviewPage.requestPremiumChangeLink.scrollToElement();
			accountOverviewPage.requestPremiumChangeLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on rquest premium change link successfully");

			// Enter premium changes details
			premiumAdjustmentRequestPage.waitTime(1);
			Assertions.verify(premiumAdjustmentRequestPage.rpcUpdateBtn.checkIfElementIsDisplayed(), true,
					"Premium Adjustment Request Page", "Premium Adjustment Request Page loaded successfully", false,
					false);
			premiumAdjustmentRequestPage.requestPremiumChanges(testData);
			Assertions.passTest("Premium Adjustment Request Page", "Premium Adjustment Details entered successfully");

			// Asserting and verifying referral message,referral message is'Thank you for
			// your referral. Your Underwriting contact has been notified of this request
			// and will get back to you shortly. If you have any questions about this
			// request, please reference the following number:XX
			Assertions.addInfo("Scenario 03", "Asserting and Verifying referral message");
			Assertions.verify(
					accountOverviewPage.requestPremiumChangeReferralMsg.getData()
							.contains("Thank you for your referral"),
					true, "Account Overview Page",
					"The Referral messgae is " + accountOverviewPage.requestPremiumChangeReferralMsg.getData(), false,
					false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Logout as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home  Page ", "SignOut as producer successfully");

			// Login as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page ", "Logged in as USM Successfully");

			// searching the quote number in grid and clicking on the quote link
			Assertions.addInfo("Scenario 04", "Asserting the Type of referred quote is displayed as Renewal Prem Adj");
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"Home page loaded successfully", false, false);
			homePage.refreshUntilRenewalQuoteFound(quoteNumber);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Click on approve/decline button
			referralPage.approveOrDeclineRequest.scrollToElement();
			referralPage.approveOrDeclineRequest.click();
			Assertions.passTest("Referral Page", "Clicked on Approve/Decline Request button successfully");

			// Verifying the target premium updated on premium relief decision page
			Assertions.addInfo("Scenario 05", "Verifying the target premium updated on premium relief decision page");
			Assertions.verify(
					premiumReliefDecisionPage.requestedPremium.getData().replace("$", "").replace(",", "").contains(
							testData.get("PremiumAdjustment_TargetPremium")),
					true, "Premium Relife Decision Page",
					"The target premium updated on premium relief decision page, The requested premium is "
							+ premiumReliefDecisionPage.requestedPremium.getData(),
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Enter premium relief decision details
			premiumReliefDecisionPage.enterPremiumReliefDetailsNAHO(testData);
			Assertions.passTest("Premium Relife Decision Page", "Premium relief details entered successfully");

			// Getting wind, aop and GL premium
			windPremium = premiumReliefDecisionPage.windPremium.getData().replace("$", "").replace(",", "");
			aopPremium = premiumReliefDecisionPage.orgAopPremium.getData().replace("$", "").replace(",", "");
			liabilityPremium = premiumReliefDecisionPage.orgGLPremium.getData().replace("$", "").replace(",", "");
			offeredApprovedPremium = premiumReliefDecisionPage.offeredOrApprovedPremium.getData().replace(",", "");

			// Sum of the Original AOP, Wind and GL/Liability Premium (AOp+Wind+GL)
			sumOfAopGLandWind = (Double.parseDouble(windPremium) + Double.parseDouble(aopPremium)
					+ Double.parseDouble(liabilityPremium));
			Assertions.addInfo("Scenario 06", "Verifying Offered/Approved Premium is equal to sum Of Aop,GL and Wind");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(offeredApprovedPremium), 2)
					- Precision.round(sumOfAopGLandWind, 2)), 2) < 0.01) {
				Assertions.passTest("Referral Approve/Decline Page", "Offered Or Approved Premium: " + "$"
						+ Precision.round(Double.parseDouble(offeredApprovedPremium), 2));
				Assertions.passTest("Referral Approve/Decline Page",
						"Sum Of Aop GL and Wind: " + "$" + Precision.round(sumOfAopGLandWind, 2));
			} else {
				Assertions.verify(Precision.round(Double.parseDouble(offeredApprovedPremium), 2),
						Precision.round(sumOfAopGLandWind, 2), "Referral Approve/Decline Page",
						"The Actaul and Calculated Wind, AOP and GL Values are not the Same", false, false);
			}
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Click on decline button
			premiumReliefDecisionPage.denyBtn.scrollToElement();
			premiumReliefDecisionPage.denyBtn.click();
			Assertions.passTest("Premium Relief Decision Page", "The referred quote decline successfully");

			// Logout as usm
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home  Page ", "SignOut as usm successfully");

			// Login as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page ", "Logged in as Producer Successfully");

			// Search renewal quote as producer
			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.passTest("Home Page", "Renewal quote searched successfully as producer");

			// Verifying the Premium requested for change is NOT updated on the Account
			// Overview page as the requested referral was Declined.
			Assertions.addInfo("Scenario 07",
					"Verifying the Premium requested for change is NOT updated on the Account Overview page as the requested referral was Declined");
			if (Precision.round(
					Math.abs(Precision
							.round(Double.parseDouble(
									accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "")), 2)
							- Precision.round(Double.parseDouble(premium.replace("$", "").replace(",", "")), 2)),
					2) < 0.01) {
				Assertions.passTest("Account Overview Page",
						"The requested premium is not changed after decline the quote,The acctual premium is "
								+ accountOverviewPage.premiumValue.getData());
				Assertions.passTest("Account Overview Page", "The expected premium is $" + premium);
			} else {
				Assertions.verify(accountOverviewPage.premiumValue.getData(), premium, "Account Overview Page",
						"The deferrence between actual and ecpected premium is more then 0.01", false, false);
			}
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Click on sign out button
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBDETC003 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBDETC003 ", "Executed Successfully");
			}
		}
	}

}
