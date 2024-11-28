package com.icat.epicenter.test.naho.regression.ISPNB;

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
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC024_PNBC001 extends AbstractNAHOTest {

	public TC024_PNBC001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBC001.xls";
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
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
		String quoteNumber;
		String policyNumber;
		String namedInsured;
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

			// Assertions of Increase Special Limits,special Personal Property and
			// Personal Injury in create Quote page
			Assertions.verify(createQuotePage.specialLimitsArrow.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"The Presence of Increased Special Limits is verified", false, false);

			Assertions.verify(createQuotePage.personalPropertyArrow.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "The Presence of Special Personal Property is verified", false, false);

			Assertions.verify(createQuotePage.personalInjuryArrow.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "The Presence of Personal Injury is verified", false, false);

			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Adding Policy fee and Inspection fee
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Click on Request Bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

			// Clicking on answer no button
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);

			// Entering bind details
			quoteNumber = requestBindPage.quoteNumber.getData();
			requestBindPage.enterBindDetailsNAHO(testData);

			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Searching the Bind Referral
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			accountOverviewPage.openReferralLink.checkIfElementIsDisplayed();
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();

			// Approve Referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();

			// click on approve in Approve Decline Quote page
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Referral Page", "Quote referral approved successfully");

			// Asserting policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is : " + policyNumber, false, false);

			// Getting Insured Name
			// Adding the CR IO-19401
			namedInsured = policySummaryPage.insuredName.getData();
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.passTest("Home Page", "Home Page Loaded successfully");

			// Global search the policy Through Policy Number
			homePage.searchField.scrollToElement();
			homePage.searchField.setData(policyNumber);

			// Check the Insured name
			homePage.searchResultNAHO.formatDynamicPath(policyNumber).waitTillPresenceOfElement(80);
			homePage.searchResultNAHO.formatDynamicPath(policyNumber).waitTillVisibilityOfElement(60);
			homePage.searchResultNAHO.formatDynamicPath(policyNumber).scrollToElement();
			String actualNamedInsured = homePage.searchResultNAHO.formatDynamicPath(policyNumber).getData();
			homePage.searchResultNAHO.formatDynamicPath(policyNumber).click();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Searched the Policy Through Policy Number successfully", false, false);
			Assertions.verify(actualNamedInsured, namedInsured, "Policy Summary Page",
					"The Insured Name " + policySummaryPage.insuredName.getData() + " displayed is verified", false,
					false);

			// Goto Home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Global search the policy Through Quote Number
			homePage.searchField.scrollToElement();
			homePage.searchField.setData(quoteNumber);

			// Check the Insured name
			homePage.searchResultNAHO.formatDynamicPath(quoteNumber).waitTillPresenceOfElement(60);
			homePage.searchResultNAHO.formatDynamicPath(quoteNumber).waitTillVisibilityOfElement(60);
			homePage.searchResultNAHO.formatDynamicPath(quoteNumber).scrollToElement();
			String actualQuoteNamedInsured = homePage.searchResultNAHO.formatDynamicPath(quoteNumber).getData();
			homePage.searchResultNAHO.formatDynamicPath(quoteNumber).click();

			// Click on view policy
			accountOverviewPage.viewPolicyButton.scrollToElement();
			accountOverviewPage.viewPolicyButton.click();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Searched the Policy Through Quote Number successfully", false, false);
			Assertions.verify(actualQuoteNamedInsured, namedInsured, "Policy Summary Page",
					"The Insured Name " + policySummaryPage.insuredName.getData() + " displayed is verified", false,
					false);

			// Goto Home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Global search the policy Through Insured Name
			homePage.searchField.scrollToElement();
			homePage.searchField.setData(namedInsured);

			// Check the Insured name
			homePage.searchResultNAHO.formatDynamicPath(policyNumber).waitTillPresenceOfElement(60);
			homePage.searchResultNAHO.formatDynamicPath(policyNumber).waitTillVisibilityOfElement(60);
			homePage.searchResultNAHO.formatDynamicPath(policyNumber).scrollToElement();
			String actualInsuredName = homePage.searchResultNAHO.formatDynamicPath(policyNumber).getData();
			homePage.searchResultNAHO.formatDynamicPath(policyNumber).click();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Searched the Policy Through Insured Name successfully", false, false);
			Assertions.verify(actualInsuredName, namedInsured, "Policy Summary Page",
					"The Insured Name " + policySummaryPage.insuredName.getData() + " displayed is verified", false,
					false);

			// Click on EndorsePB Link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();

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

			// Removing Cov C and Cov F values
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page Loaded Successfully", false, false);

			// Verifying When Coverage E selected as $300,000 the personal injury
			// Drop down has $300,000

			testData = data.get(dataValue2);
			createQuotePage.coverageFArrow.scrollToElement();
			createQuotePage.coverageFArrow.click();
			createQuotePage.coverageFOption.formatDynamicPath(testData.get("L1D1-DwellingCovF")).scrollToElement();
			createQuotePage.coverageFOption.formatDynamicPath(testData.get("L1D1-DwellingCovF")).click();
			createQuotePage.personalInjuryArrow.scrollToElement();
			createQuotePage.personalInjuryArrow.click();

			Assertions.verify(
					createQuotePage.personalInjuryOption.formatDynamicPath(testData.get("L1D1-DwellingCovF"))
							.checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"When Coverage E selected as  $300,000 the personal injury Drop down has $300,000 displayed is verified",
					false, false);

			testData = data.get(dataValue3);
			createQuotePage.enterInsuredValuesNAHO(testData, dataValue2, dataValue2);

			// Assertions of Increase Special Limits,special Personal Property and
			// Personal Injury in not present in create Quote page

			Assertions.verify(createQuotePage.specialLimitsArrow.checkIfElementIsPresent(), false, "Create Quote Page",
					"Increase Special Limits dropdown is not displayed when Cov-C,Cov-F and Cov-E values are zero is verified",
					false, false);

			Assertions.verify(createQuotePage.personalPropertyArrow.checkIfElementIsPresent(), false,
					"Create Quote Page",
					"Special Personal Property dropdown is not displayed when Cov-C,Cov-F and Cov-E values are zero is verified",
					false, false);

			Assertions.verify(createQuotePage.personalInjuryArrow.checkIfElementIsPresent(), false, "Create Quote Page",
					"Personal Injury dropdown is not displayed when Cov-C,Cov-F and Cov-E values are zero is verified",
					false, false);

			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			if (createQuotePage.override.checkIfElementIsPresent()
					&& createQuotePage.override.checkIfElementIsDisplayed()) {
				createQuotePage.override.scrollToElement();
				createQuotePage.override.click();
			}

			// Click On next Button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Asserting the Changed Values From Endorse Summary Page
			Assertions.verify(endorsePolicyPage.completeButton.checkIfElementIsDisplayed(), true,
					"Endorse Summary Page", "Endorse Summary Page Loaded Successfully", false, false);

			String covEfrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(6).getData();
			String covEto = endorsePolicyPage.endorsementSummary.formatDynamicPath(7).getData();
			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(5).checkIfElementIsDisplayed(),
					true, "Endorse Summary Page",
					"The Coverage E Value " + covEfrom + " changed to : " + covEto + " displayed is verified", false,
					false);

			String covFfrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(11).getData();
			String covFto = endorsePolicyPage.endorsementSummary.formatDynamicPath(12).getData();
			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(10).checkIfElementIsDisplayed(),
					true, "Endorse Summary Page",
					"The Coverage F Value " + covFfrom + " changed to : " + covFto + " displayed is verified", false,
					false);

			String covCfrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(16).getData();
			String covCto = endorsePolicyPage.endorsementSummary.formatDynamicPath(17).getData();
			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(15).checkIfElementIsDisplayed(),
					true, "Endorse Summary Page",
					"The Coverage C Value " + covCfrom + " changed to : " + covCto + " displayed is verified", false,
					false);

			// click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC024 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC024 ", "Executed Successfully");
			}
		}
	}

}
