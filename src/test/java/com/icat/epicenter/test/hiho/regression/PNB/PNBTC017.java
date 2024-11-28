/** Program Description: Validate details on renewal quote
 *  Author			   : SM Netserv
 *  Date of Creation   : Sep 2018
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
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC017 extends AbstractNAHOTest {

	public PNBTC017() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID17.xls";
	}

	public HomePage homePage;
	public DwellingPage dwellingPage;
	public LocationPage locationPage;
	public CreateQuotePage createQuotePage;
	public AccountOverviewPage accountOverviewPage;
	public RequestBindPage requestBindPage;
	public PolicySummaryPage policySummaryPage;
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
	static int data_Value3 = 2;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		homePage = new HomePage();
		locationPage = new LocationPage();
		createQuotePage = new CreateQuotePage();
		accountOverviewPage = new AccountOverviewPage();
		requestBindPage = new RequestBindPage();
		policySummaryPage = new PolicySummaryPage();
		referralPage = new ReferralPage();
		approveDeclineQuotePage = new ApproveDeclineQuotePage();
		testData = data.get(data_Value1);

		// NB Test case 16 is used to generate the policy
		// New account
		Assertions.passTest("Home Page", PAGE_NAVIGATED);
		basePage = homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", VALUES_ENTERED);
		dwellingPage = (DwellingPage) basePage;

		// Entering Location Details
		locationPage = new LocationPage();
		Assertions.passTest("Location Page", "Location Page loaded successfully");
		locationPage.enterLocationDetailsHIHO(testData);
		Assertions.passTest("Location Page", "Location details entered successfully");

		// Entering Location 1 Dwelling 1 Details
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		dwellingPage = new DwellingPage();
		dwellingPage.enterDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", "Location 1 Dwelling 1 -1 " + VALUES_ENTERED);

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		createQuotePage.enterDeductibles(testData);
		testData = data.get(data_Value2);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");
		createQuotePage.loading.waitTillInVisibilityOfElement(60);
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		if (createQuotePage.pageName.getData().contains("Dwelling values")) {
			createQuotePage.override.scrollToElement();
			createQuotePage.override.click();
			createQuotePage.override.waitTillInVisibilityOfElement(60);
		}
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

		accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

		// Enter Bind Details
		testData = data.get(data_Value1);
		Assertions.passTest("Request Bind Page", "Request Bind page loaded successfully");
		requestBindPage.enterBindDetailsNAHO(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");
		policyNumber = policySummaryPage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "Policy Number: " + policySummaryPage.getPolicynumber());
		policySummaryPage.renewPolicy.scrollToElement();
		policySummaryPage.renewPolicy.click();
		Assertions.passTest("Account Overview Page", "Account Overview page loaded successfully");
		if (!accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsPresent()) {
			String quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
			homePage.goToHomepage.click();
			referralPage = homePage.searchReferral(quoteNumber);
			referralPage.clickOnApprove();
			if (approveDeclineQuotePage.pageName.getData().contains("Approve/Decline Quote")) {
				approveDeclineQuotePage.clickOnApprove();
			}
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.passTest("Home Page", "Page Navigated");

			// Find the policy by entering policy Number
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findOptionQuote.scrollToElement();
			homePage.findOptionQuote.click();
			homePage.quoteField.scrollToElement();
			homePage.quoteField.setData(quoteNumber);
			homePage.quoteSearchBtn.scrollToElement();
			homePage.quoteSearchBtn.click();
			Assertions.passTest("Home Page", "Values Updated");
		}
		accountOverviewPage.editDeductibleAndLimits.scrollToElement();
		accountOverviewPage.editDeductibleAndLimits.click();
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		BaseWebElementControl moldCleanup = new BaseWebElementControl(
				By.xpath("//label[contains(text(),'Mold Clean Up')]//following-sibling::span//span[contains(text(),'"
						+ testData.get("Mold") + "')]"));
		Assertions.verify(moldCleanup.getData(), testData.get("Mold"), "Create Quote Page",
				"Mold Clean Up default value" + ext_rpt_msg, false, false);
		Assertions.verify(moldCleanup.getAttrributeValue("unselectable"), "on", "Create Quote Page",
				"Mold Clean Up is unchangeable" + ext_rpt_msg, false, false);
		Assertions.passTest("PNB_Regression_TC017", "Executed Successfully");
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
	}
}