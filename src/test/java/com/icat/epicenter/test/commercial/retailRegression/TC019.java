/** Program Description: Check South Carolina quotes will have notice printed after the loss history section and before the signature section
Check if both SC Renewal quotes have a place on the Renewal Request Bind and Confirm Bind pages to upload Due Diligence prior to Bind the renewal quote or approving the referred renewal quote.
Verifying if Handle in MMBU button link is not displayed in Approve/Decline Quote page and IO-21296
*  Author			   : John
*  Date of Creation   : 23/07/2021
**/
package com.icat.epicenter.test.commercial.retailRegression;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.NetServAutomationFramework.config.EnvironmentDetails;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.WebDriverManager;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.ConfirmBindRequestPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicyDocumentsPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewDocumentsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC019 extends AbstractCommercialTest {

	public TC019() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID019.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ConfirmBindRequestPage confirmBindRequestPage = new ConfirmBindRequestPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ReferralPage referralPage = new ReferralPage();
		PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		BuildingUnderMinimumCostPage buildingUnderminimumCost = new BuildingUnderMinimumCostPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ViewDocumentsPage viewDocumentsPage = new ViewDocumentsPage();

		// Initializing the variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
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
			Assertions.verify(locationPage.addBuildingsButton.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);
			Assertions.passTest("Location Page", "Location details entered successfully");

			// Entering Location 1 Dwelling 1 Details
			
			buildingPage.enterBuildingDetails(testData);

			// select peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

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

			// Asserting quote number
			quoteNumber = accountOverviewPage.quoteNumber.getData().replace("(", "").replace(")", "");
			Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Quote Number is " + quoteNumber, false, false);

			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View/Print Full quote link");

			// Assert forms
			Assertions.addInfo("Scenatio 01", "Asserting Notice Wording for SC State");
			Assertions.verify(
					viewOrPrintFullQuotePage.scWording.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.scWording.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.scWording.getData() + " is displayed for SC State", false, false);
			Assertions.passTest("Scenario 01", "Scenario 01 Ended");

			// click back
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.passTest("View/Print Full Quote Page", "Clicked on Back button");

			// Adding IO-21296
			// Verifying the presence of documents for SC state
			// "Due Diligence and Subscription Agreement"
			Assertions.addInfo("Scenario 02",
					"Verifying the presence of documents for SC state ,'Due Diligence and Subscription Agreement'");
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
				for (int i = 0; i <= 1; i++) {
					int dataValuei = i;
					Map<String, String> testDatai = data.get(dataValuei);
					policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).checkIfElementIsPresent();
					policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).checkIfElementIsDisplayed();
					policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).waitTillButtonIsClickable(90);
					policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).scrollToElement();
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
							"The documents for SC state is "
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
				policyDocumentsPage.waitTime(3);

				for (int i = 0; i <= 1; i++) {
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
							"The documents for SC state is "
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
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");
			// IO-21296 Ended

			// Clicked on request bind button
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

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
			if (requestBindPage.chooseFile.checkIfElementIsPresent()
					&& requestBindPage.chooseFile.checkIfElementIsDisplayed()) {
				if (!testData.get("FileNameToUpload").equals("")) {
					policyDocumentsPage.fileUpload(testData.get("FileNameToUpload"));
				}
			}

			// Verifying Handle in MMBU button should NOT be available on the Refer
			// Quote screen
			// Approve/Decline page
			Assertions.addInfo("Scenario 3",
					"Verifying Handle in MMBU button should NOT be available on the Refer Quote screen");
			Assertions.verify(approveDeclineQuotePage.handleInMMBUButton.checkIfElementIsPresent(), false,
					"Approve/Decline Quote Page",
					"Handle in MMBU button link is not displayed in Approve/Decline Quote page is verified", false,
					false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			requestBindPage.approve.scrollToElement();
			requestBindPage.approve.click();
			requestBindPage.waitTime(2);// need wait time to load element
			if (requestBindPage.approveBackDating.checkIfElementIsPresent()
					&& requestBindPage.approveBackDating.checkIfElementIsDisplayed()) {
				requestBindPage.approveBackDating.moveToElement();
				requestBindPage.approveBackDating.scrollToElement();
				requestBindPage.approveBackDating.click();
			}

			if (requestBindPage.overrideEffectiveDate.checkIfElementIsPresent()
					&& requestBindPage.overrideEffectiveDate.checkIfElementIsDisplayed()) {
				requestBindPage.overrideEffectiveDate.scrollToElement();
				requestBindPage.overrideEffectiveDate.select();
				requestBindPage.waitTime(2);// need wait time to load element
				requestBindPage.approve.scrollToElement();
				requestBindPage.approve.click();
				requestBindPage.waitTime(2);// need wait time to load element
				if (requestBindPage.approveBackDating.checkIfElementIsPresent()
						&& requestBindPage.approveBackDating.checkIfElementIsDisplayed()) {
					requestBindPage.approveBackDating.moveToElement();
					requestBindPage.approveBackDating.scrollToElement();
					requestBindPage.approveBackDating.click();
				}
			}
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Get policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Policy Number is " + policyNumber, false, false);

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
			Assertions.addInfo("Policy Summary Page", "Renew NB Policy");
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy link");

			// click on continue
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

			// getting renewal quote number
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "The Renewal Quote Number :  " + quoteNumber);

			// click on release renewal to producer button
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Renewal to Producer button");

			// logout as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Login as Producer
			loginPage.refreshPage();
			loginPage.waitTime(2);// wait time is needed to load the page
			loginPage.enterLoginDetails(setUpData.get("RetailProducer"), setUpData.get("Password"));

			// searching the renewal quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);
			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.passTest("Home Page", "Searched the Renewal Quote " + quoteNumber + " successfully");

			// click on edit dwelling
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).click();
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Building link");

			// Update building occupancy percentage to less than 31%
			testData = data.get(data_Value2);
			buildingPage.buildingOccupancyLink.waitTillVisibilityOfElement(60);
			buildingPage.buildingOccupancyLink.scrollToElement();
			buildingPage.buildingOccupancyLink.click();
			buildingPage.waitTime(2);// need wait time to load the element

			if (buildingPage.primaryPercentOccupied.checkIfElementIsPresent()
					&& buildingPage.primaryPercentOccupied.checkIfElementIsDisplayed()) {
				buildingPage.primaryPercentOccupied.clearData();
				buildingPage.waitTime(2);// wait time is needed to load the element after clear the data
				if (buildingPage.continueWithUpdateBtn.checkIfElementIsPresent()
						&& buildingPage.continueWithUpdateBtn.checkIfElementIsDisplayed()) {
					buildingPage.continueWithUpdateBtn.scrollToElement();
					buildingPage.continueWithUpdateBtn.click();
				}
				buildingPage.primaryPercentOccupied.setData(testData.get("L1B1-PercentOccupied"));
				buildingPage.primaryPercentOccupied.tab();
			}
			if (buildingPage.buildingOccupancy_no.checkIfElementIsPresent()
					&& buildingPage.buildingOccupancy_no.checkIfElementIsDisplayed()) {
				buildingPage.buildingOccupancy_no.waitTillVisibilityOfElement(60);
				buildingPage.buildingOccupancy_no.scrollToElement();
				buildingPage.buildingOccupancy_no.click();
				buildingPage.waitTime(2);// wait time is needed to load the element after clear the data
				if (buildingPage.continueWithUpdateBtn.checkIfElementIsPresent()
						&& buildingPage.continueWithUpdateBtn.checkIfElementIsDisplayed()) {
					buildingPage.continueWithUpdateBtn.scrollToElement();
					buildingPage.continueWithUpdateBtn.click();
					Assertions.passTest("Building Page", "Entered the Occupancy Percentage less than 31%");
				}
			}

			// click on review building button
			buildingPage.reviewBuilding.waitTillVisibilityOfElement(60);
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();
			Assertions.passTest("Building Page", "Clicked on Review Building");

			// click on Override button
			if (buildingPage.override.checkIfElementIsPresent() && buildingPage.override.checkIfElementIsDisplayed()) {
				buildingPage.override.waitTillVisibilityOfElement(60);
				buildingPage.override.scrollToElement();
				buildingPage.override.click();
			}

			// click on create quote button
			buildingPage.createQuote.waitTillVisibilityOfElement(60);
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();
			Assertions.passTest("Building Page", "Clicked on Create Quote");

			// Asserting waring message for ineligible Fire/AOP or AOP/GL coverage
			Assertions.addInfo("Scenario 04", "Asserting waring message for ineligible Fire/AOP or AOP/GL coverage");
			Assertions.verify(
					createQuotePage.globalWarning
							.formatDynamicPath("ineligible for Fire/AOP").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Warning message for ineligible coverage is dsplayed : "
							+ createQuotePage.globalWarning.formatDynamicPath("ineligible for Fire/AOP").getData(),
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// enter refer quote details
			if (referQuotePage.referQuote.checkIfElementIsPresent()
					&& referQuotePage.referQuote.checkIfElementIsDisplayed()) {
				Assertions.verify(referQuotePage.referQuote.checkIfElementIsDisplayed(), true, "Refer Quote Page",
						"Refer Quote Page Loaded successfully", false, false);
				referQuotePage.contactName.setData(testData.get("ProducerName"));
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.click();
				quoteNumber = referQuotePage.quoteNum.getData();
				Assertions.passTest("Referral Quote Page", "Quote Number :  " + quoteNumber);

				// Logout as producer
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();

				// Login as USM
				loginPage.refreshPage();
				loginPage.waitTime(3);// wait time is needed to load the page
				loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));

				// searching the quote number in grid and clicking on the quote link
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"USM Home page loaded successfully", false, false);
				homePage.searchQuote(quoteNumber);
				Assertions.passTest("Home Page", "Quote for referral is searched successfully");
				Assertions.verify(accountOverviewPage.openReferralLink.checkIfElementIsDisplayed(), true,
						"Account Overview Page", "Account Overview Page Loaded successfully", false, false);

				// Click on Open Referral Link
				accountOverviewPage.openReferralLink.scrollToElement();
				accountOverviewPage.openReferralLink.click();
				Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

				if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
					referralPage.pickUp.scrollToElement();
					referralPage.pickUp.click();
				}
				Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true,
						"Referral Page", "Referral page loaded successfully", false, false);
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

				// Searching the account
				homePage.goToHomepage.click();
				homePage.searchQuote(quoteNumber);
			}

			// getting renewal quote number
			Assertions.verify(accountOverviewPage.issueQuoteButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			String quoteNumber2 = accountOverviewPage.quoteNumber.getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "The Renewal Requote Number :  " + quoteNumber2);

			// click on issue quote
			accountOverviewPage.issueQuoteButton.scrollToElement();
			accountOverviewPage.issueQuoteButton.click();

			// logout as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Login as USM
			loginPage.refreshPage();
			loginPage.waitTime(2);// wait time is needed to load the page
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));

			// searching the renewal quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber2);
			Assertions.passTest("Home Page", "Searched the Renewal Quote " + quoteNumber + " successfully");

			// Click on request bind button
			testData = data.get(data_Value1);
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber2);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind Button");

			if (!accountOverviewPage.requestBind.checkIfElementIsPresent()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuote(quoteNumber2);
				accountOverviewPage.requestBind.scrollToElement();
				accountOverviewPage.requestBind.click();
			}
			// Enter Bind Details with out uploading file
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.addContactInformation(testData);
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();
			confirmBindRequestPage.confirmBind();

			// Uploading the file
			if (requestBindPage.chooseFile.checkIfElementIsPresent()
					&& requestBindPage.chooseFile.checkIfElementIsDisplayed()) {
				if (!testData.get("FileNameToUpload").equals("")) {
					policyDocumentsPage.fileUpload(testData.get("FileNameToUpload"));
				}
			}

			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();
			confirmBindRequestPage.confirmBind();

			if (requestBindPage.chooseFile.checkIfElementIsPresent()
					&& requestBindPage.chooseFile.checkIfElementIsDisplayed()) {
				if (!testData.get("FileNameToUpload").equals("")) {
					policyDocumentsPage.fileUpload(testData.get("FileNameToUpload"));
				}
			}
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
			Assertions.passTest("Request Bind Page", "Bound the Renewal quote by uploading the due diligence file");

			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind request page",
					"Bind bequest page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home page", "Clicked on home button");

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber2);
			Assertions.passTest("Home page", "Searched submitted bind request successfullly");

			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral page", "Referral request approved successfully");

			// Verifying Handle in MMBU button should NOT be available on the Refer
			// Quote screen
			// Approve/Decline page
			Assertions.addInfo("Scenario 05",
					"Verifying Handle in MMBU button should NOT be available on the Refer Quote screen");
			Assertions.verify(approveDeclineQuotePage.handleInMMBUButton.checkIfElementIsPresent(), false,
					"Approve/Decline Quote Page",
					"Handle in MMBU button link is not displayed in Approve/Decline Quote page is verified", false,
					false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// click on decline without uploading due diligence file
			Assertions.addInfo("Scenatio 06",
					"Verifying the user is able to Decline the renewal quote referral when due diligence file is not uploaded for SC state renewal quote");
			requestBindPage.decline.scrollToElement();
			requestBindPage.decline.click();
			Assertions.passTest("Request Bind Page", "Clicked on Decline Button");
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchPolicy(policyNumber);

			Assertions.passTest("Home Page", "Searched the Policy " + policyNumber + " Successfully");
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page Loaded Successfully", false, false);

			// click on view active renewal
			policySummaryPage.viewActiveRenewal.scrollToElement();
			policySummaryPage.viewActiveRenewal.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Active Renewal link");

			// click on create another quote
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();
			Assertions.passTest("Account Overview Page", "Clicked on Create Another Quote");

			// select peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Asserting quote number
			String quoteNumber3 = accountOverviewPage.quoteNumber.getData().substring(1, 11);
			Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"Account Overview Page Loaded successfully. Renewal Requote Number is " + quoteNumber3, false,
					false);

			// click on issue quote
			accountOverviewPage.issueQuoteButton.scrollToElement();
			accountOverviewPage.issueQuoteButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Issue Quote Button");

			// Click on request bind button
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber3);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind Button");

			if (!accountOverviewPage.requestBind.checkIfElementIsPresent()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuote(quoteNumber3);
				accountOverviewPage.requestBind.scrollToElement();
				accountOverviewPage.requestBind.click();
			}

			// Enter Bind Details with out uploading file
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			if (requestBindPage.chooseFile.checkIfElementIsPresent()
					&& requestBindPage.chooseFile.checkIfElementIsDisplayed()) {
				if (!testData.get("FileNameToUpload").equals("")) {
					policyDocumentsPage.fileUpload(testData.get("FileNameToUpload"));
				}
			}
			requestBindPage.addContactInformation(testData);
			Assertions.passTest("Request bind page", "Bind details entered successfully");
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();
			confirmBindRequestPage.confirmBind();

			if (requestBindPage.chooseFile.checkIfElementIsPresent()
					&& requestBindPage.chooseFile.checkIfElementIsDisplayed()) {
				if (!testData.get("FileNameToUpload").equals("")) {
					policyDocumentsPage.fileUpload(testData.get("FileNameToUpload"));
				}
			}
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

			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind request page",
					"Bind bequest page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home page", "Clicked on home button");

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber3);
			Assertions.passTest("Home page", "Searched submitted bind request successfullly");

			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral page", "Referral request approved successfully");
			if (requestBindPage.chooseFile.checkIfElementIsPresent()
					&& requestBindPage.chooseFile.checkIfElementIsDisplayed()) {
				if (!testData.get("FileNameToUpload").equals("")) {
					policyDocumentsPage.fileUpload(testData.get("FileNameToUpload"));
				}
			}
			requestBindPage.approve.waitTillVisibilityOfElement(60);
			requestBindPage.approve.scrollToElement();
			requestBindPage.approve.click();
			Assertions.passTest("Request bind page", "Bind request approved successfully");
			if (requestBindPage.chooseFile.checkIfElementIsPresent()
					&& requestBindPage.chooseFile.checkIfElementIsDisplayed()) {
				if (!testData.get("FileNameToUpload").equals("")) {
					policyDocumentsPage.fileUpload(testData.get("FileNameToUpload"));
				}
			}

			if (requestBindPage.overrideEffectiveDate.checkIfElementIsPresent()
					&& requestBindPage.overrideEffectiveDate.checkIfElementIsDisplayed()) {
				requestBindPage.overrideEffectiveDate.scrollToElement();
				requestBindPage.overrideEffectiveDate.select();
				requestBindPage.approve.scrollToElement();
				requestBindPage.approve.click();
				requestBindPage.waitTime(2);// wait time is needed to load the page
			}

			// Get Renewal policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Renewal Policy Number is " + policyNumber, false, false);

			// click on view documents link
			policySummaryPage.viewDocuments.scrollToElement();
			policySummaryPage.viewDocuments.click();
			viewDocumentsPage.waitTime(5);

			// Go to homepage
			homePage.goToHomepage.click();
			homePage.searchPolicy(policyNumber);
			policySummaryPage.waitTime(5);

			// click on view documents link
			policySummaryPage.viewDocuments.scrollToElement();
			policySummaryPage.viewDocuments.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Documents link");
			Assertions.verify(policyDocumentsPage.addDocumentButton.checkIfElementIsDisplayed(), true,
					"View Documents Page", "View Documents Page loaded successfully", false, false);

			// Verifying the presence of due diligence document added
			Assertions.addInfo("Scenario 07", "Verifying the due diligence file presence under view documents");
			Assertions.verify(
					viewDocumentsPage.editUserDocuments.formatDynamicPath("Due Diligence").checkIfElementIsPresent(),
					true, "View Documents Page", "The Uploaded due diligence document displayed is verified", false,
					false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			policyDocumentsPage.backButton.scrollToElement();
			policyDocumentsPage.backButton.click();

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
			buildingPage.numOfStories.setData(testData.get("L1B1-BldgStories"));
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
			Assertions.addInfo("Scenario 08",
					"Verifying the Costcard message and Verifying the actual and expected cost card values Construction type: Fire Resistive, Occupancy type: Condo,Building Square Feet: 2000");
			Assertions.verify(
					buildingUnderminimumCost.costcardMessage
							.formatDynamicPath("Building value").checkIfElementIsDisplayed(),
					true, "Building Under Minimum Cost Page",
					"The Costcard message "
							+ buildingUnderminimumCost.costcardMessage.formatDynamicPath("Building value").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// Signing Out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC019 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC019 ", "Executed Successfully");
			}
		}
	}
}
