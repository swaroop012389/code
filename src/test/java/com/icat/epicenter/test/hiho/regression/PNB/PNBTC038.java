/** Program Description: Create renewal requote and delete the actual renewal quote
 *  Author			   : Automation Team
 *  Date of Creation   : Oct 2018
**/
package com.icat.epicenter.test.hiho.regression.PNB;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC038 extends AbstractNAHOTest {

	public PNBTC038() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID38.xls";
	}

	public HomePage homePage;
	public DwellingPage dwellingPage;
	public LocationPage locationPage;
	public BuildingUnderMinimumCostPage dwellingCostPage;
	public CreateQuotePage createQuotePage;
	public AccountOverviewPage accountOverviewPage;
	public RequestBindPage requestBindPage;
	public PolicySummaryPage policySummaryPage;
	public LoginPage loginPage;
	public ReferQuotePage referQuotepage;
	public ReferralPage referralPage;
	public ApproveDeclineQuotePage approveDeclineQuotePage;
	public BasePageControl basePage;
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
		homePage = new HomePage();
		locationPage = new LocationPage();
		accountOverviewPage = new AccountOverviewPage();
		requestBindPage = new RequestBindPage();
		policySummaryPage = new PolicySummaryPage();
		loginPage = new LoginPage();
		dwellingCostPage = new BuildingUnderMinimumCostPage();
		referQuotepage = new ReferQuotePage();
		referralPage = new ReferralPage();
		approveDeclineQuotePage = new ApproveDeclineQuotePage();
		testData = data.get(data_Value1);

		// New account
		Assertions.passTest("Home Page", PAGE_NAVIGATED);
		basePage = homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", VALUES_ENTERED);
		dwellingPage = (DwellingPage) basePage;

		// Entering Location 1 Dwelling 1 Details
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		dwellingPage = new DwellingPage();
		dwellingPage.enterDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", "Location 1 Dwelling 1 -1 " + VALUES_ENTERED);

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		createQuotePage = new CreateQuotePage();
		createQuotePage.enterDeductibles(testData);
		testData = data.get(data_Value2);

		Assertions.passTest("Create Quote Page", "Quote details entered successfully");
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();

		// Delete the following code after the issue is fixed
		if (createQuotePage.continueButton.checkIfElementIsPresent()
				&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

		accountOverviewPage.clickOnRequestBind(setUpData, quoteNumber);
		testData = data.get(data_Value1);
		Assertions.passTest("Request Bind Page", "Request Bind page loaded successfully");
		requestBindPage.enterBindDetailsNAHO(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");
		policyNumber = policySummaryPage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "Policy Number: " + policyNumber);
		policySummaryPage.renewPolicy.scrollToElement();
		policySummaryPage.renewPolicy.click();
		if (policySummaryPage.pageName.getData().contains("Transfer")) {
			policySummaryPage.transferContinue.scrollToElement();
			policySummaryPage.transferContinue.click();
		}
		accountOverviewPage.quoteNo1Holder.scrollToElement();
		quoteNumber = accountOverviewPage.quoteNo1Holder.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Renewal Quote generated by system: " + quoteNumber);
		Assertions.passTest("Account Overview Page", PAGE_NAVIGATED);
		accountOverviewPage.editDeductibleAndLimits.scrollToElement();
		accountOverviewPage.editDeductibleAndLimits.click();
		accountOverviewPage.editDeductibleAndLimits.waitTillInVisibilityOfElement(60);
		Assertions.passTest("Account Overview Page", "Clicked on Create Another Quote");
		BaseWebElementControl moldCleanup = new BaseWebElementControl(
				By.xpath("//label[contains(text(),'Mold Clean Up')]//following-sibling::span//span[contains(text(),'"
						+ testData.get("Mold") + "')]"));
		Assertions.verify(moldCleanup.getData(), testData.get("Mold"), "Create Quote Page",
				"Mold Clean Up default value" + ext_rpt_msg, false, false);
		Assertions.verify(moldCleanup.getAttrributeValue("unselectable"), "on", "Create Quote Page",
				"Mold Clean Up is unchangeable" + ext_rpt_msg, false, false);
		Assertions.passTest("Create Quote Page", PAGE_NAVIGATED);
		testData = data.get(data_Value2);
		createQuotePage.enterDeductibles(testData);
		Assertions.passTest("Create Quote Page", VALUES_ENTERED);
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();

		// Delete the following code after the issue is fixed
		if (createQuotePage.continueButton.checkIfElementIsPresent()
				&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}

		accountOverviewPage.quoteNo1Holder.scrollToElement();
		quoteNumber = accountOverviewPage.quoteNo1Holder.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "New Renewal Quote Number: " + quoteNumber);
		accountOverviewPage.deleteRenewalButton.scrollToElement();
		accountOverviewPage.deleteRenewalButton.click();
		accountOverviewPage.deleteRenewalMsg.waitTillPresenceOfElement(60);
		accountOverviewPage.deleteRenewalMsg.waitTillVisibilityOfElement(60);
		Assertions.verify(
				accountOverviewPage.deleteRenewalMsg.checkIfElementIsPresent()
						&& accountOverviewPage.deleteRenewalMsg.checkIfElementIsDisplayed(),
				true, "Account Overview Page",
				"Are you sure you want to delete this Renewal Account. All quotes and this account will be permanently deleted? message"
						+ ext_rpt_msg,
				false, false);
		accountOverviewPage.yesDeletePopup.click();
		accountOverviewPage.yesDeletePopup.waitTillInVisibilityOfElement(60);
		Assertions.verify(
				accountOverviewPage.quoteDeleteMessage.checkIfElementIsPresent()
						&& accountOverviewPage.quoteDeleteMessage.checkIfElementIsDisplayed(),
				true, "Policy Summary Page", accountOverviewPage.quoteDeleteMessage.getData() + ext_rpt_msg, false,
				false);
		policySummaryPage.renewPolicy.scrollToElement();
		policySummaryPage.renewPolicy.click();
		if (policySummaryPage.pageName.getData().contains("Transfer")) {
			policySummaryPage.transferContinue.scrollToElement();
			policySummaryPage.transferContinue.click();
		}
		accountOverviewPage.quoteNo1Holder.scrollToElement();
		quoteNumber = accountOverviewPage.quoteNo1Holder.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Renewal Quote generated by system: " + quoteNumber);
		accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
		accountOverviewPage.releaseRenewalToProducerButton.click();
		accountOverviewPage.requestBind.scrollToElement();
		accountOverviewPage.requestBind.click();

		// Entering data in Request Bind Page
		Assertions.passTest("Request Bind Page", "Page Navigated");
		testData = data.get(data_Value1);
		requestBindPage.addContactInformation(testData);
		Assertions.passTest("Request Bind Page", "Entered Values successfully");
		requestBindPage.submit.scrollToElement();
		requestBindPage.submit.click();
		requestBindPage.requestBind.waitTillVisibilityOfElement(60);
		requestBindPage.requestBind.scrollToElement();
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

		// Getting Renewal Policy Number
		Assertions.passTest("Policy Summary Page", "Page Navigated");
		Assertions.passTest("Policy Summary Page", "Renewal Policy Number :" + policySummaryPage.getPolicynumber());
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("PNB_Regression_TC038", "Executed Successfully");

	}
}
