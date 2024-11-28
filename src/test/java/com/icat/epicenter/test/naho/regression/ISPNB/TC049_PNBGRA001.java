package com.icat.epicenter.test.naho.regression.ISPNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.DateConversions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC049_PNBGRA001 extends AbstractNAHOTest {

	public TC049_PNBGRA001() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBGRA001.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ReferralPage referralPage = new ReferralPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();

		String quoteNumber;
		int dataValue1 = 0;
		Map<String, String> testData = data.get(dataValue1);
		DateConversions date = new DateConversions();
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// creating New account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			String insuredName = testData.get("InsuredName") + date.getCurrentDate("MM/dd/YYYY_hh:mm:ss");
			homePage.createNewAccountProducer.moveToElement();
			homePage.createNewAccountProducer.click();
			homePage.namedInsured.setData(insuredName);
			Assertions.passTest("Home Page", "Insured Name is " + insuredName);
			homePage.productArrow.scrollToElement();
			homePage.productArrow.click();
			homePage.productSelection.formatDynamicPath(testData.get("ProductSelection")).click();
			homePage.effectiveDate.formatDynamicPath(1).scrollToElement();
			homePage.effectiveDate.formatDynamicPath(1).waitTillVisibilityOfElement(60);
			homePage.effectiveDate.formatDynamicPath(1).setData(testData.get("PolicyEffDate"));
			homePage.goButton.click();
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zipcode in Eligibility page
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
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Enter Referral Contact Details
			referQuotePage.contactName.setData(testData.get("ProducerName"));
			referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
			referQuotePage.referQuote.click();
			Assertions.passTest("Refer Quote Page", "Quote referred successfully");
			quoteNumber = referQuotePage.quoteNumberforReferral.getData();
			Assertions.passTest("Refer Quote Page", "The Quote Number is " + quoteNumber);

			// Navigate to home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Search for referral quote
			homePage.waitTime(10);// To load the referrals wait time is given
			homePage.producerReferralTab.waitTillVisibilityOfElement(60);
			homePage.producerReferralTab.click();
			Assertions.passTest("Home Page", "Clicked on Referrals Tab");

			homePage.recentEnhancement.scrollToElement();

			homePage.producerReferralFilterArrow.waitTillVisibilityOfElement(60);
			homePage.producerReferralFilterArrow.click();
			homePage.waitTime(2);// To load the drop down options wait time is given
			homePage.producerReferralFilterData.formatDynamicPath("Quote Referral").waitTillPresenceOfElement(60);
			homePage.producerReferralFilterData.formatDynamicPath("Quote Referral").waitTillVisibilityOfElement(60);
			homePage.producerReferralFilterData.formatDynamicPath("Quote Referral").scrollToElement();
			homePage.producerReferralFilterData.formatDynamicPath("Quote Referral").click();
			homePage.producerReferralQuoteLink.formatDynamicPath(insuredName).click();
			Assertions.passTest("Home Page", "Clicked on Account with Insured Name: " + insuredName + " successfully");

			// Assert Quote status
			Assertions
					.verify(referralPage.producerQuoteStatus.formatDynamicPath("Submitted").checkIfElementIsDisplayed(),
							true, "Referral Page",
							"Quote status is: "
									+ referralPage.producerQuoteStatus.formatDynamicPath("Submitted").getData()
									+ " in Referral Page from Homepage",
							false, false);

			// Search for quote as producer
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.passTest("Account OverviewPage", "Account Overview page is opening for quote "
					+ accountOverviewPage.quoteNumber.getData() + " without stack trace error");

			// Assert quote status on account Overview page
			Assertions.verify(accountOverviewPage.quoteStatus.formatDynamicPath(1).getData().contains("Referred"), true,
					"Account Overview page",
					"Quote status is " + accountOverviewPage.quoteStatus.formatDynamicPath(1).getData() + " for quote"
							+ quoteNumber,
					false, false);

			// SignOut as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC049 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC049 ", "Executed Successfully");
			}
		}
	}
}
