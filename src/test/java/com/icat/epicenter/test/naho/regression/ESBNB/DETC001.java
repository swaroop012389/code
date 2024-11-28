/** Program Description:1. Asserting state specific wording, diligence text and SLTF values for DE state
 * 						2. Adding validation for IO-20480 - Inspection contact and phone number should be mandatory in
 * 						   NAHO bind page even if property is newer than 5 years
 * 					    3. Adding Ticket IO-20812 (Architectural Shingle) and IO-21792
 *  Author			   : Pavan Mule
 *  Date of Creation   : 12/20/2021
 **/

package com.icat.epicenter.test.naho.regression.ESBNB;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class DETC001 extends AbstractNAHOTest {

	public DETC001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBNB/DETC001.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		LoginPage loginPage = new LoginPage();

		// Initializing Variables
		Map<String, String> testData;
		int dataValue1 = 0;
		int dataValue2 = 1;
		testData = data.get(dataValue1);
		int quoteLen;
		String quoteNumber;
		String premiumAmount;
		String icatFees;
		String surplusContribution;
		String actualSLTF;
		String sltfPercentage;
		String policyNumber;
		double d_premiumAmount;
		double d_icatFees;
		double d_surplusContribution;
		double d_calculatedSLTF;
		double d_sltfPercentage;
		double d_actualSLTF;
		String locationNumber = testData.get("LocCount");
		int locationCount = Integer.parseInt(locationNumber);
		String dwellingNumber = testData.get("L" + locationCount + "-DwellingCount");
		int dwellingCount = Integer.parseInt(dwellingNumber);
		int locNo = Integer.parseInt(locationNumber);
		int bldgNo = Integer.parseInt(dwellingNumber);
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
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
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
				Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Asserting Quote Number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Added IO-21863

			// Clicking on request e-signtaure link present on account overview page.
			accountOverviewPage.requestESignature.checkIfElementIsDisplayed();
			accountOverviewPage.requestESignature.scrollToElement();
			accountOverviewPage.requestESignature.click();

			// Entering email and comments on request e-signature page.
			accountOverviewPage.signatureEmailBox.checkIfElementIsDisplayed();
			accountOverviewPage.signatureEmailBox.scrollToElement();
			accountOverviewPage.signatureEmailBox.setData("automationuser@smnetserv.com");

			accountOverviewPage.signatureCommentBox.scrollToElement();
			accountOverviewPage.signatureCommentBox.setData("test");

			// Checking send button is in disabled state when particular document is not
			// selected.
			Assertions.verify(accountOverviewPage.disbaledDocusignSubmitBtn.checkIfElementIsDisplayed(), true,
					"Request E-Signature Page",
					"Docusign send button is disabled and verified successfully, when respective document is not selected on the request e-signature page ",
					false, false);

			accountOverviewPage.subscriptionAgreementCheckBox.scrollToElement();
			accountOverviewPage.subscriptionAgreementCheckBox.select();

			// checking the send button is not disabled when respective document is
			// selected.
			Assertions.verify(accountOverviewPage.disbaledDocusignSubmitBtn.checkIfElementIsPresent(), false,
					"Request E-Signature Page",
					"Docusign send button is not disabled and verified successfully, when respective document is selected on the request e-signature page ",
					false, false);

			// clicking on send button on request e-signature page
			accountOverviewPage.signatureSendButton.scrollToElement();
			accountOverviewPage.signatureSendButton.click();

			// getting the successs message
			accountOverviewPage.docusignSuccessMessage.checkIfElementIsDisplayed();
			Assertions.passTest("Request E-Signature Page",
					"The Success Message is: " + accountOverviewPage.docusignSuccessMessage.getData());

			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			homePage.searchQuote(quoteNumber);

			// IO-21863 Ended

			// Click on View/Print full quote page
			accountOverviewPage.viewPrintFullQuoteLink.waitTillPresenceOfElement(60);
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print Full Quote link successfully");

			// Asserting State Specific Delware words
			Assertions.addInfo("Scenario 01", "Asserting state specific words on View/Print Full Quote Page");
			Assertions.verify(
					viewOrPrintFullQuotePage.stateSpecificwords.formatDynamicPath("licensed by this State")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.stateSpecificwords.formatDynamicPath("licensed by this State")
									.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"State specific words displayed is " + viewOrPrintFullQuotePage.stateSpecificwords
							.formatDynamicPath("licensed by this State").getData(),
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.stateSpecificwords.formatDynamicPath("state insurance guaranty fund")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.stateSpecificwords
									.formatDynamicPath("state insurance guaranty fund").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"State specific words displayed is " + viewOrPrintFullQuotePage.stateSpecificwords
							.formatDynamicPath("state insurance guaranty fund").getData(),
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// click on back button
			viewOrPrintFullQuotePage.backButton.waitTillPresenceOfElement(60);
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.passTest("View Print Full Quote", "Clicked on Back Button");

			// Calculating SLTF Value with 3% of Total Premium =premium+policy fee +
			// inspection fee)
			premiumAmount = accountOverviewPage.premiumValue.getData().replace("$", " ").replace(",", "");
			icatFees = accountOverviewPage.feesValue.getData().replace("$", " ").replace(",", "");
			surplusContribution = accountOverviewPage.surplusContibutionValue.getData().replace("$", "");
			actualSLTF = accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", "");
			d_actualSLTF = Double.parseDouble(actualSLTF);
			sltfPercentage = testData.get("SLTFPercentage");

			// Printing values from account overview page
			Assertions.addInfo("Account Overview Page", "Asserting Premium, Icat fees, SLTF on account overview page");
			Assertions.passTest("Account Overview Page", "Premium is $" + premiumAmount);
			Assertions.passTest("Account Overview Page", "ICAT Fees is $" + icatFees);
			Assertions.passTest("Account Overview Page", "Actual SLTF value is $" + d_actualSLTF);

			// Converting string values to double
			d_premiumAmount = Double.parseDouble(premiumAmount);
			d_icatFees = Double.parseDouble(icatFees);
			d_surplusContribution = Double.parseDouble(surplusContribution);
			d_sltfPercentage = Double.parseDouble(sltfPercentage);
			d_calculatedSLTF = (d_premiumAmount + d_icatFees + d_surplusContribution) * (d_sltfPercentage / 100);

			// Verifying Actual SLTF and Calculated SLTF Values on Account Overview Page
			Assertions.addInfo("Scenario 02",
					"Verifying actual SLTF and calculated SLTF values on Account Overview Page");
			if (Precision.round(Math.abs(Precision.round(d_actualSLTF, 2) - Precision.round(d_calculatedSLTF, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated SLTF Value :  " + "$" + Precision.round(d_calculatedSLTF, 2));
				Assertions.passTest("Account Overview Page",
						"Actual SLTF Value : " + "$" + Precision.round(d_calculatedSLTF, 2));
			} else {
				Assertions.verify(d_actualSLTF, d_calculatedSLTF, "Account Overview Page",
						"The Difference between actual SLTF value and calculated SLTF value is more than 0.05", false,
						false);
			}
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Click on Request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Clicking on answer no button
			underwritingQuestionsPage.answerNoToAllQuestions.waitTillVisibilityOfElement(60);
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.answerNoToAllQuestions.scrollToElement();
			underwritingQuestionsPage.answerNoToAllQuestions.click();
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered successfully");

			// Clicking on save button
			underwritingQuestionsPage.saveButton.scrollToElement();
			underwritingQuestionsPage.saveButton.click();

			// Navigating to Request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);

			// Asserting Diligence text
			requestBindPage.scrollToBottomPage();
			Assertions.addInfo("Scenario 03", "Verify diligence text message is displayed on Request Bind Page");
			Assertions.verify(requestBindPage.diligenceText.checkIfElementIsDisplayed(), true, "Request Bind page",
					"Requesting bind on policy message is displayed", false, false);
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
			Assertions.verify(requestBindPage.dueDiligenceText.formatDynamicPath("SL-1923").checkIfElementIsDisplayed(),
					true, "Request Bind Page",
					"Diligence Message 3 : " + requestBindPage.dueDiligenceText.formatDynamicPath("SL-1923").getData(),
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Entering bind details
			quoteNumber = requestBindPage.quoteNumber.getData();
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Details Entered successfully");

			// Adding validation for IO-20480 - <NAHO> Inspection contact and phone number
			// should be mandatory in NAHO bind page even if property is newer than 5 years
			Assertions.addInfo("Scenario 04",
					"Asserting Error messages for Inspection Contact Name and Phone Number fileds");
			requestBindPage.inspectionContactNameErrorMsg.scrollToElement();
			Assertions.verify(
					requestBindPage.inspectionContactNameErrorMsg.checkIfElementIsPresent()
							&& requestBindPage.inspectionContactNameErrorMsg.checkIfElementIsDisplayed(),
					true, "Request Bind Page", "Inspection Contact Name Error Mesage is dispalyed as : "
							+ requestBindPage.inspectionContactNameErrorMsg.getData(),
					false, false);
			Assertions.verify(
					requestBindPage.inspectionContactPhoneErrorMsg.checkIfElementIsPresent()
							&& requestBindPage.inspectionContactPhoneErrorMsg.checkIfElementIsDisplayed(),
					true, "Request Bind Page", "Inspection Contact Phone Number Error Mesage is dispalyed as : "
							+ requestBindPage.inspectionContactPhoneErrorMsg.getData(),
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Adding inspection contact details
			testData = data.get(dataValue2);
			requestBindPage.addInspectionContact(testData);

			// Click on Submit Button
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();

			// Click on RequestBind button
			requestBindPage.requestBind.waitTillPresenceOfElement(60);
			requestBindPage.requestBind.waitTillVisibilityOfElement(60);
			if (requestBindPage.requestBind.checkIfElementIsPresent()
					&& requestBindPage.requestBind.checkIfElementIsDisplayed()) {
				requestBindPage.requestBind.scrollToElement();
				requestBindPage.requestBind.click();
				requestBindPage.requestBind.waitTillInVisibilityOfElement(60);
			}

			// Asserting policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is : " + policyNumber, false, false);

			// Asserting Status of Policy
			Assertions.verify(policySummaryPage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy status is : " + policySummaryPage.policyStatus.getData(), false, false);

			// Clicking on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Adding Ticket IO-20812(Architectural Shingle Validations)
			// Logging in as Producer
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));

			// creating New account
			testData = data.get(dataValue1);
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
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
				Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterInsuredValuesNAHO(testData, locationCount, dwellingCount);
			createQuotePage.enterDeductiblesNAHO(testData);
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			Assertions.addInfo("Scenario 05", "Change Roof Year and Assert Roof Age Message");
			for (int i = 1; i <= 13; i++) {
				testData = data.get(i);
				if (createQuotePage.previous.checkIfElementIsPresent()
						&& createQuotePage.previous.checkIfElementIsDisplayed()) {
					createQuotePage.previous.waitTillVisibilityOfElement(60);
					createQuotePage.previous.scrollToElement();
					createQuotePage.previous.click();
				}

				// Edit dwelling and update Year built and roof cladding
				if (accountOverviewPage.editDwelling.checkIfElementIsPresent()
						&& accountOverviewPage.editDwelling.checkIfElementIsDisplayed()) {
					accountOverviewPage.editDwelling.scrollToElement();
					accountOverviewPage.editDwelling.click();
				}

				// Entering Location 1 Dwelling 1 Details
				if (!testData.get("L" + locNo + "D" + bldgNo + "-DwellingYearBuilt").equals("")) {
					dwellingPage.yearBuilt.scrollToElement();
					dwellingPage.yearBuilt.clearData();
					if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()
							&& dwellingPage.expiredQuotePopUp.checkIfElementIsDisplayed()) {
						dwellingPage.expiredQuotePopUp.waitTillPresenceOfElement(60);
						dwellingPage.expiredQuotePopUp.scrollToElement();
						dwellingPage.continueWithUpdateBtn.scrollToElement();
						dwellingPage.continueWithUpdateBtn.click();

					}
					dwellingPage.yearBuilt.setData(testData.get("L" + locNo + "D" + bldgNo + "-DwellingYearBuilt"));
					dwellingPage.yearBuilt.tab();
				}
				dwellingPage.addRoofDetails(testData, locationCount, dwellingCount);
				waitTime(5); // Control is shifting to roof details link after entering
								// dwelling values instead of clicking on review
								// dwelling
				Assertions.passTest("Dwelling Page",
						"Roof Cladding is updated to " + testData.get("L1D1-DwellingRoofCladding")
								+ " and Year Built is updated to " + testData.get("L1D1-DwellingYearBuilt"));
				dwellingPage.reviewDwelling.scrollToElement();
				dwellingPage.reviewDwelling.click();
				dwellingPage.createQuote.scrollToElement();
				dwellingPage.createQuote.click();

				// Entering Create quote page Details
				createQuotePage.scrollToBottomPage();
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();

				// Asserting HardStop message for Producer
				String yearBuilt = testData.get("L1D1-DwellingYearBuilt");
				int yearBuiltValue = Integer.parseInt(yearBuilt);
				int currentYear = Calendar.getInstance().get(Calendar.YEAR);
				int diffInYears = (currentYear - yearBuiltValue);
				Assertions.passTest("Dwelling Page", "The Year Difference  is: " + diffInYears);

				if (diffInYears < 15 || diffInYears == 15) {
					// Fetching Quote Number
					quoteLen = accountOverviewPage.quoteNumber.getData().length();
					quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);

					// Printing Quote Number
					Assertions.passTest("Account Overview Page", "The Quote Number: " + quoteNumber);

					// Clicking on edit dwelling button
					accountOverviewPage.editDwelling.waitTillVisibilityOfElement(60);
					accountOverviewPage.editDwelling.scrollToElement();
					accountOverviewPage.editDwelling.click();
				}

				else if (diffInYears >= 16 && diffInYears <= 25) {
					Assertions.verify(
							referQuotePage.roofReferralMessage.checkIfElementIsPresent()
									&& referQuotePage.roofReferralMessage.checkIfElementIsDisplayed(),
							true, "Refer Quote Page",
							referQuotePage.roofReferralMessage.getData() + " is displayed when Roof Cladding is "
									+ testData.get("L1D1-DwellingRoofCladding") + " and Year Built is "
									+ testData.get("L1D1-DwellingYearBuilt"),
							false, false);

					// Referring the Quote to USM
					referQuotePage.contactEmail.clearData();
					referQuotePage.contactEmail.setData("hiho1@icat.com");
					referQuotePage.comments.setData("Test");
					referQuotePage.referQuote.scrollToElement();
					referQuotePage.referQuote.click();

					// Fetching Quote Number
					quoteNumber = referQuotePage.quoteNumberforReferral.getData();

					// Signing out as Producer
					homePage.signOutButton.scrollToElement();
					homePage.signOutButton.click();
					Assertions.passTest("Home Page", "Sign out as producer successfully");

					// Signing in as USM
					loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
					Assertions.passTest("Login Page", "Loged in as USM successfully");

					// Searching the referred quote
					homePage.searchQuote(quoteNumber);
					Assertions.passTest("Home Page", "Referred Quote searched successfully");

					// Opening the referral
					accountOverviewPage.openReferral.scrollToElement();
					accountOverviewPage.openReferral.click();

					if (referralPage.pickUp.checkIfElementIsPresent()
							&& referralPage.pickUp.checkIfElementIsDisplayed()) {
						referralPage.pickUp.scrollToElement();
						referralPage.pickUp.click();
					}

					// Approving the referral
					// IO-21792-As part of this our expectation is to validate if the referred quote
					// is assigned to USM not "Holder RMS"
					if (referralPage.assignedUser.getData().contentEquals("Sean Minn")) {

						Assertions.verify(referralPage.assignedUser.getData().contains("Sean Minn"), true,
								"Referral Page",
								"Quote is referred USM is ' " + referralPage.assignedUser.getData() + " '", false,
								false);
					} else {

						Assertions.verify(referralPage.assignedUser.getData().contains("Holder RMS"), false,
								"Referral Page",
								"Quote is referred USM is ' " + referralPage.assignedUser.getData() + " '", false,
								false);
					}
					// click on approve in Approve Decline Quote page
					referralPage.clickOnApprove();
					approveDeclineQuotePage.clickOnApprove();
					// Ended

					// Signing out as USM
					homePage.signOutButton.scrollToElement();
					homePage.signOutButton.click();

					// Signing in as Producer
					loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));

					// Searching the Approved QUote
					homePage.searchQuoteByProducer(quoteNumber);

					// Fetching Quote Number
					quoteLen = accountOverviewPage.quoteNumber.getData().length();
					quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);

					// Printing Quote Number
					Assertions.passTest("Account Overview Page", "The Quote Number: " + quoteNumber);

					// Clicking on edit dwelling button
					accountOverviewPage.editDwelling.waitTillVisibilityOfElement(60);
					accountOverviewPage.editDwelling.scrollToElement();
					accountOverviewPage.editDwelling.click();
				}

				else {
					Assertions.verify(
							createQuotePage.globalErr.checkIfElementIsDisplayed() && createQuotePage.globalErr.getData()
									.contains("The account is ineligible due to the roof age"),
							true, "Create Quote Page",
							createQuotePage.globalErr.getData() + " is displayed when Roof Cladding is "
									+ testData.get("L1D1-DwellingRoofCladding") + " and Year Built is "
									+ testData.get("L1D1-DwellingYearBuilt"),
							false, false);

					// Clicking on Previous Button on Create Quote Page
					createQuotePage.previous.scrollToElement();
					createQuotePage.previous.click();

					// Clicking on Edit Dwelling Button on Account Overview Page
					accountOverviewPage.editDwelling.scrollToElement();
					accountOverviewPage.editDwelling.click();
				}

			}

			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Navigating to create quote page
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();

			// Entering Create quote page Details
			createQuotePage.scrollToBottomPage();
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// Fetching Quote Number
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);

			// Printing Quote Number
			Assertions.passTest("Account Overview Page", "The Quote Number: " + quoteNumber);

			// Signing out as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("DETC001 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("DETC001 ", "Executed Successfully");
			}
		}
	}
}
