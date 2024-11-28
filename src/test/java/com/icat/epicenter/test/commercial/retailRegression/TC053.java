/** Program Description: Verifying the Cost card messages for Mississipi,New Jeresy,Florida,Louisiana,Missouri,Tennesse and North Carolina States on Renewal Requotes
 *  Author			   : Sowndarya
 *  Date of Creation   : 01/04/2022
 **/
package com.icat.epicenter.test.commercial.retailRegression;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.WebDriverManager;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC053 extends AbstractCommercialTest {

	public TC053() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID053.xls";
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
		PriorLossesPage priorLossPage = new PriorLossesPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		BuildingUnderMinimumCostPage buildingUnderminimumCost = new BuildingUnderMinimumCostPage();
		Map<String, String> testData;

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		boolean isTestPassed = false;

		try {
			for (int i = 0; i < 7; i++) {
				testData = data.get(i);

				// creating New account
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Producer Home page loaded successfully", false, false);
				homePage.createNewAccountWithNamedInsured(testData, setUpData);
				Assertions.passTest("New Account", "New Account created successfully");

				// Entering zipcode in Eligibility page
				Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
						"Eligibility Page loaded successfully", false, false);
				eligibilityPage.processSingleZip(testData);
				Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

				// Entering Location Details
				Assertions.verify(locationPage.addBuildingsButton.checkIfElementIsDisplayed(), true, "Location Page",
						"Location Page loaded successfully", false, false);
				locationPage.enterLocationDetails(testData);
				Assertions.passTest("Location Page", "Location details entered successfully");

				// Entering Location 1 Dwelling 1 Details
				//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
						//"Building Page loaded successfully", false, false);
				buildingPage.enterBuildingDetails(testData);

				// selecting peril
				if (!testData.get("Peril").equals("Quake")) {
					Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true,
							"Select Peril Page", "Select Peril Page loaded successfully", false, false);
					selectPerilPage.selectPeril(testData.get("Peril"));
				}

				// Enter prior loss details
				if (!testData.get("PriorLoss1").equals("")) {
					Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
							"Prior Loss Page loaded successfully", false, false);
					priorLossPage.selectPriorLossesInformation(testData);
				}

				// Entering Create quote page Details
				if (!testData.get("Peril").equals("")) {
					Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
							"Create Quote Page loaded successfully", false, false);
					createQuotePage.enterQuoteDetailsCommercialNew(testData);
				} else {
					createQuotePage.getAQuote.scrollToElement();
					createQuotePage.getAQuote.click();
				}
				Assertions.passTest("Create Quote Page", "Quote details entered successfully");

				// Asserting quote number
				Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
						"Account Overview Page", "Account Overview Page loaded successfully", false, false);
				quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
				Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
						"Account Overview Page",
						"Quote Number for state " + testData.get("QuoteState") + " is " + quoteNumber, false, false);

				// click on Request bind
				accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
				Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
				Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
						"Request Bind Page loaded successfully", false, false);
				requestBindPage.enterBindDetails(testData);
				Assertions.passTest("Request Bind Page", "Bind details entered successfully");

				// Go to HomePage
				homePage.goToHomepage.click();
				homePage.searchQuote(quoteNumber);
				Assertions.passTest("Home Page", "Searched Submitted quote " + quoteNumber + " successfullly");

				// Click on Open Referral Link
				accountOverviewPage.openReferralLink.scrollToElement();
				accountOverviewPage.openReferralLink.click();
				Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

				// Click on approve referral
				Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true,
						"Referral Page", "Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");

				Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
						"Request Approval page loaded successfully", false, false);

				// carrier selection
				if (requestBindPage.carrierWarningMsg.checkIfElementIsPresent()
						&& requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed()) {
					requestBindPage.approveRequestCommercialData(testData);
				} else {
					requestBindPage.approveRequest();
				}
				Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

				// Get policy number from policy summary page
				Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
				policyNumber = policySummaryPage.getPolicynumber();
				Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
						"Policy Summary Page", "Policy Number is " + policyNumber, false, false);

				// Go to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home page loaded successfully", false, false);

				// click on expacc link
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.click();
				expaccInfoPage.enterExpaccInfo(testData, policyNumber);
				Assertions.passTest("ExpaccInfo Page", "ExpaccInfo details entered successfully");

				// Go to Home Page
				homePage.waitTime(1);// wait time is needed to load the page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home page loaded successfully", false, false);
				homePage.searchPolicy(policyNumber);

				// click on renew policy link
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();
				Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy link");

				// click on continue
				if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
						&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
					policyRenewalPage.continueRenewal.scrollToElement();
					policyRenewalPage.continueRenewal.click();
				}

				// click on yes
				if (policyRenewalPage.yesButton.checkIfElementIsPresent()
						&& policyRenewalPage.yesButton.checkIfElementIsDisplayed()) {
					policyRenewalPage.yesButton.scrollToElement();
					policyRenewalPage.yesButton.click();
				}

				// click on edit building link
				accountOverviewPage.buildingLink.formatDynamicPath("1", "1").scrollToElement();
				accountOverviewPage.buildingLink.formatDynamicPath("1", "1").click();
				accountOverviewPage.editDwelling.scrollToElement();
				accountOverviewPage.editDwelling.click();

				// modifying Const type,square foot value,occupancy and building value
				Assertions.passTest("Building Page", "Building Page loaded successfully");
				buildingPage.constructionTypeArrow.waitTillVisibilityOfElement(60);
				buildingPage.constructionTypeArrow.scrollToElement();
				buildingPage.constructionTypeArrow.click();
				buildingPage.constructionTypeOption.formatDynamicPath(testData.get("L1B2-BldgConstType"))
						.scrollToElement();
				buildingPage.constructionTypeOption.formatDynamicPath(testData.get("L1B2-BldgConstType")).click();

				// Click on continue update button
				if (buildingPage.continueWithUpdateBtn.checkIfElementIsPresent()
						&& buildingPage.continueWithUpdateBtn.checkIfElementIsDisplayed()) {
					buildingPage.continueWithUpdateBtn.scrollToElement();
					buildingPage.continueWithUpdateBtn.click();
				}
				buildingPage.waitTime(2);// if the wait time is removed test case will fail here
				buildingPage.totalSquareFootage.setData(testData.get("L1B2-BldgSqFeet"));

				// click on building occupancy link
				buildingPage.buildingOccupancyLink.scrollToElement();
				buildingPage.buildingOccupancyLink.click();
				buildingPage.waitTime(2);// need wait time to load the element

				if (testData.get("L1B2-PrimaryOccupancyCode") != null
						&& !testData.get("L1B2-PrimaryOccupancyCode").equalsIgnoreCase("")) {
					if (!buildingPage.primaryOccupancy.getData().equals("")) {
						WebElement ele = WebDriverManager.getCurrentDriver().findElement(By.xpath(
								"//div[label[a[contains(text(),'Primary Occupancy')]]]//following-sibling::div//input[contains(@id,'primaryOccupancy')]"));
						ele.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
					}
					buildingPage.setOccupancyJS("primary", testData.get("L1B2-PrimaryOccupancyCode"),
							testData.get("Peril"), testData.get("QuoteState"));
				}

				// click on building values link
				buildingPage.waitTime(2);// need wait time to load the element
				buildingPage.buildingValuesLink.scrollToElement();
				buildingPage.buildingValuesLink.click();
				buildingPage.buildingValue.waitTillVisibilityOfElement(60);
				buildingPage.buildingValue.setData(testData.get("L1B2-BldgValue"));
				buildingPage.businessPersonalProperty.clearData();
				Assertions.passTest("Building Page", "Construction type: " + testData.get("L1B2-BldgConstType"));
				Assertions.passTest("Building Page", "Occupancy type: " + testData.get("L1B2-PrimaryOccupancy"));
				Assertions.passTest("Building Page", "Building Square Feet: " + testData.get("L1B2-BldgSqFeet"));
				Assertions.passTest("Building Page", "Building Details modified successfully");

				// click on continue
				buildingPage.createQuote.scrollToElement();
				buildingPage.createQuote.click();

				// Verifying Bring UpToCost button
				Assertions.verify(buildingUnderminimumCost.bringUpToCost.checkIfElementIsDisplayed(), true,
						"Building Under Minimum Cost Page", "Bring UpToCost button displayed is verified", false,
						false);

				// Getting Expected Cost card value
				String costCardValue = testData.get("CostCardValue");
				Assertions.passTest("CostCard value", "CostCard value: " + costCardValue);

				// Getting expected square feet value
				String squareFeet = testData.get("L1B2-BldgSqFeet");
				Assertions.passTest("SquareFeet", "Square Feet: " + squareFeet);

				// Verifying the Costcard message and verifying the actual and expected cost
				// card values
				Assertions.addInfo("Building Under Minimum Cost Page",
						"Verifying the Costcard message and Verifying the actual and expected cost card values");
				Assertions.addInfo("",
						"<span class='header'> " + "Construction Type: " + testData.get("L1B2-BldgConstType") + ","
								+ " Occupancy type: " + testData.get("L1B2-PrimaryOccupancy") + ", SquareFeet: "
								+ testData.get("L1B2-BldgSqFeet") + "," + " Quote State : " + testData.get("QuoteState")
								+ " </span>");
				Assertions.verify(
						buildingUnderminimumCost.costcardMessage
								.formatDynamicPath("Building value").checkIfElementIsDisplayed(),
						true, "Building Under Minimum Cost Page",
						"The Costcard message "
								+ buildingUnderminimumCost.costcardMessage.formatDynamicPath("Building value").getData()
								+ " displayed is verified",
						false, false);

				// go to home page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
			}
			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC053 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC053 ", "Executed Successfully");
			}
		}
	}

}
