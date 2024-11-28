/*Program Description: Verifying the Roll forward changes when the renewal quote is unreleased and Verifying the Roll forward changes when the renewal quote is released
 * Author            : Pavan Mule
 * Date of creation  : 14/02/2022
 */
package com.icat.epicenter.test.commercial.retailRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.ChangeNamedInsuredPage;
import com.icat.epicenter.pom.ChangePaymentPlanPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorseInspectionContactPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.EndorseProducerContact;
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

public class TC030 extends AbstractCommercialTest {

	public TC030() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID030.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ChangeNamedInsuredPage changeNamedInsuredPage = new ChangeNamedInsuredPage();
		ChangePaymentPlanPage changePaymentPlanPage = new ChangePaymentPlanPage();
		EndorseInspectionContactPage endorseInspectionContactPage = new EndorseInspectionContactPage();
		EndorseProducerContact endorseProducerContact = new EndorseProducerContact();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Prior Losses
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// click on request bind
			requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// entering bind details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote is searched successfully");

			// Click on Open Referral Link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			// Click on approve button
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Get policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Policy Number is " + policyNumber, false, false);

			// Click on Renewal link
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();

			// Go to Home Page
			if (policySummaryPage.expaacMessage.checkIfElementIsPresent()
					&& policySummaryPage.expaacMessage.checkIfElementIsDisplayed()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.click();
				expaccInfoPage.enterExpaccInfo(testData, policyNumber);
				Assertions.passTest("ExpaccInfo Page", "ExpaccInfo details entered successfully");

				// Go to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();

				// Performing Renewal Searches
				homePage.searchPolicy(policyNumber);

				// clicking on renewal policy link
				policySummaryPage.renewPolicy.waitTillPresenceOfElement(60);
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();
				Assertions.passTest("Policy Summary Page", "Clicked on renewal link successfully");
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
					"Account Overview Page", "Account overview page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Renewal quote number :  " + quoteNumber);

			// Click on view previous policy link
			accountOverviewPage.viewPreviousPolicyButton.scrollToElement();
			accountOverviewPage.viewPreviousPolicyButton.click();
			Assertions.passTest("Account overview page", "Clicked on view previous policy link");

			// Click on NPB endorse link
			policySummaryPage.endorseNPB.scrollToElement();
			policySummaryPage.endorseNPB.click();
			Assertions.passTest("Policy Pummary Page", "Clicked endorse NPB link");

			if (endorsePolicyPage.okButton.checkIfElementIsPresent()
					&& endorsePolicyPage.okButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.okButton.scrollToElement();
				endorsePolicyPage.okButton.click();
			}

			// Enter transaction effective date
			testData = data.get(data_Value2);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.appendData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered transaction effective date");

			// Click on change named insured link
			testData = data.get(data_Value2);
			endorsePolicyPage.changeNamedInsuredLink.scrollToElement();
			endorsePolicyPage.changeNamedInsuredLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on changed named insured link successfully");

			// changing the insured details
			changeNamedInsuredPage.enterInsuredAddressDetailPB(testData);

			// Verifying presence of roll forward button on NPB endorsement before release
			// renewal to producer
			Assertions.addInfo("Scenario 01", "Performing NPB endorsement before release renewal to producer");
			// Change inspection contact number
			endorsePolicyPage.changeInspectionContactLink.scrollToElement();
			endorsePolicyPage.changeInspectionContactLink.click();
			endorseInspectionContactPage.enterInspectionContactPB(testData);

			// Change payment plan
			endorsePolicyPage.changePaymentPlanLink.scrollToElement();
			endorsePolicyPage.changePaymentPlanLink.click();
			changePaymentPlanPage.renewalInsured10Pay.scrollToElement();
			changePaymentPlanPage.renewalInsured10Pay.click();

			// Click on ok button
			changePaymentPlanPage.okButton.scrollToElement();
			changePaymentPlanPage.okButton.click();

			// Click on producer contact and change producer detail
			endorsePolicyPage.producerContactLink.scrollToElement();
			endorsePolicyPage.producerContactLink.click();
			endorseProducerContact.enterEndorseProducerContactDetails(testData);
			Assertions.addInfo("Scenario 01", "Scenario 01 ended");

			// Click on complete and roll forward button
			endorsePolicyPage.completeButton.checkIfElementIsDisplayed();
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Complete and roll forward button");
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Click on view active renewal link
			policySummaryPage.viewActiveRenewal.scrollToElement();
			policySummaryPage.viewActiveRenewal.click();
			Assertions.passTest("Policy Summary Page", "Clicked on view active renewal link");

