/** Program Description: Create a Quote and Edit the Inspection Contact and Additional Interest as producer and verify the changed details
 *  Author			   : Sowndarya
 *  Date of Modified   : 09/30/2021
**/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EditAdditionalInterestInformationPage;
import com.icat.epicenter.pom.EditInspectionContactPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC090 extends AbstractCommercialTest {

	public TC090() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID090.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the pages
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		EditAdditionalInterestInformationPage editAdditionalInterestInformationPage = new EditAdditionalInterestInformationPage();
		EditInspectionContactPage editInspectionContactPage = new EditInspectionContactPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();

		// Initializing the variables
		String quoteNumber;
		int dataValue1 = 0;
		Map<String, String> testData = data.get(dataValue1);
		boolean isTestPassed = false;

		try {
			// New Account creation
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"USM Home Page loaded successfully ", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// ZipCode EligibilityPage
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Zipcode Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "ZipCode entered successfully");

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

			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// getting the quote number
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Signing out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as USM successfully");

			// Login as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			homePage.enterPersonalLoginDetails();
			Assertions.passTest("Login Page", "Logged in as Producer successfully");

			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);

			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.passTest("Home Page", "Quote Number " + quoteNumber + " searched successfully");

			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// Adding Inspection contact details
			Assertions.addInfo("Account Overview Page", "Edit the Inspection Contact Details");
			accountOverviewPage.editInspectionContact.scrollToElement();
			accountOverviewPage.editInspectionContact.click();
			Assertions.passTest("Account Overview Page",
					"Clicked on Edit Inspection Contact and Additional Interest Details");

			// click on update
			editInspectionContactPage.update.waitTillPresenceOfElement(60);
			editInspectionContactPage.update.waitTillVisibilityOfElement(60);
			Assertions.verify(editInspectionContactPage.update.checkIfElementIsDisplayed(), true,
					"Edit Inspection Contact Page", "Edit Inspection Contact Page loaded successfully", false, false);
			editInspectionContactPage.inspectionName.scrollToElement();
			editInspectionContactPage.inspectionName.setData(testData.get("InspectionContact"));
			editInspectionContactPage.phoneNumberAreaCode.setData(testData.get("InspectionAreaCode"));
			editInspectionContactPage.phoneNumberPrefix.setData(testData.get("InspectionPrefix"));
			editInspectionContactPage.phoneNumberEnd.setData(testData.get("InspectionNumber"));

			// click on update
			editInspectionContactPage.update.scrollToElement();
			editInspectionContactPage.update.click();
			editInspectionContactPage.update.waitTillInVisibilityOfElement(60);
			Assertions.passTest("Edit Inspection Contact Page", "Entered the Inspection Contact Details successfully");

			accountOverviewPage.editAdditionalIntersets.waitTillVisibilityOfElement(60);
			accountOverviewPage.editAdditionalIntersets.scrollToElement();
			accountOverviewPage.editAdditionalIntersets.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Additional Interests");

			// Asserting the Additional Interests types
			for (int i = 0; i < 6; i++) {
				int dataValuei = i;
				Map<String, String> testDatai = data.get(dataValuei);
				editAdditionalInterestInformationPage.aITypeArrow.waitTillPresenceOfElement(60);
				editAdditionalInterestInformationPage.aITypeArrow.waitTillVisibilityOfElement(60);
				editAdditionalInterestInformationPage.aITypeArrow.scrollToElement();
				editAdditionalInterestInformationPage.aITypeArrow.click();
				String AITypei = editAdditionalInterestInformationPage.aITypeOption
						.formatDynamicPath(0, testDatai.get("1-AIType")).getData();
				Assertions.verify(
						editAdditionalInterestInformationPage.aITypeOption
								.formatDynamicPath(0, testDatai.get("1-AIType")).checkIfElementIsDisplayed(),
						true, "Edit Additional Interest Information Page",
						"The Additional Interest Type  " + AITypei + " displayed is verified", false, false);
				editAdditionalInterestInformationPage.cancel.waitTillVisibilityOfElement(60);
				editAdditionalInterestInformationPage.cancel.scrollToElement();
				editAdditionalInterestInformationPage.cancel.click();

				accountOverviewPage.editAdditionalIntersets.waitTillPresenceOfElement(60);
				accountOverviewPage.editAdditionalIntersets.waitTillVisibilityOfElement(60);
				accountOverviewPage.editAdditionalIntersets.scrollToElement();
				accountOverviewPage.editAdditionalIntersets.click();
			}

			// click on Update
			editAdditionalInterestInformationPage.update.waitTillPresenceOfElement(60);
			editAdditionalInterestInformationPage.update.waitTillVisibilityOfElement(60);
			Assertions.verify(editAdditionalInterestInformationPage.update.checkIfElementIsDisplayed(), true,
					"Edit Additional Interest Information Page",
					"Edit Additional Interest Information Page loaded successfully", false, false);
			editAdditionalInterestInformationPage.addAdditionalInterest(testData);
			Assertions.passTest("Edit Additional Interest Information Page",
					"Additional Interest Details entered successfully");
			editAdditionalInterestInformationPage.update.scrollToElement();
			editAdditionalInterestInformationPage.update.click();

			// Asserting the entered Details
			Assertions.addInfo("Account Overview Page",
					"Assert the Changed Inspection Contact Details on Account overviw page");
			Assertions.verify(accountOverviewPage.prodInspectionContactInfo.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "The Entered Inspection Contact Derails "
							+ accountOverviewPage.prodInspectionContactInfo.getData() + " displayed is verified",
					false, false);

			// Click on View Or Print Full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View or print full quote link");

			String AiType = testData.get("1-AIType");
			String AiName = testData.get("1-AIName");
			String AiAddress = testData.get("1-AIAddr1");
			String AiTypeUi = viewOrPrintFullQuotePage.aiDetails.formatDynamicPath(4).getData();
			String AiNameUi = viewOrPrintFullQuotePage.aiDetails.formatDynamicPath(5).getData();
			String AiAddressUi = viewOrPrintFullQuotePage.aiDetails.formatDynamicPath(6).getData();

			// Verifying the changed AI Details on view print ful quote page
			Assertions.addInfo("View Or Print Full Quote Page",
					"Verifying the changed AI Details on view or print full quote page");
			Assertions.verify(AiTypeUi.contains(AiType), true, "View Or Print Full Quote Page",
					"The AI Type " + AiTypeUi + " displayed is verified", false, false);
			Assertions.verify(AiNameUi.contains(AiName), true, "View Or Print Full Quote Page",
					"The Name " + AiNameUi + " displayed is verified", false, false);
			Assertions.verify(AiAddressUi.contains(AiAddress), true, "View Or Print Full Quote Page",
					"The Address " + AiAddressUi + " displayed is verified", false, false);
			viewOrPrintFullQuotePage.backButton.click();

			// signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 90", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 90", "Executed Successfully");
			}
		}
	}
}
