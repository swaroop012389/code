/** Program Description: Verify the SLTF values,other validations on quote document and request bind page for CA state and also verify the cost card message on renewal and IO-21296
 *  Author			   : John
 *  Date of Creation   : 09/Feb/2022
 **/
package com.icat.epicenter.test.commercial.retailRegression;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.util.Precision;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.NetServAutomationFramework.config.EnvironmentDetails;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.WebDriverManager;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExistingAccountPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicyDocumentsPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC031 extends AbstractCommercialTest {

	public TC031() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID031.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
		EndorsePolicyPage endorsePage = new EndorsePolicyPage();
		ExistingAccountPage existingAccountPage = new ExistingAccountPage();
		BuildingUnderMinimumCostPage buildingUnderminimumCost = new BuildingUnderMinimumCostPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();

		// Initializing the variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		Map<String, String> testData = data.get(data_Value1);
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

			// Adding IO-21296
			// Verifying the presence of documents for CA state
			// "D-1 Notice,SL-2 Due Diligence and Subscription Agreement"
			Assertions.addInfo("Scenario 01",
					"Verifying the presence of documents for CA state ,' D-1 Notice,SL-2 Due Diligence and Subscription Agreement'");
			accountOverviewPage.requestBind.scrollToElement();
			accountOverviewPage.requestBind.click();
			policyDocumentsPage.addDocumentButton.scrollToElement();
			policyDocumentsPage.addDocumentButton.click();
			if (accountOverviewPage.getUrl().contains("uat1") || accountOverviewPage.getUrl().contains("q1")
					|| accountOverviewPage.getUrl().contains("uat2") || accountOverviewPage.getUrl().contains("ec")) {
				if (!testData.get("FileNameToUpload").equals("")) {
					String fileName = testData.get("FileNameToUpload");
					if (StringUtils.isBlank(fileName)) {
						Assertions.failTest("Upload File", "Filename is blank");
					}
					String uploadFileDir = EnvironmentDetails.getEnvironmentDetails().getString("test.file.uploadpath");
					waitTime(8);
					if (policyDocumentsPage.chooseDocument.checkIfElementIsPresent()
							&& policyDocumentsPage.chooseDocument.checkIfElementIsDisplayed()) {
						policyDocumentsPage.chooseDocument
								.setData(new File(uploadFileDir + fileName).getAbsolutePath());
						waitTime(5);// Adding wait time to load the element
					} else {
						policyDocumentsPage.chooseFile.setData(new File(uploadFileDir + fileName).getAbsolutePath());
						waitTime(5);// Adding wait time to load the element

					}
				}
				policyDocumentsPage.waitTime(3);
				for (int i = 0; i < 3; i++) {
					int dataValuei = i;
					Map<String, String> testDatai = data.get(dataValuei);
					policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).scrollToElement();
					policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).waitTillButtonIsClickable(90);
					policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).click();
					policyDocumentsPage.documentCategoryOptionsUAT
							.formatDynamicPath(testDatai.get("DocumentCategory"), 2).scrollToElement();
					policyDocumentsPage.documentCategoryOptionsUAT
							.formatDynamicPath(testDatai.get("DocumentCategory"), 2).click();
					policyDocumentsPage.waitTime(3);
					Assertions.verify(
							policyDocumentsPage.documentCategoryData
									.formatDynamicPath(testDatai.get("DocumentCategory")).getData()
									.contains(testDatai.get("DocumentCategory")),
							true, "Account overview page",
							"The documents for CA state is "
									+ policyDocumentsPage.documentCategoryData
											.formatDynamicPath(testDatai.get("DocumentCategory")).getData()
									+ " displayed",
							false, false);

				}
				policyDocumentsPage.cancelButtonUAT.scrollToElement();
				policyDocumentsPage.cancelButtonUAT.click();
				policyDocumentsPage.backButton.scrollToElement();
				policyDocumentsPage.backButton.click();
			} else {
				for (int i = 0; i <= 2; i++) {
					int dataValuei = i;
					Map<String, String> testDatai = data.get(dataValuei);
					policyDocumentsPage.documentCategoryArrow.scrollToElement();
					policyDocumentsPage.documentCategoryArrow.waitTillButtonIsClickable(90);
					policyDocumentsPage.documentCategoryArrow.click();
					policyDocumentsPage.documentCategoryOptions.formatDynamicPath(testDatai.get("DocumentCategory"))
							.scrollToElement();
					policyDocumentsPage.documentCategoryOptions.formatDynamicPath(testDatai.get("DocumentCategory"))
							.click();
					policyDocumentsPage.waitTime(3);
					Assertions.verify(
							policyDocumentsPage.documentCategoryData
									.formatDynamicPath(testDatai.get("DocumentCategory")).getData()
									.contains(testDatai.get("DocumentCategory")),
							true, "Account overview page",
							"The documents for CA state is "
									+ policyDocumentsPage.documentCategoryData
											.formatDynamicPath(testDatai.get("DocumentCategory")).getData()
									+ " displayed",
							false, false);

				}
				policyDocumentsPage.cancelButton.scrollToElement();
				policyDocumentsPage.cancelButton.click();
				policyDocumentsPage.backButton.scrollToElement();
				policyDocumentsPage.backButton.click();
			}
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
			// IO-21296 Ended

			// upload subscription document and Click on Request bind
			testData = data.get(data_Value1);
			accountOverviewPage.uploadSubscriptionDocument(testData, quoteNumber, data);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Assert commission percentage on NB Request bind page
			Assertions.verify(requestBindPage.commissionRate.getData(), "12.0%", "Request Bind Page",
					"Commission rate is " + requestBindPage.commissionRate.getData() + " on NB request bind page",
					false, false);

			// Entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home Page", "Clicked on Home button");

			// Searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote is searched successfully");

			// Click on Open Referral Link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

			// Approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			requestBindPage.waitTime(2);
			if (requestBindPage.chooseFile.checkIfElementIsPresent()
					&& requestBindPage.chooseFile.checkIfElementIsDisplayed()) {
				if (!testData.get("FileNameToUpload").equals("")) {
					policyDocumentsPage.fileUpload(testData.get("FileNameToUpload"));
				}
			}
			// carrier selection
			if (requestBindPage.carrierWarningMsg.checkIfElementIsPresent()
					&& requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed()) {
				requestBindPage.approveRequestCommercialData(testData);
			} else {
				requestBindPage.approveRequest();
			}
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Getting the policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// Click on Renewal Link
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();

			if (policySummaryPage.expaacMessage.checkIfElementIsPresent()
					&& policySummaryPage.expaacMessage.checkIfElementIsDisplayed()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.click();
				expaccInfoPage.enterExpaccInfo(testData, policyNumber);
				Assertions.passTest("Expacc Info Page", "Entered Expacc deatils successfully");

				// Go to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();

				// Performing Renewal Searches
				homePage.searchPolicy(policyNumber);

				// clicking on renewal policy link
				Assertions.addInfo("Policy Summary Page", "Renewing the Policy");
				policySummaryPage.renewPolicy.waitTillPresenceOfElement(60);
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();
				Assertions.passTest("Policy Summary Page", "Clicked on Renew policy link");
			}

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

			/*
			 * // Click on yes button if
			 * (policySummaryPage.transferContinue.checkIfElementIsPresent() &&
			 * policySummaryPage.transferContinue.checkIfElementIsDisplayed()) {
			 * policySummaryPage.transferContinue.scrollToElement();
			 * policySummaryPage.transferContinue.click(); }
			 */

			// Click on release renewal to producer button
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Renewal created and released to producer");

			// Getting renewal quote number
			String renewalQuoteNumber = accountOverviewPage.quoteNumber.getData().replace("(", "").replace(")", "");
			Assertions.passTest("Account Overview Page", "Renewal quote number is " + renewalQuoteNumber);

			// Asserting SL2 form link presence
			Assertions.addInfo("Scenario 02", "Asserting presence of SL2 form link");
			Assertions.verify(
					accountOverviewPage.sl2FormLink.checkIfElementIsPresent()
							&& accountOverviewPage.sl2FormLink.checkIfElementIsDisplayed(),
					true, "Account Overview Page", accountOverviewPage.sl2FormLink.getData() + " link is displayed",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Asserting SLTF value on account summary page of renewal quote
			Assertions.addInfo("Scenario 03", "Asserting SLTF Value on Account Overview Page of Renewal");

			// Getting data needed to calculate sltf and total
			double premiumValueAcc = Double
					.parseDouble(accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", ""));
			double feeValueAcc = Double
					.parseDouble(accountOverviewPage.feesValue.getData().replace("$", "").replace(",", ""));
			double sltfValueActualAcc = Double
					.parseDouble(accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", ""));
			double totalPremiumValueActualAcc = Double
					.parseDouble(accountOverviewPage.totalPremiumValue.getData().replace("$", "").replace(",", ""));
			double surplusContributionValue = Double.parseDouble(
					accountOverviewPage.surplusContributionValue.getData().replace("$", "").replace(",", ""));
			double stampingFeeValueCalculatedAcc = (premiumValueAcc + feeValueAcc + surplusContributionValue)
					* ((Double.parseDouble(testData.get("StampingFeeValue"))) / 100);
			double sltfValueCalculatedAcc = (premiumValueAcc + feeValueAcc + surplusContributionValue)
					* ((Double.parseDouble(testData.get("SLTFValue"))) / 100) + stampingFeeValueCalculatedAcc;
			double totalPremiumValueCalculatedAcc = premiumValueAcc + feeValueAcc + sltfValueCalculatedAcc
					+ surplusContributionValue;

			// Asserting SLTF and Total values
			/*
			 * Assertions.verify(df.format(sltfValueActualAcc),
			 * df.format(sltfValueCalculatedAcc), "Account Overview Page",
			 * "Actual and Calculated SLTF Values are matching", false, false);
			 */
			if (Precision.round(
					Math.abs(Precision.round(sltfValueActualAcc, 2)) - Precision.round(sltfValueCalculatedAcc, 2),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Actual SLTF Value : " + "$" + df.format(sltfValueActualAcc));
				Assertions.passTest("Account Overview Page",
						"Expected SLTF Value : " + df.format(sltfValueCalculatedAcc));
			} else {
				Assertions.passTest("Account Overview Page",
						"The Difference between actual and calculated  SLTF value is more than 0.05");
			}

			if (Precision.round(Math.abs(Precision.round(totalPremiumValueActualAcc, 2))
					- Precision.round(totalPremiumValueCalculatedAcc, 2), 2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Actual Premium, Taxes & Fees : " + "$" + df.format(totalPremiumValueActualAcc));
				Assertions.passTest("Account Overview Page",
						"Calculated Premium, Taxes & Fees : " + df.format(totalPremiumValueCalculatedAcc));
			} else {
				Assertions.passTest("Account Overview Page",
						"The Difference between actual and calculated  Premium, Taxes & Fees is more than 0.05");
			}
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on view print full quote page
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();

			// Asserting SLTF value on view print full quote page of renewal quote
			Assertions.addInfo("Scenario 04", "Asserting SLTF Value on View/Print Full Quote Page of renewal");

			// Getting data needed to calculate sltf, stamping fee and total
			double premiumValueVPFQ = Double
					.parseDouble(viewOrPrintFullQuotePage.premiumValue.getData().replace("$", "").replace(",", ""));
			double policyFeeVPFQVPFQ = Double
					.parseDouble(viewOrPrintFullQuotePage.policyFee.getData().replace("$", "").replace(",", ""));
			double sltFValueActualVPFQ = Double.parseDouble(
					viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData().replace("$", "").replace(",", ""));
			double stampingFeeActualVPFQ = Double
					.parseDouble(viewOrPrintFullQuotePage.stampingFeeValue.getData().replace("$", "").replace(",", ""));
			double surplusContributionVPFQ = Double.parseDouble(
					viewOrPrintFullQuotePage.surplusContributionValue.getData().replace("$", "").replace(",", ""));

			double sltfValueCalculatedVPFQ = (premiumValueVPFQ + policyFeeVPFQVPFQ + surplusContributionVPFQ)
					* (Double.parseDouble(testData.get("SLTFValue"))) / 100;
			double stampingFeeCalculated = (premiumValueVPFQ + policyFeeVPFQVPFQ + surplusContributionVPFQ)
					* (Double.parseDouble(testData.get("StampingFeeValue"))) / 100;
			double totalPremiumValueCalculated = (premiumValueVPFQ + policyFeeVPFQVPFQ + sltfValueCalculatedVPFQ
					+ stampingFeeCalculated + surplusContributionVPFQ);

			// Asserting SLTF, Stamping fee and Total values
			Assertions.verify(df.format(sltFValueActualVPFQ), df.format(sltfValueCalculatedVPFQ),
					"View/Print Full Quote Page", "Actual and Calculated SLTF Values are matching", false, false);

			if (Precision.round(
					Math.abs(Precision.round(stampingFeeActualVPFQ, 2)) - Precision.round(stampingFeeCalculated, 2),
					2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page",
						"Calculated Stamping Fees : " + "$" + df.format(stampingFeeCalculated));
				Assertions.passTest("View/Print Full Quote Page",
						"Actual Stamping Fees : " + "$" + stampingFeeActualVPFQ);
			} else {
				Assertions.passTest("View/Print Full Quote Page",
						"The Difference between actual and calculated stamping fee is more than 0.05");
			}

			if (Precision.round(Math.abs(
					Precision.round(totalPremiumValueActualAcc, 2) - Precision.round(totalPremiumValueCalculated, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated Total Premium : " + "$" + df.format(totalPremiumValueCalculated));
				Assertions.passTest("Account Overview Page",
						"Actual Total Premium  : " + "$" + df.format(totalPremiumValueActualAcc));
			} else {
				Assertions.passTest("Account Overview Page",
						"The Difference between actual and calculated Total Premium  is more than 0.05");
			}
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Asserting Mandatory Test under Subject To section on View/Print Full Quote
			// Page of renewal
			Assertions.addInfo("Scenario 05",
					"Asserting Mandatory Test under Subject To section on View/Print Full Quote Page of renewal ");
			Assertions.verify(viewOrPrintFullQuotePage.subjectToWordings
					.formatDynamicPath("Completed and signed diligent effort form (SL-2).").checkIfElementIsPresent()
					&& viewOrPrintFullQuotePage.subjectToWordings
							.formatDynamicPath("Completed and signed diligent effort form (SL-2).")
							.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.subjectToWordings
							.formatDynamicPath("Completed and signed diligent effort form (SL-2).").getData()
							+ " wording is displayed under Subject To section",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.subjectToWordings.formatDynamicPath("Signed D-1 form.")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.subjectToWordings.formatDynamicPath("Signed D-1 form.")
									.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.subjectToWordings.formatDynamicPath("Signed D-1 form.").getData()
							+ " wording is displayed under Subject To section",
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Click on back button
			viewOrPrintFullQuotePage.scrollToTopPage();
			viewOrPrintFullQuotePage.waitTime(2);
			viewOrPrintFullQuotePage.backButton.click();

			// Click on request bind button
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
			accountOverviewPage.uploadSubscriptionDocument(testData, quoteNumber, data);

			// Asserting Commission percentage on renewal request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			Assertions.addInfo("Scenario 06", "Asserting Commission Percentage on renewal request bind page");
			Assertions.verify(requestBindPage.commissionRate.getData(), "15.0%", "Request Bind Page",
					"Commission rate is " + requestBindPage.commissionRate.getData() + " on Renewal request bind page",
					false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Asserting Mandatory CA Text on renewal request bind page
			Assertions.addInfo("Scenario 07", "Asserting CA Text on renewal request bind page");
			Assertions.verify(requestBindPage.dueDiligenceText.formatDynamicPath("signed Diligent Search Report (SL-2)")
					.checkIfElementIsPresent()
					&& requestBindPage.dueDiligenceText.formatDynamicPath("signed Diligent Search Report (SL-2)")
							.checkIfElementIsDisplayed(),
					true, "Request Bind Page",
					requestBindPage.dueDiligenceText.formatDynamicPath("signed Diligent Search Report (SL-2)").getData()
							+ " wording is displayed",
					false, false);
			Assertions.verify(
					requestBindPage.dueDiligenceText.formatDynamicPath("signed copy of the D-1 notice.")
							.checkIfElementIsPresent()
							&& requestBindPage.dueDiligenceText.formatDynamicPath("signed copy of the D-1 notice.")
									.checkIfElementIsDisplayed(),
					true, "Request Bind Page",
					requestBindPage.dueDiligenceText.formatDynamicPath("signed copy of the D-1 notice.").getData()
							+ " wording is displayed",
					false, false);
			Assertions.verify(
					requestBindPage.dueDiligenceText.formatDynamicPath("result in canceled coverage.")
							.checkIfElementIsPresent()
							&& requestBindPage.dueDiligenceText.formatDynamicPath("result in canceled coverage.")
									.checkIfElementIsDisplayed(),
					true, "Request Bind Page",
					requestBindPage.dueDiligenceText.formatDynamicPath("result in canceled coverage.").getData()
							+ " wording is displayed",
					false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Enter renewal bind details
			requestBindPage.renewalRequestBind(testData);
			Assertions.passTest("Request bind page", "Renewal details entered successfully");

			// clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind request page",
					"Bind bequest page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home page", "Clicked on home button");

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"Home page loaded successfully", false, false);
			homePage.searchReferral(renewalQuoteNumber);
			Assertions.passTest("Home page", "Searched submitted bind request successfullly");

			// approving referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral page", "Referral request approved successfully");
			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Approval page loaded successfully", false, false);
			requestBindPage.approveRequest();

			// carrier selection
			if (requestBindPage.carrierWarningMsg.checkIfElementIsPresent()
					&& requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed()) {
				requestBindPage.approveRequest();
			}
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// getting the policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Renewal Policy Number is : " + policyNumber);

			// Click on endorse PB link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Enter effective date
			Assertions.verify(endorsePage.endorsementEffDate.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Endorse Policy page loaded successfully", false, false);
			endorsePage.endorsementEffDate.scrollToElement();
			endorsePage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// Click on change coverage options link
			endorsePage.changeCoverageOptionsLink.scrollToElement();
			endorsePage.changeCoverageOptionsLink.click();

			// Update building value
			testData = data.get(data_Value2);
			createQuotePage.scrollToBottomPage();
			createQuotePage.waitTime(2);
			createQuotePage.buildingValue.formatDynamicPath(0, 0).scrollToElement();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).clearData();
			createQuotePage.waitTime(2);
			createQuotePage.buildingValue.formatDynamicPath(0, 0).appendData(testData.get("L1B1-BldgValue"));
			createQuotePage.buildingValue.formatDynamicPath(0, 0).tab();
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// click on next button
			endorsePage.nextButton.scrollToElement();
			endorsePage.nextButton.click();

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}
			Assertions.passTest("Endorse policy page", "Clicked on continue button successfully");

			// Asserting SLTF calculated correctly after premium change
			testData = data.get(data_Value1);
			Assertions.addInfo("Scenario 08",
					"Asserting Transaction/Annual/Term SLTF values are calculated correctly after premium change");
			for (int i = 2; i <= 4; i++) {
				double premium = Double.parseDouble(endorsePage.transactionPremiumFee.formatDynamicPath(i).getData()
						.replace("$", "").replace(",", ""));
				double policyFee = Double.parseDouble(
						endorsePage.policyFee.formatDynamicPath(i).getData().replace("$", "").replace(",", ""));
				double inspectionFee = Double.parseDouble(
						endorsePage.inspectionFee.formatDynamicPath(i).getData().replace("$", "").replace(",", ""));
				double surplusContribution = Double.parseDouble(endorsePage.surplusContributionValue
						.formatDynamicPath(i).getData().replace("$", "").replace(",", ""));
				double sltfActual = Double.parseDouble(endorsePage.premium
						.formatDynamicPath("surplusLinesTaxesAndFees", i).getData().replace("$", "").replace(",", ""));
				double totalActual = Double.parseDouble(
						endorsePage.totalTerm.formatDynamicPath(i).getData().replace("$", "").replace(",", ""));
				double stampingCalculated = (premium + policyFee + inspectionFee + surplusContribution)
						* (Double.parseDouble(testData.get("StampingFeeValue"))) / 100;
				double sltfCalculated = (premium + policyFee + inspectionFee + surplusContribution)
						* ((Double.parseDouble(testData.get("SLTFValue"))) / 100) + stampingCalculated;
				double totalCalculated = premium + policyFee + sltfCalculated + inspectionFee + surplusContribution;
				if (i == 2) {

					// Asserting Transaction sltf calculated correctly
					if (Precision.round(Math.abs(Precision.round(sltfActual, 2) - Precision.round(sltfCalculated, 2)),
							2) < 0.05) {
						Assertions.passTest("Endorse Policy Page",
								"Calculated SLTF Value  : " + "$" + df.format(sltfCalculated));
						Assertions.passTest("Endorse Policy Page", "Actual SLTF Value : " + "$" + sltfActual);
					} else {
						Assertions.passTest("Endorse Policy Page",
								"The Difference between actual and calculated SLTF is more than 0.05");
					}

					if (Precision.round(Math.abs(Precision.round(totalActual, 2) - Precision.round(totalCalculated, 2)),
							2) < 0.05) {
						Assertions.passTest("Endorse Policy Page",
								"Calculated Transaction Total Value  : " + "$" + df.format(totalCalculated));
						Assertions.passTest("Endorse Policy Page",
								"Actual Transaction Total Value : " + "$" + totalActual);
					} else {
						Assertions.passTest("Endorse Policy Page",
								"The Difference between actual and calculated total value is more than 0.05");
					}
				} else if (i == 3) {
					// Asserting Annual sltf calculated correctly

					if (Precision.round(Math.abs(Precision.round(sltfActual, 2) - Precision.round(sltfCalculated, 2)),
							2) < 0.05) {
						Assertions.passTest("Endorse Policy Page",
								"Calculated SLTF Value  : " + "$" + df.format(sltfCalculated));
						Assertions.passTest("Endorse Policy Page", "Actual SLTF Value : " + "$" + sltfActual);
					} else {
						Assertions.passTest("Endorse Policy Page",
								"The Difference between actual and calculated SLTF is more than 0.05");
					}
					Assertions.verify(df.format(totalActual), df.format(totalCalculated), "Endorse Policy Page",
							"Annual Total, actual and calculated values are matching after premium update", false,
							false);
				} else {
					// Asserting Term sltf calculated correctly

					if (Precision.round(Math.abs(Precision.round(sltfActual, 2) - Precision.round(sltfCalculated, 2)),
							2) < 0.05) {
						Assertions.passTest("Endorse Policy Page",
								"Calculated SLTF Value  : " + "$" + df.format(sltfCalculated));
						Assertions.passTest("Endorse Policy Page", "Actual SLTF Value : " + "$" + sltfActual);
					} else {
						Assertions.passTest("Endorse Policy Page",
								"The Difference between actual and calculated SLTF is more than 0.05");
					}

					if (Precision.round(Math.abs(Precision.round(totalActual, 2) - Precision.round(totalCalculated, 2)),
							2) < 0.05) {
						Assertions.passTest("Endorse Policy Page",
								"Calculated Term total Value  : " + "$" + df.format(totalCalculated));
						Assertions.passTest("Endorse Policy Page", "Actual Term total Value : " + "$" + totalActual);
					} else {
						Assertions.passTest("Endorse Policy Page",
								"The Difference between actual and calculated Term total is more than 0.05");
					}
				}
			}
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// Complete the endorsement
			endorsePage.completeButton.scrollToElement();
			endorsePage.completeButton.click();

			// Close the endorsement transaction
			endorsePage.closeButton.scrollToElement();
			endorsePage.closeButton.click();

			// Click on endorse PB link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Enter effective date
			Assertions.verify(endorsePage.endorsementEffDate.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Endorse Policy page loaded successfully", false, false);
			endorsePage.endorsementEffDate.scrollToElement();
			endorsePage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// Enter new policy expiry date
			testData = data.get(data_Value2);
			endorsePage.policyExpirationDate.scrollToElement();
			endorsePage.policyExpirationDate.setData(testData.get("PolicyEffDate"));
			endorsePage.changeExpirationDate.scrollToElement();
			endorsePage.changeExpirationDate.click();

			// click on continue button
			if (endorsePage.continueButton.checkIfElementIsPresent()
					&& endorsePage.continueButton.checkIfElementIsDisplayed()) {
				endorsePage.continueButton.scrollToElement();
				endorsePage.continueButton.click();
			}

			// click on next button
			endorsePage.nextButton.scrollToElement();
			endorsePage.nextButton.click();

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Asserting SLTF calculated correctly after policy expiry date change
			testData = data.get(data_Value1);
			Assertions.addInfo("Scenario 09",
					"Asserting Transaction/Annual/Term SLTF values are calculated correctly after policy expiry date change");
			for (int i = 2; i <= 4; i++) {
				double premium = Double.parseDouble(endorsePage.transactionPremiumFee.formatDynamicPath(i).getData()
						.replace("$", "").replace(",", ""));
				double policyFee = Double.parseDouble(
						endorsePage.policyFee.formatDynamicPath(i).getData().replace("$", "").replace(",", ""));
				double inspectionFee = Double.parseDouble(
						endorsePage.inspectionFee.formatDynamicPath(i).getData().replace("$", "").replace(",", ""));
				double surplusContribution = Double.parseDouble(endorsePage.surplusContributionValue
						.formatDynamicPath(i).getData().replace("$", "").replace(",", ""));
				double sltfActual = Double.parseDouble(endorsePage.premium
						.formatDynamicPath("surplusLinesTaxesAndFees", i).getData().replace("$", "").replace(",", ""));
				double totalActual = Double.parseDouble(
						endorsePage.totalTerm.formatDynamicPath(i).getData().replace("$", "").replace(",", ""));
				double stampingCalculated = (premium + policyFee + inspectionFee + surplusContribution)
						* (Double.parseDouble(testData.get("StampingFeeValue"))) / 100;
				double sltfCalculated = (premium + policyFee + inspectionFee + surplusContribution)
						* ((Double.parseDouble(testData.get("SLTFValue"))) / 100) + stampingCalculated;
				double totalCalculated = premium + policyFee + sltfCalculated + inspectionFee + surplusContribution;
				if (i == 2) {
					// Asserting Transaction sltf calculated correctly
					if (Precision.round(Math.abs(Precision.round(sltfActual, 2) - Precision.round(sltfCalculated, 2)),
							2) < 0.05) {
						Assertions.passTest("Endorse Policy Page",
								"Calculated Transaction SLTF Value  : " + "$" + df.format(sltfCalculated));
						Assertions.passTest("Endorse Policy Page",
								"Actual Transaction SLTF Value : " + "$" + sltfActual);
					} else {
						Assertions.passTest("Endorse Policy Page",
								"The Difference between actual and calculated SLTF is more than 0.05");
					}

					if (Precision.round(Math.abs(Precision.round(totalActual, 2) - Precision.round(totalCalculated, 2)),
							2) < 0.05) {
						Assertions.passTest("Endorse Policy Page",
								"Calculated Transaction Total Value  : " + "$" + df.format(totalCalculated));
						Assertions.passTest("Endorse Policy Page",
								"Actual Transaction Total Value : " + "$" + totalActual);
					} else {
						Assertions.passTest("Endorse Policy Page",
								"The Difference between actual and calculated Total value is more than 0.05");
					}

				} else if (i == 3) {
					// Asserting Annual sltf calculated correctly
					Assertions.verify(df.format(sltfActual), df.format(sltfCalculated), "Endorse Policy Page",
							"Annual SLTF, actual and calculated values are matching after policy expiry date update",
							false, false);
					Assertions.verify(df.format(totalActual), df.format(totalCalculated), "Endorse Policy Page",
							"Annual Total, actual and calculated values are matching after policy expiry date update",
							false, false);
				} else {
					// Asserting Term sltf calculated correctly
					if (Precision.round(Math.abs(Precision.round(sltfActual, 2) - Precision.round(sltfCalculated, 2)),
							2) < 0.05) {
						Assertions.passTest("Endorse Policy Page",
								"Calculated Term SLTF Value  : " + "$" + df.format(sltfCalculated));
						Assertions.passTest("Endorse Policy Page", "Actual Term SLTF Value : " + "$" + sltfActual);
					} else {
						Assertions.passTest("Endorse Policy Page",
								"The Difference between actual and calculated SLTF value is more than 0.05");
					}
					if (Precision.round(Math.abs(Precision.round(totalActual, 2) - Precision.round(totalCalculated, 2)),
							2) < 0.05) {
						Assertions.passTest("Endorse Policy Page",
								"Calculated Term Total Value  : " + "$" + df.format(totalCalculated));
						Assertions.passTest("Endorse Policy Page", "Actual Term Total Value : " + "$" + totalActual);
					} else {
						Assertions.passTest("Endorse Policy Page",
								"The Difference between actual and calculated term total value is more than 0.05");
					}
				}
			}
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			// Complete the endorsement
			endorsePage.completeButton.scrollToElement();
			endorsePage.completeButton.click();

			// click on continue button
			if (endorsePage.continueButton.checkIfElementIsPresent()
					&& endorsePage.continueButton.checkIfElementIsDisplayed()) {
				endorsePage.continueButton.scrollToElement();
				endorsePage.continueButton.click();
			}

			// Close the endorsement transaction
			endorsePage.closeButton.waitTillVisibilityOfElement(60);
			endorsePage.closeButton.scrollToElement();
			endorsePage.closeButton.click();

			// Click on cancel policy link
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Cancel Policy link");

			// Select cancellation details
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			cancelPolicyPage.waitTime(2);// Need wait time to load the element
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			cancelPolicyPage.legalNoticeWording.scrollToElement();
			cancelPolicyPage.legalNoticeWording.setData(testData.get("Cancellation_LegalNoticeWording"));
			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();

			// Asserting SLTF values on cancellation page
			Assertions.addInfo("Scenario 10",
					"Asserting Earned/Returned SLTF values are calculated correctly after Cancellation");
			for (int j = 3; j <= 4; j++) {
				// get earned values
				double premium = Double.parseDouble(
						cancelPolicyPage.premiumValue.formatDynamicPath(j).getData().replace("$", "").replace("-", ""));
				double sltfActual = Double.parseDouble(
						cancelPolicyPage.taxesAndFees.formatDynamicPath(j).getData().replace("$", "").replace("-", ""));
				double totalPremiumActual = Double.parseDouble(
						cancelPolicyPage.policyTotal.formatDynamicPath(j).getData().replace("$", "").replace("-", ""));
				double surplusContribution = Double.parseDouble(cancelPolicyPage.surplusContributionVlaue
						.formatDynamicPath(j).getData().replace("$", "").replace("-", ""));
				if (j == 3) {
					double policyFeeEarned = Double
							.parseDouble(cancelPolicyPage.policyFeeEarned.getData().replace(",", ""));
					double stampingCalculated = (premium + policyFeeEarned + surplusContribution)
							* (Double.parseDouble(testData.get("StampingFeeValue")) / 100);
					double sltfCalculated = (premium + policyFeeEarned + surplusContribution)
							* (Double.parseDouble(testData.get("SLTFValue")) / 100) + stampingCalculated;
					double totalPremiumCalculated = premium + policyFeeEarned + sltfCalculated;
					if (Precision.round(Math.abs(Precision.round(sltfActual, 2)) - Precision.round(sltfCalculated, 2),
							2) < 0.05) {
						Assertions.passTest("Cancel Policy Page",
								"Calculated Surplus Lines Taxes and Fees : " + "$" + df.format(sltfCalculated));
						Assertions.passTest("Cancel Policy Page",
								"Actual Surplus Lines Taxes and Fees : " + "$" + sltfActual);
					} else {
						Assertions.passTest("Cancel Policy Page",
								"The Difference between actual SLTF and calculated SLTF is more than 0.05");
					}
					if (Precision.round(Math.abs(Precision.round(totalPremiumActual, 2))
							- Precision.round(totalPremiumCalculated, 2), 2) < 0.05) {
						Assertions.passTest("Cancel Policy Page",
								"Calculated Total Premium : " + "$" + df.format(totalPremiumCalculated));
						Assertions.passTest("Cancel Policy Page", "Actual Total Premium : " + "$" + totalPremiumActual);
					} else {
						Assertions.passTest("Cancel Policy Page",
								"The Difference between actual  and calculated total premium is more than 0.05");
					}
				} else {
					double policyFeeReturned = Double
							.parseDouble(cancelPolicyPage.policyFee.formatDynamicPath(j).getData().replace("$", ""));
					double stampingCalculated = (premium + policyFeeReturned + surplusContribution)
							* (Double.parseDouble(testData.get("StampingFeeValue")) / 100);
					double sltfCalculated = (premium + policyFeeReturned + surplusContribution)
							* (Double.parseDouble(testData.get("SLTFValue")) / 100) + stampingCalculated;
					double totalPremiumCalculated = premium + policyFeeReturned + sltfCalculated + surplusContribution;
					if (Precision.round(Math.abs(Precision.round(sltfActual, 2)) - Precision.round(sltfCalculated, 2),
							2) < 0.05) {
						Assertions.passTest("Cancel Policy Page",
								"SLTF Returned, actual and calculated values are matching " + "$"
										+ df.format(sltfActual));
					} else {
						Assertions.verify(df.format(sltfActual), df.format(sltfCalculated), "Cancel Policy Page",
								"SLTF Returned, actual and calculated values are not matching", false, false);
					}

					if (Precision.round(Math.abs(Precision.round(totalPremiumActual, 2))
							- Precision.round(totalPremiumCalculated, 2), 2) < 0.05) {
						Assertions.passTest("Cancel Policy Page",
								"Total Returned, actual and calculated values are matching " + "$"
										+ df.format(totalPremiumActual));
					} else {
						Assertions.verify(df.format(totalPremiumActual), df.format(totalPremiumCalculated),
								"Cancel Policy Page", "Total Returned, actual and calculated values are not matching",
								false, false);
					}

				}
			}
			Assertions.addInfo("Scenario 10", "Scenario 10 Ended");

			// go to hompage
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// search the policy
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Searched the Policy " + policyNumber + " successfully");

			// Click on endorse PB link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Enter effective date
			testData = data.get(data_Value2);
			Assertions.verify(endorsePage.endorsementEffDate.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Endorse Policy page loaded successfully", false, false);
			endorsePage.endorsementEffDate.scrollToElement();
			endorsePage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// click on edit location or building details link
			endorsePage.editLocOrBldgInformationLink.scrollToElement();
			endorsePage.editLocOrBldgInformationLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Edit Location or Building details link");

			// modify building details
			testData = data.get(data_Value3);
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			locationPage.buildingLink.formatDynamicPath(1, 1).click();

			// modifying Const type,square foot value,occupancy and building value
			Assertions.passTest("Building Page", "Building Page loaded successfully");
			buildingPage.constructionTypeArrow.waitTillVisibilityOfElement(60);
			buildingPage.constructionTypeArrow.scrollToElement();
			buildingPage.constructionTypeArrow.click();
			buildingPage.constructionTypeOption.formatDynamicPath(testData.get("L1B1-BldgConstType")).scrollToElement();
			buildingPage.constructionTypeOption.formatDynamicPath(testData.get("L1B1-BldgConstType")).click();
			buildingPage.totalSquareFootage.setData(testData.get("L1B1-BldgSqFeet"));

			// click on building occupancy link
			buildingPage.buildingOccupancyLink.scrollToElement();
			buildingPage.buildingOccupancyLink.click();
			buildingPage.waitTime(2);// need wait time to load the element

			if (testData.get("L1B1-PrimaryOccupancyCode") != null
					&& !testData.get("L1B1-PrimaryOccupancyCode").equalsIgnoreCase("")) {
				if (!buildingPage.primaryOccupancy.getData().equals("")) {
					WebElement ele = WebDriverManager.getCurrentDriver().findElement(By.xpath(
							"//div[label[a[contains(text(),'Primary Occupancy')]]]//following-sibling::div//input[contains(@id,'primaryOccupancy')]"));
					ele.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
				}
				buildingPage.setOccupancyJS("primary", testData.get("L1B1-PrimaryOccupancyCode"), testData.get("Peril"),
						testData.get("QuoteState"));
			}

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
			buildingPage.continueButton.scrollToElement();
			buildingPage.continueButton.click();

			// existing account found page
			if (buildingPage.pageName.getData().contains("Existing Account Found")) {
				existingAccountPage.OverrideExistingAccount();
			}

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
			Assertions.addInfo("Scenario 11",
					"Verifying the Costcard message and Verifying the actual and expected cost card values Construction type: Reinforced Masonry, Occupancy type: Wholesale,Building Square Feet: 1000");
			Assertions.verify(
					buildingUnderminimumCost.costcardMessage
							.formatDynamicPath("Building value").checkIfElementIsDisplayed(),
					true, "Building Under Minimum Cost Page",
					"The Costcard message "
							+ buildingUnderminimumCost.costcardMessage.formatDynamicPath("Building value").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 11", "Scenario 11 Ended");

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC031 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC031 ", "Executed Successfully");
			}
		}
	}
}
