
/** Program Description: Check if the below mentioned lines are added as a bullet point under Conditions in the Terms and Conditions section of commercial retail quotes:
a) The surplus lines taxes and fees presented here are an estimate only and will be subject to change based on alterations to the quote.
b) Additional fees will apply to payments made by credit card. and Perform a PB ENDt on the Released Renewal quote and check if all PB Changes are correctly reflected on the issued quote
 *  Author			   : Yeshashwini
 *  Date of Creation   : 20/07/2021
 **/
package com.icat.epicenter.test.commercial.retailRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorseInspectionContactPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
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
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC011 extends AbstractCommercialTest {

	public TC011() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID011.xls";
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
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		EndorseInspectionContactPage endorseInspectionContactPage = new EndorseInspectionContactPage();
		ExistingAccountPage existingAccountPage = new ExistingAccountPage();
		BuildingUnderMinimumCostPage buildingUnderminimumCost = new BuildingUnderMinimumCostPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuotablePage = new BuildingNoLongerQuoteablePage();

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

			// click on add buildings button
			locationPage.addBuildingsButton.scrollToElement();
			locationPage.addBuildingsButton.click();

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building page",
					//"Building page loaded successfully", false, false);
			buildingPage.addBuildingDetails(testData, 1, 1);
			buildingPage.addBuildingOccupancy(testData, 1, 1);
			buildingPage.addRoofDetails(testData, 1, 1);
			buildingPage.enterAdditionalBuildingInformation(testData, 1, 1);
			buildingPage.enterBuildingValues(testData, 1, 1);
			Assertions.passTest("Building Page", "Construction type: " + testData.get("L1B1-BldgConstType"));
			Assertions.passTest("Building Page", "Occupancy type: " + testData.get("L1B1-PrimaryOccupancy"));
			Assertions.passTest("Building Page", "Building Square Feet: " + testData.get("L1B1-BldgSqFeet"));
			Assertions.passTest("Building Page", "Building details entered successfully");

			// Click on Create quote button
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();

			// existing account found page
			if (buildingPage.pageName.getData().contains("Existing Account Found")) {
				existingAccountPage.OverrideExistingAccount();
			}

			// Verifying Bring UpToCost button
			Assertions.passTest("Building Page", "Clicked on Create Quote button successfully");
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
			Assertions.addInfo("Scenario 01",
					"Verifying the Costcard message and Verifying the actual and expected cost card values when Construction type: Wood Frame, Occupancy type: Apartment,Building Square Feet: 1000");
			Assertions.verify(
					buildingUnderminimumCost.costcardMessage
							.formatDynamicPath("Building value").checkIfElementIsDisplayed(),
					true, "Building Under Minimum Cost Page",
					"The Costcard message "
							+ buildingUnderminimumCost.costcardMessage.formatDynamicPath("Building value").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// click on BringUpToCost button
			if (buildingPage.pageName.getData().contains("Under Minimum Cost")) {
				buildingUnderminimumCost.clickOnOverride();
				Assertions.passTest("Building UnderMinimum Cost Page", "Clicked on Override button successfully");
			}

			if (buildingPage.pageName.getData().contains("Buildings No")) {
				buildingNoLongerQuotablePage.overrideNoLongerQuotableBuildings();
			}

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

			// Click on View print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View/Print Full quote link");
			Assertions.addInfo("Scenario 02",
					"Checking the bullet point under Conditions in the Terms and Conditions section of commercial retail quotes");
			Assertions.verify(viewOrPrintFullQuotePage.termsAndConditions.formatDynamicPath(2, 3).getData(),
					"The surplus lines taxes and fees presented here are an estimate only and will be subject to change based on alterations to the quote.",
					"View/Print Full Quote Page",
					viewOrPrintFullQuotePage.termsAndConditions.formatDynamicPath(2, 3).getData()
							+ "wordings added under Conditions section is verified",
					false, false);
			Assertions.verify(viewOrPrintFullQuotePage.termsAndConditions.formatDynamicPath(2, 4).getData(),
					"Additional fees will apply to payments made by credit card.", "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.termsAndConditions.formatDynamicPath(2, 4).getData()
							+ "wordings added under Conditions section is verified",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 ended");

			// click on back
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// click on request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// entering bind details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);
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

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Get policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Policy Number is " + policyNumber, false, false);

			// Click on home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Add expac details
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
			Assertions.passTest("Policy Summary Page", "Clicked on renewal link successfully");
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

			// Getting renewal quote number
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account overview page", "Renewal Quote Number :  " + quoteNumber);

			// click on release renewal to producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account overview page", "Clicked on Release Renewal to Producer button");

			// Click on view previous policy link
			accountOverviewPage.viewPreviousPolicyButton.scrollToElement();
			accountOverviewPage.viewPreviousPolicyButton.click();
			Assertions.passTest("Account overview page", "Clicked on View Previous Policy link");

			// Click on PB Endorsement
			for (int i = 2; i <= 4; i++) {
				policySummaryPage.endorsePB.scrollToElement();
				policySummaryPage.endorsePB.click();
				Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

				if (endorsePolicyPage.okButton.checkIfElementIsPresent()
						&& endorsePolicyPage.okButton.checkIfElementIsDisplayed()) {
					endorsePolicyPage.okButton.scrollToElement();
					endorsePolicyPage.okButton.click();
				}

				// Enter transaction effective date
				testData = data.get(data_Value2);
				Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
						"Endorse policy page", "Endorse policy page loaded successfully", false, false);
				endorsePolicyPage.endorsementEffDate.scrollToElement();
				endorsePolicyPage.endorsementEffDate.appendData(testData.get("TransactionEffectiveDate"));
				endorsePolicyPage.endorsementEffDate.tab();
				Assertions.passTest("Endorse policy page", "Entered transaction effective date");
				if (i == 2) {
					// click on change coverage options
					endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
					endorsePolicyPage.changeCoverageOptionsLink.click();
					Assertions.passTest("Endorse policy page", "Clicked on Change Coverage Options link successfully");

					// changing named storm value
					testData = data.get(data_Value1);
					Assertions.passTest("Create quote page",
							"Named storm original value " + testData.get("DeductibleType"));
					testData = data.get(data_Value2);
					createQuotePage.namedStormDeductibleArrow.scrollToElement();
					createQuotePage.namedStormDeductibleArrow.click();
					createQuotePage.namedStormDeductibleOption.formatDynamicPath(testData.get("DeductibleValue"))
							.scrollToElement();
					createQuotePage.namedStormDeductibleOption.formatDynamicPath(testData.get("DeductibleValue"))
							.click();
					Assertions.passTest("Create quote page",
							"Named storm latest value " + testData.get("DeductibleType"));
					createQuotePage.continueEndorsementButton.scrollToElement();
					createQuotePage.continueEndorsementButton.click();
				} else if (i == 3) {

					// Click on prior loss link
					endorsePolicyPage.editPriorLoss.scrollToElement();
					endorsePolicyPage.editPriorLoss.click();

					// Entering Prior Losses
					Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true,
							"Prior Loss Page", "Prior Loss Page loaded successfully", false, false);
					priorLossesPage.selectPriorLossesInformation(testData);
				} else {

					// Click on edit/location building
					endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
					endorsePolicyPage.editLocOrBldgInformationLink.click();
					Assertions.passTest("Endorse Policy Page", "Clicked on Edit Location/Building link");

					// click on add symbol
					locationPage.addSymbol.scrollToElement();
					locationPage.addSymbol.click();

					// click on add location
					locationPage.addNewLocation.scrollToElement();
					locationPage.addNewLocation.click();
					locationPage.waitTime(3);// added wait to load the element
					locationPage.addSymbol.scrollToElement();
					locationPage.addSymbol.click();

					// add new building and enter building details
					locationPage.addNewBuilding.scrollToElement();
					locationPage.addNewBuilding.click();
					buildingPage.addBuildingDetails(testData, 2, 1);
					buildingPage.addBuildingOccupancy(testData, 2, 1);
					buildingPage.addRoofDetails(testData, 2, 1);
					buildingPage.enterAdditionalBuildingInformation(testData, 2, 1);
					buildingPage.enterBuildingValues(testData, 2, 1);
					buildingPage.continueButton.scrollToElement();
					buildingPage.continueButton.click();
					if (buildingPage.override.checkIfElementIsPresent()
							&& buildingPage.override.checkIfElementIsDisplayed()) {
						buildingPage.override.scrollToElement();
						buildingPage.override.click();
					}
					endorseInspectionContactPage.okButton.scrollToElement();
					endorseInspectionContactPage.okButton.click();
					createQuotePage.enterDeductiblesCommercialNew(testData);
					createQuotePage.continueEndorsementButton.scrollToElement();
					createQuotePage.continueEndorsementButton.click();

				}

				// click on Continue
				if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
						&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
					endorsePolicyPage.continueButton.scrollToElement();
					endorsePolicyPage.continueButton.click();
				}

				// click on next
				endorsePolicyPage.nextButton.scrollToElement();
				endorsePolicyPage.nextButton.click();
				Assertions.passTest("Endorse policy page", "Clicked on next button successfully");

				// click on Continue
				if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
						&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
					endorsePolicyPage.continueButton.scrollToElement();
					endorsePolicyPage.continueButton.click();
				}

				// Clicked on complete button
				endorsePolicyPage.completeButton.scrollToElement();
				endorsePolicyPage.completeButton.click();
				Assertions.passTest("Endorse Policy Page", "Clicked on complete button successfully");

				// Checking for roll forward endorsement button
				Assertions.addInfo("Scenario 03", "Verifying presence of Roll Forward button on endorse policy page");
				Assertions.verify(
						endorsePolicyPage.rollForwardBtn.checkIfElementIsPresent()
								&& endorsePolicyPage.rollForwardBtn.checkIfElementIsDisplayed(),
						true, "Endorse policy page", "Roll forward endorsement button is displayed", false, false);
				Assertions.addInfo("Scenario 03", "Scenario 02 ended");

				// Click on roll forward button
				endorsePolicyPage.rollForwardBtn.scrollToElement();
				endorsePolicyPage.rollForwardBtn.click();
				Assertions.passTest("Endorse policy page", "Clicked on roll forward endorsement button");
				endorsePolicyPage.closeButton.scrollToElement();
				endorsePolicyPage.closeButton.click();

			}

			// Click on view active renewal
			policySummaryPage.viewActiveRenewal.scrollToElement();
			policySummaryPage.viewActiveRenewal.click();
			Assertions.passTest("Policy summary page", "Clicked on view active renewal link");

			// Checking PB end roll forwarded to release renewal quote
			Assertions.addInfo("Scenario 04",
					"Verifying the PB changes performed on previous policy are roll forwarded to release renewal quote");
			Assertions.verify(
					accountOverviewPage.altQuoteOptEarthquakeDed
							.formatDynamicPath(2).getData().contains(testData.get("DeductibleValue")),
					true, "Account overview page",
					"Verifying named storm value is roll forwarded correctly, latest named storm value is "
							+ accountOverviewPage.altQuoteOptEarthquakeDed.formatDynamicPath(2).getData(),
					false, false);
			Assertions.verify(
					accountOverviewPage.priorLoses.formatDynamicPath("Burglary").getData()
							.contains(testData.get("PriorLossType1")),
					true, "Account overview page",
					"Verifying prior loses value is roll forwarded correctly, latest prior loses is "
							+ accountOverviewPage.priorLoses.formatDynamicPath("Burglary").getData(),
					false, false);

			// click on building link
			accountOverviewPage.buildingLink.formatDynamicPath(2, 1).scrollToElement();
			accountOverviewPage.buildingLink.formatDynamicPath(2, 1).click();
			Assertions.verify(
					accountOverviewPage.address.formatDynamicPath("Boaz").getData()
							.contains(testData.get("L2B1-BldgCity")),
					true, "Account overview page", "Verifying address is roll forwarded correctly,address is "
							+ accountOverviewPage.priorLoses.formatDynamicPath("Boaz").getData(),
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 ended");

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC011 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC011 ", "Executed Successfully");
			}
		}
	}
}
