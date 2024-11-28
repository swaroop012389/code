package com.icat.epicenter.test.naho.regression.ISNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintRateTrace;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC072_NBGEN003 extends AbstractNAHOTest {

	public TC072_NBGEN003() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBGEN003.xls";
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
		ViewOrPrintRateTrace viewOrPrintRateTrace = new ViewOrPrintRateTrace();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		LoginPage loginPage = new LoginPage();

		// initializing variables
		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
		int quoteLength;
		String quoteNumber;
		String policyNumber;
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

			// Asserting AOP deductible default value
			createQuotePage.aopDedValue.waitTillVisibilityOfElement(60);
			Assertions.verify(createQuotePage.aopDedValue.getData().contains("$2,500"), true, "Create Quote Page",
					"Default value of AOP deductible : " + createQuotePage.aopDedValue.getData() + " is verified",
					false, false);
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Adding Below code for CR-19533 and asserting prior loss warning message when
			// prior loss two or more with the reason "water damage not due to weather"
			Assertions.verify(
					createQuotePage.warningMessages
							.formatDynamicPath("Water Damage Not Due to").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Prior loss warning message is: "
							+ createQuotePage.warningMessages.formatDynamicPath("Water Damage Not Due to").getData()
							+ " is verified",
					false, false);
			// CR 19533 is ended

			// Click on Continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Asserting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLength = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLength - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Click on View Or Print Rate Trace link
			accountOverviewPage.viewOrPrintRateTrace.scrollToElement();
			accountOverviewPage.viewOrPrintRateTrace.click();

			// Asserting Laps No Surcharge
			Assertions.verify(viewOrPrintRateTrace.backBtn.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page", "View Or Print Rate Trace Page Loaded Successfully", false, false);
			String lapseSurcharge = viewOrPrintRateTrace.lapseNoSurcharge.getData();
			Assertions.verify(viewOrPrintRateTrace.lapseNoSurcharge.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page", "The Presence of " + lapseSurcharge + " is verified", false,
					false);
			String lapseSurchargeData = viewOrPrintRateTrace.lapseNoSurchargeData.getData().substring(0, 3);
			Assertions.verify(viewOrPrintRateTrace.lapseNoSurchargeData.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page",
					"The Presence of Lapse No Ins Surcharge factor " + lapseSurchargeData + " is verified", false,
					false);
			viewOrPrintRateTrace.backBtn.scrollToElement();
			viewOrPrintRateTrace.backBtn.click();

			// Added Below code for CR-19533
			// Click on Bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request bind button successfully");

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
			Assertions.passTest("Request Bind Page", "Entered Bind details successfully");

			// Clicking on home page button
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

			// Asserting Policy Number
			policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is " + policyNumber, false, false);

			// Click on Endorse PB
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Entering Endorsement effective date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// Click on Change Coverage Option link
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Coverage Options link");

			// Entering Deductible Coverage details
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page Loaded Successfully", false, false);
			Assertions.passTest("Create Quote Page",
					"Named Storm Original Value : " + createQuotePage.namedStormData.getData());
			testData = data.get(data_Value2);
			createQuotePage.enterDeductiblesNAHO(testData);
			Assertions.passTest("Create Quote Page",
					"Named Storm Latest Value : " + createQuotePage.namedStormData.getData());

			// Click on Continue button
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Click On next Button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Next button successfully");

			// Asserting prior loss warning message on endorsement when prior loss two or
			// more with the reason "water damage not due to weather"
			Assertions.verify(
					createQuotePage.warningMessages.formatDynamicPath("Water Damage Not Due to Weather")
							.checkIfElementIsDisplayed(),
					true, "Endorse Policy Page", "Prior loss warning message is: " + createQuotePage.warningMessages
							.formatDynamicPath("Water Damage Not Due to Weather").getData(),
					false, false);

			// Click on continue button
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// click on complete button
			Assertions.verify(endorsePolicyPage.completeButton.checkIfElementIsDisplayed(), true,
					"Endorse Summary Page", "Endorse Summary Page Loaded Successfully", false, false);
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// Click on Close button
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Close button successfully");

			// Click on NB transaction
			Assertions.verify(
					policySummaryPage.policyNumber.checkIfElementIsPresent()
							&& policySummaryPage.policyNumber.checkIfElementIsDisplayed(),
					true, "Policy Summary Page", "Policy Summary Page loaded successfully", false, false);
			policySummaryPage.transHistReason.formatDynamicPath(2).scrollToElement();
			policySummaryPage.transHistReason.formatDynamicPath(2).click();

			// Click on renew policy link
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renewal link successfully");

			// Go to Home Page
			testData = data.get(data_Value1);
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
				policySummaryPage.transHistReason.formatDynamicPath(2).scrollToElement();
				policySummaryPage.transHistReason.formatDynamicPath(2).click();

				// clicking on renewal policy link
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();
			}

			// Asserting quote status referred when prior loss two or more with the reason
			// "water damage not due to weather" on renewal
			Assertions.verify(
					accountOverviewPage.quoteNumber.checkIfElementIsPresent()
							&& accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "Account Overview Page loaded successfully", false, false);
			Assertions.verify(
					accountOverviewPage.referredStatus.checkIfElementIsPresent()
							&& accountOverviewPage.referredStatus.checkIfElementIsDisplayed(),
					true, "Account Overview Page",
					"Renewal Quote Status is: " + accountOverviewPage.referredStatus.getData() + " is verified", false,
					false);

			// Asserting renewal quote number
			quoteLength = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLength - 1);
			Assertions.passTest("Account Overview Page", "Renewal Quote Number is : " + quoteNumber);

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
			homePage.searchQuote(quoteNumber);

			// Click on edit deductible's and limits
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit deductible and limits link");

			// Click on get a quote
			Assertions.verify(
					createQuotePage.getAQuote.checkIfElementIsPresent()
							&& createQuotePage.getAQuote.checkIfElementIsDisplayed(),
					true, "Create Quote Page", "Create Quote Page loaded successfully", false, false);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("create Quote Page", "Clicked on GetAQuote button successfully");

			// Asserting Prior loss warning message when prior loss two or more with the
			// reason"water damage Not Due to Weather"
			Assertions.verify(
					createQuotePage.warningMessages.formatDynamicPath("Water Damage Not Due to Weather")
							.checkIfElementIsPresent()
							&& createQuotePage.warningMessages
									.formatDynamicPath("Water Damage Not Due to Weather").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Prior loss warning message is: " + createQuotePage.warningMessages
							.formatDynamicPath("Water Damage Not Due to Weather").getData() + " is verified",
					false, false);

			// sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged out as usm successfully");

			// Login as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page ", "Logged in as Producer Successfully");
			homePage.enterPersonalLoginDetails();

			// Creating a new account
			testData = data.get(data_Value2);
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
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

			// click on get a quote
			Assertions.verify(
					createQuotePage.getAQuote.checkIfElementIsPresent()
							&& createQuotePage.getAQuote.checkIfElementIsDisplayed(),
					true, "Create Quote Page", "Create Quote Page loaded successfully", false, false);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// Asserting hard stop message as producer when prior loss reason "water damage
			// not due to weather"
			Assertions.verify(
					createQuotePage.globalErr.checkIfElementIsPresent()
							&& createQuotePage.globalErr.checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Prior loss warning message is: " + createQuotePage.globalErr.getData() + " is verified", false,
					false);
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC072 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC072 ", "Executed Successfully");
			}
		}
	}

}
