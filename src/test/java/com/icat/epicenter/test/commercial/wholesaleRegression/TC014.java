/** Program Description: Check if application allows to remove an existing form by utilizng the Modify Forms feature.Adding the ticket IO-22030
 *  Author			   : Sowndarya NH
 *  Date of Creation   : 11/26/2019
**/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.ModifyForms;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC014 extends AbstractCommercialTest {

	public TC014() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID014.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the pages
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		ModifyForms modifyForms = new ModifyForms();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();

		// Initializing the variables
		String quoteNumber;
		String newquoteNumber;
		String policyNumber;
		int dataValue1 = 0;
		int locNo = 1;
		Map<String, String> testData = data.get(dataValue1);
		boolean isTestPassed = false;

		try {
			// New Account creation
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully ", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// ZipCode Eligibility Page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Zipcode Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "ZipCode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);

			// click on add building button
			locationPage.addBuildingsButton.scrollToElement();
			locationPage.addBuildingsButton.click();

			// Entering Location 1 Dwelling 1 Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			// buildingPage.enterBuildingDetails(testData);
			buildingPage.addBuildingDetails(testData, locNo, locNo);
			buildingPage.addBuildingOccupancy(testData, locNo, locNo);

			// Adding the ticket IO-22030
			buildingPage.addRoofDetails(testData, locNo, locNo);
			Assertions.addInfo("Building Page", "Verifying the presence of year roof last replaced");
			Assertions.verify(
					buildingPage.yearRoofLastReplaced.checkIfElementIsDisplayed()
							&& buildingPage.yearRoofLastReplaced.checkIfElementIsEnabled(),
					true, "Building Page", "Year Roof last replaced field displayed is verified", false, false);

			buildingPage.enterAdditionalBuildingInformation(testData, locNo, locNo);
			buildingPage.enterBuildingValues(testData, locNo, locNo);
			buildingPage.reviewBuilding();

			// Click on create quote button
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();

			// Click on override button
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.overrideNoLongerQuotableBuildings();
			}

			// Select peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}

			// Enter prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			}

			// Entering CreateQuotePage Details
			createQuotePage.enterDeductiblesCommercialNew(testData);
			createQuotePage.addAdditionalCoveragesCommercial(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Adding new Form by utilizing Modify Forms feature
			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();
			Assertions.passTest("ModifyForms Page", "ModifyForms Page Loaded successfully");

			// Selecting The CheckBoxes
			Assertions.addInfo("Modify Forms Page", "Selecting the forms");
			waitTime(2);// waittime needed to select the form
			modifyForms.vacancypermitEntireTerm.scrollToElement();
			modifyForms.vacancypermitEntireTerm.select();
			modifyForms.heatMaintain.select();
			modifyForms.lockedAndSecured.select();
			modifyForms.outdoorTrees.select();
			waitTime(3);// waittime is needed to select the form
			modifyForms.windPropertyWithinCondoUnits.scrollToElement();
			modifyForms.windPropertyWithinCondoUnits.select();
			modifyForms.override.scrollToElement();
			modifyForms.override.click();
			Assertions.passTest("Modify Forms Page", "Modify Add Features selected successfully");

			// Click on continue button
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Click on View/print Full Quote link
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);

			// Getting the quote number
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View or print full quote link");

			// Asserting Modify form Features on View or print full quote page
			Assertions.addInfo("View Or Print Full Quote Page",
					"Asserting Modify form Features on View or print full quote page");
			Assertions.verify(
					viewOrPrintFullQuotePage.forms.formatDynamicPath("Vacancy Permit").checkIfElementIsDisplayed(),
					true, "View Or Print Full Quote Page",
					"The Form " + viewOrPrintFullQuotePage.forms.formatDynamicPath("Vacancy Permit").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.forms.formatDynamicPath("Heat Maintained").checkIfElementIsDisplayed(),
					true, "View Or Print Full Quote Page",
					"The Form " + viewOrPrintFullQuotePage.forms.formatDynamicPath("Heat Maintained").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.forms.formatDynamicPath("Locked And Secured").checkIfElementIsDisplayed(),
					true, "View Or Print Full Quote Page",
					"The Form " + viewOrPrintFullQuotePage.forms.formatDynamicPath("Locked And Secured").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.forms.formatDynamicPath("Outdoor Trees").checkIfElementIsDisplayed(), true,
					"View Or Print Full Quote Page",
					"The Form " + viewOrPrintFullQuotePage.forms.formatDynamicPath("Outdoor Trees").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.forms
							.formatDynamicPath("Property Within Condominium").checkIfElementIsDisplayed(),
					true, "View Or Print Full Quote Page",
					"The Form "
							+ viewOrPrintFullQuotePage.forms.formatDynamicPath("Property Within Condominium").getData()
							+ " displayed is verified",
					false, false);
			viewOrPrintFullQuotePage.backButton.click();

			// Click on Edit deductibles and limits
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Deductibles Button");
			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();
			Assertions.passTest("Create Quote Page", "Clicked on Modify forms");

			// Deselecting ModifyForm features
			Assertions.addInfo("Modify Forms Page", "Deselecting the selected forms");
			modifyForms.vacancypermitEntireTerm.deSelect();
			modifyForms.heatMaintain.deSelect();
			modifyForms.lockedAndSecured.deSelect();
			modifyForms.outdoorTrees.deSelect();
			waitTime(3);// waittime is needed to deselect the form
			modifyForms.windPropertyWithinCondoUnits.scrollToElement();
			modifyForms.windPropertyWithinCondoUnits.deSelect();
			modifyForms.override.scrollToElement();
			modifyForms.override.click();
			Assertions.passTest("Modify Forms Page", "Deselected the selected forms successfully");
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);

			// Click on View print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View or print full quote link");

			// Asserting the forms names are not available in view or print full quote page
			Assertions.addInfo("View Or Print Full Quote Page",
					"Asserting the forms names are not available in view or print full quote page");
			Assertions.verify(
					viewOrPrintFullQuotePage.forms.formatDynamicPath("Vacancy Permit").checkIfElementIsPresent(), false,
					"View Or Print Full Quote Page", "The Form Vacancy Permit not displayed is verified", false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.forms.formatDynamicPath("Heat Maintained").checkIfElementIsPresent(),
					false, "View Or Print Full Quote Page",
					"The Form Heat Maintained Condition and Exclusion not displayed is verified", false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.forms.formatDynamicPath("Locked And Secured").checkIfElementIsPresent(),
					false, "View Or Print Full Quote Page",
					"The Form Locked And Secured As A Condition not displayed is verified", false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.forms.formatDynamicPath("Outdoor Trees").checkIfElementIsPresent(), false,
					"View Or Print Full Quote Page",
					"The Form Outdoor Trees Shrubs And Plants not displayed is verified", false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.forms.formatDynamicPath("Property Within Condominium")
							.checkIfElementIsPresent(),
					false, "View Or Print Full Quote Page",
					"The Form Property Within Condominium Units Coverage Extension not displayed is verified", false,
					false);

			// Click on back button
			viewOrPrintFullQuotePage.backButton.click();

			// Getting the Quote Number
			newquoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(2).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Second Quote Number is : " + newquoteNumber);

			// click on request bind
			accountOverviewPage.clickOnRequestBind(testData, newquoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind ");

			// Entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details Entered successfully");

			// Clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home Page", "Clicked on Home button");

			// Searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchQuote(newquoteNumber);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// Approving referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Approval page loaded successfully", false, false);
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// Click on Quote number link
			policySummarypage.quoteNoLink.formatDynamicPath(newquoteNumber).scrollToElement();
			policySummarypage.quoteNoLink.formatDynamicPath(newquoteNumber).click();
			Assertions.passTest("Policy Summary Page", "Clicked on Quote number link");

			// Asserting the forms are not available in Quote details Page
			Assertions.addInfo("Quote Details Page",
					"Asserting the forms names are not available in Quote Details Page");
			Assertions.verify(
					viewOrPrintFullQuotePage.forms.formatDynamicPath("Vacancy Permit").checkIfElementIsPresent(), false,
					"Quote Details Page", "The Form Vacancy Permit not displayed is verified", false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.forms.formatDynamicPath("Heat Maintained").checkIfElementIsPresent(),
					false, "Quote Details Page",
					"The Form Heat Maintained Condition and Exclusion not displayed is verified", false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.forms.formatDynamicPath("Locked And Secured").checkIfElementIsPresent(),
					false, "Quote Details Page", "The Form Locked And Secured As A Condition not displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.forms.formatDynamicPath("Outdoor Trees").checkIfElementIsPresent(), false,
					"Quote Details Page", "The Form Outdoor Trees Shrubs And Plants not displayed is verified", false,
					false);
			Assertions.verify(
					viewOrPrintFullQuotePage.forms.formatDynamicPath("Property Within Condominium")
							.checkIfElementIsPresent(),
					false, "Quote Details Page",
					"The Form Property Within Condominium Units Coverage Extension not displayed is verified", false,
					false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Search policy
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Policy Number : " + policyNumber + " searched successfully");

			// click on endorse pb link
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// setting Endorsement Effective Date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// clicking on edit Location/Building information hyperlink
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();

			// click on building link
			locationPage.buildingLink.formatDynamicPath(locNo, locNo).scrollToElement();
			locationPage.buildingLink.formatDynamicPath(locNo, locNo).click();

			// Adding the ticket IO-22030 as part of endorsement
			// 13/06/2024
			buildingPage.roofDetailsLink.scrollToElement();
			buildingPage.roofDetailsLink.click();
			Assertions.passTest("Building Page", "Clicked on roof details link");
			Assertions.addInfo("Building Page", "Verifying the presence of year roof last replaced field");
			Assertions.verify(
					buildingPage.yearRoofLastReplaced.checkIfElementIsDisplayed()
							&& buildingPage.yearRoofLastReplaced.checkIfElementIsEnabled(),
					true, "Dwelling Page", "Year Roof last replaced field displayed is verified", false, false);

			// Click on Continue button
			buildingPage.continueButton.scrollToElement();
			buildingPage.continueButton.click();

			// Click on override
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			// Click on continue endorsement button
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();

			// Click on cancel
			endorsePolicyPage.cancelButton.scrollToElement();
			endorsePolicyPage.cancelButton.click();

			// click on Rewrite policylink
			policySummarypage.rewritePolicy.scrollToElement();
			policySummarypage.rewritePolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked Rewrite link successfully");

			// click on edit building icon
			accountOverviewPage.buildingLink.formatDynamicPath(locNo, locNo).scrollToElement();
			accountOverviewPage.buildingLink.formatDynamicPath(locNo, locNo).click();
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();

			// Adding the ticket IO-22030 as part of rewrite
			// 13/06/2024
			buildingPage.roofDetailsLink.scrollToElement();
			buildingPage.roofDetailsLink.click();
			Assertions.passTest("Dwelling Page", "Clicked on roof details link");
			Assertions.addInfo("Dwelling Page", "Verifying the presence of year roof last replaced field");
			Assertions.verify(
					buildingPage.yearRoofLastReplaced.checkIfElementIsDisplayed()
							&& buildingPage.yearRoofLastReplaced.checkIfElementIsEnabled(),
					true, "Dwelling Page", "Year Roof last replaced field displayed is verified", false, false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Search policy
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Policy Number : " + policyNumber + " searched successfully");

			// Click on stop work on rewrite
			policySummarypage.stopPolicyRewrite.scrollToElement();
			policySummarypage.stopPolicyRewrite.click();

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Click on expacc link
			homePage.expaccLink.scrollToElement();
			homePage.expaccLink.click();

			// Enter Expacc details
			expaccInfoPage.enterExpaccInfo(testData, policyNumber);
			Assertions.passTest("ExpaccInfo Page", "ExpaccInfo details entered successfully");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Policy Number : " + policyNumber + " searched successfully");

			// Click on renew policy link
			policySummarypage.renewPolicy.scrollToElement();
			policySummarypage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy link");

			// Click on Continue
			if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
					&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
				policyRenewalPage.continueRenewal.scrollToElement();
				policyRenewalPage.continueRenewal.click();
			}

			// click on edit building icon
			accountOverviewPage.buildingLink.formatDynamicPath(locNo, locNo).scrollToElement();
			accountOverviewPage.buildingLink.formatDynamicPath(locNo, locNo).click();
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();

			// Adding the ticket IO-22030 as part of renewal
			// 13/06/2024
			buildingPage.roofDetailsLink.scrollToElement();
			buildingPage.roofDetailsLink.click();
			Assertions.passTest("Dwelling Page", "Clicked on roof details link");
			Assertions.addInfo("Dwelling Page", "Verifying the presence of year roof last replaced field");
			Assertions.verify(
					buildingPage.yearRoofLastReplaced.checkIfElementIsDisplayed()
							&& buildingPage.yearRoofLastReplaced.checkIfElementIsEnabled(),
					true, "Dwelling Page", "Year Roof last replaced field displayed is verified", false, false);

			// Signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 14", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 14", "Executed Successfully");
			}
		}
	}
}
