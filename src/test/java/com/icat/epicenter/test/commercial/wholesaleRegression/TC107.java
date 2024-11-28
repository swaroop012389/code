/** Program Description:  Check SLTF is calculated and displayed on Rewritten quote and added IO-20545
 *  Author			   : Sowndarya
 *  Date of Modified : 09/28/2021
**/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PreferenceOptionsPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC107 extends AbstractCommercialTest {

	public TC107() {
		super(LoginType.PRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID107.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		PreferenceOptionsPage preferenceOptionsPage = new PreferenceOptionsPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		LoginPage login = new LoginPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		PolicyRenewalPage policyrenewalPage = new PolicyRenewalPage();
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		DecimalFormat df = new DecimalFormat("0.00");

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		String quoteNumber1;
		String premiumAmount;
		String otherFees;
		String icatFees;
		String surplusLinesTaxesPercentage;
		String surplusLinesTaxesPercentage1;
		double expectedsltf;
		double expectedStampingFees;
		String actualSLTFValue;
		String stampingPercentage;
		double expectedFiremarshalTax;
		double totalexpectedSLTF;
		double viewPageExpectedSltf;
		double sltfBrokerfees;
		String fireMarshalPercentage;
		double sltfComponentBrokerFee;
		String actualStampingFee;
		double viewPageExpectedStampingFees;
		String brokerPercentage;
		double viewPageExpectedFireMashalTax;
		String actualFiremarshalTax;
		double totalSLTFPercentage;
		BigDecimal roundOffSLTFPercentage;
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
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
			Assertions.passTest("Location Page", "Location Details Entered successfully");

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

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
			Assertions.addInfo("Account Overview Page",
					"Asserting the absence of Other Fees and SLTF on account overview page");
			Assertions.verify(accountOverviewPage.otherFees.checkIfElementIsDisplayed(), false, "Account Overview Page",
					"Other Fees is Not present verified ", false, false);
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsPresent(), false, "Account Overview Page",
					"Surplus Lines Taxes and Fees label Not displayed is verified", false, false);

			// Click on View/Print Full quote link on account overview page.
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page",
					"Clicked on View Print Full Quote Link successfully and ViewOrPrintFullQuotePage loaded successfully");

			// getting terms and conditions on viewOrprintFullQuote Document Page
			Assertions.addInfo("ViewOrPrintFullQuotePage",
					"Asserting terms and conditions on viewOrprintFullQuote Page");
			viewOrPrintFullQuotePage.termsAndConditions.formatDynamicPath(2, 3).scrollToElement();
			Assertions.verify(
					viewOrPrintFullQuotePage.termsAndConditions.formatDynamicPath(2, 3).checkIfElementIsPresent(), true,
					"ViewOrPrintFullQuotePage",
					"Verifying Terms and Condition:"
							+ "The Wordings Terms And Conditions,The Producer is responsible for calculation and remittance of all Surplus Lines Taxes and Fees is Verified ",
					false, false);

			// clicked on go Back button on ViewOrPrintFull Quote page
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.passTest("View or Print Full Quote Page",
					"Clicked on Back Button successfully and Navigated to Account Overview Page");

			// Sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Sign in as USM
			login.refreshPage();
			login.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login", "Logged in to application successfully");

			// search the quote
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Searched the Quote successfully");

			// entering details in request bind page
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
			requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);

			// clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home Page", "Clicked on Home button");

			// Sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Sign in as producer
			login.refreshPage();
			login.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			Assertions.passTest("Login", "Logged in to application successfully");

			// click on user preference link and enter the details
			homePage.userPreferences.scrollToElement();
			homePage.userPreferences.click();
			Assertions.passTest("Home Page", "Clicked on User Preferences");
			Assertions.passTest("Preference Options Page", "Preference Options Page loaded successfully");
			testData = data.get(data_Value2);
			preferenceOptionsPage.addBrokerFees(testData);

			// Click on GoTo Home
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.passTest("Home Page", "Clicked on Home Page Button successfully");

			// Click on LogOut Button
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "SignOut as Producer successfully");

			// Login as USM
			login.refreshPage();
			login.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "SignIn as User successfully");

			// Searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// Approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// getting the policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			Assertions.passTest("Policy Summary Page", "Policy Number :" + policyNumber);

			// Click on Rewrite policy link
			Assertions.addInfo("Policy Summary Page", "Rewriting the Policy");
			policySummaryPage.rewritePolicy.scrollToElement();
			policySummaryPage.rewritePolicy.click();
			Assertions.passTest("Policy Summary Page",
					"Clicked on ReWrite Policy link successfully And Navigated to Account Overview Page");

			// Click on Edit Dwelling link
			accountOverviewPage.buildingLink.formatDynamicPath(1, 2).scrollToElement();
			accountOverviewPage.buildingLink.formatDynamicPath(1, 2).click();
			accountOverviewPage.editBuilding1.scrollToElement();
			accountOverviewPage.editBuilding1.click();
			Assertions.passTest("Account Overview Page", "Clicked Edit Dweling link successfully");

			// Enter Building Details
			Assertions.verify(buildingPage.reviewBuilding.checkIfElementIsDisplayed(), true, "Building Page",
					"Building Page loaded successfully", false, false);
			buildingPage.modifyBuildingDetailsPNB_old(testData);

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber1 = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number1 :  " + quoteNumber1);

			Assertions.verify(accountOverviewPage.otherFees.checkIfElementIsPresent(), true, "Account Overview Page",
					"Other Fees is present verified", false, false);
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Surplus Lines Taxes and Fees label displayed is verified", false, false);

			// Fetching Premium Amount from Account Overview Page
			premiumAmount = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("Account Overview Page", "Actual Premium Amount : " + "$" + premiumAmount);

			// Fetching ICATfees Value from Account Overview Page
			icatFees = accountOverviewPage.feesValue.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("Account Overview Page", "Actual ICAT Fees : " + "$" + icatFees);

			// Fetching Otherfees from Account Overview Page
			otherFees = accountOverviewPage.otherFees.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("Account Overview Page", "Actual Broker Fees:" + "$" + otherFees);

			// Get surplus contibution value
			String surplusContributionValue = accountOverviewPage.surplusContributionValue.getData().replace("$", "")
					.replace(".00", "");

			// Getting surplusLinesTaxesPercentage from Data sheet
			surplusLinesTaxesPercentage = testData.get("SurplusLinesTaxesPercentage");

			// calculating sltf (premiumamount*SLTF%)....0.035
			expectedsltf = (Double.parseDouble(premiumAmount) + Double.parseDouble(surplusContributionValue))
					* (Double.parseDouble(surplusLinesTaxesPercentage));
			Assertions.passTest("Account Overview Page",
					"Calculated Surplus Lines Taxes and Fees :" + "$" + Math.round(expectedsltf));

			// Calculating Stamping fees(premium*stamping%)....0.0004
			stampingPercentage = testData.get("StampingPercentage");

			expectedStampingFees = (Double.parseDouble(premiumAmount)) * (Double.parseDouble(stampingPercentage));
			Assertions.passTest("Account Overview Page", "Calculated Stamping  Fees =(" + premiumAmount + "*"
					+ stampingPercentage + ")" + "=" + "$" + Math.round(expectedStampingFees));

			// Calculating Fire Marshal Tax(premium*0.0025)
			fireMarshalPercentage = testData.get("FireMarshalPercentage");
			expectedFiremarshalTax = (Double.parseDouble(premiumAmount)) * (Double.parseDouble(fireMarshalPercentage));
			Assertions.passTest("Account Overview Page", "Calculated Fire Marshal TAX = (" + premiumAmount + "*"
					+ fireMarshalPercentage + ")" + "=" + "$" + Math.round(expectedFiremarshalTax));

			// Total SLTF Percentage to calculate on account overview page
			// 3.825%=(3.5+0.04+0.25)
			totalSLTFPercentage = Double.parseDouble(surplusLinesTaxesPercentage)
					+ Double.parseDouble(stampingPercentage) + Double.parseDouble(fireMarshalPercentage);
			roundOffSLTFPercentage = new BigDecimal(totalSLTFPercentage);
			roundOffSLTFPercentage = roundOffSLTFPercentage.setScale(5, RoundingMode.HALF_UP);
			Assertions.passTest("Account Overview Page", "Total SLTF%=(" + surplusLinesTaxesPercentage + "+"
					+ stampingPercentage + "+" + fireMarshalPercentage + ")=" + roundOffSLTFPercentage);

			// Actual SLTF value from Account Overview Page
			actualSLTFValue = accountOverviewPage.sltfValue.getData();
			Assertions.passTest("Account Overview Page", "Actual SLTF Values:" + actualSLTFValue);

			// Total Calculated SLTF by adding(SLTf+StampingFees+FiremarshalTax)
			totalexpectedSLTF = (Math.round(expectedFiremarshalTax) + Math.round(expectedStampingFees)
					+ Math.round(expectedsltf));
			Assertions.passTest("Account Overview Page",
					"Expected TotalSLTF=("
							+ (Math.round(expectedFiremarshalTax) + "+" + Math.round(expectedStampingFees) + "+"
									+ Math.round(expectedsltf) + ")=" + "$" + totalexpectedSLTF + "0"));

			// Calculating SLTF with Broker fees and ICAT fees(premimu+Broker fees or other
			// fees+ICAT fees)*0.03825
			surplusLinesTaxesPercentage1 = testData.get("SurplusLinesTaxesPercentage1");
			sltfBrokerfees = (Double.parseDouble(premiumAmount) + Double.parseDouble(icatFees)
					+ Double.parseDouble(otherFees)) * (Double.parseDouble(surplusLinesTaxesPercentage1));
			Assertions.passTest("Account Overview Page",
					"SLTF With Broker Fees and ICAT Fees = : " + "(" + premiumAmount + "+" + icatFees + "+" + otherFees
							+ ")" + "*" + surplusLinesTaxesPercentage1 + " = " + "$" + Math.round(sltfBrokerfees));

			Assertions.passTest("Account Overview Page", "SLTF Without Broker Fees and ICAT Fees  : " + premiumAmount
					+ "*" + surplusLinesTaxesPercentage1 + " = " + format.format(totalexpectedSLTF));

			// Calculating SLTF component of broker fee (Otherfees*SLTF %0.037)
			sltfComponentBrokerFee = (Double.parseDouble(otherFees) + Double.parseDouble(icatFees))
					* (Double.parseDouble(surplusLinesTaxesPercentage1));
			Assertions.passTest("Account Overview Page",
					"SLTF component of broker fees and ICAT fees :" + "$" + Math.round(sltfComponentBrokerFee));
			Assertions.passTest("Account Overview Page",
					"SLTF with broker fee for Il state is:" + "$" + Math.round(sltfBrokerfees) + "="
							+ format.format(totalexpectedSLTF) + "+" + "$" + Math.round(sltfComponentBrokerFee));

			// Verify actual and calculated SLTF values are equal
			Assertions.addInfo("Account Overview Page",
					"Verifying actual and calculated SLTF values are equal on Account Overview Page");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(actualSLTFValue.replace("$", "")), 2)
					- Precision.round(totalexpectedSLTF, 2)), 2) < 0.5) {
				Assertions.passTest("Account Overview Page",
						"Calculated SLTF Value : " + "$" + df.format(totalexpectedSLTF));
				Assertions.passTest("Account Overview Page", "Actual SLTF Value : " + "$" + actualSLTFValue);
			} else {
				Assertions.passTest("Account Overview Page",
						"The Difference between actual and calculated SLTF Value is more than 0.5");
			}

			// Click on View/Print Full quote link.
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page",
					"Clicked on ViewPrintFull Quote Link successfully and ViewOrPrintFullQuotePage loaded successfully");

			// Getting premium amout from ViewOrprintFullQuotePage
			premiumAmount = viewOrPrintFullQuotePage.eqPremiumDetails.formatDynamicPath(5).getData()
					.replaceAll("[^\\d-.]", "");
			Assertions.passTest("ViewOrPrint FullQuote Page", "Premium Amount:" + premiumAmount);

			// Getting surplus contribution value
			String surplusContributionVPFQ = viewOrPrintFullQuotePage.surplusContributionValue.getData()
					.replace("$", "").replace(".00", "");

			// Calculating SLTf on viewOrprint fullQuote Page(premium*SLTf%)....0.035
			viewPageExpectedSltf = (Double.parseDouble(premiumAmount) + Double.parseDouble(surplusContributionVPFQ))
					* (Double.parseDouble(surplusLinesTaxesPercentage));

			// Getting Actual SLTF from vieworprtint Fullquote page
			actualSLTFValue = viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData();
			Assertions.passTest("ViewOrPrint FullQuote Page", "Actual SLTF:" + actualSLTFValue);
			Assertions.passTest("ViewOrPrint FullQuote Page", "Expected SLTF=(" + premiumAmount + "*"
					+ surplusLinesTaxesPercentage + ")=" + format.format(Math.round(viewPageExpectedSltf)));

			// Verifying actual and calculated SLTF values are equal
			Assertions.addInfo("ViewOrPrint FullQuote Page",
					"Verifying actual and calculated SLTF values are equal on ViewOrPrint FullQuote Page");
			if (actualSLTFValue.equalsIgnoreCase(format.format(Math.round(viewPageExpectedSltf)))) {
				Assertions.verify(actualSLTFValue, format.format(Math.round(viewPageExpectedSltf)),
						"ViewOrPrint FullQuote Page",
						"The Calculated and Actual SLTF values are the same for Il Percentage 3.5% : "
								+ actualSLTFValue,
						false, false);
			} else {
				Assertions.verify(actualSLTFValue, format.format(Math.round(viewPageExpectedSltf)),
						"ViewOrPrint FullQuote Page",
						"The Calculated and Actual SLTF values are Not the same for IL Percentage 3.5% : "
								+ actualSLTFValue,
						false, false);
			}

			// Calculatingg Stamping fees(premium*Stamping%)....0.00075
			viewPageExpectedStampingFees = (Double.parseDouble(premiumAmount))
					* (Double.parseDouble(stampingPercentage));

			// Getting actual Stamping fees from ViewOrprint Full Quote page
			actualStampingFee = viewOrPrintFullQuotePage.stampingFeeValue.getData();
			Assertions.passTest("ViewOrPrint FullQuote Page", "Actual Stamping Fees:" + actualStampingFee);
			Assertions.passTest("ViewOrPrint FullQuote Page", "Expected Staming Fees=(" + premiumAmount + "*"
					+ stampingPercentage + ")=" + format.format(Math.round(viewPageExpectedStampingFees)));

			// Verifying actual and calculated Stamping fees are equal
			Assertions.addInfo("ViewOrPrint FullQuote Page",
					"Verifying actual and calculated Stamping values are equal on ViewOrPrint FullQuote Page");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(actualStampingFee.replace("$", "")), 2)
					- Precision.round(viewPageExpectedStampingFees, 2)), 2) < 0.5) {
				Assertions.passTest("View/Print Full Quote Page",
						"Calculated Stamping Fees : " + "$" + df.format(viewPageExpectedStampingFees));
				Assertions.passTest("View/Print Full Quote Page",
						"Actual Stamping Fees : " + viewOrPrintFullQuotePage.stampingFeeValue.getData());
			} else {
				Assertions.passTest("View/Print Full Quote Page",
						"The Difference between actual and calculated Stamping fee is more than 0.05");
			}

			// Getting actual other fees from ViewOrprint Full Quote page
			brokerPercentage = testData.get("BrokerFeeValue");
			otherFees = viewOrPrintFullQuotePage.brokerFeeValue.getData();
			Assertions.passTest("ViewOrPrint FullQuote Page", "Actual Broker Fees:" + otherFees);
			Assertions.passTest("ViewOrPrint FullQuote Page", "Expected Broker Fees:" + "$" + brokerPercentage + ".00");

			// Verify actual and calculated others fees are equal
			Assertions.addInfo("ViewOrPrint FullQuote Page",
					"Verifying actual and calculated other fees values are equal on ViewOrPrint FullQuote Page");
			if (otherFees.equalsIgnoreCase("$" + brokerPercentage + ".00")) {
				Assertions.verify(otherFees, "$" + brokerPercentage + ".00", "ViewOrPrint FullQuote Page",
						"The Expected and Actual Broker Fees  are the same for Il State : " + otherFees, false, false);
			} else {
				Assertions.verify(otherFees, brokerPercentage, "ViewOrPrint FullQuote Page",
						"The Expected and Actual Broker Fees  are   not the same for Il State : " + otherFees, false,
						false);
			}

			// Getting FireMarshal Tax from ViewOrprint Full Quote page
			actualFiremarshalTax = viewOrPrintFullQuotePage.fireMarshallTax.getData();

			// Calculatingg Firemarshal fees(premium*FireTax%)....0.0025
			viewPageExpectedFireMashalTax = (Double.parseDouble(premiumAmount))
					* (Double.parseDouble(fireMarshalPercentage));
			Assertions.passTest("ViewOrPrint FullQuote Page", "Actual Fire Marshal Tax:" + actualFiremarshalTax);
			Assertions.passTest("ViewOrPrint FullQuote Page", "Expected Fire Marshal Tax=(" + premiumAmount + "*"
					+ fireMarshalPercentage + ")=" + format.format(Math.round(viewPageExpectedFireMashalTax)));

			// Verify actual and calculated FireMarshal Tax are equal
			Assertions.addInfo("ViewOrPrint FullQuote Page",
					"Verifying actual and calculated FireMarshal tax values are equal on ViewOrPrint FullQuote Page");
			if (actualFiremarshalTax.equalsIgnoreCase(format.format(Math.round(viewPageExpectedFireMashalTax)))) {
				Assertions.verify(actualFiremarshalTax, format.format(Math.round(viewPageExpectedFireMashalTax)),
						"ViewOrPrint FullQuote Page",
						"The Calculated and Actual FireMarshal Tax  are the same for Il Percentage 0.25% : "
								+ actualFiremarshalTax,
						false, false);
			} else {
				Assertions.verify(actualFiremarshalTax, format.format(Math.round(viewPageExpectedFireMashalTax)),
						"ViewOrPrint FullQuote Page",
						"The Calculated and Actual FireMarshal Tax   are Not the same for IL Percentage 0.25% : "
								+ actualFiremarshalTax,
						false, false);
			}

			// Getting terms and conditions on viewOrprintFullQuotePage
			Assertions.addInfo("ViewOrPrint FullQuote Page",
					"Asserting terms and conditions on ViewOrPrint FullQuote Page");
			viewOrPrintFullQuotePage.termsAndConditions.formatDynamicPath(2, 3).scrollToElement();
			Assertions.verify(
					viewOrPrintFullQuotePage.termsAndConditions.formatDynamicPath(2, 3).checkIfElementIsPresent(), true,
					"ViewOrPrintFullQuotePage",
					"Verifying Terms and Condition:"
							+ "The Producer is responsible for calculating and remitting any and all surplus lines taxes that may apply to this purchase. The amounts listed above are estimates and for informational purposes only. is Verified ",
					false, false);

			// click on go Back button on ViewOrPrintFull Quote page
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.passTest("View or Print Full Quote Page",
					"Clicked on Back Button successfully and Navigated to Account Overview Page");

			// Click on Rewrite Bind button
			Assertions.passTest("Account Overview Page", "Clicked on Rewriten Bind Button successfully");
			accountOverviewPage.rewriteBind.scrollToElement();
			accountOverviewPage.rewriteBind.click();
			Assertions.verify(requestBindPage.rewrite.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);

			requestBindPage.effectiveDate.waitTillVisibilityOfElement(60);
			requestBindPage.effectiveDate.scrollToElement();
			requestBindPage.effectiveDate.setData(testData.get("PolicyEffDate"));
			requestBindPage.effectiveDate.tab();
			requestBindPage.waitTime(2);// if wait time is removed test case will fail here
			requestBindPage.previousPolicyCancellationDate.clearData();
			requestBindPage.waitTime(3);// need waittime to load the page
			requestBindPage.previousPolicyCancellationDate.appendData(testData.get("PreviousPolicyCancellationDate"));
			requestBindPage.previousPolicyCancellationDate.tab();
			requestBindPage.contactSurplusLicenseNumber.scrollToElement();
			requestBindPage.contactSurplusLicenseNumber.setData(testData.get("SurplusLicenceNumber"));

			// Asserting Dates
			Assertions.addInfo("Request Bind Page",
					"Asserting Original Policy effective Date and Rewritten Policy Effecctive Date");
			Assertions.passTest("Request Bind Page",
					"Original Policy Effective date is " + testData.get("PolicyEffDate"));
			Assertions.passTest("Request Bind Page",
					"Original Policy Cancellation date is " + requestBindPage.previousPolicyCancellationDate.getData());
			Assertions.passTest("Request Bind Page",
					"Rewritten Policy Effective date is " + requestBindPage.effectiveDate.getData());

			// Click on rewrite button
			requestBindPage.rewrite.scrollToElement();
			requestBindPage.rewrite.click();
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Getting policy numbers in policy summary page
			String rewrittenPolicyNumber = policySummaryPage.policyNumber.getData();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);

			// Asserting policy status after Rewrite/Cancel
			Assertions.addInfo("Policy Summary Page", "Asserting policy status after Rewrite/Cancel");
			Assertions.verify(policySummaryPage.policyStatus.getData(), "Active", "Policy Summary Page",
					"Policy status after Rewrite/Cancel is :" + policySummaryPage.policyStatus.getData(), false, false);

			// Asserting Rewritten PolicyNumber from policy summary page
			Assertions.passTest("Policy Summary Page", "Rewritten Policy Number is : " + rewrittenPolicyNumber);

			// Navigate to original policy
			policySummaryPage.rewrittenPolicyNumber.waitTillVisibilityOfElement(60);
			policySummaryPage.rewrittenPolicyNumber.scrollToElement();
			policySummaryPage.rewrittenPolicyNumber.click();
			Assertions.passTest("Policy Summary Page", "Original Policy Number is : " + policyNumber);
			Assertions.addInfo("Policy Summary Page",
					"Asserting the absence of Taxes and Fees,SLTF,Broker Fees on Rewritten Policy summary page ");
			Assertions.passTest("Policy Summary Page",
					"Taxes and Fees is not displayed on Policy Summary page of Rewritten policy");

			// validating sltf is not present in policy summary page
			Assertions.verify(policySummaryPage.taxesAndStateFees.formatDynamicPath(1).checkIfElementIsPresent(), false,
					"Policy Summary Page",
					"Surplus Line Taxes and Broker Fees not present in Policy summary page is verified", false, false);

			// Adding below code for IO-20545
			// Click on Rewritten To policy link
			testData = data.get(data_Value3);
			policySummaryPage.rewrittenPolicyNumberTo.waitTillPresenceOfElement(60);
			policySummaryPage.rewrittenPolicyNumberTo.scrollToElement();
			policySummaryPage.rewrittenPolicyNumberTo.click();
			Assertions.verify(policySummaryPage.renewPolicy.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);

			// Click on Home Button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Click on Expacc
			homePage.scrollToBottomPage();
			homePage.expaccLink.scrollToElement();
			homePage.expaccLink.click();

			// Enter expacc details
			Assertions.verify(expaccInfoPage.submit.checkIfElementIsDisplayed(), true, "Expacc Info Page",
					"Expacc details page loaded successfully", false, false);
			expaccInfoPage.enterExpaccInfo(testData, rewrittenPolicyNumber);
			Assertions.passTest("Expacc Info Page", "Expacc Details entered successfully");

			// Click on Home Button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchPolicy(rewrittenPolicyNumber);

			// Click on renewal link
			Assertions.addInfo("Policy Summary Page", "Renewing the Policy");
			policySummaryPage.renewPolicy.waitTillVisibilityOfElement(60);
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew policy link");

			// Click on yes button
			if (accountOverviewPage.yesButton.checkIfElementIsPresent()
					&& accountOverviewPage.yesButton.checkIfElementIsDisplayed()) {
				accountOverviewPage.yesButton.scrollToElement();
				accountOverviewPage.yesButton.click();
			}
			if (policyrenewalPage.continueRenewal.checkIfElementIsPresent()
					&& policyrenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
				policyrenewalPage.continueRenewal.scrollToElement();
				policyrenewalPage.continueRenewal.click();
			}

			// getting the renewal quote number
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Renewal Quote Number is :  " + quoteNumber);

			// Click on Home Button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 107", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 107", "Executed Successfully");
			}
		}
	}
}
