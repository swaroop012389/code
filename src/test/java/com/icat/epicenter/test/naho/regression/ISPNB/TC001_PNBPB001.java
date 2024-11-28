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
import com.icat.epicenter.pom.FindUserDomain;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC001_PNBPB001 extends AbstractNAHOTest {

	public TC001_PNBPB001() {
		super(LoginType.ADMIN);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBPB001.xls";
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
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		EndorseSummaryDetailsPage endorseSummaryDetailsPage = new EndorseSummaryDetailsPage();
		FindUserDomain findUserDomain = new FindUserDomain();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();

		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// Admin Login as Rzimmer
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home  Page",
					"Rzimmer Home page loaded successfully", false, false);
			homePage.userDomainMgnt.scrollToElement();
			homePage.userDomainMgnt.click();
			Assertions.passTest("Home Page", "Clicked on User/Domain Management link successfully");

			// Find User
			Assertions.verify(findUserDomain.findUserName.checkIfElementIsDisplayed(), true, "Find User/Domain Page",
					"Find User/Domain Page Loaded successfully", false, false);
			findUserDomain.findUserName.setData("sminn");

			findUserDomain.findButton.scrollToElement();
			findUserDomain.findButton.click();

			findUserDomain.findUserLink.formatDynamicPath("sminn@icat.com").scrollToElement();
			findUserDomain.findUserLink.formatDynamicPath("sminn@icat.com").click();

			findUserDomain.actionRights.scrollToElement();
			findUserDomain.actionRights.click();

			findUserDomain.nAHORenewals.scrollToElement();

			if (findUserDomain.nAHORenewalsCheckBox.checkIfElementIsSelected()) {
				Assertions.verify(findUserDomain.nAHORenewalsCheckBox.checkIfElementIsSelected(), true, "Home  Page",
						"NAHO Renewals Early Release is Checked ", false, false);
				findUserDomain.cancelButton.scrollToElement();
				findUserDomain.cancelButton.click();
			} else {
				findUserDomain.nAHORenewalsCheckBox.scrollToElement();
				findUserDomain.nAHORenewalsCheckBox.select();
				findUserDomain.saveUserButton.scrollToElement();
				findUserDomain.saveUserButton.click();
			}

			// Logout as rzimmer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home  Page ", "SignOut as Rzimmer successfully");

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page ", "Logged in as USM Successfully");

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

			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			if (accountOverviewPage.openReferralLink.checkIfElementIsPresent()
					&& accountOverviewPage.openReferralLink.checkIfElementIsDisplayed()) {
				accountOverviewPage.openReferralLink.checkIfElementIsDisplayed();
				accountOverviewPage.openReferralLink.scrollToElement();
				accountOverviewPage.openReferralLink.click();

				// Approve Referral
				Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true,
						"Referral Page", "Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");
				Assertions.verify(approveDeclineQuotePage.approveButton.checkIfElementIsDisplayed(), true,
						"Approve Decline Quote Page", "Approve Decline Quote page loaded successfully", false, false);
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Approve Decline Quote Page", "Bind Request approved successfully");
				//getting quote number
				Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true,
						"Policy Summary Page", "Policy Summary Page loaded successfully. PolicyNumber is "
								+ policySummaryPage.policyNumber.getData(),
						false, false);
			}
			else {
				accountOverviewPage.viewPolicyButton.scrollToElement();
				accountOverviewPage.viewPolicyButton.click();

				//getting quote number
				Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true,
						"Policy Summary Page", "Policy Summary Page loaded successfully. PolicyNumber is "
								+ policySummaryPage.policyNumber.getData(),
						false, false);
			}

			// Click on PB endorsement
			policySummaryPage.endorsePB.waitTillVisibilityOfElement(60);
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			policySummaryPage.endorsePB.waitTillInVisibilityOfElement(60);
			Assertions.passTest("Policy Summary Page", "Clicked on endorse pb link");

			// Click on Edit Location or Building Information
			testData = data.get(data_Value2);
			endorsePolicyPage.endorsementEffDate.waitTillPresenceOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillVisibilityOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillElementisEnabled(60);
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			endorsePolicyPage.waitTime(3);
			endorsePolicyPage.editLocOrBldgInformationLink.waitTillPresenceOfElement(60);
			endorsePolicyPage.editLocOrBldgInformationLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.editLocOrBldgInformationLink.waitTillElementisEnabled(60);
			endorsePolicyPage.editLocOrBldgInformationLink.waitTillButtonIsClickable(60);
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();

			// Update dwelling address
			dwellingPage.editDwellingDetails(testData, 1, 1);

			// Review dwelling and continue
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			if (dwellingPage.override.checkIfElementIsPresent()
					&& dwellingPage.override.checkIfElementIsDisplayed()) {
				dwellingPage.override.scrollToElement();
				dwellingPage.override.click();
			}

			// Added the following code as protection call is not available message is
			// displayed
			if (dwellingPage.internalSection.checkIfElementIsPresent()
					&& dwellingPage.internalSection.checkIfElementIsDisplayed()) {
				dwellingPage.internalSection.waitTillVisibilityOfElement(60);
				dwellingPage.internalSection.scrollToElement();
				dwellingPage.internalSection.click();
				dwellingPage.protectionClassOverride.waitTillVisibilityOfElement(60);
				dwellingPage.protectionClassOverride.scrollToElement();
				dwellingPage.protectionClassOverride.setData(testData.get("L1D1-ProtectionClassOverride"));
				dwellingPage.protectionClassOverride.tab();
				dwellingPage.reSubmit.scrollToElement();
				dwellingPage.reSubmit.click();
				if (dwellingPage.override.checkIfElementIsPresent()
						&& dwellingPage.override.checkIfElementIsDisplayed()) {
					dwellingPage.override.scrollToElement();
					dwellingPage.override.click();
				}
			}

			dwellingPage.continueButton.waitTillVisibilityOfElement(60);
			dwellingPage.continueButton.scrollToElement();
			dwellingPage.continueButton.click();

			// Review and continue create quote page
			createQuotePage.scrollToBottomPage();
			createQuotePage.continueEndorsementButton.waitTillVisibilityOfElement(60);
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
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
			endorsePolicyPage.continueButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.continueButton.scrollToElement();
			endorsePolicyPage.continueButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on continue button");
			endorsePolicyPage.scrollToBottomPage();

			// Asserting Dwelling address change in endorsement summary page
			Assertions.verify(
					endorseSummaryDetailsPage.buildingLevelChangesToCol.formatDynamicPath(2).getData()
							.contains(testData.get("L1D1-DwellingAddress") + ", " + testData.get("L1D1-DwellingDesc")
									+ ", " + testData.get("L1D1-DwellingCity") + ", " + testData.get("InsuredState")
									+ " " + testData.get("L1D1-DwellingZIP")),
					true, "Endorse Summary Details Page",
					"Insured Address is updated to "
							+ endorseSummaryDetailsPage.buildingLevelChangesToCol.formatDynamicPath(2).getData(),
					false, false);

			// Assert dwelling address update in endorsement quote page
			endorsePolicyPage.viewEndorsementQuote.waitTillVisibilityOfElement(60);
			endorsePolicyPage.viewEndorsementQuote.scrollToElement();
			endorsePolicyPage.viewEndorsementQuote.click();
			Assertions.verify(
					endorseSummaryDetailsPage.dwellingAddress.formatDynamicPath(testData.get("L1D1-DwellingAddress"))
							.checkIfElementIsDisplayed(),
					true, "Endorse Quote Details Page", "Dwelling address is updated from "
							+ testData.get("L1D1-DwellingAddress") + " to " + testData.get("L1D1-DwellingAddress"),
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.dwellingAddress.formatDynamicPath(testData.get("L1D1-DwellingCity"))
							.checkIfElementIsDisplayed(),
					true, "Endorse Quote Details Page", "Dwelling city is updated from "
							+ testData.get("L1D1-DwellingCity") + " to " + testData.get("L1D1-DwellingCity"),
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.dwellingAddress.formatDynamicPath(testData.get("L1D1-DwellingZIP"))
							.checkIfElementIsDisplayed(),
					true, "Endorse Quote Details Page", "Dwelling Zip is updated from "
							+ testData.get("L1D1-DwellingZIP") + " to " + testData.get("L1D1-DwellingZIP"),
					false, false);
			endorseSummaryDetailsPage.scrollToTopPage();
			endorseSummaryDetailsPage.waitTime(3);
			endorseSummaryDetailsPage.closeBtn.click();

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
			policySummaryPage.endorsePB.waitTillVisibilityOfElement(60);
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			endorsePolicyPage.endorsementEffDate.waitTillPresenceOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillVisibilityOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillElementisEnabled(60);
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			endorsePolicyPage.waitTime(2);

			for (int i = 2; i <= 9; i++) {
				testData = data.get(i);

				if (createQuotePage.override.checkIfElementIsPresent()
						&& createQuotePage.override.checkIfElementIsDisplayed()) {
					createQuotePage.override.scrollToElement();
					createQuotePage.override.click();
				}
				if (createQuotePage.continueButton.checkIfElementIsPresent()
						&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
					createQuotePage.continueButton.scrollToElement();
					createQuotePage.continueButton.click();
				}
				endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
				endorsePolicyPage.editLocOrBldgInformationLink.click();

				// Entering Location 1 Dwelling 1 Details
				dwellingPage.addDwellingDetails(testData, 1, 1);
				dwellingPage.addRoofDetails(testData, 1, 1);
				Assertions.passTest("Dwelling Page",
						"Roof Cladding is updated to " + testData.get("L1D1-DwellingRoofCladding")
								+ " and Year Built is updated to " + testData.get("L1D1-DwellingYearBuilt"));

				// Review dwelling and continue
				dwellingPage.waitTime(2);
				dwellingPage.reviewDwelling.scrollToElement();
				dwellingPage.reviewDwelling.click();
				if (dwellingPage.reSubmit.checkIfElementIsPresent()
						&& dwellingPage.reSubmit.checkIfElementIsDisplayed()) {
					dwellingPage.override.scrollToElement();
					dwellingPage.override.click();
				}
				dwellingPage.continueButton.waitTillVisibilityOfElement(60);
				dwellingPage.continueButton.scrollToElement();
				dwellingPage.continueButton.click();

				// Review and continue create quote page
				createQuotePage.scrollToBottomPage();
				createQuotePage.continueEndorsementButton.waitTillVisibilityOfElement(60);
				createQuotePage.continueEndorsementButton.scrollToElement();
				createQuotePage.continueEndorsementButton.click();

				// Asserting Alert message for USM
				if (createQuotePage.roofAgeAlertMessage.checkIfElementIsPresent()
						&& createQuotePage.roofAgeAlertMessage.checkIfElementIsDisplayed()) {
					Assertions.verify(createQuotePage.roofAgeAlertMessage.checkIfElementIsDisplayed(), true,
							"Create Quote Page",
							createQuotePage.roofAgeAlertMessage.getData() + " is displayed when Roof Cladding is "
									+ testData.get("L1D1-DwellingRoofCladding") + " and Year Built is "
									+ testData.get("L1D1-DwellingYearBuilt"),
							false, false);
					createQuotePage.continueButton.scrollToElement();
					createQuotePage.continueButton.click();
				}
				if (createQuotePage.warningMessages
						.formatDynamicPath("The account is ineligible due to the roof age being outside of")
						.checkIfElementIsPresent()
						&& createQuotePage.warningMessages
								.formatDynamicPath("The account is ineligible due to the roof age being outside of")
								.checkIfElementIsDisplayed()) {
					Assertions.verify(
							createQuotePage.warningMessages
									.formatDynamicPath("The account is ineligible due to the roof age being outside of")
									.checkIfElementIsDisplayed(),
							true, "Create Quote Page",
							createQuotePage.warningMessages
									.formatDynamicPath("The account is ineligible due to the roof age being outside of")
									.getData() + " is displayed when Roof Cladding is "
									+ testData.get("L1D1-DwellingRoofCladding") + " and Year Built is "
									+ testData.get("L1D1-DwellingYearBuilt"),
							false, false);
					createQuotePage.continueButton.scrollToElement();
					createQuotePage.continueButton.click();
				}

			}

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC001 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC001 ", "Executed Successfully");
			}
		}
	}
}
