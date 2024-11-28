
/** Program Description: Checking various functionalities in Create quote page and Quote document
 *  Author			   : Sowndarya NH
 *  Date of Creation   : 15/12/2021
 **/

package com.icat.epicenter.test.naho.regression.ESBNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class RITC001 extends AbstractNAHOTest {

	public RITC001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBNB/RITC001.xls";
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
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();

		// Initializing the variables
		int dataValue1 = 0;
		String quoteNumber;
		Map<String, String> testData = data.get(dataValue1);
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

			dwellingPage.addDwellingDetails(testData, 1, 1);
			dwellingPage.addRoofDetails(testData, 1, 1);
			dwellingPage.enterProtectionDiscountsDetails(testData, 1, 1);
			dwellingPage.enterInternalSectionDetails(testData, 1, 1);
			dwellingPage.enterDwellingValuesNAHO(testData, 1, 1);
			Assertions.passTest("Dwelling Page", "Dwelling Details Entered successfully");

			// click on review dwelling
			dwellingPage.waitTime(3);// if wait time is removed test case will fail here
			dwellingPage.reviewDwelling.waitTillVisibilityOfElement(60);
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();

			// Assert the error message when protection class entered as 9
			Assertions.addInfo("Scenario 01",
					"Verifying the Error message when Protection class value entered as 9 for NB transaction");
			Assertions.verify(dwellingPage.protectionClassWarMsg.getData().contains("protection class of 9/10"), true,
					"Dwelling Page",
					"The Error message " + dwellingPage.protectionClassWarMsg.getData() + " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// click on internal section
			dwellingPage.internalSection.waitTillVisibilityOfElement(60);
			dwellingPage.internalSection.waitTillButtonIsClickable(60);
			dwellingPage.internalSection.scrollToElement();
			dwellingPage.internalSection.click();

			// clearing protection class value
			dwellingPage.protectionClassOverride.scrollToElement();
			dwellingPage.protectionClassOverride.clearData();

			// click on resubmit
			dwellingPage.scrollToBottomPage();
			dwellingPage.waitTime(3);// if wait time is removed test case will fail here
			dwellingPage.reSubmit.scrollToElement();
			dwellingPage.reSubmit.click();

			// click on create quote
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			Assertions.passTest("Dwelling Page", "Clicked on Create quote");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Verifying HO3 is selected by default and HO5 appears When Coverage A Value is
			// less than $750,000
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			Assertions.addInfo("Scenario 02",
					"Verifying HO3 is selected by default and HO5 appears When Coverage A Value is less than $750,000");
			Assertions.verify(createQuotePage.formType_HO3.checkIfElementIsSelected(), true, "Create Quote Page",
					"HO3 Form is Selected by default is verified", false, false);
			Assertions.verify(createQuotePage.coverageADwelling.getData(), testData.get("L1D1-DwellingCovA"),
					"Create Quote Page",
					"The Coverage A Entered is " + "$" + createQuotePage.coverageADwelling.getData(), false, false);
			Assertions.verify(createQuotePage.formType_HO5.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"HO5 Form displayed is verified when Coverage A is less than $750,000", false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// checking aowh is not interactable or not
			Assertions.addInfo("Scenario 03",
					"Verifying AOWH takes same Value as AOP and AOWH is Not Interactable for USM");
			Assertions.verify(createQuotePage.aowhDeductibleData.getAttrributeValue("unselectable"), "on",
					"Create Quote Page", "AOWH Arrow disabled is verified", false, false);

			// Changing the AOP deductible value to $10,000
			createQuotePage.aopDeductibleArrow.waitTillVisibilityOfElement(60);
			createQuotePage.aopDeductibleArrow.scrollToElement();
			createQuotePage.aopDeductibleArrow.click();
			Assertions.addInfo("Create Quote Page",
					"AOP Original Value : " + createQuotePage.aopDeductibleData.getData());
			createQuotePage.aopDeductibleOption.formatDynamicPath(testData.get("AOPDeductibleValue")).scrollToElement();
			createQuotePage.aopDeductibleOption.formatDynamicPath(testData.get("AOPDeductibleValue")).click();
			Assertions.passTest("Create Quote Page",
					"AOP Latest Value : " + createQuotePage.aopDeductibleData.getData());

			// Verifying aowh and aop values are same
			Assertions.verify(createQuotePage.aowhDeductibleData.getData(), createQuotePage.aopDeductibleData.getData(),
					"Create Quote Page", "After Updating AOP Value AOWH and AOP Deductible values are equal", false,
					false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Verifying whether Named Hurricane Deductible takes a value greater than or
			// equal to AOP Deductible value selected
			Assertions.addInfo("Scenario 04",
					"Verifying whether Named Hurricane Deductible takes a value greater than or equal to AOP Deductible value selected");
			String namedHurricaneValue = createQuotePage.namedHurricaneData.getData().replace("%", "");
			double d_namedHurricaneValue = Double.parseDouble(namedHurricaneValue);
			double d_calcnamedHurricaneValue = d_namedHurricaneValue / 100;
			String coveAValue = testData.get("L1D1-DwellingCovA").replace(",", "");
			double d_coveAValue = Double.parseDouble(coveAValue);

			// calculate named storm deductible value to check it is greater than or equal
			// to aop dedutible
			double d_finalnamedHurricaneValue = d_coveAValue * d_calcnamedHurricaneValue;
			String aopdeductibleValue = createQuotePage.aopDeductibleData.getData().replace("$", "").replace(",", "");
			double d_aopdeductibleValue = Double.parseDouble(aopdeductibleValue);
			if (d_finalnamedHurricaneValue >= d_aopdeductibleValue) {
				Assertions.passTest("Create Quote Page",
						"Named Hurricane Calculated Value is Greater than or Equal to AOP deductible Value");
			} else {
				Assertions.failTest("Create Quote Page",
						"Named Hurricane Calculated Value is less than AOP deductible Value");
			}
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// click on get a quote
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create quote Page", "Clicked on Get a Quote button");

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Adding Policy fee and Inspection fee
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			int quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print Full quote link");
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page", "View/Print Full Quote Page loaded successfully", false, false);
			Assertions.addInfo("Scenario 05",
					"Verifying AOWH Deductible Value choosen in Create Quote page is not displayed in Quote Document");
			Assertions.verify(
					viewOrPrintFullQuotePage.nhAndAOPValue.formatDynamicPath("Wind and Hail").checkIfElementIsPresent(),
					false, "View/Print Full Quote Page", "Wind and Hail is not displayed on Quote page is verified",
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Verifying the Named Hurricane and Calendar Year Aggregate text is displayed
			// for Named Hurricane Deductible
			Assertions.addInfo("Scenario 06",
					"Verifying the Named Hurricane and Calendar Year Aggregate text is displayed for Named Hurricane Deductible");
			Assertions.verify(
					viewOrPrintFullQuotePage.nhAndAOPValue.formatDynamicPath("Named Hurricane")
							.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The " + viewOrPrintFullQuotePage.nhAndAOPValue.formatDynamicPath("Named Hurricane").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Verifying Insurer Inspection Fee and Insurer Policy Fee is displayed as
			// Header for Inspection Fees and Policy Fees
			Assertions.addInfo("Scenario 07",
					"Verifying Insurer Inspection Fee and Insurer Policy Fee is displayed as Header for Inspection Fees and Policy Fees");
			Assertions.verify(viewOrPrintFullQuotePage.policyFeeHeader.checkIfElementIsPresent(), true,
					"View/Print Full Quote Page",
					"The Header " + viewOrPrintFullQuotePage.policyFeeHeader.getData() + " displayed is verified",
					false, false);
			Assertions.verify(viewOrPrintFullQuotePage.inspFeeHeader.checkIfElementIsPresent(), true,
					"View/Print Full Quote Page",
					"The Header " + viewOrPrintFullQuotePage.inspFeeHeader.getData() + " displayed is verified", false,
					false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Added IO-20829
			String carrIers = viewOrPrintFullQuotePage.surplusLineInsurersVA_RI.getData().replace(" ", "");
			System.out.println("Carrier is : " + carrIers);

			Assertions.addInfo("Scenario 08",
					"Verifying the updated Carrier/NAIC# details for NAHO- RI state on the quote document");
			Assertions.verify(
					viewOrPrintFullQuotePage.surplusLineInsurersVA_RI.getData().replace(" ", "")
							.equals("VictorInsuranceExchange,#17499"),
					true, "View Or Print FullQuote Page",
					"The quote document for the RI state has been updated with the carrier VIE and NAIC# details Verified",
					false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// ------ IO-20829 Ended-----

			// Click on back
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.passTest("View/Print Full Quote Page", "Clicked on Back Button");

			// Verifying the presence of Named Hurricane in Alternate Deductible table
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			Assertions.addInfo("Scenario 09",
					"Verifying the presence of Named Hurricane in Alternate Deductible table");
			Assertions.verify(accountOverviewPage.altQuoteOptChosenCoverageOption.formatDynamicPath("1").getData(),
					"Named Hurricane", "Account Overview Page",
					accountOverviewPage.altQuoteOptChosenCoverageOption.formatDynamicPath("1").getData()
							+ " is displayed in Alternate Deductible table",
					false, false);
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			// Signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("RITC001 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("RITC001 ", "Executed Successfully");
			}
		}
	}
}
