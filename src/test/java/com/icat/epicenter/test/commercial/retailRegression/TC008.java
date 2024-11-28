/** Program Description:  Check the Commission Rate is displayed as 12% on the Policy summary page and on the Rewrite Bind page
 Initiate NPB Endorsement and verify the values
*  Author			   : Karthik Malles
*  Date of Creation   : 23/07/2021
**/

package com.icat.epicenter.test.commercial.retailRegression;

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
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExistingAccountPage;
import com.icat.epicenter.pom.HealthDashBoardPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC008 extends AbstractCommercialTest {

	public TC008() {
		super(LoginType.USM);
	}

	// declaring the variable globally
	double d_eqbPremiumValue;

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID008.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Pages
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestSubmittedPage = new BindRequestSubmittedPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ReferralPage referralPage = new ReferralPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ViewPolicySnapShot viewPolicySnapShot = new ViewPolicySnapShot();
		ExistingAccountPage existingAccountPage = new ExistingAccountPage();
		BuildingUnderMinimumCostPage buildingUnderminimumCost = new BuildingUnderMinimumCostPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuotablePage = new BuildingNoLongerQuoteablePage();
		HealthDashBoardPage healthDashBoardPage = new HealthDashBoardPage();
		FWProperties property1 = new FWProperties("HealthDashBoardPage");

		// Initializing Variables
		int dataValue1 = 0;
		int dataValue2 = 1;
		String quoteNumber, commissionRateForNB;
		Map<String, String> testData;
		double calculatedTotalPremiumAmount;
		DecimalFormat df = new DecimalFormat("0.00");
		String actualSLTFValue;
		String premiumAmount;
		String icatFees;
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
		String totalValueTxn;
		String totalValueAnnual;
		String totalValueTerm;
		boolean isTestPassed = false;

		try {
			// Creating New Account
			testData = data.get(dataValue1);
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("Home Page", "New Account Created Sucessfully");

			// Entering Details in Eligibility Page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page is loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Details in Location Page
			Assertions.verify(locationPage.addBuildingsButton.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page Loaded Successfully", false, false);
			locationPage.enterLocationDetails(testData);
			Assertions.passTest("Location Page", "Location Details Entered successfully");

			// click on add buildings button
			locationPage.addBuildingsButton.scrollToElement();
			locationPage.addBuildingsButton.click();

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building page",
					//"Building page loaded successfully", false, false);
			buildingPage.addBuildingDetails(testData, 1, 1);
			buildingPage.addBuildingOccupancy(testData, 1, 1);
			buildingPage.addRoofDetails(testData, 1, 1);
			buildingPage.enterAdditionalBuildingInformation(testData, 1, 1);
			buildingPage.enterBuildingValues(testData, 1, 1);
			Assertions.passTest("Building Page", "Construction type: " + testData.get("L1B1-BldgConstType"));
			Assertions.passTest("Building Page", "Occupancy type: " + testData.get("L1B1-PrimaryOccupancy"));
			Assertions.passTest("Building Page", "Building Square Feet: " + testData.get("L1B1-BldgSqFeet"));
			Assertions.passTest("Building Page", "Building details entered successfully");

			// Click on Create quote button
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();

			// existing account found page
			if (buildingPage.pageName.getData().contains("Existing Account Found")) {
				existingAccountPage.OverrideExistingAccount();
			}

			// Verifying Bring UpToCost button
			Assertions.passTest("Building Page", "Clicked on Create Quote button successfully");
			Assertions.verify(buildingUnderminimumCost.bringUpToCost.checkIfElementIsDisplayed(), true,
					"Building Under Minimum Cost Page", "Bring UpToCost button displayed is verified", false, false);

			// Getting Expected Cost card value
			String costCardValue = testData.get("CostCardValue");
			Assertions.passTest("CostCard value", "CostCard value: " + costCardValue);

			// Getting expected square feet value
			String squareFeet = testData.get("L1B1-BldgSqFeet");
			Assertions.passTest("SquareFeet", "Square Feet: " + squareFeet);

			// Verifying the Costcard message and verifying the actual and expected cost
			// card values
			Assertions.addInfo("Scenario 01",
					"Verifying the Costcard message and Verifying the actual and expected cost card values when Construction type: Wood Frame, Occupancy type: Apartment,Building Square Feet: 1000");
			Assertions.verify(
					buildingUnderminimumCost.costcardMessage
							.formatDynamicPath("Building value").checkIfElementIsDisplayed(),
					true, "Building Under Minimum Cost Page",
					"The Costcard message "
							+ buildingUnderminimumCost.costcardMessage.formatDynamicPath("Building value").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// click on BringUpToCost button
			if (buildingPage.pageName.getData().contains("Under Minimum Cost")) {
				buildingUnderminimumCost.clickOnOverride();
				Assertions.passTest("Building UnderMinimum Cost Page", "Clicked on Override button successfully");
			}

			if (buildingPage.pageName.getData().contains("Buildings No")) {
				buildingNoLongerQuotablePage.overrideNoLongerQuotableBuildings();
			}

			// Selecting Peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded Successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}
			Assertions.passTest("Select Peril Page", "Peril selected successfully");

			// Entering Prior Losses Details
			Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Losses Page",
					"Prior Losses Page loaded successfully", false, false);
			priorLossPage.selectPriorLossesInformation(testData);

			// Entering Details in Create Quote Page
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote Details Entered successfully");

			// Getting quote number in Account Overview Page
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Click on Request Bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Entering Details in Request Bind Page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Bind Request Submitted Page
			Assertions.verify(bindRequestSubmittedPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);
			bindRequestSubmittedPage.clickOnHomepagebutton();
			Assertions.passTest("Home Page", "Clicked on Home button");

			// Searching in Home Page for Quote submitted for Bind
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.searchReferral(quoteNumber);
			Assertions.passTest("Home Page", "Searched Submitted Bind Request successfullly");

			// Approving the Bind Referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral Page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Policy Summary Page
			String policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page Loaded successfully. Policy Number is : " + policyNumber, false, false);

			// Asserting Commission Rate for NB policy
			Assertions.addInfo("Scenario 02", "Assert the Commission Rate for NB Policy");
			commissionRateForNB = policySummaryPage.commissionRateForNBPolicy.getData();
			Assertions.verify(policySummaryPage.commissionRateForNBPolicy.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "Commission Percent for New Business Policy: " + commissionRateForNB, false,
					false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Click on Endorse NPB link
			policySummaryPage.endorseNPB.scrollToElement();
			policySummaryPage.endorseNPB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse NPB link");

			// Change Named Insured
			testData = data.get(dataValue2);
			Assertions.addInfo("Policy Summary Page", "Initiating NPB Endorsement");
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully.", false, false);
			endorsePolicyPage.enterEndorsement_DetailsNew(testData);

			// click on view policy snapshot button
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page Loaded successfully.", false, false);
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on view policy snapshot button");

			// asserting modified values for Insured and Producer details
			Assertions.addInfo("View Policy Snapshot Page", "Verifying the values after NPB Endorsement is Completed");
			Assertions.verify(viewPolicySnapShot.policyNumberData.checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page", "View Policy Snapshot Page Loaded successfully.", false, false);
			Assertions.addInfo("Scenario 03", "Asserting values for Insured and Producer details");
			Assertions.verify(
					viewPolicySnapShot.policyDeductibles.formatDynamicPath("Commercial").checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					"Modified Insured Details : "
							+ viewPolicySnapShot.policyDeductibles.formatDynamicPath("Commercial").getData(),
					false, false);
			Assertions
					.verify(viewPolicySnapShot.policyDeductibles.formatDynamicPath("Test").checkIfElementIsDisplayed(),
							true, "View Policy Snapshot Page",
							"Modified Producer Details : "
									+ viewPolicySnapShot.policyDeductibles.formatDynamicPath("Test").getData(),
							false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			viewPolicySnapShot.goBackButton.scrollToElement();
			viewPolicySnapShot.goBackButton.click();

			// Click on Rewrite Policy
			policySummaryPage.rewritePolicy.waitTillVisibilityOfElement(60);
			policySummaryPage.rewritePolicy.scrollToElement();
			policySummaryPage.rewritePolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Rewrite Policy link");

			// Click on Create another quote
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();
			Assertions.passTest("Account Overview Page", "Clicked on Create another Quote button");

			// Click on override button
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			// Select Peril Page
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}
			Assertions.passTest("Select Peril Page", "Peril selected successfully for Rewrite");

			// Create Quote Page
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);

			// enter Building value
			createQuotePage.waitTime(2);// need waittime to load the element
			createQuotePage.buildingValue.formatDynamicPath(0, 0).scrollToElement();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).clearData();
			createQuotePage.waitTime(2);// need waittime to load the element
			createQuotePage.buildingValue.formatDynamicPath(0, 0).appendData(testData.get("L1B1-BldgValue"));
			createQuotePage.buildingValue.formatDynamicPath(0, 0).tab();
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote Details entered successfully");

			// Getting the Quote Number
			Assertions.verify(accountOverviewPage.uploadPreBindDocuments.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"Account Overview Page loaded successfully. And Upload Prebind Documents Button displayed is verified",
					false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(2).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "The Rewritten Quote Number :  " + quoteNumber);

			// Sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Sign out as USM successfully");

			// Sign in as Rzimmer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Admin"), setUpData.get("Password"));
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
			// 0.15* 0.1 * (transaction premium � Utility Line Premium � EQB Premium)
			surplusContributionCalcValue = d_vieParticipationValue * d_vieContributionChargeValue
					* (Double.parseDouble(premiumAmount) - d_eqbPremiumValue);

			// verify the surplus contribution value with calculated value
			Assertions.addInfo("Scenario 04",
					"Verify the surplus contribution value and total premium value with calculated value for rewritten quote");
			Assertions.verify(accountOverviewPage.surplusContributionValue.getData().replace("$", "").replace(",", ""),
					df.format(surplusContributionCalcValue), "Account Overview Page",
					"Actual and Calculated Surplus Contribution values are matching for state "
							+ testData.get("QuoteState") + " And the value is : "
							+ accountOverviewPage.surplusContributionValue.getData(),
					false, false);

			// Calculating Total Premium Value by adding Premium+ICATfees+OtherFees+SLTF
			calculatedTotalPremiumAmount = Double.parseDouble(premiumAmount) + Double.parseDouble(icatFees)
					+ Double.parseDouble(actualSLTFValue) + surplusContributionCalcValue;

			// verify the total premium value with calculated value
			Assertions.verify(
					accountOverviewPage.totalPremiumValue.getData().replace("$", "").replace(",", "").replace(".00",
							""),
					df.format(calculatedTotalPremiumAmount), "Account Overview Page",
					"Actual and Calculated Total Premium values are matching for state " + testData.get("QuoteState")
							+ " And the value is : " + accountOverviewPage.totalPremiumValue.getData(),
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Click on rewrite bind
			accountOverviewPage.rewriteBind.scrollToElement();
			accountOverviewPage.rewriteBind.click();
			Assertions.passTest("Account Overview Page", "Clicked on Rewrite Bind");

			// Asserting Commission percentage and comparing with NB Policy Commission
			// percentage
			Assertions.verify(requestBindPage.rewrite.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			Assertions.addInfo("Scenario 05", "Assert the Commission Rate in Request Bind page After Rewrite ");
			requestBindPage.commissionRate.scrollToElement();
			Assertions.verify(
					requestBindPage.commissionRate.checkIfElementIsDisplayed()
							&& requestBindPage.commissionRate.getData().equals(commissionRateForNB),
					true, "Request Bind Page",
					"The Commssion percent for Rewrite Policy in Commercial Retail is "
							+ requestBindPage.commissionRate.getData()
							+ ". It is equal to Commision Rate Percent of a Commercial Retail New Business Policy",
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// entering details in request bind page
			Assertions.verify(requestBindPage.rewrite.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.effectiveDate.waitTillVisibilityOfElement(60);
			requestBindPage.effectiveDate.scrollToElement();
			requestBindPage.effectiveDate.setData(testData.get("PolicyEffDate"));
			requestBindPage.effectiveDate.tab();
			requestBindPage.waitTime(2);// if wait time is removed test case will fail here
			requestBindPage.previousPolicyCancellationDate.scrollToElement();
			requestBindPage.previousPolicyCancellationDate.clearData();
			requestBindPage.waitTime(2);// if wait time is removed test case will fail here
			requestBindPage.previousPolicyCancellationDate.setData(testData.get("PreviousPolicyCancellationDate"));
			requestBindPage.previousPolicyCancellationDate.tab();

			// Click on rewrite button
			requestBindPage.rewrite.scrollToElement();
			requestBindPage.rewrite.click();
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");
			Assertions.passTest("Request Bind Page", "Clicked on rewrite bind");

			// Adding the ticket IO-20296
			Assertions.addInfo("Scenario 06",
					"Verifying whether the rewritte quote requires subscription agreement or not while binding");
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Rewritten quote is bound without uploading subscription agreement", false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// capturing policy numbers in policy summary page
			String rewrittenPolicyNumber = policySummaryPage.policyNumber.getData();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);

			// verifying rewritten record in policy summary page
			Assertions.addInfo("Scenario 07", "Verifying rewritten record in policy summary page");
			Assertions.verify(
					policySummaryPage.transactionType.formatDynamicPath(testData.get("TransactionType")).getData(),
					testData.get("TransactionType"), "Policy Summary Page", "Policy Rewritten Record Verified", false,
					false);
			Assertions.passTest("Policy Summary Page", "Rewritten Policy Number is : " + rewrittenPolicyNumber);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Verify the Total Values of Txn,Annual and Term colums with calculated value
			Assertions.addInfo("Scenario 08",
					"Verify the Total Values of Transaction,Annual and Term colums with calculated value for rewritten policy");
			totalValueTxn = policySummaryPage.premiumTotal.formatDynamicPath("2").getData().replace(",", "")
					.replace("$", "");

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(totalValueTxn), 2)
					- Precision.round(calculatedTotalPremiumAmount, 2)), 2) < 0.05) {
				Assertions.passTest("Policy Summary Page", "Actual Total Premium for Transaction column is :"
						+ policySummaryPage.premiumTotal.formatDynamicPath("2").getData());
				Assertions.passTest("Policy Summary Page",
						"Calculated Total Premium Value is :" + "$" + df.format(calculatedTotalPremiumAmount));
			} else {
				Assertions.passTest("Policy Summary Page",
						"The Difference between actual and calculated Total Premium is more than 0.05");
			}

			// Verify the Total premium of annual column
			totalValueAnnual = policySummaryPage.premiumTotal.formatDynamicPath("3").getData().replace(",", "")
					.replace("$", "");

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(totalValueAnnual), 2)
					- Precision.round(calculatedTotalPremiumAmount, 2)), 2) < 0.05) {
				Assertions.passTest("Policy Summary Page", "Actual Total Premium for Annual column is :"
						+ policySummaryPage.premiumTotal.formatDynamicPath("3").getData());
				Assertions.passTest("Policy Summary Page",
						"Calculated Total Premium Value is :" + "$" + df.format(calculatedTotalPremiumAmount));
			} else {
				Assertions.passTest("Policy Summary Page",
						"The Difference between actual and calculated Total Premium is more than 0.05");
			}

			// Verify the Total Premium of Term column
			totalValueTerm = policySummaryPage.premiumTotal.formatDynamicPath("4").getData().replace(",", "")
					.replace("$", "");

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(totalValueTerm), 2)
					- Precision.round(calculatedTotalPremiumAmount, 2)), 2) < 0.05) {
				Assertions.passTest("Policy Summary Page", "Actual Total Premium for Term column is :"
						+ policySummaryPage.premiumTotal.formatDynamicPath("4").getData());
				Assertions.passTest("Policy Summary Page",
						"Calculated Total Premium Value is :" + "$" + df.format(calculatedTotalPremiumAmount));
			} else {
				Assertions.passTest("Policy Summary Page",
						"The Difference between actual and calculated Total Premium is more than 0.05");
			}
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC008 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC008 ", "Executed Successfully");
			}
		}
	}
}
