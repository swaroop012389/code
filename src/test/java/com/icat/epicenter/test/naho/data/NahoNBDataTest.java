/** Program Description: Class file to create NB policy
 *  Author			   : SM Netserv
 *  Date of Creation   : 05/09/2018
 **/

package com.icat.epicenter.test.naho.data;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.config.EnvironmentDetails;
import com.NetServAutomationFramework.database.DatabaseConfiguration;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.config.TestType;
import com.icat.epicenter.dom.Policy;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.AbstractDataTest;
import com.icat.epicenter.utils.ExcelReportUtil;

public class NahoNBDataTest extends AbstractDataTest {
	String policyNumber;
	String quoteNumber;
//	FWProperties property;
	String overrideFactor;
	String aalValue;
	double elr;
	BigDecimal elrRoundOff;

	private static List<Integer> testStatus;

	public NahoNBDataTest() {
		super(TestType.NAHO, LoginType.USM, "TC_");
	}

	@Override
	protected String getReportName() {
		return "NAHO New Business Data Test";
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/data/NBDataTest.xlsx";
	}

	@Override
	protected int getSetupColumnNumber() {
		return 0;
	}

	/**
	 * There's only one sheet for NAHO.
	 */
	@Override
	protected String getSheetName(String testName, List<Map<String, String>> testSetup) {
		return "NAHO";
	}

	@Override
	protected void execute(List<Map<String, String>> testData) {
		System.out.println("executing test: " + testData.get(0).get("TCID"));

		// NB tests only care about first column of data
		dataTesting(buildTestSetupData(testData), testData.get(0), EnvironmentDetails.getEnvironmentDetails().getAftershockDetails());
	}

