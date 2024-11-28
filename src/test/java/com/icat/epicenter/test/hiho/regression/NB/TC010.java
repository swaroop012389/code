/** Program Description: To generate a HIHO policy with  initial Dwelling Type Tenant and then to Condo and Dwelling, single location single building with flood covered and assert values.
 *  Author			   : SM Netserv
 *  Date of Creation   : June 2018
 **/
package com.icat.epicenter.test.hiho.regression.NB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.ConfirmBindRequestPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EditAdditionalInterestInformationPage;
import com.icat.epicenter.pom.EditInspectionContactPage;
import com.icat.epicenter.pom.EditInsuredContactInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC010 extends AbstractNAHOTest {

	public TC010() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHONB/NBTCID10.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		DwellingPage dwellingPage = new DwellingPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ConfirmBindRequestPage confirmBindRequestPage = new ConfirmBindRequestPage();
		EditInsuredContactInfoPage editInsuredContactInfoPage = new EditInsuredContactInfoPage();
		EditAdditionalInterestInformationPage editAdditionalInterestInformationPage = new EditAdditionalInterestInformationPage();
		BindRequestSubmittedPage bindRequestSubmittedPage = new BindRequestSubmittedPage();
		OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EditInspectionContactPage editInspectionContactPage1 = new EditInspectionContactPage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		Map<String, String> testData = data.get(data_Value1);

		// New account
		Assertions.passTest("Home Page", "Page Navigated");
		homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", "New Account created successfully");

		// Entering Dwelling Details
		Assertions.passTest("Dwelling Page", "Dwelling Page loaded successfully");
		dwellingPage.enterDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		createQuotePage.enterDeductibles(testData);
		createQuotePage.addOptionalCoverageDetails(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");

		// Click on get a quote button
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		Assertions.passTest("Account Overview page", "Account Overview page is Displayed");

		// account overview page details
		accountOverviewPage.requestBind.waitTillVisibilityOfElement(60);
		testData = data.get(data_Value1);
		Assertions.verify(accountOverviewPage.quoteStatusHIHO.getData(), "Active", "Account Overview Page",
				"Quote Status :" + accountOverviewPage.quoteStatusHIHO.getData() + "is verified", false, false);
		Assertions.verify(accountOverviewPage.acntOverviewQuoteNumber.checkIfElementIsDisplayed(), true,
				"Account Overview Page",
				"Quote Number generated:" + accountOverviewPage.acntOverviewQuoteNumber.getData() + "is verified",
				false, false);
		Assertions.verify(accountOverviewPage.totalPremiumValue.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Total Premium and Fee Amount present", true, true);
		accountOverviewPage.editInsuredContactInfo.scrollToElement();
		accountOverviewPage.editInsuredContactInfo.click();
		editInsuredContactInfoPage.enterContactInfoDetails(testData);
		accountOverviewPage.editInspectionContact.waitTillVisibilityOfElement(60);
		accountOverviewPage.editInspectionContact.click();
		editInspectionContactPage1.cancel.waitTillVisibilityOfElement(30);
		editInspectionContactPage1.cancel.click();
		editInspectionContactPage1.cancel.waitTillInVisibilityOfElement(30);
		accountOverviewPage.editAdditionalIntersets.waitTillVisibilityOfElement(60);
		accountOverviewPage.editAdditionalIntersets.scrollToElement();
		accountOverviewPage.editAdditionalIntersets.click();
		editAdditionalInterestInformationPage.waitTime(3);
		editAdditionalInterestInformationPage.addAdditionalInterestHIHO(testData);
		editAdditionalInterestInformationPage.update.scrollToElement();
		editAdditionalInterestInformationPage.update.click();
		Assertions.verify(editAdditionalInterestInformationPage.aiSecondaryMartgageeError.checkIfElementIsDisplayed(),
				true, "Edit Additional Interest Information Page",
				"Error Message : " + editAdditionalInterestInformationPage.aiSecondaryMartgageeError.getData(), true,
				false);
		editAdditionalInterestInformationPage.cancel.scrollToElement();
		editAdditionalInterestInformationPage.cancel.click();
		editAdditionalInterestInformationPage.cancel.waitTillInVisibilityOfElement(60);
		String accountTotalPremium = accountOverviewPage.totalPremiumValue.getData();
		String accountQuoteNumber = accountOverviewPage.acntOverviewQuoteNumber.getData();
		accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
		accountOverviewPage.viewPrintFullQuoteLink.click();
		Assertions.verify(accountOverviewPage.viewPrintFullQuotePage.checkIfElementIsDisplayed(), true,
				"View / Print Full Quote Page", "Page Navigated", true, false);
		accountOverviewPage.goBackBtn.click();
		accountOverviewPage.emailQuoteLink.scrollToElement();
		accountOverviewPage.emailQuoteLink.click();
		Assertions.verify(accountOverviewPage.emailQuotePage.checkIfElementIsDisplayed(), true, "Email Quote Page",
				"Page Navigated", true, false);
		accountOverviewPage.scrollToBottomPage();
		accountOverviewPage.emailCancel.waitTillVisibilityOfElement(30);
		accountOverviewPage.emailCancel.click();
		accountOverviewPage.viewOrPrintRateTrace.scrollToElement();
		accountOverviewPage.viewOrPrintRateTrace.click();
		Assertions.verify(accountOverviewPage.viewPrintRateTracePage.checkIfElementIsDisplayed(), true,
				"View / Print Rate Trace Page", "Page Navigated", true, false);
		accountOverviewPage.backBtn.click();
		accountOverviewPage.clickOnRequestBind(testData, accountQuoteNumber);

		// request bind page details
		Assertions.passTest("Request Bind Page", "Request Bind Page loaded successfully");
		String newDate = testData.get("PolicyEffDate");
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(newDate));
		} catch (ParseException e) {
			Assertions.exceptionError("Error in setTime method", e.toString());
		}

		// Incrementing the date
		c.add(Calendar.YEAR, 1);
		String PolicyExpirationDate = sdf.format(c.getTime());

		String quoteNumber = requestBindPage.quoteNumber.getData();
		String quote = "(" + quoteNumber + ")";
		Assertions.verify(quote, accountQuoteNumber, "Request Bind Page",
				"Quote Number same as in Account Overview Page is verified", false, false);
		Assertions.verify(accountTotalPremium.contains(requestBindPage.quotePremium.getData()), true,
				"Request Bind Page", "Quote Premium same as in Account Overview Page is verified", false, false);
		Assertions.verify(requestBindPage.effectiveDate.getData(), testData.get("PolicyEffDate"), "Request Bind Page",
				"Effective Date is verified", false, false);
		Assertions.verify(requestBindPage.expirationDate.getData(), PolicyExpirationDate, "Request Bind Page",
				"Expiration Date is verified", false, false);
		Assertions.verify(requestBindPage.namedInsured.getData(), testData.get("InsuredName"), "Request Bind Page",
				"Insured Name is verified", false, false);
		Assertions.verify(requestBindPage.insuredEmail.getData(), testData.get("InsuredEmail"), "Request Bind Page",
				"Insured Email is verified", false, false);
		Assertions.verify(
				requestBindPage.insuredPhoneNoAreaCode.getData() + requestBindPage.insuredPhoneNoPrefix.getData()
						+ requestBindPage.insuredPhoneNoEnd.getData(),
				testData.get("InsuredPhoneNumAreaCode") + testData.get("InsuredPhoneNumPrefix")
						+ testData.get("InsuredPhoneNum"),
				"Request Bind Page", "Insured Phone Number is verified", false, false);
		Assertions.verify(requestBindPage.insuredCountry.getData(), testData.get("InsuredCountry"), "Request Bind Page",
				"Insured Country is verified", false, false);
		Assertions.verify(requestBindPage.mailingAddress.getData(),
				testData.get("InsuredAddr1") + ", " + testData.get("InsuredCity") + ", " + testData.get("InsuredState")
						+ " " + testData.get("InsuredZIP"),
				"Request Bind Page", "Mailing Address is verified", false, false);
		requestBindPage.fourPay.scrollToElement();
		requestBindPage.fourPay.click();
		testData = data.get(data_Value1);
		if (requestBindPage.inspectionName.checkIfElementIsPresent()
				&& requestBindPage.inspectionName.checkIfElementIsDisplayed()) {
			requestBindPage.addInspectionContact(testData);
		}
		requestBindPage.addAdditionalInterestEQHO(testData);
		requestBindPage.addContactInformation(testData);
		requestBindPage.submit.scrollToElement();
		requestBindPage.submit.click();
		confirmBindRequestPage.requestBind.waitTillVisibilityOfElement(60);
		confirmBindRequestPage.requestBind.click();
		confirmBindRequestPage.requestBind.waitTillInVisibilityOfElement(60);
		Assertions.verify(requestBindPage.additionalMortgageeError.checkIfElementIsDisplayed(), true,
				"Request Bind Page", "Error Message : " + requestBindPage.additionalMortgageeError.getData(), true,
				false);
		testData = data.get(data_Value2);
		requestBindPage.addAdditionalInterestEQHO(testData);
		requestBindPage.submit.scrollToElement();
		requestBindPage.submit.click();
		confirmBindRequestPage.requestBind.waitTillVisibilityOfElement(60);
		confirmBindRequestPage.requestBind.click();
		confirmBindRequestPage.requestBind.waitTillInVisibilityOfElement(60);
		Assertions.verify(requestBindPage.additionalMortgageeError.checkIfElementIsDisplayed(), true,
				"Request Bind Page", "Error Message : " + requestBindPage.additionalMortgageeError.getData(), true,
				false);
		testData = data.get(data_Value3);
		requestBindPage.addAdditionalInterestEQHO(testData);
		requestBindPage.submit.scrollToElement();
		requestBindPage.submit.click();
		Assertions.passTest("confirm Bind Request Page", "Page Navigated");
		confirmBindRequestPage.requestBind.waitTillVisibilityOfElement(60);
		confirmBindRequestPage.requestBind.click();
		confirmBindRequestPage.requestBind.waitTillInVisibilityOfElement(60);
		Assertions.passTest("confirm Bind Request Page", "Clicked on Request Bind");
		Assertions.passTest("Bind Request Submitted Page", "Page Navigated");
		if (requestBindPage.overrideEffectiveDate.checkIfElementIsPresent()
				&& requestBindPage.overrideEffectiveDate.checkIfElementIsDisplayed()) {
			requestBindPage.overrideEffectiveDate.select();
			requestBindPage.submit.waitTillVisibilityOfElement(60);
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();
			confirmBindRequestPage.requestBind.waitTillVisibilityOfElement(60);
			confirmBindRequestPage.requestBind.click();
			confirmBindRequestPage.requestBind.waitTillInVisibilityOfElement(60);
		}
		bindRequestSubmittedPage.clickOnHomepagebutton();

		// navigated to home page
		Assertions.passTest("Home Page", "Page Navigated");
		homePage.findAccountNamedInsured.scrollToElement();
		homePage.findFilterArrow.click();
		homePage.findOptionQuote.scrollToElement();
		homePage.findOptionQuote.click();
		homePage.quoteField.scrollToElement();
		homePage.quoteField.setData(quoteNumber);
		homePage.findButton.scrollToElement();
		homePage.findButton.click();
		Assertions.verify(accountOverviewPage.quoteStatusHIHO.getData(), "Submitted", "Account Overview Page",
				"Quote Status : " + accountOverviewPage.quoteStatusHIHO.getData() + " is verified", false, false);
		accountOverviewPage.unlockAccount.scrollToElement();
		accountOverviewPage.unlockAccount.click();
		Assertions.verify(accountOverviewPage.unlockMessage.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Verified " + accountOverviewPage.unlockMessage.getData() + " is displayed", true, false);
		accountOverviewPage.createAnotherQuote.scrollToElement();
		accountOverviewPage.createAnotherQuote.click();
		createQuotePage.previous.scrollToElement();
		createQuotePage.previous.click();

		// Clicking on Dwelling Link in Account Overview Page
		accountOverviewPage.dwellingName.scrollToElement();
		accountOverviewPage.dwellingName.click();
		accountOverviewPage.editDwelling.scrollToElement();
		accountOverviewPage.editDwelling.click();
		if (accountOverviewPage.yesButton.checkIfElementIsPresent()) {
			accountOverviewPage.yesButton.scrollToElement();
			accountOverviewPage.yesButton.click();
		}
		if (accountOverviewPage.quoteExpiredPopupMsg.checkIfElementIsPresent()) {
			accountOverviewPage.quoteExpiredPopupMsg.scrollToElement();
			accountOverviewPage.quoteExpiredPopupMsg.click();
			accountOverviewPage.quoteExpiredPopupMsg.waitTillInVisibilityOfElement(60);
		}
		Assertions.passTest("Dwelling Page", "Page Navigated");
		testData = data.get(data_Value2);
		dwellingPage.dwellingTypeArrow.waitTillVisibilityOfElement(60);
		dwellingPage.dwellingTypeArrow.scrollToElement();
		dwellingPage.dwellingTypeArrow.click();
		dwellingPage.dwellingTypeOption.formatDynamicPath(testData.get("L" + 1 + "D" + 1 + "-DwellingType")).click();
		if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
			dwellingPage.expiredQuotePopUp.scrollToElement();
			dwellingPage.continueWithUpdateBtn.scrollToElement();
			dwellingPage.continueWithUpdateBtn.click();
			dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
		}
		dwellingPage.enterDwellingValues(testData, 1, 1);
		dwellingPage.reviewDwelling();
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();

		// create quote page details
		createQuotePage.enterDeductibles(testData);
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		Assertions.passTest("Create Quote Page", "Clicked on get a quote button successfully");
		Assertions.verify(createQuotePage.quote1Status.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Quote 1 Status shown as 'Submitted'", true, false);

		// navigate to account overview page
		accountOverviewPage.deleteQuote.scrollToElement();
		accountOverviewPage.deleteQuote.click();
		accountOverviewPage.yesDeletePopup.scrollToElement();
		accountOverviewPage.yesDeletePopup.click();
		Assertions.verify(createQuotePage.quote2StatusDelete.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Quote 2 Status shown as 'Deleted'", true, false);
		accountOverviewPage.createAnotherQuote.scrollToElement();
		accountOverviewPage.createAnotherQuote.click();
		createQuotePage.previous.scrollToElement();
		createQuotePage.previous.click();

		// Clicking on Dwelling Link in Account Overview Page
		accountOverviewPage.dwellingName.scrollToElement();
		accountOverviewPage.dwellingName.click();
		accountOverviewPage.editDwelling.scrollToElement();
		accountOverviewPage.editDwelling.click();
		Assertions.passTest("Dwelling Page", "Page Navigated");
		testData = data.get(data_Value3);
		dwellingPage.dwellingTypeArrow.waitTillVisibilityOfElement(60);
		dwellingPage.dwellingTypeArrow.scrollToElement();
		dwellingPage.dwellingTypeArrow.click();
		dwellingPage.dwellingTypeOption.formatDynamicPath(testData.get("L" + 1 + "D" + 1 + "-DwellingType")).click();
		if (dwellingPage.dwellingCharacteristicsLink.checkIfElementIsPresent()
				&& dwellingPage.dwellingCharacteristicsLink.checkIfElementIsDisplayed()) {
			dwellingPage.dwellingCharacteristicsLink.scrollToElement();
			dwellingPage.dwellingCharacteristicsLink.click();
		}
		dwellingPage.yearBuilt.setData(testData.get("L" + 1 + "D" + 1 + "-DwellingYearBuilt"));
		dwellingPage.waitTime(3);
		dwellingPage.reviewDwelling();
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();

		// navigate to create quote page
		createQuotePage.enterDeductibles(testData);
		createQuotePage.addOptionalCoverageDetails(testData);
		createQuotePage.getAQuote.waitTillVisibilityOfElement(30);
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		if (createQuotePage.pageName.getData().contains("Dwelling values are below minimum cost")) {
			createQuotePage.override.waitTillVisibilityOfElement(30);
			createQuotePage.override.scrollToElement();
			createQuotePage.override.click();
			createQuotePage.override.waitTillInVisibilityOfElement(30);
		}

		// Delete the following code after the issue is fixed
		if (createQuotePage.continueButton.checkIfElementIsPresent()
				&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}

		Assertions.verify(accountOverviewPage.quote3Status.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Quote 3 Status shown as 'Active'", true, false);
		Assertions.verify(accountOverviewPage.quote1Status.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Quote 1 Status shown as 'Submitted'", true, false);
		Assertions.verify(accountOverviewPage.quote2StatusActive.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Quote 2 Status shown as 'Deleted'", true, false);
		accountOverviewPage.overridePremiumLink.waitTillVisibilityOfElement(60);
		accountOverviewPage.overridePremiumLink.scrollToElement();
		accountOverviewPage.overridePremiumLink.click();
		Assertions.passTest("Override Premium and Fees Page", "Page Navigated");
		overridePremiumAndFeesPage.totalInspectionFee.setData("100");
		overridePremiumAndFeesPage.policyFee.setData("100");
		overridePremiumAndFeesPage.feeOverrideJustification.setData("Test");
		overridePremiumAndFeesPage.overrideFeesButton.scrollToElement();
		overridePremiumAndFeesPage.overrideFeesButton.click();
		Assertions.passTest("Account Overiview Page", "Page Navigated");
		Assertions.verify(accountOverviewPage.totalPremiumAmount.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Premium is : " + accountOverviewPage.totalPremiumAmount.getData(), true,
				false);
		Assertions.verify(accountOverviewPage.totalFeeAmount.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Updated Total Fee is : " + accountOverviewPage.totalFeeAmount.getData(), true, false);
		accountOverviewPage.noteBarFeeName.waitTillVisibilityOfElement(60);
		accountOverviewPage.noteBarFeeName.waitTillPresenceOfElement(60);
		Assertions.verify(accountOverviewPage.noteBarFeeName.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Updated Notebar comment is : " + accountOverviewPage.noteBarFeeName.getData(), true, true);

		// navigate to home page
		homePage.scrollToTopPage();
		homePage.goToHomepage.click();
		homePage.searchQuote(quoteNumber);
		Assertions.passTest("Home Page", "Searched Quote successfullly");
		accountOverviewPage.openReferral.scrollToElement();
		accountOverviewPage.openReferral.click();
		Assertions.passTest("Account Overview Page", "Clicked on Open Referral Link successfullly");
		Assertions.passTest("Referral Page", "Referral Page openned successfullly");
		referralPage.clickOnApprove();
		approveDeclineQuotePage.clickOnApprove();
		if (requestBindPage.overrideEffectiveDate.checkIfElementIsPresent()
				&& requestBindPage.overrideEffectiveDate.checkIfElementIsDisplayed()) {
			requestBindPage.overrideEffectiveDate.select();
			approveDeclineQuotePage.approveButton.waitTillVisibilityOfElement(60);
			approveDeclineQuotePage.approveButton.scrollToElement();
			approveDeclineQuotePage.approveButton.click();
		}

		// Getting Policy NUmber
		String policyNumber = policySummaryPage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "Page Navigated");
		Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("NB_Regression_TC010", "Executed Successfully");

	}
}
