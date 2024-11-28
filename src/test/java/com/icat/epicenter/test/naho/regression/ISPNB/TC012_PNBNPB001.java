package com.icat.epicenter.test.naho.regression.ISPNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.ChangeNamedInsuredPage;
import com.icat.epicenter.pom.ChangePaymentPlanPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorseInspectionContactPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.EndorseProducerContact;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC012_PNBNPB001 extends AbstractNAHOTest {

	public TC012_PNBNPB001() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBNPB001.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {
		// Initializing the Objects
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ChangeNamedInsuredPage changeNamedInsuredPage = new ChangeNamedInsuredPage();
		ChangePaymentPlanPage changePaymentPlanPage = new ChangePaymentPlanPage();
		EndorseInspectionContactPage endorseInspectionContactPage = new EndorseInspectionContactPage();
		EndorseProducerContact endorseProducerContact = new EndorseProducerContact();

		String quoteNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Creating New Account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account Created successfully");

			// Entering Zipcode
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering dwelling details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote Details Entered successfully");

			if (referQuotePage.contactName.checkIfElementIsPresent()
					&& referQuotePage.contactName.checkIfElementIsDisplayed()) {

				// Enter Referral Contact Details
				referQuotePage.contactName.setData(testData.get("ProducerName"));
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.click();

				// verifying referral message
				Assertions.verify(referQuotePage.quoteNumberforReferral.checkIfElementIsDisplayed(), true,
						"Referral Page", "Quote " + referQuotePage.quoteNumberforReferral.getData()
								+ " referring to USM " + " is verified",
						false, false);

				quoteNumber = referQuotePage.quoteNumberforReferral.getData();

				// SignOut as Producer
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Home Page", "Logged out as Producer successfully");

				// Sign in as USM
				loginPage.refreshPage();
				loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
				Assertions.passTest("Home Page", "Logged in as USM Successfully");

				// searching the quote number in grid and clicking on the quote
				// number
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

				// Handling the pop up
				homePage.enterPersonalLoginDetails();

				// Search for quote
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuoteByProducer(quoteNumber);
			}

			// Asserting the Quote number From Account overview Page
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "The Quote Number is : " + quoteNumber);

			// Click on Request Bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

			// Entering underwriting details
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);

			// Entering bind details
			quoteNumber = requestBindPage.quoteNumber.getData();
			requestBindPage.enterBindDetailsNAHO(testData);

			// sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged Out as Producer Successfully");

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM successfully");

			// searching the quote number in grid and clicking on the quote number link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Searched Submitted Quote " + quoteNumber + " successfullly");

			// approving referral
			accountOverviewPage.uploadPreBindApproveAsUSM();
			referralPage.clickOnApprove();

			// click on approve in Approve Decline Quote page
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Referral Page", "Quote referral approved successfully");

			// Asserting Policy Number
			String policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is : " + policyNumber, false, false);

			// sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged out as USM successfully");

			// Sign in as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as Producer successfully");

			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Assert account overview page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchPolicyByProducer(policyNumber);

			// Clicking on Endorse policy link
			policySummaryPage.producerEndorsePolicyLink.scrollToElement();
			policySummaryPage.producerEndorsePolicyLink.click();

			// Entering Endorsement effective date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// Click on mailing address link and change the address

			testData = data.get(data_Value2);
			endorsePolicyPage.changeNamedInsuredLink.scrollToElement();
			endorsePolicyPage.changeNamedInsuredLink.click();

			Assertions.verify(changeNamedInsuredPage.okButton.checkIfElementIsDisplayed(), true,
					"Change Named Insured/Mailing Address Page", "Change Named Insured Page Loaded Successfully", false,
					false);
			changeNamedInsuredPage.enterInsuredAddressDetailPB(testData);
			Assertions.passTest("Change Named Insured/Mailing Address Page",
					"Changed the Mailing address successfully");

			// Click on Inspection Contact and change the Details
			endorsePolicyPage.changeInspectionContactLinkNAHO.scrollToElement();
			endorsePolicyPage.changeInspectionContactLinkNAHO.click();

			Assertions.verify(endorseInspectionContactPage.okButton.checkIfElementIsDisplayed(), true,
					"Endorse Inspection Contact Page", "Endorse Inspection Contact Page Loaded successfully", false,
					false);

			endorseInspectionContactPage.enterInspectionContactPB(testData);

			// Click on Producer Contact link and change producer phone and email
			endorsePolicyPage.producerContactLink.scrollToElement();
			endorsePolicyPage.producerContactLink.click();

			Assertions.verify(endorseProducerContact.okButton.checkIfElementIsDisplayed(), true,
					"Endorse Producer Contact Page", "Endorse Producer Contact Page Loaded Successfully", false, false);

			endorseProducerContact.enterEndorseProducerContactDetails(testData);

			// Click on Payment Plan and Change the Payment Details
			endorsePolicyPage.changePaymentPlanLink.scrollToElement();
			endorsePolicyPage.changePaymentPlanLink.click();

			Assertions.verify(changePaymentPlanPage.okButton.checkIfElementIsDisplayed(), true,
					"Change Payment Plan Page", "Change Payment Plan Page Loaded successfully", false, false);
			changePaymentPlanPage.enterChangePaymentPlanPB(testData);

			// Asserting Changes from endorse summary page
			String mailingAddressFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(6).getData();
			String mailingAddressTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(7).getData();
			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(5).checkIfElementIsDisplayed(),
					true, "Endorse Summary Page", "The Insured Mailing Address " + mailingAddressFrom + " changed to : "
							+ mailingAddressTo + " displayed is verified",
					false, false);

			String producerEmailFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(11).getData();
			String producerEmailTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(12).getData();
			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(10).checkIfElementIsDisplayed(),
					true, "Endorse Summary Page", "The Producer Email " + producerEmailFrom + " changed to : "
							+ producerEmailTo + " displayed is verified",
					false, false);

			String producerNameFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(16).getData();
			String producerNameTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(17).getData();
			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(15).checkIfElementIsDisplayed(),
					true, "Endorse Summary Page", "The Producer Name " + producerNameFrom + " changed to : "
							+ producerNameTo + " displayed is verified",
					false, false);

			String RnlPayplan = endorsePolicyPage.endorsementSummary.formatDynamicPath(22).getData();
			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(20).checkIfElementIsDisplayed(),
					true, "Endorse Summary Page",
					"The New Business Payment Plan is Full Pay and Renewal Payment Plan is " + RnlPayplan
							+ " displayed is verified",
					false, false);

			String insuredEmailFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(26).getData();
			String insuredEmailTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(27).getData();
			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(25).checkIfElementIsDisplayed(),
					true, "Endorse Summary Page", "The Insured Email " + insuredEmailFrom + " changed to  : "
							+ insuredEmailTo + " displayed is verified ",
					false, false);

			String insuredPhoneFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(31).getData();
			String insuredPhoneTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(32).getData();
			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(30).checkIfElementIsDisplayed(),
					true, "Endorse Summary Page", "The Insured Phone Number " + insuredPhoneFrom + " changed to : "
							+ insuredPhoneTo + " displayed is verified",
					false, false);

			String inspectionContactFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(38).getData();
			String inspectionContactTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(39).getData();
			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(37).checkIfElementIsDisplayed(),
					true, "Endorse Summary Page", "The Inspection Contact Details " + inspectionContactFrom
							+ " changed to  : " + inspectionContactTo + " displayed is verified",
					false, false);

			// Click On Complete Button
			endorsePolicyPage.submitButton.scrollToElement();
			endorsePolicyPage.submitButton.click();

			// sign out as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC012 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC012 ", "Executed Successfully");
			}
		}
	}

}
