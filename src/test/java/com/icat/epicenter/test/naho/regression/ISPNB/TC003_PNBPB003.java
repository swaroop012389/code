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
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC003_PNBPB003 extends AbstractNAHOTest {

	public TC003_PNBPB003() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBPB003.xls";
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
		ReferralPage referralPage = new ReferralPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		EndorseSummaryDetailsPage endorseSummaryDetailsPage = new EndorseSummaryDetailsPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();

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
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");
//-------------------------
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


//------------------------
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

			// Click on Edit Location or Building Information
			testData = data.get(data_Value2);
			endorsePolicyPage.endorsementEffDate.waitTillPresenceOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillVisibilityOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillElementisEnabled(60);
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			endorsePolicyPage.waitTime(3);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillPresenceOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillElementisEnabled(60);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillButtonIsClickable(60);
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();

			// Update dwelling address
			createQuotePage.enterDeductiblesNAHO(testData);
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);

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

			// continue with endorsement the endorsement
			endorsePolicyPage.nextButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			endorsePolicyPage.continueButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.continueButton.scrollToElement();
			endorsePolicyPage.continueButton.click();
			endorsePolicyPage.scrollToBottomPage();

			// Assert dwelling deductibles update in Endorsement Summary page
			Assertions.verify(
					endorseSummaryDetailsPage.policyLevelChangesToCol
							.formatDynamicPath(1, 5).getData().contains(testData.get("EquipmentBreakdown")),
					true, "Endorse Summary Details Page",
					"Equipment Breakdown coverage is updated to: "
							+ endorseSummaryDetailsPage.policyLevelChangesToCol.formatDynamicPath(1, 5).getData(),
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.policyLevelChangesToCol
							.formatDynamicPath(2, 5).getData().contains(testData.get("PersonalInjury")),
					true, "Endorse Summary Details Page",
					"Personal Injury coverage is updated to: "
							+ endorseSummaryDetailsPage.policyLevelChangesToCol.formatDynamicPath(2, 5).getData(),
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.policyLevelChangesToCol
							.formatDynamicPath(3, 5).getData().contains(testData.get("ServiceLine")),
					true, "Endorse Summary Details Page",
					"Service Line Interruption coverage is updated to: "
							+ endorseSummaryDetailsPage.policyLevelChangesToCol.formatDynamicPath(3, 5).getData(),
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.buildingLevelChangesToCol
							.formatDynamicPath(1, 5).getData().contains(testData.get("AdditionalDwellingCoverage").subSequence(0, 4)),
					true, "Endorse Summary Details Page",
					"Additional amount of insurance coverage is updated to: "
							+ endorseSummaryDetailsPage.buildingLevelChangesToCol.formatDynamicPath(1, 5).getData(),
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.buildingLevelChangesToCol
							.formatDynamicPath(2, 5).getData().contains(testData.get("AOPDeductibleValue")),
					true, "Endorse Summary Details Page",
					"AOP is updated to: "
							+ endorseSummaryDetailsPage.buildingLevelChangesToCol.formatDynamicPath(3, 5).getData(),
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.buildingLevelChangesToCol
							.formatDynamicPath(3, 5).getData().contains(testData.get("AOWHDeductibleValue")),
					true, "Endorse Summary Details Page",
					"AOWH is updated to: "
							+ endorseSummaryDetailsPage.buildingLevelChangesToCol.formatDynamicPath(3, 5).getData(),
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.buildingLevelChangesToCol
							.formatDynamicPath(4, 5).getData().contains("Included"),
					true, "Endorse Summary Details Page",
					"Identity Fraud is updated to: "
							+ endorseSummaryDetailsPage.buildingLevelChangesToCol.formatDynamicPath(4, 5).getData(),
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.buildingLevelChangesToCol.formatDynamicPath(5, 5).getData()
							.contains(testData.get("IncreasedLimitsOnBusinessProperty")),
					true, "Endorse Summary Details Page",
					"Increased limits on Business is updated to: "
							+ endorseSummaryDetailsPage.buildingLevelChangesToCol.formatDynamicPath(5, 5).getData(),
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.buildingLevelChangesToCol
							.formatDynamicPath(6, 5).getData().contains("Included"),
					true, "Endorse Summary Details Page",
					"Increased special limits is updated to: "
							+ endorseSummaryDetailsPage.buildingLevelChangesToCol.formatDynamicPath(6, 5).getData(),
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.buildingLevelChangesToCol
							.formatDynamicPath(7, 5).getData().contains(testData.get("LimitedPool")),
					true, "Endorse Summary Details Page",
					"Limited swimming pool coverage is updated to: "
							+ endorseSummaryDetailsPage.buildingLevelChangesToCol.formatDynamicPath(7, 5).getData(),
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.buildingLevelChangesToCol
							.formatDynamicPath(8, 5).getData().contains(testData.get("LimitedWaterSump")),
					true, "Endorse Summary Details Page",
					"Limited water backup coverage is updated to: "
							+ endorseSummaryDetailsPage.buildingLevelChangesToCol.formatDynamicPath(8, 5).getData(),
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.buildingLevelChangesToCol
							.formatDynamicPath(9, 5).getData().contains(testData.get("LossAssessment")),
					true, "Endorse Summary Details Page",
					"Loss assessment coverage is updated to: "
							+ endorseSummaryDetailsPage.buildingLevelChangesToCol.formatDynamicPath(9, 5).getData(),
					false, false);
			waitTime(2);
			Assertions.verify(
					endorseSummaryDetailsPage.buildingLevelChangesToCol
							.formatDynamicPath(11, 5).getData().contains(testData.get("MoldProperty")),
					true, "Endorse Summary Details Page",
					"Mold CleanUp coverage is updated to: "
							+ endorseSummaryDetailsPage.buildingLevelChangesToCol.formatDynamicPath(11, 5).getData(),
					false, false);

			waitTime(2);
			Assertions.verify(endorseSummaryDetailsPage.buildingLevelChangesToCol.formatDynamicPath(12, 5).getData().contains(testData.get("NamedStormValue")),
					true, "Endorse Summary Details Page",
					"Named Storm coverage is updated to: "
							+ endorseSummaryDetailsPage.buildingLevelChangesToCol.formatDynamicPath(12, 5).getData(),
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.buildingLevelChangesToCol
							.formatDynamicPath(13, 5).getData().contains(testData.get("OrdinanceOrLaw").subSequence(0, 3)),
					true, "Endorse Summary Details Page",
					"Ordinance or Law coverage is updated to: "
							+ endorseSummaryDetailsPage.buildingLevelChangesToCol.formatDynamicPath(13, 5).getData(),
					false, false);

			// Assert dwelling deductibles update in Endorsement quote page
			endorsePolicyPage.viewEndorsementQuote.waitTillVisibilityOfElement(60);
			endorsePolicyPage.viewEndorsementQuote.scrollToElement();
			endorsePolicyPage.viewEndorsementQuote.click();
			waitTime(2);
			endorseSummaryDetailsPage.endorsementsValues.formatDynamicPath("Named Storm", "1").scrollToElement();
			Assertions.verify(
					endorseSummaryDetailsPage.endorsementsValues.formatDynamicPath(" Named Storm ", "1").getData()
							.contains(testData.get("NamedStormValue")),
					true, "Endorse Quote Details Page",
					"Named Storm value is updated to " + endorseSummaryDetailsPage.endorsementsValues.formatDynamicPath(" Named Storm ", "1").getData(),
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.endorsementsValues.formatDynamicPath("All Other Perils", "1").getData()
							.contains(testData.get("AOPDeductibleValue")),
					true, "Endorse Quote Details Page",
					"All Other Perils value is updated to " + endorseSummaryDetailsPage.endorsementsValues
							.formatDynamicPath("All Other Perils", "1").getData(),
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.endorsementsValues.formatDynamicPath("Wind and Hail", "1").getData()
							.contains(testData.get("AOWHDeductibleValue")),
					true, "Endorse Quote Details Page",
					"Wind and Hail value is updated to " + endorseSummaryDetailsPage.endorsementsValues
							.formatDynamicPath("Wind and Hail", "1").getData(),
					false, false);

			Assertions.verify(
					endorseSummaryDetailsPage.endorsementsValues.formatDynamicPath("Additional AOI for Dwelling", "1")
							.getData().contains(testData.get("AdditionalDwellingCoverage").subSequence(0, 4)),
					true, "Endorse Quote Details Page",
					"Additional Dwelling coverage value updated to " + endorseSummaryDetailsPage.endorsementsValues
							.formatDynamicPath("Additional AOI for Dwelling", "1").getData(),
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.endorsementsValues.formatDynamicPath("Increased Limits", "1").getData()
							.contains(testData.get("IncreasedLimitsOnBusinessProperty")),
					true, "Endorse Quote Details Page",
					"Increased Limits On Business Property value updated to "
							+ endorseSummaryDetailsPage.endorsementsValues.formatDynamicPath("Increased Limits", "1")
									.getData(),
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.endorsementsValues.formatDynamicPath("Increased Ordinance or Law", "1")
							.getData().contains(testData.get("OrdinanceOrLaw").subSequence(0, 3)),
					true, "Endorse Quote Details Page",
					"Ordinance or Law value updated to " + endorseSummaryDetailsPage.endorsementsValues
							.formatDynamicPath("Increased Ordinance or Law", "1").getData(),
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.endorsementsValues.formatDynamicPath("Loss Assessment", "1").getData()
							.contains(testData.get("LossAssessment")),
					true, "Endorse Quote Details Page",
					"Loss Assessment value updated to " + endorseSummaryDetailsPage.endorsementsValues
							.formatDynamicPath("Loss Assessment", "1").getData(),
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.endorsementsValues.formatDynamicPath("Mold Property", "1").getData()
							.contains(testData.get("MoldProperty")),
					true, "Endorse Quote Details Page",
					"Mold value updated to " + endorseSummaryDetailsPage.endorsementsValues
							.formatDynamicPath("Mold Property", "1").getData(),
					false, false);
			// Personal Injury is not shown as included in endorsement document even
			// if it is added in create quote page
			Assertions.verify(
					endorseSummaryDetailsPage.endorsementsValues.formatDynamicPath("Personal Injury", "1").getData()
							.contains(testData.get("PersonalInjury")),
					true, "Endorse Quote Details Page",
					"Personal Injury value updated to " + endorseSummaryDetailsPage.endorsementsValues
							.formatDynamicPath("Personal Injury", "1").getData(),
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.endorsementsValues.formatDynamicPath("Pool and Patio Enclosure", "1")
							.getData().contains(testData.get("LimitedPool")),
					true, "Endorse Quote Details Page",
					"Pool Enclosure Buy-Up value updated to " + endorseSummaryDetailsPage.endorsementsValues
							.formatDynamicPath("Pool and Patio Enclosure", "1").getData(),
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.endorsementsValues.formatDynamicPath("Equipment Breakdown", "1").getData()
							.contains(testData.get("EquipmentBreakdown")),
					true, "Endorse Quote Details Page",
					"Equipment Breakdown value updated to " + endorseSummaryDetailsPage.endorsementsValues
							.formatDynamicPath("Equipment Breakdown", "1").getData(),
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.endorsementsValues.formatDynamicPath("Service Line Interruption", "1")
							.getData().contains(testData.get("ServiceLine")),
					true, "Endorse Quote Details Page",
					"Service Line Interruption value updated to " + endorseSummaryDetailsPage.endorsementsValues
							.formatDynamicPath("Service Line Interruption", "1").getData(),
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.endorsementsValues.formatDynamicPath("Identity Fraud Expense", "1")
							.getData().contains("Included"),
					true, "Endorse Quote Details Page",
					"Identity Fraud Expense value updated to " + endorseSummaryDetailsPage.endorsementsValues
							.formatDynamicPath("Identity Fraud Expense", "1").getData(),
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.endorsementsValues.formatDynamicPath("Water Back-Up", "1").getData()
							.contains(testData.get("LimitedWaterSump")),
					true, "Endorse Quote Details Page",
					"Water Back-Up value updated to " + endorseSummaryDetailsPage.endorsementsValues
							.formatDynamicPath("Water Back-Up", "1").getData(),
					false, false);
			endorseSummaryDetailsPage.scrollToTopPage();
			endorseSummaryDetailsPage.waitTime(3);
			endorseSummaryDetailsPage.closeBtn.click();

			// Complete the endorsement
			endorsePolicyPage.completeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
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
				testData = data.get(data_Value1);
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
			Assertions.failTest("PNBTC003 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC003 ", "Executed Successfully");
			}
		}
	}
}
