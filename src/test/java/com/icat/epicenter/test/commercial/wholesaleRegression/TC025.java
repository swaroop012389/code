/** Program Description: Create a quote and check for the presence of alt quotes and select an Alt quote and bind to create a policy and Adding the ticket IO-21014.
 *  Author			   : John
 *  Date of Creation   : 11/06/2019
 **/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC025 extends AbstractCommercialTest {

	public TC025() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID025.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();

		// Initializing the variables
		int dataValue1 = 0;
		Map<String, String> testData = data.get(dataValue1);
		String quoteNumber;
		String policyNumber;
		int quotelength;
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
			locationPage = eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.addBuildingsButton.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);
			Assertions.passTest("Location Page", "Location details entered successfully");

			// Entering Location 1 Dwelling 1 Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// selecting peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// enter prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
				Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");
			}

			// Getting below and above values of selcted NS deductible
			createQuotePage.namedStormDeductibleArrow.waitTillVisibilityOfElement(60);
			createQuotePage.namedStormDeductibleArrow.scrollToElement();
			createQuotePage.namedStormDeductibleArrow.click();
			String selectedNSDed = createQuotePage.namedStormDeductibleOption.formatDynamicPath("3%").getData();
			String belowSelectedDed = createQuotePage.namedStormDeductibleOption.formatDynamicPath("2%").getData();
			String aboveSelectedDed = createQuotePage.namedStormDeductibleOption.formatDynamicPath("5%").getData();
			Assertions.passTest("Create Quote Page", "NS Deductible below the selected value is " + belowSelectedDed);
			Assertions.passTest("Create Quote Page", "NS Deductible above the selected value is " + aboveSelectedDed);
			createQuotePage.refreshPage();

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// clicking on request bind button
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// getting the quote number
			quotelength = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quotelength - 1);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);
			Assertions.verify(accountOverviewPage.editDeductibleAndLimits.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Edit deductibles button displayed is verified", false, false);
			Assertions.passTest("Create Quote Page", "NS Deductible selected value is " + selectedNSDed);

			// NS deductible values in table
			Assertions.addInfo("Account Overview Page", "Asserting NS deductible values from Account overview page");
			Assertions.verify(accountOverviewPage.deductibleOptions.formatDynamicPath(4, 3).getData(), aboveSelectedDed,
					"Account Overview Page",
					"Option 1 for NS deductible, "
							+ accountOverviewPage.deductibleOptions.formatDynamicPath(4, 3).getData()
							+ " is the next available highest value to the selected value for "
							+ accountOverviewPage.deductibleOptions.formatDynamicPath(2, 2).getData() + " peril",
					false, false);
			Assertions.verify(accountOverviewPage.deductibleOptions.formatDynamicPath(4, 4).getData(), belowSelectedDed,
					"Account Overview Page",
					"Option 2 for NS deductible, "
							+ accountOverviewPage.deductibleOptions.formatDynamicPath(4, 4).getData()
							+ "  is the next available lowest value to the selected value for "
							+ accountOverviewPage.deductibleOptions.formatDynamicPath(2, 2).getData() + " peril",
					false, false);
			Assertions.verify(accountOverviewPage.deductibleOptions.formatDynamicPath(4, 5).getData(), selectedNSDed,
					"Account Overview Page",
					"Option 3 for NS deductible, "
							+ accountOverviewPage.deductibleOptions.formatDynamicPath(4, 5).getData()
							+ "  is the same value as selected value for "
							+ accountOverviewPage.deductibleOptions.formatDynamicPath(2, 3).getData() + " peril",
					false, false);
			Assertions.verify(accountOverviewPage.deductibleOptions.formatDynamicPath(4, 6).getData(), aboveSelectedDed,
					"Account Overview Page",
					"Option 4 for NS deductible, "
							+ accountOverviewPage.deductibleOptions.formatDynamicPath(4, 6).getData()
							+ "  is the next available highest value to the selected value for "
							+ accountOverviewPage.deductibleOptions.formatDynamicPath(2, 3).getData() + " peril",
					false, false);

			Assertions.passTest("Create Quote Page", "AOWH Deductible selected value is " + selectedNSDed);

			// AOWH deductible values in table
			Assertions.addInfo("Account Overview Page", "Asserting AOWH deductible values from Account overview page");
			Assertions.verify(accountOverviewPage.deductibleOptions.formatDynamicPath(5, 3).getData(), aboveSelectedDed,
					"Account Overview Page",
					"Option 1 for AOWH deductible, "
							+ accountOverviewPage.deductibleOptions.formatDynamicPath(5, 3).getData()
							+ " is the next available highest value to the selected value for "
							+ accountOverviewPage.deductibleOptions.formatDynamicPath(2, 2).getData() + " peril",
					false, false);
			Assertions.verify(accountOverviewPage.deductibleOptions.formatDynamicPath(5, 4).getData(), belowSelectedDed,
					"Account Overview Page",
					"Option 2 for AOWH deductible, "
							+ accountOverviewPage.deductibleOptions.formatDynamicPath(5, 4).getData()
							+ " is the next available lowest value to the selected value for "
							+ accountOverviewPage.deductibleOptions.formatDynamicPath(2, 2).getData() + " peril",
					false, false);
			Assertions.verify(accountOverviewPage.deductibleOptions.formatDynamicPath(5, 5).getData(), selectedNSDed,
					"Account Overview Page",
					"Option 3 for AOWH deductible, "
							+ accountOverviewPage.deductibleOptions.formatDynamicPath(5, 5).getData()
							+ " is the same value as selected value for "
							+ accountOverviewPage.deductibleOptions.formatDynamicPath(2, 3).getData() + " peril",
					false, false);
			Assertions.verify(accountOverviewPage.deductibleOptions.formatDynamicPath(5, 6).getData(), aboveSelectedDed,
					"Account Overview Page",
					"Option 4 for AOWH deductible, "
							+ accountOverviewPage.deductibleOptions.formatDynamicPath(5, 6).getData()
							+ "  is the next available lowest value to the selected value for "
							+ accountOverviewPage.deductibleOptions.formatDynamicPath(2, 3).getData() + " peril",
					false, false);
			for (int i = 3; i <= 4; i++) {
				for (int j = 3; j <= 6; j++) {
					if (j == 3 || j == 4) {
						Assertions.verify(
								accountOverviewPage.deductibleOptions
										.formatDynamicPath(i + 3, j).checkIfElementIsDisplayed(),
								true, "Account Overview Page",
								accountOverviewPage.deductibleOptions.formatDynamicPath(i + 3, 1).getData() + " : "
										+ " Option - " + (j - 2) + " : " + " "
										+ accountOverviewPage.deductibleOptions.formatDynamicPath(2, 2).getData()
										+ " : "
										+ accountOverviewPage.deductibleOptions.formatDynamicPath(i + 3, j).getData(),
								false, false);
					} else {
						Assertions.verify(
								accountOverviewPage.deductibleOptions
										.formatDynamicPath(i + 3, j).checkIfElementIsDisplayed(),
								true, "Account Overview Page",
								accountOverviewPage.deductibleOptions.formatDynamicPath(i + 3, 1).getData() + " : "
										+ " Option - " + (j - 2) + " : " + " "
										+ accountOverviewPage.deductibleOptions.formatDynamicPath(2, 3).getData()
										+ " : "
										+ accountOverviewPage.deductibleOptions.formatDynamicPath(i + 3, j).getData(),
								false, false);
					}
				}
			}

			// clicking on Total Premium and ICAT fees option 3 link
			accountOverviewPage.deductibleOptions.formatDynamicPath(7, 5).scrollToElement();
			accountOverviewPage.deductibleOptions.formatDynamicPath(7, 5).click();
			Assertions.passTest("Account Overview Page", "Alternative Premium Option 3 is selected");

			// getting the quote number
			quotelength = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, quotelength - 1);

			// entering details in request bind page
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
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

			// Signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 25", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 25", "Executed Successfully");
			}
		}
	}
}
