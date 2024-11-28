package com.icat.epicenter.test.naho.regression.ISPNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.ChangeNamedInsuredPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorseInspectionContactPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC072_PNBREN006 extends AbstractNAHOTest {

	public TC072_PNBREN006() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBREN006.xls";
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
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ChangeNamedInsuredPage changeNamedInsuredPage = new ChangeNamedInsuredPage();
		EndorseInspectionContactPage endorseInspectionContactPage = new EndorseInspectionContactPage();
		ViewPolicySnapShot viewPolicySnapShot = new ViewPolicySnapShot();
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
			Assertions.passTest("Request Bind Page", "Bind Details entered successfully");

			// get policyNumber
			String policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"PolicyNumber is " + policyNumber, false, false);

			// Renew policy
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

			// Get renewal quote status
			Assertions.verify(accountOverviewPage.referredStatus.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Renewal quote status is " + accountOverviewPage.referredStatus.getData(),
					false, false);
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();

			// Approve Referral
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(approveDeclineQuotePage.approveButton.checkIfElementIsDisplayed(), true,
					"Approve Decline Quote Page", "Approve Decline Quote page loaded successfully", false, false);
			approveDeclineQuotePage.clickOnApprove();

			// go to home page and open renewal
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchPolicy(policyNumber);

			// Click on View Active renewal link
			policySummaryPage.viewActiveRenewal.scrollToElement();
			policySummaryPage.viewActiveRenewal.click();

			// Get renewal quote status and release renewal to producer button
			Assertions.verify(accountOverviewPage.internalRenewalStatus.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"Renewal quote status is " + accountOverviewPage.internalRenewalStatus.getData(), false, false);
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Release Renewal to Producer is displayed", false, false);

			// Click on Release renewal to producer button
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Renewal to Producer Button");
			Assertions.verify(accountOverviewPage.lapseRenewal.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Lapse Renewal Button is displayed", false, false);
			Assertions.verify(accountOverviewPage.renewalStatus.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Renewal quote status is " + accountOverviewPage.renewalStatus.getData(),
					false, false);

			// Binding the quote and creating the policy
			accountOverviewPage.clickOnRenewalRequestBindNAHO(testData, quoteNumber);

			testData = data.get(data_Value2);
			// Entering bind details
			requestBindPage.floodCoverageArrow.scrollToElement();
			requestBindPage.floodCoverageArrow.click();
			requestBindPage.floodCoverageOption.formatDynamicPath(testData.get("ApplicantHaveFloodPolicy"))
					.scrollToElement();
			requestBindPage.floodCoverageOption.formatDynamicPath(testData.get("ApplicantHaveFloodPolicy")).click();
			requestBindPage.contactEmailAddress.scrollToElement();
			requestBindPage.contactEmailAddress.setData(testData.get("ProducerEmail"));
			requestBindPage.dueDiligenceCheckbox.scrollToElement();
			requestBindPage.dueDiligenceCheckbox.select();
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();
			requestBindPage.waitTime(2);
			requestBindPage.requestBind.waitTillVisibilityOfElement(60);
			requestBindPage.requestBind.scrollToElement();
			requestBindPage.requestBind.click();

			// Click on override check box
			if (requestBindPage.overrideEffectiveDate.checkIfElementIsPresent()
					&& requestBindPage.overrideEffectiveDate.checkIfElementIsDisplayed()) {
				requestBindPage.overrideEffectiveDate.scrollToElement();
				requestBindPage.overrideEffectiveDate.select();
				requestBindPage.submit.scrollToElement();
				requestBindPage.submit.click();
				requestBindPage.waitTime(2);
				requestBindPage.requestBind.waitTillVisibilityOfElement(60);
				requestBindPage.requestBind.scrollToElement();
				requestBindPage.requestBind.click();
			}
			Assertions.passTest("Request Bind Page", "Bind Details entered successfully");

			// Asserting Renewal Policy Number
			String renewalPolicyNumber = policySummaryPage.policyNumber.getData();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Renewal PolicyNumber is " + renewalPolicyNumber, false, false);

			// HomePage and search NB policy
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchPolicy(policyNumber);

			// Endorse NPB link
			policySummaryPage.endorseNPB.scrollToElement();
			policySummaryPage.endorseNPB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse NPB Link");
			policySummaryPage.okButton.scrollToElement();
			policySummaryPage.okButton.click();

			// enter endorsement effective date
			endorsePolicyPage.endorsementEffDate.waitTillVisibilityOfElement(60);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Transaction Effective Date entered successfully");

			// click on change named insured and update details
			endorsePolicyPage.changeNamedInsuredLink.scrollToElement();
			endorsePolicyPage.changeNamedInsuredLink.click();
			changeNamedInsuredPage.enterInsuredAddressDetailPB(testData);
			Assertions.passTest("Endorse Policy Page", "Named Insured Details entered successfully");

			// Asserting roll forward button
			Assertions.verify(
					endorsePolicyPage.completeAndRollForwardBtn.checkIfElementIsPresent()
							&& endorsePolicyPage.completeAndRollForwardBtn.checkIfElementIsDisplayed(),
					true, "Endorse Policy Page", "Complete and Roll Forward to Renewal Button is displayed", false,
					false);

			// click on change Inspection contact and update details
			endorsePolicyPage.changeInspectionContactLink.scrollToElement();
			endorsePolicyPage.changeInspectionContactLink.click();
			endorseInspectionContactPage.enterInspectionContactPB(testData);
			Assertions.passTest("Endorse Policy Page", "Inspection Contact Details entered successfully");

			// Asserting roll forward button
			Assertions.verify(
					endorsePolicyPage.completeAndRollForwardBtn.checkIfElementIsPresent()
							&& endorsePolicyPage.completeAndRollForwardBtn.checkIfElementIsDisplayed(),
					true, "Endorse Policy Page", "Complete and Roll Forward to Renewal Button is displayed", false,
					false);

			// Click on complete and roll forward button
			endorsePolicyPage.completeAndRollForwardBtn.scrollToElement();
			endorsePolicyPage.completeAndRollForwardBtn.click();

			// CLick on close button
			endorsePolicyPage.scrollToTopPage();
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Asserting transaction on NB policy page
			Assertions.verify(
					policySummaryPage.transactionType.formatDynamicPath("NPB-END").checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "NPB Endorsement is displayed in transaction history of NB Policy", false,
					false);
			policySummaryPage.transHistReasonNAHO.formatDynamicPath(3).scrollToElement();
			policySummaryPage.transHistReasonNAHO.formatDynamicPath(3).click();

			// Click on view Policy Snapshot
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();

			// Assert details in view policy snapshot page
			waitTime(5);
			Assertions.verify(
					viewPolicySnapShot.viewPolicySnapshotDetails.formatDynamicPath(testData.get("InsuredAddr1"))
							.getData(),
					testData.get("InsuredAddr1") + ", " + testData.get("InsuredAddr2") + ", "
							+ testData.get("InsuredCity") + ", " + testData.get("InsuredState") + " "
							+ testData.get("InsuredZIP"),
					"View Policy Snapshot Page", "Updated Insured Address is displayed in Policy Snapshot Page", false,
					false);
			System.out.println("Insured Details " + viewPolicySnapShot.viewPolicySnapshotDetails
					.formatDynamicPath(testData.get("InsuredAddr1")).getData());
			System.out.println("Insured Details testdata " + testData.get("InsuredAddr1") + ", "
					+ testData.get("InsuredAddr2") + ", " + testData.get("InsuredCity") + ", "
					+ testData.get("InsuredState") + " " + testData.get("InsuredZIP"));
			System.out.println("-----------------------------------------");
			Assertions.verify(
					viewPolicySnapShot.insuredDetails.getData()
							.contains("(" + testData.get("InsuredPhoneNumAreaCode") + ")" + " "
									+ testData.get("InsuredPhoneNumPrefix") + "-" + testData.get("InsuredPhoneNum")),
					true, "View Policy Snapshot Page",
					"Updated Insured Contact is displayed in Policy Snapshot Page of NB", false, false);
			Assertions.verify(viewPolicySnapShot.insuredDetails.getData().contains(testData.get("InsuredEmail")), true,
					"View Policy Snapshot Page", "Updated Insured Email is displayed in Policy Snapshot Page of NB",
					false, false);
			Assertions.verify(viewPolicySnapShot.inspectionName.getData().contains(testData.get("InspectionContact")),
					true, "View Policy Snapshot Page",
					"Updated Inspection Contact Name is displayed in Policy Snapshot Page of NB", false, false);
			Assertions.verify(
					viewPolicySnapShot.viewPolicySnapshotDetails.formatDynamicPath(testData.get("InsuredPhoneNum"))
							.getData()
							.contains("(" + testData.get("InsuredPhoneNumAreaCode") + ")" + " "
									+ testData.get("InsuredPhoneNumPrefix") + "-" + testData.get("InsuredPhoneNum")),
					true, "View Policy Snapshot Page",
					"Updated Inspection Contact Number is displayed in Policy Snapshot Page of NB", false, false);
			viewPolicySnapShot.scrollToTopPage();
			viewPolicySnapShot.waitTime(2);
			viewPolicySnapShot.goBackButton.click();

			// CLick on View Active renewal link
			policySummaryPage.viewActiveRenewal.scrollToElement();
			policySummaryPage.viewActiveRenewal.click();

			// Asserting transaction on Renewal policy page
			Assertions.verify(
					policySummaryPage.transactionType.formatDynamicPath("NPB-END").checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "NPB Endorsement is displayed in transaction history of Renewal Policy",
					false, false);
			policySummaryPage.transHistReasonNAHO.formatDynamicPath(3).scrollToElement();
			policySummaryPage.transHistReasonNAHO.formatDynamicPath(3).click();

			// Click on view Policy Snapshot
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();

			// Assert details in view policy snapshot page
			Assertions.verify(
					viewPolicySnapShot.viewPolicySnapshotDetails.formatDynamicPath(testData.get("InsuredAddr1"))
							.getData(),
					testData.get("InsuredAddr1") + ", " + testData.get("InsuredAddr2") + ", "
							+ testData.get("InsuredCity") + ", " + testData.get("InsuredState") + " "
							+ testData.get("InsuredZIP"),
					"View Policy Snapshot Page", "Updated Insured Address is displayed in Policy Snapshot Page", false,
					false);
			System.out.println("Insured Details " + viewPolicySnapShot.viewPolicySnapshotDetails
					.formatDynamicPath(testData.get("InsuredAddr1")).getData());
			System.out.println("Insured Details testdata " + testData.get("InsuredAddr1") + ", "
					+ testData.get("InsuredAddr2") + ", " + testData.get("InsuredCity") + ", "
					+ testData.get("InsuredState") + " " + testData.get("InsuredZIP"));
			System.out.println("-----------------------------------------");
			Assertions.verify(
					viewPolicySnapShot.insuredDetails.getData()
							.contains("(" + testData.get("InsuredPhoneNumAreaCode") + ")" + " "
									+ testData.get("InsuredPhoneNumPrefix") + "-" + testData.get("InsuredPhoneNum")),
					true, "View Policy Snapshot Page",
					"Updated Insured Contact is displayed in Policy Snapshot Page of Renewal", false, false);
			Assertions.verify(viewPolicySnapShot.insuredDetails.getData().contains(testData.get("InsuredEmail")), true,
					"View Policy Snapshot Page",
					"Updated Insured Email is displayed in Policy Snapshot Page of Renewal", false, false);
			Assertions.verify(viewPolicySnapShot.inspectionName.getData().contains(testData.get("InspectionContact")),
					true, "View Policy Snapshot Page",
					"Updated Inspection Contact Name is displayed in Policy Snapshot Page of Renewal", false, false);
			Assertions.verify(
					viewPolicySnapShot.viewPolicySnapshotDetails.formatDynamicPath(testData.get("InsuredPhoneNum"))
							.getData()
							.contains("(" + testData.get("InsuredPhoneNumAreaCode") + ")" + " "
									+ testData.get("InsuredPhoneNumPrefix") + "-" + testData.get("InsuredPhoneNum")),
					true, "View Policy Snapshot Page",
					"Updated Inspection Contact Number is displayed in Policy Snapshot Page of Renewal", false, false);
			viewPolicySnapShot.scrollToTopPage();
			viewPolicySnapShot.waitTime(2);
			viewPolicySnapShot.goBackButton.click();

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC072 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC072 ", "Executed Successfully");
			}
		}
	}
}
