/** Program Description: Verifying AOWH takes same Value as AOP and AOWH is Not Interactable for USM, Named Hurricane and All other perils values are displayed in View Or Print full Quote Page
 * Wind and Hail is not displayed in View Or Print full quote page and verifying error message when year built prior to 1978 and occupancy as tenant
 *  Author			   : Priyanka S
 *  Date of Creation   : 12/30/2021
 **/

package com.icat.epicenter.test.naho.regression.ESBPNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.EndorseSummaryDetailsPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBRITC001 extends AbstractNAHOTest {

	public PNBRITC001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBPNB/RITC001.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		EndorseSummaryDetailsPage endorseSummaryDetailsPage = new EndorseSummaryDetailsPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
		String quoteNumber;
		String policyNumber;
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
			String CovAvalue = testData.get("L1D1-DwellingCovA");
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

			// Adding Policy fee and Inspection fee
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData();
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
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is : " + policyNumber, false, false);

			// Click on EndorsePB Link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Entering Endorsement effective date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// Click on Change Coverage Option link
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Coverage Options link");

			// Changing the coverage for AOP
			testData = data.get(dataValue2);
			createQuotePage.enterDeductiblesNAHO(testData);

			// verifying AOWH takes same Value as AOP and
			// AOWH is Not Interactable for USM.
			Assertions.addInfo("Scenario 01",
					"Verifying AOWH takes same Value as AOP and AOWH is Not Interactable for USM.");
			Assertions.verify(
					createQuotePage.aopDeductibleData.getData().contains(createQuotePage.aowhDeductibleData.getData()),
					true, "Create quote Page", "After Updating AOP Value AOWH and AOP Deductible values are equal",
					false, false);
			Assertions.verify(createQuotePage.aowhDeductibleData.getAttrributeValue("unselectable").contains("on"),
					true, "Create quote Page", "AOWH Deductible Arrow disabled is verified", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Changing Cov A to 1M
			testData = data.get(dataValue3);
			String locationNumber = testData.get("LocCount");
			int locationCount = Integer.parseInt(locationNumber);
			String dwellingNumber = testData.get("L" + locationCount + "-DwellingCount");
			int dwellingCount = Integer.parseInt(dwellingNumber);
			createQuotePage.enterInsuredValuesNAHO(testData, locationCount, dwellingCount);
			Assertions.verify(
					createQuotePage.coverageADwelling.checkIfElementIsPresent()
							&& createQuotePage.coverageADwelling.checkIfElementIsDisplayed(),
					true, "Create A Quote Page", "Cov A Data is updated from " + "$" + CovAvalue + " to : " + "$"
							+ createQuotePage.coverageADwelling.getData(),
					false, false);
			Assertions.verify(
					createQuotePage.namedHurricaneData.checkIfElementIsDisplayed()
							&& createQuotePage.aowhDeductibleData.checkIfElementIsDisplayed(),
					true, "Create A Quote Page",
					"Named Hurricane Data is updated to : " + createQuotePage.namedHurricaneData.getData()
							+ " and All Other Wind And Hail Data : " + createQuotePage.aowhDeductibleData.getData()
							+ " are displayed",
					false, false);

			// click on continue endorsement button
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			Assertions.passTest("Create Quote Page", "Clicked on Continue Endorsement Button");
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Click On next Button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Next Button");

			if (endorsePolicyPage.oKContinueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.oKContinueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.oKContinueButton.scrollToElement();
				endorsePolicyPage.oKContinueButton.click();
			}

			// Click On View Endorse Quote Button
			endorsePolicyPage.viewEndorsementQuote.scrollToElement();
			endorsePolicyPage.viewEndorsementQuote.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on View Endorsement Quote Page");

			// Asserting values for Named Hurricane and All other perils
			Assertions.addInfo("Scenario 02", "Verifying Named Hurricane and All other perils");
			Assertions.verify(
					viewOrPrintFullQuotePage.discountsValue
							.formatDynamicPath("Named Hurricane").checkIfElementIsDisplayed(),
					true, "View Endorsement Quote Page",
					"Named Hurricane Data : "
							+ viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Named Hurricane").getData(),
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.discountsValue
							.formatDynamicPath("All Other Perils").checkIfElementIsDisplayed(),
					true, "View Endorsement Quote Page",
					"All Other Perils Data : "
							+ viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("All Other Perils").getData(),
					false, false);
			Assertions.verify(
					!viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Wind and Hail")
							.checkIfElementIsPresent(),
					true, "View Endorsement Quote Page", "Wind and Hail value is not dispalyed", false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Click on close Button
			viewOrPrintFullQuotePage.closeButton.scrollToElement();
			viewOrPrintFullQuotePage.closeButton.click();
			Assertions.passTest("View Endorsement Quote Page", "Clicked on Close button");

			// Click On complete Button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Complete Button");

			// Click on Close Button
			endorseSummaryDetailsPage.closeBtn.scrollToElement();
			endorseSummaryDetailsPage.closeBtn.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Close button");

			// Click on EndorsePB Link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Entering Endorsement effective date
			testData = data.get(dataValue2);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// Click on edit location or building details link
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Edit Location or building details link");
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page Loaded successfully", false, false);

			// Change the year built prior to 1978 and occupancy as tenant
			if (!testData.get("L1D1-DwellingYearBuilt").equals("")) {
				Assertions.addInfo("Dwelling Page",
						"The Year Built Original Value : " + dwellingPage.yearBuilt.getData());
				dwellingPage.yearBuilt.scrollToElement();
				dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));
				dwellingPage.yearBuilt.tab();
				Assertions.passTest("Dwelling Page",
						"The Year Built latest Value : " + dwellingPage.yearBuilt.getData());
			}
			if (dwellingPage.OccupiedByArrow.checkIfElementIsPresent() && !testData.get("L1D1-OccupiedBy").equals("")) {
				Assertions.addInfo("Dwelling Page",
						"Occupied By Original Value : " + dwellingPage.occupiedByData.getData());
				dwellingPage.OccupiedByArrow.waitTillVisibilityOfElement(60);
				dwellingPage.OccupiedByArrow.scrollToElement();
				dwellingPage.OccupiedByArrow.click();
				dwellingPage.OccupiedByOption.formatDynamicPath(testData.get("L1D1-OccupiedBy")).scrollToElement();
				dwellingPage.OccupiedByOption.formatDynamicPath(testData.get("L1D1-OccupiedBy")).click();
				Assertions.passTest("Dwelling Page",
						"Occupied By Latest Value : " + dwellingPage.occupiedByData.getData());
			}

			// Click on review dwelling
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();

			Assertions.addInfo("Scenario 03",
					"Verifying the hardstop message when year built prior to 1978 and occupancy as tenant for Endorsement");
			Assertions.verify(dwellingPage.protectionClassWarMsg.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"The Error Message " + dwellingPage.protectionClassWarMsg.getData() + " displayed is verified", false,
					false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Clicking on home page button and search the policy
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Searched the policy " + policyNumber + " successfully");

			// Click on EndorsePB Link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();

			// click on ok
			if (endorsePolicyPage.okButton.checkIfElementIsPresent()
					&& endorsePolicyPage.okButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.okButton.scrollToElement();
				endorsePolicyPage.okButton.click();
			}

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

			// Click on edit location or building details link
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Edit Location or building details link");
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page Loaded successfully", false, false);

			// change the year built as 1949
			if (!testData.get("L1D1-DwellingYearBuilt").equals("")) {
				Assertions.addInfo("Dwelling Page",
						"The Year Built Original Value : " + dwellingPage.yearBuilt.getData());
				dwellingPage.yearBuilt.scrollToElement();
				dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));
				dwellingPage.yearBuilt.tab();
				Assertions.passTest("Dwelling Page",
						"The Year Built latest Value : " + dwellingPage.yearBuilt.getData());
			}

			// Click on Continue
			dwellingPage.continueButton.scrollToElement();
			dwellingPage.continueButton.click();
			Assertions.passTest("Dwelling Page", "Clicked on Continue Button");
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page loaded successfully", false, false);

			// Enter additional dwelling coverage as 150%
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page",
					"The Additional Dwelling Coverage Entered is " + testData.get("AdditionalDwellingCoverage"));

			// click on Continue endorsement button
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			Assertions.passTest("Create quote Page", "Clicked on Continue Endorsement Button");

			// Assert the warning message when year built prior to 1950 and additional
			// dwelling coverage is 150%
			Assertions.addInfo("Scenario 04",
					"Verifying the Warning message when year built prior to 1950 and additional dwelling coverage is 150%");
			Assertions.verify(
					createQuotePage.warningMessages
							.formatDynamicPath("year of construction prior to 1950").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Warning Message " + createQuotePage.warningMessages
							.formatDynamicPath("year of construction prior to 1950").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Signout as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBRITC001 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBRITC001 ", "Executed Successfully");
			}
		}
	}
}
