/** Program Description: To Verify default named storm deductible and premium override absence for Sinkhole/CGCC for Florida,minimum Coverage A of $250,000(outside Tricountry) and generate policy as producer
 *  Author			   : Yeshashwini
 *  Date of Creation   : 03/25/2021
 **/
package com.icat.epicenter.test.naho.regression.NSNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class NBFLTC002 extends AbstractNAHOTest {

	public NBFLTC002() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSNB/FL002.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		ReferralPage referralPage = new ReferralPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		ViewPolicySnapShot viewPolicySnapShot = new ViewPolicySnapShot();

		String quoteNumber;
		String policyNumber;
		int dataValue1 = 0;
		int dataValue2 = 1;
		String namedStorm = "3%";
		boolean isTestPassed = false;

		try {
			Assertions.addInfo("Scenario 01", "Verify Sinkhole Coverage not available for Counties as Producer");
			for (int i = 2; i < 7; i++) {
				int dataValuei = i;
				Map<String, String> testData = data.get(dataValuei);

				homePage.enterPersonalLoginDetails();
				// creating New account
				Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
						"Producer Home page loaded successfully", false, false);
				homePage.createNewAccountWithNamedInsured(testData, setUpData);
				Assertions.passTest("New Account", "New Account created successfully");

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
					Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
							"Prior Loss Page loaded successfully", false, false);
					priorLossPage.selectPriorLossesInformation(testData);
				}

				// Entering Create quote page Details
				Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
						"Create Quote Page loaded successfully", false, false);

				// Assert Sinkhole Coverage is not available for Counties
				Assertions.passTest("Create Quote Page",
						"The Address Entered is " + testData.get("L1D1-DwellingAddress") + ","
								+ testData.get("L1D1-DwellingCity") + "," + testData.get("ZipCode"));

				Assertions.passTest("Create Quote Page", "The Zipcode " + testData.get("ZipCode")
						+ " belongs to the county " + testData.get("CategoryOption"));

				Assertions.verify(createQuotePage.sinkholeArrow.checkIfElementIsPresent(), false, "Create Quote Page",
						"Sinkhole Coverage not available is verified for Producer", false, false);
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
			}
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// SignOut as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Producer successfully");

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");

			// Need to revisit this
			// Creating New Account
			Assertions.addInfo("Scenario 02", "Verify Sinkhole Coverage not available for Counties as USM");
			for (int i = 2; i < 7; i++) {
				int dataValuei = i;
				Map<String, String> testData = data.get(dataValuei);
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"USM Home Page loaded successfully", false, false);
				homePage.createNewAccountWithNamedInsured(testData, setUpData);
				Assertions.passTest("New Account", "New Account Created successfully");

				// Entering Zipcode
				Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
						"Eligibility Page loaded successfully", false, false);
				eligibilityPage.processSingleZip(testData);
				Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

				// Entering Location 1 Dwelling 1 Details
				Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling  Page",
						"Dwelling Page Loaded successfully", false, false);
				dwellingPage.enterDwellingDetailsNAHO(testData);
				Assertions.passTest("Dwelling Page", "Dwelling Details Entered successfully");

				// Entering prior loss details
				if (!testData.get("PriorLoss1").equals("")) {
					Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
							"Prior Loss Page loaded successfully", false, false);
					priorLossPage.selectPriorLossesInformation(testData);
				}

				// Entering Quote Details
				Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
						"Create Quote Page loaded successfully", false, false);

				// Assert Sinkhole Coverage is available for Counties
				Assertions.passTest("Create Quote Page",
						"The Address Entered is " + testData.get("L1D1-DwellingAddress") + ","
								+ testData.get("L1D1-DwellingCity") + "," + testData.get("ZipCode"));

				Assertions.passTest("Create Quote Page", "The Zipcode " + testData.get("ZipCode")
						+ " belongs to the county " + testData.get("CategoryOption"));

				Assertions.verify(createQuotePage.sinkholeArrow.checkIfElementIsPresent(), true, "Create Quote Page",
						"Sinkhole Coverage available is verified for USM", false, false);
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
			}

			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");
			// SignOut as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as USM successfully");

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");
			homePage.enterPersonalLoginDetails();
			Map<String, String> testData = data.get(dataValue1);

			// creating New account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

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
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			Assertions.addInfo("Scenario 03",
					"Verify default named storm deductible and Assert Absence of Sinkhole when Prior loss is adedd");
			Assertions.verify(createQuotePage.sinkholeArrow.checkIfElementIsPresent(), false, "Create Quote Page",
					"Sinkhole Coverage is not Available when Sinkhole Prior Loss is added is verified as Producer",
					false, false);

			// IO-20810
			Assertions.verify(createQuotePage.namedStormData.getData(), namedStorm, "Create Quote Page",
					"All other risks in FL, outside of Tri County, will have a minimum Named Storm deductible set to "
							+ createQuotePage.namedStormData.getData() + " is verified",
					false, false);

			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			Assertions.addInfo("Scenario 04",
					"Verify minimum Coverage A of $250,000 is required for risks located outside the Tri County and Assert Error message");
			Assertions.verify(createQuotePage.globalErr.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"A minimum Coverage A of $250,000 is required for risks located outside the Tri County is verified",
					false, false);

			Assertions.verify(createQuotePage.globalErr.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"The Message " + createQuotePage.globalErr.getData() + " displayed is verified", false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			testData = data.get(dataValue2);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// IO-20816
			// Asserting hard stop message when prior loss =sinkhole/CGCC
			// The message is The account is ineligible due to a Sinkhole or Catastrophic
			// Ground Cover Collapse reported loss at the property.
			Assertions.addInfo("Scenario 05", "Verify sinkhole/CGCC loss hard stop message");
			Assertions.verify(createQuotePage.globalErr.getData().contains("Sinkhole"), true, "Create quote page",
					"The Hard Stop Message " + createQuotePage.globalErr.getData()
							+ " displayed is verified for Producer",
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Click on Previous link
			createQuotePage.previous.scrollToElement();
			createQuotePage.previous.click();
			Assertions.verify(accountOverviewPage.deleteAccount.checkIfElementIsDisplayed(), true,
					"Account Overview Page ", "Account overview page loaded successfully", false, false);

			accountOverviewPage.priorLossEditLink.scrollToElement();
			accountOverviewPage.priorLossEditLink.click();
			Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior loss page loaded successfully", false, false);
			priorLossPage.lossesInThreeYearsNo.scrollToElement();
			priorLossPage.lossesInThreeYearsNo.click();
			priorLossPage.continueButton.scrollToElement();
			priorLossPage.continueButton.click();

			Assertions.verify(accountOverviewPage.deleteAccount.checkIfElementIsDisplayed(), true,
					"Account Overview Page ", "Account overview page loaded successfully", false, false);
			accountOverviewPage.quoteAccountButton.scrollToElement();
			accountOverviewPage.quoteAccountButton.click();
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.coverageADwelling.scrollToElement();
			createQuotePage.coverageADwelling.clearData();
			createQuotePage.coverageADwelling.appendData(testData.get("L1D1-DwellingCovA"));
			createQuotePage.coverageADwelling.tab();
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// Enter Referral Contact Details
			// testData = Data.get(dataValue1);
			if (referQuotePage.contactName.checkIfElementIsPresent()
					&& referQuotePage.contactName.checkIfElementIsDisplayed()) {
				referQuotePage.contactName.setData(testData.get("ProducerName"));
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.click();
				Assertions.passTest("Refer Quote Page", "Quote referred successfully");
				quoteNumber = referQuotePage.quoteNumberforReferral.getData();
				Assertions.passTest("Refer Quote Page ", "The Quote Number is " + quoteNumber);

				// SignOut as Producer
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Home Page", "Logged out as Producer successfully");

				// Sign in as USM
				loginPage.refreshPage();
				loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
				Assertions.passTest("Home Page", "Logged in as USM Successfully");

				// searching the quote number in grid and clicking on the quote
				// number
				// link
				homePage.searchReferral(quoteNumber);
				Assertions.passTest("Home Page", "Searched Submitted Quote " + quoteNumber + " successfullly");

				// approving referral
				referralPage.clickOnApprove();

				// click on approve in Approve Decline Quote page
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");

				// SignOut as USM
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Home Page", "Logged out as USM successfully");

				// Sign in as Producer
				loginPage.refreshPage();
				loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
				Assertions.passTest("Home Page", "Logged in as Producer successfully");

				homePage.enterPersonalLoginDetails();
				// Search for quote
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuoteByProducer(quoteNumber);

			}

			// getting quote number 1
			accountOverviewPage.quoteNumber.formatDynamicPath(1).waitTillVisibilityOfElement(60);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number : " + quoteNumber);

			// clicking on request bind in Account overview page
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Entering details in Underwriting Questions Page
			testData = data.get(dataValue1);
			Assertions.verify(underwritingQuestionsPage.saveButton.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered successfully");

			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);

			// verify the certification wordings
			Assertions.addInfo("Scenario 06", "Verify Due Diligence Message");
			Assertions.verify(requestBindPage.diligenceText.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Certification wording on bind " + requestBindPage.diligenceText.getData() + " present is verified",
					false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// verifying referral
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);

			// Verifying Subscription Agreement and Prior loss message as producer and its
			// referring to USM
			// Subscription Agreement needs USM approval.
			// Entered prior claims don't equal the number of claim from APlus

		//	Subscription Agreement (which can be part of a a signed quote) document needs to be approved before bind.
			Assertions.verify(bindRequestPage.referralMessages.formatDynamicPath(
					"Subscription Agreement (which can be part of a a signed quote) document needs to be approved before bind.")
					.getData().contains(
							"Subscription Agreement (which can be part of a a signed quote) document needs to be approved before bind."),
					true, "Bind Request Submitted Page",
					"The referral message is " + bindRequestPage.referralMessages.formatDynamicPath(
							"Subscription Agreement (which can be part of a a signed quote) document needs to be approved before bind.")
							.getData() + " referring to USM is verified",
					false, false);

			// sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged Out as Producer Successfully");

			// Login to USM account
			Assertions.verify(loginPage.signInButton.checkIfElementIsDisplayed(), true, "Login Page",
					"Login page loaded successfully", false, false);
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");

			// searching the quote number in grid and clicking on the quote number
			// link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Searched Submitted Bind Request successfully");

			// Approving pre bind documents
			accountOverviewPage.uploadPreBindApproveAsUSM();

			// approving referral
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			requestBindPage.approveRequestNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// getting the policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// Asserting Sinkhole and cgcc availability in quote document
			policySummaryPage.quoteNoLinkNAHO.scrollToElement();
			policySummaryPage.quoteNoLinkNAHO.click();

			Assertions.addInfo("Scenario 07", "Verify sinkhole/CGCC availability on View policy snapshot page");
			Assertions.verify(
					viewPolicySnapShot.endorsementValues.formatDynamicPath("22").getData().equals("Not Selected"), true,
					"Quote Document Page",
					"The Option " + viewPolicySnapShot.endorsementValues.formatDynamicPath("21").getData() + " "
							+ viewPolicySnapShot.endorsementValues.formatDynamicPath("22").getData() + " is verified",
					false, false);

			Assertions.verify(
					viewPolicySnapShot.endorsementValues.formatDynamicPath("24").getData().equals("Not Selected"), true,
					"Quote Document Page",
					"The Option " + viewPolicySnapShot.endorsementValues.formatDynamicPath("23").getData() + " "
							+ viewPolicySnapShot.endorsementValues.formatDynamicPath("24").getData() + " is verified",
					false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Clicking on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBFLTC002 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBFLTC002 ", "Executed Successfully");
			}
		}
	}
}
