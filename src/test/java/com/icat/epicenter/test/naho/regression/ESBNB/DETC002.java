/** Program Description: Validating dwelling page hard stop and referral conditions, prior loss referral conditions as producer and IO-21792
 *  Author			   : Pavan Mule
 *  Date of Creation   : 12/23/2021
 **/

package com.icat.epicenter.test.naho.regression.ESBNB;

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
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class DETC002 extends AbstractNAHOTest {

	public DETC002() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBNB/DETC002.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		LoginPage loginPage = new LoginPage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();

		// Initializing Variables
		String quoteNumber;
		int quoteLen;

		Map<String, String> testData;
		int dataValue1 = 0;
		int dataValue10 = 9;
		int dataValue11 = 10;
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

			Assertions.addInfo("Scenario 01", "Change Roof Year and Assert Roof Age Message");
			for (int i = 0; i <= 9; i++) {
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
				if (createQuotePage.roofAgealertmessage.checkIfElementIsPresent()
						&& createQuotePage.roofAgealertmessage.checkIfElementIsDisplayed()) {
					Assertions.verify(createQuotePage.roofAgealertmessage.checkIfElementIsDisplayed(), true,
							"Create Quote Page",
							createQuotePage.roofAgealertmessage.getData() + " is displayed when Roof Cladding is "
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

				// Asserting referral Message for producer
				if (referQuotePage.pageName.getData().contains("Refer Quote")) {
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
					Assertions.passTest("Home Page", "Referred Quote searched successfully");

					// Opening the referral
					accountOverviewPage.openReferral.scrollToElement();
					accountOverviewPage.openReferral.click();
					if (referralPage.pickUp.checkIfElementIsPresent()
							&& referralPage.pickUp.checkIfElementIsDisplayed()) {
						referralPage.pickUp.scrollToElement();
						referralPage.pickUp.click();
					}

					// Approving the referral
					// IO-21792-As part of this our expectation is to validate if the referred quote
					// is assigned to USM not "Holder RMS"
					if (referralPage.assignedUser.getData().contentEquals("Sean Minn")) {

						Assertions.verify(referralPage.assignedUser.getData().contains("Sean Minn"), true,
								"Referral Page", "Quote is referred to USM " + referralPage.assignedUser.getData(),
								false, false);
						referralPage.approveOrDeclineRequest.scrollToElement();
						referralPage.approveOrDeclineRequest.click();
						approveDeclineQuotePage.externalUnderwriterComments.setData("Test");
						approveDeclineQuotePage.internalUnderwriterComments.setData("Test");
						approveDeclineQuotePage.approveButton.scrollToElement();
						approveDeclineQuotePage.approveButton.click();
					} else {

						Assertions.verify(referralPage.assignedUser.getData().contains("Holder RMS"), false,
								"Referral Page", "Quote is referred to USM " + referralPage.assignedUser.getData(),
								false, false);
						referralPage.approveOrDeclineRequest.scrollToElement();
						referralPage.approveOrDeclineRequest.click();
						approveDeclineQuotePage.externalUnderwriterComments.setData("Test");
						approveDeclineQuotePage.internalUnderwriterComments.setData("Test");
						approveDeclineQuotePage.approveButton.scrollToElement();
						approveDeclineQuotePage.approveButton.click();
					}
					// Ended

					// Signing out as USM
					homePage.signOutButton.scrollToElement();
					homePage.signOutButton.click();

					// Signing in as Producer
					loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));

					// Searching the Approved QUote
					homePage.searchQuoteByProducer(quoteNumber);
				}

				// Fetching Quote Number
				if (accountOverviewPage.createAnotherQuote.checkIfElementIsPresent()
						&& accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed()) {
					quoteLen = accountOverviewPage.quoteNumber.getData().length();
					quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);

					// Printing Quote Number
					Assertions.passTest("Account Overview Page", "The Quote Number: " + quoteNumber);

					// Clicking on edit dwelling button
					accountOverviewPage.editDwelling.waitTillVisibilityOfElement(60);
					accountOverviewPage.editDwelling.scrollToElement();
					accountOverviewPage.editDwelling.click();
				}

			}
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Navigating to create quote page
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();

			// Entering Create quote page Details
			createQuotePage.scrollToBottomPage();
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// Add any 3 prior loss with date 31/10/2021,01/20/2021 and 03/30/2021 and 4th
			// prior loss = liability any date
			// prior loss amount: prior loss 1(25001) prior loss 2(13000), prior loss
			// 3(15000) and prior loss 4(1)
			// Damaged repaired:prior loss 1(yes),prior loss 2(yes),prior loss 3(no) and
			// prior loss 4(yes)
			// Claim open: prior loss 1(no),prior loss 2(yes),prior loss 3(no) and prior
			// loss 4(no)
			testData = data.get(dataValue10);
			accountOverviewPage.priorLossEditLink.waitTillPresenceOfElement(60);
			accountOverviewPage.priorLossEditLink.scrollToElement();
			accountOverviewPage.priorLossEditLink.click();
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
				if (priorLossesPage.goToAccountOverviewBtn.checkIfElementIsPresent()
						&& priorLossesPage.goToAccountOverviewBtn.checkIfElementIsDisplayed()) {
					priorLossesPage.goToAccountOverviewBtn.scrollToElement();
					priorLossesPage.goToAccountOverviewBtn.click();
				}
				Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");
			}

			// Click on edit dwelling and update Year built = 2020 and roof cladding any one
			accountOverviewPage.editDwelling.waitTillVisibilityOfElement(60);
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Dwelling");

			// Updating Year built = 2020 and roof cladding any one and dwelling values
			dwellingPage.addDwellingDetails(testData, 1, 1);
			dwellingPage.addRoofDetails(testData, 1, 1);
			Assertions.passTest("Dwelling Page",
					"Roof Cladding is updated to " + testData.get("L1D1-DwellingRoofCladding")
							+ " and Year Built is updated to " + testData.get("L1D1-DwellingYearBuilt"));
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			Assertions.passTest("Dwelling page", "Updated roof cladding and year built successfully");

			// Entering dwelling values Cov A = $2,000,001,Cov B = $1,000,100 , Cov C =
			// $1,400,100, Cov D = $810,000, COV E = None and Cov F = $5000
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			Assertions.passTest("Create quote page", "Dwelling values entered successfully");
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// Asserting Hard Stop Message on Create Quote Page
			Assertions.addInfo("Scenario 02",
					"Asserting hard stop messages when added more than 3 prior loss, Cov A more than $2000000, Cov B greater than 50% of Cov A, Cov C greater than 70% of Cov A and Cov D greater than 40% of Cov A");
			Assertions.verify(
					createQuotePage.warningMessages.formatDynamicPath("unrepaired damage").checkIfElementIsDisplayed(),
					true, "Create quote page",
					"Hard Stop message 1 : "
							+ createQuotePage.warningMessages.formatDynamicPath("unrepaired damage").getData(),
					false, false);
			Assertions.verify(
					createQuotePage.warningMessages.formatDynamicPath("70% of Coverage A").checkIfElementIsDisplayed(),
					true, "Create quote page",
					"Hard Stop message 2 : "
							+ createQuotePage.warningMessages.formatDynamicPath("70% of Coverage A").getData(),
					false, false);
			Assertions.verify(
					createQuotePage.warningMessages.formatDynamicPath("40% of Coverage A").checkIfElementIsDisplayed(),
					true, "Create quote page",
					"Hard Stop message 3 : "
							+ createQuotePage.warningMessages.formatDynamicPath("40% of Coverage A").getData(),
					false, false);
			Assertions.verify(
					createQuotePage.warningMessages
							.formatDynamicPath("prior liability loss").checkIfElementIsDisplayed(),
					true, "Create quote page",
					"Hard Stop message 4 : "
							+ createQuotePage.warningMessages.formatDynamicPath("prior liability loss").getData(),
					false, false);
			Assertions.verify(
					createQuotePage.warningMessages.formatDynamicPath("Coverage A limit").checkIfElementIsDisplayed(),
					true, "Create quote page",
					"Hard Stop message 5 : "
							+ createQuotePage.warningMessages.formatDynamicPath("Coverage A limit").getData(),
					false, false);
			Assertions
					.verify(createQuotePage.warningMessages.formatDynamicPath("open claim").checkIfElementIsDisplayed(),
							true, "Create quote page",
							"Hard Stop message 6 : "
									+ createQuotePage.warningMessages.formatDynamicPath("open claim").getData(),
							false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Clicking on Locations Button
			createQuotePage.locationStep1.scrollToElement();
			createQuotePage.locationStep1.click();

			// Clicking on Edit Prior Loss
			accountOverviewPage.priorLossEditLink.waitTillPresenceOfElement(60);
			accountOverviewPage.priorLossEditLink.scrollToElement();
			accountOverviewPage.priorLossEditLink.click();
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.lossesInThreeYearsNo.scrollToElement();
				priorLossesPage.lossesInThreeYearsNo.click();
				priorLossesPage.continueButton.scrollToElement();
				priorLossesPage.continueButton.click();
				if (priorLossesPage.goToAccountOverviewBtn.checkIfElementIsPresent()
						&& priorLossesPage.goToAccountOverviewBtn.checkIfElementIsDisplayed()) {
					priorLossesPage.goToAccountOverviewBtn.scrollToElement();
					priorLossesPage.goToAccountOverviewBtn.click();
				}
				Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");
			}

			// Clicking on Edit Prior Loss
			testData = data.get(dataValue11);
			accountOverviewPage.priorLossEditLink.waitTillPresenceOfElement(60);
			accountOverviewPage.priorLossEditLink.scrollToElement();
			accountOverviewPage.priorLossEditLink.click();
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
				if (priorLossesPage.goToAccountOverviewBtn.checkIfElementIsPresent()
						&& priorLossesPage.goToAccountOverviewBtn.checkIfElementIsDisplayed()) {
					priorLossesPage.goToAccountOverviewBtn.scrollToElement();
					priorLossesPage.goToAccountOverviewBtn.click();
				}
				Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");
			}

			// Click on edit dwelling and update Year built = 2020 and roof cladding any one
			accountOverviewPage.editDwelling.waitTillVisibilityOfElement(60);
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Dwelling");

			// Updating Year built = 2020 and roof cladding any one and dwelling values
			dwellingPage.addDwellingDetails(testData, 1, 1);
			dwellingPage.addRoofDetails(testData, 1, 1);
			Assertions.passTest("Dwelling Page",
					"Roof Cladding is updated to " + testData.get("L1D1-DwellingRoofCladding")
							+ " and Year Built is updated to " + testData.get("L1D1-DwellingYearBuilt"));
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			Assertions.passTest("Dwelling page", "Updated roof cladding and year built successfully");

			// Entering dwelling values
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			Assertions.passTest("Create quote page", "Dwelling values entered successfully");
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// Asserting referral messages
			Assertions.addInfo("Scenario 03",
					"Asserting referal messages when added more than 3 prior loss, Cov B greater than 50% of Cov A");
			Assertions.verify(
					referQuotePage.quoteReferralMsgs.formatDynamicPath("Medical Payments").checkIfElementIsDisplayed(),
					true, "Refer quote page",
					"Referral message 1 : "
							+ referQuotePage.quoteReferralMsgs.formatDynamicPath("Medical Payments").getData(),
					false, false);
			Assertions.verify(
					referQuotePage.referralMessages
							.formatDynamicPath("losses in the last 3 years").checkIfElementIsDisplayed(),
					true, "Refer quote page",
					"Referral message 2 : "
							+ referQuotePage.referralMessages.formatDynamicPath("losses in the last 3 years").getData(),
					false, false);
			Assertions.verify(
					referQuotePage.referralMessages.formatDynamicPath("losses in excess of $50,000")
							.checkIfElementIsDisplayed(),
					true, "Refer quote page", "Referral message 3 : " + referQuotePage.referralMessages
							.formatDynamicPath("losses in excess of $50,000").getData(),
					false, false);
			Assertions.verify(
					referQuotePage.referralMessages.formatDynamicPath("50% of Coverage A").checkIfElementIsDisplayed(),
					true, "Refer quote page",
					"Referral message 4 : "
							+ referQuotePage.referralMessages.formatDynamicPath("50% of Coverage A").getData(),
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// ----Added ticket IO-21385-----

			// Clicking on homepage button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// logged out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			Assertions.addInfo("Scenario 04",
					"Verifying that a quote is created successfully when the Green Upgrade is selected as Yes.");

			// Signing in as USM
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));

			// creating New account
			testData = data.get(dataValue1);
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
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
			createQuotePage.getAQuote.checkIfElementIsDisplayed();
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// Checking if the quote is created when Green upgrade is selected as 'Yes'
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page",
					"Quote is created successfully when 'Green Upgrade' selected and quote number is : " + quoteNumber);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// ----IO-21385 Ended------

			// Signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("DETC002 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("DETC002 ", "Executed Successfully");
			}
		}
	}
}
