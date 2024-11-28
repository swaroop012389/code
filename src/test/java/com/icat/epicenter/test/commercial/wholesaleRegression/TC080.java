/** Program Description: Create a Wind Quote and click on View/Print Full quote. Assert all available data on the quote document and Create Policy and cancel it and assert the policy status.
 *  Author			   : John
 *  Date of Modified   : 08/28/2020
**/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.ReinstatePolicyPage;
import com.icat.epicenter.pom.RenewalIndicatorsPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC080 extends AbstractCommercialTest {

	public TC080() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID080.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the pages
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		ReinstatePolicyPage reinsatePolicyPage = new ReinstatePolicyPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		policySummarypage = new PolicySummaryPage();
		RenewalIndicatorsPage renewalIndicatorsPage = new RenewalIndicatorsPage();

		// Initializing the variables
		String quoteNumber;
		int dataValue1 = 0;
		Map<String, String> testData = data.get(dataValue1);
		boolean isTestPassed = false;

		try {
			// New Account creation
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully ", false, false);
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

			// Entering CreateQuotePage Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page Loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Click on View or print full quote Page
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View or Print Full quote link");

			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page", "View or Print Full Quote Page loaded successfully", false, false);

			// Assert values on view print full quote
			Assertions.verify(viewOrPrintFullQuotePage.quoteNumber.getData().contains(quoteNumber), true,
					"View/Print Full Quote Page", viewOrPrintFullQuotePage.quoteNumber.getData() + " is displayed",
					false, false);

			// Premium Details
			Assertions.addInfo("View/Print Full Quote Page", "Assert the Premium Details on Quote Document");
			for (int i = 1; i <= 3; i++) {
				Assertions.verify(
						viewOrPrintFullQuotePage.eqPremiumDetails.formatDynamicPath(i).checkIfElementIsDisplayed(),
						true, "View/Print Full Quote Page",
						viewOrPrintFullQuotePage.eqPremiumDetails.formatDynamicPath(i).getData()
								+ " displayed is verified",
						false, false);
			}

			for (int i = 5; i <= 6; i++) {
				Assertions.verify(
						viewOrPrintFullQuotePage.eqPremiumDetails.formatDynamicPath(i).checkIfElementIsDisplayed(),
						true, "View/Print Full Quote Page",
						viewOrPrintFullQuotePage.eqPremiumDetails.formatDynamicPath(i).getData()
								+ " displayed is verified",
						false, false);
			}

			Assertions.verify(
					viewOrPrintFullQuotePage.eqPremiumDetails.formatDynamicPath(12).checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page",
					"The " + viewOrPrintFullQuotePage.eqPremiumDetails.formatDynamicPath(12).getData()
							+ " displayed is verified",
					false, false);

			// Coverages, Limits and Deductibles
			Assertions.addInfo("View/Print Full Quote Page",
					"Assert the Coverages, Limits and Deductibles Details on Quote Document");
			Assertions.verify(
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("Coverages, Limits and Deductibles")
							.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("Coverages, Limits and Deductibles")
							.getData() + " section displayed is verified",
					false, false);

			for (int i = 1; i <= 2; i++) {
				Assertions.verify(
						viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(i).checkIfElementIsDisplayed(),
						true, "View/Print Full Quote Page",
						viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(i).getData() + " is displayed",
						false, false);
			}

			Assertions.verify(
					viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(8).getData()
							.contains(testData.get("L1B1-BldgAddr1")),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(8).getData() + " displayed is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(10).getData()
							.contains(testData.get("L1B1-BldgValue")),
					true, "View/Print Full Quote Page",
					"The Building Value " + viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(10).getData()
							+ " displayed is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(13).getData()
							.contains(testData.get("L1B1-BldgBPP")),
					true, "View/Print Full Quote Page",
					"The BPP value " + viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(13).getData()
							+ " displayed is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(16).checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page",
					"The Total limit of Insurance "
							+ viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(16).getData()
							+ " displayed is verified",
					false, false);

			// Standard Coverages
			Assertions.addInfo("View/Print Full Quote Page", "Assert the Standard Coverages Details on Quote Document");
			Assertions.verify(
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("Standard Coverage")
							.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("Standard Coverage").getData()
							+ " section displayed is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.standardCoverageValues.formatDynamicPath("Coinsurance", "1")
							.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The Coinsurance " + viewOrPrintFullQuotePage.standardCoverageValues
							.formatDynamicPath("Coinsurance", "1").getData() + " is displayed",
					false, false);

			Assertions
					.verify(viewOrPrintFullQuotePage.standardCoverageValues
							.formatDynamicPath("Coverage Basis", "1").checkIfElementIsDisplayed(), true,
							"View/Print Full Quote Page",
							"The Coverage Basis " + viewOrPrintFullQuotePage.standardCoverageValues
									.formatDynamicPath("Coverage Basis", "1").getData() + " is displayed",
							false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.standardCoverageValues
							.formatDynamicPath("Preservation of Property", "1").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The Preservation of Property " + viewOrPrintFullQuotePage.standardCoverageValues
							.formatDynamicPath("Preservation of Property", "1").getData() + " is displayed",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.standardCoverageValues
							.formatDynamicPath("Earthquake-Induced Water Loss", "1").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The Earthquake-Induced Water Loss "
							+ viewOrPrintFullQuotePage.standardCoverageValues
									.formatDynamicPath("Earthquake-Induced Water Loss", "1").getData()
							+ " is displayed",
					false, false);

			Assertions
					.verify(viewOrPrintFullQuotePage.standardCoverageValues
							.formatDynamicPath("Debris Removal", "1").checkIfElementIsDisplayed(), true,
							"View/Print Full Quote Page",
							"Debris Removal " + viewOrPrintFullQuotePage.standardCoverageValues
									.formatDynamicPath("Debris Removal", "1").getData() + " is displayed",
							false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.standardCoverageValues
							.formatDynamicPath("Pollutant Clean Up and Removal", "1").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"Pollutant Clean Up and Removal "
							+ viewOrPrintFullQuotePage.standardCoverageValues
									.formatDynamicPath("Pollutant Clean Up and Removal", "1").getData()
							+ " is displayed",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.standardCoverageValues
							.formatDynamicPath("Unscheduled Additional Property", "1").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The Unscheduled Additional Property "
							+ viewOrPrintFullQuotePage.standardCoverageValues
									.formatDynamicPath("Unscheduled Additional Property", "1").getData()
							+ " is displayed",
					false, false);

			// Selected Coverages
			Assertions.addInfo("View/Print Full Quote Page", "Assert the Selected Coverages Details on Quote Document");
			Assertions.verify(
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("Selected Endorsements")
							.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("Selected Endorsements").getData()
							+ " section displayed is verified",
					false, false);

			Assertions.verify(viewOrPrintFullQuotePage.selectedCoverageDetails
					.formatDynamicPath("Ordinance or Law", "1").getData().contains(testData.get("OrdinanceOrLaw")),
					true, "View/Print Full Quote Page",
					"Ordinance or Law value " + viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("Ordinance or Law", "1").getData() + " displayed is verified",
					false, false);

			Assertions.verify(viewOrPrintFullQuotePage.selectedCoverageDetails
					.formatDynamicPath("Electronic Data", "1").getData().contains(testData.get("ElectronicData")), true,
					"View/Print Full Quote Page",
					"Electronic Data value " + viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("Electronic Data", "1").getData() + " displayed is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("Food Spoilage", "1").getData().contains(testData.get("FoodSpoilage")),
					true, "View/Print Full Quote Page",
					"Food Spoilage value " + viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("Food Spoilage", "1").getData() + " displayed is verified",
					false, false);

			// Additional Coverages Available for Purchase Details
			Assertions.addInfo("View/Print Full Quote Page",
					"Assert the Additional Coverages Available for Purchase Details on Quote Document");
			Assertions.verify(
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath(
							"Additional Coverages Available for Purchase").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.mainSections
							.formatDynamicPath("Additional Coverages Available for Purchase").getData()
							+ " section displayed is verified",
					false, false);

			// Building Details
			Assertions.addInfo("View/Print Full Quote Page", "Assert the  Building Details on Quote Document");
			Assertions.verify(
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("Location 1, Building 1 Details")
							.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("Location 1, Building 1 Details").getData()
							+ " section displayed is verified",
					false, false);

			int k = 1;
			for (int i = 1; i <= 12; i++) {
				Assertions.verify(
						viewOrPrintFullQuotePage.buildingDetails.formatDynamicPath(k, k, i).checkIfElementIsDisplayed(),
						true, "View/Print Full Quote Page",
						viewOrPrintFullQuotePage.buildingDetails.formatDynamicPath(k, k, i).getData() + " is displayed",
						false, false);
			}

			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// Click on Request Bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
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
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// Approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
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
			String policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// Click on Cancel Policy
			Assertions.addInfo("Policy Summary Page", "Cancel the Policy");
			policySummarypage.cancelPolicy.scrollToElement();
			policySummarypage.cancelPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on cancel policy link");

			// Enter Cancellation Details
			cancelPolicyPage.enterCancellationDetails(testData);
			Assertions.passTest("Cancel Policy Page", "Policy Cancelled successfully");

			// Verifying the Renewal link not available on cancelled policy Summary
			Assertions.addInfo("Policy Summary Page", "Renewal Policy Link should not diasplayed");
			Assertions.verify(!policySummarypage.renewPolicy.checkIfElementIsPresent(), true, "Policy Summary Page",
					"Renewal Policy Link not diasplayed", false, false);

			// Asserting the Policy Status
			Assertions.addInfo("Policy Summary Page", "Assert the Policy Status After Cancellation");
			Assertions.verify(policySummarypage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Policy Status " + policySummarypage.policyStatus.getData() + " displayed is verified", false,
					false);

			// ReInstating the Policy
			policySummarypage.reinstatePolicy.checkIfElementIsPresent();
			policySummarypage.reinstatePolicy.scrollToElement();
			policySummarypage.reinstatePolicy.click();
			reinsatePolicyPage.enterReinstatePolicyDetails(testData);

			// getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Entering Expacc Details
			homePage.expaccLink.scrollToElement();
			homePage.expaccLink.click();
			expaccInfoPage.enterExpaccInfo(testData, policyNumber);
			Assertions.passTest("ExpaccInfo Page", "ExpaccInfo details entered successfully");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchPolicy(policyNumber);

			// Click on Renew Policy link
			policySummarypage.renewPolicy.scrollToElement();
			policySummarypage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy link");

			if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
					&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
				policyRenewalPage.continueRenewal.scrollToElement();
				policyRenewalPage.continueRenewal.click();
			}

			if (policyRenewalPage.yesButton.checkIfElementIsPresent()
					&& policyRenewalPage.yesButton.checkIfElementIsDisplayed()) {
				policyRenewalPage.yesButton.scrollToElement();
				policyRenewalPage.yesButton.click();
			}

//						policyRenewalPage.continueRenewal.scrollToElement();
//						policyRenewalPage.continueRenewal.click();

			// Clicking on view Release Renewal to producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Renewal to Producer Button");

			accountOverviewPage.viewPreviousPolicyButton.scrollToElement();
			accountOverviewPage.viewPreviousPolicyButton.click();

			// click on renewal indicators link

			policySummarypage.renewalIndicators.scrollToElement();
			policySummarypage.renewalIndicators.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renewal Indicators link");

			// select non renewal checkbox
			Assertions.verify(renewalIndicatorsPage.updateButton.checkIfElementIsDisplayed(), true,
					"Renewal Indicators Page", "Renewal Indicators Page loaded successfully", false, false);
			renewalIndicatorsPage.nonRenewal.scrollToElement();
			renewalIndicatorsPage.nonRenewal.select();
			Assertions.passTest("Renewal Indicators Page", "Selected the Non Renewal Checkbox");

			// select non renewal reason and enter legal notice wording
			renewalIndicatorsPage.nonRenewalReasonArrow.scrollToElement();
			renewalIndicatorsPage.nonRenewalReasonArrow.click();
			renewalIndicatorsPage.nonRenewalReasonOption.formatDynamicPath(testData.get("NonRenewalReason"))
					.scrollToElement();
			renewalIndicatorsPage.nonRenewalReasonOption.formatDynamicPath(testData.get("NonRenewalReason")).click();
			renewalIndicatorsPage.nonRenewalLegalNoticeWording.setData(testData.get("LegalNoticeWording"));

			// click on update
			renewalIndicatorsPage.updateButton.scrollToElement();
			renewalIndicatorsPage.updateButton.click();
			Assertions.passTest("Renewal Indicators Page", "Clicked on Update Button");
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page Loaded successfully", false, false);

			// Verifying the Renewal Indicator message on the Policy Summary Page
			Assertions.addInfo("Policy Summary Page",
					"Verifying the Renewal Indicator message on the Policy Summary Page");
			Assertions.verify(policySummarypage.renewalMessage.getData().contains(
					"The renewal has already been released to the producer, so it could not be deleted! Make sure you do something about this!"),
					true, "Policy Summary Page", "Verifying the Renewal Indicator message on Policy Summary Page",
					false, false);

			// Signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 80", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 80", "Executed Successfully");
			}
		}
	}
}
