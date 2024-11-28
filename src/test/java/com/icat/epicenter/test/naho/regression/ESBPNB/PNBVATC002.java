/** Program Description : 1.Verifying the warning message when Occupancy as Tenant, Coverage E as "1,000,000" and Choose Short Rental as "Yes"
2.Verifying SLTF values on view print full quote page.
3. Verifying the Auto bound message when  Entity as "Corporation", and add 3 Mortgagees as Additional Interests and Bind the Policy.
 *  Author			    : Sowndarya NH
 *  Date of Creation    : 24/05/2022
 **/
package com.icat.epicenter.test.naho.regression.ESBPNB;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBVATC002 extends AbstractNAHOTest {

	public PNBVATC002() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBPNB/VATC002.xls";
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
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();

		// Initializing the variables
		int data_Value1 = 0;
		int data_Value2 = 1;
		String quoteNumber;
		String policyNumber;

		Map<String, String> testData = data.get(data_Value1);
		Map<String, String> testData1 = data.get(data_Value2);
		DecimalFormat df = new DecimalFormat("0.00");
		boolean isTestPassed = false;

		try {
			// Creating New Account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account Created successfully");

			// Entering Zipcode
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling  Page",
					"Dwelling Page Loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling Details Entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterDeductiblesNAHO(testData);
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// click on get a quote
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// Verify the warning message
			Assertions.addInfo("Scenario 01",
					"Verifying the Warning message while creating NB quote when Occupied by as Tenant,Short Term rental as No and Coverage E=$1,000,000");
			if (createQuotePage.warningMessages.formatDynamicPath("tenant occupied or short-term rental")
					.checkIfElementIsPresent()
					&& createQuotePage.warningMessages.formatDynamicPath("tenant occupied or short-term rental")
							.checkIfElementIsDisplayed()) {
				Assertions.verify(
						createQuotePage.warningMessages
								.formatDynamicPath("tenant occupied or short-term rental").checkIfElementIsDisplayed(),
						false, "Create Quote Page",
						"The Warning message is"
								+ createQuotePage.warningMessages
										.formatDynamicPath("tenant occupied or short-term rental").getData()
								+ "displayed is verified",
						false, false);
			} else {
				Assertions.passTest("Create Quote Page", "The Warning message is not displayed");
			}
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
				Assertions.passTest("Create Quote Page", "Clicked on continue button");
			}

			// Getting the Quote Number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			int quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Click on edit dwelling
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit dwelling");

			// Occupied by as Primary
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			if (dwellingPage.OccupiedByArrow.checkIfElementIsPresent()
					&& !testData1.get("L1D1-OccupiedBy").equals("")) {
				Assertions.addInfo("Dwelling Page",
						"Occupied by Original Value : " + dwellingPage.occupiedByData.getData());
				dwellingPage.OccupiedByArrow.waitTillVisibilityOfElement(60);
				dwellingPage.OccupiedByArrow.scrollToElement();
				dwellingPage.OccupiedByArrow.click();
				dwellingPage.OccupiedByOption.formatDynamicPath(testData1.get("L1D1-OccupiedBy")).scrollToElement();
				dwellingPage.OccupiedByOption.formatDynamicPath(testData1.get("L1D1-OccupiedBy")).click();

				// Handling the expired quote popup
				if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
					dwellingPage.expiredQuotePopUp.scrollToElement();
					dwellingPage.continueWithUpdateBtn.scrollToElement();
					dwellingPage.continueWithUpdateBtn.click();
					dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
				}
				Assertions.passTest("Dwelling Page",
						"Occupied by Latest Value : " + dwellingPage.occupiedByData.getData());
			}

			// Select Short term rental as Yes
			if (dwellingPage.shortTermRentalYes.checkIfElementIsPresent()
					&& dwellingPage.shortTermRentalYes.checkIfElementIsDisplayed()) {
				if (!testData1.get("L1D1-DwellingShortTermRental").equals("")) {
					if (testData1.get("L1D1-DwellingShortTermRental").equals("Yes")) {
						dwellingPage.shortTermRentalYes.scrollToElement();
						dwellingPage.shortTermRentalYes.click();
						Assertions.passTest("Dwelling Page", "Selected the Short Term Rental as Yes");
					}
				}
			}

			// Handling the expired quote popup
			if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
				dwellingPage.expiredQuotePopUp.scrollToElement();
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
				dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}

			// Click on review dwelling
			dwellingPage.reviewDwelling();

			// Click on create quote
			if (dwellingPage.createQuote.checkIfElementIsPresent()
					|| dwellingPage.createQuote.checkIfElementIsDisplayed()) {
				dwellingPage.createQuote.waitTillPresenceOfElement(60);
				dwellingPage.createQuote.waitTillVisibilityOfElement(60);
				dwellingPage.createQuote.waitTillButtonIsClickable(60);
				dwellingPage.createQuote.scrollToElement();
				dwellingPage.createQuote.click();
			}

			// Click on Override
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			// Click on get a quote
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			Assertions.passTest("Create Quote Page",
					"Coverage E Value Entered is " + testData.get("L1D1-DwellingCovE"));
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on Get a quote button");

			// Verifying the warning messages
			Assertions.addInfo("Scenario 02",
					"Verifying the Warning messages while creating NB requote when Short Term Rental "
							+ "Selected as Yes,Occupied by as Primary and Coverage E=$1,000,000");
			if (createQuotePage.warningMessages.formatDynamicPath("tenant occupied or short-term rental")
					.checkIfElementIsPresent()
					&& createQuotePage.warningMessages.formatDynamicPath("tenant occupied or short-term rental")
							.checkIfElementIsDisplayed()) {
				Assertions.verify(
						createQuotePage.warningMessages
								.formatDynamicPath("tenant occupied or short-term rental").checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"The Warning message is"
								+ createQuotePage.warningMessages
										.formatDynamicPath("tenant occupied or short-term rental").getData()
								+ "displayed is verified",
						false, false);
			} else {
				Assertions.passTest("Create Quote Page", "The Warning message is not displayed");
			}
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
				Assertions.passTest("Create Quote Page", "Clicked on continue button");
			}

			// Getting the ReQuote Number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			int quoteLenth = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLenth - 1);
			Assertions.passTest("Account Overview Page", "The Requote Number is : " + quoteNumber);

			// Click on Request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Entering details in Underwriting Questions Page
			Assertions.verify(underwritingQuestionsPage.saveButton.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered successfully");

			// Entering bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Details Entered successfully");

			// Clicking on homepage button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			// Opening the Referral
			Assertions.verify(
					accountOverviewPage.uploadPreBindDocuments.checkIfElementIsPresent()
							&& accountOverviewPage.uploadPreBindDocuments.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "Account Overview Page Loaded Successfully", false, false);
			if (accountOverviewPage.openReferral.checkIfElementIsPresent()
					&& accountOverviewPage.openReferral.checkIfElementIsDisplayed()) {
				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();
			}

			// Approve Referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind page loaded successfully", false, false);
			requestBindPage.approveRequestNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Asserting the Policy Number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is " + policyNumber, false, false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Clicking on expaac link in home page
			homePage.expaccLink.scrollToElement();
			homePage.expaccLink.click();
			Assertions.passTest("Home Page", "Clicked on Expaac Link");

			// Entering expaac data
			Assertions.verify(expaccInfoPage.policyNumber.checkIfElementIsDisplayed(), true, "Expacc Info Page",
					"Update Expaac Data page loaded successfully", false, false);
			expaccInfoPage.enterExpaccInfo(testData, policyNumber);
			Assertions.passTest("Expacc Info Page", "Expaac Data updated successfully");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Find the policy by entering policy Number
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Policy Number : " + policyNumber + " searched successfully");

			// Click on Renew Policy Hyperlink
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy Link");

			// Assert renewal quote number
			Assertions.verify(
					accountOverviewPage.quoteNumber.checkIfElementIsPresent()
							&& accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account overview page", "Renewal Quote number is : " + quoteNumber);
			if (accountOverviewPage.openReferral.checkIfElementIsPresent()
					&& accountOverviewPage.openReferral.checkIfElementIsDisplayed()) {

				// Opening the Referral from Account Overview Page
				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();
				Assertions.passTest("Account Overview Page", "Referral is Openned");

				// Approve the referral
				referralPage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");
				Assertions.verify(approveDeclineQuotePage.approveButton.checkIfElementIsDisplayed(), true,
						"Approve Decline Quote Page", "Approve Decline Quote page loaded successfully", false, false);
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Approve Decline Quote Page", "Bind Request approved successfully");

				// Click on close
				if (referralPage.close.checkIfElementIsPresent() && referralPage.close.checkIfElementIsDisplayed()) {
					referralPage.close.scrollToElement();
					referralPage.close.click();
				}

				// Search for quote number
				homePage.searchQuote(quoteNumber);

			}

			// Click on edit dwelling
			Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit dwelling");

			// Occupied by as Tenant and Coverage E as “$1,000,000”
			if (dwellingPage.OccupiedByArrow.checkIfElementIsPresent() && !testData.get("L1D1-OccupiedBy").equals("")) {
				Assertions.addInfo("Dwelling Page",
						"Occupied by Original Value : " + dwellingPage.occupiedByData.getData());
				dwellingPage.OccupiedByArrow.waitTillVisibilityOfElement(60);
				dwellingPage.OccupiedByArrow.scrollToElement();
				dwellingPage.OccupiedByArrow.click();
				dwellingPage.OccupiedByOption.formatDynamicPath(testData.get("L1D1-OccupiedBy")).scrollToElement();
				dwellingPage.OccupiedByOption.formatDynamicPath(testData.get("L1D1-OccupiedBy")).click();

				// Handling the expired quote popup
				if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
					dwellingPage.expiredQuotePopUp.scrollToElement();
					dwellingPage.continueWithUpdateBtn.scrollToElement();
					dwellingPage.continueWithUpdateBtn.click();
					dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
				}
				Assertions.passTest("Dwelling Page",
						"Occupied by Latest Value : " + dwellingPage.occupiedByData.getData());
			}
			// Short term rental as No
			if (dwellingPage.shortTermRentalYes.checkIfElementIsPresent()
					&& dwellingPage.shortTermRentalYes.checkIfElementIsDisplayed()) {
				if (!testData.get("L1D1-DwellingShortTermRental").equals("")) {
					if (testData.get("L1D1-DwellingShortTermRental").equals("No")) {
						dwellingPage.shortTermRentalNo.scrollToElement();
						dwellingPage.shortTermRentalNo.click();
					}
				}
			}
			Assertions.passTest("Dwelling Page", "Short Term Rental selected as No");

			// Handling the expired quote popup
			if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
				dwellingPage.expiredQuotePopUp.scrollToElement();
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
				dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}

			// Click on review dwelling
			dwellingPage.reviewDwelling();

			// Click on create quote
			if (dwellingPage.createQuote.checkIfElementIsPresent()
					|| dwellingPage.createQuote.checkIfElementIsDisplayed()) {
				dwellingPage.createQuote.waitTillPresenceOfElement(60);
				dwellingPage.createQuote.waitTillVisibilityOfElement(60);
				dwellingPage.createQuote.waitTillButtonIsClickable(60);
				dwellingPage.createQuote.scrollToElement();
				dwellingPage.createQuote.click();
			}

			// Enter coverage E as “$1,000,000”
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			Assertions.passTest("Create Quote Page",
					"Coverage E Value Entered is " + testData.get("L1D1-DwellingCovE"));

			// Click on get a quote
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on Get a quote button");

			// Verifying the warning messages
			Assertions.addInfo("Scenario 03",
					"Verifying the Warning messages while creating requote on renewal policy when Occupied by as Tenant ,Short term rental as No and Coverage E=$1,000,000");
			if (createQuotePage.warningMessages.formatDynamicPath("tenant occupied or short-term rental")
					.checkIfElementIsPresent()
					&& createQuotePage.warningMessages.formatDynamicPath("tenant occupied or short-term rental")
							.checkIfElementIsDisplayed()) {
				Assertions.verify(
						createQuotePage.warningMessages
								.formatDynamicPath("tenant occupied or short-term rental").checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"The Warning message is"
								+ createQuotePage.warningMessages
										.formatDynamicPath("tenant occupied or short-term rental").getData()
								+ "displayed is verified",
						false, false);
			} else {
				Assertions.passTest("Create Quote Page", "The Warning message is not displayed");
			}
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
				Assertions.passTest("Create Quote Page", "Clicked on continue button");
			}

			// Getting the quote number
			Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "The Renewal ReQuote Number : " + quoteNumber);

			// Click on edit dwelling
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit dwelling");

			// Occupied by as Primary
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			if (dwellingPage.OccupiedByArrow.checkIfElementIsPresent()
					&& !testData1.get("L1D1-OccupiedBy").equals("")) {
				Assertions.addInfo("Dwelling Page",
						"Occupied by Original Value : " + dwellingPage.occupiedByData.getData());
				dwellingPage.OccupiedByArrow.waitTillVisibilityOfElement(60);
				dwellingPage.OccupiedByArrow.scrollToElement();
				dwellingPage.OccupiedByArrow.click();
				dwellingPage.OccupiedByOption.formatDynamicPath(testData1.get("L1D1-OccupiedBy")).scrollToElement();
				dwellingPage.OccupiedByOption.formatDynamicPath(testData1.get("L1D1-OccupiedBy")).click();

				// Handling the expired quote popup
				if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
					dwellingPage.expiredQuotePopUp.scrollToElement();
					dwellingPage.continueWithUpdateBtn.scrollToElement();
					dwellingPage.continueWithUpdateBtn.click();
					dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
				}
				Assertions.passTest("Dwelling Page",
						"Occupied by Latest Value : " + dwellingPage.occupiedByData.getData());
			}

			// Select Short term rental as Yes
			if (dwellingPage.shortTermRentalYes.checkIfElementIsPresent()
					&& dwellingPage.shortTermRentalYes.checkIfElementIsDisplayed()) {
				if (!testData1.get("L1D1-DwellingShortTermRental").equals("")) {
					if (testData1.get("L1D1-DwellingShortTermRental").equals("Yes")) {
						dwellingPage.shortTermRentalYes.scrollToElement();
						dwellingPage.shortTermRentalYes.click();
					}
				}
			}

			// Handling the expired quote popup
			if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
				dwellingPage.expiredQuotePopUp.scrollToElement();
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
				dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}
			Assertions.passTest("Dwelling Page", "Selected the Short Term Rental as Yes");

			// Click on review dwelling
			dwellingPage.reviewDwelling();

			// Click on create quote
			if (dwellingPage.createQuote.checkIfElementIsPresent()
					|| dwellingPage.createQuote.checkIfElementIsDisplayed()) {
				dwellingPage.createQuote.waitTillPresenceOfElement(60);
				dwellingPage.createQuote.waitTillVisibilityOfElement(60);
				dwellingPage.createQuote.waitTillButtonIsClickable(60);
				dwellingPage.createQuote.scrollToElement();
				dwellingPage.createQuote.click();
			}

			// Click on get a quote
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on Get a quote button");

			// Verifying the warning messages
			Assertions.addInfo("Scenario 04",
					"Verifying the Warning messages while creating requote on renewal policy when Short Term Rental Selected as Yes ,Occupied by as Primary and Coverage E=$1,000,000");
			if (createQuotePage.warningMessages.formatDynamicPath("tenant occupied or short-term rental")
					.checkIfElementIsPresent()
					&& createQuotePage.warningMessages.formatDynamicPath("tenant occupied or short-term rental")
							.checkIfElementIsDisplayed()) {
				Assertions.verify(
						createQuotePage.warningMessages
								.formatDynamicPath("tenant occupied or short-term rental").checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"The Warning message is"
								+ createQuotePage.warningMessages
										.formatDynamicPath("tenant occupied or short-term rental").getData()
								+ "displayed is verified",
						false, false);
			} else {
				Assertions.passTest("Create Quote Page", "The Warning message is not displayed");
			}
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
				Assertions.passTest("Create Quote Page", "Clicked on continue button");
			}

			// Getting renewal quote number
			Assertions.verify(accountOverviewPage.viewPrintFullQuoteLink.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "The Renewal ReQuote Number : " + quoteNumber);

			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print full quote link");
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page", "View/Print Full Quote Page loaded successfully", false, false);

			// Getting premium,inspection and policy fee values
			String premiumValue = viewOrPrintFullQuotePage.premiumValue.getData().replace("$", "").replace(",", "");
			String policyFee = viewOrPrintFullQuotePage.policyFee.getData().replace("$", "").replace(",", "");
			String inspectionFee = viewOrPrintFullQuotePage.inspectionFee.getData().replace("$", "").replace(",", "");
			String actualSltf = viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData().replace("$", "").replace(",",
					"");
