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
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.EndorseSummaryDetailsPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC004_PNBPB004 extends AbstractNAHOTest {

	public TC004_PNBPB004() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBPB004.xls";
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
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		EndorseSummaryDetailsPage endorseSummaryDetailsPage = new EndorseSummaryDetailsPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();

		// Initializing variable
		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
		String policyNumber;
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

			// Getting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			int quoteLen = accountOverviewPage.quoteNumber.getData().length();
			String quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Binding the quote and creating the policy
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

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
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

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

			// Getting policy number
			policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is "
							+ policySummaryPage.policyNumber.getData(),
					false, false);

			// Click on PB endorsement
			policySummaryPage.endorsePB.waitTillVisibilityOfElement(60);
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on endorse pb link");

			// Click on Edit Location or Building Information
			testData = data.get(data_Value2);
			endorsePolicyPage.endorsementEffDate.waitTillPresenceOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillVisibilityOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillElementisEnabled(60);
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page",
					"Entered endorsement effective date " + testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.waitTime(3);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillPresenceOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillElementisEnabled(60);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillButtonIsClickable(60);
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on change coverage option link");

			// Updating coverage A value
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			Assertions.passTest("Create Quote Page", "Updated Cov A value");

			// Review and continue create quote page
			createQuotePage.scrollToBottomPage();
			createQuotePage.continueEndorsementButton.waitTillVisibilityOfElement(60);
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			Assertions.passTest("Create Quote Page", "Clicked on continue endorsement button");
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.waitTillButtonIsClickable(60);
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}
			if (createQuotePage.override.checkIfElementIsPresent()
					&& createQuotePage.override.checkIfElementIsDisplayed()) {
				createQuotePage.override.scrollToElement();
				createQuotePage.override.click();
			}

			// continue with endorsement the endorsement
			endorsePolicyPage.nextButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on next button");

			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}
			Assertions.passTest("Endorse Policy Page", "Clicked on continue button");

			// Assert dwelling deductibles update in Endorsement Summary page
			String covA = endorseSummaryDetailsPage.buildingLevelChangesToCol.formatDynamicPath(1, 5).getData()
					.replace(",", "");
			String coverageAVal = covA.replace("$", "");
			Assertions.verify(coverageAVal.contains(testData.get("L1D1-DwellingCovA")), true, "Endorse Summary Page",
					"Coverage A value is updated to: "
							+ endorseSummaryDetailsPage.buildingLevelChangesToCol.formatDynamicPath(1, 5).getData(),
					false, false);

			// Complete the endorsement
			endorsePolicyPage.completeButton.waitTillPresenceOfElement(60);
			endorsePolicyPage.completeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.completeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.completeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			endorsePolicyPage.scrollToBottomPage();
			endorsePolicyPage.closeButton.waitTillPresenceOfElement(60);
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Enodre Policy Page", "Clicked on comlete and close button");

			// Adding code for IO-18763 and IO-18978
			testData = data.get(data_Value1);
			for (int i = 0; i <= 8; i++) {
				int dataValuei = i;
				testData = data.get(dataValuei);
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();
				Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy Link");

				if (policySummaryPage.expaacMessage.checkIfElementIsPresent()
						&& policySummaryPage.expaacMessage.checkIfElementIsDisplayed()) {
					Assertions.verify(policySummaryPage.expaacMessage.checkIfElementIsDisplayed(), true,
							"Policy Renewal Page", policySummaryPage.expaacMessage.getData() + " Message verified",
							false, false);

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

				// Getting renewal quote
				quoteLen = accountOverviewPage.quoteNumber.getData().length();
				quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
				Assertions.passTest("Account Overview Page", "Renewal Quote Number1 is " + quoteNumber);

				// Asserting status on renewal quote
				if (i == 0) {
					Assertions.addInfo("Scenario 0" + i, "Asserting renewal quote status as 'Referred'");
					Assertions.verify(accountOverviewPage.referredStatus.getData(), "Referred", "Account Overview Page",
							"Renewal quote status is " + accountOverviewPage.referredStatus.getData()
									+ " When Roof Age is " + testData.get("L1D1-DwellingYearBuilt")
									+ " and Roof Cladding is " + testData.get("L1D1-DwellingRoofCladding")
									+ " while renewal is processed",
							false, false);
					Assertions.addInfo("Scenario 0" + i, "Scenario 0" + i + " Ended");
					accountOverviewPage.waitTime(3);

					// Asserting note bar message'Generated by the residential automated renewal
					// job. Reasons for referral: This account is outside underwriting guidelines:
					// The quoted building has a Coverage A limit of more than 2000000 and is
					// ineligible. Please review the underwriting guidelines for limit availability
					Assertions.addInfo("Scenario 0" + i, "Asserting note bar message");
					Assertions.verify(accountOverviewPage.noteBarMessage.checkIfElementIsDisplayed(), true,
							"Account Overview Page",
							accountOverviewPage.noteBarMessage.getData() + " is displayed in note bar", false, false);
					Assertions.addInfo("Scenario 0" + i, "Scenario 0" + i + " Ended");

					// Click on view print full quote link
					accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
					accountOverviewPage.viewPrintFullQuoteLink.click();
					Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
							"View Print Full Quote Page", "Clicked on view print full quote link successfully", false,
							false);

					// Asserting and verifying roof coverage as 'Replacement Cost'on view print
					// full quote page
					// when year built 0-15 years older,roof cladding = any roof cladding, expect
					// other and Wood Shakes or Wood Shingles year roof last replaced same as year
					// built
					Assertions.addInfo("Scenario 0" + i, "Asserting and verifying roof coverage as 'Replacement Cost'");
					Assertions.verify(
							viewOrPrintFullQuotePage.roofCoverage.formatDynamicPath(1).getData()
									.equals("Replacement Cost"),
							true, "View Print Full Quote Page",
							"Roof coverage is " + viewOrPrintFullQuotePage.roofCoverage.formatDynamicPath(1).getData()
									+ " displayed",
							false, false);
					viewOrPrintFullQuotePage.backButton.scrollToElement();
					viewOrPrintFullQuotePage.backButton.click();
					Assertions.addInfo("Scenario 0" + i, "Scenario 0" + i + " Ended");
				}

				// Click on open referral link
				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();
				Assertions.passTest("Account Overview Page", "Clicked on open referral link");

				// Verifying and Asserting referral message Generated by the residential
				// automated renewal job. Reasons for referral: This account is outside
				// underwriting guidelines: The quoted building has a Coverage A limit of more
				// than $2000000 and is ineligible. Please review the underwriting guidelines
				// for limit availability.
				Assertions.addInfo("Scenario 0" + i, "Verifying and Asserting referral message");
				Assertions.verify(
						referralPage.producerQuoteStatus.formatDynamicPath("The quoted").getData()
								.contains("The quoted building has a Coverage A limit of more than"),
						true, "Referral Page",
						"The referral message is "
								+ referralPage.producerQuoteStatus.formatDynamicPath("The quoted").getData()
								+ " displayed",
						false, false);
				Assertions.addInfo("Scenario 0" + i, "Scenario 0" + i + " Ended");

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

				// Search for quote
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuote(quoteNumber);
				Assertions.passTest("Home Page", "Quote searched successfully");

				// Click on release renewal to producer
				accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
				accountOverviewPage.releaseRenewalToProducerButton.click();
				Assertions.passTest("Account Overview Page", "Clicked on release renewal to producer");

				// Click on override premium link
				accountOverviewPage.overridePremiumLink.scrollToElement();
				accountOverviewPage.overridePremiumLink.click();
				Assertions.passTest("Account Overview Page", "Clicked on override premium link");
				if (i == 1 || i == 2 || i == 3 || i == 5 || i == 6 || i == 7) {
					Assertions.addInfo("Scenario 0" + i,
							"Asserting Total inspection fees and presence of order inspection check box not selected");
						waitTime(2);
					Assertions.verify(
							overridePremiumAndFeesPage.originalInspectionFee.getData()
									.contains(testData.get("InspectionFeeOverride")),
							true, "Override Premium Page", "Total inspection fee is "
									+ overridePremiumAndFeesPage.originalInspectionFee.getData() + " displayed",
							false, false);
					// order inspection check box should not be selected, when last inspection date
					// should not be 4 years back from today date
					overridePremiumAndFeesPage.waitTime(2);
					Assertions.verify(overridePremiumAndFeesPage.transactionOrderInspection.checkIfElementIsSelected(), false,
							"Override Premium Page", "Order inspection check box not selected", false, false);
					Assertions.addInfo("Scenario 0" + i, "Scenario 0" + i + " Ended");
				} else {

					// Asserting Total inspection fees and order inspection checked
					Assertions.addInfo("Scenario 0" + i,
							"Asserting Total inspection fees and presence of order inspection checked");
					Assertions.verify(
							overridePremiumAndFeesPage.originalInspectionFee.getData()
									.contains(testData.get("InspectionFeeOverride")),
							true, "Override Premium Page", "Total inspection fee is "
									+ overridePremiumAndFeesPage.originalInspectionFee.getData() + " displayed",
							false, false);
					Assertions.verify(overridePremiumAndFeesPage.transactionOrderInspection.checkIfElementIsSelected(), true,
							"Override Premium Page", "Order inspection check box selected", false, false);
					Assertions.addInfo("Scenario 0" + i, "Scenario 0" + i + " Ended");
				}

				// Click on cancel button
				overridePremiumAndFeesPage.cancelButton.scrollToElement();
				overridePremiumAndFeesPage.cancelButton.click();
				Assertions.passTest("Override Premium Page", "Clicked on cancel button");

				// Binding the quote and creating the policy
				accountOverviewPage.clickOnRenewalRequestBindNAHO(testData, quoteNumber);
				Assertions.passTest("Account Overview Page", "Clicked on Request bind button successfully");

				// Entering renewal bind details
				requestBindPage.renewalRequestBindNAHO(testData);
				Assertions.passTest("Request Bind Page", "Bind Details Entered successfully");

				// Asserting policy number
				policyNumber = policySummaryPage.policyNumber.getData();
				Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true,
						"Policy Summary Page",
						"Policy Summary Page loaded successfully, Renewal Policy Number" + i + " is " + policyNumber,
						false, false);

			}

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC004 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC004 ", "Executed Successfully");
			}
		}
	}
}
