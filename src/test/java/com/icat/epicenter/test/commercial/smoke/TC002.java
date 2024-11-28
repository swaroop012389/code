/** Program Description: Create a Wholesale GL Quote with single location and single building by entering all the details in building page
 *                       create quote page,Priorloss page and request bind page and decline the bind referral
 *  Author			   : Sowndarya NH
 *  Date of Creation   : 04/05/2023
**/

package com.icat.epicenter.test.commercial.smoke;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BrokerOfRecordPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EditAdditionalInterestInformationPage;
import com.icat.epicenter.pom.EditInspectionContactPage;
import com.icat.epicenter.pom.EditInsuredContactInfoPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EmailQuotePage;
import com.icat.epicenter.pom.GLInformationPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.RequestCancellationPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;
import com.icat.epicenter.utils.TestConstants;

public class TC002 extends AbstractCommercialTest {

	public TC002() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/smoke/NBTCID02.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setupData) {

		// Initializing the pages
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		GLInformationPage glInformationPage = new GLInformationPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		ViewOrPrintFullQuotePage viewprFullQuotePage = new ViewOrPrintFullQuotePage();
		EmailQuotePage emailQuotePage = new EmailQuotePage();
		OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();
		RequestCancellationPage requestCancellationPage = new RequestCancellationPage();
		BrokerOfRecordPage brokerOfRecordPage = new BrokerOfRecordPage();
		EditInsuredContactInfoPage editInsuredContactInfoPage = new EditInsuredContactInfoPage();
		EditInspectionContactPage editInspectionContactPage = new EditInspectionContactPage();
		EditAdditionalInterestInformationPage editAdditionalInterestInformationPage = new EditAdditionalInterestInformationPage();

		// Initializing the variables
		String quoteNumber;
		Map<String, String> testData = data.get(TestConstants.DATA_COL_1);

		// creating New account
		Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
				"Home page loaded successfully", false, false);
		homePage.createNewAccountWithNamedInsured(testData, setupData);
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

		// Enter Building Details
		//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page","Building Page loaded successfully", false, false);
		buildingPage.enterBuildingDetails(testData);

		// selecting peril
		if (!testData.get("Peril").equals("")) {
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));
		}

		// enter prior loss details
		if (!testData.get("PriorLoss1").equals("")) {
			Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossPage.selectPriorLossesInformation(testData);
		}

		// entering general liability details
		if (!testData.get("L1-GLLocationClass").equals("")) {
			Assertions.verify(glInformationPage.locationClassArrow.formatDynamicPath(0).checkIfElementIsDisplayed(),
					true, "GL Information Page", "GL Information Page loaded successfully", false, false);
			glInformationPage.enterGLInformation(testData);
			Assertions.passTest("GL Information Page", "GL Information details entered successfully");
		}

		// Entering Create quote page Details
		Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Create Quote Page loaded successfully", false, false);
		createQuotePage.enterQuoteDetailsCommercialSmoke(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");

		// getting the quote number
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Account Overview Page loaded successfully", false, false);
		quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
		Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

		// Assert the presence of Account overview elements
		Assertions.addInfo("Account Overview Page", "Verifying the presence of Account Overview features");
		Assertions.verify(accountOverviewPage.viewPrintFullQuoteLink.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "View/Print Full Quote link displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.emailQuoteLink.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Email Quote link displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.requestESignatureLink.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Request E Signature link displayed is verified", false, false);
		//TF 10/29/24 - Request Premium Change is only available for external users
		//Assertions.verify(accountOverviewPage.requestPremiumChangeLink.checkIfElementIsDisplayed(), true,"Account Overview Page", "Request Premium Change link displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.overridePremiumLink.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Override Premium link displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.pushToRMSLink.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Push to RMS link displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Create Another Quote button displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.editDeductibleAndLimits.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Edit deductibles and limits button displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.deleteAccount.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Delete Account Button displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.priorLossEditLink.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Edit Prior Loss link displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.producerLink.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Producer Edit link displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.editInsuredContactInfo.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Edit Insured Contact link displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.editInspectionContact.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Edit Inspection Contact link displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.editAdditionalIntersets.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Edit Additional Interests link displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.deleteAccount.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Delete Account Button displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.editFees.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Edit Fees icon displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.deleteQuote.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Delete Quote icon displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.quoteSpecifics.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Quote Specifics link displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.quotePremium.formatDynamicPath("1").checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Quote Premium displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.quoteStatus.formatDynamicPath("1").checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Quote Status displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.editLocation.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Edit Location link displayed is verified", false, false);

		// click on all the available link on account overview page
		// Click on View print full quote link
		accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
		accountOverviewPage.viewPrintFullQuoteLink.click();
		Assertions.passTest("Account Overview Page", "Clicked on View Print Full Quote link");
		Assertions.verify(viewprFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
				"View Print Full Quote Page", "View Print Full Quote Page Loaded successfully", false, false);
		viewprFullQuotePage.backButton.scrollToElement();
		viewprFullQuotePage.backButton.click();

		// Click on Email Quote link
		accountOverviewPage.emailQuoteLink.scrollToElement();
		accountOverviewPage.emailQuoteLink.click();
		Assertions.passTest("Account Overview Page", "Clicked on Email Quote link");
		Assertions.verify(emailQuotePage.cancel.checkIfElementIsDisplayed(), true, "Email Quote Page",
				"Email Quote Page Loaded successfully", false, false);
		emailQuotePage.cancel.scrollToElement();
		emailQuotePage.cancel.click();

		//TF 10/28/24 - Request Premium Change link is not available to USMs
		// Click on Request Premium change link
		/*accountOverviewPage.requestPremiumChangeLink.scrollToElement();
		accountOverviewPage.requestPremiumChangeLink.click();
		Assertions.passTest("Account Overview Page", "Clicked on Request Premium Change link");
		accountOverviewPage.rpcUpdateBtn.waitTillVisibilityOfElement(60);
		Assertions.verify(accountOverviewPage.rpcUpdateBtn.checkIfElementIsDisplayed(), true, "Premium Adjustment Page",
				"Premium Adjustment Page Loaded successfully", false, false);
		accountOverviewPage.rpcCancelBtn.scrollToElement();
		accountOverviewPage.rpcCancelBtn.click();*/

		// Click on Override Premium link
		accountOverviewPage.overridePremiumLink.scrollToElement();
		accountOverviewPage.overridePremiumLink.click();
		Assertions.passTest("Account Overview Page", "Clicked on Override Premium link");
		overridePremiumAndFeesPage.cancelButton.waitTillVisibilityOfElement(60);
		Assertions.verify(overridePremiumAndFeesPage.cancelButton.checkIfElementIsDisplayed(), true,
				"Override Premium and Fees Page", "Override Premium and Fees Page Loaded successfully", false, false);
		overridePremiumAndFeesPage.cancelButton.scrollToElement();
		overridePremiumAndFeesPage.cancelButton.click();

		// Click on Request E-Signature link
		accountOverviewPage.requestESignatureLink.scrollToElement();
		accountOverviewPage.requestESignatureLink.click();
		Assertions.passTest("Account Overview Page", "Clicked on Request E Signature link");
		requestCancellationPage.cancelBtn.waitTillVisibilityOfElement(60);
		Assertions.verify(requestCancellationPage.cancelBtn.checkIfElementIsDisplayed(), true,
				"Request E Signature Page", "Request E Signature Page Loaded successfully", false, false);
		requestCancellationPage.cancelBtn.scrollToElement();
		requestCancellationPage.cancelBtn.click();

		// click on Producer link
		accountOverviewPage.producerLink.scrollToElement();
		accountOverviewPage.producerLink.click();
		Assertions.passTest("Account Overview Page", "Clicked on Edit Producer link");
		brokerOfRecordPage.cancel.waitTillVisibilityOfElement(60);
		Assertions.verify(brokerOfRecordPage.cancel.checkIfElementIsDisplayed(), true, "Broker of Record Page",
				"Broker of Record Page Loaded successfully", false, false);
		brokerOfRecordPage.cancel.scrollToElement();
		brokerOfRecordPage.cancel.click();

		// click on Insured contact info link
		accountOverviewPage.editInsuredContactInfo.scrollToElement();
		accountOverviewPage.editInsuredContactInfo.click();
		Assertions.passTest("Account Overview Page", "Clicked on Edit Insured Contact link");
		editInsuredContactInfoPage.cancel.waitTillVisibilityOfElement(60);
		Assertions.verify(editInsuredContactInfoPage.cancel.checkIfElementIsDisplayed(), true,
				"Edit Insured Name/Address Page", "Edit Insured Name/Address Page Loaded successfully", false, false);
		editInsuredContactInfoPage.cancel.scrollToElement();
		editInsuredContactInfoPage.cancel.click();

		// click on edit inspection contact link
		accountOverviewPage.editInspectionContact.scrollToElement();
		accountOverviewPage.editInspectionContact.click();
		Assertions.passTest("Account Overview Page", "Clicked on Edit Inspection Contact link");
		editInspectionContactPage.cancel.waitTillVisibilityOfElement(60);
		Assertions.verify(editInspectionContactPage.cancel.checkIfElementIsDisplayed(), true,
				"Edit Inspection Contact Page", "Edit Inspection Contact Page Loaded successfully", false, false);
		editInspectionContactPage.cancel.scrollToElement();
		editInspectionContactPage.cancel.click();

		// click on edit additional interests link
		accountOverviewPage.editAdditionalIntersets.scrollToElement();
		accountOverviewPage.editAdditionalIntersets.click();
		Assertions.passTest("Account Overview Page", "Clicked on Edit Additional Interests link");
		editAdditionalInterestInformationPage.cancel.waitTillVisibilityOfElement(60);
		Assertions.verify(editAdditionalInterestInformationPage.cancel.checkIfElementIsDisplayed(), true,
				"Edit Additional Interests Information Page",
				"Edit Additional Interests Information Page Loaded successfully", false, false);
		editAdditionalInterestInformationPage.cancel.scrollToElement();
		editAdditionalInterestInformationPage.cancel.click();

		// click on edit prior loss link
		accountOverviewPage.priorLossEditLink.scrollToElement();
		accountOverviewPage.priorLossEditLink.click();
		Assertions.passTest("Account Overview Page", "Clicked on Edit Prior Loss link");
		priorLossPage.cancelButton.waitTillVisibilityOfElement(60);
		Assertions.verify(priorLossPage.cancelButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
				"Prior Loss Page Loaded successfully", false, false);
		priorLossPage.cancelButton.scrollToElement();
		priorLossPage.cancelButton.click();

		// click on edit fees link
		accountOverviewPage.editFees.scrollToElement();
		accountOverviewPage.editFees.click();
		Assertions.passTest("Account Overview Page", "Clicked on Edit Fees link");
		accountOverviewPage.closeSymbol.waitTillVisibilityOfElement(60);
		Assertions.verify(accountOverviewPage.closeSymbol.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Edit Fees section loaded successfully", false, false);
		accountOverviewPage.closeSymbol.scrollToElement();
		accountOverviewPage.closeSymbol.click();
		accountOverviewPage.cancelYes.scrollToElement();
		accountOverviewPage.cancelYes.click();

		// click on alt quote
		Assertions.addInfo("Account Overview Page", "Starting Alt Quote Test");
		accountOverviewPage.deductibleOptions.formatDynamicPath("3", "3").scrollToElement();
		accountOverviewPage.deductibleOptions.formatDynamicPath("3", "3").click();
		Assertions.passTest("Account Overview Page", "Clicked on Alt Quote link");
		accountOverviewPage.quoteNumber.waitTillVisibilityOfElement(60);
		Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Alt Quote loaded successfully", false, false);

		// getting alt quote number
		quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
		Assertions.passTest("Account Overview Page", "Alt Quote Number :  " + quoteNumber);

		// click on request bind
		accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

		// entering details in request bind page
		Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
				"Request Bind Page loaded successfully", false, false);
		requestBindPage.enterBindDetails(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");

		// clicking on home button
		Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
				"Bind Request Page loaded successfully", false, false);
		bindRequestPage.clickOnHomepagebutton();
		Assertions.passTest("Home Page", "Clicked on Home button");
		homePage.searchQuote(quoteNumber);
		Assertions.passTest("Home Page", "Quote for referral is searched successfully");

		// Click on Open Referral link
		accountOverviewPage.openReferral.scrollToElement();
		accountOverviewPage.openReferral.click();
		Assertions.passTest("Account Overview Page", "Clicked on open referral link");

		// Decline Referral - No carriers available for GL policies anyway
		Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
				"Referral page loaded successfully", false, false);
		referralPage.approveOrDeclineRequest.scrollToElement();
		referralPage.approveOrDeclineRequest.click();
		requestBindPage.decline.scrollToElement();
		requestBindPage.decline.click();
		Assertions.passTest("Request Bind Page", "Clicked on decline button successfully");
		Assertions.passTest("Referral Page", "Referral request Declined successfully");

		// Click on home page link
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();

		// Search the quote
		homePage.searchQuote(quoteNumber);
		Assertions.passTest("Home Page", "Searched the quote number successfully");

		// Delete the account
		Assertions.verify(accountOverviewPage.deleteAccount.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Account Overview Page loaded successfully", false, false);
		accountOverviewPage.deleteAccount.scrollToElement();
		accountOverviewPage.deleteAccount.click();
		Assertions.passTest("Account Overview Page", "Clicked on delete account");

		// click on yes delete
		accountOverviewPage.yesDeleteAccount.scrollToElement();
		accountOverviewPage.yesDeleteAccount.click();
		Assertions.passTest("Account Overview Page", "Account Deleted successfully");

		// Asserting account deleted message
		Assertions.verify(homePage.accountSuccessfullyDeletedMsg.checkIfElementIsDisplayed(), true, "Home page",
				homePage.accountSuccessfullyDeletedMsg.getData(), false, false);

		// Signing out
		Assertions.passTest("Commercial Smoke Test Case 02 - Wholesale GL", "Executed Successfully");
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
	}
}