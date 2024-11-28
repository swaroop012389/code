/** Program Description: Checking for the Warning Message while creation of SC State NB Policy and Renewal Policy when
 * Earth Quake Prior Loss and Earth Quake Deductibles both are chosen (IO-19489)
 *  Author			   : Karthik Malles
 *  Date of Creation   : 10/18/2021
 **/
package com.icat.epicenter.test.naho.regression.NSPNB;

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
import com.icat.epicenter.pom.PolicyDocumentsPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBSCTC006_RNL extends AbstractNAHOTest {

	public PNBSCTC006_RNL() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSPNB/SC006.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
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
		ReferralPage referralPage = new ReferralPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ApproveDeclineQuotePage approveDeclineQuote = new ApproveDeclineQuotePage();

		// Declaring Variables
		String quoteNumber;
		String policyNumber;
		int quoteLen;
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;

		Map<String, String> testData = data.get(data_Value1);
		String locationNumber = testData.get("LocCount");
		int locationCount = Integer.parseInt(locationNumber);
		String dwellingNumber = testData.get("L" + locationCount + "-DwellingCount");
		int dwellingCount = Integer.parseInt(dwellingNumber);
		boolean isTestPassed = false;

		try {
			// Creating a New Account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page Loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("Home Page", "New Account created successfully");

			// Entering Zipcode
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibilty Page",
					"Eligibilty Page is Loaded Successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode Entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.createQuote.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page Loaded Successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling Details are Entered successfully");

			// Entering Prior Loss Details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Losses Page",
						"Prior Loss Page Loaded Successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
				Assertions.passTest("Prior Loss Page", "Prior Loss Details are entered successfully");
			}

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page Loaded Successfully", false, false);
			createQuotePage.enterInsuredValuesNAHO(testData, locationCount, dwellingCount);
			createQuotePage.enterDeductiblesNAHO(testData);
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);
			if (createQuotePage.getAQuote.checkIfElementIsPresent()
					&& createQuotePage.getAQuote.checkIfElementIsDisplayed()) {
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();
			}

			// Code for IO-19701
			// Checking for Validation Warning Message for Roof Age year built = 2000 and
			// roof cladding = Tile or Clay
			Assertions.addInfo("Scenario 01",
					"Checking for Validation Warning Message when Roof Age = 2000 of Tile Or Clay");
			Assertions.verify(
					createQuotePage.warningMessages
							.formatDynamicPath("ineligible due to the roof age").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Validation Warning Message for Roof Age of Tile Or Clay is displayed correctly as: "
							+ createQuotePage.warningMessages.formatDynamicPath("ineligible due to the roof age")
									.getData(),
					false, false);
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
			Assertions.passTest("Create Quote Page", "Quote Details Entered Successfully");

			// Account Overview Page
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Filling Answers in Underwriting Questions Page
			Assertions.verify(underwritingQuestionsPage.saveButton.checkIfElementIsDisplayed(), true,
					"Underwritng Questions Page", "Underwriting Questions Page Loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page",
					"Answers for Underwriting Questions are entered successfully");

			// Entering Details in Request Bind Page
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Entered Details in Request Bind Page successfully");

			// Approve Bind Request
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.passTest("Request Bind Page", "Home Page Link clicked successfully");
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Searched Submitted Bind Request successfully");

			// Opening Bind Referred Quote to Approve Bind Request
			Assertions.verify(accountOverviewPage.openReferral.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded Successfully", false, false);
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();
			Assertions.passTest("Account Overview Page", "Open Referral Link clicked successfully");

			// Referral Page
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral Page Loaded Successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral Request Approved Successfully");
			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page Loaded Sucessfully", false, false);
			if (requestBindPage.chooseFile.checkIfElementIsPresent()
					&& requestBindPage.chooseFile.checkIfElementIsDisplayed()) {
				if (!testData.get("FileNameToUpload").equals("")) {
					PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
					policyDocumentsPage.fileUpload(testData.get("FileNameToUpload"));
				}
			}
			requestBindPage.approve.scrollToElement();
			requestBindPage.approve.click();
			Assertions.passTest("Request Bind Page", "Approve Button in Request Bind Page clicked successfully");

			// Policy Summary Page
			Assertions.verify(policySummaryPage.renewPolicy.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page is loaded Successfully", false, false);
			policyNumber = policySummaryPage.policyNumber.getData();
			policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.passTest("Policy Summary Page",
					"Policy generated successfully. The policy Number is: " + policyNumber);

			// Code for IO-19701
			// Checking for Validation Warning Message for Roof Age of Tile or Clay
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

			// This Wait Time have to be added for the Page to be loaded after
			// Entering
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

			// Waiting for Warning Message to be loaded
			createQuotePage.continueButton.waitTillVisibilityOfElement(60);

			// Checking for Warning Message while performing PB Endorsement
			Assertions.addInfo("Scenario 03",
					"Checking the Validation Warning Message while performing PB Endorsement in Create Quote Page");
			Assertions.verify(
					createQuotePage.warningMessages
							.formatDynamicPath("ineligible due to the roof age").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Validation Warning Message for Roof Age of Tile or Clay is displayed correctly. The Message is: "
							+ createQuotePage.warningMessages.formatDynamicPath("ineligible due to the roof age")
									.getData(),
					false, false);

			// Clicked on Continue Button
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Continue Button successfully");

			// Clicked on Next Button
			Assertions.verify(endorsePolicyPage.nextButton.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Summary of Endorsement section is loaded successfully", false, false);
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Next Button is clicked successfully");

			// Clicked on Ok, Continue Button
			endorsePolicyPage.oKContinueButton.scrollToElement();
			endorsePolicyPage.oKContinueButton.click();
			Assertions.passTest("Endorse Policy Page", "Ok, Continue Button is clicked successfilly");

			// Clicked on Continue Button and Close Button
			Assertions.verify(endorsePolicyPage.completeButton.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Summary of Changes loaded successfully", false, false);
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Complete Button clicked successfully");
			endorsePolicyPage.closeButton.waitTillPresenceOfElement(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Clicked on Renew Policy Link
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Renew Policy link clicked successfully");

			// Taking the Values for Expacc Details from Excel Sheet
			testData = data.get(data_Value1);

			// Adding Expacc Details
			if (policySummaryPage.expaacMessage.checkIfElementIsPresent()
					&& policySummaryPage.expaacMessage.checkIfElementIsDisplayed()) {

				// Navigating to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				Assertions.passTest("Policy Summary Page", "Home Page Link clicked successfully");

				// Opening Expacc Link
				Assertions.verify(homePage.expaccLink.checkIfElementIsDisplayed(), true, "Home Page",
						"Home Page is loaded successfully", false, false);
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.click();
				Assertions.passTest("Home Page", "Expacc Link clicked successfully");

				// Filling Expacc Details
				Assertions.verify(expaccInfoPage.submit.checkIfElementIsDisplayed(), true, "ExpaccInfo Page",
						"ExpaccInfo Page loaded successfully", false, false);
				expaccInfoPage.enterExpaccInfo(testData, policyNumber);
				Assertions.passTest("ExpaccInfo Page", "Expacc Information added successfully");

				// Navigating to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				Assertions.passTest("ExpaccInfo Page", "Home Page Link clicked successfully");

				// Searching for Policy to Renew the Policy
				homePage.searchPolicy(policyNumber);
				Assertions.passTest("Home Page", "Searched for the Policy Number successfully");

				// Renewing Policy
				Assertions.verify(policySummaryPage.renewPolicy.checkIfElementIsDisplayed(), true,
						"Policy Summary Page", "Policy Summary Page loaded successfully", false, false);
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();
				Assertions.passTest("Policy Summary Page", "Renew Policy Link clicked successfully");
			}

			// Account Overview Page while Renewing Policy
			Assertions.verify(accountOverviewPage.openReferral.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();
			Assertions.passTest("Account Overview Page", "Create Another Quote Button clicked successfully");

			// Referral Page
			if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
				referralPage.pickUp.scrollToElement();
				referralPage.pickUp.click();
				Assertions.passTest("Referral Page", "Clicked on Pick Up Button");
			}

			// Checking for the Presence of Ineligible due to Roof Age Validation
			// Message Under
			// Producer Comments
			Assertions.addInfo("Scenario 04",
					"Checking for the presence of Vaidation Message during Renewal Review for Roof Age of Tile or Clay");
			Assertions.verify(
					referralPage.producerCommentsProducerSection.getData().contains("ineligible due to the roof age"),
					true, "Referral Page",
					"Ordinance Or Law Warning Message is Not present under Producer Comments Section", false, false);

			// Clicked on Approve/Decline Request Button
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
			Assertions.passTest("Policy Summary Page", "Clicked on View Active Renewal Link");

			// Account Overview Page
			Assertions.verify(accountOverviewPage.editDeductibleAndLimits.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded Successfully", false, false);
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account Overview Page", "Edit Deductibles Link clicked successfully");

			// Create Quote Page
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page Loaded", false, false);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Get A Quote Button clicked successfully");

			// Checking for Validation Warning Message in Create Quote Page while
			// Renewal
			// ReQuoting
			Assertions.addInfo("Scenario 06",
					"Checking for the Validation Warning Message for Roof Age of Tile or Clay");
			Assertions.verify(
					createQuotePage.warningMessages.formatDynamicPath("ineligible due to the roof age")
							.checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Validation Warning Message is displayed correctly as: " + createQuotePage.warningMessages
							.formatDynamicPath("ineligible due to the roof age").getData(),
					false, false);

			// Sign Out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Create Quote Page", "Sign Out Button successfully clicked from USM Login");

			// Log in as Producer
			Assertions.verify(loginPage.signInButton.checkIfElementIsDisplayed(), true, "Login Page",
					"Login Page loaded successfully", false, false);
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Log In Details entered successfully");

			// Home Page of Producer
			testData = data.get(data_Value3);
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

			// Clicked on Get A Quote Button
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on Get A Quote Button successfully");

			// Checking for Validation Hard Stop Message for Roof Age of Tile or
			// Clay
			Assertions.addInfo("Scenario 07",
					"Checking for the display of Validation Hard Stop Message for Roof Age of Tile or Clay in Create Quote Page as Producer");
			Assertions.verify(
					createQuotePage.aopNSEQData
							.formatDynamicPath("ineligible due to the roof age").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Hard Stop Message is displayed correctly as: "
							+ createQuotePage.aopNSEQData.formatDynamicPath("ineligible due to the roof age").getData(),
					false, false);
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBSCTC006 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBSCTC006 ", "Executed Successfully");
			}
		}
	}
}
