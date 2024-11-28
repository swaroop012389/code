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
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CancelPolicySuccessfulPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.EndorseSummaryDetailsPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.ReinstatePolicyPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC065_PNBSLTFREG009 extends AbstractNAHOTest {

	public TC065_PNBSLTFREG009() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBSLTFREG009.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
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
		ReinstatePolicyPage reinsatePolicyPage = new ReinstatePolicyPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		EndorseSummaryDetailsPage endorseSummaryDetailsPage = new EndorseSummaryDetailsPage();
		String quoteNumber;
		String premium;
		String inspectionFee;
		String policyFee;
		String fees;
		String surplusLinesTaxesPercentage;
		String premiumAmount;
		String surplusContributionValue;
		double surplusTax;
		String policyNumber;
		double d_premium;
		int i_inspectionFee;
		int i_policyFee;
		double i_fees;
		double d_surplusContributionValue;
		double d_surplusLinesTaxesPercentage;
		String actualSLTFValue;
		String calculatedSLTFValue;
		String actualEarnedSLTFValue1;
		String calculatedEarnedSLTFValue1;
		String actualEarnedSLTFValue2;
		String actualReturnedSLTFValue1;
		String calculatedReturnedSLTFValue1;
		String actualReturnedSLTFValue2;
		int dataValue1 = 0;
		int dataValue2 = 1;

		Map<String, String> testData = data.get(dataValue1);
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		DecimalFormat df = new DecimalFormat("0.00");
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

				// searching the quote number in grid and clicking on the quote
				// number link
				homePage.searchQuote(quoteNumber);
				Assertions.passTest("Home Page", "Searched Submitted Quote successfullly");

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
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(testData.get("QuoteNumber")).getData()
					.substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number : " + quoteNumber);

			// Verify SLTF label is displayed in account overview Page
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Surplus Lines Taxes and Fees Label is displayed", false, false);

			// Getting sltf value from account overview page
			actualSLTFValue = accountOverviewPage.sltfValue.getData();

			// Getting premium value from account overview page
			premium = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");

			// Getting fees value from account overview page
			fees = accountOverviewPage.feesValue.getData().replace("$", "").replace(",", "");

			// Getting Surplus ContributionValue from account overview page
			surplusContributionValue = accountOverviewPage.surplusContibutionValue.getData().replace("$", "")
					.replace(",", "").replace("%", "");

			// Getting surplusLinesTaxesPercentage from test data
			surplusLinesTaxesPercentage = testData.get("SurplusLinesTaxesPercentage");

			// Conversion of String to double/int to calculate sltf
			d_premium = Double.parseDouble(premium);
			i_fees = Double.parseDouble(fees);
			d_surplusContributionValue = Double.parseDouble(surplusContributionValue);
			d_surplusLinesTaxesPercentage = Double.parseDouble(surplusLinesTaxesPercentage);

			// Calculate SLTF=(Premium+Fees)*surplusLinesTaxesPercentage(0.0485)
			double d_SLTFValue = (d_premium + i_fees + d_surplusContributionValue) * d_surplusLinesTaxesPercentage;

			// Rounding Off SLTF decimal value to 2 digits and Converting back to
			// String
			BigDecimal SLTFRoundOff = BigDecimal.valueOf(d_SLTFValue);
			SLTFRoundOff = SLTFRoundOff.setScale(2, RoundingMode.HALF_UP);
			calculatedSLTFValue = format.format(SLTFRoundOff);

			// Verify actual and calculated SLTF values are equal
			if (actualSLTFValue != null && calculatedSLTFValue != null) {
				Assertions.passTest("Account Overview Page",
						"Actual Surplus Lines Taxes and Fees : " + actualSLTFValue);
				Assertions.passTest("Account Overview Page",
						"Calculated Surplus Lines Taxes and Fees : " + calculatedSLTFValue);
				Assertions.verify(actualSLTFValue.equalsIgnoreCase(calculatedSLTFValue), true, "Account Overview Page",
						"The SLTF value is calculated correctly as per the LA percentage(4.85%)", false, false);
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

			// Clicking on Cancel Policy
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.waitTillVisibilityOfElement(60);
			policySummaryPage.cancelPolicy.waitTillElementisEnabled(60);
			policySummaryPage.cancelPolicy.waitTillButtonIsClickable(60);
			policySummaryPage.cancelPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Cancel Policy link");

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
			if (!testData.get("Cancellation_LegalNoticeWording").equals("")) {
				cancelPolicyPage.legalNoticeWording.scrollToElement();
				cancelPolicyPage.legalNoticeWording.setData(testData.get("Cancellation_LegalNoticeWording"));
			}
			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();

			// Getting Earned Premium from Cancellation screen
			premium = cancelPolicyPage.newPremium.getData().replace("$", "").replace(",", "");

			// Getting Earned Inspection fee from Cancellation screen
			inspectionFee = cancelPolicyPage.newInspectionFee.getData().replace("$", "");

			// Getting Earned Policy fee from Cancellation screen
			policyFee = cancelPolicyPage.newPolicyFee.getData().replace("$", "");

			// Getting Earned surplus contribution value from cancel policy page
			surplusContributionValue = cancelPolicyPage.surplusContributionVal.formatDynamicPath(2).getData()
					.replace("$", "").replace(",", "").replace("-", "").replace("%", "").replace("%", "");

			// Getting Earned sltf value from Cancellation screen
			actualEarnedSLTFValue1 = cancelPolicyPage.newSLTF.getData();

			// Conversion of String to double/int to calculate sltf
			d_premium = Double.parseDouble(premium);
			i_inspectionFee = Integer.parseInt(inspectionFee);
			i_policyFee = Integer.parseInt(policyFee);
			d_surplusContributionValue = Double.parseDouble(surplusContributionValue);

			// Calculate SLTF=(Premium+Fees)*surplusLinesTaxesPercentage(0.0485)
			d_SLTFValue = (d_premium + (i_inspectionFee + i_policyFee) + d_surplusContributionValue)
					* d_surplusLinesTaxesPercentage;

			// Rounding Off SLTF decimal value to 2 digits and Converting back to String
			SLTFRoundOff = BigDecimal.valueOf(d_SLTFValue);
			SLTFRoundOff = SLTFRoundOff.setScale(2, RoundingMode.HALF_UP);
			calculatedEarnedSLTFValue1 = format.format(SLTFRoundOff);

			// Verify actual and calculated earned SLTF values are equal in Cancellation
			// screen
			if (actualEarnedSLTFValue1 != null && calculatedEarnedSLTFValue1 != null) {
				Assertions.passTest("Cancellation Screen",
						"Actual Earned Surplus Lines Taxes and Fees : " + actualEarnedSLTFValue1);
				Assertions.passTest("Cancellation Screen",
						"Calculated Earned Surplus Lines Taxes and Fees : " + calculatedEarnedSLTFValue1);
				Assertions.verify(actualEarnedSLTFValue1.equalsIgnoreCase(calculatedEarnedSLTFValue1), true,
						"Cancellation Screen",
						"The Earned SLTF value is calculated correctly as per the LA percentage(4.85% )", false, false);
			}

			// Getting Returned Premium from Cancellation screen
			premium = cancelPolicyPage.returnedPremium.getData().replace("$", "").replace("-", "").replace(",", "");

			// Getting Returned Inspection fee from Cancellation screen
			inspectionFee = cancelPolicyPage.returnedInspectionFee.getData().replace("$", "").replace("-", "")
					.replace(",", "");

			// Getting Returned Policy fee from Cancellation screen
			policyFee = cancelPolicyPage.returnedPolicyFee.getData().replace("$", "").replace("-", "").replace(",", "");

			// Getting Returned surplus contribution value
			surplusContributionValue = cancelPolicyPage.surplusContributionVal.formatDynamicPath(3).getData()
					.replace("$", "").replace("-", "").replace(",", "").replace("%", "").replace("%", "");

			// Getting Returned sltf value from Cancellation screen
			actualReturnedSLTFValue1 = cancelPolicyPage.returnedSLTF.getData();

			// Conversion of String to double/int to calculate sltf
			d_premium = Double.parseDouble(premium);
			i_inspectionFee = Integer.parseInt(inspectionFee);
			i_policyFee = Integer.parseInt(policyFee);
			d_surplusContributionValue = Double.parseDouble(surplusContributionValue);

			// Calculate SLTF=(Premium+Fees)*surplusLinesTaxesPercentage(0.0485)
			d_SLTFValue = (d_premium + (i_inspectionFee + i_policyFee) + d_surplusContributionValue)
					* d_surplusLinesTaxesPercentage;

			// Rounding Off SLTF decimal value to 2 digits and Converting back to
			// String
			SLTFRoundOff = BigDecimal.valueOf(d_SLTFValue);
			SLTFRoundOff = SLTFRoundOff.setScale(2, RoundingMode.HALF_UP);

			calculatedReturnedSLTFValue1 = "-" + format.format(SLTFRoundOff).replace(",", "");

			// Verify actual and calculated returned SLTF values are equal in
			// Cancellation
			// screen
			if (actualReturnedSLTFValue1 != null && calculatedEarnedSLTFValue1 != null) {
				Assertions.passTest("Cancellation Screen",
						"Actual Returned Surplus Lines Taxes and Fees : " + actualReturnedSLTFValue1);
				Assertions.passTest("Cancellation Screen",
						"Calculated Returned Surplus Lines Taxes and Fees : " + calculatedReturnedSLTFValue1);
				Assertions.verify(actualReturnedSLTFValue1.equalsIgnoreCase(calculatedReturnedSLTFValue1), true,
						"Cancellation Screen",
						"The Returned SLTF value is calculated correctly as per the LA percentage(4.85% )", false,
						false);
			}

			// Clicking on complete cancellation
			cancelPolicyPage.completeTransactionButton.scrollToElement();
			cancelPolicyPage.completeTransactionButton.waitTillVisibilityOfElement(60);
			cancelPolicyPage.completeTransactionButton.waitTillButtonIsClickable(60);
			cancelPolicyPage.completeTransactionButton.click();
			Assertions.passTest("Cancel Policy Page", "Clicked on Complete Transaction Button");

			// Getting Earned sltf value from Cancellation Successful Page
			actualEarnedSLTFValue2 = cancelPolicySuccessfulPage.newSLTF.getData();

			// Verify actual and calculated earned SLTF values are equal in
			// Cancellation
			// Successful Page
			if (actualEarnedSLTFValue2 != null && calculatedEarnedSLTFValue1 != null) {
				Assertions.passTest("Cancellation Successful page",
						"Actual Earned Surplus Lines Taxes and Fees : " + actualEarnedSLTFValue2);
				Assertions.passTest("Cancellation Successful page",
						"Calculated Earned Surplus Lines Taxes and Fees : " + calculatedEarnedSLTFValue1);
				Assertions.verify(actualEarnedSLTFValue2.equalsIgnoreCase(calculatedEarnedSLTFValue1), true,
						"Cancellation Successful page",
						"The Earned SLTF value is calculated correctly as per the LA percentage(4.85% )", false, false);
			}

			// Getting Returned sltf value from Cancellation Successful page
			actualReturnedSLTFValue2 = cancelPolicySuccessfulPage.returnedSLTF.getData();

			// Verify actual and calculated returned SLTF values are equal in
			// Cancellation
			// Successful page
			if (actualReturnedSLTFValue2 != null && calculatedEarnedSLTFValue1 != null) {
				Assertions.passTest("Cancellation Successful page",
						"Actual Returned Surplus Lines Taxes and Fees : " + actualReturnedSLTFValue2);
				Assertions.passTest("Cancellation Successful page",
						"Calculated Returned Surplus Lines Taxes and Fees : " + calculatedReturnedSLTFValue1);
				Assertions.verify(actualReturnedSLTFValue2.equalsIgnoreCase(calculatedReturnedSLTFValue1), true,
						"Cancellation Successful page",
						"The Returned SLTF value is calculated correctly as per the LA percentage(4.85% )", false,
						false);
			}

			// Clicking close button
			cancelPolicyPage.closeButton.scrollToElement();
			cancelPolicyPage.closeButton.waitTillVisibilityOfElement(60);
			cancelPolicyPage.closeButton.waitTillElementisEnabled(60);
			cancelPolicyPage.closeButton.waitTillButtonIsClickable(60);
			cancelPolicyPage.closeButton.click();
			Assertions.passTest("Cancel Policy Page", "Policy cancelled successfullly");
			policySummaryPage.transHistReason.formatDynamicPath(3).scrollToElement();
			policySummaryPage.transHistReason.formatDynamicPath(3).click();
			String newcalculatedSLTFValue = calculatedSLTFValue;

			// Getting Actual SLTF Value actualSLTFValue =
			actualSLTFValue = policySummaryPage.TaxesAndStateFees.formatDynamicPath(1).getData();

			// Verify actual and calculated SLTF values are equal if
			if (actualSLTFValue != null && newcalculatedSLTFValue != null) {
				Assertions.passTest("Policy Summary Page",
						"Actual Transation Surplus Lines Taxes and Fees : " + actualSLTFValue);
				Assertions.passTest("Policy Summary Page",
						"Calculated Transaction Surplus Lines Taxes and Fees: " + newcalculatedSLTFValue);
				Assertions.verify(actualSLTFValue, newcalculatedSLTFValue.replace("(", "-").replace(")", ""),
						"Policy Summary Page",
						"The Transaction SLTF value is calculated correctly as per the LA percentage(4.85% )", false,
						false);
			}

			// Click on Reinstate policy link
			policySummaryPage.reinstatePolicy.scrollToElement();
			policySummaryPage.reinstatePolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Reinstate Policy link");

			// Click on complete
			reinsatePolicyPage.completeReinstatement.scrollToElement();
			reinsatePolicyPage.completeReinstatement.click();
			Assertions.passTest("Reinstate Policy Page", "Clicked on Complete Reinstate button");
			reinsatePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			reinsatePolicyPage.closeButton.scrollToElement();
			reinsatePolicyPage.closeButton.click();
			policySummaryPage.transHistReason.formatDynamicPath(4).waitTillVisibilityOfElement(60);
			policySummaryPage.transHistReason.formatDynamicPath(4).scrollToElement();
			policySummaryPage.transHistReason.formatDynamicPath(4).click();

			// Getting the Actual STLF value
			actualSLTFValue = policySummaryPage.TaxesAndStateFees.formatDynamicPath(1).getData();

			// Verifying the SLTF is calculated on Reinstatement
			if (actualSLTFValue != null && calculatedSLTFValue != null) {
				Assertions.passTest("Policy Summary Page", "Actual Surplus Lines Taxes and Fees: " + actualSLTFValue);
				Assertions.passTest("Policy Summary Page",
						"Calculated Surplus Lines Taxes and Fees: " + calculatedSLTFValue);
				Assertions.verify(actualSLTFValue.equalsIgnoreCase(calculatedSLTFValue), true, "Policy Summary Page",
						"The SLTF value after Reinstatement is calculated correctly as per the LA percentage(4.85% )",
						false, false);
			}
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Setting Endorsement effective date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.waitTillVisibilityOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillElementisEnabled(60);
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			endorsePolicyPage.changeCoverageOptionsLink.waitTillPresenceOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			testData = data.get(dataValue2);

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

			// Asserting Changes from endorse summary page
			endorsePolicyPage.waitTime(3);
			String additionalCoverageFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(6).getData();
			String additionalCoverageTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(7).getData();
			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(5).checkIfElementIsDisplayed(),
					true, "Endorse Summary Page",
					"Additional Amount of Insurance for Dwelling Coverage " + additionalCoverageFrom + " changed to : "
							+ additionalCoverageTo + " displayed is verified",
					false, false);
			String perilDedFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(11).getData();
			String perilDedTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(12).getData();
			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(10).checkIfElementIsDisplayed(),
					true, "Endorse Summary Page", "All Other Perils Deductible " + perilDedFrom + " changed to : "
							+ perilDedTo + " displayed is verified",
					false, false);
			String identityFraudFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(16).getData();
			String identityFraudTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(17).getData();
			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(15).checkIfElementIsDisplayed(),
					true, "Endorse Summary Page", "Identity Fraud Expense Coverage " + identityFraudFrom
							+ " changed to : " + identityFraudTo + " displayed is verified",
					false, false);
			String increasedSplLimitFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(21).getData();
			String increasedSplLimitTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(22).getData();
			Assertions
					.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(20).checkIfElementIsDisplayed(),
							true, "Endorse Summary Page", "Increased Special Limits Coverage " + increasedSplLimitFrom
									+ " changed to  : " + increasedSplLimitTo + " displayed is verified ",
							false, false);
			String namedStormDedFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(21).getData().replace(" Named Storm Deductible by building", "");
			String namedStormDedTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(22).getData().replace(" Named Storm Deductible by building", "");
			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(22).checkIfElementIsDisplayed(),
					true, "Endorse Summary Page", "Named Storm Deductible " + namedStormDedFrom + " changed to : "
							+ namedStormDedTo + " displayed is verified",
					false, false);
			endorsePolicyPage.nextButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// clicking on ok,continue in Endorse policy page
			if (endorsePolicyPage.pageName.getData().contains("Overrides Required")) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// SLTF value calculated and displayed correctly for Transaction, Annual
			// and Term total columns in Endorse Policy page
			testData = data.get(dataValue1);
			Assertions.passTest("Endorse Policy Page ",
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

			// Comparing actual and expected SLTF value and printing calculated
			// value
			Assertions.verify(
					endorsePolicyPage.premiumDetails.formatDynamicPath(4, 2).getData().replace("(", "").replace(")", "")
							.replace("-", ""),
					format.format(termsurplustaxes).replace("(", "").replace(")", "").replace("-", ""),
					"Endorse Policy Page",
					"Calculated Surplus Lines Taxes and Fees : " + format.format(termsurplustaxes), false, false);

			// Comparing actual and expected SLTF value and printing actual value
			Assertions.verify(
					endorsePolicyPage.premiumDetails.formatDynamicPath(4, 2).getData().replace("(", "").replace(")", "")
							.replace("-", ""),
					format.format(termsurplustaxes).replace("(", "").replace(")", "").replace("-", ""),
					"Endorse Policy Page", "Actual Surplus Lines Taxes and Fees : "
							+ endorsePolicyPage.premiumDetails.formatDynamicPath(4, 2).getData(),
					false, false);

			// Comparing actual and expected total premium and fees value and
			// printing calculated value
			Assertions.verify(
					endorsePolicyPage.premiumDetails.formatDynamicPath(6, 2).getData().replace("(", "").replace(")", "")
							.replace("-", ""),
					format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(termsurplustaxes + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue)))))
							.replace("(", "").replace(")", "").replace("-", ""),
					"Endorse Policy Page",
					"Calculated Total Premium, Taxes and Fees : " + format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(termsurplustaxes + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					false, false);

			// Comparing actual and expected total premium and fees value and
			// printing actual value
			Assertions.verify(
					endorsePolicyPage.premiumDetails.formatDynamicPath(6, 2).getData().replace("(", "").replace(")", "")
							.replace("-", ""),
					format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(termsurplustaxes + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue)))))
							.replace("(", "").replace(")", "").replace("-", ""),
					"Endorse Policy Page", "Actual Total Premium, Taxes and Fees : "
							+ endorsePolicyPage.premiumDetails.formatDynamicPath(6, 2).getData(),
					false, false);
			Assertions.passTest("Endorse Policy Page",
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

			// Comparing actual and expected SLTF value and printing calculated
			// value
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath(4, 3).getData(),
					format.format(annualsurplustaxes).replace("-", ""), "Endorse Policy Page",
					"Calculated Surplus Lines Taxes and Fees : " + format.format(annualsurplustaxes), false, false);

			// Comparing actual and expected SLTF value and printing actual value
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath(4, 3).getData(),
					format.format(annualsurplustaxes).replace("-", ""), "Endorse Policy Page",
					"Actual Surplus Lines Taxes and Fees : "
							+ endorsePolicyPage.premiumDetails.formatDynamicPath(4, 3).getData(),
					false, false);

			// Comparing actual and expected total premium and fees value and
			// printing calculated value
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath(6, 3).getData(), format
					.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(annualsurplustaxes + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue)))))
					.replace("-", ""), "Endorse Policy Page",
					"Calculated Total Premium, Taxes and Fees : " + format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(annualsurplustaxes + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					false, false);

			// Comparing actual and expected total premium and fees value and
			// printing actual value
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath(6, 3).getData(), format
					.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(annualsurplustaxes + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue)))))
					.replace("-", ""), "Endorse Policy Page",
					"Actual Total Premium, Taxes and Fees : "
							+ endorsePolicyPage.premiumDetails.formatDynamicPath(6, 3).getData(),
					false, false);
			Assertions.passTest("Endorse Policy Page ",
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
			double d_totaltermsurplustaxes = totaltermsurplustaxes.doubleValue();
			// totaltermsurplustaxes = totaltermsurplustaxes.setScale(2,
			// RoundingMode.HALF_UP);

			// Comparing actual and expected SLTF value and printing calculated
			// value
			String actualPremiumValueEP = endorsePolicyPage.premiumDetails.formatDynamicPath(4, 4).getData()
					.replace("(", "").replace(")", "").replace("-", "").replace("$", "").replace(",", "");
			double d_actualPremiumValueEP = Double.parseDouble(actualPremiumValueEP);
			if (Precision.round(
					Math.abs(Precision.round(d_actualPremiumValueEP, 2) - Precision.round(d_totaltermsurplustaxes, 2)),
					2) < 0.05) {
				Assertions.passTest("Endorse Policy Page", "Calculated Surplus Lines Taxes and Fees : " + "$"
						+ Precision.round(d_totaltermsurplustaxes, 2));
				Assertions.passTest("Endorse Policy Page",
						"Actual Surplus Lines Taxes and Fees : " + "$" + d_actualPremiumValueEP);
			} else {
				Assertions.verify(d_actualPremiumValueEP, d_totaltermsurplustaxes, "Endorse Policy Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05", false, false);

			}

			// Comparing actual and expected total premium and fees value and
			// printing calculated value
			String actualTotalPremium = endorsePolicyPage.premiumDetails.formatDynamicPath(6, 4).getData()
					.replace("(", "").replace(")", "").replace("-", "").replace("$", "").replace(",", "");
			double d_actualTotalPremium = Double.parseDouble(actualTotalPremium);
			String calcTotaPremium = df.format((Double.parseDouble(premiumAmount))
					+ (Double.parseDouble(totaltermsurplustaxes + "") + (Integer.parseInt(inspectionFee)
							+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue)))));
			double d_calcTotalPremium = Double.parseDouble(calcTotaPremium);
			if (Precision.round(
					Math.abs(Precision.round(d_actualTotalPremium, 2) - Precision.round(d_calcTotalPremium, 2)),
					2) < 0.05) {
				Assertions.passTest("Endorse Policy Page",
						"Calculated Total Premium, Taxes and Fees : " + "$" + Precision.round(d_calcTotalPremium, 2));
				Assertions.passTest("Endorse Policy Page",
						"Actual Total Premium, Taxes and Fees : " + "$" + d_actualTotalPremium);
			} else {
				Assertions.verify(d_actualTotalPremium, d_calcTotalPremium, "Endorse Policy Page",
						"The Difference between actual Total Premium, Taxes and Fees and calculated Total Premium, Taxes and Fees is more than 0.05",
						false, false);

			}

			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Complete");

			// getting transaction,annual and term total premiums from Endorse
			// Summary page
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

			// Comparing actual and expected SLTF value and printing calculated
			// value
			Assertions.verify(
					endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(4, 2).getData().replace("(", "")
							.replace(")", "").replace("-", ""),
					format.format(termsurplustaxesSummary).replace("(", "").replace(")", "").replace("-", ""),
					"Endorse Summary Page",
					"Calculated Surplus Lines Taxes and Fees : " + format.format(termsurplustaxes), false, false);

			// Comparing actual and expected SLTF value and printing actual value
			Assertions.verify(
					endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(4, 2).getData().replace("(", "")
							.replace(")", "").replace("-", ""),
					format.format(termsurplustaxesSummary).replace("(", "").replace(")", "").replace("-", ""),
					"Endorse Summary Page", "Actual Surplus Lines Taxes and Fees : "
							+ endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(4, 2).getData(),
					false, false);

			// Comparing actual and expected total premium and fees value and
			// printing calculated value
			Assertions.verify(
					endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(7, 2).getData().replace("(", "")
							.replace(")", "").replace("-", ""),
					format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(termsurplustaxesSummary + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue)))))
							.replace("(", "").replace(")", "").replace("-", ""),
					"Endorse Summary Page",
					"Calculated Total Premium, Taxes and Fees : " + format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(termsurplustaxesSummary + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					false, false);

			// Comparing actual and expected total premium and fees value and
			// printing actual value
			Assertions.verify(
					endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(7, 2).getData().replace("(", "")
							.replace(")", "").replace("-", ""),
					format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(termsurplustaxesSummary + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue)))))
							.replace("(", "").replace(")", "").replace("-", ""),
					"Endorse Summary Page", "Actual Total Premium, Taxes and Fees : "
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

			// Comparing actual and expected SLTF value and printing calculated
			// value
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

			// Comparing actual and expected total premium and fees value and
			// printing calculated value
			Assertions.verify(endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(7, 3).getData(),
					format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(annualsurplustaxesSummary + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					"Endorse Summary Page",
					"Calculated Total Premium, Taxes and Fees : " + format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(annualsurplustaxes + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					false, false);

			// Comparing actual and expected total premium and fees value and
			// printing actual value
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
			double d_totaltermsurplustaxesSummary = totaltermsurplustaxesSummary.doubleValue();
			// totaltermsurplustaxesSummary = totaltermsurplustaxesSummary.setScale(2,
			// RoundingMode.HALF_UP);

			// Comparing actual and expected SLTF value and printing calculated
			// value
			String actualPremiumValueES = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(4, 4).getData()
					.replace("$", "").replace(",", "");
			double d_actualPremiumValueES = Double.parseDouble(actualPremiumValueES);
			if (Precision.round(Math.abs(
					Precision.round(d_actualPremiumValueES, 2) - Precision.round(d_totaltermsurplustaxesSummary, 2)),
					2) < 0.05) {
				Assertions.passTest("Endorse Policy Page", "Calculated Surplus Lines Taxes and Fees : " + "$"
						+ Precision.round(d_totaltermsurplustaxesSummary, 2));
				Assertions.passTest("Endorse Policy Page",
						"Actual Surplus Lines Taxes and Fees : " + "$" + d_actualPremiumValueES);
			} else {
				Assertions.verify(d_actualPremiumValueES, d_totaltermsurplustaxesSummary, "Endorse Policy Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05", false, false);

			}

			// Comparing actual and expected total premium and fees value and
			// printing calculated value
			String actualTotalPremiumEndorseSummary = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(7, 4)
					.getData().replace("$", "").replace(",", "");
			double d_actualTotalPremiumEndorseSummary = Double.parseDouble(actualTotalPremiumEndorseSummary);
			String calcTotaPremiumEndorseSummary = df.format((Double.parseDouble(premiumAmount))
					+ (Double.parseDouble(totaltermsurplustaxesSummary + "") + (Integer.parseInt(inspectionFee)
							+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue)))));
			double d_calcTotaPremiumEndorseSummary = Double.parseDouble(calcTotaPremiumEndorseSummary);
			if (Precision.round(Math.abs(Precision.round(d_actualTotalPremiumEndorseSummary, 2)
					- Precision.round(d_calcTotaPremiumEndorseSummary, 2)), 2) < 0.05) {
				Assertions.passTest("Endorse Policy Page", "Calculated Total Premium, Taxes and Fees : " + "$"
						+ Precision.round(d_calcTotaPremiumEndorseSummary, 2));
				Assertions.passTest("Endorse Policy Page",
						"Actual Total Premium, Taxes and Fees : " + "$" + d_actualTotalPremiumEndorseSummary);
			} else {
				Assertions.verify(d_actualTotalPremiumEndorseSummary, d_calcTotaPremiumEndorseSummary,
						"Endorse Policy Page",
						"The Difference between actual Total Premium, Taxes and Fees and calculated Total Premium, Taxes and Fees is more than 0.05",
						false, false);

			}
			endorsePolicyPage.scrollToBottomPage();
			endorsePolicyPage.waitTime(3);
			endorsePolicyPage.closeButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC065 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC065 ", "Executed Successfully");
			}
		}
	}

}
