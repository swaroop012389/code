/** Program Description: 1. Verifying the sinkhole options in dropdown and sinkhole not available referral reason,Quote status is referred when renewal is created,EMPA Surcharge on Renewal quote $2,referral message as USM when Roof clading=tileorclay and yearbuilt>30 years and Hard stop message,prior loss link not available and Expired status afetr prior loss added on renewl quote as Producer
 * 						 2. Initiate an Endt1: NPB With Actual Exp date. , Endt2: PB With Actual Exp date and Endt3: Create Endt3 by changing the policy term to Mar 2023 and check the behavour.
 *  Author			   : John
 *  Date of Creation   : 05/26/2021
 **/
package com.icat.epicenter.test.naho.regression.NSPNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorseInspectionContactPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBFLTC005_RNL extends AbstractNAHOTest {

	public PNBFLTC005_RNL() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSPNB/FL005.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ReferralPage referralPage = new ReferralPage();
		LoginPage loginPage = new LoginPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ViewOrPrintFullQuotePage viewprOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		EndorseInspectionContactPage endorseInspectionContactPage = new EndorseInspectionContactPage();

		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		String namedStorm = "5%";
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// Create account as producer
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page ",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account ", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page ",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page ", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page ",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page ", "Dwelling details entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page ",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page ",
					"Create Quote Page loaded successfully", false, false);

			// IO-20810
			// Verifying A minimum Named Storm deductible of 5% will be set for all risks
			// located in Tri County microzone, the minimum named storm dedcutible
			Assertions.addInfo("Scenario 01",
					"Verify Named Storm Deductible and AOWH deductible minimum value and Sinkhole/CGCC are available");
			Assertions.verify(createQuotePage.namedStormData.getData(), namedStorm, "Create Quote Page",
					"A minimum Named Storm deductible of 5% will be set for all risks located in Tri County microzone, the minimum named storm dedcutible "
							+ createQuotePage.namedStormData.getData() + " is displayed verified",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
			// IO-20810 ended

			// "Asserting Override Sinkhole Values
			createQuotePage.sinkholeArrow.scrollToElement();
			createQuotePage.sinkholeArrow.click();
			Assertions.addInfo("Scenario 02", "Asserting Override Sinkhole Values");
			Assertions.verify(createQuotePage.sinkholeOption.formatDynamicPath("Sinkhole").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					createQuotePage.sinkholeOption.formatDynamicPath("Sinkhole").getData() + " is displayed", false,
					false);
			Assertions.verify(
					createQuotePage.sinkholeOption.formatDynamicPath("Catastrophic Ground Cover Collapse")
							.checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					createQuotePage.sinkholeOption.formatDynamicPath("Catastrophic Ground Cover Collapse").getData()
							+ " is displayed",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			createQuotePage.refreshPage();

			// Entering Create quote page Details
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page ", "Quote details entered successfully");

			// click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// getting Quote number1
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page ", "Account Overview Page loaded successfully", false, false);
			int quoteLen = accountOverviewPage.quoteNumber.getData().length();
			String quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page ", "Quote Number is " + quoteNumber);

			// Binding the quote and creating the policy
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

			// Clicking on answer no button
			underwritingQuestionsPage.answerNoToAllQuestions.waitTillVisibilityOfElement(60);
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page ", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.answerNoToAllQuestions.scrollToElement();
			underwritingQuestionsPage.answerNoToAllQuestions.click();
			Assertions.passTest("Underwriting Questions Page ",
					"Underwriting Questions selected as answer No To AllQuestions");

			// Clicking on save button
			underwritingQuestionsPage.saveButton.scrollToElement();
			underwritingQuestionsPage.saveButton.click();

			// Entering bind details
			testData = data.get(data_Value1);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page ",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Details Entered successfully");

			// Assert policy number
			String policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page ",
					"Policy Summary Page loaded successfully " + "Policy Number "
							+ policySummaryPage.policyNumber.getData(),
					false, false);

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

			// Scenario 1
			// assert quote status
			String renewalQuoteNumber = accountOverviewPage.quoteNumber.getData().substring(1,
					(accountOverviewPage.quoteNumber.getData().length() - 1));
			Assertions.passTest("Account Overview Page", "Renewal Quote Number is " + renewalQuoteNumber);
			Assertions.addInfo("Scenario 03", "Asserting Referred quote status when renewal is created");
			Assertions.verify(
					accountOverviewPage.referredStatus.checkIfElementIsPresent()
							&& accountOverviewPage.referredStatus.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "Quote Status is " + accountOverviewPage.referredStatus.getData(),
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Approve referral
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();

			// approving referral
			if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
				referralPage.pickUp.waitTillVisibilityOfElement(60);
				referralPage.pickUp.scrollToElement();
				referralPage.pickUp.click();
			}
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Approve Decline Quote Page", "Bind Request approved successfully");

			// go to home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Search renewal quote
			homePage.searchQuote(renewalQuoteNumber);

			// Click on release to producer button
			Assertions.verify(accountOverviewPage.internalRenewalStatus.getData(), "Internal Renewal",
					"Account Overview Page", "Renewal Quote is Approved", false, false);
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();

			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();

			// IO-20810
			// Verifying A minimum Named Storm deductible of 5% will be set for all risks
			// located in Tri County microzone, the minimum named storm dedcutible
			Assertions.addInfo("Scenario 04",
					"Verify Named Storm Deductible and AOWH deductible minimum value and Sinkhole/CGCC are available");
			Assertions.verify(createQuotePage.namedStormData.getData(), namedStorm, "Create Quote Page",
					"A minimum Named Storm deductible of 5% will be set for all risks located in Tri County microzone, the minimum named storm dedcutible "
							+ createQuotePage.namedStormData.getData() + " is displayed verified",
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");
			// IO-20810 Ended

			// Click on get quote
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// assert empa surcharge is $2 on renewal
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.addInfo("Scenario 05", "Asserting EMPA Surcharge on Renewal quote as $2");
			Assertions.verify(viewprOrPrintFullQuotePage.empaSurcharge.getData(), "$2.00", "View/Print Full Quote Page",
					"EMPA Surcharge for FL Renewal is " + viewprOrPrintFullQuotePage.empaSurcharge.getData(), false,
					false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Click on back button
			viewprOrPrintFullQuotePage.scrollToTopPage();
			viewprOrPrintFullQuotePage.waitTime(2);
			viewprOrPrintFullQuotePage.backButton.click();

			// Update year built to 1949
			testData = data.get(data_Value2);
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();
			dwellingPage.yearBuilt.waitTillVisibilityOfElement(60);
			dwellingPage.yearBuilt.scrollToElement();
			dwellingPage.yearBuilt.clearData();
			dwellingPage.waitTime(2);
			if (dwellingPage.continueWithUpdateBtn.checkIfElementIsPresent()
					&& dwellingPage.continueWithUpdateBtn.checkIfElementIsDisplayed()) {
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
			}
			dwellingPage.yearBuilt.appendData(testData.get("L1D1-DwellingYearBuilt"));
			dwellingPage.yearBuilt.tab();
			dwellingPage.scrollToBottomPage();
			dwellingPage.createQuote.click();
			if (dwellingPage.override.checkIfElementIsPresent() && dwellingPage.override.checkIfElementIsDisplayed()) {
				dwellingPage.override.waitTillPresenceOfElement(60);
				dwellingPage.override.scrollToElement();
				dwellingPage.override.click();
				dwellingPage.createQuote.scrollToElement();
				dwellingPage.createQuote.click();
			} else {
				// click on get a quote button in create quote page
				createQuotePage.getAQuote.waitTillVisibilityOfElement(60);
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();

				// Assert hard-stop message
				Assertions.addInfo("Scenario 06", "Asserting Hard Stop warning when Year Built is 1949");
				Assertions.verify(
						createQuotePage.floodCoverageError.checkIfElementIsPresent()
								&& createQuotePage.floodCoverageError.checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"Hard Stop Warning, " + createQuotePage.floodCoverageError.getData() + " is displayed for USM",
						false, false);
				Assertions.addInfo("Scenario 06", "Scenario 06 Ended");
			}
			// Click on previous button
			createQuotePage.previous.scrollToElement();
			createQuotePage.previous.click();

			// Update year built to Year-36 years
			testData = data.get(data_Value3);
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();
			dwellingPage.yearBuilt.waitTillVisibilityOfElement(60);
			dwellingPage.yearBuilt.scrollToElement();
			dwellingPage.yearBuilt.clearData();
			dwellingPage.waitTime(2);
			if (dwellingPage.continueWithUpdateBtn.checkIfElementIsPresent()
					&& dwellingPage.continueWithUpdateBtn.checkIfElementIsDisplayed()) {
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
			}
			dwellingPage.yearBuilt.appendData(testData.get("L1D1-DwellingYearBuilt"));
			dwellingPage.yearBuilt.tab();
			dwellingPage.scrollToBottomPage();
			dwellingPage.createQuote.click();

			// click on get a quote button in create quote page
			createQuotePage.getAQuote.waitTillVisibilityOfElement(60);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// Assert referral message for roof cladding
			Assertions.addInfo("Scenario 07",
					"Asserting Referral for USM when Roof cladding = Tile or Clay and Year Built > 30 years");
			Assertions.verify(
					referQuotePage.referralMessages
							.formatDynamicPath("ineligible due to the roof age").checkIfElementIsDisplayed(),
					true, "Refer Quote Page",
					"Referral message, " + referQuotePage.referralMessages
							.formatDynamicPath("ineligible due to the roof age").getData() + " is displayed",
					false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			testData = data.get(data_Value1);
			if (referQuotePage.referQuote.checkIfElementIsPresent()
					&& referQuotePage.referQuote.checkIfElementIsDisplayed()) {
				referQuotePage.contactName.setData(testData.get("ProducerName"));
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.scrollToElement();
				referQuotePage.referQuote.click();
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuote(renewalQuoteNumber);
			}

			// Getting Renewal Quote Number
			String renewalQuoteNo = accountOverviewPage.quoteNumber.getData().substring(1,
					(accountOverviewPage.quoteNumber.getData().length() - 1));
			Assertions.passTest("Account Overview Page", "Renewal Quote Number is " + renewalQuoteNo);
			if (accountOverviewPage.referredStatus.checkIfElementIsPresent()
					&& accountOverviewPage.referredStatus.checkIfElementIsDisplayed()) {
				accountOverviewPage.quoteReferred.formatDynamicPath("Referred").scrollToElement();
				accountOverviewPage.quoteReferred.formatDynamicPath("Referred").click();

				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();
				testData = data.get(data_Value1);

				// approving referral
				if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
					referralPage.pickUp.waitTillVisibilityOfElement(60);
					referralPage.pickUp.scrollToElement();
					referralPage.pickUp.click();
				}
				referralPage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Approve Decline Quote Page", "Bind Request approved successfully");
				referralPage.close.scrollToElement();
				referralPage.close.click();

			}
			testData = data.get(data_Value3);

			// Logout as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page ", "SignOut as USM successfully");

			// Login as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page ", "Logged in as Producer Successfully");
			homePage.enterPersonalLoginDetails();
			homePage.searchQuoteByProducer(renewalQuoteNo);

			// edit dwelling and click on create quote
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();
			dwellingPage.scrollToBottomPage();
			dwellingPage.createQuote.click();

			// click on get a quote button in create quote page
			createQuotePage.getAQuote.waitTillVisibilityOfElement(60);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// Assert referral message for roof cladding
			Assertions.addInfo("Scenario 08",
					"Asserting Hard Stop for producer when Roof cladding = Tile or Clay and Year Built > 30 years");
			Assertions.verify(
					createQuotePage.floodCoverageError.checkIfElementIsPresent()
							&& createQuotePage.floodCoverageError.checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Hard Stop Warning, " + createQuotePage.floodCoverageError.getData() + " is displayed for Producer",
					false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");
			createQuotePage.previous.scrollToElement();
			createQuotePage.previous.click();

			// go to home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Search renewal quote
			homePage.searchQuoteByProducer(renewalQuoteNo);

			// Click on issue quote button
			accountOverviewPage.issueQuoteButton.scrollToElement();
			accountOverviewPage.issueQuoteButton.click();

			// asserting prior loss link not available on producer login on renewal quote
			Assertions.verify(
					accountOverviewPage.priorLossEditLink.checkIfElementIsPresent()
							&& accountOverviewPage.priorLossEditLink.checkIfElementIsDisplayed(),
					false, "Account Overview Page", "Producer login prior loss link not available is verified", false,
					false);

			// logout as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// login as tuser
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page ", "Logged in as USM Successfully");
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(renewalQuoteNo);

			// Click on prior loss edit link
			accountOverviewPage.priorLossEditLink.scrollToElement();
			accountOverviewPage.priorLossEditLink.click();
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page ",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// logout as usm
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Login as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page ", "Logged in as Producer Successfully");
			homePage.enterPersonalLoginDetails();

			// go to home page and search for quote
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuoteByProducer(renewalQuoteNo);

			// Asserting quote status after entering prior loss
			Assertions.addInfo("Scenario 09", "Asserting Expired status after Prior Loss added on renewal quote");
			Assertions.verify(accountOverviewPage.expiredStatus.getData(), "Expired", "Account Overview Page",
					"Quote Status is " + accountOverviewPage.expiredStatus.getData(), false, false);
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			// logout as usm
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Login as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page ", "Logged in as Producer Successfully");
			homePage.enterPersonalLoginDetails();

			// go to home page and search for quote
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchPolicy(policyNumber);

			// Click on Endorse PB Link
			policySummaryPage.endorseNPB.scrollToElement();
			policySummaryPage.endorseNPB.click();

			// Click on OK button
			if (policySummaryPage.okButton.checkIfElementIsPresent()
					&& policySummaryPage.okButton.checkIfElementIsDisplayed()) {
				policySummaryPage.okButton.scrollToElement();
				policySummaryPage.okButton.click();
			}

			// Entering Endorsement effective date
			testData = data.get(data_Value2);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// Initiate NPB Transaction
			Assertions.addInfo("Scenario 10", "NPB Transaction - Change Payment Plan");
			endorsePolicyPage.changeInspectionContactLink.scrollToElement();
			endorsePolicyPage.changeInspectionContactLink.click();

			// Change Payment Plan
			endorseInspectionContactPage.enterInspectionContactPB(testData);
			Assertions.passTest("Endorse Inspection contact Page", "Updated Inspection Contact details");
			Assertions.addInfo("Scenario 10", "Scenario 10 Ended");

			// click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// Click on Close Button
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Initiate PB ENDT with actual expiration date
			// Click on Endorse PB Link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();

			// Click on Ok button
			if (policySummaryPage.okButton.checkIfElementIsPresent()
					&& policySummaryPage.okButton.checkIfElementIsDisplayed()) {
				policySummaryPage.okButton.scrollToElement();
				policySummaryPage.okButton.click();
			}

			testData = data.get(data_Value2);
			Assertions.addInfo("Scenario 11", "PB Transaction - Change Expiration Date");
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// Click on Change Coverage Option link
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Coverage Options link");

			// Entering Optional Coverage details
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page Loaded Successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create A Quote Page", "Cov A value chaged Succesfully");

			// Click On next Button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Complete Button");

			// Click on continue Button
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Click on Close Button
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Asserting policy Effective Date and Policy Expiration Date
			Assertions.verify(policySummaryPage.effectiveDate.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Policy Effective date is disaplyed as : "
							+ policySummaryPage.effectiveDate.getData().substring(0, 10),
					false, false);
			Assertions.verify(policySummaryPage.expirationDate.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Policy Expiration date is disaplyed as : "
							+ policySummaryPage.expirationDate.getData().substring(0, 10),
					false, false);
			Assertions.addInfo("Scenario 11", "Scenario 11 Ended");

			// Initiate PB ENDT with expiration date in future
			// Click on Endorse PB Link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();

			// Click on Ok button
			if (policySummaryPage.okButton.checkIfElementIsPresent()
					&& policySummaryPage.okButton.checkIfElementIsDisplayed()) {
				policySummaryPage.okButton.scrollToElement();
				policySummaryPage.okButton.click();
			}

			// Entering Endorsement effective date
			testData = data.get(data_Value3);
			Assertions.addInfo("Scenario 12",
					"PB Transaction - Change Expiration Date inbetween extended period of change");
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// Entering Policy Expiration date
			endorsePolicyPage.policyExpirationDate.scrollToElement();
			endorsePolicyPage.policyExpirationDate.setData(testData.get("PolicyExpirationDate"));
			endorsePolicyPage.policyExpirationDate.tab();

			// Click on Change Expiration Button
			endorsePolicyPage.changeExpirationDate.scrollToElement();
			endorsePolicyPage.changeExpirationDate.click();

			// Asserting Out of Sequence Warning Message
			Assertions.verify(endorsePolicyPage.oosMsg.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Out of Sequence message is disaplyed as : " + endorsePolicyPage.oosMsg.getData(), false, false);

			// Click on continue Button
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Click On next Button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// Click on Ok, Continue button for Sinkhole warning message
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Complete Button");

			// Asserting Out of Sequence Warning Message
			Assertions.verify(endorsePolicyPage.oosMsg.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Out of Sequence message is disaplyed as : " + endorsePolicyPage.oosMsg.getData(), false, false);

			// Click on continue Button
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Click on Close Button
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Asserting policy Effective Date and Policy Expiration Date
			Assertions.verify(policySummaryPage.effectiveDate.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Policy Effective date is disaplyed as : "
							+ policySummaryPage.effectiveDate.getData().substring(0, 10),
					false, false);
			Assertions.verify(policySummaryPage.expirationDate.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Policy Expiration date is disaplyed as : "
							+ policySummaryPage.expirationDate.getData().substring(0, 10),
					false, false);
			Assertions.addInfo("Scenario 12", "Scenario 12 Ended");

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBFLTC005 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBFLTC005 ", "Executed Successfully");
			}
		}
	}
}
