/** Program Description: 1. Verifying hard stop message for future policy date greater than 31 days from current date,validating diligence text, request received messages.
 2. Validating IO-20810(YOC Eligibility of GA)
*  Author			   : Priyanka S
*  Date of Creation   : 12/22/2021
**/
package com.icat.epicenter.test.naho.regression.ESBNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.ConfirmBindRequestPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class GATC001 extends AbstractNAHOTest {

	public GATC001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBNB/GATC001.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestSubmittedPage = new BindRequestSubmittedPage();
		ConfirmBindRequestPage confirmBindRequestPage = new ConfirmBindRequestPage();
		LoginPage loginPage = new LoginPage();

		// Initializing Variables
		Map<String, String> testData;
		int dataValue1 = 0;
		int dataValue2 = 1;
		String quoteNumber;
		testData = data.get(dataValue1);
		int locationCount = 1;
		int dwellingCount = 1;
		int locNo = 1;
		int bldgNo = 1;
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			homePage.effectiveDateField.scrollToElement();
			homePage.effectiveDateField.click();
			homePage.effectiveDateErrorMsg.waitTillVisibilityOfElement(60);

			// Asserting hard stop error message for Policy Effective date
			Assertions.addInfo("Scenario 01",
					"Verify hard stop error message is displayed invalid policy effective Date is displayed in Home page");
			Assertions.verify(
					homePage.effectiveDateErrorMsg.checkIfElementIsPresent()
							&& homePage.effectiveDateErrorMsg.checkIfElementIsDisplayed(),
					true, "Dwelling page", "Effective Date Error Message : " + homePage.effectiveDateErrorMsg.getData(),
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
			testData = data.get(dataValue2);
			if (homePage.effectiveDate.formatDynamicPath(1).checkIfElementIsPresent()
					&& homePage.effectiveDate.formatDynamicPath(1).checkIfElementIsDisplayed()) {
				homePage.effectiveDate.formatDynamicPath(1).scrollToElement();
				homePage.effectiveDate.formatDynamicPath(1).waitTillVisibilityOfElement(60);
				homePage.effectiveDate.formatDynamicPath(1).setData(testData.get("PolicyEffDate"));
			} else {
				homePage.effectiveDate.formatDynamicPath(2).scrollToElement();
				homePage.effectiveDate.formatDynamicPath(2).waitTillVisibilityOfElement(60);
				homePage.effectiveDate.formatDynamicPath(2).setData(testData.get("PolicyEffDate"));
			}
			homePage.goButton.click();
			homePage.loading.waitTillInVisibilityOfElement(60);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zipcode in Eligibility page
			testData = data.get(dataValue1);
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.addDwellingDetails(testData, locationCount, dwellingCount);
			dwellingPage.addRoofDetails(testData, locationCount, dwellingCount);
			dwellingPage.enterProtectionDiscountsDetails(testData, locationCount, dwellingCount);
			if (dwellingPage.internalSection.checkIfElementIsPresent()
					&& dwellingPage.internalSection.checkIfElementIsDisplayed()) {
				dwellingPage.internalSection.scrollToElement();
				dwellingPage.internalSection.click();
			}
			if (!testData.get("L" + locNo + "D" + bldgNo + "-ProtectionClassOverride").equals("")) {
				dwellingPage.enterInternalSectionDetails(testData, locationCount, dwellingCount);
			}

			dwellingPage.enterDwellingValuesNAHO(testData, locationCount, dwellingCount);
			waitTime(5); // Control is shifting to roof details link after entering
			// dwelling values instead of clicking on review
			// dwelling
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			Assertions.addInfo("Scanrio 02", "Verifying Warning Message Appears for the Year Built as USM");
			Assertions.verify(
					dwellingPage.dwellingDetailsErrorMessages.formatDynamicPath("Due to a year built prior to 1970")
							.checkIfElementIsPresent()
							&& dwellingPage.dwellingDetailsErrorMessages
									.formatDynamicPath("Due to a year built prior to 1970").checkIfElementIsDisplayed(),
					true, "Dwelling Page",
					"The warning message is displayed for the YOC prior to 1970: "
							+ dwellingPage.dwellingDetailsErrorMessages
									.formatDynamicPath("Due to a year built prior to 1970").getData(),
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			dwellingPage.override.scrollToElement();
			dwellingPage.override.click();
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering prior loss details
			testData = data.get(dataValue1);
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
				Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.click();
			}
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Binding the quote and creating the policy
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Clicking on answer No button
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered successfully");

			// Entering bind details
			// Entering policy details
			testData = data.get(dataValue2);
			requestBindPage.enterPolicyDetailsNAHO(testData);

			// Entering payment information
			testData = data.get(dataValue1);
			requestBindPage.enterPaymentInformationNAHO(testData);

			// Entering inspection details
			requestBindPage.addInspectionContact(testData);

			// Entering AI values
			requestBindPage.addAdditionalInterest(testData);

			// Entering contact information
			requestBindPage.addContactInformation(testData);
			Assertions.addInfo("Scenario 03", "Verify Diligence Text Message is displayed on Request Bind Page");
			Assertions.verify(requestBindPage.diligenceText.checkIfElementIsDisplayed(), true, "Request Bind page",
					"Requesting bind on Policy message is displayed", false, false);
			Assertions.verify(
					requestBindPage.dueDiligenceText.formatDynamicPath("minimum 25%").checkIfElementIsDisplayed(), true,
					"Request Bind page",
					"Diligence Message 1 : "
							+ requestBindPage.dueDiligenceText.formatDynamicPath("minimum 25%").getData(),
					false, false);
			Assertions.verify(
					requestBindPage.dueDiligenceText
							.formatDynamicPath("signed application").checkIfElementIsDisplayed(),
					true, "Request Bind page",
					"Diligence Message 2 : "
							+ requestBindPage.dueDiligenceText.formatDynamicPath("signed application").getData(),
					false, false);
			Assertions.verify(
					requestBindPage.dueDiligenceText.formatDynamicPath("signed diligent").checkIfElementIsDisplayed(),
					true, "Request Bind page",
					"Diligence Message 3 : "
							+ requestBindPage.dueDiligenceText.formatDynamicPath("signed diligent").getData(),
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");
			if (requestBindPage.submit.checkIfElementIsPresent()
					&& requestBindPage.submit.checkIfElementIsDisplayed()) {
				requestBindPage.submit.scrollToElement();
				requestBindPage.submit.click();
				confirmBindRequestPage.requestBindBtn.waitTillVisibilityOfElement(60);
				confirmBindRequestPage.requestBindBtn.scrollToElement();
				confirmBindRequestPage.requestBindBtn.click();
			}

			// Bind Request Submitted Page
			// Asserting Request Received Information
			Assertions.addInfo("Scenario 04", "Verify Request Received Message is displayed Request Submitted Page");
			Assertions.verify(
					bindRequestSubmittedPage.requestReceivedMsg.formatDynamicPath("Additional Insured")
							.checkIfElementIsDisplayed(),
					true, "Bind Request Submitted page",
					"Request Received 1 : " + bindRequestSubmittedPage.requestReceivedMsg
							.formatDynamicPath("Additional Insured").getData(),
					false, false);
			Assertions.verify(
					bindRequestSubmittedPage.requestReceivedMsg
							.formatDynamicPath("three or more").checkIfElementIsDisplayed(),
					true, "Bind Request Submitted page",
					"Request Received 2 : "
							+ bindRequestSubmittedPage.requestReceivedMsg.formatDynamicPath("three or more").getData(),
					false, false);
			Assertions.verify(
					bindRequestSubmittedPage.requestReceivedMsg.formatDynamicPath("trust").checkIfElementIsDisplayed(),
					true, "Bind Request Submitted page",
					"Request Received 3 : "
							+ bindRequestSubmittedPage.requestReceivedMsg.formatDynamicPath("trust").getData(),
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// SignIn as Producer
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));

			// creating New account
			testData = data.get(dataValue2);
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home page",
					"Producer home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New account", "New account created successfully");

			// Entering zipcode in Eligibility page
			testData = data.get(dataValue1);
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility page",
					"Eligibility page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.addDwellingDetails(testData, locationCount, dwellingCount);
			dwellingPage.addRoofDetails(testData, locationCount, dwellingCount);
			dwellingPage.enterProtectionDiscountsDetails(testData, locationCount, dwellingCount);
			if (dwellingPage.internalSection.checkIfElementIsPresent()
					&& dwellingPage.internalSection.checkIfElementIsDisplayed()) {
				dwellingPage.internalSection.scrollToElement();
				dwellingPage.internalSection.click();
			}
			if (!testData.get("L" + locNo + "D" + bldgNo + "-ProtectionClassOverride").equals("")) {
				dwellingPage.enterInternalSectionDetails(testData, locationCount, dwellingCount);
			}

			dwellingPage.enterDwellingValuesNAHO(testData, locationCount, dwellingCount);
			waitTime(5); // Control is shifting to roof details link after entering
			// dwelling values instead of clicking on review
			// dwelling
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			Assertions.addInfo("Scanrio 05", "Verifying Hard Stop Message Appears for the Year Built as Producer");
			Assertions.verify(
					dwellingPage.dwellingDetailsErrorMessages.formatDynamicPath("Due to a year built prior to 1970")
							.checkIfElementIsPresent()
							&& dwellingPage.dwellingDetailsErrorMessages
									.formatDynamicPath("Due to a year built prior to 1970").checkIfElementIsDisplayed(),
					true, "Dwelling Page",
					"The Hard Stop message is displayed for the YOC prior to 1970: "
							+ dwellingPage.dwellingDetailsErrorMessages
									.formatDynamicPath("Due to a year built prior to 1970").getData(),
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("GATC001 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("GATC001 ", "Executed Successfully");
			}
		}
	}
}