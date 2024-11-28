/** Program Description : Initiating PB endorsement and verifying OOS Transaction and few validations for Virginia State
 *  Author			    : Sowndarya NH
 *  Date of Creation    : 03/01/2022
 **/
package com.icat.epicenter.test.naho.regression.ESBPNB;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ConfirmBindRequestPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.EndorseSummaryDetailsPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.RmsModelResultsPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBVATC001 extends AbstractNAHOTest {

	public PNBVATC001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBPNB/VATC001.xls";
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
		ConfirmBindRequestPage confirmBindRequestPage = new ConfirmBindRequestPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		EndorseSummaryDetailsPage endorseSummaryDetailsPage = new EndorseSummaryDetailsPage();
		RmsModelResultsPage rmsModelResultsPage = new RmsModelResultsPage();

		// Initializing the variables
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		int data_Value4 = 3;
		int data_Value5 = 4;
		int data_Value6 = 5;
		int data_Value7 = 6;
		int data_Value8 = 7;
		String quoteNumber;
		double d_sltfPercentage;
		Map<String, String> testData = data.get(data_Value1);
		String locationNumber = testData.get("LocCount");
		int locationCount = Integer.parseInt(locationNumber);

		String dwellingNumber = testData.get("L" + locationCount + "-DwellingCount");
		int dwellingCount = Integer.parseInt(dwellingNumber);
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

		// Asserting global error message displayed for invalid address
		Assertions.addInfo("Scenario 01", "Verifying the Error Message when entered invalid loaction");
		Assertions.verify(createQuotePage.floodCoverageError.checkIfElementIsDisplayed(), true, "Create A Page",
				"The Error Message " + createQuotePage.floodCoverageError.getData() + " displayed is verified", false,
				false);
		Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

		// Click on previous button
		createQuotePage.previous.scrollToElement();
		createQuotePage.previous.click();
		Assertions.passTest("Create A Quote Page", "Clicked on Previous Button");

		// Click on Edit Dwelling button
		Assertions.verify(accountOverviewPage.editDwelling.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Account Overview Page Loaded successfully", false, false);
		accountOverviewPage.editDwelling.scrollToElement();
		accountOverviewPage.editDwelling.click();

		// Updating Address details
		Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling  Page",
				"Dwelling Page Loaded successfully", false, false);
		testData = data.get(data_Value2);
		dwellingPage.modifyDwellingDetailsNAHO(testData);
		Assertions.passTest("Dwelling Page", "Dwelling Details Entered successfully");

		// Entering Quote Details
		Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Create Quote Page loaded successfully", false, false);
		createQuotePage.enterQuoteDetailsNAHO(testData);
		Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

		// Getting the Quote Number
		testData = data.get(data_Value1);
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Account Overview Page Loaded successfully", false, false);
		int quoteLen = accountOverviewPage.quoteNumber.getData().length();
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
		Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

		// Click on Request bind
		accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

		// Entering details in Underwriting Questions Page
		Assertions.verify(underwritingQuestionsPage.saveButton.checkIfElementIsDisplayed(), true,
				"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
		underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
		Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered successfully");

		// Entering bind details
		Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
				"Request Bind Page loaded successfully", false, false);
		requestBindPage.enterPolicyDetailsNAHO(testData);
		requestBindPage.enterPaymentInformationNAHO(testData);
		requestBindPage.addInspectionContact(testData);
		requestBindPage.addContactInformation(testData);
		Assertions.passTest("Request Bind Page", "Bind Details Entered successfully");

		requestBindPage.waitTime(2); // Added waittime as not clicking on submit
										// button in headless
		if (requestBindPage.submit.checkIfElementIsPresent() && requestBindPage.submit.checkIfElementIsDisplayed()) {
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();
		}

		if (confirmBindRequestPage.requestBind.checkIfElementIsPresent()) {
			Assertions.passTest("Confirm Bind Request Page", "Confirm Bind Request page loaded successfully");
			confirmBindRequestPage.confirmBind();
			Assertions.passTest("Confirm Bind Request Page", "Clicked on Request Bind");
		}

		// Assert the error message for payment information
		Assertions.addInfo("Scenario 02",
				"Verifying the Error Message when Mortgagee Payment is selected and No Additional Interest Added");
		Assertions.verify(requestBindPage.mortgageeWarningMessage.checkIfElementIsDisplayed(), true,
				"Request Bind Page",
				"The Error Message " + requestBindPage.mortgageeWarningMessage.getData() + " displayed is verified",
				false, false);
		Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

		// Entering AI Details
		testData = data.get(data_Value2);
		requestBindPage.addAdditionalInterest(testData);

		requestBindPage.waitTime(2); // Added waittime as not clicking on submit
										// button in headless
		if (requestBindPage.submit.checkIfElementIsPresent() && requestBindPage.submit.checkIfElementIsDisplayed()) {
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();
		}

		if (confirmBindRequestPage.requestBind.checkIfElementIsPresent()) {
			Assertions.passTest("Confirm Bind Request Page", "Confirm Bind Request page loaded successfully");
			confirmBindRequestPage.confirmBind();
			Assertions.passTest("Confirm Bind Request Page", "Clicked on Request Bind");
		}

		Assertions.addInfo("Scenario 02",
				"Verifying the Error Message when Mortgagee AI and Additional Mortgagee Rank is Added without adding Second Mortgagee Rank");
		Assertions.verify(requestBindPage.additionalMortgageeError.checkIfElementIsDisplayed(), true,
				"Request Bind Page",
				"The Error Message " + requestBindPage.additionalMortgageeError.getData() + " displayed is verified",
				false, false);
		Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

		// Change the Rank as Second Mortgagee
		testData = data.get(data_Value3);
		requestBindPage.aIRankArrow1.scrollToElement();
		requestBindPage.aIRankArrow1.click();
		requestBindPage.aIRankoption1.formatDynamicPath(testData.get("1-AIRank")).waitTillVisibilityOfElement(60);
		requestBindPage.aIRankoption1.formatDynamicPath(testData.get("1-AIRank")).scrollToElement();
		requestBindPage.aIRankoption1.formatDynamicPath(testData.get("1-AIRank")).click();
		Assertions.passTest("Request Bind Page",
				"Additional Interest Rank Changed to : " + requestBindPage.aIRankData.getData());

		requestBindPage.waitTime(2); // Added waittime as not clicking on submit
										// button in headless
		if (requestBindPage.submit.checkIfElementIsPresent() && requestBindPage.submit.checkIfElementIsDisplayed()) {
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();
		}

		if (confirmBindRequestPage.requestBind.checkIfElementIsPresent()) {
			Assertions.passTest("Confirm Bind Request Page", "Confirm Bind Request page loaded successfully");
			confirmBindRequestPage.confirmBind();
			Assertions.passTest("Confirm Bind Request Page", "Clicked on Request Bind");
		}

		Assertions.addInfo("Scenario 03",
				"Verifying the Error Message when Mortgagee AI and Second Mortgagee Rank is Added without adding First Mortgagee Rank");
		Assertions.verify(requestBindPage.additionalMortgageeError.checkIfElementIsDisplayed(), true,
				"Request Bind Page",
				"The Error Message " + requestBindPage.additionalMortgageeError.getData() + " displayed is verified",
				false, false);
		Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

		// Change the Rank as First Mortgagee
		testData = data.get(data_Value4);
		requestBindPage.aIRankArrow1.scrollToElement();
		requestBindPage.aIRankArrow1.click();
		requestBindPage.aIRankoption1.formatDynamicPath(testData.get("1-AIRank")).waitTillVisibilityOfElement(60);
		requestBindPage.aIRankoption1.formatDynamicPath(testData.get("1-AIRank")).scrollToElement();
		requestBindPage.aIRankoption1.formatDynamicPath(testData.get("1-AIRank")).click();
		Assertions.passTest("Request Bind Page",
				"Additional Interest Rank Changed to : " + requestBindPage.aIRankData.getData());

		requestBindPage.waitTime(2); // Added waittime as not clicking on submit
										// button in headless
		if (requestBindPage.submit.checkIfElementIsPresent() && requestBindPage.submit.checkIfElementIsDisplayed()) {
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();
		}

		if (confirmBindRequestPage.requestBind.checkIfElementIsPresent()) {
			Assertions.passTest("Confirm Bind Request Page", "Confirm Bind Request page loaded successfully");
			confirmBindRequestPage.confirmBind();
			Assertions.passTest("Confirm Bind Request Page", "Clicked on Request Bind");
		}

		// Clicking on homepage button
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		homePage.searchReferral(quoteNumber);
		Assertions.passTest("Home Page", "Quote for referral is searched successfully");

		// Approve Referral
		Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
				"Referral page loaded successfully", false, false);
		referralPage.clickOnApprove();
		Assertions.passTest("Referral Page", "Referral request approved successfully");
		Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
				"Request Bind page loaded successfully", false, false);
		requestBindPage.approveRequestNAHO(testData);
		Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

		// Validating the premium amount
		Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
				"Policy Summary Page loaded successfully. PolicyNumber is " + policySummaryPage.policyNumber.getData(),
				false, false);

		// Click on Endorse PB link
		policySummaryPage.endorsePB.scrollToElement();
		policySummaryPage.endorsePB.click();
		Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

		// Entering Endorsement effective date
		testData = data.get(data_Value2);
		Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
				"Endorse Policy Page Loaded successfully", false, false);
		endorsePolicyPage.endorsementEffDate.scrollToElement();
		endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
		endorsePolicyPage.endorsementEffDate.tab();
		Assertions.passTest("Endorse Policy Page", "Entered the Endorsement Efffective Date");

		// Click on Fee only Endorsement
		endorsePolicyPage.feeOnlyEndorsement.scrollToElement();
		endorsePolicyPage.feeOnlyEndorsement.click();
		Assertions.passTest("Endorse Policy Page", "Clicked on Fee only Endorsement link");

		Assertions.verify(overridePremiumAndFeesPage.saveAndClose.checkIfElementIsDisplayed(), true,
				"Override Premium and Fees Page", "Override Premium and Fees Page Loaded successfully", false, false);
		overridePremiumAndFeesPage.enterOverrideFeesDetails(testData);

		// Verifying the SLTF values
		Assertions.addInfo("Scenario 04",
				"Verifying Whether The Value Surplus Taxes & Fees under Transaction Column = 2.275 % of [Total Premium + Inspection Fee + Policy Fee of Transaction Column]");

		// getting the actual values
		String premium = endorsePolicyPage.premiumDetails.formatDynamicPath("1", "2").getData().replace("$", "");
		String inspFee = endorsePolicyPage.premiumDetails.formatDynamicPath("2", "2").getData().replace("$", "");
		String policyFee = endorsePolicyPage.premiumDetails.formatDynamicPath("3", "2").getData().replace("$", "");
		String surplusContributionValue = endorsePolicyPage.surplusContributionValueNAHO.formatDynamicPath("1")
				.getData().replace("$", "").replace("%", "");

		Assertions.passTest("Premium", premium);
		Assertions.passTest("inspection Fee", inspFee);
		Assertions.passTest("Policy Fee", policyFee);
		Assertions.passTest("Surplus Contribution Value", surplusContributionValue);

		// Convert the actual values to double
		double d_premium = Double.parseDouble(premium);
		double d_inspFee = Double.parseDouble(inspFee);
		double d_policyFee = Double.parseDouble(policyFee);
		double d_surplusContributionValue = Double.parseDouble(surplusContributionValue);
		d_sltfPercentage = Double.parseDouble(testData.get("SLTFPercentage"));

		// Calculate the SLTF value
		double calcSltf = (d_premium + d_inspFee + d_policyFee + d_surplusContributionValue) * d_sltfPercentage;

		// Getting Actual SLTF value
		String actualSltf = endorsePolicyPage.premiumDetails.formatDynamicPath("4", "2").getData().replace("$", "");
		double d_actualSltf = Double.parseDouble(actualSltf);

		// Comparing Actual and Calculated Transaction column SLTF values
		if (Precision.round(Math.abs(Precision.round(d_actualSltf, 2) - Precision.round(calcSltf, 2)), 2) < 0.05) {
			Assertions.passTest("Endorse Policy Page",
					"Calculated Surplus Lines Taxes and Fees for Transaction Column : " + "$"
							+ Precision.round(calcSltf, 2));
			Assertions.passTest("Endorse Policy Page",
					"Actual Surplus Lines Taxes and Fees for Transaction Column : " + "$" + d_actualSltf);
		} else {
			Assertions.passTest("Endorse Policy Page",
					"The Difference between actual SLTF and calculated SLTF is more than 0.05");
		}
		Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

		Assertions.addInfo("Scenario 05",
				"Verifying whether The Value of Surplus Taxes & Fees under Annual Column= 2.275% of [Total Premium + Inspection Fee + Policy Fee of Annual Column]");

		// getting the actual values
		String annualpremium = endorsePolicyPage.premiumDetails.formatDynamicPath("1", "3").getData().replace("$", "")
				.replace(",", "");
		String annualinspFee = endorsePolicyPage.premiumDetails.formatDynamicPath("2", "3").getData().replace("$", "");
		String annualpolicyFee = endorsePolicyPage.premiumDetails.formatDynamicPath("3", "3").getData().replace("$",
				"");
		String annualSCValue = endorsePolicyPage.surplusContributionValueNAHO.formatDynamicPath("2").getData()
				.replace("$", "").replace("%", "");

		// Convert the actual values to double
		double d_annualpremium = Double.parseDouble(annualpremium);
		double d_annualinspFee = Double.parseDouble(annualinspFee);
		double d_annualpolicyFee = Double.parseDouble(annualpolicyFee);
		double d_annualSCValue = Double.parseDouble(annualSCValue);

		// Calculate the SLTF value
		double calcAnnualSltf = (d_annualpremium + d_annualinspFee + d_annualpolicyFee + d_annualSCValue)
				* d_sltfPercentage;

		// Getting Actual SLTF value
		String actualAnnualSltf = endorsePolicyPage.premiumDetails.formatDynamicPath("4", "3").getData().replace("$",
				"");
		double d_actualAnnualSltf = Double.parseDouble(actualAnnualSltf);

		// Comparing Actual and Calculated Transaction column SLTF values
		if (Precision.round(Math.abs(Precision.round(d_actualAnnualSltf, 2) - Precision.round(calcAnnualSltf, 2)),
				2) < 0.05) {
			Assertions.passTest("Endorse Policy Page", "Calculated Surplus Lines Taxes and Fees for Annual Column : "
					+ "$" + Precision.round(calcAnnualSltf, 2));
			Assertions.passTest("Endorse Policy Page",
					"Actual Surplus Lines Taxes and Fees for Annual Column : " + "$" + d_actualAnnualSltf);
		} else {
			Assertions.verify(d_actualAnnualSltf, calcAnnualSltf, "Endorse Policy Page",
					"The Difference between actual Annual SLTF value and calculated Annual SLTF value is more than 0.05",
					false, false);
		}
		Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

		Assertions.addInfo("Scenario 06",
				"Verifying whether The Value of Surplus Taxes & Fees under Term Total Column= 2.275% of [Total Premium + Inspection Fee + Policy Fee of Term Total Column]");
		// getting the actual values
		String termpremium = endorsePolicyPage.premiumDetails.formatDynamicPath("1", "4").getData().replace("$", "")
				.replace(",", "");
		String terminspFee = endorsePolicyPage.premiumDetails.formatDynamicPath("2", "4").getData().replace("$", "");
		String termpolicyFee = endorsePolicyPage.premiumDetails.formatDynamicPath("3", "4").getData().replace("$", "");
		String termSCValue = endorsePolicyPage.surplusContributionValueNAHO.formatDynamicPath("3").getData()
				.replace("$", "").replace("%", "");

		// Convert the actual values to double
		double d_termpremium = Double.parseDouble(termpremium);
		double d_terminspFee = Double.parseDouble(terminspFee);
		double d_termpolicyFee = Double.parseDouble(termpolicyFee);
		double d_termSCValue = Double.parseDouble(termSCValue);

		// Calculate the SLTF value
		double calctermSltf = (d_termpremium + d_terminspFee + d_termpolicyFee + d_termSCValue) * d_sltfPercentage;

		// Getting Actual SLTF value
		String actualtermSltf = endorsePolicyPage.premiumDetails.formatDynamicPath("4", "4").getData().replace("$", "");
		double d_actualtermSltf = Double.parseDouble(actualtermSltf);

		// Comparing Actual and Calculated Transaction column SLTF values
		if (Precision.round(Math.abs(Precision.round(d_actualtermSltf, 2) - Precision.round(calctermSltf, 2)),
				2) < 0.05) {
			Assertions.passTest("Endorse Policy Page",
					"Calculated Surplus Lines Taxes and Fees for Term Total Column : " + "$"
							+ Precision.round(calctermSltf, 2));
			Assertions.passTest("Endorse Policy Page",
					"Actual Surplus Lines Taxes and Fees for Term Total Column : " + "$" + d_actualtermSltf);
		} else {
			Assertions.verify(d_actualtermSltf, calctermSltf, "Endorse Policy Page",
					"The Difference between actual Term SLTF value and calculated Term SLTF value is more than 0.05",
					false, false);
		}
		Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

		// Verifying the Prorata factor
		// Prorata Factor = 1 - [The Difference in Number of Days between
		// Endorsement
		// Effective Date and Policy Effective Date]/365
		// i.e. daysDifference = 2 / 365 => 0.005
		Assertions.addInfo("Scenario 07",
				"Verifying the Prorata Factor and Comparing Actual and Calulated Prorata Factors");
		double daysDifference = 0.005;
		double calcprorataFactor = 1 - daysDifference;

		// getting actual Prorata value
		String actualProrata = endorsePolicyPage.prorataFactor.getData();
		double d_actualProrata = Double.parseDouble(actualProrata);
		if (Precision.round(Math.abs(Precision.round(d_actualProrata, 3) - Precision.round(calcprorataFactor, 3)),
				3) < 0.05) {
			Assertions.passTest("Endorse Policy Page", "Calculated Prorata Factor : " + calcprorataFactor);
			Assertions.passTest("Endorse Policy Page", "Actual Prorata Factor : " + d_actualProrata);
		} else {
			Assertions.verify(d_actualProrata, calcprorataFactor, "Endorse Policy Page",
					"The Difference between actual Prorata Factor and calculated Prorata Factor is more than 0.05",
					false, false);
		}
		Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

		// Asserting the Modeling data like GC17, ELR Premium, peril AAl and Peril ERL
		// Labels and it's values for Alternative quote
		// Assert the presence of View Model Results link on Endorse Policy page
		endorsePolicyPage = new EndorsePolicyPage();
		endorsePolicyPage.viewModelResultsButton.scrollToElement();
		endorsePolicyPage.viewModelResultsButton.click();
		Assertions.passTest("Endorse Policy Page", "Clicked on view model result link");

		// Asserting the Modeling data like GC17, ELR Premium, peril AAl and Peril ERL
		// Labels and it's values
		// Verifying the GC17 Label
		rmsModelResultsPage.gc17Label.waitTillPresenceOfElement(60);
		rmsModelResultsPage.gc17Label.waitTillVisibilityOfElement(60);
		rmsModelResultsPage.gc17Label.scrollToElement();
		Assertions.addInfo("Scenario 08",
				" Guy Carpenter Scenario : Asserting the Modeling data like GC17, ELR Premium, peril AAl and Peril ERL Labels and it's values for Alternative quote");
		Assertions.verify(rmsModelResultsPage.gc17Label.getData().equals("GC17"), true,
				"View Model Results Page",
				"Label displayed on View Model Results Page is : " + rmsModelResultsPage.gc17Label.getData(), false,
				false);

		// Verifying the AAL label and it's value
		Assertions.verify(
				rmsModelResultsPage.guyCarpenterAALLabel.checkIfElementIsDisplayed()
						&& rmsModelResultsPage.guyCarpenterAALLabel.checkIfElementIsDisplayed()
						&& rmsModelResultsPage.guyCarpenterAAL.checkIfElementIsDisplayed(),
				true, "View Model Results Page",
				"Guy Carpenter AAl Label is displayed as : " + rmsModelResultsPage.guyCarpenterAALLabel.getData()
						+ " and it's value displayed on View Model Results Page is: "
						+ rmsModelResultsPage.guyCarpenterAAL.getData(),
				false, false);

		// Verifying the ELR label and it's value
		Assertions.verify(
				rmsModelResultsPage.guyCarpenterELRLabel.checkIfElementIsDisplayed()
						&& rmsModelResultsPage.guyCarpenterELRLabel.checkIfElementIsPresent()
						&& rmsModelResultsPage.rmsModelResultValues
								.formatDynamicPath("Peril ELR").checkIfElementIsDisplayed(),
				true, "View Model Results Page",
				"Guy Carpenter ELR Label is dasplayed as :" + rmsModelResultsPage.guyCarpenterELRLabel.getData()
						+ "and it's value displayed on View Model Results Page is: "
						+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril ELR").getData(),
				false, false);

		// Verifying the ELR Premium Label and it's value
		Assertions.verify(
				rmsModelResultsPage.elrPremiumLabel.checkIfElementIsDisplayed()
						&& rmsModelResultsPage.elrPremiumLabel.checkIfElementIsPresent(),
				true, "View Model Results Page",
				"Guy Carpenter ELR Premium Label is diaplayed as " + rmsModelResultsPage.elrPremiumLabel.getData(),
				false, false);

		// Verifying the TIV label and It's value
		Assertions.verify(
				rmsModelResultsPage.tivValue.checkIfElementIsDisplayed()
						&& rmsModelResultsPage.tivValue.checkIfElementIsPresent(),
				true, "View Model Results Page",
				"TIV value displayed on View Model Results Page is: " + rmsModelResultsPage.tivValue.getData(), false,
				false);

		// Calculating GC ELR value
		// Verify the calculation of ELR: GC Peril ELR = GC Peril AAL amount/ELR premium
		String ELRPremium = rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("ELR Premium").getData()
				.replace("$", "");
		String PerilAAL = rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril AAL").getData().replace("$",
				"");
		double PerilELR = Precision.round((Double.parseDouble(PerilAAL) / Double.parseDouble(ELRPremium)) * 100, 1);
		String actualPerilELRStr = rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril ELR").getData()
				.replace("%", "");
		double actualPerilELR = Double.parseDouble(actualPerilELRStr);

		Assertions.verify(PerilELR == actualPerilELR, true, "View Model Results Page",
				"The Actual and Calculated Peril ELR are matching", false, false);

		if (PerilELR == actualPerilELR) {
			Assertions.passTest("View Model Results Page", "Calculated Peril ELR: " + PerilELR + "%");
			Assertions.passTest("View Model Results Page", "Actual Peril ELR: " + actualPerilELR + "%");
		} else {
			Assertions.verify(PerilELR, actualPerilELR, "View Model Results Page",
					"The Actual and Calculated values are not matching", false, false);
		}

		rmsModelResultsPage.closeButton.waitTillVisibilityOfElement(60);
		rmsModelResultsPage.closeButton.click();

		// Clicking on close button on View Model Results Page
		rmsModelResultsPage.closeButton.click();
		Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

		// Click on Complete
		endorsePolicyPage.completeButton.scrollToElement();
		endorsePolicyPage.completeButton.click();
		Assertions.passTest("Endorse Policy Page", "Clicked on Complete Button");

		// click on close
		endorsePolicyPage.scrollToBottomPage();
		endorsePolicyPage.closeButton.waitTillPresenceOfElement(60);
		endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
		endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
		endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
		endorsePolicyPage.closeButton.scrollToElement();
		endorsePolicyPage.closeButton.click();
		Assertions.passTest("Endorse Policy Page", "Clicked on Close Button");

		// Click on Endorse PB link
		policySummaryPage.endorsePB.scrollToElement();
		policySummaryPage.endorsePB.click();
		Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

		// Entering Endorsement effective date
		testData = data.get(data_Value3);
		Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
				"Endorse Policy Page Loaded successfully", false, false);
		endorsePolicyPage.endorsementEffDate.scrollToElement();
		endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
		endorsePolicyPage.endorsementEffDate.tab();
		Assertions.passTest("Endorse Policy Page", "Entered the Endorsement Efffective Date");

		// Asseting the OOS message
		Assertions.addInfo("Scenario 09", "Verifying the Presence of Out Of Sequence Message");
		Assertions.verify(endorsePolicyPage.conflictTxn.checkIfElementIsDisplayed(), true,
				"Out Of Sequesnce Transaction",
				"The Message " + endorsePolicyPage.conflictTxn.getData() + " displayed is verified", false, false);

		Assertions.verify(endorsePolicyPage.oosMsg.checkIfElementIsDisplayed(), true, "Out Of Sequesnce Transaction",
				"The Message " + endorsePolicyPage.oosMsg.getData() + " displayed is verified", false, false);
		Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

		// click on continue
		endorsePolicyPage.continueButton.scrollToElement();
		endorsePolicyPage.continueButton.click();

		// click on edit location or building details link
		endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
		endorsePolicyPage.editLocOrBldgInformationLink.click();
		Assertions.passTest("Endorse Policy Page", "Clicked on Edit Location or Building Details link");

		// Entering dwelling details
		Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
				"Dwelling Page loaded Successfully", false, false);
		Assertions.addInfo("Dwelling Page",
				"Dwelling Construction Type original Value : " + dwellingPage.constructionTypeData.getData());
		dwellingPage.constructionTypeArrow.waitTillVisibilityOfElement(60);
		dwellingPage.constructionTypeArrow.scrollToElement();
		dwellingPage.constructionTypeArrow.click();
		dwellingPage.constructionTypeOption.formatDynamicPath(testData.get("L1D1-DwellingConstType"))
				.waitTillVisibilityOfElement(60);
		dwellingPage.constructionTypeOption.formatDynamicPath(testData.get("L1D1-DwellingConstType")).scrollToElement();
		dwellingPage.constructionTypeOption.formatDynamicPath(testData.get("L1D1-DwellingConstType")).click();
		Assertions.passTest("Dwelling Page",
				"Dwelling Construction Type Latest Value : " + dwellingPage.constructionTypeData.getData());

		if (dwellingPage.noOfUnits.checkIfElementIsPresent() && dwellingPage.noOfUnits.checkIfElementIsDisplayed()) {
			Assertions.addInfo("Dwelling Page", "Number of Units original Value : " + dwellingPage.noOfUnits.getData());
			if (!testData.get("L1D1-DwellingUnits").equals("")) {
				dwellingPage.noOfUnits.scrollToElement();
				dwellingPage.noOfUnits.setData(testData.get("L1D1-DwellingUnits"));
				Assertions.passTest("Dwelling Page",
						"Number of Units Latest Value : " + dwellingPage.noOfUnits.getData());
			}
		}

		if (dwellingPage.numOfFloors.checkIfElementIsPresent()
				&& dwellingPage.numOfFloors.checkIfElementIsDisplayed()) {
			Assertions.addInfo("Dwelling Page",
					"Number of Stories original Value : " + dwellingPage.numOfFloors.getData());
			if (!testData.get("L1D1-DwellingFloors").equals("")) {
				dwellingPage.numOfFloors.setData(testData.get("L1D1-DwellingFloors"));
				Assertions.passTest("Dwelling Page",
						"Number of Units Latest Value : " + dwellingPage.numOfFloors.getData());
			}
		}

		if (dwellingPage.totalSquareFootage.checkIfElementIsPresent()
				&& dwellingPage.totalSquareFootage.checkIfElementIsDisplayed()) {
			Assertions.addInfo("Dwelling Page",
					"Total Square Footage original Value : " + dwellingPage.totalSquareFootage.getData());
			if (!testData.get("L1D1-DwellingSqFoot").equals("")) {
				dwellingPage.totalSquareFootage.setData(testData.get("L1D1-DwellingSqFoot"));
				Assertions.passTest("Dwelling Page",
						"Total Square Footage Latest Value : " + dwellingPage.totalSquareFootage.getData());
			}
		}

		if (!testData.get("L1D1-DwellingYearBuilt").equals("")) {
			Assertions.addInfo("Dwelling Page", "Year Built original Value : " + dwellingPage.yearBuilt.getData());
			dwellingPage.yearBuilt.scrollToElement();
			dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));
			dwellingPage.yearBuilt.tab();
			Assertions.passTest("Dwelling Page", "Year Built Latest Value : " + dwellingPage.yearBuilt.getData());
		}

		// enter Protecyion discounts details
		dwellingPage.waitTime(3); // Added to click on the link
		dwellingPage.protectionDiscounts.waitTillVisibilityOfElement(60);
		dwellingPage.protectionDiscounts.scrollToElement();
		dwellingPage.protectionDiscounts.click();

		if (dwellingPage.windMitigationArrow.checkIfElementIsPresent()
				&& dwellingPage.windMitigationArrow.checkIfElementIsEnabled()) {
			dwellingPage.waitTime(3); // added waittime to click on the dropdown
			Assertions.addInfo("Dwelling Page",
					"Wind Mitigation original Value : " + dwellingPage.WindMitigationData.getData());
			if (!testData.get("L1D1-DwellingWindMitigation").equals("")) {
				dwellingPage.windMitigationArrow.scrollToElement();
				dwellingPage.windMitigationArrow.click();
				dwellingPage.WindMitigationOption.formatDynamicPath(testData.get("L1D1-DwellingWindMitigation"))
						.waitTillVisibilityOfElement(60);
				dwellingPage.WindMitigationOption.formatDynamicPath(testData.get("L1D1-DwellingWindMitigation"))
						.waitTillButtonIsClickable(60);
				dwellingPage.WindMitigationOption.formatDynamicPath(testData.get("L1D1-DwellingWindMitigation"))
						.scrollToElement();
				dwellingPage.WindMitigationOption.formatDynamicPath(testData.get("L1D1-DwellingWindMitigation"))
						.click();
				Assertions.passTest("Dwelling Page",
						"Wind Mitigation Latest Value : " + dwellingPage.WindMitigationData.getData());
			}
		}
		if (dwellingPage.centralStationAlarmArrow.checkIfElementIsPresent()
				&& dwellingPage.centralStationAlarmArrow.checkIfElementIsEnabled()) {
			if (!testData.get("L1D1-CentralStationAlarm").equals("")) {
				Assertions.addInfo("Dwelling Page",
						"Central Station Alarm original Value : " + dwellingPage.centralStationAlarmData.getData());
				dwellingPage.centralStationAlarmArrow.scrollToElement();
				dwellingPage.centralStationAlarmArrow.click();
				dwellingPage.centralStationAlarmOption.formatDynamicPath(testData.get("L1D1-CentralStationAlarm"))
						.waitTillVisibilityOfElement(60);
				dwellingPage.centralStationAlarmOption.formatDynamicPath(testData.get("L1D1-CentralStationAlarm"))
						.scrollToElement();
				dwellingPage.centralStationAlarmOption.formatDynamicPath(testData.get("L1D1-CentralStationAlarm"))
						.click();
				Assertions.passTest("Dwelling Page",
						"Central Station Alarm Latest Value : " + dwellingPage.centralStationAlarmData.getData());
			}
		}

		// click on continue
		dwellingPage.continueButton.scrollToElement();
		dwellingPage.continueButton.click();

		Assertions.verify(createQuotePage.continueEndorsementBtn.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Create Quote Page Loaded successfully", false, false);
		Assertions.addInfo("Create Quote Page",
				"The Original Coverage A Value : $" + createQuotePage.coverageADwelling.getData());
		createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
		Assertions.passTest("Create Quote Page",
				"The Latest Coverage A Value : $" + createQuotePage.coverageADwelling.getData());

		if (!testData.get("NamedStormValue").equals("")) {
			if (!createQuotePage.namedStormArrow_NAHO.getAttrributeValue("class").contains("disabled")) {
				Assertions.addInfo("Create Quote Page",
						"Named Strom Original Value : " + createQuotePage.namedStormData.getData());
				createQuotePage.namedStormArrow_NAHO.scrollToElement();
				createQuotePage.namedStormArrow_NAHO.click();
				createQuotePage.namedStormDeductibleOption.formatDynamicPath(testData.get("NamedStormValue"))
						.waitTillVisibilityOfElement(60);
				createQuotePage.namedStormDeductibleOption.formatDynamicPath(testData.get("NamedStormValue"))
						.scrollToElement();
				createQuotePage.namedStormDeductibleOption.formatDynamicPath(testData.get("NamedStormValue")).click();
				Assertions.passTest("Create Quote Page",
						"Named Strom Latest Value : " + createQuotePage.namedStormData.getData());
			}
		}

		if (!testData.get("AOPDeductibleValue").equals("")) {
			if (!createQuotePage.aopDeductibleArrow.getAttrributeValue("class").contains("disabled")) {
				Assertions.addInfo("Create Quote Page",
						"AOP Deductible Original Value : " + createQuotePage.aopDeductibleData.getData());
				createQuotePage.aopDeductibleArrow.waitTillPresenceOfElement(60);
				createQuotePage.aopDeductibleArrow.waitTillVisibilityOfElement(60);
				createQuotePage.aopDeductibleArrow.scrollToElement();
				createQuotePage.aopDeductibleArrow.click();
				createQuotePage.aopDeductibleOption.formatDynamicPath(testData.get("AOPDeductibleValue"))
						.scrollToElement();
				createQuotePage.aopDeductibleOption.formatDynamicPath(testData.get("AOPDeductibleValue")).click();
				Assertions.passTest("Create Quote Page",
						"AOP Deductible Latest Value : " + createQuotePage.aopDeductibleData.getData());
			}
		}
		if (!testData.get("AOWHDeductibleValue").equals("")) {
			if (!createQuotePage.aowhDeductibleArrow.getAttrributeValue("class").contains("disabled")) {
				Assertions.addInfo("Create Quote Page",
						"AOWH Deductible Original Value : " + createQuotePage.aowhDeductibleData.getData());
				createQuotePage.aowhDeductibleArrow.scrollToElement();
				createQuotePage.aowhDeductibleArrow.click();
				createQuotePage.aowhDeductibleOptionNAHO.formatDynamicPath(testData.get("AOWHDeductibleValue"))
						.scrollToElement();
				createQuotePage.aowhDeductibleOptionNAHO.formatDynamicPath(testData.get("AOWHDeductibleValue")).click();
				Assertions.passTest("Create Quote Page",
						"AOWH Deductible Latest Value : " + createQuotePage.aowhDeductibleData.getData());
			}
		}

		createQuotePage.enterOptionalcoverageDetailsNAHO(testData);
		testData = data.get(data_Value1);
		Assertions.addInfo("Create Quote Page",
				"Additional Dwelling Coverage Original Value : " + testData.get("AdditionalDwellingCoverage"));
		Assertions.passTest("Create Quote Page",
				"Additional Dwelling Coverage Latest Value : " + createQuotePage.dwellingCovData.getData());
		Assertions.addInfo("Create Quote Page", "Loss Assessment Original Value : " + testData.get("LossAssessment"));
		Assertions.passTest("Create Quote Page",
				"Loss Assessment Latest Value : " + createQuotePage.lossAssessmentData.getData());
		Assertions.addInfo("Create Quote Page", "Ordinance or Law Original Value : " + testData.get("OrdinanceOrLaw"));
		Assertions.passTest("Create Quote Page",
				"Ordinance or Law  Latest Value : " + createQuotePage.ordinanceLawData.getData());

		// click on Continue
		createQuotePage.continueEndorsementBtn.scrollToElement();
		createQuotePage.continueEndorsementBtn.click();

		// Click on Continue
		if (createQuotePage.continueButton.checkIfElementIsPresent()
				&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}

		// click on edit prior loss link
		endorsePolicyPage.editPriorLoss.scrollToElement();
		endorsePolicyPage.editPriorLoss.click();
		Assertions.passTest("Endorse Policy Page", "Clicked on Edit Prior Loss link");

		// Delete the 2nd prior loss
		Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
				"Prior Loss Page loaded successfully", false, false);
		priorLossesPage.deleteSymbol.formatDynamicPath(testData.get("PriorLossType1")).scrollToElement();
		priorLossesPage.deleteSymbol.formatDynamicPath(testData.get("PriorLossType1")).click();
		Assertions.passTest("Prior Loss Page", "Deleted the Second Prior loss successfully");

		// change the existing prior loss
		testData = data.get(data_Value2);
		priorLossesPage.typeOfLossArrow.click();
		Assertions.addInfo("Prior Loss Page",
				"Type of Loss original Value : " + priorLossesPage.priorLossData.formatDynamicPath(0).getData());
		priorLossesPage.typeOfLossOption.formatDynamicPath(0, testData.get("PriorLossType3")).scrollToElement();
		priorLossesPage.typeOfLossOption.formatDynamicPath(0, testData.get("PriorLossType3")).click();
		Assertions.passTest("Prior Loss Page",
				"Type of Loss Latest Value : " + priorLossesPage.priorLossData.formatDynamicPath(0).getData());
		Assertions.addInfo("Prior Loss Page",
				"Gross Loss Amount original Value : $" + priorLossesPage.grossLossAmount.getData());
		priorLossesPage.grossLossAmount.setData(testData.get("PriorLossAmount3"));
		Assertions.passTest("Prior Loss Page",
				"Gross Loss Amount Latest Value : $" + priorLossesPage.grossLossAmount.getData());

		// click on continue button
		priorLossesPage.continueButton.scrollToElement();
		priorLossesPage.continueButton.click();
		Assertions.passTest("Prior Loss Page", "Clicked on Continue Button");

		// click on next button
		endorsePolicyPage.nextButton.scrollToElement();
		endorsePolicyPage.nextButton.click();
		Assertions.passTest("Endorse Policy Page", "Clicked on Next Button");

		// Verifying the SLTF values
		Assertions.addInfo("Scenario 10",
				"Verifying Whether The Value Surplus Taxes & Fees under Transaction Column = 2.275 % of [Total Premium + Inspection Fee + Policy Fee of Transaction Column]");

		// Click on Continue
		if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
				&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
			endorsePolicyPage.continueButton.scrollToElement();
			endorsePolicyPage.continueButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Continue Button");
		}

		// getting the actual values
		String summarypremium = endorsePolicyPage.premiumDetails.formatDynamicPath("1", "2").getData().replace("$", "")
				.replace("-", "").replace(",", "");
		String summaryinspFee = endorsePolicyPage.premiumDetails.formatDynamicPath("2", "2").getData().replace("$", "");
		String summarypolicyFee = endorsePolicyPage.premiumDetails.formatDynamicPath("3", "2").getData().replace("$",
				"");
		String summarySCValue = endorsePolicyPage.premiumDetails.formatDynamicPath("5", "2").getData().replace("$", "")
				.replace("-", "");

		// Convert the actual values to double
		double d_summarypremium = Double.parseDouble(summarypremium);
		double d_summaryinspFee = Double.parseDouble(summaryinspFee);
		double d_summarypolicyFee = Double.parseDouble(summarypolicyFee);
		double d_summarySCValue = Double.parseDouble(summarySCValue);

		// Calculate the SLTF value
		double calcsummarySltf = (d_summarypremium + d_summaryinspFee + d_summarypolicyFee + d_summarySCValue)
				* d_sltfPercentage;

		// Getting Actual SLTF value
		String actualsummarySltf = endorsePolicyPage.premiumDetails.formatDynamicPath("4", "2").getData()
				.replace("$", "").replace("-", "");
		double d_actualsummarySltf = Double.parseDouble(actualsummarySltf);

		// Printing Calculated and Actual SLTF
		Assertions.passTest("Endorsement Policy Page", "Calculated SLTF: " + calcsummarySltf);
		Assertions.passTest("Endorsement Policy Page", "Actual SLTF: " + d_actualsummarySltf);

		// Comparing Actual and Calculated Transaction column SLTF values
		if (Precision.round(Math.abs(Precision.round(d_actualsummarySltf, 2) - Precision.round(calcsummarySltf, 2)),
				2) < 0.05) {
			Assertions.passTest("Endorse Policy Page",
					"Calculated Surplus Lines Taxes and Fees for Transaction Column : " + "-$"
							+ Precision.round(calcsummarySltf, 2));
			Assertions.passTest("Endorse Policy Page",
					"Actual Surplus Lines Taxes and Fees for Transaction Column : " + "-$" + d_actualsummarySltf);
		} else {
			Assertions.verify(d_actualsummarySltf, calcsummarySltf, "Endorse Policy Page",
					"The Difference between actual transaction SLTF value and calculated transaction SLTF value is more than 0.05",
					false, false);
		}
		Assertions.addInfo("Scenario 10", "Scenario 10 Ended");

		Assertions.addInfo("Scenario 11",
				"Verifying whether The Value of Surplus Taxes & Fees under Annual Column= 2.275% of [Total Premium + Inspection Fee + Policy Fee of Annual Column]");

		// getting the actual values
		String summaryannualpremium = endorsePolicyPage.premiumDetails.formatDynamicPath("1", "3").getData()
				.replace("$", "").replace(",", "");
		String summaryannualinspFee = endorsePolicyPage.premiumDetails.formatDynamicPath("2", "3").getData()
				.replace("$", "");
		String summaryannualpolicyFee = endorsePolicyPage.premiumDetails.formatDynamicPath("3", "3").getData()
				.replace("$", "");
		String summaryannualSCValue = endorsePolicyPage.surplusContributionValueNAHO.formatDynamicPath("2").getData()
				.replace("$", "").replace("%", "");

		// Convert the actual values to double
		double d_summaryannualpremium = Double.parseDouble(summaryannualpremium);
		double d_summaryannualinspFee = Double.parseDouble(summaryannualinspFee);
		double d_summaryannualpolicyFee = Double.parseDouble(summaryannualpolicyFee);
		double d_summaryannualSCValue = Double.parseDouble(summaryannualSCValue);

		// Calculate the SLTF value
		double calcsummaryAnnualSltf = (d_summaryannualpremium + d_summaryannualinspFee + d_summaryannualpolicyFee
				+ d_summaryannualSCValue) * d_sltfPercentage;

		// Getting Actual SLTF value
		String actualsummaryAnnualSltf = endorsePolicyPage.premiumDetails.formatDynamicPath("4", "3").getData()
				.replace("$", "");
		double d_actualsummaryAnnualSltf = Double.parseDouble(actualsummaryAnnualSltf);

		// Comparing Actual and Calculated Transaction column SLTF values
		if (Precision.round(
				Math.abs(Precision.round(d_actualsummaryAnnualSltf, 2) - Precision.round(calcsummaryAnnualSltf, 2)),
				2) < 0.05) {
			Assertions.passTest("Endorse Policy Page", "Calculated Surplus Lines Taxes and Fees for Annual Column : "
					+ "$" + Precision.round(calcsummaryAnnualSltf, 2));
			Assertions.passTest("Endorse Policy Page",
					"Actual Surplus Lines Taxes and Fees for Annual Column : " + "$" + d_actualsummaryAnnualSltf);
		} else {
			Assertions.verify(d_actualsummaryAnnualSltf, calcsummaryAnnualSltf, "Endorse Policy Page",
					"The Difference between actual Annual SLTF value Fund and calculated Annual SLTF value is more than 0.05",
					false, false);
		}
		Assertions.addInfo("Scenario 11", "Scenario 11 Ended");

		Assertions.addInfo("Scenario 12",
				"Verifying whether The Value of Surplus Taxes & Fees under Term Total Column= 2.275% of [Total Premium + Inspection Fee + Policy Fee of Term Total Column]");
		// getting the actual values
		String summarytermpremium = endorsePolicyPage.premiumDetails.formatDynamicPath("1", "4").getData()
				.replace("$", "").replace(",", "");
		String summaryterminspFee = endorsePolicyPage.premiumDetails.formatDynamicPath("2", "4").getData().replace("$",
				"");
		String summarytermpolicyFee = endorsePolicyPage.premiumDetails.formatDynamicPath("3", "4").getData()
				.replace("$", "");
		String summarytermSCValue = endorsePolicyPage.surplusContributionValueNAHO.formatDynamicPath("3").getData()
				.replace("$", "").replace("%", "");

		// Convert the actual values to double
		double d_summarytermpremium = Double.parseDouble(summarytermpremium);
		double d_summaryterminspFee = Double.parseDouble(summaryterminspFee);
		double d_summarytermpolicyFee = Double.parseDouble(summarytermpolicyFee);
		double d_summarytermSCValue = Double.parseDouble(summarytermSCValue);

		// Calculate the SLTF value
		double calcsummarytermSltf = (d_summarytermpremium + d_summaryterminspFee + d_summarytermpolicyFee
				+ d_summarytermSCValue) * d_sltfPercentage;

		// Getting Actual SLTF value
		String actualsummarytermSltf = endorsePolicyPage.premiumDetails.formatDynamicPath("4", "4").getData()
				.replace("$", "");
		double d_actualsummarytermSltf = Double.parseDouble(actualsummarytermSltf);

		// Comparing Actual and Calculated Transaction column SLTF values
		if (Precision.round(
				Math.abs(Precision.round(d_actualsummarytermSltf, 2) - Precision.round(calcsummarytermSltf, 2)),
				2) < 0.05) {
			Assertions.passTest("Endorse Policy Page",
					"Calculated Surplus Lines Taxes and Fees for Term Total Column : " + "$"
							+ Precision.round(calcsummarytermSltf, 2));
			Assertions.passTest("Endorse Policy Page",
					"Actual Surplus Lines Taxes and Fees for Term Total Column : " + "$" + d_actualsummarytermSltf);
		} else {
			Assertions.verify(d_actualsummarytermSltf, calcsummarytermSltf, "Account Overview Page",
					"The Difference between actual Total SLTF value and calculated Total SLTF value is more than 0.05",
					false, false);
		}
		Assertions.addInfo("Scenario 12", "Scenario 12 Ended");

		// Verifying the Prorata factor
		// Prorata Factor = 1 - [The Difference in Number of Days between
		// Endorsement
		// Effective Date and Policy Effective Date]/365
		// i.e. daysDiff = 1 / 365 => 0.0027 rounded off as 0.003
		Assertions.addInfo("Scenario 13",
				"Verifying the Prorata Factor and Comparing Actual and Calulated Prorata Factors");
		double daysDiff = 0.003;
		double calcProrataFactorData = 1 - daysDiff;

		// getting actual Prorata value
		String actualProrataValue = endorsePolicyPage.prorataFactor.getData();
		double d_actualProrataValue = Double.parseDouble(actualProrataValue);
		if (Precision.round(
				Math.abs(Precision.round(d_actualProrataValue, 3) - Precision.round(calcProrataFactorData, 3)),
				3) < 0.05) {
			Assertions.passTest("Endorse Policy Page",
					"Calculated Prorata Factor : " + Precision.round(calcProrataFactorData, 3));
			Assertions.passTest("Endorse Policy Page", "Actual Prorata Factor : " + d_actualProrataValue);
		} else {
			Assertions.passTest("Endorse Policy Page",
					"The Difference between actual  and calculated Prorata factor is more than 0.05");
		}
		Assertions.addInfo("Scenario 13", "Scenario 13 Ended");

		// Verify the changes under endorsement summary
		Assertions.addInfo("Scenario 14", "Verifying all the Changes made during PB Endorsement");
		String priorlossFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(6).getData();
		String priorlossTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(7).getData();
		Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(5).checkIfElementIsDisplayed(), true,
				"Endorse Summary Page",
				"The Prior Loss Details " + priorlossTo + " changed to : " + priorlossFrom + " displayed is verified",
				false, false);
		String lossFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(11).getData();
		String lossTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(12).getData();
		Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(10).checkIfElementIsDisplayed(), true,
				"Endorse Summary Page",
				"The Prior Loss Details " + lossFrom + " changed to : " + lossTo + " displayed is verified", false,
				false);
		String amountFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(18).getData();
		String amountTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(19).getData();
		Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(17).checkIfElementIsDisplayed(), true,
				"Endorse Summary Page", "The Additional Amount of Insurance for Dwelling Coverage " + amountFrom
						+ " changed to : " + amountTo + " displayed is verified",
				false, false);
		String aopFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(23).getData();
		String aopTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(24).getData();
		Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(22).checkIfElementIsDisplayed(), true,
				"Endorse Summary Page",
				"The All Other Perils Deductible " + aopFrom + " changed to : " + aopTo + " displayed is verified",
				false, false);
		String aowhFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(28).getData();
		String aowhTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(29).getData();
		Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(27).checkIfElementIsDisplayed(), true,
				"Endorse Summary Page", "The All Other Wind and Hail Deductible " + aowhFrom + " changed to : " + aowhTo
						+ " displayed is verified",
				false, false);
		String buildingNumFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(33).getData();
		String buildingNumTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(34).getData();
		Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(32).checkIfElementIsDisplayed(), true,
				"Endorse Summary Page", "The Building Number Of Units " + buildingNumFrom + " changed to : "
						+ buildingNumTo + " displayed is verified",
				false, false);
		String constclassFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(38).getData();
		String constclassTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(39).getData();
		Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(37).checkIfElementIsDisplayed(), true,
				"Endorse Summary Page",
				"The Construction Class " + constclassFrom + " changed to : " + constclassTo + " displayed is verified",
				false, false);
		String conAFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(43).getData();
		String covATo = endorsePolicyPage.endorsementSummary.formatDynamicPath(44).getData();
		Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(42).checkIfElementIsDisplayed(), true,
				"Endorse Summary Page",
				"The Coverage A Limit " + conAFrom + " changed to : " + covATo + " displayed is verified", false,
				false);
		String lossAssFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(48).getData();
		String lossAssTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(49).getData();
		Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(47).checkIfElementIsDisplayed(), true,
				"Endorse Summary Page",
				"The Loss Assessment Coverage " + lossAssFrom + " changed to : " + lossAssTo + " displayed is verified",
				false, false);
		String NSFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(48).getData();
		String NSTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(49).getData();
		Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(47).checkIfElementIsDisplayed(), true,
				"Endorse Summary Page",
				"The Additional Interest " + NSFrom + " changed to : " + NSTo + " displayed is verified", false, false);
		String OrdinanceLawFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(53).getData();
		String OrdinanceLawTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(54).getData();
		Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(52).checkIfElementIsDisplayed(), true,
				"Endorse Summary Page",
				"The Ordinance Law " + OrdinanceLawFrom + " changed to : " + OrdinanceLawTo + " displayed is verified",
				false, false);
		String ResidentialWindFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(58).getData();
		String ResidentialWindTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(59).getData();
		Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(57).checkIfElementIsDisplayed(), true,
				"Endorse Summary Page", "The Residential Wind Mitigation " + ResidentialWindFrom + " changed to : "
						+ ResidentialWindTo + " displayed is verified",
				false, false);
		String SecurityFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(63).getData();
		String SecurityTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(64).getData();
		Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(62).checkIfElementIsDisplayed(), true,
				"Endorse Summary Page",
				"The Security " + SecurityFrom + " changed to : " + SecurityTo + " displayed is verified", false,
				false);
		String NoOfStoriesFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(68).getData();
		String NoOfStoriesTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(69).getData();
		Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(67).checkIfElementIsDisplayed(), true,
				"Endorse Summary Page",
				"The Security " + NoOfStoriesFrom + " changed to : " + NoOfStoriesTo + " displayed is verified", false,
				false);
		String TotalSqFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(73).getData();
		String TotalSqTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(74).getData();
		Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(72).checkIfElementIsDisplayed(), true,
				"Endorse Summary Page",
				"The Total Square Footage " + TotalSqFrom + " changed to : " + TotalSqTo + " displayed is verified",
				false, false);
		String YearConstructedFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(78).getData();
		String YearConstructedTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(79).getData();
		Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(77).checkIfElementIsDisplayed(), true,
				"Endorse Summary Page", "The Year Constructed " + YearConstructedFrom + " changed to : "
						+ YearConstructedTo + " displayed is verified",
				false, false);
		Assertions.addInfo("Scenario 14", "Scenario 14 Ended");

		// click on View Endorsement Quote button
		endorsePolicyPage.viewEndorsementQuote.scrollToElement();
		endorsePolicyPage.viewEndorsementQuote.click();
		Assertions.passTest("Endorse Policy Page", "Clicked on View Endorsement Quote Button");

		// Verify the changes under View endorsement Quote for Deductibles
		testData = data.get(data_Value3);
		Assertions.addInfo("Scenario 15",
				"Verifying all the Changes made during PB Endorsement is displayed in View Endorsement Quote Page");
		String namedStromData = viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Named Storm").getData()
				.substring(0, 3);
		Assertions.verify(namedStromData, testData.get("NamedStormValue"), "View Endorsement Quote Page",
				"The Named Strom is verified and displayed as "
						+ viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Named Storm").getData(),
				false, false);
		String WOHData = viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Wind and Hail").getData()
				.substring(0, 3);
		Assertions.verify(WOHData, testData.get("AOWHDeductibleValue"), "View Endorsement Quote Page",
				"The Wind and Hail value is verified and displayed as "
						+ viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Wind and Hail").getData(),
				false, false);
		Assertions.verify(viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("All Other Perils").getData(),
				testData.get("AOPDeductibleValue"), "View Endorsement Quote Page",
				"The All Other Perils value is verified and displayed as "
						+ viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("All Other Perils").getData(),
				false, false);

		// Verify the changes under View endorsement Quote for Endorsement
		Assertions.verify(viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Additional AOI").getData(),
				testData.get("AdditionalDwellingCoverage").substring(0,4), "View Endorsement Quote Page",
				"The Additional Dwelling Coverage is verified and displayed as "
						+ viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Additional AOI").getData(),
				false, false);
		System.out.println("Length"+testData.get("AdditionalDwellingCoverage").length());