//		String surplusContributionValue = viewOrPrintFullQuotePage.surplusContributionValue.getData().replace("$", "")
//				.replace(",", "");

			// Converting String values to double
			double d_premiumVPFQ = Double.parseDouble(premiumValue);
			double d_inspecionFeeVPFQ = Double.parseDouble(inspectionFee);
			double d_policyFeeVPFQ = Double.parseDouble(policyFee);
			double d_actualSltf = Double.parseDouble(actualSltf);
//		double d_surplusContributionValueVPFQ = Double.parseDouble(surplusContributionValue);

			// Getting sltf percentage
			String sltfPercentage = testData1.get("SLTFPercentage");
			String maintananceAssesmentFund = testData1.get("MaintenanceAssesmentFund");

			// Calculating sltf
//		double d_sltf = (d_premiumVPFQ + d_inspecionFeeVPFQ + d_policyFeeVPFQ + d_surplusContributionValueVPFQ);
			double d_sltf = (d_premiumVPFQ + d_inspecionFeeVPFQ + d_policyFeeVPFQ);
			double d_calcSltf = d_sltf * Double.parseDouble(sltfPercentage);

			// Calculating Maintenance Fund Assessment
			double d_maintananceAssesmentFund = d_sltf * Double.parseDouble(maintananceAssesmentFund);

			// Getting Actual Maintenance Fund Assessment
			String actualmaintananceAssesmentFund = viewOrPrintFullQuotePage.maintenanceFund.getData().replace("$", "");
			double d_actualmaintananceAssesmentFund = Double.parseDouble(actualmaintananceAssesmentFund);

			// Verifying calculated and actual sltf values and Maintenance Fund Assessment
			Assertions.addInfo("Scenario 05",
					"Verifying the Actual and Calculated SLTF values on View Print Full Quote Page");
			if (Precision.round(Math.abs(Precision.round(d_calcSltf, 2) - Precision.round(d_actualSltf, 2)),
					2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page", "Actual SLTF Value :" + "$" + d_actualSltf);
				Assertions.passTest("View/Print Full Quote Page",
						"Calculated SLTF Value :" + "$" + df.format(d_calcSltf));
			} else {
				Assertions.passTest("View/Print Full Quote Page",
						"The Difference between actual  and calculated SLTF is more than 0.05");
			}

			if (Precision.round(Math.abs(Precision.round(d_maintananceAssesmentFund, 2)
					- Precision.round(d_actualmaintananceAssesmentFund, 2)), 2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page",
						"Actual Maintenance Fund Assessment Value :" + "$" + d_actualmaintananceAssesmentFund);
				Assertions.passTest("View/Print Full Quote Page",
						"Calculated Maintenance Fund Assessment Value :" + "$" + df.format(d_maintananceAssesmentFund));
			} else {
				Assertions.passTest("View/Print Full Quote Page",
						"The Difference between actual  and calculated Maintenance Fund Assessment is more than 0.05");
			}
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Click on go back
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// Click on issue quote
			accountOverviewPage.issueQuoteButton.scrollToElement();
			accountOverviewPage.issueQuoteButton.click();

			// Click on release renewal to producer button
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release renewal to producer button");

			// Click on request bind
			accountOverviewPage.clickOnRenewalRequestBindNAHO(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind Button");

			// Enter bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);

			// enter AI details
			requestBindPage.addAdditionalInterest(testData1);
			requestBindPage.renewalRequestBindNAHO(testData);

			// Asserting policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. The Renewal PolicyNumber is : " + policyNumber, false,
					false);

			// Verifying the auto bound message
			Assertions.addInfo("Scenario 06", "Verifying the Autobound message on Policy Summary Page");
			Assertions.verify(policySummaryPage.renewalDelMsg.getData().contains("account has been bound"), true,
					"Policy Summary Page",
					"The Message " + policySummaryPage.renewalDelMsg.getData() + " displayed is verified", false,
					false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBVATC002 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBVATC002 ", "Executed Successfully");
			}
		}
	}
}
