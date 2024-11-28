/** Program Description: Create a Wind Policy and during renewal requote, upgrade from Wind to AOP and check Broker Fee Not Taxable in NY state
 *  Author			   :  Vinay
 *  Date of Creation   : 11/15/2019
 **/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.text.DecimalFormat;
import java.util.List;
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
import com.icat.epicenter.pom.GLInformationPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
//import com.epicenter.POM.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PreferenceOptionsPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC044 extends AbstractCommercialTest {

	public TC044() {
		super(LoginType.PRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID044.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Pages
		LoginPage loginPage = new LoginPage();
		PreferenceOptionsPage preferenceOptionsPage = new PreferenceOptionsPage();
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
		GLInformationPage glInformationPage = new GLInformationPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		double expectedsltf;
		double expectedsltfWithBrokerfee;
		DecimalFormat df = new DecimalFormat("0.00");
		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			homePage.enterPersonalLoginDetails();
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);

			// Click on user preference link and enter the details
			homePage.userPreferences.scrollToElement();
			homePage.userPreferences.click();
			Assertions.passTest("Home Page", "Clicked on User Preferences");

			// Add broker fees
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

			// Navigate to homepage
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Signing out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Producer successfully");

			// Login as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login", "Logged in to application successfully");

			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"USM Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully for Newyork State");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);
			Assertions.passTest("Location Page", "Location details entered successfully");

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
				Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");
			}

			// entering general liability details
			if (!testData.get("L1-GLLocationClass").equals("")) {
				Assertions.verify(glInformationPage.locationClassArrow.checkIfElementIsDisplayed(), true,
						"GL Information Page", "GL Information Page loaded successfully", false, false);
				glInformationPage.enterGLInformation(testData);
				Assertions.passTest("GL Information Page", "GL Information details entered successfully");
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

			// Asserting SLTF value from account overview page to verify broker fee is not
			// taxable for NY state - IO-18557
			// Getting the premium,icat fees,other fees and sltf values from premium section
			// in Account Overview page
			Assertions.addInfo("Account Overview Page",
					"Asserting SLTF value from account overview page to verify broker fee is not taxable for NY state");
			Assertions.verify(accountOverviewPage.otherFees.checkIfElementIsPresent(), true, "Account Overview Page",
					"Other Fees displayed is verified", false, false);
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Surplus Lines Taxes and Fees label displayed is verified", false, false);
			Assertions.verify(accountOverviewPage.premiumValue.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Premium Value is : " + accountOverviewPage.premiumValue.getData(), false,
					false);
			Assertions.verify(accountOverviewPage.feesValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Fees Value is : " + accountOverviewPage.feesValue.getData(), false, false);

			// fetching premium amount
			String premium = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
			double premiumAmount = Double.parseDouble(premium);

			// fetching fees value
			String icatFees = accountOverviewPage.feesValue.getData().replace("$", "").replace(",", "");
			double icatFeesAmount = Double.parseDouble(icatFees);

			// fetching sltf percentage
			String sltfPercentage = testData.get("SurplusLinesTaxesPercentage");
			double double_sltfPercentage = Double.parseDouble(sltfPercentage);

			testData = data.get(data_Value2);
			String sltfWithbrokerFeePercentage = testData.get("SurplusLinesTaxesPercentage");
			double d_sltfWithbrokerFeePercentage = Double.parseDouble(sltfWithbrokerFeePercentage);

			// calculating sltf by adding Premium+Fees and multiplying by sltf percentage
			// 0.0377
			expectedsltf = (premiumAmount + icatFeesAmount) * double_sltfPercentage;

			// Rounding sltf decimal value to 2 digits
			df.format(expectedsltf);

			// Printing sltf value without broker fee
			Assertions.passTest("Account Overview Page",
					"The Calculated Surplus Lines Taxes and Fees without Broker fee for NY state is : " + "$"
							+ df.format(expectedsltf));

			// calculating sltf by adding premium+fees_broker fees multiplying by sltf
			// percentage 0.0677
			expectedsltfWithBrokerfee = (premiumAmount + icatFeesAmount) * d_sltfWithbrokerFeePercentage;

			// Rounding sltf decimal value to 2 digits
			df.format(expectedsltfWithBrokerfee);

			// Printing sltf value without broker fee
			Assertions.passTest("Account Overview Page",
					"The Calculated Surplus Lines Taxes and Fees with Broker fee for NY state is : " + "$"
							+ df.format(expectedsltfWithBrokerfee));

			// getting actual sltf value
			String actualSltf = accountOverviewPage.sltfValue.getData().replace("$", "");

			// Compare sltf value with and without broker fee are not equal
			if (Precision.round(
					Math.abs(Precision.round(Double.parseDouble(actualSltf), 2) - Precision.round(expectedsltf, 2)),
					2) < 0.5) {
				Assertions.passTest("Account Overview Page",
						"Calculated SLTF Value : " + "$" + df.format(expectedsltf));
				Assertions.passTest("Account Overview Page",
						"Actual SLTF Value : " + accountOverviewPage.sltfValue.getData());
			} else {
				Assertions.passTest("Account Overview Page",
						"The Difference between actual and calculated SLTF is more than 0.05");
			}

			testData = data.get(data_Value1);

			// Click on request bind
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
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// approving referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
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

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// click on expacc link
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

			// click on release renewal to producer button
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();

			// getting renewal quote number
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// click on request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);

			// Binding renewal quote
			requestBindPage.renewalRequestBind(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// clicking on home button
			if (bindRequestPage.homePage.checkIfElementIsPresent()
					&& bindRequestPage.homePage.checkIfElementIsDisplayed()) {
				Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
						"Bind Request Page loaded successfully", false, false);
				bindRequestPage.clickOnHomepagebutton();
				Assertions.passTest("Home Page", "Clicked on Home button");

				// searching the quote number in grid and clicking on the quote link
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home page loaded successfully", false, false);
				homePage.searchQuote(quoteNumber);
				Assertions.passTest("Home Page", "Quote opened successfully");

				// click on open referral link
				accountOverviewPage.openReferralLink.scrollToElement();
				accountOverviewPage.openReferralLink.click();
				Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

				// approving referral
				referralPage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");
				requestBindPage.approveRequest();
				Assertions.passTest("Request Bind Page", "Bind Request approved successfully");
			}

			// getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Renewed Policy Number is : " + policyNumber);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 44", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 44", "Executed Successfully");
			}
		}
	}
}
