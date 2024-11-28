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
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC050_NBF001 extends AbstractNAHOTest {

	public TC050_NBF001() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBF001.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		int dataValue1 = 0;
		Map<String, String> testData = data.get(dataValue1);
		String quoteNumber;
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// creating New account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Entering Refer quote Details
			Assertions.verify(referQuotePage.referQuote.checkIfElementIsDisplayed(), true, "Refer Quote Page",
					"Refer Quote page loaded successfully", false, false);

			referQuotePage.contactName.scrollToElement();
			referQuotePage.contactName.setData(testData.get("ProducerName"));

			referQuotePage.contactEmail.scrollToElement();
			referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));

			referQuotePage.referQuote.scrollToElement();
			referQuotePage.referQuote.click();

			// verifying referral message
			Assertions.verify(referQuotePage.quoteNumberforReferral.checkIfElementIsDisplayed(), true, "Referral Page",
					"Quote " + referQuotePage.quoteNumberforReferral.getData() + " referring to USM " + " is verified",
					false, false);

			quoteNumber = referQuotePage.quoteNumberforReferral.getData();

			// sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged Out as Producer Successfully");

			loginPage.refreshPage();

			// Login to USM account
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

			// verifying referral complete message
			Assertions.verify(referralPage.referralCompleteMsg.checkIfElementIsDisplayed(), true, "Referral Page",
					referralPage.referralCompleteMsg.getData() + " message is verified successfully", false, false);
			referralPage.close.scrollToElement();
			referralPage.close.click();

			// sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged Out as USM Successfully");

			loginPage.refreshPage();

			// login as producer
			Assertions.verify(loginPage.signInButton.checkIfElementIsDisplayed(), true, "Login Page",
					"Login page loaded successfully", false, false);
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Login Page",
					"Logged in as Producer " + setUpData.get("NahoProducer") + " Successfully");

			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// search for the quote number
			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.passTest("Producer Home Page", "Quote Number : " + quoteNumber + " searched successfully");

			// Account Overview Page
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);

			// Assert policy fee and inspection fee
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();

			String totalPolicyFee = viewOrPrintFullQuotePage.policyFee.getData();
			Assertions.verify(viewOrPrintFullQuotePage.policyFee.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Policy fee applied on the account " + totalPolicyFee + " is verified ",
					false, false);

			String totalInspectionFee = viewOrPrintFullQuotePage.inspectionFee.getData();
			Assertions.verify(viewOrPrintFullQuotePage.inspectionFee.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"Inspection fee applied on the account " + totalInspectionFee + " is verified", false, false);

			// Go to HomePage and Sign out
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC050 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC050 ", "Executed Successfully");
			}
		}
	}

}
