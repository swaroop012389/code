/** Program Description: Verifying the following
 * 1)Verifying the PB Endorsement is deleted message
 * 2)Check the Green endorsement functionality for NAHO product with Premium calculation
 *
 *  Author			   : Sowndarya NH
 *  Date of Creation   :31/8/2023
 **/
package com.icat.epicenter.test.naho.regression.ISPNB;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC006_PNBPB006 extends AbstractNAHOTest {

	public TC006_PNBPB006() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBPB006.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the pages
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();

		// Initializing the variables
		String quoteNumber;
		int quoteLen;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
		String windPremiumQuote1;
		String aopPremiumQuote1;
		String glPremiumQuote1;
		String windPremiumQuote2;
		String aopPremiumQuote2;
		String glPremiumQuote2;
		boolean isTestPassed = false;

		try {
			// Creating New Account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account Created successfully");

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

			// Verifying the default value of Green upgrades is No
			Assertions.addInfo("Scenario 01", "Verifying the default value of Green upgrades is No");
			Assertions.verify(createQuotePage.greenUpgradesData.getData(), testData.get("GreenUpgrades"),
					"Create Quote Page", "The Greeen Upgrades default value "
							+ createQuotePage.greenUpgradesData.getData() + " is displayed",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Entering Quote Details
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Click on Continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Adding Policy fee and Inspection fee
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is 1 : " + quoteNumber);

			// Click on Override Premium link
			accountOverviewPage.overridePremiumLink.scrollToElement();
			accountOverviewPage.overridePremiumLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Override premium link");

			// Get the windpremium,aop premium and gl premium values
			Assertions.verify(overridePremiumAndFeesPage.cancelButton.checkIfElementIsDisplayed(), true,
					"Override Premium and Fees Page", "Override Premium and Fees page loaded successfully", false,
					false);
			windPremiumQuote1 = overridePremiumAndFeesPage.originalWindPremium.getData().replace("$", "").replace(",",
					"");
			aopPremiumQuote1 = overridePremiumAndFeesPage.originalAopPremium.getData().replace("$", "").replace(",",
					"");
			glPremiumQuote1 = overridePremiumAndFeesPage.originalGlPremium.getData().replace("$", "").replace(",", "");

			// Print the windpremium,aop premium and gl premium values
			Assertions.addInfo("Scenario 02", "Printing the windpremium,aop premium and gl premium values for Quote 1");
			Assertions.passTest("Override Premium and Fees Page",
					"Wind Premium for Quote 1 " + "$" + windPremiumQuote1);
			Assertions.passTest("Override Premium and Fees Page", "Aop Premium for Quote 1 " + "$" + aopPremiumQuote1);
			Assertions.passTest("Override Premium and Fees Page", "GL Premium for Quote 1 " + "$" + glPremiumQuote1);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// click on cancel button
			overridePremiumAndFeesPage.cancelButton.scrollToElement();
			overridePremiumAndFeesPage.cancelButton.click();
			Assertions.passTest("Override Premium and Fees Page", "Clicked on Cancel button");

			// click on edit deductibles and limits
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit deductibles and limits");

			// click on override
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			// Select Green upgrades as Yes
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			testData = data.get(data_Value2);
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);

			// click on get a quote
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on Get a quote button");

			// click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Get the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is 2 : " + quoteNumber);

			// Click on Override Premium link
			accountOverviewPage.overridePremiumLink.scrollToElement();
			accountOverviewPage.overridePremiumLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Override premium link");

			// Get the windpremium,aop premium and gl premium values for quote2
			Assertions.verify(overridePremiumAndFeesPage.cancelButton.checkIfElementIsDisplayed(), true,
					"Override Premium and Fees Page", "Override Premium and Fees page loaded successfully", false,
					false);
			windPremiumQuote2 = overridePremiumAndFeesPage.originalWindPremium.getData().replace("$", "").replace(",",
					"");
			System.out.println(windPremiumQuote2);
			aopPremiumQuote2 = overridePremiumAndFeesPage.originalAopPremium.getData().replace("$", "").replace(",",
					"");
			glPremiumQuote2 = overridePremiumAndFeesPage.originalGlPremium.getData().replace("$", "").replace(",", "");

			// calculation of Wind Premium of Quote2 = Wind Premium of Quote 1 +[Wind
			// Premium of Quote 1 * 15%]
			double calcWindPremiumQuote2 = Double.parseDouble(windPremiumQuote1)
					+ (Double.parseDouble(windPremiumQuote1) * 15 / 100);

			// calculation of AOP Premium of Quote2 = AOP Premium of Quote 1 +[AOP Premium
			// of Quote 1 * 15%]
			double calcAopPremiumQuote2 = Double.parseDouble(aopPremiumQuote1)
					+ (Double.parseDouble(aopPremiumQuote1) * 15 / 100);

			// Verify the calculated and actual windpremium,aop premium and gl premium
			// values for quote2
			Assertions.addInfo("Scenario 03",
					"Verify the calculated and actual windpremium,aop premium and gl premium values for Quote 2");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(windPremiumQuote2), 2)
					- Precision.round(calcWindPremiumQuote2, 2)), 2) < 1) {
				Assertions.passTest("Override Premium and Fees Page", "Calculated Wind Premium value for Quote 2 :   "
						+ "$" + Precision.round(calcWindPremiumQuote2, 2));
				Assertions.passTest("Override Premium and Fees Page",
						"Actual Wind Premium value for Quote 2 : " + "$" + windPremiumQuote2);
			} else {
				Assertions.passTest("Account Overview Page",
						"The Difference between actual and calculated Wind premium is more than 0.5");
			}

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(aopPremiumQuote2), 2)
					- Precision.round(calcAopPremiumQuote2, 2)), 2) < 1) {
				Assertions.passTest("Override Premium and Fees Page", "Calculated AOP Premium value for Quote 2 :   "
						+ "$" + Precision.round(calcAopPremiumQuote2, 2));
				Assertions.passTest("Override Premium and Fees Page",
						"Actual AOP Premium value for Quote 2 : " + "$" + aopPremiumQuote2);
			} else {
				Assertions.passTest("Account Overview Page",
						"The Difference between actual and calculated AOP premium is more than 0.5");
			}

			Assertions.verify(glPremiumQuote2, glPremiumQuote1, "Override Premium and Fees Page",
					"The GL Premium for Quote 2 is " + glPremiumQuote2, false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// click on cancel button
			overridePremiumAndFeesPage.cancelButton.scrollToElement();
			overridePremiumAndFeesPage.cancelButton.click();
			Assertions.passTest("Override Premium and Fees Page", "Override Premium and Fees Page loaded successfully");

			// Binding the quote and creating the policy
			testData = data.get(data_Value1);
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

			// Clicking on answer no button
			underwritingQuestionsPage.answerNoToAllQuestions.waitTillVisibilityOfElement(60);
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.answerNoToAllQuestions.scrollToElement();
			underwritingQuestionsPage.answerNoToAllQuestions.click();

			// Clicking on save button
			underwritingQuestionsPage.saveButton.scrollToElement();
			underwritingQuestionsPage.saveButton.click();

			// Entering bind details
			requestBindPage.enterBindDetailsNAHO(testData);

			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			accountOverviewPage.openReferralLink.checkIfElementIsDisplayed();
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();

			// Approve Referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(approveDeclineQuotePage.approveButton.checkIfElementIsDisplayed(), true,
					"Approve Decline Quote Page", "Approve Decline Quote page loaded successfully", false, false);
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Approve Decline Quote Page", "Bind Request approved successfully");

			// Validating the premium amount
			policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is "
							+ policySummaryPage.policyNumber.getData(),
					false, false);

			// Click on PB endorsement
			policySummaryPage.endorsePB.waitTillVisibilityOfElement(60);
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			policySummaryPage.endorsePB.waitTillInVisibilityOfElement(60);

			// Click on Edit Location or Building Information
			endorsePolicyPage.endorsementEffDate.waitTillPresenceOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillVisibilityOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillElementisEnabled(60);
			testData = data.get(data_Value2);
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			endorsePolicyPage.waitTime(3);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillPresenceOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillElementisEnabled(60);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillButtonIsClickable(60);
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();

			// Update dwelling address
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);

			// Review and continue create quote page
			createQuotePage.scrollToBottomPage();
			createQuotePage.continueEndorsementButton.waitTillVisibilityOfElement(60);
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.waitTillButtonIsClickable(60);
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}
			if (createQuotePage.override.checkIfElementIsPresent()
					&& createQuotePage.override.checkIfElementIsDisplayed()) {
				createQuotePage.override.scrollToElement();
				createQuotePage.override.click();
			}
			// continue with endorsement the endorsement
			endorsePolicyPage.nextButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// click on continue
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Assert quote deleted message
			endorsePolicyPage.deleteButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.deleteButton.scrollToElement();
			endorsePolicyPage.deleteButton.click();

			Assertions.addInfo("Scenario 04", "Verifying the Endorsement quote deleted message");
			Assertions.verify(
					policySummaryPage.endorsementDeletedWarningMsg.getData()
							.contains("endorsement quote has been deleted"),
					true, "Policy Summary Page", "Endorsement deleted warning message is displayed", false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Adding code for IO-18763
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy Link");

			if (policySummaryPage.expaacMessage.checkIfElementIsPresent()
					&& policySummaryPage.expaacMessage.checkIfElementIsDisplayed()) {
				Assertions.verify(policySummaryPage.expaacMessage.checkIfElementIsDisplayed(), true,
						"Policy Renewal Page", policySummaryPage.expaacMessage.getData() + " Message verified", false,
						false);

				// Go to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home page loaded successfully", false, false);

				// clicking on expaac link in home page
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.click();
				Assertions.passTest("Home Page", "Clicked on Expaac Link");

				// entering expaac data
				testData = data.get(data_Value1);
				Assertions.verify(expaccInfoPage.policyNumber.checkIfElementIsDisplayed(), true, "Expacc Info Page",
						"Update Expaac Data page loaded successfully", false, false);
				expaccInfoPage.enterExpaccInfo(testData, policyNumber);
				Assertions.passTest("Expacc Info Page", "Expaac Data updated successfully");

				// Go to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home page loaded successfully", false, false);

				// Find the policy by entering policy Number
				homePage.searchPolicy(policyNumber);
				Assertions.passTest("Home Page", "Policy Number : " + policyNumber + " searched successfully");

				// Click on Renew Policy Hyperlink
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();
				Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy Link");

				// click on continue button in Renewal building review page
				if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
						&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
					policyRenewalPage.continueRenewal.scrollToElement();
					policyRenewalPage.continueRenewal.click();
					Assertions.passTest("Renewal Building Review Page", "Clicked on Continue");
				}
			}

			// Asserting status on renewal quote
			Assertions.addInfo("Scenario 05", "Verifying Renewal quote reffered status and notebar message");
			Assertions.verify(accountOverviewPage.referredStatus.getData(), "Referred", "Account Overview Page",
					"Renewal quote status is " + accountOverviewPage.referredStatus.getData() + " When Roof Age is "
							+ testData.get("L1D1-DwellingYearBuilt") + " and Roof Cladding is "
							+ testData.get("L1D1-DwellingRoofCladding") + " while renewal is processed",
					false, false);
			accountOverviewPage.waitTime(3);
			Assertions.verify(accountOverviewPage.noteBarMessage.checkIfElementIsDisplayed(), true,
					"Account Overview Page", accountOverviewPage.noteBarMessage.getData() + " is displayed in note bar",
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC006 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC006 ", "Executed Successfully");
			}
		}
	}
}
