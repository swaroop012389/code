/** Program Description:  Check the Wordings printed For Florida above the signature section on the quote document page
verify the outstanding renewal must be deleted message when policy is put under non renewal and added IO-20882
*  Author			   : Sowndarya
*  Date of Creation   : 22/07/2021
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
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HealthDashBoardPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.ReinstatePolicyPage;
import com.icat.epicenter.pom.RenewalIndicatorsPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC016 extends AbstractCommercialTest {

	public TC016() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID016.xls";
	}

	double d_eqbPremiumValue;
	String inspFee;
	String policyFee;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Pages
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		BuildingPage buildingPage = new BuildingPage();
		LocationPage locationPage = new LocationPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		RenewalIndicatorsPage renewalIndicatorsPage = new RenewalIndicatorsPage();
		HealthDashBoardPage healthDashBoardPage = new HealthDashBoardPage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		ReinstatePolicyPage reinsatePolicyPage = new ReinstatePolicyPage();
		FWProperties property1 = new FWProperties("HealthDashBoardPage");

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		Map<String, String> testData = data.get(data_Value1);
		List<WebElement> eqbPremiumValue;
		Iterator<WebElement> itr;
		String eqbPremValue1;
		String premiumAmount;
		int vieParticipationValuelength;
		String vieParticipationValue;
		int vieContributionChargelength;
		String vieContributionChargeValue;
		double d_vieParticipationValue;
		double d_vieContributionChargeValue;
		double surplusContributionCalcValue;
		DecimalFormat df = new DecimalFormat("0.00");
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

			// selecting peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}

			// enter prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// adding ticket IO-21777
			Assertions.passTest("Scenario 01 ",
					"Validating the Request Deductible buy back link Not displayed on quote");
			Assertions.verify(accountOverviewPage.requestDeductibleBuyBackBtn.checkIfElementIsDisplayed(), false,
					"Account Overview Page", "Request Deductible buy back link Not displayed", false, false);
			Assertions.passTest("Scenario 01 ", "Scenario 01 Ended");

			// Calculating Total Premium Value by adding Premium+ICATfees+OtherFees+SLTF
			premiumAmount = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");

			// Sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Sign out as usm successfully");

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

			// search the quote
			homePage.searchQuote(quoteNumber);

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

			surplusContributionCalcValue = d_vieParticipationValue * d_vieContributionChargeValue
					* (Double.parseDouble(premiumAmount) - d_eqbPremiumValue);

			// Click on Request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home Page", "Clicked on Home button");

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote is searched successfully");

			// Click on Open Referral Link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// Click on Quote link
			policySummarypage.quoteNoLink.formatDynamicPath(quoteNumber).scrollToElement();
			policySummarypage.quoteNoLink.formatDynamicPath(quoteNumber).click();
			Assertions.passTest("Policy Summary Page", "Clicked on Quote Number link");

			// Asserting Florida Wording in Quote Page
			Assertions.addInfo("Scenario 01",
					"Assert the Florida Wordings present above the signature section on the quote");
			Assertions.verify(
					viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
							.formatDynamicPath("Florida Surplus Lines Service").checkIfElementIsDisplayed(),
					true, "Quote Document Page",
					"The Wordings "
							+ viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
									.formatDynamicPath("Florida Surplus Lines Service").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// search the policy
			homePage.searchPolicy(policyNumber);

			// Added IO-20882
			// Click on cancel policy
			policySummarypage.cancelPolicy.scrollToElement();
			policySummarypage.cancelPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Cancel Policy link");

			// Click on cancel arrow
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			cancelPolicyPage.waitTime(2);// wait time is need to load the element

			// Select cancellation reason
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			cancelPolicyPage.legalNoticeWording.scrollToElement();
			cancelPolicyPage.legalNoticeWording.setData(testData.get("Cancellation_LegalNoticeWording"));

			// Click on next
			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();

			Assertions.verify(cancelPolicyPage.shortRatedRadioBtn.checkIfElementIsSelected(), true,
					"Cancel Policy Page", "Short-Rated Option selected by default is verified", false, false);

			// Get the Short rated value
			Assertions.addInfo("Scenario 02",
					"Verifying the Original,Earned and Rreturned values when Shortrated option is selected");
			for (int i = 2; i <= 4; i++) {
				if (i == 2) {
					Assertions.addInfo("Cancel Policy Page",
							"Verifying the Original Column Values when Short Rated Option is selected");
				}
				if (i == 3) {
					Assertions.addInfo("Cancel Policy Page",
							"Verifying the Earned Column Values when Short Rated Option is selected");
				}
				if (i == 4) {
					Assertions.addInfo("Cancel Policy Page",
							"Verifying the Returned Column Values when Short Rated Option is selected");
				}
				String premium = cancelPolicyPage.premiumValue.formatDynamicPath(i).getData().replace("$", "");
				if (i == 3) {
					inspFee = cancelPolicyPage.inspectionFeeEarned.getData().replace("$", "").replace(",", "");
					policyFee = cancelPolicyPage.policyFeeEarned.getData().replace("$", "").replace(",", "");
				} else {
					inspFee = cancelPolicyPage.inspectionFee.formatDynamicPath(i).getData().replace("$", "");
					policyFee = cancelPolicyPage.policyFee.formatDynamicPath(i).getData().replace("$", "");
				}
				String sltf = cancelPolicyPage.taxesAndFees.formatDynamicPath(i).getData().replace("$", "");
				String surplusContribution = cancelPolicyPage.surplusContributionVlaue.formatDynamicPath(i).getData()
						.replace("$", "");
				String premiumTotal = cancelPolicyPage.policyTotal.formatDynamicPath(i).getData().replace("$", "");

				// Calculate surplus contribution value
				surplusContributionCalcValue = d_vieParticipationValue * d_vieContributionChargeValue
						* (Double.parseDouble(premium) - d_eqbPremiumValue);

				// Verify the surplus contribution value with calculated value
				if (Precision.round(Math.abs(Precision.round(Double.parseDouble(surplusContribution), 2)
						- Precision.round(surplusContributionCalcValue, 2)), 2) < 0.05) {
					Assertions.passTest("Cancel Policy Page",
							"Actual Surplus Contibution Value : " + "$" + surplusContribution);
					Assertions.passTest("Cancel Policy Page",
							"Calculated Surplus Contibution Value :" + "$" + df.format(surplusContributionCalcValue));
				} else {
					Assertions.verify(surplusContribution, df.format(surplusContributionCalcValue),
							"Cancel Policy Page", "Actual and Calculated Surplus Contribution values are not matching",
							false, false);
				}

				// calculate Policy total for short rated original column
				double premiumTotalCalc = Double.parseDouble(premium) + Double.parseDouble(inspFee)
						+ Double.parseDouble(policyFee) + Double.parseDouble(sltf)
						+ Double.parseDouble(surplusContribution);

				// Verify the Policy total value with calculated value for original column
				if (Precision.round(Math.abs(
						Precision.round(Double.parseDouble(premiumTotal), 2) - Precision.round(premiumTotalCalc, 2)),
						2) < 0.05) {
					Assertions.passTest("Cancel Policy Page", "Actual Total Premium Value : " + "$" + premiumTotal);
					Assertions.passTest("Cancel Policy Page",
							"Calculated Total Premium Value :" + "$" + df.format(premiumTotalCalc));
				} else {
					Assertions.verify(premiumTotal, df.format(premiumTotalCalc), "Cancel Policy Page",
							"Actual and Calculated Policy Total values are not matching and the Value is " + "$"
									+ premiumTotal,
							false, false);
				}
			}
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Select pro rated radio button
			cancelPolicyPage.proRatedRadioBtn.scrollToElement();
			cancelPolicyPage.proRatedRadioBtn.click();
			cancelPolicyPage.waitTime(2);// need wait time to load the page
			Assertions.passTest("Cancel Policy Page", "Pro rated Radio button is Selected");

			// Get the Prorated values
			Assertions.addInfo("Scenario 03",
					"Verifying the Original,Earned and Rreturned values when Pro rated option is selected");
			for (int i = 2; i <= 4; i++) {
				if (i == 2) {
					Assertions.addInfo("Cancel Policy Page",
							"Verifying the Original Column Values when Pro rated Rated Option is selected");
				}
				if (i == 3) {
					Assertions.addInfo("Cancel Policy Page",
							"Verifying the Earned Column Values when Pro rated Option is selected");
				}
				if (i == 4) {
					Assertions.addInfo("Cancel Policy Page",
							"Verifying the Returned Column Values when Pro rated Option is selected");
				}
				String premium = cancelPolicyPage.premiumValue.formatDynamicPath(i).getData().replace("$", "");
				if (i == 3) {
					inspFee = cancelPolicyPage.inspectionFeeEarned.getData().replace("$", "").replace(",", "");
					policyFee = cancelPolicyPage.policyFeeEarned.getData().replace("$", "").replace(",", "");
				} else {
					inspFee = cancelPolicyPage.inspectionFee.formatDynamicPath(i).getData().replace("$", "");
					policyFee = cancelPolicyPage.policyFee.formatDynamicPath(i).getData().replace("$", "");
				}
				String sltf = cancelPolicyPage.taxesAndFees.formatDynamicPath(i).getData().replace("$", "");
				String surplusContribution = cancelPolicyPage.surplusContributionVlaue.formatDynamicPath(i).getData()
						.replace("$", "");
				String premiumTotal = cancelPolicyPage.policyTotal.formatDynamicPath(i).getData().replace("$", "");

				// Calculate surplus contribution value
				surplusContributionCalcValue = d_vieParticipationValue * d_vieContributionChargeValue
						* (Double.parseDouble(premium) - d_eqbPremiumValue);

				// Verify the surplus contribution value with calculated value
				if (Precision.round(Math.abs(Precision.round(Double.parseDouble(surplusContribution), 2)
						- Precision.round(surplusContributionCalcValue, 2)), 2) < 0.05) {
					Assertions.passTest("Cancel Policy Page",
							"Actual Surplus Contibution Value : " + "$" + surplusContribution);
					Assertions.passTest("Cancel Policy Page",
							"Calculated Surplus Contibution Value :" + "$" + df.format(surplusContributionCalcValue));
				} else {
					Assertions.verify(surplusContribution, df.format(surplusContributionCalcValue),
							"Cancel Policy Page", "Actual and Calculated Surplus Contribution values are not matching",
							false, false);
				}

				// calculate Policy total for short rated original column
				double premiumTotalCalc = Double.parseDouble(premium) + Double.parseDouble(inspFee)
						+ Double.parseDouble(policyFee) + Double.parseDouble(sltf)
						+ Double.parseDouble(surplusContribution);

				// Verify the Policy total value with calculated value for original column
				if (Precision.round(Math.abs(
						Precision.round(Double.parseDouble(premiumTotal), 2) - Precision.round(premiumTotalCalc, 2)),
						2) < 0.05) {
					Assertions.passTest("Cancel Policy Page", "Actual Total Premium Value : " + "$" + premiumTotal);
					Assertions.passTest("Cancel Policy Page",
							"Calculated Total Premium Value :" + "$" + df.format(premiumTotalCalc));
				} else {
					Assertions.verify(premiumTotal, df.format(premiumTotalCalc), "Cancel Policy Page",
							"Actual and Calculated Policy Total values are not matching and the Value is " + "$"
									+ premiumTotal,
							false, false);
				}
			}
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Select pro rated min earned radio button
			cancelPolicyPage.cancelOption.scrollToElement();
			cancelPolicyPage.cancelOption.click();
			cancelPolicyPage.waitTime(2);// need wait time to load the page
			Assertions.passTest("Cancel Policy Page", "Pro rated Min Earned Radio button is Selected");

			// Get the Prorated min earned values
			Assertions.addInfo("Scenario 04",
					"Verifying the Original,Earned and Rreturned values when Prorated Min Earned option is selected");
			for (int i = 2; i <= 4; i++) {
				if (i == 2) {
					Assertions.addInfo("Cancel Policy Page",
							"Verifying the Original Column Values when Pro  Rated Min Earned Option is selected");
				}
				if (i == 3) {
					Assertions.addInfo("Cancel Policy Page",
							"Verifying the Earned Column Values when Pro rated Min Earned Option is selected");
				}
				if (i == 4) {
					Assertions.addInfo("Cancel Policy Page",
							"Verifying the Returned Column Values when Pro rated Min Earned Option is selected");
				}
				String premium = cancelPolicyPage.premiumValue.formatDynamicPath(i).getData().replace("$", "");
				if (i == 3) {
					inspFee = cancelPolicyPage.inspectionFeeEarned.getData().replace("$", "").replace(",", "");
					policyFee = cancelPolicyPage.policyFeeEarned.getData().replace("$", "").replace(",", "");
				} else {
					inspFee = cancelPolicyPage.inspectionFee.formatDynamicPath(i).getData().replace("$", "");
					policyFee = cancelPolicyPage.policyFee.formatDynamicPath(i).getData().replace("$", "");
				}
				String sltf = cancelPolicyPage.taxesAndFees.formatDynamicPath(i).getData().replace("$", "");
				String surplusContribution = cancelPolicyPage.surplusContributionVlaue.formatDynamicPath(i).getData()
						.replace("$", "");
				String premiumTotal = cancelPolicyPage.policyTotal.formatDynamicPath(i).getData().replace("$", "");

				// Calculate surplus contribution value
				surplusContributionCalcValue = d_vieParticipationValue * d_vieContributionChargeValue
						* (Double.parseDouble(premium) - d_eqbPremiumValue);

				// Verify the surplus contribution value with calculated value
				if (Precision.round(Math.abs(Precision.round(Double.parseDouble(surplusContribution), 2)
						- Precision.round(surplusContributionCalcValue, 2)), 2) < 0.05) {
					Assertions.passTest("Cancel Policy Page",
							"Actual Surplus Contibution Value : " + "$" + surplusContribution);
					Assertions.passTest("Cancel Policy Page",
							"Calculated Surplus Contibution Value :" + "$" + df.format(surplusContributionCalcValue));
				} else {
					Assertions.verify(surplusContribution, df.format(surplusContributionCalcValue),
							"Cancel Policy Page", "Actual and Calculated Surplus Contribution values are not matching",
							false, false);
				}

				// calculate Policy total for short rated original column
				double premiumTotalCalc = Double.parseDouble(premium) + Double.parseDouble(inspFee)
						+ Double.parseDouble(policyFee) + Double.parseDouble(sltf)
						+ Double.parseDouble(surplusContribution);

				// Verify the Policy total value with calculated value for original column
				if (Precision.round(Math.abs(
						Precision.round(Double.parseDouble(premiumTotal), 2) - Precision.round(premiumTotalCalc, 2)),
						2) < 0.05) {
					Assertions.passTest("Cancel Policy Page", "Actual Total Premium Value : " + "$" + premiumTotal);
					Assertions.passTest("Cancel Policy Page",
							"Calculated Total Premium Value :" + "$" + df.format(premiumTotalCalc));
				} else {
					Assertions.verify(premiumTotal, df.format(premiumTotalCalc), "Cancel Policy Page",
							"Actual and Calculated Policy Total values are not matching and the Value is " + "$"
									+ premiumTotal,
							false, false);
				}
			}
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Select short rated radio button
			cancelPolicyPage.shortRatedRadioBtn.scrollToElement();
			cancelPolicyPage.shortRatedRadioBtn.click();
			cancelPolicyPage.waitTime(2);// need wait time to load the page
			Assertions.passTest("Cancel Policy Page", "Short rated Radio button is Selected");

			// click on complete
			cancelPolicyPage.completeTransactionButton.scrollToElement();
			cancelPolicyPage.completeTransactionButton.click();
			Assertions.passTest("Cancel Policy Page", "Clicked on Complete Transaction button");

			Assertions.verify(cancelPolicyPage.closeButton.checkIfElementIsDisplayed(), true,
					"Cancellation Successful Page", "Cancellation Successful Page loaded successfully", false, false);

			// Get Earned Premium,Returned premium ,Earned surplus contribution and returned
			// surplus contribution values
			String earnedPremium = cancelPolicyPage.earnedPremiumTotal.getData().replace("$", "");
			String earnedSurplusContribution = cancelPolicyPage.earnedSurplusContribution.getData().replace("$", "");
			String returnedPremium = cancelPolicyPage.returnedPremiumTotal.getData().replace("$", "").replace("-", "");
			String returnedSurplusContribution = cancelPolicyPage.returnedSurplusContribution.getData().replace("$", "")
					.replace("-", "");

			// Calculate Surplus Contribution value
			surplusContributionCalcValue = d_vieParticipationValue * d_vieContributionChargeValue
					* (Double.parseDouble(earnedPremium) - d_eqbPremiumValue);

			// Verify the surplus contribution value with calculated value
			Assertions.addInfo("Scenario 05",
					"Verifying the Earned and Returned Surplus Contribution value with calculated value on cancellation successful page");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(earnedSurplusContribution), 2)
					- Precision.round(surplusContributionCalcValue, 2)), 2) < 0.05) {
				Assertions.passTest("Cancellation Successful Page",
						"Actual Earned Surplus Contibution Value : " + "$" + earnedSurplusContribution);
				Assertions.passTest("Cancellation Successful Page", "Calculated Earned Surplus Contibution Value :"
						+ "$" + df.format(surplusContributionCalcValue));
			} else {
				Assertions.verify(earnedSurplusContribution, df.format(surplusContributionCalcValue),
						"Cancellation Successful Page",
						"Actual and Calculated Earned Surplus Contribution values are not matching", false, false);
			}

			// Calculate Surplus Contribution value for returned
			surplusContributionCalcValue = d_vieParticipationValue * d_vieContributionChargeValue
					* (Double.parseDouble(returnedPremium) - d_eqbPremiumValue);

			// Verify the surplus contribution value with calculated value
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(returnedSurplusContribution), 2)
					- Precision.round(surplusContributionCalcValue, 2)), 2) < 0.05) {
				Assertions.passTest("Cancellation Successful Page",
						"Actual Returned Surplus Contibution Value : " + "$" + returnedSurplusContribution);
				Assertions.passTest("Cancellation Successful Page", "Calculated Returned Surplus Contibution Value :"
						+ "$" + df.format(surplusContributionCalcValue));
			} else {
				Assertions.verify(returnedSurplusContribution, df.format(surplusContributionCalcValue),
						"Cancellation Successful Page",
						"Actual and Calculated Returned Surplus Contribution values are not matching", false, false);
			}
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");
			// IO-20882 ended

			// click on close button
			cancelPolicyPage.closeButton.scrollToElement();
			cancelPolicyPage.closeButton.click();
			Assertions.passTest("Cancellation Successful Page", "Clicked on close button");

			// Reinstate the policy
			policySummarypage.reinstatePolicy.scrollToElement();
			policySummarypage.reinstatePolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on reinstate policy link");

			// reinstate policy
			Assertions.verify(reinsatePolicyPage.completeReinstatement.checkIfElementIsDisplayed(), true,
					"Reinstatement Policy Page", "Reinstatement Policy Page is loaded successfully", false, false);
			reinsatePolicyPage.enterReinstatePolicyDetails(testData);

			// click on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// click on expacc link and entering expacc details
			homePage.expaccLink.scrollToElement();
			homePage.expaccLink.click();
			expaccInfoPage.enterExpaccInfo(testData, policyNumber);
			Assertions.passTest("ExpaccInfo Page", "ExpaccInfo details entered successfully");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchPolicy(policyNumber);

			// click on renew policy link
			policySummarypage.renewPolicy.scrollToElement();
			policySummarypage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy link");

			// click on continue
			if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
					&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
				policyRenewalPage.continueRenewal.scrollToElement();
				policyRenewalPage.continueRenewal.click();
			}

			// click on yes button
			if (policyRenewalPage.yesButton.checkIfElementIsPresent()
					&& policyRenewalPage.yesButton.checkIfElementIsDisplayed()) {
				policyRenewalPage.yesButton.scrollToElement();
				policyRenewalPage.yesButton.click();
			}

			// getting renewal quote number
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "The Renewal Quote Number :  " + quoteNumber);

			// click on view previous policy
			accountOverviewPage.viewPreviousPolicyButton.scrollToElement();
			accountOverviewPage.viewPreviousPolicyButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Previous Policy Button");
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);

			// Check if the NRNL date is displayed according to the Notice period on Policy
			// Summary page.
			// click on renewal indicators link
			policySummarypage.renewalIndicators.scrollToElement();
			policySummarypage.renewalIndicators.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renewal Indicators link");

			// select non renewal check box
			Assertions.verify(renewalIndicatorsPage.updateButton.checkIfElementIsDisplayed(), true,
					"Renewal Indicators Page", "Renewal Indicators Page loaded successfully", false, false);
			renewalIndicatorsPage.nonRenewal.scrollToElement();
			renewalIndicatorsPage.nonRenewal.select();
			Assertions.passTest("Renewal Indicators Page", "Selected the Non Renewal Checkbox");

			// select non renewal reason and enter legal notice wording
			renewalIndicatorsPage.nonRenewalReasonArrow.scrollToElement();
			renewalIndicatorsPage.nonRenewalReasonArrow.click();
			renewalIndicatorsPage.nonRenewalReasonOption.formatDynamicPath(testData.get("NonRenewalReason"))
					.scrollToElement();
			renewalIndicatorsPage.nonRenewalReasonOption.formatDynamicPath(testData.get("NonRenewalReason")).click();
			renewalIndicatorsPage.nonRenewalLegalNoticeWording.setData(testData.get("LegalNoticeWording"));

			// click on update
			renewalIndicatorsPage.updateButton.scrollToElement();
			renewalIndicatorsPage.updateButton.click();
			Assertions.passTest("Renewal Indicators Page", "Clicked on Update Button");
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page Loaded successfully", false, false);

			// Verifying Existing Renewal has been removed from the policy message
			Assertions.addInfo("Scenario 06", "Verifying Existing Renewal has been removed from the policy message");
			Assertions.verify(policySummarypage.nocMessage.getData().contains("Renewal has been removed"), true,
					"Policy Summary Page", "The Message displayed is " + policySummarypage.nocMessage.getData(), false,
					false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Verifying the absence of renew policy link when a policy put under
			// non-renewal.
			Assertions.addInfo("Scenario 07",
					"Verifying the absence of renew policy link when a policy put under non-renewal.");
			Assertions.verify(policySummarypage.renewPolicy.checkIfElementIsPresent(), false, "Policy Summary Page",
					"The Renew Policy link not present is verified when a policy put under non-renewal.", false, false);
			Assertions.passTest("Scenario 07", "Scenario 07 Ended");

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC016 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC016 ", "Executed Successfully");
			}
		}
	}
}
