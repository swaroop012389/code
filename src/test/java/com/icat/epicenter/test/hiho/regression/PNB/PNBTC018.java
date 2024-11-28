/** Program Description: To check whether the previous NB discount is applied to the HIHO New policy when user changes the dwelling address
* 						 and dwelling moves to another location during renewal requote
*  Author			   : Aishwarya Rangasamy
*  Date of Creation    : 10/10/2018
**/
package com.icat.epicenter.test.hiho.regression.PNB;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.WebDriverManager;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EndorseInspectionContactPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.RateTracePage;
import com.icat.epicenter.pom.ReinstatePolicyPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC018 extends AbstractNAHOTest {

	public PNBTC018() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID18.xls";
	}

	HomePage homePage;
	DwellingPage dwellingPage;
	LocationPage locationPage;
	CreateQuotePage createQuotePage;
	AccountOverviewPage accountOverviewPage;
	PolicySummaryPage policySummaryPage;
	RequestBindPage requestBindPage;
	CancelPolicyPage cancelPolicyPage;
	ReinstatePolicyPage reinsatePolicyPage;
	Map<String, String> testData;
	EndorsePolicyPage endorsePolicyPage;
	EndorseInspectionContactPage endorseInspectionContactPage;
	RateTracePage rateTracePage;
	BasePageControl basePage = new BasePageControl();
	String quoteNumber;
	String policyNumber;
	static final String PAGE_NAVIGATED = "Page Navigated";
	static final String VALUES_ENTERED = "Values Entered successfully";
	static int dataValue1 = 0;
	static int dataValue2 = 1;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		homePage = new HomePage();
		locationPage = new LocationPage();
		createQuotePage = new CreateQuotePage();
		accountOverviewPage = new AccountOverviewPage();
		requestBindPage = new RequestBindPage();
		policySummaryPage = new PolicySummaryPage();
		cancelPolicyPage = new CancelPolicyPage();
		reinsatePolicyPage = new ReinstatePolicyPage();
		endorsePolicyPage = new EndorsePolicyPage();
		endorseInspectionContactPage = new EndorseInspectionContactPage();
		rateTracePage = new RateTracePage();
		testData = data.get(dataValue1);

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
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

		accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		Assertions.passTest("Request Bind Page", "Request Bind page loaded successfully");
		requestBindPage.enterBindDetailsNAHO(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");
		policyNumber = policySummaryPage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "Policy Number: " + policySummaryPage.getPolicynumber());

		// Go to Home Page
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		Assertions.passTest("Home Page", PAGE_NAVIGATED);

		// Find the policy by entering policy Number
		homePage.refreshPage();
		homePage.policyNumber.scrollToElement();
		homePage.findFilterArrow.scrollToElement();
		homePage.findFilterArrow.click();
		homePage.findFilterPolicyOption.scrollToElement();
		homePage.findFilterPolicyOption.click();
		homePage.policyNumber.setData(policyNumber);
		homePage.findBtnPolicy.scrollToElement();
		homePage.findBtnPolicy.click();
		Assertions.passTest("Home Page", "Created Policy is selected");

		// Renew Policy
		policySummaryPage.renewPolicy.waitTillVisibilityOfElement(60);
		policySummaryPage.renewPolicy.waitTillButtonIsClickable(60);
		policySummaryPage.renewPolicy.scrollToElement();
		policySummaryPage.renewPolicy.click();
		accountOverviewPage.releaseRenewalToProducerButton.click();

		// Retrieve NB discounts
		accountOverviewPage.viewOrPrintRateTrace.waitTillButtonIsClickable(60);
		accountOverviewPage.viewOrPrintRateTrace.waitTillVisibilityOfElement(60);
		accountOverviewPage.viewOrPrintRateTrace.scrollToElement();
		accountOverviewPage.viewOrPrintRateTrace.click();
		rateTracePage.rateTraceHeader.waitTillPresenceOfElement(60);
		rateTracePage.L1D1WindNBDiscValue.scrollToElement();
		String L1D1WindNBDBeforeMove = rateTracePage.L1D1WindNBDiscValue.getData();
		rateTracePage.L1D1EarthquakeNBDiscValue.scrollToElement();
		String L1D1EarthquakeNBDBeforeMove = rateTracePage.L1D1EarthquakeNBDiscValue.getData();
		rateTracePage.L1D2WindNBDiscValue.scrollToElement();
		String L1D2WindNBD_Actual = rateTracePage.L1D2WindNBDiscValue.getData();
		rateTracePage.L1D2EarthquakeNBDiscValue.scrollToElement();
		String L1D2EarthquakeNBD_Actual = rateTracePage.L1D2EarthquakeNBDiscValue.getData();
		rateTracePage.backButton.click();

		// Edit L1D1
		accountOverviewPage.editDwelling11.scrollToElement();
		accountOverviewPage.editDwelling11.click();
		accountOverviewPage.editBuilding.scrollToElement();
		accountOverviewPage.editBuilding.click();

		// Commented because of CR19068
		if (accountOverviewPage.yesButton.checkIfElementIsPresent()) {
			accountOverviewPage.yesButton.scrollToElement();
			accountOverviewPage.yesButton.click();
		}
		testData = data.get(dataValue2);
		dwellingPage.addDwellingDetails(testData, 1, 1);
		dwellingPage.reviewDwelling();
		Assertions.passTest("Dwelling Page", "Location 1 Dwelling 1 Adrress " + VALUES_ENTERED);

		// Add New Location and move D2 to New Location
		dwellingPage.addSymbol.waitTillVisibilityOfElement(60);
		dwellingPage.addSymbol.scrollToElement();
		dwellingPage.addSymbol.click();
		dwellingPage.addNewLocation.scrollToElement();
		dwellingPage.addNewLocation.click();
		Actions builder1 = new Actions(WebDriverManager.getCurrentDriver())
				.moveToElement(WebDriverManager.getCurrentDriver().findElement(By.xpath(
						"//ul[@id='accountDetailsStructure']/following-sibling::ul//span[contains(text(),'Dwelling 1-2')]")))
				.pause(Duration.ofSeconds(3))
				.clickAndHold(WebDriverManager.getCurrentDriver().findElement(By.xpath(
						"//ul[@id='accountDetailsStructure']/following-sibling::ul//span[contains(text(),'Dwelling 1-2')]")))
				.pause(Duration.ofSeconds(3)).moveByOffset(1, 0)
				.moveToElement(
						WebDriverManager.getCurrentDriver().findElement(By.xpath("(//ul[@class='ui-sortable'])[2]")))
				.moveByOffset(1, 0).pause(Duration.ofSeconds(3)).release();
		builder1.perform();
		accountOverviewPage.saveOrder.scrollToElement();
		accountOverviewPage.saveOrder.click();

		// Review Dwelling
		dwellingPage.dwellingLink.formatDynamicPath(2, 1).waitTillVisibilityOfElement(60);
		if (dwellingPage.dwellingLink.formatDynamicPath(2, 1).checkIfElementIsPresent()) {
			dwellingPage.dwellingLink.formatDynamicPath(2, 1).scrollToElement();
			dwellingPage.dwellingLink.formatDynamicPath(2, 1).click();
		}
		dwellingPage.reviewDwelling.scrollToElement();
		dwellingPage.reviewDwelling.click();
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();

		// Retrieve NB discounts after moving the second dwelling(L1D2) to the second
		// location(L2
		accountOverviewPage.viewOrPrintRateTrace.click();
		rateTracePage.rateTraceHeader.waitTillPresenceOfElement(60);
		rateTracePage.L1D1WindNBDiscValue.scrollToElement();
		String L1D1WindNBDAfterMove_Actual = rateTracePage.L1D1WindNBDiscValue.getData();
		rateTracePage.L1D1EarthquakeNBDiscValue.scrollToElement();
		String L1D1EarthquakeNBDAfterMove_Actual = rateTracePage.L1D1EarthquakeNBDiscValue.getData();
		rateTracePage.scrollToBottomPage();
		rateTracePage.L2D1WindNBDiscValue.scrollToElement();
		String L2D1WindNBDAfterMove_Actual = rateTracePage.L2D1WindNBDiscValue.getData();
		rateTracePage.L2D1EarthquakeNBDiscValue.scrollToElement();
		String L2D1EarthquakeNBDAfterMove_Actual = rateTracePage.L2D1EarthquakeNBDiscValue.getData();
		rateTracePage.backButton.click();
		Assertions.verify(L1D1WindNBDAfterMove_Actual, L1D1WindNBDBeforeMove, "Rate Trace Page",
				"NB Discount for Dwelling 1-1 - Wind is verified", false, false);
		Assertions.verify(L1D1EarthquakeNBDAfterMove_Actual, L1D1EarthquakeNBDBeforeMove, "Rate Trace Page",
				"NB Discount for Dwelling 1-1 - EarthQuake is verified", false, false);
		Assertions.verify(L2D1WindNBDAfterMove_Actual, L1D2WindNBD_Actual, "Rate Trace Page",
				"NB Discount for Dwelling 1-2 - Wind after moving L1D2 to L2 is verified", false, false);
		Assertions.verify(L2D1EarthquakeNBDAfterMove_Actual, L1D2EarthquakeNBD_Actual, "Rate Trace Page",
				"NB Discount for Dwelling 1-2 - EarthQuake after moving L1D2 to L2 is verified", false, false);
		Assertions.passTest("PNB_Regression_TC018", "Executed Successfully");
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);

	}
}
