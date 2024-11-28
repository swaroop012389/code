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
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC047_PNBOOS002 extends AbstractNAHOTest {

	public TC047_PNBOOS002() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBOOS002.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the Objects
		HomePage homePage = new HomePage();
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
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ViewPolicySnapShot viewPolicySnapShot = new ViewPolicySnapShot();
		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
		Map<String, String> testData = data.get(dataValue1);
		Map<String, String> testData1 = data.get(dataValue2);
		Map<String, String> testData2 = data.get(dataValue3);
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

			// Geting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			String quoteNumber = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Click on Request Bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on request bind button");

			// Clicking on answer no button
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered");

			// Entering bind details
			quoteNumber = requestBindPage.quoteNumber.getData();
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind deyals entered successfully");

			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Searching the Bind Referral
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			accountOverviewPage.openReferralLink.checkIfElementIsDisplayed();
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on open referral link");

			// Approve Referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();

			// click on approve in Approve Decline Quote page
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Referral Page", "Quote referral approved successfully");

			// Asserting policy number
			String policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is : " + policyNumber, false, false);

			// click on policy snapshot link
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View policy snapshot link");

			// Asserting value before change
			Assertions.verify(viewPolicySnapShot.goBackButton.checkIfElementIsDisplayed(), true,
					"View Policy SnapShot Page", "View Policy SnapShot Page loaded successfully", false, false);
			String namedstromInitialValue = viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath(20)
					.getData();
			Assertions.verify(namedstromInitialValue.contains(testData.get("NamedStormValue")), true,
					"View Policy SnapShot Page",
					"The Named Storm Value Before PB-END  is " + namedstromInitialValue + " displayed is verified",
					false, false);

			// Click on back button
			viewPolicySnapShot.goBackButton.scrollToElement();
			viewPolicySnapShot.goBackButton.click();

			// Process PB Endorsement
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Initiating PB Endorsement to update coverage details
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			Assertions.passTest("Endorse Policy Page",
					"The Transaction Effective Date is " + testData1.get("TransactionEffectiveDate"));
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Inspection Contact Link");
			endorsePolicyPage.enterEndorsement_DetailsNAHO(testData1, testData1.get("ProductSelection"));
			Assertions.passTest("Endorse Policy Page", "Endorse details entered successfully");

			// Asserting the Changed coverage value from Policy snapshot page
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page Loaded successfully", false, false);

			// Click on Endorse history
			policySummaryPage.endorsementTransaction.formatDynamicPath(1).waitTillPresenceOfElement(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(1).waitTillButtonIsClickable(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(1).scrollToElement();
			policySummaryPage.endorsementTransaction.formatDynamicPath(1).click();
			policySummaryPage.waitTime(1);
			Assertions.passTest("Policy Summary Page", "Clicked on PB-END transaction history");

			// click on policy snapshot link
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Policy snapshot link");

			// Asserting the Changed Value From Policy Snapshot Page
			Assertions.verify(viewPolicySnapShot.goBackButton.checkIfElementIsDisplayed(), true,
					"View Policy SnapShot Page", "View Policy SnapShot Page loaded successfully", false, false);

			String namedstromChangedValue = viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath(20)
					.getData();
			Assertions.verify(namedstromChangedValue.contains(testData1.get("NamedStormValue")), true,
					"View Policy SnapShot Page",
					"The Named Storm Value Changed to  " + namedstromChangedValue + " displayed is verified", false,
					false);

			// Click on back button
			viewPolicySnapShot.goBackButton.click();

			// Process NPB Endorsement
			policySummaryPage.endorseNPB.scrollToElement();
			policySummaryPage.endorseNPB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse NPB link");

			// Initiating NPB Endorsement to Update the AI Details
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			Assertions.passTest("Endorse Policy Page",
					"The Transaction Effective Date is " + testData2.get("TransactionEffectiveDate"));
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Inspection Contact Link");
			endorsePolicyPage.enterEndorsement_DetailsNAHO(testData2, testData.get("ProductSelection"));

			// Asserting this transaction has OOS
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page Loaded successfully", false, false);

			Assertions.verify(policySummaryPage.transHistReason.formatDynamicPath(4).checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "This Transaction has " + testData2.get("TransactionType") + " is verified",
					false, false);

			// Click on Endorse history
			policySummaryPage.transHistReason.formatDynamicPath(2).scrollToElement();
			policySummaryPage.transHistReason.formatDynamicPath(2).click();
			Assertions.passTest("Policy Summary Page", "Clicked on NB transaction history");

			// click on policy snapshot link
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Policy snapshot link");

			// Asserting the Changed Value From Policy Snapshot Page
			Assertions.verify(viewPolicySnapShot.goBackButton.checkIfElementIsDisplayed(), true,
					"View Policy SnapShot Page", "View Policy SnapShot Page loaded successfully", false, false);

			String inspectionContactName = viewPolicySnapShot.homeownerQuoteDetails.formatDynamicPath(7).getData();
			String inspectionPhone = viewPolicySnapShot.homeownerQuoteDetails.formatDynamicPath(8).getData();
			Assertions.verify(inspectionContactName.contains(testData.get("InspectionContact")), true,
					"View Policy Snapshot Page", "The " + inspectionContactName + " displayed is verified", false,
					false);

			Assertions.verify(inspectionPhone.contains(testData.get("InspectionNumber")), true,
					"View Policy Snapshot Page", "The " + inspectionPhone + " displayed is verified", false, false);

			// Click on back button
			viewPolicySnapShot.goBackButton.click();

			// Click on Endorse history
			policySummaryPage.endorsementTransaction.formatDynamicPath(3).waitTillPresenceOfElement(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(3).waitTillButtonIsClickable(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(3).scrollToElement();
			policySummaryPage.endorsementTransaction.formatDynamicPath(3).click();
			policySummaryPage.waitTime(1);
			Assertions.passTest("Policy Summary Page", "Clicked on NPB-END transaction history");

			// click on policy snapshot link
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Policy snapshot link");

			// Asserting the changed value

			Assertions.verify(viewPolicySnapShot.goBackButton.checkIfElementIsDisplayed(), true,
					"View Policy SnapShot Page", "View Policy SnapShot Page loaded successfully", false, false);

			String changedInspectionContactName = viewPolicySnapShot.homeownerQuoteDetails.formatDynamicPath(7)
					.getData();
			String changedInspectionPhone = viewPolicySnapShot.homeownerQuoteDetails.formatDynamicPath(8).getData();
			Assertions.verify(changedInspectionContactName.contains(testData2.get("InspectionContact")), true,
					"View Policy Snapshot Page",
					"The changed " + changedInspectionContactName + "  displayed is verified", false, false);

			Assertions.verify(changedInspectionPhone.contains(testData2.get("InspectionNumber")), true,
					"View Policy Snapshot Page", "The changed " + changedInspectionPhone + " displayed is verified",
					false, false);

			// Click on back button

			viewPolicySnapShot.goBackButton.click();

			// Asserting Initial named storm value
			Assertions.verify(policySummaryPage.transHistReason.formatDynamicPath(4).checkIfElementIsDisplayed(), true,
					"Policy Summary Page",
					"The Transaction Reason " + policySummaryPage.transHistReason.formatDynamicPath(4).getData()
							+ " displayed is verified",
					false, false);

			// Click on Endorse history
			policySummaryPage.endorsementTransaction.formatDynamicPath(2).waitTillPresenceOfElement(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(2).waitTillButtonIsClickable(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(2).scrollToElement();
			policySummaryPage.endorsementTransaction.formatDynamicPath(2).click();
			policySummaryPage.waitTime(1);
			Assertions.passTest("Policy Summary Page", "Clicked on PB-END Reversal transaction history");

			// click on policy snapshot link
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Policy snapshot link");

			// Asserting the Changed Value From Policy Snapshot Page
			Assertions.verify(viewPolicySnapShot.goBackButton.checkIfElementIsDisplayed(), true,
					"View Policy SnapShot Page", "View Policy SnapShot Page loaded successfully", false, false);

			String initialNamedstromValue = viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath(20)
					.getData();
			Assertions.verify(initialNamedstromValue.contains(testData.get("NamedStormValue")), true,
					"View Policy SnapShot Page",
					"The Initial Named Storm Value " + initialNamedstromValue + " displayed is verified", false, false);

			// Click on back button
			viewPolicySnapShot.goBackButton.click();

			// Click on Reverse last Endorsement link
			policySummaryPage.reversePreviousEndorsementLink.scrollToElement();
			policySummaryPage.reversePreviousEndorsementLink.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Reverse Last Endorsement Link");

			// click on Complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// Click on Close
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Asserting the Reversal Endorsement transaction
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page Loaded successfully", false, false);

			Assertions.verify(policySummaryPage.transHistReason.formatDynamicPath(5).checkIfElementIsDisplayed(), true,
					"Policy Summary Page",
					"The Transaction Reason " + policySummaryPage.transHistReason.formatDynamicPath(5).getData()
							+ " displayed is verified",
					false, false);

			// Click on Endorse history
			policySummaryPage.endorsementTransaction.formatDynamicPath(4).waitTillPresenceOfElement(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(4).waitTillButtonIsClickable(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(4).scrollToElement();
			policySummaryPage.endorsementTransaction.formatDynamicPath(4).click();
			policySummaryPage.waitTime(1);
			Assertions.passTest("Policy Summary Page", "Clicked on NPB-END Reversal transaction history");

			// click on policy snapshot link
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Policy snapshot link");

			// Asserting the Initial values
			Assertions.verify(viewPolicySnapShot.goBackButton.checkIfElementIsDisplayed(), true,
					"View Policy SnapShot Page", "View Policy SnapShot Page loaded successfully", false, false);

			String initialInspectionContactName = viewPolicySnapShot.homeownerQuoteDetails.formatDynamicPath(7)
					.getData();
			String initialInspectionPhone = viewPolicySnapShot.homeownerQuoteDetails.formatDynamicPath(8).getData();
			Assertions.verify(initialInspectionContactName.contains(testData.get("InspectionContact")), true,
					"View Policy Snapshot Page",
					"After the Reverse Last Endorsement the " + initialInspectionContactName + " displayed is verified",
					false, false);

			Assertions.verify(initialInspectionPhone.contains(testData.get("InspectionNumber")), true,
					"View Policy Snapshot Page",
					"After the Reverse Last Endorsement the " + initialInspectionPhone + " displayed is verified",
					false, false);

			// sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC047 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC047 ", "Executed Successfully");
			}
		}
	}

}
