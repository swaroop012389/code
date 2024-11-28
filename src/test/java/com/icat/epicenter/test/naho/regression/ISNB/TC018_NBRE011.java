package com.icat.epicenter.test.naho.regression.ISNB;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC018_NBRE011 extends AbstractNAHOTest {

	public TC018_NBRE011() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBRE011.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();

		// Initializing Variables
		Map<String, String> testData;
		int data_Value1 = 0;

		testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Creating a new account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Checking the processing of Zipcode
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering dwelling details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering Prior Loss details
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);

			priorLossesPage.lossesInThreeYearsYes.click();
			priorLossesPage.typeOfLossArrow.waitTillVisibilityOfElement(60);
			priorLossesPage.typeOfLossArrow.click();
			// Getting all options from loss type and verifying the options
			BaseWebElementControl optionsApp = new BaseWebElementControl(
					By.xpath("//label[contains(text(),'Type of Loss')]//following-sibling::span//a"));

			boolean mechanical = false;
			boolean flood = false;
			boolean identityFraud = false;

			for (int i = 0; i < optionsApp.getNoOfWebElements(); i++) {
				BaseWebElementControl opt = new BaseWebElementControl(By.xpath(
						"(//select[contains(@id,'lossType')]/..//span[contains(@id,'lossTypeSelectBoxItContainer')]//a)["
								+ (i + 1) + "]"));
				if (opt.getData().equals("Mechanical Breakdown") && !mechanical) {
					Assertions.verify(opt.getData(), "Mechanical Breakdown", "Prior Loss Page",
							"Mechanical Breakdown present in Type of Loss dropdown is verified", false, false);
					mechanical = true;
				}
				if (opt.getData().equals("Flood") && !flood) {
					Assertions.verify(opt.getData(), "Flood", "Prior Loss Page",
							"Flood present in Type of Loss dropdown is verified", false, false);
					flood = true;
				}
				if (opt.getData().equals("Identity Fraud") && !identityFraud) {
					Assertions.verify(opt.getData(), "Identity Fraud", "Prior Loss Page",
							"Identity Fraud present in Type of Loss dropdown is verified", false, false);
					identityFraud = true;
				}
			}
			priorLossesPage.waitTime(5);
			priorLossesPage.lossesInThreeYearsNo.waitTillVisibilityOfElement(60);
			priorLossesPage.lossesInThreeYearsNo.scrollToElement();
			priorLossesPage.lossesInThreeYearsNo.click();
			priorLossesPage.selectPriorLossesInformation(testData);

			// Entering Quote details
			Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC018 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC018 ", "Executed Successfully");
			}
		}
	}
}
