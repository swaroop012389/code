/** Program Description: Assert the warning and error messages for different building details
 *  Author			   : John
 *  Date of Modified   : 09/29/2021
 **/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.WebDriverManager;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExistingAccountPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC096 extends AbstractCommercialTest {

	public TC096() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID096.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		ExistingAccountPage existingAccountPage = new ExistingAccountPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approve_DeclineQuotePagelPage = new ApproveDeclineQuotePage();

		// Initializing the variables
		int data_Value1 = 0;
		int data_Value3 = 2;
		int data_Value4 = 3;
		String quoteNumber;
		Map<String, String> testData = data.get(data_Value1);
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

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
			//"Building Page loaded successfully", false, false);
			locationPage.addBuildingsButton.scrollToElement();
			locationPage.addBuildingsButton.click();
			buildingPage.addBuildingDetails(testData, 1, 1);
			buildingPage.waitTime(2);
			// buildingPage.addBuildingOccupancy(testData, 1, 1);
			buildingPage.buildingOccupancyLink.waitTillVisibilityOfElement(60);
			buildingPage.buildingOccupancyLink.scrollToElement();
			buildingPage.buildingOccupancyLink.click();
			buildingPage.buildingOccupancy_yes.scrollToElement();
			buildingPage.buildingOccupancy_yes.click();
			buildingPage.addBuildingPrimaryOccupancy(testData, 1, 1);
			buildingPage.waitTime(1);

			// add secondary occupancy
			if (testData.get("L1B1-SecondaryOccupancyCode") != null
					&& !(testData.get("L1B1-SecondaryOccupancyCode").equals(""))) {
				if (!buildingPage.secondaryOccupancy.getData().equals("")) {
					WebElement ele = WebDriverManager.getCurrentDriver().findElement(By.xpath(
							"//div[label[a[contains(text(),'Secondary Occupancy')]]]//following-sibling::div//input[contains(@id,'secondaryOccupancy')]"));
					ele.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
					buildingPage.waitTime(2);
					buildingPage.secondaryOccupancy.tab();
				}
				buildingPage.setOccupancyJS("questionssecondary", testData.get("L1B1-SecondaryOccupancyCode"),
						testData.get("Peril"), testData.get("QuoteState"));
				buildingPage.waitTime(2);
			}
			buildingPage.waitTime(3);// if waittime is removed it is not going to if condition
			buildingPage.secondaryPercentOccupied.scrollToElement();
			buildingPage.secondaryPercentOccupied.setData(testData.get("L1B1-SecOccupancyPercent"));
			buildingPage.waitTime(3);// if waittime is removed test case wil fail here
			buildingPage.enterAdditionalBuildingInformation(testData, 1, 1);
			buildingPage.addRoofDetails(testData, 1, 1);
			buildingPage.enterBuildingValues(testData, 1, 1);

			// Click on review Building
			buildingPage.reviewBuilding.waitTillPresenceOfElement(60);
			buildingPage.reviewBuilding.waitTillVisibilityOfElement(60);
			buildingPage.reviewBuilding.waitTillElementisEnabled(60);
			buildingPage.reviewBuilding.waitTillButtonIsClickable(60);
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();
			Assertions.passTest("Building Page", "Clicked on Review Building");

			// click on override button
			if (buildingPage.pageName.getData().contains("Existing Account Found")) {
				existingAccountPage = new ExistingAccountPage();
				existingAccountPage.OverrideExistingAccount();
			}

			// click on create quote
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();

			// click on override
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			// Selecting a peril
			testData = data.get(data_Value1);
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Prior Losses
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);

			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);

			// Asserting Mold and Ord/law are not present
			Assertions.addInfo("Create Quote Page", "Asserting the absence of Mold arrow and Ordinance Law arrow");
			Assertions.verify(createQuotePage.moldArrow.checkIfElementIsPresent(), false, "Create Quote Page",
					"Mold arrow not present is verified", false, false);

			Assertions.verify(createQuotePage.ordinanceLawArrow.checkIfElementIsPresent(), false, "Create Quote Page",
					"Ordinance/Law arrow not present is verified", false, false);

			// Click on previous
			createQuotePage.previous.scrollToElement();
			createQuotePage.previous.click();

			// click on building link
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).click();

			// click on edit building link
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Building");
			Assertions.verify(buildingPage.reviewBuilding.checkIfElementIsDisplayed(), true, "Building Page",
					"Building Page loaded successfully", false, false);

			// click on building occupancy link
			buildingPage.buildingOccupancyLink.scrollToElement();
			buildingPage.buildingOccupancyLink.click();

			testData = data.get(data_Value3);
			// Changing the percent occupancy to 79%
			if (buildingPage.primaryPercentOccupied.checkIfElementIsPresent()
					&& buildingPage.primaryPercentOccupied.checkIfElementIsDisplayed()) {
				Assertions.addInfo("Building Page", "Changing the Primary Percent Occupied to 79%");
				buildingPage.primaryPercentOccupied.setData(testData.get("L1B1-PercentOccupied"));
				buildingPage.primaryPercentOccupied.tab();
				Assertions.passTest("Building Page",
						"Changed the Primary Percent Occupied to " + buildingPage.primaryPercentOccupied.getData());
			}

			// click on review building and create quote
			buildingPage.reviewBuilding();
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();
			Assertions.passTest("Building Page", "Clicked on Create Quote");

			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			// Verifying the presence of Gl option
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			Assertions.verify(selectPerilPage.gLandAllOtherPerils.checkIfElementIsDisplayed(), true,
					"Select Peril Page", "GL Option displayed is verified", false, false);

			// click on previous button
			selectPerilPage.previousButton.scrollToElement();
			selectPerilPage.previousButton.click();

			// click on building link
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).click();

			// click on edit buidling
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Building");
			Assertions.verify(buildingPage.reviewBuilding.checkIfElementIsDisplayed(), true, "Building Page",
					"Building Page loaded successfully", false, false);

			buildingPage.roofDetailsLink.scrollToElement();
			buildingPage.roofDetailsLink.click();
			buildingPage.yearRoofLastReplaced.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
			buildingPage.yearRoofLastReplaced.setData(testData.get("L1B1-BldgRoofAge"));

			// review building
			buildingPage.reviewBuilding.waitTillPresenceOfElement(TIME_OUT_SIXTY_SECS);
			buildingPage.reviewBuilding.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
			buildingPage.reviewBuilding.waitTillElementisEnabled(TIME_OUT_SIXTY_SECS);
			buildingPage.reviewBuilding.waitTillButtonIsClickable(TIME_OUT_SIXTY_SECS);
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();

			// Asserting the error message
			Assertions.addInfo("Building Page",
					"Assert the Error message when Construction type=Joisted Masonry and Roof Age = Older than 25 years");
			Assertions.verify(buildingPage.globalError.checkIfElementIsDisplayed(), true, "Building Page",
					"The Error message " + buildingPage.globalError.getData() + " displayed is verified", false, false);
			testData = data.get(data_Value4);

			// Changing the Construction type and year built
			// Construction type = Modified Fire Resistive and year built=1949
			Assertions.addInfo("Building Page",
					"Changing the Construction type = Modified Fire Resistive and  year built=1949");
			Assertions.addInfo("Building Page",
					"Building Construction Type original Value : " + buildingPage.constructionTypeData.getData());
			buildingPage.constructionTypeArrow.waitTillVisibilityOfElement(60);
			buildingPage.constructionTypeArrow.scrollToElement();
			buildingPage.constructionTypeArrow.click();
			buildingPage.constructionTypeOption.formatDynamicPath(testData.get("L1B1-BldgConstType")).scrollToElement();
			buildingPage.constructionTypeOption.formatDynamicPath(testData.get("L1B1-BldgConstType")).click();
			Assertions.passTest("Building Page",
					"Building Construction Type Latest Value : " + buildingPage.constructionTypeData.getData());
			Assertions.addInfo("Building Page", "Year Built original Value : " + buildingPage.yearBuilt.getData());
			buildingPage.yearBuilt.setData(testData.get("L1B1-BldgYearBuilt"));
			buildingPage.yearBuilt.tab();
			Assertions.passTest("Building Page", "Year Built Latest Value : " + buildingPage.yearBuilt.getData());

			// click on create quote
			buildingPage.scrollToBottomPage();
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();
			Assertions.passTest("Building Page", "Clicked on Create Quote");

			// Asserting the error messages
			Assertions.addInfo("Building Page", "Assert the Error message when year built is 1949");
			Assertions.verify(buildingPage.yearBuiltErrormessage.checkIfElementIsDisplayed(), true, "Building Page",
					"The Error message " + buildingPage.yearBuiltErrormessage.getData() + " displayed is verified",
					false, false);

			// Added ticket IO-21800
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Clicking on filter on the my referral section
			homePage.myReferralArrow.scrollToElement();
			homePage.myReferralArrow.click();

			// Filtering by Quote option
			homePage.myReferralsOption.formatDynamicPath("Quote").scrollToElement();
			homePage.myReferralsOption.formatDynamicPath("Quote").click();

			// Based on 'In progress' quote status getting the quote number.
			homePage.userResultStatus.formatDynamicPath(1).checkIfElementIsPresent();
			homePage.userResultStatus.formatDynamicPath(1).scrollToElement();
			quoteNumber = homePage.userResultStatus.formatDynamicPath(1).getData();
			homePage.searchQuote(quoteNumber);

			// Click on Open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();

			// Approve Referral
			if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
				referralPage.pickUp.scrollToElement();
				referralPage.pickUp.click();
			}
			referralPage.approveOrDeclineRequest.waitTillButtonIsClickable(60);
			referralPage.approveOrDeclineRequest.scrollToElement();
			referralPage.approveOrDeclineRequest.click();

			Assertions.addInfo("Scenario 1",
					"Verifying that PBU referral quote has AAL and ELR information on the Approve Decline Quote Page.");
			if (approve_DeclineQuotePagelPage.aAL.checkIfElementIsPresent()) {
				approve_DeclineQuotePagelPage.aAL.scrollToElement();
				Assertions.verify(approve_DeclineQuotePagelPage.aAL.getData().contains("$"), true,
						"Approve Decline Quote Page",
						"AAL is present with respective value on the approve decline quote page is verified.", false,
						false);
				Assertions.passTest("AAL value is: ", approve_DeclineQuotePagelPage.aAL.getData());
			} else {
				Assertions.verify(approve_DeclineQuotePagelPage.aAL.checkIfElementIsPresent(), true,
						"Approve Decline Quote Page", "Value is missing for AAL on the Approve Decline Quote Page.",
						false, false);
			}

			if (approve_DeclineQuotePagelPage.eLR.formatDynamicPath(3).checkIfElementIsPresent()) {
				approve_DeclineQuotePagelPage.eLR.formatDynamicPath(3).scrollToElement();
				Assertions.verify(approve_DeclineQuotePagelPage.eLR.formatDynamicPath(3).getData().contains("%"), true,
						"Approve Decline Quote Page",
						"ELR is present with respective value on the approve decline quote page is verified.", false,
						false);
				Assertions.passTest("ELR value is: ", approve_DeclineQuotePagelPage.eLR.formatDynamicPath(3).getData());
			} else {
				Assertions.verify(approve_DeclineQuotePagelPage.aAL.checkIfElementIsPresent(), true,
						"Approve Decline Quote Page", "Value is missing for ELR on the Approve Decline Quote Page.",
						false, false);
			}

			Assertions.addInfo("Scenario", "Scenario Ended");

			// ticket IO-21800 has ended

			// signout and close browser
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 96", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 96", "Executed Successfully");
			}
		}
	}
}
