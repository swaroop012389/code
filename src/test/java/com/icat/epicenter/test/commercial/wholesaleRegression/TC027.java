/** Program Description: Create Wind account with following details in building page Construction Type = Non-Combustible and Year Built = 1987. Roof = Single Ply and 21-25 years old. Occupancy as Agriculture - Agriculture/Food Processing, Greenhouses, Grow Houses, Pole Barns and Check for hard stop. and adedd IO-21092 and IO-21147
 *  Author			   : Sowndarya NH
 *  Date of Creation   : 11/27/2019
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
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC027 extends AbstractCommercialTest {

	public TC027() {
		super(LoginType.PRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID027.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		BuildingPage buildingPage = new BuildingPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ReferralPage referralPage = new ReferralPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		LoginPage loginPage = new LoginPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		String yearOfConstructionErrorMessage;
		String roofAgeErrorMessage;
		String occupancyTypeErrorMessage;
		String quoteNumber;

		// Initializing the variables
		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			homePage.enterPersonalLoginDetails();
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zip-code in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Dwelling Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Location Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);
			Assertions.passTest("Building Page", "Construction Type Non-Combustible entered successfully");
			Assertions.passTest("Building Page", "Year Built 1987 entered successfully");
			Assertions.passTest("Building Page",
					"Occupancy type Agriculture/Food Processing, Greenhouses, Grow Houses, Pole Barns - Agriculture entered successfully");
			Assertions.passTest("Building Page", "Roof cladding Single ply membrane entered successfully");
			Assertions.passTest("Building Page", "Roof Age 21-25 years entered successfully");
			Assertions.passTest("Building Page", "Building details entered successfully");

			/*
			 * if (buildingPage.roofDetailsLink.checkIfElementIsPresent() &&
			 * buildingPage.roofDetailsLink.checkIfElementIsDisplayed()) {
			 * buildingPage.roofDetailsLink.waitTillVisibilityOfElement(60);
			 * buildingPage.roofDetailsLink.scrollToElement();
			 * buildingPage.roofDetailsLink.click();
			 * buildingPage.roofLastReplaced.checkIfElementIsPresent();
			 * buildingPage.roofLastReplaced.scrollToElement();
			 * buildingPage.roofLastReplaced.setData("1978"); } if
			 * (buildingPage.createQuote.checkIfElementIsPresent() &&
			 * buildingPage.createQuote.checkIfElementIsDisplayed()) {
			 * buildingPage.reSubmit.checkIfElementIsDisplayed();
			 * buildingPage.reSubmit.scrollToElement(); buildingPage.reSubmit.click();
			 * buildingPage.waitTime(2); buildingPage.reSubmit.click(); }
			 */

			// Verifying the HardStop messages
			// Occupancy type error message 'Coverage is not available on buildings of the
			// selected occupancy type'
			// Construction year error message, 'This building is in-eligible because of its
			// year of construction. Sorry we can't help you out on this one'
			// Roof Age and Roof Cladding error message, 'Single Ply Membrane roofs that are
			// 21-25 Years old are not eligible'
			Assertions.addInfo("Scenario 01", "Verifying the HardStop messages");
			occupancyTypeErrorMessage = buildingPage.occupancyTypeErrorMessage.getData();
			Assertions.verify(buildingPage.occupancyTypeErrorMessage.checkIfElementIsDisplayed(), true, "Building Page",
					"Occupany type error message, " + occupancyTypeErrorMessage + " is displayed", false, false);
			yearOfConstructionErrorMessage = buildingPage.yearOfConstructionErrorMessage.getData();
			Assertions.verify(buildingPage.yearOfConstructionErrorMessage.checkIfElementIsDisplayed(), true,
					"Building Page",
					"Construction year error message, " + yearOfConstructionErrorMessage + " is displayed", false,
					false);
			roofAgeErrorMessage = buildingPage.roofAgeErrorMessage.getData();
			Assertions.verify(buildingPage.roofAgeErrorMessage.checkIfElementIsDisplayed(), true, "Building Page",
					"Roof Age and Roof Cladding error message, " + roofAgeErrorMessage + " is displayed", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Update the construction type
			testData = data.get(data_Value2);
			buildingPage.constructionTypeArrow.waitTillVisibilityOfElement(60);
			buildingPage.constructionTypeArrow.scrollToElement();
			buildingPage.constructionTypeArrow.click();
			buildingPage.constructionTypeOption.formatDynamicPath(testData.get("L1B1-BldgConstType")).scrollToElement();
			buildingPage.constructionTypeOption.formatDynamicPath(testData.get("L1B1-BldgConstType")).click();
			Assertions.passTest("Building Page",
					"Construction Type is updated to " + buildingPage.constructionTypeData.getData());

			// Adding below code IO-21147
			// Update year built
			buildingPage.yearBuilt.scrollToElement();
			buildingPage.yearBuilt.setData(testData.get("L1B1-BldgYearBuilt"));
			Assertions.passTest("Building Page", "Year Built is updated to " + buildingPage.yearBuilt.getData());

			// Update Occupancy type
			buildingPage.buildingOccupancyLink.waitTillVisibilityOfElement(60);
			buildingPage.buildingOccupancyLink.scrollToElement();
			buildingPage.buildingOccupancyLink.click();
			buildingPage.primaryOccupancy.waitTillVisibilityOfElement(60);
			if (!buildingPage.primaryOccupancy.getData().equals("")) {
				WebElement ele = WebDriverManager.getCurrentDriver()
						.findElement(By.xpath("//input[@id='primaryOccupancy']"));
				ele.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
			}
			buildingPage.primaryOccupancy.setData(testData.get("L1B1-PrimaryOccupancy"));
			buildingPage.primaryOccupancyLink.formatDynamicPath(testData.get("L1B1-PrimaryOccupancy"))
					.waitTillVisibilityOfElement(60);
			buildingPage.primaryOccupancyLink.formatDynamicPath(testData.get("L1B1-PrimaryOccupancy")).click();
			buildingPage.primaryOccupancy.tab();
			buildingPage.primaryOccupancyText.formatDynamicPath(testData.get("L1B1-PrimaryOccupancy"))
					.waitTillVisibilityOfElement(60);
			buildingPage.waitTime(2);// if we remove wait time it is not selecting primary occupancy condition
			Assertions.passTest("Building Page",
					"Occupancy type is updated to " + buildingPage.primaryOccupancy.getData());

			// Update Roof details
			buildingPage.addRoofDetails(testData, 1, 1);
			Assertions.passTest("Building Page", "Roof Age value is updated to " + buildingPage.roofAgeData.getData());

			// Create quote
			buildingPage.scrollToBottomPage();
			buildingPage.createQuote.waitTillVisibilityOfElement(60);
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();

			// Selecting a peril
			testData = data.get(data_Value1);
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Verifying referral message when occupancy = 'Museum - Government/Public
			// Building' as producer
			// Referral message is 'This account includes a museum and requires review by an
			// underwriter.'
			Assertions.addInfo("Scenario 02",
					"Verifying referral message when occupancy = Museum - Government/Public Building as producer");
			Assertions.verify(referQuotePage.referralMessages.formatDynamicPath("museum").getData().contains("museum"),
					true, "Referral page", "The referral message is "
							+ referQuotePage.referralMessages.formatDynamicPath("museum").getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Enter Referral Contact Details
			referQuotePage.contactName.setData(testData.get("ProducerName"));
			referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
			referQuotePage.referQuote.click();

			// getting the quote number
			quoteNumber = referQuotePage.quoteNumberforReferral.getData();
			Assertions.passTest("Refer Quote Page", "The Quote Number is : " + quoteNumber);

			// signing out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Logging in as USM to approve the referral
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login page", "Loged in as usm successfully");

			// Navigating to RenewalQuote1's Account Overview Page
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home page", "Referred quote searched successfully");

			// Click on open link
			accountOverviewPage.openReferralLink.checkIfElementIsPresent();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account overview page", "Clicked on open referral link successfully");

			// Approving the Quote
			referralPage.clickOnApprove();
			approveDeclineQuotePage.clickOnApprove();
			referralPage.close.scrollToElement();
			referralPage.close.click();
			Assertions.passTest("Approve decline quote page", "Referred quote approved successfully");

			// Search quote number
			homePage.searchQuote(quoteNumber);
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// Click on edit deductible and limits link
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);

			// Click on get a quote button
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create quote page", "Clicked on get quote button successfully");

			// Verifying warning message when occupancy = 'Museum - Government/Public
			// Building' as USM
			// Warning message is 'This account includes a museum and requires review by an
			// underwriter.'
			Assertions.addInfo("Scenario 03",
					"Verifying warning message when occupancy = 'Museum - Government/Public Building' as USM");
			Assertions.verify(createQuotePage.warningMessage.getData().contains("museum"), true, "Create quote page",
					"The warning message is " + createQuotePage.warningMessage.getData() + " displayed", false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");
			// IO-21147 Ended

			// signing out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Adding below code IO-21092
			// signing in as Producer
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			Assertions.passTest("Login page", "Loged in as usm successfully");

			// Search quote as producer and navigate account overview page
			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 27", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 27", "Executed Successfully");
			}
		}
	}
}