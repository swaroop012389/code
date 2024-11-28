/** Program Description: Create AOP quote and assert for presence of EQ Sprinkler leakage with/without EQ deductible, presence of monthly limit of indemnity with/without BI , default values for EQB, WDR, Ordinance or law, terrorism and coverage package with/without building value.
 *  Author			   : John
 *  Date of Creation   : 06/30/2020
**/

package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.EndorseSummaryDetailsPage;
import com.icat.epicenter.pom.GLInformationPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.ModifyForms;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC009 extends AbstractCommercialTest {

	public TC009() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID009.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		ModifyForms modifyForms = new ModifyForms();
		ViewOrPrintFullQuotePage viewPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		GLInformationPage gLInformationPage = new GLInformationPage();
		LoginPage loginPage = new LoginPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestSubmittedPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		EndorseSummaryDetailsPage endorseSummaryDetailsPage = new EndorseSummaryDetailsPage();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
		Map<String, String> testData1 = data.get(data_Value2);
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

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Prior Losses
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterDeductiblesCommercialNew(testData);
			createQuotePage.buildingValue.formatDynamicPath(0, 0).waitTillVisibilityOfElement(60);
			createQuotePage.buildingValue.formatDynamicPath(0, 0).scrollToElement();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).clearData();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).tab();
			waitTime(2);// need wait time to load the element

			// Asserting absence of optional coverages when building value is not provided
			// WDR
			Assertions.addInfo("Create Quote Page",
					"Asserting absence of optional coverages when building value is not provided");
			Assertions.verify(createQuotePage.windDrivenRainArrow.checkIfElementIsPresent(), false, "Create Quote Page",
					"Wind-Driven Rain is not displayed when building value is not provided", false, false);
			// Ordinance or Law
			Assertions.verify(createQuotePage.ordinanceLawArrow.checkIfElementIsPresent(), false, "Create Quote Page",
					"Ordinance or Law is not displayed when building value is not provided", false, false);

			// Adding building value
			scrollToBottomPage();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).waitTillVisibilityOfElement(60);
			createQuotePage.buildingValue.formatDynamicPath(0, 0).scrollToElement();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).clearData();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).appendData(testData1.get("L1B1-BldgValue"));
			createQuotePage.buildingValue.formatDynamicPath(0, 0).tab();
			Assertions.passTest("Create Quote Page", "Building Value is added");

			// Asserting default values for optional coverages after adding building value
			// and absence of monthly limit of indemnity before adding BI value
			scrollToTopPage();

			// EQ deductible
			Assertions.addInfo("Create Quote Page",
					"Asserting default values for optional coverages after adding building value and absence of monthly limit of indemnity before adding BI value");
			Assertions.verify(createQuotePage.earthquakeData.getData(), testData.get("EQDeductibleValue"),
					"Create Quote Page", "EQ deductible default value after adding Building value is "
							+ createQuotePage.earthquakeData.getData(),
					false, false);

			// EQ Sprinkler Leakage
			Assertions.verify(
					createQuotePage.earthquakeSprinklerLeakageData.checkIfElementIsPresent()
							&& createQuotePage.earthquakeSprinklerLeakageData.checkIfElementIsDisplayed(),
					false, "Create Quote Page",
					"EQ Sprinkler Leakage is not available when EQ deductible is not selected", false, false);

			// EQB
			Assertions.verify(createQuotePage.equipmentBreakdownData.getData(), testData.get("EquipmentBreakdown"),
					"Create Quote Page", "Equipment BreakDown default value after adding Building value is "
							+ createQuotePage.equipmentBreakdownData.getData(),
					false, false);

			// WDR
			Assertions.verify(createQuotePage.windDrivenRainData.getData(), testData.get("WindDrivenRain"),
					"Create Quote Page", "Wind-Driven Rain default value after adding Building value is "
							+ createQuotePage.windDrivenRainData.getData(),
					false, false);

			// if below assertion fails refer IO-20673
			// Ordinance or Law
			Assertions.verify(createQuotePage.ordinanceLawData.getData(), testData.get("OrdinanceOrLaw"),
					"Create Quote Page", "Ordinance or Law default value after adding Building value is "
							+ createQuotePage.ordinanceLawData.getData(),
					false, false);

			// Terrorism
			Assertions.verify(createQuotePage.terrorismData.getData(), testData.get("Terrorism"), "Create Quote Page",
					"Terrorism default value after adding Building value is " + createQuotePage.terrorismData.getData(),
					false, false);

			// Coverage Package
			Assertions.verify(createQuotePage.coverageExtensionPackageData.getData(),
					testData.get("CoverageExtensionPackage"), "Create Quote Page",
					"Coverage Extension Package default value after adding Building value is "
							+ createQuotePage.coverageExtensionPackageData.getData(),
					false, false);

			// Monthly Limit of Indemnity
			Assertions.verify(
					createQuotePage.monthlyLimitOfIndemnityData.checkIfElementIsPresent()
							&& createQuotePage.monthlyLimitOfIndemnityData.checkIfElementIsDisplayed(),
					false, "Create Quote Page", "Monthly Limit of Indemnity is not displayed when BI is not provided",
					false, false);

			// Adding BI and EQ values
			scrollToBottomPage();
			createQuotePage.bIValue.formatDynamicPath(0).waitTillVisibilityOfElement(60);
			createQuotePage.bIValue.formatDynamicPath(0).scrollToElement();
			createQuotePage.bIValue.formatDynamicPath(0).clearData();
			createQuotePage.bIValue.formatDynamicPath(0).appendData(testData1.get("L1-LocBI"));
			createQuotePage.bIValue.formatDynamicPath(0).tab();
			Assertions.passTest("Create Quote Page", "BI value is added");
			createQuotePage.earthquakeDeductibleArrow.scrollToElement();
			createQuotePage.earthquakeDeductibleArrow.click();
			createQuotePage.earthquakeDeductibleOption.formatDynamicPath(testData1.get("EQDeductibleValue"))
					.scrollToElement();
			createQuotePage.earthquakeDeductibleOption.formatDynamicPath(testData1.get("EQDeductibleValue")).click();
			Assertions.passTest("Create Quote Page",
					"EQ deductible is updated to " + createQuotePage.earthquakeData.getData());

			// Asserting presence of monthly limit of indemnity after adding BI value and
			// presence of EQSL after adding EQ value
			// EQ Sprinkler Leakage
			Assertions.addInfo("Create Quote Page",
					"Asserting presence of monthly limit of indemnity after adding BI value and presence of EQSL after adding EQ value");
			Assertions.verify(
					createQuotePage.earthquakeSprinklerLeakageData.checkIfElementIsPresent()
							&& createQuotePage.earthquakeSprinklerLeakageData.checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"EQ Sprinkler Leakage is available when EQ deductible is provided. Default value is "
							+ createQuotePage.earthquakeSprinklerLeakageData.getData(),
					false, false);

			// Monthly Limit of Indemnity
			Assertions.verify(
					createQuotePage.monthlyLimitOfIndemnityData.checkIfElementIsPresent()
							&& createQuotePage.monthlyLimitOfIndemnityData.checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Monthly Limit of Indemnity is displayed when BI is provided. Default value is "
							+ createQuotePage.monthlyLimitOfIndemnityData.getData(),
					false, false);

			// Adding the following Code CR IO-17656
			// createQuotePage
			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();
			Assertions.verify(
					modifyForms.override.checkIfElementIsPresent() && modifyForms.override.checkIfElementIsDisplayed(),
					true, "Modify Forms Page", "Modify Forms Page loaded successfully", false, false);
			modifyForms.specialConditionsAOP.waitTillPresenceOfElement(60);
			modifyForms.specialConditionsAOP.waitTillVisibilityOfElement(60);
			waitTime(2);
			modifyForms.specialConditionsAOP.deSelect();
			Assertions.passTest("Modify Forms Page", "Special Conditions of wind coverage is deselected");
			modifyForms.override.waitTillPresenceOfElement(60);
			modifyForms.override.scrollToElement();
			modifyForms.override.click();
			if (createQuotePage.continueButton.checkIfElementIsPresent()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
				createQuotePage.continueButton.waitTillInVisibilityOfElement(60);
			}

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// adding below code for CR IO-17656
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.verify(
					viewPrintFullQuotePage.backButton.checkIfElementIsPresent()
							&& viewPrintFullQuotePage.backButton.checkIfElementIsDisplayed(),
					true, "View Print Full Quote Page", "View Print Full Quote Page loaded successfully", false, false);
			Assertions.addInfo("View Print Full Quote Page", "Asserting the absence condition for windstorm, Wording");
			Assertions.verify(
					viewPrintFullQuotePage.termsAndConditions.formatDynamicPath(2, 7).getData()
							.contains("Named Insured is required to maintain separate flood "),
					false, "View Print Full Quote Page",
					"Named Insured is required to maintain separate flood insurance as a condition for windstorm, Wording is not displayed verified",
					false, false);

			// click on back button
			viewPrintFullQuotePage.scrollToTopPage();
			viewPrintFullQuotePage.backButton.scrollToElement();
			viewPrintFullQuotePage.backButton.click();
			Assertions.verify(
					accountOverviewPage.createAnotherQuote.checkIfElementIsPresent()
							&& accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "Account Overview Page loaded successfully", false, false);
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();
			Assertions.passTest("Account Overview Page", "Clicked on Create Another Quote successfully");

			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			// Selecting a peril as GL
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData1.get("Peril"));

			// Entering GL information
			Assertions.verify(
					gLInformationPage.continueButton.checkIfElementIsPresent()
							&& gLInformationPage.continueButton.checkIfElementIsDisplayed(),
					true, "GL Information Page", "GL Information Page loaded successfuly", false, false);
			gLInformationPage.enterGLInformation(testData);
			Assertions.passTest("GL Information Page", "GL Information entered successfully");

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterDeductiblesCommercialNew(testData1);

			// createQuotePage
			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();
			Assertions.verify(
					modifyForms.override.checkIfElementIsPresent() && modifyForms.override.checkIfElementIsDisplayed(),
					true, "Modify Forms Page", "Modify Forms Page loaded successfully", false, false);
			modifyForms.specialConditionsAOP.waitTillPresenceOfElement(60);
			modifyForms.specialConditionsAOP.deSelect();
			Assertions.passTest("Modify Forms Page", "Special Conditions of wind coverage is deselected");
			modifyForms.override.waitTillPresenceOfElement(60);
			modifyForms.override.scrollToElement();
			modifyForms.override.click();
			if (createQuotePage.continueButton.checkIfElementIsPresent()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
				createQuotePage.continueButton.waitTillInVisibilityOfElement(60);
			}

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			String anotherQuoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number 2 :  " + anotherQuoteNumber);

			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.verify(
					viewPrintFullQuotePage.backButton.checkIfElementIsPresent()
							&& viewPrintFullQuotePage.backButton.checkIfElementIsDisplayed(),
					true, "View Print Full Quote Page", "View Print Full Quote Page loaded successfully", false, false);
			Assertions.addInfo("View Print Full Quote Page",
					"Asserting the absence condition for windstorm, Wording for newly created quote");
			Assertions.verify(
					viewPrintFullQuotePage.termsAndConditions.formatDynamicPath(2, 7).getData()
							.contains("Named Insured is required to maintain separate flood "),
					false, "View Print Full Quote Page",
					"Named Insured is required to maintain separate flood insurance as a condition for windstorm, Wording is not displayed verified",
					false, false);

			// Sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Sign Out as USM successfully");

			// Adding following code for CR IO-18722
			// Sign In as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			homePage.enterPersonalLoginDetails();
			Assertions.passTest("Login Page", "Logged in as Producer successfully");
			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.passTest("Home Page", "Quote Searched successfully");
			Assertions.verify(
					accountOverviewPage.requestBind.checkIfElementIsPresent()
							&& accountOverviewPage.requestBind.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "Account Overview Page loaded successfully", false, false);
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account Overview Page", "Clicked Edit Deductible and Limits link successfully");
			Assertions.verify(
					createQuotePage.getAQuote.checkIfElementIsPresent()
							&& createQuotePage.getAQuote.checkIfElementIsDisplayed(),
					true, "Create Quote Page", "Create Quote Page loaded successfully", false, false);
			Assertions.addInfo("Create Quote Page", "Asserting the presence of Wind Driven rain");
			Assertions.verify(
					createQuotePage.windDrivenRainData.checkIfElementIsPresent()
							&& createQuotePage.windDrivenRainData.checkIfElementIsDisplayed(),
					true, "Create Quote Page", "Wind Driven Rain Dropdown displayed is verified", false, false);
			createQuotePage.buildingValue.formatDynamicPath(0, 0).waitTillVisibilityOfElement(60);
			createQuotePage.buildingValue.formatDynamicPath(0, 0).scrollToElement();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).clearData();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).tab();
			Assertions.passTest("Create Quote Page", "Removing the building values");
			Assertions.addInfo("Create Quote Page",
					"Asserting the absence of Wind Driven rain After Removing building value");
			Assertions.verify(
					createQuotePage.windDrivenRainData.checkIfElementIsPresent()
							&& createQuotePage.windDrivenRainData.checkIfElementIsDisplayed(),
					false, "Create Quote Page",
					"After Removing building values,Wind Driven Rain Dropdown not displayed is verified", false, false);
			createQuotePage.bPPValue.formatDynamicPath(0, 0).waitTillVisibilityOfElement(60);
			createQuotePage.bPPValue.formatDynamicPath(0, 0).scrollToElement();
			createQuotePage.bPPValue.formatDynamicPath(0, 0).appendData(testData.get("L1B1-BldgBPP"));
			createQuotePage.bPPValue.formatDynamicPath(0, 0).tab();
			createQuotePage.getAQuote.waitTillVisibilityOfElement(60);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on Get A Quote button successfully");
			Assertions.addInfo("Create Quote Page", "Asserting the WDR Warning message");
			Assertions.verify(createQuotePage.wdrWarning.formatDynamicPath("Wind-Driven Rain").checkIfElementIsPresent()
					&& createQuotePage.wdrWarning.formatDynamicPath("Wind-Driven Rain").checkIfElementIsDisplayed(),
					true, "Creaate Quote Page",
					"WDR warning message is "
							+ createQuotePage.wdrWarning.formatDynamicPath("Wind-Driven Rain").getData()
							+ "displayed is verified",
					false, false);

			// click on continue button
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number 3 :  " + quoteNumber);

			// Sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login", "Logged in to application successfully");

			// search the quote
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Searched the Quote successfully");

			// entering details in request bind page
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind button successfully");
			Assertions.verify(
					requestBindPage.submit.checkIfElementIsPresent()
							&& requestBindPage.submit.checkIfElementIsDisplayed(),
					true, "Request Bind Page", "Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");
			if (bindRequestSubmittedPage.homePage.checkIfElementIsPresent()
					&& bindRequestSubmittedPage.homePage.checkIfElementIsDisplayed()) {
				bindRequestSubmittedPage.homePage.scrollToElement();
				bindRequestSubmittedPage.homePage.click();
			}

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// getting the policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// click on endorse link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link successfully");
			Assertions.verify(endorsePolicyPage.cancelButton.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Endorse Policy Page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.appendData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Endorsement Effective Date is "
					+ testData.get("TransactionEffectiveDate") + "Entered successfully");
			endorsePolicyPage.changeCoverageOptionsLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Coverage Options link successfully");
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page loaded successfully", false, false);
			Assertions.addInfo("Create Quote Page",
					"Asserting the absence of Wind Driven Rain Dropdown After Removing building value");
			Assertions.verify(
					createQuotePage.windDrivenRainData.checkIfElementIsPresent()
							&& createQuotePage.windDrivenRainData.checkIfElementIsDisplayed(),
					false, "Create Quote Page",
					"After Removing building values,Wind Driven Rain Dropdown not displayed is verified", false, false);
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			Assertions.passTest("Create Quote Page", "Clicked on Continue Endorsement button successfully");
			Assertions.verify(
					endorseSummaryDetailsPage.policyLevelChangesToCol.formatDynamicPath(1).checkIfElementIsPresent(),
					false, "Endorse Summary Details Page",
					"After Removing Building values on Endorse Summary Details Page WDR not displayed is verified",
					false, false);

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 09", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 09", "Executed Successfully");
			}
		}
	}
}