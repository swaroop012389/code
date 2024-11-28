/* Name: John
Description:Verifying the absence of HO5 form when occupancy = 'Tenant' and added IO-21053
Date: 02/04/2020   */

package com.icat.epicenter.test.naho.regression.ISNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC003_NBQ003 extends AbstractNAHOTest {

	public TC003_NBQ003() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBQ003.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ViewPolicySnapShot viewPolicySnapShotPage = new ViewPolicySnapShot();

		String quoteNumber;
		int quoteLen;
		int dataValue1 = 0;
		int dataValue2 = 1;
		Map<String, String> testData = data.get(dataValue1);
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// creating New account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
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

			// Verifying HO5 form is not present for occupancy = 'Tenant'
			Assertions.addInfo("Scenario 01", "Verifying HO5 form is not present for occupancy = 'Tenant'");
			Assertions.verify(createQuotePage.formType_HO5.checkIfElementIsPresent(), false, "Create Quote Page",
					"HO5 form is not present for occupancy 'Tenant' is verified", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			createQuotePage.previous.scrollToElement();
			createQuotePage.previous.click();

			accountOverviewPage.editBuilding.waitTillVisibilityOfElement(60);
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();

			testData = data.get(dataValue2);
			dwellingPage.addDwellingDetails(testData, 1, 1);
			dwellingPage.enterDwellingValuesNAHO(testData, 1, 1);
			dwellingPage.reviewDwelling();

			boolean addressFound = false;

			if (dwellingPage.addressMsg.checkIfElementIsPresent() && dwellingPage.addressMsg.checkIfElementIsDisplayed()
					&& !addressFound) {
				for (int i = 1; i <= 10; i++) {
					if (dwellingPage.editDwellingSymbol.checkIfElementIsPresent()) {
						dwellingPage.editDwellingSymbol.scrollToElement();
						dwellingPage.editDwellingSymbol.click();
					}
					dwellingPage.manualEntry.click();
					dwellingPage.manualEntryAddress.waitTillVisibilityOfElement(60);
					dwellingPage.manualEntryAddress.setData(dwellingPage.manualEntryAddress.getData().replace(
							dwellingPage.manualEntryAddress.getData().replaceAll("[^0-9]", ""),
							(Integer.parseInt(dwellingPage.manualEntryAddress.getData().replaceAll("[^0-9]", ""))) + 1
									+ ""));
					dwellingPage.manualEntryAddress.tab();
					dwellingPage.dwellingValuesLink.click();
					dwellingPage.scrollToBottomPage();
					dwellingPage.reviewDwelling();

					if (!dwellingPage.addressMsg.checkIfElementIsPresent()) {
						addressFound = true;
						break;
					}
				}
			}
			dwellingPage.createQuote.waitTillVisibilityOfElement(60);
			dwellingPage.createQuote.waitTillButtonIsClickable(60);
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();

			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			Assertions.passTest("Create Quote Page",
					"Dwelling Occupancy changed from Tenant to Primary</br>Coverage A value changed from $500,000 to $850,000 successfully</br>Entered Cov C : $"
							+ testData.get("L1D1-DwellingCovC"));

			createQuotePage.refreshPage();
			createQuotePage.formType_HO5.waitTillVisibilityOfElement(60);
			Assertions.verify(createQuotePage.coverageCPersonalProperty.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "HO5 form is displayed for Cov C value : $"
							+ createQuotePage.coverageCPersonalProperty.getData() + " and Occupancy 'Primary'",
					false, false);
			Assertions.verify(createQuotePage.formType_HO5.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"HO5 form is present is verified", false, false);

			createQuotePage.formType_HO5.scrollToElement();
			createQuotePage.formType_HO5.click();

			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			testData = data.get(dataValue1);

			if (referQuotePage.contactName.checkIfElementIsPresent()
					&& referQuotePage.contactName.checkIfElementIsDisplayed()) {

				// Enter Referral Contact Details
				referQuotePage.contactName.setData(testData.get("ProducerName"));
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.click();
				Assertions.passTest("Refer Quote Page", "Quote referred successfully");
				quoteNumber = referQuotePage.quoteNumberforReferral.getData();
				Assertions.passTest("Refer Quote Page", "The Quote Number is " + quoteNumber);

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
				homePage.searchQuote(quoteNumber);
				Assertions.passTest("Home Page", "Searched Submitted Quote " + quoteNumber + " successfullly");

				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();

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

				// Search for quote
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuoteByProducer(quoteNumber);

			}

			// Clicking on Bind button
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);
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

			// SignOut as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Producer successfully");

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");

			// searching the quote number in grid and clicking on the quote number link
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Searched Submitted Quote " + quoteNumber + " successfullly");

			accountOverviewPage.uploadPreBindApproveAsUSM();
			referralPage.clickOnApprove();
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			// Verifying policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);
			String policyFormat = policySummaryPage.policyNumber.getData().substring(14, 15);
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyFormat), true,
					"Policy Summary Page",
					"Policy Number format for policy no : " + policySummaryPage.policyNumber.getData() + " is verified",
					false, false);

			// Adding IO-21053
			// Click on view policy snapshot link
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy summary page", "Clicked on view policy snapshot link successfully");

			// Verifying the wordings Surplus line insurer = Victor Insurance Exchange
			Assertions.addInfo("Scenario 02",
					"Verifying the wordings Surplus line insurer = Victor Insurance Exchange");
			Assertions.verify(
					viewPolicySnapShotPage.wordingSurplusLineInsurer.getData().contains("Victor Insurance Exchange"),
					true, "View policy snapshot page", "Verifying the wordings surplus line insurer = "
							+ viewPolicySnapShotPage.wordingSurplusLineInsurer.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");
			// Adding IO-21053 Ended

			// Sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC003 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC003 ", "Executed Successfully");
			}
		}
	}
}