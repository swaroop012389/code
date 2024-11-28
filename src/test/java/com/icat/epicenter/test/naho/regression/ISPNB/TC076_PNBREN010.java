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
import com.icat.epicenter.pom.EndorseAdditionalInterestsPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.EndorseSummaryDetailsPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC076_PNBREN010 extends AbstractNAHOTest {

	public TC076_PNBREN010() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBREN010.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		LoginPage loginPage = new LoginPage();
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
		PolicyRenewalPage policyrenewalPage = new PolicyRenewalPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();
		EndorseSummaryDetailsPage endorseSummaryDetailsPage = new EndorseSummaryDetailsPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		EndorseAdditionalInterestsPage endorseAdditionalInterestsPage = new EndorseAdditionalInterestsPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		String policyNumber;
		String renewalPolicyNumber;
		int data_Value1 = 0;

		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// Creating New Account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account Created successfully");

			// Entering Zip code
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

			// Clicking on home page button
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
			policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is "
							+ policySummaryPage.policyNumber.getData(),
					false, false);

			// Click on Renewal Link
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

			// Approve referral
			if (accountOverviewPage.openReferral.checkIfElementIsPresent()
					&& accountOverviewPage.openReferral.checkIfElementIsDisplayed()) {
				accountOverviewPage.approveRenewalReferralUSM(renewalQuoteNumber);
			}

			// Click on Release Renewal To Producer
			accountOverviewPage.releaseRenewalToProducerButton.waitTillPresenceOfElement(60);
			accountOverviewPage.releaseRenewalToProducerButton.waitTillPresenceOfElement(60);
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Renewal To Producer link successfully");

			// click on Bind Button
			accountOverviewPage.clickOnRenewalRequestBindNAHO(testData, renewalQuoteNumber);
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
			Assertions.passTest("Request Bind Page", "Bind Details Entered successfully ");

			// Getting Policy Number from policy summary Page
			renewalPolicyNumber = policySummaryPage.policyNumber.getData();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true,
					"Renewal Policy Summary Page",
					"Renewal Policy Summary Page loaded successfully. Renewal PolicyNumber is " + renewalPolicyNumber,
					false, false);

			// Asserting SLTF value Before endorsement on renewal policy page
			String transactionSLTF = policySummaryPage.TaxesAndStateFees.formatDynamicPath(1).getData();
			String annualSLTF = policySummaryPage.TaxesAndStateFees.formatDynamicPath(2).getData();
			String termTotalSLTF = policySummaryPage.TaxesAndStateFees.formatDynamicPath(3).getData();
			Assertions.passTest("Renewal Policy Summary Page",
					"Before Endorsement Transaction SLTF value:" + transactionSLTF);
			Assertions.passTest("Renewal Policy Summary Page", "Before Endorsement Annual SLTF value:" + annualSLTF);
			Assertions.passTest("Renewal Policy Summary Page",
					"Before Endorsement Term Total SLTF value:" + termTotalSLTF);

			// Adding the CR IO-18281
			// SignOut as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as USM successfully");

			// Sign in as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as Producer successfully");

			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Homepage Loaded successfully", false, false);

			// Search the policy
			homePage.searchPolicyByProducer(policyNumber);
			Assertions.passTest("Home Page", "Searched the Policy " + policyNumber + " sucessfully");

			// Click on endorse policy link
			Assertions.addInfo("Policy Summary Page",
					"Initiating the Endorsement to delete the Additional Interest as producer");
			policySummaryPage.producerEndorsePolicyLink.scrollToElement();
			policySummaryPage.producerEndorsePolicyLink.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse Policy link");

			policyRenewalPage.rnlOkButton.scrollToElement();
			policyRenewalPage.rnlOkButton.click();

			// Entering Endorsement effective date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// Click on Additional Interest Information link
			endorsePolicyPage.changeAIInformationLink.scrollToElement();
			endorsePolicyPage.changeAIInformationLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Additional Information link");

			// Deleting the AI Details
			Assertions.verify(endorseAdditionalInterestsPage.okButton.checkIfElementIsDisplayed(), true,
					"Endorse Additional Interests Page", "Endorse Additional Interests Page loaded successfully", false,
					false);

			// Delete Additional Insured AI type
			endorseAdditionalInterestsPage.deleteIcon.formatDynamicPath("1").scrollToElement();
			endorseAdditionalInterestsPage.deleteIcon.formatDynamicPath("1").click();

			// click on yes delete
			endorseAdditionalInterestsPage.yes_DeleteAi.scrollToElement();
			endorseAdditionalInterestsPage.yes_DeleteAi.click();

			// click on Ok button
			endorseAdditionalInterestsPage.okButton.scrollToElement();
			endorseAdditionalInterestsPage.okButton.click();
			Assertions.passTest("Endorse Additional Interests Page",
					"Deleted the Added Additional Interest Successfully");

			// Verify the changes
			String AIDetailsFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(6).getData();
			String AIDetailsTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(7).getData();
			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(5).checkIfElementIsDisplayed(),
					true, "Endorse Summary Page", "The Additional Interest " + AIDetailsFrom + " changed to : "
							+ AIDetailsTo + " displayed is verified",
					false, false);

			// Click on roll forward button
			endorsePolicyPage.completeAndRollForwardBtn.scrollToElement();
			endorsePolicyPage.completeAndRollForwardBtn.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Complete and Rollforward Button");

			if (referQuotePage.contactName.checkIfElementIsPresent()
					&& referQuotePage.contactName.checkIfElementIsDisplayed()) {

				// Enter Referral Contact Details
				referQuotePage.contactName.setData(testData.get("ProducerName"));
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.click();

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
				homePage.searchPolicy(policyNumber);
				Assertions.passTest("Home Page", "Searched Submitted Policy " + policyNumber + " successfullly");

				policySummaryPage.openCurrentReferral.checkIfElementIsDisplayed();
				policySummaryPage.openCurrentReferral.scrollToElement();
				policySummaryPage.openCurrentReferral.click();

				if (referralPage.approveOrDeclineRequest.checkIfElementIsPresent()
						&& referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed()) {
					Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true,
							"Referral Page", "Approve/Decline Request button displayed is verified ", false, false);
					referralPage.approveOrDeclineRequest.scrollToElement();
					referralPage.approveOrDeclineRequest.click();
					referralPage.approveOrDeclineRequest.waitTillInVisibilityOfElement(60);
				}
				if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
						&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
					endorsePolicyPage.continueButton.scrollToElement();
					endorsePolicyPage.continueButton.click();
				}
				// click on approve in Approve Decline Quote page
				if (approveDeclineQuotePage.internalUnderwriterComments.checkIfElementIsPresent()
						&& approveDeclineQuotePage.internalUnderwriterComments.checkIfElementIsDisplayed()) {
					approveDeclineQuotePage.internalUnderwriterComments.scrollToElement();
					approveDeclineQuotePage.internalUnderwriterComments.setData("Test");
				}
				if (approveDeclineQuotePage.externalUnderwriterComments.checkIfElementIsPresent()
						&& approveDeclineQuotePage.externalUnderwriterComments.checkIfElementIsDisplayed()) {
					approveDeclineQuotePage.externalUnderwriterComments.scrollToElement();
					approveDeclineQuotePage.externalUnderwriterComments.setData("Test");
				}
				approveDeclineQuotePage.approveButton.scrollToElement();
				approveDeclineQuotePage.approveButton.click();

				Assertions.passTest("Referral Page", "Referral request approved successfully");

			}

			// SignOut as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Producer successfully");

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");

			// Search the NB policy
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Searched the Policy " + policyNumber + " sucessfully");

			// Click on Endorse PB link on policy summary page
			policySummaryPage.endorsePB.waitTillPresenceOfElement(60);
			policySummaryPage.endorsePB.waitTillVisibilityOfElement(60);
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			waitTime(2);
			Assertions.passTest("Policy Summary Page", "Clicked on EndorsePB link successfully");
			Assertions.verify(policyrenewalPage.rnlWarningMSG.checkIfElementIsDisplayed(), true, "Renewal Created Page",
					"Renewal Warning message is verified:" + policyrenewalPage.rnlWarningMSG.getData(), false, false);
			policyrenewalPage.rnlOkButton.click();
			Assertions.passTest("Renewal Created Page", "Clicked on OK Button and Navigated to Endorse Policy Page");

			// Enter endorsement effective date
			endorsePolicyPage.endorsementEffDate.waitTillPresenceOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillVisibilityOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillElementisEnabled(60);
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page",
					"Entered Endorsement Effective Date successfully,Endorsement Effective Date: "
							+ testData.get("TransactionEffectiveDate"));

			// Click on fee only Endorsement link
			endorsePolicyPage.feeOnlyEndorsement.waitTillPresenceOfElement(60);
			endorsePolicyPage.feeOnlyEndorsement.waitTillVisibilityOfElement(60);
			endorsePolicyPage.feeOnlyEndorsement.scrollToElement();
			endorsePolicyPage.feeOnlyEndorsement.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on FeeOnlyEndorsement link successfully");
			Assertions.verify(overridePremiumAndFeesPage.saveAndClose.checkIfElementIsDisplayed(), true,
					"Override Premium and Fees Page", "Override Premium and Fees Page loaded successfully", false,
					false);

			String previousPolicyFee = overridePremiumAndFeesPage.policyFeePrevious.getData();
			Assertions.passTest("Override Premium and Fees Page", " Previous Policy Fee: " + previousPolicyFee);
			overridePremiumAndFeesPage.transactionPolicyFee.setData(testData.get("PolicyFeeOverride"));
			overridePremiumAndFeesPage.transactionPolicyFee.tab();
			Assertions.passTest("Override Premium and Fees Page",
					" Added Transaction Policy Fee: " + "$" + testData.get("PolicyFeeOverride"));
			Assertions.passTest("Override Premium and Fees Page",
					" New Total Policy fees: " + overridePremiumAndFeesPage.newPolicyFee.getData());
			overridePremiumAndFeesPage.saveAndClose.waitTillPresenceOfElement(60);
			overridePremiumAndFeesPage.saveAndClose.waitTillVisibilityOfElement(60);
			overridePremiumAndFeesPage.scrollToBottomPage();
			overridePremiumAndFeesPage.saveAndClose.click();
			Assertions.passTest("Override Premium And Fees Page", "Clicked on save and close button successfully");

			// Adding Warning Assertion
			Assertions.verify(endorsePolicyPage.rnlWarningmsg.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Endorse Policy Page loaded successfully,Warning Message is verified:"
							+ endorsePolicyPage.rnlWarningmsg.getData(),
					false, false);
			Assertions.verify(endorsePolicyPage.rollForwardBtn.checkIfElementIsPresent(), false, "Endorse Policy Page",
					"Roll forward Option is not availabe", false, false);
			endorsePolicyPage.completeButton.waitTillPresenceOfElement(60);
			endorsePolicyPage.completeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page",
					"The changes are reflected on Endorse Summary page is verified,Policy fee changed from: "
							+ endorseSummaryDetailsPage.policyLevelChangesToCol.formatDynamicPath(1, 4).getData()
							+ " to "
							+ endorseSummaryDetailsPage.policyLevelChangesToCol.formatDynamicPath(1, 5).getData());

			endorsePolicyPage.closeButton.waitTillPresenceOfElement(60);
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on close button successfully");

			// Click on viewActive Renewal link
			Assertions.verify(policySummaryPage.viewActiveRenewal.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "Policy Summary Page loaded", false, false);
			policySummaryPage.viewActiveRenewal.waitTillPresenceOfElement(60);
			policySummaryPage.viewActiveRenewal.waitTillVisibilityOfElement(60);
			policySummaryPage.viewActiveRenewal.scrollToElement();
			policySummaryPage.viewActiveRenewal.click();
			Assertions.passTest("Policy Summary Page",
					"Clicked on View Active Renewal button successfully and Navigated to Renewal policy summary page");

			// Adding Assertions
			Assertions.verify(policySummaryPage.PremiumPolicyFee.formatDynamicPath(1).getData(),
					previousPolicyFee + ".00", "Renewal Policy Summary Page",
					"The Changed value During Endorsement is not reflected on bound renewal,Transaction Policy Fee:"
							+ policySummaryPage.PremiumPolicyFee.formatDynamicPath(1).getData(),
					false, false);
			Assertions.verify(policySummaryPage.PremiumPolicyFee.formatDynamicPath(2).getData(),
					previousPolicyFee + ".00", "Renewal Policy Summary Page",
					"The Changed value During Endorsement is not reflected on bound renewal,Annual Policy Fee:"
							+ policySummaryPage.PremiumPolicyFee.formatDynamicPath(2).getData(),
					false, false);
			Assertions.verify(policySummaryPage.PremiumPolicyFee.formatDynamicPath(3).getData(),
					previousPolicyFee + ".00", "Renewal Policy Summary Page",
					"The Changed value During Endorsement is not reflected on bound renewal,TermTotal Policy Fee:"
							+ policySummaryPage.PremiumPolicyFee.formatDynamicPath(3).getData(),
					false, false);

			Assertions.verify(policySummaryPage.TaxesAndStateFees.formatDynamicPath(1).getData(), transactionSLTF,
					"Renewal Policy Summary Page",
					"The SLTF value on the renewal policy summary page is unaffected,Transaction SLTF:"
							+ policySummaryPage.TaxesAndStateFees.formatDynamicPath(1).getData(),
					false, false);

			Assertions.verify(policySummaryPage.TaxesAndStateFees.formatDynamicPath(1).getData(), transactionSLTF,
					"Renewal Policy Summary Page",
					"The SLTF value on the renewal policy summary page is unaffected,Annual SLTF:"
							+ policySummaryPage.TaxesAndStateFees.formatDynamicPath(2).getData(),
					false, false);

			Assertions.verify(policySummaryPage.TaxesAndStateFees.formatDynamicPath(1).getData(), transactionSLTF,
					"Renewal Policy Summary Page",
					"The SLTF value on the renewal policy summary page is unaffected,TermTotal SLTF:"
							+ policySummaryPage.TaxesAndStateFees.formatDynamicPath(3).getData(),
					false, false);

			// Sign Out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC076 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC076 ", "Executed Successfully");
			}
		}
	}
}
