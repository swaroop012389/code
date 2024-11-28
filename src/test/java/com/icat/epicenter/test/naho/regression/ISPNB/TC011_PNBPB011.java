package com.icat.epicenter.test.naho.regression.ISPNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ChangeNamedInsuredPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.EndorseSummaryDetailsPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC011_PNBPB011 extends AbstractNAHOTest {

	public TC011_PNBPB011() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBPB011.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

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
		ChangeNamedInsuredPage changeNamedInsuredPage = new ChangeNamedInsuredPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();

		String policyNumber;
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

			// Adding Policy fee and Inspection fee
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
/*
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
*/
			// Validating the premium amount
			policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is "
							+ policySummaryPage.policyNumber.getData(),
					false, false);

			// Click on PB endorsement
			policySummaryPage.endorsePB.waitTillVisibilityOfElement(60);
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			policySummaryPage.endorsePB.waitTillInVisibilityOfElement(60);

			// Assert NPB transaction links
			Assertions.verify(
					endorsePolicyPage.changeNamedInsuredLink.checkIfElementIsPresent()
							&& endorsePolicyPage.changeNamedInsuredLink.checkIfElementIsDisplayed(),
					true, "Endorse Policy Page",
					endorsePolicyPage.changeNamedInsuredLink.getData() + " link is present", false, false);
			Assertions.verify(
					endorsePolicyPage.changeInspectionContactLink.checkIfElementIsPresent()
							&& endorsePolicyPage.changeInspectionContactLink.checkIfElementIsDisplayed(),
					true, "Endorse Policy Page",
					endorsePolicyPage.changeInspectionContactLink.getData() + " link is present", false, false);
			Assertions.verify(
					endorsePolicyPage.changeAIInformationLink.checkIfElementIsPresent()
							&& endorsePolicyPage.changeAIInformationLink.checkIfElementIsDisplayed(),
					true, "Endorse Policy Page",
					endorsePolicyPage.changeAIInformationLink.getData() + " link is present", false, false);
			Assertions.verify(
					endorsePolicyPage.producerContactLink.checkIfElementIsPresent()
							&& endorsePolicyPage.producerContactLink.checkIfElementIsDisplayed(),
					true, "Endorse Policy Page", endorsePolicyPage.producerContactLink.getData() + " link is present",
					false, false);
			Assertions.verify(
					endorsePolicyPage.changePaymentPlanLink.checkIfElementIsPresent()
							&& endorsePolicyPage.changePaymentPlanLink.checkIfElementIsDisplayed(),
					true, "Endorse Policy Page", endorsePolicyPage.changePaymentPlanLink.getData() + " link is present",
					false, false);

			// Click on Edit Location or Building Information
			endorsePolicyPage.endorsementEffDate.waitTillPresenceOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillVisibilityOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillElementisEnabled(60);
			testData = data.get(data_Value2);
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			endorsePolicyPage.waitTime(3);
			endorsePolicyPage.changeNamedInsuredLink.waitTillPresenceOfElement(60);
			endorsePolicyPage.changeNamedInsuredLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeNamedInsuredLink.waitTillElementisEnabled(60);
			endorsePolicyPage.changeNamedInsuredLink.waitTillButtonIsClickable(60);
			endorsePolicyPage.changeNamedInsuredLink.scrollToElement();
			endorsePolicyPage.changeNamedInsuredLink.click();

			// Update Named Insured and mailing address
			changeNamedInsuredPage.enterInsuredAddressDetailPB(testData);

			// Continue with endorsement the endorsement
			endorsePolicyPage.nextButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			endorsePolicyPage.continueButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.continueButton.scrollToElement();
			endorsePolicyPage.continueButton.click();
			endorsePolicyPage.scrollToBottomPage();

			// Assert update from HO3 to HO5 on Endorse policy summary page
			Assertions.verify(
					endorseSummaryDetailsPage.policyLevelChangesToCol.formatDynamicPath(1, 5).getData()
							.contains(testData.get("InsuredAddr1")
									+ ", " + testData.get("InsuredAddr2") + ", " + testData.get("InsuredCity") + ", "
									+ testData.get("InsuredState") + " " + testData.get("InsuredZIP")),
					true, "Endorse Summary Page",
					"Insured Address is updated to "
							+ endorseSummaryDetailsPage.policyLevelChangesToCol.formatDynamicPath(1, 5).getData(),
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.policyLevelChangesToCol
							.formatDynamicPath(2, 5).getData().contains(testData.get("InsuredEmail")),
					true, "Endorse Summary Page",
					"Insured Email is updated to "
							+ endorseSummaryDetailsPage.policyLevelChangesToCol.formatDynamicPath(2, 5).getData(),
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.policyLevelChangesToCol
							.formatDynamicPath(3, 5).getData().contains(testData.get("ExtendedNamedInsured")),
					true, "Endorse Summary Page",
					"Extended Insured Name is updated to "
							+ endorseSummaryDetailsPage.policyLevelChangesToCol.formatDynamicPath(3, 5).getData(),
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.policyLevelChangesToCol
							.formatDynamicPath(4, 5).getData().contains(testData.get("InsuredName")),
					true, "Endorse Summary Page",
					"Insured Name is updated to "
							+ endorseSummaryDetailsPage.policyLevelChangesToCol.formatDynamicPath(4, 5).getData(),
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.policyLevelChangesToCol.formatDynamicPath(5, 5).getData()
							.contains(testData.get("InsuredPhoneNumAreaCode") + "."
									+ testData.get("InsuredPhoneNumPrefix") + "." + testData.get("InspectionNumber")),
					true, "Endorse Summary Page",
					"Insured Phone Number is updated to "
							+ endorseSummaryDetailsPage.policyLevelChangesToCol.formatDynamicPath(5, 5).getData(),
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

			// Adding code for IO-18763
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy Link");

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
				testData = data.get(data_Value1);
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

			// Asserting status on renewal quote
			Assertions.verify(accountOverviewPage.referredStatus.getData(), "Referred", "Account Overview Page",
					"Renewal quote status is " + accountOverviewPage.referredStatus.getData() + " When Roof Age is "
							+ testData.get("L1D1-DwellingYearBuilt") + " and Roof Cladding is "
							+ testData.get("L1D1-DwellingRoofCladding") + " while renewal is processed",
					false, false);
			accountOverviewPage.waitTime(3);
			Assertions.verify(accountOverviewPage.noteBarMessage.checkIfElementIsDisplayed(), true,
					"Account Overview Page", accountOverviewPage.noteBarMessage.getData() + " is displayed in note bar",
					false, false);

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC011 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC011 ", "Executed Successfully");
			}
		}
	}
}
