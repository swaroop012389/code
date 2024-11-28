/** Program Description: Check if for all states EXCEPT Louisiana, Mississippi, Arizona, Nevada, and Utah the following line will be added as a bullet point under Subject To in the Terms and Conditions Section of commercial retail renewal quotes:
Completed and signed diligent effort form.
 *  Author			   : Pavan Mule
 *  Date of Creation   : 01/02/2022
 **/
package com.icat.epicenter.test.commercial.retailRegression;

import java.util.List;
import java.util.Map;

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
import com.icat.epicenter.pom.PolicyDocumentsPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC032 extends AbstractCommercialTest {

	public TC032() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID032.xls";
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
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		BindRequestSubmittedPage bindRequestSubmittedPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		Map<String, String> testData;
		boolean isTestPassed = false;

		try {
			// 0 = AL 1 = AZ 2=AR 3=FL_AOP 4=LA 5=MS 6=MO 7=NJ 8=NV 9=NC 10=SC
			// 11=TN 12=TX 13=UT
			for (int i = 0; i < 14; i++) {
				testData = data.get(i);

				// creating New account
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
						"Home page loaded successfully", false, false);
				homePage.createNewAccountWithNamedInsured(testData, setUpData);
				Assertions.passTest("New account", "New account created successfully");

				// Entering zipcode in Eligibility page
				Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility page",
						"Eligibility page loaded successfully", false, false);
				eligibilityPage.processSingleZip(testData);
				Assertions.passTest("Eligibility page", "Zipcode entered successfully");

				// Entering Location Details
				Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location page",
						"Location page loaded successfully", false, false);
				locationPage.enterLocationDetails(testData);

				// Enter Building Details
				//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building page",
						//"Building page loaded successfully", false, false);
				buildingPage.enterBuildingDetails(testData);

				// Selecting a peril
				if (!testData.get("Peril").equals("Quake")) {
					Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true,
							"Select peril page", "Select peril page loaded successfully", false, false);
					selectPerilPage.selectPeril(testData.get("Peril"));
				}

				// Entering Prior Losses
				if (!testData.get("PriorLoss1").equals("")) {
					Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true,
							"Prior loss page", "Prior loss page loaded successfully", false, false);
					priorLossesPage.selectPriorLossesInformation(testData);
				}

				// Entering Create quote page Details
				Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create quote page",
						"Create quote page loaded successfully", false, false);
				createQuotePage.enterQuoteDetailsCommercialNew(testData);
				Assertions.passTest("Create quote page", "quote details entered successfully");

				// getting the quote number
				Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
						"Account overview page", "Account overview page loaded successfully", false, false);
				quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
				Assertions.passTest("Account overview page", "Quote number :  " + quoteNumber);

				// Click on Request bind button
				accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

				// Enter Bind details
				Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request bind page",
						"Request bind page loaded successfully", false, false);
				requestBindPage.enterBindDetails(testData);
				Assertions.passTest("Request bind page", "Bind details entered successfully");
				if (bindRequestSubmittedPage.homePage.checkIfElementIsPresent()
						&& bindRequestSubmittedPage.homePage.checkIfElementIsDisplayed()) {

					// Go to HomePage
					homePage.goToHomepage.click();
					homePage.searchQuote(quoteNumber);
					Assertions.passTest("Home page", "Quote for referral is searched successfully");

					// Click on open referral
					accountOverviewPage.openReferralLink.waitTillPresenceOfElement(60);
					accountOverviewPage.openReferralLink.scrollToElement();
					accountOverviewPage.openReferralLink.click();

					// Approve Referral
					if (referralPage.pickUp.checkIfElementIsPresent()
							&& referralPage.pickUp.checkIfElementIsDisplayed()) {
						referralPage.pickUp.scrollToElement();
						referralPage.pickUp.click();
					}
					Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true,
							"Referral page", "Referral page loaded successfully", false, false);
					referralPage.clickOnApprove();
					Assertions.passTest("Referral page", "Referral request approved successfully");

					Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request bind page",
							"Request approval page loaded successfully", false, false);
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
					Assertions.passTest("Request bind page", "Bind request approved successfully");
				}

				// getting policy number
				Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true,
						"Policy summary page", "Policy summary page loaded successfully", false, false);
				policyNumber = policySummaryPage.policyNumber.getData();
				Assertions.passTest("Policy summary page", "Policy number :" + policyNumber);

				// click on renewal link
				policySummaryPage.renewPolicy.waitTillPresenceOfElement(60);
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();

				// Go to Home Page
				if (policySummaryPage.expaacMessage.checkIfElementIsPresent()
						&& policySummaryPage.expaacMessage.checkIfElementIsDisplayed()) {
					homePage.goToHomepage.scrollToElement();
					homePage.goToHomepage.click();
					homePage.expaccLink.scrollToElement();
					homePage.expaccLink.click();
					expaccInfoPage.enterExpaccInfo(testData, policyNumber);
					Assertions.passTest("ExpaccInfo page", "ExpaccInfo details entered successfully");

					// Go to Home Page
					homePage.goToHomepage.scrollToElement();
					homePage.goToHomepage.click();

					// Performing Renewal Searches
					homePage.searchPolicy(policyNumber);

					// clicking on renewal policy link
					policySummaryPage.renewPolicy.waitTillPresenceOfElement(60);
					policySummaryPage.renewPolicy.scrollToElement();
					policySummaryPage.renewPolicy.click();
					Assertions.passTest("Policy summary page", "Clicked on renewal link successfully");
				}
				if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
						&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
					policyRenewalPage.continueRenewal.scrollToElement();
					policyRenewalPage.continueRenewal.click();
				}

				if (policyRenewalPage.renewalYes.checkIfElementIsPresent()
						&& policyRenewalPage.renewalYes.checkIfElementIsDisplayed()) {
					policyRenewalPage.renewalYes.scrollToElement();
					policyRenewalPage.renewalYes.click();
				}

				// Getting renewal qoute number
				Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
						"Account overview page", "Account overview page loaded successfully", false, false);
				quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
				Assertions.passTest("Account overview page", "Renewal quote number :  " + quoteNumber);

				// Click on view/print full quote link
				accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
				accountOverviewPage.viewPrintFullQuoteLink.click();
				Assertions.passTest("Account overview page", "Clicked on View/Print Full quote link");

				// Asserting presence of Completed and signed diligent effort form ,except
				// Lousiana, Missippi, Arizona,Nevada and Utah
				Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
						"View/Print Full Quote Page", "View/Print Full Quote Page loaded successfully", false, false);
				Assertions.addInfo("Scenario 01",
						"Checking all states EXCEPT Louisiana, Mississippi, Arizona, Nevada, and Utah as a bullet point under Subject To in the Terms and Conditions Section of commercial retail renewal quote");
				if (testData.get("QuoteState").contains("AL") || testData.get("QuoteState").contains("AR")
						|| testData.get("QuoteState").contains("FL") || testData.get("QuoteState").contains("MO")
						|| testData.get("QuoteState").contains("NJ") || testData.get("QuoteState").contains("NC")
						|| testData.get("QuoteState").contains("SC") || testData.get("QuoteState").contains("TN")
						|| testData.get("QuoteState").contains("TX")) {
					Assertions.verify(
							viewOrPrintFullQuotePage.subjectToWordings.formatDynamicPath("diligent effort form")
									.checkIfElementIsPresent(),
							true, "View/Print Full quote page",
							"Completed and signed diligent effort form displayed for Quote " + quoteNumber
									+ " under subjectTo section is verified",
							false, false);
				} else {
					Assertions.verify(
							viewOrPrintFullQuotePage.subjectToWordings.formatDynamicPath("diligent effort form")
									.checkIfElementIsPresent(),
							false, "View/Print Full Quote page",
							"Completed and signed diligent effort form not displayed for Quote " + quoteNumber
									+ " under subjectTo section is verified",
							false, false);
				}

				Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.waitTime(3);

			}

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC032 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC032 ", "Executed Successfully");
			}
		}
	}

}