	public String dataTesting(Map<String, String> DataTestSetup, Map<String, String> data, DatabaseConfiguration dbConfig) {

		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		BasePageControl page = new BasePageControl();
//		ExcelReport excelReport = new ExcelReport();

		Policy policy = new Policy(dbConfig);

		try {
			String param = EnvironmentDetails.getEnvironmentDetails().getEnvironmentName();
			//TODO: this moved to abstract class.  add more specific "Smoke Test" naming?
//			if (param.equalsIgnoreCase("PN1") || param.equalsIgnoreCase("PN2") || param.equalsIgnoreCase("qa5")) {
////				ReportsManager.startTest("<span class='header'>NAHO Smoke Test - " + data.get("ProductSelection") + "</span>", "");
//				ReportsManager.startTest("NAHO Smoke Test - " + data.get("ProductSelection"), "");
//			} else {
////				ReportsManager.startTest("<span class='header'>NAHO NB Data Test" + "</span>", "");
//				ReportsManager.startTest("NAHO NB Data Test", "");
//			}
			System.out.println(data.get("TCID") + " execution started");

			Assertions.verify(loginPage.signInButton.checkIfElementIsDisplayed(), true, "Login Page",
					"Login page loaded successfully", false, false);
//			loginPage.enterLoginDetails(property.getProperty("Username"), property.getProperty("Password"));
			loginPage.enterLoginDetails(EnvironmentDetails.getEnvironmentDetails().getString("test.login.usm.userName"),
					EnvironmentDetails.getEnvironmentDetails().getString("test.login.usm.password"));

			if (homePage == null) {
				Assertions.failTest("Login Page", "Failed while logging in");
			}
			Assertions.passTest("Login", "Logged in to application successfully");

			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(data, DataTestSetup);
			Assertions.passTest("New Account", "New Account created successfully");

			if (eligibilityPage == null) {
				Assertions.failTest("Home Page", "Failed while creating New Account");
			}

			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(data);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			if (dwellingPage == null) {
				Assertions.failTest("Eligibility Page", "Failed while entering Zipcode");
			}

			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(data);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			if (priorLossesPage == null) {
				Assertions.failTest("Dwelling Page", "Failed while entering Dwelling details");
			}

			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(data);
			Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");

			if (createQuotePage == null) {
				Assertions.failTest("Prior Loss Page", "Failed while entering Prior Loss details");
			}

			Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
			createQuotePage.enterQuoteDetailsNAHO(data);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			if (createQuotePage.override.checkIfElementIsPresent()
					&& createQuotePage.override.checkIfElementIsDisplayed()) {
				createQuotePage.override.scrollToElement();
				createQuotePage.override.click();
			}

			if (accountOverviewPage == null) {
				Assertions.failTest("Create Quote Page", "Failed while entering Quote Details");
			}
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			if (param.equalsIgnoreCase("PN1") || param.equalsIgnoreCase("PN2") || param.equalsIgnoreCase("qa5")) {
				Assertions.verify(accountOverviewPage.pageName.checkIfElementIsDisplayed(), true,
						"Account Overview Page", "Account Name present ", true, true);
				Assertions.verify(accountOverviewPage.totalPremiumValue.checkIfElementIsDisplayed(), true,
						"Account Overview Page", "Total Premium and Fee Amount present ", true, true);
				Assertions.verify(accountOverviewPage.producerNumber.checkIfElementIsDisplayed(), true,
						"Account Overview Page", "Producer Number present ", true, true);
				Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
						"Account Overview Page", "Create Another Quote present ", true, true);
				Assertions.verify(accountOverviewPage.editDeductibleAndLimits.checkIfElementIsDisplayed(), true,
						"Account Overview Page", "Edit Deductibles and limits button present ", true, true);
				Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
						"Account Overview Page", "Bind button present ", true, true);
				Assertions.verify(accountOverviewPage.quoteStatus.formatDynamicPath(1).checkIfElementIsDisplayed(),
						true, "Account Overview Page", "Quote Status present ", true, true);
				Assertions.verify(accountOverviewPage.viewPrintFullQuoteLink.checkIfElementIsDisplayed(), true,
						"Account Overview Page", "View/Print Full Quote Link present ", true, true);
				Assertions.verify(accountOverviewPage.emailQuoteLink.checkIfElementIsDisplayed(), true,
						"Account Overview Page", "Email Quote Link present ", true, true);
				Assertions.verify(accountOverviewPage.overridePremiumLink.checkIfElementIsDisplayed(), true,
						"Account Overview Page", "Override Premium Link present ", true, true);
				Assertions.verify(accountOverviewPage.viewModelResultsLink.checkIfElementIsDisplayed(), true,
						"Account Overview Page", "View Modelling Results Link present ", true, true);
				testStatus = Assertions.verify(accountOverviewPage.viewOrPrintRateTrace.checkIfElementIsDisplayed(),
						true, "Account Overview Page", "View/Print Rate Trace Link present ", true, true);
				quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
				Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);
				accountOverviewPage.deleteAccount.scrollToElement();
				accountOverviewPage.deleteAccount.click();
				accountOverviewPage.yesDeleteAccount.waitTillVisibilityOfElement(60);
				accountOverviewPage.yesDeleteAccount.scrollToElement();
				accountOverviewPage.yesDeleteAccount.click();
				accountOverviewPage.yesDeleteAccount.waitTillInVisibilityOfElement(60);
				Assertions.passTest("Account Overview Page", "Account deleted successfully");
			}
			if (accountOverviewPage.requestBind.checkIfElementIsPresent()
					&& accountOverviewPage.requestBind.checkIfElementIsDisplayed()) {
				if (!data.get("InspectionFeeOverride").equals("") || !data.get("PolicyFeeOverride").equals("")
						|| !data.get("WindPremiumOverride").equals("") || !data.get("AOPPremiumOverride").equals("")
						|| !data.get("LiabilityPremiumOverride").equals("")) {
					Assertions.verify(overridePremiumAndFeesPage.overrideFeesButton.checkIfElementIsDisplayed(), true,
							"Override Premium and Fees Page", "Override Premium and Fees Page loaded successfully",
							false, false);
					accountOverviewPage.overridePremiumLink.scrollToElement();
					accountOverviewPage.overridePremiumLink.click();
//					overridePremiumAndFeesPage.enterFeesDetails(data);
					overridePremiumAndFeesPage.enterOverrideFeesDetails(data);
					Assertions.passTest("Override Premium and Fees Page", "Fees details updated successfully");
				}
				if (accountOverviewPage.quoteNumber.formatDynamicPath(1).checkIfElementIsPresent()
						&& accountOverviewPage.quoteNumber.formatDynamicPath(1).checkIfElementIsDisplayed()) {
					quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
					Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);
				}

			if (accountOverviewPage.overrideFactor.checkIfElementIsPresent()
					&& accountOverviewPage.overrideFactor.checkIfElementIsDisplayed()) {
				overrideFactor = accountOverviewPage.overrideFactor.getData();
				Assertions.passTest("Account Overview Page",
						"Override Factor :  " + accountOverviewPage.overrideFactor.getData());
			}
	/*		if (accountOverviewPage.viewModelResultsLink.checkIfElementIsPresent()
					&& accountOverviewPage.viewModelResultsLink.checkIfElementIsDisplayed()) {
				accountOverviewPage.viewModelResultsLink.scrollToElement();
				accountOverviewPage.viewModelResultsLink.click();
				aalValue = rmsPage.guyCarpenterAAL.getData();
				elr = Double.parseDouble(rmsPage.guyCarpenterELR.getData().replace("%", "").replace(",", "")) / 100;

				elrRoundOff = new BigDecimal(elr);
				elrRoundOff = elrRoundOff.setScale(3, RoundingMode.HALF_UP);

				rmsPage.closeButton.scrollToElement();
				rmsPage.closeButton.click();
			} */

			accountOverviewPage.clickOnRequestBind(data, quoteNumber);

			if (underwritingQuestionsPage == null) {
				Assertions.failTest("Account Overview Page", "Failed while clicking on Request Bind button");
			}
			Assertions.verify(underwritingQuestionsPage.saveButton.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);

				underwritingQuestionsPage.enterUnderwritingQuestionsDetails(data);
				Assertions.passTest("Underwriting Questions Page",
						"Underwriting Questions details entered successfully");

			if (requestBindPage == null) {
				Assertions.failTest("Underwriting Questions Page",
						"Failed while entering Underwriting Questions details");
			}
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			page = requestBindPage.enterBindDetails(data);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			if (page.getClass().getSimpleName().equalsIgnoreCase("BindRequestSubmittedPage")) {
				bindRequestPage = (BindRequestSubmittedPage) page;
				Assertions.passTest("Bind Request Page", "Bind Request page loaded successfully");
				homePage = bindRequestPage.clickOnHomepagebutton();
				Assertions.passTest("Home Page", "Clicked on Quote Number");
				referralPage = homePage.searchReferral(quoteNumber);
				Assertions.passTest("Home Page", "Searched Submitted Bind Request successfullly");
				if (referralPage == null) {
					Assertions.failTest("Referral Page", "Failed to load Referral Page");
				}
				Assertions.passTest("Referral Page", "Referral page loaded successfully");
				requestBindPage = referralPage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");
				if (requestBindPage == null) {
					Assertions.failTest("Request Bind Page", "Failed to load Request Bind Page");
				}
//				policySummaryPage = requestBindPage.approveRequest(data);
				policySummaryPage = requestBindPage.approveRequest();
				Assertions.passTest("Request Bind Page", "Bind Request approved successfully");
				policyNumber = policySummaryPage.getPolicynumber();
				Assertions.passTest("Policy Summary", "Policy Number is : " + policyNumber);
			}
			if (page.getClass().getSimpleName().equalsIgnoreCase("PolicySummaryPage")) {
				policySummaryPage = (PolicySummaryPage) page;
				policyNumber = policySummaryPage.getPolicynumber();
				Assertions.passTest("Policy Summary", "Policy Number is : " + policyNumber);
			}
			testStatus = Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "Validation of policy number", false, false);
			String expDAte = data.get("PolicyEffDate");
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			Calendar c = Calendar.getInstance();
			try {
				c.setTime(sdf.parse(expDAte));
			} catch (ParseException e) {
				e.printStackTrace();
			}

				// Incrementing the date
				c.add(Calendar.YEAR, 1);
				String newDate = sdf.format(c.getTime());
				/*if (DataTestSetup.get("GetTransactionID").equalsIgnoreCase("yes")) {
					ExcelReportUtil.setNB_ExeReport(DataTestSetup.get("SheetName"), data.get("TCID"),
							data.get("InsuredName"), quoteNumber, policyNumber, data.get("PolicyEffDate"), newDate,
							(String.valueOf(policy.setTransactionID(policyNumber))), aalValue, elrRoundOff + "");
				} else {*/
					ExcelReportUtil.getInstance().addRow(data.get("TCID"),
							data.get("InsuredName"), quoteNumber, policyNumber, data.get("PolicyEffDate"), newDate,
							"GetTransactionID is no in setup sheet", aalValue, elrRoundOff + "");
				//}
				System.out.println(data.get("TCID") + policyNumber);
			}
		} catch (Exception e) {
			Assertions.exceptionError("Error in DataTesting method", e.toString());
		}
		if (testStatus.get(1) > 0) {
			return "FAIL";
		} else {
			return "PASS";
		}
	}
}