			// Verifying the following NPB changes performed on previous policy are not roll
			// forwarded to unreleased renewal quote
			testData = data.get(data_Value2);
			Assertions.addInfo("Scenario 02",
					"Verifying the following NPB changes performed on previous policy are not roll forwarded to unreleased renewal quote");
			Assertions.verify(accountOverviewPage.insuredAcctInfo.getData().contains(testData.get("InsuredName")),
					false, "Account Overview Page",
					"Verifying insured name is roll forwarded correctly , latest insured details is "
							+ accountOverviewPage.insuredAcctInfo.getData(),
					false, false);
			Assertions.verify(
					accountOverviewPage.prodInspectionContactInfo.getData()
							.contains(testData.get("InspectionAreaCode") + "-" + testData.get("InspectionPrefix") + "-"
									+ testData.get("InspectionNumber")),
					false, "Account Overview Page",
					"Verifying inspection phone number is roll forwarded correctly, latest inspection phone number is "
							+ accountOverviewPage.prodInspectionContactInfo.getData(),
					false, false);
			Assertions.verify(accountOverviewPage.paymentPlan.getData(), "Full Pay", "Account Overview Pagee",
					"Verifying payment plan is roll forwarded correctly, latest payment plan is "
							+ accountOverviewPage.paymentPlan.getData(),
					false, false);
			Assertions.verify(accountOverviewPage.producerNumber.getData().contains(testData.get("ProducerEmail")),
					false, "Account Overview Page",
					"Verifying producer contact is roll forwarded correctly, latest producer contact is "
							+ accountOverviewPage.producerNumber.getData(),
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 ended");

			// Click on view previous policy link
			accountOverviewPage.viewPreviousPolicyButton.scrollToElement();
			accountOverviewPage.viewPreviousPolicyButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on view previous policy link");

			// Click on PB Endorse link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked endorse PB link");

			// Asserting roll forward message "A renewal has already been created for this
			// policy. Please contact Underwriting to update the renewal to reflect these
			// changes."
			Assertions.addInfo("Scenario 03",
					"Asserting roll forward message when rolling forward from prior term to renewal");
			Assertions.verify(
					policyRenewalPage.coverageWarningMessage.formatDynamicPath("renewal has already been created")
							.checkIfElementIsPresent()
							&& policyRenewalPage.coverageWarningMessage
									.formatDynamicPath("renewal has already been created").checkIfElementIsDisplayed(),
					true, "Policy Renewal Page",
					"Roll forward message is " + policyRenewalPage.coverageWarningMessage
							.formatDynamicPath("renewal has already been created").getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			if (endorsePolicyPage.okButton.checkIfElementIsPresent()
					&& endorsePolicyPage.okButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.okButton.scrollToElement();
				endorsePolicyPage.okButton.click();
			}

			// Enter transaction effective date
			testData = data.get(data_Value2);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.appendData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered transaction effective date");

			// click on change coverage options
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on change coverage options link successfully");

			// changing named storm value
			testData = data.get(data_Value1);
			Assertions.passTest("Create Quote Page", "Named storm original value " + testData.get("DeductibleType"));
			testData = data.get(data_Value2);
			createQuotePage.namedStormDeductibleArrow.scrollToElement();
			createQuotePage.namedStormDeductibleArrow.click();
			createQuotePage.namedStormDeductibleOption.formatDynamicPath(testData.get("DeductibleValue"))
					.scrollToElement();
			createQuotePage.namedStormDeductibleOption.formatDynamicPath(testData.get("DeductibleValue")).click();
			Assertions.passTest("Create Quote Page", "Named storm latest value " + testData.get("DeductibleType"));

			// Click on continue endorsement
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			Assertions.passTest("Create Quote Page", "Clicked on continue endorsement button");

			// click on next
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// Click on continue
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}
			Assertions.passTest("Endorse Policy Page", "Clicked on next button successfully");

			// Asserting roll forward message "A renewal has already been created for this
			// policy. Please contact Underwriting to update the renewal to reflect these
			// changes."
			// added in if condition because of IO-21782
			if (endorsePolicyPage.globalWarning.formatDynamicPath("renewal has already been created")
					.checkIfElementIsPresent()) {
				Assertions.addInfo("Scenario 04",
						"Asserting roll forward message when rolling forward from prior term to renewal");
				Assertions.verify(endorsePolicyPage.globalWarning.formatDynamicPath("renewal has already been created")
						.checkIfElementIsPresent()
						&& endorsePolicyPage.globalWarning
								.formatDynamicPath("renewal has already been created").checkIfElementIsDisplayed(),
						true, "Policy Renewal Page",
						"Roll forward message is " + endorsePolicyPage.globalWarning
								.formatDynamicPath("renewal has already been created").getData() + " displayed",
						false, false);

				Assertions.passTest("Scenario 04", "Scenario 04 Ended");
			}

			// Clicked on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on complete button successfully");

			// Click on view active renewal
			policySummaryPage.viewActiveRenewal.scrollToElement();
			policySummaryPage.viewActiveRenewal.click();
			Assertions.passTest("Policy Summary Page", "Clicked on view active renewal link");
			testData = data.get(data_Value1);
			Assertions.passTest("Create quote page",
					"Original named storm value is " + testData.get("DeductibleValue"));
			testData = data.get(data_Value2);

			// Verifying the following PB changes performed on previous policy are roll
			// forwarded to un release renewal quote
			Assertions.addInfo("Scenario 06",
					"Verifying the following PB changes performed on previous policy are roll forwarded to unrelease renewal quote");
			Assertions.verify(
					accountOverviewPage.altQuoteOptEarthquakeDed
							.formatDynamicPath(2).getData().contains(testData.get("DeductibleValue")),
					false, "Account Overview Page",
					"Verifying named storm value is roll forwarded correctly, latest named storm value is "
							+ accountOverviewPage.altQuoteOptEarthquakeDed.formatDynamicPath(2).getData(),
					false, false);
			Assertions.passTest("Scenario 06", "Scenario 06 Ended");

			// click on release renewal to producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on release renewal to producer button");

			// Click on view previous policy link
			accountOverviewPage.viewPreviousPolicyButton.scrollToElement();
			accountOverviewPage.viewPreviousPolicyButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on view previous policy link");

			// Click on NPB endorse link
			policySummaryPage.endorseNPB.scrollToElement();
			policySummaryPage.endorseNPB.click();
			Assertions.passTest("Policy Summary Page", "Clicked endorse NPB link");

			// Click on ok button
			if (endorsePolicyPage.okButton.checkIfElementIsPresent()
					&& endorsePolicyPage.okButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.okButton.scrollToElement();
				endorsePolicyPage.okButton.click();
			}

			// Enter transaction effective date
			testData = data.get(data_Value2);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.appendData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered transaction effective date");

			// Click on change the Named Insured, Edit the Insured email/Phone number
			testData = data.get(data_Value1);
			endorsePolicyPage.changeNamedInsuredLink.scrollToElement();
			endorsePolicyPage.changeNamedInsuredLink.click();
			Assertions.passTest("Endorse policy page", "Clicked on changed named insured link successfully");
			changeNamedInsuredPage.enterInsuredAddressDetailPB(testData);

			// Verifying presence of roll forward button on NPB endorsement release renewal
			// to producer
			Assertions.addInfo("Scenario 07",
					"Verifying unpresence of roll forward button on NPB endorsement release renewal to producer");
			// Change inspection contact number
			endorsePolicyPage.changeInspectionContactLink.scrollToElement();
			endorsePolicyPage.changeInspectionContactLink.click();
			endorseInspectionContactPage.enterInspectionContactPB(testData);

			// Change payment plan
			endorsePolicyPage.changePaymentPlanLink.scrollToElement();
			endorsePolicyPage.changePaymentPlanLink.click();
			changePaymentPlanPage.insuredFullPay.scrollToElement();
			changePaymentPlanPage.insuredFullPay.click();

			// Click on ok button
			changePaymentPlanPage.okButton.scrollToElement();
			changePaymentPlanPage.okButton.click();

			// Click on producer contact
			endorsePolicyPage.producerContactLink.scrollToElement();
			endorsePolicyPage.producerContactLink.click();
			Assertions.passTest("Endorse policy page", "Clicked on producer contact link successfully");
			endorseProducerContact.enterEndorseProducerContactDetails(testData);
			Assertions.addInfo("Scenario 07", "Scenario 07 ended");

			// Click on complete and roll forward button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse policy page", "Clicked on Complete and roll forward button");
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Click on view active renewal link
			policySummaryPage.viewActiveRenewal.scrollToElement();
			policySummaryPage.viewActiveRenewal.click();
			Assertions.passTest("Policy summary page", "Clicked on view active renewal link");

			// Asserting insured name,email and phone number roll forwarded to renewal quote
			testData = data.get(data_Value1);

			// Verifying the following NPB changes performed on previous policy are roll
			// forwarded to released renewal quote
			Assertions.addInfo("Scenario 08",
					"Verifying the following NPB changes performed on previous policy are roll forwarded to released renewal quote");
			Assertions.verify(accountOverviewPage.insuredAcctInfo.getData().contains(testData.get("InsuredName")), true,
					"Account Overview Page",
					"Verifying insured name is roll forwarded correctly , latest insured details is "
							+ accountOverviewPage.insuredAcctInfo.getData(),
					false, false);
			Assertions.verify(
					accountOverviewPage.prodInspectionContactInfo.getData()
							.contains(testData.get("InspectionAreaCode") + "-" + testData.get("InspectionPrefix") + "-"
									+ testData.get("InspectionNumber")),
					true, "Account Overview Pagee",
					"Verifying inspection phone number is roll forwarded correctly, latest inspection phone number is "
							+ accountOverviewPage.prodInspectionContactInfo.getData(),
					false, false);
			Assertions.verify(accountOverviewPage.paymentPlan.getData(), "Full Pay", "Account Overview Page",
					"Verifying payment plan is roll forwarded correctly, latest payment plan is "
							+ accountOverviewPage.paymentPlan.getData(),
					false, false);
			Assertions.verify(accountOverviewPage.producerNumber.getData().contains(testData.get("ProducerEmail")),
					false, "Account Overview Page",
					"Verifying producer contact is roll forwarded correctly, latest producer contact is "
							+ accountOverviewPage.producerNumber.getData(),
					false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC030 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC030 ", "Executed Successfully");
			}
		}
	}
}
