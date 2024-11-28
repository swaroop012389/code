package com.icat.epicenter.test.naho.regression.ISNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExistingAccountPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC057_NBAD002 extends AbstractNAHOTest {

	public TC057_NBAD002() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBAD002.xls";
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
		ExistingAccountPage existingAccountPage = new ExistingAccountPage();
		BuildingUnderMinimumCostPage buildingUnderMinimumCostPage = new BuildingUnderMinimumCostPage();
		BuildingNoLongerQuoteablePage noLongerQuoteable = new BuildingNoLongerQuoteablePage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();

		int dataValue1 = 0;
		String quoteNumber;
		String policyNumber;
		Map<String, String> testData = data.get(dataValue1);
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
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling  Page",
					"Dwelling Page Loaded successfully", false, false);
			dwellingPage.addDwellingDetails(testData, 1, 1);
			dwellingPage.addRoofDetails(testData, 1, 1);
			dwellingPage.enterProtectionDiscountsDetails(testData, 1, 1);
			dwellingPage.enterDwellingValuesNAHO(testData, 1, 1);
			dwellingPage.waitTime(1);
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			Assertions.passTest("Dwelling Page", "Dwelling Details Entered successfully");

			// IO-2010
			// Asserting warning message TX state when YOC less than
			// 1970, The warning
			// message is 'Due to a year built prior to 1970 this risk is ineligible for
			// coverage. If the home has undergone a gut rehab and supporting documentation
			// can be provided, we will reconsider offering terms. Please reach out to your
			// assigned UW.'
			Assertions.addInfo("Scenario 01", "Asserting warning message when yoc is less than 1970");
			Assertions.verify(
					dwellingPage.protectionClassWarMsg.getData().contains("Due to a year built prior to 1970"), true,
					"Dwelling Page",
					"The Warning meesage " + dwellingPage.protectionClassWarMsg.getData() + " is displayed", false,
					false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
			// IO-20810 ended

			// Click on override button
			dwellingPage.override.scrollToElement();
			dwellingPage.override.click();
			if (existingAccountPage.override.checkIfElementIsPresent()
					&& existingAccountPage.override.checkIfElementIsDisplayed()) {
				existingAccountPage.override.scrollToElement();
				existingAccountPage.override.click();
			}
			if (buildingUnderMinimumCostPage.override.checkIfElementIsPresent()
					&& buildingUnderMinimumCostPage.override.checkIfElementIsDisplayed()) {
				buildingUnderMinimumCostPage.override.scrollToElement();
				buildingUnderMinimumCostPage.override.click();
			}
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			if (noLongerQuoteable.override.checkIfElementIsPresent()
					&& noLongerQuoteable.override.checkIfElementIsPresent()) {
				noLongerQuoteable.override.scrollToElement();
				noLongerQuoteable.override.click();
			}

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

			// Getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			int quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// assert Alt deductible section
			Assertions.addInfo("Scenario 02", "Verifying presence of Alt deductible section");
			Assertions.verify(accountOverviewPage.otherDeductibleOptions.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Alt Deductible section present is verified", false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// looping through AOP section and asserting values
			Assertions.addInfo("Scenario 03", "Asserting AOP Values");
			for (int i = 1; i <= 3; i++) {
				if (i == 1) {
					Assertions.verify(
							accountOverviewPage.deductibleOptionsNAHO.formatDynamicPath("All Other Perils", i)
									.checkIfElementIsDisplayed(),
							true, "Account Overview Page",
							"Quote value selected : " + accountOverviewPage.deductibleOptionsNAHO
									.formatDynamicPath("All Other Perils", i).getData(),
							false, false);
				} else {
					Assertions.verify(
							accountOverviewPage.deductibleOptionsNAHO
									.formatDynamicPath("All Other Perils", i).checkIfElementIsDisplayed(),
							true, "Account Overview Page",
							"Other Quote Options : Option " + (i - 1) + " : "
									+ accountOverviewPage.deductibleOptionsNAHO.formatDynamicPath("All Other Perils", i)
											.getData(),
							false, false);
				}
			}

			Assertions.verify(
					accountOverviewPage.deductibleOptionsNAHO.formatDynamicPath("All Other Perils", 1)
							.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "Quote value selected : " + accountOverviewPage.deductibleOptionsNAHO
							.formatDynamicPath("All Other Perils", 1).getData(),
					false, false);

			if (Integer.parseInt(accountOverviewPage.deductibleOptionsNAHO.formatDynamicPath("All Other Perils", 2)
					.getData().replaceAll("[^\\d-.]", "")) > Integer
							.parseInt(testData.get("AOPDeductibleValue").replaceAll("[^0-9]", ""))) {
				Assertions.verify(
						accountOverviewPage.deductibleOptionsNAHO
								.formatDynamicPath("All Other Perils", 2).checkIfElementIsDisplayed(),
						true, "Account Overview Page",
						"Alt Quote Option 1 displayed is higher than selected deductible : "
								+ accountOverviewPage.deductibleOptionsNAHO.formatDynamicPath("All Other Perils", 2)
										.getData()
								+ " is verified",
						false, false);
			}

			if (Integer.parseInt(accountOverviewPage.deductibleOptionsNAHO.formatDynamicPath("All Other Perils", 3)
					.getData().replaceAll("[^\\d-.]", "")) > Integer
							.parseInt(testData.get("AOPDeductibleValue").replaceAll("[^0-9]", ""))) {
				Assertions.verify(
						accountOverviewPage.deductibleOptionsNAHO
								.formatDynamicPath("All Other Perils", 3).checkIfElementIsDisplayed(),
						true, "Account Overview Page",
						"Alt Quote Option 2 displayed is higher than selected deductible : "
								+ accountOverviewPage.deductibleOptionsNAHO.formatDynamicPath("All Other Perils", 3)
										.getData()
								+ " is verified",
						false, false);
			}
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Binding the quote and creating the policy
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

			// Clicking on answer no button
			underwritingQuestionsPage.answerNoToAllQuestions.waitTillVisibilityOfElement(60);
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.answerNoToAllQuestions.scrollToElement();
			underwritingQuestionsPage.answerNoToAllQuestions.click();

			// Clicking on save button
			underwritingQuestionsPage.saveButton.scrollToElement();
			underwritingQuestionsPage.saveButton.click();
			requestBindPage.refreshPage();

			// Entering bind details
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Values Entered Successfully");

			// Getting policy number
			policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Number is " + policyNumber, false, false);

			// Click on PB Endorsement link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page loaded successfully", false, false);

			// Enter endorse effective date click on edit location/building information link
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.appendData(testData.get("PolicyEffDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();

			// Click on review dwelling link
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling  Page",
					"Dwelling Page Loaded successfully", false, false);
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();

			// IO-2010
			// Asserting warning message TX state when YOC less than
			// 1970 for Endorsement, The warning
			// message is 'Due to a year built prior to 1970 this risk is ineligible for
			// coverage. If the home has undergone a gut rehab and supporting documentation
			// can be provided, we will reconsider offering terms. Please reach out to your
			// assigned UW.'
			Assertions.addInfo("Scenario 04", "Asserting warning message when yoc is less than 1970");
			Assertions.verify(
					dwellingPage.protectionClassWarMsg.getData().contains("Due to a year built prior to 1970"), true,
					"Dwelling Page",
					"The Warning meesage " + dwellingPage.protectionClassWarMsg.getData() + " is displayed", false,
					false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Click on override button
			dwellingPage.override.scrollToElement();
			dwellingPage.override.click();
			Assertions.passTest("Dwelling Page", "Clicked on override button successfully");
			// IO-20810 ended

			// Click on home icon link
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Search policy
			homePage.searchPolicy(policyNumber);
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			// Click on Rewrite link
			policySummaryPage.rewritePolicy.scrollToElement();
			policySummaryPage.rewritePolicy.click();
			cancelPolicyPage.continueButton.scrollToElement();
			cancelPolicyPage.continueButton.click();
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// Click on edit building link
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();
			Assertions.passTest("Account Overview Page", "Clicked on editbuilding link successfully");

			// Click on review dwelling button
			Assertions.verify(dwellingPage.createQuote.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();

			// IO-2010
			// Asserting warning message TX state when YOC less than
			// 1970 for Rewrite, The warning
			// message is 'Due to a year built prior to 1970 this risk is ineligible for
			// coverage. If the home has undergone a gut rehab and supporting documentation
			// can be provided, we will reconsider offering terms. Please reach out to your
			// assigned UW.'
			Assertions.addInfo("Scenario 05", "Asserting warning message when yoc is less than 1970");
			Assertions.verify(
					dwellingPage.protectionClassWarMsg.getData().contains("Due to a year built prior to 1970"), true,
					"Dwelling Page",
					"The Warning meesage " + dwellingPage.protectionClassWarMsg.getData() + " is displayed", false,
					false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Click on override button
			dwellingPage.override.scrollToElement();
			dwellingPage.override.click();
			Assertions.passTest("Dwelling Page", "Clicked on override button successfully");
			// IO-20810 ended

			if (existingAccountPage.override.checkIfElementIsPresent()
					&& existingAccountPage.override.checkIfElementIsDisplayed()) {
				existingAccountPage.override.scrollToElement();
				existingAccountPage.override.click();
			}
			if (buildingUnderMinimumCostPage.override.checkIfElementIsPresent()
					&& buildingUnderMinimumCostPage.override.checkIfElementIsDisplayed()) {
				buildingUnderMinimumCostPage.override.scrollToElement();
				buildingUnderMinimumCostPage.override.click();
			}
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			if (noLongerQuoteable.override.checkIfElementIsPresent()
					&& noLongerQuoteable.override.checkIfElementIsPresent()) {
				noLongerQuoteable.override.scrollToElement();
				noLongerQuoteable.override.click();
			}
			// Click on get quote
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Getting the rewrite quote number
			Assertions.verify(accountOverviewPage.rewriteBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData();
			Assertions.addInfo("Account Overview Page", "Rewrite Quote Number is : " + quoteNumber);

			// Click on view policy link
			accountOverviewPage.viewPolicyButton.scrollToElement();
			accountOverviewPage.viewPolicyButton.click();

			// Stop policy rewrite
			policySummaryPage.stopPolicyRewrite.scrollToElement();
			policySummaryPage.stopPolicyRewrite.click();
			Assertions.passTest("Policy Summary Page", "Clicked on stoprewrite link successfully");

			// clicking on renewal policy link
			Assertions.verify(policySummaryPage.renewPolicy.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			cancelPolicyPage.continueButton.scrollToElement();
			cancelPolicyPage.continueButton.click();

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
				cancelPolicyPage.continueButton.scrollToElement();
				cancelPolicyPage.continueButton.click();
				Assertions.passTest("Policy Summary Page", "Clicked on renewal link successfully");
			}

			// Getting the renewal quote number
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.addInfo("Account Overview Page", "Renewal Quote Number is : " + quoteNumber);

			// Click on release renewal producer
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on release renewal to producer successfully");

			// Click on edit building link
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();

			// Click on review dwelling button
			Assertions.verify(dwellingPage.createQuote.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();

			// IO-2010
			// Checking absence of warning message TX state when YOC less than
			// 1970 for Renewal, The warning
			// message is 'Due to a year built prior to 1970 this risk is ineligible for
			// coverage. If the home has undergone a gut rehab and supporting documentation
			// can be provided, we will reconsider offering terms. Please reach out to your
			// assigned UW.'
			Assertions.addInfo("Scenario 06", "Checking absence of warning message when yoc is less than 1970");
			Assertions.verify(dwellingPage.protectionClassWarMsg.checkIfElementIsPresent(), false, "Dwelling Page",
					"The Warning meesage is not displayed", false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Click on sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC057 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC057 ", "Executed Successfully");
			}
		}
	}
}
