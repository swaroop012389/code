/**Program Description: Performing various validation on NAHO product as USM and IO-21801
 * Author: Pavan Mule
 * Date of Creation: 16/09/2021
 */
package com.icat.epicenter.test.naho.regression.NSPNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExistingAccountPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBNCTC004_GEN extends AbstractNAHOTest {

	public PNBNCTC004_GEN() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSPNB/NC004.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
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
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		ExistingAccountPage existingAccountPage = new ExistingAccountPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();

		// Initializing local variables
		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
		Map<String, String> testData = data.get(dataValue1);
		String quoteNumber;
		int quoteLength;
		String policyNumber;
		String renewalQuoteNumber;
		String covAvalue;
		String inflationGuardPercentage;
		double calCovAValue;
		String actualCovAValue;
		String calCovAValue_s;
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, " Eligibility page ",
					"Eligibility page loaded successfully ", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility page  ", " Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling page",
					"Dwelling page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page ", "Dwelling details entered successfully");

			// Asserting warning messages when Number of Stories = 0, Number of Units = 0,
			// Total Square footage = 0, Year built = 2022, Year plumbing = 2022, Year HVAC
			// = 2022, Year Electrical = 2022
			Assertions.addInfo("Scenario 01",
					"Asserting warning message when Number of Stories = 0, Number of Units = 0, Total Square footage = 0, Year built = 2022, Year plumbing = 2022, Year HVAC = 2022, Year Electrical = 2022");
			Assertions.verify(dwellingPage.protectionClassWarMsg.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Warning message is " + dwellingPage.protectionClassWarMsg.getData() + " is verified", false,
					false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Updating Number of units = 2, Number of stories = 2, Year built = 2018, Total
			// Square Footage = 2000,
			testData = data.get(dataValue2);
			dwellingPage.addDwellingDetails(testData, 1, 1);

			// Updating protection discounts details Year Plumbing = 2017, Year Electrical =
			// 2017, Year HVAC = 2017
			dwellingPage.enterProtectionDiscountsDetails(testData, 1, 1);

			// Click on resubmit
			dwellingPage.reSubmit.scrollToElement();
			dwellingPage.reSubmit.click();

			// Click on override
			if (existingAccountPage.override.checkIfElementIsPresent()
					&& existingAccountPage.override.checkIfElementIsDisplayed()) {
				existingAccountPage.override.click();
			}

			// Click on create quote
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			Assertions.passTest("Dwelling Page", "Clicked on create quote button successfully");

			// Click on override
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			// Entering prior loss details
			Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior loss page",
					"Prior loss page loaded successfully", false, false);

			testData = data.get(dataValue1);
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior loss page",
						"Prior loss page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			}

			// Click on get a quote
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on Get A quote button successfully");

			// Asserting warning message when Year Plumbing = 2017, Year Electrical = 2017,
			// Year HVAC = 2017
			Assertions.addInfo("Scenario 02",
					"Asserting warning message when Year Plumbing = 2017, Year Electrical = 2017, Year HVAC = 2017");
			Assertions.verify(createQuotePage.globalErr.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Warning message is " + createQuotePage.globalErr.getData() + " is verified", false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Click on previous button
			createQuotePage.previous.scrollToElement();
			createQuotePage.previous.click();
			Assertions.passTest("Create Quote Page", "Clicked on previous button successfully");

			// Click on Edit building pencil button
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();
			Assertions.passTest("Account Overview Page", "Clicked on editbuilding link successfully");

			// Updating roof details Year Roof Last Replaced = 2017
			testData = data.get(dataValue2);
			dwellingPage.addRoofDetails(testData, 1, 1);

			// Updating protection discounts details Year Plumbing = 2019, Year Electrical =
			// 2019, Year HVAC = 2019
			testData = data.get(dataValue3);
			dwellingPage.enterProtectionDiscountsDetails(testData, 1, 1);

			// Click on resubmit
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();

			// Asserting warning message Year Roof Last Replaced = 2017
			Assertions.addInfo("Scenario 03", "Asserting warning message when Year Roof Last Replaced = 2017");
			Assertions.verify(dwellingPage.protectionClassWarMsg.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Warning message is " + dwellingPage.protectionClassWarMsg.getData() + " is verified", false,
					false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on roof details
			dwellingPage.roofDetailsLink.scrollToElement();
			dwellingPage.roofDetailsLink.click();

			// Clearing roof details, Year Roof Last Replaced
			dwellingPage.yearRoofLastReplaced.scrollToElement();
			dwellingPage.yearRoofLastReplaced.clearData();
			dwellingPage.yearRoofLastReplaced.tab();

			// Click on resubmit
			dwellingPage.waitTime(2);// adding wait time to visible the element
			dwellingPage.reSubmit.scrollToElement();
			dwellingPage.reSubmit.click();

			// Click on create quote
			dwellingPage.waitTime(2);// adding wait time to visible the element
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();

			// Click on override
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			// Entering Create Quote Details
			testData = data.get(dataValue1);
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create quote page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);

			// Click on Get A Quote
			createQuotePage.getAQuote.waitTillPresenceOfElement(60);
			createQuotePage.getAQuote.waitTillVisibilityOfElement(60);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create quote page", "Quote details entered uccessfully");

			// Asserting prior loss warning message when Have the damages been repaired? =
			// No, Is the claim open? = yes
			Assertions.addInfo("Scenario 04", "Scenario 04 Stared");
			Assertions.addInfo("Create quote page",
					"Asserting prior loss warning message when Have the damages been repaired? = No ");
			Assertions.verify(createQuotePage.warningMessages.formatDynamicPath("damage").checkIfElementIsDisplayed(),
					true, "Create quote page",
					"Prior loss damages been repaired warning message is "
							+ createQuotePage.warningMessages.formatDynamicPath("damage").getData() + " is verified",
					false, false);
			Assertions.addInfo("Create quote page",
					"Asserting prior loss warning message when Is the claim open? = yes");
			Assertions.verify(
					createQuotePage.warningMessages.formatDynamicPath("open claim").checkIfElementIsDisplayed(), true,
					"Create quote page",
					"Prior loss claim open warning message is "
							+ createQuotePage.warningMessages.formatDynamicPath("open claim").getData()
							+ " is verified",
					false, false);

			// Asserting Cov E waring message when Cov E = None
			Assertions.addInfo("Create quote page", "Asserting Cov E warning message when Cov E = None");
			Assertions.verify(
					createQuotePage.coverageEWarningmessage.formatDynamicPath("Cov E").checkIfElementIsDisplayed(),
					true, "Create quote page",
					"Cov E warning message is "
							+ createQuotePage.coverageEWarningmessage.formatDynamicPath("Cov E").getData()
							+ " is verified",
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Click on Continue
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();

			// Asserting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account overview page Loaded successfully", false, false);
			quoteLength = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLength - 1);
			Assertions.passTest("Account overview page", "Quote Number is : " + quoteNumber);

			// click on edit link
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();

			// Updating Year built 40 years back Year built = 1980
			testData = data.get(dataValue3);
			Assertions.verify(dwellingPage.createQuote.checkIfElementIsDisplayed(), true, "Dwelling page",
					"Dwelling page loaded successfully", false, false);
			dwellingPage.yearBuilt.scrollToElement();
			dwellingPage.yearBuilt.clearData();
			dwellingPage.continueWithUpdateBtn.scrollToElement();
			dwellingPage.continueWithUpdateBtn.click();
			dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));
			dwellingPage.yearBuilt.tab();

			// click on create quote
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();

			// Click on create quote
			dwellingPage.waitTime(2);// adding wait time to visible the element
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();

			// Click on override
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			// Click on Get A Quote
			createQuotePage.getAQuote.waitTillPresenceOfElement(60);
			createQuotePage.getAQuote.waitTillVisibilityOfElement(60);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create quote page", "Quote details entered uccessfully");

			// Adding IO-20816
			// Verifying roof age warning message when year built 40 years older on dwelling
			// page and usm can override
			// Message: The account is ineligible due to the quoted building being 40 years
			// or older with no updates in the last 15 years.
			Assertions.addInfo("Scenario 05",
					"Verifying roof age warning message when year built 40 years older on dwelling page");
			Assertions.verify(
					createQuotePage.warningMessages
							.formatDynamicPath(
									"The account is ineligible due to the quoted building being 40 years or older")
							.getData().contains("building being 40 years or older"),
					true, "Create Quote Page",
					"The year built warning message is " + createQuotePage.warningMessages
							.formatDynamicPath(
									"The account is ineligible due to the quoted building being 40 years or older")
							.getData() + " displayed verified",
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Asserting Roof age warning message when year built older 40 Years
			// Year built = 1980
			Assertions.addInfo("Scenario 06", "Verifying open claim, unrepaired damage and roof age warning message");
			Assertions.verify(createQuotePage.warningMessages.formatDynamicPath("roof age").checkIfElementIsDisplayed(),
					true, "Create quote page",
					"Account is ineligible due to an roof age warning message is '"
							+ createQuotePage.warningMessages.formatDynamicPath("roof age").getData() + "' is verified",
					false, false);
			Assertions.verify(
					createQuotePage.warningMessages.formatDynamicPath("open claim").checkIfElementIsDisplayed(), true,
					"Create quote page",
					"Account is inligible due to an open claim warning message is '"
							+ createQuotePage.warningMessages.formatDynamicPath("open claim").getData()
							+ "' is verified",
					false, false);
			Assertions.verify(
					createQuotePage.warningMessages.formatDynamicPath("open claim").checkIfElementIsDisplayed(), true,
					"Create quote page",
					"Account is ineligible due to unrepaired damage warning message is '"
							+ createQuotePage.warningMessages.formatDynamicPath("open claim").getData()
							+ "' is verified",
					false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// click on continue
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();

			// Asserting quote number 2
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			quoteLength = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLength - 1);
			Assertions.passTest("Account overview page", " Quote Number is : " + quoteNumber);

			// click on request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account overview page", " Clicked request bind successfully");

			// Enter UnderWriting questions
			testData = data.get(dataValue1);
			Assertions.verify(underwritingQuestionsPage.saveButton.checkIfElementIsDisplayed(), true,
					"Underwriting questions page", "Underwriting questions page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting questions page", "Entered underwriting questions successfully");

			// Enter Bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request bind page",
					"Request bind page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request bind page", "Entered bind details successfully");

			// Asserting referral messages
			Assertions.addInfo("Scenario 07", "Scenario 07 Started");
			Assertions.addInfo("Underwriting questions page",
					"Asserting referral message when Is the named insured high profile or target risk (actors, athletes, political figure)? = Yes");
			Assertions.verify(
					createQuotePage.warningMessages.formatDynamicPath("high profile").checkIfElementIsDisplayed(), true,
					"Request bind page",
					"This is a high profile insured referral message is "
							+ createQuotePage.warningMessages.formatDynamicPath("high profile").getData()
							+ " is verified",
					false, false);
			Assertions.addInfo("Underwriting questions page",
					"Asserting referral message when Has the named insured been cancelled or non-renewed for underwriting reasons in the past three years? = Yes");
			Assertions.verify(
					createQuotePage.warningMessages.formatDynamicPath("cancelled").checkIfElementIsDisplayed(), true,
					"Request bind page",
					"This account was cancelled or non-renewed referral message is "
							+ createQuotePage.warningMessages.formatDynamicPath("cancelled").getData() + " is verified",
					false, false);
			Assertions.addInfo("Underwriting questions page",
					"Asserting referral message when Does the home have a wood or similar type burning stove as a primary or secondary heat source? = Yes");
			Assertions.verify(
					createQuotePage.warningMessages.formatDynamicPath("wood stove").checkIfElementIsDisplayed(), true,
					"Request bind page",
					"The quoted building has a wood stove referral message is "
							+ createQuotePage.warningMessages.formatDynamicPath("wood stove").getData()
							+ " is verified",
					false, false);
			Assertions.addInfo("Request bind page", "Asserting referral message when Entity = Trust");
			Assertions.verify(createQuotePage.warningMessages.formatDynamicPath("trust").checkIfElementIsDisplayed(),
					true, "Request bind page",
					"Insured is a trust or corporation referral message is "
							+ createQuotePage.warningMessages.formatDynamicPath("trust").getData() + " is verified",
					false, false);
			Assertions.addInfo("Request Bind Page",
					"Asserting referral message when Additional Interest Type = Additional Insured and Additional Interest Relationship Type =  Non Related Individual");
			Assertions.verify(
					createQuotePage.warningMessages.formatDynamicPath("Additional Insured").checkIfElementIsDisplayed(),
					true, "Request bind page",
					"Additional Insured referral message is "
							+ createQuotePage.warningMessages.formatDynamicPath("Additional Insured").getData()
							+ " is verified",
					false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Click on goto home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// search referral quote
			homePage.searchReferral(quoteNumber);
			Assertions.passTest("Home page", "Quote referral is searched successfully");

			// Approve Referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral page", "Referral request approved successfully");

			// Approving bind request
			Assertions.verify(approveDeclineQuotePage.approveButton.checkIfElementIsDisplayed(), true,
					"Approve decline quote page", "Approve decline quote page loaded successfully", false, false);
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Approve decline quote page", "Bind request approved successfully");

			// Asserting policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfullt", false, false);
			policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.passTest("Policy summary page", "Policy Number is " + policyNumber);

			// Click on Renewal
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy summary page", "Clicked on renewal link successfully");

			// Go to Home Page
			testData = data.get(dataValue1);
			if (policySummaryPage.expaacMessage.checkIfElementIsPresent()
					&& policySummaryPage.expaacMessage.checkIfElementIsDisplayed()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.click();

				// Enter expacc details
				expaccInfoPage.enterExpaccInfo(testData, policyNumber);
				Assertions.passTest("ExpaccInfo page", "ExpaccInfo details entered successfully");

				// Go to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();

				// Performing Renewal Searches
				homePage.searchPolicy(policyNumber);

				// clicking on renewal policy link
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();
			}

			// Asserting Renewal quote number
			Assertions.verify(accountOverviewPage.referredStatus.getData().contains("Referred"), true,
					"Account overvew page", "Account overview page loaded successfully", false, false);
			renewalQuoteNumber = accountOverviewPage.quoteNumber.getData().substring(1,
					(accountOverviewPage.quoteNumber.getData().length() - 1));
			Assertions.passTest("Account overview page", "Renewal quote number is " + renewalQuoteNumber);

			// Adding IO-21801
			// Calculating inflation guard applying to Cov A
			covAvalue = testData.get("L1D1-DwellingCovA");
			inflationGuardPercentage = testData.get("InflationGuardPercentage");
			calCovAValue = (Double.parseDouble(covAvalue) * Double.parseDouble(inflationGuardPercentage));
			Assertions.passTest("View/Print Full Quote Page",
					"NB Coverage A value " + testData.get("L1D1-DwellingCovA"));
			Assertions.passTest("View/Print Full Quote Page",
					"Inflation Guard Percentage " + testData.get("InflationGuardPercentage"));

			// Rounding off
			long roundCalCovAValue = Math.round(calCovAValue);

			// Converting double to string
			calCovAValue_s = Double.toString(roundCalCovAValue).replace(".0", "");

			// Click on view print full quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("View/Print Full Quote Page", "Clicked on vieworprint full quote link");

			// Verifying and Asserting actual and calculated Cov A value
			// Cov A = Cov A*inflationguard%(1.0713)
			Assertions.addInfo("Scenario 08", "Verifying inflation guard applied to renewal Cov A");
			actualCovAValue = viewOrPrintFullQuotePage.coveragesValues.formatDynamicPath(4).getData().replace(",", "");
			Assertions.verify(actualCovAValue, "$" + calCovAValue_s, "View Print Full Quote Page",
					"Inflation guard " + testData.get("InflationGuardPercentage")
							+ " applied to Coverage A, actual coverage A value " + actualCovAValue
							+ " and calculated coverage A value $" + calCovAValue_s + " bothe are same",
					false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			// IO-21801 Ended

			// Click on open referral
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();

			// approving referral
			if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
				referralPage.pickUp.waitTillVisibilityOfElement(60);
				referralPage.pickUp.scrollToElement();
				referralPage.pickUp.click();
			}
			referralPage.clickOnApprove();
			Assertions.passTest("Referral page", "Referral request approved successfully");

			// Approving Bind request
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Approve decline quote page", "Bind request approved successfully");

			// go to home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Search renewal quote
			homePage.searchQuote(renewalQuoteNumber);
			Assertions.passTest("Home page", "Renewal quote searched successfully");

			// creating another quote on renewal quote
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account Overview Page loaded successfully", false, false);
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account overview page", "Clicked on edit deductible and limits successfully");

			// Decrease the Cov A value
			testData = data.get(dataValue2);
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);

			// Click on get quote
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// Click on Override
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			// Asserting warning message when coverage A is reduced from 2000000 to 200000
			// on a renewal quote
			Assertions.addInfo("Scenario 09",
					"Asserting warning message when coverage A is reduced from 2000000 to 200000 on a renewal quote");
			Assertions.verify(
					createQuotePage.warningMessages.formatDynamicPath("Coverage A").checkIfElementIsDisplayed(), true,
					"Account overview page",
					"Cov A warning message is "
							+ createQuotePage.warningMessages.formatDynamicPath("Coverage A").getData()
							+ " is verified",
					false, false);
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBNCTC004 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBNCTC004 ", "Executed Successfully");
			}
		}
	}

}
