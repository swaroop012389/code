/**Program Description: Performing Validations for Year of Construction prior to 1970 and Increased Ordinance Law Additional Coverage in USM and Producer Login during various Transactions.
 * Author: Karthik Malles
 * Date of Creation: 26/11/2021
 */
package com.icat.epicenter.test.naho.regression.NSPNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExistingAccountPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBNCTC005_GEN extends AbstractNAHOTest {

	public PNBNCTC005_GEN() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSPNB/NC005.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page Objects
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuote = new ApproveDeclineQuotePage();
		ExistingAccountPage existingAccountPage = new ExistingAccountPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();

		// Initializing Variables
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		int data_Value4 = 3;
		String warningMessageDuringNBQuoteYearBuilt;
		String warningMessageDuringNBQuote;
		String warningMessageDuringPBEndorsement;
		String warningMessageDuringRenewalQuote;
		String hardStopMessageAsProducer;
		Map<String, String> testData = data.get(data_Value1);
		String quoteNumber;
		boolean isTestPassed = false;

		try {
			// Creating a New Account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("Home Page", "New Account Created successfully in USM Login");

			// Eligibility Page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zip Code entered successfully");

			// Dwelling Page
			Assertions.verify(dwellingPage.createQuote.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);

			dwellingPage.addDwellingDetails(testData, 1, 1);
			dwellingPage.addRoofDetails(testData, 1, 1);
			dwellingPage.enterProtectionDiscountsDetails(testData, 1, 1);
			dwellingPage.enterDwellingValuesNAHO(testData, 1, 1);
			dwellingPage.waitTime(5);
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			warningMessageDuringNBQuoteYearBuilt = dwellingPage.protectionClassWarMsg.getData();
			dwellingPage.override.scrollToElement();
			dwellingPage.override.click();
			if (existingAccountPage.override.checkIfElementIsPresent()
					&& existingAccountPage.override.checkIfElementIsDisplayed()) {
				existingAccountPage.OverrideExistingAccount();
			}
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.overrideNoLongerQuotableBuildings();
			}
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfuly");

			// Prior Losses Page
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Losses Page",
						"Prior Losses Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
				Assertions.passTest("Prior Losses Page", "Prior Losses details entered successfully");
			}

			// Create Quote Page
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);

			// Clicked on Get A Quote
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on Get A Quote Button successfully");

			// Getting Warning Message
			warningMessageDuringNBQuote = createQuotePage.warningMessages.formatDynamicPath("roof age").getData();

			// Asserting Roof age warning message when year built older 40 Years
			// Year built = 1969
			Assertions.addInfo("Scenario 01", "Verifying roof age warning message");
			Assertions.verify(createQuotePage.warningMessages.formatDynamicPath("roof age").checkIfElementIsDisplayed(),
					true, "Create quote page",
					"Account is ineligible due to an roof age warning message is '"
							+ createQuotePage.warningMessages.formatDynamicPath("roof age").getData() + "' is verified",
					false, false);

			// Clicked on Continue Button
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();

			// Assert the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number : " + quoteNumber);

			// Click on Request bind
			accountOverviewPage.clickOnRequestBind(testData, accountOverviewPage.quoteNumber.getData());
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind successfully");

			// Underwriting Questions Page
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Answered to all Questions successfully");

			// Request Bind Page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Details entered successfully");

			// Policy Summary Page
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);
			policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.passTest("Policy Summary Page",
					"Policy generated successfully. The policy Number is: " + policyNumber);

			// Perform PB Endorsement
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Endorse PB link is clicked");

			// Endorse Policy Page
			testData = data.get(data_Value2);
			Assertions.verify(endorsePolicyPage.cancelButton.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Endorse Policy Page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("EndorsementEffectiveDate"));
			Assertions.passTest("Endorse Policy Page", "Endorsement Effective Date entered successfully");

			// Selecting Administrative Transaction Check Box
			endorsePolicyPage.administrativeTransaction.scrollToElement();
			endorsePolicyPage.administrativeTransaction.select();

			// This Wait Time have to be added for the Page to be loaded after Entering
			// Endorsement Effective Date.
			// If this Wait Time is not added Script fails here
			endorsePolicyPage.waitTime(2);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();

			// Create Quote Page while PB Endorsement
			// Selecting Ordinance Or Law Coverage during PB Endorsement
			Assertions.verify(createQuotePage.continueEndorsementBtn.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page loaded successfully during PB Endorsement", false, false);
			createQuotePage.ordinanceLawArrow_NAHO.waitTillPresenceOfElement(60);
			createQuotePage.ordinanceLawArrow_NAHO.scrollToElement();
			createQuotePage.ordinanceLawArrow_NAHO.click();
			createQuotePage.ordinanceLawOption.formatDynamicPath(testData.get("OrdinanceOrLaw"))
					.waitTillPresenceOfElement(60);
			createQuotePage.ordinanceLawOption.formatDynamicPath(testData.get("OrdinanceOrLaw")).scrollToElement();
			createQuotePage.ordinanceLawOption.formatDynamicPath(testData.get("OrdinanceOrLaw")).click();

			// Clicked on Continue Button
			createQuotePage.continueEndorsementBtn.scrollToElement();
			createQuotePage.continueEndorsementBtn.click();
			Assertions.passTest("Create Quote Page", "Clicked on Continue Button successfully");

			// Getting Warning Message for PB Endorsement
			warningMessageDuringPBEndorsement = createQuotePage.warningMessages.formatDynamicPath("roof age").getData();

			// Checking for the presence of same Warning Message in PB Endorsement as in New
			// Business Quoting
			Assertions.addInfo("Scenario 02",
					"Checking for the same Warning Message wordings While NB Quoting and while performing PB Endorsement");
			Assertions.verify(warningMessageDuringNBQuote, warningMessageDuringPBEndorsement, "Create Quote Page",
					"New Business - Create Quote Page and PB Endorsement - Create Quote Page has same Warning Message displayed",
					false, false);

			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Continue Button successfully");

			// Clicked on Next Button
			Assertions.verify(endorsePolicyPage.nextButton.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Summary of Endorsement section is loaded successfully", false, false);
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Next Button is clicked successfully");

			// Clicked on Ok,Continue Button
			endorsePolicyPage.oKContinueButton.scrollToElement();
			endorsePolicyPage.oKContinueButton.click();
			Assertions.passTest("Endorse Policy Page", "Ok, Continue Button is clicked successfilly");

			// Clicked on Complete and Close Button
			Assertions.verify(endorsePolicyPage.completeButton.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Summary of Changes loaded successfully", false, false);
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Complete Button clicked successfully");
			endorsePolicyPage.closeButton.waitTillPresenceOfElement(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Click on Renewal link
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Renew Policy link clicked successfully");
			testData = data.get(data_Value1);

			// Adding Expacc Details
			if (policySummaryPage.expaacMessage.checkIfElementIsPresent()
					&& policySummaryPage.expaacMessage.checkIfElementIsDisplayed()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.click();

				// Enter expacc details
				expaccInfoPage.enterExpaccInfo(testData, policyNumber);
				Assertions.passTest("ExpaccInfo page", "ExpaccInfo details entered successfully");

				// Go to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();

				// Performing Renewal Searches
				homePage.searchPolicy(policyNumber);

				// clicking on renewal policy link
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();
				Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy link successfully");

			}

			// Account Overview Page while Renewing Policy
			Assertions.verify(accountOverviewPage.openReferral.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();
			Assertions.passTest("Account Overview Page", "Create Another Quote Button clicked successfully");

			// Checking for the Absence of Ordinance Or Law Validation Message Under
			// Producer Comments
			Assertions.addInfo("Scenario 03", "Checking for the absence of Vaidation Message during Renewal Review");
			Assertions.verify(
					referralPage.producerCommentsProducerSection.getData()
							.contains("year of construction prior to 1970"),
					false, "Referral Page",
					"Ordinance Or Law Warning Message is Not present under Producer Comments Section", false, false);
			if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
				referralPage.pickUp.scrollToElement();
				referralPage.pickUp.click();
				Assertions.passTest("Referral Page", "Clicked on Pick Up Button");
			}

			// Clicked on Approve/Decline Request
			referralPage.approveOrDeclineRequest.waitTillVisibilityOfElement(60);
			referralPage.approveOrDeclineRequest.scrollToElement();
			referralPage.approveOrDeclineRequest.click();
			Assertions.passTest("Referral Page", "Approve/Decline Request Button clicked successfully");

			// Approve/Decline Quote Page
			Assertions.verify(approveDeclineQuote.approveButton.checkIfElementIsDisplayed(), true,
					"Approve/Decline Quote Page", "Approve/Decline Quote Page loaded successfully", false, false);
			approveDeclineQuote.internalUnderwriterComments.clearData();
			approveDeclineQuote.internalUnderwriterComments.setData(testData.get("ExtendedNamedInsured"));
			approveDeclineQuote.approveButton.scrollToElement();
			approveDeclineQuote.approveButton.click();
			Assertions.passTest("Approve/Declice Quote Page", "Approve Button clicked sucessfully");

			// Referral Complete
			referralPage.close.waitTillVisibilityOfElement(60);
			referralPage.close.scrollToElement();
			referralPage.close.click();

			// Home Page
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.searchPolicy(policyNumber);

			// Policy Summary Page
			Assertions.verify(policySummaryPage.viewActiveRenewal.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "Policy Summary Page loaded successfully", false, false);
			policySummaryPage.viewActiveRenewal.scrollToElement();
			policySummaryPage.viewActiveRenewal.click();

			// Account Overview Page and assert the renewal quote number
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "The Renewal Quote Number : " + quoteNumber);
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();

			// Create Quote Page
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully during Renewal ReQuoting", false, false);

			// Selecting Ordinance Or Law Coverage in Renewal ReQuote
			testData = data.get(data_Value3);
			createQuotePage.ordinanceLawArrow_NAHO.waitTillPresenceOfElement(60);
			createQuotePage.ordinanceLawArrow_NAHO.scrollToElement();
			createQuotePage.ordinanceLawArrow_NAHO.click();
			createQuotePage.ordinanceLawOption.formatDynamicPath(testData.get("OrdinanceOrLaw"))
					.waitTillPresenceOfElement(60);
			createQuotePage.ordinanceLawOption.formatDynamicPath(testData.get("OrdinanceOrLaw")).scrollToElement();
			createQuotePage.ordinanceLawOption.formatDynamicPath(testData.get("OrdinanceOrLaw")).click();

			// Clicked on Get A Quote Button
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on Continue Button successfully");

			// Getting Warning Message for Renewal ReQuote
			warningMessageDuringRenewalQuote = createQuotePage.warningMessages.formatDynamicPath("roof age").getData();

			// Checking for the presence of same Warning Message in Renewal ReQuoting as in
			// New Business Quoting
			Assertions.addInfo("Scenario 04",
					"Checking for the same Warning Message wordings While NB Quoting and while Renewal ReQuoting");
			Assertions.verify(warningMessageDuringNBQuote, warningMessageDuringRenewalQuote, "Create Quote Page",
					"New Business - Create Quote Page and Renewal ReQuoting - Create Quote Page has same Warning Message displayed",
					false, false);

			// Sign Out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Create Quote Page", "Sign Out Button successfully clicked from USM Login");

			// Login In as Producer
			Assertions.verify(loginPage.signInButton.checkIfElementIsDisplayed(), true, "Login Page",
					"Login Page loaded successfully", false, false);
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Log In Details entered successfully");

			// Home Page of Producer
			testData = data.get(data_Value4);
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("Home Page", "New Account Created successfully as a Producer");

			// Eligibility Page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zip Code entered successfully");

			// Dwelling Page
			Assertions.verify(dwellingPage.createQuote.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfuly");

			// Checking Hard Stop Message
			Assertions.addInfo("Scenario 05",
					"Checking for the display of Hard Stop Message in Create Quote Page as Producer");
			Assertions.verify(createQuotePage.globalErr.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Hard Stop Message is displayed successfully ", false, false);
			hardStopMessageAsProducer = createQuotePage.globalErr.getData();

			// Checking for the presence of same wordings in Error Message of NB Quote in
			// Producer Login and Warning Message of NB Quote in USM Login
			Assertions.addInfo("Scenario 06",
					"Checking whether Hard Message Message displayed during NB Quoting as Producer and Warning Message displayed during NB Quoting in Create Quote Page as USM are same");
			Assertions.verify(hardStopMessageAsProducer, warningMessageDuringNBQuoteYearBuilt, "Create Quote Page",
					"Hard Stop Message in New Business - Create Quote Page as Producer and Warning Message in New Business - Create Quote Page as USM has same wordings",
					false, false);
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBNCTC005 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBNCTC005 ", "Executed Successfully");
			}
		}
	}
}