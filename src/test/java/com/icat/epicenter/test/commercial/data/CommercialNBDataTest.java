/** Program Description: Class file to create NB policy
 *  Author			   : SM Netserv
 *  Date of Creation   : 05/09/2018
 **/
//check checkin
package com.icat.epicenter.test.commercial.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.GLInformationPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicyDocumentsPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.AbstractDataTest;
import com.icat.epicenter.utils.ExcelReportUtil;

public class CommercialNBDataTest extends AbstractDataTest {
	String policyNumber;
	String quoteNumber;
	private static List<Integer> testStatus;
	public CommercialNBDataTest() {
		super(TestType.COMMERCIAL, LoginType.USM, "NBTC");
	}

	@Override
	protected String getReportName() {
		return "Commercial New Business Data Test";
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/data/CommercialDataTestMaster.xls";
	}

	@Override
	protected int getSetupColumnNumber() {
		return 0;
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
		System.out.println("executing test: " + testData.get(0).get("TCID"));

		WebDriverManager.launchBrowser();
		dataTesting(buildTestSetupData(testData), testData.get(0)); // NB tests only care about first column of data
	}

	public List<Integer> dataTesting(Map<String, String> dataTestSetup, Map<String, String> data) {
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		AdminPage adminPage = new AdminPage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		GLInformationPage glInformationPage = new GLInformationPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
		OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		BasePageControl page = new BasePageControl();

		try {
			//add cookies
			page.addCookie("NO_SUGGEST_LOCATION", "true");
			page.addCookie("noGoogle", "true");
			page.addCookie("hideBindCelebration", "true");
			page.addCookie("hideNotesBar", "true");

			Assertions.verify(loginPage.signInButton.checkIfElementIsDisplayed(), true, "Login Page",
					"Login page loaded successfully", false, false);
			loginPage.enterLoginDetails(dataTestSetup.get("UserName"), dataTestSetup.get("Password"));

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

	        if (data.get("ProductType").equalsIgnoreCase("retail")) {
	            dataTestSetup.replace("ProducerNumber", "14048.1");
	        } else {
	            dataTestSetup.replace("ProducerNumber", "8521.1");
	        }
			homePage.createNewAccountWithNamedInsured(data, dataTestSetup, true);

			Assertions.passTest("New Account", "New Account created successfully");

			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(data);

			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			Assertions.verify(locationPage.addBuildingsButton.checkIfElementIsDisplayed(),true,"Location Page", "Location Page loaded successfully",false,false);
			locationPage.enterLocationDetails(data);

			Assertions.passTest("Location Page", "Location details entered successfully");

			buildingPage.enterBuildingDetails(data);
			Assertions.passTest("Building Page", "Building details entered successfully");

			//TF 02/23/2021 - may have trouble here if forced to wind-only for ineligible AOP buildings
			if (!data.get("Peril").equals("EQ")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(),true,"Select Peril Page", "Select Peril page loaded successfully",false,false);
				selectPerilPage.selectPeril(data.get("Peril"));
				Assertions.passTest("Select Peril Page", "Peril selected successfully");

				//TF 02/23/2021 - the user is only prompted for prior loss info for AOP and GL perils
				if (data.get("Peril").equals("GL") || data.get("Peril").equals("AOP") ) {
					Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
							"Prior Loss Page loaded successfully", false, false);
					priorLossesPage.selectPriorLossesInformation(data);
					Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");
				}

				if (data.get("Peril").equals("GL")) {
					Assertions.passTest("GL Information Page", "GL Information Page loaded successfully");
					// Entering GL Information
					glInformationPage.enterGLInformation(data);
					Assertions.passTest("GL Information Page", "GL Information details entered successfully");
				}
			}

			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);

			createQuotePage.enterQuoteDetailsCommercial(data);

			Assertions.passTest("Create Quote Page", "Quote details entered successfully");
			Assertions.passTest("Account Overview Page", "Account Overview page loaded successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			if (!data.get("OverridePremium").equals("") || !data.get("TransactionInspectionFee").equals("") || !data.get("TransactionPolicyfee").equals("")) {
				Assertions.verify(overridePremiumAndFeesPage.overrideFeesButton.checkIfElementIsDisplayed(), true,
						"Override Premium and Fees Page", "Override Premium and Fees Page loaded successfully", false,
						false);
				accountOverviewPage.overridePremiumLink.scrollToElement();
				accountOverviewPage.overridePremiumLink.click();
				overridePremiumAndFeesPage.overridePremiumAndFeesDetails(data);
				Assertions.passTest("Override Premium and Fees Page", "Fees details updated successfully");
			}


			//pre-bind docs
			accountOverviewPage.uploadPreBindDocuments.scrollToElement();
			accountOverviewPage.uploadPreBindDocuments.click();
			Assertions.verify(policyDocumentsPage.addDocumentButton.checkIfElementIsDisplayed(), true, "Policy Documents Page",
					"Policy Documents Page loaded successfully", false, false);
			policyDocumentsPage.addDocumentButton.click();
			Assertions.verify(policyDocumentsPage.uploadButton.checkIfElementIsDisplayed(), true, "Upload Document Overlay",
					"Upload Document Overlay loaded successfully", false, false);
			policyDocumentsPage.fileUpload("subscription_dummy.txt");

			//upload due diligence if required
			if (policyDocumentsPage.dueDiligenceRequired.checkIfElementIsPresent()) {
				policyDocumentsPage.addDocumentButton.click();
				policyDocumentsPage.fileUpload("subscription_dummy.txt", "due diligence");
			}

			policyDocumentsPage.continueBindButton.waitTillVisibilityOfElement(5);
			policyDocumentsPage.continueBindButton.waitTillPresenceOfElement(5);
			policyDocumentsPage.continueBindButton.click();

			Assertions.passTest("Request Bind Page", "Request Bind page loaded successfully");
			page = requestBindPage.enterBindDetails(data);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			String expDate = null;
		    String processDate = null;
		    String binderDate = null;
		    String inspFee = null;
		    String polFee = null;
			String surplusContribution = null;
		    String taxesAndFees = null;
		    Double feesTotal = null;
		    Double premiumTotal = null;
		    Double policyTotal = null;
		    String txnPolFee = null;
		    String txnInspFee = null;
		    Double txnPremium = null;
		    String proRataFactor = null;
		    Double annualTxnPremium = null;
		    Double annualPolPremium = null;
			String previousPolNumber = null;

			if (page.getClass().getSimpleName().equalsIgnoreCase("BindRequestSubmittedPage")) {
				Assertions.passTest("Bind Request Page", "Bind Request page loaded successfully");
				bindRequestPage.clickOnHomepagebutton();
				Assertions.passTest("Home Page", "Clicked on Quote Number");

				homePage.searchReferral(quoteNumber);
				Assertions.passTest("Home Page", "Searched Submitted Bind Request successfullly");
				Assertions.passTest("Referral Page", "Referral page loaded successfully");

				referralPage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");
				requestBindPage.externalComments.setData("Aftershock Testing");
				requestBindPage.approveRequestCommercialData(data);
				Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

				policyNumber = policySummaryPage.getPolicynumber();
	            expDate = policySummaryPage.expirationDate.formatDynamicPath(1).getData().substring(0,10);

	            LocalDate epiProcessingDate = LocalDate.parse(policySummaryPage.processingDate.formatDynamicPath(1).getData().substring(0,8), DateTimeFormatter.ofPattern("MM/dd/yy"));
	            processDate = epiProcessingDate.format(DateTimeFormatter.ofPattern("MM/dd/YYYY"));
	            LocalDate epiBinderDate = LocalDate.parse(policySummaryPage.processingDate.formatDynamicPath(1).getData().substring(0,8), DateTimeFormatter.ofPattern("MM/dd/yy"));
	            binderDate = epiBinderDate.format(DateTimeFormatter.ofPattern("MM/dd/YYYY"));
	            inspFee = policySummaryPage.inspectionFee.formatDynamicPath(1).getData().replace("$", "").replace(",", "");
	            polFee = policySummaryPage.policyFee.formatDynamicPath(1).getData().replace("$", "").replace(",", "");

				surplusContribution = "0.0";
				if (policySummaryPage.transactionSurplusContribution.checkIfElementIsPresent()) {
					surplusContribution = policySummaryPage.transactionSurplusContribution.formatDynamicPath(1).getData().replace("$", "").replace(",", "");
				}

				taxesAndFees = "0.0";
	            if (policySummaryPage.txnTaxesAndStateFees.formatDynamicPath(1).checkIfElementIsPresent()) {
	                taxesAndFees = policySummaryPage.txnTaxesAndStateFees.formatDynamicPath(1).getData().replace("$", "").replace(",", "");
	            }
				feesTotal = Double.valueOf(inspFee) + Double.valueOf(polFee) + Double.valueOf(surplusContribution) + Double.valueOf(taxesAndFees);
				premiumTotal = Double.valueOf(policySummaryPage.transactionPremium.formatDynamicPath(1).getData().replace("$", "").replace(",", ""));
				System.out.println("policyTotalPremium = " + policySummaryPage.policyTotalPremium.formatDynamicPath(1).getData());
				policyTotal = Double.valueOf(policySummaryPage.policyTotalPremium.formatDynamicPath(1).getData().replace("$", "").replace(",", ""));
				txnPolFee = policySummaryPage.policyFee.formatDynamicPath(1).getData().replace("$", "").replace(",", "");
				txnInspFee = policySummaryPage.inspectionFee.formatDynamicPath(1).getData().replace("$", "").replace(",", "");
				txnPremium = premiumTotal;
				proRataFactor = policySummaryPage.proRataFactor.formatDynamicPath(1).getData().substring(0,5);
				annualTxnPremium = premiumTotal;
				annualPolPremium = premiumTotal;

				if (policySummaryPage.previousPolicyNumber.checkIfElementIsPresent()) {
					previousPolNumber = policySummaryPage.previousPolicyNumber.getData();
				} else {
					previousPolNumber = "";
				}

	            //createDateTime = policySummaryPage.expirationDate.formatDynamicPath(1).getData().substring(0,10);
				Assertions.passTest("Policy Summary", "Policy Number is : " + policyNumber);
			}

			testStatus = Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "Validation of policy number", false, false);

			String txnId = null;
			if (dataTestSetup.get("GetTransactionID").equalsIgnoreCase("yes")) {
	            //TF - wait for the new business transaction to make it to aftershock and then get the transactionId
	            Thread.sleep(10000);
	            DBFrameworkConnection connection = new DBFrameworkConnection(EnvironmentDetails.getEnvironmentDetails().getAftershockDetails());
	            QueryBuilder build = new QueryBuilder(connection);
	            List<String> outfield = new ArrayList<>();
	            outfield.add("TransactionID");
	            build.outFields(outfield);
	            build.tableName("policy");
	            build.whereBy("policyNumber = " + "'" + policyNumber + "'");
	            List<Map<String, Object>> txnIdResult = build.execute(60);
	            txnId = txnIdResult.get(0).get("TransactionID").toString();
			} else {
				txnId = "GetTransactionID is 'No' in setup sheet";
			}

			//write to CommDataExeReport.xls
			ExcelReportUtil.getInstance().addRow(data.get("TCID") + "_Trx1", data.get("InsuredName"), quoteNumber, policyNumber,
					epi_RatingEffectiveDate, data.get("PolicyEffDate"), expDate, data.get("PolicyEffDate"), processDate, binderDate,
					inspFee, polFee, String.valueOf(feesTotal), String.valueOf(Math.round(premiumTotal)), String.valueOf(policyTotal), txnPolFee, txnInspFee,
					String.valueOf(Math.round(txnPremium)), proRataFactor.toString(), String.valueOf(Math.round(annualTxnPremium)),
					String.valueOf(Math.round(annualPolPremium)), txnId, data.get("ProductType"), taxesAndFees, previousPolNumber);

			System.out.println(data.get("TCID")+ policyNumber);
		} catch (Exception e) {
			e.printStackTrace();
			Assertions.exceptionError("Error in DataTesting method", e.toString());
		}
		return testStatus;
	}

}
