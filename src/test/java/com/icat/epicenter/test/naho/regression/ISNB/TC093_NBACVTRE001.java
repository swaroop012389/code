/** Program Description: As USM, Check if the NAHO Roof validation is trigerred as per the given condition year built 16-20, roof cladding =Architectural Shingle
 * and processing PB endorsement updating year built 15 years older and roof cladding = Normal Shingle
 *  Author			   : Pavan Mule
 *  Date of Creation   : 07/18/2024
 **/
package com.icat.epicenter.test.naho.regression.ISNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.ModifyForms;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC093_NBACVTRE001 extends AbstractNAHOTest {

	public TC093_NBACVTRE001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBACVTRE001.xls";
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
		ModifyForms modifyForms = new ModifyForms();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();

		// Initializing variable
		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
		int dataValue4 = 3;
		Map<String, String> testData = data.get(dataValue1);
		String quoteNumber;
		int quoteLen;
		String policyNumber;
		boolean isTestPassed = false;

		try {
			// Creating a new account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Checking the processing of Zipcode
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering dwelling details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering Prior Loss details
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);
			Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");

			// Click on modify form button
			Assertions.verify(createQuotePage.modifyForms.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();
			Assertions.passTest("Create Quote Page", "Clicked on modify form button successfully");

			// Verifying AVC(Actual cash value) roof form automatically attached when year
			// built 16-20 years older,roof cladding = Architectural Shingle and year roof
			// last
			// replaced same as year built
			Assertions.addInfo("Scenario 01", "Verifying AVC(Actual cash value) roof form automatically attached");
			Assertions.verify(modifyForms.actualCashValueRoof.checkIfElementIsSelected(), true, "Modify Form Page",
					"Actual cash value roof form attached automaticlly", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Click on override button
			modifyForms.override.scrollToElement();
			modifyForms.override.click();
			Assertions.passTest("Modify Form Page", "Clicked on override button successfully");

			// Asserting and Verifying Actual cash value warning message ='Roof Excluded
			// From Replacement Cost and Covered Only at Actual Cash Value.'
			// when year built 16-20 years older,roof cladding = Architectural Shingle and
			// year
			// roof last replaced same as year built
			Assertions.addInfo("Scenario 02", "Asserting and Verifying Actual cash value warning message");
			Assertions.verify(
					createQuotePage.errorMessageWarningPage.formatDynamicPath("Actual Cash Value").getData()
							.equals("Roof Excluded From Replacement Cost and Covered Only at Actual Cash Value."),
					true, "Create Quote Page",
					"Warning message is "
							+ createQuotePage.errorMessageWarningPage.formatDynamicPath("Actual Cash Value").getData()
							+ " is displayed when Roof Cladding is " + testData.get("L1D1-DwellingRoofCladding")
							+ " and Year Built is " + testData.get("L1D1-DwellingYearBuilt"),
					false, false);
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Getting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number1 is " + quoteNumber);

			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View Print Full Quote Page", "Clicked on view print full quote link successfully", false, false);

			// Asserting and verifying roof coverage as 'actual cash value'on view print
			// full quote page
			// when year built 16-20 years older,roof cladding = Architectural Shingle and
			// year
			// roof last replaced same as year built
			Assertions.addInfo("Scenario 03", "Asserting and verifying roof coverage as 'actual cash value'");
			Assertions.verify(
					viewOrPrintFullQuotePage.roofCoverage.formatDynamicPath(1).getData().equals("Actual Cash Value"),
					true, "View Print Full Quote Page", "Roof coverage is "
							+ viewOrPrintFullQuotePage.roofCoverage.formatDynamicPath(1).getData() + " displayed",
					false, false);
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on edit deductible and limits
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit deductible and limits successfully");

			// Click on modify form button
			Assertions.verify(createQuotePage.modifyForms.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();
			Assertions.passTest("Create Quote Page", "Clicked on modify form button successfully");

			// Select total roof exclusion
			modifyForms.totalRoofExclusion.scrollToElement();
			modifyForms.totalRoofExclusion.select();
			Assertions.verify(modifyForms.totalRoofExclusion.checkIfElementIsSelected(), true, "Modify Form Page",
					"Total roof exclusion selected successfully", false, false);

			// Verifying Actual cash value deselected automatically,when total roof
			// exclusion selected
			Assertions.addInfo("Scenario 04",
					"Actual cash value deselected automatically,when total roof exclusion selected");
			Assertions.verify(modifyForms.actualCashValueRoof.checkIfElementIsSelected(), false, "Modify Form Page",
					"Actual cash value deselected automatically", false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Click on override button
			modifyForms.override.scrollToElement();
			modifyForms.override.click();
			Assertions.passTest("Modify Form Page", "Clicked on override button successfully");

			// Getting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number2 is " + quoteNumber);

			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View Print Full Quote Page", "Clicked on view print full quote link successfully", false, false);

			// Asserting and Verifying roof coverage as excluded
			Assertions.addInfo("Scenario 05", "Asserting and verifying roof coverage as 'Excluded'");
			Assertions.verify(viewOrPrintFullQuotePage.roofCoverage.formatDynamicPath(1).getData().equals("Excluded"),
					true, "View Print Full Quote Page", "Roof coverage is "
							+ viewOrPrintFullQuotePage.roofCoverage.formatDynamicPath(1).getData() + " displayed",
					false, false);
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Edit dwelling and update Year built and roof cladding
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit dwelling");

			// Entering Location 1 Dwelling 1 Details
			testData = data.get(dataValue2);
			dwellingPage.yearBuilt.scrollToElement();
			dwellingPage.yearBuilt.clearData();
			if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
				dwellingPage.expiredQuotePopUp.scrollToElement();
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
				dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}
			dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));
			if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
				dwellingPage.expiredQuotePopUp.scrollToElement();
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
				dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}
			dwellingPage.yearBuilt.tab();
			dwellingPage.addRoofDetails(testData, 1, 1);
			Assertions.passTest("Dwelling Page",
					"Roof Cladding is updated to " + testData.get("L1D1-DwellingRoofCladding")
							+ " and Year Built is updated to " + testData.get("L1D1-DwellingYearBuilt"));

			// Review dwelling and continue
			dwellingPage.waitTime(2);
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.overrideNoLongerQuotableBuildings();
			}

			// Entering Create quote page Details
			createQuotePage.scrollToBottomPage();
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on get a quote button");

			// Asserting and Verifying AVC warning message
			Assertions.addInfo("Scenario 06", "Asserting and Verifying AVC warning message");
			createQuotePage.errorMessageWarningPage.formatDynamicPath("Actual Cash Value")
					.waitTillPresenceOfElement(60);
			Assertions.verify(
					createQuotePage.errorMessageWarningPage
							.formatDynamicPath("Actual Cash Value").getData().contains("Actual Cash Value"),
					true, "Create Quote Page",
					"Warning message is "
							+ createQuotePage.errorMessageWarningPage.formatDynamicPath("Actual Cash Value").getData()
							+ " is displayed when Roof Cladding is " + testData.get("L1D1-DwellingRoofCladding")
							+ " and Year Built is " + testData.get("L1D1-DwellingYearBuilt"),
					false, false);
			Assertions.passTest("Scenario 06", "Scenario 06 Ended");

			// Click on continue button
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();

			// Getting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number3 is " + quoteNumber);

			// Click on Request Bind
			testData = data.get(dataValue1);
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on request bind button");

			// Clicking on answer no button
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Entered underwriting details successfully");

			// Entering bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request bind page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Asserting policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is : " + policyNumber, false, false);

			// Click on Endorse PB
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Entering Endorsement effective date
			testData = data.get(dataValue3);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Transaction effective date entered successfully");

			// Click on Edit location/building information
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();
			Assertions.passTest("Endorse Policy Page",
					"Clicked on edit location or building information link successfully");

			// Update year built = 2009 and year roof last replaced = 2009
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling  Page",
					"Dwelling Page Loaded successfully", false, false);
			dwellingPage.yearBuilt.scrollToElement();
			dwellingPage.yearBuilt.clearData();
			if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
				dwellingPage.expiredQuotePopUp.scrollToElement();
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
				dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}
			dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));
			if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
				dwellingPage.expiredQuotePopUp.scrollToElement();
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
				dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}
			dwellingPage.yearBuilt.tab();
			dwellingPage.addRoofDetails(testData, 1, 1);
			Assertions.passTest("Dwelling Page", "Year Built is updated to " + testData.get("L1D1-DwellingYearBuilt"));

			// Review dwelling and continue
			dwellingPage.waitTime(2);
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			dwellingPage.continueButton.scrollToElement();
			dwellingPage.continueButton.click();
			Assertions.passTest("Dwelling Page", "Clicked on continue button");

			// Click on modify form button
			Assertions.verify(createQuotePage.modifyForms.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();
			Assertions.passTest("Create Quote Page", "Clicked on modify form button successfully");

			// Verifying none of the forms are not attaching automatically, when year built
			// and year roof last replaced 15 years older and roof cladding = Normal shingle
			Assertions.addInfo("Scenario 07", "Verifying none of the forms are not attaching automatically");
			Assertions.verify(modifyForms.totalRoofExclusion.checkIfElementIsSelected(), false, "Modify Form Page",
					"Total roof exclusion not attached", false, false);
			Assertions.verify(modifyForms.actualCashValueRoof.checkIfElementIsSelected(), false, "Modify Form Page",
					"Actual cash value not attached", false, false);
			Assertions.verify(modifyForms.aircraftExclusion.checkIfElementIsSelected(), false, "Modify Form Page",
					"Air craft exclusion not attached", false, false);
			Assertions.verify(modifyForms.limitationOfSwimmingPoolLiability.checkIfElementIsSelected(), false,
					"Modify Form Page", "Limitation of swimming pool liability not attached", false, false);
			Assertions.verify(modifyForms.premisesLiability.checkIfElementIsSelected(), false, "Modify Form Page",
					" Limited coverage premises liability not attached", false, false);
			Assertions.verify(modifyForms.modifiedFuncionalReplacement.checkIfElementIsSelected(), false,
					"Modify Form Page", "Modified functional replacement cost coverage not attached", false, false);
			Assertions.verify(modifyForms.roofSurfacingExclusion.checkIfElementIsSelected(), false, "Modify Form Page",
					"Roof surfacing cosmetic damage exclusion not attached", false, false);
			Assertions.verify(modifyForms.swimmingPoolLiabilityExclusion.checkIfElementIsSelected(), false,
					"Modify Form Page", "Swimming pool liability exclusion not attached", false, false);
			Assertions.verify(modifyForms.animalExclusion.checkIfElementIsSelected(), false, "Modify Form Page",
					"Total animal exclusion not attached", false, false);
			Assertions.verify(modifyForms.waterDamageExclusionZero.checkIfElementIsSelected(), false,
					"Modify Form Page", "Water damage exclusion sublimit zero not attached", false, false);
			Assertions.verify(modifyForms.waterDamageExclusionTenThousand.checkIfElementIsSelected(), false,
					"Modify Form Page", "Water damage exclusion sublimit ten thousand not attached", false, false);
			Assertions.verify(modifyForms.specifyWaterDamageDeductibleCheck.checkIfElementIsSelected(), false,
					"Modify Form Page", "Specify water damage deductible not attached", false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Click on override button
			modifyForms.override.scrollToElement();
			modifyForms.override.click();
			Assertions.passTest("Modify Form Page", "Clicked on override button successfully");

			// Click on next
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Click on next button");

			// Click on view endt quote button
			endorsePolicyPage.viewEndorsementQuote.scrollToElement();
			endorsePolicyPage.viewEndorsementQuote.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on view endorsement quote button");

			// Asserting and Verifying the roof coverage display as replacement Cost in Endt
			// quote document
			Assertions.addInfo("Scenario 08", "Asserting and Verifying the roof coverage display as replacement Cost");
			Assertions.verify(
					viewOrPrintFullQuotePage.roofCoverage.formatDynamicPath(1).getData().equals("Replacement Cost"),
					true, "Endorse Quote Document Page", "Roof coverage is "
							+ viewOrPrintFullQuotePage.roofCoverage.formatDynamicPath(1).getData() + " displayed",
					false, false);
			viewOrPrintFullQuotePage.closeButton.scrollToElement();
			viewOrPrintFullQuotePage.closeButton.click();
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// Click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on complete button successfully");

			// Click on Close
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on close button successfully");

			// Click on Endorse PB
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Entering Endorsement effective date
			testData = data.get(dataValue4);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Transaction effective date entered successfully");

			// Click on Edit location/building information
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();
			Assertions.passTest("Endorse Policy Page",
					"Clicked on edit location or building information link successfully");

			// Update year built = 2007 and year roof last replaced = 2007 and roof cladding
			// = Tile or Clay
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling  Page",
					"Dwelling Page Loaded successfully", false, false);
			dwellingPage.yearBuilt.scrollToElement();
			dwellingPage.yearBuilt.clearData();
			if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
				dwellingPage.expiredQuotePopUp.scrollToElement();
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
				dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}
			dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));
			if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
				dwellingPage.expiredQuotePopUp.scrollToElement();
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
				dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}
			dwellingPage.yearBuilt.tab();
			dwellingPage.addRoofDetails(testData, 1, 1);
			Assertions.passTest("Dwelling Page", "Year Built is updated to " + testData.get("L1D1-DwellingYearBuilt"));
			Assertions.passTest("Dwelling Page",
					"Roof Cladding is updated to " + testData.get("L1D1-DwellingRoofCladding"));

			// Review dwelling and continue
			dwellingPage.waitTime(2);
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			dwellingPage.continueButton.scrollToElement();
			dwellingPage.continueButton.click();
			Assertions.passTest("Dwelling Page", "Clicked on continue button");

			// Click on modify form button
			Assertions.verify(createQuotePage.modifyForms.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();
			Assertions.passTest("Create Quote Page", "Clicked on modify form button successfully");

			// Verifying TRE form attached automatically when year built older 16-20 years
			// and roof cladding = Tile or Clay
			Assertions.addInfo("Scenario 09", "Verifying Total roof exclusion form attached automatically");
			Assertions.verify(modifyForms.totalRoofExclusion.checkIfElementIsSelected(), true, "Modify Form Page",
					"Total roof exclusion form automatically attached successfully", false, false);
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			// Click on override button
			modifyForms.override.scrollToElement();
			modifyForms.override.click();
			Assertions.passTest("Modify Form Page", "Clicked on override button successfully");

			// Asserting and Verifying TRE warning message 'Roof Coverage Excluded'
			Assertions.addInfo("Scenario 10", "Asserting and Verifying TRE warning message");
			createQuotePage.errorMessageWarningPage.formatDynamicPath("Roof Coverage Excluded")
					.waitTillPresenceOfElement(60);
			Assertions.verify(
					createQuotePage.errorMessageWarningPage
							.formatDynamicPath("Roof Coverage Excluded").getData().contains("Roof Coverage Excluded"),
					true, "Create Quote Page",
					"Warning message is "
							+ createQuotePage.errorMessageWarningPage.formatDynamicPath("Roof Coverage Excluded")
									.getData()
							+ " is displayed when Roof Cladding is " + testData.get("L1D1-DwellingRoofCladding")
							+ " and Year Built is " + testData.get("L1D1-DwellingYearBuilt"),
					false, false);
			Assertions.passTest("Scenario 10", "Scenario 10 Ended");

			// Click on continue button
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();

			// Click on next
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Click on next button");
			if (endorsePolicyPage.oKContinueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.oKContinueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.oKContinueButton.scrollToElement();
				endorsePolicyPage.oKContinueButton.click();

			}

			// Click on view endt quote button
			endorsePolicyPage.viewEndorsementQuote.scrollToElement();
			endorsePolicyPage.viewEndorsementQuote.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on view endorsement quote button");

			// Asserting and Verifying the roof coverage display as Excluded in Endt
			// quote document
			Assertions.addInfo("Scenario 11", "Asserting and Verifying the roof coverage display as Excluded ");
			Assertions.verify(viewOrPrintFullQuotePage.roofCoverage.formatDynamicPath(1).getData().equals("Excluded"),
					true, "Endorse Quote Document Page", "Roof coverage is "
							+ viewOrPrintFullQuotePage.roofCoverage.formatDynamicPath(1).getData() + " displayed",
					false, false);
			viewOrPrintFullQuotePage.closeButton.scrollToElement();
			viewOrPrintFullQuotePage.closeButton.click();
			Assertions.addInfo("Scenario 11", "Scenario 11 Ended");

			// Click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on complete button successfully");

			// Click on Close
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on close button successfully");

			// Clicking on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC093 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC093 ", "Executed Successfully");
			}
		}
	}

}
