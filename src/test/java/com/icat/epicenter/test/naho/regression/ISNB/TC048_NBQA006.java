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

public class TC048_NBQA006 extends AbstractNAHOTest {

	public TC048_NBQA006() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBQA006.xls";
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
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();

		String quoteNumber;
		int dataValue1 = 0;
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
			// Entering Referral Details
			if (referQuotePage.contactName.checkIfElementIsPresent()
					&& referQuotePage.contactName.checkIfElementIsDisplayed()) {
				referQuotePage.contactName.waitTillVisibilityOfElement(60);
				referQuotePage.contactName.clearData();
				referQuotePage.contactName.scrollToElement();
				referQuotePage.contactName.setData(testData.get("ProducerName"));
				referQuotePage.contactEmail.clearData();
				referQuotePage.contactEmail.scrollToElement();
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.scrollToElement();
				referQuotePage.referQuote.click();

				// Getting quote no
				quoteNumber = referQuotePage.quoteNumberforReferral.getData();
				Assertions.passTest("Refer Quote Page", "The Quote Number is " + quoteNumber);

				// Sign out as producer
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();

				// Sign in as USM
				loginPage.refreshPage();
				loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
				Assertions.passTest("Login", "Logged in to application successfully");

				// Go to HomePage
				homePage.goToHomepage.click();
				homePage.searchQuote(quoteNumber);
				Assertions.passTest("Home Page", "Quote for referral is searched successfully");

				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();

				// Clicking on pick up button
				if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
					referralPage.pickUp.scrollToElement();
					referralPage.pickUp.click();
				}

				// Approve Referral
				Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true,
						"Referral Page", "Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");

				Assertions.verify(approveDeclineQuotePage.approveButton.checkIfElementIsDisplayed(), true,
						"Approve Decline Quote Page", "Approve Decline Quote page loaded successfully", false, false);
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Approve Decline Quote Page", "Bind Request approved successfully");
				Assertions.passTest("Approve Decline Quote Page",
						"Quote number : " + quoteNumber + " approved successfully");

				// Sign out as USM
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();

				// Sign in as Producer
				loginPage.refreshPage();
				loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
				Assertions.passTest("Login", "Logged in to application successfully");

				// Handling the pop up
				homePage.enterPersonalLoginDetails();

				// Searching for Quote
				homePage.searchQuoteByProducer(quoteNumber);
			} else {
				// getting quote number
				quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
				Assertions.passTest("Account Overview Page", "Quote Number : " + quoteNumber);
			}

			// click on view/Print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();

			// verification in view/Print full quote page
			Assertions.verify(viewOrPrintFullQuotePage.bindPolicyCertificationMsg.checkIfElementIsDisplayed(), true,
					"View Or Print Full Quote Page", "View Or Print Full Quote Page Loaded successfully", false, false);

			String certificationMsg = viewOrPrintFullQuotePage.bindPolicyCertificationMsg.getData();
			Assertions.verify(viewOrPrintFullQuotePage.bindPolicyCertificationMsg.checkIfElementIsDisplayed(), true,
					"View Or Print Full Quote Page", "The Text displayed is  " + certificationMsg + " is verified",
					false, false);
			Assertions.verify(viewOrPrintFullQuotePage.declineCompany.formatDynamicPath(1).checkIfElementIsDisplayed(),
					true, "View Or Print Full Quote Page", "Presence of Decline Company is verified ", false, false);
			Assertions.verify(viewOrPrintFullQuotePage.naicNumber.formatDynamicPath(1).checkIfElementIsDisplayed(),
					true, "View Or Print Full Quote Page", "Presence of NAIC Number is verified ", false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.reasonforDeclination.formatDynamicPath(1).checkIfElementIsDisplayed(),
					true, "View Or Print Full Quote Page", "Presence of Reason for declination is verified ", false,
					false);
			Assertions.verify(viewOrPrintFullQuotePage.decliningDate.formatDynamicPath(1).checkIfElementIsDisplayed(),
					true, "View Or Print Full Quote Page", "Presence of Date Declined is verified ", false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.producingAgentName.formatDynamicPath(1).checkIfElementIsDisplayed(), true,
					"View Or Print Full Quote Page", "Presence of Producing Agent Name is verified ", false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.producingAgentSignature.formatDynamicPath(1).checkIfElementIsDisplayed(),
					true, "View Or Print Full Quote Page", "Presence of Producing Agent Signature is verified ", false,
					false);
			Assertions.verify(viewOrPrintFullQuotePage.date.formatDynamicPath(1).checkIfElementIsDisplayed(), true,
					"View Or Print Full Quote Page", "Presence of Date is verified ", false, false);

			// SignOut and Close the browser
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC048 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC048 ", "Executed Successfully");
			}
		}
	}

}
