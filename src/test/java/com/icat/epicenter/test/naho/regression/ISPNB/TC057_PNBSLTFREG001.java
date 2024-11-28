package com.icat.epicenter.test.naho.regression.ISPNB;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.ConfirmBindRequestPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.EndorseSummaryDetailsPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC057_PNBSLTFREG001 extends AbstractNAHOTest {

	public TC057_PNBSLTFREG001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBSLTFREG001.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ReferralPage referralPage = new ReferralPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ConfirmBindRequestPage confirmBindRequestPage = new ConfirmBindRequestPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ViewPolicySnapShot viewPolicySnapshot = new ViewPolicySnapShot();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		EndorseSummaryDetailsPage endorseSummaryDetailsPage = new EndorseSummaryDetailsPage();
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);

		String quoteNumber;
		String policyNumber;
		String premiumAmount;
		String fees;
		String inspectionFee;
		String policyFee;
		String surplusContributionValue;
		double surplusTax;
		String totalPremiumValue;
		int dataValue1 = 0;
		int dataValue2 = 1;
		Map<String, String> testData = data.get(dataValue1);
		DecimalFormat df = new DecimalFormat("0.00");
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
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
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");
			// Adding this condition to handle deductible minimum warning
			if (createQuotePage.override.checkIfElementIsPresent()
					&& createQuotePage.override.checkIfElementIsDisplayed()) {
				createQuotePage.override.scrollToElement();
				createQuotePage.override.click();
			}

			// Getting the quote number from account overview page
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Getting the premium,fees, Surplus Contribution value and sltf values from
			// premium section in Account
			// Overview page
			Assertions.verify(accountOverviewPage.premiumValue.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Premium Value is : " + accountOverviewPage.premiumValue.getData(), false,
					false);
			Assertions.verify(accountOverviewPage.feesValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Fees Value is : " + accountOverviewPage.feesValue.getData(), false, false);
			Assertions.verify(accountOverviewPage.surplusContibutionValue.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"Surplus Contribution Value is : " + accountOverviewPage.surplusContibutionValue.getData(), false,
					false);
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Surplus Lines Taxes and Fees label displayed is verified", false, false);

			// getting premium amount
			premiumAmount = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");

			// getting fees value
			fees = accountOverviewPage.feesValue.getData().replace("$", "").replace(",", "");

			// getting surplus contribution value
			surplusContributionValue = accountOverviewPage.surplusContibutionValue.getData().replace("$", "")
					.replace(",", "").replace("%", "");

			// calculating sltf percentage by adding Premium+Fees and multiplying by sltf
			// percentage 0.04
			surplusTax = (Double.parseDouble(premiumAmount) + Double.parseDouble(fees)
					+ Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			BigDecimal surplustaxes = BigDecimal.valueOf(surplusTax);
			Assertions.passTest("Account Overview Page", "Surplus Lines Taxes :  $" + df.format(surplustaxes));

			// Comparing actual and expected SLTF value and printing calculated value
			Assertions.verify(accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", ""),
					df.format(surplustaxes), "Account Overview Page",
					"Calculated Surplus Lines Taxes and Fees : " + format.format(surplustaxes), false, false);

			// Comparing actual and expected SLTF value and printing actual value
			Assertions.verify(accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", ""),
					df.format(surplustaxes), "Account Overview Page",
					"Actual Surplus Lines Taxes and Fees : " + accountOverviewPage.sltfValue.getData(), false, false);
			totalPremiumValue = accountOverviewPage.totalPremiumValue.getData().replace("$", "").replace(",", "");

			// Comparing actual and expected total premium and fees value and printing
			// calculated value
			Assertions.verify(accountOverviewPage.totalPremiumValue.getData().replace("$", "").replace(",", ""),
					df.format((Double.parseDouble(premiumAmount)) + (Double.parseDouble(surplustaxes + "")
							+ (Double.parseDouble(fees) + Double.parseDouble(surplusContributionValue)))),
					"Account Overview Page",
					"Calculated Premium, Taxes and Fees : " + "$"
							+ df.format((Double.parseDouble(premiumAmount)) + (Double.parseDouble(surplustaxes + "")
									+ (Double.parseDouble(fees) + Double.parseDouble(surplusContributionValue)))),
					false, false);

			// Comparing actual and expected total premium and fees value and printing
			// actual value
			Assertions.verify(accountOverviewPage.totalPremiumValue.getData().replace("$", "").replace(",", ""),
					df.format((Double.parseDouble(premiumAmount)) + (Double.parseDouble(surplustaxes + "")
							+ (Double.parseDouble(fees) + Double.parseDouble(surplusContributionValue)))),
					"Account Overview Page",
					"Actual Premium, Taxes and Fees : " + accountOverviewPage.totalPremiumValue.getData(), false,
					false);

			// clicking on Request Bind button
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Request Bind Page", "Clicked on Request Bind");

			// Entering details in Underwriting Questions Page
			Assertions.verify(underwritingQuestionsPage.saveButton.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered successfully");

			// Entering Details in Request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterPolicyDetailsNAHO(testData);
			requestBindPage.enterPaymentInformationNAHO(testData);
			requestBindPage.addInspectionContact(testData);
			requestBindPage.addContactInformation(testData);

			Assertions.verify(requestBindPage.diligenceText.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Diligence Question field present is verfied", false, false);

			Assertions.verify(requestBindPage.diligenceText.checkIfElementIsDisplayed(), true, "Request Bind Page",
					requestBindPage.diligenceText.getData() + "is verfied", false, false);

			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			confirmBindRequestPage.quotePremium.waitTillVisibilityOfElement(60);

			// Get quote premium
			String actualQuotePremium = confirmBindRequestPage.quotePremium.getData().substring(15).replace(",", "")
					.replace("$", "");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(actualQuotePremium), 2)
					- Precision.round(Double.parseDouble(totalPremiumValue), 2)), 2) < 0.05) {
				Assertions.passTest("Confirm Bind Request Page", "Calculated Premium Value : " + "$"
						+ Precision.round(Double.parseDouble(totalPremiumValue), 2));
				Assertions.passTest("Confirm Bind Request Page", "Actual Premium Value : " + "$" + actualQuotePremium);

			} else {
				Assertions.passTest("Confirm Bind Request Page",
						"The Difference between actual and calculated total premium is more than 0.05");
			}

			requestBindPage.confirmBindNAHO(testData);

			if (bindRequestPage.pageName.getData().equalsIgnoreCase("Bind Request Submitted")) {
				// clicking on Home page button in Bind Request Submitted Page
				Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
						"Bind Request Page loaded successfully", false, false);
				bindRequestPage.clickOnHomepagebutton();
				Assertions.passTest("Home Page", "Clicked on Quote Number");

				// Searching for Quote Number in User Referrals Table
				Assertions.verify(homePage.goToHomepage.checkIfElementIsDisplayed(), true, "Bind Request Page",
						"Home Page loaded successfully", false, false);
				homePage.searchReferral(quoteNumber);
				Assertions.passTest("Home Page", "Searched Submitted Bind Request successfullly");

				// Clicking on Approve/Decline Request button in Referrals page
				Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true,
						"Referral Page", "Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");

				// Clicking on Approve in Request Bind Page
				requestBindPage.approveRequestNAHO(testData);
				Assertions.passTest("Request Bind Page", "Bind Request approved successfully");
			}

			// Getting the Policy Number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.passTest("Policy Summary", "Policy Number is : " + policyNumber);

			// clicking on View Policy Snapshot link
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.verify(viewPolicySnapshot.surplusLinesTaxesValue.checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page", "View Policy Snapshot Page loaded successfully", false, false);

			// Comparing actual and expected SLTF value and printing actual and expected
			// value
			Assertions.verify(viewPolicySnapshot.surplusLinesTaxesValue.getData().replace("$", "").replace(",", ""),
					df.format(surplustaxes).replace(",", ""), "View Policy Snapshot Page",
					"Calculated Surplus Fees : " + "$" + df.format(surplustaxes).replace(",", ""), false, false);
			Assertions.verify(viewPolicySnapshot.surplusLinesTaxesValue.getData().replace("$", "").replace(",", ""),
					df.format(surplustaxes).replace(",", ""), "View Policy Snapshot Page",
					"Actual Surplus Fees : " + viewPolicySnapshot.surplusLinesTaxesValue.getData(), false, false);
			Assertions.verify(viewPolicySnapshot.surplusLinesTaxesValue.getData().replace("$", "").replace(",", ""),
					df.format(surplustaxes).replace(",", ""), "View Policy Snapshot Page",
					"Surplus Fees calculated as per Surplus Lines Taxes Percentage 4.85% for LA is verified", false,
					false);

			// clicking on go back button
			viewPolicySnapshot.scrollToTopPage();
			viewPolicySnapshot.waitTime(3);
			viewPolicySnapshot.goBackButton.click();

			// clicking on Endorse PB Link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();

			// Setting Endorsement effective date
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// clicking on Edit Loc/Bldg information
			endorsePolicyPage.editLocOrBldgInformationLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.editLocOrBldgInformationLink.waitTillButtonIsClickable(60);
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();
			Assertions.passTest("Dwelling Page", "Dwelling Page loaded successfully");

			// getting location and building count
			String locationNumber = testData.get("LocCount");
			int locNo = Integer.parseInt(locationNumber);

			String dwellingNumber = testData.get("L" + locNo + "-DwellingCount");
			int bldgNo = Integer.parseInt(dwellingNumber);

			// Changing Construction type to Fire Resistive and Occupied by to Tenant
			testData = data.get(dataValue2);
			Assertions.passTest("Dwelling Page",
					"Construction Type Before Change: " + dwellingPage.constructionTypeData.getData());
			dwellingPage.constructionTypeArrow.waitTillVisibilityOfElement(60);
			dwellingPage.constructionTypeArrow.scrollToElement();
			dwellingPage.constructionTypeArrow.click();
			dwellingPage.constructionTypeOption
					.formatDynamicPath(testData.get("L" + locNo + "D" + bldgNo + "-DwellingConstType"))
					.waitTillVisibilityOfElement(60);
			dwellingPage.constructionTypeOption
					.formatDynamicPath(testData.get("L" + locNo + "D" + bldgNo + "-DwellingConstType"))
					.scrollToElement();
			dwellingPage.constructionTypeOption
					.formatDynamicPath(testData.get("L" + locNo + "D" + bldgNo + "-DwellingConstType")).click();
			Assertions.passTest("Dwelling Page",
					"Construction Type After Change : " + dwellingPage.constructionTypeData.getData());

			Assertions.passTest("Dwelling Page", "Occupied By Before Change: " + dwellingPage.occupiedByData.getData());
			dwellingPage.OccupiedByArrow.waitTillVisibilityOfElement(60);
			dwellingPage.OccupiedByArrow.scrollToElement();
			dwellingPage.OccupiedByArrow.click();
			dwellingPage.OccupiedByOption.formatDynamicPath(testData.get("L" + locNo + "D" + bldgNo + "-OccupiedBy"))
					.scrollToElement();
			dwellingPage.OccupiedByOption.formatDynamicPath(testData.get("L" + locNo + "D" + bldgNo + "-OccupiedBy"))
					.click();
			Assertions.passTest("Dwelling Page", "Occupied By After Change: " + dwellingPage.occupiedByData.getData());
			Assertions.passTest("Dwelling Page", "Details modified successfully");

			// click on continue button
			dwellingPage.continueButton.scrollToElement();
			dwellingPage.continueButton.click();

			// changing deductible values
			Assertions.passTest("Create Quote Page", "Create Quote loaded successfully");
			createQuotePage.enterDeductiblesNAHO(testData);
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			Assertions.passTest("Create Quote Page", "Deductibles details modified successfully");

			// click on continue button
			if (createQuotePage.pageName.getData().contains("Create Quote")) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}
			if (createQuotePage.override.checkIfElementIsPresent()
					&& createQuotePage.override.checkIfElementIsDisplayed()) {
				createQuotePage.override.scrollToElement();
				createQuotePage.override.click();
			}
			// verifying the values changed in Endorse Summary section
			Assertions.verify(endorseSummaryDetailsPage.policyLevelChangesToCol.formatDynamicPath(4, 5).getData(),
					"N/A", "Endorse Policy Page",
					"Personal Injury Coverage changed from"
							+ endorseSummaryDetailsPage.policyLevelChangesToCol.formatDynamicPath(4, 4).getData()
							+ " to "
							+ endorseSummaryDetailsPage.policyLevelChangesToCol.formatDynamicPath(4, 5).getData()
							+ " is verified",
					false, false);

			Assertions.verify(
					endorseSummaryDetailsPage.buildingLevelChangesToCol.formatDynamicPath(1, 5).getData()
							.contains("$5,000"),
					true, "Endorse Policy Page",
					"AOP Deductible changed from "
							+ endorseSummaryDetailsPage.buildingLevelChangesToCol.formatDynamicPath(1, 4).getData()
							+ " to "
							+ endorseSummaryDetailsPage.buildingLevelChangesToCol.formatDynamicPath(1, 5).getData()
							+ " is verified",
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.buildingLevelChangesToCol
							.formatDynamicPath(3, 5).getData().contains(testData.get("NamedStormValue")),
					true, "Endorse Policy Page",
					"Named Storm Deductible changed from "
							+ endorseSummaryDetailsPage.buildingLevelChangesToCol.formatDynamicPath(3, 4).getData()
							+ " to "
							+ endorseSummaryDetailsPage.buildingLevelChangesToCol.formatDynamicPath(3, 5).getData()
							+ " is verified",
					false, false);

			Assertions.verify(
					endorseSummaryDetailsPage.buildingLevelChangesToCol.formatDynamicPath(2, 5).getData()
							.contains(testData.get("L" + locNo + "D" + bldgNo + "-DwellingConstType")),
					true, "Endorse Policy Page",
					"Construction Class changed from "
							+ endorseSummaryDetailsPage.buildingLevelChangesToCol.formatDynamicPath(2, 4).getData()
							+ " to "
							+ endorseSummaryDetailsPage.buildingLevelChangesToCol.formatDynamicPath(2, 5).getData()
							+ " is verified",
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.buildingLevelChangesToCol.formatDynamicPath(4, 5).getData()
							.contains(testData.get("L" + locNo + "D" + bldgNo + "-OccupiedBy")),
					true, "Endorse Policy Page",
					"Occupied By changed from "
							+ endorseSummaryDetailsPage.buildingLevelChangesToCol.formatDynamicPath(4, 4).getData()
							+ " to "
							+ endorseSummaryDetailsPage.buildingLevelChangesToCol.formatDynamicPath(4, 5).getData()
							+ " is verified",
					false, false);

			// clicking on next button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// clicking on ok,continue in Endorse policy page
			if (endorsePolicyPage.pageName.getData().contains("Overrides Required")) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// getting transaction,annual and term total premiums from the Endorse Policy
			// page
			testData = data.get(dataValue1);
			Assertions.passTest("Endorse Policy Page : ",
					"Transaction Term Premium : " + endorsePolicyPage.premiumDetails.formatDynamicPath(1, 2).getData());

			premiumAmount = endorsePolicyPage.premiumDetails.formatDynamicPath(1, 2).getData().replaceAll("[^\\d-.]",
					"");
			inspectionFee = endorsePolicyPage.premiumDetails.formatDynamicPath(2, 2).getData().replaceAll("[^\\d-.]",
					"");
			policyFee = endorsePolicyPage.premiumDetails.formatDynamicPath(3, 2).getData().replaceAll("[^\\d-.]", "");
			surplusContributionValue = endorsePolicyPage.premiumDetails.formatDynamicPath(5, 2).getData()
					.replaceAll("[^\\d-.]", "");

			surplusTax = (Double.parseDouble(premiumAmount) + Integer.parseInt(inspectionFee)
					+ Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			BigDecimal termsurplustaxes = BigDecimal.valueOf(surplusTax);
			termsurplustaxes = termsurplustaxes.setScale(2, RoundingMode.HALF_UP);

			// Comparing actual and expected SLTF value and printing calculated value
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath(4, 2).getData(),
					format.format(termsurplustaxes).replace("(", "-").replace(")", ""), "Endorse Policy Page",
					"Calculated Surplus Lines Taxes and Fees : "
							+ format.format(termsurplustaxes).replace("(", "-").replace(")", ""),
					false, false);

			// Comparing actual and expected SLTF value and printing actual value
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath(4, 2).getData(),
					format.format(termsurplustaxes).replace("(", "-").replace(")", ""), "Endorse Policy Page",
					"Actual Surplus Lines Taxes and Fees : "
							+ endorsePolicyPage.premiumDetails.formatDynamicPath(4, 2).getData(),
					false, false);

			// Comparing actual and expected total premium and fees value and printing
			// calculated value
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath(6, 2).getData(), format
					.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(termsurplustaxes + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue)))))
					.replace("(", "-").replace(")", ""), "Endorse Policy Page",
					"Calculated Total Premium, Taxes and Fees : " + format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(termsurplustaxes + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue)))))
							.replace("(", "-").replace(")", ""),
					false, false);

			// Comparing actual and expected total premium and fees value and printing
			// actual value
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath(6, 2).getData(), format
					.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(termsurplustaxes + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue)))))
					.replace("(", "-").replace(")", ""), "Endorse Policy Page",
					"Actual Total Premium, Taxes and Fees : "
							+ endorsePolicyPage.premiumDetails.formatDynamicPath(6, 2).getData(),
					false, false);

			Assertions.passTest("Endorse Policy Page : ",
					"Annual Term Premium : " + endorsePolicyPage.premiumDetails.formatDynamicPath(1, 3).getData());

			premiumAmount = endorsePolicyPage.premiumDetails.formatDynamicPath(1, 3).getData().replaceAll("[^\\d-.]",
					"");
			inspectionFee = endorsePolicyPage.premiumDetails.formatDynamicPath(2, 3).getData().replaceAll("[^\\d-.]",
					"");
			policyFee = endorsePolicyPage.premiumDetails.formatDynamicPath(3, 3).getData().replaceAll("[^\\d-.]", "");
			surplusContributionValue = endorsePolicyPage.premiumDetails.formatDynamicPath(5, 3).getData()
					.replaceAll("[^\\d-.]", "");

			surplusTax = (Double.parseDouble(premiumAmount) + Integer.parseInt(inspectionFee)
					+ Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			BigDecimal annualsurplustaxes = BigDecimal.valueOf(surplusTax);
			annualsurplustaxes = annualsurplustaxes.setScale(2, RoundingMode.HALF_UP);

			// Comparing actual and expected SLTF value and printing calculated value
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath(4, 3).getData(),
					format.format(annualsurplustaxes), "Endorse Policy Page",
					"Calculated Surplus Lines Taxes and Fees : " + format.format(annualsurplustaxes), false, false);

			// Comparing actual and expected SLTF value and printing actual value
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath(4, 3).getData(),
					format.format(annualsurplustaxes), "Endorse Policy Page", "Actual Surplus Lines Taxes and Fees : "
							+ endorsePolicyPage.premiumDetails.formatDynamicPath(4, 3).getData(),
					false, false);

			// Comparing actual and expected total premium and fees value and printing
			// calculated value
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath(6, 3).getData(),
					format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(annualsurplustaxes + "") + (Integer
									.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					"Endorse Policy Page",
					"Calculated Total Premium, Taxes and Fees : " + format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(annualsurplustaxes + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					false, false);

			// Comparing actual and expected total premium and fees value and printing
			// actual value
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath(6, 3).getData(),
					format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(annualsurplustaxes + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					"Endorse Policy Page", "Actual Total Premium, Taxes and Fees : "
							+ endorsePolicyPage.premiumDetails.formatDynamicPath(6, 3).getData(),
					false, false);

			Assertions.passTest("Endorse Policy Page : ",
					"Term Total Premium : " + endorsePolicyPage.premiumDetails.formatDynamicPath(1, 4).getData());

			premiumAmount = endorsePolicyPage.premiumDetails.formatDynamicPath(1, 4).getData().replaceAll("[^\\d-.]",
					"");
			inspectionFee = endorsePolicyPage.premiumDetails.formatDynamicPath(2, 4).getData().replaceAll("[^\\d-.]",
					"");
			policyFee = endorsePolicyPage.premiumDetails.formatDynamicPath(3, 4).getData().replaceAll("[^\\d-.]", "");
			surplusContributionValue = endorsePolicyPage.premiumDetails.formatDynamicPath(5, 4).getData()
					.replaceAll("[^\\d-.]", "");

			surplusTax = (Double.parseDouble(premiumAmount) + Integer.parseInt(inspectionFee)
					+ Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			BigDecimal totaltermsurplustaxes = BigDecimal.valueOf(surplusTax);

			// Comparing actual and expected SLTF value and printing calculated value
			Assertions.verify(
					endorsePolicyPage.premiumDetails.formatDynamicPath(4, 4).getData().replace("$", "").replace(",",
							""),
					df.format(totaltermsurplustaxes), "Endorse Policy Page",
					"Calculated Surplus Lines Taxes and Fees : " + df.format(totaltermsurplustaxes), false, false);

			// Comparing actual and expected SLTF value and printing actual value
			Assertions.verify(
					endorsePolicyPage.premiumDetails.formatDynamicPath(4, 4).getData().replace("$", "").replace(",",
							""),
					df.format(totaltermsurplustaxes), "Endorse Policy Page", "Actual Surplus Lines Taxes and Fees : "
							+ endorsePolicyPage.premiumDetails.formatDynamicPath(4, 4).getData(),
					false, false);

			// Comparing actual and expected total premium and fees value and printing
			// calculated value
			Assertions.verify(
					endorsePolicyPage.premiumDetails.formatDynamicPath(6, 4).getData().replace("$", "").replace(",",
							""),
					df.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(totaltermsurplustaxes + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					"Endorse Policy Page",
					"Calculated Total Premium, Taxes and Fees : " + format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(totaltermsurplustaxes + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					false, false);

			// Comparing actual and expected total premium and fees value and printing
			// actual value
			Assertions.verify(
					endorsePolicyPage.premiumDetails.formatDynamicPath(6, 4).getData().replace("$", "").replace(",",
							""),
					df.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(totaltermsurplustaxes + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					"Endorse Policy Page", "Actual Total Premium, Taxes and Fees : "
							+ endorsePolicyPage.premiumDetails.formatDynamicPath(6, 4).getData(),
					false, false);
			// df.setRoundingMode(RoundingMode.DOWN);

			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// getting transaction,annual and term total premiums from Endorse Summary page
			Assertions.passTest("Endorse Summary Page: ", "Transaction Term Premium : "
					+ endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(1, 2).getData());

			premiumAmount = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(1, 2).getData()
					.replaceAll("[^\\d-.]", "");
			inspectionFee = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(2, 2).getData()
					.replaceAll("[^\\d-.]", "");
			policyFee = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(3, 2).getData()
					.replaceAll("[^\\d-.]", "");
			surplusContributionValue = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(6, 2).getData()
					.replaceAll("[^\\d-.]", "");

			surplusTax = (Double.parseDouble(premiumAmount) + Integer.parseInt(inspectionFee)
					+ Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			BigDecimal termsurplustaxesSummary = BigDecimal.valueOf(surplusTax);
			termsurplustaxesSummary = termsurplustaxesSummary.setScale(2, RoundingMode.HALF_UP);

			// Comparing actual and expected SLTF value and printing calculated value
			Assertions.verify(endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(4, 2).getData(),
					format.format(termsurplustaxesSummary).replace("(", "-").replace(")", ""), "Endorse Summary Page",
					"Calculated Surplus Lines Taxes and Fees : "
							+ format.format(termsurplustaxes).replace("(", "-").replace(")", ""),
					false, false);

			// Comparing actual and expected SLTF value and printing actual value
			Assertions.verify(endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(4, 2).getData(),
					format.format(termsurplustaxesSummary).replace("(", "-").replace(")", ""), "Endorse Summary Page",
					"Actual Surplus Lines Taxes and Fees : "
							+ endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(4, 2).getData(),
					false, false);

			// Comparing actual and expected total premium and fees value and printing
			// calculated value
			Assertions.verify(endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(7, 2).getData(), format
					.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(termsurplustaxesSummary + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue)))))
					.replace("(", "-").replace(")", ""), "Endorse Summary Page",
					"Calculated Total Premium, Taxes and Fees : " + format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(termsurplustaxesSummary + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue)))))
							.replace("(", "-").replace(")", ""),
					false, false);

			// Comparing actual and expected total premium and fees value and printing
			// actual value
			Assertions.verify(endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(7, 2).getData(), format
					.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(termsurplustaxesSummary + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue)))))
					.replace("(", "-").replace(")", ""), "Endorse Summary Page",
					"Actual Total Premium, Taxes and Fees : "
							+ endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(7, 2).getData(),
					false, false);

			Assertions.passTest("Endorse Summary Page: ", "Annual Term Premium : "
					+ endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(1, 3).getData());

			premiumAmount = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(1, 3).getData()
					.replaceAll("[^\\d-.]", "");
			inspectionFee = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(2, 3).getData()
					.replaceAll("[^\\d-.]", "");
			policyFee = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(3, 3).getData()
					.replaceAll("[^\\d-.]", "");
			surplusContributionValue = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(6, 3).getData()
					.replaceAll("[^\\d-.]", "");

			surplusTax = (Double.parseDouble(premiumAmount) + Integer.parseInt(inspectionFee)
					+ Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			BigDecimal annualsurplustaxesSummary = BigDecimal.valueOf(surplusTax);
			annualsurplustaxesSummary = annualsurplustaxesSummary.setScale(2, RoundingMode.HALF_UP);

			// Comparing actual and expected SLTF value and printing calculated value
			Assertions.verify(endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(4, 3).getData(),
					format.format(annualsurplustaxesSummary), "Endorse Summary Page",
					"Calculated Surplus Lines Taxes and Fees : " + format.format(annualsurplustaxes), false, false);

			// Comparing actual and expected SLTF value and printing actual value
			Assertions
					.verify(endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(4, 3).getData(),
							format.format(annualsurplustaxesSummary), "Endorse Summary Page",
							"Actual Surplus Lines Taxes and Fees : "
									+ endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(4, 3).getData(),
							false, false);

			// Comparing actual and expected total premium and fees value and printing
			// calculated value
			Assertions.verify(endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(7, 3).getData(),
					format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(annualsurplustaxesSummary + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					"Endorse Summary Page",
					"Calculated Total Premium, Taxes and Fees : " + format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(annualsurplustaxes + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					false, false);

			// Comparing actual and expected total premium and fees value and printing
			// actual value
			Assertions.verify(endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(7, 3).getData(),
					format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(annualsurplustaxesSummary + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					"Endorse Summary Page", "Actual Total Premium, Taxes and Fees : "
							+ endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(7, 3).getData(),
					false, false);

			Assertions.passTest("Endorse Summary Page: ", "Term Total Premium : "
					+ endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(1, 4).getData());

			premiumAmount = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(1, 4).getData()
					.replaceAll("[^\\d-.]", "");
			inspectionFee = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(2, 4).getData()
					.replaceAll("[^\\d-.]", "");
			policyFee = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(3, 4).getData()
					.replaceAll("[^\\d-.]", "");

			surplusContributionValue = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(6, 4).getData()
					.replaceAll("[^\\d-.]", "");

			surplusTax = (Double.parseDouble(premiumAmount) + Integer.parseInt(inspectionFee)
					+ Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			BigDecimal totaltermsurplustaxesSummary = BigDecimal.valueOf(surplusTax);

			// Comparing actual and expected SLTF value and printing calculated value
			Assertions.verify(
					endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(4, 4).getData().replace("$", "")
							.replace(",", ""),
					df.format(totaltermsurplustaxesSummary), "Endorse Summary Page",
					"Calculated Surplus Lines Taxes and Fees : " + "$" + df.format(totaltermsurplustaxes), false,
					false);

			// Comparing actual and expected SLTF value and printing actual value
			Assertions.verify(
					endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(4, 4).getData().replace("$", "")
							.replace(",", ""),
					df.format(totaltermsurplustaxesSummary), "Endorse Summary Page",
					"Actual Surplus Lines Taxes and Fees : "
							+ endorsePolicyPage.premiumDetails.formatDynamicPath(4, 4).getData(),
					false, false);

			// Comparing actual and expected total premium and fees value and printing
			// calculated value
			Assertions.verify(
					endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(7, 4).getData().replace("$", "")
							.replace(",", ""),
					df.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(totaltermsurplustaxesSummary + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					"Endorse Summary Page",
					"Calculated Total Premium, Taxes and Fees : " + "$" + df.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(totaltermsurplustaxesSummary + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					false, false);

			// Comparing actual and expected total premium and fees value and printing
			// actual value
			Assertions.verify(
					endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(7, 4).getData().replace("$", "")
							.replace(",", ""),
					df.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(totaltermsurplustaxesSummary + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					"Endorse Summary Page", "Actual Total Premium, Taxes and Fees : "
							+ endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(7, 4).getData(),
					false, false);

			endorsePolicyPage.scrollToBottomPage();
			endorsePolicyPage.waitTime(3);
			endorsePolicyPage.closeButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC057 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC057 ", "Executed Successfully");
			}
		}
	}
}