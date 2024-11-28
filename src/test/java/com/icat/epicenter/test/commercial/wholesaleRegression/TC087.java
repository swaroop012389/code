/** Program Description: Assert the warning messages for different building Details
 *  Author			   : John
 *  Date of Creation   : 07/30/2020
 **/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.NetServAutomationFramework.config.EnvironmentDetails;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.WebDriverManager;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.IntegrationsAdminPage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC087 extends AbstractCommercialTest {

	public TC087() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID087.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		IntegrationsAdminPage integrationsAdminPage = new IntegrationsAdminPage();

		// Initializing the variables
		String quoteNumber;
		String policyNumber;
		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
		int dataValue4 = 3;
		Map<String, String> testData = data.get(dataValue1);
		Map<String, String> testData1 = data.get(dataValue2);
		Map<String, String> testData2 = data.get(dataValue3);
		Map<String, String> testData3 = data.get(dataValue4);
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
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

			// Entering Location 1 Dwelling 1 Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// clicking on request bind button
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// getting the quote number
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Click on Request bind page
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

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			// carrier selection
			if (requestBindPage.carrierWarningMsg.checkIfElementIsPresent()
					&& requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed()) {
				requestBindPage.approveRequestCommercialData(testData);
			} else {
				requestBindPage.approveRequest();
			}
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// Endorse fee only
			Assertions.addInfo("Policy Summary Page", "Initiating Endorsement to edit Location and Building Details");
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();

			// setting Endorsement Effective Date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// clicking on editLocation or building hyperlink
			endorsePolicyPage.editLocOrBldgInformationLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();
			endorsePolicyPage.loading.waitTillInVisibilityOfElement(60);

			// Edit building
			locationPage.buildingLink.formatDynamicPath("1", "1").waitTillVisibilityOfElement(60);
			locationPage.buildingLink.formatDynamicPath("1", "1").scrollToElement();
			locationPage.buildingLink.formatDynamicPath("1", "1").click();
			Assertions.passTest("Location Page", "Clicked on Building 1-1");

			// set building=$0, BPP=$100000, Occupancy=Glass and Porcelain - Manufacturing
			Assertions.addInfo("Building Page",
					"Entering building value =$0, BPP=$100000, Occupancy=Glass and Porcelain - Manufacturing");
			buildingPage.buildingOccupancyLink.scrollToElement();
			buildingPage.buildingOccupancyLink.click();
			buildingPage.waitTime(3);// need wait time otherwise test case will fail here

			if (testData1.get("L1B1-PrimaryOccupancyCode") != null
					&& !testData1.get("L1B1-PrimaryOccupancyCode").equalsIgnoreCase("")) {
				if (!buildingPage.primaryOccupancy.getData().equals("")) {
					WebElement ele = WebDriverManager.getCurrentDriver().findElement(By.xpath(
							"//div[label[a[contains(text(),'Primary Occupancy')]]]//following-sibling::div//input[contains(@id,'primaryOccupancy')]"));
					ele.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
					ele.sendKeys(testData1.get("L1B1-PrimaryOccupancy"));
					buildingPage.primaryOccupancyLink.formatDynamicPath(testData1.get("L1B1-PrimaryOccupancy"))
							.waitTillVisibilityOfElement(60);
					buildingPage.primaryOccupancyLink.formatDynamicPath(testData1.get("L1B1-PrimaryOccupancy"))
							.scrollToElement();
					buildingPage.primaryOccupancyLink.formatDynamicPath(testData1.get("L1B1-PrimaryOccupancy")).click();
				} else {
					buildingPage.setOccupancyJS("primary", testData1.get("L1B1-PrimaryOccupancyCode"),
							testData.get("Peril"), testData.get("QuoteState"));
				}
			}
			buildingPage.waitTime(2);// need wait time to load the page
			buildingPage.editBuildingValuesPNB(testData1, 1, 1);
			Assertions.passTest("Building Page", "Building value updated to " + testData1.get("L1B1-BldgValue")
					+ " and BPP value updated to " + testData1.get("L1B1-BldgBPP"));

			// Review dwelling and assert warning message
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();
			Assertions.addInfo("Building Page",
					"Assert the Error message when building value =$0, BPP=$100000, Occupancy=Glass and Porcelain - Manufacturing");
			Assertions.verify(buildingPage.roofAgeWarningMessage.getData().contains(
					"BPP/TIB Coverage has been removed from the building(s) that do not qualify for that coverage."),
					true, "Building Page", buildingPage.roofAgeWarningMessage.getData() + " is displayed", false,
					false);

			// click on continue
			buildingPage.continueButton.scrollToElement();
			buildingPage.continueButton.click();

			// click on continue on create quote page
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create quote page loaded successfully", false, false);
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();

			Assertions.addInfo("Create Quote Page",
					"Assert the Error message when building value =$0, BPP=$100000, Occupancy=Glass and Porcelain - Manufacturing");
			Assertions.verify(
					createQuotePage.globalErr.getData()
							.contains("There must be at least one non-$0 coverage for this building."),
					true, "Create Quote Page", createQuotePage.globalErr.getData() + " is displayed", false, false);

			// Go to home page and search the policy
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchPolicy(policyNumber);

			// Click on endorse PB link
			Assertions.addInfo("Policy Summary Page", "Initiating Endorsement to edit Location and Building Details");
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();

			if (endorsePolicyPage.okButton.checkIfElementIsPresent()
					&& endorsePolicyPage.okButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.okButton.scrollToElement();
				endorsePolicyPage.okButton.click();
			}

			// click on endorse PB link
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();

			// setting Endorsement Effective Date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// clicking on editLocation or building hyperlink
			endorsePolicyPage.editLocOrBldgInformationLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();
			endorsePolicyPage.loading.waitTillInVisibilityOfElement(60);

			// Edit building
			locationPage.buildingLink.formatDynamicPath("1", "1").waitTillVisibilityOfElement(60);
			locationPage.buildingLink.formatDynamicPath("1", "1").scrollToElement();
			locationPage.buildingLink.formatDynamicPath("1", "1").click();
			Assertions.passTest("Location Page", "Clicked on Building 1-1");

			// Update construction type=Reinforced Masonry, year built=1949,
			// Occupancy=Office, Building=2000000
			Assertions.addInfo("Building Page",
					"Update the construction type=Reinforced Masonry, year built=1949,Occupancy=Office, Building value=2000000");
			buildingPage.constructionTypeArrow.waitTillVisibilityOfElement(60);
			buildingPage.constructionTypeArrow.scrollToElement();
			buildingPage.constructionTypeArrow.click();
			buildingPage.constructionTypeOption.formatDynamicPath(testData2.get("L1B1-BldgConstType"))
					.scrollToElement();
			buildingPage.constructionTypeOption.formatDynamicPath(testData2.get("L1B1-BldgConstType")).click();
			Assertions.passTest("Building Page", "Contruction Type Updated to " + testData2.get("L1B1-BldgConstType"));
			buildingPage.yearBuilt.setData(testData2.get("L1B1-BldgYearBuilt"));
			Assertions.passTest("Building Page", "Year Built Updated to " + testData2.get("L1B1-BldgYearBuilt"));
			buildingPage.editBuildingOccupancyPNB(testData2, 1, 1);
			buildingPage.waitTime(3);// need wait time to load the page
			buildingPage.enterBuildingValues(testData2, 1, 1);
			Assertions.passTest("Building Page", "Building value updated to " + testData2.get("L1B1-BldgValue")
					+ " and BPP value updated to " + testData2.get("L1B1-BldgBPP"));

			// Re-submit dwelling and assert warning message
			buildingPage.scrollToBottomPage();
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();
			Assertions.addInfo("Building Page",
					"Assert the Warning message when construction type=Reinforced Masonry, year built=1949,Occupancy=Office, Building value=2000000");
			Assertions.verify(buildingPage.roofAgeWarningMessage.getData().contains(
					"Due to the age of this risk, this building may be located on the National Historic building registry"),
					true, "Building Page", buildingPage.roofAgeWarningMessage.getData() + " is displayed", false,
					false);

			// click on Edit building
			buildingPage.editBuilding.waitTillButtonIsClickable(60);
			buildingPage.editBuilding.scrollToElement();
			buildingPage.editBuilding.click();

			// Update construction year built=1999, Building=10100000
			Assertions.addInfo("Building Page", "Update the Year Built=1999 and Building value=10100000");
			buildingPage.yearBuilt.setData(testData3.get("L1B1-BldgYearBuilt"));
			Assertions.passTest("Building Page", "Year Built Updated to " + testData3.get("L1B1-BldgYearBuilt"));
			buildingPage.waitTime(3);// need waittime to load the page
			buildingPage.enterBuildingValues(testData3, 1, 1);
			Assertions.passTest("Building Page", "Building value updated to " + testData3.get("L1B1-BldgValue"));
			buildingPage.reviewBuilding.click();

			// click on continue
			buildingPage.continueButton.scrollToElement();
			buildingPage.continueButton.click();

			// Create new quote
			createQuotePage.continueEndorsementButton.waitTillVisibilityOfElement(60);
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();

			// Assert warning message for insured value
			Assertions.addInfo("Building Page",
					"Assert the Warning message when the Year Built=1999 and Building value=10100000");
			Assertions.verify(
					endorsePolicyPage.insuredValueMsg.getData()
							.contains("This account violates UW guidelines due to the insured value"),
					true, "Endorse Policy Page",
					"Warning message " + endorsePolicyPage.insuredValueMsg.getData() + " is displayed", false, false);
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();

			// Complete endorsement
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Complete endorement
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			endorsePolicyPage.closeButton.waitTillInVisibilityOfElement(60);

			// verifying endorsement record in policy summary page
			Assertions.addInfo("Policy Summary Page", "Verifying endorsement record in policy summary page");
			Assertions.verify(
					policySummarypage.transactionType.formatDynamicPath(testData.get("TransactionType")).getData(),
					testData.get("TransactionType"), "Policy Summary Page", "Endorsement Transaction is Recorded",
					false, false);
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// verify new business and endt transactions do not go to BIN
			// logout

			if (EnvironmentDetails.getEnvironmentDetails().getEpicenterUrl().contains("q1")) {
				WebDriverManager.getCurrentDriver().navigate()
						.to("https://icat-int-internal-q1-01.mrshmc.com/integrations-admin/");
			} else if (EnvironmentDetails.getEnvironmentDetails().getEpicenterUrl().contains("uat1")) {
				WebDriverManager.getCurrentDriver().navigate()
						.to("https://icat-int-internal-u1-01.mrshmc.com/integrations-admin/");
			} else if (EnvironmentDetails.getEnvironmentDetails().getEpicenterUrl().contains("uat2")) {
				WebDriverManager.getCurrentDriver().navigate()
						.to("https://icat-int-internal-u2-01.mrshmc.com/integrations-admin/");
			}

			integrationsAdminPage.intAdminLogin();
			integrationsAdminPage.checkBINStatus(policyNumber);

			// make sure BIN text isn't in the table
			Assertions.verify(!integrationsAdminPage.resultsTable.getData().contains("BIN"), true,
					"Integrations Admin Document Delivery Page", "Wholesale Transaction Sent to BIN", false, false);

			integrationsAdminPage.intAdminLogout();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 87", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 87", "Executed Successfully");
			}
		}
	}
}
