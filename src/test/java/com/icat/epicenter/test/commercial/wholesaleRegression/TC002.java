/** Program Description: Create a commercial Policy without APC coverages. Select Package A in quote page. Assert values of Package A and Package B and Added ticket IO-20723
 *  Author			   : Sowndarya NH
 *  Date of Creation   : 11/26/2019
**/

package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.WebDriverManager;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorseInspectionContactPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC002 extends AbstractCommercialTest {

	public TC002() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID002.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the pages
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		ViewPolicySnapShot policySnapshot = new ViewPolicySnapShot();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		EndorseInspectionContactPage endorseInspectionContactPage = new EndorseInspectionContactPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		ViewPolicySnapShot viewPolicySnapShot = new ViewPolicySnapShot();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		String quoteNumber;
		String policyNumber;
		String businessIncomeValue;
		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
		Map<String, String> testData = data.get(dataValue1);
		char ch = 'A';
		boolean isTestPassed = false;

		try {
			// New Account creation
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully ", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// ZipCode EligibilityPage
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Zipcode Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "ZipCode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);
			Assertions.passTest("Location Page", "Location Details Entered successfully");

			// Entering Location 1 Dwelling 1 Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// Select peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);

			// Adding the CR IO-19429
			// Asserting the Presence of Radio Buttons
			Assertions.addInfo("Select Peril Page", "Asserting the Peril Types");
			Assertions.verify(selectPerilPage.windOnly.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Wind Only Radio Button displayed is verified", false, false);
			Assertions.verify(selectPerilPage.allOtherPeril.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"All Other Peril Radio Button displayed is verified", false, false);
			Assertions.verify(selectPerilPage.gLandAllOtherPerils.checkIfElementIsDisplayed(), true,
					"Select Peril Page", "GL and AOP Radio Button displayed is verified", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Asserting navigation to create quote page
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page Loaded successfully", false, false);

			// Asserting Package +ch+ values
			createQuotePage.coverageExtensionPackageLink.waitTillVisibilityOfElement(60);
			createQuotePage.coverageExtensionPackageLink.scrollToElement();
			createQuotePage.coverageExtensionPackageLink.click();
			Assertions.addInfo("Create Quote Page", "Asserting the Package values");
			Assertions.verify(
					createQuotePage.packageValues
							.formatDynamicPath("Accounts Receivable", "3").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Base Package Accounts Receivable value in create quote page is "
							+ createQuotePage.packageValues.formatDynamicPath("Accounts Receivable", "3").getData(),
					false, false);
			Assertions.verify(
					createQuotePage.packageValues.formatDynamicPath("Fine Arts", "3").checkIfElementIsDisplayed(), true,
					"Create Quote Page",
					"Base Package Fine Arts value in create quote page is "
							+ createQuotePage.packageValues.formatDynamicPath("Fine Arts", "3").getData(),
					false, false);
			Assertions.verify(
					createQuotePage.packageValues.formatDynamicPath("Food Spoilage", "3").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Base Package Food Spoilage value in create quote page is "
							+ createQuotePage.packageValues.formatDynamicPath("Food Spoilage", "3").getData(),
					false, false);
			Assertions.verify(
					createQuotePage.packageValues
							.formatDynamicPath("Utility Interruption", "3").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Base Package Utility Interruption value in create quote page is "
							+ createQuotePage.packageValues.formatDynamicPath("Utility Interruption", "3").getData(),
					false, false);
			Assertions.verify(
					createQuotePage.packageValues.formatDynamicPath("Electronic Data", "3").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Base Package  Electronic Data value in create quote page is "
							+ createQuotePage.packageValues.formatDynamicPath("Electronic Data", "3").getData(),
					false, false);
			Assertions.verify(
					createQuotePage.packageValues.formatDynamicPath("Valuable Papers", "3").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Base Package Valuable Papers value in create quote page is "
							+ createQuotePage.packageValues.formatDynamicPath("Valuable Papers", "3").getData(),
					false, false);
			Assertions.verify(
					createQuotePage.packageValues
							.formatDynamicPath("Extended Period of Indemnity", "3").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Base Package Extended Period of Indemnity value in create quote page is "
							+ createQuotePage.packageValues.formatDynamicPath("Extended Period of Indemnity", "3")
									.getData(),
					false, false);
			for (int i = 1; i <= 2; i++) {
				Assertions.verify(
						createQuotePage.packageValues
								.formatDynamicPath("Accounts Receivable", i).checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"Package  " + ch + " Accounts Receivable value in create quote page is "
								+ createQuotePage.packageValues.formatDynamicPath("Accounts Receivable", i).getData(),
						false, false);
				Assertions.verify(
						createQuotePage.packageValues.formatDynamicPath("Fine Arts", i).checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"Package " + ch + " Fine Arts value in create quote page is "
								+ createQuotePage.packageValues.formatDynamicPath("Fine Arts", i).getData(),
						false, false);
				Assertions.verify(
						createQuotePage.packageValues.formatDynamicPath("Food Spoilage", i).checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"Package " + ch + " Food Spoilage value in create quote page is "
								+ createQuotePage.packageValues.formatDynamicPath("Food Spoilage", i).getData(),
						false, false);
				Assertions.verify(
						createQuotePage.packageValues
								.formatDynamicPath("Utility Interruption", i).checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"Package " + ch + " Utility Interruption value in create quote page is "
								+ createQuotePage.packageValues.formatDynamicPath("Utility Interruption", i).getData(),
						false, false);
				Assertions.verify(
						createQuotePage.packageValues
								.formatDynamicPath("Electronic Data", i).checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"Package  " + ch + "  Electronic Data value in create quote page is "
								+ createQuotePage.packageValues.formatDynamicPath("Electronic Data", i).getData(),
						false, false);
				Assertions.verify(
						createQuotePage.packageValues
								.formatDynamicPath("Valuable Papers", i).checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"Package " + ch + " Valuable Papers value in create quote page is "
								+ createQuotePage.packageValues.formatDynamicPath("Valuable Papers", i).getData(),
						false, false);
				Assertions
						.verify(createQuotePage.packageValues
								.formatDynamicPath("Extended Period of Indemnity", i).checkIfElementIsDisplayed(), true,
								"Create Quote Page",
								"Package " + ch + " Extended Period of Indemnity value in create quote page is "
										+ createQuotePage.packageValues
												.formatDynamicPath("Extended Period of Indemnity", i).getData(),
								false, false);
				waitTime(3);
				ch++;
			}
			ch = 'A';

			// Entering create quote page details
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Clicking on request bind button
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// Getting the quote number
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();

			// Adding below code for IO-20723
			// Verifying absence of When the BI value is limited, a formatting tag is not
			// visible on the quote
			Assertions.addInfo("View Print Full Quote Page",
					"Verifying absence of When the BI value is limited, a formatting tag is not visible on the quote");
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View Print Full Quote Page", "View Print Full Quote Page loaded successfully", false, false);
			Assertions.verify(viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(18).getData().contains("br"),
					false, "View Print Full Quote Page",
					"Verifying absence of When the BI value is limited, a formatting tag is not visible on the quote is verified the BI value is "
							+ viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(18).getData(),
					false, false);
			// IO-20723 ended

			// Assert Package +ch+ details in quote document
			Assertions.addInfo("View Print Full Quote Page", "Asserting the package Details from quote document");
			Assertions.verify(
					accountOverviewPage.packageData
							.formatDynamicPath("Accounts Receivable", "1").checkIfElementIsDisplayed(),
					true, "View Print Full Quote Page",
					"Base Package Accounts Receivable value in quote document is "
							+ accountOverviewPage.packageData.formatDynamicPath("Accounts Receivable", "1").getData(),
					false, false);
			Assertions.verify(
					accountOverviewPage.packageData.formatDynamicPath("Fine Arts", "1").checkIfElementIsDisplayed(),
					true, "View Print Full Quote Page",
					"Base Package Fine Arts value in quote document is "
							+ accountOverviewPage.packageData.formatDynamicPath("Fine Arts", "1").getData(),
					false, false);
			Assertions.verify(
					accountOverviewPage.packageData.formatDynamicPath("Food Spoilage", "1").checkIfElementIsDisplayed(),
					true, "View Print Full Quote Page",
					"Base Package Food Spoilage value in quote document is "
							+ accountOverviewPage.packageData.formatDynamicPath("Food Spoilage", "1").getData(),
					false, false);
			Assertions.verify(
					accountOverviewPage.packageData
							.formatDynamicPath("Utility Interruption", "1").checkIfElementIsDisplayed(),
					true, "View Print Full Quote Page",
					"Base Package Utility Interruption value in quote document is "
							+ accountOverviewPage.packageData.formatDynamicPath("Utility Interruption", "1").getData(),
					false, false);
			Assertions.verify(
					accountOverviewPage.packageData
							.formatDynamicPath("Electronic Data", "1").checkIfElementIsDisplayed(),
					true, "View Print Full Quote Page",
					"Base Package Electronic Data value in quote document is "
							+ accountOverviewPage.packageData.formatDynamicPath("Electronic Data", "1").getData(),
					false, false);
			Assertions.verify(
					accountOverviewPage.packageData
							.formatDynamicPath("Valuable Papers", "1").checkIfElementIsDisplayed(),
					true, "View Print Full Quote Page",
					"Base Package Valuable Papers value in quote document is "
							+ accountOverviewPage.packageData.formatDynamicPath("Valuable Papers", "1").getData(),
					false, false);
			Assertions.verify(
					accountOverviewPage.packageData
							.formatDynamicPath("Extended Period of Indemnity", "1").checkIfElementIsDisplayed(),
					true, "View Print Full Quote Page",
					"Base Package Extended Period of Indemnity value in quote document is "
							+ accountOverviewPage.packageData.formatDynamicPath("Extended Period of Indemnity", "1")
									.getData(),
					false, false);
			for (int i = 3; i >= 2; i--) {
				Assertions.verify(
						accountOverviewPage.packageData
								.formatDynamicPath("Accounts Receivable", i).checkIfElementIsDisplayed(),
						true, "View Print Full Quote Page",
						"Package " + ch + " Accounts Receivable value in quote document is "
								+ accountOverviewPage.packageData.formatDynamicPath("Accounts Receivable", i).getData(),
						false, false);
				Assertions.verify(
						accountOverviewPage.packageData.formatDynamicPath("Fine Arts", i).checkIfElementIsDisplayed(),
						true, "View Print Full Quote Page",
						"Package " + ch + " Fine Arts value in quote document is "
								+ accountOverviewPage.packageData.formatDynamicPath("Fine Arts", i).getData(),
						false, false);
				Assertions.verify(
						accountOverviewPage.packageData
								.formatDynamicPath("Food Spoilage", i).checkIfElementIsDisplayed(),
						true, "View Print Full Quote Page",
						"Package " + ch + " Food Spoilage value in quote document is "
								+ accountOverviewPage.packageData.formatDynamicPath("Food Spoilage", i).getData(),
						false, false);
				Assertions.verify(
						accountOverviewPage.packageData
								.formatDynamicPath("Utility Interruption", i).checkIfElementIsDisplayed(),
						true, "View Print Full Quote Page",
						"Package " + ch + " Utility Interruption value in quote document is "
								+ accountOverviewPage.packageData.formatDynamicPath("Utility Interruption", i)
										.getData(),
						false, false);
				Assertions.verify(
						accountOverviewPage.packageData
								.formatDynamicPath("Electronic Data", i).checkIfElementIsDisplayed(),
						true, "View Print Full Quote Page",
						"Package " + ch + " Electronic Data value in quote document is "
								+ accountOverviewPage.packageData.formatDynamicPath("Electronic Data", i).getData(),
						false, false);
				Assertions.verify(
						accountOverviewPage.packageData
								.formatDynamicPath("Valuable Papers", i).checkIfElementIsDisplayed(),
						true, "View Print Full Quote Page",
						"Package " + ch + " Valuable Papers value in quote document is "
								+ accountOverviewPage.packageData.formatDynamicPath("Valuable Papers", i).getData(),
						false, false);
				Assertions
						.verify(accountOverviewPage.packageData
								.formatDynamicPath("Extended Period of Indemnity", i).checkIfElementIsDisplayed(), true,
								"View Print Full Quote Page",
								"Package " + ch + " Extended Period of Indemnity value in quote document is "
										+ accountOverviewPage.packageData
												.formatDynamicPath("Extended Period of Indemnity", i).getData(),
								false, false);
				waitTime(2);
				ch++;
			}
			accountOverviewPage.scrollToTopPage();
			accountOverviewPage.waitTime(3);// need wait time to scroll to top page
			accountOverviewPage.goBackBtn.click();

			// Click on Request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind ");

			// Entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details Entered successfully");

			// Clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home Page", "Clicked on Home button");

			// Searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchReferral(quoteNumber);
			Assertions.passTest("Home Page", "Searched the Account successfully");

			// Approving referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Approval page loaded successfully", false, false);

			// carrier selection
			if (requestBindPage.carrierWarningMsg.checkIfElementIsPresent()
					&& requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed()) {
				requestBindPage.approveRequestCommercialData(testData);
			} else {
				requestBindPage.approveRequest();
			}
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// Click on view Binder
			policySummarypage.viewPolicySnapshot.scrollToElement();
			policySummarypage.viewPolicySnapshot.click();
			Assertions.passTest("View Policy Snap Shot Page", "Policy Snap Shot Page Loaded successfully");

			// Verifying apc's are present in policySnapshot or not
			Assertions.addInfo("View Policy Snap Shot Page", "Asserting the Presence of Apc's in Policy snapshot page");
			businessIncomeValue = policySnapshot.businessIncomeData.getData();
			Assertions.verify(policySnapshot.apcBusinessIncome.checkIfElementIsPresent(), true,
					"View Policy Snap Shot Page",
					"Business Income/Extra Expense/Rental is displayed and the value is : " + businessIncomeValue,
					false, false);
			Assertions.verify(policySnapshot.apcAwnings.checkIfElementIsPresent(), false, "View Policy Snap Shot Page",
					"Awnings, Canopies,IncludingOver Gas Pumps not displayed", false, false);
			Assertions.verify(policySnapshot.apcBoardwalks.checkIfElementIsPresent(), false,
					"View Policy Snap Shot Page", "Boardwalks,Catwalks,Decks,Trestles and Bridges not displayed", false,
					false);
			Assertions.verify(policySnapshot.apcCarports.checkIfElementIsPresent(), false, "View Policy Snap Shot Page",
					"Boardwalks,Catwalks,Decks,Trestles and Bridges not displayed", false, false);
			Assertions.verify(policySnapshot.apcDriveways.checkIfElementIsPresent(), false,
					"View Policy Snap Shot Page", "Driveways,Courts,Pads and PavedSurfaces not displayed", false,
					false);
			Assertions.verify(policySnapshot.apcFences.checkIfElementIsPresent(), false, "View Policy Snap Shot Page",
					"Fences,PropertyLineWalls, Lattice Work and Trellis not displayed", false, false);
			Assertions.verify(policySnapshot.apcLightpoles.checkIfElementIsPresent(), false,
					"View Policy Snap Shot Page", "Light Poles and Unattached Signs not displayed", false, false);
			Assertions.verify(policySnapshot.apcPlayGroundEquipment.checkIfElementIsPresent(), false,
					"View Policy Snap Shot Page", "Playground Equipment not displayed", false, false);
			Assertions.verify(policySnapshot.apcOtherStructures.checkIfElementIsPresent(), false,
					"View Policy Snap Shot Page", "Other Structures - Fully Enclosed not displayed", false, false);
			Assertions.verify(policySnapshot.apcSatellitedishes.checkIfElementIsPresent(), false,
					"View Policy Snap Shot Page", "Satellite Dishes not displayed", false, false);
			Assertions.verify(policySnapshot.apcPoolsandwaterfalls.checkIfElementIsPresent(), false,
					"View Policy Snap Shot Page", "Pools and Waterfalls not displayed", false, false);
			Assertions.verify(policySnapshot.apcMachinary.checkIfElementIsPresent(), false,
					"View Policy Snap Shot Page",
					"Machinery and Equipment in the Open, including gas pumps not displayed", false, false);
			Assertions.verify(policySnapshot.apcFountains.checkIfElementIsPresent(), false,
					"View Policy Snap Shot Page", "Fountains,Statuary,Monuments or Tombstones not displayed", false,
					false);
			Assertions.verify(policySnapshot.apcUndergroundutilities.checkIfElementIsPresent(), false,
					"View Policy Snap Shot Page", "Underground Utilities not displayed", false, false);
			Assertions.verify(policySnapshot.apcOpennotfullyenclosed.checkIfElementIsPresent(), false,
					"View Policy Snap Shot Page", "Other Structures - Open or Not Fully Enclosed not displayed", false,
					false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Searched the Policy " + policyNumber + " successfully");

			// Click on Endorse PB link
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Enter Endorsement Effective Date
			testData = data.get(dataValue2);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// Clicking on Edit Building/Location Details link
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Edit Location or Building link successfully");
			locationPage.enterLocationDetails(testData);

			// Enter new Building Details
			buildingPage.enterBuildingDetails(testData);

			// Clicking on Continue button
			buildingPage.continueButton.scrollToElement();
			buildingPage.continueButton.click();

			// Clicking on Ok button
			endorseInspectionContactPage.okButton.waitTillVisibilityOfElement(60);
			endorseInspectionContactPage.okButton.scrollToElement();
			endorseInspectionContactPage.okButton.click();

			// clicking on continue button in create quote page
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page loaded successfully", false, false);

			// Close Browser
			WebDriverManager.closeCurrentBrowser();
			Assertions.passTest("Create Quote Page", "Closed the Browser successfully");
			WebDriverManager.launchBrowser();
			// WebDriverManager.loadTestPage();
			// WebDriverManager.maximizeCurrentWindow();

			Assertions.passTest("Login Page", "Login Page loaded successfully");
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login", "Logged in to application successfully");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Searched the Policy " + policyNumber + " successfully");

			// Click on Endorse PB link
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");
			if (endorsePolicyPage.okButton.checkIfElementIsPresent()
					&& endorsePolicyPage.okButton.checkIfElementIsDisplayed()) {
				Assertions.verify(endorsePolicyPage.okButton.checkIfElementIsDisplayed(), true,
						"Corrupt Endorsement Quote Page", "Corrupt Endorsement Quote Page loaded successfully", false,
						false);
				endorsePolicyPage.okButton.scrollToElement();
				endorsePolicyPage.okButton.click();
				Assertions.passTest("Corrupt Endorsement Quote Page", "Clicked on OK Button");
			}

			// Click on Endorse PB link
			Assertions.addInfo("Policy Summary Page", "Starting the Endorsement again");
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// Clicking on Edit Building/Location Details link
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Edit Location or Building link successfully");
			locationPage.enterLocationDetails(testData);

			// Enter new Building Details
			buildingPage.enterBuildingDetails(testData);

			// Clicking on Continue button
			buildingPage.continueButton.scrollToElement();
			buildingPage.continueButton.click();

			// Clicking on Ok button
			endorseInspectionContactPage.okButton.waitTillVisibilityOfElement(60);
			endorseInspectionContactPage.okButton.scrollToElement();
			endorseInspectionContactPage.okButton.click();

			// clicking on continue button in create quote page
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);

			// clicking on next button in endorse policy page
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// click on continue button
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// clicking on complete button in endorse policy page
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Complete Button");

			// clicking on close button in endorse policy page
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			// click on transaction history reason
			policySummarypage.transHistReason.formatDynamicPath(3).scrollToElement();
			policySummarypage.transHistReason.formatDynamicPath(3).click();

			// Click on View policy snapshot
			policySummarypage.viewPolicySnapshot.scrollToElement();
			policySummarypage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Policy Snapshot link");

			Assertions.addInfo("View Policy Snapshot Page", "Verifying Location 2 is added");
			Assertions.verify(
					viewPolicySnapShot.locationName.formatDynamicPath("2", "Location").getData().contains("2"), true,
					"View Policy Snapshot Page", "Location 2 is added is verified", false, false);

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Added IO-21293/21667
			// if it fails in QA please refer the comment added in IO-21667
			// Sign in as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as Producer successfully");

			// New Account creation
			testData = data.get(dataValue1);
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully ", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// ZipCode EligibilityPage
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Zipcode Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "ZipCode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);
			Assertions.passTest("Location Page", "Location Details Entered successfully");

			// Entering Location 1 Dwelling 1 Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// Select peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Asserting navigation to create quote page
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page Loaded successfully", false, false);

			testData = data.get(dataValue3);
			// Entering create quote page details
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Clicking on request bind button
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// Getting the quote number
			String quoteNumber2 = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber2);

			testData = data.get(dataValue1);
			// Click on Request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber2);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind ");

			// Entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details Entered successfully");

			// sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged out as producer Successfully");

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");

			homePage.searchQuote(quoteNumber2);
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();

			referralPage.clickOnApprove();
			// click on decline in Approve Decline Quote page

			if (approveDeclineQuotePage.internalUnderwriterComments.checkIfElementIsPresent()) {
				approveDeclineQuotePage.internalUnderwriterComments.scrollToElement();
				approveDeclineQuotePage.internalUnderwriterComments.setData("Test");
			}
			if (approveDeclineQuotePage.externalUnderwriterComments.checkIfElementIsPresent()) {
				approveDeclineQuotePage.externalUnderwriterComments.scrollToElement();
				approveDeclineQuotePage.externalUnderwriterComments.setData("Test");
			}
			approveDeclineQuotePage.declineButton.scrollToElement();
			approveDeclineQuotePage.declineButton.click();

			Assertions.addInfo("Scenario",
					"Verifying that the bind request declined successfully when producer uploaded subscription agreement not approved by USM");
			Assertions.verify(
					approveDeclineQuotePage.quoteDeclinedMessage.checkIfElementIsPresent()
							&& approveDeclineQuotePage.quoteDeclinedMessage.getData()
									.contains("The bind request has been declined."),
					true, "Approve decline quote page",
					"The bind request declined successfully when producer uploaded subscription agreement not approved by USM",
					false, false);
			Assertions.addInfo("Scenario", "Scenario Ended");

			// IO-21293/21667 Ended

			// Close the browser
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 02", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 02", "Executed Successfully");
			}
		}
	}

}
