/*Description :verify the AOWH deductible can be selected independent of the AOP deductible and
not defaulted to AOP deductible as USM.
*/

package com.icat.epicenter.test.naho.regression.ISNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintRateTrace;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC066_NBC003 extends AbstractNAHOTest {

	public TC066_NBC003() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBC003.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {
		// Initializing page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		LoginPage loginPage = new LoginPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ViewOrPrintRateTrace viewOrPrintRateTrace = new ViewOrPrintRateTrace();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();

		// Initializing Variables
		Map<String, String> testData;
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Creating a new account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
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

			// Entering Quote details
			Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote page loaded successfully", false, false);

			Assertions.verify(createQuotePage.businessPropertyArrow.checkIfElementIsDisplayed(), true,
					"Create Quote Page",
					"Increased Limits on Business Property Dropdown is displayed when Cov c value is NOT zero", false,
					false);
			testData = data.get(data_Value2);
			createQuotePage.coverageCPersonalProperty.clearData();
			createQuotePage.coverageCPersonalProperty.tab();
			createQuotePage.coverageCPersonalProperty.scrollToElement();
			createQuotePage.coverageCPersonalProperty.setData(testData.get("L1D1-DwellingCovC"));
			createQuotePage.coverageCPersonalProperty.tab();
			Assertions.passTest("Create Quote Page", "Cov C value zero entered successfully");

			Assertions.verify(createQuotePage.businessPropertyArrow.checkIfElementIsPresent(), false,
					"Create Quote Page",
					"Increased Limits on Business Property Dropdown is displayed when Cov c value is zero", false,
					false);

			// Sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Sign out as producer successfully");

			// Sign in as USM
			testData = data.get(data_Value3);
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Login", "Logged in to application successfully");

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

			// Verifying and Asserting the AOWH deductible is default to minimum
			// deductible(3%)
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			Assertions.addInfo("Scenari 01", "Verifying and Asserting AOWH deductible is default to minimum 3%");
			Assertions.verify(createQuotePage.aowhDeductibleData.getData().equals("3%"), true, "Create Quote Page",
					" AOWH Deductible is default to minimum value is " + createQuotePage.aowhDeductibleData.getData()
							+ " displayed",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Verifying and asserting both percentage and flat dollar values present in the
			// AOWH deductible dropdown
			createQuotePage.aowhDeductibleArrow.scrollToElement();
			createQuotePage.aowhDeductibleArrow.click();
			Assertions.addInfo("Scenario 02",
					"Verifying and asserting both percentage and flat dollar values present in the AOWH deductible dropdown");
			Assertions.verify(
					createQuotePage.aowhDeductibleOptionNAHO.formatDynamicPath("10%").checkIfElementIsDisplayed(), true,
					"Create Quote Page",
					"AOWH percentage value show in dropdown, AOWH value is "
							+ createQuotePage.aowhDeductibleOptionNAHO.formatDynamicPath("10%").getData()
							+ " displayed",
					false, false);
			Assertions.verify(
					createQuotePage.aowhDeductibleOptionNAHO
							.formatDynamicPath("$10,000 (Override)").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"AOWH flat dollars values shows in dropdown AOWH value is "
							+ createQuotePage.aowhDeductibleOptionNAHO.formatDynamicPath("$10,000 (Override)").getData()
							+ " displayed",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Verifying and Asserting the AOWH deductible can be selected by the user.
			Assertions.addInfo("Scenario 03",
					"Verifying and Asserting the AOWH deductible can be selected by the user");
			testData = data.get(data_Value1);
			createQuotePage.aowhDeductibleOptionNAHO.formatDynamicPath(testData.get("AOWHDeductibleValue"))
					.scrollToElement();
			createQuotePage.aowhDeductibleOptionNAHO.formatDynamicPath(testData.get("AOWHDeductibleValue")).click();
			Assertions.verify(createQuotePage.aowhDeductibleData.getData().equals(testData.get("AOWHDeductibleValue")),
					true, "Create Quote Page", "User is able to select AOWH deductible percenatge value is "
							+ createQuotePage.aowhDeductibleData.getData() + " displayed",
					false, false);

			// Selecting AOWH Deductible flat dollars
			testData = data.get(data_Value2);
			createQuotePage.aowhDeductibleArrow.scrollToElement();
			createQuotePage.aowhDeductibleArrow.click();
			createQuotePage.aowhDeductibleOptionNAHO.formatDynamicPath(testData.get("AOWHDeductibleValue"))
					.scrollToElement();
			createQuotePage.aowhDeductibleOptionNAHO.formatDynamicPath(testData.get("AOWHDeductibleValue")).click();
			Assertions.verify(createQuotePage.aowhDeductibleData.getData().equals(testData.get("AOWHDeductibleValue")),
					true, "Create Quote Page", "User is able to select AOWH deductible flat dollars value is "
							+ createQuotePage.aowhDeductibleData.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Select AOP deductible value
			createQuotePage.enterDeductiblesNAHO(testData);
			Assertions.passTest("Create Quote Page", "AOP Value entered successfully");

			// Verifying and Asserting that the AOWH deductible is not affected by the
			// selected AOP deductible.
			Assertions.addInfo("Scenario 04",
					"Verifying and Asserting that the AOWH deductible is not affected by the selected AOP deductible.");
			Assertions.verify(createQuotePage.aowhDeductibleData.getData().equals(testData.get("AOWHDeductibleValue")),
					true, "Create Quote Page",
					"After selecting AOP deductible, AOWH value not changed the AOWH value is "
							+ createQuotePage.aowhDeductibleData.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Click on getAquote button
			createQuotePage.waitTime(1);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create quote Page", "Clicked on getAquote button successfully");

			// Getting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			String quoteNumber = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Verifying AOWH present in quote tree
			Assertions.addInfo("Scenario 05", "Verifying AOWH present in quote tree");
			Assertions.verify(
					accountOverviewPage.quoteTreeAopNsAowh.formatDynamicPath("AOWH").checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"AOWH displayed in quote tree AOWH value is " + accountOverviewPage.quoteTreeAopNsAowh
							.formatDynamicPath("AOWH").getData().substring(36, 39),
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Verifying All other wind and Hail options are present
			Assertions.addInfo("Scenario 06", "Verifying All other wind and Hail options are present");
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
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Click on the View/Print Rate Trace link.
			accountOverviewPage.viewOrPrintRateTrace.scrollToElement();
			accountOverviewPage.viewOrPrintRateTrace.click();
			Assertions.passTest("Account Overview Page", "Clicked on view or print rate trace link");

			// Verify the Rate Trace contains DeductibleFactorAOWH for Wind peril.
			Assertions.addInfo("Scenario 07", "Verify the Rate Trace contains DeductibleFactorAOWH for Wind peril");
			Assertions.verify(viewOrPrintRateTrace.windDeductibleAOWHFactor.getData().equals("DeductibleFactorAOWH"),
					true, "View Or Print Rate Trace Page", "Rate Trace contains DeductibleFactorAOWH for Wind peril is "
							+ viewOrPrintRateTrace.windDeductibleAOWHFactor.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Verify the DeductibleFactorAOWH value in the Rate Trace is same as the
			// DeductibleFactorAOWH in the rater sheet.
			testData = data.get(data_Value1);
			Assertions.addInfo("Scenario 08",
					" Verify the DeductibleFactorAOWH value in the Rate Trace is same as the DeductibleFactorAOWH in the rater sheet.");
			Assertions.verify(viewOrPrintRateTrace.windDeductibleAOWHFactorValue.getData(),
					testData.get("DeductibleFactorAOWHValue"), "View Or Print Rate Trace Page",
					"The value of DeductibleFactorAOWH valu in the rate trace matches with the value in rater sheet(0.9000). Deductible Factor AOWH Value is "
							+ viewOrPrintRateTrace.windDeductibleAOWHFactorValue.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// click on back button
			viewOrPrintRateTrace.backBtn.scrollToElement();
			viewOrPrintRateTrace.backBtn.click();
			Assertions.passTest("View Or Print Rate Trace Page", "Clicked in back button");

			// Click on bind button
			testData = data.get(data_Value3);
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on bind request button");

			// Clicking on answer no button
			underwritingQuestionsPage.answerNoToAllQuestions.waitTillVisibilityOfElement(60);
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.answerNoToAllQuestions.scrollToElement();
			underwritingQuestionsPage.answerNoToAllQuestions.click();
			Assertions.passTest("Underwriting Questions Page", "Clicked on answer NoToAll questions");

			// Clicking on save button
			underwritingQuestionsPage.saveButton.scrollToElement();
			underwritingQuestionsPage.saveButton.click();
			Assertions.passTest("Underwriting Questions Page", "Clicked on save button");

			// Entering bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request bind page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Validating the premium amount
			String originalPolicyNumber = policySummaryPage.policyNumber.getData();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is " + originalPolicyNumber, false, false);

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC066 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC066 ", "Executed Successfully");
			}
		}
	}
}