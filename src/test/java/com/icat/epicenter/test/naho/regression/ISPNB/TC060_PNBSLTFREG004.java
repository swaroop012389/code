package com.icat.epicenter.test.naho.regression.ISPNB;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CancelPolicySuccessfulPage;
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
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC060_PNBSLTFREG004 extends AbstractNAHOTest {

	public TC060_PNBSLTFREG004() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBSLTFREG004.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the Objects
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		CancelPolicySuccessfulPage cancelPolicySuccessfulPage = new CancelPolicySuccessfulPage();

		String quoteNumber;
		String premium;
		String inspectionFee;
		String policyFee;
		String fees;
		String surplusContributionValue;
		String surplusLinesTaxesPercentage;
		String policyNumber;
		double d_premium;
		double d_inspectionFee;
		double d_policyFee;
		double i_fees;
		double d_surplusContributionValue;
		double d_surplusLinesTaxesPercentage;
		BigDecimal SLTFRoundOff;
		String actualSLTFValue;
		String calculatedSLTFValue;
		String actualEarnedSLTFValue1;
		String actualEarnedSLTFValue2;
		String actualReturnedSLTFValue1;
		String calculatedReturnedSLTFValue1;
		String actualReturnedSLTFValue2;
		String calculatedReturnedSLTFValue2;

		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		int data_Value1 = 0;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Creating new producer account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zip code
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering dwelling details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page Loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling Details entered successfully");

			// Entering prior loss Page
			Assertions.verify(priorLossesPage.lossesInThreeYearsNo.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"prior Loss Page Loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);
			Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");

			// Entering Create quote page details
			Assertions.verify(createQuotePage.quoteName.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page Loaded Successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Refer Quote
			if (referQuotePage.contactName.checkIfElementIsPresent()
					&& referQuotePage.contactName.checkIfElementIsDisplayed()) {
				// Enter Referral Contact Details
				referQuotePage.contactName.setData(testData.get("ProducerName"));
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.click();
				Assertions.passTest("Refer Quote Page", "Quote referred successfully");
				quoteNumber = referQuotePage.quoteNumberforReferral.getData();
				Assertions.passTest("Refer Quote Page", "Quote Number : " + quoteNumber);

				// SignOut as Producer
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Home Page", "Logged out as Producer successfully");

				// Sign in as USM
				loginPage.refreshPage();
				loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
				Assertions.passTest("Home Page", "Logged in as USM Successfully");

				// searching the quote number in grid and clicking on the quote number link
				// homePage.searchReferral(quoteNumber);
				homePage.searchQuote(quoteNumber);
				Assertions.passTest("Home Page", "Searched Submitted Quote" + quoteNumber + " successfullly");

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

				// Handling the pop up
				homePage.enterPersonalLoginDetails();

				// Search for quote
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuoteByProducer(quoteNumber);
			}

			// Getting quote number from account overview Page
			Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded Successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(testData.get("QuoteCount")).getData()
					.substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number : " + quoteNumber);

			// Verify SLTF label is displayed in account overview Page
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Surplus Lines Taxes and Fees Label is displayed", false, false);

			// Getting sltf value from account overview page
			actualSLTFValue = accountOverviewPage.sltfValue.getData();

			// Getting premium value from account overview page
			premium = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");

			// Getting fees value from account overview page
			fees = accountOverviewPage.feesValue.getData().replace("$", "").replace(",", "");
			surplusContributionValue = accountOverviewPage.surplusContibutionValue.getData().replace("$", "")
					.replace(",", "");

			// Getting surplusLinesTaxesPercentage from testdata
			surplusLinesTaxesPercentage = testData.get("SurplusLinesTaxesPercentage");

			// Conversion of String to double/int to calculate sltf
			d_premium = Double.parseDouble(premium);
			i_fees = Double.parseDouble(fees);
			d_surplusLinesTaxesPercentage = Double.parseDouble(surplusLinesTaxesPercentage);
			d_surplusContributionValue = Double.parseDouble(surplusContributionValue);

			// Calculate SLTF=(Premium+Fees)*surplusLinesTaxesPercentage(0.0485)
			double d_SLTFValue = (d_premium + i_fees + d_surplusContributionValue) * d_surplusLinesTaxesPercentage;

			// Rounding Off SLTF decimal value to 2 digits and Converting back to String
			SLTFRoundOff = BigDecimal.valueOf(d_SLTFValue);
			SLTFRoundOff = SLTFRoundOff.setScale(2, RoundingMode.HALF_UP);
			calculatedSLTFValue = format.format(SLTFRoundOff);

			// Verify actual and calculated SLTF values are equal
			if (actualSLTFValue != null && calculatedSLTFValue != null) {
				Assertions.passTest("Account Overview Page",
						"Actual Surplus Lines Taxes and Fees : " + actualSLTFValue);
				Assertions.passTest("Account Overview Page",
						"Calculated Surplus Lines Taxes and Fees : " + calculatedSLTFValue);
				Assertions.verify(actualSLTFValue.equalsIgnoreCase(calculatedSLTFValue), true, "Account Overview Page",
						"The SLTF value is calculated correctly as per the LA percentage (4.85%)", false, false);
			}

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
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// clicking on Home page button in Bind Request Submitted Page
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);

			// SignOut as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Producer successfully");

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");

			// Searching for Quote Number in User Referrals Table
			Assertions.verify(homePage.goToHomepage.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			accountOverviewPage.uploadPreBindApproveAsUSM();
			Assertions.passTest("Home Page", "Searched Submitted Bind Request successfullly");

			// Clicking on Approve/Decline Request button in Referrals page
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			// Clicking on Approve in Request Bind Page
			requestBindPage.approveRequestNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Getting the Policy Number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);
			policySummaryPage.inspectionFee.scrollToElement();

			for (int i = 1; i <= 3; i++) {
				// Getting premium of Transaction, Annual, Term Total
				premium = policySummaryPage.PremiumFee.formatDynamicPath(i).getData().replaceAll("[^\\d-.]", "");
				inspectionFee = policySummaryPage.PremiunInspectionFee.formatDynamicPath(i).getData().replace("$", "");
				policyFee = policySummaryPage.PremiumPolicyFee.formatDynamicPath(i).getData().replace("$", "")
						.replace(",", "");
				surplusContributionValue = policySummaryPage.surplusContributionValue.formatDynamicPath(i).getData()
						.replace("$", "").replace(",", "");

				// Conversion of String to double/int to calculate sltf
				d_premium = Double.parseDouble(premium);
				d_inspectionFee = Double.parseDouble(inspectionFee);
				d_policyFee = Double.parseDouble(policyFee);
				d_surplusContributionValue = Double.parseDouble(surplusContributionValue);

				// Calculate SLTF=(Premium+Fees)*surplusLinesTaxesPercentage(0.0485)
				d_SLTFValue = (d_premium + (d_inspectionFee + d_policyFee + d_surplusContributionValue))
						* d_surplusLinesTaxesPercentage;

				// Rounding Off SLTF decimal value to 2 digits and Converting back to String
				SLTFRoundOff = BigDecimal.valueOf(d_SLTFValue);
				SLTFRoundOff = SLTFRoundOff.setScale(2, RoundingMode.HALF_UP);
				calculatedSLTFValue = format.format(SLTFRoundOff);

				// Getting Actual SLTF Value
				actualSLTFValue = policySummaryPage.TaxesAndStateFees.formatDynamicPath(i).getData();

				// Verify actual and calculated SLTF values are equal
				if (actualSLTFValue != null && calculatedSLTFValue != null) {
					if (i == 1) {
						Assertions.passTest("Policy Summary Page",
								"Actual Transation Surplus Lines Taxes and Fees : " + actualSLTFValue);
						Assertions.passTest("Policy Summary Page",
								"Calculated Transaction Surplus Lines Taxes and Fees: " + calculatedSLTFValue);
						Assertions.verify(actualSLTFValue.equalsIgnoreCase(calculatedSLTFValue), true,
								"Policy Summary Page",
								"The Transaction SLTF value is calculated correctly as per the LA percentage(4.85%)",
								false, false);
					} else if (i == 2) {
						Assertions.passTest("Policy Summary Page",
								"Actual Annual Surplus Lines Taxes and Fees : " + actualSLTFValue);
						Assertions.passTest("Policy Summary Page",
								"Calculated Annual Surplus Lines Taxes and Fees : " + calculatedSLTFValue);
						Assertions.verify(actualSLTFValue.equalsIgnoreCase(calculatedSLTFValue), true,
								"Policy Summary Page",
								"The Annual SLTF value is calculated correctly as per the LA percentage(4.85%)", false,
								false);
					} else if (i == 3) {
						Assertions.passTest("Policy Summary Page",
								"Actual Term Total Surplus Lines Taxes and Fees : " + actualSLTFValue);
						Assertions.passTest("Policy Summary Page",
								"Calculated Term Total Annual Surplus Lines Taxes and Fees : " + calculatedSLTFValue);
						Assertions.verify(actualSLTFValue.equalsIgnoreCase(calculatedSLTFValue), true,
								"Policy Summary Page",
								"The Term Total SLTF value is calculated correctly as per the LA percentage(4.85%)",
								false, false);
					}
				}
			}

			// Clicking on Cancel Policy
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.waitTillVisibilityOfElement(60);
			policySummaryPage.cancelPolicy.waitTillElementisEnabled(60);
			policySummaryPage.cancelPolicy.waitTillButtonIsClickable(60);
			policySummaryPage.cancelPolicy.click();

			// Cancel Policy Page
			Assertions.verify(cancelPolicyPage.daysBeforeNOC.checkIfElementIsDisplayed(), true, "Cancel Policy Page",
					"Cancel Policy Page loaded successfully", false, false);

			// Entering cancellation reason
			cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			cancelPolicyPage.waitTime(3);
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).scrollToElement();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			Assertions.passTest("Cancel Policy Page",
					"Cancellation Reason : " + cancelPolicyPage.cancelReasonData.getData());

			// Entering cancellation effective date
			cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
			cancelPolicyPage.cancellationEffectiveDate.setData(testData.get("CancellationEffectiveDate"));
			cancelPolicyPage.cancellationEffectiveDate.tab();

			// Entering cancellation legal notice wording
			cancelPolicyPage.legalNoticeWording.waitTillVisibilityOfElement(60);
			cancelPolicyPage.legalNoticeWording.waitTillElementisEnabled(60);
			cancelPolicyPage.legalNoticeWording.scrollToElement();
			cancelPolicyPage.legalNoticeWording.setData(testData.get("Cancellation_LegalNoticeWording"));

			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.waitTillVisibilityOfElement(60);
			cancelPolicyPage.nextButton.click();

			// Getting Earned Premium from Cancellation screen
			premium = cancelPolicyPage.newPremium.getData().replaceAll("[^\\d-.]", "");

			// Getting Earned Inspection fee from Cancellation screen
			inspectionFee = cancelPolicyPage.newInspectionFee.getData().replace("$", "");

			// Getting Earned Policy fee from Cancellation screen
			policyFee = cancelPolicyPage.newPolicyFee.getData().replace("$", "").replace(",", "");

			// Getting Earned surplus contribution value from cancel policy page
			surplusContributionValue = cancelPolicyPage.surplusContributionVal.formatDynamicPath(2).getData()
					.replace("$", "").replace(",", "").replace("-", "").replace("%", "");

			// Getting Earned sltf value from Cancellation screen
			actualEarnedSLTFValue1 = cancelPolicyPage.newSLTF.getData().replace("$", "").replace(",", "");

			// Conversion of String to double/int to calculate sltf
			d_premium = Double.parseDouble(premium);
			d_inspectionFee = Double.parseDouble(inspectionFee);
			d_policyFee = Double.parseDouble(policyFee);
			d_surplusContributionValue = Double.parseDouble(surplusContributionValue);

			// Calculate SLTF=(Premium+Fees)*surplusLinesTaxesPercentage(0.0485)
			double d_calculatedEarnedSLTFValue1 = (d_premium + d_inspectionFee + d_policyFee
					+ d_surplusContributionValue) * d_surplusLinesTaxesPercentage;

			// Verify actual and calculated earned SLTF values are equal in Cancellation
			// screen
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(actualEarnedSLTFValue1), 2)
					- Precision.round(d_calculatedEarnedSLTFValue1, 2)), 2) < 0.05) {
				Assertions.passTest("Cancellation Screen",
						"Actual Earned Surplus Lines Taxes and Fees : " + actualEarnedSLTFValue1);
				Assertions.passTest("Cancellation Screen",
						"Calculated Earned Surplus Lines Taxes and Fees : " + d_calculatedEarnedSLTFValue1);
			} else {

				Assertions.verify(actualEarnedSLTFValue1, d_calculatedEarnedSLTFValue1, "Cancellation Page",
						"The Difference between actual and calculated Earned SLTF is more than 0.05", false, false);

			}

			// Getting Returned Premium from Cancellation screen
			premium = cancelPolicyPage.returnedPremium.getData().replace("$", "").replace("-", "").replace(",", "");

			// Getting Returned Inspection fee from Cancellation screen
			inspectionFee = cancelPolicyPage.returnedInspectionFee.getData().replace("$", "");

			// Getting Returned Policy fee from Cancellation screen
			policyFee = cancelPolicyPage.returnedPolicyFee.getData().replace("$", "");

			// Getting Returned surplus contribution value
			surplusContributionValue = cancelPolicyPage.surplusContributionVal.formatDynamicPath(3).getData()
					.replace("$", "").replace("-", "").replace(",", "").replace("%", "");

			// Getting Returned sltf value from Cancellation screen
			actualReturnedSLTFValue1 = cancelPolicyPage.returnedSLTF.getData().replace("$", "").replace(",", "");

			// Conversion of String to double/int to calculate sltf
			d_premium = Double.parseDouble(premium);
			d_inspectionFee = Double.parseDouble(inspectionFee);
			d_policyFee = Double.parseDouble(policyFee);
			d_surplusContributionValue = Double.parseDouble(surplusContributionValue);

			// Calculate SLTF=(Premium+Fees)*surplusLinesTaxesPercentage(0.0485)
			d_SLTFValue = (d_premium + (d_inspectionFee + d_policyFee) + d_surplusContributionValue)
					* d_surplusLinesTaxesPercentage;

			// Rounding Off SLTF decimal value to 2 digits and Converting back to String
			SLTFRoundOff = BigDecimal.valueOf(d_SLTFValue);
			SLTFRoundOff = SLTFRoundOff.setScale(2, RoundingMode.HALF_UP);
			calculatedReturnedSLTFValue1 = "-"
					+ format.format(SLTFRoundOff).replaceAll(",", "").replaceAll("\\(", "").replaceAll("\\)", "");

			// Verify actual and calculated returned SLTF values are equal in Cancellation
			// screen
			String str_actualReturnedSLTFValue1 = actualReturnedSLTFValue1.replace("$", "").replace("-", "");
			String str_calculatedReturnedSLTFValue1 = calculatedReturnedSLTFValue1.replace("$", "").replace("-", "");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(str_actualReturnedSLTFValue1), 2)
					- Precision.round(Double.parseDouble(str_calculatedReturnedSLTFValue1), 2)), 2) < 0.05) {
				Assertions.passTest("Cancellation Screen",
						"Actual Returned Surplus Lines Taxes and Fees : " + actualReturnedSLTFValue1);
				Assertions.passTest("Cancellation Screen",
						"Calculated Returned Surplus Lines Taxes and Fees : " + calculatedReturnedSLTFValue1);
			} else {

				Assertions.verify(str_actualReturnedSLTFValue1, str_calculatedReturnedSLTFValue1, "Cancellation Page",
						"The Difference between actual and calculated Returned SLTF is more than 0.05", false, false);

			}

			// Clicking on complete cancellation
			cancelPolicyPage.completeTransactionButton.scrollToElement();
			cancelPolicyPage.completeTransactionButton.waitTillVisibilityOfElement(60);
			cancelPolicyPage.completeTransactionButton.waitTillButtonIsClickable(60);
			cancelPolicyPage.completeTransactionButton.click();
			Assertions.passTest("Cancel Policy Page", "Clicked on complete button");

			// Cancellation Successful page
			Assertions.verify(cancelPolicySuccessfulPage.newPremium.checkIfElementIsDisplayed(), true,
					"Cancellation Successful Page", "Cancellation Successful Page loaded successfully", false, false);

			// Getting Earned Premium from Cancellation Successful Page
			premium = cancelPolicySuccessfulPage.newPremium.getData().replace("$", "").replace("-", "").replace(",",
					"");

			// Getting Earned Inspection fee from Cancellation Successful Page
			inspectionFee = cancelPolicySuccessfulPage.newInspectionFee.getData().replace("$", "");

			// Getting Earned Policy fee from Cancellation Successful Page
			policyFee = cancelPolicySuccessfulPage.newPolicyFee.getData().replace("$", "");

			// Getting Earned surplus contribution value from Cancellation Successful Page
			surplusContributionValue = cancelPolicySuccessfulPage.earnedSurplusContributionValue.getData()
					.replace("$", "").replace("-", "").replace(",", "");

			// Getting Earned sltf value from Cancellation Successful Page
			actualEarnedSLTFValue2 = cancelPolicySuccessfulPage.newSLTF.getData().replace("$", "").replace(",", "");

			// Conversion of String to double/int to calculate sltf
			d_premium = Double.parseDouble(premium);
			d_inspectionFee = Double.parseDouble(inspectionFee);
			d_policyFee = Double.parseDouble(policyFee);
			d_surplusContributionValue = Double.parseDouble(surplusContributionValue);

			// Calculate SLTF=(Premium+Fees)*surplusLinesTaxesPercentage(0.0485)
			d_SLTFValue = (d_premium + (d_inspectionFee + d_policyFee) + d_surplusContributionValue)
					* d_surplusLinesTaxesPercentage;

			// Verify actual and calculated earned SLTF values are equal in Cancellation
			// Successful Page
			if (Precision.round(Math.abs(
					Precision.round(Double.parseDouble(actualEarnedSLTFValue2), 2) - Precision.round(d_SLTFValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Cancellation Successful page",
						"Actual Earned Surplus Lines Taxes and Fees : " + actualEarnedSLTFValue2);
				Assertions.passTest("Cancellation Successful page",
						"Calculated Earned Surplus Lines Taxes and Fees : " + d_SLTFValue);
			} else {
				Assertions.verify(actualEarnedSLTFValue2, d_SLTFValue, "Cancellation Successful page",
						"The Difference between actual and calculated Earned SLTF is more than 0.05", false, false);

			}

			// Getting Returned Premium from Cancellation Successful page
			premium = cancelPolicySuccessfulPage.returnedPremium.getData().replace("$", "").replace("-", "")
					.replace(",", "");

			// Getting Returned Inspection fee from Cancellation Successful page
			inspectionFee = cancelPolicySuccessfulPage.returnedInspectionFee.getData().replace("$", "");

			// Getting Returned Policy fee from Cancellation Successful page
			policyFee = cancelPolicySuccessfulPage.returnedPolicyFee.getData().replace("$", "");

			// Getting Returned surplus contribution value from Cancellation Successful Page
			surplusContributionValue = cancelPolicySuccessfulPage.returnedSurplusContributionValue.getData()
					.replace("$", "").replace("-", "").replace(",", "");

			// Getting Returned sltf value from Cancellation Successful page
			actualReturnedSLTFValue2 = cancelPolicySuccessfulPage.returnedSLTF.getData().replace("$", "").replace(",",
					"");

			// Conversion of String to double/int to calculate sltf
			d_premium = Double.parseDouble(premium);
			d_inspectionFee = Double.parseDouble(inspectionFee);
			d_policyFee = Double.parseDouble(policyFee);
			d_surplusContributionValue = Double.parseDouble(surplusContributionValue);

			// Calculate SLTF=(Premium+Fees)*surplusLinesTaxesPercentage(0.0485)
			double d_calculatedReturnedSLTFValue2 = (d_premium + d_inspectionFee + d_policyFee
					+ d_surplusContributionValue) * d_surplusLinesTaxesPercentage;

			// Rounding Off SLTF decimal value to 2 digits and Converting back to String
			SLTFRoundOff = BigDecimal.valueOf(d_calculatedReturnedSLTFValue2);
			SLTFRoundOff = SLTFRoundOff.setScale(2, RoundingMode.HALF_UP);
			calculatedReturnedSLTFValue2 = "-"
					+ format.format(SLTFRoundOff).replaceAll(",", "").replaceAll("\\(", "").replaceAll("\\)", "");

			// Verify actual and calculated returned SLTF values are equal in Cancellation
			// Successful page
			String str_actualReturnedSLTFValue2 = actualReturnedSLTFValue2.replace("$", "").replace("-", "");
			String str_calculatedReturnedSLTFValue2 = calculatedReturnedSLTFValue2.replace("$", "").replace("-", "");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(str_actualReturnedSLTFValue2), 2)
					- Precision.round(Double.parseDouble(str_calculatedReturnedSLTFValue2), 2)), 2) < 0.05) {
				Assertions.passTest("Cancellation Successful page",
						"Actual Returned Surplus Lines Taxes and Fees : " + actualReturnedSLTFValue2);
				Assertions.passTest("Cancellation Successful page",
						"Calculated Returned Surplus Lines Taxes and Fees : " + calculatedReturnedSLTFValue2);
			} else {
				Assertions.verify(str_actualReturnedSLTFValue2, str_calculatedReturnedSLTFValue2,
						"Cancellation Successful page",
						"The Difference between actual and calculated Returned SLTF is more than 0.05", false, false);

			}

			// Verify Earned SLTF values in Cancellation screen and Cancellation Successful
			// page is equal
			Assertions.verify(actualEarnedSLTFValue1.equalsIgnoreCase(actualEarnedSLTFValue2), true,
					"Cancellation Successful page", "The earned SLTF value is displayed same as in Cancellation Screen",
					false, false);

			// Verify Returned SLTF values in Cancellation screen and Cancellation
			// Successful page is equal
			Assertions.verify(actualReturnedSLTFValue1.equalsIgnoreCase(actualReturnedSLTFValue2), true,
					"Cancellation Successful page",
					"The returned SLTF value is displayed same as in Cancellation Screen", false, false);

			// Clicking close button
			cancelPolicyPage.closeButton.scrollToElement();
			cancelPolicyPage.closeButton.waitTillVisibilityOfElement(60);
			cancelPolicyPage.closeButton.waitTillElementisEnabled(60);
			cancelPolicyPage.closeButton.waitTillButtonIsClickable(60);
			cancelPolicyPage.closeButton.click();
			Assertions.passTest("Cancel Policy Page", "Policy cancelled successfullly");

			// Go to HomePage
			homePage.goToHomepage.waitTillVisibilityOfElement(60);
			homePage.goToHomepage.waitTillElementisEnabled(60);
			homePage.goToHomepage.waitTillButtonIsClickable(60);
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC060 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC060 ", "Executed Successfully");
			}
		}
	}
}