System.out.println("value "+testData.get("AdditionalDwellingCoverage").substring(0, 4));
		Assertions.verify(viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Loss Assessment").getData(),
				testData.get("LossAssessment"), "View Endorsement Quote Page",
				"The Loss Assessment is verified and displayed as "
						+ viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Loss Assessment").getData(),
				false, false);
		Assertions.verify(viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Ordinance or Law").getData(),
				testData.get("OrdinanceOrLaw"), "View Endorsement Quote Page",
				"The Ordinance or Law is verified and displayed as "
						+ viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Ordinance or Law").getData(),
				false, false);

		// Verify the changes under View endorsement Quote for Risk
		// Characteristics
		Assertions.verify(viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Year of Construction").getData(),
				testData.get("L1D1-DwellingYearBuilt"), "View Endorsement Quote Page",
				"The Year of Construction is verified and displayed as "
						+ viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Year of Construction").getData(),
				false, false);
		Assertions.verify(
				viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Square Footage").getData().replace(",", ""),
				testData.get("L1D1-DwellingSqFoot"), "View Endorsement Quote Page",
				"The Square Footage is verified and displayed as "
						+ viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Square Footage").getData(),
				false, false);
		Assertions.verify(viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Construction Type").getData(),
				testData.get("L1D1-DwellingConstType"), "View Endorsement Quote Page",
				"The Construction Type is verified and displayed as "
						+ viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Construction Type").getData(),
				false, false);
		Assertions.verify(viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Central Station Alarm").getData(),
				testData.get("L1D1-CentralStationAlarm"), "View Endorsement Quote Page",
				"The Central Station Alarm is verified and displayed as "
						+ viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Central Station Alarm").getData(),
				false, false);
		Assertions.verify(viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Number of Units").getData(),
				testData.get("L1D1-DwellingUnits"), "View Endorsement Quote Page",
				"The Number of Units is verified and displayed as "
						+ viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Number of Units").getData(),
				false, false);
		Assertions.verify(viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Number of Floors").getData(),
				testData.get("L1D1-DwellingFloors"), "View Endorsement Quote Page",
				"The Number of Floors is verified and displayed as "
						+ viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Number of Floors").getData(),
				false, false);
		Assertions.addInfo("Scenario 15", "Scenario 15 Ended");

		// click on View Endorsement Quote button
		viewOrPrintFullQuotePage.closeButton.scrollToElement();
		viewOrPrintFullQuotePage.closeButton.click();
		Assertions.passTest("View Or Print Full Quote Page", "Clicked on Close Button");

		// Click on Next Button
		Assertions.passTest("Endorse Policy Page", "Endorse Policy page loaded successfully");
		endorsePolicyPage.completeButton.scrollToElement();
		endorsePolicyPage.completeButton.click();
		Assertions.passTest("Endorse Policy Page", "Clicked on Complete Button");

		// Asseting the OOS message
		Assertions.addInfo("Scenario 16", "Verifying the Presence of Out Of Sequence Message");
		Assertions.verify(endorsePolicyPage.conflictTxn.checkIfElementIsDisplayed(), true,
				"Out Of Sequesnce Transaction",
				"The Message " + endorsePolicyPage.conflictTxn.getData() + " displayed is verified", false, false);
		Assertions.verify(endorsePolicyPage.oosMsg.checkIfElementIsDisplayed(), true, "Out Of Sequesnce Transaction",
				"The Message " + endorsePolicyPage.oosMsg.getData() + " displayed is verified", false, false);
		Assertions.addInfo("Scenario 16", "Scenario 16 Ended");

		// click on continue
		endorsePolicyPage.continueButton.scrollToElement();
		endorsePolicyPage.continueButton.click();

		// Click on Close
		Assertions.passTest("Endorse Summary Page", "Endorse Summary page loaded successfully");
		endorseSummaryDetailsPage.closeBtn.scrollToElement();
		endorseSummaryDetailsPage.closeBtn.click();
		Assertions.passTest("Endorse Summary Page", "Clicked on Close Button");

		// Click on Endorse PB link
		policySummaryPage.endorsePB.scrollToElement();
		policySummaryPage.endorsePB.click();
		Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

		// Entering Endorsement effective date
		testData = data.get(data_Value5);
		Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
				"Endorse Policy Page Loaded successfully", false, false);
		endorsePolicyPage.endorsementEffDate.scrollToElement();
		endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
		endorsePolicyPage.endorsementEffDate.tab();
		Assertions.passTest("Endorse Policy Page", "Entered the Endorsement Efffective Date");

		// click on edit location or building details link
		endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
		endorsePolicyPage.editLocOrBldgInformationLink.click();
		Assertions.passTest("Endorse Policy Page", "Clicked on Edit Location or Building Details link");

		// Entering Year of Built to 1949
		testData = data.get(data_Value4);
		Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
				"Dwelling Page loaded Successfully", false, false);
		if (!testData.get("L1D1-DwellingYearBuilt").equals("")) {
			dwellingPage.yearBuilt.scrollToElement();
			dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));
			dwellingPage.yearBuilt.tab();
		}

		// click on continue button
		dwellingPage.continueButton.scrollToElement();
		dwellingPage.continueButton.click();

		// Click on Continue
		Assertions.addInfo("Scenario 17", "Verifying the Presence of warning message for ineligible year built");
		Assertions.verify(dwellingPage.protectionClassWarMsg.checkIfElementIsDisplayed(), true, "Create A Quote Page",
				"Warning message is " + dwellingPage.protectionClassWarMsg.getData() + " is verified", false, false);
		dwellingPage.override.scrollToElement();
		dwellingPage.override.click();
		Assertions.addInfo("Scenario 17", "Scenario 17 Ended");

		// click on continue button
		dwellingPage.continueButton.scrollToElement();
		dwellingPage.continueButton.click();
		Assertions.passTest("Dwelling Page", "Clicked on continue button");

		// Click on Continue
		Assertions.verify(createQuotePage.continueEndorsementBtn.checkIfElementIsDisplayed(), true,
				"Create A Quote Page", "Create A Quote Page loaded Successfully", false, false);
		createQuotePage.continueEndorsementBtn.scrollToElement();
		createQuotePage.continueEndorsementBtn.click();
		Assertions.passTest("Create Quote Page", "Clicked on continue endorsement button");

		// click on Continue button
		if (createQuotePage.continueButton.checkIfElementIsPresent()
				&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}

		// click on edit location or building details link
		endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
		endorsePolicyPage.editLocOrBldgInformationLink.click();
		Assertions.passTest("Endorse Policy Page", "Clicked on Edit Location or Building Details link");

		// Entering roof and protection discounts details
		// Entering roof details
		// dwellingPage.addRoofDetails(testData, locationCount, dwellingCount);
		testData = data.get(data_Value5);
		dwellingPage.roofDetailsLink.waitTillVisibilityOfElement(60);
		dwellingPage.roofDetailsLink.scrollToElement();
		dwellingPage.roofDetailsLink.click();
		if (!testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingRoofReplacedYear").equals("")) {
			dwellingPage.yearRoofLastReplaced.scrollToElement();
			dwellingPage.yearRoofLastReplaced
					.setData(testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingRoofReplacedYear"));
			Assertions.passTest("Dwelling Page",
					"Roof last replaced year Latest Value : " + dwellingPage.yearRoofLastReplaced.getData());
		}

		// Entering protection details
		dwellingPage.enterProtectionDiscountsDetails(testData, locationCount, dwellingCount);
		Assertions.passTest("Dwelling Page",
				"Year Plumbing in Future Year Value : " + dwellingPage.yearPlumbingUpdated.getData());
		Assertions.passTest("Dwelling Page",
				"Year Electrical in Future Year Value : " + dwellingPage.yearElectricalUpdated.getData());
		Assertions.passTest("Dwelling Page",
				"Year HVAC in Future Year Value : " + dwellingPage.yearHVACUpdated.getData());

		// Click on resubmit
		dwellingPage.reviewDwelling.scrollToElement();
		dwellingPage.reviewDwelling.click();
		Assertions.passTest("Dwelling Page", "Clicked on reviewDwelling Button");

		// Asserting warning message when Year Roof replaced , Year Plumbing,
		// Year Electrical,
		// Year HVAC in future
		Assertions.addInfo("Scenario 18",
				"Verifying the Presence of Warning Message for Roof replaced ,Year Plumbing ,Year Electrical, Year HVAC are in future Year");
		Assertions.addInfo("Dwelling page",
				"Asserting warning message when Year Roof replaced ,Year Plumbing ,Year Electrical, Year HVAC are in future Year");
		Assertions.verify(dwellingPage.protectionClassWarMsg.checkIfElementIsDisplayed(), true, "Dwelling Page",
				"Warning message is " + dwellingPage.protectionClassWarMsg.getData() + " is verified", false, false);
		Assertions.addInfo("Scenario 18", "Scenario 18 Ended");

		// Entering Latest Year Built
		testData = data.get(data_Value3);
		dwellingPage.addDwellingDetails(testData, locationCount, dwellingCount);

		// Entering roof and protection discounts details
		testData = data.get(data_Value6);

		// Entering roof details
		// dwellingPage.addRoofDetails(testData, locationCount, dwellingCount);
		dwellingPage.roofDetailsLink.waitTillVisibilityOfElement(60);
		dwellingPage.roofDetailsLink.scrollToElement();
		dwellingPage.roofDetailsLink.click();
		if (!testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingRoofReplacedYear").equals("")) {
			dwellingPage.yearRoofLastReplaced.scrollToElement();
			dwellingPage.yearRoofLastReplaced
					.setData(testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingRoofReplacedYear"));
			Assertions.passTest("Dwelling Page",
					"Roof last replaced in Future year Value : " + dwellingPage.yearRoofLastReplaced.getData());
		}

		// Entering protection details
		dwellingPage.enterProtectionDiscountsDetails(testData, locationCount, dwellingCount);
		Assertions.passTest("Dwelling Page",
				"Year Plumbing in Future Year Value : " + dwellingPage.yearPlumbingUpdated.getData());
		Assertions.passTest("Dwelling Page",
				"Year Electrical in Future Year Value : " + dwellingPage.yearElectricalUpdated.getData());
		Assertions.passTest("Dwelling Page",
				"Year HVAC in Future Year Value : " + dwellingPage.yearHVACUpdated.getData());

		// Click on resubmit
		dwellingPage.reSubmit.scrollToElement();
		dwellingPage.reSubmit.click();
		Assertions.passTest("Dwelling Page", "Clicked on Resubmit Button");

		// Asserting warning message when Year Roof replaced ,Year Plumbing,
		// Year Electrical,
		// Year HVAC before year built
		Assertions.addInfo("Scenario 19",
				"Verifying the Presence of Warning Message for Roof replaced ,Year Plumbing ,Year Electrical, Year HVAC are before year built");
		Assertions.addInfo("Dwelling page",
				"Asserting warning message when Year Roof replaced ,Plumbing Year ,Electrical Year, HVAC Year before year built");
		Assertions.verify(dwellingPage.protectionClassWarMsg.checkIfElementIsDisplayed(), true, "Dwelling Page",
				"Warning message is " + dwellingPage.protectionClassWarMsg.getData() + " is verified", false, false);
		Assertions.addInfo("Scenario 19", "Scenario 19 Ended");

		// Updating when Year Roof replaced ,Year Plumbing, Year Electrical,
		// Year HVAC to year built
		// Entering roof and protection discounts details
		testData = data.get(data_Value7);

		// Entering roof details
		// dwellingPage.addRoofDetails(testData, locationCount, dwellingCount);
		dwellingPage.roofDetailsLink.waitTillVisibilityOfElement(60);
		dwellingPage.roofDetailsLink.scrollToElement();
		dwellingPage.roofDetailsLink.click();
		if (!testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingRoofReplacedYear").equals("")) {
			dwellingPage.yearRoofLastReplaced.scrollToElement();
			dwellingPage.yearRoofLastReplaced
					.setData(testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingRoofReplacedYear"));
			Assertions.passTest("Dwelling Page",
					"Roof last replaced year Latest Value : " + dwellingPage.yearRoofLastReplaced.getData());
		}

		// Entering protection details
		dwellingPage.enterProtectionDiscountsDetails(testData, locationCount, dwellingCount);
		Assertions.passTest("Dwelling Page",
				"Year Plumbing Latest Value : " + dwellingPage.yearPlumbingUpdated.getData());
		Assertions.passTest("Dwelling Page",
				"Year Electrical Latest Value : " + dwellingPage.yearElectricalUpdated.getData());
		Assertions.passTest("Dwelling Page", "Year HVAC Latest Value : " + dwellingPage.yearHVACUpdated.getData());

		// Click on resubmit
		dwellingPage.reSubmit.scrollToElement();
		dwellingPage.reSubmit.click();
		Assertions.passTest("Dwelling Page", "Clicked on Resubmit Button");

		// Click on Continue
		dwellingPage.continueButton.scrollToElement();
		dwellingPage.continueButton.click();
		Assertions.passTest("Dwelling Page", "Clicked on Continue Button");

		// Updating Coverage F without Coverage E (i.e. CovE = none)
		Assertions.verify(createQuotePage.continueEndorsementBtn.checkIfElementIsDisplayed(), true,
				"Create A Quote Page", "Create A Quote Page loaded Successfully", false, false);
		createQuotePage.enterInsuredValuesNAHO(testData, locationCount, dwellingCount);

		// Click on Continue
		createQuotePage.continueEndorsementBtn.scrollToElement();
		createQuotePage.continueEndorsementBtn.click();
		Assertions.passTest("Create A Quote Page", "Clicked on Continue Button");

		// Asserting Warning message for CovE
		Assertions.addInfo("Scenario 20", "Verifying Presence of warning message when CovE value is removed");
		Assertions.verify(
				createQuotePage.coverageEWarningmessage.formatDynamicPath("limit for Cov F")
						.checkIfElementIsDisplayed(),
				true, "Create A Quote Page",
				"Warning message is "
						+ createQuotePage.coverageEWarningmessage.formatDynamicPath("limit for Cov F").getData()
						+ " is verified",
				false, false);
		Assertions.addInfo("Scenario 20", "Scenario 20 Ended");

		// Click on Continue Button
		createQuotePage.continueButton.scrollToElement();
		createQuotePage.continueButton.click();
		Assertions.passTest("Create A Quote Page", "Clicked on Continue Button");

		// Click on Next button
		Assertions.verify(endorsePolicyPage.nextButton.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
				"Endorse Policy Page loaded Successfully", false, false);
		endorsePolicyPage.nextButton.scrollToElement();
		endorsePolicyPage.nextButton.click();

		// Click on Continue
		endorsePolicyPage.continueButton.scrollToElement();
		endorsePolicyPage.continueButton.click();
		Assertions.passTest("Endorse Policy Page", "Clicked on Continue Button");

		// Click on Complete
		Assertions.verify(endorsePolicyPage.completeButton.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
				"Endorse Policy Page Loaded successfully", false, false);
		endorsePolicyPage.completeButton.scrollToElement();
		endorsePolicyPage.completeButton.click();
		Assertions.passTest("Endorse Policy Page", "Clicked on Complete Button");

		// Click on Complete
		Assertions.verify(endorseSummaryDetailsPage.closeBtn.checkIfElementIsDisplayed(), true, "Endorse Summary Page",
				"Endorse Policy Page Loaded successfully", false, false);
		endorseSummaryDetailsPage.closeBtn.scrollToElement();
		endorseSummaryDetailsPage.closeBtn.click();
		Assertions.passTest("Endorse Summary Page", "Clicked on Close Button");

		// Click on Endorse PB link
		policySummaryPage.endorsePB.scrollToElement();
		policySummaryPage.endorsePB.click();
		Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

		// Entering Endorsement effective date
		testData = data.get(data_Value8);
		Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
				"Endorse Policy Page Loaded successfully", false, false);
		endorsePolicyPage.endorsementEffDate.scrollToElement();
		endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
		endorsePolicyPage.endorsementEffDate.tab();
		Assertions.passTest("Endorse Policy Page", "Entered the Endorsement Efffective Date");

		// Click on Continue Button
		endorsePolicyPage.continueButton.scrollToElement();
		endorsePolicyPage.continueButton.click();

		// click on edit location or building details link
		endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
		endorsePolicyPage.editLocOrBldgInformationLink.click();
		Assertions.passTest("Endorse Policy Page", "Clicked on Edit Location or Building Details link");

		// Updating Address details
		Assertions.verify(dwellingPage.continueButton.checkIfElementIsDisplayed(), true, "Dwelling  Page",
				"Dwelling Page Loaded successfully", false, false);
		dwellingPage.modifyDwellingDetailsNAHO(testData);
		Assertions.passTest("Dwelling Page", "Dwelling Details Entered successfully");

		// click on Continue
		dwellingPage.continueButton.scrollToElement();
		dwellingPage.continueButton.click();
		Assertions.passTest("Dwelling Page", "Clicked on Continue Button");

		// Entering Quote Details
		Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
				"Create Quote Page", "Create Quote Page loaded successfully", false, false);
		if (createQuotePage.continueEndorsementButton.checkIfElementIsPresent()
				&& createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed()) {

			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");
		}

		// Asserting Warning message displayed for invalid address
		Assertions.addInfo("Scenario 21", "Verifying the Warning Message when entered invalid loaction");
		Assertions.verify(
				createQuotePage.warningMessages
						.formatDynamicPath("building is not eligible").checkIfElementIsDisplayed(),
				true, "Create A Page",
				"The Warning Message "
						+ createQuotePage.warningMessages.formatDynamicPath("building is not eligible").getData()
						+ " displayed is verified",
				false, false);
		Assertions.addInfo("Scenario 21", "Scenario 21 Ended");

		// Click on continue
		createQuotePage.continueButton.scrollToElement();
		createQuotePage.continueButton.click();
		Assertions.passTest("Create A Quote Page", "Clicked on Continue Button");

		// Click on Next
		Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
				"Endorse Policy Page Loaded successfully", false, false);
		endorsePolicyPage.nextButton.scrollToElement();
		endorsePolicyPage.nextButton.click();
		Assertions.passTest("Endorse Policy Page", "Clicked on Next Button");

		// Asserting Warning message displayed for invalid address
		Assertions.addInfo("Scenario 22", "Verifying the Warning Message when entered invalid loaction");
		Assertions.verify(
				endorsePolicyPage.endorsementWarningMsg
						.formatDynamicPath("building is not eligible").checkIfElementIsDisplayed(),
				true, "Endorse Policy Page",
				"The Warning Message " + endorsePolicyPage.endorsementWarningMsg
						.formatDynamicPath("building is not eligible").getData() + " displayed is verified",
				false, false);
		Assertions.addInfo("Scenario 22", "Scenario 22 Ended");

		// Click on Ok Continue
		endorsePolicyPage.oKContinueButton.scrollToElement();
		endorsePolicyPage.oKContinueButton.click();
		Assertions.passTest("Endorse Policy Page", "Clicked on Ok Continue Button");

		// Click on Complete
		endorsePolicyPage.completeButton.scrollToElement();
		endorsePolicyPage.completeButton.click();
		Assertions.passTest("Endorse Policy Page", "Clicked on Complete Button");

		// Click on Continue
		endorsePolicyPage.continueButton.scrollToElement();
		endorsePolicyPage.continueButton.click();

		// Click on Close
		Assertions.passTest("Endorse Summary Page", "Endorse Summary page loaded successfully");
		endorseSummaryDetailsPage.closeBtn.scrollToElement();
		endorseSummaryDetailsPage.closeBtn.click();
		Assertions.passTest("Endorse Summary Page", "Clicked on Close Button");

		// Asserting policy Number
		Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
				"Policy Summary Page loaded successfully. PolicyNumber is " + policySummaryPage.policyNumber.getData(),
				false, false);

		// Adding the presence of dec links for all the transactions
		Assertions.addInfo("Scenario 23", "Adding the presence of dec links for all the transactions");
		Assertions.verify(policySummaryPage.decLink.checkIfElementIsDisplayed(), true, "Policy Summary Page",
				"Dec link displayed is verifed for New business transaction", false, false);
		Assertions.verify(policySummaryPage.pbEndtAmmDecLink.formatDynamicPath("3").checkIfElementIsDisplayed(), true,
				"Policy Summary Page", "Amended Dec link displayed is verifed for first endorsement transaction", false,
				false);
		Assertions.verify(policySummaryPage.pbEndtAmmDecLink.formatDynamicPath("4").checkIfElementIsDisplayed(), true,
				"Policy Summary Page", "Amended Dec link displayed is verifed for reversal transaction", false, false);
		Assertions.verify(policySummaryPage.pbEndtAmmDecLink.formatDynamicPath("5").checkIfElementIsDisplayed(), true,
				"Policy Summary Page", "Amended Dec link displayed is verifed for second endorsement transaction",
				false, false);
		Assertions.verify(policySummaryPage.pbEndtAmmDecLink.formatDynamicPath("6").checkIfElementIsDisplayed(), true,
				"Policy Summary Page", "Amended Dec link displayed is verifed for third endorsement transaction", false,
				false);
		Assertions.verify(policySummaryPage.pbEndtAmmDecLink.formatDynamicPath("7").checkIfElementIsDisplayed(), true,
				"Policy Summary Page", "Amended Dec link displayed is verifed for reversal transaction", false, false);
		Assertions.verify(policySummaryPage.pbEndtAmmDecLink.formatDynamicPath("8").checkIfElementIsDisplayed(), true,
				"Policy Summary Page", "Amended Dec link displayed is verifed for reversal transaction", false, false);
		Assertions.verify(policySummaryPage.pbEndtAmmDecLink.formatDynamicPath("9").checkIfElementIsDisplayed(), true,
				"Policy Summary Page", "Amended Dec link displayed is verifed for fourth endorsement transaction",
				false, false);
		Assertions.addInfo("Scenario 23", "Scenario 23 Ended");

			// Signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBVATC001 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBVATC001 ", "Executed Successfully");
			}
		}
	}
}
