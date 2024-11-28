package com.icat.epicenter.test.naho.regression.ISNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC004_NBRQ001 extends AbstractNAHOTest {

	public TC004_NBRQ001() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBRQ001.xls";
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
		ReferralPage referralPage = new ReferralPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		BuildingUnderMinimumCostPage buildingUnderMinimumCostPage = new BuildingUnderMinimumCostPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();

		String quoteNumber;
		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
		Map<String, String> testData = data.get(dataValue1);
		String effectiveDate;
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

			// Entering Dwelling Details
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
			createQuotePage.verifyDeductibleDetails(testData);
			// createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			createQuotePage.enterDeductiblesNAHO(testData);
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);

			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			if (buildingUnderMinimumCostPage.continueButton.checkIfElementIsPresent()
					&& buildingUnderMinimumCostPage.continueButton.checkIfElementIsDisplayed()) {
				buildingUnderMinimumCostPage.continueButton.scrollToElement();
				buildingUnderMinimumCostPage.continueButton.click();
			}

			if (buildingUnderMinimumCostPage.continueButton.checkIfElementIsPresent()
					&& buildingUnderMinimumCostPage.continueButton.checkIfElementIsDisplayed()) {
				buildingUnderMinimumCostPage.continueButton.scrollToElement();
				buildingUnderMinimumCostPage.continueButton.click();
			}
			if (referQuotePage.contactName.checkIfElementIsPresent()
					&& referQuotePage.contactName.checkIfElementIsDisplayed()) {
				// Enter Referral Contact Details
				referQuotePage.contactName.setData(testData.get("ProducerName"));
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
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
				loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
				Assertions.passTest("Home Page", "Logged in as USM Successfully");

				// searching the quote number in grid and clicking on the quote number
				// link
				homePage.searchReferral(quoteNumber);
				Assertions.passTest("Home Page", "Searched Submitted Quote " + quoteNumber + " successfullly");

				// approving referral
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

				// Search for quote
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuoteByProducer(quoteNumber);

			}
			// getting quote number 1
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number 1 : " + quoteNumber);

			// click on create another quote
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();

			// Entering Create quote page Details
			testData = data.get(dataValue2);
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.verifyDeductibleDetails(testData);
			// createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			createQuotePage.enterDeductiblesNAHO(testData);
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);

			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");
			if (buildingUnderMinimumCostPage.continueButton.checkIfElementIsPresent()
					&& buildingUnderMinimumCostPage.continueButton.checkIfElementIsDisplayed()) {
				buildingUnderMinimumCostPage.continueButton.scrollToElement();
				buildingUnderMinimumCostPage.continueButton.click();
			}

			if (buildingUnderMinimumCostPage.continueButton.checkIfElementIsPresent()
					&& buildingUnderMinimumCostPage.continueButton.checkIfElementIsDisplayed()) {
				buildingUnderMinimumCostPage.continueButton.scrollToElement();
				buildingUnderMinimumCostPage.continueButton.click();
			}
			testData = data.get(dataValue1);

			if (referQuotePage.contactName.checkIfElementIsPresent()
					&& referQuotePage.contactName.checkIfElementIsDisplayed()) {
				// Enter Referral Contact Details
				referQuotePage.contactName.setData(testData.get("ProducerName"));
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.click();
				Assertions.passTest("Refer Quote Page", "Quote referred successfully");
				quoteNumber = referQuotePage.quoteNumberforReferral.getData();

				// SignOut as Producer
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Home Page", "Logged out as Producer successfully");

				// Sign in as USM
				loginPage.refreshPage();
				loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
				Assertions.passTest("Home Page", "Logged in as USM Successfully");

				// searching the quote number in grid and clicking on the quote number link
				homePage.searchReferral(quoteNumber);
				Assertions.passTest("Home Page", "Searched Submitted Quote successfullly");

				// approving referral
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

				// Search for quote
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuoteByProducer(quoteNumber);

			}

			// getting quote number 2
			String quoteNumber2 = accountOverviewPage.quoteNumber.formatDynamicPath(2).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number 2 : " + quoteNumber2);

			// asserting policy effective dates for quote 1
			accountOverviewPage.quoteLink.formatDynamicPath(1).scrollToElement();
			accountOverviewPage.quoteLink.formatDynamicPath(1).click();

			accountOverviewPage.quoteSpecifics.scrollToElement();
			accountOverviewPage.quoteSpecifics.click();
			Assertions.passTest("Account Overview Page", "Policy Effective Date for Quote 1 " + quoteNumber + ": "
					+ accountOverviewPage.requestedEffectiveDate.getData());

			// asserting policy effective dates for quote 2
			accountOverviewPage.quoteLink.formatDynamicPath(2).scrollToElement();
			accountOverviewPage.quoteLink.formatDynamicPath(2).click();

			accountOverviewPage.quoteSpecifics.scrollToElement();
			accountOverviewPage.quoteSpecifics.click();
			Assertions.passTest("Account Overview Page", "Policy Effective Date for Quote 2 " + quoteNumber2 + ": "
					+ accountOverviewPage.requestedEffectiveDate.getData());

			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			Assertions.verify(underwritingQuestionsPage.saveButton.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Details entered successfully");

			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			effectiveDate = requestBindPage.effectiveDate.getData();
			testData = data.get(dataValue2);
			requestBindPage.effectiveDate.scrollToElement();
			requestBindPage.effectiveDate.setData(testData.get("PolicyEffDate"));
			requestBindPage.effectiveDate.tab();
			Assertions.addInfo("<span class='group'> Request Bind Page : </span>",
					"Updating Policy Effective date from " + effectiveDate + " to " + testData.get("PolicyEffDate"));

			if (requestBindPage.wanttoContinue.checkIfElementIsPresent()
					&& requestBindPage.wanttoContinue.checkIfElementIsDisplayed()) {
				requestBindPage.wanttoContinue.waitTillVisibilityOfElement(60);
				requestBindPage.wanttoContinue.scrollToElement();
				requestBindPage.wanttoContinue.click();
			}

			homePage.goToHomepage.click();

			homePage.searchQuoteByProducer(quoteNumber);
			// asserting policy effective dates for quote 1
			accountOverviewPage.quoteLink.formatDynamicPath(1).waitTillPresenceOfElement(60);
			accountOverviewPage.quoteLink.formatDynamicPath(1).scrollToElement();
			accountOverviewPage.quoteLink.formatDynamicPath(1).click();

			accountOverviewPage.quoteSpecifics.scrollToElement();
			accountOverviewPage.quoteSpecifics.click();
			Assertions.passTest("Account Overview Page", "Policy Effective Date for Quote 1 " + quoteNumber
					+ " after updation : " + accountOverviewPage.requestedEffectiveDate.getData());

			// asserting policy effective dates for quote 2
			accountOverviewPage.quoteLink.formatDynamicPath(2).scrollToElement();
			accountOverviewPage.quoteLink.formatDynamicPath(2).click();

			accountOverviewPage.quoteSpecifics.scrollToElement();
			accountOverviewPage.quoteSpecifics.click();
			Assertions.passTest("Account Overview Page", "Policy Effective Date for Quote 2 " + quoteNumber2
					+ " after updation : " + accountOverviewPage.requestedEffectiveDate.getData());

			// sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged Out as USM Successfully");

			// Added IO-21293/21667
			// Note: This tests will fail in QA as per the comment added in ticket
			// IO-21667.
			// Sign in as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as Producer successfully");

			testData = data.get(dataValue1);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Dwelling Details
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
			createQuotePage.verifyDeductibleDetails(testData);
			createQuotePage.enterDeductiblesNAHO(testData);
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);

			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			if (buildingUnderMinimumCostPage.continueButton.checkIfElementIsPresent()
					&& buildingUnderMinimumCostPage.continueButton.checkIfElementIsDisplayed()) {
				buildingUnderMinimumCostPage.continueButton.scrollToElement();
				buildingUnderMinimumCostPage.continueButton.click();
			}

			if (buildingUnderMinimumCostPage.continueButton.checkIfElementIsPresent()
					&& buildingUnderMinimumCostPage.continueButton.checkIfElementIsDisplayed()) {
				buildingUnderMinimumCostPage.continueButton.scrollToElement();
				buildingUnderMinimumCostPage.continueButton.click();
			}

			String quoteNumber3 = accountOverviewPage.quoteNumber.formatDynamicPath(2).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number 2 : " + quoteNumber3);

			accountOverviewPage.clickOnRequestBind(testData, quoteNumber3);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			Assertions.verify(underwritingQuestionsPage.saveButton.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Details entered successfully");

			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);

			testData = data.get(dataValue3);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged out as producer Successfully");

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");

			homePage.searchQuote(quoteNumber3);
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();

			referralPage.clickOnApprove();
			// click on decline in Approve Decline Quote page
			if (approveDeclineQuotePage.internalUnderwriterComments.checkIfElementIsPresent()) {
				approveDeclineQuotePage.internalUnderwriterComments.scrollToElement();
				approveDeclineQuotePage.internalUnderwriterComments.setData("Test");
			}
			if (approveDeclineQuotePage.externalUnderwriterComments.checkIfElementIsPresent()) {
				approveDeclineQuotePage.externalUnderwriterComments.scrollToElement();
				approveDeclineQuotePage.externalUnderwriterComments.setData("Test");
			}
			approveDeclineQuotePage.declineButton.scrollToElement();
			approveDeclineQuotePage.declineButton.click();

			Assertions.addInfo("Scenario",
					"Verifying that the bind request declined successfully when producer uploaded subscription agreement not approved by USM");
			Assertions.verify(
					approveDeclineQuotePage.quoteDeclinedMessage.checkIfElementIsPresent()
							&& approveDeclineQuotePage.quoteDeclinedMessage.getData()
									.contains("The bind request has been declined."),
					true, "Approve decline quote page",
					"The bind request declined successfully when producer uploaded subscription agreement not approved by USM",
					false, false);
			Assertions.addInfo("Scenario", "Scenario Ended");

			// IO-21293/21667 Ended

			// sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC004 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC004 ", "Executed Successfully");
			}
		}
	}
}
