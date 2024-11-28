/** Program Description: To Verify AOP and AOWH deductibles are equal and account notes message in notebar and Due Diligence message in view print full quote page
 *  Author			   : Sowndarya NH
 *  Date of Creation   : 03/30/2021
 **/
package com.icat.epicenter.test.naho.regression.NSNB;

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
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class NBFLTC006 extends AbstractNAHOTest {

	public NBFLTC006() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSNB/FL006.xls";
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
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		ViewPolicySnapShot viewPolicySnapShot = new ViewPolicySnapShot();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();

		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
		int dataValue4 = 3;
		Map<String, String> testData = data.get(dataValue1);
		boolean isTestPassed = false;

		try {
			// creating New account
			testData = data.get(dataValue3);
			Assertions.addInfo("Scenario 01",
					"Verifying Sinkhole/CGCC is not available for Sinkhole known Address for Producer and check producer is able to create quote");
			homePage.enterPersonalLoginDetails();
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
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			Assertions.verify(createQuotePage.sinkholeArrow.checkIfElementIsPresent(), false, "Create Quote Page",
					"Sinkhole/CGCC is not available for Sinkhole known Address is verified as Producer", false, false);

			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on Get A Quote button");
			testData = data.get(dataValue1);
			if (referQuotePage.contactName.checkIfElementIsPresent()
					&& referQuotePage.contactName.checkIfElementIsDisplayed()) {
				Assertions.verify(referQuotePage.referQuote.checkIfElementIsDisplayed(), true, "Refer Quote Page",
						"Refer Quote Page loaded successfully", false, false);
				// Enter Referral Contact Details
				referQuotePage.contactName.setData(testData.get("ProducerName"));
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.click();
				Assertions.passTest("Refer Quote Page", "Quote referred successfully");
				String quoteNumber = referQuotePage.quoteNumberforReferral.getData();
				Assertions.passTest("Refer Quote Page ", "The Quote Number is " + quoteNumber);

				// SignOut as Producer
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Home Page", "Logged out as Producer successfully");
				testData = data.get(dataValue3);

				// Sign in as USM
				loginPage.refreshPage();
				loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
				Assertions.passTest("Home Page", "Logged in as USM Successfully");

				// searching the quote number in grid and clicking on the quote
				// number
				// link
				homePage.searchQuote(quoteNumber);
				Assertions.passTest("Home Page", "Searched Submitted Quote " + quoteNumber + " successfullly");

				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();

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

				// Search for quote
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuoteByProducer(quoteNumber);

			}

			String quoteNumber = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page",
					"Account Overview Page Loaded successfully.Quote Number is : " + quoteNumber);
			Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Producer is able to create Quote is verified", false, false);

			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// SignOut as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Producer successfully");

			// Sign in as USM
			Assertions.addInfo("Scenario 02", "Verify Monroe County zipcode is not Eligible");
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");

			// Creating New Account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"USM Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account Created successfully");

			// Entering Zipcode
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			testData = data.get(dataValue2);
			if (!testData.get("ZipCode").equals("")) {
				eligibilityPage.zipCode1.setData(testData.get("ZipCode"));
				eligibilityPage.riskAppliedYes.waitTillElementisEnabled(60);
				Assertions.passTest("Eligibility",
						"Zip code entered for Monroe County is " + eligibilityPage.zipCode1.getData());
			}

			// Assert Hard stop message
			Assertions.verify(eligibilityPage.zipCodeClosedMsg.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"The Zipcode closed Message " + eligibilityPage.zipCodeClosedMsg.getData()
							+ " displayed is verified",
					false, false);

			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			testData = data.get(dataValue1);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling  Page",
					"Dwelling Page Loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling Details Entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Verifying Sinkhole is availability for USM
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			Assertions.addInfo("Scenario 03",
					"Verifying Sinkhole is available for Sinkhole known address and verify aowh and AOP deductiles are equal");
			Assertions.verify(createQuotePage.sinkholeArrow.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"For Sinkhole Known Address Sinkhole Coverage is available for USM is verified", false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Entering Quote Details
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Adding Policy fee and Inspection fee
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			String quoteNumber1 = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "Quote Number 1 is : " + quoteNumber1);
			Assertions.addInfo("Scenario 04",
					"Verifying Alt quote values,Account Notes message on Account overview page");
			for (int i = 2; i < 5; i++) {
				Assertions.verify(
						accountOverviewPage.altQuoteOptEarthquakeDed.formatDynamicPath(i).checkIfElementIsDisplayed(),
						true, "Account Overview Page",
						"The " + accountOverviewPage.altQuoteOptEarthquakeDed.formatDynamicPath("1").getData()
								+ " Value : "
								+ accountOverviewPage.altQuoteOptEarthquakeDed.formatDynamicPath(i).getData()
								+ " displayed is verified",
						false, false);
			}

			for (int i = 2; i < 5; i++) {
				Assertions.verify(
						accountOverviewPage.altQuoteOptChosenCoverageOption.formatDynamicPath(i)
								.checkIfElementIsDisplayed(),
						true, "Account Overview Page",
						"The " + accountOverviewPage.altQuoteOptChosenCoverageOption.formatDynamicPath("1").getData()
								+ " Value : "
								+ accountOverviewPage.altQuoteOptChosenCoverageOption.formatDynamicPath(i).getData()
								+ " displayed is verified",
						false, false);
			}
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Click on View print full quote link
			Assertions.addInfo("Scenario 05", "Verifying Due diligence wordings,labels in Ciew print full quote page");
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print Full quote link");

			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page ", "View/Print Full Quote Page loaded successfully", false, false);

			Assertions.verify(viewPolicySnapShot.endorsementValues.formatDynamicPath("22").getData().equals("Included"),
					true, "View/Print Full Quote Page",
					"The Option " + viewPolicySnapShot.endorsementValues.formatDynamicPath("21").getData() + " "
							+ viewPolicySnapShot.endorsementValues.formatDynamicPath("22").getData() + " is verified",
					false, false);

			Assertions.verify(
					viewPolicySnapShot.endorsementValues.formatDynamicPath("24").getData().equals("Not Available"),
					true, "View/Print Full Quote Page",
					"The Option " + viewPolicySnapShot.endorsementValues.formatDynamicPath("23").getData() + " "
							+ viewPolicySnapShot.endorsementValues.formatDynamicPath("24").getData() + " is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.declinationsData
							.formatDynamicPath("diligent effort").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The Due Diligence Wordings "
							+ viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("diligent effort").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Declining Company")
							.getNoOfWebElements(),
					3, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Declining Company").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("NAIC").getNoOfWebElements(), 3,
					"View/Print Full Quote Page",
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("NAIC").getData()
							+ "  displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Reason for Declination")
							.getNoOfWebElements(),
					3, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Reason for Declination").getData()
							+ "  displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Date Declined").getNoOfWebElements(),
					3, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Date Declined").getData()
							+ "  displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Producing Agent Name")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Producing Agent Name")
									.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Producing Agent Name").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Producing Agent Signature")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Producing Agent Signature")
									.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Producing Agent Signature").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Date").checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Date")
									.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Date").getData()
							+ " displayed is verified",
					false, false);
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Click on edit deductibles and create another quote
			Assertions.addInfo("Scenario 6", "Verify the Premium if sinkhole is added and removed");
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Deductibles and limits");
			testData = data.get(dataValue2);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Adding Policy fee and Inspection fee
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			String quoteNumber2 = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "Quote Number 2 is : " + quoteNumber2);

			// Click on View print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print Full quote link");

			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page ", "View/Print Full Quote Page loaded successfully", false, false);

			Assertions.verify(
					viewPolicySnapShot.endorsementValues.formatDynamicPath("22").getData().equals("Not Selected"), true,
					"View/Print Full Quote Page",
					"The Option " + viewPolicySnapShot.endorsementValues.formatDynamicPath("21").getData() + " "
							+ viewPolicySnapShot.endorsementValues.formatDynamicPath("22").getData() + " is verified",
					false, false);

			viewOrPrintFullQuotePage.backButton.click();

			// Click on Quote number 1
			accountOverviewPage.quoteLink.formatDynamicPath("1").scrollToElement();
			accountOverviewPage.quoteLink.formatDynamicPath("1").click();
			Assertions.passTest("Account Overview Page", "Clicked on Quote 1 link");

			// Assert Premium if sinkhole is added
			Assertions.verify(accountOverviewPage.quoteOptionsTotalPremium.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "The Total Premium and ICAT Fees for Sinkhole Included is "
							+ accountOverviewPage.quoteOptionsTotalPremium.getData(),
					false, false);

			accountOverviewPage.quoteLink.formatDynamicPath("2").scrollToElement();
			accountOverviewPage.quoteLink.formatDynamicPath("2").click();
			Assertions.passTest("Account Overview Page", "Clicked on Quote 2 link");

			// Assert Premium if sinkhole is not added
			Assertions.verify(accountOverviewPage.quoteOptionsTotalPremium.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "The Total Premium and ICAT Fees for Sinkhole Not Included is "
							+ accountOverviewPage.quoteOptionsTotalPremium.getData(),
					false, false);

			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");
			// Clicking on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// ----Added IO-21660----

			// Login as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as Producer Successfully");

			testData = data.get(dataValue1);
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			testData = data.get(dataValue4);
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
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on Get A Quote button");

			Assertions.addInfo("Scenario 08",
					"Verifying when the quote with Tile/Clay Roof for 16-20 years,the roof coverage is excluded");
			Assertions.verify(
					createQuotePage.warningMessages
							.formatDynamicPath("Roof Coverage Excluded.").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Roof age warning "
							+ createQuotePage.warningMessages.formatDynamicPath("Roof Coverage Excluded.").getData()
							+ " diaplyed is verified",
					false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Below code adding because of quote is referring because of modeling service
			// down message
			if (referQuotePage.referQuote.checkIfElementIsPresent()
					&& referQuotePage.referQuote.checkIfElementIsDisplayed()) {
				referQuotePage.contactEmail.scrollToElement();
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.scrollToElement();
				referQuotePage.referQuote.click();
				Assertions.passTest("Refer Quote Page", "Quote referred successfully");
				quoteNumber = referQuotePage.quoteNumberforReferral.getData();
				Assertions.passTest("Refer Quote Page", "The Quote Number is " + quoteNumber);

				// SignOut as Producer
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Home Page", "Logged out as Producer successfully");

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

				// homePage.searchReferral(quoteNumber);
				homePage.searchQuote(quoteNumber);
				Assertions.passTest("Home Page", "Searched Submitted Quote" + quoteNumber + " successfullly");

				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();

				// approving referral
				Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
						"Referral page loaded successfully", false, false);
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

				// Handling the pop up
				homePage.enterPersonalLoginDetails();

				// Search for quote
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuoteByProducer(quoteNumber);
				Assertions.passTest("Home Page", "Searched the Quote " + quoteNumber + " sucessfully");

			}
			// Ended

			// Assert the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			String quoteNumber3 = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "Quote Number 3 is : " + quoteNumber3);

			// Click on View print full quote link
			Assertions.addInfo("Scenario 09", "Verifying roof coverage is excluded on view print full quote page");
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print Full quote link");

			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page ", "View/Print Full Quote Page loaded successfully", false, false);

			Assertions.verify(viewPolicySnapShot.endorsementValues.formatDynamicPath("34").getData().equals("Excluded"),
					true, "View/Print Full Quote Page",
					"The Option " + viewPolicySnapShot.endorsementValues.formatDynamicPath("33").getData() + " "
							+ viewPolicySnapShot.endorsementValues.formatDynamicPath("34").getData() + " is verified",
					false, false);

			// Click on back button
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBFLTC006 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBFLTC006 ", "Executed Successfully");
			}
		}
	}

}
