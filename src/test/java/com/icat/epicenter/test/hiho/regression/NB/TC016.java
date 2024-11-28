/** Program Description: To verify presence of Additional interest fields in Edit Additional Interest page,create policy by adding Additional Interest and to verify the same in quote document.
 *  Author			   : John
 *  Date of Creation   : 24/12/2020
**/
package com.icat.epicenter.test.hiho.regression.NB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EditAdditionalInterestInformationPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC016 extends AbstractNAHOTest {

	public TC016() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHONB/NBTCID16.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {
		// Initializing all Objects
		HomePage homePage = new HomePage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		DwellingPage dwellingPage = new DwellingPage();
		EditAdditionalInterestInformationPage editAdditionalInterestInformationPage = new EditAdditionalInterestInformationPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
		Assertions.passTest("Home Page", "Logged in as Producer ");

		// Create New Account
		testData = data.get(data_Value1);

		// asserting personal login details
		homePage.enterPersonalLoginDetails();
		Assertions.passTest("Home Page", "Page Navigated");

		Assertions.passTest("Home Page", "Page Navigated");
		Assertions.passTest("Home Page", "New Account created successfully");
		homePage.createNewAccountWithNamedInsured(testData, setUpData);

		// Entering Dwelling Details
		dwellingPage.enterDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", "Dwelling details entered");

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		createQuotePage.enterDeductibles(testData);
		createQuotePage.addOptionalCoverageDetails(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered");

		// Click on get a quote button
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();

		Assertions.passTest("Create Quote Page", "Clicked on get a quote button");

		// Click on Request Bind
		Assertions.passTest("Account Overview Page", "Account Overview page loaded successfully");

		// Getting Quote Number
		String quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

		accountOverviewPage.editAdditionalIntersets.scrollToElement();
		accountOverviewPage.editAdditionalIntersets.click();

		editAdditionalInterestInformationPage.addAdditionalInterestHIHO(testData);
		editAdditionalInterestInformationPage.update.scrollToElement();
		editAdditionalInterestInformationPage.update.click();
		accountOverviewPage.viewPrintFullQuoteLink.waitTillPresenceOfElement(60);
		accountOverviewPage.viewPrintFullQuoteLink.waitTillVisibilityOfElement(60);
		accountOverviewPage.viewPrintFullQuoteLink.waitTillElementisEnabled(60);
		accountOverviewPage.viewPrintFullQuoteLink.waitTillButtonIsClickable(60);
		accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
		accountOverviewPage.viewPrintFullQuoteLink.click();
		Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
				"View Or Print Full Quote Page", "View Or Print Full Quote Page loaded successfully", false, false);

		// Verifying the Additional Interest details from View or print Full
		// Quote Page
		String aiType1 = viewOrPrintFullQuotePage.aiAddInsured.getData();
		Assertions.verify(aiType1.contains(testData.get("1-AIType")), true, "View Or Print Full Quote Page",
				"The Additional Interest Type Selected is " + aiType1 + " displayed is verified", false, false);
		String aiName1 = viewOrPrintFullQuotePage.aiDetailsHIHO.formatDynamicPath(5).getData();
		Assertions.verify(aiName1.contains(testData.get("1-AIName")), true, "View Or Print Full Quote Page",
				"The Name Entered for Additional Insured is " + aiName1 + " displyed is verified", false, false);
		String aiAddress1 = viewOrPrintFullQuotePage.aiDetailsHIHO.formatDynamicPath(6).getData();
		Assertions.verify(aiAddress1.contains(testData.get("1-AIAddr1")), true, "View Or Print Full Quote Page",
				"The Address Entered for Additional Insured is " + aiAddress1 + " displayed is verified", false, false);

		String aiMortgagee = viewOrPrintFullQuotePage.aiMortgagee.getData();
		Assertions.verify(aiMortgagee.contains(testData.get("2-AIType")), true, "View Or Print Full Quote Page",
				"The Additional Interest Type Selected is " + aiMortgagee + " displayed is verified", false, false);
		String mortName = viewOrPrintFullQuotePage.mortAIDetails.formatDynamicPath(7).getData();
		Assertions.verify(mortName.contains(testData.get("2-AIName")), true, "View Or Print Full Quote Page",
				"The Name Entered is " + mortName + " displayed is verified", false, false);

		String mortRank = viewOrPrintFullQuotePage.mortAIDetails.formatDynamicPath(8).getData();
		Assertions.verify(mortRank.contains("First"), true, "View Or Print Full Quote Page",
				"The Morgagee Rank selected is " + mortRank + " Mortgagee displayed is verified", false, false);

		String mortAddress = viewOrPrintFullQuotePage.mortAIDetails.formatDynamicPath(9).getData();
		Assertions.verify(mortAddress.contains(testData.get("2-AIAddr1")), true, "View Or Print Full Quote Page",
				"The Address Entered for Mortgagee is " + mortAddress + " displayed is verified", false, false);

		String mortLoanNumber = viewOrPrintFullQuotePage.mortAIDetails.formatDynamicPath(10).getData();
		Assertions.verify(mortLoanNumber.contains(testData.get("2-AILoanNumber")), true,
				"View Or Print Full Quote Page",
				"The LoanNumber entered for Mortgagee " + mortLoanNumber + " displayed is verified", false, false);

		viewOrPrintFullQuotePage.backButton.click();
		viewOrPrintFullQuotePage.backButton.waitTillInVisibilityOfElement(60);

		requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		Assertions.passTest("Account Overview Page ", "Clicked on Request Bind");

		Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
				"Request Bind Page Loaded successfully", false, false);

		// Entering Request Bind Page Details
		requestBindPage.enterPolicyDetailsNAHO(testData);
		requestBindPage.enterPaymentInformation(testData);
		requestBindPage.addInspectionContact(testData);

		// Adding AI from Request Bind Page
		testData = data.get(data_Value2);
		requestBindPage.aIAddSymbol.waitTillVisibilityOfElement(60);
		requestBindPage.aIAddSymbol.click();
		requestBindPage.aITypeArrow.scrollToElement();
		requestBindPage.aITypeArrow.click();
		requestBindPage.aITypeOption.formatDynamicPath(2, testData.get("3-AIType")).waitTillVisibilityOfElement(60);
		requestBindPage.aITypeOption.formatDynamicPath(2, testData.get("3-AIType")).scrollToElement();
		requestBindPage.aITypeOption.formatDynamicPath(2, testData.get("3-AIType")).click();
		requestBindPage.aIName.setData(testData.get("3-AIName"));
		requestBindPage.aILoanNumber.setData(testData.get("3-AILoanNumber"));
		requestBindPage.aIEnterAddressManuallyLink.scrollToElement();
		requestBindPage.aIEnterAddressManuallyLink.click();
		requestBindPage.aIAddressLine1.setData(testData.get("3-AIAddr1"));
		requestBindPage.aIAddressLine2.setData(testData.get("3-AIAddr2"));
		requestBindPage.aICity.setData(testData.get("3-AICity"));
		requestBindPage.aIState.setData(testData.get("3-AIState"));
		if (requestBindPage.aIZipCode1.checkIfElementIsPresent()
				&& requestBindPage.aIZipCode1.checkIfElementIsDisplayed()) {
			requestBindPage.aIZipCode1.setData(testData.get("3-AIZIP"));
		} else if (requestBindPage.aiZipCodeQ3.formatDynamicPath(2).checkIfElementIsPresent()
				&& requestBindPage.aiZipCodeQ3.formatDynamicPath(2).checkIfElementIsDisplayed()) {
			requestBindPage.aiZipCodeQ3.formatDynamicPath(2).setData(testData.get("3-AIZIP"));
		}

		Assertions.passTest("Request Bind Page", "Added Another AI Additional Insured");

		testData = data.get(data_Value1);
		requestBindPage.addContactInformation(testData);
		requestBindPage.submit.waitTillButtonIsClickable(60);
		requestBindPage.submit.click();
		requestBindPage.confirmBind();

		String policyNumber = policySummarypage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);
		Assertions.verify(policyNumber.startsWith("52-358"), true, "Policy Summary Page",
				"Policy Number format is verified", false, false);

		policySummarypage.quoteNoLinkNAHO.scrollToElement();
		policySummarypage.quoteNoLinkNAHO.click();
		Assertions.passTest("Quote Document Page", "Quote Document Page Loaded successfully");

		// Verifying the Additional Interest details from
		// Policy Summary Quote Page
		String aiType1policy = viewOrPrintFullQuotePage.aiAddInsured.getData();
		Assertions.verify(aiType1policy.contains(testData.get("1-AIType")), true, "Quote Document Page",
				"The Additional Interest Type Selected is " + aiType1policy + " displayed is verified", false, false);
		String aiName1policy = viewOrPrintFullQuotePage.aiDetailsHIHO.formatDynamicPath(5).getData();
		Assertions.verify(aiName1policy.contains(testData.get("1-AIName")), true, "Quote Document Page",
				"The Name Entered for Additional Insured is " + aiName1policy + " displyed is verified", false, false);
		String aiAddress1policy = viewOrPrintFullQuotePage.aiDetailsHIHO.formatDynamicPath(6).getData();
		Assertions.verify(aiAddress1policy.contains(testData.get("1-AIAddr1")), true, "Quote Document Page",
				"The Address Entered for Additional Insured is " + aiAddress1policy + " displayed is verified", false,
				false);

		String aiMortgageepolicy = viewOrPrintFullQuotePage.aiMortgagee.getData();
		Assertions.verify(aiMortgageepolicy.contains(testData.get("2-AIType")), true, "Quote Document Page",
				"The Additional Interest Type Selected is " + aiMortgageepolicy + " displayed is verified", false,
				false);
		String mortNamepolicy = viewOrPrintFullQuotePage.mortAIDetails.formatDynamicPath(7).getData();
		Assertions.verify(mortNamepolicy.contains(testData.get("2-AIName")), true, "Quote Document Page",
				"The Name Entered is " + mortNamepolicy + " displayed is verified", false, false);

		String mortRankpolicy = viewOrPrintFullQuotePage.mortAIDetails.formatDynamicPath(8).getData();
		Assertions.verify(mortRankpolicy.contains("First"), true, "Quote Document Page",
				"The Mortgagee Rank selected is " + mortRankpolicy + " Mortgagee displayed is verified", false, false);

		String mortAddresspolicy = viewOrPrintFullQuotePage.mortAIDetails.formatDynamicPath(9).getData();
		Assertions.verify(mortAddresspolicy.contains(testData.get("2-AIAddr1")), true, "Quote Document Page",
				"The Address Entered for Mortgagee is " + mortAddresspolicy + " displayed is verified", false, false);

		String mortLoanNumberpolicy = viewOrPrintFullQuotePage.mortAIDetails.formatDynamicPath(10).getData();
		Assertions.verify(mortLoanNumberpolicy.contains(testData.get("2-AILoanNumber")), true, "Quote Document Page",
				"The LoanNumber entered for Mortgagee " + mortLoanNumberpolicy + " displayed is verified", false,
				false);
		testData = data.get(data_Value2);
		String aiType2policy = viewOrPrintFullQuotePage.aiAddInsured.getData();
		Assertions.verify(aiType2policy.contains(testData.get("3-AIType")), true, "Quote Document Page",
				"The Additional Interest Type Selected is " + aiType2policy + " displayed is verified", false, false);
		String aiName2policy = viewOrPrintFullQuotePage.aiDetailsHIHO.formatDynamicPath(8).getData();
		Assertions.verify(aiName2policy.contains(testData.get("3-AIName")), true, "Quote Document Page",
				"The Name Entered for Additional Insured is " + aiName2policy + " displyed is verified", false, false);
		String aiAddress2policy = viewOrPrintFullQuotePage.aiDetailsHIHO.formatDynamicPath(9).getData();
		Assertions.verify(aiAddress2policy.contains(testData.get("3-AIAddr1")), true, "Quote Document Page",
				"The Address Entered for Additional Insured is " + aiAddress2policy + " displayed is verified", false,
				false);

		testData = data.get(data_Value1);
		// Verifying Mailing Address
		String mailingAddrs = viewOrPrintFullQuotePage.mailingAddress.formatDynamicPath(2).getData();
		Assertions.verify(mailingAddrs.contains(testData.get("InsuredAddr1")), true, "Quote Document Page",
				"The Malining Address entered " + mailingAddrs + " displayed is verified", false, false);

		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("NB_Regression_TC016", "Executed Successfully");
	}

}
