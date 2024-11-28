/** Program Description: Check for the New Minimum Deductible Value in the Latitude/Longitude impacted Areas For
a) New Business Policy with PB Endorsement and Rewrite
b) Renewal Policy
 *  Author			   : Sowndarya
 *  Date of Creation   : 07/27/2022
 **/
package com.icat.epicenter.test.hiho.regression.PNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountDetails;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC013 extends AbstractNAHOTest {

	public PNBTC013() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID13.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		LocationPage locationPage = new LocationPage();
		DwellingPage dwellingPage = new DwellingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		AccountDetails accountDetails = new AccountDetails();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		Map<String, String> testData;
		String quoteNumber;
		String policyNumber;
		int quoteLen;
		final String PAGE_NAVIGATED = "Page Navigated";
		final String VALUES_ENTERED = "Values Entered successfully";
		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
		testData = data.get(dataValue1);

		// creating New account
		Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
				"Home page loaded successfully", false, false);
		Assertions.passTest("Home Page", PAGE_NAVIGATED);
		homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("New Account", "New Account created successfully");

		// Entering Location 1 Dwelling 1 Details
		Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Building Page",
				"Dwelling Page loaded successfully", false, false);
		dwellingPage.enterDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", "Location 1 Dwelling 1 -1 " + VALUES_ENTERED);

		// Entering Create quote page Details
		Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Create Quote Page loaded successfully", false, false);
		Assertions.addInfo("Scenario 01",
				"Verifying the Default Named Hurricane Deductible displayed as 2% for the address 779 Kii St Honolulu, HI 96825");
		Assertions.verify(createQuotePage.namedHurricaneData.getData(), "10%", "Create Quote Page",
				"The default Named Hurricane deductible value " + createQuotePage.namedHurricaneData.getData()
						+ " displayed is verified",
				false, false);
		Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
		createQuotePage.enterDeductibles(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();

		// Getting the Quote Number
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Account Overview Page Loaded successfully", false, false);
		quoteLen = accountOverviewPage.quoteNumber.getData().length();
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
		Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

		// Click on account detail link
		accountOverviewPage.quoteAccDetails.scrollToElement();
		accountOverviewPage.quoteAccDetails.click();

		// click on edit
		accountOverviewPage.quoteEditAccDetails.scrollToElement();
		accountOverviewPage.quoteEditAccDetails.click();

		// change effective date
		Assertions.verify(accountDetails.reviewButton.checkIfElementIsDisplayed(), true, "Account Details Page",
				"Account Details Page Loaded successfully", false, false);
		testData = data.get(dataValue2);
		accountDetails.effectiveDate.setData(testData.get("PolicyEffDate"));
		accountDetails.effectiveDate.tab();
		waitTime(2);// wait time is needed to load the element
		if (accountDetails.wanttoContinue.checkIfElementIsPresent()
				&& accountDetails.wanttoContinue.checkIfElementIsDisplayed()) {
			accountDetails.wanttoContinue.waitTillVisibilityOfElement(60);
			accountDetails.wanttoContinue.scrollToElement();
			accountDetails.wanttoContinue.click();
		}

		// Click on create quote
		accountDetails.createQuoteBtn.scrollToElement();
		accountDetails.createQuoteBtn.click();

		// Entering Create quote page Details
		Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Create Quote Page loaded successfully", false, false);
		Assertions.addInfo("Scenario 02",
				"Verifying the Default Named Hurricane Deductible displayed as 10% when the Policy Effective Date is September 23, 2022");
		Assertions.verify(createQuotePage.namedHurricaneData.getData(), "10%", "Create Quote Page",
				"The default Named Hurricane deductible value " + createQuotePage.namedHurricaneData.getData()
						+ " displayed is verified",
				false, false);
		Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

		// verify the Drop Down and Check if 2% (Override is available) and not 2%.
		createQuotePage.namedHurricaneDeductibleArrow.scrollToElement();
		createQuotePage.namedHurricaneDeductibleArrow.click();
		Assertions.addInfo("Scenario 03", "Verifying the presence of Named Hurricane Deductible value 2%(Override)");
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2% (Override)")
						.checkIfElementIsDisplayed(),
				true, "Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2% (Override)").getData()
						+ " is verified",
				false, false);
		Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

		// Click on get a quote
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();

		// Getting the Quote Number
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Account Overview Page Loaded successfully", false, false);
		quoteLen = accountOverviewPage.quoteNumber.getData().length();
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
		Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);
		accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		Assertions.passTest("Request Bind Page", "Request Bind page loaded successfully");

		// Enter Bind Details
		Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
				"Request Bind Page loaded successfully", false, false);
		requestBindPage.enterBindDetailsNAHO(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");

		// Get the policy number
		Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
				"Policy Summary Page loaded successfully", false, false);
		policyNumber = policySummaryPage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "Policy Number: " + policyNumber);

		// Click on endorse pb link
		policySummaryPage.endorsePB.scrollToElement();
		policySummaryPage.endorsePB.click();
		Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

		// Enter Endorsement eff date
		testData = data.get(dataValue3);
		Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
				"Endorse Policy Page loaded successfully", false, false);
		endorsePolicyPage.endorsementEffDate.setData(testData.get("PolicyEffDate"));
		endorsePolicyPage.endorsementEffDate.tab();

		// clicking on edit Location/Building information hyperlink
		endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
		endorsePolicyPage.editLocOrBldgInformationLink.click();
		Assertions.passTest("Endorse Policy Page", "Clicked on Edit location or building information link");

		// Edit the dwelling details
		Assertions.verify(locationPage.reviewLocation.checkIfElementIsDisplayed(), true, "Location Page",
				"Location Page loaded successfully", false, false);
		locationPage.dwelling1.scrollToElement();
		locationPage.dwelling1.click();

		// Edit Location 1 Dwelling 1 Details
		Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Building Page",
				"Dwelling Page loaded successfully", false, false);
		dwellingPage.addDwellingDetails(testData, 1, 1);
		dwellingPage.reviewDwelling();

		// click on create quote
		dwellingPage.continueButton.scrollToElement();
		dwellingPage.continueButton.click();

		// Verify the Drop Down and Check if 2% value is available
		Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
				"Create Quote Page", "Create Quote Page loaded successfully", false, false);
		createQuotePage.namedHurricaneDeductibleArrow.scrollToElement();
		createQuotePage.namedHurricaneDeductibleArrow.click();
		Assertions.addInfo("Scenario 04",
				"Verifying the presence of Named Hurricane Deductible value 2% in the dropdown when the address entered is 445 Seaside Ave, Honolulu, HI 96815 as part of endorsement");
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2%").checkIfElementIsDisplayed(),
				true, "Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2%").getData()
						+ " is verified",
				false, false);
		Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

		// click on continue endorsement button
		createQuotePage.continueEndorsementButton.scrollToElement();
		createQuotePage.continueEndorsementButton.click();
		Assertions.passTest("Create Quote Page", "Clicked on Continue endorsement button");

		// clicking on edit Location/Building information hyperlink
		endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
		endorsePolicyPage.editLocOrBldgInformationLink.click();
		Assertions.passTest("Endorse Policy Page", "Clicked on Edit location or building information link");

		// Edit the dwelling details
		Assertions.verify(locationPage.reviewLocation.checkIfElementIsDisplayed(), true, "Location Page",
				"Location Page loaded successfully", false, false);
		locationPage.dwelling1.scrollToElement();
		locationPage.dwelling1.click();

		// Edit Location 1 Dwelling 1 Details
		testData = data.get(dataValue1);
		Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Building Page",
				"Dwelling Page loaded successfully", false, false);
		dwellingPage.addDwellingDetails(testData, 1, 1);
		dwellingPage.reviewDwelling();

		// click on create quote
		dwellingPage.continueButton.scrollToElement();
		dwellingPage.continueButton.click();

		// verify the default value of named hurricane as 10%
		Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
				"Create Quote Page", "Create Quote Page loaded successfully", false, false);
		Assertions.addInfo("Scenario 05",
				"Verifying the Default Named Hurricane Deductible displayed as 10% when the address entered as 779 Kii St Honolulu, HI 96825 as part of endorsement");
		Assertions.verify(createQuotePage.namedHurricaneData.getData(), "10%", "Create Quote Page",
				"The default Named Hurricane deductible value " + createQuotePage.namedHurricaneData.getData()
						+ " displayed is verified",
				false, false);
		Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

		// verify the Drop Down and Check if 2% (Override is available) and not 2%.
		createQuotePage.namedHurricaneDeductibleArrow.scrollToElement();
		createQuotePage.namedHurricaneDeductibleArrow.click();
		Assertions.addInfo("Scenario 06", "Verifying the presence of Named Hurricane Deductible value 2%(Override)");
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2% (Override)")
						.checkIfElementIsDisplayed(),
				true, "Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2% (Override)").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("10%").waitTillVisibilityOfElement(60);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("10%").scrollToElement();
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("10%").click();
		Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

		// Edit Cov A value
		testData = data.get(dataValue2);
		createQuotePage.cova_NH.scrollToElement();
		createQuotePage.cova_NH.clearData();
		createQuotePage.cova_NH.tab();
		createQuotePage.loading.waitTillInVisibilityOfElement(60);
		createQuotePage.cova_NH.appendData(testData.get("L1D1-DwellingCovA"));
		createQuotePage.cova_NH.tab();
		createQuotePage.waitTime(2);// if wait time is removed testcase will fail here
		Assertions.passTest("Create Quote Page",
				"Updated the Named Hurricane Coverage A value to " + "$" + createQuotePage.cova_NH.getData());

		// click on continue endorsement button
		createQuotePage.continueEndorsementButton.scrollToElement();
		createQuotePage.continueEndorsementButton.click();
		Assertions.passTest("Create Quote Page", "Clicked on Continue endorsement button");

		// Click on continue
		if (createQuotePage.continueButton.checkIfElementIsPresent()
				&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}

		// click on next button
		endorsePolicyPage.nextButton.scrollToElement();
		endorsePolicyPage.nextButton.click();

		// click on continue
		if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
				&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
			endorsePolicyPage.continueButton.scrollToElement();
			endorsePolicyPage.continueButton.click();
		}

		// click on complete button
		endorsePolicyPage.completeButton.scrollToElement();
		endorsePolicyPage.completeButton.click();
		endorsePolicyPage.completeButton.waitTillInVisibilityOfElement(60);
		endorsePolicyPage.scrollToBottomPage();
		endorsePolicyPage.closeButton.waitTillPresenceOfElement(60);
		endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
		endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
		endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
		endorsePolicyPage.closeButton.scrollToElement();
		endorsePolicyPage.closeButton.click();

		// Click on rewrite policy link
		policySummaryPage.rewritePolicy.scrollToElement();
		policySummaryPage.rewritePolicy.click();
		Assertions.passTest("POlicy Summary Page", "Clicked on Rewrite Policy link");

		// Click on create another quote button
		Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Account Overview Page loaded successfully", false, false);
		accountOverviewPage.createAnotherQuote.scrollToElement();
		accountOverviewPage.createAnotherQuote.click();
		Assertions.passTest("Account Overview Page", "Clicked on Create another quote button");
		Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Create Quote Page loaded successfully", false, false);

		// verify the default value of named hurricane as 10%
		Assertions.verify(createQuotePage.previous.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Create Quote Page loaded successfully", false, false);
		Assertions.addInfo("Scenario 07", "Verifying the Default Named Hurricane Deductible displayed as 10%");
		Assertions.verify(createQuotePage.namedHurricaneData.getData(), "10%", "Create Quote Page",
				"The default Named Hurricane deductible value " + createQuotePage.namedHurricaneData.getData()
						+ " displayed is verified",
				false, false);
		Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

		// verify the Drop Down and Check if 2% (Override is available) and not 2%.
		createQuotePage.namedHurricaneDeductibleArrow.scrollToElement();
		createQuotePage.namedHurricaneDeductibleArrow.click();
		Assertions.addInfo("Scenario 08",
				"Verifying the presence of Named Hurricane Deductible value 2%(Override) in the dropdown");
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2% (Override)")
						.checkIfElementIsDisplayed(),
				true, "Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2% (Override)").getData()
						+ " is verified",
				false, false);
		Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

		// click on previous
		createQuotePage.previous.scrollToElement();
		createQuotePage.previous.click();
		Assertions.passTest("Create Quote Page", "Clicked on Previous Button");

		// click on edit dwelling
		accountOverviewPage.dwelling1.scrollToElement();
		accountOverviewPage.dwelling1.click();
		accountOverviewPage.editDwelling.scrollToElement();
		accountOverviewPage.editDwelling.click();
		Assertions.passTest("Account Overview Page", "Clicked on Edit dwelling");

		// Edit Location 1 Dwelling 1 Details
		testData = data.get(dataValue3);
		Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Building Page",
				"Dwelling Page loaded successfully", false, false);
		dwellingPage.addDwellingDetails(testData, 1, 1);
		dwellingPage.reviewDwelling();

		// click on create quote
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();

		// Verifying the Default Named Hurricane Deductible displayed as 2%
		Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Create Quote Page loaded successfully", false, false);
		Assertions.addInfo("Scenario 09",
				"Verifying the Default Named Hurricane Deductible displayed as 2% when the address entered as 445 Seaside Ave, Honolulu, HI 96815 as part of rewrite");
		Assertions.verify(createQuotePage.namedHurricaneData.getData(), "2%", "Create Quote Page",
				"The default Named Hurricane deductible value " + createQuotePage.namedHurricaneData.getData()
						+ " displayed is verified",
				false, false);
		Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

		// click on get a quote
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		Assertions.passTest("Create Quote Page", "Clicked on Get A quote");

		// click on edit dwelling
		accountOverviewPage.dwelling1.scrollToElement();
		accountOverviewPage.dwelling1.click();
		accountOverviewPage.editDwelling.scrollToElement();
		accountOverviewPage.editDwelling.click();
		Assertions.passTest("Account Overview Page", "Clicked on Edit dwelling");

		// Edit Location 1 Dwelling 1 Details
		testData = data.get(dataValue1);
		Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Building Page",
				"Dwelling Page loaded successfully", false, false);
		dwellingPage.addDwellingDetails(testData, 1, 1);
		dwellingPage.reviewDwelling();

		// click on create quote
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();

		// Verifying the Default Named Hurricane Deductible displayed as 10%
		Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Create Quote Page loaded successfully", false, false);
		Assertions.addInfo("Scenario 10",
				"Verifying the Default Named Hurricane Deductible displayed as 10% when the address entered as 779 Kii St Honolulu, HI 96825 as part of rewrite");
		Assertions.verify(createQuotePage.namedHurricaneData.getData(), "10%", "Create Quote Page",
				"The default Named Hurricane deductible value " + createQuotePage.namedHurricaneData.getData()
						+ " displayed is verified",
				false, false);
		Assertions.addInfo("Scenario 10", "Scenario 10 Ended");

		// verify the Drop Down and Check if 2% (Override is available) and not 2%.
		createQuotePage.namedHurricaneDeductibleArrow.scrollToElement();
		createQuotePage.namedHurricaneDeductibleArrow.click();
		Assertions.addInfo("Scenario 11",
				"Verifying the presence of Named Hurricane Deductible value 2%(Override) in the dropdown");
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2% (Override)")
						.checkIfElementIsDisplayed(),
				true, "Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2% (Override)").getData()
						+ " is verified",
				false, false);
		Assertions.addInfo("Scenario 11", "Scenario 11 Ended");

		// click on get a quote
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		Assertions.passTest("Create Quote Page", "Clicked on Get A quote");

		// Getting the Quote Number
		Assertions.verify(accountOverviewPage.rewriteBind.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Account Overview Page Loaded successfully", false, false);
		quoteLen = accountOverviewPage.quoteNumber.getData().length();
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
		Assertions.passTest("Account Overview Page", "The Rewritten Quote Number is : " + quoteNumber);

		// click on rewrite bind
		accountOverviewPage.rewriteBind.scrollToElement();
		accountOverviewPage.rewriteBind.click();
		Assertions.passTest("Account Overview Page", "Clicked on Rewrite bind");

		// Enter rewrite bind details
		testData = data.get(dataValue2);
		Assertions.verify(requestBindPage.rewrite.checkIfElementIsDisplayed(), true, "Request Bind Page",
				"Request Bind Page loaded successfully", false, false);
		requestBindPage.previousPolicyCancellationDate.waitTillVisibilityOfElement(60);
		requestBindPage.previousPolicyCancellationDate.setData(testData.get("PolicyEffDate"));
		requestBindPage.previousPolicyCancellationDate.tab();

		if (requestBindPage.internalComments.checkIfElementIsPresent()) {
			requestBindPage.internalComments.scrollToElement();
			requestBindPage.internalComments.setData("Test");
		}
		if (requestBindPage.externalComments.checkIfElementIsPresent()) {
			requestBindPage.externalComments.scrollToElement();
			requestBindPage.externalComments.setData("Test");
		}

		// click on rewrite
		requestBindPage.rewrite.scrollToElement();
		requestBindPage.rewrite.click();

		// get the policy number
		Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
				"Policy Summary Page loaded successfully", false, false);
		policyNumber = policySummaryPage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "The Rewritten Policy Number: " + policyNumber);

		// click on renew policy link
		policySummaryPage.renewPolicy.scrollToElement();
		policySummaryPage.renewPolicy.click();
		Assertions.passTest("Policy Summary Page", "Clicked on Renew policy link");

		// Getting the Quote Number
		Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
		quoteLen = accountOverviewPage.quoteNumber.getData().length();
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
		Assertions.passTest("Account Overview Page", "The Renewal Quote Number is : " + quoteNumber);

		// Verify(NH 10%) is displayed below the Renewal Quote Number near the Premium
		// Value of the Renewal Quote.
		Assertions.addInfo("Scenario 12",
				"Verify(NH 10%) is displayed below the Renewal Quote Number near the Premium Value of the Renewal Quote");
		Assertions.verify(accountOverviewPage.renewalOffer.getData().contains("10%"), true, "Account Overview Page",
				"The NH Deductible value near the Premium Value of the Renewal Quote is 10% is verified", false, false);
		Assertions.addInfo("Scenario 12", "Scenario 12 Ended");

		// click on create another quote button
		accountOverviewPage.createAnotherQuote.scrollToElement();
		accountOverviewPage.createAnotherQuote.click();
		Assertions.passTest("Account Overview Page", "Clicked on Create another quote button");

		// Verifying the Default Named Hurricane Deductible displayed as 10%
		Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Create Quote Page loaded successfully", false, false);
		Assertions.addInfo("Scenario 13", "Verifying the Default Named Hurricane Deductible displayed as 10%");
		Assertions.verify(createQuotePage.namedHurricaneData.getData(), "10%", "Create Quote Page",
				"The default Named Hurricane deductible value " + createQuotePage.namedHurricaneData.getData()
						+ " displayed is verified",
				false, false);
		Assertions.addInfo("Scenario 13", "Scenario 13 Ended");

		// verify the Drop Down and Check if 2% (Override is available) and not 2%.
		createQuotePage.namedHurricaneDeductibleArrow.scrollToElement();
		createQuotePage.namedHurricaneDeductibleArrow.click();
		Assertions.addInfo("Scenario 14", "Verifying the presence of Named Hurricane Deductible value 2%(Override)");
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2% (Override)")
						.checkIfElementIsDisplayed(),
				true, "Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2% (Override)").getData()
						+ " is verified",
				false, false);
		Assertions.addInfo("Scenario 14", "Scenario 14 Ended");

		// click on get a quote
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		Assertions.passTest("Create Quote Page", "Clicked on Get A quote");

		// Click on continue
		if (createQuotePage.continueButton.checkIfElementIsPresent()
				&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}

		// click on edit dwelling
		accountOverviewPage.dwelling1.scrollToElement();
		accountOverviewPage.dwelling1.click();
		accountOverviewPage.editDwelling.scrollToElement();
		accountOverviewPage.editDwelling.click();
		Assertions.passTest("Account Overview Page", "Clicked on Edit dwelling");

		// Edit Location 1 Dwelling 1 Details
		testData = data.get(dataValue3);
		Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Building Page",
				"Dwelling Page loaded successfully", false, false);
		dwellingPage.addDwellingDetails(testData, 1, 1);
		dwellingPage.reviewDwelling();

		// click on create quote
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();

		// Verifying the Default Named Hurricane Deductible displayed as 2%
		Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Create Quote Page loaded successfully", false, false);
		Assertions.addInfo("Scenario 15",
				"Verifying the Default Named Hurricane Deductible displayed as 2% when the address entered as 445 Seaside Ave, Honolulu, HI 96815 as part of renewal");
		Assertions.verify(createQuotePage.namedHurricaneData.getData(), "2%", "Create Quote Page",
				"The default Named Hurricane deductible value " + createQuotePage.namedHurricaneData.getData()
						+ " displayed is verified",
				false, false);
		Assertions.addInfo("Scenario 15", "Scenario 15 Ended");

		// click on get a quote
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		Assertions.passTest("Create Quote Page", "Clicked on Get A quote");

		// Click on continue
		if (createQuotePage.continueButton.checkIfElementIsPresent()
				&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}

		// click on edit dwelling
		accountOverviewPage.dwelling1.scrollToElement();
		accountOverviewPage.dwelling1.click();
		accountOverviewPage.editDwelling.scrollToElement();
		accountOverviewPage.editDwelling.click();
		Assertions.passTest("Account Overview Page", "Clicked on Edit dwelling");

		// Edit Location 1 Dwelling 1 Details
		testData = data.get(dataValue1);
		Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Building Page",
				"Dwelling Page loaded successfully", false, false);
		dwellingPage.addDwellingDetails(testData, 1, 1);
		dwellingPage.reviewDwelling();

		// click on create quote
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();

		// Verifying the Default Named Hurricane Deductible displayed as 10%
		Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Create Quote Page loaded successfully", false, false);
		Assertions.addInfo("Scenario 16",
				"Verifying the Default Named Hurricane Deductible displayed as 10% when the address entered as 779 Kii St Honolulu, HI 96825 as part of renewal");
		Assertions.verify(createQuotePage.namedHurricaneData.getData(), "10%", "Create Quote Page",
				"The default Named Hurricane deductible value " + createQuotePage.namedHurricaneData.getData()
						+ " displayed is verified",
				false, false);
		Assertions.addInfo("Scenario 16", "Scenario 16 Ended");

		// verify the Drop Down and Check if 2% (Override is available) and not 2%.
		createQuotePage.namedHurricaneDeductibleArrow.scrollToElement();
		createQuotePage.namedHurricaneDeductibleArrow.click();
		Assertions.addInfo("Scenario 17", "Verifying the presence of Named Hurricane Deductible value 2%(Override)");
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2% (Override)")
						.checkIfElementIsDisplayed(),
				true, "Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2% (Override)").getData()
						+ " is verified",
				false, false);
		Assertions.addInfo("Scenario 17", "Scenario 17 Ended");

		// Update nh deductible to 7.5%
		testData = data.get(dataValue2);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath(testData.get("NamedHurricaneDedValue"))
				.scrollToElement();
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath(testData.get("NamedHurricaneDedValue"))
				.click();

		// click on get a quote
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		Assertions.passTest("Create Quote Page", "Clicked on Get A quote");

		// Click on continue
		if (createQuotePage.continueButton.checkIfElementIsPresent()
				&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}

		// Getting the renewal quote Number
		Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
		quoteLen = accountOverviewPage.quoteNumber.getData().length();
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
		Assertions.passTest("Account Overview Page", "The Renewal Quote Number is : " + quoteNumber);

		// click on release renewal to producer btn
		accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
		accountOverviewPage.releaseRenewalToProducerButton.click();
		Assertions.passTest("Account Overview Page", "Clicked on Release renewal to producer button");

		// Signing out as USM
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		Assertions.passTest("Home Page", "Logged out as USM successfully");

		// Login as producer
		loginPage.refreshPage();
		Assertions.verify(loginPage.signInButton.checkIfElementIsDisplayed(), true, "Login Page",
				"Login page loaded successfully", false, false);
		loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
		Assertions.passTest("Login Page", "Logged in as Producer " + setUpData.get("NahoProducer") + " Successfully");

		// search the renewal quote
		Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
				"Producer Home Page loaded successfully", false, false);
		homePage.searchQuoteByProducer(quoteNumber);
		Assertions.passTest("Home Page", "Searched the quote " + quoteNumber + " successfully");

		// click on edit deductibles and limits
		Assertions.verify(accountOverviewPage.editDeductibleAndLimits.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Account Overview Page loaded successfully", false, false);
		accountOverviewPage.editDeductibleAndLimits.scrollToElement();
		accountOverviewPage.editDeductibleAndLimits.click();
		Assertions.passTest("Account Overview Page", "Clicked on Edit deductibles and limits");

		// Verifying the Default Named Hurricane Deductible displayed as 10%
		Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Create Quote Page loaded successfully", false, false);
		Assertions.addInfo("Scenario 18", "Verifying the Default Named Hurricane Deductible displayed as 10%");
		Assertions.verify(createQuotePage.namedHurricaneData.getData(), "10%", "Create Quote Page",
				"The default Named Hurricane deductible value " + createQuotePage.namedHurricaneData.getData()
						+ " displayed is verified",
				false, false);
		Assertions.addInfo("Scenario 18", "Scenario 18 Ended");

		// Verify the global warning message
		Assertions.addInfo("Scenario 19",
				"Verify the message The previous quote had one or more deductibles that were below the minimum allowed. They will re-set to the appropriate minimum");
		Assertions.verify(createQuotePage.alertError.getData().contains("quote had one or more deductible"), true,
				"Create Quote Page", "The message " + createQuotePage.alertError.getData() + " displayed is verified",
				false, false);
		Assertions.addInfo("Scenario 19", "Scenario 19 Ended");

		// Go to Home Page
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		Assertions.passTest("Home Page", PAGE_NAVIGATED);

		// quite the browser
		Assertions.passTest("PNB_Regression_TC013", "Executed Successfully");
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);

	}
}