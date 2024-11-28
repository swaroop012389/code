/*Description:Check if when a PFC is added during quoting, another will not be able to be added at the time of bind or at endorsement unless the existing PFC is removed first and IO-21583 and IO-21085 and IO-21254
Author: Pavan Mule
Date :  23/07/2021*/

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
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.ConfirmBindRequestPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EditAdditionalInterestInformationPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorseAdditionalInterestsPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HealthDashBoardPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC022 extends AbstractCommercialTest {

	public TC022() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID022.xls";
	}

	// declaring the variable globally
	double d_eqbPremiumValue;

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
		EditAdditionalInterestInformationPage editAdditionalInterestInformationPage = new EditAdditionalInterestInformationPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		EndorseAdditionalInterestsPage endorseAdditionalInterestsPage = new EndorseAdditionalInterestsPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		ConfirmBindRequestPage confirmBindRequestPage = new ConfirmBindRequestPage();
		BuildingUnderMinimumCostPage buildingUnderminimumCost = new BuildingUnderMinimumCostPage();
		HealthDashBoardPage healthDashBoardPage = new HealthDashBoardPage();
		LoginPage loginPage = new LoginPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();

		FWProperties property1 = new FWProperties("HealthDashBoardPage");

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
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
		String totalValueTxn;
		String totalValueAnnual;
		String totalValueTerm;
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
			if (priorLossesPage.continueButton.checkIfElementIsPresent()
					&& priorLossesPage.continueButton.checkIfElementIsDisplayed()) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
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

			// Calculating Total Premium Value by adding Premium+ICATfees+OtherFees+SLTF
			premiumAmount = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");
			icatFees = accountOverviewPage.feesValue.getData().replaceAll("[^\\d-.]", "");
			actualSLTFValue = accountOverviewPage.sltfValue.getData().replaceAll("[^\\d-.]", "");

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

			// verify the surplus contribution value with calculated value
			Assertions.addInfo("Scenario 01",
					"Verifying the Surplus Contribution Value and Total Premium Value with the calculated value for NB quote");
			Assertions.verify(accountOverviewPage.surplusContributionValue.getData().replace("$", "").replace(",", ""),
					df.format(surplusContributionCalcValue), "Account Overview Page",
					"Actual and Calculated Surplus Contribution values are matching for state "
							+ testData.get("QuoteState") + " And the value is : "
							+ accountOverviewPage.surplusContributionValue.getData(),
					false, false);

			// Calculating Total Premium Value by adding Premium+ICATfees+OtherFees+SLTF
			calculatedTotalPremiumAmount = Double.parseDouble(premiumAmount) + Double.parseDouble(icatFees)
					+ Double.parseDouble(actualSLTFValue) + surplusContributionCalcValue;

			// Verify the total premium with calculated value for NB quote
			Assertions.verify(accountOverviewPage.totalPremiumValue.getData().replace("$", "").replace(",", ""),
					df.format(calculatedTotalPremiumAmount), "Account Overview Page",
					"Actual and Calculated Total Premium values are matching for state " + testData.get("QuoteState")
							+ " And the value is : " + accountOverviewPage.totalPremiumValue.getData(),
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// click on Additional Interests
			accountOverviewPage.editAdditionalIntersets.scrollToElement();
			accountOverviewPage.editAdditionalIntersets.click();
			Assertions.verify(editAdditionalInterestInformationPage.update.checkIfElementIsPresent(), true,
					"Edit Additional Interests Page", "Edit Additional Interests page loaded successfully", false,
					false);

			// adding additional interests with premium finance company(PFC)
			editAdditionalInterestInformationPage.addAdditionalInterest(testData);
			editAdditionalInterestInformationPage.update.waitTillPresenceOfElement(60);
			editAdditionalInterestInformationPage.update.waitTillVisibilityOfElement(60);
			editAdditionalInterestInformationPage.update.click();
			Assertions.passTest("Edit Additional Interests Page",
					"The AI Type Selected is: " + testData.get("1-AIType"));

			// Click on request bind button
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);

			// entering details in request bind page
			requestBindPage.enterPolicyDetails(testData);
			requestBindPage.enterPaymentInformation(testData);
			requestBindPage.addInspectionContact(testData);

			// click Add symbol
			requestBindPage.aIAddSymbol.scrollToElement();
			requestBindPage.aIAddSymbol.click();
			requestBindPage.aITypeArrow.scrollToElement();
			requestBindPage.aITypeArrow.click();
			Assertions.passTest("Request Bind Page", "Clicked on Add symbol successfully");

			// Checking for PFC is Not available on request bind page after adding
			// additional interests on Account Overview Page
			Assertions.addInfo("Scenario 02",
					"Checking for PFC is Not available on request bind page after adding additional interests on Account Overview Page");
			Assertions.verify(
					requestBindPage.aITypeOption.formatDynamicPath(0, "Premium Finance Company")
							.checkIfElementIsPresent()
							&& requestBindPage.aITypeOption.formatDynamicPath(0, "Premium Finance Company")
									.checkIfElementIsDisplayed(),
					false, "Requset Bind Page",
					"Additional Interest Type " + testData.get("1-AIType") + " Not Available is verified", false,
					false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// entering bind details
			requestBindPage.addContactInformation(testData);
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();

			// Click on request bind button
			requestBindPage.requestBind.waitTillVisibilityOfElement(60);
			requestBindPage.requestBind.scrollToElement();
			requestBindPage.requestBind.click();
			requestBindPage.requestBind.waitTillInVisibilityOfElement(60);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			if (requestBindPage.overrideEffectiveDate.checkIfElementIsPresent()
					&& requestBindPage.overrideEffectiveDate.checkIfElementIsDisplayed()) {
				requestBindPage.overrideEffectiveDate.scrollToElement();
				requestBindPage.overrideEffectiveDate.select();
				requestBindPage.submit.scrollToElement();
				requestBindPage.submit.click();
				requestBindPage.requestBind.waitTillVisibilityOfElement(60);
				requestBindPage.requestBind.scrollToElement();
				requestBindPage.requestBind.click();
				requestBindPage.requestBind.waitTillInVisibilityOfElement(60);
			}

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote is searched successfully");

			// Click on Open Referral Link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

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
			}
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Get policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Policy Number is " + policyNumber, false, false);

			// Verify the Total Values of Txn,Annual and Term colums with calculated value
			Assertions.addInfo("Scenario 03",
					"Verify the Total Premium Values of Transaction,Annual and Term colums with calculated value for NB Policy");
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

			// Verify the premium total transaction of annual column
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

			// Verify the premium total of Term column
			totalValueTerm = policySummaryPage.premiumTotal.formatDynamicPath("4").getData().replace(",", "")
					.replace("$", "");

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(totalValueTerm), 2)
					- Precision.round(calculatedTotalPremiumAmount, 2)), 2) < 0.05) {
				Assertions.passTest("Policy Summary Page", "Actual Total Premium for Transaction column is :"
						+ policySummaryPage.premiumTotal.formatDynamicPath("4").getData());
				Assertions.passTest("Policy Summary Page",
						"Calculated Total Premium Value is :" + "$" + df.format(calculatedTotalPremiumAmount));
			} else {
				Assertions.passTest("Policy Summary Page",
						"The Difference between actual and calculated Total Premium is more than 0.05");
			}
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on endorse policy link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Entering Endorsement Effective Date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page",
					"Entered Transaction Effective Date " + testData.get("TransactionEffectiveDate"));

			// clicking on Change additional interests information on endorse policy page
			endorsePolicyPage.changeAIInformationLink.scrollToElement();
			endorsePolicyPage.changeAIInformationLink.click();
			Assertions.passTest("Endorse Policy Page",
					"Clicked on Change Additional Interests Information link successfully");

			// Click on add symbol
			endorseAdditionalInterestsPage.aIAddSymbol.scrollToElement();
			endorseAdditionalInterestsPage.aIAddSymbol.click();
			Assertions.passTest("Endorse Additional Interests Page", "Clicked on add symbol successfully");
			endorseAdditionalInterestsPage.aITypeArrow.formatDynamicPath(2).scrollToElement();
			endorseAdditionalInterestsPage.aITypeArrow.formatDynamicPath(2).click();

			// Checking PFC is Not available on on Endorse Additional Interests page after
			// adding additional interest on Account Overview page
			Assertions.addInfo("Scenario 04",
					"Checking PFC is Not available on on Endorse Additional Interests page after adding additional interest on Account Overview page");
			Assertions.verify(
					endorseAdditionalInterestsPage.aITypeOption.formatDynamicPath(0, "Premium Finance Company")
							.checkIfElementIsPresent()
							&& endorseAdditionalInterestsPage.aITypeOption
									.formatDynamicPath(0, "Premium Finance Company").checkIfElementIsDisplayed(),
					false, "Endorse Additional Interests Page",
					"Additional Interest Type " + testData.get("1-AIType") + " Not Available is verified", false,
					false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Click on cancel button
			endorseAdditionalInterestsPage.cancelButton.scrollToElement();
			endorseAdditionalInterestsPage.cancelButton.click();
			Assertions.passTest("Endorse Additional Interests Page", "Clicked on cancel button");

			// Deleting the PFC
			endorsePolicyPage.changeAIInformationLink.scrollToElement();
			endorsePolicyPage.changeAIInformationLink.click();
			endorseAdditionalInterestsPage.deleteAdditionalInterestPB(testData);
			endorseAdditionalInterestsPage.okButton.scrollToElement();
			endorseAdditionalInterestsPage.okButton.click();

			// checking PFC is available on additional interests page
			endorsePolicyPage.changeAIInformationLink.scrollToElement();
			endorsePolicyPage.changeAIInformationLink.click();
			endorseAdditionalInterestsPage.aITypeArrow.formatDynamicPath(1).scrollToElement();
			endorseAdditionalInterestsPage.aITypeArrow.formatDynamicPath(1).click();

			// Checking PFC available after deleting the additional interests on Endorse
			// Additional Interests Page
			Assertions.addInfo("Scenario 05",
					"Checking PFC available after deleting the additional interests on Endorse Additional Interests Page");
			Assertions.verify(
					endorseAdditionalInterestsPage.aITypeOption.formatDynamicPath(0, "Premium Finance Company")
							.checkIfElementIsPresent()
							&& endorseAdditionalInterestsPage.aITypeOption
									.formatDynamicPath(0, "Premium Finance Company").checkIfElementIsDisplayed(),
					true, "Endorse Additional Interests Page",
					"After Deleting  Additinal Interests, Additional interests Type "
							+ endorseAdditionalInterestsPage.aITypeOption
									.formatDynamicPath(0, "Premium Finance Company").getData()
							+ "  Available is verified",
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");
			testData = data.get(data_Value2);

			// Click on ok button
			endorseAdditionalInterestsPage.okButton.scrollToElement();
			endorseAdditionalInterestsPage.okButton.click();

			// Adding PFC to additional interests
			endorsePolicyPage.changeAIInformationLink.scrollToElement();
			endorsePolicyPage.changeAIInformationLink.click();
			endorseAdditionalInterestsPage.enterEndorsementAdditionalInterestDetails(testData);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// click on expacc link
			homePage.expaccLink.scrollToElement();
			homePage.expaccLink.click();
			testData = data.get(data_Value1);
			expaccInfoPage.enterExpaccInfo(testData, policyNumber);
			Assertions.passTest("ExpaccInfo Page", "ExpaccInfo details entered successfully");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchPolicy(policyNumber);

			// click on renew policy link
			Assertions.addInfo("Policy Summary Page", "Renew NB Policy");
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy link");

			if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
					&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
				policyRenewalPage.continueRenewal.scrollToElement();
				policyRenewalPage.continueRenewal.click();
			}

			// click on yes
			if (policyRenewalPage.yesButton.checkIfElementIsPresent()
					&& policyRenewalPage.yesButton.checkIfElementIsDisplayed()) {
				policyRenewalPage.yesButton.scrollToElement();
				policyRenewalPage.yesButton.click();
			}

			if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
					&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
				policyRenewalPage.continueRenewal.scrollToElement();
				policyRenewalPage.continueRenewal.click();
			}

			// getting renewal quote number
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "The Renewal Quote Number :  " + quoteNumber);

			// Calculating sltf value by adding (Premium+ICATfees+OtherFees)*SLTF percent
			premiumAmount = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");
			icatFees = accountOverviewPage.feesValue.getData().replaceAll("[^\\d-.]", "");
			actualSLTFValue = accountOverviewPage.sltfValue.getData().replaceAll("[^\\d-.]", "");
			String actualSurpluscontribution = accountOverviewPage.surplusContributionValue.getData().replace("$", "")
					.replace(",", "");
			String stampingFeePercent = testData.get("StampingFeeValue");
			String sltfPercent = testData.get("SLTFValue");

			// Calculating sltf value
			double a_calStaming = (Double.parseDouble(premiumAmount) + Double.parseDouble(icatFees)
					+ Double.parseDouble(actualSurpluscontribution)) * Double.parseDouble(stampingFeePercent);

			double a_calSLTF = ((Double.parseDouble(premiumAmount) + Double.parseDouble(icatFees)
					+ Double.parseDouble(actualSurpluscontribution)) * Double.parseDouble(sltfPercent)) + a_calStaming;

			// Verifying actual and calculated STFL
			Assertions.addInfo("Scenario 06",
					"Verifying the actual and calculated SLTF on account overview page 4.85%");
			if (Precision.round(
					Math.abs(Precision.round(Double.parseDouble(actualSLTFValue), 2) - Precision.round(a_calSLTF, 2)),
					2) < 0.05) {
				Assertions.passTest("Account overview page ", "Actual SLTF value :" + "$" + actualSLTFValue);
				Assertions.passTest("Account overview page", "Calculated SLTF Vaule :" + "$" + a_calSLTF);
			} else {
				Assertions.verify(actualSLTFValue, a_calSLTF, "Account overview page",
						"The Difference between actual  and calculated SLTF value is more than 0.05", false, false);

			}
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

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

			// Add IO-21254
			// Verifying the presence of reciprocal for renewal quote with peril = wind and
			// building value = 2.5M to 5M
			Assertions.addInfo("Scenario 07", "Verifying the presence of reciprocal for renewal quote");
			Assertions.verify(accountOverviewPage.surplusContributionValue.checkIfElementIsDisplayed(), true,
					"Account overview page", "For renewal quote reciprocal participation is presence verified", false,
					false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");
			// IO-21254

			// click on quote specifics
			accountOverviewPage.quoteSpecifics.scrollToElement();
			accountOverviewPage.quoteSpecifics.click();
			Assertions.passTest("Account overview page", "Clicked on quote specific link successfully");

			// get VIE participation value
			accountOverviewPage.vieParicipation.waitTillVisibilityOfElement(60);
			vieParticipationValuelength = accountOverviewPage.vieParicipation.getData().length();
			vieParticipationValue = accountOverviewPage.vieParicipation.getData().substring(0,
					vieParticipationValuelength - 1);
			d_vieParticipationValue = Double.parseDouble(vieParticipationValue) / 100;

			// get vie participation charge vieContributionChargelength =
			accountOverviewPage.vieContributionCharge.getData().length();
			vieContributionChargeValue = accountOverviewPage.vieContributionCharge.getData().substring(0,
					vieContributionChargelength - 1);
			d_vieContributionChargeValue = Double.parseDouble(vieContributionChargeValue) / 100;

			// calculate surplus contribution value // 0.15* 0.1 * (transaction premium

			surplusContributionCalcValue = d_vieParticipationValue * d_vieContributionChargeValue
					* (Double.parseDouble(premiumAmount) - d_eqbPremiumValue);

			// verify the surplus contribution value with calculated value
			Assertions.addInfo("Scenario 08",
					"Verify the surplus contribution value and tota premium with calculated value for Renewal quote");
			Assertions.verify(accountOverviewPage.surplusContributionValue.getData().replace("$", "").replace(",", ""),
					df.format(surplusContributionCalcValue), "Account Overview Page",
					"Actual and Calculated Surplus Contribution values are matching for state "
							+ testData.get("QuoteState") + " And the value is : "
							+ accountOverviewPage.surplusContributionValue.getData(),
					false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// Calculating Total Premium Value by adding Premium+ICATfees+OtherFees+SLTF
			calculatedTotalPremiumAmount = Double.parseDouble(premiumAmount) + Double.parseDouble(icatFees)
					+ Double.parseDouble(actualSLTFValue) + Double.parseDouble(actualSurpluscontribution);

			// Verify the total premium with calculated value
			Assertions.addInfo("Scenario 09", "Verify the tota premium with calculated value for Renewal quote");
			Assertions.verify(accountOverviewPage.totalPremiumValue.getData().replace("$", "").replace(",", ""),
					df.format(calculatedTotalPremiumAmount), "Account Overview Page",
					"Actual and Calculated Total Premium values are matching for state " + testData.get("QuoteState")
							+ " And the value is : " + accountOverviewPage.totalPremiumValue.getData(),
					false, false);
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			// Adding IO-21583
			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.verify(accountOverviewPage.goBackBtn.checkIfElementIsDisplayed(), true,
					"View print full quote page", "View print full quote page loaded successfully", false, false);

			// Getting values from view print full quote page
			String actualPremiumValue = viewOrPrintFullQuotePage.premiumValue.getData().replace("$", "").replace(",",
					"");
			String actualPolicyFee = viewOrPrintFullQuotePage.policyFee.getData().replace("$", "").replace(",", "");
			String actualSurplusContributionValue = viewOrPrintFullQuotePage.surplusContributionValue.getData()
					.replace("$", "").replace(",", "");
			String actualSLTF = viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData().replace("$", "").replace(",",
					"");
			String actualStampingFee = viewOrPrintFullQuotePage.stampingFeeValue.getData().replace("$", "").replace(",",
					"");

			// Calculating stamping fee = (premium+ policy fee+surplus
			// contribution)*stamping percent(0.04=.0004)
			double calStampingFee = (Double.parseDouble(actualPremiumValue) + Double.parseDouble(actualPolicyFee)
					+ Double.parseDouble(actualSurplusContributionValue)) * Double.parseDouble(stampingFeePercent);

			// Calculating SLTF = (premium+ policy fee+surplus contribution+stamping
			// fee)*SLTF Percent(4.85%)
			double calSLTF = (Double.parseDouble(actualPremiumValue) + Double.parseDouble(actualPolicyFee)
					+ Double.parseDouble(actualSurplusContributionValue)) * Double.parseDouble(sltfPercent);

			// Verifying actual and calculated stamping fee
			Assertions.addInfo("Scenario 10",
					"Verifying the actual and calculated staming fee on view print full qoute page 0.04%");
			if (Precision.round(Math.abs(
					Precision.round(Double.parseDouble(actualStampingFee), 2) - Precision.round(calStampingFee, 2)),
					2) < 0.05) {
				Assertions.passTest("View print full quote page", "Actual Stamping fee :" + "$" + actualStampingFee);
				Assertions.passTest("View print full quote page",
						"Calculated Stamping fee :" + "$" + df.format(calStampingFee));
			} else {
				Assertions.verify(actualStampingFee, calStampingFee, "View print full quote page",
						"The Difference between actual  and calculated stamping fee is more than 0.05", false, false);

			}
			Assertions.addInfo("Scenario 10", "Scenario 10 Ended");

			// Verifying actual and calculated STFL
			Assertions.addInfo("Scenario 11",
					"Verifying the actual and calculated SLTF on view print full qoute page 4.85%");
			if (Precision.round(
					Math.abs(Precision.round(Double.parseDouble(actualSLTF), 2) - Precision.round(calSLTF, 2)),
					2) < 0.05) {
				Assertions.passTest("View print full quote page", "Actual SLTF value :" + "$" + actualSLTF);
				Assertions.passTest("View print full quote page", "Calculated SLTF Vaule :" + "$" + df.format(calSLTF));
			} else {
				Assertions.verify(actualSLTF, calSLTF, "View print full quote page",
						"The Difference between actual  and calculated SLTF value is more than 0.05", false, false);

			}
			Assertions.addInfo("Scenario 11", "Scenario 11 Ended");

			// Click on back button
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.passTest("View print full quote page", "Clicked on back button successfully");
			// IO-21583 Ended

			// Adding IO-21085
			// click on release renewal to producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account overview page", "Clicked on release renewal to producer button successfully");

			// Click on request bind button
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind Button");
			// IO-21085 Ended

			// Enter Bind Details with out uploading file
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);

			// Verify the quote premium is equal to calculated value
			Assertions.addInfo("Scenario 12",
					"Verify the quote premium is equal to calculated value for Renewal quote on Request bind page");
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
			Assertions.addInfo("Scenario 12", "Scenario 12 Ended");

			// Enter bind details
			requestBindPage.addContactInformation(testData);
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();

			// Verify the quote premium value on Confirm Request bind page
			confirmBindRequestPage.waitTime(2);// need waittime to load the element
			int lengthofQuotePremiumValue = confirmBindRequestPage.quotePremium.getData().length();
			String quotePremiumConfimBindPage = confirmBindRequestPage.quotePremium.getData()
					.substring(15, lengthofQuotePremiumValue).replace(",", "").replace("$", "");

			Assertions.addInfo("Scenario 13",
					"Verify the quote premium is equal to calculated value for Renewal quote on Confirm Request bind page");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(quotePremiumConfimBindPage), 2)
					- Precision.round(calculatedTotalPremiumAmount, 2)), 2) < 0.05) {
				Assertions.passTest("Confirm Request Bind Page",
						"Actual TotalPremium Amount:" + confirmBindRequestPage.quotePremium.getData());
				Assertions.passTest("Confirm Request Bind Page",
						"Calculated Total Premium :" + "$" + df.format(calculatedTotalPremiumAmount));
			} else {
				Assertions.passTest("Confirm Request Bind Page",
						"The Difference between actual TotalPremium and calculated TotalPremium is more than 0.05");
			}
			Assertions.addInfo("Scenario 13", "Scenario 13 Ended");

			// click on confirm bind
			confirmBindRequestPage.confirmBind();

			if (requestBindPage.overrideEffectiveDate.checkIfElementIsPresent()
					&& requestBindPage.overrideEffectiveDate.checkIfElementIsDisplayed()) {
				requestBindPage.overrideEffectiveDate.scrollToElement();
				requestBindPage.overrideEffectiveDate.select();
				requestBindPage.submit.scrollToElement();
				requestBindPage.submit.click();
				requestBindPage.requestBind.waitTillVisibilityOfElement(60);
				requestBindPage.requestBind.scrollToElement();
				requestBindPage.requestBind.click();
				requestBindPage.requestBind.waitTillInVisibilityOfElement(60);
			}

			// clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home Page", "Clicked on Home button");

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
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

			// Get policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Renewal Policy Number is " + policyNumber, false, false);

			// Verify the surplus contribution values of transaction,annual, and term
			// columns
			Assertions.addInfo("Scenario 14",
					"Verify the surplus contribution values of transaction,annual, and term columns for Renewal Policy");
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

			// Verify the surplus contribution of annual column
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

			// Verify the surplus contribution of Term column
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
			Assertions.addInfo("Scenario 14", "Scenario 14 Ended");

			// Verify the Total Values of Txn,Annual and Term colums with calculated value
			Assertions.addInfo("Scenario 15",
					"Verify the Premium Total Values of Transaction,Annual and Term colums with calculated value for Renewal quote");
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

			// Verify the Total Premium of annual column
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
				Assertions.passTest("Policy Summary Page", "Actual Total Premium for Transaction column is :"
						+ policySummaryPage.premiumTotal.formatDynamicPath("4").getData());
				Assertions.passTest("Policy Summary Page",
						"Calculated Total Premium Value is :" + "$" + df.format(calculatedTotalPremiumAmount));
			} else {
				Assertions.passTest("Policy Summary Page",
						"The Difference between actual and calculated Total Premium is more than 0.05");
			}
			Assertions.addInfo("Scenario 15", "Scenario 15 Ended");

			// Click on Rewrite Policy
			policySummaryPage.rewritePolicy.waitTillVisibilityOfElement(60);
			policySummaryPage.rewritePolicy.scrollToElement();
			policySummaryPage.rewritePolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Rewrite Policy link");

			// Click on Create another quote
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// click on edit building link
			testData = data.get(data_Value2);
			accountOverviewPage.buildingLink.formatDynamicPath("1", "1").scrollToElement();
			accountOverviewPage.buildingLink.formatDynamicPath("1", "1").click();
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();

			// modifying Const type,square foot value,occupancy and building value
			Assertions.passTest("Building Page", "Building Page loaded successfully");
			buildingPage.constructionTypeArrow.waitTillVisibilityOfElement(60);
			buildingPage.constructionTypeArrow.scrollToElement();
			buildingPage.constructionTypeArrow.click();
			buildingPage.constructionTypeOption.formatDynamicPath(testData.get("L1B1-BldgConstType")).scrollToElement();
			buildingPage.constructionTypeOption.formatDynamicPath(testData.get("L1B1-BldgConstType")).click();

			// Click on continue update button
			if (buildingPage.continueWithUpdateBtn.checkIfElementIsPresent()
					&& buildingPage.continueWithUpdateBtn.checkIfElementIsDisplayed()) {
				buildingPage.continueWithUpdateBtn.scrollToElement();
				buildingPage.continueWithUpdateBtn.click();
			}
			buildingPage.totalSquareFootage.setData(testData.get("L1B1-BldgSqFeet"));

			// click on building values link
			buildingPage.waitTime(2);// need wait time to load the element
			buildingPage.buildingValuesLink.scrollToElement();
			buildingPage.buildingValuesLink.click();
			buildingPage.buildingValue.waitTillVisibilityOfElement(60);
			buildingPage.buildingValue.setData(testData.get("L1B1-BldgValue"));
			buildingPage.businessPersonalProperty.clearData();
			Assertions.passTest("Building Page", "Construction type: " + testData.get("L1B1-BldgConstType"));
			Assertions.passTest("Building Page", "Occupancy type: " + testData.get("L1B1-PrimaryOccupancy"));
			Assertions.passTest("Building Page", "Building Square Feet: " + testData.get("L1B1-BldgSqFeet"));
			Assertions.passTest("Building Page", "Building Details modified successfully");

			// click on continue
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();

			// Verifying Bring UpToCost button
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
			Assertions.addInfo("Scenario 16",
					"Verifying the Costcard message and Verifying the actual and expected cost card values Construction type: Joisted mansonary, Occupancy type: Apartment,Building Square Feet: 3000");
			Assertions.verify(
					buildingUnderminimumCost.costcardMessage
							.formatDynamicPath("Building value").checkIfElementIsDisplayed(),
					true, "Building Under Minimum Cost Page",
					"The Costcard message "
							+ buildingUnderminimumCost.costcardMessage.formatDynamicPath("Building value").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 16", "Scenario 16 Ended");

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC022 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC022 ", "Executed Successfully");
			}
		}
	}
}