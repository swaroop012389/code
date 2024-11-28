package com.icat.epicenter.test.naho.regression.ISNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC045_NBQA003 extends AbstractNAHOTest {

	public TC045_NBQA003() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBQA003.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the Objects
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		int data_Value1 = 0;
		Map<String, String> testData = data.get(data_Value1);
		String namedStorm = "2%";
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Create New Account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("Home Page", "New account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);

			// Io-20810
			// Verifying minimum default named storm deductible is 2% as producer
			Assertions.addInfo("Scenario 01", "Verifying minimu default named storm deductible is 2% as producer");
			Assertions.verify(createQuotePage.namedStormData.getData(), namedStorm, "Create Quote Page",
					"All other risks in TX, outside of Tri County, will have a minimum Named Storm deductible set to "
							+ createQuotePage.namedStormData.getData() + " is verified",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Enter Referral Contact Details
			referQuotePage.contactName.setData(testData.get("ProducerName"));
			referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
			referQuotePage.referQuote.click();

			// verifying referral message
			Assertions.verify(referQuotePage.quoteNumberforReferral.checkIfElementIsDisplayed(), true, "Referral Page",
					"Quote " + referQuotePage.quoteNumberforReferral.getData() + " referring to USM " + " is verified",
					false, false);
			String quoteNumber = referQuotePage.quoteNumberforReferral.getData();

			// Sign in out as producer
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged out as Producer successfully");

			// Sign in as USM
			loginPage.refreshPage();
			Assertions.verify(loginPage.signInButton.checkIfElementIsDisplayed(), true, "Login Page",
					"Login page loaded successfully", false, false);
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");

			// searching the quote number in grid and clicking on the quote number
			// link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Searched Submitted Quote successfullly");

			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();

			// click on approve in Approve Decline Quote page
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			// Sign out as USM
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged out as USM successfully");

			// Sign in as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as Producer successfully");

			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Assert account overview page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.passTest("Producer Home Page", "Quote Number : " + quoteNumber + " searched successfully");

			// Asserting the Details
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();

			Assertions.verify(viewOrPrintFullQuotePage.payPlan.checkIfElementIsDisplayed(), true,
					"View Or Print Full Quote Page", "Quote Page Loaded successfully", false, false);

			// Verifying the presence of Payment plans
			Assertions.verify(viewOrPrintFullQuotePage.method.formatDynamicPath(4).checkIfElementIsDisplayed(), true,
					"View Or Print Full Quote Page",
					"The Payment Plan DownPayment for Full/Mortgagee pay "
							+ viewOrPrintFullQuotePage.method.formatDynamicPath(4).getData() + " is displayed",
					false, false);
			Assertions.verify(viewOrPrintFullQuotePage.method.formatDynamicPath(5).checkIfElementIsDisplayed(), true,
					"View Or Print Full Quote Page",
					"The Payment Plan FuturePayment for Full/Mortgagee pay "
							+ viewOrPrintFullQuotePage.method.formatDynamicPath(5).getData() + " is displayed",
					false, false);
			Assertions.verify(viewOrPrintFullQuotePage.method.formatDynamicPath(7).checkIfElementIsDisplayed(), true,
					"View Or Print Full Quote Page",
					"The Payment Plan DownPayment for 4 pay "
							+ viewOrPrintFullQuotePage.method.formatDynamicPath(7).getData() + " is displayed",
					false, false);
			Assertions.verify(viewOrPrintFullQuotePage.method.formatDynamicPath(8).checkIfElementIsDisplayed(), true,
					"View Or Print Full Quote Page",
					"The Payment Plan FuturePayment for 4 pay "
							+ viewOrPrintFullQuotePage.method.formatDynamicPath(8).getData() + " is displayed",
					false, false);

			// Verifying the Presence of Payplan,method,Down payment and Future payment
			Assertions.verify(viewOrPrintFullQuotePage.payPlan.checkIfElementIsDisplayed(), true,
					"View Or Print Full Quote Page", viewOrPrintFullQuotePage.payPlan.getData() + " is displayed",
					false, false);
			Assertions.verify(viewOrPrintFullQuotePage.method.formatDynamicPath(1).checkIfElementIsDisplayed(), true,
					"View Or Print Full Quote Page",
					viewOrPrintFullQuotePage.method.formatDynamicPath(1).getData() + " is displayed", false, false);
			Assertions.verify(viewOrPrintFullQuotePage.method.formatDynamicPath(2).checkIfElementIsDisplayed(), true,
					"View Or Print Full Quote Page",
					viewOrPrintFullQuotePage.method.formatDynamicPath(2).getData() + " is displayed", false, false);
			Assertions.verify(viewOrPrintFullQuotePage.method.formatDynamicPath(3).checkIfElementIsDisplayed(), true,
					"View Or Print Full Quote Page",
					viewOrPrintFullQuotePage.method.formatDynamicPath(3).getData() + " is displayed", false, false);
			Assertions.verify(viewOrPrintFullQuotePage.method.formatDynamicPath(6).checkIfElementIsDisplayed(), true,
					"View Or Print Full Quote Page",
					viewOrPrintFullQuotePage.method.formatDynamicPath(6).getData() + " is displayed", false, false);

			// Calculation of 3 pay and 10 pay for Downpayment and future payment
			int premValLen = viewOrPrintFullQuotePage.premiumValue.getData().length();
			String premiumValueA = viewOrPrintFullQuotePage.premiumValue.getData().substring(1, premValLen);
			String premiumValue = premiumValueA.replace(",", "");
			double premiumValue1 = Double.parseDouble(premiumValue);
			Assertions.passTest("View Or Print Full Quote Page", "Premium Value is " + premiumValue1);

			int policyFeeLen = viewOrPrintFullQuotePage.policyFee.getData().length();
			String policyFee = viewOrPrintFullQuotePage.policyFee.getData().substring(1, policyFeeLen);
			double policyFee1 = Double.parseDouble(policyFee);
			Assertions.passTest("View Or Print Full Quote Page", "Policy Fee is " + policyFee1);

			int inspectionFeeLen = viewOrPrintFullQuotePage.inspectionFee.getData().length();
			String inspectionFee = viewOrPrintFullQuotePage.inspectionFee.getData().substring(1, inspectionFeeLen);
			double inspectionFee1 = Double.parseDouble(inspectionFee);
			Assertions.passTest("View Or Print Full Quote Page", "Inspection Fee is " + inspectionFee1);

			double fees = policyFee1 + inspectionFee1;
			Assertions.passTest("View Or Print Full Quote Page", "Total Fee is " + fees);
			double f1 = 40.0 / 100;
			double f3 = 15.04 / 100;
			double f4 = 9.44 / 100;
			String t = "10";
			double t1 = Double.parseDouble(t);

			// Full pay Down payment Calculations
			double fullPayDownPaymentVal = premiumValue1 * f1 + fees;
			double fullPayDownPaymentdouble = fullPayDownPaymentVal;
			int fullPayDownPaymentLen = viewOrPrintFullQuotePage.method.formatDynamicPath(4).getData().length();
			String fullPayDownPaymentApplnA = viewOrPrintFullQuotePage.method.formatDynamicPath(4).getData()
					.substring(1, fullPayDownPaymentLen);
			String fullPayDownPaymentAppln = fullPayDownPaymentApplnA.replace(",", "").replace("$", "");
			double fullPayDownPaymentAppln1 = Double.parseDouble(fullPayDownPaymentAppln);
			Assertions.verify((fullPayDownPaymentdouble - fullPayDownPaymentAppln1) < 1, true,
					"View Or Print Full Quote Page",
					"FullPay Down payment " + "$" + fullPayDownPaymentAppln1 + " is displayed", false, false);

			// Full pay Future payment Calculations
			Assertions.verify(viewOrPrintFullQuotePage.method.formatDynamicPath(5).getData().contains("$0.00"), true,
					"View Or Print Full Quote Page", "FullPay Future payment "
							+ viewOrPrintFullQuotePage.method.formatDynamicPath(5).getData() + " is displayed",
					false, false);

			// Four pay Down payment Calculations
			double fourPayDownPayment = premiumValue1 * f3 + fees;
			double fourPayDownPaymentdouble = fourPayDownPayment;
			int fourPayDownPaymentLen = viewOrPrintFullQuotePage.method.formatDynamicPath(7).getData().length();
			String fourPayDownPaymentApplnA = viewOrPrintFullQuotePage.method.formatDynamicPath(7).getData()
					.substring(1, fourPayDownPaymentLen);
			String fourPayDownPaymentAppln = fourPayDownPaymentApplnA.replace(",", "").replace("$", "").replace("*",
					"");
			double fourPayDownPaymentAppln1 = Double.parseDouble(fourPayDownPaymentAppln);
			Assertions.verify((fourPayDownPaymentdouble - fourPayDownPaymentAppln1) < 1, true,
					"View Or Print Full Quote Page",
					"4 Pay Down payment " + "$" + fourPayDownPaymentAppln1 + " is displayed", false, false);

			// Four pay Future payment Calculations
			double fourPayFuturePayment = premiumValue1 * f4 + t1;
			double fourPayFuturePaymentdouble = fourPayFuturePayment;
			int fourPayFuturePaymentLen = viewOrPrintFullQuotePage.method.formatDynamicPath(8).getData().length();
			String fourPayFuturePaymentApplnA = viewOrPrintFullQuotePage.method.formatDynamicPath(8).getData()
					.substring(1, fourPayFuturePaymentLen);
			String fourPayFuturePaymentAppln = fourPayFuturePaymentApplnA.replace(",", "").replace("$", "").replace("*",
					"");

			double fourPayFuturePaymentAppln1 = Double.parseDouble(fourPayFuturePaymentAppln);
			Assertions.verify((fourPayFuturePaymentdouble - fourPayFuturePaymentAppln1) < 1, true,
					"View Or Print Full Quote Page",
					"4 Pay Future payment " + "$" + fourPayFuturePaymentAppln1 + " is displayed", false, false);

			// SignOut and Close the browser
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC045 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC045 ", "Executed Successfully");
			}
		}
	}
}