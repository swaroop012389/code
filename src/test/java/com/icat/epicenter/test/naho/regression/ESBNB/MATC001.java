/** Program Description: Asserting zipcode ineligibility hard stop message and asserting hard stop message when Cov A less than $150,000 and added ticket IO-20773 and IO-20812(Hurricane, Normal Shingle and Steel or Metal)
 *  Author			   : Pavan Mule
 *  Date of Creation   : 12/24/2021
 **/

package com.icat.epicenter.test.naho.regression.ESBNB;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BrokerOfRecordPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class MATC001 extends AbstractNAHOTest {

	public MATC001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBNB/MATC001.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		LoginPage loginPage = new LoginPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		BrokerOfRecordPage brokerOfRecordPage = new BrokerOfRecordPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();

		// Initializing Variables
		Map<String, String> testData;
		int quoteLen;
		String quoteNumber;
		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
		int dataValue4 = 3;
		int dataValue18 = 17;
		int dataValue32 = 31;
		testData = data.get(dataValue1);

		String locationNumber = testData.get("LocCount");
		int locationCount = Integer.parseInt(locationNumber);
		String dwellingNumber = testData.get("L" + locationCount + "-DwellingCount");
		int dwellingCount = Integer.parseInt(dwellingNumber);
		int locNo = Integer.parseInt(locationNumber);
		int bldgNo = Integer.parseInt(dwellingNumber);
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New account", "New account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility page",
					"Eligibility page loaded successfully", false, false);
			eligibilityPage.zipCode1.waitTillPresenceOfElement(60);
			eligibilityPage.zipCode1.scrollToElement();
			eligibilityPage.zipCode1.setData(testData.get("ZipCode"));
			eligibilityPage.zipCode1.tab();
			eligibilityPage.riskAppliedYes.waitTillPresenceOfElement(60);
			eligibilityPage.riskAppliedYes.scrollToElement();
			eligibilityPage.riskAppliedYes.click();
			eligibilityPage.waitTime(3);// adding wait to display pop up message
			eligibilityPage.continueButton.waitTillPresenceOfElement(60);
			eligibilityPage.continueButton.scrollToElement();
			eligibilityPage.continueButton.click();
			Assertions.passTest("Eligibility page", "Zipcode entered successfully");
			eligibilityPage.waitTime(3);// adding wait to display pop up message

			// Asserting warning message when "Do any of the following apply to this risk" =
			// yes
			Assertions.addInfo("Scenario 01",
					"Asserting Popup box and warning message when 'Do any of the following apply to this risk' = yes");
			Assertions.verify(
					eligibilityPage.ineligibleRiskPopup.checkIfElementIsPresent()
							&& eligibilityPage.ineligibleRiskPopup.checkIfElementIsDisplayed(),
					true, "Eligibility page",
					"Popup box displayed with the name : " + eligibilityPage.ineligibleRiskPopup.getData(), false,
					false);
			Assertions.verify(eligibilityPage.inEligibleRiskErrorMsg.checkIfElementIsDisplayed(), true,
					"Eligibility page",
					"Warning message is displayed " + eligibilityPage.inEligibleRiskErrorMsg.getData(), false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 ended");

			// Click on close
			eligibilityPage.closeButton.waitTillPresenceOfElement(60);
			eligibilityPage.scrollToBottomPage();
			eligibilityPage.closeButton.click();

			// adding below code for validation message
			eligibilityPage.riskAppliedNo.waitTillPresenceOfElement(60);
			eligibilityPage.riskAppliedNo.scrollToElement();
			eligibilityPage.riskAppliedNo.click();
			eligibilityPage.continueButton.waitTillPresenceOfElement(60);
			eligibilityPage.continueButton.scrollToElement();
			eligibilityPage.continueButton.click();

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling page",
					"Dwelling page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
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
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote page", "Quote details entered successfully");

			// Asserting hard stop message when Cov A less than $150,000
			Assertions.addInfo("Scenario 02", "Asserting hard stop message when Cov A less than $150,000");
			Assertions.verify(
					dwellingPage.protectionClassWarMsg.checkIfElementIsPresent()
							&& dwellingPage.protectionClassWarMsg.checkIfElementIsDisplayed(),
					true, "Create quote page", "Hard stop message is " + dwellingPage.protectionClassWarMsg.getData(),
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 ended");

			// Adding Below Code for CR IO-20773
			testData = data.get(dataValue3);
			createQuotePage.coverageADwelling.scrollToElement();
			createQuotePage.coverageADwelling.clearData();

			createQuotePage.coverageADwelling.appendData(testData.get("L1D1-DwellingCovA"));
			createQuotePage.coverageADwelling.tab();
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on get a quote successfully");

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Asserting Quote Number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote number is : " + quoteNumber);

			// Click on producer link
			accountOverviewPage.producerLink.scrollToElement();
			accountOverviewPage.producerLink.click();

			// Updating producer number from 11250.1 to 8581.1
			brokerOfRecordPage.newProducerNumber.scrollToElement();
			brokerOfRecordPage.newProducerNumber.appendData(testData.get("NewProducerNumber"));
			brokerOfRecordPage.borStatusArrow.scrollToElement();
			brokerOfRecordPage.borStatusArrow.click();
			brokerOfRecordPage.borStatusOption.formatDynamicPath(testData.get("BORStatus")).scrollToElement();
			brokerOfRecordPage.borStatusOption.formatDynamicPath(testData.get("BORStatus")).click();
			brokerOfRecordPage.processBOR.scrollToElement();
			brokerOfRecordPage.processBOR.click();
			Assertions.passTest("Broker Of Record Page", "Updating producer number from "
					+ brokerOfRecordPage.oldProducerNumber.getData() + " to " + testData.get("NewProducerNumber"));

			// Verifying hard stop message "The producer number 8581.1 is not authorized for
			// All Risk in Massachusetts"
			Assertions.addInfo("Scenario 03 ", "Verifying hard stop message");
			Assertions.verify(
					brokerOfRecordPage.hardStopMessage.getData()
							.contains("The producer number 8581.1 is not authorized for All Risk in Massachusetts"),
					true, "Broker Of Record Page", "The Actual and Expected Hard stop message  is "
							+ brokerOfRecordPage.hardStopMessage.getData() + " verified",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 ended");
			// CR IO-20773 Ended

			// Signout as usm
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home page", "Sign out as usm successfully");

			// Logging as producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page ", "Logged in as Producer Successfully");

			// Create account as producer
			testData = data.get(dataValue2);
			homePage.enterPersonalLoginDetails();
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page ",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account ", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page ",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page ", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page ",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.addDwellingDetails(testData, 1, 1);
			dwellingPage.addRoofDetails(testData, 1, 1);
			dwellingPage.enterProtectionDiscountsDetails(testData, 1, 1);
			dwellingPage.enterDwellingValuesNAHO(testData, 1, 1);
			Assertions.passTest("Dwelling Page ", "Dwelling details entered successfully");

			// click on Get A quote button
			if (dwellingPage.createQuote.checkIfElementIsPresent()
					&& dwellingPage.createQuote.checkIfElementIsDisplayed()) {
				dwellingPage.createQuote.scrollToElement();
				dwellingPage.createQuote.click();
			}

			// click on Get A quote button
			if (dwellingPage.continueOverrideCost.checkIfElementIsPresent()
					&& dwellingPage.continueOverrideCost.checkIfElementIsDisplayed()) {
				dwellingPage.continueOverrideCost.scrollToElement();
				dwellingPage.continueOverrideCost.click();
			}

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page ",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page ",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page ", "Quote details entered successfully");

			// Asserting hard stop message when Cov A less than $150,000 as producer
			Assertions.addInfo("Scenario 04", "Asserting hard stop message when Cov A less than $150,000");
			Assertions.verify(
					dwellingPage.protectionClassWarMsg.checkIfElementIsPresent()
							&& dwellingPage.protectionClassWarMsg.checkIfElementIsDisplayed(),
					true, "Create quote page", "Hard stop message is " + dwellingPage.protectionClassWarMsg.getData(),
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 ended");

			// Adding IO-20812 Hurricane Shingle
			// Signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Adding Ticket IO-20812(Hurricane Shingle Validations)
			// Loging in as Producer
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));

			// creating New account
			testData = data.get(dataValue4);
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
				Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterInsuredValuesNAHO(testData, locationCount, dwellingCount);
			createQuotePage.enterDeductiblesNAHO(testData);
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			Assertions.addInfo("Scenario 05", "Change Roof Year and Assert Roof Age Message for Hurricane Shingle");
			for (int i = 4; i <= 16; i++) {
				testData = data.get(i);
				if (createQuotePage.previous.checkIfElementIsPresent()
						&& createQuotePage.previous.checkIfElementIsDisplayed()) {
					createQuotePage.previous.waitTillVisibilityOfElement(60);
					createQuotePage.previous.scrollToElement();
					createQuotePage.previous.click();
				}

				// Edit dwelling and update Year built and roof cladding
				if (accountOverviewPage.editDwelling.checkIfElementIsPresent()
						&& accountOverviewPage.editDwelling.checkIfElementIsDisplayed()) {
					accountOverviewPage.editDwelling.scrollToElement();
					accountOverviewPage.editDwelling.click();
				}

				// Entering Location 1 Dwelling 1 Details
				if (!testData.get("L" + locNo + "D" + bldgNo + "-DwellingYearBuilt").equals("")) {
					dwellingPage.yearBuilt.scrollToElement();
					dwellingPage.yearBuilt.clearData();
					if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()
							&& dwellingPage.expiredQuotePopUp.checkIfElementIsDisplayed()) {
						dwellingPage.expiredQuotePopUp.waitTillPresenceOfElement(60);
						dwellingPage.expiredQuotePopUp.scrollToElement();
						dwellingPage.continueWithUpdateBtn.scrollToElement();
						dwellingPage.continueWithUpdateBtn.click();

					}
					dwellingPage.yearBuilt.setData(testData.get("L" + locNo + "D" + bldgNo + "-DwellingYearBuilt"));
					dwellingPage.yearBuilt.tab();
				}
				dwellingPage.addRoofDetails(testData, locationCount, dwellingCount);
				waitTime(5); // Control is shifting to roof details link after entering
								// dwelling values instead of clicking on review
								// dwelling
				Assertions.passTest("Dwelling Page",
						"Roof Cladding is updated to " + testData.get("L1D1-DwellingRoofCladding")
								+ " and Year Built is updated to " + testData.get("L1D1-DwellingYearBuilt"));
				dwellingPage.reviewDwelling.scrollToElement();
				dwellingPage.reviewDwelling.click();
				dwellingPage.createQuote.scrollToElement();
				dwellingPage.createQuote.click();

				// Entering Create quote page Details
				createQuotePage.scrollToBottomPage();
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();

				// Asserting HardStop message for Producer
				String yearBuilt = testData.get("L1D1-DwellingYearBuilt");
				int yearBuiltValue = Integer.parseInt(yearBuilt);
				int currentYear = Calendar.getInstance().get(Calendar.YEAR);
				int diffInYears = (currentYear - yearBuiltValue);
				Assertions.passTest("Dwelling Page", "The Year Difference  is: " + diffInYears);

				if (diffInYears < 15 || diffInYears == 15) {
					// Fetching Quote Number
					quoteLen = accountOverviewPage.quoteNumber.getData().length();
					quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);

					// Printing Quote Number
					Assertions.passTest("Account Overview Page", "The Quote Number: " + quoteNumber);

					// Clicking on edit dwelling button
					accountOverviewPage.editDwelling.waitTillVisibilityOfElement(60);
					accountOverviewPage.editDwelling.scrollToElement();
					accountOverviewPage.editDwelling.click();
				}

				else if (diffInYears >= 16 && diffInYears <= 25) {
					Assertions.verify(
							referQuotePage.roofReferralMessage.checkIfElementIsPresent()
									&& referQuotePage.roofReferralMessage.checkIfElementIsDisplayed(),
							true, "Refer Quote Page",
							referQuotePage.roofReferralMessage.getData() + " is displayed when Roof Cladding is "
									+ testData.get("L1D1-DwellingRoofCladding") + " and Year Built is "
									+ testData.get("L1D1-DwellingYearBuilt"),
							false, false);

					// Referring the Quote to USM
					referQuotePage.contactEmail.clearData();
					referQuotePage.contactEmail.setData("hiho1@icat.com");
					referQuotePage.comments.setData("Test");
					referQuotePage.referQuote.scrollToElement();
					referQuotePage.referQuote.click();

					// Fetching Quote Number
					quoteNumber = referQuotePage.quoteNumberforReferral.getData();

					// Signing out as Producer
					homePage.signOutButton.scrollToElement();
					homePage.signOutButton.click();

					// Signing in as USM
					loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));

					// Searching the referred quote
					homePage.searchQuote(quoteNumber);

					// Opening the referral
					accountOverviewPage.openReferral.scrollToElement();
					accountOverviewPage.openReferral.click();

					if (referralPage.pickUp.checkIfElementIsPresent()
							&& referralPage.pickUp.checkIfElementIsDisplayed()) {
						referralPage.pickUp.scrollToElement();
						referralPage.pickUp.click();
					}

					// Approving the referral
					referralPage.approveOrDeclineRequest.scrollToElement();
					referralPage.approveOrDeclineRequest.click();
					approveDeclineQuotePage.externalUnderwriterComments.setData("Test");
					approveDeclineQuotePage.internalUnderwriterComments.setData("Test");
					approveDeclineQuotePage.approveButton.scrollToElement();
					approveDeclineQuotePage.approveButton.click();

					// Signing out as USM
					homePage.signOutButton.scrollToElement();
					homePage.signOutButton.click();

					// Signing in as Producer
					loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));

					// Searching the Approved QUote
					homePage.searchQuoteByProducer(quoteNumber);

					// Fetching Quote Number
					quoteLen = accountOverviewPage.quoteNumber.getData().length();
					quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);

					// Printing Quote Number
					Assertions.passTest("Account Overview Page", "The Quote Number: " + quoteNumber);

					// Clicking on edit dwelling button
					accountOverviewPage.editDwelling.waitTillVisibilityOfElement(60);
					accountOverviewPage.editDwelling.scrollToElement();
					accountOverviewPage.editDwelling.click();
				}

				else {
					Assertions.verify(
							createQuotePage.globalErr.checkIfElementIsDisplayed() && createQuotePage.globalErr.getData()
									.contains("The account is ineligible due to the roof age"),
							true, "Create Quote Page",
							createQuotePage.globalErr.getData() + " is displayed when Roof Cladding is "
									+ testData.get("L1D1-DwellingRoofCladding") + " and Year Built is "
									+ testData.get("L1D1-DwellingYearBuilt"),
							false, false);

					// Clicking on Previous Button on Create Quote Page
					createQuotePage.previous.scrollToElement();
					createQuotePage.previous.click();

					// Clicking on Edit Dwelling Button on Account Overview Page
					accountOverviewPage.editDwelling.scrollToElement();
					accountOverviewPage.editDwelling.click();
				}

			}

			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Navigating to create quote page
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();

			// Entering Create quote page Details
			createQuotePage.scrollToBottomPage();
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// Fetching Quote Number
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);

			// Printing Quote Number
			Assertions.passTest("Account Overview Page", "The Quote Number: " + quoteNumber);

			// Signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Adding Ticket IO-20812(Normal Shingle Validations)
			// Loging in as Producer
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));

			// creating New account
			testData = data.get(dataValue18);
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
				Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterInsuredValuesNAHO(testData, locationCount, dwellingCount);
			createQuotePage.enterDeductiblesNAHO(testData);
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			Assertions.addInfo("Scenario 06", "Change Roof Year and Assert Roof Age Message for Normal Shingle");
			for (int i = 18; i <= 30; i++) {
				testData = data.get(i);
				if (createQuotePage.previous.checkIfElementIsPresent()
						&& createQuotePage.previous.checkIfElementIsDisplayed()) {
					createQuotePage.previous.waitTillVisibilityOfElement(60);
					createQuotePage.previous.scrollToElement();
					createQuotePage.previous.click();
				}

				// Edit dwelling and update Year built and roof cladding
				if (accountOverviewPage.editDwelling.checkIfElementIsPresent()
						&& accountOverviewPage.editDwelling.checkIfElementIsDisplayed()) {
					accountOverviewPage.editDwelling.scrollToElement();
					accountOverviewPage.editDwelling.click();
				}

				// Entering Location 1 Dwelling 1 Details
				if (!testData.get("L" + locNo + "D" + bldgNo + "-DwellingYearBuilt").equals("")) {
					dwellingPage.yearBuilt.scrollToElement();
					dwellingPage.yearBuilt.clearData();
					if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()
							&& dwellingPage.expiredQuotePopUp.checkIfElementIsDisplayed()) {
						dwellingPage.expiredQuotePopUp.waitTillPresenceOfElement(60);
						dwellingPage.expiredQuotePopUp.scrollToElement();
						dwellingPage.continueWithUpdateBtn.scrollToElement();
						dwellingPage.continueWithUpdateBtn.click();

					}
					dwellingPage.yearBuilt.setData(testData.get("L" + locNo + "D" + bldgNo + "-DwellingYearBuilt"));
					dwellingPage.yearBuilt.tab();
				}
				dwellingPage.addRoofDetails(testData, locationCount, dwellingCount);
				waitTime(5); // Control is shifting to roof details link after entering
								// dwelling values instead of clicking on review
								// dwelling
				Assertions.passTest("Dwelling Page",
						"Roof Cladding is updated to " + testData.get("L1D1-DwellingRoofCladding")
								+ " and Year Built is updated to " + testData.get("L1D1-DwellingYearBuilt"));
				dwellingPage.reviewDwelling.scrollToElement();
				dwellingPage.reviewDwelling.click();
				dwellingPage.createQuote.scrollToElement();
				dwellingPage.createQuote.click();

				// Entering Create quote page Details
				createQuotePage.scrollToBottomPage();
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();

				// Asserting HardStop message for Producer
				String yearBuilt = testData.get("L1D1-DwellingYearBuilt");
				int yearBuiltValue = Integer.parseInt(yearBuilt);
				int currentYear = Calendar.getInstance().get(Calendar.YEAR);
				int diffInYears = (currentYear - yearBuiltValue);
				Assertions.passTest("Dwelling Page", "The Year Difference  is: " + diffInYears);

				if (diffInYears < 15 || diffInYears == 15) {
					// Fetching Quote Number
					quoteLen = accountOverviewPage.quoteNumber.getData().length();
					quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);

					// Printing Quote Number
					Assertions.passTest("Account Overview Page", "The Quote Number: " + quoteNumber);

					// Clicking on edit dwelling button
					accountOverviewPage.editDwelling.waitTillVisibilityOfElement(60);
					accountOverviewPage.editDwelling.scrollToElement();
					accountOverviewPage.editDwelling.click();
				}

				else if (diffInYears >= 16 && diffInYears <= 25) {
					Assertions.verify(
							referQuotePage.roofReferralMessage.checkIfElementIsPresent()
									&& referQuotePage.roofReferralMessage.checkIfElementIsDisplayed(),
							true, "Refer Quote Page",
							referQuotePage.roofReferralMessage.getData() + " is displayed when Roof Cladding is "
									+ testData.get("L1D1-DwellingRoofCladding") + " and Year Built is "
									+ testData.get("L1D1-DwellingYearBuilt"),
							false, false);

					// Referring the Quote to USM
					referQuotePage.contactEmail.clearData();
					referQuotePage.contactEmail.setData("hiho1@icat.com");
					referQuotePage.comments.setData("Test");
					referQuotePage.referQuote.scrollToElement();
					referQuotePage.referQuote.click();

					// Fetching Quote Number
					quoteNumber = referQuotePage.quoteNumberforReferral.getData();

					// Signing out as Producer
					homePage.signOutButton.scrollToElement();
					homePage.signOutButton.click();

					// Signing in as USM
					loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));

					// Searching the referred quote
					homePage.searchQuote(quoteNumber);

					// Opening the referral
					accountOverviewPage.openReferral.scrollToElement();
					accountOverviewPage.openReferral.click();

					if (referralPage.pickUp.checkIfElementIsPresent()
							&& referralPage.pickUp.checkIfElementIsDisplayed()) {
						referralPage.pickUp.scrollToElement();
						referralPage.pickUp.click();
					}

					// Approving the referral
					referralPage.approveOrDeclineRequest.scrollToElement();
					referralPage.approveOrDeclineRequest.click();
					approveDeclineQuotePage.externalUnderwriterComments.setData("Test");
					approveDeclineQuotePage.internalUnderwriterComments.setData("Test");
					approveDeclineQuotePage.approveButton.scrollToElement();
					approveDeclineQuotePage.approveButton.click();

					// Signing out as USM
					homePage.signOutButton.scrollToElement();
					homePage.signOutButton.click();

					// Signing in as Producer
					loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));

					// Searching the Approved QUote
					homePage.searchQuoteByProducer(quoteNumber);

					// Fetching Quote Number
					quoteLen = accountOverviewPage.quoteNumber.getData().length();
					quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);

					// Printing Quote Number
					Assertions.passTest("Account Overview Page", "The Quote Number: " + quoteNumber);

					// Clicking on edit dwelling button
					accountOverviewPage.editDwelling.waitTillVisibilityOfElement(60);
					accountOverviewPage.editDwelling.scrollToElement();
					accountOverviewPage.editDwelling.click();
				}

				else {
					Assertions.verify(
							createQuotePage.globalErr.checkIfElementIsDisplayed() && createQuotePage.globalErr.getData()
									.contains("The account is ineligible due to the roof age"),
							true, "Create Quote Page",
							createQuotePage.globalErr.getData() + " is displayed when Roof Cladding is "
									+ testData.get("L1D1-DwellingRoofCladding") + " and Year Built is "
									+ testData.get("L1D1-DwellingYearBuilt"),
							false, false);

					// Clicking on Previous Button on Create Quote Page
					createQuotePage.previous.scrollToElement();
					createQuotePage.previous.click();

					// Clicking on Edit Dwelling Button on Account Overview Page
					accountOverviewPage.editDwelling.scrollToElement();
					accountOverviewPage.editDwelling.click();
				}

			}

			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Navigating to create quote page
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();

			// Entering Create quote page Details
			createQuotePage.scrollToBottomPage();
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// Fetching Quote Number
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);

			// Printing Quote Number
			Assertions.passTest("Account Overview Page", "The Quote Number: " + quoteNumber);

			// Signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Adding Ticket IO-20812(Steel or Metal Validations)
			// Loging in as Producer
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));

			// creating New account
			testData = data.get(dataValue32);
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
				Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterInsuredValuesNAHO(testData, locationCount, dwellingCount);
			createQuotePage.enterDeductiblesNAHO(testData);
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			Assertions.addInfo("Scenario 07", "Change Roof Year and Assert Roof Age Message for Steel or Metal");
			for (int i = 32; i <= 44; i++) {
				testData = data.get(i);
				if (createQuotePage.previous.checkIfElementIsPresent()
						&& createQuotePage.previous.checkIfElementIsDisplayed()) {
					createQuotePage.previous.waitTillVisibilityOfElement(60);
					createQuotePage.previous.scrollToElement();
					createQuotePage.previous.click();
				}

				// Edit dwelling and update Year built and roof cladding
				if (accountOverviewPage.editDwelling.checkIfElementIsPresent()
						&& accountOverviewPage.editDwelling.checkIfElementIsDisplayed()) {
					accountOverviewPage.editDwelling.scrollToElement();
					accountOverviewPage.editDwelling.click();
				}

				// Entering Location 1 Dwelling 1 Details
				if (!testData.get("L" + locNo + "D" + bldgNo + "-DwellingYearBuilt").equals("")) {
					dwellingPage.yearBuilt.scrollToElement();
					dwellingPage.yearBuilt.clearData();
					if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()
							&& dwellingPage.expiredQuotePopUp.checkIfElementIsDisplayed()) {
						dwellingPage.expiredQuotePopUp.waitTillPresenceOfElement(60);
						dwellingPage.expiredQuotePopUp.scrollToElement();
						dwellingPage.continueWithUpdateBtn.scrollToElement();
						dwellingPage.continueWithUpdateBtn.click();

					}
					dwellingPage.yearBuilt.setData(testData.get("L" + locNo + "D" + bldgNo + "-DwellingYearBuilt"));
					dwellingPage.yearBuilt.tab();
				}
				dwellingPage.addRoofDetails(testData, locationCount, dwellingCount);
				waitTime(5); // Control is shifting to roof details link after entering
								// dwelling values instead of clicking on review
								// dwelling
				Assertions.passTest("Dwelling Page",
						"Roof Cladding is updated to " + testData.get("L1D1-DwellingRoofCladding")
								+ " and Year Built is updated to " + testData.get("L1D1-DwellingYearBuilt"));
				dwellingPage.reviewDwelling.scrollToElement();
				dwellingPage.reviewDwelling.click();
				dwellingPage.createQuote.scrollToElement();
				dwellingPage.createQuote.click();

				// Entering Create quote page Details
				createQuotePage.scrollToBottomPage();
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();

				// Asserting HardStop message for Producer
				String yearBuilt = testData.get("L1D1-DwellingYearBuilt");
				int yearBuiltValue = Integer.parseInt(yearBuilt);
				int currentYear = Calendar.getInstance().get(Calendar.YEAR);
				int diffInYears = (currentYear - yearBuiltValue);
				Assertions.passTest("Dwelling Page", "The Year Difference  is: " + diffInYears);

				if (diffInYears < 15 || diffInYears == 15) {
					// Fetching Quote Number
					quoteLen = accountOverviewPage.quoteNumber.getData().length();
					quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);

					// Printing Quote Number
					Assertions.passTest("Account Overview Page", "The Quote Number: " + quoteNumber);

					// Clicking on edit dwelling button
					accountOverviewPage.editDwelling.waitTillVisibilityOfElement(60);
					accountOverviewPage.editDwelling.scrollToElement();
					accountOverviewPage.editDwelling.click();
				}

				else if (diffInYears >= 16 && diffInYears <= 25) {
					Assertions.verify(
							referQuotePage.roofReferralMessage.checkIfElementIsPresent()
									&& referQuotePage.roofReferralMessage.checkIfElementIsDisplayed(),
							true, "Refer Quote Page",
							referQuotePage.roofReferralMessage.getData() + " is displayed when Roof Cladding is "
									+ testData.get("L1D1-DwellingRoofCladding") + " and Year Built is "
									+ testData.get("L1D1-DwellingYearBuilt"),
							false, false);

					// Referring the Quote to USM
					referQuotePage.contactEmail.clearData();
					referQuotePage.contactEmail.setData("hiho1@icat.com");
					referQuotePage.comments.setData("Test");
					referQuotePage.referQuote.scrollToElement();
					referQuotePage.referQuote.click();

					// Fetching Quote Number
					quoteNumber = referQuotePage.quoteNumberforReferral.getData();

					// Signing out as Producer
					homePage.signOutButton.scrollToElement();
					homePage.signOutButton.click();

					// Signing in as USM
					loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));

					// Searching the referred quote
					homePage.searchQuote(quoteNumber);

					// Opening the referral
					accountOverviewPage.openReferral.scrollToElement();
					accountOverviewPage.openReferral.click();

					// Approving the referral
					referralPage.approveOrDeclineRequest.scrollToElement();
					referralPage.approveOrDeclineRequest.click();
					approveDeclineQuotePage.externalUnderwriterComments.setData("Test");
					approveDeclineQuotePage.internalUnderwriterComments.setData("Test");
					approveDeclineQuotePage.approveButton.scrollToElement();
					approveDeclineQuotePage.approveButton.click();

					// Signing out as USM
					homePage.signOutButton.scrollToElement();
					homePage.signOutButton.click();

					// Signing in as Producer
					loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));

					// Searching the Approved QUote
					homePage.searchQuoteByProducer(quoteNumber);

					// Fetching Quote Number
					quoteLen = accountOverviewPage.quoteNumber.getData().length();
					quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);

					// Printing Quote Number
					Assertions.passTest("Account Overview Page", "The Quote Number: " + quoteNumber);

					// Clicking on edit dwelling button
					accountOverviewPage.editDwelling.waitTillVisibilityOfElement(60);
					accountOverviewPage.editDwelling.scrollToElement();
					accountOverviewPage.editDwelling.click();
				}

				else {
					Assertions.verify(
							createQuotePage.globalErr.checkIfElementIsDisplayed() && createQuotePage.globalErr.getData()
									.contains("The account is ineligible due to the roof age"),
							true, "Create Quote Page",
							createQuotePage.globalErr.getData() + " is displayed when Roof Cladding is "
									+ testData.get("L1D1-DwellingRoofCladding") + " and Year Built is "
									+ testData.get("L1D1-DwellingYearBuilt"),
							false, false);

					// Clicking on Previous Button on Create Quote Page
					createQuotePage.previous.scrollToElement();
					createQuotePage.previous.click();

					// Clicking on Edit Dwelling Button on Account Overview Page
					accountOverviewPage.editDwelling.scrollToElement();
					accountOverviewPage.editDwelling.click();
				}

			}

			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Navigating to create quote page
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();

			// Entering Create quote page Details
			createQuotePage.scrollToBottomPage();
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// Fetching Quote Number
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);

			// Printing Quote Number
			Assertions.passTest("Account Overview Page", "The Quote Number: " + quoteNumber);

			// Signout as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("MATC001 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("MATC001 ", "Executed Successfully");
			}
		}
	}
}
