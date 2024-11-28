/** Program Description: Create another quote in renewal page and bind
 *  Author			   : SM Netserv
 *  Date of Creation   : Oct 2018
**/
package com.icat.epicenter.test.hiho.regression.PNB;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.TextFieldControl;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EditAdditionalInterestInformationPage;
import com.icat.epicenter.pom.EditPaymentPlanPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RenewalIndicatorsPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.RequestCancellationPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC041 extends AbstractNAHOTest {

	public PNBTC041() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID41.xls";
	}

	public HomePage homePage;
	public LocationPage locationPage;
	public DwellingPage dwellingPage;
	public BuildingUnderMinimumCostPage dwellingCostPage;
	public CreateQuotePage createQuotePage;
	public AccountOverviewPage accountOverviewPage;
	public RequestBindPage requestBindPage;
	public PolicySummaryPage policySummaryPage;
	public LoginPage loginPage;
	public EditPaymentPlanPage editPaymentPlanPage;
	public EditAdditionalInterestInformationPage editAdditionalInterestPage;
	public BasePageControl basePage;
	public ReferralPage referralPage;
	RequestCancellationPage requestCancellationPage;
	public Map<String, String> testData;
	String quoteNumber;
	String policyNumber;
	static final String ext_rpt_msg = " is verified";
	static final String PAGE_NAVIGATED = "Page Navigated";
	static final String VALUES_UPDATED = "Values Updated";
	static final String VALUES_VERIFIED = "Values Verified";
	static final String VALUES_ENTERED = "Values Entered successfully";
	static int data_Value1 = 0;
	static int data_Value2 = 1;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		locationPage = new LocationPage();
		createQuotePage = new CreateQuotePage();
		accountOverviewPage = new AccountOverviewPage();
		requestBindPage = new RequestBindPage();
		policySummaryPage = new PolicySummaryPage();
		loginPage = new LoginPage();
		dwellingCostPage = new BuildingUnderMinimumCostPage();
		editAdditionalInterestPage = new EditAdditionalInterestInformationPage();
		editPaymentPlanPage = new EditPaymentPlanPage();
		referralPage = new ReferralPage();
		requestCancellationPage = new RequestCancellationPage();
		RenewalIndicatorsPage renewalIndicatorsPage = new RenewalIndicatorsPage();
		testData = data.get(data_Value1);

		// Login to producer account
		Assertions.passTest("Home Page", "Logged in as Producer Successfully");

		// New account
		Assertions.passTest("Home Page", PAGE_NAVIGATED);
		homePage = new HomePage();

		// asserting personal login details
		homePage.enterPersonalLoginDetails();
		Assertions.passTest("Home Page", "Page Navigated");

		basePage = homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", VALUES_ENTERED);
		dwellingPage = (DwellingPage) basePage;

		// Entering Location 1 Dwelling 1 Details
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		dwellingPage = new DwellingPage();
		dwellingPage.addDwellingDetails(testData, 1, 1);
		dwellingPage.addRoofDetails(testData, 1, 1);
		dwellingPage.enterDwellingValues(testData, 1, 1);
		Assertions.passTest("Dwelling Page", "Location 1 Dwelling 1 -1 " + VALUES_ENTERED);
		dwellingPage.reviewDwelling.scrollToElement();
		dwellingPage.reviewDwelling.click();
		dwellingCostPage.bringUpToCost.scrollToElement();
		dwellingCostPage.bringUpToCost.click();

		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		createQuotePage.enterDeductibles(testData);
		testData = data.get(data_Value2);
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");

		// Getting quote number
		String quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Quote Number : " + quoteNumber);
		accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		testData = data.get(data_Value1);
		Assertions.passTest("Request Bind Page", "Request Bind page loaded successfully");

		// Enter bind details
		requestBindPage.enterPolicyDetailsNAHO(testData);
		requestBindPage.enterPaymentInformationNAHO(testData);
		requestBindPage.addInspectionContact(testData);
		requestBindPage.addContactInformation(testData);
		requestBindPage.addAdditionalInterestEQHO(testData);
		if (requestBindPage.infoSymbol.checkIfElementIsPresent()) {
			requestBindPage.infoSymbol.waitTillButtonIsClickable(30);
			requestBindPage.infoSymbol.waitTillVisibilityOfElement(60);
			requestBindPage.infoSymbol.click();
			requestBindPage.infoSymbol.waitTillButtonIsClickable(30);
			requestBindPage.infoSymbol.click();
		}
		requestBindPage.waitTime(5);
		requestBindPage.submit.scrollToElement();
		requestBindPage.submit.click();
		requestBindPage.requestBind.waitTillVisibilityOfElement(60);
		requestBindPage.requestBind.scrollToElement();
		requestBindPage.requestBind.click();
		requestBindPage.requestBind.waitTillInVisibilityOfElement(60);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");

		// Getting policy number
		policyNumber = policySummaryPage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "Policy number " + policyNumber);

		// logout as producer
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		Assertions.passTest("Login Page", "Signed Out as Producer Successfully");

		// login as USM
		loginPage.refreshPage();
		loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
		Assertions.passTest("Home Page", "Logged in as USM Successfully");

		// Find the policy by entering policy Number
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		homePage.findAccountNamedInsured.scrollToElement();
		homePage.findFilterArrow.click();
		homePage.findFilterPolicyOption.click();
		homePage.policyNumber.waitTillVisibilityOfElement(60);
		homePage.policyNumber.setData(policyNumber);
		homePage.policyNumber.tab();
		homePage.findPolicyButton.click();

		// Click on Renew Policy Hyperlink
		policySummaryPage.renewPolicy.scrollToElement();
		policySummaryPage.renewPolicy.click();
		if (policySummaryPage.pageName.getData().contains("Transfer")) {
			policySummaryPage.transferContinue.scrollToElement();
			policySummaryPage.transferContinue.click();
		}

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
		renewalIndicatorsPage.nonRenewalLegalNoticeWording.scrollToElement();
		renewalIndicatorsPage.nonRenewalLegalNoticeWording.appendData(testData.get("LegalNoticeWordings"));

		// click on update
		renewalIndicatorsPage.updateButton.scrollToElement();
		renewalIndicatorsPage.updateButton.click();
		Assertions.passTest("Renewal Indicators Page", "Clicked on Update Button");
		Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
				"Policy Summary Page Loaded successfully", false, false);

		// Verifying the View Renewal and Renewal link not available on policy Summary
		// Page
		Assertions.addInfo("Policy Summary Page", "View Active Renewal Link should not diasplayed");
		Assertions.verify(!policySummaryPage.viewActiveRenewal.checkIfElementIsPresent(), true, "Policy Summary Page",
				"View Active Renewal Link not diasplayed", false, false);

		Assertions.addInfo("Policy Summary Page", "Renewal Policy Link should not diasplayed");
		Assertions.verify(!policySummaryPage.renewPolicy.checkIfElementIsPresent(), true, "Policy Summary Page",
				"Renewal Policy Link not diasplayed", false, false);

		Assertions.verify(policySummaryPage.renewalMessage.getData().contains(testData.get("PolicyEffDateForNRNL")),
				true, "Policy Summary Page",
				"The Non-Renewal Notice for this policy will be sent by" + testData.get("PolicyEffDateForNRNL"), false,
				false);

		// click on renewal indicators link
		policySummaryPage.renewalIndicators.scrollToElement();
		policySummaryPage.renewalIndicators.click();
		Assertions.passTest("Policy Summary Page", "Clicked on Renewal Indicators link");

		// select non renewal checkbox
		Assertions.verify(renewalIndicatorsPage.updateButton.checkIfElementIsDisplayed(), true,
				"Renewal Indicators Page", "Renewal Indicators Page loaded successfully", false, false);
		renewalIndicatorsPage.nonRenewal.scrollToElement();
		renewalIndicatorsPage.nonRenewal.deSelect();
		Assertions.passTest("Renewal Indicators Page", "De-Selected the Non Renewal Checkbox");

		// click on update
		renewalIndicatorsPage.updateButton.scrollToElement();
		renewalIndicatorsPage.updateButton.click();

		// Click on Renew Policy Hyperlink
		policySummaryPage.renewPolicy.scrollToElement();
		policySummaryPage.renewPolicy.click();
		if (policySummaryPage.pageName.getData().contains("Transfer")) {
			policySummaryPage.transferContinue.scrollToElement();
			policySummaryPage.transferContinue.click();
		}
		accountOverviewPage.quoteNo1Holder.scrollToElement();
		quoteNumber = accountOverviewPage.quoteNo1Holder.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Renewal Quote Number : " + quoteNumber);
		accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
		accountOverviewPage.releaseRenewalToProducerButton.click();

		// logout as USM
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		Assertions.passTest("Login Page", "Signed Out as USM Successfully");

		// Login to producer account
		loginPage.refreshPage();
		loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
		Assertions.passTest("Home Page", "Logged in as Producer Successfully");

		// asserting personal login details
		homePage.enterPersonalLoginDetails();
		Assertions.passTest("Home Page", "Page Navigated");

		// find the account by entering quote number in producer home page
		homePage.producerQuoteOption.scrollToElement();
		homePage.producerQuoteOption.click();
		homePage.quoteSearchTextbox.scrollToElement();
		homePage.quoteSearchTextbox.setData(quoteNumber);
		homePage.quoteSearchBtn.scrollToElement();
		homePage.quoteSearchBtn.click();
		homePage.quoteLinkButton.waitTillVisibilityOfElement(30);
		homePage.quoteLinkButton.scrollToElement();
		homePage.quoteLinkButton.click();
		accountOverviewPage.editAdditionalIntersets.waitTillVisibilityOfElement(60);
		accountOverviewPage.editAdditionalIntersets.scrollToElement();
		accountOverviewPage.editAdditionalIntersets.click();
		Assertions.passTest("Edit Additional Interest Information Page",
				"Screen to display Additional Interest Details displayed" + ext_rpt_msg);
		testData = data.get(data_Value2);
		editAdditionalInterestPage.waitTime(3);
		editAdditionalInterestPage.addAdditionalInterestNAHO(testData);
		editAdditionalInterestPage.update.scrollToElement();
		editAdditionalInterestPage.update.click();
		editAdditionalInterestPage.update.waitTillInVisibilityOfElement(60);
		accountOverviewPage.editPaymentPlan.waitTillButtonIsClickable(60);
		accountOverviewPage.editPaymentPlan.waitTillVisibilityOfElement(60);
		accountOverviewPage.editPaymentPlan.scrollToElement();
		accountOverviewPage.editPaymentPlan.click();
		editPaymentPlanPage.mortgageePay.waitTillVisibilityOfElement(30);
		Assertions.verify(editPaymentPlanPage.mortgageePay.checkIfElementIsDisplayed(), true, "Edit Payment Plan Page",
				"Mortgagee Pay option displayed in Payment Plan" + ext_rpt_msg, false, false);
		editPaymentPlanPage.cancel.scrollToElement();
		editPaymentPlanPage.cancel.click();
		editPaymentPlanPage.cancel.waitTillInVisibilityOfElement(30);

		// logout as producer
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		Assertions.passTest("Login Page", "Signed Out as Producer Successfully");

		// login as USM
		loginPage.refreshPage();
		loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
		Assertions.passTest("Home Page", "Logged in as USM Successfully");

		// find the account by entering quote number
		homePage.findFilterArrow.scrollToElement();
		homePage.findFilterArrow.click();
		homePage.findOptionQuote.click();
		homePage.quoteField.setData(quoteNumber);
		homePage.findButton.scrollToElement();
		homePage.findButton.click();
		Assertions.passTest("Account Overview Page", "Click on Request Bind");
		accountOverviewPage.requestBind.scrollToElement();
		accountOverviewPage.requestBind.click();
		Assertions.passTest("Request Bind Page", PAGE_NAVIGATED);
		testData = data.get(data_Value2);

		// verify additional interests
		for (int i = 1; i < 4; i++) {
			if (!testData.get(i + "-AIType").equals("")) {
				BaseWebElementControl aiType = new BaseWebElementControl(By.xpath("//div[div[div[input[@value='"
						+ testData.get(i + "-AIName") + "']]]]//span[contains(@id,'typeSelectBoxItText')]"));
				Assertions.verify(aiType.getData(), testData.get(i + "-AIType"), "Request Bind Page",
						"Additional Interest Type" + ext_rpt_msg, false, false);
				BaseWebElementControl aILoanNumber = new BaseWebElementControl(By.xpath("//div[div[div[input[@value='"
						+ testData.get(i + "-AIName") + "']]]]//input[contains(@id,'loanNumber')]"));
				TextFieldControl aIName = new TextFieldControl(By.xpath("//div[div[div[input[@value='"
						+ testData.get(i + "-AIName") + "']]]]//input[contains(@id,'name')]"));
				Assertions.verify(aIName.getData(), testData.get(i + "-AIName"), "Request Bind Page",
						"Additional Interest Name " + ext_rpt_msg, false, false);
				if (!testData.get(i + "-AILoanNumber").equals("")) {
					Assertions.verify(aILoanNumber.getAttrributeValue("Value"), testData.get(i + "-AILoanNumber"),
							"Request Bind Page", "Additional Interest Loan Number " + ext_rpt_msg, false, false);
				}
				if (testData.get(i + "-AIType").equals("Mortgagee")) {
					BaseWebElementControl aiRank = new BaseWebElementControl(By.xpath("//div[div[div[input[@value='"
							+ testData.get(i + "-AIName") + "']]]]//span[contains(@id,'rankSelectBoxItText')]"));
					Assertions.verify(aiRank.getData(), testData.get(i + "-AIRank"), "Request Bind Page",
							"Additional Interest Rank " + ext_rpt_msg, false, false);
				}
				Assertions.verify(requestBindPage.aIAddressLine1.getAttrributeValue("Value"),
						testData.get(i + "-AIAddr1"), "Request Bind Page",
						"Additional Interest Address Line 1 " + ext_rpt_msg, false, false);
				Assertions.verify(requestBindPage.aICity.getData(), testData.get(i + "-AICity"), "Request Bind Page",
						"Additional Interest City " + ext_rpt_msg, false, false);
				Assertions.verify(requestBindPage.aIState.getData(), testData.get(i + "-AIState"), "Request Bind Page",
						"Additional Interest State " + ext_rpt_msg, false, false);
				if (requestBindPage.aIZipCode1.checkIfElementIsPresent()
						&& requestBindPage.aIZipCode1.checkIfElementIsDisplayed()) {
					Assertions.verify(requestBindPage.aIZipCode1.getData(), testData.get(i + "-AIZIP"),
							"Request Bind Page", "Additional Interest Zip Code " + ext_rpt_msg, false, false);
				} else {
					Assertions.verify(requestBindPage.aiZipCodeQ3.formatDynamicPath(0).getData(),
							testData.get(i + "-AIZIP"), "Request Bind Page",
							"Additional Interest Zip Code " + ext_rpt_msg, false, false);
				}

			}
		}
		testData = data.get(data_Value1);
		Assertions.passTest("Request Bind Page", "Additional Interests added" + ext_rpt_msg);
		requestBindPage.addContactInformation(testData);
		Assertions.passTest("Request Bind Page", VALUES_ENTERED);
		requestBindPage.submit.click();
		requestBindPage.requestBind.waitTillVisibilityOfElement(60);
		requestBindPage.requestBind.click();
		requestBindPage.requestBind.waitTillInVisibilityOfElement(60);

		// Override Effective date in Request Bind Page
		if (requestBindPage.overrideEffectiveDate.checkIfElementIsPresent()
				&& requestBindPage.overrideEffectiveDate.checkIfElementIsDisplayed()) {
			requestBindPage.overrideEffectiveDate.scrollToElement();
			requestBindPage.overrideEffectiveDate.select();
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();
			requestBindPage.requestBind.waitTillVisibilityOfElement(60);
			requestBindPage.requestBind.scrollToElement();
			requestBindPage.requestBind.click();
			requestBindPage.requestBind.waitTillInVisibilityOfElement(60);
		}
		Assertions.passTest("Policy Summary Page", PAGE_NAVIGATED);
		String renewalPolicyNumber = policySummaryPage.policyNumber.getData();
		Assertions.passTest("Policy Summary Page", "Renewal Policy Number :" + policySummaryPage.getPolicynumber());
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();

		// Login as Producer
		loginPage.waitTime(2);
		loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));

		// asserting personal login details
		homePage.enterPersonalLoginDetails();
		Assertions.passTest("Home Page", "Page Navigated");

		// search for policy
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		homePage.searchPolicyByProducer(renewalPolicyNumber);

		// Asserting request cancellation
		Assertions.verify(
				policySummaryPage.requestCancellationLink.checkIfElementIsPresent()
						&& policySummaryPage.requestCancellationLink.checkIfElementIsDisplayed(),
				true, "Policy Summary Page", policySummaryPage.requestCancellationLink.getData() + " link is displayed",
				false, false);
		policySummaryPage.requestCancellationLink.scrollToElement();
		policySummaryPage.requestCancellationLink.click();

		// asserting request cancellation page
		Assertions.verify(requestCancellationPage.insuredName.checkIfElementIsDisplayed(), true,
				"Request Cancellation Page", "Request Cancellation Page loaded successfully", false, false);

		// asserting presence of new effective date field
		for (int i = 1; i <= 4; i++) {
			testData = data.get(i);
			requestCancellationPage.cancellationReasonArrow.scrollToElement();
			requestCancellationPage.cancellationReasonArrow.click();
			requestCancellationPage.cancellationReasonOption.formatDynamicPath(testData.get("CancellationReason"))
					.scrollToElement();
			requestCancellationPage.cancellationReasonOption.formatDynamicPath(testData.get("CancellationReason"))
					.click();
			if (i != 4) {
				Assertions.verify(
						requestCancellationPage.newEffectiveDate.checkIfElementIsPresent()
								&& requestCancellationPage.newEffectiveDate.checkIfElementIsDisplayed(),
						false, "Request Cancellation Page",
						"New Effective Date field is not available for cancellation reason "
								+ requestCancellationPage.cancellationReasonData.getData(),
						false, false);
			} else {
				Assertions.verify(
						requestCancellationPage.newEffectiveDate.checkIfElementIsPresent()
								&& requestCancellationPage.newEffectiveDate.checkIfElementIsDisplayed(),
						true, "Request Cancellation Page",
						"New Effective Date field is available for cancellation reason "
								+ requestCancellationPage.cancellationReasonData.getData(),
						false, false);
			}
		}
		testData = data.get(data_Value1);
		// Assert verbage
		Assertions.verify(requestCancellationPage.addDocumentVerbiage.checkIfElementIsDisplayed(), true,
				"Request Cancellation Page", requestCancellationPage.addDocumentVerbiage.getData() + " is displayed",
				false, false);

		// Enter cancellation request details
		requestCancellationPage.enterRequestCancellationDetails(testData);
		Assertions.passTest("Request Cancellation Page", "Details entered successfully");

		// Asserting the request message
		Assertions.verify(requestCancellationPage.cancellationRequestMsg.checkIfElementIsDisplayed(), true,
				"Request Cancellation Page", requestCancellationPage.cancellationRequestMsg.getData() + " is displayed",
				false, false);

		Assertions.passTest("PNB_Regression_TC041", "Executed Successfully");

	}
}