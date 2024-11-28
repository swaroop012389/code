/** Program Description: Performing various validations on NAHO product as Producer
 *  Author			   : Sowndarya
 *  Date of Creation   : 09/17/2021
 **/
package com.icat.epicenter.test.naho.regression.NSPNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
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
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBFLTC006_GEN extends AbstractNAHOTest {

	public PNBFLTC006_GEN() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSPNB/FL006.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the page objects
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		BuildingPage buildingPage = new BuildingPage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestSubmittedPage = new BindRequestSubmittedPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();

		// Initializing the variables
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		Map<String, String> testData = data.get(data_Value1);
		boolean addressFound = false;
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Create account as producer
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page ",
					"Producer Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account ", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page ",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page ", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			// Entering Number of Stories=Number of Units=Total Square footage=0
			// Electrical Update=HVAC Update=Plumbing Update year= 2022
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page ",
					"Dwelling Page loaded successfully", false, false);
			Assertions.addInfo("Dwelling Page",
					"Entering  Number of Stories,Number of Units,Total Square footage as 0, Year built,Electrical Update,HVAC Update, Plumbing Update years as Future year");
			dwellingPage.addDwellingDetails(testData, 1, 1);
			dwellingPage.addRoofDetails(testData, 1, 1);
			dwellingPage.enterProtectionDiscountsDetails(testData, 1, 1);
			dwellingPage.enterDwellingValuesNAHO(testData, 1, 1);
			Assertions.passTest("Dwelling Page ", "Dwelling details entered successfully");

			// click on Review dwelling
			dwellingPage.reviewDwelling.waitTillPresenceOfElement(60);
			dwellingPage.reviewDwelling.waitTillVisibilityOfElement(60);
			dwellingPage.waitTime(2);// need wait time to scroll to the element
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();

			// Assert the Error Messages
			Assertions.addInfo("Scenario 01",
					"Assert the Error messages when Number of Stories,Number of Units,Total Square footage are 0, Year built,Electrical Update,HVAC Update, Plumbing Update years are Future year");
			Assertions.verify(
					dwellingPage.dwellingDetailsErrorMessages1.formatDynamicPath("Year constructed")
							.checkIfElementIsDisplayed(),
					true, "Dwelling Page",
					"The Error Message "
							+ dwellingPage.dwellingDetailsErrorMessages1.formatDynamicPath("Year constructed").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					dwellingPage.dwellingDetailsErrorMessages1
							.formatDynamicPath("Total square footage").checkIfElementIsDisplayed(),
					true, "Dwelling Page",
					"The Error Message " + dwellingPage.dwellingDetailsErrorMessages1
							.formatDynamicPath("Total square footage").getData() + " displayed is verified",
					false, false);
			Assertions.verify(
					dwellingPage.dwellingDetailsErrorMessages1.formatDynamicPath("Number of stories")
							.checkIfElementIsDisplayed(),
					true, "Dwelling Page", "The Error Message " + dwellingPage.dwellingDetailsErrorMessages1
							.formatDynamicPath("Number of stories").getData() + " displayed is verified",
					false, false);
			Assertions.verify(
					dwellingPage.dwellingDetailsErrorMessages1.formatDynamicPath("Year plumbing")
							.checkIfElementIsDisplayed(),
					true, "Dwelling Page",
					"The Error Message "
							+ dwellingPage.dwellingDetailsErrorMessages1.formatDynamicPath("Year plumbing").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					dwellingPage.dwellingDetailsErrorMessages1.formatDynamicPath("Year electrical")
							.checkIfElementIsDisplayed(),
					true, "Dwelling Page",
					"The Error Message "
							+ dwellingPage.dwellingDetailsErrorMessages1.formatDynamicPath("Year electrical").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					dwellingPage.dwellingDetailsErrorMessages1.formatDynamicPath("HVAC").checkIfElementIsDisplayed(),
					true, "Dwelling Page",
					"The Error Message "
							+ dwellingPage.dwellingDetailsErrorMessages1.formatDynamicPath("HVAC").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					dwellingPage.dwellingDetailsErrorMessages1.formatDynamicPath("Number of units")
							.checkIfElementIsDisplayed(),
					true, "Dwelling Page",
					"The Error Message "
							+ dwellingPage.dwellingDetailsErrorMessages1.formatDynamicPath("Number of units").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Edit the Dwelling details
			// Change the Year built = 2018,Electrical Update = 2017,HVAC Update = 2017,
			// Plumbing Update = 2017
			testData = data.get(data_Value2);
			Assertions.addInfo("Dwelling Page",
					"Changing the Year built = 2018,Electrical Update = 2017,HVAC Update = 2017, Plumbing Update = 2017");
			dwellingPage.addDwellingDetails(testData, 1, 1);
			dwellingPage.enterProtectionDiscountsDetails(testData, 1, 1);
			Assertions.passTest("Dwelling Page", "Modified the Dwelling details successfully");

			// Click on Resubmit
			dwellingPage.reSubmit.waitTillPresenceOfElement(60);
			dwellingPage.reSubmit.waitTillVisibilityOfElement(60);
			dwellingPage.waitTime(2);// need wait time to scroll to the element
			dwellingPage.reSubmit.scrollToElement();
			dwellingPage.reSubmit.click();

			// Click on get a quote
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			Assertions.passTest("Dwelling Page", "Clicked on create quote button successfully");

			addressFound = false;

			if (dwellingPage.addressMsg.checkIfElementIsPresent() && dwellingPage.addressMsg.checkIfElementIsDisplayed()
					&& !addressFound) {
				for (int i = 1; i <= 50; i++) {
					if (dwellingPage.editDwellingSymbol.checkIfElementIsPresent()) {
						dwellingPage.editDwellingSymbol.scrollToElement();
						dwellingPage.editDwellingSymbol.click();
					}
					dwellingPage.manualEntry.click();
					dwellingPage.manualEntryAddress.waitTillVisibilityOfElement(60);
					dwellingPage.manualEntryAddress.setData(dwellingPage.manualEntryAddress.getData().replace(
							dwellingPage.manualEntryAddress.getData().replaceAll("[^0-9]", ""),
							(Integer.parseInt(dwellingPage.manualEntryAddress.getData().replaceAll("[^0-9]", ""))) + 2
									+ ""));
					dwellingPage.manualEntryAddress.tab();
					dwellingPage.dwellingValuesLink.click();
					dwellingPage.scrollToBottomPage();
					dwellingPage.reviewDwelling();

					if (!dwellingPage.addressMsg.checkIfElementIsPresent()
							|| !dwellingPage.addressMsg.checkIfElementIsPresent()) {
						addressFound = true;
						break;
					}
				}
			}

			if (dwellingPage.createQuote.checkIfElementIsPresent()
					&& dwellingPage.createQuote.checkIfElementIsDisplayed()) {
				dwellingPage.createQuote.scrollToElement();
				dwellingPage.createQuote.click();
				Assertions.passTest("Dwelling Page", "Clicked on create quote button successfully");

			}

			// Entering prior loss details
			testData = data.get(data_Value1);
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Clicl on get a quote
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on Get A Quote Button");

			// Assert the hard stop message
			Assertions.addInfo("Scenario 02",
					"Asserting Error messages when Year built = 2018,Electrical Update = 2017,HVAC Update = 2017, Plumbing Update = 2017");
			Assertions.verify(
					createQuotePage.errorMessageWarningPage.formatDynamicPath("Year plumbing")
							.checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Error Message "
							+ createQuotePage.errorMessageWarningPage.formatDynamicPath("Year plumbing").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					createQuotePage.errorMessageWarningPage
							.formatDynamicPath("Year electrical").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Error Message "
							+ createQuotePage.errorMessageWarningPage.formatDynamicPath("Year electrical").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					createQuotePage.errorMessageWarningPage.formatDynamicPath("HVAC").checkIfElementIsDisplayed(), true,
					"Create Quote Page",
					"The Error Message " + createQuotePage.errorMessageWarningPage.formatDynamicPath("HVAC").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Click on previous button
			createQuotePage.previous.scrollToElement();
			createQuotePage.previous.click();
			Assertions.passTest("Create Quote Page", "Clicked on previous button successfully");

			// Click on Edit building pencil button
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();
			Assertions.passTest("Account Overview Page", "Clicked on editbuilding link successfully");

			// Edit the Dwelling details to update the Electrical,HAVC,Plumbing years
			// Changing Electrical Update = 2019,HVAC Update = 2019, Plumbing Update =
			// 2019,Roof replaced year = 2017
			testData = data.get(data_Value3);
			Assertions.addInfo("Dwelling Page",
					"Changing the Electrical Update year = 2019,HVAC Update year= 2019, Plumbing Update year = 2019,Roof replaced year = 2017");
			dwellingPage.addRoofDetails(testData, 1, 1);
			dwellingPage.enterProtectionDiscountsDetails(testData, 1, 1);
			Assertions.passTest("Dwelling Page", "Modified the Dwelling details successfully");

			// Click on review
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();

			// Assert the error message
			Assertions.addInfo("Scenario 03",
					"Asserting Error messages when Year built = 2018,Electrical Update = 2019,HVAC Update = 2019, Plumbing Update = 2019,Year Roof Replaced = 2017");
			Assertions.verify(dwellingPage.protectionClassWarMsg.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"The Error Message " + dwellingPage.protectionClassWarMsg.getData() + " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Remove the Roof Replaced year
			Assertions.addInfo("Dwelling Page", "Removing the Roof Replaced Year");
			dwellingPage.roofDetailsLink.waitTillElementisEnabled(60);
			dwellingPage.roofDetailsLink.waitTillVisibilityOfElement(60);
			dwellingPage.roofDetailsLink.scrollToElement();
			dwellingPage.roofDetailsLink.click();
			dwellingPage.yearRoofLastReplaced.scrollToElement();
			dwellingPage.yearRoofLastReplaced.clearData();
			Assertions.passTest("Dwelling Page", "Removed the Roof Replaced year successfully");

			// Click on Resubmit
			dwellingPage.reSubmit.waitTillPresenceOfElement(60);
			dwellingPage.reSubmit.waitTillVisibilityOfElement(60);
			dwellingPage.waitTime(2);// need wait time to scroll to the element
			dwellingPage.reSubmit.scrollToElement();
			dwellingPage.reSubmit.click();

			// To change the Dwelling address
			if (dwellingPage.addressMsg.checkIfElementIsPresent() && dwellingPage.addressMsg.checkIfElementIsDisplayed()
					&& !addressFound) {
				for (int i = 1; i <= 15; i++) {
					if (dwellingPage.editDwellingSymbol.checkIfElementIsPresent()) {
						dwellingPage.editDwellingSymbol.scrollToElement();
						dwellingPage.editDwellingSymbol.click();
					}
					buildingPage.manualEntry.scrollToElement();
					dwellingPage.manualEntry.click();
					dwellingPage.manualEntryAddress.waitTillVisibilityOfElement(60);
					dwellingPage.manualEntryAddress.setData(dwellingPage.manualEntryAddress.getData().replace(
							dwellingPage.manualEntryAddress.getData().replaceAll("[^0-9]", ""),
							(Integer.parseInt(dwellingPage.manualEntryAddress.getData().replaceAll("[^0-9]", ""))) + 2
									+ ""));
					dwellingPage.manualEntryAddress.tab();
					dwellingPage.dwellingValuesLink.click();
					dwellingPage.scrollToBottomPage();
					dwellingPage.reviewDwelling();

					if (!dwellingPage.addressMsg.checkIfElementIsPresent()) {
						addressFound = true;
						break;
					}
				}
			}

			// Click on Create quote
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			Assertions.passTest("Dwelling Page", "Clicked on Create Quote Button");

			// Click on get a quote
			testData = data.get(data_Value1);
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on Get A Quote Button");

			// Asserting hard stop message when adding prior loss = open claim,unrepaired
			// damage and sinkhole or Catastrophic Ground Cover Collapse as producer
			// Hard stop message is "The account is ineligible due to an open claim.","The
			// account is ineligible due to a Sinkhole or Catastrophic Ground Cover Collapse
			// reported loss at the property.","The account is ineligible due to unrepaired
			// damage."
			Assertions.addInfo("Scenario 04", "Verifying hard stop message");
			Assertions.verify(createQuotePage.globalErr.getData().contains("unrepaired damage"), true,
					"Create quote page",
					"The Hard stop message is " + createQuotePage.globalErr.getData() + " displayed verified", false,
					false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Click on previous link
			createQuotePage.previous.scrollToElement();
			createQuotePage.previous.click();
			Assertions.verify(accountOverviewPage.deleteAccount.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account overview page loaded successfully", false, false);

			// Click on edit prior loss link
			accountOverviewPage.priorLossEditLink.scrollToElement();
			accountOverviewPage.priorLossEditLink.click();
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior loss page loaded successfully", false, false);

			// Select prior loss = No
			priorLossesPage.lossesInThreeYearsNo.scrollToElement();
			priorLossesPage.lossesInThreeYearsNo.click();
			Assertions.passTest("Prior Loss Page", "Clicked on No button successfully");
			priorLossesPage.continueButton.scrollToElement();
			priorLossesPage.continueButton.click();
			Assertions.passTest("Prior Loss Page", "Clicked on continue button successfully");

			Assertions.verify(accountOverviewPage.deleteAccount.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account overview page loaded successfully", false, false);
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create quote page loaded successfully", false, false);

			// Entering Coverage E as None
			// Coverage E = None
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			Assertions.passTest("Create Quote Page", "Entered Coverage E as None");
			// Clicl on get a quote
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on Get A Quote Button");

			// Assert the Referral Messages
			Assertions.verify(referQuotePage.referQuote.checkIfElementIsDisplayed(), true, "Refer Quote Page",
					"Refer Quote Page Loaded successfully", false, false);
			Assertions.addInfo("Scenario 05",
					"Assert the Referral Messages when Coverage E is None and when we add Sinkhole/CGCC losses");
			Assertions.verify(
					createQuotePage.coverageEWarningmessage.formatDynamicPath("limit for Cov F")
							.checkIfElementIsDisplayed(),
					true, "Refer Quote Page",
					"The Referral Message "
							+ createQuotePage.coverageEWarningmessage.formatDynamicPath("limit for Cov F").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Enter Referral Contact Details
			referQuotePage.contactName.setData(testData.get("ProducerName"));
			referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
			referQuotePage.referQuote.click();
			Assertions.passTest("Refer Quote Page", "Quote referred successfully");
			String quoteNumber = referQuotePage.quoteNumberforReferral.getData();
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

			// edit dwelling then update year built to 1980[41 years back], remove
			// Electrical
			// Update, HVAC Update,
			// Plumbing Update and add coverage E value and click on get a quote
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Dwelling");

			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page Loaded successfully", false, false);
			testData = data.get(data_Value3);
			Assertions.addInfo("Dwelling Page", "Updating the Year built = 1980 and Adding Coverage E value");
			dwellingPage.yearBuilt.scrollToElement();
			dwellingPage.yearBuilt.clearData();
			if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
				dwellingPage.expiredQuotePopUp.scrollToElement();
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
				dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}
			dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));
			if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
				dwellingPage.expiredQuotePopUp.scrollToElement();
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
				dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}
			dwellingPage.yearBuilt.tab();
			Assertions.passTest("Dwelling Page", "Year Built Updated to " + testData.get("L1D1-DwellingYearBuilt"));

			// Clearing the years
			dwellingPage.protectionDiscounts.waitTillVisibilityOfElement(60);
			dwellingPage.protectionDiscounts.scrollToElement();
			dwellingPage.protectionDiscounts.click();

			// Clear Plumbing Year Update
			dwellingPage.yearPlumbingUpdated.scrollToElement();
			dwellingPage.yearPlumbingUpdated.clearData();

			// Clear Electrical Update Year
			dwellingPage.yearElectricalUpdated.scrollToElement();
			dwellingPage.yearElectricalUpdated.clearData();

			// Clear HVAC Update year
			dwellingPage.yearHVACUpdated.scrollToElement();
			dwellingPage.yearHVACUpdated.clearData();

			// click on Review dwelling
			dwellingPage.reviewDwelling.waitTillPresenceOfElement(60);
			dwellingPage.reviewDwelling.waitTillVisibilityOfElement(60);
			dwellingPage.waitTime(2);// need wait time to scroll to element
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();

			// Click on create quote
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			Assertions.passTest("Dwelling Page", "Clicked on Create Quote");

			// Click on get a quote
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on Get A quote button successfully");

			// Asserting hard stop message when year built 40 year old and with no
			// HVAC/Electrical updates in the last 15 years as producer
			Assertions.addInfo("Scenario 06", "Asserting hard stop message");
			Assertions.verify(
					createQuotePage.globalErr.getData().contains(
							"building being 40 years or older with no HVAC/Electrical"),
					true, "Dwelling Page",
					"The hard stop message is " + createQuotePage.globalErr.getData() + " displayed verified", false,
					false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Click on previous button
			createQuotePage.previous.scrollToElement();
			createQuotePage.previous.click();
			Assertions.passTest("Create Quote Page", "Clicked on previous button successfully");

			// Click on Edit building pencil button
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();
			Assertions.passTest("Account Overview Page", "Clicked on editbuilding link successfully");

			// Update year built
			testData = data.get(data_Value2);
			dwellingPage.yearBuilt.scrollToElement();
			dwellingPage.yearBuilt.clearData();
			dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));
			dwellingPage.yearBuilt.tab();

			// Click on create quote
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();

			// Add the Coverage E value
			testData = data.get(data_Value3);
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.coverageEArrow.waitTillVisibilityOfElement(60);
			createQuotePage.coverageEArrow.scrollToElement();
			createQuotePage.coverageEArrow.click();
			createQuotePage.coverageEOption.formatDynamicPath(testData.get("L1D1-DwellingCovE")).scrollToElement();
			createQuotePage.coverageEOption.formatDynamicPath(testData.get("L1D1-DwellingCovE")).click();
			createQuotePage.loading.waitTillInVisibilityOfElement(60);
			Assertions.passTest("Create Quote Page",
					"The Coverage E value Entered is " + testData.get("L1D1-DwellingCovE"));

			// click on get a quote
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on get a quote");

			if (referQuotePage.contactName.checkIfElementIsPresent()
					&& referQuotePage.contactName.checkIfElementIsDisplayed()) {
				// Enter Referral Contact Details
				referQuotePage.contactName.setData(testData.get("ProducerName"));
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.click();
				Assertions.passTest("Refer Quote Page", "Quote referred successfully");
				String quoteNumber1 = referQuotePage.quoteNumberforReferral.getData();
				Assertions.passTest("Refer Quote Page", "The Quote Number is " + quoteNumber1);

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
				// homePage.searchReferral(quoteNumber1);
				homePage.searchQuote(quoteNumber1);
				Assertions.passTest("Home Page", "Searched Submitted Quote" + quoteNumber1 + " successfullly");

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
				homePage.searchQuoteByProducer(quoteNumber1);

			}

			// click on edit dwelling
			Assertions.verify(accountOverviewPage.editDwelling.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			Assertions.passTest("Account Overview page", "Quote Number : "
					+ accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12));
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Dwelling");

			// Changing the year built to 2018
			// Year Built =2018
			testData = data.get(data_Value2);
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page Loaded successfully", false, false);
			dwellingPage.yearBuilt.scrollToElement();
			dwellingPage.yearBuilt.clearData();
			if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
				dwellingPage.expiredQuotePopUp.scrollToElement();
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
				dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}
			dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));
			if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
				dwellingPage.expiredQuotePopUp.scrollToElement();
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
				dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}
			dwellingPage.yearBuilt.tab();
			Assertions.passTest("Dwelling Page", "Changed the Year Built to " + testData.get("L1D1-DwellingYearBuilt"));

			// click on Review dwelling
			dwellingPage.reviewDwelling.waitTillPresenceOfElement(60);
			dwellingPage.reviewDwelling.waitTillVisibilityOfElement(60);
			dwellingPage.waitTime(2);// need wait time to scroll to the element
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();

			// Click on create quote
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			Assertions.passTest("Dwelling Page", "Clicked on Create Quote");

			// Add the Coverage E value
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.coverageEArrow.waitTillVisibilityOfElement(60);
			createQuotePage.coverageEArrow.scrollToElement();
			createQuotePage.coverageEArrow.click();
			createQuotePage.coverageEOption.formatDynamicPath(testData.get("L1D1-DwellingCovE")).scrollToElement();
			createQuotePage.coverageEOption.formatDynamicPath(testData.get("L1D1-DwellingCovE")).click();
			createQuotePage.loading.waitTillInVisibilityOfElement(60);
			Assertions.passTest("Create Quote Page",
					"The Coverage E value Entered is " + testData.get("L1D1-DwellingCovE"));

			// click on get a quote
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on get a quote");

			// Enter Referral Contact Details
			testData = data.get(data_Value1);
			Assertions.verify(referQuotePage.referQuote.checkIfElementIsDisplayed(), true, "Refer Quote Page",
					"Refer Quote Page Loaded successfully", false, false);
			referQuotePage.contactName.setData(testData.get("ProducerName"));
			referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
			referQuotePage.referQuote.click();
			Assertions.passTest("Refer Quote Page", "Quote referred successfully");
			String quoteNumber2 = referQuotePage.quoteNumberforReferral.getData();
			Assertions.passTest("Refer Quote Page", "The New Quote Number is " + quoteNumber2);

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
			// homePage.searchReferral(quoteNumber2);
			homePage.searchQuote(quoteNumber2);
			Assertions.passTest("Home Page", "Searched Submitted Quote" + quoteNumber2 + " successfullly");

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
			homePage.searchQuoteByProducer(quoteNumber2);

			// Click on Request Bind
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber2);

			// Answer underwriting questions
			Assertions.verify(underwritingQuestionsPage.saveButton.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered successfully");

			// Enter Bind Details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Assert the referral Messages
			Assertions.verify(bindRequestSubmittedPage.homePage.checkIfElementIsDisplayed(), true,
					"Bind Request Submitted Page", "Bind Request Submitted Page loaded successfully", false, false);
			Assertions.addInfo("Scenario 07", "Asserting Referral Message when the Entity is Trust");
			Assertions.verify(bindRequestSubmittedPage.entityMessage.checkIfElementIsDisplayed(), true,
					"Bind Request Submitted Page", "The Referral Message "
							+ bindRequestSubmittedPage.entityMessage.getData() + " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");
			Assertions.addInfo("Scenario 08",
					"Asserting Referral Message when the AI is Additional Insured with the Relation Type of Other");
			Assertions.verify(bindRequestSubmittedPage.aiRelationshipMessage.checkIfElementIsDisplayed(), true,
					"Bind Request Submitted Page", "The Referral Message "
							+ bindRequestSubmittedPage.aiRelationshipMessage.getData() + " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			Assertions.addInfo("Scenario 09",
					"Asserting Referral Message when UW question Nhome have a wood or similar type burning stove as a primary or secondary heat source question is Yes");
			Assertions.verify(
					bindRequestSubmittedPage.referralMessages.formatDynamicPath("wood").checkIfElementIsDisplayed(),
					true, "Bind Request Submitted Page",
					"The Referral Message "
							+ bindRequestSubmittedPage.referralMessages.formatDynamicPath("wood").getData()
							+ " diaplayed is verified",
					false, false);
			Assertions.addInfo("Bind Request Submitted Page",
					"Asserting Referral Message when UW question NamedInsuredCancelled/NonRenewed question is Yes");
			Assertions.verify(
					bindRequestSubmittedPage.referralMessages
							.formatDynamicPath("cancelled").checkIfElementIsDisplayed(),
					true, "Bind Request Submitted Page",
					"The Referral Message "
							+ bindRequestSubmittedPage.referralMessages.formatDynamicPath("cancelled").getData()
							+ " diaplayed is verified",
					false, false);
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");
			Assertions.addInfo("Scenario 10",
					"Asserting Referral Message when the named insured high profile or target risk is Yes");
			Assertions.verify(bindRequestSubmittedPage.highTargerRiskRefMsg.checkIfElementIsDisplayed(), true,
					"Bind Request Submitted Page", "The Referral Message "
							+ bindRequestSubmittedPage.highTargerRiskRefMsg.getData() + " diaplayed is verified",
					false, false);
			Assertions.addInfo("Scenario 10", "Scenario 10 Ended");

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

			// Clicking on home page button
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			// searching the quote number in grid and clicking on the quote number link
			homePage.searchQuote(quoteNumber2);
			Assertions.passTest("Home Page ", "Searched Submitted Quote " + quoteNumber2 + " successfullly");

			// Approving Subscription agreement document By USM
			accountOverviewPage.uploadPreBindApproveAsUSM();

			// Approve Referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(approveDeclineQuotePage.approveButton.checkIfElementIsDisplayed(), true,
					"Approve Decline Quote Page", "Approve Decline Quote page loaded successfully", false, false);
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Approve Decline Quote Page", "Bind Request approved successfully");

			// Getting the policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. Policy Number is "
							+ policySummaryPage.policyNumber.getData(),
					false, false);

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBFLTC006 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBFLTC006 ", "Executed Successfully");
			}
		}
	}
}
