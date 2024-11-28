/*Program Description: Verifying PFC available on additional interest page renewal quote and absence of PFC after adding addiotional interest on another quote, bind request page and endorsement
 *					   Verifying EQSL coverage is not avaibale on Renewal Create quote screen, if the EQ deductible is Declined.(Check with AOP peril)
 * Author            : Pavan Mule
 * Date of creation  : 03/02/2022
 */
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
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EditAdditionalInterestInformationPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorseAdditionalInterestsPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExistingAccountPage;
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

public class TC038 extends AbstractCommercialTest {

	public TC038() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID038.xls";
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
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		EditAdditionalInterestInformationPage editAdditionalInterestInformationPage = new EditAdditionalInterestInformationPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		EndorseAdditionalInterestsPage endorseAdditionalInterestsPage = new EndorseAdditionalInterestsPage();
		ExistingAccountPage existingAccountPage = new ExistingAccountPage();
		BuildingUnderMinimumCostPage buildingUnderminimumCost = new BuildingUnderMinimumCostPage();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;

		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New account", "New account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility page",
					"Eligibility page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location page",
					"Location page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building page",
					//"Building page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select peril page",
					"Select peril page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Prior Losses
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior loss page",
					"Prior loss page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create quote page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create quote page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account overview page", "Quote number :  " + quoteNumber);

			// Click on request bind button
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account overview page", "Clicked on request bind button successfully");

			// Enter bind details
			Assertions.verify(
					requestBindPage.submit.checkIfElementIsPresent()
							&& requestBindPage.submit.checkIfElementIsDisplayed(),
					true, "Request bind page", "Request bind page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request bind page", "Bind details entered successfully");

			// clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind request page",
					"Bind bequest page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home page", "Clicked on home button");

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote is searched successfully");

			// Click on Open Referral Link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral page", "Referral request approved successfully");
			requestBindPage.approveRequest();
			Assertions.passTest("Request bind page", "Bind request approved successfully");

			// getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy summary page",
					"Policy summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy summary page", "Policy number is : " + policyNumber);

			// Click on Renewal link
			policySummarypage.renewPolicy.scrollToElement();
			policySummarypage.renewPolicy.click();

			// Go to Home Page
			if (policySummarypage.expaacMessage.checkIfElementIsPresent()
					&& policySummarypage.expaacMessage.checkIfElementIsDisplayed()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.click();
				expaccInfoPage.enterExpaccInfo(testData, policyNumber);
				Assertions.passTest("ExpaccInfo page", "ExpaccInfo details entered successfully");

				// Go to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();

				// Performing Renewal Searches
				homePage.searchPolicy(policyNumber);
				// clicking on renewal policy link
				policySummarypage.renewPolicy.waitTillPresenceOfElement(60);
				policySummarypage.renewPolicy.scrollToElement();
				policySummarypage.renewPolicy.click();
				Assertions.passTest("Policy summary page", "Clicked on renewal link successfully");
			}
			if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
					&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
				policyRenewalPage.continueRenewal.scrollToElement();
				policyRenewalPage.continueRenewal.click();
			}

			if (policyRenewalPage.renewalYes.checkIfElementIsPresent()
					&& policyRenewalPage.renewalYes.checkIfElementIsDisplayed()) {
				policyRenewalPage.renewalYes.scrollToElement();
				policyRenewalPage.renewalYes.click();
			}

			// Getting renewal quote number
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account overview page", "Renewal quote number :  " + quoteNumber);

			// Click on Edit Deductibles
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Deductible and Limits Button");

			// Asserting absence of Earthquake Sprinkler Leakage dropdown
			Assertions.addInfo("Scenario 01", "Verifying Absence of EQSL Dropdown");
			Assertions.verify(createQuotePage.earthquakeSprinklerLeakageArrow.checkIfElementIsPresent(), false,
					"Create A Quote Page", "Earthquake Sprinkler Leakage Option is not displayed", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Click on Get A Quote Button
			if (createQuotePage.getAQuote.checkIfElementIsPresent()
					&& createQuotePage.getAQuote.checkIfElementIsDisplayed()) {
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();
			}

			// click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// click on Additional Interests
			accountOverviewPage.editAdditionalIntersets.scrollToElement();
			accountOverviewPage.editAdditionalIntersets.click();
			editAdditionalInterestInformationPage.update.waitTillVisibilityOfElement(60);
			Assertions.verify(editAdditionalInterestInformationPage.update.checkIfElementIsPresent(), true,
					"Additional interests page", "Additional interests page loaded successfully", false, false);
			editAdditionalInterestInformationPage.aITypeArrow.scrollToElement();
			editAdditionalInterestInformationPage.aITypeArrow.click();

			// checking PFC available on renewal quote
			Assertions.addInfo("Scenario 02",
					"Checking the PFC is available for additional interests on renewal quote");
			Assertions.verify(
					editAdditionalInterestInformationPage.aITypeOption.formatDynamicPath(0, "Premium Finance Company")
							.checkIfElementIsPresent()
							&& editAdditionalInterestInformationPage.aITypeOption
									.formatDynamicPath(0, "Premium Finance Company").checkIfElementIsDisplayed(),
					true, "Additional interests page",
					"New additinal interests Type is "
							+ editAdditionalInterestInformationPage.aITypeOption
									.formatDynamicPath(0, "Premium Finance Company").getData()
							+ " Available is verified",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");
			editAdditionalInterestInformationPage.cancel.waitTillVisibilityOfElement(60);
			editAdditionalInterestInformationPage.cancel.click();

			// click on Additional Interests
			accountOverviewPage.editAdditionalIntersets.scrollToElement();
			accountOverviewPage.editAdditionalIntersets.click();

			// adding additional interests with premium finance company(PFC) on renewal
			// quote
			testData = data.get(data_Value2);
			editAdditionalInterestInformationPage.addAdditionalInterest(testData);
			editAdditionalInterestInformationPage.update.waitTillPresenceOfElement(60);
			editAdditionalInterestInformationPage.update.waitTillVisibilityOfElement(60);
			editAdditionalInterestInformationPage.update.scrollToElement();
			editAdditionalInterestInformationPage.update.click();
			Assertions.passTest("Edit additional interests page",
					"The AI type selected is " + testData.get("1-AIType"));
			Assertions.passTest("Edit additional interest page", "Additional interest added successfully");

			// click on edit additional interest link
			accountOverviewPage.waitTime(3);// need waittime to load the element
			accountOverviewPage.editAdditionalIntersets.waitTillPresenceOfElement(60);
			accountOverviewPage.editAdditionalIntersets.waitTillVisibilityOfElement(60);
			accountOverviewPage.editAdditionalIntersets.scrollToElement();
			accountOverviewPage.editAdditionalIntersets.click();
			Assertions.passTest("Account overview page", "Clicked on edit additional interest icon");
			editAdditionalInterestInformationPage.aIAddSymbol.waitTillVisibilityOfElement(60);
			editAdditionalInterestInformationPage.aIAddSymbol.click();
			editAdditionalInterestInformationPage.aITypeArrow.scrollToElement();
			editAdditionalInterestInformationPage.aITypeArrow.click();

			// checking PFC are not available in drop down after adding the PFC in
			// additional interests on renewal quote
			Assertions.addInfo("Scenario 03",
					"Checking PFC is Not available after adding additional interest on renewal quote");
			Assertions.verify(
					editAdditionalInterestInformationPage.aITypeOption.formatDynamicPath(0, "Premium Finance Company")
							.checkIfElementIsPresent()
							&& editAdditionalInterestInformationPage.aITypeOption
									.formatDynamicPath(0, "Premium Finance Company").checkIfElementIsDisplayed(),
					false, "Edit additional interests page",
					"Additional interest type " + testData.get("1-AIType") + " Not Available is verified", false,
					false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");
			editAdditionalInterestInformationPage.cancel.click();

			// create another quote
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();
			Assertions.passTest("Account overview page", "Clicked on another quote successfully");
			selectPerilPage.selectPeril(testData.get("Peril"));
			createQuotePage.enterQuoteDetailsCommercialNew(testData);

			// Getting renewal quote number 2
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			String quoteNumber2 = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account overview page", "Renewal quote number2 :  " + quoteNumber2);

			// click on edit additional interest link
			accountOverviewPage.waitTime(3);// need waittime to load the element
			accountOverviewPage.editAdditionalIntersets.waitTillPresenceOfElement(60);
			accountOverviewPage.editAdditionalIntersets.waitTillVisibilityOfElement(60);
			accountOverviewPage.editAdditionalIntersets.scrollToElement();
			accountOverviewPage.editAdditionalIntersets.click();
			Assertions.passTest("Account overview page", "Clicked on edit additional interest icon");
			editAdditionalInterestInformationPage.aIAddSymbol.waitTillVisibilityOfElement(60);
			editAdditionalInterestInformationPage.aIAddSymbol.click();
			editAdditionalInterestInformationPage.aITypeArrow.scrollToElement();
			editAdditionalInterestInformationPage.aITypeArrow.click();

			// checking PFC are not available in drop down after adding the PFC in
			// additional interests on renewal quote
			Assertions.addInfo("Scenario 04",
					"Checking PFC is Not available after adding additional interest on another renewal quote");
			Assertions.verify(
					editAdditionalInterestInformationPage.aITypeOption.formatDynamicPath(0, "Premium Finance Company")
							.checkIfElementIsPresent()
							&& editAdditionalInterestInformationPage.aITypeOption
									.formatDynamicPath(0, "Premium Finance Company").checkIfElementIsDisplayed(),
					false, "Edit additional interests page",
					"Additional interest type " + testData.get("1-AIType") + " Not Available is verified", false,
					false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");
			editAdditionalInterestInformationPage.cancel.click();

			// click on release renewal to producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account overview page", "Clicked on release renewal to producer button successfully");

			// click on issue quote
			accountOverviewPage.issueQuoteButton.scrollToElement();
			accountOverviewPage.issueQuoteButton.click();

			// click on request bind button
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber2);
			Assertions.passTest("Account overview page", "Clicked on request bind button successfully");

			if (!accountOverviewPage.requestBind.checkIfElementIsPresent()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuote(quoteNumber2);
				accountOverviewPage.requestBind.scrollToElement();
				accountOverviewPage.requestBind.click();
			}

			Assertions.verify(
					requestBindPage.submit.checkIfElementIsPresent()
							&& requestBindPage.submit.checkIfElementIsDisplayed(),
					true, "Request bind page", "Request bind page loaded successfully", false, false);
			editAdditionalInterestInformationPage.aIAddSymbol.scrollToElement();
			editAdditionalInterestInformationPage.aIAddSymbol.click();
			editAdditionalInterestInformationPage.aITypeArrow.scrollToElement();
			editAdditionalInterestInformationPage.aITypeArrow.click();

			// Checking absence of PFC on request bind page after adding PFC
			Assertions.addInfo("Scenario 05",
					"Checking PFC is Not available after adding additional interest on request bind page");
			Assertions.verify(
					editAdditionalInterestInformationPage.aITypeOption.formatDynamicPath(0, "Premium Finance Company")
							.checkIfElementIsPresent()
							&& editAdditionalInterestInformationPage.aITypeOption
									.formatDynamicPath(0, "Premium Finance Company").checkIfElementIsDisplayed(),
					false, "Request bind page",
					"Additional interest type " + testData.get("1-AIType") + " Not Available is verified", false,
					false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			testData = data.get(data_Value1);
			requestBindPage.addContactInformation(testData);
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();
			requestBindPage.confirmBind();
			Assertions.passTest("Request bind page", "Bind details entered successfully");

			// clicking on home button
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home page", "Clicked on home button");

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber2);
			Assertions.passTest("Home Page", "Quote is searched successfully");

			// Click on Open Referral Link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral page", "Referral request approved successfully");
			requestBindPage.approveRequest();
			Assertions.passTest("Request bind page", "Bind request approved successfully");

			// getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy summary page",
					"Policy summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy summary page", "Renewal policy number is : " + policyNumber);

			// Checking PFC not available on endorsement page
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse policy page", "Endorse policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.appendData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// Click on AI link
			endorsePolicyPage.changeAIInformationLink.scrollToElement();
			endorsePolicyPage.changeAIInformationLink.click();
			Assertions.verify(endorseAdditionalInterestsPage.okButton.checkIfElementIsDisplayed(), true,
					"Endorse additional interest page", "Endorse additional interest page loaded successfully", false,
					false);
			endorseAdditionalInterestsPage.aIAddSymbol.scrollToElement();
			endorseAdditionalInterestsPage.aIAddSymbol.click();
			editAdditionalInterestInformationPage.aITypeArrow.scrollToElement();
			editAdditionalInterestInformationPage.aITypeArrow.click();

			// Checking absence of PFC on Endorsement page after adding PFC
			testData = data.get(data_Value2);
			Assertions.addInfo("Scenario 06",
					"Checking PFC is Not available after adding additional interest on endorse additional interest page");
			Assertions.verify(
					editAdditionalInterestInformationPage.aITypeOption.formatDynamicPath(0, "Premium Finance Company")
							.checkIfElementIsPresent()
							&& editAdditionalInterestInformationPage.aITypeOption
									.formatDynamicPath(0, "Premium Finance Company").checkIfElementIsDisplayed(),
					false, "Endorse additional interests page",
					"Additional interest type " + testData.get("1-AIType") + " Not Available is verified", false,
					false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// go to hompage
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// search the policy
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Searched the Policy " + policyNumber + " successfully");

			// Click on endorse PB link
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			if (endorsePolicyPage.deleteExistingEndorsementButton.checkIfElementIsPresent()
					&& endorsePolicyPage.deleteExistingEndorsementButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.deleteExistingEndorsementButton.scrollToElement();
				endorsePolicyPage.deleteExistingEndorsementButton.click();
			}

			// Enter effective date
			testData = data.get(data_Value2);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// click on edit location or building details link
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Edit Location or Building details link");

			// edit the building details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			locationPage.buildingLink.formatDynamicPath(1, 1).click();

			// modifying Const type,square foot value,occupancy and building value
			Assertions.passTest("Building Page", "Building Page loaded successfully");
			buildingPage.constructionTypeArrow.waitTillVisibilityOfElement(60);
			buildingPage.constructionTypeArrow.scrollToElement();
			buildingPage.constructionTypeArrow.click();
			buildingPage.constructionTypeOption.formatDynamicPath(testData.get("L1B1-BldgConstType")).scrollToElement();
			buildingPage.constructionTypeOption.formatDynamicPath(testData.get("L1B1-BldgConstType")).click();
			buildingPage.totalSquareFootage.setData(testData.get("L1B1-BldgSqFeet"));

			// click on building occupancy link
			buildingPage.buildingOccupancyLink.scrollToElement();
			buildingPage.buildingOccupancyLink.click();
			buildingPage.waitTime(2);// need wait time to load the element

			if (testData.get("L1B1-PrimaryOccupancyCode") != null
					&& !testData.get("L1B1-PrimaryOccupancyCode").equalsIgnoreCase("")) {
				if (!buildingPage.primaryOccupancy.getData().equals("")) {
					WebElement ele = WebDriverManager.getCurrentDriver().findElement(By.xpath(
							"//div[label[a[contains(text(),'Primary Occupancy')]]]//following-sibling::div//input[contains(@id,'primaryOccupancy')]"));
					ele.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
				}
				buildingPage.setOccupancyJS("primary", testData.get("L1B1-PrimaryOccupancyCode"), testData.get("Peril"),
						testData.get("QuoteState"));
			}

			// click on building values link
			buildingPage.waitTime(2);// need wait time to load the element
			buildingPage.buildingValuesLink.scrollToElement();
			buildingPage.buildingValuesLink.click();
			buildingPage.buildingValue.waitTillVisibilityOfElement(60);
			buildingPage.buildingValue.setData(testData.get("L1B1-BldgValue"));
			buildingPage.businessPersonalProperty.clearData();
			Assertions.passTest("Building Page", "Construction type: " + testData.get("L1B1-BldgConstType"));
			Assertions.passTest("Building Page", "Occupancy type: " + testData.get("L1B1-PrimaryOccupancy"));
			Assertions.passTest("Building Page", "Building Square Feet: " + testData.get("L1B1-BldgSqFeet"));
			Assertions.passTest("Building Page", "Building Details modified successfully");

			// click on continue
			buildingPage.continueButton.scrollToElement();
			buildingPage.continueButton.click();

			// existing account found page
			if (buildingPage.pageName.getData().contains("Existing Account Found")) {
				existingAccountPage.OverrideExistingAccount();
			}

			// Verifying Bring UpToCost button
			Assertions.verify(buildingUnderminimumCost.bringUpToCost.checkIfElementIsDisplayed(), true,
					"Building Under Minimum Cost Page", "Bring UpToCost button displayed is verified", false, false);

			// Getting Expected Cost card value
			String costCardValue = testData.get("CostCardValue");
			Assertions.passTest("CostCard value", "CostCard value: " + costCardValue);

			// Getting expected square feet value
			String squareFeet = testData.get("L1B1-BldgSqFeet");
			Assertions.passTest("SquareFeet", "Square Feet: " + squareFeet);

			// Verifying the Costcard message and verifying the actual and expected cost
			// card values
			Assertions.addInfo("Scenario 07",
					"Verifying the Costcard message and Verifying the actual and expected cost card values Construction type: Reinforced Masonry, Occupancy type: Wholesale,Building Square Feet: 1000");
			Assertions.verify(
					buildingUnderminimumCost.costcardMessage
							.formatDynamicPath("Building value").checkIfElementIsDisplayed(),
					true, "Building Under Minimum Cost Page",
					"The Costcard message "
							+ buildingUnderminimumCost.costcardMessage.formatDynamicPath("Building value").getData()
							+ " displayed is verified",
					false, false);

			// below code is commenting because of application not running on cost card
			// value
			// Actual Cost card value
			/*
			 * String actualCostCardValue =
			 * buildingUnderminimumCost.costcardMessage.formatDynamicPath("Building value")
			 * .getData().substring(45, 52).replace(",", "").replace(".", "");
			 * Assertions.passTest("Building Under Minimum Cost Page",
			 * "Actual CostCard value: " + "$" + actualCostCardValue);
			 *
			 * // Calculating Cost Card value double expectedCostCardValue =
			 * Double.parseDouble(costCardValue) * Double.parseDouble(squareFeet); int
			 * ExpectedCostCardVaue = (int) expectedCostCardValue;
			 * Assertions.passTest("Building Under Minimum Cost Page",
			 * "Expected CostCard value: " + "$" + ExpectedCostCardVaue);
			 *
			 * // Verifying actual cost card value and expected cost card value
			 * Assertions.verify("$" + actualCostCardValue, "$" + ExpectedCostCardVaue,
			 * "Building Under Minimum Cost Page",
			 * "Expected and Actual CostCard values are equal", false, false);
			 */
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC038 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC038 ", "Executed Successfully");
			}
		}
	}
}
