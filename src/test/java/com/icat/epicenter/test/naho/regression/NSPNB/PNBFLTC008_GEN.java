/** Program Description: 1. Performing various validations on NAHO Renewal product as Producer
 * 						 2. Create a NPB Endt. with the actual Exp. Date and Create PB Endt2 with the change in Exp. date to 3 months future for Actual Exp Date.
 *  					 3. Endt1: Extend the Policy Term to 3 Months future from Actual Exp Date and Endt2: create another PB Endt between the extended period of change and Actual Exp date.
 *  					 4. Check the Guy Carpenter values for Renewal requote and validate the ELR and AAL values are calculated correctly
 *  Author			   : Pavan Mule
 *  Date of Creation   : 27/05/2022
 **/
package com.icat.epicenter.test.naho.regression.NSPNB;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorseInspectionContactPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.RmsModelResultsPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBFLTC008_GEN extends AbstractNAHOTest {

	public PNBFLTC008_GEN() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSPNB/FL008.xls";
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
		LoginPage loginPage = new LoginPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		EndorseInspectionContactPage endorseInspectionContactPage = new EndorseInspectionContactPage();
		RmsModelResultsPage rmsModelResultsPage = new RmsModelResultsPage();

		// initializing variables
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		int data_Value4 = 3;
		int quoteLen;
		String quoteNumber;
		String policyNumber;
		String elrPremium;
		String aalValue;
		String elrValue;
		double calELR;
		BigDecimal b_calELR;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// Create account as producer
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page ",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account ", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page ",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page ", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page ",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page ", "Dwelling details entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page ",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Enter quote details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page ",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Click on Continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Asserting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Adding below code for guy carpenter
			// Verifying presence of view model result link
			Assertions.addInfo("Scenario 01", "Verifying presence of view model result link");
			Assertions.verify(accountOverviewPage.viewModelResultsLink.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "View model result link displayed", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Click on view model result link
			accountOverviewPage.viewModelResultsLink.scrollToElement();
			accountOverviewPage.viewModelResultsLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on view model result link");

			// Verify presence of GC17,ELR Premium, peril AAL,peril ELR,Peril,Total
			// Premium,peril deductible and TIV labels
			rmsModelResultsPage.closeButton.waitTillVisibilityOfElement(60);
			Assertions.verify(rmsModelResultsPage.closeButton.checkIfElementIsDisplayed(), true,
					"RMS Model Result Page", "RMS Model Result page loaded successfully", false, false);
			Assertions.addInfo("Scenario 02",
					"Verify presence of GC17,ELR Premium, peril AAL,peril ELR,Peril,Total Premium,peril deductible and TIV labels");
			Assertions.verify(
					rmsModelResultsPage.gc17Label.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.gc17Label.getData().contains("GC17"),
					true, "RMS Model Result Page",
					"Guy Carpenter label is " + rmsModelResultsPage.gc17Label.getData() + " displayed", false, false);
			Assertions.verify(
					rmsModelResultsPage.elrPremiumLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.elrPremiumLabel.getData().contains("ELR Premium"),
					true, "RMS Model Result Page", "Guy Carpenter ELR Premium label is "
							+ rmsModelResultsPage.elrPremiumLabel.getData() + " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.guyCarpenterELRLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.guyCarpenterELRLabel.getData().contains("Peril ELR"),
					true, "RMS Model Result Page", "Guy Carpenter Peril ELR label is "
							+ rmsModelResultsPage.guyCarpenterELRLabel.getData() + " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.guyCarpenterAALLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.guyCarpenterAALLabel.getData().contains("Peril AAL"),
					true, "RMS Model Result Page", "Guy Carpenter Peril AAL label is "
							+ rmsModelResultsPage.guyCarpenterAALLabel.getData() + " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.tivValueLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.tivValueLabel.getData().contains("TIV"),
					true, "RMS Model Result Page",
					"Guy Carpenter TIV label is " + rmsModelResultsPage.tivValueLabel.getData() + " displayed", false,
					false);
			Assertions.verify(
					rmsModelResultsPage.totalPremiumLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.totalPremiumLabel.getData().contains("Total Premium:"),
					true, "RMS Model Result Page", "Guy Carpenter Total Premium label is "
							+ rmsModelResultsPage.totalPremiumLabel.getData() + " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.perilDeductibleLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.perilDeductibleLabel.getData().contains("Peril Deductible:"),
					true, "RMS Model Result Page", "Guy Carpenter Peril Deductible label is "
							+ rmsModelResultsPage.perilDeductibleLabel.getData() + " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.perilLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.perilLabel.getData().contains("Peril:"),
					true, "RMS Model Result Page",
					"Guy Carpenter Peril label is " + rmsModelResultsPage.perilLabel.getData() + " displayed", false,
					false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Verifying and Asserting ELR Premium,Peril AAL,Peril ELR, Total
			// Premium,Peril,PerilDeductible and TIV values
			Assertions.addInfo("Scenario 03",
					"Verifying and Asserting ELR Premium,Peril AAL,Peril ELR, Total Premium,Peril,PerilDeductible and TIV values");
			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues
							.formatDynamicPath("ELR Premium").checkIfElementIsDisplayed(),
					true, "RMS Model Result Page",
					"ELR Premium value is "
							+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("ELR Premium").getData()
							+ " displayed",
					false, false);
			Assertions.verify(rmsModelResultsPage.guyCarpenterELR.checkIfElementIsDisplayed(), true,
					"RMS Model Result Page",
					"Peril ELR value is " + rmsModelResultsPage.guyCarpenterELR.getData() + " displayed", false, false);
			Assertions.verify(rmsModelResultsPage.guyCarpenterAAL.checkIfElementIsDisplayed(), true,
					"RMS Model Result Page",
					"Peril AAL value is " + rmsModelResultsPage.guyCarpenterAAL.getData() + " displayed", false, false);
			Assertions.verify(rmsModelResultsPage.tivValue.checkIfElementIsDisplayed(), true, "RMS Model Result Page",
					"TIV value is " + rmsModelResultsPage.tivValue.getData() + " displayed", false, false);
			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril:").checkIfElementIsDisplayed(),
					true, "RMS Model Result Page",
					"Peril value is " + rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril:").getData()
							+ " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues
							.formatDynamicPath("Peril Deductible").checkIfElementIsDisplayed(),
					true, "RMS Model Result Page",
					"Peril Deductible value is "
							+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril Deductible").getData()
							+ " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues
							.formatDynamicPath("Total Premium").checkIfElementIsDisplayed(),
					true, "RMS Model Result Page",
					"Total Premium value is "
							+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Total Premium").getData()
							+ " displayed",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Getting ELR Premium,AAL Value and ELR Value
			elrPremium = rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("ELR Premium").getData()
					.replace("%", "").replace(",", "").replace("$", "");

			aalValue = rmsModelResultsPage.guyCarpenterAAL.getData().replace("%", "").replace(",", "").replace("$", "");
			System.out.println("aalValue " + aalValue);
			elrValue = rmsModelResultsPage.guyCarpenterELR.getData().replace("%", "").replace(",", "").replace("$", "");

			// Calculating ELR = (AAL/ELR Premium)*100
			// Verifying actual and calculated Peril ELR
			Assertions.addInfo("Scenario 04", "Verifying actual and calculated Peril ELR");
			calELR = (Double.parseDouble(aalValue) / Double.parseDouble(elrPremium)) * 100;

			// Rounding cal ELR decimal value to 1 digits(eg.36.08 = 36.1)
			b_calELR = new BigDecimal(Double.toString(calELR));
			b_calELR = b_calELR.setScale(1, RoundingMode.HALF_UP);
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(elrValue), 2) - Precision.round(calELR, 2)),
					2) < 0.5) {
				Assertions.passTest("RMS Model Result Page", "Calculated Peril ELR is " + b_calELR + "%");
				Assertions.passTest("RMS Model Result Page", "Actual Peril ELR is " + elrValue + "%");

			} else {
				Assertions.verify(elrValue, calELR, "RMS Model Result Page",
						"The Difference between actual and calculated peril ELR value is more than 0.5", false, false);
			}
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");
			// Click on close
			rmsModelResultsPage.closeButton.scrollToElement();
			rmsModelResultsPage.closeButton.click();
			Assertions.passTest("RMS Model Result Page", "Clicked on close button successfully");
			// Guy carpenter code Ended

			// Click on Bind button on request bind page
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind Button");

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
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Details Entered successfully");

			// Asserting policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfuly", false, false);
			policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.passTest("Policy Summary Page", "Policy Number is " + policyNumber);
			Assertions.verify(policySummaryPage.effectiveDate.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Policy Effective date is disaplyed as : "
							+ policySummaryPage.effectiveDate.getData().substring(0, 10),
					false, false);

			// clicking on renewal policy link
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();

			// Go to Home Page
			if (policySummaryPage.expaacMessage.checkIfElementIsPresent()
					&& policySummaryPage.expaacMessage.checkIfElementIsDisplayed()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.click();
				expaccInfoPage.enterExpaccInfo(testData, policyNumber);
				Assertions.passTest("ExpaccInfo Page", "ExpaccInfo details entered successfully");

				// Go to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();

				// Performing Renewal Searches
				homePage.searchPolicy(policyNumber);
				// clicking on renewal policy link
				policySummaryPage.renewPolicy.waitTillPresenceOfElement(60);
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();
				Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy Link");

			}

			// Assert renewal quote number
			Assertions.verify(
					accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsPresent()
							&& accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account overview page", "Renewal Quote number is : " + quoteNumber);

			// Adding below code for guy carpenter scenario
			// Click on create another quote button
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();
			Assertions.passTest("Account Overview Page", "Clicked on create another quote button successfully");

			// Click on Get A quote button
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote page loaded successfully", false, false);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on get a quote button successfully");

			// Verifying presence of view model result link
			Assertions.addInfo("Scenario 05", "Verifying presence of view model result link");
			Assertions.verify(accountOverviewPage.viewModelResultsLink.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "View model result link displayed", false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Click on view model result link
			accountOverviewPage.viewModelResultsLink.scrollToElement();
			accountOverviewPage.viewModelResultsLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on view model result link");

			// Verify presence of GC17,ELR Premium, peril AAL,peril ELR,Peril,Total
			// Premium,peril deductible and TIV labels
			rmsModelResultsPage.closeButton.waitTillVisibilityOfElement(60);
			Assertions.verify(rmsModelResultsPage.closeButton.checkIfElementIsDisplayed(), true,
					"RMS Model Result Page", "RMS Model Result page loaded successfully", false, false);
			Assertions.addInfo("Scenario 06", "Verify presence of GC17,ELR Premium, peril AAL and peril ELR labels");
			Assertions.verify(
					rmsModelResultsPage.gc17Label.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.gc17Label.getData().contains("GC17"),
					true, "RMS Model Result Page",
					"Guy Carpenter label is " + rmsModelResultsPage.gc17Label.getData() + " displayed", false, false);
			Assertions.verify(
					rmsModelResultsPage.elrPremiumLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.elrPremiumLabel.getData().contains("ELR Premium"),
					true, "RMS Model Result Page", "Guy Carpenter ELR Premium label is "
							+ rmsModelResultsPage.elrPremiumLabel.getData() + " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.guyCarpenterELRLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.guyCarpenterELRLabel.getData().contains("Peril ELR"),
					true, "RMS Model Result Page", "Guy Carpenter Peril ELR label is "
							+ rmsModelResultsPage.guyCarpenterELRLabel.getData() + " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.guyCarpenterAALLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.guyCarpenterAALLabel.getData().contains("Peril AAL"),
					true, "RMS Model Result Page", "Guy Carpenter Peril AAL label is "
							+ rmsModelResultsPage.guyCarpenterAALLabel.getData() + " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.tivValueLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.tivValueLabel.getData().contains("TIV"),
					true, "RMS Model Result Page",
					"Guy Carpenter TIV label is " + rmsModelResultsPage.tivValueLabel.getData() + " displayed", false,
					false);
			Assertions.verify(
					rmsModelResultsPage.totalPremiumLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.totalPremiumLabel.getData().contains("Total Premium:"),
					true, "RMS Model Result Page", "Guy Carpenter Total Premium label is "
							+ rmsModelResultsPage.totalPremiumLabel.getData() + " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.perilDeductibleLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.perilDeductibleLabel.getData().contains("Peril Deductible:"),
					true, "RMS Model Result Page", "Guy Carpenter Peril Deductible label is "
							+ rmsModelResultsPage.perilDeductibleLabel.getData() + " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.perilLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.perilLabel.getData().contains("Peril:"),
					true, "RMS Model Result Page",
					"Guy Carpenter Peril label is " + rmsModelResultsPage.perilLabel.getData() + " displayed", false,
					false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Verifying and Asserting ELR Premium,Peril AAL,Peril ELR, Total
			// Premium,Peril,PerilDeductible and TIV values
			Assertions.addInfo("Scenario 07",
					"Verifying and Asserting ELR Premium,Peril AAL,Peril ELR, Total Premium,Peril,PerilDeductible and TIV values");
			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues
							.formatDynamicPath("ELR Premium").checkIfElementIsDisplayed(),
					true, "RMS Model Result Page",
					"ELR Premium value is "
							+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("ELR Premium").getData()
							+ " displayed",
					false, false);
			Assertions.verify(rmsModelResultsPage.guyCarpenterELR.checkIfElementIsDisplayed(), true,
					"RMS Model Result Page",
					"Peril ELR value is " + rmsModelResultsPage.guyCarpenterELR.getData() + " displayed", false, false);
			Assertions.verify(rmsModelResultsPage.guyCarpenterAAL.checkIfElementIsDisplayed(), true,
					"RMS Model Result Page",
					"Peril AAL value is " + rmsModelResultsPage.guyCarpenterAAL.getData() + " displayed", false, false);
			Assertions.verify(rmsModelResultsPage.tivValue.checkIfElementIsDisplayed(), true, "RMS Model Result Page",
					"TIV value is " + rmsModelResultsPage.tivValue.getData() + " displayed", false, false);
			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril:").checkIfElementIsDisplayed(),
					true, "RMS Model Result Page",
					"Peril value is " + rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril:").getData()
							+ " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues
							.formatDynamicPath("Peril Deductible").checkIfElementIsDisplayed(),
					true, "RMS Model Result Page",
					"Peril Deductible value is "
							+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril Deductible").getData()
							+ " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues
							.formatDynamicPath("Total Premium").checkIfElementIsDisplayed(),
					true, "RMS Model Result Page",
					"Total Premium value is "
							+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Total Premium").getData()
							+ " displayed",
					false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Getting ELR Premium,AAL Value and ELR Value
			elrPremium = rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("ELR Premium").getData()
					.replaceAll("[^a-zA-Z0-9\\s]", "");
			aalValue = rmsModelResultsPage.guyCarpenterAAL.getData().replaceAll("[^a-zA-Z0-9\\s]", "");
			elrValue = rmsModelResultsPage.guyCarpenterELR.getData().replace("%", "");

			// Calculating ELR = (AAL/ELR Premium)*100
			// Verifying actual and calculated Peril ELR
			Assertions.addInfo("Scenario 08", "Verifying actual and calculated Peril ELR");
			calELR = (Double.parseDouble(aalValue) / Double.parseDouble(elrPremium)) * 100;

			// Rounding cal ELR decimal value to 1 digits(eg.36.08 = 36.1)
			b_calELR = new BigDecimal(Double.toString(calELR));
			b_calELR = b_calELR.setScale(1, RoundingMode.HALF_UP);
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(elrValue), 2) - Precision.round(calELR, 2)),
					2) < 0.5) {
				Assertions.passTest("RMS Model Result Page", "Calculated Peril ELR is " + b_calELR + "%");
				Assertions.passTest("RMS Model Result Page", "Actual Peril ELR is " + elrValue + "%");

			} else {
				Assertions.verify(elrValue, calELR, "RMS Model Result Page",
						"The Difference between actual and calculated peril ELR value is more than 0.5", false, false);
			}
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");
			// Click on close
			rmsModelResultsPage.closeButton.scrollToElement();
			rmsModelResultsPage.closeButton.click();
			Assertions.passTest("RMS Model Result Page", "Clicked on close button successfully");
			// Guy carpenter code Ended

			// Clicking on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			// Click on Release renewal to producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on release renewal to producer button successfully");

			// LogOut as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "LogOut as USM successfully");

			// Login as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as Producer Successfully");

			// searching policy as producer
			homePage.enterPersonalLoginDetails();
			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.passTest("Home Page", "Quote Searched successfully");

			// click on EditDeductibles and Limits button
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();

			// updating Cov A = $49,9999
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page ",
					"Create Quote Page loaded successfully", false, false);
			Assertions.passTest("Create Quote Page", "Original Cov A value is " + testData.get("L1D1-DwellingCovA"));
			testData = data.get(data_Value2);
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Latest Cov A value is " + testData.get("L1D1-DwellingCovA"));

			// Asserting hard stop message when Cov A = 499999, hard stop message is
			// Coverage A less than minimum limit of $500000"
			Assertions.addInfo("Scenario 09", "Asserting Hard stop message");
			Assertions.verify(
					createQuotePage.globalErr.checkIfElementIsPresent()
							&& createQuotePage.globalErr.checkIfElementIsDisplayed(),
					createQuotePage.globalErr.getData().contains("Coverage A less than minimum limit of $500000"),
					"Create quote page",
					"The Hard stop message is " + createQuotePage.globalErr.getData() + "displayed", false, false);
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Sign In as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");

			// Search for Quote
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Quote Searched successfully");

			// Click on Endorse PB Link
			policySummaryPage.endorseNPB.scrollToElement();
			policySummaryPage.endorseNPB.click();

			// Click on OK button
			if (policySummaryPage.okButton.checkIfElementIsPresent()
					&& policySummaryPage.okButton.checkIfElementIsDisplayed()) {
				policySummaryPage.okButton.scrollToElement();
				policySummaryPage.okButton.click();
			}

			// Entering Endorsement effective date
			testData = data.get(data_Value2);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// Initiate NPB Transaction
			Assertions.addInfo("Scenario 10", "NPB Transaction - Change Inspection Contact");
			endorsePolicyPage.changeInspectionContactLink.scrollToElement();
			endorsePolicyPage.changeInspectionContactLink.click();

			// Change Payment Plan
			endorseInspectionContactPage.enterInspectionContactPB(testData);
			Assertions.passTest("Endorse Inspection contact Page", "Updated Inspection Contact details");
			Assertions.addInfo("Scenario 10", "Scenario 10 Ended");

			// click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// Click on No, leave Renewal Account As it is button
			if (endorsePolicyPage.leaveRenewalAsIsButton.checkIfElementIsPresent()
					&& endorsePolicyPage.leaveRenewalAsIsButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.leaveRenewalAsIsButton.scrollToElement();
				endorsePolicyPage.leaveRenewalAsIsButton.click();
			}
			// Click on Close Button
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Initiate PB ENDT with expiration date in future
			// Click on Endorse PB Link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();

			// Click on Ok button
			if (policySummaryPage.okButton.checkIfElementIsPresent()
					&& policySummaryPage.okButton.checkIfElementIsDisplayed()) {
				policySummaryPage.okButton.scrollToElement();
				policySummaryPage.okButton.click();
			}

			// OOS Transaction for
			// Endt1: Extend the Policy Term to future Date.
			// Endt2: Again create another PB Endt between the extended period of change and
			// check the behaviour.
			// Entering Endorsement effective date
			testData = data.get(data_Value3);
			Assertions.addInfo("Scenario 11", "PB Transaction - Change Expiration Date");
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// Entering Policy Expiration date
			endorsePolicyPage.policyExpirationDate.scrollToElement();
			endorsePolicyPage.policyExpirationDate.setData(testData.get("PolicyExpirationDate"));
			endorsePolicyPage.policyExpirationDate.tab();

			// Click on Change Expiration Button
			endorsePolicyPage.changeExpirationDate.scrollToElement();
			endorsePolicyPage.changeExpirationDate.click();

			// Asserting Out of Sequence Warning Message
			Assertions.verify(endorsePolicyPage.oosMsg.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Out of Sequence message is disaplyed as : " + endorsePolicyPage.oosMsg.getData(), false, false);

			// Click on continue Button
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Click On next Button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Complete Button");

			// Asserting Out of Sequence Warning Message
			Assertions.verify(endorsePolicyPage.oosMsg.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Out of Sequence message is disaplyed as : " + endorsePolicyPage.oosMsg.getData(), false, false);

			// Click on continue Button
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Click on No, leave Renewal Account As it is button
			if (endorsePolicyPage.leaveRenewalAsIsButton.checkIfElementIsPresent()
					&& endorsePolicyPage.leaveRenewalAsIsButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.leaveRenewalAsIsButton.scrollToElement();
				endorsePolicyPage.leaveRenewalAsIsButton.click();
			}

			// Click on Close Button
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Asserting policy Effective Date and Policy Expiration Date
			Assertions.verify(policySummaryPage.effectiveDate.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Policy Effective date is disaplyed as : "
							+ policySummaryPage.effectiveDate.getData().substring(0, 10),
					false, false);
			Assertions.verify(policySummaryPage.expirationDate.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Policy Expiration date is disaplyed as : "
							+ policySummaryPage.expirationDate.getData().substring(0, 10),
					false, false);
			Assertions.addInfo("Scenario 11", "Scenario 11 Ended");

			// Initiate PB ENDT with expiration date in future
			// Click on Endorse PB Link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();

			// Click on Ok button
			if (policySummaryPage.okButton.checkIfElementIsPresent()
					&& policySummaryPage.okButton.checkIfElementIsDisplayed()) {
				policySummaryPage.okButton.scrollToElement();
				policySummaryPage.okButton.click();
			}

			// Entering Endorsement effective date
			testData = data.get(data_Value4);
			Assertions.addInfo("Scenario 12",
					"PB Transaction - Change Expiration Date inbetween extended period of change");
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// Entering Policy Expiration date
			endorsePolicyPage.policyExpirationDate.scrollToElement();
			endorsePolicyPage.policyExpirationDate.setData(testData.get("PolicyExpirationDate"));
			endorsePolicyPage.policyExpirationDate.tab();

			// Click on Change Expiration Button
			endorsePolicyPage.changeExpirationDate.scrollToElement();
			endorsePolicyPage.changeExpirationDate.click();

			// Asserting Out of Sequence Warning Message
			Assertions.verify(endorsePolicyPage.oosMsg.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Out of Sequence message is disaplyed as : " + endorsePolicyPage.oosMsg.getData(), false, false);

			// Click on continue Button
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Click On next Button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Complete Button");

			// Asserting Out of Sequence Warning Message
			Assertions.verify(endorsePolicyPage.oosMsg.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Out of Sequence message is disaplyed as : " + endorsePolicyPage.oosMsg.getData(), false, false);

			// Click on continue Button
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Click on No, leave Renewal Account As it is button
			if (endorsePolicyPage.leaveRenewalAsIsButton.checkIfElementIsPresent()
					&& endorsePolicyPage.leaveRenewalAsIsButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.leaveRenewalAsIsButton.scrollToElement();
				endorsePolicyPage.leaveRenewalAsIsButton.click();
			}

			// Click on Close Button
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Asserting policy Effective Date and Policy Expiration Date
			Assertions.verify(policySummaryPage.effectiveDate.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Policy Effective date is disaplyed as : "
							+ policySummaryPage.effectiveDate.getData().substring(0, 10),
					false, false);
			Assertions.verify(policySummaryPage.expirationDate.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Policy Expiration date is disaplyed as : "
							+ policySummaryPage.expirationDate.getData().substring(0, 10),
					false, false);
			Assertions.addInfo("Scenario 12", "Scenario 12 Ended");

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBFLTC008 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBFLTC008 ", "Executed Successfully");
			}
		}
	}

}
