/*Program Description: Create renewal policy perform endorsement changing premium value,selecting wave premium check box and change policy expiration date,verify sltf value calculated correctly and wave premium row added in endorse summary page
 * Author            : Pavan Mule
 * Date of creation  : 10/02/2022
 */
package com.icat.epicenter.test.commercial.retailRegression;

import java.text.DecimalFormat;
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

public class TC041 extends AbstractCommercialTest {

	public TC041() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID041.xls";
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
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		DecimalFormat df = new DecimalFormat("0.00");

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

			// click on view Previous policy
			accountOverviewPage.viewPreviousPolicyButton.scrollToElement();
			accountOverviewPage.viewPreviousPolicyButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Previous Policy Button");

			// click on Endorse NPB link
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// click on ok
			if (endorsePolicyPage.okButton.checkIfElementIsPresent()
					&& endorsePolicyPage.okButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.okButton.scrollToElement();
				endorsePolicyPage.okButton.click();
			}

			// setting Endoresment Effective Date
			testData = data.get(data_Value2);
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

			// entering details in create quote page
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialPNB(testData);
			Assertions.passTest("Create Quote Page", "Details modified successfully");

			// click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// clicking on next button in endorse policy page
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Next Button");

			// click on continue
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Complete Button");

			// click on No,Leave the Renewal Account as it is Button displayed
			Assertions.addInfo("Scenario 01", "Verifying the presence of No,Leave the Renewal Account as it is Button");
			Assertions.verify(endorsePolicyPage.makePolicyUnAutomated_No.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "No,Leave the Renewal Account as it is Button displayed is verified", false,
					false);
			endorsePolicyPage.makePolicyUnAutomated_No.scrollToElement();
			endorsePolicyPage.makePolicyUnAutomated_No.click();
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// click on close
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// click on view active renewal
			policySummarypage.viewActiveRenewal.scrollToElement();
			policySummarypage.viewActiveRenewal.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Active Renewal link");

			// click on release renewal to producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account overview page", "Clicked on release renewal to producer button");

			// click on request bind button
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account overview page", "Clicked on request bind button");

			// Enter bind details
			Assertions.verify(
					requestBindPage.submit.checkIfElementIsPresent()
							&& requestBindPage.submit.checkIfElementIsDisplayed(),
					true, "Request bind page", "Request bind page loaded successfully", false, false);
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
			Assertions.passTest("Policy summary page", "Renewal policy number is : " + policyNumber);

			// Click on endorse PB link
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();
			Assertions.passTest("Policy summary page", "Clicked on endorse PB link");

			// Enter effective date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse policy page", "Endorse policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.appendData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse policy page", "Entered transaction effective date");

			// Click on change coverage options link
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse policy page", "Clicked on change coverage options link");

