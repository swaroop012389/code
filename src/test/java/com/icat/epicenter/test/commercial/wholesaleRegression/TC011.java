/** Program Description: Create a AOP Policy with NH deductibles and assert the relevant data on PolicySnapShot page
 *  Author			   : Abha
 *  Date of Creation   : 11/26/2019
**/

package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.FWProperties;
import com.NetServAutomationFramework.generic.WebDriverManager;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.ConfirmBindRequestPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.EndorseSummaryDetailsPage;
import com.icat.epicenter.pom.HealthDashBoardPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PreferenceOptionsPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.RmsModelResultsPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC011 extends AbstractCommercialTest {
	double d_eqbPremiumValue;

	public TC011() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID011.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		LoginPage loginPage = new LoginPage();
		PreferenceOptionsPage preferenceOptionsPage = new PreferenceOptionsPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		ViewPolicySnapShot viewPolicySnapshotPage = new ViewPolicySnapShot();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		ConfirmBindRequestPage confirmBindRequestPage = new ConfirmBindRequestPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		HealthDashBoardPage healthDashBoardPage = new HealthDashBoardPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		EndorseSummaryDetailsPage endorseSummaryDetailsPage = new EndorseSummaryDetailsPage();
		ViewOrPrintFullQuotePage viewpOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		RmsModelResultsPage rmsModelResultsPage = new RmsModelResultsPage();
		FWProperties property1 = new FWProperties("HealthDashBoardPage");

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		int data_Value4 = 3;
		Map<String, String> testData = data.get(data_Value1);
		Map<String, String> testData1 = data.get(data_Value2);
		Map<String, String> testData2 = data.get(data_Value3);
		Map<String, String> testData3 = data.get(data_Value4);
		String surplusContribution = "Term VIE Surplus Contribution";
		String premiumAmount;
		String icatFees;
		double calculatedTotalPremiumAmount;
		DecimalFormat df = new DecimalFormat("0.00");
		String actualSLTFValue;
		List<WebElement> eqbPremiumValue;
		String eqbPremValue1;
		Iterator<WebElement> itr;
		int vieParticipationValuelength;
		String vieParticipationValue;
		int vieContributionChargelength;
		String vieContributionChargeValue;
		double d_vieParticipationValue;
		double d_vieContributionChargeValue;
		double surplusContributionCalcValue;
		String actualsurplusContributionTxn;
		String actualsurplusContributionAnnual;
		String actualsurplusContributionTerm;
		boolean isTestPassed = false;

		try {
			// Added code for Taxes and fees update for the MS state
			// Sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Sign out as usm successfully");

			// Sign in as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			homePage.enterPersonalLoginDetails();
			Assertions.passTest("Login", "Logged in as Producer successfully");

			// Click on user preference link and enter the details
			homePage.userPreferences.scrollToElement();
			homePage.userPreferences.click();
			Assertions.passTest("Home Page", "Clicked on User Preferences");

			// Add broker fees
			testData = data.get(data_Value1);
			Assertions.passTest("Preference Options Page", "Preference Options Page loaded successfully");
			preferenceOptionsPage.addBrokerFees(testData);
			Assertions.passTest("Preference Options Page", "Details entered successfully");

			// Navigate to homepage
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Sign out as producer successfully");
			// New code ended

			// Login as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login", "Logged in to application successfully");

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
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote1 details entered successfully");
			Assertions.passTest("Create Quote Page", "NH deductible for Quote 1 is " + testData.get("DeductibleValue"));

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number : 1 is " + quoteNumber);

			// Creating another quote
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();
			Assertions.passTest("Account Overview Page", "Clicked on Create Another quote button");

			// Click on override button
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData1);
			Assertions.passTest("Create Quote Page", "Quote2 details entered successfully");
			Assertions.passTest("Create Quote Page",
					"NH deductible for Quote 2 is " + testData1.get("DeductibleValue"));

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			String quoteNumber2 = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number : 2 is " + quoteNumber2);

			// Calculating Total Premium Value by adding Premium+ICATfees+OtherFees+SLTF
			premiumAmount = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");
			icatFees = accountOverviewPage.feesValue.getData().replaceAll("[^\\d-.]", "");
			actualSLTFValue = accountOverviewPage.sltfValue.getData().replaceAll("[^\\d-.]", "");

			// Sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Sign out as usm successfully");

			// Sign in as Rzimmer
			loginPage.refreshPage();
			loginPage.enterLoginDetails("swilcox", setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in to application successfully as Rzimmer");

			// click on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Click on admin link
			homePage.adminLink.scrollToElement();
			homePage.adminLink.click();
			Assertions.passTest("Home Page", "Clicked on admin link successfully");

			// click on policy inspector page
			healthDashBoardPage.policyInspectorLink.scrollToElement();
			healthDashBoardPage.policyInspectorLink.click();
			Assertions.passTest("Health Dashboard Page", "Clicked on Policy Inspector link successfully");

			// enter the quote number in quote number field
			healthDashBoardPage.quoteNumberField.setData(quoteNumber2);

			// click on find Policy
			healthDashBoardPage.findPolicyBtn.scrollToElement();
			healthDashBoardPage.findPolicyBtn.click();

			// Get EQB Premium value and store in a variable
			if (healthDashBoardPage.eqbPremium.checkIfElementIsPresent()
					&& healthDashBoardPage.eqbPremium.checkIfElementIsDisplayed()) {
				eqbPremiumValue = WebDriverManager.getCurrentDriver()
						.findElements(By.xpath(property1.getProperty("xp_EQBPremium")));
				itr = eqbPremiumValue.iterator();
				while (itr.hasNext()) {
					eqbPremValue1 = eqbPremiumValue.get(1).getText().toString();
					d_eqbPremiumValue = Double.parseDouble(eqbPremValue1);
					break;
				}
			}

			// click on back to tool list
			healthDashBoardPage.toolList.scrollToElement();
			healthDashBoardPage.toolList.click();

			// Sign out as swilcox
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Rzimmer successfully");

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in to application successfully as USM");

			// click on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// search the quote
			homePage.searchQuote(quoteNumber2);
			Assertions.passTest("Home page", "Quote searched successfully");

			// click on quote specifics
			accountOverviewPage.quoteSpecifics.scrollToElement();
			accountOverviewPage.quoteSpecifics.click();
			Assertions.passTest("Account Overview Page", "Clicked on Quote Specifics");

			// Adding the ticket IO-20942
			// Verify the presence of Vie Participation and vie contribution charge
			Assertions
					.verify(accountOverviewPage.vieParicipation.checkIfElementIsDisplayed(), true,
							"Account Overview Page", "The VIE participation value : "
									+ accountOverviewPage.vieParicipation.getData() + " displayed is verified",
							false, false);

			Assertions.verify(accountOverviewPage.vieContributionCharge.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "The VIE contribution charge value : "
							+ accountOverviewPage.vieContributionCharge.getData() + " displayed is verified",
					false, false);

			// get VIE participation value
			accountOverviewPage.vieParicipation.waitTillVisibilityOfElement(60);
			vieParticipationValuelength = accountOverviewPage.vieParicipation.getData().length();
			vieParticipationValue = accountOverviewPage.vieParicipation.getData().substring(0,
					vieParticipationValuelength - 1);
			d_vieParticipationValue = Double.parseDouble(vieParticipationValue) / 100;

			// get vie participation charge
			vieContributionChargelength = accountOverviewPage.vieContributionCharge.getData().length();
			vieContributionChargeValue = accountOverviewPage.vieContributionCharge.getData().substring(0,
					vieContributionChargelength - 1);
			d_vieContributionChargeValue = Double.parseDouble(vieContributionChargeValue) / 100;

			// calculate surplus contribution value
			// 0.15* 0.1 * (transaction premium – Utility Line Premium – EQB Premium)
			surplusContributionCalcValue = d_vieParticipationValue * d_vieContributionChargeValue
					* (Double.parseDouble(premiumAmount) - d_eqbPremiumValue);

			// verify the surplus contribution value with calculated value
			// Adding the ticket IO-20942
			Assertions.passTest("Account overview page",
					"Calculated surplus contribution value is " + surplusContributionCalcValue);
			Assertions.verify(accountOverviewPage.surplusContributionValue.getData().replace("$", "").replace(",", ""),
					df.format(surplusContributionCalcValue), "Account Overview Page",
					"Actual and Calculated Surplus Contribution values are matching for state "
							+ testData.get("QuoteState") + " And the actual value is : "
							+ accountOverviewPage.surplusContributionValue.getData(),
					false, false);

			// Calculating Total Premium Value by adding Premium+ICATfees+OtherFees+SLTF
			calculatedTotalPremiumAmount = Double.parseDouble(premiumAmount) + Double.parseDouble(icatFees)
					+ Double.parseDouble(actualSLTFValue) + surplusContributionCalcValue;

			// Click on Request Bind
			testData = data.get(data_Value1);
			requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber2);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
			// added below code to handle order of the quote displayed(Order is different in
			// UAT and QA)on the account overview page
			if (requestBindPage.submit.checkIfElementIsPresent()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuote(quoteNumber2);
				accountOverviewPage.requestBind.scrollToElement();
				accountOverviewPage.requestBind.click();
			}
			// Enter bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);

			// Verify the quote premium is equal to calculated value
			String actualQuotePremium = requestBindPage.quotePremium.getData().replace("$", "").replace(",", "");
			double d_actualQuotePremium = Double.parseDouble(actualQuotePremium);
			if (Precision.round(Math
					.abs(Precision.round(d_actualQuotePremium, 2) - Precision.round(calculatedTotalPremiumAmount, 2)),
					2) < 0.05) {
				Assertions.passTest("Request Bind Page",
						"Actual Total Premium Amount:" + requestBindPage.quotePremium.getData());
				Assertions.passTest("Request Bind Page",
						"Calculated Total Premium :" + "$" + df.format(calculatedTotalPremiumAmount));
			} else {
				Assertions.passTest("Request Bind Page",
						"The Difference between actual TotalPremium and calculated TotalPremium is more than 0.05");
			}

			// requestBindPage.enterBindDetails(testData);
			requestBindPage.enterPolicyDetails(testData);
			requestBindPage.enterPaymentInformation(testData);
			requestBindPage.addInspectionContact(testData);
			requestBindPage.addContactInformation(testData);
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();

			// Verify the quote premium value on Confirm Request bind page
			confirmBindRequestPage.waitTime(2);// need waittime to load the element
			int lengthofQuotePremiumValue = confirmBindRequestPage.grandTotal.getData().length();
			String quotePremiumConfimBindPage = confirmBindRequestPage.grandTotal.getData()
					.substring(15, lengthofQuotePremiumValue).replace(",", "").replace("$", "");

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(quotePremiumConfimBindPage), 2)
					- Precision.round(calculatedTotalPremiumAmount, 2)), 2) < 0.05) {
				Assertions.passTest("Confirm Request Bind Page",
						"Actual TotalPremium Amount:" + confirmBindRequestPage.grandTotal.getData());
				Assertions.passTest("Confirm Request Bind Page",
						"Calculated Total Premium :" + "$" + df.format(calculatedTotalPremiumAmount));
			} else {
				Assertions.passTest("Confirm Request Bind Page",
						"The Difference between actual TotalPremium and calculated TotalPremium is more than 0.05");
			}

			// click on confirm bind
			requestBindPage.confirmBind();
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home Page", "Clicked on Home button");

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber2);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// Approve Referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Approval page loaded successfully", false, false);

			// carrier selection
			if (requestBindPage.carrierWarningMsg.checkIfElementIsPresent()
					&& requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed()) {
				requestBindPage.approveRequestCommercialData(testData);
			} else {
				requestBindPage.approveRequest();
				Assertions.passTest("Request Bind Page", "Bind Request approved successfully");
			}

			// Get policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Policy Number is " + policyNumber, false, false);

			// Verify the surplus transaction values of transaction,annual, and term columns
			actualsurplusContributionTxn = policySummaryPage.surplusContributionValue.formatDynamicPath("1").getData()
					.replace(",", "").replace("$", "");

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(actualsurplusContributionTxn), 2)
					- Precision.round(surplusContributionCalcValue, 2)), 2) < 0.05) {
				Assertions.passTest("Policy Summary Page",
						"Actual Surplus Contribution Value for Transaction column is :"
								+ policySummaryPage.surplusContributionValue.formatDynamicPath("1").getData());
				Assertions.passTest("Policy Summary Page",
						"Calculated Surplus Contribution Value is :" + "$" + df.format(surplusContributionCalcValue));
			} else {
				Assertions.passTest("Policy Summary Page",
						"The Difference between actual and calculated Surplus Contribution is more than 0.05");
			}

			// Verify the surplus transaction of annual column
			actualsurplusContributionAnnual = policySummaryPage.surplusContributionValue.formatDynamicPath("2")
					.getData().replace(",", "").replace("$", "");

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(actualsurplusContributionAnnual), 2)
					- Precision.round(surplusContributionCalcValue, 2)), 2) < 0.05) {
				Assertions.passTest("Policy Summary Page", "Actual Surplus Contribution Value for Annual column is :"
						+ policySummaryPage.surplusContributionValue.formatDynamicPath("2").getData());
				Assertions.passTest("Policy Summary Page",
						"Calculated Surplus Contribution Value is :" + "$" + df.format(surplusContributionCalcValue));
			} else {
				Assertions.passTest("Policy Summary Page",
						"The Difference between actual and calculated Surplus Contribution is more than 0.05");
			}

			// Verify the surplus transaction of Term column
			actualsurplusContributionTerm = policySummaryPage.surplusContributionValue.formatDynamicPath("3").getData()
					.replace(",", "").replace("$", "");

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(actualsurplusContributionTerm), 2)
					- Precision.round(surplusContributionCalcValue, 2)), 2) < 0.05) {
				Assertions.passTest("Policy Summary Page", "Actual Surplus Contribution Value for Term column is :"
						+ policySummaryPage.surplusContributionValue.formatDynamicPath("3").getData());
				Assertions.passTest("Policy Summary Page",
						"Calculated Surplus Contribution Value is :" + "$" + df.format(surplusContributionCalcValue));
			} else {
				Assertions.passTest("Policy Summary Page",
						"The Difference between actual and calculated Surplus Contribution is more than 0.05");
			}

			// Clicking on View Policy Snapshot link to view the details
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Snap Shot Page", "Policy Snap Shot Page loaded successfully");

			// Verifying NH Deductibles on Policy snapshot page
			Assertions.addInfo("Policy Snap Shot Page", "Verifying NH Deductibles on Policy snapshot page");
			viewPolicySnapshotPage.policyDeductibles.formatDynamicPath("Named Hurricane")
					.waitTillVisibilityOfElement(60);
			Assertions.verify(
					viewPolicySnapshotPage.policyDeductibles
							.formatDynamicPath("Named Hurricane").getData().contains("Named Hurricane"),
					true, "Policy Snap Shot Page",
					"Named Hurricane deductible details are verified</br>"
							+ viewPolicySnapshotPage.policyDeductibles.formatDynamicPath("Named Hurricane").getData(),
					false, false);
			Assertions.verify(
					viewPolicySnapshotPage.policyOtherDeductibles.formatDynamicPath("All Other Causes").getData()
							.contains("All Other Causes"),
					true, "Policy Snap Shot Page",
					"Policy Other deductible details are verified</br>" + viewPolicySnapshotPage.policyOtherDeductibles
							.formatDynamicPath("All Other Causes").getData(),
					false, false);

			// Verify the presence of Term Surplus Contribution on policy snapshot page
			Assertions.addInfo("Policy Snap Shot Page",
					"Verifying the presence of Term Surplus Contribution on policy snapshot page and it should include the word Term");
			Assertions.verify(viewPolicySnapshotPage.termSurplusContribution.getData().equals(surplusContribution),
					true, "Policy Snap Shot Page", "The " + viewPolicySnapshotPage.termSurplusContribution.getData()
							+ " displayed is verified with the word Term",
					false, false);
			Assertions.verify(viewPolicySnapshotPage.termSurplusContributionData.checkIfElementIsDisplayed(), true,
					"Policy Snap Shot Page",
					"The Term Surplus contribution Value "
							+ viewPolicySnapshotPage.termSurplusContributionData.getData() + " displayed is verified",
					false, false);

		// Adding the Ticket IO-20842
		// Getting the premium and fee values
		String termPremiumTotal = viewPolicySnapshotPage.policyDeductiblesValues
				.formatDynamicPath("Term Premium Total", "2").getData().replace("$", "").replace(",", "");
		String termInspFees = viewPolicySnapshotPage.policyDeductiblesValues
				.formatDynamicPath("Term Inspections Fees Total", "2").getData().replace("$", "").replace(",", "");
		String termPolicyFees = viewPolicySnapshotPage.policyDeductiblesValues.formatDynamicPath("Term Policy Fee", "2")
				.getData().replace("$", "").replace(",", "");
		String termSurplusContribution = viewPolicySnapshotPage.policyDeductiblesValues
				.formatDynamicPath("Term VIE Surplus Contribution", "2").getData().replace("$", "").replace(",", "");
		String termGrandTotal = viewPolicySnapshotPage.policyDeductiblesValues
				.formatDynamicPath("Term Premium & Fees Total", "2").getData().replace("$", "").replace(",", "");

			// Calculate the Grand Total value
			double grandTotal = Double.parseDouble(termPremiumTotal) + Double.parseDouble(termInspFees)
					+ Double.parseDouble(termPolicyFees) + Double.parseDouble(termSurplusContribution);

			// Verify the Grand Total value with calculated value
			if (Precision.round(
					Math.abs(Precision.round(Double.parseDouble(termGrandTotal), 2) - Precision.round(grandTotal, 2)),
					2) == 0.0) {
				Assertions.passTest("Policy Snap Shot Page",
						"The Actual Term Premium & Fees Total Value " + "$" + termGrandTotal);
				Assertions.passTest("Policy Snap Shot Page",
						"The Calculated Term Premium & Fees Total Value " + "$" + grandTotal);
			} else {
				Assertions.passTest("Policy Snap Shot Page",
						"The Difference between actual  and calculated Term Premium & Fees Total is more than 0");
			}

			// Click on back
			viewPolicySnapshotPage.goBackButton.scrollToElement();
			viewPolicySnapshotPage.goBackButton.click();
			Assertions.passTest("Policy Snap Shot Page", "Clicked on Back button");

			// Clicking on PB Endorsement link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB Link Successfully");

			// Entering Transaction Eff Date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.setData(testData2.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// Click on Change Coverage Option link
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Coverage Options link");

			// Entering endorsement Details
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page Loaded Successfully", false, false);
			Assertions
					.verify(createQuotePage.equipmentBreakdownData.getData(), testData.get("EquipmentBreakdown"),
							"Create Quote Page", "The Original EQB Value "
									+ createQuotePage.equipmentBreakdownData.getData() + " displayed is verified",
							false, false);
			createQuotePage.editOptionalCoverageDetailsPNB(testData2);
			Assertions.verify(createQuotePage.equipmentBreakdownData.getData(), testData2.get("EquipmentBreakdown"),
					"Create Quote Page", "The Latest EQB Value is " + createQuotePage.equipmentBreakdownData.getData(),
					false, false);
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Click On next Button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Getting Transaction column values on endorse policy page
			String premiumTxn = endorsePolicyPage.transactionPremiumFee.formatDynamicPath("2").getData()
					.replace("$", "").replace(",", "");
			String inspectionFeeTxn = endorsePolicyPage.inspectionFee.formatDynamicPath("2").getData().replace("$", "")
					.replace(",", "");
			String policyFeeTxn = endorsePolicyPage.policyFee.formatDynamicPath("2").getData().replace("$", "")
					.replace(",", "");
			String otherFeeTxn = endorsePolicyPage.otherFees.formatDynamicPath("2").getData().replace("$", "")
					.replace(",", "");
			String surplusContributionTxn = endorsePolicyPage.surplusContributionValue.formatDynamicPath("2").getData()
					.replace("$", "").replace(",", "");
			String totalTxn = endorsePolicyPage.totalTerm.formatDynamicPath("2").getData().replace("$", "").replace(",",
					"");

			// Adding the CR IO-20843
			// Click on view endt quote button
			endorsePolicyPage.viewEndtQuoteButton.scrollToElement();
			endorsePolicyPage.viewEndtQuoteButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on View Endorsement Quote button");

			// Verify the transaction surplus contribution value with endorsement quote
			// surplus contribution value
			Assertions.verify(endorsePolicyPage.closeButton.checkIfElementIsDisplayed(), true, "Endorsement Quote Page",
					"Endorsement Quote Page Loaded successfully", false, false);
			String supluscontributionEndtquote = viewpOrPrintFullQuotePage.surplusContributionValue.getData()
					.replace("$", "").replace(",", "");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(surplusContributionTxn), 2)
					- Precision.round(Double.parseDouble(supluscontributionEndtquote), 2)), 2) == 0.0) {
				Assertions.passTest("Endorsement Quote Page",
						"The Surplus Contribution value of Transaction column is " + "$" + surplusContributionTxn);
				Assertions.passTest("Endorsement Quote Page",
						"The Surplus Contribution value in Endorsement Quote is " + "$" + supluscontributionEndtquote);
			} else {
				Assertions.passTest("Endorsement Quote Page",
						"The Difference between actual  and calculated surplus contribution is more than 0");
			}

			// click on close
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorsement Quote Page", "Clicked on Close button");

			// Calculate Transaction Total value on endorse policy page
			double totalTxnEndorsePolicy = Double.parseDouble(premiumTxn) + Double.parseDouble(inspectionFeeTxn)
					+ Double.parseDouble(policyFeeTxn) + Double.parseDouble(otherFeeTxn)
					+ Double.parseDouble(surplusContributionTxn);

			// Verify the Total value of Txn column with actual value on endorse policy page
			if (Precision.round(Math
					.abs(Precision.round(Double.parseDouble(totalTxn), 2) - Precision.round(totalTxnEndorsePolicy, 2)),
					2) < 0.05) {
				Assertions.passTest("Endorse Policy Page",
						"The Actual Total value of Transaction column is " + "$" + totalTxn);
				Assertions.passTest("Endorse Policy Page",
						"The Calculated Total value of Transaction column is " + "$" + totalTxnEndorsePolicy);
			} else {
				Assertions.passTest("Endorse Policy Page",
						"The Difference between actual TotalPremium and calculated TotalPremium is more than 0.05");
			}

			// Getting Annual column values on endorse policy page
			String premiumAnnual = endorsePolicyPage.transactionPremiumFee.formatDynamicPath("3").getData()
					.replace("$", "").replace(",", "");
			String inspectionFeeAnnual = endorsePolicyPage.inspectionFee.formatDynamicPath("3").getData()
					.replace("$", "").replace(",", "");
			String policyFeeAnnual = endorsePolicyPage.policyFee.formatDynamicPath("3").getData().replace("$", "")
					.replace(",", "");
			String otherFeeAnnual = endorsePolicyPage.otherFees.formatDynamicPath("3").getData().replace("$", "")
					.replace(",", "");
			String surplusContributionAnnual = endorsePolicyPage.surplusContributionValue.formatDynamicPath("3")
					.getData().replace("$", "").replace(",", "");
			String totalAnnual = endorsePolicyPage.totalTerm.formatDynamicPath("3").getData().replace("$", "")
					.replace(",", "");

			// Calculate Annual Total value on endorse policy page
			double totalAnnualEndorsePolicy = Double.parseDouble(premiumAnnual)
					+ Double.parseDouble(inspectionFeeAnnual) + Double.parseDouble(policyFeeAnnual)
					+ Double.parseDouble(otherFeeAnnual) + Double.parseDouble(surplusContributionAnnual);

			// Verify the Total value of Annual column with actual value on endorse policy
			// page
			if (Precision.round(Math.abs(
					Precision.round(Double.parseDouble(totalAnnual), 2) - Precision.round(totalAnnualEndorsePolicy, 2)),
					2) < 0.05) {
				Assertions.passTest("Endorse Policy Page",
						"The Actual Total value of Annual column is " + "$" + totalAnnual);
				Assertions.passTest("Endorse Policy Page",
						"The Calculated Total value of Annual column is " + "$" + totalAnnualEndorsePolicy);
			} else {
				Assertions.passTest("Endorse Policy Page",
						"The Difference between actual TotalPremium and calculated TotalPremium is more than 0.05");
			}

			// Getting Term total column values on endorse policy page
			String premiumTerm = endorsePolicyPage.transactionPremiumFee.formatDynamicPath("4").getData()
					.replace("$", "").replace(",", "");
			String inspectionFeeTerm = endorsePolicyPage.inspectionFee.formatDynamicPath("4").getData().replace("$", "")
					.replace(",", "");
			String policyFeeTerm = endorsePolicyPage.policyFee.formatDynamicPath("4").getData().replace("$", "")
					.replace(",", "");
			String otherFeeTerm = endorsePolicyPage.otherFees.formatDynamicPath("4").getData().replace("$", "")
					.replace(",", "");
			String surplusContributionTerm = endorsePolicyPage.surplusContributionValue.formatDynamicPath("4").getData()
					.replace("$", "").replace(",", "");
			String totalTerm = endorsePolicyPage.totalTerm.formatDynamicPath("4").getData().replace("$", "")
					.replace(",", "");

			// Calculate Term Total value on endorse policy page
			double totalTermEndorsePolicy = Double.parseDouble(premiumTerm) + Double.parseDouble(inspectionFeeTerm)
					+ Double.parseDouble(policyFeeTerm) + Double.parseDouble(otherFeeTerm)
					+ Double.parseDouble(surplusContributionTerm);

			// Verify the Total value of term column with actual value on endorse policy
			// page
			if (Precision.round(Math.abs(
					Precision.round(Double.parseDouble(totalTerm), 2) - Precision.round(totalTermEndorsePolicy, 2)),
					2) < 0.05) {
				Assertions.passTest("Endorse Policy Page",
						"The Actual Total value of Term column is " + "$" + totalTerm);
				Assertions.passTest("Endorse Policy Page",
						"The Calculated Total value of Term column is " + "$" + totalTermEndorsePolicy);
			} else {
				Assertions.passTest("Endorse Policy Page",
						"The Difference between actual TotalPremium and calculated TotalPremium is more than 0.05");
			}

			// Click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Complete Button Successfully");
			Assertions.passTest("Endorse Summary Details Page", "Endorse Summary Details Page loaded successfully");

			// Getting Transaction column values on endorse summary details page
			String premiumTxnEndtSummary = endorseSummaryDetailsPage.transactionPremiumFee.formatDynamicPath("2")
					.getData().replace("$", "").replace(",", "");
			String inspectionFeeTxnEndtSummary = endorseSummaryDetailsPage.inspectionFee.formatDynamicPath("2")
					.getData().replace("$", "").replace(",", "");
			String policyFeeTxnEndtSummary = endorseSummaryDetailsPage.policyFee.formatDynamicPath("2").getData()
					.replace("$", "").replace(",", "");
			String otherFeeTxnEndtSummary = endorseSummaryDetailsPage.otherFees.formatDynamicPath("2").getData()
					.replace("$", "").replace(",", "");
			String surplusContributionTxnEndtSummary = endorseSummaryDetailsPage.surplusContributionValue
					.formatDynamicPath("2").getData().replace("$", "").replace(",", "");
			String totalTxnEndtSummary = endorseSummaryDetailsPage.totalTerm.formatDynamicPath("2").getData()
					.replace("$", "").replace(",", "");

			// Calculate Transaction Total value on endorse policy page
			double totalTxnEndorseSummary = Double.parseDouble(premiumTxnEndtSummary)
					+ Double.parseDouble(inspectionFeeTxnEndtSummary) + Double.parseDouble(policyFeeTxnEndtSummary)
					+ Double.parseDouble(otherFeeTxnEndtSummary)
					+ Double.parseDouble(surplusContributionTxnEndtSummary);

			// Verify the Total value of Txn column with actual value on endorse summary
			// page
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(totalTxnEndtSummary), 2)
					- Precision.round(totalTxnEndorseSummary, 2)), 2) < 0.05) {
				Assertions.passTest("Endorse Summary Page",
						"The Actual Total value of Transaction column is " + "$" + totalTxnEndtSummary);
				Assertions.passTest("Endorse Summary Page",
						"The Calculated Total value of Transaction column is " + "$" + totalTxnEndorseSummary);
			} else {
				Assertions.passTest("Endorse Summary Page",
						"The Difference between actual TotalPremium and calculated TotalPremium is more than 0.05");
			}

			// Getting Annual column values on endorse summary details page
			String premiumAnnualEndtSummary = endorseSummaryDetailsPage.transactionPremiumFee.formatDynamicPath("3")
					.getData().replace("$", "").replace(",", "");
			String inspectionFeeAnnualEndtSummary = endorseSummaryDetailsPage.inspectionFee.formatDynamicPath("3")
					.getData().replace("$", "").replace(",", "");
			String policyFeeAnnualEndtSummary = endorseSummaryDetailsPage.policyFee.formatDynamicPath("3").getData()
					.replace("$", "").replace(",", "");
			String otherFeeAnnualEndtSummary = endorseSummaryDetailsPage.otherFees.formatDynamicPath("3").getData()
					.replace("$", "").replace(",", "");
			String surplusContributionAnnualEndtSummary = endorseSummaryDetailsPage.surplusContributionValue
					.formatDynamicPath("3").getData().replace("$", "").replace(",", "");
			String totalAnnualEndtSummary = endorseSummaryDetailsPage.totalTerm.formatDynamicPath("3").getData()
					.replace("$", "").replace(",", "");

			// Calculate Annual Total value on endorse summary details page
			double totalAnnualEndorseSummary = Double.parseDouble(premiumAnnualEndtSummary)
					+ Double.parseDouble(inspectionFeeAnnualEndtSummary)
					+ Double.parseDouble(policyFeeAnnualEndtSummary) + Double.parseDouble(otherFeeAnnualEndtSummary)
					+ Double.parseDouble(surplusContributionAnnualEndtSummary);

			// Verify the Total value of Txn column with actual value on endorse summary
			// details page
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(totalAnnualEndtSummary), 2)
					- Precision.round(totalAnnualEndorseSummary, 2)), 2) < 0.05) {
				Assertions.passTest("Endorse Summary Page",
						"The Actual Total value of Annual column is " + "$" + totalAnnualEndtSummary);
				Assertions.passTest("Endorse Summary Page",
						"The Calculated Total value of Annual column is " + "$" + totalAnnualEndorseSummary);
			} else {
				Assertions.passTest("Endorse Summary Page",
						"The Difference between actual TotalPremium and calculated TotalPremium is more than 0.05");
			}

			// Getting Term total column values on endorse summary details page
			String premiumTermEndtSummary = endorseSummaryDetailsPage.transactionPremiumFee.formatDynamicPath("4")
					.getData().replace("$", "").replace(",", "");
			String inspectionFeeTermEndtSummary = endorseSummaryDetailsPage.inspectionFee.formatDynamicPath("4")
					.getData().replace("$", "").replace(",", "");
			String policyFeeTermEndtSummary = endorseSummaryDetailsPage.policyFee.formatDynamicPath("4").getData()
					.replace("$", "").replace(",", "");
			String otherFeeTermEndtSummary = endorseSummaryDetailsPage.otherFees.formatDynamicPath("4").getData()
					.replace("$", "").replace(",", "");
			String surplusContributionTermEndtSummary = endorseSummaryDetailsPage.surplusContributionValue
					.formatDynamicPath("4").getData().replace("$", "").replace(",", "");
			String totalTermEndtSummary = endorseSummaryDetailsPage.totalTerm.formatDynamicPath("4").getData()
					.replace("$", "").replace(",", "");

			// Calculate Term Total value on endorse policy page
			double totalTermEndorseSummary = Double.parseDouble(premiumTermEndtSummary)
					+ Double.parseDouble(inspectionFeeTermEndtSummary) + Double.parseDouble(policyFeeTermEndtSummary)
					+ Double.parseDouble(otherFeeTermEndtSummary)
					+ Double.parseDouble(surplusContributionTermEndtSummary);

			// Verify the Total value of Txn column with actual value on endorse summary
			// details page
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(totalTermEndtSummary), 2)
					- Precision.round(totalTermEndorseSummary, 2)), 2) < 0.05) {
				Assertions.passTest("Endorse Summary Page",
						"The Actual Total value of Term column is " + "$" + totalTermEndtSummary);
				Assertions.passTest("Endorse Summary Page",
						"The Calculated Total value of Term column is " + "$" + totalTermEndorseSummary);
			} else {
				Assertions.passTest("Endorse Summary Page",
						"The Difference between actual TotalPremium and calculated TotalPremium is more than 0.05");
			}

			// Click on close button
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Close Button Successfully");

			// Sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Sign out as USM successfully");

			// Sign in as Rzimmer
			loginPage.refreshPage();
			loginPage.enterLoginDetails("swilcox", setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in to application successfully as Rzimmer");

			// click on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Click on admin link
			homePage.adminLink.scrollToElement();
			homePage.adminLink.click();
			Assertions.passTest("Home Page", "Clicked on admin link successfully");

			// click on policy inspector page
			healthDashBoardPage.policyInspectorLink.scrollToElement();
			healthDashBoardPage.policyInspectorLink.click();
			Assertions.passTest("Health Dashboard Page", "Clicked on Policy Inspector link successfully");

			// enter the quote number in quote number field
			healthDashBoardPage.policyNumberField.setData(policyNumber);

			// click on find Policy
			healthDashBoardPage.findPolicyBtn.scrollToElement();
			healthDashBoardPage.findPolicyBtn.click();

			// Get EQB Premium value and store in a variable
			if (healthDashBoardPage.eqbPremiumEndt.checkIfElementIsPresent()
					&& healthDashBoardPage.eqbPremiumEndt.checkIfElementIsDisplayed()) {
				eqbPremiumValue = WebDriverManager.getCurrentDriver()
						.findElements(By.xpath(property1.getProperty("xp_EQBPremiumEndt")));
				itr = eqbPremiumValue.iterator();
				while (itr.hasNext()) {
					eqbPremValue1 = eqbPremiumValue.get(0).getText().toString();
					d_eqbPremiumValue = Double.parseDouble(eqbPremValue1);
					break;
				}
			}

			// click on back to tool list
			healthDashBoardPage.toolList.scrollToElement();
			healthDashBoardPage.toolList.click();

			// Sign out as swilcox
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Rzimmer successfully");

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in to application successfully as USM");

			// click on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// search the policy
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Policy Summary Page", "Policy Summary Page Loaded successfully");

			// Calculate surplus contribution value for endorse policy and summary page
			// for txn column on endorse policy page
			double surplusContributionCalcValueTxnEndt = d_vieParticipationValue * d_vieContributionChargeValue
					* (Double.parseDouble(premiumTxn) - d_eqbPremiumValue);

			// Verify the calculated surplus contribution value with actual value for txn
			// column
			Assertions.verify(surplusContributionTxn, df.format(surplusContributionCalcValueTxnEndt),
					"Endorse Policy Page",
					"The Calculated and Actual Surplus contribution Values of Transaction column are same.The Value is "
							+ "$" + surplusContributionTxn,
					false, false);

			// for annual columnn endorse policy page
			double surplusContributionCalcValueAnnualEndt = d_vieParticipationValue * d_vieContributionChargeValue
					* (Double.parseDouble(premiumAnnual) - d_eqbPremiumValue);

			// Verify the calculated surplus contribution value with actual value for annual
			// column
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(surplusContributionAnnual), 2)
					- Precision.round(surplusContributionCalcValueAnnualEndt, 2)), 2) < 0.5) {
				Assertions.passTest("Endorse Policy Page",
						"The Calculated Surplus contribution Values of Annual column  is " + "$"
								+ surplusContributionCalcValueAnnualEndt);
				Assertions.passTest("Endorse Policy Page",
						"The Actual Surplus contribution Values of Annual column  is " + "$"
								+ surplusContributionAnnual);
			} else {
				Assertions.passTest("Endorse Policy Page",
						"The Difference between actual and calculated Surplus contribution is more than 0.5");
			}

			// for Term columnn endorse policy page
			double surplusContributionCalcValueTermEndt = d_vieParticipationValue * d_vieContributionChargeValue
					* (Double.parseDouble(premiumTerm) - d_eqbPremiumValue);

			// Verify the calculated surplus contribution value with actual value for term
			// column
			Assertions.verify(surplusContributionTerm, df.format(surplusContributionCalcValueTermEndt),
					"Endorse Policy Page",
					"The Calculated and Actual Surplus contribution Values of Term column are same.The Value is " + "$"
							+ surplusContributionTerm,
					false, false);

			// for txn column on endorse summary page
			double surplusContributionCalcValueTxnEndtSummary = d_vieParticipationValue * d_vieContributionChargeValue
					* (Double.parseDouble(premiumTxnEndtSummary) - d_eqbPremiumValue);

			// Verify the calculated surplus contribution value with actual value for txn
			// column
			Assertions.verify(surplusContributionTxnEndtSummary, df.format(surplusContributionCalcValueTxnEndtSummary),
					"Endorse Summary Page",
					"The Calculated and Actual Surplus contribution Values of Transaction column are same.The Value is "
							+ "$" + surplusContributionTxnEndtSummary,
					false, false);

			// for annual columnn endorse summary page
			double surplusContributionCalcValueAnnualEndtSummary = d_vieParticipationValue
					* d_vieContributionChargeValue * (Double.parseDouble(premiumAnnualEndtSummary) - d_eqbPremiumValue);

			// Verify the calculated surplus contribution value with actual value for annual
			// column
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(surplusContributionAnnualEndtSummary), 2)
					- Precision.round(surplusContributionCalcValueAnnualEndtSummary, 2)), 2) < 0.5) {
				Assertions.passTest("Endorse Summary Page",
						"The Calculated Surplus contribution Values of Annual column  is " + "$"
								+ surplusContributionCalcValueAnnualEndtSummary);
				Assertions.passTest("Endorse Summary Page",
						"The Actual Surplus contribution Values of Annual column  is " + "$"
								+ surplusContributionAnnualEndtSummary);
			} else {
				Assertions.passTest("Endorse Summary Page",
						"The Difference between actual and calculated Surplus contribution is more than 0.5");
			}

			// for Term columnn endorse policy page
			double surplusContributionCalcValueTermEndtSummary = d_vieParticipationValue * d_vieContributionChargeValue
					* (Double.parseDouble(premiumTermEndtSummary) - d_eqbPremiumValue);

			// Verify the calculated surplus contribution value with actual value for term
			// column
			Assertions.verify(surplusContributionTermEndtSummary,
					df.format(surplusContributionCalcValueTermEndtSummary), "Endorse Summary Page",
					"The Calculated and Actual Surplus contribution Values of Term column are same.The Value is " + "$"
							+ surplusContributionTermEndtSummary,
					false, false);

			// Initiate Rewrite Txn
			// Clicking on Rewrite link
			policySummaryPage.rewritePolicy.scrollToElement();
			policySummaryPage.rewritePolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Rewrite Policy link Successfully");

			// Creating another Quote to Rewrite
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			accountOverviewPage.createAnotherQuote.checkIfElementIsPresent();
			accountOverviewPage.createAnotherQuote.click();
			Assertions.passTest("Account Overview Page", "Clicked on Create another quote button");

			// click on override button
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			// selecting peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData3.get("Peril"));
			Assertions.passTest("Select Peril Page", "Peril selected successfully");

			// modifying deductibles and coverages in create quote page
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialPNB(testData3);
			Assertions.passTest("Create Quote Page", "Deductibles and coverages details entered successfully");

			// click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Getting the Quote Number
			Assertions.verify(accountOverviewPage.uploadPreBindDocuments.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"Account Overview Page loaded successfully. And Upload Prebind Documents Button displayed is verified",
					false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(2).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "The Rewritten Quote Number :  " + quoteNumber);

			// Adding Guy Carpenter scenario TC02
			Assertions.addInfo("Scenario 01 ",
					"Asserting the Modeling data like GC17, ELR Premium, peril AAl and Peril ERL Labels and it's values for Re-written quote");

			Assertions.addInfo("Scenario 01", "Sceanrio TC01 started");
			Assertions.verify(
					accountOverviewPage.viewModelResultsLink.checkIfElementIsDisplayed()
							&& accountOverviewPage.viewModelResultsLink.checkIfElementIsPresent(),
					true, "Account Overview Page", "View Model Result Link Displayed", false, false);

			// Clicking on View Model Result Link
			accountOverviewPage.viewModelResultsLink.scrollToElement();
			accountOverviewPage.viewModelResultsLink.click();

			// Asserting the Modeling data like GC17, ELR Premium, peril AAl and Peril ERL
			// Labels and it's values
			// Verifying the GC17 Label
			Assertions.verify(rmsModelResultsPage.gc17Label.getData().contains("GC17"), true, "View Model Results Page",
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
									.formatDynamicPath("Peril ELR", 1).checkIfElementIsDisplayed(),
					true, "View Model Results Page",
					"Guy Carpenter ELR Label is dasplayed as :" + rmsModelResultsPage.guyCarpenterELRLabel.getData()
							+ "and it's value displayed on View Model Results Page is: "
							+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril ELR", 1).getData(),
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
					"TIV value displayed on View Model Results Page is: " + rmsModelResultsPage.tivValue.getData(),
					false, false);

			// Calculating GC ELR value
			// Verify the calculation of ELR: GC Peril ELR = GC Peril AAL amount/ELR premium
			String ELRPremium = rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("ELR Premium", 1).getData()
					.replace("$", "").replace(",", "");
			String PerilAAL = rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril AAL", 1).getData()
					.replace("$", "").replace(",", "");

			double PerilELR = Precision.round((Double.parseDouble(PerilAAL) / Double.parseDouble(ELRPremium)) * 100, 1);
			String actualPerilELRStr = rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril ELR", 1)
					.getData().replace("%", "");
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

			// Clicking on close button on View Model Results Page
			rmsModelResultsPage.closeButton.click();
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Clicking on Alternative quote option from Other Deductible Option table
			accountOverviewPage.quoteOptions1TotalPremium.scrollToElement();
			accountOverviewPage.quoteOptions1TotalPremium.click();

			// Asserting the Modeling data like GC17, ELR Premium, peril AAl and Peril ERL
			// Labels and it's values for Alternative quote
			Assertions.addInfo("Scenario 02 ",
					"Asserting the Modeling data like GC17, ELR Premium, peril AAl and Peril ERL Labels and it's values for Alternative quote");
			// Clicking on View Model Result Link
			accountOverviewPage.viewModelResultsLink.scrollToElement();
			accountOverviewPage.viewModelResultsLink.click();

			// Asserting the Modeling data like GC17, ELR Premium, peril AAl and Peril ERL
			// Labels and it's values
			// Verifying the GC17 Label
			Assertions.verify(rmsModelResultsPage.gc17Label.getData().replace(" ", "").contains("GC17"), true, "View Model Results Page",
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
									.formatDynamicPath("Peril ELR", 1).checkIfElementIsDisplayed(),
					true, "View Model Results Page",
					"Guy Carpenter ELR Label is dasplayed as :" + rmsModelResultsPage.guyCarpenterELRLabel.getData()
							+ "and it's value displayed on View Model Results Page is: "
							+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril ELR", 1).getData(),
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
					"TIV value displayed on View Model Results Page is: " + rmsModelResultsPage.tivValue.getData(),
					false, false);

			// Calculating GC ELR value
			// Verify the calculation of ELR: GC Peril ELR = GC Peril AAL amount/ELR premium
			String ELRPremium1 = rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("ELR Premium", 1).getData()
					.replace("$", "").replace(",", "");
			String PerilAAL1 = rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril AAL", 1).getData()
					.replace("$", "").replace(",", "");

			double PerilELR1 = Precision.round((Double.parseDouble(PerilAAL1) / Double.parseDouble(ELRPremium1)) * 100,
					1);
			String actualPerilELRStr1 = rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril ELR", 1)
					.getData().replace("%", "");
			double actualPerilELR1 = Double.parseDouble(actualPerilELRStr1);

			Assertions.verify(PerilELR == actualPerilELR, true, "View Model Results Page",
					"The Actual and Calculated Peril ELR are matching", false, false);

			if (PerilELR == actualPerilELR) {
				Assertions.passTest("View Model Results Page", "Calculated Peril ELR: " + PerilELR1 + "%");
				Assertions.passTest("View Model Results Page", "Actual Peril ELR: " + actualPerilELR1 + "%");
			} else {
				Assertions.verify(PerilELR1, actualPerilELR1, "View Model Results Page",
						"The Actual and Calculated values are not matching", false, false);
			}
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			rmsModelResultsPage.closeButton.waitTillVisibilityOfElement(60);
			rmsModelResultsPage.closeButton.click();

			// Sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Sign out as USM successfully");

			// Sign in as Rzimmer
			loginPage.refreshPage();
			loginPage.enterLoginDetails("swilcox", setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in to application successfully as Rzimmer");

			// click on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Click on admin link
			homePage.adminLink.scrollToElement();
			homePage.adminLink.click();
			Assertions.passTest("Home Page", "Clicked on admin link successfully");

			// click on policy inspector page
			healthDashBoardPage.policyInspectorLink.scrollToElement();
			healthDashBoardPage.policyInspectorLink.click();
			Assertions.passTest("Health Dashboard Page", "Clicked on Policy Inspector link successfully");

			// enter the quote number in quote number field
			healthDashBoardPage.quoteNumberField.setData(quoteNumber);

			// click on find Policy
			healthDashBoardPage.findPolicyBtn.scrollToElement();
			healthDashBoardPage.findPolicyBtn.click();

			// Get EQB Premium value and store in a variable
			if (healthDashBoardPage.eqbPremium.checkIfElementIsPresent()
					&& healthDashBoardPage.eqbPremium.checkIfElementIsDisplayed()) {
				eqbPremiumValue = WebDriverManager.getCurrentDriver()
						.findElements(By.xpath(property1.getProperty("xp_EQBPremium")));
				itr = eqbPremiumValue.iterator();
				while (itr.hasNext()) {
					eqbPremValue1 = eqbPremiumValue.get(1).getText().toString();
					d_eqbPremiumValue = Double.parseDouble(eqbPremValue1);
					break;
				}
			}

			// click on back to tool list
			healthDashBoardPage.toolList.scrollToElement();
			healthDashBoardPage.toolList.click();

			// Sign out as swilcox
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Rzimmer successfully");

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in to application successfully as USM");

			// click on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// search the policy
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Account Overview Page", "Account Overview Page Loaded successfully");

			// Calculating Total Premium Value by adding Premium+ICATfees+OtherFees+SLTF
			premiumAmount = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");
			icatFees = accountOverviewPage.feesValue.getData().replaceAll("[^\\d-.]", "");
			actualSLTFValue = accountOverviewPage.sltfValue.getData().replaceAll("[^\\d-.]", "");
			String otherFees = accountOverviewPage.otherFees.getData().replaceAll("[^\\d-.]", "");

			// click on quote specifics
			accountOverviewPage.quoteSpecifics.scrollToElement();
			accountOverviewPage.quoteSpecifics.click();

			// get VIE participation value
			accountOverviewPage.vieParicipation.waitTillVisibilityOfElement(60);
			vieParticipationValuelength = accountOverviewPage.vieParicipation.getData().length();
			vieParticipationValue = accountOverviewPage.vieParicipation.getData().substring(0,
					vieParticipationValuelength - 1);
			d_vieParticipationValue = Double.parseDouble(vieParticipationValue) / 100;

			// get vie participation charge
			vieContributionChargelength = accountOverviewPage.vieContributionCharge.getData().length();
			vieContributionChargeValue = accountOverviewPage.vieContributionCharge.getData().substring(0,
					vieContributionChargelength - 1);
			d_vieContributionChargeValue = Double.parseDouble(vieContributionChargeValue) / 100;

			// calculate surplus contribution value
			// 0.15* 0.1 * (transaction premium – Utility Line Premium – EQB Premium)
			surplusContributionCalcValue = d_vieParticipationValue * d_vieContributionChargeValue
					* (Double.parseDouble(premiumAmount) - d_eqbPremiumValue);

			// verify the surplus contribution value with calculated value
			Assertions.passTest("Account overview page",
					"Calculated surplus contribution value is " + surplusContributionCalcValue);
			Assertions.verify(accountOverviewPage.surplusContributionValue.getData().replace("$", "").replace(",", ""),
					df.format(surplusContributionCalcValue), "Account Overview Page",
					"Actual and Calculated Surplus Contribution values are matching for state "
							+ testData.get("QuoteState") + " And the value is : "
							+ accountOverviewPage.surplusContributionValue.getData(),
					false, false);

			// Calculating Total Premium Value by adding Premium+ICATfees+OtherFees+SLTF
			calculatedTotalPremiumAmount = Double.parseDouble(premiumAmount) + Double.parseDouble(icatFees)
					+ Double.parseDouble(actualSLTFValue) + Double.parseDouble(otherFees)
					+ surplusContributionCalcValue;

			// verify the total premium value with calculated value
			Assertions.verify(
					accountOverviewPage.totalPremiumValue.getData().replace("$", "").replace(",", "").replace(".00",
							""),
					df.format(calculatedTotalPremiumAmount), "Account Overview Page",
					"Actual and Calculated Total Premium values are matching for state " + testData.get("QuoteState")
							+ " And the value is : " + accountOverviewPage.totalPremiumValue.getData(),
					false, false);

			// Click on rewrite bind
			accountOverviewPage.rewriteBind.scrollToElement();
			accountOverviewPage.rewriteBind.click();
			Assertions.passTest("Account Overview Page", "Clicked on Rewrite Bind");

			Assertions.verify(requestBindPage.rewrite.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.effectiveDate.waitTillVisibilityOfElement(60);
			requestBindPage.effectiveDate.scrollToElement();
			requestBindPage.effectiveDate.setData(testData.get("PolicyEffDate"));
			requestBindPage.effectiveDate.tab();
			requestBindPage.waitTime(2);// need wait time to load the element
			requestBindPage.previousPolicyCancellationDate.waitTillVisibilityOfElement(60);
			requestBindPage.previousPolicyCancellationDate.scrollToElement();
			requestBindPage.previousPolicyCancellationDate.setData(testData.get("PreviousPolicyCancellationDate"));
			requestBindPage.previousPolicyCancellationDate.tab();

			// Click on Rewrite
			requestBindPage.rewrite.scrollToElement();
			requestBindPage.rewrite.click();
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// capturing policy numbers in policy summary page
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			Assertions.addInfo("Policy Summary Page", "Asserting Rewritten Policy Number");
			policyNumber = policySummaryPage.rewrittenPolicyNumber.getData();
			Assertions.passTest("Policy Summary Page", "Rewritten Policy Number is : " + policyNumber);

			// Verify the surplus transaction values of transaction,annual, and term columns
			// of rewritten policy
			actualsurplusContributionTxn = policySummaryPage.surplusContributionValue.formatDynamicPath("1").getData()
					.replace(",", "").replace("$", "");

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(actualsurplusContributionTxn), 2)
					- Precision.round(surplusContributionCalcValue, 2)), 2) < 0.05) {
				Assertions.passTest("Policy Summary Page",
						"Actual Surplus Contribution Value for Transaction column is :"
								+ policySummaryPage.surplusContributionValue.formatDynamicPath("1").getData());
				Assertions.passTest("Policy Summary Page",
						"Calculated Surplus Contribution Value is :" + "$" + df.format(surplusContributionCalcValue));
			} else {
				Assertions.passTest("Policy Summary Page",
						"The Difference between actual and calculated Surplus Contribution is more than 0.05");
			}

			// Verify the surplus transaction of annual column
			actualsurplusContributionAnnual = policySummaryPage.surplusContributionValue.formatDynamicPath("2")
					.getData().replace(",", "").replace("$", "");

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(actualsurplusContributionAnnual), 2)
					- Precision.round(surplusContributionCalcValue, 2)), 2) < 0.05) {
				Assertions.passTest("Policy Summary Page", "Actual Surplus Contribution Value for Annual column is :"
						+ policySummaryPage.surplusContributionValue.formatDynamicPath("2").getData());
				Assertions.passTest("Policy Summary Page",
						"Calculated Surplus Contribution Value is :" + "$" + df.format(surplusContributionCalcValue));
			} else {
				Assertions.passTest("Policy Summary Page",
						"The Difference between actual and calculated Surplus Contribution is more than 0.05");
			}

			// Verify the surplus transaction of Term column
			actualsurplusContributionTerm = policySummaryPage.surplusContributionValue.formatDynamicPath("3").getData()
					.replace(",", "").replace("$", "");

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(actualsurplusContributionTerm), 2)
					- Precision.round(surplusContributionCalcValue, 2)), 2) < 0.05) {
				Assertions.passTest("Policy Summary Page", "Actual Surplus Contribution Value for Term column is :"
						+ policySummaryPage.surplusContributionValue.formatDynamicPath("3").getData());
				Assertions.passTest("Policy Summary Page",
						"Calculated Surplus Contribution Value is :" + "$" + df.format(surplusContributionCalcValue));
			} else {
				Assertions.passTest("Policy Summary Page",
						"The Difference between actual and calculated Surplus Contribution is more than 0.05");
			}

			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 11", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 11", "Executed Successfully");
			}
		}
	}
}
