/** Program Description: Create an AOP Policy and renew it. Adding the Ticket IO-20569 for team referral
 *  Author			   :  Vinay
 *  Date of Creation   : 11/20/2019
 **/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BrokerOfRecordPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.GLInformationPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.ModifyForms;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC039 extends AbstractCommercialTest {

	public TC039() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID039.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Pages
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
		BrokerOfRecordPage brokerOfRecordPage = new BrokerOfRecordPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		ModifyForms modifyForms = new ModifyForms();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();

		// Initializing variables
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

			// Adding the CR IO-20569
			/*
			 * Assertions.addInfo("Home Page",
			 * "Verifying the presence of Data after selecting the filter Option"); for (int
			 * i = 0; i <= 4; i++) { int dataValuei = i; HashMap<String, String> testDatai =
			 * data.get(dataValuei); homePage.teamReferralFilter.scrollToElement();
			 * homePage.teamReferralFilter.click();
			 * homePage.teamReferralFilterOption.formatDynamicPath(testDatai.get(
			 * "ReferralOption")) .waitTillVisibilityOfElement(Duration.ofSeconds(60));
			 * homePage.teamReferralFilterOption.formatDynamicPath(testDatai.get(
			 * "ReferralOption")).scrollToElement();
			 * homePage.teamReferralFilterOption.formatDynamicPath(testDatai.get(
			 * "ReferralOption")).click(); Assertions.passTest("Home Page",
			 * "The Team Referral filter Option selected is " +
			 * testDatai.get("ReferralOption")); Assertions.verify(
			 * homePage.teamReferralsResultTable.formatDynamicPath(testDatai.get(
			 * "ReferralOption"), 1) .checkIfElementIsDisplayed(), true, "Home Page",
			 * "The Team Referrals Result table displayed the data is verified", false,
			 * false); homePage.refreshPage(); }
			 */
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
			createQuotePage.enterDeductiblesCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Adding the CR IO-19734
			// Click on Modify form button
			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();
			Assertions.passTest("Create Quote Page", "Clicked on Modify Forms Button");

			Assertions.verify(modifyForms.override.checkIfElementIsDisplayed(), true, "Modify Forms Page",
					"Modify Forms Page Loaded successfully", false, false);
			Assertions.addInfo("Modify forms page",
					"Verifying the Special Conditions of Wind Coverage Form is selected by default");
			modifyForms.specialConditionsAOP.waitTillPresenceOfElement(60);
			modifyForms.specialConditionsAOP.waitTillVisibilityOfElement(60);
			Assertions.verify(modifyForms.specialConditionsAOP.checkIfElementIsSelected(), true, "Modify Forms Page",
					"Special Conditions of Wind Coverage Form Selected by default is verified", false, false);

			// Click on override
			modifyForms.override.scrollToElement();
			modifyForms.override.click();

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Click on View print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print Full Quote link");

			Assertions.addInfo("View/Print Full Quote Page",
					"Verifying the presence of Wind Form 60  in Quote Document");
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page", "View/Print Full Quote Page Loaded successfully", false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.forms.formatDynamicPath("Wind Form 60").checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page",
					"The form " + viewOrPrintFullQuotePage.forms.formatDynamicPath("Wind Form 60").getData()
							+ " diaplyed is verified",
					false, false);

			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// click on request bind
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
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
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

			// Adding the CR IO-18569
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
			policySummarypage.renewPolicy.scrollToElement();
			policySummarypage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy link");

			// Click on continue
			if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
					&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
				policyRenewalPage.continueRenewal.scrollToElement();
				policyRenewalPage.continueRenewal.click();
			}

			// click on Yes
			if (policyRenewalPage.yesButton.checkIfElementIsPresent()
					&& policyRenewalPage.yesButton.checkIfElementIsDisplayed()) {
				policyRenewalPage.yesButton.scrollToElement();
				policyRenewalPage.yesButton.click();
			}

			// clicking on producer link to process AOR in account overview page
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// getting renewal quote number
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "The Renewal Quote Number :  " + quoteNumber);

			// Clicking on view/Print full quote
			accountOverviewPage.viewPrintFullQuoteLink.waitTillVisibilityOfElement(60);
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View/Print full quote link");

			// Adding the CR IO-19734
			// Asserting the presence of form 60 for renewal quote
			Assertions.addInfo("View/Print Full Quote Page",
					"Verifying the presence of Wind Form 60 in Quote Document for renewal quote");
			Assertions.verify(
					viewOrPrintFullQuotePage.forms.formatDynamicPath("Wind Form 60").checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page",
					"The form " + viewOrPrintFullQuotePage.forms.formatDynamicPath("Wind Form 60").getData()
							+ " diaplyed is verified",
					false, false);

			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// Click on create another quote to create renewal requote
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();
			Assertions.passTest("Account Overview Page", "Clicked on Create another quote button");

			// selecting peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterDeductiblesCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Click on Modify form button
			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();
			Assertions.passTest("Create Quote Page", "Clicked on Modify Forms Button");

			Assertions.verify(modifyForms.override.checkIfElementIsDisplayed(), true, "Modify Forms Page",
					"Modify Forms Page Loaded successfully", false, false);
			Assertions.addInfo("Modify forms page",
					"Verifying the Special Conditions of Wind Coverage Form is selected by default for renewal requote");
			modifyForms.specialConditionsAOP.waitTillPresenceOfElement(60);
			modifyForms.specialConditionsAOP.waitTillVisibilityOfElement(60);
			Assertions.verify(modifyForms.specialConditionsAOP.checkIfElementIsSelected(), true, "Modify Forms Page",
					"Special Conditions of Wind Coverage Form Selected by default is verified", false, false);

			// Click on override
			modifyForms.override.scrollToElement();
			modifyForms.override.click();

			// click on continue button
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// getting renewal quote number
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "The Renewal Requote Number :  " + quoteNumber);

			// Clicking on view/Print full quote
			accountOverviewPage.viewPrintFullQuoteLink.waitTillVisibilityOfElement(60);
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View/Print full quote link");

			// Adding the CR IO-19734
			// Asserting the presence of form 60 for renewal quote
			Assertions.addInfo("View/Print Full Quote Page",
					"Verifying the presence of Wind Form 60 in Quote Document for renewal requote");
			Assertions.verify(
					viewOrPrintFullQuotePage.forms.formatDynamicPath("Wind Form 60").checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page",
					"The form " + viewOrPrintFullQuotePage.forms.formatDynamicPath("Wind Form 60").getData()
							+ " diaplyed is verified",
					false, false);

			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// click on producer link
			Assertions.addInfo("Account Overview Page", "Processing the BOR");
			accountOverviewPage.producerLink.scrollToElement();
			accountOverviewPage.producerLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Producer Link");

			// changing the producer details
			Assertions.verify(brokerOfRecordPage.processBOR.checkIfElementIsDisplayed(), true, "Broker of Record Page",
					"Broker of Record Page loaded successfully", false, false);

			// enter new producer number
			brokerOfRecordPage.newProducerNumber.scrollToElement();
			brokerOfRecordPage.newProducerNumber.setData(testData.get("NewBORProducerNumber"));
			Assertions.passTest("Broker of Record Page",
					"New Producer Number is : " + brokerOfRecordPage.newProducerNumber.getData());
			brokerOfRecordPage.borStatusArrow.scrollToElement();
			brokerOfRecordPage.borStatusArrow.click();
			brokerOfRecordPage.borStatusOption.formatDynamicPath(testData.get("BORStatus"))
					.waitTillVisibilityOfElement(60);
			brokerOfRecordPage.borStatusOption.formatDynamicPath(testData.get("BORStatus")).scrollToElement();
			brokerOfRecordPage.borStatusOption.formatDynamicPath(testData.get("BORStatus")).click();

			// click on process BOR
			brokerOfRecordPage.processBOR.scrollToElement();
			brokerOfRecordPage.processBOR.click();

			// click on close BOR
			brokerOfRecordPage.closeBORPage.scrollToElement();
			brokerOfRecordPage.closeBORPage.click();
			Assertions.passTest("Broker Of Record Page", "Changed the Producer Number successfully");

			// Asserting the View renewal documents not present
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			Assertions.verify(accountOverviewPage.viewRenewalDocuments.checkIfElementIsPresent(), false,
					"Account Overview Page", "View Renewal Documents Button not present is verified", false, false);

			// click on release renewal to producer button
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Renewal to Producer button");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Renewal Quote Number :  " + quoteNumber);

			// Click on request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
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
			Assertions.passTest("Policy Summary Page", "Renewal Policy Number is : " + policyNumber);

			// Adding code for IO-19260
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

			// click on renew policy
			Assertions.addInfo("Policy Summary Page", "Renew the Renewed Policy");
			policySummarypage.renewPolicy.scrollToElement();
			policySummarypage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy link");

			// Click on continue
			if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
					&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
				policyRenewalPage.continueRenewal.scrollToElement();
				policyRenewalPage.continueRenewal.click();
			}

			// click on Yes
			if (policyRenewalPage.yesButton.checkIfElementIsPresent()
					&& policyRenewalPage.yesButton.checkIfElementIsDisplayed()) {
				policyRenewalPage.yesButton.scrollToElement();
				policyRenewalPage.yesButton.click();
			}

			// clicking on producer link to process AOR in account overview page
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// getting renewal quote number
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "The Renewal Quote Number :  " + quoteNumber);

			// click on producer link
			Assertions.addInfo("Account Overview Page", "Processing the BOR");
			accountOverviewPage.producerLink.scrollToElement();
			accountOverviewPage.producerLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Producer Link");

			// changging the producer details
			testData = data.get(data_Value2);
			Assertions.verify(brokerOfRecordPage.processBOR.checkIfElementIsDisplayed(), true, "Broker of Record Page",
					"Broker of Record Page loaded successfully", false, false);
			brokerOfRecordPage.newProducerNumber.scrollToElement();
			brokerOfRecordPage.newProducerNumber.setData(testData.get("NewBORProducerNumber"));
			Assertions.passTest("Broker of Record Page",
					"New Producer Number is : " + brokerOfRecordPage.newProducerNumber.getData());
			brokerOfRecordPage.borStatusArrow.scrollToElement();
			brokerOfRecordPage.borStatusArrow.click();
			testData = data.get(data_Value1);
			brokerOfRecordPage.borStatusOption.formatDynamicPath(testData.get("BORStatus"))
					.waitTillVisibilityOfElement(60);
			brokerOfRecordPage.borStatusOption.formatDynamicPath(testData.get("BORStatus")).scrollToElement();
			brokerOfRecordPage.borStatusOption.formatDynamicPath(testData.get("BORStatus")).click();

			// click on process BOR
			brokerOfRecordPage.processBOR.scrollToElement();
			brokerOfRecordPage.processBOR.click();

			// Click on close BOR
			brokerOfRecordPage.closeBORPage.scrollToElement();
			brokerOfRecordPage.closeBORPage.click();
			Assertions.passTest("Broker Of Record Page", "Changed the Producer Number successfully");

			// sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 39", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 39", "Executed Successfully");
			}
		}
	}
}
