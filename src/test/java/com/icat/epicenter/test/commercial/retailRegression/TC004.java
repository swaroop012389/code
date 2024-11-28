/** Program Description:  Check that the Commercial retail agents will not be able to add their own fees and Added CR IO-19987 and Added IO-20951 and Check the premium adjustment that is applied to the New Business  will apply to the endorsement.
 *  Author			   : Sowndarya
 *  Date of Creation   : 19/07/2021
 **/
package com.icat.epicenter.test.commercial.retailRegression;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC004 extends AbstractCommercialTest {

	public TC004() {
		super(LoginType.RETAILPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID004.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		LoginPage loginPage = new LoginPage();

		// Initializing variables
		String policyNumber;
		String quoteNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		String premiumFeeTx;
		String premiumFeeEndTx;
		String premiumFeeEndAnnual;
		double calAnnualPremiumFee;
		Map<String, String> testData = data.get(data_Value1);

		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Asserting User preference icon is not present in home page
			Assertions.addInfo("Scenario 01", "Assert the Presence of Userpreference icon on Producer Homepage");
			Assertions.verify(homePage.userPreferences.checkIfElementIsDisplayed(), true, "Home Page",
					"User Preference icon not present is verified", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// creating New account
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

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Prior Losses
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);

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

			// Asserting Edit fees icon not present on account overview page
			Assertions.addInfo("Scenario 02", "Assert the absence of Edit Fees icon");
			Assertions.verify(accountOverviewPage.editFees.checkIfElementIsPresent(), false, "Account Overview Page",
					"Edit Fees icon not present is verified", false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Added below code CR IO-19987
			// log out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home  Page ", "SignOut as Producer successfully");

			// Sign in as usm
			Assertions.passTest("Login Page", "Login Page loaded successfully");
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login", "Logged in as USM successfully");

			// creating New account
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

			// Click on add building
			locationPage.addBuildingsButton.scrollToElement();
			locationPage.addBuildingsButton.click();
			Assertions.verify(buildingPage.reviewBuilding.checkIfElementIsDisplayed(), true, "Building Page",
					"Building Page loaded successfully", false, false);

			// Click on building occupancy link
			buildingPage.buildingOccupancyLink.scrollToElement();
			buildingPage.buildingOccupancyLink.click();
			Assertions.passTest("Building Page", "Clicked on Building occupancy link successfully");

			// Added Below code for IO-20951
			// Verifying absence of secondary occupancy percent field, when secondary
			// occupancy is "None"
			Assertions.addInfo("Scenario 03",
					"Verifying absence of secondary occupancy percent field, when secondary occupancy is 'None'");
			Assertions.verify(buildingPage.secondaryPercentOccupied.checkIfElementIsDisplayed(), false, "Building page",
					"Secondary occupancy percent field  not displayed", false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");
			// IO-20951 Ended

			// Click on more than31% occupied link
			buildingPage.MoreThan31occupied.scrollToElement();
			buildingPage.MoreThan31occupied.click();

			// Verifying presence of Building that are occupied at 31% more or less are
			// considered vacant text
			buildingPage.MoreThan31orLessThanoccupied
					.formatDynamicPath("Buildings that are occupied at 31% or less are considered vacant.")
					.waitTillPresenceOfElement(60);
			buildingPage.MoreThan31orLessThanoccupied
					.formatDynamicPath("Buildings that are occupied at 31% or less are considered vacant.")
					.waitTillVisibilityOfElement(60);
			buildingPage.MoreThan31orLessThanoccupied
					.formatDynamicPath("Buildings that are occupied at 31% or less are considered vacant.")
					.scrollToElement();
			Assertions.addInfo("Scenario 04",
					"Verifying presence of Building that are occupied at 31% more or less are considered vacant text");
			Assertions.verify(
					buildingPage.MoreThan31orLessThanoccupied
							.formatDynamicPath("Buildings that are occupied at 31% or less are considered vacant.")
							.checkIfElementIsPresent()
							&& buildingPage.MoreThan31orLessThanoccupied
									.formatDynamicPath(
											"Buildings that are occupied at 31% or less are considered vacant.")
									.checkIfElementIsDisplayed(),
					true, "Building Page",
					"Building that are occupied at 31% more or less are considered vacant text present is verified, The text is "
							+ buildingPage.MoreThan31orLessThanoccupied
									.formatDynamicPath(
											"Buildings that are occupied at 31% or less are considered vacant.")
									.getData(),
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Close the popup
			buildingPage.CloseButton.formatDynamicPath(1).waitTillPresenceOfElement(60);
			buildingPage.CloseButton.formatDynamicPath(1).waitTillVisibilityOfElement(60);
			buildingPage.CloseButton.formatDynamicPath(1).scrollToElement();
			buildingPage.CloseButton.formatDynamicPath(1).click();

			// Click on home icon
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.passTest("Home Page", "Clicked on home icon successfully");

			// creating New account
			testData = data.get(data_Value1);
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

			// selecting peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
				Assertions.passTest("Select Peril Page", "Peril selected successfully");
			}

			// enter prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
				Assertions.passTest("Prior Loss Page", "Prior loss details entered successfully");
			}

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

			// Assertion of premium Before changing the premium
			Assertions.addInfo("Scenario 06", "Asserting the Premium value before changing the premium");
			Assertions.verify(accountOverviewPage.premiumValue.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"The Premium Before Changing is " + accountOverviewPage.premiumValue.getData(), false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// click on Request bind
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
			homePage.searchReferral(quoteNumber);
			Assertions.passTest("Home Page", "Searched Submitted Bind Request successfullly");

			// approving referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
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

			premiumFeeTx = policySummarypage.PremiumFee.formatDynamicPath(1).getData().replace("$", "").replace(",", "")
					.replace(".00", "");

			// click on Endorse PB link
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// click on ok
			if (endorsePolicyPage.okButton.checkIfElementIsPresent()
					&& endorsePolicyPage.okButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.okButton.scrollToElement();
				endorsePolicyPage.okButton.click();
			}

			// setting Endorsement Effective Date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// click on change coverage options link
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Coverage Options link");

			// entering details in create quote page
			testData = data.get(data_Value2);
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page loaded successfully", false, false);
			createQuotePage.editDeductiblesCommercialPNB(testData);
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			Assertions.passTest("Create Quote Page", "Details modified successfully");

			// Click on next button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on next button successfully");

			// Getting transaction premium and annual premium from endorse policy page
			premiumFeeEndTx = endorsePolicyPage.transactionPremium.getData().replace("$", "").replace(",", "");
			premiumFeeEndAnnual = endorsePolicyPage.transactionPremiumFee.formatDynamicPath(3).getData()
					.replace("$", "").replace(",", "");
			double d_premiumFeeAnual = Double.parseDouble(premiumFeeEndAnnual);
			calAnnualPremiumFee = Double.parseDouble(premiumFeeEndTx) + Double.parseDouble(premiumFeeTx);

			// Verifying and asserting the Transaction Premium is applied to Updated Premium
			// under Annual Premium column on endorse policy page
			Assertions.addInfo("Scenario 10",
					"Verifying and asserting the Transaction Premium is applied to Updated Premium  under Annual Premium column");
			if (Precision.round(
					Math.abs(Precision.round(d_premiumFeeAnual, 2) - Precision.round(calAnnualPremiumFee, 2)),
					2) < 1.00) {
				Assertions.passTest("Endorse Policy Page",
						"Actual and Calculated Annual premium fee both are same actual annual premium fee is "
								+ d_premiumFeeAnual);
				Assertions.passTest("Endorse Policy Page", "Calculated annual premium fee is " + calAnnualPremiumFee);

			} else {
				Assertions.verify(d_premiumFeeAnual, calAnnualPremiumFee, "Endorse Policy Page",
						"Actual and Calculated premium fees are not matching", false, false);
			}
			Assertions.addInfo("Scenario 10", "Scenario 10 Ended");

			// Click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on complete button successfully");

			// Click on close button
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on close button successfully");

			// Verifying transaction premium fee from endorse policy page and transaction
			// premium fee from policy summary page
			premiumFeeTx = policySummarypage.PremiumFee.formatDynamicPath(1).getData().replace("$", "").replace(",", "")
					.replace(".00", "");
			Assertions.addInfo("Scenario 11", "Verifying transaction premium fee");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(premiumFeeTx), 2)
					- Precision.round(Double.parseDouble(premiumFeeEndTx), 2)), 2) < 1.00) {
				Assertions.passTest("Policy Summary Page",
						"Actual and Expected transaction premium fee bothe are same actual transaction premium fees is $"
								+ premiumFeeTx);
				Assertions.passTest("Policy Summary Page", "Expected transaction premium fee is $" + premiumFeeEndTx);

			} else {
				Assertions.verify(premiumFeeTx, premiumFeeEndTx, "Policy Summary Page",
						"Actual and Expected transaction premium fees are not matching", false, false);
			}
			Assertions.addInfo("Scenario 11", "Scenario 11 Ended");

			// Verifying and asserting actual and calculated annual premium fee from policy
			// summary page
			String annualPremiumFee = policySummarypage.PremiumFee.formatDynamicPath(2).getData().replace("$", "")
					.replace(",", "").replace(".00", "");
			Assertions.addInfo("Scenario 12", "Verifying annual premium fee");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(annualPremiumFee), 2)
					- Precision.round((calAnnualPremiumFee), 2)), 2) < 1.00) {
				Assertions.passTest("Policy Summary Page",
						"Actual and Expected annual premium fee bothe are same actual annual premium fees is $"
								+ annualPremiumFee);
				Assertions.passTest("Policy Summary Page", "Expected annual premium fee is $" + calAnnualPremiumFee);

			} else {
				Assertions.verify(annualPremiumFee, calAnnualPremiumFee, "Policy Summary Page",
						"Actual and Expected annual premium fees are not matching", false, false);
			}
			Assertions.addInfo("Scenario 12", "Scenario 12 Ended");

			// sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC004 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC004 ", "Executed Successfully");
			}
		}
	}
}
