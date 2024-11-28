/** Program Description: Assert the Error messages when the building cladding is Exterior Insulating Finishing Systems (EIFS) and Roof age is Older Than 25 Years and As a USM, check if the Request Premium Change button is available and is working as expected on Renewal.
 *  Author			   : Sowndarya
 *  Date of Modified   : 09/27/2019
**/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExistingAccountPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC075 extends AbstractCommercialTest {

	public TC075() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID075.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the Pages
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		BuildingPage buildingPage = new BuildingPage();
		LocationPage locationPage = new LocationPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ExistingAccountPage existingAccountPage = new ExistingAccountPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyrenewalPage = new PolicyRenewalPage();

		// Initializing the variables
		String policyNumber;
		String quoteNumber;
		String premiumFee;
		String premiumFeeTx;
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
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);
			Assertions.passTest("Location Page", "Location details entered successfully");

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			locationPage.addBuildingsButton.scrollToElement();
			locationPage.addBuildingsButton.click();
			buildingPage.addBuildingDetails(testData, 1, 1);
			buildingPage.addBuildingOccupancy(testData, 1, 1);
			buildingPage.addRoofDetails(testData, 1, 1);
			// adding this below code to enter roof replaced year which is older than 25
			// years

			if (buildingPage.roofDetailsLink.checkIfElementIsPresent()
					&& buildingPage.roofDetailsLink.checkIfElementIsDisplayed()) {
				buildingPage.roofDetailsLink.waitTillVisibilityOfElement(60);
				buildingPage.roofDetailsLink.scrollToElement();
				buildingPage.roofDetailsLink.click();
				buildingPage.yearRoofLastReplaced.checkIfElementIsPresent();
				buildingPage.yearRoofLastReplaced.scrollToElement();
				buildingPage.yearRoofLastReplaced.setData("1978");
			}

			buildingPage.enterAdditionalBuildingInformation(testData, 1, 1);
			buildingPage.enterBuildingValues(testData, 1, 1);
			buildingPage.reviewBuilding.waitTillPresenceOfElement(60);
			buildingPage.reviewBuilding.waitTillVisibilityOfElement(60);
			buildingPage.reviewBuilding.waitTillElementisEnabled(60);
			buildingPage.reviewBuilding.waitTillButtonIsClickable(60);
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();

			// verifying warning messages when building cladding = Exterior Insulating
			// Finishing Systems (EIFS)
			// The warning message is 'This building is ineligible because it has EIFS
			// exterior cladding. Sorry we can't help you out on this one'
			Assertions.addInfo("Scenario 01",
					"Assert the Error message when building cladding is Exterior Insulating Finishing Systems (EIFS)");
			Assertions.verify(
					buildingPage.eifsErrorMessage.getData().contains(
							"This building is ineligible because it has EIFS exterior cladding"),
					true, "Building Page",
					" The error message is " + buildingPage.eifsErrorMessage.getData() + " verified successfully",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// verifying warning messages,when the Roof age is Older Than 25 Years
			// warning message is Asphalt Shingles roofs that are Older Than 25 Years old
			// are not eligible
			Assertions.addInfo("Scenario 02", "Assert the Error message when the Roof age is Older Than 25 Years");
			Assertions.verify(
					buildingPage.roofAgeErrorMessage.getData().contains(
							"Asphalt Shingles roofs with roof year 1978 are not eligible"),
					true, "Building Page",
					"The error message is " + buildingPage.roofAgeErrorMessage.getData() + "verified successfully",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// change EIFS
			testData = data.get(data_Value2);
			buildingPage.exteriorCladdingArrow.scrollToElement();
			buildingPage.exteriorCladdingArrow.click();
			buildingPage.exteriorCladdingOption.formatDynamicPath(testData.get("L1B1-BldgCladding")).scrollToElement();
			buildingPage.exteriorCladdingOption.formatDynamicPath(testData.get("L1B1-BldgCladding")).click();

			// change roof age
			buildingPage.addRoofDetails(testData, 1, 1);

			// Click on resubmit
			buildingPage.reSubmit.scrollToElement();
			buildingPage.reSubmit.click();
			Assertions.passTest("Building Page", "EIFS, Al Wiring, and Roof Age details modified successfully");
			if (buildingPage.pageName.getData().contains("Existing Account Found")) {
				existingAccountPage.OverrideExistingAccount();
			}

			// click on create quote
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();

			if (buildingPage.pageName.getData().contains("Buildings No Longer Quoteable")) {
				buildingNoLongerQuoteablePage.overrideNoLongerQuotableBuildings();
			}

			// selecting peril
			testData = data.get(data_Value1);
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
				Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");
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

			// Verifying absence of request premium change button
			Assertions.addInfo("Scenario 03",
					"Verifying absence of request premium change link when premium is below <$3000");
			Assertions.verify(accountOverviewPage.requestPremiumChangeLink.checkIfElementIsPresent(), false,
					"Account Overview Page", "Request premium change link not displayed ", false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on request bind button
			requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind button successfully");

			// Enter bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// Approve Referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Approval page loaded successfully", false, false);
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Get policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Policy Number is " + policyNumber, false, false);

			// Click on renewal link
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();

			// Add expac details
			testData = data.get(data_Value1);
			if (policyrenewalPage.addExpaccButton.checkIfElementIsPresent()
					&& policyrenewalPage.addExpaccButton.checkIfElementIsDisplayed()) {
				// Click on Home Button
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();

				// Click on Expacc
				homePage.scrollToBottomPage();
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.click();

				// Enter expacc details
				Assertions.verify(expaccInfoPage.submit.checkIfElementIsDisplayed(), true, "Expacc Info Page",
						"Expacc details page loaded successfully", false, false);
				expaccInfoPage.enterExpaccInfo(testData, policyNumber);
				Assertions.passTest("Expacc Info Page", "Expacc Details entered successfully");

				// Click on Home Button
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchPolicy(policyNumber);

				// Renew policy and release to producer
				Assertions.addInfo("Policy Summary Page", "Renewing the Policy");
				policySummaryPage.renewPolicy.waitTillVisibilityOfElement(60);
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();
				Assertions.passTest("Policy Summary Page", "Clicked on renew policy link");

			}

			// Click on continue
			if (policyrenewalPage.continueRenewal.checkIfElementIsPresent()
					&& policyrenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
				policyrenewalPage.continueRenewal.scrollToElement();
				policyrenewalPage.continueRenewal.click();
			}

			// click on Yes
			if (policyrenewalPage.yesButton.checkIfElementIsPresent()
					&& policyrenewalPage.yesButton.checkIfElementIsDisplayed()) {
				policyrenewalPage.yesButton.scrollToElement();
				policyrenewalPage.yesButton.click();
			}

			// Getting renewal quote number1
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account overview page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData().replace("(", "").replace(")", "");
			Assertions.passTest("Account Overview Page", "Renewal quote number1 is " + quoteNumber);

			// Verifying absence of request premium change button on renewal quote
			Assertions.addInfo("Scenario 04",
					"Verifying absence of request premium change link when premium is below <$3000");
			Assertions.verify(accountOverviewPage.requestPremiumChangeLink.checkIfElementIsPresent(), false,
					"Account Overview Page", "Request premium change link not displayed ", false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Click on edit deductibles link on account overview page
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit deductible link successfully");

			// Change Deductible/Coverages such a way premium become greter than >$3000
			testData = data.get(data_Value2);
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.namedStormDeductibleArrow.scrollToElement();
			createQuotePage.namedStormDeductibleArrow.click();
			createQuotePage.namedStormDeductibleOption.formatDynamicPath(testData.get("DeductibleValue"))
					.scrollToElement();
			createQuotePage.namedStormDeductibleOption.formatDynamicPath(testData.get("DeductibleValue")).click();
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Getting renewal quote number2
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account overview page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData().replace("(", "").replace(")", "");
			Assertions.passTest("Account Overview Page", "Renewal quote number2 is " + quoteNumber);

			premiumFee = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");

			// click on release renewal to producer button
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Renewal to Producer button");

			// Click on request bind button
			testData = data.get(data_Value1);
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request bind page",
					"Request bind page loaded successfully", false, false);

			// Enter renewal bind details
			requestBindPage.renewalRequestBind(testData);
			Assertions.passTest("Request bind page", "Bind details entered successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchReferral(quoteNumber);
			Assertions.passTest("Home page", "Quote for referral is searched successfully");

			// Approve Referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral page", "Referral request approved successfully");

			// carrier selection
			if (requestBindPage.carrierWarningMsg.checkIfElementIsPresent()
					&& requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed()) {
				requestBindPage.approveRequestCommercialData(testData);

			} else {
				requestBindPage.approveRequest();
			}

			Assertions.passTest("Request bind page", "Bind request approved successfully");

			// Get policy number from policy summary page
			Assertions.passTest("Policy summary page", "Policy summary page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy summary page", "Renewal policy number is " + policyNumber, false, false);

			// Verifying transaction premium fee from account overview page and transaction
			// premium fee from policy summary page
			premiumFeeTx = policySummaryPage.PremiumFee.formatDynamicPath(1).getData().replace("$", "").replace(",", "")
					.replace(".00", "");
			Assertions.addInfo("Scenario 09", "Verifying transaction premium fee");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(premiumFeeTx), 2)
					- Precision.round(Double.parseDouble(premiumFee), 2)), 2) < 1.00) {
				Assertions.passTest("Policy Summary Page",
						"Actual and Expected transaction premium fee bothe are same actual transaction premium fees is $"
								+ premiumFeeTx);
				Assertions.passTest("Policy Summary Page", "Expected transaction premium fee is $" + premiumFee);

			} else {
				Assertions.verify(premiumFeeTx, premiumFee, "Policy Summary Page",
						"Actual and Expected transaction premium fees are not matching", false, false);
			}
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			// signout from the application
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 75", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 75", "Executed Successfully");
			}
		}
	}
}