			// Update building value
			Assertions.passTest("Create quote page", "Original building value is " + testData.get("L1B1-BldgValue"));
			testData = data.get(data_Value2);
			createQuotePage.buildingValue.formatDynamicPath(0, 0).scrollToElement();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).clearData();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).tab();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).waitTillPresenceOfElement(60);
			createQuotePage.buildingValue.formatDynamicPath(0, 0).waitTillVisibilityOfElement(60);
			createQuotePage.buildingValue.formatDynamicPath(0, 0).appendData(testData.get("L1B1-BldgValue"));
			Assertions.passTest("Create quote page", "Latest building value is " + testData.get("L1B1-BldgValue"));
			createQuotePage.buildingValue.formatDynamicPath(0, 0).tab();
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			Assertions.passTest("Create quote page", "Clicked on continue endorsement button");

			// click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// click on next button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse policy page", "Clicked on next button");

			// click on continue
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Asserting SLTF calculated correctly after premium change
			Assertions.addInfo("Scenario 02",
					"Asserting Transaction/Annual/Term SLTF values are calculated correctly after premium change in endorse policy page");
			Assertions.passTest("Alabama state SLTF percentage",
					"Alabama state SLTF percentage is " + testData.get("SLTFValue") + "%");
			for (int i = 2; i <= 4; i++) {
				double premium = Double.parseDouble(endorsePolicyPage.transactionPremiumFee.formatDynamicPath(i)
						.getData().replace("$", "").replace(",", ""));
				double inspectionFee = Double.parseDouble(endorsePolicyPage.inspectionFee.formatDynamicPath(i).getData()
						.replace("$", "").replace(",", ""));
				double policyFee = Double.parseDouble(
						endorsePolicyPage.policyFee.formatDynamicPath(i).getData().replace("$", "").replace(",", ""));
				double sltfActual = Double.parseDouble(endorsePolicyPage.premium
						.formatDynamicPath("surplusLinesTaxesAndFees", i).getData().replace("$", "").replace(",", ""));
				double totalActual = Double.parseDouble(
						endorsePolicyPage.totalTerm.formatDynamicPath(i).getData().replace("$", "").replace(",", ""));
				double surplusContributionValueActual = Double.parseDouble(endorsePolicyPage.surplusContributionValue
						.formatDynamicPath(i).getData().replace("$", "").replace(",", ""));
				double sltfCalculated = (premium + inspectionFee + policyFee + surplusContributionValueActual)
						* (Double.parseDouble(testData.get("SLTFValue"))) / 100;
				double totalCalculated = premium + inspectionFee + policyFee + sltfCalculated
						+ surplusContributionValueActual;

				if (i == 2) {
					Assertions.passTest("Endorse policy page ", "Transaction premium is " + "$" + premium);
					Assertions.passTest("Endorse policy page ", "Transaction inspection fee is " + "$" + inspectionFee);
					Assertions.passTest("Endorse policy page ", "Transaction policy fee is " + "$" + policyFee);
					Assertions.passTest("Endorse policy page ",
							"Transaction SLTF calculated value is " + "$" + df.format(sltfCalculated));
					Assertions.passTest("Endorse policy page ",
							"Transaction totalpremium calculated value is " + "$" + df.format(totalCalculated));

					if (Precision.round(Math.abs(Precision.round(sltfActual, 2)) - Precision.round(sltfCalculated, 2),
							2) < 0.5) {
						Assertions.passTest("Endorse policy Page",
								"Calculated Transaction Surplus Lines Taxes and Fees : " + "$"
										+ df.format(sltfCalculated));
						Assertions.passTest("Endorse policy Page",
								"Actual Transaction Surplus Lines Taxes and Fees : " + "$" + sltfActual);
					} else {
						Assertions.passTest("Endorse policy Page",
								"The Difference between actual SLTF and calculated SLTF is more than 0.5");
					}

					if (Precision.round(Math.abs(Precision.round(totalActual, 2)) - Precision.round(totalCalculated, 2),
							2) < 0.9) {
						Assertions.passTest("Endorse policy Page",
								"Calculated Transaction Total Premium is  : " + "$" + df.format(totalCalculated));
						Assertions.passTest("Endorse policy Page",
								"Actual Transaction Total Premium is : " + "$" + totalActual);
					} else {
						Assertions.passTest("Endorse policy Page",
								"The Difference between actual and calculated total premium is more than 0.9");
					}

				} else if (i == 3) {
					Assertions.passTest("Endorse policy page ", "Annual premium is " + "$" + premium);
					Assertions.passTest("Endorse policy page ", "Annual inspection fee is " + "$" + inspectionFee);
					Assertions.passTest("Endorse policy page ", "Annual policy fee is " + "$" + policyFee);
					Assertions.passTest("Endorse policy page ",
							"Annual SLTF calculated values is " + "$" + df.format(sltfCalculated));
					Assertions.passTest("Endorse policy page ",
							"Annual totalpremium calculated value is " + "$" + df.format(totalCalculated));
					// Asserting Annual sltf calculated correctly
					if (Precision.round(Math.abs(Precision.round(sltfActual, 2)) - Precision.round(sltfCalculated, 2),
							2) < 0.5) {
						Assertions.passTest("Endorse policy Page",
								"Calculated Annual Surplus Lines Taxes and Fees : " + "$" + df.format(sltfCalculated));
						Assertions.passTest("Endorse policy Page",
								"Actual Annual Surplus Lines Taxes and Fees : " + "$" + sltfActual);
					} else {
						Assertions.passTest("Endorse policy Page",
								"The Difference between actual SLTF and calculated SLTF is more than 0.5");
					}

					if (Precision.round(Math.abs(Precision.round(totalActual, 2)) - Precision.round(totalCalculated, 2),
							2) < 0.9) {
						Assertions.passTest("Endorse policy Page",
								"Calculated Annual Total Premium is  : " + "$" + df.format(totalCalculated));
						Assertions.passTest("Endorse policy Page",
								"Actual Annual Total Premium is : " + "$" + totalActual);
					} else {
						Assertions.passTest("Endorse policy Page",
								"The Difference between actual and calculated total premium is more than 0.9");
					}
				} else {
					Assertions.passTest("Endorse policy page ", "Term premium is " + "$" + premium);
					Assertions.passTest("Endorse policy page ", "Term inspection fee is " + "$" + inspectionFee);
					Assertions.passTest("Endorse policy page ", "Term policy fee is " + "$" + policyFee);
					Assertions.passTest("Endorse policy page ",
							"Term SLTF calculated values is " + "$" + df.format(sltfCalculated));
					Assertions.passTest("Endorse policy page ",
							"Term totalpremium calculated value is " + "$" + df.format(totalCalculated));
					// Asserting Term sltf calculated correctly
					if (Precision.round(Math.abs(Precision.round(sltfActual, 2)) - Precision.round(sltfCalculated, 2),
							2) < 0.5) {
						Assertions.passTest("Endorse policy Page",
								"Calculated Term Surplus Lines Taxes and Fees : " + "$" + df.format(sltfCalculated));
						Assertions.passTest("Endorse policy Page",
								"Actual Term Surplus Lines Taxes and Fees : " + "$" + sltfActual);
					} else {
						Assertions.passTest("Endorse policy Page",
								"The Difference between actual SLTF and calculated SLTF is more than 0.5");
					}

					if (Precision.round(Math.abs(Precision.round(totalActual, 2)) - Precision.round(totalCalculated, 2),
							2) < 0.9) {
						Assertions.passTest("Endorse policy Page",
								"Calculated Term Total Premium is  : " + "$" + df.format(totalCalculated));
						Assertions.passTest("Endorse policy Page",
								"Actual Term Total Premium is : " + "$" + totalActual);
					} else {
						Assertions.passTest("Endorse policy Page",
								"The Difference between actual and calculated total premium is more than 0.9");
					}
				}
			}
			Assertions.addInfo("Scenario 02", "Scenario 02 ended");

			// Complete the endorsement
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse policy page", "Clicked on complete button");

			// Close the endorsement transaction
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse policy page", "Clicked on close button");

			// Click on endorse PB link
			testData = data.get(data_Value1);
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Enter effective date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse policy page", "Endorse policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("PolicyExpirationDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse policy page", "Entered transaction effective date");

			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Enter new policy expiry date
			testData = data.get(data_Value2);
			endorsePolicyPage.policyExpirationDate.scrollToElement();
			endorsePolicyPage.policyExpirationDate.setData(testData.get("PolicyEffDate"));
			endorsePolicyPage.policyExpirationDate.tab();
			endorsePolicyPage.changeExpirationDate.scrollToElement();
			endorsePolicyPage.changeExpirationDate.click();
			Assertions.passTest("Endorse policy page",
					"Entered policy expiration date is " + testData.get("PolicyEffDate"));

			// click on continue button
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// click on next button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse policy page", "Clicked on next button");

			// click on continue button
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Asserting SLTF calculated correctly after policy expiry date change
			Assertions.addInfo("Scenario 03",
					"Asserting Transaction/Annual/Term SLTF values are calculated correctly after policy expiry date change in endorse policy page");
			for (int i = 2; i <= 4; i++) {
				double premium = Double.parseDouble(endorsePolicyPage.transactionPremiumFee.formatDynamicPath(i)
						.getData().replace("$", "").replace(",", ""));
				double inspectionFee = Double.parseDouble(endorsePolicyPage.inspectionFee.formatDynamicPath(i).getData()
						.replace("$", "").replace(",", ""));
				double policyFee = Double.parseDouble(
						endorsePolicyPage.policyFee.formatDynamicPath(i).getData().replace("$", "").replace(",", ""));
				double sltfActual = Double.parseDouble(endorsePolicyPage.premium
						.formatDynamicPath("surplusLinesTaxesAndFees", i).getData().replace("$", "").replace(",", ""));
				double totalActual = Double.parseDouble(
						endorsePolicyPage.totalTerm.formatDynamicPath(i).getData().replace("$", "").replace(",", ""));
				double surplusContributionValueActual = Double.parseDouble(endorsePolicyPage.surplusContributionValue
						.formatDynamicPath(i).getData().replace("$", "").replace(",", ""));
				double sltfCalculated = (premium + inspectionFee + policyFee + surplusContributionValueActual)
						* (Double.parseDouble(testData.get("SLTFValue"))) / 100;
				double totalCalculated = premium + inspectionFee + policyFee + sltfCalculated
						+ surplusContributionValueActual;

				if (i == 2) {
					Assertions.passTest("Endorse policy page ", "Transaction premium is " + "$" + premium);
					Assertions.passTest("Endorse policy page ", "Transaction inspection fee is " + "$" + inspectionFee);
					Assertions.passTest("Endorse policy page ", "Transaction policy fee is " + "$" + policyFee);
					Assertions.passTest("Endorse policy page ",
							"Transaction SLTF calculated values is " + "$" + df.format(sltfCalculated));
					Assertions.passTest("Endorse policy page ",
							"Transaction totalpremium calculated value is " + "$" + df.format(totalCalculated));
					String str_sltfCalculated = df.format(sltfCalculated).replace("-", "");
					String str_totalCalculated = df.format(totalCalculated).replace("-", "");

					// Asserting Transaction sltf calculated correctly
					if (Precision.round(Math.abs(Precision.round(sltfActual, 2))
							- Precision.round(Double.parseDouble(str_sltfCalculated), 2), 2) < 0.9) {
						Assertions.passTest("Endorse policy Page",
								"Calculated Transaction Surplus Lines Taxes and Fees : " + "$"
										+ df.format(sltfCalculated));
						Assertions.passTest("Endorse policy Page",
								"Actual Transaction Surplus Lines Taxes and Fees : " + "$" + sltfActual);
					} else {
						Assertions.passTest("Endorse policy Page",
								"The Difference between actual and calculated sltf premium is more than 0.9");
					}

					if (Precision.round(Math.abs(Precision.round(totalActual, 2))
							- Precision.round(Double.parseDouble(str_totalCalculated), 2), 2) < 0.9) {
						Assertions.passTest("Endorse policy Page",
								"Calculated Transaction Total Premium is  : " + "$" + df.format(totalCalculated));
						Assertions.passTest("Endorse policy Page",
								"Actual Transaction Total Premium is : " + "$" + totalActual);
					} else {
						Assertions.passTest("Endorse policy Page",
								"The Difference between actual and calculated total premium is more than 0.9");
					}

				} else if (i == 3) {
					Assertions.passTest("Endorse policy page ", "Annual premium is " + "$" + premium);
					Assertions.passTest("Endorse policy page ", "Annual inspection fee is " + "$" + inspectionFee);
					Assertions.passTest("Endorse policy page ", "Annual policy fee is " + "$" + policyFee);
					Assertions.passTest("Endorse policy page ",
							"Annual SLTF calculated values is " + "$" + df.format(sltfCalculated));
					Assertions.passTest("Endorse policy page ",
							"Annual totalpremium calculated value is " + "$" + df.format(totalCalculated));
					// Asserting Annual sltf calculated correctly
					if (Precision.round(Math.abs(Precision.round(sltfActual, 2)) - Precision.round(sltfCalculated, 2),
							2) < 0.5) {
						Assertions.passTest("Endorse policy Page",
								"Calculated Annual Surplus Lines Taxes and Fees : " + "$" + df.format(sltfCalculated));
						Assertions.passTest("Endorse policy Page",
								"Actual Annual Surplus Lines Taxes and Fees : " + "$" + sltfActual);
					} else {
						Assertions.passTest("Endorse policy Page",
								"The Difference between actual SLTF and calculated SLTF is more than 0.5");
					}

					if (Precision.round(Math.abs(Precision.round(totalActual, 2)) - Precision.round(totalCalculated, 2),
							2) < 0.5) {
						Assertions.passTest("Endorse policy Page",
								"Calculated Annual Total Premium is  : " + "$" + df.format(totalCalculated));
						Assertions.passTest("Endorse policy Page",
								"Actual Annual Total Premium is : " + "$" + totalActual);
					} else {
						Assertions.passTest("Endorse policy Page",
								"The Difference between actual and calculated total premium is more than 0.5");
					}
				} else {
					Assertions.passTest("Endorse policy page ", "Term premium is " + "$" + premium);
					Assertions.passTest("Endorse policy page ", "Term inspection fee is " + "$" + inspectionFee);
					Assertions.passTest("Endorse policy page ", "Term policy fee is " + "$" + policyFee);
					Assertions.passTest("Endorse policy page ",
							"Term SLTF calculated values is " + "$" + df.format(sltfCalculated));
					Assertions.passTest("Endorse policy page ",
							"Term totalpremium calculated value is " + "$" + df.format(totalCalculated));

					// Asserting Term sltf calculated correctly
					if (Precision.round(Math.abs(Precision.round(sltfActual, 2)) - Precision.round(sltfCalculated, 2),
							2) < 0.9) {
						Assertions.passTest("Endorse policy Page",
								"Calculated Term Surplus Lines Taxes and Fees : " + "$" + df.format(sltfCalculated));
						Assertions.passTest("Endorse policy Page",
								"Actual Term Surplus Lines Taxes and Fees : " + "$" + sltfActual);
					} else {
						Assertions.passTest("Endorse policy Page",
								"The Difference between actual SLTF and calculated SLTF is more than 0.9");
					}

					if (Precision.round(Math.abs(Precision.round(totalActual, 2)) - Precision.round(totalCalculated, 2),
							2) < 0.9) {
						Assertions.passTest("Endorse policy Page",
								"Calculated Term Total Premium is  : " + "$" + df.format(totalCalculated));
						Assertions.passTest("Endorse policy Page",
								"Actual Term Total Premium is : " + "$" + totalActual);
					} else {
						Assertions.passTest("Endorse policy Page",
								"The Difference between actual and calculated total premium is more than 0.9");
					}
				}
			}
			Assertions.addInfo("Scenario 03", "Scenario 03 ended");

			// Complete the endorsement
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse policy page", "Clicked on complete button");

			// click on continue button
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Close the endorsement transaction
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse policy page", "Clicked on close button");

			// Click on endorse PB link
			testData = data.get(data_Value1);
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();
			Assertions.passTest("Policy summary page", "Clicked on endorse PB link");

			// Enter effective date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse policy page", "Endorse policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.appendData(testData.get("PolicyExpirationDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse policy page", "Entered transaction effective date");

			// Click on change coverage options link
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse policy page", "Clicked on change coverage options link");

			// Update building value
			testData = data.get(data_Value2);
			createQuotePage.buildingValue.formatDynamicPath(0, 0).scrollToElement();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).clearData();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).tab();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).waitTillPresenceOfElement(60);
			createQuotePage.buildingValue.formatDynamicPath(0, 0).waitTillVisibilityOfElement(60);
			createQuotePage.buildingValue.formatDynamicPath(0, 0).appendData(testData.get("L1B1-BldgValue"));
			createQuotePage.buildingValue.formatDynamicPath(0, 0).tab();
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			Assertions.passTest("Create quote page", "Clicked on continue endorsement button successfully");

			// click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// click on next button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse policy page", "Clicked on next button");

			// click on continue button
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Click on override fees button
			endorsePolicyPage.overrideFeesButton.scrollToElement();
			endorsePolicyPage.overrideFeesButton.click();
			Assertions.passTest("Endorse policy page", "Clicked on override fees button");

			// select wave premium check box
			endorsePolicyPage.waivepremium.scrollToElement();
			endorsePolicyPage.waivepremium.select();
			Assertions.verify(
					endorsePolicyPage.waivepremium.checkIfElementIsPresent()
							&& endorsePolicyPage.waivepremium.checkIfElementIsDisplayed(),
					true, "Endorse policy page", "Wave premium check box selected sucessfully ", false, false);
			endorsePolicyPage.saveAndCloseButton.scrollToElement();
			endorsePolicyPage.saveAndCloseButton.click();

			// Asserting SLTF calculated correctly after wave premium check box selected
			Assertions.addInfo("Scenario 04",
					"Asserting Transaction/Annual/Term SLTF values are calculated correctly after wave premium check box selectede in endorse policy page");
			for (int i = 2; i <= 4; i++) {
				double premium = Double.parseDouble(endorsePolicyPage.transactionPremiumFee.formatDynamicPath(i)
						.getData().replace("$", "").replace(",", ""));
				double inspectionFee = Double.parseDouble(endorsePolicyPage.inspectionFee.formatDynamicPath(i).getData()
						.replace("$", "").replace(",", ""));
				double policyFee = Double.parseDouble(
						endorsePolicyPage.policyFee.formatDynamicPath(i).getData().replace("$", "").replace(",", ""));
				double sltfActual = Double.parseDouble(endorsePolicyPage.premium
						.formatDynamicPath("surplusLinesTaxesAndFees", i).getData().replace("$", "").replace(",", ""));
				double totalActual = Double.parseDouble(
						endorsePolicyPage.totalTerm.formatDynamicPath(i).getData().replace("$", "").replace(",", ""));
				double surplusContributionValueActual = Double.parseDouble(endorsePolicyPage.surplusContributionValue
						.formatDynamicPath(i).getData().replace("$", "").replace(",", ""));
				double sltfCalculated = (premium + inspectionFee + policyFee + surplusContributionValueActual)
						* (Double.parseDouble(testData.get("SLTFValue"))) / 100;
				double totalCalculated = premium + inspectionFee + policyFee + sltfCalculated
						+ surplusContributionValueActual;

				if (i == 2) {
					Assertions.passTest("Endorse policy page ", "Transaction premium is " + "$" + premium);
					Assertions.passTest("Endorse policy page ", "Transaction inspection fee is " + "$" + inspectionFee);
					Assertions.passTest("Endorse policy page ", "Transaction policy fee is " + "$" + policyFee);
					Assertions.passTest("Endorse policy page ",
							"Transaction SLTF calculated values is " + "$" + df.format(sltfCalculated));
					Assertions.passTest("Endorse policy page ",
							"Transaction totalpremium calculated value is " + "$" + df.format(totalCalculated));

					// Asserting Transaction sltf calculated correctly
					Assertions.verify(sltfActual, sltfCalculated, "Endorse policy page",
							"Transaction SLTF, actual and calculated values are matching after wave premium check box selected. Actual SLTF value is "
									+ "$" + sltfActual,
							false, false);
					Assertions.verify(totalActual, totalCalculated, "Endorse policy page",
							"Transaction total, actual and calculated values are matching after wave premium check box selected. Actual total premium is "
									+ "$" + totalActual,
							false, false);
					Assertions.verify(
							endorsePolicyPage.premium.formatDynamicPath("waivedPremium", i).checkIfElementIsDisplayed(),
							true, "Endorse policy page",
							"After wave premium check box selecting, for transaction wave premium row added is verified, wave premium is "
									+ "$" + endorsePolicyPage.premium.formatDynamicPath("waivedPremium", i).getData(),
							false, false);

				} else if (i == 3) {
					Assertions.passTest("Endorse policy page ", "Annual premium is " + "$" + premium);
					Assertions.passTest("Endorse policy page ", "Annual inspection fee is " + "$" + inspectionFee);
					Assertions.passTest("Endorse policy page ", "Annual policy fee is " + "$" + policyFee);
					Assertions.passTest("Endorse policy page ",
							"Annual SLTF calculated values is " + "$" + df.format(sltfCalculated));
					Assertions.passTest("Endorse policy page ",
							"Annual totalpremium calculated value is " + "$" + df.format(totalCalculated));

					// Asserting Annual sltf calculated correctly
					if (Precision.round(Math.abs(Precision.round(sltfActual, 2)) - Precision.round(sltfCalculated, 2),
							2) < 0.9) {
						Assertions.passTest("Endorse policy Page",
								"Annual SLTF, actual and calculated values are matching after wave premium check box selected. Actaul sltf value is: "
										+ "$" + sltfActual);
					} else {
						Assertions.passTest("Endorse policy Page",
								"The Difference between actual SLTF and calculated SLTF is more than 0.5");
					}
					if (Precision.round(Math.abs(Precision.round(totalActual, 2)) - Precision.round(totalCalculated, 2),
							2) < 0.9) {
						Assertions.passTest("Endorse policy Page",
								"Annual total, actual and calculated values are matching after wave premium check box selected. Actual total premium is: "
										+ "$" + totalActual);
					} else {
						Assertions.passTest("Endorse policy Page",
								"The Difference between actual total and calculated total is more than 0.5");
					}
					Assertions.verify(
							endorsePolicyPage.premium.formatDynamicPath("waivedPremium", i).checkIfElementIsDisplayed(),
							true, "Endorse policy page",
							"After wave premium check box selecting, for anual wave premium row added is verified, wave premium is "
									+ "$" + endorsePolicyPage.premium.formatDynamicPath("waivedPremium", i).getData(),
							false, false);
				} else {
					Assertions.passTest("Endorse policy page ", "Term premium is " + "$" + premium);
					Assertions.passTest("Endorse policy page ", "Term inspection fee is " + "$" + inspectionFee);
					Assertions.passTest("Endorse policy page ", "Term policy fee is " + "$" + policyFee);
					Assertions.passTest("Endorse policy page ",
							"Term SLTF calculated values is " + "$" + df.format(sltfCalculated));
					Assertions.passTest("Endorse policy page ",
							"Term totalpremium calculated value is " + "$" + df.format(totalCalculated));

					// Asserting Term sltf calculated correctly
					if (Precision.round(Math.abs(Precision.round(sltfActual, 2)) - Precision.round(sltfCalculated, 2),
							2) < 0.5) {
						Assertions.passTest("Endorse policy Page",
								"Calculated Term Surplus Lines Taxes and Fees : " + "$" + df.format(sltfCalculated));
						Assertions.passTest("Endorse policy Page",
								"Actual Term Surplus Lines Taxes and Fees : " + "$" + sltfActual);
					} else {
						Assertions.passTest("Endorse policy Page",
								"The Difference between actual SLTF and calculated SLTF is more than 0.5");
					}

					if (Precision.round(Math.abs(Precision.round(totalActual, 2)) - Precision.round(totalCalculated, 2),
							2) < 0.9) {
						Assertions.passTest("Endorse policy Page",
								"Calculated Term Total Premium is  : " + "$" + df.format(totalCalculated));
						Assertions.passTest("Endorse policy Page",
								"Actual Term Total Premium is : " + "$" + totalActual);
					} else {
						Assertions.passTest("Endorse policy Page",
								"The Difference between actual and calculated total premium is more than 0.9");
					}
					Assertions.verify(
							endorsePolicyPage.premium.formatDynamicPath("waivedPremium", i).checkIfElementIsDisplayed(),
							true, "Endorse policy page",
							"After wave premium check box selecting, for term total wave premium row added is verified, wave premium is "
									+ "$" + endorsePolicyPage.premium.formatDynamicPath("waivedPremium", i).getData(),
							false, false);
				}
			}

			Assertions.addInfo("Scenario 04", "Scenario 04 ended");
			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC041 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC041 ", "Executed Successfully");
			}
		}
	}
}
