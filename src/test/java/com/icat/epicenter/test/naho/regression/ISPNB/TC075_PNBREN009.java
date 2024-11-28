package com.icat.epicenter.test.naho.regression.ISPNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicyDocumentsPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC075_PNBREN009 extends AbstractNAHOTest {

	public TC075_PNBREN009() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBREN009.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {
		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ReferralPage referralPage = new ReferralPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		// Initializing the variables
		String quoteNumber;
		String quoteNumber2;
		String quoteNumber3;
		String policyNumber;
		int dataValue1 = 0;
		int dataValue2 = 1;
		Map<String, String> testData = data.get(dataValue1);
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
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting quote number 1
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number : " + quoteNumber);

			// clicking on request bind in Account overview page
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Entering details in Underwriting Questions Page
			Assertions.verify(underwritingQuestionsPage.saveButton.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered successfully");

			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Searching the Bind Referral
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			accountOverviewPage.openReferralLink.checkIfElementIsDisplayed();
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();

			// Approve Referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();

			// click on approve in Referral page
			Assertions.passTest("Referral Page", "Quote referral approved successfully");

			requestBindPage.approveRequestNAHO(testData);

			// Asserting policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is : " + policyNumber, false, false);

			// Go to home page and search the policy
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.passTest("Home Page", "Home Page loaded successfully");

			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Searched the policy successfully");

			// Click on Renew policy
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy Link successfully");

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

			// Getting Renewal Quote Number
			String renewalQuoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Renewal Quote Number is : " + renewalQuoteNumber);

			// Approving referral
			if (accountOverviewPage.openReferral.checkIfElementIsPresent()
					&& accountOverviewPage.openReferral.checkIfElementIsDisplayed()) {
				accountOverviewPage.approveRenewalReferralUSM(renewalQuoteNumber);
			}

			// Asserting the Renewal policy number has -01 at the end
			Assertions.verify(accountOverviewPage.deleteRenewalButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			Assertions
					.verify(accountOverviewPage.renewalOfferData.checkIfElementIsDisplayed(), true,
							"Account Overview Page", "The Renewal Policy Number is : "
									+ accountOverviewPage.renewalOfferData.getData() + " displayed is verified",
							false, false);

			// Asserting the Delete renewal link is available
			Assertions.verify(accountOverviewPage.deleteRenewalButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Delete Renewal Button present is verified", false, false);

			// Asserting Lapse Renewal button is not available
			Assertions.verify(accountOverviewPage.lapseRenewal.checkIfElementIsPresent(), false,
					"Account Overview Page", "Lapse Renewal Button not present is verified", false, false);

			// click on delete renewal
			accountOverviewPage.deleteRenewalButton.scrollToElement();
			accountOverviewPage.deleteRenewalButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Delete Renewal Button");

			// Click on Yes delete link
			accountOverviewPage.yesDeletePopup.waitTillVisibilityOfElement(60);
			accountOverviewPage.yesDeletePopup.scrollToElement();
			accountOverviewPage.yesDeletePopup.click();

			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully " + "Policy Number is : "
							+ policySummaryPage.getPolicynumber(),
					false, false);

			// Asserting Renewal deleted message
			Assertions.verify(policySummaryPage.renewalDelMsg.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Renewal Delete message " + policySummaryPage.renewalDelMsg.getData()
							+ " displayed is verified",
					false, false);

			// Click on Renew policy
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy Link successfully");

			// Getting Renewal Quote Number
			String renewalQuoteNumber2 = accountOverviewPage.quoteNumber.getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Renewal Quote Number is : " + renewalQuoteNumber2);

			// Approving referral
			if (accountOverviewPage.openReferral.checkIfElementIsPresent()
					&& accountOverviewPage.openReferral.checkIfElementIsDisplayed()) {
				accountOverviewPage.approveRenewalReferralUSM(renewalQuoteNumber2);
			}

			// click on edit deductibles and limits
			Assertions.verify(accountOverviewPage.editDeductibleAndLimits.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Deductibles and Limits");

			// Entering Create quote page Details
			testData = data.get(dataValue2);
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting quote number 2
			quoteNumber2 = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page",
					"Account Overview Page Loaded successfully.Quote Number 2 is : " + quoteNumber2);

			// Asserting Delete icon is available in account overview page
			Assertions.verify(accountOverviewPage.deleteQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Delete Icon present is verified", false, false);

			// click on issue quote
			accountOverviewPage.issueQuoteButton.scrollToElement();
			accountOverviewPage.issueQuoteButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Issue Quote Button successfully");

			// Asserting Delete icon is not available in account overview page
			Assertions.verify(accountOverviewPage.deleteQuote.checkIfElementIsPresent(), false, "Account Overview Page",
					"Delete Icon not present is verified", false, false);

			// create another quote and add prior loss to that
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Deductibles and Limits");

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting quote number 3
			quoteNumber3 = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page",
					"Account Overview Page Loaded successfully.Quote Number 3 is : " + quoteNumber3);

			// click on edit prior loss
			accountOverviewPage.priorLossEditLink.scrollToElement();
			accountOverviewPage.priorLossEditLink.click();
			Assertions.passTest("Account Overvire Page", "Clicked on Edit Prior loss link");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			}

			if (accountOverviewPage.gotoAccountOverviewButton.checkIfElementIsPresent()
					&& accountOverviewPage.gotoAccountOverviewButton.checkIfElementIsDisplayed()) {
				accountOverviewPage.gotoAccountOverviewButton.scrollToElement();
				accountOverviewPage.gotoAccountOverviewButton.click();
			}

			// Asserting the previous quotes status
			Assertions.verify(accountOverviewPage.quoteExpiredStatus.formatDynamicPath("3").checkIfElementIsDisplayed(),
					true, "Account Overview Page",
					"The Renewal Offer Quote Status : "
							+ accountOverviewPage.quoteExpiredStatus.formatDynamicPath("3").getData()
							+ " displayed is verified",
					false, false);

			// Asserting the quote 2 status which is issued
			Assertions.verify(accountOverviewPage.quoteExpiredStatus.formatDynamicPath("2").checkIfElementIsDisplayed(),
					true, "Account Overview Page",
					"Quote 2 :" + quoteNumber2 + " Status : "
							+ accountOverviewPage.quoteExpiredStatus.formatDynamicPath("2").getData()
							+ " displayed is verified",
					false, false);

			// Asserting quote 3 status which is not issued
			Assertions.verify(accountOverviewPage.quoteExpiredStatus.formatDynamicPath("1").checkIfElementIsDisplayed(),
					true, "Account Overview Page",
					"Quote 3 : " + quoteNumber3 + " Status : "
							+ accountOverviewPage.quoteExpiredStatus.formatDynamicPath("1").getData()
							+ " displayed is verified",
					false, false);

			// Asserting Delete icon is available on Unissued quote
			Assertions.verify(accountOverviewPage.deleteQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"Delete Icon present for the Unissued Quote : " + quoteNumber3 + " is verified", false, false);

			// click on delete icon and delete the quote
			accountOverviewPage.deleteQuote.scrollToElement();
			accountOverviewPage.deleteQuote.click();

			// Click on Yes delete link
			accountOverviewPage.yesDeletePopup.waitTillVisibilityOfElement(60);
			accountOverviewPage.yesDeletePopup.scrollToElement();
			accountOverviewPage.yesDeletePopup.click();
			Assertions.passTest("Account Overview Page", "Deleted the Quote successfully using delete icon");

			// Asserting the quote status
			Assertions.verify(accountOverviewPage.quoteStatus.formatDynamicPath("3").checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"The Quote Status : " + accountOverviewPage.quoteStatus.formatDynamicPath("3").getData()
							+ " displayed is verified",
					false, false);

			// Undelete the quote
			accountOverviewPage.undeleteQuote.scrollToElement();
			accountOverviewPage.undeleteQuote.click();
			accountOverviewPage.yesDeleteBuilding.waitTillVisibilityOfElement(60);
			accountOverviewPage.yesDeleteBuilding.scrollToElement();
			accountOverviewPage.yesDeleteBuilding.click();
			Assertions.passTest("Account Overview Page", "Restored the Quote successfully");

			Assertions
					.verify(accountOverviewPage.unlockMessage.checkIfElementIsDisplayed(), true,
							"Account Overview Page", "The Quote Restored message "
									+ accountOverviewPage.unlockMessage.getData() + " displayed is verified",
							false, false);

			// Create another quote
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit dwelling");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.reviewDwelling();

			if (dwellingPage.createQuote.checkIfElementIsPresent()
					|| dwellingPage.createQuote.checkIfElementIsDisplayed()) {
				dwellingPage.createQuote.waitTillPresenceOfElement(60);
				dwellingPage.createQuote.waitTillVisibilityOfElement(60);
				dwellingPage.createQuote.waitTillButtonIsClickable(60);
				dwellingPage.createQuote.scrollToElement();
				dwellingPage.createQuote.click();
			}
			Assertions.passTest("Dwelling Page", "Clicked on Create quote button successfully");

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			if (createQuotePage.getAQuote.checkIfElementIsPresent()
					&& createQuotePage.getAQuote.checkIfElementIsDisplayed()) {
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();
			}
			Assertions.passTest("Create Quote Page", "Clicked on Get a Quote successfully");

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			if (dwellingPage.override.checkIfElementIsPresent() && dwellingPage.override.checkIfElementIsDisplayed()) {
				dwellingPage.override.scrollToElement();
				dwellingPage.override.click();
			}

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Click on Issue quote
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.verify(accountOverviewPage.issueQuoteButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"Account Overview Page loaded successfully.Quote Number is : " + quoteNumber, false, false);

			accountOverviewPage.issueQuoteButton.scrollToElement();
			accountOverviewPage.issueQuoteButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Issue Quote Button successfully");

			// click on release to producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Renewal to Producer successfully");

			// Asserting Delete Renewal is not available
			Assertions.verify(accountOverviewPage.deleteRenewalButton.checkIfElementIsPresent(), false,
					"Account Overview Page", "Delete Renewal Button not present is verified", false, false);

			// Asserting Lapse renewal button is available on account overview page
			Assertions.verify(accountOverviewPage.lapseRenewal.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Lapse Renewal Button present is verified", false, false);

			// click on view previous policy button
			accountOverviewPage.viewPreviousPolicyButton.scrollToElement();
			accountOverviewPage.viewPreviousPolicyButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Previous Policy Button");

			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			// Asserting the Re-Renew policy link availability
			Assertions.verify(policySummaryPage.re_RenewPolicyLink.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "The Re-Renew Policy link displayed is verified", false, false);

			// click on re-renew policy link
			policySummaryPage.re_RenewPolicyLink.scrollToElement();
			policySummaryPage.re_RenewPolicyLink.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Re-Renew policy link");

			// Asserting Existing renewal will be delete warning message
			Assertions.verify(
					accountOverviewPage.priorLossDetails
							.formatDynamicPath("existing renewal").checkIfElementIsDisplayed(),
					true, "Policy Summary Page",
					"The Existing renewal will be delete warning message "
							+ accountOverviewPage.priorLossDetails.formatDynamicPath("existing renewal").getData()
							+ " displayed is verified",
					false, false);

			// click on yes
			accountOverviewPage.yesButton.scrollToElement();
			accountOverviewPage.yesButton.click();

			// Getting Renewal Quote Number
			String renewalQuoteNumber3 = accountOverviewPage.quoteNumber.getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "The Re-Renewal Quote Number is : " + renewalQuoteNumber3);

			// Approving Referral
			if (accountOverviewPage.openReferral.checkIfElementIsPresent()
					&& accountOverviewPage.openReferral.checkIfElementIsDisplayed()) {
				accountOverviewPage.approveRenewalReferralUSM(renewalQuoteNumber3);
			}

			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// click on release to producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Renewal to Producer successfully");

			// click on View Renewal Documents link
			accountOverviewPage.viewRenewalDocuments.scrollToElement();
			accountOverviewPage.viewRenewalDocuments.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Renewal Documents successfully");

			Assertions.verify(policyDocumentsPage.addDocumentButton.checkIfElementIsDisplayed(), true,
					"Policy Documents Page", "Policy Documents Page loaded successfully", false, false);

			// Asserting Lapse notice link is not available
			Assertions.verify(policyDocumentsPage.policyDocuments.formatDynamicPath("Lapse").checkIfElementIsPresent(),
					false, "Policy Documents Page", "Lapse Notice link not present is verified", false, false);

			// Go to home page and search the policy
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC075 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC075 ", "Executed Successfully");
			}
		}
	}

}
