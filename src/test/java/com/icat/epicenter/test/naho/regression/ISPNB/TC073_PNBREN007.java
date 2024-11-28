package com.icat.epicenter.test.naho.regression.ISPNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC073_PNBREN007 extends AbstractNAHOTest {

	public TC073_PNBREN007() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBREN007.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		HomePage homePage = new HomePage();
		LoginPage loginPage = new LoginPage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();

		int data_Value1 = 0;
		int data_Value2 = 1;

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

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Click on Continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			String quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Binding the quote and creating the policy
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

			// Clicking on answer no button
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions Details Entered successfully");

			// Entering bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			Assertions.passTest("Request Bind Page", "Bind Details Entered Successfully");
			requestBindPage.enterBindDetailsNAHO(testData);

			// Clicking on homepage button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			accountOverviewPage.openReferralLink.checkIfElementIsDisplayed();
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();

			// Approve Referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(approveDeclineQuotePage.approveButton.checkIfElementIsDisplayed(), true,
					"Approve Decline Quote Page", "Approve Decline Quote page loaded successfully", false, false);
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Approve Decline Quote Page", "Bind Request approved successfully");

			// Getting Policy Number from policy summary Page
			String policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is " + policyNumber, false, false);

			// Go to homepage and search the policy
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.passTest("Home Page", "Home Page loaded successfully");
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Searched the policy successfully");

			// Click on Rnewal Link
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renewal link successfully ");

			if (policySummaryPage.expaacMessage.checkIfElementIsPresent()
					&& policySummaryPage.expaacMessage.checkIfElementIsDisplayed()) {
				Assertions.verify(policySummaryPage.expaacMessage.checkIfElementIsDisplayed(), true,
						"Policy Renewal Page", policySummaryPage.expaacMessage.getData() + " Message verified", false,
						false);

				// Go to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home page loaded successfully", false, false);

				// clicking on expaac link in home page
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.click();
				Assertions.passTest("Home Page", "Clicked on Expaac Link");

				// entering expaac data
				Assertions.verify(expaccInfoPage.policyNumber.checkIfElementIsDisplayed(), true, "Expacc Info Page",
						"Update Expaac Data page loaded successfully", false, false);
				expaccInfoPage.enterExpaccInfo(testData, policyNumber);
				Assertions.passTest("Expacc Info Page", "Expaac Data updated successfully");

				// Go to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home page loaded successfully", false, false);

				// Find the policy by entering policy Number
				homePage.searchPolicy(policyNumber);
				Assertions.passTest("Home Page", "Policy Number : " + policyNumber + " searched successfully");

				// Click on Renew Policy Hyperlink
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();
				Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy Link");

				// click on continue button in Renewal building review page
				if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
						&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
					policyRenewalPage.continueRenewal.scrollToElement();
					policyRenewalPage.continueRenewal.click();
					Assertions.passTest("Renewal Building Review Page", "Clicked on Continue");
				}
			}
			Assertions.verify(accountOverviewPage.viewPreviousPolicyButton.checkIfElementIsDisplayed(), true,
					"Account Overvew Page", "Account Overview Page loaded successfully", false, false);

			// Getting Renewal Quote Number
			String renewalQuoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Renewal Quote Number is : " + renewalQuoteNumber);

			// Approving renewal referral
			if (accountOverviewPage.openReferral.checkIfElementIsPresent()
					&& accountOverviewPage.openReferral.checkIfElementIsDisplayed()) {
				accountOverviewPage.approveRenewalReferralUSM(renewalQuoteNumber);
			}

			// Click on Release Renewal To Producer
			Assertions.passTest("Account Overview Page", "Before Release Renewal To Producer, Quote Status is:"
					+ accountOverviewPage.renewalStatus.getData());
			accountOverviewPage.releaseRenewalToProducerButton.waitTillPresenceOfElement(60);
			accountOverviewPage.releaseRenewalToProducerButton.waitTillPresenceOfElement(60);
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Renewal To Producer link successfully");
			Assertions.passTest("Account Overview Page",
					"After Release To Producer Quote Status is:" + accountOverviewPage.renewalStatus.getData());

			// click on LogOut as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as USM successfully");

			// Sign in as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as Producer Successfully");

			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Search for quote
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuoteByProducer(renewalQuoteNumber);
			Assertions.passTest("Home Page", "Searched for Renewal Quote and Clicked on Renewal Quote successfully");
			if (accountOverviewPage.renewalStatus.checkIfElementIsDisplayed()
					&& accountOverviewPage.renewalStatus.checkIfElementIsPresent()) {
				Assertions.verify(accountOverviewPage.renewalStatus.checkIfElementIsDisplayed(), true,
						"Account Overview Page",
						"The Quote Status Renewal is verified, Quote Status is :" + accountOverviewPage.renewalStatus,
						false, false);
			} else {
				Assertions.verify(accountOverviewPage.renewalStatus.checkIfElementIsDisplayed(), true,
						"Account Overview Page", "The Quote Status Renewal is not verified, Quote Status is :"
								+ accountOverviewPage.renewalStatus,
						false, false);
			}

			// Click on Edit Deductibles and limit
			testData = data.get(data_Value2);
			Assertions.verify(accountOverviewPage.editDeductibleAndLimits.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			accountOverviewPage.editDeductibleAndLimits.waitTillPresenceOfElement(60);
			accountOverviewPage.editDeductibleAndLimits.waitTillVisibilityOfElement(60);
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account Overview Page", "Clicked on EditDeductibles and Limit Button successfully");

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			Assertions.passTest("Create Quote Page", "Coverage A value changes from: "
					+ createQuotePage.coverageADwelling.getData() + " to: " + testData.get("L1D1-DwellingCovA"));
			Assertions.passTest("Create Quote Page", "Coverage B value changes from: "
					+ createQuotePage.coverageBOtherStructures.getData() + " to: " + testData.get("L1D1-DwellingCovB"));
			Assertions.passTest("Create Quote Page",
					"Coverage C value changes from: " + createQuotePage.coverageCPersonalProperty.getData() + " to: "
							+ testData.get("L1D1-DwellingCovC"));
			Assertions.passTest("Create Quote Page", "Coverage D value changes from: "
					+ createQuotePage.coverageDFairRental.getData() + " to: " + testData.get("L1D1-DwellingCovD"));
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page",
					"Quote Details Entered successfully and Clicked on Create Quote Button successfully");

			// Checking the referral message has TIV increased more than 20%
			Assertions.verify(referQuotePage.refMessageForUSM.checkIfElementIsDisplayed(), true, "Refer Quote Page",
					"Referral message  TIV increased more than 20% is verified,Referral message is:"
							+ referQuotePage.refMessageForUSM.getData(),
					false, false);
			referQuotePage.contactName.setData(testData.get("ProducerName"));
			referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
			referQuotePage.referQuote.waitTillPresenceOfElement(60);
			referQuotePage.referQuote.waitTillVisibilityOfElement(60);
			referQuotePage.referQuote.scrollToElement();
			referQuotePage.referQuote.click();
			String referQuoteNumber = referQuotePage.quoteNumberforReferral.getData();
			Assertions.verify(referQuotePage.quoteNumberforReferral.checkIfElementIsDisplayed(), true,
					"Referral Quote Page",
					"The Quote is referred with Quote number is verified, Quote number is:" + referQuoteNumber, false,
					false);

			// SignOut as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Producer successfully");

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");

			// Clicking on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(referQuoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();

			// Approve the quote for referral
			if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
				referralPage.pickUp.scrollToElement();
				referralPage.pickUp.click();
			}

			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(approveDeclineQuotePage.approveButton.checkIfElementIsDisplayed(), true,
					"Approve Decline Quote Page", "Approve Decline Quote page loaded successfully", false, false);
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Approve Decline Quote Page", "Bind Request approved successfully");
			referralPage.close.waitTillPresenceOfElement(60);
			referralPage.close.waitTillVisibilityOfElement(60);
			referralPage.close.scrollToElement();
			referralPage.close.click();

			// Searched for New Quote
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.waitTillPresenceOfElement(60);
			homePage.findFilterArrow.click();
			homePage.findFilterQuoteOption.click();
			homePage.findQuoteNumber.setData(referQuoteNumber);
			homePage.findBtnQuote.waitTillPresenceOfElement(60);
			homePage.findBtnQuote.click();
			Assertions.passTest("Home Page", "Click on Searched Quote and Navigated to Account Overview Page ");

			// Click on Edit Dwelling link
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();
			Assertions.passTest(" Account Overview Page",
					"Clicked on EditDwelling link successfully and Navigated to Dwelling Page");
			dwellingPage.lapseInCoverageYes.waitTillPresenceOfElement(60);
			dwellingPage.lapseInCoverageYes.waitTillVisibilityOfElement(60);
			dwellingPage.lapseInCoverageYes.scrollToElement();
			dwellingPage.lapseInCoverageYes.click();
			Assertions.passTest("Dwelling page",
					"LapseIn Coverage Selected as:" + testData.get("L1D1-LapseInCoverage"));
			dwellingPage.continueWithUpdateBtn.waitTillPresenceOfElement(60);
			dwellingPage.continueWithUpdateBtn.waitTillVisibilityOfElement(60);
			dwellingPage.continueWithUpdateBtn.scrollToElement();
			dwellingPage.continueWithUpdateBtn.click();
			Assertions.passTest("Dwelling Page", "Clicked on ContinueWithUpdate Button successfully");
			dwellingPage.createQuote.waitTillPresenceOfElement(60);
			dwellingPage.createQuote.waitTillVisibilityOfElement(60);
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			Assertions.passTest("Dwelling Page",
					"Clicked on Create Quote Button successfully and Navigated to Create Quote Page");

			// click on get quote
			createQuotePage.getAQuote.waitTillPresenceOfElement(60);
			createQuotePage.getAQuote.waitTillVisibilityOfElement(60);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on GetAQuote Button successfully");

			if (createQuotePage.warningMessages.formatDynamicPath("lapse in coverage").checkIfElementIsDisplayed()
					&& createQuotePage.warningMessages.formatDynamicPath("lapse in coverage")
							.checkIfElementIsPresent()) {

				Assertions.verify(
						createQuotePage.warningMessages
								.formatDynamicPath("lapse in coverage").checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"Lapse In Coverage warning message is verified, warning message is:"
								+ createQuotePage.warningMessages.formatDynamicPath("lapse in coverage").getData(),
						false, false);
			} else {
				Assertions.verify(
						createQuotePage.warningMessages
								.formatDynamicPath("lapse in coverage").checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"Lapse In Coverage warning message is Not verified, warning message is:"
								+ createQuotePage.warningMessages.formatDynamicPath("lapse in coverage").getData(),
						false, false);
			}

			createQuotePage.continueButton.waitTillPresenceOfElement(60);
			createQuotePage.continueButton.waitTillVisibilityOfElement(60);
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
			Assertions.passTest("Create Quote Page", "Clicked on continue button successfully");

			// Getting Renewal Quote Number
			Assertions.verify(accountOverviewPage.issueQuoteButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			String newRenewalQuoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "New Renewal Quote Number is : " + newRenewalQuoteNumber);

			// Click on issue quote
			accountOverviewPage.issueQuoteButton.waitTillPresenceOfElement(60);
			accountOverviewPage.issueQuoteButton.waitTillVisibilityOfElement(60);
			accountOverviewPage.issueQuoteButton.scrollToElement();
			accountOverviewPage.issueQuoteButton.click();
			Assertions.passTest("Account Overview Page", "Click on Issue Quote Button successfully");
			accountOverviewPage.clickOnRenewalRequestBindNAHO(testData, newRenewalQuoteNumber);
			Assertions.passTest("Account Overview Page",
					"Click on Bind Request Button successfully and Navigated to Request Bind page successfully");

			// enter additional policy information
			requestBindPage.floodCoverageArrow.scrollToElement();
			requestBindPage.floodCoverageArrow.click();
			requestBindPage.floodCoverageOption.formatDynamicPath(testData.get("ApplicantHaveFloodPolicy"))
					.scrollToElement();
			requestBindPage.floodCoverageOption.formatDynamicPath(testData.get("ApplicantHaveFloodPolicy")).click();
			Assertions.passTest("Request Bind Page",
					"Selected the Flood as :" + testData.get("ApplicantHaveFloodPolicy"));
			requestBindPage.renewalRequestBindNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Details Entered successfully");

			// Getting Policy Number from policy summary Page
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. Renewal PolicyNumber is "
							+ policySummaryPage.policyNumber.getData(),
					false, false);

			// Sign Out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC073 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC073 ", "Executed Successfully");
			}
		}
	}
}
