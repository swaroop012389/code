/** Program Description: Validating referral conditions for prior loss, short term rental, lapse in coverage and number of stories as producer and IO-21792
 *  Author			   : Pavan Mule
 *  Date of Creation   : 12/24/2021
 **/

package com.icat.epicenter.test.naho.regression.ESBNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class METC001 extends AbstractNAHOTest {

	public METC001() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBNB/METC001.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		LoginPage loginPage = new LoginPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuotablePage = new BuildingNoLongerQuoteablePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ReferralPage referralPage = new ReferralPage();

		// Initializing Variables
		Map<String, String> testData;
		int dataValue1 = 0;
		int dataValue2 = 1;
		testData = data.get(dataValue1);
		String quoteNumber;
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home page",
					"Producer home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New account", "New account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility page",
					"Eligibility page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling page",
					"Dwelling page loaded successfully", false, false);
			dwellingPage.addDwellingDetails(testData, 1, 1);
			dwellingPage.addRoofDetails(testData, 1, 1);
			dwellingPage.enterProtectionDiscountsDetails(testData, 1, 1);
			if (dwellingPage.internalSection.checkIfElementIsPresent()
					&& dwellingPage.internalSection.checkIfElementIsDisplayed()) {
				dwellingPage.internalSection.scrollToElement();
				dwellingPage.internalSection.click();
			}
			if (!testData.get("L" + 1 + "D" + 1 + "-ProtectionClassOverride").equals("")) {
				dwellingPage.enterInternalSectionDetails(testData, 1, 1);
			}
			waitTime(3);

			// Opening Dwelling Values Accordion
			dwellingPage.dwellingValuesLink.checkIfElementIsPresent();
			dwellingPage.dwellingValuesLink.checkIfElementIsDisplayed();
			dwellingPage.dwellingValuesLink.scrollToElement();
			dwellingPage.dwellingValuesLink.click();

			// clicking on CovE Arrow
			dwellingPage.coverageEArrow.checkIfElementIsPresent();
			dwellingPage.coverageEArrow.scrollToElement();
			dwellingPage.coverageEArrow.click();

			// Adding Ticket IO-20815
			Assertions.addInfo("Scenario 01",
					"Verifying if the CovE Value is not more than $500,000 When Short Term Rental is Selected as Yes");
			if (!dwellingPage.coverageEOption.formatDynamicPath("$1,000,000").checkIfElementIsPresent()) {
				Assertions.verify(dwellingPage.coverageEOption.formatDynamicPath("$500,000").checkIfElementIsPresent()
						&& dwellingPage.coverageEOption.formatDynamicPath("$500,000").checkIfElementIsDisplayed(), true,
						"Dwelling Page", "The highest value that can be select is $500,000,"
								+ " when short term rental is selected as Yes. ",
						false, false);
			} else {
				Assertions.verify(
						dwellingPage.coverageEOption.formatDynamicPath("$1,000,000").checkIfElementIsPresent(), false,
						"Dwelling Page", "The highest value that we can select is $1,000,000,"
								+ " when short term rental is selected as Yes. ",
						false, false);
			}
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// closing dwelling values accordion
			dwellingPage.dwellingValuesLink.checkIfElementIsPresent();
			dwellingPage.dwellingValuesLink.checkIfElementIsDisplayed();
			dwellingPage.dwellingValuesLink.scrollToElement();
			dwellingPage.dwellingValuesLink.click();

			dwellingPage.enterDwellingValuesNAHO(testData, 1, 1);
			waitTime(5); // Control is shifting to roof details link after entering
							// dwelling values instead of clicking on review
							// dwelling
			dwellingPage.reviewDwelling();
			dwellingPage.createQuote.checkIfElementIsPresent();
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			Assertions.passTest("Dwelling page", "Dwelling details entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior loss page",
						"Prior loss page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
				Assertions.passTest("Prior loss page", "Prior loss details entered successfully");
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create quote page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			createQuotePage.enterDeductiblesNAHO(testData);
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);
			createQuotePage.coverageEArrow.click();

			Assertions.addInfo("Scenario 02",
					"Verifying if the CovE Value is not more than $500,000 When Short Term Rental is Selected as Yes");
			if (!createQuotePage.coverageEOption.formatDynamicPath("$1,000,000 (Override)").checkIfElementIsPresent()) {
				Assertions.verify(
						createQuotePage.coverageEOption.formatDynamicPath("$500,000").checkIfElementIsPresent()
								&& createQuotePage.coverageEOption.formatDynamicPath("$500,000")
										.checkIfElementIsDisplayed(),
						true, "Create Quote Page", "The highest value that can be select is $500,000, "
								+ "when short term rental is selected as Yes. ",
						false, false);
			} else {
				Assertions.verify(
						createQuotePage.coverageEOption.formatDynamicPath("$1,000,000 (Override)")
								.checkIfElementIsPresent(),
						false, "Create Quote Page", "The highest value that can be select is $1,000,000, "
								+ "when short term rental is selected as Yes. ",
						false, false);
			}

			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}
			if (createQuotePage.pageName.getData().contains("Create Quote")) {
				createQuotePage.override.scrollToElement();
				createQuotePage.override.click();
			}
			if (createQuotePage.getAQuote.checkIfElementIsPresent()
					&& createQuotePage.getAQuote.checkIfElementIsDisplayed()) {
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();
			}
			Assertions.passTest("Create quote page", "Quote details entered successfully");

			// Asserting referral messages
			Assertions.addInfo("Scenario 03",
					"Asserting referal messages when  'lapse in coverage = yes' and number of stories = 4 ");
			Assertions.verify(
					referQuotePage.referralMessages
							.formatDynamicPath("more than 3 stories").checkIfElementIsDisplayed(),
					true, "Refer quote page",
					"Referral message 1 : "
							+ referQuotePage.referralMessages.formatDynamicPath("more than 3 stories").getData(),
					false, false);
			Assertions.verify(
					referQuotePage.referralMessages.formatDynamicPath("lapse in coverage").checkIfElementIsDisplayed(),
					true, "Refer quote page",
					"Referral message 2 : "
							+ referQuotePage.referralMessages.formatDynamicPath("lapse in coverage").getData(),
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 ended");

			// IO-21792
			// Referring the Quote to USM because number of stories more than 3 and lapse in
			// coverage = Yes
			referQuotePage.contactEmail.clearData();
			referQuotePage.contactEmail.setData("hiho1@icat.com");
			referQuotePage.comments.setData("Test");
			referQuotePage.referQuote.scrollToElement();
			referQuotePage.referQuote.click();

			// Fetching Quote Number
			quoteNumber = referQuotePage.quoteNumberforReferral.getData();
			Assertions.passTest("Referral Page", "Reerred quote number " + quoteNumber);

			// Signing out as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Signing in as USM
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));

			// Searching the referred quote
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Referred Quote searched successfully");

			// Opening the referral
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();
			if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
				referralPage.pickUp.scrollToElement();
				referralPage.pickUp.click();
			}

			// Approving the referral
			// IO-21792-As part of this our expectation is to validate if the referred quote
			// is assigned to USM not "Holder RMS"
			if (referralPage.assignedUser.getData().contentEquals("Sean Minn")) {

				Assertions.verify(referralPage.assignedUser.getData().contains("Sean Minn"), true, "Referral Page",
						"Quote is referred to USM " + referralPage.assignedUser.getData(), false, false);

			} else {

				Assertions.verify(referralPage.assignedUser.getData().contains("Holder RMS"), false, "Referral Page",
						"Quote is referred to USM " + referralPage.assignedUser.getData(), false, false);
			}
			// Ended

			// Signing out as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Signing in as Producer
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));

			testData = data.get(dataValue2);

			// creating New account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home page",
					"Producer home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New account", "New account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility page",
					"Eligibility page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling page",
					"Dwelling page loaded successfully", false, false);
			dwellingPage.addDwellingDetails(testData, 1, 1);
			dwellingPage.addRoofDetails(testData, 1, 1);
			dwellingPage.enterProtectionDiscountsDetails(testData, 1, 1);
			if (dwellingPage.internalSection.checkIfElementIsPresent()
					&& dwellingPage.internalSection.checkIfElementIsDisplayed()) {
				dwellingPage.internalSection.scrollToElement();
				dwellingPage.internalSection.click();
			}
			if (!testData.get("L" + 1 + "D" + 1 + "-ProtectionClassOverride").equals("")) {
				dwellingPage.enterInternalSectionDetails(testData, 1, 1);
			}
			waitTime(3);

			// Opening Dwelling Values Accordion
			dwellingPage.dwellingValuesLink.checkIfElementIsPresent();
			dwellingPage.dwellingValuesLink.checkIfElementIsDisplayed();
			dwellingPage.dwellingValuesLink.scrollToElement();
			dwellingPage.dwellingValuesLink.click();

			// clicking on CovE Arrow
			dwellingPage.coverageEArrow.checkIfElementIsPresent();
			dwellingPage.coverageEArrow.scrollToElement();
			dwellingPage.coverageEArrow.click();

			// Adding Ticket IO-20815
			Assertions.addInfo("Scenario 04",
					"Verifying if the CovE Value is not more than $500,000 When Tenant is Selected as Primary Occupant");
			if (!dwellingPage.coverageEOption.formatDynamicPath("$1,000,000").checkIfElementIsPresent()) {
				Assertions.verify(
						 dwellingPage.coverageEOption.formatDynamicPath("$500,000").checkIfElementIsDisplayed(), true,
						"Dwelling Page",
						"The highest value that can be select is $500,000," + " when Primary is selected as Occupant. ",
						false, false);
			} else {
				Assertions.verify(
						dwellingPage.coverageEOption.formatDynamicPath("$1,000,000").checkIfElementIsPresent(), false,
						"Dwelling Page", "The highest value that we can select is $1,000,000,"
								+ " when Primary is selected as Occupant. ",
						false, false);
			}
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// closing dwelling values accordion
			dwellingPage.dwellingValuesLink.checkIfElementIsPresent();
			dwellingPage.dwellingValuesLink.checkIfElementIsDisplayed();
			dwellingPage.dwellingValuesLink.scrollToElement();
			dwellingPage.dwellingValuesLink.click();

			dwellingPage.enterDwellingValuesNAHO(testData, 1, 1);
			waitTime(5); // Control is shifting to roof details link after entering
							// dwelling values instead of clicking on review
							// dwelling
			dwellingPage.reviewDwelling();
			dwellingPage.createQuote.checkIfElementIsPresent();
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			Assertions.passTest("Dwelling page", "Dwelling details entered successfully");

			// Entering prior loss details
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior loss page",
					"Prior loss page loaded successfully", false, false);
			priorLossesPage.lossesInThreeYearsNo.click();
			priorLossesPage.continueButton.click();
			Assertions.passTest("Prior loss page", "Prior loss details entered successfully");

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create quote page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			createQuotePage.enterDeductiblesNAHO(testData);
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);
			createQuotePage.coverageEArrow.click();

			Assertions.addInfo("Scenario 05",
					"Verifying if the CovE Value is not more than $500,000 When Short Term Rental is Selected as Yes");
			if (!createQuotePage.coverageEOption.formatDynamicPath("$1,000,000 (Override)").checkIfElementIsPresent()) {
				Assertions.verify(
						createQuotePage.coverageEOption.formatDynamicPath("$500,000").checkIfElementIsPresent()
								&& createQuotePage.coverageEOption.formatDynamicPath("$500,000")
										.checkIfElementIsDisplayed(),
						true, "Create Quote Page", "The highest value that can be select is $500,000, "
								+ "when short term rental is selected as Yes. ",
						false, false);
			} else {
				Assertions.verify(
						createQuotePage.coverageEOption.formatDynamicPath("$1,000,000 (Override)")
								.checkIfElementIsPresent(),
						false, "Create Quote Page", "The highest value that can be select is $1,000,000, "
								+ "when short term rental is selected as Yes. ",
						false, false);
			}

			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}
			if (createQuotePage.pageName.getData().contains("Create Quote")) {
				createQuotePage.override.scrollToElement();
				createQuotePage.override.click();
			}
			if (createQuotePage.getAQuote.checkIfElementIsPresent()
					&& createQuotePage.getAQuote.checkIfElementIsDisplayed()) {
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();
			}
			Assertions.passTest("Create quote page", "Quote details entered successfully");

			// Signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Signing in as USM
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));

			testData = data.get(dataValue2);

			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"USM home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New account", "New account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility page",
					"Eligibility page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling page",
					"Dwelling page loaded successfully", false, false);
			dwellingPage.addDwellingDetails(testData, 1, 1);
			dwellingPage.addRoofDetails(testData, 1, 1);
			dwellingPage.enterProtectionDiscountsDetails(testData, 1, 1);
			if (dwellingPage.internalSection.checkIfElementIsPresent()
					&& dwellingPage.internalSection.checkIfElementIsDisplayed()) {
				dwellingPage.internalSection.scrollToElement();
				dwellingPage.internalSection.click();
			}
			if (!testData.get("L" + 1 + "D" + 1 + "-ProtectionClassOverride").equals("")) {
				dwellingPage.enterInternalSectionDetails(testData, 1, 1);
			}
			waitTime(3);

			// Opening Dwelling Values Accordion
			dwellingPage.dwellingValuesLink.checkIfElementIsPresent();
			dwellingPage.dwellingValuesLink.checkIfElementIsDisplayed();
			dwellingPage.dwellingValuesLink.scrollToElement();
			dwellingPage.dwellingValuesLink.click();

			// clicking on CovE Arrow
			waitTime(2);
			dwellingPage.coverageEArrow.checkIfElementIsPresent();
			dwellingPage.coverageEArrow.scrollToElement();
			dwellingPage.coverageEArrow.click();

			// Adding Ticket IO-20815
			Assertions.addInfo("Scenario 06",
					"Verifying if the CovE Value is not more than $500,000 When Tenant is Selected as Primary Occupant");
			if (!dwellingPage.coverageEOption.formatDynamicPath("$1,000,000").checkIfElementIsPresent()) {
				Assertions.verify(dwellingPage.coverageEOption.formatDynamicPath("$500,000").checkIfElementIsPresent()
						&& dwellingPage.coverageEOption.formatDynamicPath("$500,000").checkIfElementIsDisplayed(), true,
						"Dwelling Page",
						"The highest value that can be select is $500,000," + " when Tenant is selected as Occupant. ",
						false, false);
			} else {
				Assertions.verify(
						dwellingPage.coverageEOption.formatDynamicPath("$1,000,000").checkIfElementIsPresent(), false,
						"Dwelling Page", "The highest value that we can select is $1,000,000,"
								+ " when Tenant is selected as Occupant. ",
						false, false);
			}
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// closing dwelling values accordion
			dwellingPage.dwellingValuesLink.checkIfElementIsPresent();
			dwellingPage.dwellingValuesLink.checkIfElementIsDisplayed();
			dwellingPage.dwellingValuesLink.scrollToElement();
			dwellingPage.dwellingValuesLink.click();

			dwellingPage.enterDwellingValuesNAHO(testData, 1, 1);
			waitTime(5); // Control is shifting to roof details link after entering
							// dwelling values instead of clicking on review
							// dwelling
			dwellingPage.reviewDwelling();
			dwellingPage.createQuote.checkIfElementIsPresent();
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			if (buildingNoLongerQuotablePage.pageName.getData().contains("Buildings No")) {
				buildingNoLongerQuotablePage.overrideNoLongerQuotableBuildings();
			}
			Assertions.passTest("Dwelling page", "Dwelling details entered successfully");

			// Entering prior loss details
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior loss page",
					"Prior loss page loaded successfully", false, false);
			priorLossesPage.lossesInThreeYearsNo.click();
			priorLossesPage.continueButton.click();
			Assertions.passTest("Prior loss page", "Prior loss details entered successfully");

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create quote page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			createQuotePage.enterDeductiblesNAHO(testData);
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);
			createQuotePage.coverageEArrow.scrollToElement();
			createQuotePage.coverageEArrow.waitTillButtonIsClickable(60);
			createQuotePage.coverageEArrow.click();

			Assertions.addInfo("Scenario 07",
					"Verifying if the CovE Value is not more than $500,000 When Short Term Rental is Selected as Yes");
			if (createQuotePage.coverageEOption.formatDynamicPath("$1,000,000 (Override)").checkIfElementIsPresent()
					&& createQuotePage.coverageEOption.formatDynamicPath("$1,000,000 (Override)")
							.checkIfElementIsDisplayed()) {
				Assertions.verify(
						createQuotePage.coverageEOption.formatDynamicPath("$1,000,000 (Override)")
								.checkIfElementIsPresent()
								&& createQuotePage.coverageEOption.formatDynamicPath("$1,000,000 (Override)")
										.checkIfElementIsDisplayed(),
						true, "Create Quote Page", "The highest value that can be select is $1,000,000 (Override), "
								+ " when Tenant is selected as Occupant. ",
						false, false);
			} else {
				Assertions.verify(
						createQuotePage.coverageEOption.formatDynamicPath("$500,000").checkIfElementIsPresent(), true,
						"Create Quote Page",
						"The highest value that can be select is $500,000, " + " when Tenant is selected as Occupant. ",
						false, false);
			}

			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}
			if (createQuotePage.pageName.getData().contains("Create Quote")) {
				createQuotePage.override.scrollToElement();
				createQuotePage.override.click();
			}
			if (createQuotePage.getAQuote.checkIfElementIsPresent()
					&& createQuotePage.getAQuote.checkIfElementIsDisplayed()) {
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();
			}
			Assertions.passTest("Create quote page", "Quote details entered successfully");

			// Clicking on homepage button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// creating New account
			testData = data.get(dataValue1);
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"USM home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New account", "New account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility page",
					"Eligibility page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling page",
					"Dwelling page loaded successfully", false, false);
			dwellingPage.addDwellingDetails(testData, 1, 1);
			dwellingPage.addRoofDetails(testData, 1, 1);
			dwellingPage.enterProtectionDiscountsDetails(testData, 1, 1);
			if (dwellingPage.internalSection.checkIfElementIsPresent()
					&& dwellingPage.internalSection.checkIfElementIsDisplayed()) {
				dwellingPage.internalSection.scrollToElement();
				dwellingPage.internalSection.click();
			}
			if (!testData.get("L" + 1 + "D" + 1 + "-ProtectionClassOverride").equals("")) {
				dwellingPage.enterInternalSectionDetails(testData, 1, 1);
			}
			waitTime(3);

			// Opening Dwelling Values Accordion
			dwellingPage.dwellingValuesLink.checkIfElementIsPresent();
			dwellingPage.dwellingValuesLink.checkIfElementIsDisplayed();
			dwellingPage.dwellingValuesLink.scrollToElement();
			dwellingPage.dwellingValuesLink.click();

			// clicking on CovE Arrow
			dwellingPage.coverageEArrow.checkIfElementIsPresent();
			dwellingPage.coverageEArrow.scrollToElement();
			dwellingPage.coverageEArrow.click();

			// Adding Ticket IO-20815
			Assertions.addInfo("Scenario 08",
					"Verifying if the CovE Value is not more than $500,000 When Short Term Rental is Selected as Yes");
			if (dwellingPage.coverageEOption.formatDynamicPath("$500,000").checkIfElementIsPresent()
					&& dwellingPage.coverageEOption.formatDynamicPath("$500,000").checkIfElementIsDisplayed()) {
				Assertions.verify(dwellingPage.coverageEOption.formatDynamicPath("$500,000").checkIfElementIsPresent()
						&& dwellingPage.coverageEOption.formatDynamicPath("$500,000").checkIfElementIsDisplayed(), true,
						"Dwelling Page", "The highest value that can be select is $500,000,"
								+ " when short term rental is selected as Yes. ",
						false, false);
			} else {
				Assertions.verify(
						dwellingPage.coverageEOption.formatDynamicPath("$1,000,000").checkIfElementIsPresent(), false,
						"Dwelling Page", "The highest value that we can select is $1,000,000,"
								+ " when short term rental is selected as Yes. ",
						false, false);
			}
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// closing dwelling values accordion
			dwellingPage.dwellingValuesLink.checkIfElementIsPresent();
			dwellingPage.dwellingValuesLink.checkIfElementIsDisplayed();
			dwellingPage.dwellingValuesLink.scrollToElement();
			dwellingPage.dwellingValuesLink.click();

			dwellingPage.enterDwellingValuesNAHO(testData, 1, 1);
			waitTime(5); // Control is shifting to roof details link after entering
							// dwelling values instead of clicking on review
							// dwelling
			dwellingPage.reviewDwelling();
			dwellingPage.createQuote.checkIfElementIsPresent();
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			if (buildingNoLongerQuotablePage.pageName.getData().contains("Buildings No")) {
				buildingNoLongerQuotablePage.overrideNoLongerQuotableBuildings();
			}
			Assertions.passTest("Dwelling page", "Dwelling details entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior loss page",
						"Prior loss page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
				Assertions.passTest("Prior loss page", "Prior loss details entered successfully");
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create quote page",
					"Create quote page loaded successfully", false, false);
