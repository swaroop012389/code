/** Program Description: Process AOR and assert the quote presence in updated agent login, renewal requotes, sltf values calculated correctly
 *  Author			   : Sowndarya NH
 *  Date of Creation   : 05/25/2021
 **/
package com.icat.epicenter.test.naho.regression.NSPNB;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BrokerOfRecordPage;
import com.icat.epicenter.pom.ConfirmBindRequestPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicyDocumentsPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RenewalIndicatorsPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewDocumentsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBNCTC003_RNL extends AbstractNAHOTest {

	public PNBNCTC003_RNL() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSPNB/NC003.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ReferralPage referralPage = new ReferralPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		BrokerOfRecordPage brokerOfRecordPage = new BrokerOfRecordPage();
		ViewOrPrintFullQuotePage viewprFullQuotePage = new ViewOrPrintFullQuotePage();
		ViewDocumentsPage viewDocumentsPage = new ViewDocumentsPage();
		ConfirmBindRequestPage confirmBindRequestPage = new ConfirmBindRequestPage();
		PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
		ViewPolicySnapShot viewPolicySnapShot = new ViewPolicySnapShot();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		RenewalIndicatorsPage renewalIndicatorsPage = new RenewalIndicatorsPage();

		// Initializing the variables
		String premiumAmount;
		String inspectionFee;
		String policyFee;
		double surplusTax;
		BigDecimal surplustaxes;
		double stampingFee;
		BigDecimal stampingTaxes;
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		DecimalFormat df = new DecimalFormat("0.00");

		String quoteNumber;
		String policyNumber;
		int dataValue1 = 0;
		int dataValue2 = 1;
		Map<String, String> testData = data.get(dataValue1);
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

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting quote number 1
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "NB Quote Number : " + quoteNumber);

			// clicking on request bind in Account overview page
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Entering details in Underwriting Questions Page
			Assertions.verify(underwritingQuestionsPage.saveButton.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered successfully");

			// Entering Bind Details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Searching the Bind Referral
			homePage.searchReferral(quoteNumber);
			Assertions.passTest("Home Page", "Searched Submitted Quote " + quoteNumber + " successfullly");

			// Approve Referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();

			// click on approve in Referral page
			Assertions.passTest("Referral Page", "Quote referral approved successfully");
			requestBindPage.approveRequestNAHO(testData);

			// Asserting policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is : " + policyNumber, false, false);

			// Go to home page and search the policy
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.passTest("Home Page", "Home Page loaded successfully");
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Searched the policy successfully");

			// Click on Renew policy
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();

			// Go to Home Page
			if (policySummaryPage.expaacMessage.checkIfElementIsPresent()
					&& policySummaryPage.expaacMessage.checkIfElementIsDisplayed()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.click();
				expaccInfoPage.enterExpaccInfo(testData, policyNumber);
				Assertions.passTest("ExpaccInfo Page", "ExpaccInfo details entered successfully");

				// Go to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();

				// Performing Renewal Searches
				homePage.searchPolicy(policyNumber);
				// clicking on renewal policy link
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();
			}
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy Link successfully");
			Assertions.verify(accountOverviewPage.viewPreviousPolicyButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// Getting Renewal Quote Number
			String renewalQuoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Renewal Quote Number 1 is " + renewalQuoteNumber);

			// Approve referral
			if (accountOverviewPage.openReferral.checkIfElementIsPresent()
					&& accountOverviewPage.openReferral.checkIfElementIsDisplayed()) {
				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();

				Assertions.verify(
						referralPage.pickUp.checkIfElementIsPresent()
								|| referralPage.approveOrDeclineRequest.checkIfElementIsPresent(),
						true, "Referral Page", "Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");
				Assertions.verify(approveDeclineQuotePage.approveButton.checkIfElementIsDisplayed(), true,
						"Approve Decline Quote Page", "Approve Decline Quote page loaded successfully", false, false);
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Approve Decline Quote Page", "Bind Request approved successfully");
				referralPage.close.scrollToElement();
				referralPage.close.click();
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuote(renewalQuoteNumber);
			}

			// click on release to producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Renewal to Producer successfully");

			Assertions.addInfo("Policy Summary Page", "Scenario For Non-Renewal Started "
					+ "initiate the renewal with relesing to producer and set the NRNL");
			// Adding the Non-Renewal indicator scenario
			// initiate the renewal with relesing to producer and set the NRNL
			accountOverviewPage.viewPreviousPolicyButton.scrollToElement();
			accountOverviewPage.viewPreviousPolicyButton.click();
			// click on renewal indicators link

			policySummaryPage.renewalIndicators.scrollToElement();
			policySummaryPage.renewalIndicators.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renewal Indicators link");

			// select non renewal checkbox
			Assertions.verify(renewalIndicatorsPage.updateButton.checkIfElementIsDisplayed(), true,
					"Renewal Indicators Page", "Renewal Indicators Page loaded successfully", false, false);
			renewalIndicatorsPage.nonRenewal.scrollToElement();
			renewalIndicatorsPage.nonRenewal.select();
			Assertions.passTest("Renewal Indicators Page", "Selected the Non Renewal Checkbox");

			// select non renewal reason and enter legal notice wording
			renewalIndicatorsPage.nonRenewalReasonArrow.scrollToElement();
			renewalIndicatorsPage.nonRenewalReasonArrow.click();
			renewalIndicatorsPage.nonRenewalReasonOption.formatDynamicPath(testData.get("NonRenewalReason"))
					.scrollToElement();
			renewalIndicatorsPage.nonRenewalReasonOption.formatDynamicPath(testData.get("NonRenewalReason")).click();
			renewalIndicatorsPage.nonRenewalLegalNoticeWording.setData(testData.get("LegalNoticeWording"));

			// click on update
			renewalIndicatorsPage.updateButton.scrollToElement();
			renewalIndicatorsPage.updateButton.click();
			Assertions.passTest("Renewal Indicators Page", "Clicked on Update Button");

			// Verifying the Renewal Indicator message on the Renewal Indicator Page
			Assertions.addInfo("Non-Renewal Indicator Page",
					"Verifying the Renewal Indicator message on the Non Renewal Indicator Page");
			String warningmessage = renewalIndicatorsPage.nonRenewalErrorMessage.getData();
			Assertions.verify(renewalIndicatorsPage.nonRenewalErrorMessage.getData().contains(warningmessage), true,
					"Non-Renewal Indicator Page",
					"The Non-Renewal warning message on renewal Indicator Page Diaplayed as: " + warningmessage, false,
					false);

			// Clicking on Cancel button
			renewalIndicatorsPage.cancelButton.scrollToElement();
			renewalIndicatorsPage.cancelButton.click();

			// Clicking on View Active Renewal policy Link on Policy Summary Page
			policySummaryPage.viewActiveRenewal.scrollToElement();
			policySummaryPage.viewActiveRenewal.click();

			Assertions.addInfo("Policy Summary Page", "Scenario For Non Renewal Ended");

			// Asserting Old producer Number
			Assertions.addInfo("Scenario 01", "Asserting Old Producer Number");
			String oldProducerNumber = accountOverviewPage.producerNumber.getData();
			Assertions.verify(accountOverviewPage.producerNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "The producer Number Before Updating New Producer is  " + oldProducerNumber
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// click on LogOut as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as USM successfully");

			// Sign in as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as Producer Successfully");

			// Search for quote
			homePage.enterPersonalLoginDetails();
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.addInfo("Scenario 02", "Asserting the Presence of Renewal Quote in Producer Login");
			homePage.searchQuoteByProducer(renewalQuoteNumber);
			Assertions.passTest("Home Page", "Searched the Renewal Quote and Clicked on Renewal Quote successfully");
			Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Adding the CR IO-19222
			// Click on Edit dwelling
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Dwelling");
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page Loaded successfully", false, false);

			Assertions.addInfo("Scenario 03",
					"Asserting the agent should not be able to edit dwelling characterisitics");
			Assertions.verify(
					dwellingPage.address.checkIfElementIsEnabled()
							&& !dwellingPage.address.getAttrributeValue("disabled").equalsIgnoreCase("disabled"),
					false, "Dwelling Page", "The Address field disabled is verified", false, false);
			Assertions.verify(
					dwellingPage.constructionTypeArrow.checkIfElementIsEnabled()
							&& !dwellingPage.constructionTypeArrow.getAttrributeValue("unselectable").contains("on"),
					false, "Dwelling Page", "Construction Type Arrow disabled is verified", false, false);
			Assertions.verify(
					dwellingPage.hardiePlankNo.checkIfElementIsEnabled()
							&& !dwellingPage.hardiePlankNo.getAttrributeValue("disabled").contains("disabled"),
					false, "Dwelling Page", "Hardiplank siding No option disabled is verified", false, false);
			Assertions.verify(
					dwellingPage.hardiePlankYes.checkIfElementIsEnabled()
							&& !dwellingPage.hardiePlankYes.getAttrributeValue("disabled").contains("disabled"),
					false, "Dwelling Page", "Hardieplank siding Yes option disabled is verified", false, false);
			Assertions.verify(
					dwellingPage.noOfUnits.checkIfElementIsEnabled()
							&& !dwellingPage.noOfUnits.getAttrributeValue("disabled").contains("disabled"),
					false, "Dwelling Page", "Number of units field disabled is verified", false, false);
			Assertions.verify(
					dwellingPage.numOfFloors.checkIfElementIsEnabled()
							&& !dwellingPage.numOfFloors.getAttrributeValue("disabled").contains("disabled"),
					false, "Dwelling Page", "Number of Stories field disabled is verified", false, false);
			Assertions.verify(
					dwellingPage.totalSquareFootage.checkIfElementIsEnabled()
							&& !dwellingPage.totalSquareFootage.getAttrributeValue("disabled").contains("disabled"),
					false, "Dwelling Page", "Total Square Footage field disabled is verified", false, false);
			Assertions.verify(
					dwellingPage.yearBuilt.checkIfElementIsEnabled()
							&& !dwellingPage.yearBuilt.getAttrributeValue("disabled").contains("disabled"),
					false, "Dwelling Page", "Year Built field disabled is verified", false, false);
			Assertions.verify(
					dwellingPage.OccupiedByArrow.checkIfElementIsEnabled()
							&& !dwellingPage.OccupiedByArrow.getAttrributeValue("unselectable").contains("on"),
					false, "Dwelling Page", "Occupied By arrow disabled is verified", false, false);
			Assertions.verify(
					dwellingPage.shortTermRentalNo.checkIfElementIsEnabled()
							&& !dwellingPage.shortTermRentalNo.getAttrributeValue("disabled").contains("disabled"),
					false, "Dwelling Page", "Shortterm rental No option disabled is verified", false, false);
			Assertions.verify(
					dwellingPage.shortTermRentalYes.checkIfElementIsEnabled()
							&& !dwellingPage.shortTermRentalYes.getAttrributeValue("disabled").contains("disabled"),
					false, "Dwelling Page", "Shortterm rental Yes option disabled is verified", false, false);
			Assertions.verify(
					dwellingPage.exoticAnimalNo.checkIfElementIsEnabled()
							&& !dwellingPage.exoticAnimalNo.getAttrributeValue("disabled").contains("disabled"),
					false, "Dwelling Page", "Exotic Animals No option disabled is verified", false, false);
			Assertions.verify(
					dwellingPage.exoticAnimalYes.checkIfElementIsEnabled()
							&& !dwellingPage.exoticAnimalYes.getAttrributeValue("disabled").contains("disabled"),
					false, "Dwelling Page", "Exotic Animals Yes option disabled is verified", false, false);
			Assertions.verify(
					dwellingPage.unfencedSwimmingPoolNo.checkIfElementIsEnabled()
							&& !dwellingPage.unfencedSwimmingPoolNo.getAttrributeValue("disabled").contains("disabled"),
					false, "Dwelling Page", "Unfenced swimming pool No option disabled is verified", false, false);
			Assertions.verify(dwellingPage.unfencedSwimmingPoolYes.checkIfElementIsEnabled()
					&& !dwellingPage.unfencedSwimmingPoolYes.getAttrributeValue("disabled").contains("disabled"), false,
					"Dwelling Page", "Unfenced swimming pool Yes option disabled is verified", false, false);
			Assertions.verify(
					dwellingPage.lapseInCoverageNo.checkIfElementIsEnabled()
							&& !dwellingPage.lapseInCoverageNo.getAttrributeValue("disabled").contains("disabled"),
					false, "Dwelling Page", "Lapse in coverage No option disabled is verified", false, false);
			Assertions.verify(
					dwellingPage.lapseInCoverageYes.checkIfElementIsEnabled()
							&& !dwellingPage.lapseInCoverageYes.getAttrributeValue("disabled").contains("disabled"),
					false, "Dwelling Page", "Lapse in coverage Yes option disabled is verified", false, false);

			// Click on Roof Details link
			dwellingPage.roofDetailsLink.waitTillElementisEnabled(60);
			dwellingPage.roofDetailsLink.waitTillVisibilityOfElement(60);
			dwellingPage.roofDetailsLink.scrollToElement();
			dwellingPage.roofDetailsLink.click();
			dwellingPage.mapZoomInButton.waitTillVisibilityOfElement(60);
			Assertions.verify(
					dwellingPage.roofShapeArrow.checkIfElementIsEnabled()
							&& !dwellingPage.roofShapeArrow.getAttrributeValue("unselectable").contains("on"),
					false, "Dwelling Page", "Roof Shape Arrow disabled is verified", false, false);
			Assertions.verify(
					dwellingPage.roofCladdingArrow.checkIfElementIsEnabled()
							&& !dwellingPage.roofCladdingArrow.getAttrributeValue("unselectable").contains("on"),
					false, "Dwelling Page", "Roof Cladding Arrow disabled is verified", false, false);
			Assertions.verify(
					dwellingPage.yearRoofLastReplaced.checkIfElementIsEnabled()
							&& !dwellingPage.yearRoofLastReplaced.getAttrributeValue("disabled").contains("disabled"),
					false, "Dwelling Page", "Year Roof Last Replaced field disabled is verified", false, false);

			// Click on Protection Discounts link
			dwellingPage.protectionDiscounts.waitTillVisibilityOfElement(60);
			dwellingPage.protectionDiscounts.scrollToElement();
			dwellingPage.protectionDiscounts.click();
			Assertions.verify(
					dwellingPage.yearPlumbingUpdated.checkIfElementIsEnabled()
							&& !dwellingPage.yearPlumbingUpdated.getAttrributeValue("disabled").contains("disabled"),
					false, "Dwelling Page", "Year Plumbing Updated field disabled is verified", false, false);
			Assertions.verify(
					dwellingPage.yearElectricalUpdated.checkIfElementIsEnabled()
							&& !dwellingPage.yearElectricalUpdated.getAttrributeValue("disabled").contains("disabled"),
					false, "Dwelling Page", "Year Electrical Updated field disabled is verified", false, false);
			Assertions.verify(
					dwellingPage.yearHVACUpdated.checkIfElementIsEnabled()
							&& !dwellingPage.yearHVACUpdated.getAttrributeValue("disabled").contains("disabled"),
					false, "Dwelling Page", "Year HAVC Updated field disabled is verified", false, false);
			Assertions.verify(
					dwellingPage.windMitigationArrow.checkIfElementIsEnabled()
							&& !dwellingPage.windMitigationArrow.getAttrributeValue("unselectable").contains("on"),
					false, "Dwelling Page", "Wind Mitigation Arrow disabled is verified", false, false);
			Assertions.verify(
					dwellingPage.centralStationAlarmArrow.checkIfElementIsEnabled()
							&& !dwellingPage.centralStationAlarmArrow.getAttrributeValue("unselectable").contains("on"),
					false, "Dwelling Page", "Central Station Alarm Arrow disabled is verified", false, false);
			Assertions.verify(
					dwellingPage.wholeSprinklerNo.checkIfElementIsEnabled()
							&& !dwellingPage.wholeSprinklerNo.getAttrributeValue("disabled").contains("disabled"),
					false, "Dwelling Page", "Whole Home Sprinkler No Option disabled is verified", false, false);
			Assertions.verify(
					dwellingPage.wholeSprinklerYes.checkIfElementIsEnabled()
							&& !dwellingPage.wholeSprinklerYes.getAttrributeValue("disabled").contains("disabled"),
					false, "Dwelling Page", "Whole Home Sprinkler Yes Option disabled is verified", false, false);
			Assertions.verify(
					dwellingPage.waterDetectionNo.checkIfElementIsEnabled()
							&& !dwellingPage.waterDetectionNo.getAttrributeValue("disabled").contains("disabled"),
					false, "Dwelling Page", "Water Detection No Option disabled is verified", false, false);
			Assertions.verify(
					dwellingPage.waterDetectionYes.checkIfElementIsEnabled()
							&& !dwellingPage.waterDetectionYes.getAttrributeValue("disabled").contains("disabled"),
					false, "Dwelling Page", " Water Detection Yes Option disabled is verified", false, false);
			Assertions.verify(
					dwellingPage.gatedCommunityNo.checkIfElementIsEnabled()
							&& !dwellingPage.gatedCommunityNo.getAttrributeValue("disabled").contains("disabled"),
					false, "Dwelling Page", "Gated Community No option disabled is verified", false, false);
			Assertions.verify(
					dwellingPage.gatedCommunityYes.checkIfElementIsEnabled()
							&& !dwellingPage.gatedCommunityYes.getAttrributeValue("disabled").contains("disabled"),
					false, "Dwelling Page", "Gated Community Yes option disabled is verified", false, false);
			Assertions.verify(
					dwellingPage.newPurchaseNo.checkIfElementIsEnabled()
							&& !dwellingPage.newPurchaseNo.getAttrributeValue("disabled").contains("disabled"),
					false, "Dwelling Page", "New Purchase No option disabled is verified", false, false);
			Assertions.verify(
					dwellingPage.newPurchaseYes.checkIfElementIsEnabled()
							&& !dwellingPage.newPurchaseYes.getAttrributeValue("disabled").contains("disabled"),
					false, "Dwelling Page", "New Purchase Yes option disabled is verified", false, false);
			Assertions.verify(
					dwellingPage.yearPlumbingUpdated.checkIfElementIsEnabled()
							&& !dwellingPage.yearPlumbingUpdated.getAttrributeValue("disabled").contains("disabled"),
					false, "Dwelling Page", "Year Plumbing Updated field disabled is verified", false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// click on LogOut as Producer
			homePage.refreshPage();
			homePage.scrollToTopPage();
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Producer successfully");

			// Sign in as USM
			loginPage.waitTime(2);
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");

			// Go to home page and search the policy
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.passTest("Home Page", "Home Page loaded successfully");
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Searched the policy successfully");
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page Loaded successfully", false, false);
			Assertions.addInfo("Scenario 04", "Asserting the Presence of View Active renewal link");
			Assertions.verify(policySummaryPage.viewActiveRenewal.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "View Active Renewal Link Present is verified", false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Click on View active renewal link
			policySummaryPage.viewActiveRenewal.scrollToElement();
			policySummaryPage.viewActiveRenewal.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Active Renewal Link successfully");
			Assertions.verify(accountOverviewPage.viewPreviousPolicyButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);

			// Click on edit producer
			accountOverviewPage.producerLink.scrollToElement();
			accountOverviewPage.producerLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Producer Number successfully");
			Assertions.verify(brokerOfRecordPage.processBOR.checkIfElementIsDisplayed(), true, "Broker of Record Page",
					"Broker of Record Page loaded successfully", false, false);
			brokerOfRecordPage.newProducerNumber.setData(testData.get("NewProducerNumber"));
			Assertions.passTest("Broker of Record Page",
					"The New Producer Number : " + testData.get("NewProducerNumber") + " updated successfully ");
			brokerOfRecordPage.borStatusArrow.click();
			brokerOfRecordPage.borStatusOption.formatDynamicPath(testData.get("BORStatus")).click();
			Assertions.passTest("Broker of Record Page",
					"The BOR status " + testData.get("BORStatus") + " selected successfully");

			// Click on Process Bor Button
			brokerOfRecordPage.processBOR.scrollToElement();
			brokerOfRecordPage.processBOR.click();
			Assertions.passTest("Broker of Record Page", "Clicked on Process BOR");

			// Click on Close
			brokerOfRecordPage.closeBORPage.scrollToElement();
			brokerOfRecordPage.closeBORPage.click();

			// Asserting New producer Number
			Assertions.addInfo("Scenario 05", "Asserting New Producer Number");
			String newProducerNumber = accountOverviewPage.producerNumber.getData();
			Assertions.verify(accountOverviewPage.producerNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "The producer Number After Updating New Producer is  " + newProducerNumber
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// click on LogOut as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as USM successfully");

			// Sign in as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as Producer Successfully");

			// Search for quote
			homePage.enterPersonalLoginDetails();
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// verify the absence of renewal quote in producer login
			homePage.waitTime(2);
			homePage.producerQuoteSearchButton.waitTillVisibilityOfElement(60);
			homePage.producerQuoteSearchButton.scrollToElement();
			homePage.producerQuoteSearchButton.click();
			homePage.producerQuoteNumberSearchTextbox.waitTillVisibilityOfElement(60);
			homePage.producerQuoteNumberSearchTextbox.scrollToElement();
			homePage.producerQuoteNumberSearchTextbox.setData(renewalQuoteNumber);
			homePage.producerQuoteFindButton.waitTillVisibilityOfElement(60);
			homePage.producerQuoteFindButton.scrollToElement();
			homePage.producerQuoteFindButton.click();
			Assertions.addInfo("Scenario 06", "Asserting the absence of renewal quote in 11250.1 producer login");
			Assertions.verify(
					homePage.producerQuoteNumberLink.formatDynamicPath(renewalQuoteNumber).checkIfElementIsPresent(),
					false, "Home Page", "The Renewal Quote Not Present is verified", false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// click on LogOut as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Producer 11250.1 successfully");

			// Sign in as Producer-11252.1
			loginPage.refreshPage();
			loginPage.enterLoginDetails("eqho1@test.com", setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as Producer 11252.1 Successfully");

			// Search the Renewal Quote
			homePage.enterPersonalLoginDetails();
			homePage.searchQuoteByProducer(renewalQuoteNumber);
			Assertions.passTest("Home Page", "Searched the Renewal Quote and Clicked on Renewal Quote successfully");
			Assertions.verify(accountOverviewPage.viewPreviousPolicyButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);

			// Asserting SLTF value on account overview page
			Assertions.addInfo("Scenario 07", "Calculating SLTF Values on Account Overview Page");
			String premiumOnAccountOverviewPage = accountOverviewPage.premiumValue.getData();
			String feesOnAccountOverviewPage = accountOverviewPage.feesValue.getData();
			String sltfValueAccountOverviewPage = accountOverviewPage.sltfValue.getData().replace(",", "");
			String surplusContributionValueAccountOverviewPage = accountOverviewPage.surplusContibutionValue.getData()
					.replace("$", "").replace(",", "").replace("%", "");

			double d_premiumValueOnAccountOverviewPage = Double
					.parseDouble(premiumOnAccountOverviewPage.replace("$", "").replace(",", ""));
			double d_FeeValueOnAccountOverviewPage = Double
					.parseDouble(feesOnAccountOverviewPage.replace("$", "").replace(",", ""));
			double d_surplusContributionValueAccountOverviewPage = Double
					.parseDouble(surplusContributionValueAccountOverviewPage);
			double d_sltfPercentageValue = Double.parseDouble(testData.get("SLTFPercentage"));
			double d_stampingFeePercentageValue = Double.parseDouble(testData.get("StampingFeePercentage"));
			double d_sltValue = (d_premiumValueOnAccountOverviewPage + d_FeeValueOnAccountOverviewPage
					+ d_surplusContributionValueAccountOverviewPage) * d_sltfPercentageValue;
			double d_stampingFeeValue = (d_premiumValueOnAccountOverviewPage + d_FeeValueOnAccountOverviewPage
					+ d_surplusContributionValueAccountOverviewPage) * d_stampingFeePercentageValue;
			double d_sltfValueOnAccountOverviewPage = d_sltValue + d_stampingFeeValue;
			Assertions.verify(sltfValueAccountOverviewPage, "$" + df.format(d_sltfValueOnAccountOverviewPage),
					"Account Overview Page",
					"Actual and Calculated SLTF Values are matching and calculated according to 5% NC SLTF value "
							+ sltfValueAccountOverviewPage,
					false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Click on Altf quote option
			accountOverviewPage.quoteOptions1TotalPremium.scrollToElement();
			accountOverviewPage.quoteOptions1TotalPremium.click();
			Assertions.passTest("Account Overview Page", "Clicked on ALT Quote Available");

			// Getting Alt Quote Number
			String altQuoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Alt Quote Number is  " + altQuoteNumber);

			// Assert the quote status and calculation of sltf
			Assertions.addInfo("Scenario 08",
					"Asserting the Quote Status and Calculating SLTF Values in Account Overview Page");

			Assertions.verify(accountOverviewPage.renewalStatus.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "The Alt Quote Status is  " + accountOverviewPage.renewalStatus.getData(),
					false, false);

			String premiumOnAccountOverviewPageAlt1 = accountOverviewPage.premiumValue.getData();
			String feesOnAccountOverviewPageAlt1 = accountOverviewPage.feesValue.getData();
			String sltfValueAccountOverviewPageAlt1 = accountOverviewPage.sltfValue.getData().replace("$", "")
					.replace(",", "");
			String surplusContributionValuesAlt1 = accountOverviewPage.surplusContibutionValue.getData()
					.replace("$", "").replace(",", "").replace("%", "");

			double d_premiumValueOnAccountOverviewPageAlt1 = Double
					.parseDouble(premiumOnAccountOverviewPageAlt1.replace("$", "").replace(",", ""));
			double d_FeeValueOnAccountOverviewPageAlt1 = Double
					.parseDouble(feesOnAccountOverviewPageAlt1.replace("$", "").replace(",", ""));
			double d_surplusContributionValuesAlt1 = Double.parseDouble(surplusContributionValuesAlt1);
			double d_sltfPercentageValueAlt1 = Double.parseDouble(testData.get("SLTFPercentage"));
			double d_stampingFeePercentageValueAlt1 = Double.parseDouble(testData.get("StampingFeePercentage"));
			double d_sltValueAlt1 = (d_premiumValueOnAccountOverviewPageAlt1 + d_FeeValueOnAccountOverviewPageAlt1
					+ d_surplusContributionValuesAlt1) * d_sltfPercentageValueAlt1;
			double d_stampingFeeValueAlt1 = (d_premiumValueOnAccountOverviewPageAlt1
					+ d_FeeValueOnAccountOverviewPageAlt1 + d_surplusContributionValuesAlt1)
					* d_stampingFeePercentageValueAlt1;
			double d_sltfValueOnAccountOverviewPageAlt1 = d_sltValueAlt1 + d_stampingFeeValueAlt1;

			double expected_sltfValueOnAccountOverviewPageAlt1 = Double
					.parseDouble(df.format(d_sltfValueOnAccountOverviewPageAlt1));

			// if condition added for comparing actual and calculated SLTF according to 5%
			// NC SLTF value

			if (Math.abs(Precision.round(Double.parseDouble(sltfValueAccountOverviewPageAlt1), 2))
					- (expected_sltfValueOnAccountOverviewPageAlt1) < 0.05) {
				Assertions.passTest("Endorse Policy Page",
						"Actual SLTF according to 5% NC SLTF value : " + "$" + sltfValueAccountOverviewPageAlt1);
				Assertions.passTest("Endorse Policy Page", "The Calculated SLTF according to 5% NC SLTF value : " + "$"
						+ expected_sltfValueOnAccountOverviewPageAlt1);
			} else {
				Assertions.verify(sltfValueAccountOverviewPageAlt1, expected_sltfValueOnAccountOverviewPageAlt1,
						"Endorse Policy Page", "The Difference between actual and calculated SLTF is more than 0.05",
						false, false);
			}
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// Click on Edit Deductibles
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Deductibles and Limits");

			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page Loaded successfully", false, false);

			// change the form type from HO3 TO HO5
			testData = data.get(dataValue2);
			if (createQuotePage.formType_HO5.checkIfElementIsPresent()
					&& testData.get("FormType").equalsIgnoreCase("HO5")) {
				createQuotePage.formType_HO5.scrollToElement();
				createQuotePage.formType_HO5.click();
			}

			createQuotePage.getAQuote.waitTillVisibilityOfElement(60);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// Below if condition code adding because in uat2 inflation guard percent adding
			// to Cov A so getting hard top message "The quoted building has a Coverage A
			// limit of more than $2000000 and is ineligible. Please review the underwriting
			// guidelines for limit availability."
			// Uat1 not comming
			if (createQuotePage.globalErr.checkIfElementIsPresent()
					&& createQuotePage.globalErr.checkIfElementIsDisplayed()) {
				testData = data.get(dataValue1);
				createQuotePage.coverageADwelling.scrollToElement();
				createQuotePage.coverageADwelling.clearData();
				createQuotePage.coverageADwelling.appendData(testData.get("L1D1-DwellingCovA"));
				createQuotePage.coverageADwelling.tab();
				createQuotePage.getAQuote.waitTillVisibilityOfElement(60);
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();
			}

			// Adding below code for qa2 its renewal quote is referring as producer
			// login
			testData = data.get(dataValue1);
			if (referQuotePage.referQuote.checkIfElementIsPresent()
					&& referQuotePage.referQuote.checkIfElementIsDisplayed()) {
				referQuotePage.contactName.setData(testData.get("ProducerName"));
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.scrollToElement();
				referQuotePage.referQuote.click();
				quoteNumber = referQuotePage.quoteNumberforReferral.getData();
				Assertions.passTest("Refequote Page", "Renewal Requote number " + quoteNumber);

			}

			// Searching alt quote
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuoteByProducer(altQuoteNumber);
			testData = data.get(dataValue2);
			Assertions.verify(accountOverviewPage.viewPreviousPolicyButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);

			// Asserting the Quote Status and Calculation of SLTF
			Assertions.addInfo("Scenario 09", "Asserting the Quote Status and Calculation of SLTF");
			Assertions.verify(accountOverviewPage.renewalStatus.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "The Quote Status is  " + accountOverviewPage.renewalStatus.getData(),
					false, false);
			testData = data.get(dataValue1);
			String newQuotepremiumOnAccountOverviewPage = accountOverviewPage.premiumValue.getData();
			String newQuotefeesOnAccountOverviewPage = accountOverviewPage.feesValue.getData();
			String newQuotesltfValueAccountOverviewPage = accountOverviewPage.sltfValue.getData().replace("$", "")
					.replace(",", "");
			String newQuoteSurplusContributionValues = accountOverviewPage.surplusContibutionValue.getData()
					.replace("$", "").replace(",", "").replace("%", "");

			double d_newQuotepremiumValueOnAccountOverviewPage = Double
					.parseDouble(newQuotepremiumOnAccountOverviewPage.replace("$", "").replace(",", ""));
			double d_newQuoteFeeValueOnAccountOverviewPage = Double
					.parseDouble(newQuotefeesOnAccountOverviewPage.replace("$", "").replace(",", ""));
			double d_newQuoteSurplusContributionValues = Double.parseDouble(newQuoteSurplusContributionValues);
			double d_newQuotesltfPercentageValue = Double.parseDouble(testData.get("SLTFPercentage"));
			double d_newQuotestampingFeePercentageValue = Double.parseDouble(testData.get("StampingFeePercentage"));
			double d_newQuotesltValue = (d_newQuotepremiumValueOnAccountOverviewPage
					+ d_newQuoteFeeValueOnAccountOverviewPage + d_newQuoteSurplusContributionValues)
					* d_newQuotesltfPercentageValue;
			double d_newQuotestampingFeeValue = (d_newQuotepremiumValueOnAccountOverviewPage
					+ d_newQuoteFeeValueOnAccountOverviewPage + d_newQuoteSurplusContributionValues)
					* d_newQuotestampingFeePercentageValue;
			double d_newQuotesltfValueOnAccountOverviewPage = d_newQuotesltValue + d_newQuotestampingFeeValue;

			double expected_newQuotesltfValueOnAccountOverviewPage = Double
					.parseDouble(df.format(d_newQuotesltfValueOnAccountOverviewPage));

			// if condition added for comparing actual and calculated SLTF according to 5%
			// NC SLTF value

			if (Math.abs(Precision.round(Double.parseDouble(newQuotesltfValueAccountOverviewPage), 2))
					- (expected_newQuotesltfValueOnAccountOverviewPage) < 0.05) {
				Assertions.passTest("Endorse Policy Page",
						"Actual SLTF according to 5% NC SLTF value : " + "$" + newQuotesltfValueAccountOverviewPage);
				Assertions.passTest("Endorse Policy Page", "The Calculated SLTF according to 5% NC SLTF value  : " + "$"
						+ expected_newQuotesltfValueOnAccountOverviewPage);
			} else {
				Assertions.verify(newQuotesltfValueAccountOverviewPage, expected_newQuotesltfValueOnAccountOverviewPage,
						"Endorse Policy Page",
						"The difference between Actual and Calculated SLTF Values are not matching and is more than 0.05 "
								+ newQuotesltfValueAccountOverviewPage,
						false, false);
			}
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			// click on LogOut as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Producer successfully");

			// Sign in as Producer-11252.1
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");

			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Searched the policy successfully");

			// Click on View Active Renewal
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page Loaded successfully", false, false);
			policySummaryPage.viewActiveRenewal.scrollToElement();
			policySummaryPage.viewActiveRenewal.click();

			// Click on edit dwelling link
			Assertions.verify(accountOverviewPage.viewPreviousPolicyButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);

			// Approve referral
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			if (accountOverviewPage.openReferral.checkIfElementIsPresent()
					&& accountOverviewPage.openReferral.checkIfElementIsDisplayed()) {
				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();

				Assertions.verify(
						referralPage.pickUp.checkIfElementIsPresent()
								|| referralPage.approveOrDeclineRequest.checkIfElementIsPresent(),
						true, "Referral Page", "Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");
				Assertions.verify(approveDeclineQuotePage.approveButton.checkIfElementIsDisplayed(), true,
						"Approve Decline Quote Page", "Approve Decline Quote page loaded successfully", false, false);
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Approve Decline Quote Page", "Bind Request approved successfully");
				referralPage.close.scrollToElement();
				referralPage.close.click();
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuote(renewalQuoteNumber);
			}
			accountOverviewPage.waitTime(5);
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Dwelling");

			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page Loaded successfully", false, false);

			testData = data.get(dataValue2);
			dwellingPage.addDwellingDetails(testData, 1, 1);

			dwellingPage.reviewDwelling();

			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			Assertions.passTest("Dwelling Page", "Clicked on Create Quote button");

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Getting New Quote Number
			String newQuoteNumber1 = accountOverviewPage.quoteNumber.getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Renewal Quote Number 4 is : " + newQuoteNumber1);

			Assertions.addInfo("Scenario 10",
					"Asserting the Status of the Quote,Presence of Issue quote button and calculation of SLTF");

			Assertions.verify(accountOverviewPage.renewalStatus.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "The Quote Status is  " + accountOverviewPage.renewalStatus.getData(),
					false, false);

			Assertions.verify(accountOverviewPage.issueQuoteButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Issue Quote Button Present is verified", false, false);
			testData = data.get(dataValue1);
			String newQuote1premiumOnAccountOverviewPage = accountOverviewPage.premiumValue.getData();
			String newQuote1feesOnAccountOverviewPage = accountOverviewPage.feesValue.getData();
			String newQuote1sltfValueAccountOverviewPage = accountOverviewPage.sltfValue.getData().replace(",", "")
					.replace("$", "");
			String newQuoteSurplusContributionValueAccountOverviewPage = accountOverviewPage.surplusContibutionValue
					.getData().replace("$", "").replace(",", "").replace("%", "");

			double d_newQuote1sltfValueAccountOverviewPage = Double.parseDouble(newQuote1sltfValueAccountOverviewPage);
			double d_newQuote1premiumValueOnAccountOverviewPage = Double
					.parseDouble(newQuote1premiumOnAccountOverviewPage.replace("$", "").replace(",", ""));
			double d_newQuote1FeeValueOnAccountOverviewPage = Double
					.parseDouble(newQuote1feesOnAccountOverviewPage.replace("$", "").replace(",", ""));
			double d_newQuoteSurplusContributionValueAccountOverviewPage = Double
					.parseDouble(newQuoteSurplusContributionValueAccountOverviewPage);
			double d_newQuote1sltfPercentageValue = Double.parseDouble(testData.get("SLTFPercentage"));
			double d_newQuote1stampingFeePercentageValue = Double.parseDouble(testData.get("StampingFeePercentage"));
			double d_newQuote1sltValue = (d_newQuote1premiumValueOnAccountOverviewPage
					+ d_newQuote1FeeValueOnAccountOverviewPage + d_newQuoteSurplusContributionValueAccountOverviewPage)
					* d_newQuote1sltfPercentageValue;
			double d_newQuote1stampingFeeValue = (d_newQuote1premiumValueOnAccountOverviewPage
					+ d_newQuote1FeeValueOnAccountOverviewPage + d_newQuoteSurplusContributionValueAccountOverviewPage)
					* d_newQuote1stampingFeePercentageValue;
			double d_newQuote1sltfValueOnAccountOverviewPage = d_newQuote1sltValue + d_newQuote1stampingFeeValue;

			if (Precision.round(Math.abs(Precision.round(d_newQuote1sltfValueAccountOverviewPage, 2)
					- Precision.round(d_newQuote1sltfValueOnAccountOverviewPage, 2)), 2) < 0.05) {
				Assertions.passTest("View print full quote page", "Calculated surplus lines taxes and fees : " + "$"
						+ Precision.round(d_newQuote1sltfValueOnAccountOverviewPage, 2));
				Assertions.passTest("View print full quote page",
						"Actual surplus lines taxes and fees : " + "$" + d_newQuote1sltfValueAccountOverviewPage);
			} else {
				Assertions.verify(d_newQuote1sltfValueAccountOverviewPage, d_newQuote1sltfValueOnAccountOverviewPage,
						"Endorse Policy Page",
						"The Difference between actual SLTF value and calculated SLTF value is more than 0.05", false,
						false);
			}
			Assertions.addInfo("Scenario 10", "Scenario 10 Ended");

			// Click on Issue Quote Button
			accountOverviewPage.issueQuoteButton.scrollToElement();
			accountOverviewPage.issueQuoteButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Issue Quote Button");

			// Click on view print full quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View print Full Quote");

			// Need to assert the Updated address
			Assertions.verify(viewprFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page", "View/Print Full Quote Page Loaded successfully", false, false);
			Assertions.addInfo("Scenario 11", "Asserting the Presence of Updated Address");
			testData = data.get(dataValue2);
			Assertions.verify(
					viewprFullQuotePage.viewPrintFullQuoteDetails
							.formatDynamicPath(testData.get("L1D1-DwellingAddress")).checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The Updated Address "
							+ viewprFullQuotePage.viewPrintFullQuoteDetails
									.formatDynamicPath(testData.get("L1D1-DwellingAddress")).getData()
							+ " displayed is verified",
					false, false);
			// Click on back button
			viewprFullQuotePage.backButton.click();
			Assertions.addInfo("Scenario 11", "Scenario 11 Ended");

			// Click View Renewal Documents
			accountOverviewPage.viewRenewalDocuments.scrollToElement();
			accountOverviewPage.viewRenewalDocuments.click();
			viewDocumentsPage.backBtn.scrollToElement();
			viewDocumentsPage.backBtn.click();
			accountOverviewPage.viewRenewalDocuments.scrollToElement();
			accountOverviewPage.viewRenewalDocuments.click();
			Assertions.passTest("Account Overview Page", "Clicked On View Renewal Documents");

			viewDocumentsPage.refreshPage();
			Assertions.addInfo("Scenario 12", "Asserting the Presence of Documents");
			Assertions.verify(viewDocumentsPage.documents.formatDynamicPath("_P.").getNoOfWebElements(), 3,
					"View Documents Page",
					viewDocumentsPage.documents.formatDynamicPath("_P.").getData() + " is displayed", false, false);
			viewDocumentsPage.refreshPage();
			Assertions.verify(viewDocumentsPage.documents.formatDynamicPath("_I.").getNoOfWebElements(), 2,
					"View Documents Page",
					viewDocumentsPage.documents.formatDynamicPath("_I.").getData() + " is displayed", false, false);
			Assertions.addInfo("Scenario 12", "Scenario 12 Ended");

			viewDocumentsPage.backBtn.scrollToElement();
			viewDocumentsPage.backBtn.click();
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(newQuoteNumber1);
			// newQuoteNumber1
			accountOverviewPage.clickOnRenewalRequestBind(testData, newQuoteNumber1);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Entering details in Underwriting Questions Page
			testData = data.get(dataValue1);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.floodCoverageArrow.scrollToElement();
			requestBindPage.floodCoverageArrow.click();
			requestBindPage.floodCoverageOption.formatDynamicPath(testData.get("ApplicantHaveFloodPolicy"))
					.scrollToElement();
			requestBindPage.floodCoverageOption.formatDynamicPath(testData.get("ApplicantHaveFloodPolicy")).click();
			Assertions.passTest("Request Bind Page", "Selected the Flood as No");

			requestBindPage.addContactInformation(testData);

			if (requestBindPage.submit.checkIfElementIsPresent()
					&& requestBindPage.submit.checkIfElementIsDisplayed()) {
				requestBindPage.submit.scrollToElement();
				requestBindPage.submit.click();
			}

			if (confirmBindRequestPage.requestBind.checkIfElementIsPresent()) {
				Assertions.passTest("Confirm Bind Request Page", "Confirm Bind Request page loaded successfully");
				confirmBindRequestPage.confirmBind();
				Assertions.passTest("Confirm Bind Request Page", "Clicked on Request Bind");
			}

			if (requestBindPage.overrideEffectiveDate.checkIfElementIsPresent()
					&& requestBindPage.overrideEffectiveDate.checkIfElementIsDisplayed()) {
				requestBindPage.overrideEffectiveDate.scrollToElement();
				requestBindPage.overrideEffectiveDate.select();
				requestBindPage.waitTime(2);
			}
			if (requestBindPage.submit.checkIfElementIsPresent()
					&& requestBindPage.submit.checkIfElementIsDisplayed()) {
				requestBindPage.scrollToBottomPage();
				requestBindPage.submit.scrollToElement();
				requestBindPage.submit.click();
			}
			if (confirmBindRequestPage.requestBind.checkIfElementIsPresent()) {
				confirmBindRequestPage.confirmBind();
			}

			// Asserting policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. The Renewal PolicyNumber is : " + policyNumber, false,
					false);

			// Click on view documents link
			policySummaryPage.viewDocuments.scrollToElement();
			policySummaryPage.viewDocuments.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Documents link");

			policyDocumentsPage.refreshPage();
			Assertions.addInfo("Scenario 13", "Asserting the Presence of Documents");
			Assertions.verify(policyDocumentsPage.policyDocuments.formatDynamicPath("_P.").getNoOfWebElements(), 3,
					"Policy Documents Page",
					policyDocumentsPage.policyDocuments.formatDynamicPath("_P.").getData() + " is displayed", false,
					false);
			Assertions.verify(policyDocumentsPage.policyDocuments.formatDynamicPath("_I.").getNoOfWebElements(), 3,
					"Policy Documents Page",
					policyDocumentsPage.policyDocuments.formatDynamicPath("_I.").getData() + " is displayed", false,
					false);
			Assertions.addInfo("Scenario 13", "Scenario 13 Ended");

			policyDocumentsPage.backButton.scrollToElement();
			policyDocumentsPage.backButton.click();

			premiumAmount = policySummaryPage.transactionPremium.getData().replaceAll("[^\\d-.]", "");
			inspectionFee = policySummaryPage.inspectionFee.getData().replaceAll("[^\\d-.]", "");
			policyFee = policySummaryPage.policyFee.getData().replaceAll("[^\\d-.]", "");
			String surplusContributionValue = policySummaryPage.transactionSurplusContribution.getData()
					.replaceAll("[^\\d-.]", "").replace("%", "");

			// calculating surplus tax
			surplusTax = (Double.parseDouble(premiumAmount) + Double.parseDouble(inspectionFee)
					+ Double.parseDouble(policyFee) + Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("SLTFPercentage"));

			// Rounding sltf decimal value to 2 digits
			surplustaxes = BigDecimal.valueOf(surplusTax);
			surplustaxes = surplustaxes.setScale(2, RoundingMode.HALF_UP);

			// Calculating sltf with stamping fees
			stampingFee = (Double.parseDouble(premiumAmount) + Double.parseDouble(inspectionFee)
					+ Double.parseDouble(policyFee) + Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("StampingFeePercentage"));

			// Rounding sltf decimal value to 2 digits
			stampingTaxes = BigDecimal.valueOf(stampingFee);
			stampingTaxes = stampingTaxes.setScale(2, RoundingMode.HALF_UP);

			// Click on Policy snapshot link
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Policy Snapshot link");
			Assertions.verify(viewPolicySnapShot.surplusLinesTaxesValue.checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page", "View Policy Snapshot Page loaded successfully", false, false);

			// Comparing actual and expected SLTF value and printing actual and
			// expected
			// value
			Assertions.addInfo("Scenario 14", "Calculation of SLTF");
			String surplusLinesTaxesValue = viewPolicySnapShot.surplusLinesTaxesValue.getData().replace("$", "");
			double surplusLinesTaxesValue1 =Double.parseDouble(surplusLinesTaxesValue);

			if (Precision.round(Math.abs(Precision.round(surplusLinesTaxesValue1, 2) - Precision.round(surplusTax, 2)),
					2) < 0.05) {
				Assertions.passTest("View Policy Snapshot Page",
						"Calculated Surplus Fees : " + "$" + Precision.round(surplusTax, 2));
				Assertions.passTest("View Policy Snapshot Page", "Actual Surplus Fees : " + "$" + surplusLinesTaxesValue1);
				Assertions.passTest("View Policy Snapshot Page","Stamping Fees calculated as per Stamping Fees Percentage 0.004 for NC is verified");
			} else {
				Assertions.verify(surplusLinesTaxesValue1, surplusTax, "View Policy Snapshot Page",
						"The Difference between actual Surplus Fees and calculated Surplus Fees is more than 0.05", false, false);
			}

			// Comparing actual and expected Stamping fee value and printing actual
			// and
			// expected value
			Assertions.verify(viewPolicySnapShot.stampingFeeValue.getData(),
					format.format(stampingTaxes).replace(",", ""), "View Policy Snapshot Page",
					"Calculated Stamping Fees : " + format.format(stampingTaxes).replace(",", ""), false, false);
			Assertions.verify(viewPolicySnapShot.stampingFeeValue.getData(),
					format.format(stampingTaxes).replace(",", ""), "View Policy Snapshot Page",
					"Actual Stamping Fees : " + viewPolicySnapShot.stampingFeeValue.getData(), false, false);
			Assertions.verify(viewPolicySnapShot.stampingFeeValue.getData(),
					format.format(stampingTaxes).replace(",", ""), "View Policy Snapshot Page",
					"Stamping Fees calculated as per Stamping Fees Percentage 0.004 for NC is verified", false, false);
			Assertions.addInfo("Scenario 14", "Scenario 14 Ended");

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBNCTC003 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBNCTC003 ", "Executed Successfully");
			}
		}
	}

}
