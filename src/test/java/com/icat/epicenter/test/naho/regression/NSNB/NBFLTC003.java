/** Program Description: To Verify absence of Sinkhole/CGCC coverage when there is sinkhole/cgcc losses and to verify the hard stop message as producer
 *  Author			   : Yeshashwini
 *  Date of Creation   : 03/29/2021
 **/
package com.icat.epicenter.test.naho.regression.NSNB;

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
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class NBFLTC003 extends AbstractNAHOTest {

	public NBFLTC003() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSNB/FL003.xls";
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
		ReferQuotePage referQuotePage = new ReferQuotePage();
		LoginPage loginPage = new LoginPage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();

		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		Map<String, String> testData = data.get(data_Value1);
		String quoteNumber;
		boolean isTestPassed = false;

		try {
			// creating New account
			homePage.enterPersonalLoginDetails();
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
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// IO-2010
			// Asserting hard stop message FL state for producer login when YOC less than
			// 1980, The hard stop
			// message is 'Due to a year built prior to 1980 this risk is ineligible for
			// coverage. If the home has undergone a gut rehab and supporting documentation
			// can be provided, we will reconsider offering terms. Please reach out to your
			// assigned UW.'
			Assertions.addInfo("Scenario 01",
					"Asserting hard stop message when yoc is less than 1980, for prodcuer login");
			Assertions.verify(dwellingPage.protectionClassWarMsg
					.formatDynamicPath("Due to a year built prior to 1980 this risk is ineligible for coverage")
					.getData().contains("Due to a year built prior to 1980 this risk is ineligible for coverage"), true,
					"Dwelling page",
					"The hard stop message, " + dwellingPage.protectionClassWarMsg
							.formatDynamicPath("Due to a year built prior to 1980 this risk is ineligible for coverage")
							.getData() + " is displayed",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
			// IO-20810 ended

			// Changing year built 1982
			testData = data.get(data_Value2);
			dwellingPage.yearBuilt.scrollToElement();
			dwellingPage.yearBuilt.clearData();
			dwellingPage.yearBuilt.appendData(testData.get("L1D1-DwellingYearBuilt"));
			dwellingPage.yearBuilt.tab();
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();

			// Entering prior loss details
			testData = data.get(data_Value1);
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}
			// Click on get a quote
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on get a quote button successfully");

			// IO-20816
			// Asserting and Verifying hard stop message when dwelling 40 years and older
			// and they have stated that the HVAC or Electrical is more than 15 years old
			// (Is it possible to get this validation to trigger on the building page?)
			Assertions.addInfo("Scenario 02", "Asserting and Verifying hard stop message");
			Assertions.verify(createQuotePage.errorMessageWarningPage.formatDynamicPath(
					"quoted building being 40 years or older with no HVAC/Electrical updates in the last 15 years.")
					.getData().contains("40 years or older with no HVAC/Electrical updates in the last 15 years."),
					true, "Create Quote Page",
					"The hard stop message  " + createQuotePage.errorMessageWarningPage.formatDynamicPath(
							"quoted building being 40 years or older with no HVAC/Electrical updates in the last 15 years.")
							.getData() + "is displyed",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");
			// IO-20816 ended

			// Verifying absence of sinkhole coverage
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			Assertions.addInfo("Scenario 03",
					"Verify the absence of Sinkhole coverage when there is sinkhole losses for Producer");

			Assertions.verify(createQuotePage.sinkholeArrow.checkIfElementIsPresent(), false, "Create Quote Page",
					"Any Sinkhole reported losses will make the quote ineligible for Sinkhole or CGCC coverage for producer is verified",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on Previous link
			createQuotePage.previous.scrollToElement();
			createQuotePage.previous.click();
			Assertions.passTest("Create Quote Page", "Clicked on previous button successfully");

			// Click on edit building link
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit building link successfully");

			// Changing year built 2022
			testData = data.get(data_Value3);
			dwellingPage.yearBuilt.scrollToElement();
			dwellingPage.yearBuilt.clearData();
			dwellingPage.yearBuilt.appendData(testData.get("L1D1-DwellingYearBuilt"));
			dwellingPage.yearBuilt.tab();
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			Assertions.passTest("Dwelling Page", "Year built changed to 2022");

			// Entering Create quote page Details
			testData = data.get(data_Value1);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Click on Previous link
			createQuotePage.previous.scrollToElement();
			createQuotePage.previous.click();
			Assertions.verify(accountOverviewPage.deleteAccount.checkIfElementIsDisplayed(), true,
					"Account Overview Page ", "Account overview page loaded successfully", false, false);
			accountOverviewPage.priorLossEditLink.scrollToElement();
			accountOverviewPage.priorLossEditLink.click();
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior loss page loaded successfully", false, false);
			priorLossesPage.lossesInThreeYearsNo.scrollToElement();
			priorLossesPage.lossesInThreeYearsNo.click();
			priorLossesPage.continueButton.scrollToElement();
			priorLossesPage.continueButton.click();
			Assertions.verify(accountOverviewPage.deleteAccount.checkIfElementIsDisplayed(), true,
					"Account Overview Page ", "Account overview page loaded successfully", false, false);
			accountOverviewPage.quoteAccountButton.scrollToElement();
			accountOverviewPage.quoteAccountButton.click();
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create quote page loaded successfully", false, false);
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

			// asserting quote number
			accountOverviewPage.quoteNumber.formatDynamicPath(1).waitTillVisibilityOfElement(60);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number : " + quoteNumber);

			// Verify the absence of CGCC coverage when there is CGCC losses for Producer
			Assertions.addInfo("Scenario 04",
					"Verify the absence of CGCC coverage when there is CGCC losses for Producer");

			accountOverviewPage.priorLossEditLink.scrollToElement();
			accountOverviewPage.priorLossEditLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Prior Losses Edit link");

			testData = data.get(data_Value2);
			priorLossesPage.selectPriorLossesInformation(testData);
			Assertions.passTest("Prior Loss Page", "Selected CGCC");
			accountOverviewPage.gotoAccountOverviewButton.scrollToElement();
			accountOverviewPage.gotoAccountOverviewButton.click();

			// create another quote
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			Assertions.verify(createQuotePage.sinkholeArrow.checkIfElementIsPresent(), false, "Create Quote Page",
					"Any CGCC reported losses will make the quote ineligible for Sinkhole or CGCC coverage for producer is verified",
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBFLTC003 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBFLTC003 ", "Executed Successfully");
			}
		}
	}
}