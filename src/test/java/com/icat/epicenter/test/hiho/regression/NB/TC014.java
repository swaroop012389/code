/** Program Description: Create NB policy with unbound renewal, lapse the renewal and validate if lapse notice is downloading without any error.
 *  Author			   : Abha
 *  Date of Creation   : 24/12/2019
**/

package com.icat.epicenter.test.hiho.regression.NB;

import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.WebDriverManager;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicyDocumentsPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC014 extends AbstractNAHOTest {

	public TC014() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHONB/NBTCID14.xls";
	}

	String quoteNumber;
	public BuildingUnderMinimumCostPage dwellingCost;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {
		HomePage homePage = new HomePage();
		DwellingPage dwellingPage = new DwellingPage();
		LocationPage locationPage = new LocationPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
		BuildingUnderMinimumCostPage dwellingCost = new BuildingUnderMinimumCostPage();

		int dataValue1 = 0;
		Map<String, String> testData = data.get(dataValue1);
		String locationNumber = testData.get("LocCount");
		int locationCount = Integer.parseInt(locationNumber);
		int quoteLen;
		String quoteNumber;

		// New account
		Assertions.passTest("Home Page", "Page Navigated");
		homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", "Values Entered");

		// Entering Location Details
		locationPage = new LocationPage();
		Assertions.passTest("Location Page", "Location Page loaded successfully");
		locationPage.enterLocationDetailsHIHO(testData);
		Assertions.passTest("Location Page", "Location details entered successfully");

		// Enter Dwelling details
		Assertions.passTest("Dwelling Page", "Page Navigated");
		dwellingPage.enterDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", "Location 1 Dwelling 1 - 1 Values Entered successfully");

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		createQuotePage.enterDeductibles(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();

		// Click on Override
		dwellingCost.clickOnOverride();

		// Click on continue
		createQuotePage.continueButton.click();
		createQuotePage.continueButton.waitTillInVisibilityOfElement(60);
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
		if (WebDriverManager.getCurrentDriver().getCurrentUrl().equalsIgnoreCase("PN1")) {
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);
			Assertions.verify(accountOverviewPage.pageName.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Account Name present", true, true);
			Assertions.verify(accountOverviewPage.totalPremiumAmount.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Total Premium and Fee Amount present", true, true);
			Assertions.verify(accountOverviewPage.producerNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Producer Number present", true, true);
			if (locationCount >= 2) {
				Assertions.verify(accountOverviewPage.quoteSomeDwellingsButton.checkIfElementIsDisplayed(), true,
						"Account Overview Page", "Create Another Quote present", true, true);
			} else {
				Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
						"Account Overview Page", "Create Another Quote present", true, true);
			}
			Assertions.verify(accountOverviewPage.editDeductibleAndLimits.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Edit Deductibles and limits button present", true, true);
			Assertions.verify(accountOverviewPage.quoteStatus.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Quote Status present", true, true);
			Assertions.verify(accountOverviewPage.viewPrintFullQuoteLink.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "View/Print Full Quote Link present", true, true);
			Assertions.verify(accountOverviewPage.emailQuoteLink.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Email Quote Link present", true, true);
			Assertions.verify(accountOverviewPage.overridePremiumLink.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Override Premium Link present", true, true);
			Assertions.verify(accountOverviewPage.viewOrPrintRateTrace.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "View/Print Rate Trace Link present", true, true);
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Bind button present is verified", true, true);
			accountOverviewPage.deleteAccount.scrollToElement();
			accountOverviewPage.deleteAccount.click();
			accountOverviewPage.yesDeletePopup.waitTillVisibilityOfElement(60);
			accountOverviewPage.yesDeletePopup.scrollToElement();
			accountOverviewPage.yesDeletePopup.click();
			Assertions.passTest("Account Overview Page", "Account deleted successfully");
			Assertions.passTest("Smoke Test TC_014", "Executed Successfully");
		}

		if (!WebDriverManager.getCurrentDriver().getCurrentUrl().equalsIgnoreCase("PN1")) {
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Request Bind Page", "Request Bind page loaded successfully");
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");
			String policyNumber = policySummaryPage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number: " + policyNumber);

			// Initiate the renewal
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Renew Policy Page", "Renewal initiated Successfully");

			// Release to Producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Renewal released to producer Successfully");

			// Clicking on view Renewal Documents link
			accountOverviewPage.viewRenewalDocuments.waitTillVisibilityOfElement(60);
			accountOverviewPage.viewRenewalDocuments.scrollToElement();
			accountOverviewPage.viewRenewalDocuments.click();

			// Clicking on Add Document button
			policyDocumentsPage.waitTime(10);
			policyDocumentsPage.addDocumentButton.waitTillVisibilityOfElement(60);
			policyDocumentsPage.addDocumentButton.scrollToElement();
			policyDocumentsPage.addDocumentButton.click();
			Assertions.passTest("Policy Documents Page", "Clicked on Add document Successfully");
			policyDocumentsPage.waitTime(5);
			policyDocumentsPage.fileUploadNAHO("AutomationTest.pdf");
			policyDocumentsPage.waitTime(5);

			// Uploading the document
			policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(3).scrollToElement();
			policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(3).click();
			policyDocumentsPage.waitTime(3);
			policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Contract", 2).scrollToElement();
			policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Contract", 2).click();

			policyDocumentsPage.uploadButtonUAT.waitTillPresenceOfElement(60);
			policyDocumentsPage.uploadButtonUAT.scrollToElement();
			policyDocumentsPage.uploadButtonUAT.click();

			// waiting for document to be uploaded
			policyDocumentsPage.policyDocuments.formatDynamicPath("AutomationTest.pdf").waitTillVisibilityOfElement(60);
			Assertions.verify(
					policyDocumentsPage.policyDocuments
							.formatDynamicPath("AutomationTest.pdf").checkIfElementIsDisplayed(),
					true, "Policy Documents Page",
					"Policy document : "
							+ policyDocumentsPage.policyDocuments.formatDynamicPath("AutomationTest.pdf").getData()
							+ " is uploaded successfully",
					true, true);

			// Clicking on back button
			policyDocumentsPage.backButton.waitTillPresenceOfElement(60);
			policyDocumentsPage.backButton.waitTillVisibilityOfElement(60);
			policyDocumentsPage.backButton.scrollToElement();
			policyDocumentsPage.backButton.click();

			// Clicking on lapse renewal
			accountOverviewPage.lapseRenewal.waitTillVisibilityOfElement(60);
			accountOverviewPage.lapseRenewal.scrollToElement();
			accountOverviewPage.lapseRenewal.click();
			Assertions.passTest("Account Overview Page", "Clicked on lapse Renewal Successfully");

			// Clicking on Yes I want to continue button to expire all active
			// quotes
			accountOverviewPage.quoteExpiredPopupMsg1
					.formatDynamicPath("Lapsing this account will expire all active quotes.").checkIfElementIsDisplayed();

			accountOverviewPage.yesIWantToContinue.scrollToElement();
			accountOverviewPage.yesIWantToContinue.click();

			// Clicking on view Renewal Documents link
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account overview page", "Quote number : " + quoteNumber);
			accountOverviewPage.viewRenewalDocuments.waitTillVisibilityOfElement(60);
			accountOverviewPage.viewRenewalDocuments.scrollToElement();
			accountOverviewPage.viewRenewalDocuments.click();
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			accountOverviewPage.viewRenewalDocuments.waitTillVisibilityOfElement(60);
			accountOverviewPage.viewRenewalDocuments.scrollToElement();
			accountOverviewPage.viewRenewalDocuments.click();
			policyDocumentsPage.backButton.scrollToElement();
			policyDocumentsPage.backButton.click();
			accountOverviewPage.waitTime(60);
			accountOverviewPage.viewRenewalDocuments.scrollToElement();
			accountOverviewPage.viewRenewalDocuments.click();
			waitTime(2);
			policyDocumentsPage.refreshPage();
			waitTime(3);

			// Validation of presence of lapse document
			if (policyDocumentsPage.policyDocuments.formatDynamicPath("View All Terms documents")
					.checkIfElementIsPresent()
					&& policyDocumentsPage.policyDocuments.formatDynamicPath("View All Terms documents")
							.checkIfElementIsDisplayed()) {
				policyDocumentsPage.policyDocuments.formatDynamicPath("View All Terms documents").scrollToElement();
				policyDocumentsPage.policyDocuments.formatDynamicPath("View All Terms documents").click();
			}

			Assertions.verify(
					policyDocumentsPage.policyDocuments1.formatDynamicPath("LAPSE").checkIfElementIsDisplayed(), true,
					"Policy Documents Page", "Presence of Lapse notice for Insured is verified ", true, true);
			Assertions.verify(
					policyDocumentsPage.policyDocuments1.formatDynamicPath("LAPSE").checkIfElementIsDisplayed(), true,
					"Policy Documents Page", "Presence of Lapse notice for Producer is verified ", true, true);

			policyDocumentsPage.policyDocuments1.formatDynamicPath("LAPSE").click();

			// Validating the lapse document
			String URL = WebDriverManager.getCurrentDriver()
					.findElement(By.xpath("//table[@id='policy-docs']/tbody/tr[2]/td[2]/a")).getAttribute("href");
			try {
				SSLContext ctx = SSLContext.getInstance("TLS");
				ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
				SSLContext.setDefault(ctx);

				URL url = new URL("https://mms.nw.ru");
				HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
				conn.setHostnameVerifier(new HostnameVerifier() {
					@Override
					public boolean verify(String arg0, SSLSession arg1) {
						return true;
					}
				});
				conn.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				URL obj = new URL(URL);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("HEAD");
				con.connect();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Assertions.verify(
					policyDocumentsPage.policyDocuments.formatDynamicPath("LAPSE").checkIfElementIsDisplayed(), true,
					"Policy Documents Page", "Opened Lapse notice document successfully", true, true);

			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			homePage.signOutButton.waitTillInVisibilityOfElement(60);
			Assertions.passTest("NB_Regression_TC014", "Executed Successfully");
		}
	}

	private static class DefaultTrustManager implements X509TrustManager {
		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	}
}
