/**
 * Description : Asserting NS and AOP default value and Check the Premium Overridden done successfully - NB Quote using CSV file
 * Author : Pavan Mule
 * Date   : 10/07/2024
 */
package com.icat.epicenter.test.naho.regression.ISNB;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.NetServAutomationFramework.config.EnvironmentDetails;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.WebDriverManager;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC073_NBGEN004 extends AbstractNAHOTest {

	public TC073_NBGEN004() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBGEN004.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		LoginPage loginPage = new LoginPage();
		ReferralPage referralPage = new ReferralPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();

		// Initializing Variables
		Map<String, String> testData;
		int data_Value1 = 0;
		int data_Value2 = 1;
		String quoteNo;
		testData = data.get(data_Value1);
		String premium;
		double d_premium;
		String icatFees;
		double d_icatFees;
		double calPremium;
		String currentPremium;
		String newPremium;
		String windPremium;
		String aopPremium;
		String liabilityPremium;
		double d_windPremium;
		double d_aopPremium;
		double d_liabilityPremium;
		double calNewPremium;
		String overrideFactor;
		double d_overrideFactor;
		String originalPremium;
		double d_originalPremium;
		String overridePremium;
		double d_overridePremium;
		String actualSLTF;
		double d_actualSLTF;
		String surplusContributionValue;
		double d_surplusContributionValue;
		String actualIcatFees;
		double d_actualIcatFees;
		double calOverrideFactor;
		double calSLTF;
		double calStamingFee;
		String sltfPercentage;
		double d_sltfPercentage;
		String stampingFee;
		double d_stampingFee;
		String vpfqPremium;
		double d_vpfqPremium;
		String vpfqPolicyFee;
		double d_vpfqPolicyFee;
		String vpfqInspectionFee;
		double d_vpfqInspectionFee;
		String vpfqSLTF;
		double d_vpfqSLTF;
		String vpfqStampingFee;
		double d_vpfqStampingFee;
		String vpfqSurplusContribution;
		double d_vpfqSurplusContribution;
		double d_vpfqCalSLTF;
		double d_vpfqCalStampingFee;
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
			priorLossesPage.selectPriorLossesInformation(testData);
			Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			// Entering Cov_A value to 150,000
			createQuotePage.coverageADwelling.waitTillVisibilityOfElement(60);
			createQuotePage.coverageADwelling.scrollToElement();
			WebElement wb = WebDriverManager.getCurrentDriver().findElement(By.xpath("//input[@id='CovA_AOP']"));
			wb.sendKeys(Keys.chord(Keys.CONTROL, "a"), testData.get("L1D1-DwellingCovA"));
			createQuotePage.coverageADwelling.tab();
			createQuotePage.loading.waitTillInVisibilityOfElement(60);
			Assertions.passTest("Create Quote Page",
					"The Coverage A Dwelling Value is : " + createQuotePage.coverageADwelling.getData());

			// Asserting AOP and NS default value
			String namedStromvalue = testData.get("NamedStormValue");
			Assertions.addInfo("Scenario 01", "Asserting AOP and NS default value");
			Assertions.verify(createQuotePage.namedStormData.getData(), namedStromvalue, "Create Quote Page",
					"The Named Storm Default Value " + createQuotePage.namedStormData.getData()
							+ " displayed is verified",
					false, false);
			String aopDedValue = testData.get("AOPDeductibleValue");
			Assertions.verify(createQuotePage.aopDeductibleData.getData(), aopDedValue, "Create Quote Page",
					"The AOP Deductible Value " + createQuotePage.aopDeductibleData.getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Assert NS deductible low value should not be able to select
			testData = data.get(data_Value2);
			createQuotePage.namedStormDeductibleArrow.waitTillVisibilityOfElement(60);
			createQuotePage.namedStormDeductibleArrow.scrollToElement();
			createQuotePage.namedStormDeductibleArrow.waitTillButtonIsClickable(60);
			createQuotePage.namedStormDeductibleArrow.click();
			Assertions.passTest("Create Quote Page", "Clicked on Named storm deductible arrow");
			Assertions.addInfo("Scenario 02", "Assert NS deductible low value should not be able to select");
			Assertions.verify(
					createQuotePage.namedStormDeductibleOption.formatDynamicPath(testData.get("NamedStormValue"))
							.checkIfElementIsPresent(),
					false, "Create Quote Page", "The Named Storm value " + testData.get("NamedStormValue")
							+ " is not present in the dropdown is verified",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			testData = data.get(data_Value1);
			createQuotePage.namedStormDeductibleOption.formatDynamicPath(testData.get("NamedStormValue"))
					.waitTillVisibilityOfElement(60);
			createQuotePage.namedStormDeductibleOption.formatDynamicPath(testData.get("NamedStormValue"))
					.scrollToElement();
			createQuotePage.namedStormDeductibleOption.formatDynamicPath(testData.get("NamedStormValue")).click();

			// Asserting AOP and AOWH value after changing cov A value 15000
			Assertions.addInfo("Scenario 03", "Asserting AOP and AOWH value after changing cov A value");
			testData = data.get(data_Value2);
			Assertions.verify(createQuotePage.aowhDeductibleData.getData().contains("2%"), true, "Create Quote Page",
					"Minimum AOWH Value is " + createQuotePage.aowhDeductibleData.getData() + " when Cov A $150,00",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");
			createQuotePage.aopDeductibleArrow.waitTillVisibilityOfElement(60);
			createQuotePage.aopDeductibleArrow.scrollToElement();
			createQuotePage.aopDeductibleArrow.click();
			createQuotePage.aopDeductibleOption.formatDynamicPath(testData.get("AOPDeductibleValue"))
					.waitTillVisibilityOfElement(60);
			createQuotePage.aopDeductibleOption.formatDynamicPath(testData.get("AOPDeductibleValue")).scrollToElement();
			createQuotePage.aopDeductibleOption.formatDynamicPath(testData.get("AOPDeductibleValue")).click();
			Assertions.passTest("Create Quote Page",
					"The AOP Deductible Latest Value : " + createQuotePage.aopDeductibleData.getData());

			// Assertions to verify AOWH value is Not updated as NS value
			Assertions.addInfo("Scenario 04", "Assertions to verify AOWH value is Not updated as NS value");
			Assertions.verify(createQuotePage.aowhDeductibleData.getData().contains("2%"), true, "Create Quote Page",
					"AOWH Value is " + createQuotePage.aowhDeductibleData.getData()
							+ ", not updated after updating AOP value",
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Click on get a quote button
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Entering Referral Details
			testData = data.get(data_Value1);
			referQuotePage.contactName.setData(testData.get("ProducerName"));
			referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
			referQuotePage.referQuote.click();

			// Getting quote no
			quoteNo = referQuotePage.quoteNumberforReferral.getData();
			Assertions.passTest("Refer Quote Page", "The Quote Number is " + quoteNo);

			// Create a BufferedWriter to write to the CSV file
			String BasePath = EnvironmentDetails.getEnvironmentDetails().getString("CSVFilePath");
			String filename = "TC073.csv";
			String fullPath = BasePath + filename;

			try {
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath))) {

					// Write the header
					writer.write("Quote Number,Wind Premium,AOP Premium,Liability Premium,Earthquake Premium");
					writer.newLine();

					// Write data rows
					writer.write(quoteNo + "," + testData.get("WindPremiumOverride") + ","
							+ testData.get("AOPPremiumOverride") + "," + testData.get("LiabilityPremiumOverride")
							+ ",");
				}

				Assertions.passTest("Override Premium and Fees Page", "CSV file created successfully");

			} catch (IOException e) {
				e.printStackTrace();
			}

			// Sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Login", "Logged in to application successfully");

			// Go to HomePage
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Search the quote
			homePage.searchQuote(quoteNo);
			Assertions.passTest("Home Page", "Searched Submitted Quote" + quoteNo + " successfullly");

			// getting premium and icat fees
			premium = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
			d_premium = Double.parseDouble(premium);
			icatFees = accountOverviewPage.icatFees.getData().replace("$", "").replace(",", "");
			d_icatFees = Double.parseDouble(icatFees);
			calPremium = d_premium + d_icatFees;

			// Click on open referral link
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();
			Assertions.passTest("Account Overview Page", "Clicked on open referral link");

			// Approve Referral
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(approveDeclineQuotePage.approveButton.checkIfElementIsDisplayed(), true,
					"Approve Decline Quote Page", "Approve Decline Quote page loaded successfully", false, false);
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Approve Decline Quote Page", "Bind Request approved successfully");
			Assertions.passTest("Approve Decline Quote Page", "Quote number : " + quoteNo + " approved successfully");

			// Click on go to home link
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			testData = data.get(data_Value1);
			Assertions.passTest("Home Page", "Clicked on home link");

			// Click on uploadOverride button
			homePage.uplaodOverride.scrollToElement();
			homePage.uplaodOverride.click();
			Assertions.passTest("Home Page", "Clicked on upload override button");

			// click on naho radio button
			overridePremiumAndFeesPage.submit.formatDynamicPath(1).waitTillPresenceOfElement(60);
			overridePremiumAndFeesPage.submit.formatDynamicPath(1).scrollToElement();
			Assertions.verify(overridePremiumAndFeesPage.submit.formatDynamicPath(1).checkIfElementIsDisplayed(), true,
					"Override Premium and Fees Page", "Override premium and fees page loaded successfully", false,
					false);
			overridePremiumAndFeesPage.NahoRadioButton.scrollToElement();
			overridePremiumAndFeesPage.NahoRadioButton.click();
			Assertions.passTest("Override Premium and Fees Page", "NAHO product selected successfully");

			// upload csv file
			overridePremiumAndFeesPage.csvFileUpload(testData.get("CSVFileUpload"));
			overridePremiumAndFeesPage.submit.formatDynamicPath(2).scrollToElement();
			overridePremiumAndFeesPage.submit.formatDynamicPath(2).click();
			overridePremiumAndFeesPage.continueButton.waitTillPresenceOfElement(60);
			overridePremiumAndFeesPage.continueButton.scrollToElement();
			Assertions.verify(overridePremiumAndFeesPage.continueButton.checkIfElementIsDisplayed(), true,
					"Override Premium and Fees Page", "CSV file uploaded successfully", false, false);

			// Getting current premium and new premium
			currentPremium = overridePremiumAndFeesPage.currentPremium.getData().replace("$", "").replace(",", "");
			newPremium = overridePremiumAndFeesPage.newPremium.getData().replace("$", "").replace(",", "");
			windPremium = testData.get("WindPremiumOverride");
			d_windPremium = Double.parseDouble(windPremium);
			aopPremium = testData.get("AOPPremiumOverride");
			liabilityPremium = testData.get("LiabilityPremiumOverride");
			d_aopPremium = Double.parseDouble(aopPremium);
			d_liabilityPremium = Double.parseDouble(liabilityPremium);
			calNewPremium = d_windPremium + d_aopPremium + d_liabilityPremium;

			// Verifying and Asserting Current premium and New Premium
			Assertions.addInfo("Scenario 05", "Verifying and Asserting Current premium and New Premium");
			Assertions.verify(Double.parseDouble(newPremium), calNewPremium, "Override Premium and Fees Page",
					"Calculated and Actual New premium both are same, actual new premium is " + newPremium
							+ " calculated new premium is " + calNewPremium,
					false, false);
			Assertions.verify(Double.parseDouble(currentPremium), calPremium, "Override Premium and Fees Page",
					"Calculated and Actual current premium both are same, actual current premium is " + currentPremium
							+ " calculated current premium is " + calPremium,
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// click continue button
			overridePremiumAndFeesPage.continueButton.scrollToElement();
			overridePremiumAndFeesPage.continueButton.click();
			Assertions.passTest("Override Premium and Fees Page", "Clicked on continue button");

			// Go to HomePage
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Search the quote
			homePage.searchQuote(quoteNo);
			Assertions.passTest("Home Page", "Searched Submitted Quote" + quoteNo + " successfullly");

			// Assert and verifying the confirmation message "This quote has a premium
			// adjustment. Please review to determine if the premium adjustment should be
			// reapplied."
			Assertions.addInfo("Scenario 06", "Asserting and verifying the premium adjustment confirmation message");
			Assertions.verify(
					accountOverviewPage.premiumWarningMessage.getData().contains("This quote has a premium adjustment"),
					true, "Account Overview Page", "Premium adjustment message is "
							+ accountOverviewPage.premiumWarningMessage.getData() + " verified",
					false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Getting values after uploading CSV file
			overrideFactor = accountOverviewPage.overrideFactor.getData().replace("$", "").replace(",", "");
			originalPremium = accountOverviewPage.originalPremiumData.getData().replace("$", "").replace(",", "");
			overridePremium = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
			actualSLTF = accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", "");
			surplusContributionValue = accountOverviewPage.surplusContibutionValue.getData().replace("$", "")
					.replace(",", "");
			actualIcatFees = accountOverviewPage.icatFees.getData().replace("$", "").replace(",", "");
			sltfPercentage = testData.get("SurplusLinesTaxesPercentage");
			stampingFee = testData.get("StampingFeePercentage");

			// Converting string to double
			d_overrideFactor = Double.parseDouble(overrideFactor);
			d_originalPremium = Double.parseDouble(originalPremium);
			d_overridePremium = Double.parseDouble(overridePremium);
			d_actualSLTF = Double.parseDouble(actualSLTF);
			d_surplusContributionValue = Double.parseDouble(surplusContributionValue);
			d_actualIcatFees = Double.parseDouble(actualIcatFees);
			d_sltfPercentage = Double.parseDouble(sltfPercentage);
			d_stampingFee = Double.parseDouble(stampingFee);

			// Calculating OverrideFactor and SLTF
			calOverrideFactor = d_overridePremium / d_originalPremium;
			calStamingFee = (d_overridePremium + d_surplusContributionValue + d_actualIcatFees) * d_stampingFee;
			calSLTF = ((d_overridePremium + d_surplusContributionValue + d_actualIcatFees) * d_sltfPercentage)
					+ calStamingFee;

			// Verifying and asserting override factor and SLTF after overriding premium
			Assertions.addInfo("Scenario 07",
					"Verifying and asserting override factor,SLTF,original premium and override premium after overriding premium value");
			if (Precision.round(Math.abs(Precision.round(d_overrideFactor, 2) - Precision.round(calOverrideFactor, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated Override factor : " + "$" + Math.abs(Precision.round(calOverrideFactor, 2)));
				Assertions.passTest("Account Overview Page", "Actual Override factor : " + "$" + d_overrideFactor);
			} else {
				Assertions.verify(d_overrideFactor, calOverrideFactor, "Account Overview Page",
						"The Difference between actual and calculated Override factor is more than 0.05", false, false);
			}
			if (Precision.round(Math.abs(Precision.round(d_actualSLTF, 2) - Precision.round(calSLTF, 2)), 2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated Surplus Lines Taxes and Fees : " + "$" + Precision.round(calSLTF, 2));
				Assertions.passTest("Account Overview Page",
						"Actual Surplus Lines Taxes and Fees : " + "$" + d_actualSLTF);
			} else {
				Assertions.verify(d_actualSLTF, calSLTF, "Account Overview Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05", false, false);
			}
			if (Precision.round(Math.abs(Precision.round(d_originalPremium, 2) - Precision.round(d_premium, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Original premium and Previous premium both are same, previous premium: " + "$"
								+ Precision.round(d_premium, 2));
				Assertions.passTest("Account Overview Page", "Original Premium : " + "$" + d_originalPremium);
			} else {
				Assertions.verify(d_originalPremium, d_premium, "Account Overview Page",
						"The Difference between original premium  and previous premium is more than 0.05", false,
						false);
			}

			if (Precision.round(
					Math.abs(
							Precision.round(d_overridePremium, 2) - Precision.round(Double.parseDouble(newPremium), 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Premium and Override premium both are same, override premium is : " + "$"
								+ Precision.round(Double.parseDouble(newPremium), 2));
				Assertions.passTest("Account Overview Page", "Actual premium is : " + "$" + d_overridePremium);
			} else {
				Assertions.verify(d_overridePremium, Double.parseDouble(newPremium), "Account Overview Page",
						"The Difference between actual and override premium is more than 0.05", false, false);
			}
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Click o view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on view or print full quote link");

			// Getting premium,icatfees,sltf and surplus contribution
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View Print Full Quote Page", "View print full quote page loaded successfully", false, false);
			vpfqPremium = viewOrPrintFullQuotePage.premiumValue.getData().replace(",", "").replace("$", "");
			vpfqPolicyFee = viewOrPrintFullQuotePage.insurerPolFeeVlaue.getData().replace(",", "").replace("$", "");
			vpfqInspectionFee = viewOrPrintFullQuotePage.insurerInspectionFeeValue.getData().replace(",", "")
					.replace("$", "");
			vpfqSLTF = viewOrPrintFullQuotePage.surplusLinesTaxNaho.getData().replace(",", "").replace("$", "");
			vpfqStampingFee = viewOrPrintFullQuotePage.stampingFeeNaho.getData().replace(",", "").replace("$", "");
			vpfqSurplusContribution = viewOrPrintFullQuotePage.surplusContributionValue.getData().replace(",", "")
					.replace("$", "");

			// Converting string to double
			d_vpfqPremium = Double.parseDouble(vpfqPremium);
			d_vpfqPolicyFee = Double.parseDouble(vpfqPolicyFee);
			d_vpfqInspectionFee = Double.parseDouble(vpfqInspectionFee);
			d_vpfqSLTF = Double.parseDouble(vpfqSLTF);
			d_vpfqStampingFee = Double.parseDouble(vpfqStampingFee);
			d_vpfqSurplusContribution = Double.parseDouble(vpfqSurplusContribution);

			// Calculating SLTF on view print full quote page
			d_vpfqCalStampingFee = (d_vpfqPremium + d_vpfqPolicyFee + d_vpfqInspectionFee + d_vpfqSurplusContribution)
					* d_stampingFee;
			d_vpfqCalSLTF = ((d_vpfqPremium + d_vpfqPolicyFee + d_vpfqInspectionFee + d_vpfqSurplusContribution)
					* d_sltfPercentage);

			// Verifying and asserting sltf and override premium after updating override
			// premium value on view print full quote
			Assertions.addInfo("Scenario 08",
					"Verifying and asserting sltf and override premium after updating override premium value on view print full quote");
			if (Precision.round(Math.abs(Precision.round(d_vpfqSLTF, 2) - Precision.round(d_vpfqCalSLTF, 2)),
					2) < 0.05) {
				Assertions.passTest("View Print Full Quote Page",
						"Calculated Surplus Lines Taxes and Fees : " + "$" + Precision.round(d_vpfqCalSLTF, 2));
				Assertions.passTest("View Print Full Quote Page",
						"Actual Surplus Lines Taxes and Fees : " + "$" + d_vpfqSLTF);
			} else {
				Assertions.verify(d_vpfqSLTF, d_vpfqCalSLTF, "View Print Full Quote Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05", false, false);
			}

			if (Precision.round(Math.abs(Precision.round(d_vpfqPremium, 2) - Precision.round(d_overridePremium, 2)),
					2) < 0.05) {
				Assertions.passTest("View Print Full Quote Page",
						"Actual override premium and expected override premium both are same,expected override premium is : "
								+ "$" + Precision.round(d_overridePremium, 2));
				Assertions.passTest("View Print Full Quote Page", "Actual override premium: " + "$" + d_vpfqPremium);
			} else {
				Assertions.verify(d_vpfqPremium, d_overridePremium, "View Print Full Quote Page",
						"The Difference between Actual override premium and expected override premium is more than 0.05",
						false, false);
			}

			if (Precision.round(
					Math.abs(Precision.round(d_vpfqStampingFee, 2) - Precision.round(d_vpfqCalStampingFee, 2)),
					2) < 0.05) {
				Assertions.passTest("View Print Full Quote Page",
						"Actual Stamping fee and expected Stamping fee both are same,expected Stamping fee is : " + "$"
								+ Precision.round(d_vpfqCalStampingFee, 2));
				Assertions.passTest("View Print Full Quote Page", "Actual Stamping fee : " + "$" + d_vpfqStampingFee);
			} else {
				Assertions.verify(d_vpfqStampingFee, d_vpfqCalStampingFee, "View Print Full Quote Page",
						"The Difference between Actual Stamping fee and expected Stamping fee is more than 0.05", false,
						false);
			}
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// Asserting premium overridden message;
			accountOverviewPage.noteBarMessage.waitTillPresenceOfElement(60);
			accountOverviewPage.noteBarMessage.waitTillVisibilityOfElement(60);
			accountOverviewPage.noteBarMessage.scrollToElement();
			Assertions.addInfo("Scenario 09", "Asserting premium overridden message on note bar");
			Assertions.verify(accountOverviewPage.noteBarMessage.getData().contains("Premium overridden as follows:"),
					true, "Account Overview Page",
					"The premium overridden message is " + accountOverviewPage.noteBarMessage.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			// Sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC073 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC073 ", "Executed Successfully");
			}
		}
	}
}
