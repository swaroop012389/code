/** Program Description: Execution of PNB DataTest test cases based on the Data
 *  Author			   : SM Netserv
 *  Date of Creation   : 05/09/2018
 **/
//check checkin
package com.icat.epicenter.test.commercial.data;

import static com.icat.epicenter.test.commercial.data.CommercialPNBDataTest.KillChromeDriver.killChromeDriver;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.NetServAutomationFramework.config.EnvironmentDetails;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.DBFrameworkConnection;
import com.NetServAutomationFramework.generic.QueryBuilder;
import com.NetServAutomationFramework.generic.WebDriverManager;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.config.TestType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.AdminPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicyDocumentsPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.ReinstatePolicyPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.AbstractDataTest;
import com.icat.epicenter.utils.ExcelReportUtil;

public class CommercialPNBDataTest extends AbstractDataTest {
	Map<String, String> testData;
	private static final int TIME_OUT_SIXTY_SECS = 60;
	private static List<Integer> testStatus;

	public CommercialPNBDataTest() {
		super(TestType.COMMERCIAL, LoginType.USM, "NBTC");
	}

	@Override
	protected String getReportName() {
		return "Commercial Post-NB Data Tests";
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/data/CommercialDataTestMaster.xls";
	}

	@Override
	protected int getSetupColumnNumber() {
		return 1;
	}

	/**
	 * Sheet name is on the first page of the data spreadsheet.
	 */
	@Override
	protected String getSheetName(String testName, List<Map<String, String>> testSetup) {
		return testSetup.get(3).get(testName);
	}


	@Override
	protected void execute(List<Map<String, String>> testData) {
		String tcid = testData.get(0).get("TCID");
		String policyNumber = ExcelReportUtil.getInstance().getLatestPolicyFromMaster(tcid + "_Trx1");

		WebDriverManager.launchBrowser();
		pnbDataTesting(buildTestSetupData(testData), tcid, policyNumber, testData);
	}

