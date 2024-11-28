/** Program Description: 1. Asserting the presence of Renew policy link and renewal indicators, different status of renewal process, Roll forward functionality.
 * 						 2. Update the Policy Expiration date to the date after the Actual expiration date and complete the transaction.
 * 						 3. Update the Policy Expiration date to the date after the newly provided expiration date in the previous ENDT and complete the transaction.
 *  Author			   : Sowndarya NH
 *  Date of Creation   : 04/05/2021
 **/
package com.icat.epicenter.test.naho.regression.NSPNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.ChangeNamedInsuredPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.EndorseSummaryDetailsPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RenewalIndicatorsPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBFLTC004_RNL extends AbstractNAHOTest {

	public PNBFLTC004_RNL() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSPNB/FL004.xls";
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
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		ViewPolicySnapShot viewPolicySnapShot = new ViewPolicySnapShot();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestSubmittedPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		LoginPage loginPage = new LoginPage();
		RenewalIndicatorsPage renewalIndicatorsPage = new RenewalIndicatorsPage();
		EndorseSummaryDetailsPage endorseSummaryDetailsPage = new EndorseSummaryDetailsPage();
		ChangeNamedInsuredPage changeNamedInsuredPage = new ChangeNamedInsuredPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		String namedStorm = "3%";
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		int data_Value4 = 3;

		int quoteLen;
		String quoteNumber;
		String nbExpirationDate;
		String policyNumber;
		String actualNamedStormValue;
		String expectedNamedStormValue;
		String renewalEffectiveDate;
		String renewalPolicyNumber;
		int policyExpirationDateLength;

		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// Creating New Account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account Created successfully");

			// Entering Zipcode
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
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);

			// IO-20810
			// Verifying A minimum Named Storm deductible of 3%
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			Assertions.addInfo("Scenario 01", "Verify Named Storm Deductible mimum value");
			Assertions.verify(createQuotePage.namedStormData.getData(), namedStorm, "Create Quote Page",
					"Asserting Named storm deuctible minimum value " + createQuotePage.namedStormData.getData()
							+ " is displayed verified",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
			// IO-20810 Ended

			// Entering Quote Details
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			createQuotePage.enterDeductiblesNAHO(testData);
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Click on Continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Adding Policy fee and Inspection fee
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Click on Bind button on request bind page
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind Button");

			// Clicking on answer no button
			underwritingQuestionsPage.answerNoToAllQuestions.waitTillVisibilityOfElement(60);
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.answerNoToAllQuestions.scrollToElement();
			underwritingQuestionsPage.answerNoToAllQuestions.click();

			// Clicking on save button
			underwritingQuestionsPage.saveButton.scrollToElement();
			underwritingQuestionsPage.saveButton.click();

			// Entering bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			nbExpirationDate = requestBindPage.expirationDate.getData();
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Details Entered successfully");

			// Adding the CR IO-19481
			// verifying the Prior loss referral message
			Assertions.addInfo("Scenario 02", "Asserting prior loss message");
			Assertions.verify(
					bindRequestSubmittedPage.priorLossesMessage.getData().contains("prior loss service results"), true,
					"Bind Request Submitted Page", "The Prior Loss message "
							+ bindRequestSubmittedPage.priorLossesMessage.getData() + " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Searching the Bind Referral
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Searched the Referral Quote " + quoteNumber + " successfullly");

			// Asserting the referral message in Notebar and it should not contain
			// html tags
			accountOverviewPage.waitTime(3);
			Assertions.verify(!accountOverviewPage.noteBarMessage.getData().contains("<br/>"), true,
					"Account Overview Page",
					"The Message Displayed in Note bar is " + accountOverviewPage.noteBarMessage.getData(), false,
					false);

			// Click on open referral link
			if (accountOverviewPage.openReferral.checkIfElementIsPresent()
					&& accountOverviewPage.openReferral.checkIfElementIsDisplayed()) {
				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();

				// Approve Referral
				Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
						"Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();

				// click on approve in Referral page
				Assertions.passTest("Referral Page", "Quote referral approved successfully");

				requestBindPage.approveRequestNAHO(testData);
			}

			// Asserting policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfuly", false, false);
			policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.passTest("Policy Summary Page", "Policy Number is " + policyNumber);

			// Asserting renewal indicators
			Assertions.addInfo("Scenario 03", "Verifying presence of Renewal Policy link and Renewal Indicators link");
			Assertions.verify(policySummaryPage.renewPolicy.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Renewal policy link is available", false, false);
			Assertions.verify(policySummaryPage.renewalIndicators.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "Renewal Indicators link is available", false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// LogOut as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "LogOut as USM successfully");

			// Login as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as Producer Successfully");

			// searching policy as producer
			homePage.enterPersonalLoginDetails();
			homePage.searchPolicyByProducer(policyNumber);
			Assertions.passTest("Home Page", "Policy Searched successfully");

			// Asserting renewal link absence for producer
			Assertions.addInfo("Scenario 04", "Verifying Absence of Renewal policy link");
			Assertions.verify(policySummaryPage.renewPolicy.checkIfElementIsPresent(), false, "Policy Summary Page",
					"Renewal policy link is not available for producer", false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// LogOut as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logout as Producer successfully");

			// Login as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");

			// Searching policy as usm
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Policy Searched successfully");
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			// Clicking on Renewal indicators link
			policySummaryPage.renewalIndicators.scrollToElement();
			policySummaryPage.renewalIndicators.click();
			Assertions.verify(renewalIndicatorsPage.updateButton.checkIfElementIsDisplayed(), true,
					"Renewal Indicators Page", "Renewal Indicators Page loaded successfully", false, false);

			// Asserting values in renewal indicator page
			Assertions.addInfo("Scenario 05", "Verifying available fields in Renewal Indicators page");
			Assertions.verify(renewalIndicatorsPage.nonRenewal.checkIfElementIsDisplayed(), true,
					"Renewal Indicators Page", "Non Renewal Check Box is available", false, false);
			Assertions.verify(renewalIndicatorsPage.underwritingReview.checkIfElementIsDisplayed(), true,
					"Renewal Indicators Page", "Underwriting Review Check Box is available", false, false);
			Assertions.verify(renewalIndicatorsPage.coverageChange.checkIfElementIsDisplayed(), true,
					"Renewal Indicators Page", "Coverage Changes Check Box is available", false, false);
			Assertions.verify(renewalIndicatorsPage.addNoteToRenewal.checkIfElementIsDisplayed(), true,
					"Renewal Indicators Page", "Add NoteToRenewal Check Box is available", false, false);
			Assertions.verify(renewalIndicatorsPage.reInspection.checkIfElementIsDisplayed(), true,
					"Renewal Indicators Page", "Re-Inspection Check Box is available", false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// selecting non renewal check box
			renewalIndicatorsPage.nonRenewal.scrollToElement();
			renewalIndicatorsPage.nonRenewal.select();
			renewalIndicatorsPage.enterNonRenewalDetails(testData);
			Assertions.passTest("Renewal Indicators Page", "Non Renewal Checkbox is selected successfully");
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			// Asserting absence of renewal link after selcting non renewal checkbox
			// in
			// renewal indicators page
			Assertions.addInfo("Scenario 06", "Verifying Absence of Renewal link after selcting Non Renewal checkbox");
			Assertions.verify(policySummaryPage.renewPolicy.checkIfElementIsPresent(), false, "Policy Summary Page",
					"Renewal policy link not available when Non Renewal checkbox is selected", false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// clciking on renewal indicator link
			policySummaryPage.renewalIndicators.scrollToElement();
			policySummaryPage.renewalIndicators.click();
			Assertions.verify(renewalIndicatorsPage.updateButton.checkIfElementIsDisplayed(), true,
					"Renewal Indicators Page", "Renewal Indicators Page loaded successfully", false, false);

			// Deselecting non renewal checkbox
			renewalIndicatorsPage.nonRenewal.scrollToElement();
			renewalIndicatorsPage.nonRenewal.deSelect();
			Assertions.passTest("Renewal Indicators Page", "Non Renewal Checkbox is deselected successfully");
			renewalIndicatorsPage.updateButton.scrollToElement();
			renewalIndicatorsPage.updateButton.click();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			// Asserting presence of renewal link after deselcting non renewal
			// check box in
			// renewal indicators page
			Assertions.addInfo("Scenario 07",
					"Verifying presence of Renewal policy link after deselecting Non Renewal Indicator checkbox");
			Assertions.verify(policySummaryPage.renewPolicy.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Renewal policy link is available", false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// clicking on renewal policy link
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();

			// Go to Home Page
			if (policySummaryPage.expaacMessage.checkIfElementIsPresent()
					&& policySummaryPage.expaacMessage.checkIfElementIsDisplayed()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.click();
				expaccInfoPage.enterExpaccInfo(testData, policyNumber);
				Assertions.passTest("ExpaccInfo Page", "ExpaccInfo details entered successfully");

				// Go to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();

				// Performing Renewal Searches
				homePage.searchPolicy(policyNumber);
				// clicking on renewal policy link
				policySummaryPage.renewPolicy.waitTillPresenceOfElement(60);
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();
			}
			if (accountOverviewPage.openReferral.checkIfElementIsPresent()
					&& accountOverviewPage.openReferral.checkIfElementIsDisplayed()) {
				quoteLen = accountOverviewPage.quoteNumber.getData().length();
				quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
				Assertions.passTest("Account overview page", "Renewal quote number " + quoteNumber);
				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();

				// Approve Referral
				Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
						"Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();

				// click on approve in Referral page
				Assertions.passTest("Referral Page", "Quote referral approved successfully");
				approveDeclineQuotePage.clickOnApprove();
				referralPage.close.scrollToElement();
				referralPage.close.click();
				homePage.searchQuote(quoteNumber);
			}

			// Asserting renewal quote status
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			Assertions.addInfo("Scenario 08",
					"Verifying presence of Renewal quote status as Internal Renewal and Release Renewal to Producer button");
			Assertions.verify(accountOverviewPage.internalRenewalStatus.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Renewal Quote status as Internal Renewal is verified", false, false);
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Release Renewal to Producer button is available", false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// click on release renewal to producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked Release Renewal To Producer button successfully");
			Assertions.addInfo("Scenario 09",
					"Verifying the Renewal quote status as Renewal, Renewal successfully released message, Presence of Renewal Offer link/"
							+ "Edit payment plan link/Edit Insured Contact Info link/Edit Inspection Contact Info link/Edit Additional Interests link/"
							+ "View or print full quote link/Email quote link/Override Premium link/View/Print Rate Trace link");
			Assertions.verify(accountOverviewPage.renewalStatus.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Renewal Quote status as Renewal is verified", false, false);
			Assertions.verify(accountOverviewPage.unlockMessage.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"Release message is " + accountOverviewPage.unlockMessage.getData() + " Displayed is verified",
					false, false);
			Assertions.verify(accountOverviewPage.renewalQuote1.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Renewal offer link is available", false, false);
			Assertions.verify(accountOverviewPage.editPaymentPlan.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Edit Payment plan link is available", false, false);
			Assertions.verify(accountOverviewPage.editInsuredContactInfo.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Edit Insured Contact Info link is available", false, false);
			Assertions.verify(accountOverviewPage.editInspectionContact.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Edit Inspection Contact Info link is available", false, false);
			Assertions.verify(accountOverviewPage.editAdditionalIntersets.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Edit Additional Interests link is available", false, false);
			Assertions.verify(accountOverviewPage.viewPrintFullQuoteLink.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "View/print full quote link is available", false, false);
			Assertions.verify(accountOverviewPage.emailQuoteLink.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Email quote link is available", false, false);
			Assertions.verify(accountOverviewPage.overridePremiumLink.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Override Premium link is available", false, false);
			Assertions.verify(accountOverviewPage.viewOrPrintRateTrace.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "View/Print Rate Trace link is available", false, false);
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			// Click on create another quote link
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();

			// IO-20810 NAHO Product changes ticket
			// Verifying A minimum Named Storm deductible of 3% on Rnewal quote
			createQuotePage.namedStormArrow_NAHO.scrollToElement();
			createQuotePage.namedStormArrow_NAHO.click();
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			Assertions.addInfo("Scenario 10", "Verify Named Storm Deductible mimum value");
			Assertions
					.verify(createQuotePage.namedStormData.getData(), namedStorm, "Create Quote Page",
							"Asserting Named storm deuctible minimum value on renewal quote "
									+ createQuotePage.namedStormData.getData() + " is displayed verified",
							false, false);
			// IO_20810 Ended

			Assertions.addInfo("Scenario 10", "Scenario 10 Ended");
			createQuotePage.previous.scrollToElement();
			createQuotePage.previous.click();

			// Click on Continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// click on view previous policy
			accountOverviewPage.viewPreviousPolicyButton.scrollToElement();
			accountOverviewPage.viewPreviousPolicyButton.click();
			Assertions.passTest("Account Overview Page", " Clicked on View Previous policy link scuccessfully");
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			// click on Enodrse PB link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			policySummaryPage.okButton.scrollToElement();
			policySummaryPage.okButton.click();
			Assertions.passTest("Policy Summary Page", "Clicked Endorse PB link successfully");
			testData = data.get(data_Value1);
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			endorsePolicyPage.changeCoverageOptionsLink.waitTillPresenceOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page loaded successfully", false, false);

			// Enter quote details
			testData = data.get(data_Value1);
			Assertions.verify(createQuotePage.namedStormData.getData(), testData.get("NamedStormValue"),
					"Create Quote Page",
					"Before Roll Forward Named Storm value is " + createQuotePage.namedStormData.getData(), false,
					false);
			testData = data.get(data_Value2);

			if (!testData.get("NamedStormValue").equals("")) {
				if (createQuotePage.namedStormArrow_NAHO.checkIfElementIsPresent()
						&& createQuotePage.namedStormArrow_NAHO.checkIfElementIsDisplayed()) {
					createQuotePage.namedStormArrow_NAHO.scrollToElement();
					createQuotePage.namedStormArrow_NAHO.click();
					createQuotePage.namedStormDeductibleOption1.formatDynamicPath(testData.get("NamedStormValue"))
							.waitTillVisibilityOfElement(60);
					createQuotePage.namedStormDeductibleOption1.formatDynamicPath(testData.get("NamedStormValue"))
							.scrollToElement();
					createQuotePage.namedStormDeductibleOption1.formatDynamicPath(testData.get("NamedStormValue"))
							.click();
				}
			}
			Assertions.passTest("Create Quote Page ", "Quote Details Entered successfully");

			// Complete the endorsement
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			Assertions.passTest("Create Quote Page", "Clicked on Continue Endorsement button successfully");

			// Adding code for if service is down
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			Assertions.verify(endorsePolicyPage.nextButton.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Endorse Policy Page loaded successfully", false, false);
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// Adding code for service is down
			if (endorsePolicyPage.oKContinueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.oKContinueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.oKContinueButton.scrollToElement();
				endorsePolicyPage.oKContinueButton.click();
			}
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// Asserting presence of roll ofrward button
			Assertions.addInfo("Scenario 11", "Asserting presence of Roll Forward Button for PB endorsement");
			Assertions.verify(endorsePolicyPage.rollForwardBtn.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Roll Forward Button displayed while processing PB endorsement", false, false);
			Assertions.addInfo("Scenario 11", "Scenario 11 Ended");

			// Click on roll forward and complete the ensorsement
			endorsePolicyPage.rollForwardBtn.scrollToElement();
			endorsePolicyPage.rollForwardBtn.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on roll forward button successfully");
			endorseSummaryDetailsPage.closeBtn.scrollToElement();
			endorseSummaryDetailsPage.closeBtn.click();

			// click on view active renewal
			policySummaryPage.viewActiveRenewal.scrollToElement();
			policySummaryPage.viewActiveRenewal.click();

			// Click on view print full quote page
			Assertions.verify(accountOverviewPage.viewPrintFullQuoteLink.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// Asserting the renewal Quote number
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "The Renewal Quote Number : " + quoteNumber);

			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"ViewOrPrintFullQuote Page", "ViewOrPrintFullQuote Page loaded successfully", false, false);
			actualNamedStormValue = viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Named").getData()
					.substring(0, 2);
			expectedNamedStormValue = testData.get("NamedStormValue");

			// Asserting named storm value
			Assertions.addInfo("Scenario 12", "Asserting Roll Forwarded value in renewal quote");
			Assertions.verify(actualNamedStormValue, expectedNamedStormValue, "ViewOrPrintFullQuote Page",
					"Roll Forwarded Named Storm value is " + actualNamedStormValue, false, false);
			Assertions.addInfo("Scenario 12", "Scenario 12 Ended");

			// CLicked on back button
			viewOrPrintFullQuotePage.waitTime(2);
			viewOrPrintFullQuotePage.scrollToTopPage();
			viewOrPrintFullQuotePage.backButton.click();

			// Click on quote specific
			accountOverviewPage.quoteSpecifics.waitTillPresenceOfElement(60);
			accountOverviewPage.quoteSpecifics.waitTillVisibilityOfElement(60);
			accountOverviewPage.quoteSpecifics.scrollToElement();
			accountOverviewPage.quoteSpecifics.click();
			Assertions.passTest("Account Overview Page", "Clicked Quote Specifics link successfully");

			// Asserting quote specific values
			Assertions.addInfo("Scenario 13",
					"Asserting Requested Effective Date, Requested Expiration Date and Rating Effective Date");
			Assertions.verify(accountOverviewPage.requestedEffectiveDate.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"Requested Effective Date is " + accountOverviewPage.requestedEffectiveDate.getData(), false,
					false);
			Assertions.verify(accountOverviewPage.requestedExpirationDate.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"Requested Expiration Date is " + accountOverviewPage.requestedExpirationDate.getData(), false,
					false);
			Assertions.verify(accountOverviewPage.ratingEffectiveDate.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"Rating Effective Date is " + accountOverviewPage.ratingEffectiveDate.getData(), false, false);
			Assertions.addInfo("Scenario 13", "Scenario 13 Ended");

			// click on bind request button
			accountOverviewPage.clickOnRenewalRequestBindNAHO(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on RequestBind button successfully");

			// Asserting renewal effective date
			Assertions.addInfo("Scenario 14", "Asserting Renewal Effective date");
			renewalEffectiveDate = requestBindPage.effectiveDate.getData();
			Assertions.verify(renewalEffectiveDate, nbExpirationDate, "Request Bind Page", "NB Expiration Date "
					+ nbExpirationDate + " and Renewal Effective Date " + renewalEffectiveDate + " Both are same",
					false, false);
			Assertions.addInfo("Scenario 14", "Scenario 14 Ended");
			testData = data.get(data_Value1);

			// Entering renewal bind details
			requestBindPage.renewalRequestBindNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Details Entered successfully");

			// Adding the CR IO-19481 for renewal Quote
			// verifying the Prior loss referral message
			Assertions.verify(
					bindRequestSubmittedPage.priorLossesMessage.getData().contains("prior loss service results"), true,
					"Bind Request Submitted Page", "The Prior Loss message "
							+ bindRequestSubmittedPage.priorLossesMessage.getData() + " displayed is verified",
					false, false);

			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Searching the Bind Referral
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Searched the Referral Quote " + quoteNumber + " successfullly");

			// Asserting the referral message in Notebar and it should not contain
			// html tags
			accountOverviewPage.waitTime(3);
			Assertions.verify(!accountOverviewPage.noteBarMessage.getData().contains("<br/>"), true,
					"Account Overview Page",
					"The Message Displayed in Note bar is " + accountOverviewPage.noteBarMessage.getData(), false,
					false);

			// Click on open referral link
			if (accountOverviewPage.openReferral.checkIfElementIsPresent()
					&& accountOverviewPage.openReferral.checkIfElementIsDisplayed()) {
				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();

				// Approve Referral
				Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
						"Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();

				// click on approve in Referral page
				Assertions.passTest("Referral Page", "Quote referral approved successfully");

				requestBindPage.approveRequestNAHO(testData);
			}

			// Asserting policy number
			renewalPolicyNumber = policySummaryPage.policyNumber.getData();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully, Renewal Policy Number is " + renewalPolicyNumber, false,
					false);

			// click on previous policy number
			policySummaryPage.previousPolicyNumber.waitTillPresenceOfElement(60);
			policySummaryPage.previousPolicyNumber.scrollToElement();
			policySummaryPage.previousPolicyNumber.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Previous Policy Number successfully");

			// CLick on endorsement transaction
			policySummaryPage.transHistReason.formatDynamicPath(2).scrollToElement();
			policySummaryPage.transHistReason.formatDynamicPath(2).click();

			// click onEnodrse NPB link
			policySummaryPage.endorseNPB.scrollToElement();
			policySummaryPage.endorseNPB.click();
			policySummaryPage.okButton.scrollToElement();
			policySummaryPage.okButton.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Enodrse NPB link successfully");
			testData = data.get(data_Value1);

			// Change named insured value
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			endorsePolicyPage.changeNamedInsuredLink.waitTillPresenceOfElement(60);
			endorsePolicyPage.changeNamedInsuredLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeNamedInsuredLink.scrollToElement();
			endorsePolicyPage.changeNamedInsuredLink.click();
			Assertions.verify(changeNamedInsuredPage.okButton.checkIfElementIsDisplayed(), true,
					"Change NamedInsured Page", "Change NamedInsured Page loaded successfully", false, false);
			testData = data.get(data_Value2);
			changeNamedInsuredPage.enterInsuredAddressDetailPB(testData);

			// Asserting Roll Forward for NPB endorsement
			Assertions.addInfo("Scenario 15",
					"Verifying Presence of Complete and Roll Forward Changes to Renewal Account for NPB Endorsement");
			Assertions.verify(endorsePolicyPage.completeAndRollForwardBtn.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Complete and RollForward Changes to Renewal button is displayed", false,
					false);
			Assertions.addInfo("Scenario 15", "Scenario 15 Ended");

			// click on RollForwarcomplete button
			endorsePolicyPage.completeAndRollForwardBtn.scrollToElement();
			endorsePolicyPage.completeAndRollForwardBtn.click();
			Assertions.passTest("Endorse Policy Page", "Clicked Complete And RollForward button successfully");
			if (endorsePolicyPage.closeButton.checkIfElementIsPresent()
					&& endorsePolicyPage.closeButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.closeButton.scrollToElement();
				endorsePolicyPage.closeButton.click();
			}

			// Click on view active renewal link
			policySummaryPage.viewActiveRenewal.scrollToElement();
			policySummaryPage.viewActiveRenewal.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Active Renewal link successfully");
			Assertions.verify(policySummaryPage.renewPolicy.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Renewal Policy Summary Page loaded successfully", false, false);

			// Click on endorsement transaction
			policySummaryPage.endorsementTransaction.formatDynamicPath(1).waitTillPresenceOfElement(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(1).waitTillButtonIsClickable(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(1).scrollToElement();
			policySummaryPage.endorsementTransaction.formatDynamicPath(1).click();
			policySummaryPage.waitTime(1);
			Assertions.passTest("Policy Summary Page", "Clicked on endorsement transaction");

			// Click on policy snapshot
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Policy Snapshot link successfully");
			actualNamedStormValue = viewPolicySnapShot.locationLevelCovLimit.formatDynamicPath("Named").getData()
					.substring(0, 2);

			// Asserting the insured name/named storm values are roll forwarded
			// successfully
			Assertions.addInfo("Scenario 16", "Verifying Roll Forward on Policy Snapshot");
			Assertions.verify(viewPolicySnapShot.insuredNameNAHO.getData().contains(testData.get("InsuredName")), true,
					"View Policy Snapshot Page",
					"Roll Forwarded Insured Name is " + viewPolicySnapShot.insuredNameNAHO.getData(), false, false);
			Assertions.verify(actualNamedStormValue, expectedNamedStormValue, "View Policy Snapshot Page",
					"Roll Forwarded NamedStorm value is " + actualNamedStormValue, false, false);
			Assertions.addInfo("Scenario 16", "Scenario 16 Ended");

			// Click on back button
			viewPolicySnapShot.scrollToTopPage();
			viewPolicySnapShot.goBackButton.click();

			// click on previous policy number
			policySummaryPage.previousPolicyNumber.scrollToElement();
			policySummaryPage.previousPolicyNumber.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Previous Policy Number link");

			// Click on Endorse PB link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// click on ok button
			if (policyRenewalPage.rnlOkButton.checkIfElementIsPresent()
					&& policyRenewalPage.rnlOkButton.checkIfElementIsDisplayed()) {
				policyRenewalPage.rnlOkButton.scrollToElement();
				policyRenewalPage.rnlOkButton.click();
			}

			// Enter Endorsement Eff date as policy eff date
			testData = data.get(data_Value1);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// enter policy expiration date
			endorsePolicyPage.policyExpirationDate.setData(testData.get("PolicyExpirationDate"));
			endorsePolicyPage.policyExpirationDate.tab();
			Assertions.passTest("Endorse Policy Page",
					"Entered the Policy Expiration Date as " + endorsePolicyPage.policyExpirationDate.getData());
			endorsePolicyPage.changeExpirationDate.scrollToElement();
			endorsePolicyPage.changeExpirationDate.click();

			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// click on next button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Next Button");

			if (endorsePolicyPage.oKContinueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.oKContinueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.oKContinueButton.scrollToElement();
				endorsePolicyPage.oKContinueButton.click();
			}

			// click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Complete button");

			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// click on close button
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on close button successfully");

			// Clicked transaction history
			policySummaryPage.endorsementTransaction.formatDynamicPath(5).waitTillPresenceOfElement(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(5).waitTillButtonIsClickable(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(5).scrollToElement();
			policySummaryPage.endorsementTransaction.formatDynamicPath(5).click();
			Assertions.passTest("Policy summary Page", "Clicked on transaction history successfully");

			// Click on View policy snapshot link
			policySummaryPage.scrollToTopPage();
			policySummaryPage.waitTime(2);
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Policy Snapshot link");

			Assertions.addInfo("Scenario 17", "Verifying the Changed Policy expiration date");
			Assertions.verify(viewPolicySnapShot.goBackButton.checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page", "View Policy Snapshot Page loaded successfully", false, false);
			policyExpirationDateLength = viewPolicySnapShot.homeownerQuoteDetails.formatDynamicPath("4").getData()
					.length();
			Assertions.verify(viewPolicySnapShot.homeownerQuoteDetails.formatDynamicPath("4").getData()
					.substring(23, policyExpirationDateLength - 23).contains(testData.get("PolicyExpirationDate")),
					true, "View Policy Snapshot Page",
					"The Policy Expiration Date "
							+ viewPolicySnapShot.homeownerQuoteDetails.formatDynamicPath("4").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 17", "Scenario 17 Ended");

			// Click on back button
			viewPolicySnapShot.goBackButton.scrollToElement();
			viewPolicySnapShot.goBackButton.click();

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Performing Renewal Searches
			homePage.searchPolicy(renewalPolicyNumber);

			// Clicked transaction history
			policySummaryPage.endorsementTransaction.formatDynamicPath(1).waitTillPresenceOfElement(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(1).waitTillButtonIsClickable(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(1).scrollToElement();
			policySummaryPage.endorsementTransaction.formatDynamicPath(1).click();
			Assertions.passTest("Policy summary Page", "Clicked on transaction history successfully");

			// Click on View policy snapshot link
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Policy Snapshot link");

			// Verify the policy expiration date remains same for renewal policy even after
			// changing the Policy expiration date for NB policy
			Assertions.addInfo("Scenario 18",
					"Verifying the policy expiration date remains same for renewal policy even after changing the Policy expiration date for NB policy");
			Assertions.verify(viewPolicySnapShot.goBackButton.checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page", "View Policy Snapshot Page loaded successfully", false, false);
			testData = data.get(data_Value2);
			Assertions.verify(viewPolicySnapShot.homeownerQuoteDetails.formatDynamicPath("4").getData()
					.substring(23, policyExpirationDateLength - 23).contains(testData.get("TransactionEffectiveDate")),
					true, "View Policy Snapshot Page",
					"The Policy Expiration Date "
							+ viewPolicySnapShot.homeownerQuoteDetails.formatDynamicPath("4").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 18", "Scenario 18 Ended");

			// Click on back button
			viewPolicySnapShot.goBackButton.scrollToElement();
			viewPolicySnapShot.goBackButton.click();

			// Click on Endorse PB link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Enter Endorsement Eff date as policy eff date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("PolicyEffDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// enter policy expiration date
			endorsePolicyPage.policyExpirationDate.scrollToElement();
			endorsePolicyPage.policyExpirationDate.setData(testData.get("PolicyExpirationDate"));
			endorsePolicyPage.policyExpirationDate.tab();
			Assertions.passTest("Endorse Policy Page",
					"Entered the Policy Expiration Date as " + endorsePolicyPage.policyExpirationDate.getData());
			endorsePolicyPage.changeExpirationDate.scrollToElement();
			endorsePolicyPage.changeExpirationDate.click();

			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// click on next button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Next Button");

			// click on continue button
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Complete button");

			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// click on close button
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// CLick on endorsement transaction
			policySummaryPage.endorsementTransaction.formatDynamicPath(3).waitTillPresenceOfElement(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(3).waitTillButtonIsClickable(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(3).scrollToElement();
			policySummaryPage.endorsementTransaction.formatDynamicPath(3).click();

			// Click on View policy snapshot link
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Policy Snapshot link");

			Assertions.addInfo("Scenario 19", "Verifying the Changed Policy expiration date on renewal policy");
			Assertions.verify(viewPolicySnapShot.goBackButton.checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page", "View Policy Snapshot Page loaded successfully", false, false);
			Assertions.verify(
					viewPolicySnapShot.homeownerQuoteDetails
							.formatDynamicPath("4").getData().contains(testData.get("PolicyExpirationDate")),
					true, "View Policy Snapshot Page",
					"The Policy Expiration Date "
							+ viewPolicySnapShot.homeownerQuoteDetails.formatDynamicPath("4").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 19", "Scenario 19 Ended");

			// Click on back button
			viewPolicySnapShot.scrollToTopPage();
			viewPolicySnapShot.goBackButton.click();

			// Click on View previous policy Number
			if (policySummaryPage.previousPolicyNumber.checkIfElementIsPresent()
					&& policySummaryPage.previousPolicyNumber.checkIfElementIsDisplayed()) {
				policySummaryPage.previousPolicyNumber.scrollToElement();
				policySummaryPage.previousPolicyNumber.click();
			}

			// Click on Endorse PB
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Click on Ok button
			if (policySummaryPage.okButton.checkIfElementIsPresent()
					&& policySummaryPage.okButton.checkIfElementIsDisplayed()) {
				policySummaryPage.okButton.scrollToElement();
				policySummaryPage.okButton.click();
			}

			Assertions.addInfo("Scenario 20",
					"Update the Policy Expiration date to the date after the Actual expiration date and complete the transaction.");

			// Entering Endorsement effective date
			testData = data.get(data_Value3);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// Entering Policy Expiration date
			endorsePolicyPage.policyExpirationDate.scrollToElement();
			endorsePolicyPage.policyExpirationDate.setData(testData.get("PolicyExpirationDate"));
			endorsePolicyPage.policyExpirationDate.tab();

			// Click on Change Expiration Button
			endorsePolicyPage.changeExpirationDate.scrollToElement();
			endorsePolicyPage.changeExpirationDate.click();

			// Click on continue Button for out of sequence transaction
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Click On next Button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// click on continue button
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Asserting the Values From Endorse Summary Page
			Assertions.verify(endorsePolicyPage.completeButton.checkIfElementIsDisplayed(), true,
					"Endorse Summary Page", "Endorse Summary Page Loaded Successfully", false, false);

			// click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// Click on continue Button for out of sequence transaction
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Click on Close Button
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Asserting policy Effective Date and Policy Expiration Date
			Assertions.verify(policySummaryPage.effectiveDate.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Policy Effective date is displayed as : "
							+ policySummaryPage.effectiveDate.getData().substring(0, 10),
					false, false);
			Assertions.verify(policySummaryPage.expirationDate.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Policy Expiration date is displayed as : "
							+ policySummaryPage.expirationDate.getData().substring(0, 10),
					false, false);
			Assertions.addInfo("Scenario 20", "Scenario 20 Ended");

			// Click on Endorse PB
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Click on Ok button
			if (policySummaryPage.okButton.checkIfElementIsPresent()
					&& policySummaryPage.okButton.checkIfElementIsDisplayed()) {
				policySummaryPage.okButton.scrollToElement();
				policySummaryPage.okButton.click();
			}

			Assertions.addInfo("Scenario 21",
					"Update the Policy Expiration date to the date after the newly provided expiration date in the previous ENDT and complete the transaction.");

			// Entering Endorsement effective date
			testData = data.get(data_Value4);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// Entering Policy Expiration date
			endorsePolicyPage.policyExpirationDate.scrollToElement();
			endorsePolicyPage.policyExpirationDate.setData(testData.get("PolicyExpirationDate"));
			endorsePolicyPage.policyExpirationDate.tab();

			// Click on Change Expiration Button
			endorsePolicyPage.changeExpirationDate.scrollToElement();
			endorsePolicyPage.changeExpirationDate.click();

			// Click on continue Button for out of sequence transaction
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Click On next Button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// click on continue button
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Asserting the Values From Endorse Summary Page
			Assertions.verify(endorsePolicyPage.completeButton.checkIfElementIsDisplayed(), true,
					"Endorse Summary Page", "Endorse Summary Page Loaded Successfully", false, false);

			// click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// Click on Continue Button for out of sequence transaction
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Click on Close Button
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Asserting policy Effective Date and Policy Expiration Date
			Assertions.verify(policySummaryPage.effectiveDate.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Effective date is displayed as : "
							+ policySummaryPage.effectiveDate.getData().substring(0, 10),
					false, false);
			Assertions.verify(policySummaryPage.expirationDate.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Expiration date is displayed as : "
							+ policySummaryPage.expirationDate.getData().substring(0, 10),
					false, false);
			Assertions.addInfo("Scenario 21", "Scenario 21 Ended");

			// logout as usm
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBFLTC004 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBFLTC004 ", "Executed Successfully");
			}
		}
	}

}
