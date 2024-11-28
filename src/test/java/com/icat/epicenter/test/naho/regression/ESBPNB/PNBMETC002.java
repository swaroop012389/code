/** Program Description: Validating and asserting the following for Renewal Quote
 * 						1. Warning messagge not displayed for Coverage E, Occupancy type = 'Tenant'
 * 						2. Warning messagge for Coverage E, Occupancy type = 'Primary' and Short Term Rental = 'Yes'
 *						3. View or print full Quote page - Validating SLTF value
 *  Author			   : Priyanka S
 *  Date of Creation   : 24/08/2022
 **/

package com.icat.epicenter.test.naho.regression.ESBPNB;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBMETC002 extends AbstractNAHOTest {

	public PNBMETC002() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBPNB/METC002.xls";
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
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();

		// Initializing the variables
		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
		String quoteNumber;
		String policyNumber;
		String sltfData;
		double actalSltfData;
		Map<String, String> testData = data.get(dataValue1);
		boolean isTestPassed = false;

		try {
			// Creating New Account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

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

			// Asserting Quote Number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Click on Request Bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Clicking on answer no button
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered successfully");

			// Entering bind details
			quoteNumber = requestBindPage.quoteNumber.getData();
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Details Entered successfully");

			// Clicking on homepage button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			accountOverviewPage.openReferralLink.waitTillButtonIsClickable(60);
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();

			// Approve Referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);

			// IO-21792-As part of this our expectation is to validate if the referred quote
			// is assigned to USM not "Holder RMS"
			if (referralPage.assignedUser.getData().contentEquals("Sean Minn")) {

				Assertions.verify(referralPage.assignedUser.getData().contains("Sean Minn"), true, "Referral Page",
						"Quote is referred USM is ' " + referralPage.assignedUser.getData() + " '", false, false);
			} else {

				Assertions.verify(referralPage.assignedUser.getData().contains("Holder RMS"), false, "Referral Page",
						"Quote is referred USM is ' " + referralPage.assignedUser.getData() + " '", false, false);
			}
			// Ended
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind page loaded successfully", false, false);
			requestBindPage.approveRequestNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Asserting policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is : " + policyNumber, false, false);

			// Asserting Status of Policy
			Assertions.verify(policySummaryPage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy status is : " + policySummaryPage.policyStatus.getData(), false, false);

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

				// Clicking on expaac link in home page
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.click();
				Assertions.passTest("Home Page", "Clicked on Expaac Link");

				// Entering expaac data
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

				// Click on continue button in Renewal building review page
				if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
						&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
					policyRenewalPage.continueRenewal.scrollToElement();
					policyRenewalPage.continueRenewal.click();
					Assertions.passTest("Renewal Building Review Page", "Clicked on Continue");
				}
			}

			// Getting quote number 2
			Assertions.verify(accountOverviewPage.viewPreviousPolicyButton.checkIfElementIsDisplayed(), true,
					"Account Overvew Page", "Account Overview Page loaded successfully", false, false);
			// Assert renewal quote number
			Assertions.verify(
					accountOverviewPage.quoteNumber.checkIfElementIsPresent()
							&& accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "Account Overview Page loaded successfully", false, false);
			int quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account overview page", "Renewal Quote number is : " + quoteNumber);

			// Click on edit dwelling button
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();

			// Update Occupied by = Tenant and Cov E = $1,000,000 (Override)
			testData = data.get(dataValue2);
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling  Page",
					"Dwelling Page Loaded successfully", false, false);
			if (dwellingPage.yearBuilt.checkIfElementIsPresent()
					&& dwellingPage.yearBuilt.checkIfElementIsDisplayed()) {
				dwellingPage.yearBuilt.clearData();
				dwellingPage.continueWithUpdateBtn.waitTillVisibilityOfElement(60);
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
				dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));
			}

			if (dwellingPage.OccupiedByArrow.checkIfElementIsPresent() && !testData.get("L1D1-OccupiedBy").equals("")) {
				dwellingPage.OccupiedByArrow.waitTillVisibilityOfElement(60);
				dwellingPage.OccupiedByArrow.scrollToElement();
				dwellingPage.OccupiedByArrow.click();
				dwellingPage.OccupiedByOption.formatDynamicPath(testData.get("L1D1-OccupiedBy")).scrollToElement();
				dwellingPage.OccupiedByOption.formatDynamicPath(testData.get("L1D1-OccupiedBy")).click();
			}

			// Click on get quote button
			if (dwellingPage.createQuote.checkIfElementIsPresent()
					&& dwellingPage.createQuote.checkIfElementIsDisplayed()) {
				dwellingPage.createQuote.waitTillVisibilityOfElement(60);
				dwellingPage.createQuote.scrollToElement();
				dwellingPage.createQuote.click();
			}

			// Click on get a Quote Button
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);

			// Click on get Quote button
			if (createQuotePage.getAQuote.checkIfElementIsPresent()
					&& createQuotePage.getAQuote.checkIfElementIsDisplayed()) {
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();
			}

			// Asserting the warning message for Cov E value
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				Assertions.addInfo("Scenario 01", "Verifying and asserting warning message for Cov E");
				if (createQuotePage.warningMessages.formatDynamicPath("short-term rental property")
						.checkIfElementIsPresent()
						&& createQuotePage.warningMessages.formatDynamicPath("short-term rental property")
								.checkIfElementIsDisplayed()) {
					Assertions.verify(
							createQuotePage.warningMessages
									.formatDynamicPath("short-term rental property").checkIfElementIsDisplayed(),
							true, "Create Quote Page",
							"Warning messsage for roof age is" + createQuotePage.warningMessages
									.formatDynamicPath("short-term rental property").getData() + " displayed",
							false, false);
				} else {
					Assertions.passTest("Create Quote Page", "Warning messsage for roof age is not dispalyed");
				}
				Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
				Assertions.passTest("create A quote page", "Clicked on continue button");
			}

			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Click on view or print full quote link
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);

			// Getting quote number 2
			Assertions.verify(accountOverviewPage.viewPreviousPolicyButton.checkIfElementIsDisplayed(), true,
					"Account Overvew Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "The Renewal Quote Number : " + quoteNumber);

			// click on edit dwelling link
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();

			// Update Year built = 1977 , Occupied by = Tenant and Cov A = $149,999
			testData = data.get(dataValue3);
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling  Page",
					"Dwelling Page Loaded successfully", false, false);
			if (dwellingPage.yearBuilt.checkIfElementIsPresent()
					&& dwellingPage.yearBuilt.checkIfElementIsDisplayed()) {
				dwellingPage.yearBuilt.clearData();
				dwellingPage.continueWithUpdateBtn.waitTillVisibilityOfElement(60);
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
				dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));
			}

			if (dwellingPage.OccupiedByArrow.checkIfElementIsPresent() && !testData.get("L1D1-OccupiedBy").equals("")) {
				dwellingPage.OccupiedByArrow.waitTillVisibilityOfElement(60);
				dwellingPage.OccupiedByArrow.scrollToElement();
				dwellingPage.OccupiedByArrow.click();
				dwellingPage.OccupiedByOption.formatDynamicPath(testData.get("L1D1-OccupiedBy")).scrollToElement();
				dwellingPage.OccupiedByOption.formatDynamicPath(testData.get("L1D1-OccupiedBy")).click();
			}

			if (dwellingPage.shortTermRentalYes.checkIfElementIsPresent()
					&& dwellingPage.shortTermRentalYes.checkIfElementIsDisplayed()) {
				if (!testData.get("L1D1-DwellingShortTermRental").equals("")) {
					if (testData.get("L1D1-DwellingShortTermRental").equals("Yes")) {
						dwellingPage.shortTermRentalYes.scrollToElement();
						dwellingPage.shortTermRentalYes.click();
					}
				}
			}

			// Click on get quote button
			if (dwellingPage.createQuote.checkIfElementIsPresent()
					&& dwellingPage.createQuote.checkIfElementIsDisplayed()) {
				dwellingPage.createQuote.waitTillVisibilityOfElement(60);
				dwellingPage.createQuote.scrollToElement();
				dwellingPage.createQuote.click();
			}

			// Click on get a Quote Button
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			// createQuotePage.enterQuoteDetailsNAHO(testData);
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);

			// Click on get Quote button
			if (createQuotePage.getAQuote.checkIfElementIsPresent()
					&& createQuotePage.getAQuote.checkIfElementIsDisplayed()) {
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();
			}

			// Asserting the warning message for Cov E value
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				Assertions.addInfo("Scenario 02", "Verifying and asserting warning message for Cov E");
				if (createQuotePage.warningMessages.formatDynamicPath("short-term rental property")
						.checkIfElementIsPresent()
						&& createQuotePage.warningMessages.formatDynamicPath("short-term rental property")
								.checkIfElementIsDisplayed()) {
					Assertions.verify(
							createQuotePage.warningMessages
									.formatDynamicPath("short-term rental property").checkIfElementIsDisplayed(),
							true, "Create Quote Page",
							"Warning messsage for Cov E limit is" + createQuotePage.warningMessages
									.formatDynamicPath("short-term rental property").getData() + " displayed",
							false, false);
				}

				else {
					Assertions.passTest("Create Quote Page", "Warning messsage for Cov E limit is not displayed");
				}

				Assertions.addInfo("Scenario 02", "Scenario 02 Ended");
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
				Assertions.passTest("create A quote page", "Clicked on continue button");
			}
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Getting quote number 2
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "The Renewal Quote Number : " + quoteNumber);

			// click on view or pring full quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();

			// Validating SLTF value - 3% of Total Premium (Premium + Policy Fee +
			// Inspection Fee)
			viewOrPrintFullQuotePage.surplusLinesTaxesValue.scrollToElement();
			String premiumValue = viewOrPrintFullQuotePage.premiumValue.getData().replace("$", "").replace(",", "");
			String inspectionValue = viewOrPrintFullQuotePage.inspectionFee.getData().replace("$", "").replace(",", "");
			String policyValue = viewOrPrintFullQuotePage.policyFee.getData().replace("$", "").replace(",", "");

			// Commenting as Reciprocal is not available for Renewal
			String surplusContributionValue = viewOrPrintFullQuotePage.surplusContributionValue.getData()
					.replace("$", "").replace(",", "").replace("%", "");

			double sltfFee = Double.parseDouble(premiumValue) + Double.parseDouble(inspectionValue)
					+ Double.parseDouble(policyValue) + Double.parseDouble(surplusContributionValue);
			// double sltfFee = (double) (Double.parseDouble(premiumValue) +
			// Double.parseDouble(inspectionValue)
			// + Double.parseDouble(policyValue));
			double sltfFeeValue = sltfFee * 3 / 100;

			// Click on Go Back Button
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// Asserting SLTF Value
			sltfData = accountOverviewPage.sltfValue.getData().replace("$", "");
			actalSltfData = Double.parseDouble(sltfData);
			Assertions.addInfo("Scenario 03", "Calculating and verifying SLTF value in View or print full quote page");
			if (Precision.round(Math.abs(Precision.round(actalSltfData, 2) - Precision.round(sltfFeeValue, 2)),
					2) < 0.05) {
				Assertions.passTest("View print full quote page",
						"Calculated surplus lines taxes and fees : " + "$" + Precision.round(sltfFeeValue, 2));
				Assertions.passTest("View print full quote page",
						"Actual surplus lines taxes and fees : " + "$" + actalSltfData);
			} else {
				Assertions.verify(actalSltfData, sltfFeeValue, "Account Overview Page",
						"The Difference between actual SLTF value and calculated SLTF value is more than 0.05", false,
						false);
			}
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Assert renewal quote number
			Assertions.verify(
					accountOverviewPage.createAnotherQuote.checkIfElementIsPresent()
							&& accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account overview page", "Renewal Quote number is : " + quoteNumber);

			// Click on Release renewal to producer button
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release renewal to producer Button");

			// Click on issue Quote button
			if (accountOverviewPage.issueQuoteButton.checkIfElementIsPresent()
					&& accountOverviewPage.issueQuoteButton.checkIfElementIsDisplayed()) {
				accountOverviewPage.issueQuoteButton.scrollToElement();
				accountOverviewPage.issueQuoteButton.click();
				Assertions.passTest("Account Overview Page", "Clicked on Issue Quote Button");
			}

			// Click on Request Bind
			// testData = data.get(dataValue1);
			accountOverviewPage.clickOnRenewalRequestBindNAHO(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Asserting Diligence message
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page Loaded successfully", false, false);
			requestBindPage.renewalRequestBindNAHO(testData);

			// Clicking on homepage button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			accountOverviewPage.openReferralLink.waitTillButtonIsClickable(60);
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();

			// Approve Referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);

			// IO-21792-As part of this our expectation is to validate if the referred quote
			// is assigned to USM not "Holder RMS"
			if (referralPage.assignedUser.getData().contentEquals("Sean Minn")) {

				Assertions.verify(referralPage.assignedUser.getData().contains("Sean Minn"), true, "Referral Page",
						"Quote is referred USM is ' " + referralPage.assignedUser.getData() + " '", false, false);
			} else {

				Assertions.verify(referralPage.assignedUser.getData().contains("Holder RMS"), false, "Referral Page",
						"Quote is referred USM is ' " + referralPage.assignedUser.getData() + " '", false, false);
			}
			// Ended
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind page loaded successfully", false, false);
			requestBindPage.approveRequestNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Asserting the policy Status
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is : " + policyNumber, false, false);
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Status is : " + policySummaryPage.policyStatus.getData(), false, false);

			// Sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBMETC002 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBMETC002 ", "Executed Successfully");
			}
		}
	}
}