	public List<Integer> pnbDataTesting(Map<String, String> dataTestSetup, String tcid, String policyNumber, List<Map<String, String>> data) {
		// try {
		LoginPage login = new LoginPage();
		HomePage homePage = new HomePage();
		AdminPage adminPage = new AdminPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		BuildingPage buildingPage = new BuildingPage();
		LocationPage locationPage = new LocationPage();
		PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ReinstatePolicyPage reinstatePolicyPage = new ReinstatePolicyPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuotablePage = new BuildingNoLongerQuoteablePage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		BasePageControl page = new BasePageControl();

		List rolledBackTxns = new ArrayList();

		//add cookies
		page.addCookie("NO_SUGGEST_LOCATION", "true");
		page.addCookie("noGoogle", "true");
		page.addCookie("hideBindCelebration", "true");
		page.addCookie("hideNotesBar", "true");

		Assertions.verify(login.signInButton.checkIfElementIsDisplayed(), true, "Login Page",
				"Login page loaded successfully", false, false);
		login.enterLoginDetails(dataTestSetup.get("UserName"), dataTestSetup.get("Password"));

		Assertions.passTest("Login", "Logged in to application successfully");

		Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
				"Home page loaded successfully", false, false);
		//get the rating effective date from the admin
		homePage.adminLink.click();
		Assertions.verify(adminPage.ratingEffectiveDate.checkIfElementIsDisplayed(), true, "Admin Page",
				"Admin Page loaded successfully", false, false);
		String epi_RatingEffectiveDate = adminPage.ratingEffectiveDate.getData().replace("\nRefresh", "");
		if (epi_RatingEffectiveDate.equalsIgnoreCase("not set")){
			LocalDate today = LocalDate.now();
			epi_RatingEffectiveDate = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US).format(today);
		}

		homePage.goToHomepage.click();

		// entering policy number in find policy field
		homePage.searchPolicy(policyNumber);
		Assertions.passTest("Home Page", "Secondary Transactions for Policy : " + policyNumber + " Started");

		//process secondary transactions, skip new business, out-of-sequence reversals, cancels for rewrites,  and pre-existing transactions
		for (int i = 1; i < data.size(); i++) {
			testData = data.get(i);
			if (testData.get("TransactionType").contains("New Business") || testData.get("TransactionType").equals("OOS Reversal") || testData.get("TransactionType").equals("CXLRW") || testData.get("ExistingTransaction").toUpperCase().equals("YES"))
				continue;
			String transactionType = null;
			transactionType = testData.get("TransactionType");

			Assertions.passTest("Policy Summary Page", "Transaction " + (i) + " : " + transactionType + " Started");
			if (testData.get("TransactionType").contains("Rewrite Policy")) {

				processRewrite(dataTestSetup, tcid, policyNumber, data);
			}

			if (testData.get("TransactionType").contains("Renewal")) {
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();

				processRenewal(dataTestSetup, tcid, policyNumber, data);

				// getting the Renewal policy number
				Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
						"Policy Summary page loaded successfully", false, false);
				policyNumber = policySummaryPage.getPolicynumber();

				Assertions.passTest("Policy Summary Page", "Renewal Policy Number is : " + policyNumber);

			}
			if (testData.get("TransactionType").equalsIgnoreCase("Reverse Previous Endorsement")) {
				policySummaryPage.reversePreviousEndorsementLink.scrollToElement();
				policySummaryPage.reversePreviousEndorsementLink.click();
				endorsePolicyPage.completeButton.scrollToElement();
				endorsePolicyPage.completeButton.click();
				endorsePolicyPage.completeButton.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
				endorsePolicyPage.closeButton.scrollToElement();
				endorsePolicyPage.closeButton.click();
				endorsePolicyPage.closeButton.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
				//give the transaction a few seconds to transfer to aftershock so the next one can be run
				waitTime(5);
			}
			if (testData.get("TransactionType").equalsIgnoreCase("Premium Bearing Endorsement")
					|| testData.get("TransactionType").equalsIgnoreCase("OOS Premium Bearing Endorsement") || testData
					.get("TransactionType").equalsIgnoreCase("Premium Bearing Endorsement(Rolled Forward)")) {
				policySummaryPage.endorsePB.scrollToElement();
				policySummaryPage.endorsePB.click();
				policySummaryPage.endorsePB.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
				endorsePolicyPage.enterEndorsement_Details(testData);
				//give the transaction a few seconds to transfer to aftershock so the next one can be run
				waitTime(5);
			}
			if (testData.get("TransactionType").equalsIgnoreCase("Non-Premium Bearing Endorsement")
					|| testData.get("TransactionType").equalsIgnoreCase("OOS Non-Premium Bearing Endorsement")
					|| testData.get("TransactionType")
					.equalsIgnoreCase("Non-Premium Bearing Endorsement(Rolled Forward)")) {
				policySummaryPage.endorseNPB.scrollToElement();
				policySummaryPage.endorseNPB.click();
				policySummaryPage.endorseNPB.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
				endorsePolicyPage.enterEndorsement_Details(testData);
				//give the transaction a few seconds to transfer to aftershock so the next one can be run
				waitTime(5);
			}
			if (testData.get("TransactionType").equalsIgnoreCase("Cancellation")
					|| testData.get("TransactionType").equalsIgnoreCase("Cancellation(Rolled Forward)")) {
				policySummaryPage.cancelPolicy.waitTillButtonIsClickable(TIME_OUT_SIXTY_SECS);
				policySummaryPage.cancelPolicy.scrollToElement();
				policySummaryPage.cancelPolicy.click();
				cancelPolicyPage.enterCancellationDetails(testData);
				//give the transaction a few seconds to transfer to aftershock so the next one can be run
				waitTime(8);
			}
			if (testData.get("TransactionType").equalsIgnoreCase("Reinstatement")
					|| testData.get("TransactionType").equalsIgnoreCase("Reinstatement(Rolled Forward)")) {
				policySummaryPage.reinstatePolicy.click();
				reinstatePolicyPage.enterReinstatePolicyDetails(testData);
				//give the transaction a few seconds to transfer to aftershock so the next one can be run
				waitTime(5);
			}

			Assertions.passTest("Policy Summary Page", "Transaction " + (i) + " : " + transactionType + " Ended");
			policyNumber = policySummaryPage.getPolicynumber();
			testStatus = Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "Validation of policy number", false, false);
		}

		//get the data for the NB_DT_ExeReport.xls spreadsheet
		//for renewals or rewrites, start with the transactions on the original policy, then come back to the renewal or rewrite
		String rewritePolicyNumber = "";
		if (policySummaryPage.transHistTable.getData().contains("RENEWAL")) {
			policySummaryPage.previousPolicyNumber.click();
		} else if (policySummaryPage.transHistTable.getData().contains("REWR")) {
			//there's a bug where sometimes the rewrite policy number isn't shown on the old policy summary page, get it for cases where it's missing
			rewritePolicyNumber =  policySummaryPage.getPolicynumber();
			policySummaryPage.rewrittenPolicyNumber.click();
		}

		int originalPolTxns = 0;
		for (int j = 2; j <= data.size(); j++) {
			testData = data.get(j-1);

			if (testData.get("TransactionType").equalsIgnoreCase("Renewal")) {
				policySummaryPage.viewActiveRenewal.click();
				originalPolTxns = j-1;
			} else if (testData.get("TransactionType").equalsIgnoreCase("Rewrite Policy")) {
				//should just be able to click Rewritten policy number link, but sometimes it's not there
				if (policySummaryPage.rewrittenPolicyNumberTo.checkIfElementIsPresent() && policySummaryPage.rewrittenPolicyNumberTo.checkIfElementIsEnabled()) {
					policySummaryPage.rewrittenPolicyNumberTo.click();
				} else {
					homePage.goToHomepage.click();
					homePage.searchPolicy(rewritePolicyNumber);
				}
				originalPolTxns = j-1;
			}

			//click on the transaction row
			if (originalPolTxns == 0) {
				policySummaryPage.transHistNum.formatDynamicPath(j + 1).scrollToElement();
				policySummaryPage.transHistNum.formatDynamicPath(j + 1).click();
			} else {
				int txnToClick = j-originalPolTxns;
				System.out.println("j = " + j);
				System.out.println("txnToClick = " + txnToClick);
				policySummaryPage.transHistNum.formatDynamicPath(txnToClick + 1).scrollToElement();
				policySummaryPage.transHistNum.formatDynamicPath(txnToClick + 1).click();
			}

			String insName = policySummaryPage.insuredName.getData();
			String quoteNumber = policySummaryPage.quoteNumText.getData().substring(0,10);
			policyNumber = policySummaryPage.getPolicynumber();
			String effDate = policySummaryPage.effectiveDate.formatDynamicPath(1).getData().substring(0,10);
			String expDate = policySummaryPage.expirationDate.formatDynamicPath(1).getData().substring(0,10);
			String txnDate;
			if (testData.get("TransactionType").equalsIgnoreCase("New Business") || testData.get("TransactionType").equalsIgnoreCase("Renewal")) {
				txnDate = effDate;
			} else if (testData.get("TransactionType").equalsIgnoreCase("Cancellation")) {
				txnDate = testData.get("CancellationEffectiveDate");
			} else {
				txnDate = testData.get("TransactionEffectiveDate");
			}

			LocalDate epiProcessingDate = LocalDate.parse(policySummaryPage.processingDate.formatDynamicPath(1).getData().substring(0,8), DateTimeFormatter.ofPattern("MM/dd/yy"));
			String processDate = epiProcessingDate.format(DateTimeFormatter.ofPattern("MM/dd/YYYY"));
			LocalDate epiBinderDate = LocalDate.parse(policySummaryPage.processingDate.formatDynamicPath(1).getData().substring(0,8), DateTimeFormatter.ofPattern("MM/dd/yy"));
			String binderDate = epiBinderDate.format(DateTimeFormatter.ofPattern("MM/dd/YYYY"));
			String inspFee = policySummaryPage.termInspectionFee.formatDynamicPath(1).getData().replace("$", "").replace(",", "").replace("N/A", "0");
			String polFee = policySummaryPage.termPolicyFee.formatDynamicPath(1).getData().replace("$", "").replace(",", "").replace("N/A", "0");

			String surplusContribution = "0.00";
			if (policySummaryPage.termSurplusContribution.checkIfElementIsPresent()) {
				surplusContribution = policySummaryPage.termSurplusContribution.formatDynamicPath(1).getData().replace("$", "").replace(",", "");
			}
			String taxesAndFees = "0.00";
			if (policySummaryPage.termTaxesAndStateFees.checkIfElementIsPresent() && policySummaryPage.termTaxesAndStateFees.checkIfElementIsDisplayed()) {
				taxesAndFees = policySummaryPage.termTaxesAndStateFees.formatDynamicPath(1).getData().replace("$", "").replace(",", "");
			}

			Double feesTotal = Double.valueOf(inspFee) + Double.valueOf(polFee) + Double.valueOf(surplusContribution) + Double.valueOf(taxesAndFees);
			Double policyTotal = Double.valueOf(policySummaryPage.termTotal.formatDynamicPath(1).getData().replace("$", "").replace(",", ""));

			Double txnPolFee = 0.00;
			if (!policySummaryPage.transactionPolicyFee.formatDynamicPath(1).getData().equals("N/A")) {
				txnPolFee = Double.valueOf(policySummaryPage.transactionPolicyFee.formatDynamicPath(1).getData().replace("$", "").replace(",", ""));
			}

			Double txnInspFee = 0.00;
			if (!policySummaryPage.transactionInspectionFee.formatDynamicPath(1).getData().equals("N/A")) {
				txnInspFee = Double.valueOf(policySummaryPage.transactionInspectionFee.formatDynamicPath(1).getData().replace("$", "").replace(",", ""));
			}

			Double txnPremium = 0.00;
			if (!policySummaryPage.transactionPremium.formatDynamicPath(1).getData().equals(("N/A"))) {
				txnPremium = Double.valueOf(policySummaryPage.transactionPremium.formatDynamicPath(1).getData().replace("$", "").replace(",", ""));
			}

			Double termPremium = 0.00;
			if (!policySummaryPage.termPremium.formatDynamicPath(1).getData().equals("N/A")) {
				termPremium = Double.valueOf(policySummaryPage.termPremium.formatDynamicPath(1).getData().replace("$", "").replace(",", ""));
			}
			String proRataFactorString = String.valueOf(policySummaryPage.proRataFactor.formatDynamicPath(1).getData());
			String proRataFactor;
			if (proRataFactorString.equals("N/A")) {
				//impossible value
				proRataFactor = "9.999";
			}
			else {
				proRataFactor = policySummaryPage.proRataFactor.formatDynamicPath(1).getData().substring(0,5);
			}

			//this is not in the front end - calculated by dividing the annual transaction premium by the proRata factor.
			//it's often not exact, so only fail if it's over 10% when checked
			Double annualTxnPremium;
			if (proRataFactor != "9.999") {
				annualTxnPremium = txnPremium / Double.valueOf(proRataFactor);
			} else {
				annualTxnPremium = txnPremium;
			}

			//if cancellation and not on NOC, annualPolPremium is 0 - NOC shows up in the docs links - need to check txn type in the table
			Double annualPolPremium;
			if (testData.get("TransactionType").equalsIgnoreCase("Cancellation") && !policySummaryPage.transHistTable.getData().contains("NOC")) {
				annualPolPremium = Double.valueOf("0");
			} else {
				annualPolPremium = Double.valueOf(policySummaryPage.annualPremium.formatDynamicPath(1).getData().replace("$", "").replace(",", ""));
			}

			String previousPolicyNum;
			//get previous policy number for renewals and for rewrites
			if (testData.get("Renewal?").equalsIgnoreCase("yes")) {
				previousPolicyNum = policySummaryPage.previousPolicyNumber.formatDynamicPath(1).getData();
			} else {
				previousPolicyNum = "";
			}

			String txnId;
			if (dataTestSetup.get("GetTransactionID").equalsIgnoreCase("yes")) {
				//TF - wait for the transaction to make it to aftershock and then get the transactionId
				waitTime(5);
				DBFrameworkConnection connection = new DBFrameworkConnection(EnvironmentDetails.getEnvironmentDetails().getAftershockDetails());
				QueryBuilder build = new QueryBuilder(connection);
				ArrayList<String> outfield = new ArrayList<>();
				outfield.add("TransactionID");
				build.outFields(outfield);
				build.tableName("policy");
				build.whereBy("policyNumber = " + "'" + policyNumber + "'" + "and HVN = " + testData.get("HVN"));
				List<Map<String, Object>> txnIdResult = build.execute(60);
				txnId = txnIdResult.get(0).get("TransactionID").toString();

			} else {
				txnId = "GetTransactionID is 'No' in setup sheet";
			}

			//write to CommDataExeReport.xls
			ExcelReportUtil.getInstance().addRow(testData.get("TCID") + "_Trx" + testData.get("TransactionNum"), insName,
					quoteNumber, policyNumber, epi_RatingEffectiveDate, effDate, expDate, txnDate, processDate, binderDate, inspFee, polFee,
					String.valueOf(feesTotal), String.valueOf(termPremium), String.valueOf(policyTotal),
					txnPolFee.toString(), txnInspFee.toString(), String.valueOf(txnPremium), proRataFactor, String.valueOf(annualTxnPremium),
					String.valueOf(annualPolPremium), txnId, testData.get("ProductType"), taxesAndFees, previousPolicyNum);
		}
		System.out.println(tcid + " execution ended");
		Assertions.passTest("Home Page", "Secondary Transactions Ended");

		//kill chromedriver.exe processes
		killChromeDriver();

		/* } catch (Exception e) {
		  Assertions.exceptionError("Error in PNBDataTesting method",
		  e.toString());
		  }*/
		return testStatus;

	}

	//method to kill the chromedriver.exe processes at the end of the tests
	public class KillChromeDriver {
		public static void main(String[] args) {
			killChromeDriver();
		}

		public static void killChromeDriver() {
			try {
				ProcessBuilder processBuilder = new ProcessBuilder("taskkill", "/F", "/IM", "chromedriver.exe");
				Process process = processBuilder.start();
				process.waitFor(); // Wait for the process to complete
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	//TODO - TF 09/30/2024 - I should eventually move this to a different file, for now, adding it here because it makes following what's going on in the framework easier
	public void processRenewal(Map<String, String> dataTestSetup, String tcid, String policyNumber, List<Map<String, String>> data) {
		HomePage homePage = new HomePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		BuildingPage buildingPage = new BuildingPage();
		LocationPage locationPage = new LocationPage();
		PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuotablePage = new BuildingNoLongerQuoteablePage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		BasePageControl page = new BasePageControl();

		if (policySummaryPage.expaacMessage.checkIfElementIsPresent() && policySummaryPage.expaacMessage.checkIfElementIsDisplayed()) {
			Assertions.verify(policySummaryPage.expaacMessage.checkIfElementIsDisplayed(), true, "Policy Renewal Page",
					policySummaryPage.expaacMessage.getData() + " Message verified", false, false);

			policySummaryPage.addExpaccInfo.click();
			// entering expaac data
			Assertions.verify(expaccInfoPage.policyNumber.checkIfElementIsDisplayed(), true, "Expacc Info Page",
					"Update Expaac Data page loaded successfully", false, false);
			expaccInfoPage.enterExpaccInfo(testData, policyNumber);
			Assertions.passTest("Expacc Info Page", "Expaac Data updated successfully");



			// Click on Renew Policy Hyperlink
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy Link");
		}

		//use building values set by the test instead of minimum valuation
		//cycle through the list of elements or spend a day figuring something else out, xpaths just wouldn't work after the first dropdown
		if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()) {
			String elementClass = "selectboxit-text";
			List<WebElement> elements = WebDriverManager.getCurrentDriver().findElements(By.className(elementClass));

			for (WebElement ele : elements) {
				ele.click();
				waitTime(1);
				WebElement option = WebDriverManager.getCurrentDriver().findElement(By.partialLinkText("Leave"));
				option.click();
				waitTime(1);
			}
		}

		page.scrollToBottomPage();
		policyRenewalPage.continueRenewal.click();
		Assertions.passTest("Renewal Building Review Page", "Clicked on Continue");

		//click on Ok button if warned that the building value is lower than minimum valuation
		if (policyRenewalPage.buildingValueOk.checkIfElementIsPresent()) {
			policyRenewalPage.buildingValueOk.click();
		}

		//click on yes button
		if (policyRenewalPage.renewalYes.checkIfElementIsDisplayed()) {
			policyRenewalPage.renewalYes.scrollToElement();
			policyRenewalPage.renewalYes.click();
		}

		if (testData.get("ChangeLocationOrBuildingDetails").equalsIgnoreCase("Yes")) {
			locationPage.enterEndorsementLocationDetails(testData);
			buildingPage.enterEndorsementBuildingDetails(testData);
		}

		Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Account Overview Page loaded successfully", false, false);
		accountOverviewPage.createAnotherQuote.scrollToElement();
		accountOverviewPage.createAnotherQuote.click();

		if (buildingNoLongerQuotablePage.pageName.getData().contains("Buildings No")) {
			buildingNoLongerQuotablePage.overrideNoLongerQuotableBuildings();
		}

		if (!testData.get("Peril").equals("EQ")) {
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page", "Select Peril page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));
			Assertions.passTest("Select Peril Page", "Peril selected successfully");
		}

		Assertions.passTest("Account Overview Page", "Clicked on Create Another Quote");
		createQuotePage.enterQuoteDetailsCommercial(testData);

		// clicking on release renewal to producer
		accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
		accountOverviewPage.releaseRenewalToProducerButton.click();


		Assertions.passTest("Account Overview Page", "Account Overview page loaded successfully");

		// getting the quote number
		String quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
		Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

		//for retail, need to click "issue quote"
		if (accountOverviewPage.issueQuoteButton.checkIfElementIsPresent()){
			accountOverviewPage.issueQuoteButton.click();
		}

		accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

		//pre-bind docs - do we need them for renewals?
		if (policyDocumentsPage.addDocumentButton.checkIfElementIsPresent()) {
			Assertions.verify(policyDocumentsPage.addDocumentButton.checkIfElementIsDisplayed(), true, "Policy Documents Page",
					"Policy Documents Page loaded successfully", false, false);
			policyDocumentsPage.addDocumentButton.click();
			Assertions.verify(policyDocumentsPage.uploadButton.checkIfElementIsDisplayed(), true, "Upload Document Overlay",
					"Upload Document Overlay loaded successfully", false, false);
			policyDocumentsPage.fileUpload("subscription_dummy.txt");
			policyDocumentsPage.backButton.waitTillVisibilityOfElement(5);
			policyDocumentsPage.waitTime(10);
			policyDocumentsPage.backButton.click();
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		}

		// entering details in request bind page
		Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
				"Request Bind Page loaded successfully", false, false);
		requestBindPage.addContactInformation(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");

		requestBindPage.submit.scrollToElement();
		requestBindPage.submit.click();

		waitTime(15);

		requestBindPage.confirmBind();

		if (bindRequestPage.pageName.getData().equals("Bind Request Submitted")) {
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home Page", "Clicked on Home button");

			referralPage = homePage.searchReferral(quoteNumber);
			Assertions.passTest("Home Page", "Searched Submitted Renewal Bind Request successfullly");

			Assertions.passTest("Referral Page", "Referral page loaded successfully");
			requestBindPage = referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Renewal Referral request approved successfully");

			requestBindPage.approveRequestCommercialData(testData);
			Assertions.passTest("Request Bind Page", "Renewal Bind Request approved successfully");
		}

	}

	public void processRewrite(Map<String, String> dataTestSetup, String tcid, String policyNumber, List<Map<String, String>> data) {
		HomePage homePage = new HomePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		BuildingPage buildingPage = new BuildingPage();
		LocationPage locationPage = new LocationPage();
		PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuotablePage = new BuildingNoLongerQuoteablePage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		BasePageControl page = new BasePageControl();

		policySummaryPage.rewritePolicy.scrollToElement();
		policySummaryPage.rewritePolicy.click();

		accountOverviewPage.editBuilding.waitTillPresenceOfElement(20);

		if (testData.get("ChangeLocationOrBuildingDetails").equalsIgnoreCase("Yes")) {
			System.out.println("about to change buildings");
			accountOverviewPage.editLocation.click();
			locationPage.locationName.waitTillVisibilityOfElement(8);

			locationPage.enterEndorsementLocationDetails(testData);
			buildingPage.enterEndorsementBuildingDetails(testData);
		}

		if (testData.get("ChangeCoverageOptions").equalsIgnoreCase("Yes")) {
			System.out.println("about to change coverages");

			if (buildingPage.createQuote.checkIfElementIsPresent() && buildingPage.createQuote.checkIfElementIsDisplayed()) {
				buildingPage.createQuote.scrollToElement();
				buildingPage.createQuote.click();
			} else if (accountOverviewPage.createAnotherQuote.checkIfElementIsPresent() && accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed()) {
				accountOverviewPage.createAnotherQuote.click();
			}

			System.out.println("Bldg not quoteable #1 override present? " + buildingNoLongerQuotablePage.override.checkIfElementIsPresent());
			if (buildingNoLongerQuotablePage.override.checkIfElementIsPresent() && buildingNoLongerQuotablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuotablePage.override.click();
			}

			//select peril, if necessary
			if (!testData.get("Peril").equals("EQ")) {
				selectPerilPage.selectPeril(testData.get("Peril"));
				selectPerilPage.allOtherPeril.waitTillInVisibilityOfElement(8);
			}


			System.out.println("Bldg not quoteable #2 override present? " + buildingNoLongerQuotablePage.override.checkIfElementIsPresent());
			if (buildingNoLongerQuotablePage.override.checkIfElementIsPresent() && buildingNoLongerQuotablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuotablePage.override.click();
			}

			Assertions.passTest("Account Overview Page", "Clicked on Create Another Quote");
			createQuotePage.enterQuoteDetailsCommercial(testData);
		}

		accountOverviewPage.rewriteBind.scrollToElement();
		accountOverviewPage.rewriteBind.click();

		requestBindPage.enteringRewriteData(testData);
		Assertions.passTest("Request Bind Page", "Entered Rewrite Data");

		if (requestBindPage.backdatingRewrite.checkIfElementIsPresent() && requestBindPage.backdatingRewrite.checkIfElementIsDisplayed()) {
			System.out.println("In Approve Backdating");
			requestBindPage.backdatingRewrite.moveToElement();
			requestBindPage.backdatingRewrite.scrollToElement();
			requestBindPage.backdatingRewrite.click();
			waitTime(2);
		}

		// Getting Policy Number
		Assertions.passTest("Policy Summary Page", "Policy Summary page loaded successfully");
		Assertions.passTest("Policy Summary Page","Rewritten policy Number : " + policySummaryPage.getPolicynumber());
	}

}
