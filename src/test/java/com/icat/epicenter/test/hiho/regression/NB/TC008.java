/** Program Description: To generate a HIHO policy with single location/dwelling with Coverage limits referrals and assert values.
 *  Author			   : SM Netserv
 *  Date of Creation   : June 2018
 **/

package com.icat.epicenter.test.hiho.regression.NB;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.DateConversions;
import com.NetServAutomationFramework.generic.TextFieldControl;
import com.NetServAutomationFramework.generic.WebDriverManager;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.ConfirmBindRequestPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EmailQuotePage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC008 extends AbstractNAHOTest {

	public TC008() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHONB/NBTCID08.xls";
	}

	public HomePage homePage;
	public DwellingPage dwellingPage;
	public CreateQuotePage createQuotePage;
	public AccountOverviewPage accountOverviewPage;
	public RequestBindPage requestBindPage;
	public BindRequestSubmittedPage bindRequestPage;
	public ReferralPage referralPage;
	public PolicySummaryPage policySummaryPage;
	public BuildingUnderMinimumCostPage dwellingCost;
	public EmailQuotePage emailQuote;
	public ConfirmBindRequestPage confirmBindRequest;
	public BasePageControl basePage;
	String quoteNumber;
	String policyNumber;
	static final String ext_rpt_msg = " is verified";
	static final String PAGE_NAVIGATED = "Page Navigated";
	static final String VALUES_UPDATED = "Values Updated";
	static final String VALUES_ENTERED = "Values Entered Successfully";
	static final String VALUES_VERIFIED = "Values Verified";
	static int data_Value1 = 0;
	static int data_Value2 = 1;
	static int data_Value3 = 2;
	static int data_Value4 = 3;
	static int data_Value5 = 4;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		homePage = new HomePage();
		dwellingCost = new BuildingUnderMinimumCostPage();
		createQuotePage = new CreateQuotePage();
		accountOverviewPage = new AccountOverviewPage();
		emailQuote = new EmailQuotePage();
		policySummaryPage = new PolicySummaryPage();
		confirmBindRequest = new ConfirmBindRequestPage();
		requestBindPage = new RequestBindPage();
		referralPage = new ReferralPage();
		dwellingPage = new DwellingPage();
		Map<String, String> testData = data.get(data_Value1);

		DateConversions date = new DateConversions();
		String insuredName = testData.get("InsuredName") + date.getCurrentDate("MM/dd/YYYY_hh:mm:ss");

		homePage.goToHomepage.click();
		homePage.createNewAccount.scrollToElement();
		homePage.createNewAccount.click();
		homePage.namedInsured.setData(insuredName);
		Assertions.passTest("Home Page", "Insured Name is " + insuredName);
		homePage.producerNumber.setData("11250.1");
		if (homePage.productArrow.checkIfElementIsPresent()) {
			homePage.productArrow.click();
			homePage.productSelection.formatDynamicPath(testData.get("ProductSelection"))
					.waitTillVisibilityOfElement(60);
			homePage.productSelection.formatDynamicPath(testData.get("ProductSelection")).click();
		}
		if (!testData.get("ProductSelection").equalsIgnoreCase("Commercial")) {
			if (homePage.effectiveDate.formatDynamicPath(1).checkIfElementIsPresent()
					&& homePage.effectiveDate.formatDynamicPath(1).checkIfElementIsDisplayed()) {
				homePage.effectiveDate.formatDynamicPath(1).waitTillVisibilityOfElement(30);
				homePage.effectiveDate.formatDynamicPath(1).setData(testData.get("PolicyEffDate"));
			} else if (homePage.effectiveDate.formatDynamicPath(2).checkIfElementIsPresent()
					&& homePage.effectiveDate.formatDynamicPath(2).checkIfElementIsDisplayed()) {
				homePage.effectiveDate.formatDynamicPath(2).waitTillVisibilityOfElement(30);
				homePage.effectiveDate.formatDynamicPath(2).setData(testData.get("PolicyEffDate"));
			} else {
				homePage.effectiveDateNew.waitTillVisibilityOfElement(60);
				homePage.effectiveDateNew.scrollToElement();
				homePage.effectiveDateNew.setData(testData.get("PolicyEffDate"));
			}
		}
		homePage.goButton.click();
		homePage.goButton.waitTillInVisibilityOfElement(30);
		Assertions.passTest("New Account", "New Account created successfully");

		// Click on Home page Button
		homePage.goToHomepage.click();
		Assertions.passTest("Home Page", PAGE_NAVIGATED);

		// find the account by entering insured name and producer name
		homePage.findAccountNamedInsured.scrollToElement();
		homePage.findAccountNamedInsured.setData(insuredName);
		homePage.findBtn.scrollToElement();
		homePage.findBtn.click();
		Assertions.passTest("Account Overview Page", PAGE_NAVIGATED);

		// Delete account
		accountOverviewPage.deleteAccount.scrollToElement();
		accountOverviewPage.deleteAccount.click();
		accountOverviewPage.yesDeleteBuilding.click();
		Assertions.verify(accountOverviewPage.deleteAccountMessage.checkIfElementIsDisplayed(), true, "Home Page",
				"Delete Account Message" + ext_rpt_msg, false, false);

		// New account 2
		testData = data.get(data_Value2);
		homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("New Account", "New Account created successfully");
		testData = data.get(data_Value1);

		// Entering Dwelling 1 Details
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		dwellingPage.addDwellingDetails(testData, 1, 1);
		dwellingPage.addRoofDetails(testData, 1, 1);
		dwellingPage.enterDwellingValues(testData, 1, 1);
		Assertions.passTest("Dwelling Page", VALUES_ENTERED);

		accountOverviewPage.editDwelling11.scrollToElement();
		accountOverviewPage.editDwelling11.click();
		dwellingPage.reviewDwelling.scrollToElement();
		dwellingPage.reviewDwelling.click();

		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();

		// Entering Quote page details
		Assertions.passTest("Create Quote Page", PAGE_NAVIGATED);
		createQuotePage.enterDeductibles(testData);
		Assertions.passTest("Create Quote Page", VALUES_UPDATED + " and " + VALUES_VERIFIED);
		/*accountOverviewPage.editDwelling11.scrollToElement();
		accountOverviewPage.editDwelling11.click();
		dwellingPage.reviewDwelling.scrollToElement();
		dwellingPage.reviewDwelling.click();
		if (dwellingPage.override.checkIfElementIsPresent() && dwellingPage.override.checkIfElementIsDisplayed()) {
			dwellingPage.override.scrollToElement();
			dwellingPage.override.click();
		}
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();
		dwellingPage.createQuote.waitTillInVisibilityOfElement(60);*/

		// Entering Quote page details
		Assertions.passTest("Create Quote Page", PAGE_NAVIGATED);
		createQuotePage.enterDeductibles(testData);
		createQuotePage.addOptionalCoverageDetails(testData);
		Assertions.verify(createQuotePage.mold.getData(), testData.get("Mold"), "Create Quote Page",
				"Default Mold value" + ext_rpt_msg, false, false);
		for (int i = 1; i <= Integer.parseInt(testData.get("LocCount")); i++) {
			for (int j = 1; j <= Integer.parseInt(testData.get("L" + i + "-DwellingCount")); j++) {
				Assertions.addInfo("<span class='group'> Create Quote Page</span>",
						"<span class='group'> Location " + i + " - Dwelling " + i + "-" + j + " </span>");
				BaseWebElementControl locationDwelling = new BaseWebElementControl(
						By.xpath("//span[contains(text(),'Location " + i + " - Dwelling " + i + "-" + j + "')]"));
				TextFieldControl covA_NHinputBox = new TextFieldControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[2]//input"));
				TextFieldControl covB_NHinputBox = new TextFieldControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[3]//td[2]//input"));
				TextFieldControl covC_NHinputBox = new TextFieldControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[4]//td[2]//input"));
				TextFieldControl covD_NHinputBox = new TextFieldControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[5]//td[2]//input"));
				BaseWebElementControl covA_NHlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covB_NHlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covC_NHlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covD_NHlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				TextFieldControl covA_EQinputBox = new TextFieldControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[3]//input"));
				TextFieldControl covB_EQinputBox = new TextFieldControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[3]//td[3]//input"));
				TextFieldControl covC_EQinputBox = new TextFieldControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[4]//td[3]//input"));
				TextFieldControl covD_EQinputBox = new TextFieldControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[5]//td[3]//input"));
				BaseWebElementControl covA_EQlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covB_EQlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covC_EQlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covD_EQlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));

				if (locationDwelling.checkIfElementIsPresent() && locationDwelling.checkIfElementIsDisplayed()) {
					if (testData.get("L" + i + "D" + j + "-DwellingCovA") != "") {
						Assertions.verify(covA_NHinputBox.getAttrributeValue("Value"),
								testData.get("L" + i + "D" + j + "-DwellingCovA").replace(",", ""), "Create Quote Page",
								"Coverage A Named Hurricane value: " + covA_NHinputBox.getAttrributeValue("Value")
										+ ext_rpt_msg,
								false, false);
						Assertions
								.verify(covA_EQinputBox.getAttrributeValue("Value"),
										testData.get("L" + i + "D" + j + "-DwellingCovA").replace(",", ""),
										"Create Quote Page", "Coverage A Earthquake value: "
												+ covA_EQinputBox.getAttrributeValue("Value") + ext_rpt_msg,
										false, false);
					} else {
						Assertions.verify(covA_NHlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage A Named Hurricane value: " + covA_NHlabel.getData() + ext_rpt_msg, false,
								false);
						Assertions.verify(covA_EQlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage A Earthquake value: " + covA_EQlabel.getData() + ext_rpt_msg, false, false);
					}
					if (testData.get("L" + i + "D" + j + "-DwellingCovB") != "") {
						Assertions.verify(covB_NHinputBox.getAttrributeValue("Value"),
								testData.get("L" + i + "D" + j + "-DwellingCovB").replace(",", ""), "Create Quote Page",
								"Coverage B Named Hurricane value: " + covB_NHinputBox.getAttrributeValue("Value")
										+ ext_rpt_msg,
								false, false);
						Assertions
								.verify(covB_EQinputBox.getAttrributeValue("Value"),
										testData.get("L" + i + "D" + j + "-DwellingCovB").replace(",", ""),
										"Create Quote Page", "Coverage B Earthquake value: "
												+ covB_EQinputBox.getAttrributeValue("Value") + ext_rpt_msg,
										false, false);
					} else {
						Assertions.verify(covB_NHlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage B Named Hurricane value: " + covB_NHlabel.getData() + ext_rpt_msg, false,
								false);
						Assertions.verify(covB_EQlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage B Earthquake value: " + covB_EQlabel.getData() + ext_rpt_msg, false, false);
					}
					if (testData.get("L" + i + "D" + j + "-DwellingCovC") != "") {
						Assertions.verify(covC_NHinputBox.getAttrributeValue("Value"),
								testData.get("L" + i + "D" + j + "-DwellingCovC").replace(",", ""), "Create Quote Page",
								"Coverage C Named Hurricane value: " + covC_NHinputBox.getAttrributeValue("Value")
										+ ext_rpt_msg,
								false, false);
						Assertions
								.verify(covC_EQinputBox.getAttrributeValue("Value"),
										testData.get("L" + i + "D" + j + "-DwellingCovC").replace(",", ""),
										"Create Quote Page", "Coverage C Earthquake value: "
												+ covC_NHinputBox.getAttrributeValue("Value") + ext_rpt_msg,
										false, false);
					} else {
						Assertions.verify(covC_NHlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage C Named Hurricane value: " + covC_NHlabel.getData() + ext_rpt_msg, false,
								false);
						Assertions.verify(covC_EQlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage C Earthquake value: " + covC_EQlabel.getData() + ext_rpt_msg, false, false);
					}
					if (testData.get("L" + i + "D" + j + "-DwellingCovD") != "") {
						Assertions.verify(covD_NHinputBox.getAttrributeValue("Value"),
								testData.get("L" + i + "D" + j + "-DwellingCovD").replace(",", ""), "Create Quote Page",
								"Coverage D Named Hurricane value: " + covD_NHinputBox.getAttrributeValue("Value")
										+ ext_rpt_msg,
								false, false);
						Assertions
								.verify(covD_EQinputBox.getAttrributeValue("Value"),
										testData.get("L" + i + "D" + j + "-DwellingCovD").replace(",", ""),
										"Create Quote Page", "Coverage D Earthquake value: "
												+ covD_EQinputBox.getAttrributeValue("Value") + ext_rpt_msg,
										false, false);
					} else {
						Assertions.verify(covD_NHlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage D Named Hurricane value: " + covD_NHlabel.getData() + ext_rpt_msg, false,
								false);
						Assertions.verify(covD_EQlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage D Earthquake value: " + covD_EQlabel.getData() + ext_rpt_msg, false, false);
					}

				}
			}
		}
		Assertions.passTest("Create Quote Page", "Insured values verified successfully");

		// Click on Quote step 3
		createQuotePage.quoteStep3.click();
		createQuotePage.goBack.waitTillPresenceOfElement(30);
		createQuotePage.goBack.scrollToElement();
		createQuotePage.goBack.click();
		createQuotePage.goBack.waitTillInVisibilityOfElement(60);
		Assertions.passTest("Create quote page", "Clicked on back button successfully");

		testData = data.get(data_Value2);

		// Entering Quote page details
		testData = data.get(data_Value2);
		Assertions.passTest("Create Quote Page", PAGE_NAVIGATED);
		createQuotePage = new CreateQuotePage();
		createQuotePage.enterDeductibles(testData);

		createQuotePage.addOptionalCoverageDetails(testData);
		Assertions.verify(createQuotePage.mold.getData(), testData.get("Mold"), "Create Quote Page",
				"Default Mold value" + ext_rpt_msg, false, false);

		// Setting Insured values in create quote page - 1
		char ch = 'A';
		createQuotePage.getAQuote.scrollToElement();
		for (int j = 2; j < 5; j++) {
			TextFieldControl ele = new TextFieldControl(By.xpath(
					"//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-1')]]]]]]//following-sibling::table[1]//tr["
							+ j + "]//td[2]//input"));
			ele.scrollToElement();
			ele.clearData();
			createQuotePage.waitTime(5);
			ele.setData(testData.get("L1D1-DwellingCov" + ch));
			ele.tab();
			createQuotePage.loading.waitTillInVisibilityOfElement(60);
			ch++;
		}

		// Verifying Earthquake Coverage in Grid
		char ch1 = 'A';
		for (int j = 2; j <= 5; j++) {
			WebElement ele = WebDriverManager.getCurrentDriver().findElement(By.xpath(
					"//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-1')]]]]]]//following-sibling::table[1]//tr["
							+ j + "]//td[3]//input"));
			Assertions.verify(ele.getAttribute("value").replaceAll(",", ""),
					testData.get("L1D1-DwellingCov" + ch1).replaceAll(",", ""), "Create Quote Page",
					"Cov " + ch1 + " value " + " : Earthquake - Dwelling 1" + ext_rpt_msg, false, false);
			ch1++;
		}

		createQuotePage.loading.waitTillInVisibilityOfElement(60);
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		createQuotePage.goBack.waitTillPresenceOfElement(30);
		createQuotePage.goBack.click();
		createQuotePage.goBack.waitTillInVisibilityOfElement(60);
		testData = data.get(data_Value3);

		// Setting Insured values in create quote page - 2nd
		char ch2 = 'A';
		for (int j = 2; j < 5; j++) {
			TextFieldControl ele = new TextFieldControl(By.xpath(
					"//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-1')]]]]]]//following-sibling::table[1]//tr["
							+ j + "]//td[2]//input"));
			ele.scrollToElement();
			ele.clearData();
			createQuotePage.waitTime(5);// To clear the data from text field
			ele.setData(testData.get("L1D1-DwellingCov" + ch2));
			ele.tab();
			createQuotePage.loading.waitTillInVisibilityOfElement(60);
			ch2++;
		}

		Assertions.passTest("Create Quote Page", VALUES_UPDATED + " and " + VALUES_VERIFIED);

		// Click on Get a quote Button
		createQuotePage.getAQuote.waitTillVisibilityOfElement(30);
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		createQuotePage.continueButton.click();
		createQuotePage.continueButton.waitTillInVisibilityOfElement(60);

		// Navigate to Account Overview Page
		Assertions.passTest("Account Overview Page", PAGE_NAVIGATED);
		Assertions.verify(accountOverviewPage.totalPremiumValue.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Total Premium" + ext_rpt_msg, false, false);
		Assertions.verify(accountOverviewPage.quoteNo1Holder.checkIfElementIsPresent(), true, "Account Overview Page",
				"Quote Number is displayed" + ext_rpt_msg, false, false);
		Assertions.verify(accountOverviewPage.quoteDedDetails.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Quote Deductibles Details", false, false);

		// Verifying Quote Details Link,Email Quote and Print Rate Trace
		accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
		accountOverviewPage.viewPrintFullQuoteLink.click();
		Assertions.verify(accountOverviewPage.quoteDocument.checkIfElementIsDisplayed(), true, "Quote Document",
				"Quote Document displayed" + ext_rpt_msg, false, false);
		createQuotePage.goBack.waitTillVisibilityOfElement(30);
		createQuotePage.goBack.click();
		accountOverviewPage.emailQuoteLink.click();
		Assertions.verify(accountOverviewPage.pageName.getData().contains("Email Quote"), true, "Email Quote Page",
				"Email Quote page displayed" + ext_rpt_msg, false, false);
		emailQuote.cancel.waitTillVisibilityOfElement(30);
		emailQuote.cancel.scrollToElement();
		emailQuote.cancel.click();
		accountOverviewPage.viewOrPrintRateTrace.click();
		Assertions.verify(accountOverviewPage.viewPrintRateTracePage.checkIfElementIsDisplayed(), true,
				"Rate Trace for Quote", "Rate Trace for Quote Screen displayed" + ext_rpt_msg, false, false);
		createQuotePage.backToAccountOverview.waitTillVisibilityOfElement(30);
		createQuotePage.backToAccountOverview.click();
		Assertions.passTest("Account Overview Page", VALUES_VERIFIED);

		// Creating another Quote
		accountOverviewPage.createAnotherQuote.scrollToElement();
		accountOverviewPage.createAnotherQuote.click();
		accountOverviewPage.createAnotherQuote.waitTillInVisibilityOfElement(60);

		// Entering Quote page details
		Assertions.passTest("Create Quote Page", PAGE_NAVIGATED);
		createQuotePage.enterDeductibles(testData);
		createQuotePage.addOptionalCoverageDetails(testData);
		Assertions.verify(createQuotePage.mold.getData(), testData.get("Mold"), "Create Quote Page",
				"Default Mold value" + ext_rpt_msg, false, false);
		testData = data.get(data_Value4);
		char ch4 = 'A';
		for (int j = 2; j < 5; j++) {
			TextFieldControl ele = new TextFieldControl(By.xpath(
					"//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-1')]]]]]]//following-sibling::table[1]//tr["
							+ j + "]//td[2]//input"));
			ele.scrollToElement();
			ele.clearData();
			createQuotePage.waitTime(5);
			ele.setData(testData.get("L1D1-DwellingCov" + ch4));
			createQuotePage.loading.waitTillInVisibilityOfElement(60);
		}

		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();

		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();

		if (createQuotePage.continueButton.checkIfElementIsPresent()
				&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}

		// Verifying quote number in Account Overview Page
		Assertions.passTest("Account Overview Page", PAGE_NAVIGATED);
		Assertions.verify(accountOverviewPage.quoteNo1Holder.checkIfElementIsPresent(), true, "Account Overview Page",
				"Quote Number is displayed" + ext_rpt_msg, false, false);
		Assertions.verify(accountOverviewPage.quote2StatusActive.getData(), testData.get("Status"),
				"Account Overview Page", "Quote 2 Status" + ext_rpt_msg, false, false);
		Assertions.passTest("Account Overview Page", VALUES_VERIFIED);

		accountOverviewPage.bindStep4.scrollToElement();
		accountOverviewPage.bindStep4.click();
		testData = data.get(data_Value1);

		// Entering Request Bind Page
		Assertions.passTest("Request Bind Page", PAGE_NAVIGATED);
		requestBindPage.enterPolicyDetailsNAHO(testData);
		testData = data.get(data_Value2);
		requestBindPage.namedInsured.clearData();
		requestBindPage.waitTime(3);
		if (requestBindPage.nameChangeNo.checkIfElementIsPresent()
				&& requestBindPage.nameChangeNo.checkIfElementIsDisplayed()) {
			requestBindPage.nameChangeNo.waitTillPresenceOfElement(60);
			requestBindPage.nameChangeNo.waitTillVisibilityOfElement(60);
			requestBindPage.nameChangeNo.waitTillButtonIsClickable(60);
			requestBindPage.nameChangeNo.scrollToElement();
			requestBindPage.nameChangeNo.click();
			requestBindPage.nameChangeNo.waitTillInVisibilityOfElement(60);
		}
		requestBindPage.namedInsured.setData(testData.get("InsuredName"));
		requestBindPage.namedInsured.tab();
		requestBindPage.waitTime(3);
		if (requestBindPage.nameChangeNo.checkIfElementIsPresent()
				&& requestBindPage.nameChangeNo.checkIfElementIsDisplayed()) {
			requestBindPage.nameChangeNo.waitTillPresenceOfElement(60);
			requestBindPage.nameChangeNo.waitTillVisibilityOfElement(60);
			requestBindPage.nameChangeNo.waitTillButtonIsClickable(60);
			requestBindPage.nameChangeNo.scrollToElement();
			requestBindPage.nameChangeNo.click();
			requestBindPage.nameChangeNo.waitTillInVisibilityOfElement(60);
		}
		testData = data.get(data_Value1);
		requestBindPage.enterPaymentInformation(testData);
		requestBindPage.addInspectionContact(testData);
		requestBindPage.addContactInformation(testData);

		// Enter Request Bind Info
		testData = data.get(data_Value2);
		Assertions.passTest("Request Bind Page", PAGE_NAVIGATED);
		requestBindPage.enterPaymentInformation(testData);
		requestBindPage.addInspectionContact(testData);
		requestBindPage.addContactInformation(testData);
		requestBindPage.submit.scrollToElement();
		requestBindPage.submit.click();
		requestBindPage.requestBind.waitTillVisibilityOfElement(60);
		requestBindPage.requestBind.scrollToElement();
		requestBindPage.requestBind.click();
		Assertions.verify(requestBindPage.mortgageeWarningMessage.checkIfElementIsDisplayed(), true,
				"Request Bind Page", requestBindPage.mortgageeWarningMessage.getData() + ext_rpt_msg, false, false);
		requestBindPage.addAdditionalInterestEQHO(testData);
		testData = data.get(data_Value3);
		requestBindPage.aIRankArrow.formatDynamicPath((1 - 1)).scrollToElement();
		requestBindPage.aIRankArrow.formatDynamicPath((1 - 1)).click();
		requestBindPage.aIRankoption.formatDynamicPath((1 - 1), testData.get(1 + "-AIRank"))
				.waitTillVisibilityOfElement(60);
		requestBindPage.aIRankoption.formatDynamicPath((1 - 1), testData.get(1 + "-AIRank")).scrollToElement();
		requestBindPage.aIRankoption.formatDynamicPath((1 - 1), testData.get(1 + "-AIRank")).click();
		requestBindPage.mortgageePay.scrollToElement();
		requestBindPage.mortgageePay.click();
		requestBindPage.submit.waitTillVisibilityOfElement(30);
		requestBindPage.submit.scrollToElement();
		requestBindPage.submit.click();
		requestBindPage.requestBind.waitTillVisibilityOfElement(30);
		requestBindPage.requestBind.scrollToElement();
		requestBindPage.requestBind.click();
		if (requestBindPage.overrideEffectiveDate.checkIfElementIsPresent()
				&& requestBindPage.overrideEffectiveDate.checkIfElementIsDisplayed()) {
			requestBindPage.overrideEffectiveDate.select();
			requestBindPage.submit.waitTillVisibilityOfElement(60);
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();
			requestBindPage.requestBind.waitTillVisibilityOfElement(60);
			requestBindPage.requestBind.click();
			requestBindPage.requestBind.waitTillInVisibilityOfElement(60);
		}
		policySummaryPage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", policySummaryPage.getPolicynumber());
		Assertions.passTest("Policy Summary Page", "Policy Summary Page is displayed");

		// Verification in Policy Summary Page
		Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsPresent(), true, "Policy Summary Page",
				"Policy Number Displayed" + ext_rpt_msg, false, false);
		Assertions.passTest("Policy Summary Page", "Policy Number is : " + policySummaryPage.getPolicynumber());
		Assertions.verify(policySummaryPage.policyStatus.getData(), testData.get("Status"), "Policy Summary Page",
				"Policy Active Status" + ext_rpt_msg, false, false);
		policySummaryPage.viewPolicySnapshot.click();
		Assertions.verify(policySummaryPage.policySnapshotScreen.checkIfElementIsDisplayed(), true,
				"Policy Snapshot Document", "Policy Snapshot Document displayed" + ext_rpt_msg, false, false);
		createQuotePage.goBack.click();
		homePage.goToHomepage.click();
		Assertions.passTest("Welcome Page", PAGE_NAVIGATED);
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("NB Regression Test TC008", "Executed Successfully");
	}
}