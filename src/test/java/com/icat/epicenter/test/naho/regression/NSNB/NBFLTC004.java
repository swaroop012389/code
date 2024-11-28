/** Program Description: To Verify hardstop for year built prior to 1950 both as USM and producer,and to verify water damage exclusion form automatic attachment,AOWH deductible will be set to the AOP deductible for external users and AOWH deductibles will not be selectable by an internal user
 *  Author			   : Yeshashwini
 *  Date of Creation   : 03/29/2021
 **/
package com.icat.epicenter.test.naho.regression.NSNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.DateConversions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.ModifyForms;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class NBFLTC004 extends AbstractNAHOTest {

	public NBFLTC004() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSNB/FL004.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the Objects
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		ModifyForms modifyForms = new ModifyForms();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		DateConversions date = new DateConversions();
		int data_Value1 = 0;
		Map<String, String> testData = data.get(data_Value1);
		String insuredName = testData.get("InsuredName") + date.getCurrentDate("MM/dd/YYYY_hh:mm:ss");
		boolean isTestPassed = false;

		try {
			// Create New Account
			homePage.enterPersonalLoginDetails();
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);

			homePage.createNewAccountProducer.waitTillPresenceOfElement(60);
			homePage.createNewAccountProducer.waitTillVisibilityOfElement(60);
			homePage.createNewAccountProducer.moveToElement();
			homePage.createNewAccountProducer.click();

			homePage.namedInsured.waitTillVisibilityOfElement(60);
			homePage.namedInsured.setData(insuredName);
			Assertions.passTest("Home Page", "Insured Name is " + insuredName);

			if (homePage.producerNumber.checkIfElementIsPresent()) {
				homePage.producerNumber.setData("11250.1");
			}
			if (homePage.productArrow.checkIfElementIsPresent() && homePage.productArrow.checkIfElementIsDisplayed()) {
				homePage.productArrow.scrollToElement();
				homePage.productArrow.click();
				homePage.productSelection.formatDynamicPath(testData.get("ProductSelection")).click();
			}
			if (homePage.effectiveDate.formatDynamicPath(1).checkIfElementIsPresent()
					&& homePage.effectiveDate.formatDynamicPath(1).checkIfElementIsDisplayed()) {
				homePage.effectiveDate.formatDynamicPath(1).scrollToElement();
				homePage.effectiveDate.formatDynamicPath(1).waitTillVisibilityOfElement(60);
				homePage.effectiveDate.formatDynamicPath(1).setData(testData.get("PolicyEffDate"));
			} else {
				homePage.effectiveDate.formatDynamicPath(2).scrollToElement();
				homePage.effectiveDate.formatDynamicPath(2).waitTillVisibilityOfElement(60);
				homePage.effectiveDate.formatDynamicPath(2).setData(testData.get("PolicyEffDate"));
			}
			homePage.goButton.click();
			homePage.loading.waitTillInVisibilityOfElement(60);
			Assertions.passTest("Home Page", "New account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Changing the script Because of the CR IO-19320
			// Asserting the Hardstop message for the year built 1945
			if (dwellingPage.protectionClassWarMsg.checkIfElementIsPresent()
					&& dwellingPage.protectionClassWarMsg.checkIfElementIsDisplayed()) {
				Assertions.verify(dwellingPage.protectionClassWarMsg.getData().contains("year built prior to 1980"),
						true, "Dwelling Page", "The Year Built Harstop message "
								+ dwellingPage.protectionClassWarMsg.getData() + " displayed is verified",
						false, false);

			}

			// Entering prior loss details
			else {
				if (!testData.get("PriorLoss1").equals("")) {
					Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true,
							"Prior Loss Page", "Prior Loss Page loaded successfully", false, false);
					priorLossesPage.selectPriorLossesInformation(testData);
				}

				// Entering Create quote page Details
				Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
						"Create Quote Page loaded successfully", false, false);
				Assertions.addInfo("Scenario 01", "Verify hardstop for year built prior to 1950");
				createQuotePage.enterQuoteDetailsNAHO(testData);
				Assertions.verify(createQuotePage.globalErr.checkIfElementIsDisplayed(), true, "Create Quote Page",
						"For Producer : " + createQuotePage.globalErr.getData() + " is verified", false, false);
				Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
			}
			// Signing out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged out as Producer successfully");

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");

			// search for account
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.findAccountNamedInsured.setData(insuredName);
			homePage.findBtnAccount.click();
			if (accountOverviewPage.quoteAccountButton.checkIfElementIsPresent()
					&& accountOverviewPage.quoteAccountButton.checkIfElementIsDisplayed()) {
				accountOverviewPage.quoteAccountButton.scrollToElement();
				accountOverviewPage.quoteAccountButton.click();
			} else {
				accountOverviewPage.editDwelling.scrollToElement();
				accountOverviewPage.editDwelling.click();
				dwellingPage.reviewDwelling.scrollToElement();
				dwellingPage.reviewDwelling.click();
				dwellingPage.override.scrollToElement();
				dwellingPage.override.click();
				dwellingPage.createQuote.scrollToElement();
				dwellingPage.createQuote.click();
				// Entering prior loss details
				if (!testData.get("PriorLoss1").equals("")) {
					Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true,
							"Prior Loss Page", "Prior Loss Page loaded successfully", false, false);
					priorLossesPage.selectPriorLossesInformation(testData);
				}

				// Entering Create quote page Details
				Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
						"Create Quote Page loaded successfully", false, false);
				Assertions.addInfo("Scenario 01", "Verify hardstop for year built prior to 1950");
				createQuotePage.enterQuoteDetailsNAHO(testData);
				Assertions.verify(createQuotePage.globalErr.checkIfElementIsDisplayed(), true, "Create Quote Page",
						"For Producer : " + createQuotePage.globalErr.getData() + " is verified", false, false);
				Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
			}
			Assertions.addInfo("Scenario 02",
					"Verify whether water damage exclusion form is attached and hardstop for year built prior to 1950 for USM");

			// to verify whether water damage exclusion form is attached
			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();
			Assertions.verify(modifyForms.waterDamageExclusionTenThousand.checkIfElementIsSelected(), true,
					"Create Quote Page",
					"Risks that are 40 years or older  will automatically attach Water Damage Exclusion Sublimit Ten Thousand form is verified",
					false, false);
			modifyForms.waterDamageExclusionTenThousand.scrollToElement();
			modifyForms.waterDamageExclusionTenThousand.deSelect();
			Assertions.verify(modifyForms.waterDamageExclusionTenThousand.checkIfElementIsSelected(), false,
					"Create Quote Page",
					"For Risks that are 40 years or older,the form can be removed by an internal user is verified",
					false, false);
			modifyForms.override.scrollToElement();
			modifyForms.override.click();
			Assertions.verify(createQuotePage.globalErr.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"For USM : " + createQuotePage.globalErr.getData() + " is verified", false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Clicking on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBFLTC004 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBFLTC004 ", "Executed Successfully");
			}
		}
	}
}