//				createQuotePage.enterQuoteDetailsNAHO(testData);
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			createQuotePage.enterDeductiblesNAHO(testData);
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);
			createQuotePage.coverageEArrow.scrollToElement();
			createQuotePage.coverageEArrow.waitTillButtonIsClickable(60);
			createQuotePage.coverageEArrow.click();

			Assertions.addInfo("Scenario 09",
					"Verifying if the CovE Value is not more than $500,000 When Short Term Rental is Selected as Yes");
			if (createQuotePage.coverageEOption.formatDynamicPath("$1,000,000 (Override)").checkIfElementIsPresent()
					&& createQuotePage.coverageEOption.formatDynamicPath("$1,000,000 (Override)")
							.checkIfElementIsDisplayed()) {
				Assertions.verify(
						createQuotePage.coverageEOption.formatDynamicPath("$1,000,000 (Override)")
								.checkIfElementIsPresent()
								&& createQuotePage.coverageEOption.formatDynamicPath("$1,000,000 (Override)")
										.checkIfElementIsDisplayed(),
						true, "Create Quote Page", "The highest value that can be select is $1,000,000, "
								+ "when short term rental is selected as Yes. ",
						false, false);
			} else {
				Assertions.verify(
						createQuotePage.coverageEOption.formatDynamicPath("$500,000").checkIfElementIsPresent(), false,
						"Create Quote Page", "The highest value that can be select is $500,000, "
								+ "when short term rental is selected as Yes. ",
						false, false);
			}

			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}
			if (createQuotePage.pageName.getData().contains("Create Quote")) {
				createQuotePage.override.scrollToElement();
				createQuotePage.override.click();
			}
			if (createQuotePage.getAQuote.checkIfElementIsPresent()
					&& createQuotePage.getAQuote.checkIfElementIsDisplayed()) {
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();
			}

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("METC001 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("METC001 ", "Executed Successfully");
			}
		}
	}
}
