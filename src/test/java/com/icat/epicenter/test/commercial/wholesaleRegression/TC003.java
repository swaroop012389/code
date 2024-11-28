/** Program Description: Create a commerical policy with BI and check if all details can be asserted on Quote and Policy SnapShot pages.
 *  Author			   : Sowndarya NH
 *  Date of Creation   : 11/26/2019
**/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.ModifyForms;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.RmsModelResultsPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC003 extends AbstractCommercialTest {

	public TC003() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID003.xls";
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
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ViewPolicySnapShot viewPolicySnapshotPage = new ViewPolicySnapShot();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ModifyForms modifyForms = new ModifyForms();
		RmsModelResultsPage rmsModelResultsPage = new RmsModelResultsPage();

		// Initializing the variables
		String quoteNumber;
		String policyNumber;
		String businessIncomeValue = null;
		int quoteLength;
		int dataValue1 = 0;
		int dataValue2 = 1;
		char ch = 'A';
		Map<String, String> testData = data.get(dataValue1);
		boolean isTestPassed = false;

		try {
			// New account Creation
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account Created successfully");

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
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page Loaded successfully", false, false);

			// click on coverage extension packagelink
			createQuotePage.coverageExtensionPackageLink.scrollToElement();
			createQuotePage.coverageExtensionPackageLink.click();
			Assertions.passTest("Create Quote Page", "Clicked on Coverage Extension Package link");
			Assertions.verify(createQuotePage.packageBAccountsReceivable.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Package Values Loaded successfully", false, false);

			// Asserting Package A and Package B values
			Assertions.addInfo("Create Quote Page", "Asserting Package A and Package B values");
			for (int i = 1; i <= 2; i++) {
				Assertions.verify(
						createQuotePage.packageValues
								.formatDynamicPath("Accounts Receivable", i).checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"The Package " + ch + " Accounts Receivable value "
								+ createQuotePage.packageValues.formatDynamicPath("Accounts Receivable", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						createQuotePage.packageValues.formatDynamicPath("Fine Arts", i).checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"The Package " + ch + " Fine Arts value "
								+ createQuotePage.packageValues.formatDynamicPath("Fine Arts", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						createQuotePage.packageValues.formatDynamicPath("Spoilage", i).checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"The Package  " + ch + " Spoilage value "
								+ createQuotePage.packageValues.formatDynamicPath("Spoilage", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						createQuotePage.packageValues
								.formatDynamicPath("Utility Services - Direct Damage", i).checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"The Package "
								+ ch + " Utility Services - Direct Damage value " + createQuotePage.packageValues
										.formatDynamicPath("Utility Services - Direct Damage", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						createQuotePage.packageValues
								.formatDynamicPath("Utility Services - Time Element", i).checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"The Package "
								+ ch + " Utility Services - Time Element value " + createQuotePage.packageValues
										.formatDynamicPath("Utility Services - Time Element", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(createQuotePage.packageValues
						.formatDynamicPath("Sewer, Drain, and Sump Back-up or Overflow", i).checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"The Package " + ch + " Sewer, Drain, and Sump Back-up or Overflow value "
								+ createQuotePage.packageValues
										.formatDynamicPath("Sewer, Drain, and Sump Back-up or Overflow", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						createQuotePage.packageValues
								.formatDynamicPath("Property in Your Covered Building", i).checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"The Package " + ch + " Customers' Property in Your Covered Building value "
								+ createQuotePage.packageValues
										.formatDynamicPath("Property in Your Covered Building", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						createQuotePage.packageValues
								.formatDynamicPath("Lock Replacement", i).checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"The Package " + ch + " Lock Replacement value "
								+ createQuotePage.packageValues.formatDynamicPath("Lock Replacement", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						createQuotePage.packageValues
								.formatDynamicPath("Theft, Disappearance", i).checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"The Package " + ch + " Theft, Disappearance, or Destruction of Money and Securities value "
								+ createQuotePage.packageValues.formatDynamicPath("Theft, Disappearance", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						createQuotePage.packageValues
								.formatDynamicPath("Robbery of a Custodian", i).checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"The Package " + ch + " Robbery of a Custodian or Safe Burglary Coverage value "
								+ createQuotePage.packageValues.formatDynamicPath("Robbery of a Custodian", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						createQuotePage.packageValues
								.formatDynamicPath("Fire Extinguisher Recharge", i).checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"The Package "
								+ ch + " Fire Extinguisher Recharge value " + createQuotePage.packageValues
										.formatDynamicPath("Fire Extinguisher Recharge", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						createQuotePage.packageValues
								.formatDynamicPath("Electronic Data", i).checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"The Package " + ch + " Electronic Data value "
								+ createQuotePage.packageValues.formatDynamicPath("Electronic Data", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						createQuotePage.packageValues
								.formatDynamicPath("Fire Department Service Charge", i).checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"The Package "
								+ ch + " Fire Department Service Charge value " + createQuotePage.packageValues
										.formatDynamicPath("Fire Department Service Charge", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						createQuotePage.packageValues
								.formatDynamicPath("Constructed Property", i).checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"The Package " + ch + " Newly Acquired or Constructed Property value "
								+ createQuotePage.packageValues.formatDynamicPath("Constructed Property", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						createQuotePage.packageValues
								.formatDynamicPath("Outdoor Property", i).checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"The Package " + ch + " Outdoor Property value "
								+ createQuotePage.packageValues.formatDynamicPath("Outdoor Property", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						createQuotePage.packageValues
								.formatDynamicPath("Valuable Papers and Records", i).checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"The Package "
								+ ch + " Valuable Papers and Records value " + createQuotePage.packageValues
										.formatDynamicPath("Valuable Papers and Records", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						createQuotePage.packageValues
								.formatDynamicPath("Personal Effects", i).checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"The Package " + ch + " Personal Effects and Property of Others value "
								+ createQuotePage.packageValues.formatDynamicPath("Personal Effects", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						createQuotePage.packageValues
								.formatDynamicPath("Property in Transit", i).checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"The Package " + ch + " Property in Transit value "
								+ createQuotePage.packageValues.formatDynamicPath("Property in Transit", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						createQuotePage.packageValues
								.formatDynamicPath("Property off Premises", i).checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"The Package " + ch + " Property off Premises value "
								+ createQuotePage.packageValues.formatDynamicPath("Property off Premises", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions
						.verify(createQuotePage.packageValues
								.formatDynamicPath("Covered Property - Building", i).checkIfElementIsDisplayed(), true,
								"Create Quote Page",
								"The Package " + ch + " Perimeter Extension: Covered Property - Building value "
										+ createQuotePage.packageValues
												.formatDynamicPath("Covered Property - Building", i).getData()
										+ " displayed is verified",
								false, false);
				Assertions.verify(
						createQuotePage.packageValues
								.formatDynamicPath("Covered Property - BPP", i).checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"The Package " + ch + " Perimeter Extension: Covered Property - BPP value "
								+ createQuotePage.packageValues.formatDynamicPath("Covered Property - BPP", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						createQuotePage.packageValues.formatDynamicPath("Tenant Glass", i).checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"The Package " + ch + " Tenant Glass value "
								+ createQuotePage.packageValues.formatDynamicPath("Tenant Glass", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						createQuotePage.packageValues
								.formatDynamicPath("Extended Period of Indemnity", i).checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"The Package "
								+ ch + " Extended Period of Indemnity value " + createQuotePage.packageValues
										.formatDynamicPath("Extended Period of Indemnity", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(createQuotePage.packageValues
						.formatDynamicPath("Newly Acquired Business Personal Property", i).checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"The Package " + ch + " Newly Acquired Business Personal Property value "
								+ createQuotePage.packageValues
										.formatDynamicPath("Newly Acquired Business Personal Property", i).getData()
								+ " displayed is verified",
						false, false);
				ch++;
			}

			// Asserting Base Package values
			Assertions.verify(
					createQuotePage.packageValues
							.formatDynamicPath("Accounts Receivable", 3).checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Base Package " + createQuotePage.packageBAccountsReceivable.getData() + " value "
							+ createQuotePage.packageValues.formatDynamicPath("Accounts Receivable", 3).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					createQuotePage.packageValues.formatDynamicPath("Fine Arts", 3).checkIfElementIsDisplayed(), true,
					"Create Quote Page",
					"The Base Package " + createQuotePage.packageBFineArts.getData() + " value "
							+ createQuotePage.packageValues.formatDynamicPath("Fine Arts", 3).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					createQuotePage.packageValues.formatDynamicPath("Spoilage", 3).checkIfElementIsDisplayed(), true,
					"Create Quote Page",
					"The Base Package " + createQuotePage.packageBSpoilage.getData() + " value "
							+ createQuotePage.packageValues.formatDynamicPath("Spoilage", 3).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					createQuotePage.packageValues.formatDynamicPath("Spoilage", 3).checkIfElementIsDisplayed(), true,
					"Create Quote Page",
					"The Base Package " + createQuotePage.packageBSpoilage.getData() + " value "
							+ createQuotePage.packageValues.formatDynamicPath("Spoilage", 3).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					createQuotePage.packageValues
							.formatDynamicPath("Utility Services - Direct Damage", 3).checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Base Package "
							+ createQuotePage.packageBDirectDamage.getData() + " value " + createQuotePage.packageValues
									.formatDynamicPath("Utility Services - Direct Damage", 3).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					createQuotePage.packageValues
							.formatDynamicPath("Utility Services - Time Element", 3).checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Base Package "
							+ createQuotePage.packageBTimeElement.getData() + " value " + createQuotePage.packageValues
									.formatDynamicPath("Utility Services - Time Element", 3).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(createQuotePage.packageValues
					.formatDynamicPath("Sewer, Drain, and Sump Back-up or Overflow", 3).checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Base Package" + createQuotePage.packageBSewerDrain.getData() + " value "
							+ createQuotePage.packageValues
									.formatDynamicPath("Sewer, Drain, and Sump Back-up or Overflow", 3).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					createQuotePage.packageValues
							.formatDynamicPath("Property in Your Covered Building", 3).checkIfElementIsDisplayed(),
					true, "Create Quote Page", "The Base Package" + createQuotePage.packageBCustomersProperty.getData()
							+ " value " + createQuotePage.packageValues
									.formatDynamicPath("Property in Your Covered Building", 3).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					createQuotePage.packageValues.formatDynamicPath("Lock Replacement", 3).checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Base Package " + createQuotePage.packageBLockReplacement.getData() + " value "
							+ createQuotePage.packageValues.formatDynamicPath("Lock Replacement", 3).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					createQuotePage.packageValues
							.formatDynamicPath("Theft, Disappearance", 3).checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Base Package " + createQuotePage.packageBTheft.getData() + " value "
							+ createQuotePage.packageValues.formatDynamicPath("Theft, Disappearance", 3).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					createQuotePage.packageValues
							.formatDynamicPath("Robbery of a Custodian", 3).checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Base Package " + createQuotePage.packageBRobbery.getData() + " value "
							+ createQuotePage.packageValues.formatDynamicPath("Robbery of a Custodian", 3).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					createQuotePage.packageValues
							.formatDynamicPath("Fire Extinguisher Recharge", 3).checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Base Package " + createQuotePage.packageBFireExtinguisher.getData() + " value "
							+ createQuotePage.packageValues.formatDynamicPath("Fire Extinguisher Recharge", 3).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					createQuotePage.packageValues.formatDynamicPath("Electronic Data", 3).checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Base Package " + createQuotePage.packageBElectronic.getData() + " value "
							+ createQuotePage.packageValues.formatDynamicPath("Electronic Data", 3).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					createQuotePage.packageValues
							.formatDynamicPath("Fire Department Service Charge", 3).checkIfElementIsDisplayed(),
					true, "Create Quote Page", "The Base Package" + createQuotePage.packageBFireDepartment.getData()
							+ " value " + createQuotePage.packageValues
									.formatDynamicPath("Fire Department Service Charge", 3).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					createQuotePage.packageValues.formatDynamicPath("Newly Acquired", 3).checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Base Package " + createQuotePage.packageBConstructedProperty.getData() + " value "
							+ createQuotePage.packageValues.formatDynamicPath("Newly Acquired", 3).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					createQuotePage.packageValues.formatDynamicPath("Outdoor Property", 3).checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Base Package " + createQuotePage.packageBOutdoorProperty.getData() + " value "
							+ createQuotePage.packageValues.formatDynamicPath("Outdoor Property", 3).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					createQuotePage.packageValues
							.formatDynamicPath("Valuable Papers and Records", 3).checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Base Package " + createQuotePage.packageBValuablePapersAndRecords.getData() + " value "
							+ createQuotePage.packageValues.formatDynamicPath("Valuable Papers and Records", 3)
									.getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					createQuotePage.packageValues.formatDynamicPath("Personal Effects", 3).checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Base Package " + createQuotePage.packageBPersonalEffects.getData() + " value "
							+ createQuotePage.packageValues.formatDynamicPath("Personal Effects", 3).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					createQuotePage.packageValues
							.formatDynamicPath("Property in Transit", 3).checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Base Package" + createQuotePage.packageBPropertyinTransit.getData() + " value "
							+ createQuotePage.packageValues.formatDynamicPath("Property in Transit", 3).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					createQuotePage.packageValues
							.formatDynamicPath("Property off Premises", 3).checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Base Package " + createQuotePage.packageBPropertyOffPremises.getData() + " value "
							+ createQuotePage.packageValues.formatDynamicPath("Property off Premises", 3).getData()
							+ " displayed is verified",
					false, false);
			Assertions
					.verify(createQuotePage.packageValues
							.formatDynamicPath("Covered Property - Building", 3).checkIfElementIsDisplayed(), true,
							"Create Quote Page",
							"The Base Package " + createQuotePage.packageBPerimeterExtensionBuilding.getData()
									+ " value " + createQuotePage.packageValues
											.formatDynamicPath("Covered Property - Building", 3).getData()
									+ " displayed is verified",
							false, false);
			Assertions.verify(
					createQuotePage.packageValues
							.formatDynamicPath("Covered Property - BPP", 3).checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Base Package " + createQuotePage.packageBPerimeterExtensionBpp.getData() + " value "
							+ createQuotePage.packageValues.formatDynamicPath("Covered Property - BPP", 3).getData()
							+ " displayed is verified",
					false, false);

			Assertions.verify(
					createQuotePage.packageValues.formatDynamicPath("Tenant Glass", 3).checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Base Package " + createQuotePage.packageBTenantGlass.getData() + " value "
							+ createQuotePage.packageValues.formatDynamicPath("Tenant Glass", 3).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					createQuotePage.packageValues
							.formatDynamicPath("Extended Period of Indemnity", 3).checkIfElementIsDisplayed(),
					true, "Create Quote Page", "The Base Package " + createQuotePage.packageBExtendedPeriod.getData()
							+ " value " + createQuotePage.packageValues
									.formatDynamicPath("Extended Period of Indemnity", 3).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(createQuotePage.packageValues
					.formatDynamicPath("Newly Acquired Business Personal Property", 3).checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Base Package" + createQuotePage.packageBNewlyAcquiredBpp.getData() + " value "
							+ createQuotePage.packageValues
									.formatDynamicPath("Newly Acquired Business Personal Property", 3).getData()
							+ " displayed is verified",
					false, false);

			// Click on coverage extension package
			createQuotePage.coverageExtensionPackageLink.scrollToElement();
			createQuotePage.coverageExtensionPackageLink.click();
			Assertions.passTest("Create Quote Page", "Clicked on Coverage Extension Package link");

			// Entering Quote Details
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Click on Request Bind button
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);

			// Get quote number
			quoteLength = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, quoteLength - 1);
			Assertions.passTest("Account Overview Page", "Quote number is : " + quoteNumber);

			// Click on view/print full quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View or Print Full Quote Link");

			// Asserting Base Package values from View or print full quote page
			Assertions.addInfo("View or Print Full Quote Page",
					"Asserting Base Package values from View or print full quote page");
			Assertions.verify(
					viewOrPrintFullQuotePage.packageValues
							.formatDynamicPath("Property in Your Covered Building", 1).checkIfElementIsDisplayed(),
					true, "View or Print Full Quote Page",
					"The Base Package Customers' Property in Your Covered Building value "
							+ viewOrPrintFullQuotePage.packageValues
									.formatDynamicPath("Property in Your Covered Building", 1).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.packageValues
							.formatDynamicPath("Electronic Data", 1).checkIfElementIsDisplayed(),
					true, "View or Print Full Quote Page",
					"The Base Package Electronic Data value "
							+ viewOrPrintFullQuotePage.packageValues.formatDynamicPath("Electronic Data", 1).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.packageValues
							.formatDynamicPath("Fire Department Service Charge", 1).checkIfElementIsDisplayed(),
					true, "View or Print Full Quote Page",
					"The Base Package Fire Department Service Charge value "
							+ viewOrPrintFullQuotePage.packageValues
									.formatDynamicPath("Fire Department Service Charge", 1).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.packageValues
							.formatDynamicPath("Fire Extinguisher Recharge", 1).checkIfElementIsDisplayed(),
					true, "View or Print Full Quote Page",
					"The Base Package Fire Extinguisher Recharge value " + viewOrPrintFullQuotePage.packageValues
							.formatDynamicPath("Fire Extinguisher Recharge", 1).getData() + " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.packageValues
							.formatDynamicPath("Lock Replacement", 1).checkIfElementIsDisplayed(),
					true, "View or Print Full Quote Page",
					"The Base Package Lock Replacement value "
							+ viewOrPrintFullQuotePage.packageValues.formatDynamicPath("Lock Replacement", 1).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.packageValues
							.formatDynamicPath("Newly Acquired BPP", 1).checkIfElementIsDisplayed(),
					true, "View or Print Full Quote Page",
					"The Base Package Newly Acquired BPP value " + viewOrPrintFullQuotePage.packageValues
							.formatDynamicPath("Newly Acquired BPP", 1).getData() + " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.packageValues
							.formatDynamicPath("Newly Acquired or Constructed Property", 1).checkIfElementIsDisplayed(),
					true, "View or Print Full Quote Page",
					"The Base Package Newly Acquired or Constructed Property value "
							+ viewOrPrintFullQuotePage.packageValues
									.formatDynamicPath("Newly Acquired or Constructed Property", 1).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.packageValues
							.formatDynamicPath("Outdoor Property", 1).checkIfElementIsDisplayed(),
					true, "View or Print Full Quote Page",
					"The Base Package Outdoor Property value "
							+ viewOrPrintFullQuotePage.packageValues.formatDynamicPath("Outdoor Property", 1).getData()
							+ " displayed is verified",
					false, false);
			Assertions
					.verify(viewOrPrintFullQuotePage.packageValues
							.formatDynamicPath("Covered Property - BPP", 1).checkIfElementIsDisplayed(), true,
							"View or Print Full Quote Page",
							"The Base Package  Perimeter Extension: Covered Property - BPP value "
									+ viewOrPrintFullQuotePage.packageValues
											.formatDynamicPath("Covered Property - BPP", 1).getData()
									+ " displayed is verified",
							false, false);
			Assertions
					.verify(viewOrPrintFullQuotePage.packageValues
							.formatDynamicPath("Covered Property - Building", 1).checkIfElementIsDisplayed(), true,
							"View or Print Full Quote Page",
							"The Base Package  Perimeter Extension: Covered Property - Building value "
									+ viewOrPrintFullQuotePage.packageValues
											.formatDynamicPath("Covered Property - Building", 1).getData()
									+ " displayed is verified",
							false, false);
			Assertions.verify(viewOrPrintFullQuotePage.packageValues
					.formatDynamicPath("Personal Effects and Property of Others", 1).checkIfElementIsDisplayed(), true,
					"View or Print Full Quote Page",
					"The Base Package  Personal Effects and Property of Others value "
							+ viewOrPrintFullQuotePage.packageValues
									.formatDynamicPath("Personal Effects and Property of Others", 1).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(viewOrPrintFullQuotePage.packageValues
					.formatDynamicPath("Property in Transit - Lesser of BPP Limit", 1).checkIfElementIsDisplayed(),
					true, "View or Print Full Quote Page",
					"The Base Package  Property in Transit - Lesser of BPP Limit value "
							+ viewOrPrintFullQuotePage.packageValues
									.formatDynamicPath("Property in Transit - Lesser of BPP Limit", 1).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.packageValues
							.formatDynamicPath("Property off Premises", 1).checkIfElementIsDisplayed(),
					true, "View or Print Full Quote Page",
					"The Base Package Property off Premises value " + viewOrPrintFullQuotePage.packageValues
							.formatDynamicPath("Property off Premises", 1).getData() + " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.packageValues.formatDynamicPath("Sewer", 1).checkIfElementIsDisplayed(),
					true, "View or Print Full Quote Page",
					"The Base Package Sewer, Drain, and Sump Back-up or Overflow value "
							+ viewOrPrintFullQuotePage.packageValues.formatDynamicPath("Sewer", 1).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.packageValues
							.formatDynamicPath("Direct Damage", 1).checkIfElementIsDisplayed(),
					true, "View or Print Full Quote Page",
					"The Base Package Utility Services - Direct Damage value "
							+ viewOrPrintFullQuotePage.packageValues.formatDynamicPath("Direct Damage", 1).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.packageValues
							.formatDynamicPath("Accounts Receivable", 1).checkIfElementIsDisplayed(),
					true, "View or Print Full Quote Page",
					"The Base Package Accounts Receivable value " + viewOrPrintFullQuotePage.packageValues
							.formatDynamicPath("Accounts Receivable", 1).getData() + " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.packageValues
							.formatDynamicPath("Fine Arts", 1).checkIfElementIsDisplayed(),
					true, "View or Print Full Quote Page",
					"The Base Package Fine Arts value "
							+ viewOrPrintFullQuotePage.packageValues.formatDynamicPath("Fine Arts", 1).getData()
							+ " displayed is verified",
					false, false);
			Assertions
					.verify(viewOrPrintFullQuotePage.packageValues
							.formatDynamicPath("Safe Burglary Coverage", 1).checkIfElementIsDisplayed(), true,
							"View or Print Full Quote Page",
							"The Base Package Robbery of a Custodian or Safe Burglary Coverage value "
									+ viewOrPrintFullQuotePage.packageValues
											.formatDynamicPath("Safe Burglary Coverage", 1).getData()
									+ " displayed is verified",
							false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.packageValues.formatDynamicPath("Spoilage", 1).checkIfElementIsDisplayed(),
					true, "View or Print Full Quote Page",
					"The Base Package Spoilage value "
							+ viewOrPrintFullQuotePage.packageValues.formatDynamicPath("Spoilage", 1).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.packageValues
							.formatDynamicPath("Tenant Glass", 1).checkIfElementIsDisplayed(),
					true, "View or Print Full Quote Page",
					"The Base Package Tenant Glass value "
							+ viewOrPrintFullQuotePage.packageValues.formatDynamicPath("Tenant Glass", 1).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.packageValues.formatDynamicPath("Theft", 1).checkIfElementIsDisplayed(),
					true, "View or Print Full Quote Page",
					"The Base Package Theft, Disappearance, or Destruction of Money and Securities value "
							+ viewOrPrintFullQuotePage.packageValues.formatDynamicPath("Theft", 1).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.packageValues
							.formatDynamicPath("Valuable Papers and Records", 1).checkIfElementIsDisplayed(),
					true, "View or Print Full Quote Page",
					"The Base Package Valuable Papers and Records value " + viewOrPrintFullQuotePage.packageValues
							.formatDynamicPath("Valuable Papers and Records", 1).getData() + " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.packageValues
							.formatDynamicPath("Extended Period of Indemnity", 1).checkIfElementIsDisplayed(),
					true, "View or Print Full Quote Page",
					"The Base Package Extended Period of Indemnity value "
							+ viewOrPrintFullQuotePage.packageValues
									.formatDynamicPath("Extended Period of Indemnity", 1).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.packageValues
							.formatDynamicPath("Utility Services - Time Element", 1).checkIfElementIsDisplayed(),
					true, "View or Print Full Quote Page",
					"The Base Package Utility Services - Time Element-Lesser of BI limit value "
							+ viewOrPrintFullQuotePage.packageValues
									.formatDynamicPath("Utility Services - Time Element", 1).getData()
							+ " displayed is verified",
					false, false);

			// Asserting Package A and Package B values from View or print full quote page
			Assertions.addInfo("View or Print Full Quote Page",
					"Asserting Package A and Package B values from View or print full quote page");
			ch = 'A';
			for (int i = 3; i > 1; i--) {
				Assertions.verify(
						viewOrPrintFullQuotePage.packageValues
								.formatDynamicPath("Property in Your Covered Building", i).checkIfElementIsDisplayed(),
						true, "View or Print Full Quote Page",
						"The Package  " + ch + " Customers' Property in Your Covered Building value "
								+ viewOrPrintFullQuotePage.packageValues
										.formatDynamicPath("Property in Your Covered Building", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						viewOrPrintFullQuotePage.packageValues
								.formatDynamicPath("Electronic Data", i).checkIfElementIsDisplayed(),
						true, "View or Print Full Quote Page",
						"The Package  " + ch + " Electronic Data value " + viewOrPrintFullQuotePage.packageValues
								.formatDynamicPath("Electronic Data", i).getData() + " displayed is verified",
						false, false);
				Assertions.verify(
						viewOrPrintFullQuotePage.packageValues
								.formatDynamicPath("Fire Department Service Charge", i).checkIfElementIsDisplayed(),
						true, "View or Print Full Quote Page",
						"The Package  " + ch + " Fire Department Service Charge value "
								+ viewOrPrintFullQuotePage.packageValues
										.formatDynamicPath("Fire Department Service Charge", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						viewOrPrintFullQuotePage.packageValues
								.formatDynamicPath("Fire Extinguisher Recharge", i).checkIfElementIsDisplayed(),
						true, "View or Print Full Quote Page",
						"The Package  " + ch + " Fire Extinguisher Recharge value "
								+ viewOrPrintFullQuotePage.packageValues
										.formatDynamicPath("Fire Extinguisher Recharge", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						viewOrPrintFullQuotePage.packageValues
								.formatDynamicPath("Lock Replacement", i).checkIfElementIsDisplayed(),
						true, "View or Print Full Quote Page",
						"The Package  " + ch + " Lock Replacement value " + viewOrPrintFullQuotePage.packageValues
								.formatDynamicPath("Lock Replacement", i).getData() + " displayed is verified",
						false, false);
				Assertions.verify(
						viewOrPrintFullQuotePage.packageValues
								.formatDynamicPath("Newly Acquired BPP", i).checkIfElementIsDisplayed(),
						true, "View or Print Full Quote Page",
						"The Package  "
								+ ch + " Newly Acquired BPP value " + viewOrPrintFullQuotePage.packageValues
										.formatDynamicPath("Newly Acquired BPP", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(viewOrPrintFullQuotePage.packageValues
						.formatDynamicPath("Newly Acquired or Constructed Property", i).checkIfElementIsDisplayed(),
						true, "View or Print Full Quote Page",
						"The Package  " + ch + " Newly Acquired or Constructed Property value "
								+ viewOrPrintFullQuotePage.packageValues
										.formatDynamicPath("Newly Acquired or Constructed Property", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						viewOrPrintFullQuotePage.packageValues
								.formatDynamicPath("Outdoor Property", i).checkIfElementIsDisplayed(),
						true, "View or Print Full Quote Page",
						"The Package  " + ch + " Outdoor Property value " + viewOrPrintFullQuotePage.packageValues
								.formatDynamicPath("Outdoor Property", i).getData() + " displayed is verified",
						false, false);
				Assertions
						.verify(viewOrPrintFullQuotePage.packageValues
								.formatDynamicPath("Covered Property - BPP", i).checkIfElementIsDisplayed(), true,
								"View or Print Full Quote Page",
								"The Package  " + ch + " Perimeter Extension: Covered Property - BPP value "
										+ viewOrPrintFullQuotePage.packageValues
												.formatDynamicPath("Covered Property - BPP", i).getData()
										+ " displayed is verified",
								false, false);
				Assertions.verify(
						viewOrPrintFullQuotePage.packageValues
								.formatDynamicPath("Covered Property - Building", i).checkIfElementIsDisplayed(),
						true, "View or Print Full Quote Page",
						"The Package  " + ch + " Perimeter Extension: Covered Property - Building value "
								+ viewOrPrintFullQuotePage.packageValues
										.formatDynamicPath("Covered Property - Building", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(viewOrPrintFullQuotePage.packageValues
						.formatDynamicPath("Personal Effects and Property of Others", i).checkIfElementIsDisplayed(),
						true, "View or Print Full Quote Page",
						"The Package  " + ch + " Personal Effects and Property of Others value "
								+ viewOrPrintFullQuotePage.packageValues
										.formatDynamicPath("Personal Effects and Property of Others", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(viewOrPrintFullQuotePage.packageValues
						.formatDynamicPath("Property in Transit - Lesser of BPP Limit", i).checkIfElementIsDisplayed(),
						true, "View or Print Full Quote Page",
						"The Package  " + ch + " Property in Transit - Lesser of BPP Limit value "
								+ viewOrPrintFullQuotePage.packageValues
										.formatDynamicPath("Property in Transit - Lesser of BPP Limit", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						viewOrPrintFullQuotePage.packageValues
								.formatDynamicPath("Property off Premises", i).checkIfElementIsDisplayed(),
						true, "View or Print Full Quote Page",
						"The Package  "
								+ ch + " Property off Premises value " + viewOrPrintFullQuotePage.packageValues
										.formatDynamicPath("Property off Premises", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						viewOrPrintFullQuotePage.packageValues
								.formatDynamicPath("Sewer", i).checkIfElementIsDisplayed(),
						true, "View or Print Full Quote Page",
						"The Package  " + ch + " Sewer, Drain, and Sump Back-up or Overflow value "
								+ viewOrPrintFullQuotePage.packageValues.formatDynamicPath("Sewer", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						viewOrPrintFullQuotePage.packageValues
								.formatDynamicPath("Direct Damage", i).checkIfElementIsDisplayed(),
						true, "View or Print Full Quote Page",
						"The Package  " + ch + " Utility Services - Direct Damage value "
								+ viewOrPrintFullQuotePage.packageValues.formatDynamicPath("Direct Damage", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						viewOrPrintFullQuotePage.packageValues
								.formatDynamicPath("Accounts Receivable", i).checkIfElementIsDisplayed(),
						true, "View or Print Full Quote Page",
						"The Package  "
								+ ch + " Accounts Receivable value " + viewOrPrintFullQuotePage.packageValues
										.formatDynamicPath("Accounts Receivable", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						viewOrPrintFullQuotePage.packageValues
								.formatDynamicPath("Fine Arts", i).checkIfElementIsDisplayed(),
						true, "View or Print Full Quote Page",
						"The Package  " + ch + " Fine Arts value "
								+ viewOrPrintFullQuotePage.packageValues.formatDynamicPath("Fine Arts", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions
						.verify(viewOrPrintFullQuotePage.packageValues
								.formatDynamicPath("Safe Burglary Coverage", i).checkIfElementIsDisplayed(), true,
								"View or Print Full Quote Page",
								"The Package  " + ch + " Robbery of a Custodian or Safe Burglary Coverage value "
										+ viewOrPrintFullQuotePage.packageValues
												.formatDynamicPath("Safe Burglary Coverage", i).getData()
										+ " displayed is verified",
								false, false);
				Assertions.verify(
						viewOrPrintFullQuotePage.packageValues
								.formatDynamicPath("Spoilage", i).checkIfElementIsDisplayed(),
						true, "View or Print Full Quote Page",
						"The Package  " + ch + " Spoilage value "
								+ viewOrPrintFullQuotePage.packageValues.formatDynamicPath("Spoilage", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						viewOrPrintFullQuotePage.packageValues
								.formatDynamicPath("Tenant Glass", i).checkIfElementIsDisplayed(),
						true, "View or Print Full Quote Page",
						"The Package  " + ch + " Tenant Glass value "
								+ viewOrPrintFullQuotePage.packageValues.formatDynamicPath("Tenant Glass", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						viewOrPrintFullQuotePage.packageValues
								.formatDynamicPath("Theft", i).checkIfElementIsDisplayed(),
						true, "View or Print Full Quote Page",
						"The Package  " + ch + " Theft, Disappearance, or Destruction of Money and Securities value "
								+ viewOrPrintFullQuotePage.packageValues.formatDynamicPath("Theft", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						viewOrPrintFullQuotePage.packageValues
								.formatDynamicPath("Valuable Papers and Records", i).checkIfElementIsDisplayed(),
						true, "View or Print Full Quote Page",
						"The Package  " + ch + " Valuable Papers and Records value "
								+ viewOrPrintFullQuotePage.packageValues
										.formatDynamicPath("Valuable Papers and Records", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						viewOrPrintFullQuotePage.packageValues
								.formatDynamicPath("Extended Period of Indemnity", i).checkIfElementIsDisplayed(),
						true, "View or Print Full Quote Page",
						"The Package  " + ch + " Extended Period of Indemnity value "
								+ viewOrPrintFullQuotePage.packageValues
										.formatDynamicPath("Extended Period of Indemnity", i).getData()
								+ " displayed is verified",
						false, false);
				Assertions.verify(
						viewOrPrintFullQuotePage.packageValues
								.formatDynamicPath("Utility Services - Time Element", i).checkIfElementIsDisplayed(),
						true, "View or Print Full Quote Page",
						"The Package  " + ch + " Utility Services - Time Element-Lesser of BI limit value "
								+ viewOrPrintFullQuotePage.packageValues
										.formatDynamicPath("Utility Services - Time Element", i).getData()
								+ " displayed is verified",
						false, false);
				ch++;
			}

			// Click on back button
			viewOrPrintFullQuotePage.backButton.click();

			// Click on RequestBind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request bind");

			// Enter details in request bind page
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
			homePage.searchReferral(quoteNumber);
			Assertions.passTest("Home Page", "Searched the Account successfully");

			// Approving referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			// Request Approval
			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Approval page loaded successfully", false, false);
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Getting the policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully ", false, false);
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy number is : " + policyNumber);

			// Clicking on view policysnapshot link
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();

			// Verifying BI and Package B values in Policy SnapShotPage
			Assertions.addInfo("View Policy Snap Shot Page",
					"Verifying BI and Package B values in Policy SnapShotPage");
			Assertions.verify(viewPolicySnapshotPage.businessIncomeData.checkIfElementIsDisplayed(), true,
					"View Policy Snap Shot Page", "Policy snapshot page loaded successfully", false, false);
			businessIncomeValue = viewPolicySnapshotPage.businessIncomeData.getData();
			Assertions.verify(viewPolicySnapshotPage.apcBusinessIncome.checkIfElementIsPresent(), true,
					"View Policy Snap Shot Page",
					"Business Income/Extra Expense/Rental Value " + businessIncomeValue + " displayed is verified",
					false, false);

			// Go to previous page
			viewPolicySnapshotPage.goBackButton.scrollToElement();
			viewPolicySnapshotPage.goBackButton.click();

			// Adding the CR IO-19683
			// Click on Endorse PB
			Assertions.addInfo("Endorse Policy Page", "Initiating endorsement and Changing Construction Type");
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// setting Endorsement Effective Date
			testData = data.get(dataValue2);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// Click on Edit location or building details link
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Edit location or building details link");

			// Entering new Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			locationPage.buildingLink.formatDynamicPath(1, 1).click();

			// Enter new Building Details
			buildingPage.editBuildingDetailsPNB(testData, 1, 1);
			buildingPage.reviewBuilding();

			// Clicking on Continue button
			buildingPage.continueButton.scrollToElement();
			buildingPage.continueButton.click();

			// Click on Continue endorsement button
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page Loaded successfully", false, false);
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			Assertions.passTest("Create Quote Page", "Clicked on Continue Endorsement Button");

			// click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Verifying the changes
			Assertions.addInfo("Endorse Policy Page", "Verifying the Changes in Endorse Policy Page");
			String constructionTypeFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(6).getData();
			String constructionTypeTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(7).getData();
			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(5).checkIfElementIsDisplayed(),
					true, "Endorse Summary Page", "The Construction Type " + constructionTypeFrom + " changed to : "
							+ constructionTypeTo + " displayed is verified",
					false, false);

			// clicking on next button in endorse policy page
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// Asserting the Modeling data like GC17, ELR Premium, peril AAl and Peril ERL
			// Labels and it's values for Alternative quote
			// Assert the presence of View Model Results link on Endorse Policy page
			endorsePolicyPage = new EndorsePolicyPage();
			endorsePolicyPage.viewModelResultsButton.scrollToElement();
			endorsePolicyPage.viewModelResultsButton.click();
			Assertions.addInfo("Scenario 01 ",
					" Guy Carpenter Scenario : Asserting the Modeling data like GC17, ELR Premium, peril AAl and Peril ERL Labels and it's values for Alternative quote");
			// Asserting the Modeling data like GC17, ELR Premium, peril AAl and Peril ERL
			// Labels and it's values
			// Verifying the GC17 Label
			Assertions.verify(rmsModelResultsPage.gc17Label.getData().contains("GC17"), true, "View Model Results Page",
					"Label displayed on View Model Results Page is : " + rmsModelResultsPage.gc17Label.getData(), false,
					false);
			// Verifying the AAL label and it's value
			Assertions.verify(
					rmsModelResultsPage.guyCarpenterAALLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.guyCarpenterAALLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.guyCarpenterAAL.checkIfElementIsDisplayed(),
					true, "View Model Results Page",
					"Guy Carpenter AAl Label is displayed as : " + rmsModelResultsPage.guyCarpenterAALLabel.getData()
							+ " and it's value displayed on View Model Results Page is: "
							+ rmsModelResultsPage.guyCarpenterAAL.getData(),
					false, false);

			// Verifying the ELR label and it's value
			Assertions.verify(
					rmsModelResultsPage.guyCarpenterELRLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.guyCarpenterELRLabel.checkIfElementIsPresent()
							&& rmsModelResultsPage.rmsModelResultValues
									.formatDynamicPath("Peril ELR", 1).checkIfElementIsDisplayed(),
					true, "View Model Results Page",
					"Guy Carpenter ELR Label is dasplayed as :" + rmsModelResultsPage.guyCarpenterELRLabel.getData()
							+ "and it's value displayed on View Model Results Page is: "
							+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril ELR", 1).getData(),
					false, false);

			// Verifying the ELR Premium Label and it's value
			Assertions.verify(
					rmsModelResultsPage.elrPremiumLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.elrPremiumLabel.checkIfElementIsPresent(),
					true, "View Model Results Page",
					"Guy Carpenter ELR Premium Label is diaplayed as " + rmsModelResultsPage.elrPremiumLabel.getData(),
					false, false);

			// Verifying the TIV label and It's value
			Assertions.verify(
					rmsModelResultsPage.tivValue.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.tivValue.checkIfElementIsPresent(),
					true, "View Model Results Page",
					"TIV value displayed on View Model Results Page is: " + rmsModelResultsPage.tivValue.getData(),
					false, false);

			// Calculating GC ELR value
			// Verify the calculation of ELR: GC Peril ELR = GC Peril AAL amount/ELR premium
			String ELRPremium = rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("ELR Premium", 1).getData()
					.replace("$", "").replace(",", "");
			String PerilAAL = rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril AAL", 1).getData()
					.replace("$", "").replace(",", "");
			double PerilELR = Precision.round((Double.parseDouble(PerilAAL) / Double.parseDouble(ELRPremium)) * 100, 1);
			String actualPerilELRStr = rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril ELR", 1)
					.getData().replace("%", "");
			double actualPerilELR = Double.parseDouble(actualPerilELRStr);

			Assertions.verify(PerilELR == actualPerilELR, true, "View Model Results Page",
					"The Actual and Calculated Peril ELR are matching", false, false);

			if (PerilELR == actualPerilELR) {
				Assertions.passTest("View Model Results Page", "Calculated Peril ELR: " + PerilELR + "%");
				Assertions.passTest("View Model Results Page", "Actual Peril ELR: " + actualPerilELR + "%");
			} else {
				Assertions.verify(PerilELR, actualPerilELR, "View Model Results Page",
						"The Actual and Calculated values are not matching", false, false);
			}

			rmsModelResultsPage.closeButton.waitTillVisibilityOfElement(60);
			rmsModelResultsPage.closeButton.click();

			// click on continue
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// clicking on complete button in endorse policy page
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// clicking on close button in endorse policy page
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Click on ES link on Policy summary page
			Assertions.addInfo("Policy Summary Page", "Verifying the Endorsement Details on Policy Summary Page");
			policySummaryPage.esLink.scrollToElement();
			policySummaryPage.esLink.click();
			Assertions.passTest("Policy Summary Page", "Clicked on ES link");

			Assertions.verify(endorsePolicyPage.closeButton.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Endorse Summary Page Loaded successfully", false, false);
			String constructionTypeFromSummary = endorsePolicyPage.endorsementSummary.formatDynamicPath(6).getData();
			String constructionTypeToSummary = endorsePolicyPage.endorsementSummary.formatDynamicPath(7).getData();
			Assertions
					.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(5).checkIfElementIsDisplayed(), true,
							"Endorse Summary Page", "The Construction Type " + constructionTypeFromSummary
									+ " changed to : " + constructionTypeToSummary + " displayed is verified",
							false, false);

			// click on close
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			Assertions.addInfo("Endorse Policy Page",
					"Verifying Special Conditions of Wind Coverage form is Selected or not");
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// setting Endorsement Effective Date
			testData = data.get(dataValue2);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// click on change coverage options link
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Coverage Options link");

			// click on modify forms
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page Loaded successfully", false, false);
			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();
			Assertions.passTest("Create Quote Page", "Clicked on Mofify forms button");
			Assertions.verify(
					modifyForms.override.checkIfElementIsPresent() && modifyForms.override.checkIfElementIsDisplayed(),
					true, "Modify Forms Page", "Modify Forms Page loaded successfully", false, false);
			modifyForms.specialConditionsAOP.waitTillPresenceOfElement(60);
			modifyForms.specialConditionsAOP.waitTillVisibilityOfElement(60);
			Assertions.verify(modifyForms.specialConditionsAOP.checkIfElementIsSelected(), true, "Modify Forms Page",
					"Special Conditions of Wind Coverage Form Selected by default is verified", false, false);

			// Go to Home Page and signout
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 03", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 03", "Executed Successfully");
			}
		}
	}
}