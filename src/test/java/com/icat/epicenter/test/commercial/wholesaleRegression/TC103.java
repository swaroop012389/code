/** Program Description:  Validate  SLTF and Broker Fee is calculated correctly in NB Quote for producer and  Verify Taxes and Fees are not displayed on policy summary page
 *  Author			   : Sowndarya
 *  Date of Creation   : 09/28/2019
**/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PreferenceOptionsPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC103 extends AbstractCommercialTest {

	public TC103() {
		super(LoginType.PRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID103.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		PreferenceOptionsPage preferenceOptionsPage = new PreferenceOptionsPage();
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		ViewPolicySnapShot viewPolicySnapShotPage = new ViewPolicySnapShot();
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);

		// Initializing the variables
		String quoteNumber;
		String policyNumber;
		String premiumAmount;
		double expectedsltf;
		double expectedBrokerFee;
		BigDecimal roundOffExpectedSltf;
		BigDecimal roundOffBrokerFee;
		String inspectionAndPolicyFee;
		String surplusContributionValue;
		int length;
		int data_Value1 = 0;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();
			// click on user preference link and enter the details
			Assertions.addInfo("Home Page", "Entering User Preference Details");
			homePage.userPreferences.scrollToElement();
			homePage.userPreferences.click();
			Assertions.passTest("Home Page", "Clicked on User Preferences");

			Assertions.passTest("Preference Options Page", "Preference Options Page loaded successfully");
			preferenceOptionsPage.addBrokerFees(testData);

			if (testData.get("BrokerFeePercentageOrDollar").equals("%")) {
				Assertions.passTest("Preference Options Page", "Broker Fee value : " + testData.get("BrokerFeeValue")
						+ testData.get("BrokerFeePercentageOrDollar"));
			} else {
				Assertions.passTest("Preference Options Page", "Broker Fee value : "
						+ testData.get("BrokerFeePercentageOrDollar") + testData.get("BrokerFeeValue"));
			}
			Assertions.passTest("Preference Options Page", "Details entered successfully");
			homePage.goToHomepage.click();

			// creating New account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
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

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Refer Quote for binding
			if (referQuotePage.referQuote.checkIfElementIsPresent()
					&& referQuotePage.referQuote.checkIfElementIsDisplayed()) {
				referQuotePage.contactName.setData(testData.get("ProducerName"));
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.click();
				quoteNumber = referQuotePage.quoteNum.getData();

				// Signing out as producer
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();

				// Login as USM
				loginPage.refreshPage();
				loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
				Assertions.passTest("Login", "Logged in to application successfully");

				// Go to HomePage
				homePage.goToHomepage.click();
				homePage.searchQuote(quoteNumber);
				Assertions.passTest("Home Page", "Quote for referral is searched successfully");

				// Click on Open referral link
				accountOverviewPage.openReferralLink.scrollToElement();
				accountOverviewPage.openReferralLink.click();

				// Approve Referral
				if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
					referralPage.pickUp.scrollToElement();
					referralPage.pickUp.click();
				}
				referralPage.approveOrDeclineRequest.waitTillButtonIsClickable(60);
				referralPage.approveOrDeclineRequest.scrollToElement();
				referralPage.approveOrDeclineRequest.click();
				referralPage.scrollToBottomPage();
				referralPage.internalComments.setData(testData.get("PremiumAdjustment_InternalComments"));
				referralPage.externalComments.setData(testData.get("PremiumAdjustment_InternalComments"));
				referralPage.approveRequest.waitTillButtonIsClickable(60);
				referralPage.approveRequest.scrollToElement();
				referralPage.approveRequest.click();
				Assertions.passTest("Referral Page", "Bind Referral Approved successfully");

				// Logout as USM
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();

				// Login as Producer
				loginPage.refreshPage();
				loginPage.waitTime(3);// wait time is needed to load the page
				loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));

				// Searching the account
				homePage.goToHomepage.click();
				homePage.searchQuoteByProducer(quoteNumber);
			}

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Getting the premium,fees and sltf values from premium section in Account
			// Overview page
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Surplus Lines Taxes and Fees label displayed is verified", false, false);
			Assertions.verify(accountOverviewPage.premiumValue.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Premium Value is : " + accountOverviewPage.premiumValue.getData(), false,
					false);
			Assertions.verify(accountOverviewPage.feesValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Fees Value is : " + accountOverviewPage.feesValue.getData(), false, false);

			// Verifying surplus contribution value
			Assertions.verify(accountOverviewPage.surplusContributionValue.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "The Surplus Contribution Value "
							+ accountOverviewPage.surplusContributionValue.getData() + " displayed is verified",
					false, false);

			// fetching premium amount
			premiumAmount = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "").replace(".00", "");

			// fetching fees value
			inspectionAndPolicyFee = accountOverviewPage.feesValue.getData().replaceAll("[^\\d-.]", "").replace(".00",
					"");

			// fetching surplus contribution value
			surplusContributionValue = accountOverviewPage.surplusContributionValue.getData().replace("$", "")
					.replace(",", "");

			// calculating expected broker fee
			if (testData.get("BrokerFeePercentageOrDollar").equals("%")) {
				expectedBrokerFee = (Double.parseDouble(testData.get("BrokerFeeValue")) / Double.parseDouble(100 + ""))
						* (Double.parseDouble(premiumAmount));
			} else if (testData.get("BrokerFeePercentageOrDollar").equals("$")) {
				expectedBrokerFee = (Integer.parseInt(testData.get("BrokerFeeValue")))
						* (Double.parseDouble(premiumAmount));
			} else {
				expectedBrokerFee = 0.0;
			}

			// rounding off broker fee
			roundOffBrokerFee = new BigDecimal(expectedBrokerFee);
			roundOffBrokerFee = roundOffBrokerFee.setScale(2, RoundingMode.HALF_UP);

			// calculating sltf percentage by adding Premium+Fees+Broker fees and
			// multiplying by sltf percentage 0.06
			expectedsltf = Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"))
					* (Integer.parseInt(premiumAmount) + Double.parseDouble(inspectionAndPolicyFee)
							+ roundOffBrokerFee.doubleValue() + Double.parseDouble(surplusContributionValue));

			// Rounding sltf decimal value to 2 digits
			roundOffExpectedSltf = new BigDecimal(expectedsltf);
			roundOffExpectedSltf = roundOffExpectedSltf.setScale(2, RoundingMode.HALF_UP);

			// Comparing actual and expected SLTF value and printing calculated value
			Assertions.verify(accountOverviewPage.sltfValue.getData(), format.format(roundOffExpectedSltf),
					"Account Overview Page", "Calculated Surplus Lines Taxes and Fees : " + format.format(expectedsltf),
					false, false);

			// Comparing actual and expected SLTF value and printing actual value
			Assertions.verify(accountOverviewPage.sltfValue.getData(), format.format(roundOffExpectedSltf),
					"Account Overview Page",
					"Actual Surplus Lines Taxes and Fees : " + accountOverviewPage.sltfValue.getData(), false, false);

			// Comparing actual and expected Broker Fee value and printing calculated value
			Assertions.verify(accountOverviewPage.otherFees.getData(), format.format(roundOffBrokerFee),
					"Account Overview Page", "Calculated Broker Fees : " + format.format(roundOffBrokerFee), false,
					false);

			// Comparing actual and expected Broker Fee value and printing actual value
			Assertions.verify(accountOverviewPage.otherFees.getData(), format.format(roundOffBrokerFee),
					"Account Overview Page", "Actual Broker Fees : " + accountOverviewPage.otherFees.getData(), false,
					false);

			// Comparing actual and expected total premium and fees value and printing
			// calculated value
			Assertions.verify(accountOverviewPage.totalPremiumValue.getData(),
					format.format(Double.parseDouble(premiumAmount) + roundOffExpectedSltf.doubleValue()
							+ (Double.parseDouble(inspectionAndPolicyFee)) + roundOffBrokerFee
									.doubleValue()
							+ Double.parseDouble(surplusContributionValue)),
					"Account Overview Page",
					"Calculated Premium, Taxes and Fees : " + format.format(Double.parseDouble(premiumAmount)
							+ roundOffExpectedSltf.doubleValue() + (Double.parseDouble(inspectionAndPolicyFee))
							+ roundOffBrokerFee.doubleValue() + Double.parseDouble(surplusContributionValue)),
					false, false);

			// Comparing actual and expected total premium and fees value and printing
			// actual value
			Assertions.verify(accountOverviewPage.totalPremiumValue.getData(),
					format.format(Double.parseDouble(premiumAmount) + roundOffExpectedSltf.doubleValue()
							+ (Double.parseDouble(inspectionAndPolicyFee)) + roundOffBrokerFee.doubleValue()
							+ Double.parseDouble(surplusContributionValue)),
					"Account Overview Page", "Actual Premium, Taxes and Fees in Premium Section: "
							+ accountOverviewPage.totalPremiumValue.getData(),
					false, false);

			// verifyiing premium taxes and fees in quote tree section and premium section
			Assertions.addInfo("Account Overview Page",
					"Verifying premium taxes and fees in quote tree section and premium section");
			length = accountOverviewPage.totalPremiumValue.getData().length();
			Assertions.verify(accountOverviewPage.quotePremium.formatDynamicPath(1).getData().substring(0, length),
					format.format(Double.parseDouble(premiumAmount) + roundOffExpectedSltf.doubleValue()
							+ (Double.parseDouble(inspectionAndPolicyFee)) + roundOffBrokerFee.doubleValue()
							+ Double.parseDouble(surplusContributionValue)),
					"Account Overview Page", "Actual Premium, Taxes and Fees in Quote tree Section: "
							+ accountOverviewPage.totalPremiumValue.getData(),
					false, false);

			Assertions.verify(accountOverviewPage.quotePremium.formatDynamicPath(1).getData().substring(0, length),
					accountOverviewPage.totalPremiumValue.getData(), "Account Overview Page",
					"Premium, Taxes and Fees in Quote tree and Premium Section are equal is verified ", false, false);

			// click on veiw/Print full Quote
			accountOverviewPage.viewPrintFullQuoteLink.click();

			// verifying surplus fees and broker fees in view/print full quote page
			Assertions.passTest("View/Print Full Quote Page",
					"Surplus Lines Taxes and Fees : " + viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData());
			Assertions.verify(viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData(),
					format.format(roundOffExpectedSltf), "View/Print Full Quote Page",
					"Calculated Surplus Lines Taxes and Fees and Surplus Lines Taxes and Fees in View/Print Full Quote Page are equal",
					false, false);

			Assertions.passTest("View/Print Full Quote Page",
					"Broker Fees : " + viewOrPrintFullQuotePage.brokerFeeValue.getData());
			Assertions.verify(viewOrPrintFullQuotePage.brokerFeeValue.getData(), format.format(roundOffBrokerFee),
					"View/Print Full Quote Page",
					"Calculated Broker Fees and Broker Fees in View/Print Full Quote Page are equal", false, false);

			// click on go back
			viewOrPrintFullQuotePage.scrollToTopPage();
			viewOrPrintFullQuotePage.waitTime(3);// need wait time to scroll to element
			viewOrPrintFullQuotePage.backButton.click();

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

			// click on request bind button in account overview page
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Go to HomePage
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			// approving request
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// getting the policy number
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			Assertions.passTest("Policy Summary Page", "Policy Number : " + policyNumber);

			// validating sltf is not present in policy summary page
			Assertions.addInfo("Policy Summary Page",
					"Asserting sltf and broker fee is not present in policy summary page");
			Assertions.verify(policySummarypage.taxesAndStateFees.formatDynamicPath(1).checkIfElementIsPresent(), false,
					"Policy Summary Page",
					"Surplus Line Taxes and Broker Fees not present in Policy summary page is verified", false, false);

			// click on view policy snapshot link
			policySummarypage.viewPolicySnapshot.click();
			Assertions.passTest("View Policy Snapshot Page", "View Policy Snapshot Page loaded successfully");

			// validating sltf is not present in view policy snapshot page
			Assertions.addInfo("View Policy Snapshot Page",
					"Asserting sltf and broker fee is not present in View Policy Snapshot Page");
			Assertions.verify(viewPolicySnapShotPage.surplusLinesTaxesValue.checkIfElementIsPresent(), false,
					"View Policy Snapshot Page",
					"Surplus Line Taxes and Fees is not present in View Policy snapshot page is verified", false,
					false);
			Assertions.verify(viewPolicySnapShotPage.brokerFeeValue.checkIfElementIsPresent(), false,
					"View Policy Snapshot Page", "Broker Fees is not present in View Policy snapshot page is verified",
					false, false);
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 103", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 103", "Executed Successfully");
			}
		}
	}
}
