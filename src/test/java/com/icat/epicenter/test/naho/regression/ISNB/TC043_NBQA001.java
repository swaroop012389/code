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

public class TC043_NBQA001 extends AbstractNAHOTest {

	public TC043_NBQA001() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBQA001.xls";
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
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ReferralPage referralPage = new ReferralPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();

		String quoteNumber;
		int dataValue1 = 0;
		int dataValue2 = 1;
		Map<String, String> testData = data.get(dataValue1);
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// creating New account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
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
			dwellingPage.addDwellingDetails(testData, 1, 1);
			dwellingPage.addRoofDetails(testData, 1, 1);
			dwellingPage.enterProtectionDiscountsDetails(testData, 1, 1);
			dwellingPage.enterDwellingValuesNAHO(testData, 1, 1);
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();

			// Asserting hard stop message TX State for producer login when YOC less than
			// 1970, The hard stop
			// message is 'Due to a year built prior to 1970 this risk is ineligible for
			// coverage. If the home has undergone a gut rehab and supporting documentation
			// can be provided, we will reconsider offering terms. Please reach out to your
			// assigned UW.
			Assertions.addInfo("Scenario 01",
					"Asserting hard stop message for TX when yoc less than 1970 for prodcuer login");
			Assertions.verify(
					dwellingPage.dwellingDetailsErrorMessages
							.formatDynamicPath(
									"Due to a year built prior to 1970 this risk is ineligible for coverage.")
							.getData()
							.contains("Due to a year built prior to 1970 this risk is ineligible for coverage."),
					true, "Dwelling page",
					"The hard stop message, "
							+ dwellingPage.dwellingDetailsErrorMessages
									.formatDynamicPath(
											"Due to a year built prior to 1970 this risk is ineligible for coverage.")
									.getData()
							+ " is displayed",
					false, false);
			Assertions.passTest("Scenario 01", "Scenario 01 Ended");

			// Updating year built 1969 to 2017
			testData = data.get(dataValue2);
			dwellingPage.yearBuilt.scrollToElement();
			dwellingPage.yearBuilt.clearData();
			dwellingPage.yearBuilt.appendData(testData.get("L1D1-DwellingYearBuilt"));
			dwellingPage.yearBuilt.tab();
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering prior loss details
			testData = data.get(dataValue1);
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

			// Entering details in refer quote page
			Assertions.verify(referQuotePage.referQuote.checkIfElementIsDisplayed(), true, "Refer Quote Page",
					"Refer Quote page loaded successfully", false, false);
			referQuotePage.contactName.scrollToElement();
			referQuotePage.contactName.setData(testData.get("ProducerName"));

			referQuotePage.contactEmail.scrollToElement();
			referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));

			referQuotePage.referQuote.scrollToElement();
			referQuotePage.referQuote.click();

			quoteNumber = referQuotePage.quoteNumberforReferral.getData();
			Assertions.passTest("Refer Quote Page", "The Quote Number is " + quoteNumber);

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

			// searching the quote number in grid and clicking on the quote number link
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

			// Clicking on View or Print Full Quote
			accountOverviewPage.viewPrintFullQuoteLink.waitTillVisibilityOfElement(60);
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();

			// Validating the discounts details
			viewOrPrintFullQuotePage.underwritingQuestions.waitTillVisibilityOfElement(60);
			Assertions.verify(
					viewOrPrintFullQuotePage.discountsText
							.formatDynamicPath("Central Station Burglar Alarm").checkIfElementIsDisplayed(),
					true, "Quote Application Page",
					"Discount : "
							+ viewOrPrintFullQuotePage.discountsText.formatDynamicPath("Central Station Burglar Alarm")
									.getData()
							+ " is " + viewOrPrintFullQuotePage.discountsValue
									.formatDynamicPath("Central Station Burglar Alarm").getData()
							+ " is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.discountsText
							.formatDynamicPath("Central Station Fire Alarm").checkIfElementIsDisplayed(),
					true, "Quote Application Page",
					"Discount : "
							+ viewOrPrintFullQuotePage.discountsText.formatDynamicPath("Central Station Fire Alarm")
									.getData()
							+ " is " + viewOrPrintFullQuotePage.discountsValue
									.formatDynamicPath("Central Station Fire Alarm").getData()
							+ " is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.discountsText
							.formatDynamicPath("Fully Sprinklered Home").checkIfElementIsDisplayed(),
					true, "Quote Application Page",
					"Discount : "
							+ viewOrPrintFullQuotePage.discountsText.formatDynamicPath("Fully Sprinklered Home")
									.getData()
							+ " is " + viewOrPrintFullQuotePage.discountsValue
									.formatDynamicPath("Fully Sprinklered Home").getData()
							+ " is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.discountsText
							.formatDynamicPath("Guard Gated Community").checkIfElementIsDisplayed(),
					true, "Quote Application Page",
					"Discount : "
							+ viewOrPrintFullQuotePage.discountsText.formatDynamicPath("Guard Gated Community")
									.getData()
							+ " is " + viewOrPrintFullQuotePage.discountsValue
									.formatDynamicPath("Guard Gated Community").getData()
							+ " is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.discountsText.formatDynamicPath("HardiePlank").checkIfElementIsDisplayed(),
					true, "Quote Application Page",
					"Discount : " + viewOrPrintFullQuotePage.discountsText.formatDynamicPath("HardiePlank").getData()
							+ " is "
							+ viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("HardiePlank").getData()
							+ " is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.discountsText
							.formatDynamicPath("New Purchase").checkIfElementIsDisplayed(),
					true, "Quote Application Page",
					"Discount : " + viewOrPrintFullQuotePage.discountsText.formatDynamicPath("New Purchase").getData()
							+ " is "
							+ viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("New Purchase").getData()
							+ " is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.discountsText
							.formatDynamicPath("Renovated Home").checkIfElementIsDisplayed(),
					true, "Quote Application Page",
					"Discount : " + viewOrPrintFullQuotePage.discountsText.formatDynamicPath("Renovated Home").getData()
							+ " is "
							+ viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Renovated Home").getData()
							+ " is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.discountsText
							.formatDynamicPath("Water Mitigation").checkIfElementIsDisplayed(),
					true, "Quote Application Page",
					"Discount : "
							+ viewOrPrintFullQuotePage.discountsText.formatDynamicPath("Water Mitigation").getData()
							+ " is "
							+ viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Water Mitigation").getData()
							+ " is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.discountsText
							.formatDynamicPath("Wind Mitigation").checkIfElementIsDisplayed(),
					true, "Quote Application Page",
					"Discount : "
							+ viewOrPrintFullQuotePage.discountsText.formatDynamicPath("Wind Mitigation").getData()
							+ " is "
							+ viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Wind Mitigation").getData()
							+ " is verified",
					false, false);

			// sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC043 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC043 ", "Executed Successfully");
			}
		}
	}
}
