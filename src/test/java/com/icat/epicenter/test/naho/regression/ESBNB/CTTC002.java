/** Program Description: Verifying SLTF values on Quote document and Account overview page are matching, validating Healthy Homes Fund Values is $12 in VieworPrintFullquote Page and added IO-20814 and IO-20819 and IO-21410.verify the AOWH deductible can be selected independent of the AOP deductible and not defaulted to AOP deductible as a producer.
 *  Author			   : Priyanka S
 *  Date of Creation   : 12/27/2021
 **/

package com.icat.epicenter.test.naho.regression.ESBNB;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class CTTC002 extends AbstractNAHOTest {

	public CTTC002() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBNB/CTTC002.xls";
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
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		BindRequestSubmittedPage bindRequestSubmittedPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		LoginPage loginPage = new LoginPage();

		// Initializing the variables
		Map<String, String> testData;
		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
		int dataValue4 = 3;
		testData = data.get(dataValue1);
		String quoteNumber;
		int locNo = 1;
		int bldgNo = 1;
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			Assertions.addInfo("Scenario 01",
					"Verifying if the updated verbiage of the Eligibity Question is displayed");
			Assertions.verify(
					eligibilityPage.underWritingQuestions.formatDynamicPath("2").checkIfElementIsPresent()
							&& eligibilityPage.underWritingQuestions.formatDynamicPath("2").checkIfElementIsDisplayed()
							&& eligibilityPage.underWritingQuestions
									.formatDynamicPath("2").getData().contains("replacement cost"),
					true, "Eligibility Page",
					"The Element is present and the verbiage is: "
							+ eligibilityPage.underWritingQuestions.formatDynamicPath("2").getData()
							+ eligibilityPage.homeVacant1.getData() + eligibilityPage.homeVacant2.getData(),
					false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
				Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			Assertions.addInfo("Scenario 02", "Verifying and Asserting AOWH minimum value 1%");
			Assertions.verify(createQuotePage.aowhDeductibleData.getData().contains("1%"), true, "Create Quote Page",
					"AOWH minium value is " + createQuotePage.aowhDeductibleData.getData(), false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Calculating AOWH minimum deductible
			String aowhDeductibleValue = createQuotePage.aowhDeductibleData.getData().replace("%", "");
			String CovAvalue = createQuotePage.coverageADwelling.getData().replace(",", "");
			Double d_aowhDeductibleValue = Double.parseDouble(aowhDeductibleValue);
			Double d_CovAvalue = Double.parseDouble(CovAvalue);
			double calAOWH = (d_aowhDeductibleValue * d_CovAvalue) / 100;

			// Verifying and asserting both percentage and flat dollar values present in the
			// AOWH deductible dropdown
			createQuotePage.aowhDeductibleArrow.scrollToElement();
			createQuotePage.aowhDeductibleArrow.click();
			testData = data.get(dataValue2);
			Assertions.addInfo("Scenario 03",
					"Verifying and asserting both percentage and flat dollar values present in the AOWH deductible dropdown");
			Assertions.verify(
					createQuotePage.aowhDeductibleOptionNAHO
							.formatDynamicPath(testData.get("AOWHDeductibleValue")).checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"AOWH percentage value show in dropdown, AOWH value is " + createQuotePage.aowhDeductibleOptionNAHO
							.formatDynamicPath(testData.get("AOWHDeductibleValue")).getData() + " displayed",
					false, false);
			testData = data.get(dataValue3);
			Assertions
					.verify(createQuotePage.aowhDeductibleOptionNAHO
							.formatDynamicPath(testData.get("AOWHDeductibleValue")).checkIfElementIsDisplayed(), true,
							"Create Quote Page",
							"AOWH flat dollars values shows in dropdown AOWH value is "
									+ createQuotePage.aowhDeductibleOptionNAHO
											.formatDynamicPath(testData.get("AOWHDeductibleValue")).getData()
									+ " displayed",
							false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Verifying and Asserting the AOWH deductible can be selected by the user.
			Assertions.addInfo("Scenario 04",
					"Verifying and Asserting the AOWH deductible can be selected by the user");
			testData = data.get(dataValue2);
			createQuotePage.aowhDeductibleOptionNAHO.formatDynamicPath(testData.get("AOWHDeductibleValue"))
					.scrollToElement();
			createQuotePage.aowhDeductibleOptionNAHO.formatDynamicPath(testData.get("AOWHDeductibleValue")).click();
			Assertions.verify(createQuotePage.aowhDeductibleData.getData().equals(testData.get("AOWHDeductibleValue")),
					true, "Create Quote Page", "User is able to select AOWH deductible percenatge value is "
							+ createQuotePage.aowhDeductibleData.getData() + " displayed",
					false, false);

			// Selecting AOWH Deductible flat dollars
			testData = data.get(dataValue3);
			createQuotePage.aowhDeductibleArrow.scrollToElement();
			createQuotePage.aowhDeductibleArrow.click();
			String aowhDeductible = createQuotePage.aowhDeductibleOptionNAHO
					.formatDynamicPath(testData.get("AOWHDeductibleValue")).getData().replace("$", "").replace(",", "");
			Double d_aowhDeductible = Double.parseDouble(aowhDeductible);
			createQuotePage.aowhDeductibleOptionNAHO.formatDynamicPath(testData.get("AOWHDeductibleValue"))
					.scrollToElement();
			createQuotePage.aowhDeductibleOptionNAHO.formatDynamicPath(testData.get("AOWHDeductibleValue")).click();
			Assertions.verify(createQuotePage.aowhDeductibleData.getData().equals(testData.get("AOWHDeductibleValue")),
					true, "Create Quote Page", "User is able to select AOWH deductible flat dollars value is "
							+ createQuotePage.aowhDeductibleData.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Verify that in the AOWH dropdown flat dollar values are greater than or equal
			// to the calculated minimum deductible and percentage values are greater than
			// or equal to the minimum deductible in the MinimumDeductibleNAHOAOWH table.
			Assertions.addInfo("Scenari 05",
					"Verify that in the AOWH dropdown flat dollar values are greater than or equal to the calculated minimum deductible and percentage values are greater than or equal to the minimum deductible in the MinimumDeductibleNAHOAOWH table.");
			if (d_aowhDeductible > calAOWH) {
				Assertions.passTest("Create Quote Page",
						"AOWH deductible dropdown value shows greter than or equal to calculated AOWH deductible value, actual AOWH deductible value is "
								+ "$" + d_aowhDeductible);
				Assertions.passTest("Create Quote Page", "Calculated AOWH deductible value " + calAOWH);
			} else {
				Assertions.verify(calAOWH < d_aowhDeductible, true, "Create Quote Page",
						"AOWH dedcutible dropdown shows less than calculated AOWH deductible value", false, false);
			}
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Verify that in the AOP dropdown flat dollar values are greater than or equal
			// to the minimum deductible in the MinimumDeductibleNAHOAOP table.
			String actualAOPDeductible = createQuotePage.aopDeductibleData.getData().replace("$", "").replace(",", "");
			String raterSheetMinimumDeductibleAOP = testData.get("MiniumumAOPDeductible").replace("$", "").replace(",",
					"");
			Double d_actualAOPDeductible = Double.parseDouble(actualAOPDeductible);
			Double d_raterSheetMinimumDeductibleAOP = Double.parseDouble(raterSheetMinimumDeductibleAOP);
			Assertions.addInfo("Scenario 06",
					"Verify that in the AOP dropdown flat dollar values are greater than or equal to the minimum deductible in the MinimumDeductibleNAHOAOP table");
			if (d_actualAOPDeductible.equals(d_raterSheetMinimumDeductibleAOP)) {
				Assertions.passTest("Create Quote Page",
						" Verifying AOP dropdown flat dollar values are greater than or equal to the minimum deductible, actual AOP Deductible value is "
								+ "$" + actualAOPDeductible);
				Assertions.passTest("Create Quote Page",
						"Rater Sheet Minimum deductible AOP value is  " + "$" + raterSheetMinimumDeductibleAOP);
			} else {
				Assertions.verify(d_raterSheetMinimumDeductibleAOP < d_actualAOPDeductible, true, "Create Quote Page",
						"AOP dedcutible dropdown shows less than expected AOP deductible value " + "$"
								+ d_actualAOPDeductible,
						false, false);
			}
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Calculating AOWH minimum deductible
			testData = data.get(dataValue4);
			String nsDeductibleValue = createQuotePage.namedStormData.getData().replace("%", "");
			Double d_nsDeductibleValue = Double.parseDouble(nsDeductibleValue);
			double calNSDeductible = (d_nsDeductibleValue * d_CovAvalue) / 100;
			createQuotePage.namedStormArrow_NAHO.scrollToElement();
			createQuotePage.namedStormArrow_NAHO.click();

			String actualNSDeductible = createQuotePage.namedStormDeductibleOption
					.formatDynamicPath(testData.get("NamedStormValue")).getData().replace("$", "").replace(",", "");
			Double d_actualNSDeductible = Double.parseDouble(actualNSDeductible);
			createQuotePage.namedStormDeductibleOption.formatDynamicPath(testData.get("NamedStormValue")).click();

			// Verify that in the NS dropdown flat dollar values are greater than or equal
			// to the calculated minimum deductible and percentage values are greater than
			// or equal to the minimum deductible in the MinimumDeductibleNAHONS table
			Assertions.addInfo("Scenario 07",
					"Verify that in the NS dropdown flat dollar values are greater than or equal to the calculated minimum deductible and percentage values are greater than or equal to the minimum deductible in the MinimumDeductibleNAHONS table");
			if (d_actualNSDeductible > calNSDeductible) {
				Assertions.passTest("Create Quote Page",
						"NS deductible dropdown value shows greter than or equal to calculated NS deductible value, actual NS deductible value is "
								+ "$" + d_actualNSDeductible);
				Assertions.passTest("Create Quote Page", "Calculated NS deductible value " + calNSDeductible);
			} else {
				Assertions.verify(calNSDeductible < d_actualNSDeductible, true, "Create Quote Page",
						"NS dedcutible dropdown shows less than calculated NS deductible value", false, false);
			}
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Selecting AOP
			createQuotePage.enterDeductiblesNAHO(testData);
			Assertions.passTest("Create Quote Page", "AOP deductible selected " + testData.get("AOPDeductibleValue"));
			Assertions.passTest("Create Quote Page", "AOP deductible value selected successfully");

			// Verifying and asserting the AOWH deductible is not affected by the selected
			// AOP deductible.
			Assertions.addInfo("Scenario 08",
					"Verifying and asserting the AOWH deductible is not affected by the selected AOP deductible");
			Assertions.verify(createQuotePage.aowhDeductibleData.getData().replace(",", ""), "$" + aowhDeductible,
					"Create Quote Page", "After selecting AOP deductible, AOWH value not changed the AOWH value is $"
							+ aowhDeductible + " displayed",
					false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 ended");

			// Enter quote details
			testData = data.get(dataValue1);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Verifying if the CovC and CovD hardstop error message per ticket IO-20814
			Assertions.addInfo("Scenario 09", "Verifying if the CovC and CovD hardstop error message as Producer");
			if (createQuotePage.errorMessage.formatDynamicPath(
					"The quoted building is ineligible due to a Coverage C limit greater than 70% of Coverage A.")
					.checkIfElementIsPresent()
					&& createQuotePage.errorMessage.formatDynamicPath(
							"The quoted building is ineligible due to a Coverage C limit greater than 70% of Coverage A.")
							.checkIfElementIsDisplayed()) {
				Assertions.verify(createQuotePage.errorMessage.formatDynamicPath(
						"The quoted building is ineligible due to a Coverage C limit greater than 70% of Coverage A.")
						.checkIfElementIsDisplayed(), true, "Create Quote Page",
						"The Hard Stop message for Coverage C limit greater than 70% of Coverage A is displayed", false,
						false);
			} else {
				Assertions.verify(createQuotePage.errorMessage.formatDynamicPath(
						"The quoted building is ineligible due to a Coverage C limit greater than 70% of Coverage A.")
						.checkIfElementIsDisplayed(), true, "Create Quote Page",
						"The Hard Stop message for Coverage C limit greater than 70% of Coverage A is not displayed",
						false, false);
			}
			if (createQuotePage.errorMessage.formatDynamicPath(
					"The quoted building is ineligible due to a Coverage D limit greater than 40% of Coverage A.")
					.checkIfElementIsPresent()
					&& createQuotePage.errorMessage.formatDynamicPath(
							"The quoted building is ineligible due to a Coverage D limit greater than 40% of Coverage A.")
							.checkIfElementIsDisplayed()) {
				Assertions.verify(createQuotePage.errorMessage.formatDynamicPath(
						"The quoted building is ineligible due to a Coverage D limit greater than 40% of Coverage A.")
						.checkIfElementIsDisplayed(), true, "Create Quote Page",
						"The Hard Stop message for Coverage D limit greater than 40% of Coverage A is displayed", false,
						false);
			} else {
				Assertions.verify(createQuotePage.errorMessage.formatDynamicPath(
						"The quoted building is ineligible due to a Coverage D limit greater than 40% of Coverage A.")
						.checkIfElementIsDisplayed(), true, "Create Quote Page",
						"The Hard Stop message for Coverage D limit greater than 40% of Coverage A is not displayed",
						false, false);
			}
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			testData = data.get(dataValue2);
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			if (referQuotePage.contactEmail.checkIfElementIsPresent()
					&& referQuotePage.contactEmail.checkIfElementIsDisplayed()) {
				referQuotePage.contactEmail.clearData();
				referQuotePage.contactEmail.setData("hiho1@icat.com");
				referQuotePage.comments.setData("Test");
				referQuotePage.referQuote.scrollToElement();
				referQuotePage.referQuote.click();

				// Fetching Quote Number
				quoteNumber = referQuotePage.quoteNumberforReferral.getData();
				Assertions.passTest("Referral Page", "Reerred quote number " + quoteNumber);

				// Signing out as Producer
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();

				// Signing in as USM
				loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));

				// Searching the referred quote
				homePage.searchQuote(quoteNumber);
				Assertions.passTest("Home Page", "Referred Quote searched successfully");

				// Opening the referral
				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();

				// Approving referral
				Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
						"Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();

				// click on approve in Approve Decline Quote page
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");

				// verifying referral complete message
				Assertions.verify(referralPage.referralCompleteMsg.checkIfElementIsDisplayed(), true, "Referral Page",
						referralPage.referralCompleteMsg.getData() + " message is verified successfully", false, false);
				referralPage.close.scrollToElement();
				referralPage.close.click();

				// Signing out as USM
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();

				// Signing in as Producer
				loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));

				homePage.searchQuoteByProducer(quoteNumber);
			}

			// Getting Quote number
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number is " + quoteNumber);

			// Verifying AOWH present in quote tree
			Assertions.addInfo("Scenario 10", "Verifying AOWH present in quote tree");
			Assertions.verify(
					accountOverviewPage.quoteTreeAopNsAowh.formatDynamicPath("AOWH").checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"AOWH displayed in quote tree AOWH value is " + accountOverviewPage.quoteTreeAopNsAowh
							.formatDynamicPath("AOWH").getData().substring(46, 53),
					false, false);
			Assertions.addInfo("Scenario 10", "Scenario 10 Ended");

			// Verifying All other wind and Hail options are present
			Assertions.addInfo("Scenario 11", "Verifying All other wind and Hail options are present");
			Assertions.verify(accountOverviewPage.aowhLable.getData().equals("All Other Wind & Hail"), true,
					"Account Overview Page",
					"All Other Wind & Hail option present is " + accountOverviewPage.aowhLable.getData() + " verified",
					false, false);
			Assertions.verify(
					accountOverviewPage.deductibleOptionsNAHO.formatDynamicPath("All Other Wind & Hail", 2)
							.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "All Other Wind & Hail Option1 present is verified", false, false);
			Assertions.verify(
					accountOverviewPage.deductibleOptionsNAHO.formatDynamicPath("All Other Wind & Hail", 3)
							.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "All Other Wind & Hail Option2 present is verified", false, false);
			Assertions.verify(
					accountOverviewPage.deductibleOptionsNAHO.formatDynamicPath("All Other Wind & Hail", 4)
							.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "All Other Wind & Hail Option3 present is verified", false, false);
			Assertions.addInfo("Scenario 11", "Scenario 11 Ended");

			// Entering View/Print Full Quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print Full quote link");

			// Asserting Value for Healthy Homes Fund
			Assertions.addInfo("Scenario 12",
					"Verify the Healthy Homes funds value is displayed on ViewOrPrintFullQuote page");
			Assertions.verify("$12.00", viewOrPrintFullQuotePage.healthyHomesFund.getData(),
					"ViewOrPrintFullQuote Page",
					"Healthy Homes Fund Value : " + viewOrPrintFullQuotePage.healthyHomesFund.getData(), false, false);
			Assertions.addInfo("Scenario 12", "Scenario 12 Ended");
			String premiumValue = viewOrPrintFullQuotePage.premiumValue.getData().replace("$", "").replace(",", "");
			String healthyHomesFund = viewOrPrintFullQuotePage.healthyHomesFund.getData().replace("$", "")
					.replace(",", "").replace("%", "");
			String surplusCont = viewOrPrintFullQuotePage.surplusContributionValue.getData().replace("$", "")
					.replace("%", "");

			double premiumData = Double.valueOf(premiumValue).doubleValue();
			double healthyHomesFundData = Double.valueOf(healthyHomesFund).doubleValue();
			double surplusContData = Double.parseDouble(surplusCont);
			double sltfFee = premiumData + surplusContData;
			double healthyHomesFundValues = (healthyHomesFundData);
			double sltfFeeValue = (sltfFee * 4 / 100) + healthyHomesFundValues;

			accountOverviewPage.goBackBtn.scrollToElement();
			accountOverviewPage.goBackBtn.click();

			// Asserting SLTF Value
			String sltfData = accountOverviewPage.sltfValue.getData().replace("$", "");
			double actalSltfData = Double.parseDouble(sltfData);
			Assertions.addInfo("Scenario 13", "Verify the SLTF value is displayed on Account overview page");
			if (Precision.round(Math.abs(Precision.round(actalSltfData, 2) - Precision.round(sltfFeeValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated SLTF Data :  " + "$" + Precision.round(sltfFeeValue, 2));
				Assertions.passTest("Account Overview Page", "Actual SLTF Data : " + "$" + actalSltfData);
			} else {
				Assertions.verify(actalSltfData, sltfFeeValue, "Account Overview Page",
						"The Difference between actual SLTF value and calculated SLTF value is more than 0.05", false,
						false);
			}
			Assertions.addInfo("Scenario 13", "Scenario 13 Ended");

			// Click on Request bind
			testData = data.get(dataValue1);
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Clicking on answer No button
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered successfully");

			// Entering bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Details Entered successfully");

			// Bind Request Submitted Page
			// Asserting Request Received Information
			Assertions.addInfo("Scenario 14", "Verify Request Received Message is displayed Request Submitted Page");
			Assertions.verify(
					bindRequestSubmittedPage.requestReceivedMsg.formatDynamicPath("Subscription Agreement")
							.checkIfElementIsDisplayed(),
					true, "Bind Request Submitted page",
					"Request Received 1 : " + bindRequestSubmittedPage.requestReceivedMsg
							.formatDynamicPath("Subscription Agreement").getData(),
					false, false);
			Assertions.addInfo("Scenario 14", "Scenario 14 Ended");
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Signin as USM
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));

			// creating New account
			testData = data.get(dataValue1);
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			Assertions.addInfo("Scenario 15",
					"Verifying if the updated verbiage of the Eligibity Question is displayed");
			Assertions.verify(
					eligibilityPage.underWritingQuestions.formatDynamicPath("2").checkIfElementIsPresent()
							&& eligibilityPage.underWritingQuestions.formatDynamicPath("2").checkIfElementIsDisplayed()
							&& eligibilityPage.underWritingQuestions
									.formatDynamicPath("2").getData().contains("replacement cost"),
					true, "Eligibility Page",
					"The Element is present and the verbiage is: "
							+ eligibilityPage.underWritingQuestions.formatDynamicPath("2").getData()
							+ eligibilityPage.homeVacant1.getData(),
					false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");
			Assertions.addInfo("Scenario 15", "Scenario 15 Ended");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.addDwellingDetails(testData, 1, 1);
			dwellingPage.addRoofDetails(testData, 1, 1);
			dwellingPage.enterProtectionDiscountsDetails(testData, 1, 1);
			if (dwellingPage.dwellingValuesLink.checkIfElementIsPresent()
					&& dwellingPage.dwellingValuesLink.checkIfElementIsDisplayed()) {
				dwellingPage.dwellingValuesLink.waitTillPresenceOfElement(60);
				dwellingPage.dwellingValuesLink.waitTillVisibilityOfElement(60);
				dwellingPage.dwellingValuesLink.waitTillElementisEnabled(60);
				dwellingPage.dwellingValuesLink.waitTillButtonIsClickable(60);
				dwellingPage.dwellingValuesLink.scrollToElement();
				dwellingPage.dwellingValuesLink.click();
			}
			if (dwellingPage.coverageADwelling.checkIfElementIsPresent()
					&& dwellingPage.coverageADwelling.checkIfElementIsDisplayed()) {
				if (!testData.get("L" + locNo + "D" + locNo + "-DwellingCovA").equals("")) {
					dwellingPage.coverageADwelling.waitTillPresenceOfElement(60);
					dwellingPage.coverageADwelling.waitTillVisibilityOfElement(60);
					dwellingPage.coverageADwelling.waitTillElementisEnabled(60);
					dwellingPage.coverageADwelling.scrollToElement();
					dwellingPage.coverageADwelling.setData(testData.get("L" + locNo + "D" + bldgNo + "-DwellingCovA"));
					dwellingPage.coverageADwelling.tab();
				}

				if (!testData.get("L" + locNo + "D" + locNo + "-DwellingCovB").equals("")) {
					dwellingPage.coverageBOtherStructures.waitTillPresenceOfElement(60);
					dwellingPage.coverageBOtherStructures.waitTillVisibilityOfElement(60);
					dwellingPage.coverageBOtherStructures.waitTillElementisEnabled(60);
					dwellingPage.coverageBOtherStructures.scrollToElement();
					dwellingPage.coverageBOtherStructures
							.setData(testData.get("L" + locNo + "D" + bldgNo + "-DwellingCovB"));
					dwellingPage.coverageBOtherStructures.tab();
				}

				if (!testData.get("L" + locNo + "D" + locNo + "-DwellingCovC").equals("")) {
					dwellingPage.coverageCPersonalProperty.waitTillPresenceOfElement(60);
					dwellingPage.coverageCPersonalProperty.waitTillVisibilityOfElement(60);
					dwellingPage.coverageCPersonalProperty.waitTillElementisEnabled(60);
					dwellingPage.coverageCPersonalProperty.scrollToElement();
					dwellingPage.coverageCPersonalProperty
							.setData(testData.get("L" + locNo + "D" + bldgNo + "-DwellingCovC"));
					dwellingPage.coverageCPersonalProperty.tab();
				}
				if (!testData.get("L" + locNo + "D" + locNo + "-DwellingCovD").equals("")) {
					dwellingPage.coverageDFairRental.waitTillPresenceOfElement(60);
					dwellingPage.coverageDFairRental.waitTillVisibilityOfElement(60);
					dwellingPage.coverageDFairRental.waitTillElementisEnabled(60);
					dwellingPage.coverageDFairRental.scrollToElement();
					dwellingPage.coverageDFairRental
							.setData(testData.get("L" + locNo + "D" + bldgNo + "-DwellingCovD"));
					dwellingPage.coverageDFairRental.tab();
				}
			}
			dwellingPage.reviewDwelling.waitTillPresenceOfElement(60);
			dwellingPage.reviewDwelling.waitTillVisibilityOfElement(60);
			waitTime(2);
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			if (dwellingPage.override.checkIfElementIsPresent() && dwellingPage.override.checkIfElementIsDisplayed()) {
				dwellingPage.override.scrollToElement();
				waitTime(2);
				dwellingPage.override.click();
			}
			if (dwellingPage.createQuote.checkIfElementIsPresent()
					|| dwellingPage.createQuote.checkIfElementIsDisplayed()) {
				dwellingPage.createQuote.waitTillPresenceOfElement(60);
				dwellingPage.createQuote.waitTillVisibilityOfElement(60);
				dwellingPage.createQuote.waitTillButtonIsClickable(60);
				dwellingPage.createQuote.scrollToElement();
				dwellingPage.createQuote.click();
			}
			Assertions.passTest("Dwelling Page", "testData details entered successfully");

			// Entering prior loss details
			testData = data.get(dataValue2);
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
				Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Verifying if the CovC and CovD warning error message per ticket IO-20814
			Assertions.addInfo("Scenario 16",
					"Verifying if the CovC and CovD warning error message is displayed as USM");
			Assertions.verify(createQuotePage.errorMessageWarningPage
					.formatDynamicPath("Coverage C limit greater than 70% of Coverage A.").checkIfElementIsPresent()
					&& createQuotePage.errorMessageWarningPage
							.formatDynamicPath("Coverage C limit greater than 70% of Coverage A.")
							.checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The warning message for Coverage C limit greater than 70% of Coverage A is displayed", false,
					false);
			Assertions.verify(createQuotePage.errorMessageWarningPage
					.formatDynamicPath("Coverage D limit greater than 40% of Coverage A.").checkIfElementIsPresent()
					&& createQuotePage.errorMessageWarningPage
							.formatDynamicPath("Coverage D limit greater than 40% of Coverage A.")
							.checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The warning message for Coverage D limit greater than 40% of Coverage A is displayed", false,
					false);
			Assertions.addInfo("Scenario 16", "Scenario 16 Ended");

			// Adding ticket IO-21410
			// Verifying prior loss warning message when 2 or more prior loss added with
			// prior loss
			// type"water damage not due to weather or sprinkler leakage
			// Warning message is "This account is ineligible due to two or more prior
			// losses selected as ?Water Damage Not Due to Weather? and/or "Sprinkler
			// Leakage"
			Assertions.addInfo("Scenario 17",
					"Verifying warning message when 2 or more prior loss added with prior loss type\"water damage not due to weather or sprinkler leakage.");
			Assertions.verify(
					createQuotePage.errorMessage.formatDynamicPath("Water Damage Not Due to Weather").getData()
							.contains("Water Damage Not Due to Weather"),
					true, "Create quote page",
					"The warning message is " + createQuotePage.errorMessage
							.formatDynamicPath("Water Damage Not Due to Weather").getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 17", "Scenario 17 Ended");

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Fetching Quote Number
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page",
					"The Quote is created as USM and the Quote Number is: " + quoteNumber);

			// Click on edit prior loss link
			accountOverviewPage.priorLossEditLink.scrollToElement();
			accountOverviewPage.priorLossEditLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit prior loss link successfully");

			// change any one prior loss
			testData = data.get(dataValue3);
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Priorloss Page",
					"Priorloss page loaded successfully", false, false);
			priorLossesPage.typeOfLossArrow.scrollToElement();
			priorLossesPage.typeOfLossArrow.click();
			priorLossesPage.typeOfLossOption.formatDynamicPath(1, testData.get("PriorLossType1")).scrollToElement();
			priorLossesPage.typeOfLossOption.formatDynamicPath(1, testData.get("PriorLossType1")).click();
			priorLossesPage.continueButton.scrollToElement();
			priorLossesPage.continueButton.click();
			priorLossesPage.goToAccountOverviewBtn.scrollToElement();
			priorLossesPage.goToAccountOverviewBtn.click();
			Assertions.passTest("Prior Losses Page", "Clicked on gotoaccount overview button successfully");

			// Click on create another quote
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account overview page loaded successfully", false, false);
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();

			// Click on get A quote button
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// below scenario will fail because of IO-21410 this ticket not yet deployed
			// verifying the absence of prior loss warning message when selecting 2 or more
			// prior loss the prior loss type
			// other than?Water Damage Not Due to Weather? and/or "Sprinkler Leakage"
			Assertions.addInfo("Scenario 18",
					"Verifying the absence of warning message when selecting 2 or more prior loss the prior loss type other than?Water Damage Not Due to Weather? and/or \"Sprinkler Leakage");
			Assertions.verify(
					createQuotePage.errorMessage.formatDynamicPath("Water Damage Not Due to Weather")
							.checkIfElementIsPresent(),
					false, "Create quote page", "The prior loss warning message is not displayed", false, false);
			Assertions.addInfo("Scenario 18", "Scenario 18 Ended");

			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();

			// Sign out as usm
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// login as producer
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));

			// creating New account
			testData = data.get(dataValue1);
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);
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
			testData = data.get(dataValue2);
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
				Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);

			// Verifying prior loss hard stop message when 2 or more prior loss added with
			// prior loss
			// type"water damage not due to weather or sprinkler leakage as producer
			// The hard stop message is This account is ineligible due to two or more prior
			// losses selected as ?Water Damage Not Due to Weather? and/or "Sprinkler
			// Leakage"
			Assertions.addInfo("Scenario 19",
					"Asserting hard stop message when 2 or more prior loss added with prior loss type is water damage not due to weather or sprinkler leakage as producer ");
			Assertions.verify(createQuotePage.globalErr.getData().contains("Water Damage Not Due to Weather"), true,
					"Create Quote Page",
					"The priorloss hard stop message is " + createQuotePage.globalErr.getData() + " displayed", false,
					false);
			Assertions.addInfo("Scenario 19", "Scenario 19 Ended");

			// Click on previous link
			createQuotePage.previous.scrollToElement();
			createQuotePage.previous.click();

			// Click on edit prior loss link
			accountOverviewPage.priorLossEditLink.scrollToElement();
			accountOverviewPage.priorLossEditLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit prior loss link successfully");

			// change any one prior loss
			testData = data.get(dataValue3);
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Priorloss Page",
					"Priorloss page loaded successfully", false, false);
			priorLossesPage.typeOfLossArrow.scrollToElement();
			priorLossesPage.typeOfLossArrow.click();
			priorLossesPage.typeOfLossOption.formatDynamicPath(1, testData.get("PriorLossType1")).scrollToElement();
			priorLossesPage.typeOfLossOption.formatDynamicPath(1, testData.get("PriorLossType1")).click();
			priorLossesPage.continueButton.scrollToElement();
			priorLossesPage.continueButton.click();
			Assertions.passTest("Prior Losses Page", "Clicked on continue button successfully");

			// Click on coverage link
			accountOverviewPage.coverageLink.scrollToElement();
			accountOverviewPage.coverageLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on coverage link successfully");

			// Click on get A quote button
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// below scenario will fail because of IO-21410 this ticket not yet deployed
			// verifying the absence of priorloss hard stop message when selecting 2 or more
			// prior loss the prior losses type
			// other than?Water Damage Not Due to Weather? and/or "Sprinkler Leakage"
			Assertions.addInfo("Scenario 20",
					"Verifying the absence of hard stop message when selecting 2 or more prior loss the prior losses type other than 'Water Damage Not Due to Weather? and/or Sprinkler Leakage ");
			Assertions.verify(createQuotePage.globalErr.checkIfElementIsPresent(), false, "Create Quote Page",
					"The prior loss hard stop message not displayed", false, false);
			Assertions.addInfo("Scenario 20", "Scenario 20 Ended");

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("CTTC002 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("CTTC002 ", "Executed Successfully");
			}
		}
	}
}
