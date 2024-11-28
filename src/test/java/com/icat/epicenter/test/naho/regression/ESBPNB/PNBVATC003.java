/*Program Description: Asserting Following:
 * 1. SLTF Value and Maintenance assessment fund on view print full quote page for renewal quote
 * 2. Verifying presence of  waring message NB and Renewal quote when occupancy = tenant and short term rental = yes
 * 3. Verifying absence of  waring message NB and Renewal quote when occupancy = primary and short term rental = NO
 * 4. Due diligence text words on renewal request bind page
 * 5. Creating renewal policy without referral
 * Author            : Pavan Mule
 * Date of Creation  : 25-05-2022
 */

package com.icat.epicenter.test.naho.regression.ESBPNB;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBVATC003 extends AbstractNAHOTest {

	public PNBVATC003() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBPNB/VATC003.xls";
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
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();

		// Initializing the variables
		int data_Value1 = 0;
		int data_Value2 = 1;
		String quoteNumber;
		int quoteLen;
		String policyNumber;
		String actualPremium;
		String actualPolicyFees;
		String actualSLTF;
		double d_actualPremium;
		double d_actualPolicyFees;
		double d_actualSLTF;
		String SLTFPercentage;
		double d_SLTFPercentage;
		double calculatedSLTF;
		String actualInspectionFee;
		double d_actualInspectionFee;
		String actualMaintenanceAssesmentFund;
		double d_actualMaintenanceAssesmentFund;
		String MaintenanceAssesmentPercentage;
		double d_MaintenanceAssesmentPercentage;
		double calculatedMaintenanceAssesmentFund;
		String actualSurplusContributionValue;
		double d_actualSurplusContributionValue;
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
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Asserting warning message when occupancy = Tenant and Cov E = 1000000 "This
			// is a tenant occupied or short-term rental property with Coverage E limit
			// greater than $500,000 and requires further review by an ICAT Online
			// Underwriter."
			Assertions.addInfo("Scenario 01", "Verifying presence of warning message");
			if (createQuotePage.warningMessages.formatDynamicPath("tenant occupied").checkIfElementIsPresent()
					&& createQuotePage.warningMessages.formatDynamicPath("tenant occupied")
							.checkIfElementIsDisplayed()) {
				Assertions.verify(
						createQuotePage.warningMessages.formatDynamicPath("tenant occupied").checkIfElementIsPresent()
								&& createQuotePage.warningMessages.formatDynamicPath("tenant occupied")
										.checkIfElementIsDisplayed(),
						createQuotePage.warningMessages.formatDynamicPath("tenant occupied").getData().contains(
								"This is a tenant occupied or short-term rental property with Coverage E limit greater than $500,000 and requires further review by an ICAT Online Underwriter."),
						"Create Quote Page",
						"The warning message is "
								+ createQuotePage.warningMessages.formatDynamicPath("tenant occupied").getData()
								+ " displayed",
						false, false);
			} else {
				Assertions.passTest("Create Quote Page", "The Warning Message is not displayed");
			}
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Click on continue button
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Getting the Quote Number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Click on edit dwelling link
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();

			// updating occupancy = primary and short term rental = Yes
			Assertions.passTest("Dwelling Page", "Original occupancy is " + testData.get("L1D1-OccupiedBy"));
			testData = data.get(data_Value2);
			dwellingPage.OccupiedByArrow.scrollToElement();
			dwellingPage.OccupiedByArrow.click();
			dwellingPage.OccupiedByOption.formatDynamicPath(testData.get("L1D1-OccupiedBy")).scrollToElement();
			dwellingPage.OccupiedByOption.formatDynamicPath(testData.get("L1D1-OccupiedBy")).click();
			dwellingPage.continueWithUpdateBtn.waitTillPresenceOfElement(60);
			dwellingPage.continueWithUpdateBtn.scrollToElement();
			dwellingPage.continueWithUpdateBtn.click();
			Assertions.passTest("Dwelling Page", "Latest occupancy is " + testData.get("L1D1-OccupiedBy"));
			dwellingPage.shortTermRentalYes.scrollToElement();
			dwellingPage.shortTermRentalYes.click();

			// Click on create quote button
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			if (dwellingPage.override.checkIfElementIsPresent() && dwellingPage.override.checkIfElementIsDisplayed()) {
				dwellingPage.override.scrollToElement();
				dwellingPage.override.click();
			}

			// Updating Cov E = $1000000
			testData = data.get(data_Value1);
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// Verifying absence of warning message when occupancy = Primary, short term
			// rental = yes and cov E = 1000000
			Assertions.addInfo("Scenario 02", "Verifying absence of warning message");
			Assertions.verify(
					createQuotePage.warningMessages.formatDynamicPath("tenant occupied").checkIfElementIsPresent(),
					false, "Create Quote Page", "The warning message is not displayed", false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Click on continue button
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Getting the another Quote Number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// click on request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Clicking on answer no button
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting questions page", "Underwriting questions details entered successfully");

			// Entering bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request bind page",
					"Request bind page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request bind page", "Bind details entered successfully");

			// Asserting policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy summary page",
					"Policy summary page loaded successfully. PolicyNumber is : " + policyNumber, false, false);

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

			// Assert renewal quote number
			Assertions.verify(
					accountOverviewPage.quoteNumber.checkIfElementIsPresent()
							&& accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account overview page", "Renewal Quote number is : " + quoteNumber);
			if (accountOverviewPage.openReferral.checkIfElementIsPresent()
					&& accountOverviewPage.openReferral.checkIfElementIsDisplayed()) {

				// Opening the Referral from Account Overview Page
				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();
				Assertions.passTest("Account Overview Page", "Referral is Openned");

				// Approve the referral
				referralPage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");
				Assertions.verify(approveDeclineQuotePage.approveButton.checkIfElementIsDisplayed(), true,
						"Approve Decline Quote Page", "Approve Decline Quote page loaded successfully", false, false);
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Approve Decline Quote Page", "Bind Request approved successfully");

				// Click on close
				if (referralPage.close.checkIfElementIsPresent() && referralPage.close.checkIfElementIsDisplayed()) {
					referralPage.close.scrollToElement();
					referralPage.close.click();
				}

				// Search for quote number
				homePage.searchQuote(quoteNumber);
			}

			// Assert renewal quote number
			Assertions.verify(
					accountOverviewPage.quoteNumber.checkIfElementIsPresent()
							&& accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account overview page", "Renewal Quote number is : " + quoteNumber);

			// Click on edit dwelling link
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();

			// updating occupancy = tenant and short term rental = No
			testData = data.get(data_Value2);
			Assertions.passTest("Dwelling Page", "Original occupancy is " + testData.get("L1D1-OccupiedBy"));
			testData = data.get(data_Value1);
			dwellingPage.OccupiedByArrow.scrollToElement();
			dwellingPage.OccupiedByArrow.click();
			dwellingPage.OccupiedByOption.formatDynamicPath(testData.get("L1D1-OccupiedBy")).scrollToElement();
			dwellingPage.OccupiedByOption.formatDynamicPath(testData.get("L1D1-OccupiedBy")).click();
			dwellingPage.continueWithUpdateBtn.waitTillPresenceOfElement(60);
			dwellingPage.continueWithUpdateBtn.scrollToElement();
			dwellingPage.continueWithUpdateBtn.click();
			Assertions.passTest("Dwelling Page", "Latest occupancy is " + testData.get("L1D1-OccupiedBy"));
			dwellingPage.shortTermRentalNo.scrollToElement();
			dwellingPage.shortTermRentalNo.click();

			// Click on create quote button
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			if (dwellingPage.override.checkIfElementIsPresent() && dwellingPage.override.checkIfElementIsDisplayed()) {
				dwellingPage.override.scrollToElement();
				dwellingPage.override.click();
			}

			// Updating Cov E = $1000000
			testData = data.get(data_Value1);
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// Asserting warning message when occupancy = Tenant and Cov E = 1000000 and
			// short term rental = No "This
			// is a tenant occupied or short-term rental property with Coverage E limit
			// greater than $500,000 and requires further review by an ICAT Online
			// Underwriter."
			Assertions.addInfo("Scenario 03", "Verifying presence of warning message");
			if (createQuotePage.warningMessages.formatDynamicPath("tenant occupied").checkIfElementIsPresent()
					&& createQuotePage.warningMessages.formatDynamicPath("tenant occupied")
							.checkIfElementIsDisplayed()) {
				Assertions.verify(
						createQuotePage.warningMessages.formatDynamicPath("tenant occupied").checkIfElementIsPresent()
								&& createQuotePage.warningMessages.formatDynamicPath("tenant occupied")
										.checkIfElementIsDisplayed(),
						createQuotePage.warningMessages.formatDynamicPath("tenant occupied").getData().contains(
								"This is a tenant occupied or short-term rental property with Coverage E limit greater than $500,000 and requires further review by an ICAT Online Underwriter."),
						"Create Quote Page",
						"The warning message is "
								+ createQuotePage.warningMessages.formatDynamicPath("tenant occupied").getData()
								+ " displayed",
						false, false);
			} else {
				Assertions.passTest("Create Quote Page", "The warning message is not displayed");
			}
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on continue button
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Getting the renewal quote Number
			Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Renewal Quote Number is : " + quoteNumber);

			// Click on edit dwelling link
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();

			// updating occupancy = primary and short term rental = No
			testData = data.get(data_Value1);
			Assertions.passTest("Dwelling Page", "Original occupancy is " + testData.get("L1D1-OccupiedBy"));
			testData = data.get(data_Value2);
			dwellingPage.OccupiedByArrow.scrollToElement();
			dwellingPage.OccupiedByArrow.click();
			dwellingPage.OccupiedByOption.formatDynamicPath(testData.get("L1D1-OccupiedBy")).scrollToElement();
			dwellingPage.OccupiedByOption.formatDynamicPath(testData.get("L1D1-OccupiedBy")).click();
			dwellingPage.continueWithUpdateBtn.waitTillPresenceOfElement(60);
			dwellingPage.continueWithUpdateBtn.scrollToElement();
			dwellingPage.continueWithUpdateBtn.click();
			Assertions.passTest("Dwelling Page", "Latest occupancy is " + testData.get("L1D1-OccupiedBy"));

			// Click on create quote button
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			if (dwellingPage.override.checkIfElementIsPresent() && dwellingPage.override.checkIfElementIsDisplayed()) {
				dwellingPage.override.scrollToElement();
				dwellingPage.override.click();
			}

			// Updating Cov E = $1000000
			testData = data.get(data_Value1);
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// Verifying absence of warning message when occupancy = Primary, short term
			// rental = No and cov E = 1000000
			Assertions.addInfo("Scenario 04", "Verifying absence of warning message");
			Assertions.verify(
					createQuotePage.warningMessages.formatDynamicPath("tenant occupied").checkIfElementIsPresent(),
					false, "Create Quote Page", "The warning message is not displayed", false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Click on continue button
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Getting the renewal quote Number
			Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Renewal Quote Number is : " + quoteNumber);

			// Click on view/print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View Print Full Quote Page", "View Print Full Quote Page loaded successfully", false, false);

			// Getting actual SLTF Value,Premium and Policy Fees
			actualPremium = viewOrPrintFullQuotePage.premiumValue.getData().replace("$", "").replace(",", "");
			actualPolicyFees = viewOrPrintFullQuotePage.policyFeeNaho.getData().replace("$", "").replace(",", "");
			actualInspectionFee = viewOrPrintFullQuotePage.inspectionFeeNaho.getData().replace("$", "").replace(",", "");

			// commenting as Reciprocal is not available for Renewal
			actualSurplusContributionValue = viewOrPrintFullQuotePage.surplusContributionValue.getData()
					.replace("$", "").replace("%", "");

			actualSLTF = viewOrPrintFullQuotePage.surplusLinesTaxNaho.getData().replace("$", "").replace(",", "")
					.replace("%", "");
			actualMaintenanceAssesmentFund = viewOrPrintFullQuotePage.maintenanceFund.getData().replace("$", "")
					.replace(",", "");
			SLTFPercentage = testData.get("SLTFPercentage");
			MaintenanceAssesmentPercentage = testData.get("MaintenanceAssesmentFund");
			d_actualPremium = Double.parseDouble(actualPremium);
			d_actualPolicyFees = Double.parseDouble(actualPolicyFees);
			d_actualInspectionFee = Double.parseDouble(actualInspectionFee);

			// commenting as Reciprocal is not available for Renewal
			d_actualSurplusContributionValue = Double.parseDouble(actualSurplusContributionValue);

			d_actualSLTF = Double.parseDouble(actualSLTF);
			d_actualMaintenanceAssesmentFund = Double.parseDouble(actualMaintenanceAssesmentFund);
			d_SLTFPercentage = Double.parseDouble(SLTFPercentage);
			d_MaintenanceAssesmentPercentage = Double.parseDouble(MaintenanceAssesmentPercentage);
			calculatedSLTF = (d_actualPremium + d_actualPolicyFees + d_actualInspectionFee
					+ d_actualSurplusContributionValue) * d_SLTFPercentage;
			// calculatedSLTF = (d_actualPremium + d_actualPolicyFees +
			// d_actualInspectionFee) * d_SLTFPercentage;

			calculatedMaintenanceAssesmentFund = (d_actualPremium + d_actualPolicyFees + d_actualInspectionFee
					+ d_actualSurplusContributionValue) * d_MaintenanceAssesmentPercentage;
			// calculatedMaintenanceAssesmentFund = (d_actualPremium + d_actualPolicyFees +
			// d_actualInspectionFee) * d_MaintenanceAssesmentPercentage;
			Assertions.passTest("View/Print Full Quote Page", "Actual Premium Value " + d_actualPremium);
			Assertions.passTest("View/Print Full Quote Page", "Actual Policy Fees " + d_actualPolicyFees);
			Assertions.passTest("View/Print Full Quote Page", "Actual Inspection Fees " + d_actualInspectionFee);
			Assertions.passTest("View/Print Full Quote Page",
					"Actual Surplus Contribution Value " + d_actualSurplusContributionValue);

			// Verifying Actual SLTF and Calculated SLTF Values on View/Print full quote
			Assertions.addInfo("Scenario 05",
					"Verifying actual SLTF and calculated SLTF values View/Print full quote page");
			if (Precision.round(Math.abs(Precision.round(d_actualSLTF, 2) - Precision.round(calculatedSLTF, 2)),
					2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page",
						"Calculated surplus lines taxes and fees : " + "$" + Precision.round(calculatedSLTF, 2));
				Assertions.passTest("View/Print Full Quote Page",
						"Actual surplus lines taxes and fees : " + "$" + d_actualSLTF);
			} else {
				Assertions.verify(d_actualSLTF, calculatedSLTF, "View/Print Full Quote Page",
						"The Difference between actual SLTF value Fund and calculated SLTF value is more than 0.05",
						false, false);
			}
			Assertions.passTest("Scenario 05", "Scenario 05 Ended");

			// Verifying Actual Maintenance Assessment fund and Calculated Maintenance
			// Assessment fund Values on View/Print full quote
			Assertions.addInfo("Scenario 06",
					"Verifying Actual Maintenance Assessment fund and Calculated Maintenance Assessment fund values View/Print full quote page");
			if (Precision.round(Math.abs(Precision.round(d_actualMaintenanceAssesmentFund, 2)
					- Precision.round(calculatedMaintenanceAssesmentFund, 2)), 2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page", "Calculated Maintenance Assessment fund : " + "$"
						+ Precision.round(calculatedMaintenanceAssesmentFund, 2));
				Assertions.passTest("View/Print Full Quote Page",
						"Actual Maintenance Assessment fund : " + "$" + d_actualMaintenanceAssesmentFund);
			} else {
				Assertions.verify(d_actualMaintenanceAssesmentFund, calculatedSLTF, "View/Print Full Quote Page",
						"The Difference between actual Maintenance Assessment fund value and calculated Maintenance Assessment fund value is more than 0.05",
						false, false);
			}
			Assertions.passTest("Scenario 06", "Scenario 06 Ended");
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// Click on release renewal to producer link
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			accountOverviewPage.issueQuoteButton.scrollToElement();
			accountOverviewPage.issueQuoteButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on issue quote button successfully");

			// click on request bind
			accountOverviewPage.clickOnRenewalRequestBindNAHO(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Verifying presence of A minimum 25% down payment (if not mortgagee billed)
			// Completed and signed application
			// Completed and signed diligent effort form is displayed correctly.
			Assertions.addInfo("Scenario 07",
					"Verifying presence of A minimum 25% down payment (if not mortgagee billed),Completed and signed application,Completed and signed diligent effort form");
			Assertions.verify(
					requestBindPage.diligenceText.checkIfElementIsPresent()
							&& requestBindPage.diligenceText.checkIfElementIsDisplayed(),
					true, "Request Bind Page",
					"Diligence Text Message is " + requestBindPage.diligenceText.getData() + " Displayed", false,
					false);
			Assertions.addInfo("Scenario 07 ", "Scenario 07 Ended");

			// Entering bind details with entity = corporation and adding 3 additional
			// interest = losspayee
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request bind page",
					"Request bind page loaded successfully", false, false);
			testData = data.get(data_Value2);
			requestBindPage.entityArrow.waitTillPresenceOfElement(60);
			requestBindPage.entityArrow.scrollToElement();
			requestBindPage.entityArrow.click();
			requestBindPage.entityOption.formatDynamicPath(testData.get("Entity")).scrollToElement();
			requestBindPage.entityOption.formatDynamicPath(testData.get("Entity")).click();
			requestBindPage.addAdditionalInterest(testData);
			testData = data.get(data_Value1);
			requestBindPage.renewalRequestBindNAHO(testData);
			Assertions.passTest("Request Bind Page", "Renewal details entered successfully");

			// Asserting policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy summary page",
					"Policy summary page loaded successfully. Renewal PolicyNumber is : " + policyNumber, false, false);

			// Close the browser
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBVATC003 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBVATC003 ", "Executed Successfully");
			}
		}
	}

}
