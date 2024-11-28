/** Program Description: As a Producer, check if the EQB and UL premium is displayed and added in the Premium total on the Referral quote page
 *  Author			   : Pavan Mule
 *  Date of Creation   : 05/03/2024
 **/
package com.icat.epicenter.test.naho.regression.NSPNB;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PremiumAdjustmentRequestPage;
import com.icat.epicenter.pom.PremiumReliefDecisionPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBNCTC006_GEN extends AbstractNAHOTest {

	public PNBNCTC006_GEN() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSPNB/NC006.xls";
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
		PremiumAdjustmentRequestPage premiumAdjustmentRequestPage = new PremiumAdjustmentRequestPage();
		PremiumReliefDecisionPage premiumReliefDecisionPage = new PremiumReliefDecisionPage();
		ReferralPage referralPage = new ReferralPage();
		LoginPage loginPage = new LoginPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();

		// Variables
		String quoteNumber;
		int data_Value1 = 0;
		String originalPremium;
		String windPremium;
		String aopPremium;
		String liabilityPremium;
		String eqBreakPremium;
		String utilLinePremium;
		String offeredApprovedPremium;
		double sumOfAopGLWindEqBreakandUtilLine;
		String latestPremium;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// Creating New Account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account ", "New Account created successfully");

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
				Assertions.passTest("Prior Loss Page", "Prior Loss Details Entered successfully");
			}

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Below code adding because of quote is referring because of modeling service
			// down message
			if (referQuotePage.referQuote.checkIfElementIsPresent()
					&& referQuotePage.referQuote.checkIfElementIsDisplayed()) {
				referQuotePage.contactEmail.scrollToElement();
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.scrollToElement();
				referQuotePage.referQuote.click();
				Assertions.passTest("Refer Quote Page", "Quote referred successfully");
				quoteNumber = referQuotePage.quoteNumberforReferral.getData();
				Assertions.passTest("Refer Quote Page", "The Quote Number is " + quoteNumber);

				// SignOut as Producer
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Home Page", "Logged out as Producer successfully");

				// Sign in as USM
				loginPage.refreshPage();
				Assertions.verify(loginPage.signInButton.checkIfElementIsDisplayed(), true, "Login Page",
						"Login page loaded successfully", false, false);
				loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
				Assertions.passTest("Home Page", "Logged in as USM Successfully");

				// searching the quote number in grid and clicking on the quote number
				// link
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home page loaded successfully", false, false);

				// homePage.searchReferral(quoteNumber);
				homePage.searchQuote(quoteNumber);
				Assertions.passTest("Home Page", "Searched Submitted Quote" + quoteNumber + " successfullly");

				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();

				// approving referral
				Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
						"Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();

				// click on approve in Approve Decline Quote page
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");

				// SignOut as USM
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Home Page", "Logged out as USM successfully");

				// Sign in as Producer
				loginPage.refreshPage();
				loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
				Assertions.passTest("Home Page", "Logged in as Producer successfully");

				// Handling the pop up
				homePage.enterPersonalLoginDetails();

				// Search for quote
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuoteByProducer(quoteNumber);
				Assertions.passTest("Home Page", "Searched the Quote " + quoteNumber + " sucessfully");

			}
			// Ended

			// Getting Quote number
			Assertions.verify(
					accountOverviewPage.requestBind.checkIfElementIsPresent()
							&& accountOverviewPage.requestBind.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number : " + quoteNumber);

			// Getting premium value from account overview page
			originalPremium = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");

			// Verifying the presence of request premium change link on account overview
			// page
			Assertions.addInfo("Scenario 01", "Verifying presence of premium change link");
			Assertions.verify(accountOverviewPage.requestPremiumChangeLink.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Premium Change link displayed successfully", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			accountOverviewPage.requestPremiumChangeLink.scrollToElement();
			accountOverviewPage.requestPremiumChangeLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on request premium change link successfully");

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
			Assertions.addInfo("Scenario 02", "Asserting and Verifying referral message");
			Assertions.verify(
					accountOverviewPage.requestPremiumChangeReferralMsg.getData()
							.contains("Thank you for your referral"),
					true, "Account Overview Page",
					"The Referral messgae is " + accountOverviewPage.requestPremiumChangeReferralMsg.getData(), false,
					false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Logout as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home  Page ", "SignOut as producer successfully");

			// Login as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page ", "Logged in as USM Successfully");

			// Search referred quote
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote searched successfully");

			// Click on open referral(Here quote is referring because of changing the
			// premium in request premium page
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();
			Assertions.passTest("Account Overview Page", "Clicked on open referral link successfully");

			// Click on approve/decline button
			referralPage.approveOrDeclineRequest.scrollToElement();
			referralPage.approveOrDeclineRequest.click();
			Assertions.passTest("Referral Page", "Clicked on Approve/Decline Request button successfully");

			// Verifying the presence of EQ break premium and utilline premium in premium
			// relief
			// decision page, when eq break and utilline added
			Assertions.addInfo("Scenario 03",
					"Verifying the presence of EQ premium label and value in premium relief decision page, when eq deductible added");
			Assertions
					.verify(premiumReliefDecisionPage.eqBreakPremiumLable.checkIfElementIsDisplayed(), true,
							"Premium Relief Decision Page", "The EQ break Premium lable is "
									+ premiumReliefDecisionPage.eqBreakPremiumLable.getData() + " displayed",
							false, false);
			Assertions
					.verify(premiumReliefDecisionPage.eqBreakPremiumValue.checkIfElementIsDisplayed(), true,
							"Premium Relief Decision Page", "The EQ Break Premium value is "
									+ premiumReliefDecisionPage.eqBreakPremiumValue.getData() + " displayed",
							false, false);
			Assertions
					.verify(premiumReliefDecisionPage.utilLinePremiumLable.checkIfElementIsDisplayed(), true,
							"Premium Relief Decision Page", "The Util line premium lable is "
									+ premiumReliefDecisionPage.utilLinePremiumLable.getData() + " displayed",
							false, false);
			Assertions
					.verify(premiumReliefDecisionPage.utilLinePremiumValue.checkIfElementIsDisplayed(), true,
							"Premium Relief Decision Page", "The Util line premium Value is "
									+ premiumReliefDecisionPage.utilLinePremiumValue.getData() + " displayed",
							false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Verifying the target premium updated on premium relief decision page
			Assertions.addInfo("Scenario 04", "Verifying the target premium updated on premium relief decision page");
			Assertions.verify(
					premiumReliefDecisionPage.requestedPremium.getData().replace("$", "").replace(",", "").contains(
							testData.get("PremiumAdjustment_TargetPremium")),
					true, "Premium Relife Decision Page",
					"The target premium updated on premium relief decision page, The requested premium is "
							+ premiumReliefDecisionPage.requestedPremium.getData(),
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Enter premium relief decision details
			premiumReliefDecisionPage.enterPremiumReliefDetailsNAHO(testData);
			Assertions.passTest("Premium Relife Decision Page", "Premium relief details entered successfully");

			// Getting wind, aop GL premium eqbreak premium and utilline premium
			windPremium = premiumReliefDecisionPage.windPremium.getData();
			aopPremium = premiumReliefDecisionPage.aopPremium.getData().replace("$", "").replace(",", "");
			liabilityPremium = premiumReliefDecisionPage.orgGLPremium.getData().replace("$", "").replace(",", "");
			eqBreakPremium = premiumReliefDecisionPage.eqBreakPremiumValue.getData().replace("$", "").replace(",", "");
			offeredApprovedPremium = premiumReliefDecisionPage.offeredOrApprovedPremium.getData().replace(",", "");
			utilLinePremium = premiumReliefDecisionPage.utilLinePremiumValue.getData().replace("$", "").replace(",",
					"");

			// Calculating Sum of the Original AOP, Wind and GL/Liability Premium and
			// EQBreak and utilline
			// (AOp+Wind+GL+EQBreak+utilline)
			sumOfAopGLWindEqBreakandUtilLine = (Double.parseDouble(windPremium) + Double.parseDouble(aopPremium)
					+ Double.parseDouble(liabilityPremium) + Double.parseDouble(eqBreakPremium)
					+ Double.parseDouble(utilLinePremium));

			// Verifying Offered/Approved Premium is equal to sum Of Aop,GL,Wind,EQ break
			// and utilline
			Assertions.addInfo("Scenario 05",
					"Verifying Offered/Approved Premium is equal to sum Of Aop,GL,Wind eqbreak and utilline");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(offeredApprovedPremium), 2)
					- Precision.round(sumOfAopGLWindEqBreakandUtilLine, 2)), 2) < 0.01) {
				Assertions.passTest("Referral Approve/Decline Page", "Offered Or Approved Premium: " + "$"
						+ +Precision.round(Double.parseDouble(offeredApprovedPremium), 2));
				Assertions.passTest("Referral Approve/Decline Page", "Sum Of Aop, GL, Wind, EqBreak and Utilline: "
						+ "$" + Precision.round(sumOfAopGLWindEqBreakandUtilLine, 2));
			} else {
				Assertions.verify(Precision.round(Double.parseDouble(offeredApprovedPremium), 2),
						Precision.round(sumOfAopGLWindEqBreakandUtilLine, 2), "Referral Approve/Decline Page",
						"The Actaul and Calculated Wind,AOP,GL,EqBreak and Utilline Values are not the Same", false,
						false);
			}
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Click on approve button
			premiumReliefDecisionPage.approveBtn.scrollToElement();
			premiumReliefDecisionPage.approveBtn.click();
			Assertions.passTest("Premium Relief Decision Page", "The referred quote approved successfully");

			// Open the referred quote
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Referred quote searched successfully");

			// Getting updated premium from account overview page
			Assertions.addInfo("Scenario 06",
					"Comparing and Asserting latest premium value from account overview page after reoffer approved premium");
			latestPremium = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(latestPremium), 2)
					- Precision.round(Double.parseDouble(offeredApprovedPremium), 2)), 2) < 0.01) {
				Assertions.passTest("Account Overview Page",
						"Original Premium: " + "$" + Precision.round(Double.parseDouble(originalPremium), 2));
				Assertions.passTest("Account Overview Page", "Offered Or Approved Premium: " + "$"
						+ Precision.round(Double.parseDouble(offeredApprovedPremium), 2));
				Assertions.passTest("Account Overview Page",
						"Latest Premium: " + "$" + Precision.round(Double.parseDouble(latestPremium), 2));
			} else {
				Assertions.verify(Precision.round(Double.parseDouble(latestPremium), 2),
						Precision.round(Double.parseDouble(offeredApprovedPremium), 2), "Account Overview Page",
						"Offered or approved premium and latest premium are not matching", false, false);
			}
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Click on sign out button
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBNCTC006 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBNCTC006 ", "Executed Successfully");
			}
		}
	}

}
