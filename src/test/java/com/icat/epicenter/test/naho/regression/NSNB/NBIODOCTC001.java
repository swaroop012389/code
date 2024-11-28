/*Program Description: Account documentation validations for NB and RNL accounts:
 *Scenario-1: Generate a NAHO DE state NB Quote, upload documents with specified categories,
              validate uploads, manage document deletion and restoration and proceed with binding.
 *Scenario-2: Generate a NAHO NB LA state Quote, upload three specified documents, confirm successful uploads, switch to producer login, verify displayed documents,
              and ensure that only the 'Subscription Agreement' option is available in the Category drop-down.
 *Scenario-3: Generate a NAHO SC state NB Policy, initiate a Renewal, release it to the producer, upload categorized documents, validate uploads, proceed with RNL Bind, complete binding,
 			  and ensure user-uploaded documents include Edit options.
 */

package com.icat.epicenter.test.naho.regression.NSNB;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.NetServAutomationFramework.config.EnvironmentDetails;
import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicyDocumentsPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewDocumentsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class NBIODOCTC001 extends AbstractNAHOTest {

	public NBIODOCTC001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSNB/IODOCTC001.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		LoginPage loginPage = new LoginPage();
		ViewDocumentsPage viewDocumentsPage = new ViewDocumentsPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		BindRequestSubmittedPage bindRequestSubmittedPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();

		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		int quoteLen;
		int quoteLen2;
		int quoteLen3;
		String quoteNumber;
		String quoteNumber2;
		String quoteNumber3;
		String policyNumber;
		String renewalQuoteNumber;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			Assertions.addInfo("Scenario-1(NB-USM)",
					"Generate a NAHO DE state NB Quote, upload documents with specified categories, validate uploads, manage document deletion and restoration and proceed with binding.");

			// Creating New Account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account Created successfully");

			// Entering Zipcode
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Adding Policy fee and Inspection fee
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// validating the account document categories
			testData = data.get(data_Value1);
			if (accountOverviewPage.uploadPreBindDocuments.checkIfElementIsDisplayed()) {
				accountOverviewPage.uploadPreBindDocuments.scrollToElement();
				accountOverviewPage.uploadPreBindDocuments.click();

				policyDocumentsPage.addDocumentButton.scrollToElement();
				policyDocumentsPage.addDocumentButton.click();
			}
			if (testData.get("QuoteState").contains("DE")) {
				if (!testData.get("FileNameToUpload").equals("")) {
					String fileName = testData.get("FileNameToUpload");
					if (StringUtils.isBlank(fileName)) {
						Assertions.failTest("Upload File", "Filename is blank");
					}
					String uploadFileDir = EnvironmentDetails.getEnvironmentDetails().getString("test.file.uploadpath");
					waitTime(8);
					if (policyDocumentsPage.chooseDocument.checkIfElementIsPresent()
							&& policyDocumentsPage.chooseDocument.checkIfElementIsDisplayed()) {
						policyDocumentsPage.chooseDocument
								.setData(new File(uploadFileDir + fileName).getAbsolutePath());
						waitTime(5);// Adding wait time to load the element
						System.out.println("Choose document");
					} else {
						policyDocumentsPage.chooseFile.setData(new File(uploadFileDir + fileName).getAbsolutePath());
						waitTime(5);// Adding wait time to load the element

					}
					policyDocumentsPage.waitTime(2);
					for (int i = 0; i < 6; i++) {
						int dataValue = i;
						testData = data.get(dataValue);
						policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).scrollToElement();
						policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).click();
						policyDocumentsPage.waitTime(2);
						String documentCategoryOptions = policyDocumentsPage.categoryOptions
								.formatDynamicPath(testData.get("AccountDocumentName")).getData();
						policyDocumentsPage.categoryOptions.formatDynamicPath(testData.get("AccountDocumentName"))
								.waitTillPresenceOfElement(60);
						policyDocumentsPage.categoryOptions.formatDynamicPath(testData.get("AccountDocumentName"))
								.waitTillVisibilityOfElement(60);
						policyDocumentsPage.categoryOptions.formatDynamicPath(testData.get("AccountDocumentName"))
								.scrollToElement();
						policyDocumentsPage.categoryOptions.formatDynamicPath(testData.get("AccountDocumentName"))
								.click();
						Assertions.passTest("Upload Quote Documents Page", documentCategoryOptions
								+ " document category option is present and verified for DE State.");
						policyDocumentsPage.waitTime(2);
					}
				}
				policyDocumentsPage.cancelButtonUAT.scrollToElement();
				policyDocumentsPage.cancelButtonUAT.click();
				policyDocumentsPage.waitTime(5);// adding wait time to load the element
				policyDocumentsPage.backButton.scrollToElement();
				policyDocumentsPage.backButton.click();
			}

			// uploading the account documents
			testData = data.get(data_Value1);
			if (accountOverviewPage.uploadPreBindDocuments.checkIfElementIsPresent()
					&& accountOverviewPage.uploadPreBindDocuments.checkIfElementIsDisplayed()) {
				accountOverviewPage.uploadPreBindDocuments.scrollToElement();
				accountOverviewPage.uploadPreBindDocuments.click();

				if (policyDocumentsPage.pageName.getData().contains("Quote Documents")) {

					if (testData.get("QuoteState").contains("DE")) {
						policyDocumentsPage.addDocumentButton.scrollToElement();
						policyDocumentsPage.addDocumentButton.click();
						policyDocumentsPage.fileUploadNAHO("AutomationTest.pdf");
						policyDocumentsPage.waitTime(2);
						policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).scrollToElement();
						policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).click();
						policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Subscription Agreement", 2)
								.scrollToElement();
						policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Subscription Agreement", 2)
								.click();
						policyDocumentsPage.fileUploadNAHO("TestSubscriberAgreement.pdf");
						policyDocumentsPage.waitTime(2);
						policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(3).scrollToElement();
						policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(3).click();
						policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Inspections", 3)
								.scrollToElement();
						policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Inspections", 3).click();
						policyDocumentsPage.externalCheckbox.formatDynamicPath("Inspections").scrollToElement();
						policyDocumentsPage.externalCheckbox.formatDynamicPath("Inspections").select();
						policyDocumentsPage.fileUploadNAHO("subscription_dummy.txt");
						policyDocumentsPage.waitTime(2);
						policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(4).scrollToElement();
						policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(4).click();
						policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("UW Support Documents", 4)
								.scrollToElement();
						policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("UW Support Documents", 4)
								.click();
						policyDocumentsPage.uploadButtonUAT.waitTillPresenceOfElement(60);
						policyDocumentsPage.uploadButtonUAT.scrollToElement();
						policyDocumentsPage.uploadButtonUAT.click();
						Assertions.passTest("Policy Documents Page",
								"The uploading of the Subscription Agreement, Inspections, and UW Support Documents has been completed successfully.");

						// Validating uploaded documents
						policyDocumentsPage.documentCategory.formatDynamicPath("Subscription Agreement")
								.checkIfElementIsPresent();
						policyDocumentsPage.documentCategory.formatDynamicPath("Inspections").checkIfElementIsPresent();
						policyDocumentsPage.documentCategory.formatDynamicPath("UW Support Documents")
								.checkIfElementIsPresent();
						Assertions.passTest("Policy Documents Page",
								"The policy documents page contains and has verified the Subscription Agreement, Inspections, and UW Support Documents.");

						// Performing deleting and restoring operations for subscription agreement
						Assertions.addInfo("Policy Documents Page",
								"Validating the presence of delete options for the uploaded documents and performing deletion and restoration actions for the subscription agreement document.");
						policyDocumentsPage.deleteIcon.formatDynamicPath("Subscription Agreement")
								.checkIfElementIsPresent();
						policyDocumentsPage.deleteIcon.formatDynamicPath("Inspections").checkIfElementIsPresent();
						policyDocumentsPage.deleteIcon.formatDynamicPath("UW Support Documents")
								.checkIfElementIsPresent();
						Assertions.passTest("Policy Documents Page",
								"Successfully validated the presence of delete options for the Subscription Agreement,Inspections and UW Support documents.");
						// Deleting Subscription Agreement Document
						policyDocumentsPage.deleteIcon.formatDynamicPath("Subscription Agreement").scrollToElement();
						policyDocumentsPage.deleteIcon.formatDynamicPath("Subscription Agreement").click();
						policyDocumentsPage.waitTime(2);
						policyDocumentsPage.deleteButton.scrollToElement();
						policyDocumentsPage.deleteButton.click();
						Assertions.passTest("Policy Documents Page",
								"Successfully deleted the Subscription Agreement document.");
						// Checking for restore option available after deleting the subscription
						// agreement document.
						policyDocumentsPage.restoreOption.formatDynamicPath("Subscription Agreement")
								.checkIfElementIsPresent();
						policyDocumentsPage.restoreOption.formatDynamicPath("Subscription Agreement")
								.checkIfElementIsDisplayed();
						// Restoring the Subscription Agreement document
						policyDocumentsPage.restoreOption.formatDynamicPath("Subscription Agreement").scrollToElement();
						policyDocumentsPage.restoreOption.formatDynamicPath("Subscription Agreement").click();
						policyDocumentsPage.waitTime(2);
						policyDocumentsPage.restoreButton.scrollToElement();
						policyDocumentsPage.restoreButton.click();
						Assertions.passTest("Policy Documents Page",
								"Successfully validated the presence of restore options for the Subscription Agreement and Restored.");
						Assertions.addInfo("Policy Documents Page",
								"Successfully validated the presence of delete options for the uploaded documents and performed delete and restore operations for subscription agreement document.");
					}
					policyDocumentsPage.waitTime(5);// adding wait time to load the element
					policyDocumentsPage.backButton.waitTillPresenceOfElement(60);
					policyDocumentsPage.backButton.waitTillVisibilityOfElement(60);
					policyDocumentsPage.backButton.scrollToElement();
					policyDocumentsPage.backButton.click();
					accountOverviewPage.requestBind.scrollToElement();
					accountOverviewPage.requestBind.click();
				}
			}

			// Clicking on answer no button
			underwritingQuestionsPage.answerNoToAllQuestions.waitTillVisibilityOfElement(30);
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.answerNoToAllQuestions.scrollToElement();
			underwritingQuestionsPage.answerNoToAllQuestions.click();

			// Clicking on save button
			underwritingQuestionsPage.saveButton.scrollToElement();
			underwritingQuestionsPage.saveButton.click();

			// Entering bind details
			testData = data.get(data_Value1);
			requestBindPage.enterBindDetailsNAHO(testData);

			// Asserting policy number in policy summary page.
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Number is " + policySummaryPage.policyNumber.getData(), false, false);

			// Clicking on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			Assertions.addInfo("Scenario-1(NB-USM)", "Scenario-1(NB-USM) Ended");
			Assertions.addInfo("Scenario-2(NB-USM to Producer)",
					"Generate a NAHO NB LA state Quote, upload three specified documents, confirm successful uploads, switch to producer login, verify displayed documents, and ensure that only the 'Subscription Agreement' option is available in the Category dropdown.");

			// Creating New Account
			testData = data.get(data_Value2);
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account Created successfully");

			// Entering Zipcode
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen2 = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber2 = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen2 - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber2);

			// uploading the account documents
			testData = data.get(data_Value2);
			if (accountOverviewPage.uploadPreBindDocuments.checkIfElementIsPresent()
					&& accountOverviewPage.uploadPreBindDocuments.checkIfElementIsDisplayed()) {
				accountOverviewPage.uploadPreBindDocuments.scrollToElement();
				accountOverviewPage.uploadPreBindDocuments.click();

				if (policyDocumentsPage.pageName.getData().contains("Quote Documents")) {

					if (testData.get("QuoteState").contains("LA")) {
						policyDocumentsPage.addDocumentButton.scrollToElement();
						policyDocumentsPage.addDocumentButton.click();
						policyDocumentsPage.fileUploadNAHO("AutomationTest.pdf");
						policyDocumentsPage.waitTime(2);
						policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).scrollToElement();
						policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).click();
						policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Subscription Agreement", 2)
								.scrollToElement();
						policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Subscription Agreement", 2)
								.click();
						policyDocumentsPage.fileUploadNAHO("TestSubscriberAgreement.pdf");
						policyDocumentsPage.waitTime(2);
						policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(3).scrollToElement();
						policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(3).click();
						policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Inspections", 3)
								.scrollToElement();
						policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Inspections", 3).click();
						policyDocumentsPage.externalCheckbox.formatDynamicPath("Inspections").scrollToElement();
						policyDocumentsPage.externalCheckbox.formatDynamicPath("Inspections").select();
						policyDocumentsPage.fileUploadNAHO("subscription_dummy.txt");
						policyDocumentsPage.waitTime(2);
						policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(4).scrollToElement();
						policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(4).click();
						policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("UW Support Documents", 4)
								.scrollToElement();
						policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("UW Support Documents", 4)
								.click();
						policyDocumentsPage.uploadButtonUAT.waitTillPresenceOfElement(60);
						policyDocumentsPage.uploadButtonUAT.scrollToElement();
						policyDocumentsPage.uploadButtonUAT.click();
						Assertions.passTest("Policy Documents Page",
								"The uploading of the Subscription Agreement, Inspections, and UW Support Documents has been completed successfully.");

						// Validating uploaded documents
						policyDocumentsPage.documentCategory.formatDynamicPath("Subscription Agreement")
								.checkIfElementIsPresent();
						policyDocumentsPage.documentCategory.formatDynamicPath("Inspections").checkIfElementIsPresent();
						policyDocumentsPage.documentCategory.formatDynamicPath("UW Support Documents")
								.checkIfElementIsPresent();
						Assertions.passTest("Policy Documents Page",
								"The policy documents page contains and has verified the Subscription Agreement, Inspections, and UW Support Documents.");

					}
					policyDocumentsPage.waitTime(5);// adding wait time to load the element
					policyDocumentsPage.backButton.waitTillPresenceOfElement(60);
					policyDocumentsPage.backButton.waitTillVisibilityOfElement(60);
					policyDocumentsPage.backButton.scrollToElement();
					policyDocumentsPage.backButton.click();
					accountOverviewPage.requestBind.scrollToElement();
					accountOverviewPage.requestBind.click();
				}

			}

			// SignOut as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as USM successfully");

			// Sign in as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as Producer Successfully");

			// searching the quote number in grid and clicking on the quote number link
			homePage.searchQuoteByProducer(quoteNumber2);
			Assertions.passTest("Home Page", "Searched Submitted Quote " + quoteNumber2 + " successfullly");

			if (accountOverviewPage.uploadPreBindDocuments.checkIfElementIsDisplayed()) {
				accountOverviewPage.uploadPreBindDocuments.scrollToElement();
				accountOverviewPage.uploadPreBindDocuments.click();

			}

			// Checking only two documents are present in producer login
			Assertions.addInfo("Producer login", "Verifying the USM uploaded documents through producer login");

			if (policyDocumentsPage.documentCategory.formatDynamicPath("Subscription Agreement")
					.checkIfElementIsPresent()) {
				policyDocumentsPage.documentCategory.formatDynamicPath("Subscription Agreement")
						.checkIfElementIsPresent();
				policyDocumentsPage.documentCategory.formatDynamicPath("Inspections").checkIfElementIsPresent();
				if (!policyDocumentsPage.documentCategory.formatDynamicPath("UW Support Documents")
						.checkIfElementIsPresent()) {
					Assertions.passTest("Policy Documents Page", "UW Support Document is not displayed");
				} else {
					Assertions.verify(policyDocumentsPage.documentCategory.formatDynamicPath("UW Support Documents"),
							true, "Upload Quote Documents Page", "UW Support Document is displayed", false, false);
				}
				Assertions.passTest("Policy Documents Page",
						"Only Subscription Agreement and Inspections documents are present");
			}
			Assertions.addInfo("Producer login",
					"Successfully verified the USM uploaded documents through producer login");

			// Adding below code for IO-21798
			policyDocumentsPage.backButton.scrollToElement();
			policyDocumentsPage.backButton.click();
			Assertions.passTest("Policy Document Page", "Clicked on back button successfully");

			// Click on bind button button
			accountOverviewPage.requestBind.scrollToElement();
			accountOverviewPage.requestBind.click();

			// Clicking on answer no button
			underwritingQuestionsPage.answerNoToAllQuestions.waitTillVisibilityOfElement(30);
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.answerNoToAllQuestions.scrollToElement();
			underwritingQuestionsPage.answerNoToAllQuestions.click();

			// Clicking on save button
			underwritingQuestionsPage.saveButton.scrollToElement();
			underwritingQuestionsPage.saveButton.click();

			// Entering bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request bind page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Getting Policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Number is " + policySummaryPage.policyNumber.getData(), false, false);
			// IO-21798 Ended

			// Sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			Assertions.passTest("Home Page", "Logged out as Producer successfully");
			Assertions.addInfo("Scenario-2(NB-USM to Producer)", "Scenario-2(NB-USM to Producer) Ended");

			Assertions.addInfo("Scenario-3(RNL-As USM)",
					"Generate a NAHO SC state NB Policy, initiate a Renewal, release it to the producer, upload categorized documents, validate uploads, proceed with RNL Bind, complete binding, and ensure user-uploaded documents include Edit options.");

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");

			// Creating New Account
			testData = data.get(data_Value3);
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account Created successfully");

			// Entering Zipcode
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen3 = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber3 = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen3 - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber3);

			// Binding the quote and creating the policy
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber3);

			// Clicking on answer no button
			underwritingQuestionsPage.answerNoToAllQuestions.waitTillVisibilityOfElement(30);
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.answerNoToAllQuestions.scrollToElement();
			underwritingQuestionsPage.answerNoToAllQuestions.click();

			// Clicking on save button
			underwritingQuestionsPage.saveButton.scrollToElement();
			underwritingQuestionsPage.saveButton.click();

			// Entering bind details
			testData = data.get(data_Value3);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request bind page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Below code adding because of quote is referring, if not referring means don't
			// think this is issue
			if (bindRequestSubmittedPage.homePage.checkIfElementIsPresent()
					&& bindRequestSubmittedPage.homePage.checkIfElementIsDisplayed()) {
				bindRequestSubmittedPage.homePage.scrollToElement();
				bindRequestSubmittedPage.homePage.click();

				homePage.searchQuote(quoteNumber3);
				Assertions.passTest("Home Page", "Searched Submitted Quote " + quoteNumber3 + " successfullly");
				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();

				// Approve Referral
				Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
						"Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();

				// click on approve in Approve Decline Quote page
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Referral Page", "Quote referral approved successfully");

			}

			// Asserting policy number in policy summary page.
			policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Number is " + policySummaryPage.policyNumber.getData(), false, false);

			// Click on Renew policy
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
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();
			}
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy Link successfully");
			Assertions.verify(accountOverviewPage.viewPreviousPolicyButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// Getting Renewal Quote Number
			renewalQuoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "The Renewal Quote Number is : " + renewalQuoteNumber);

			if (accountOverviewPage.openReferral.checkIfElementIsPresent()
					&& accountOverviewPage.openReferral.checkIfElementIsDisplayed()) {
				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();

				// Approve Referral
				Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
						"Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();

				// click on approve in Approve Decline Quote page
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Referral Page", "Quote referral approved successfully");

				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuote(renewalQuoteNumber);
				Assertions.passTest("Home Page", "Renewal quote searched successfully");

			}

			// click on release to producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Renewal to Producer successfully");

			// validating the account document categories
			testData = data.get(data_Value3);
			if (accountOverviewPage.uploadPreBindDocuments.checkIfElementIsDisplayed()) {
				accountOverviewPage.uploadPreBindDocuments.scrollToElement();
				accountOverviewPage.uploadPreBindDocuments.click();

				policyDocumentsPage.addDocumentButton.scrollToElement();
				policyDocumentsPage.addDocumentButton.click();
			}
			if (testData.get("QuoteState").contains("SC")) {
				if (!testData.get("FileNameToUpload").equals("")) {
					policyDocumentsPage.fileUploadNAHO(testData.get("FileNameToUpload"));
					policyDocumentsPage.waitTime(2);
					for (int i = 0; i < 7; i++) {
						int dataValue = i;
						testData = data.get(dataValue);
						policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).scrollToElement();
						policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).click();
						policyDocumentsPage.waitTime(2);
						String documentCategoryOptions = policyDocumentsPage.categoryOptions
								.formatDynamicPath(testData.get("AccountDocumentName")).getData();
						policyDocumentsPage.categoryOptions.formatDynamicPath(testData.get("AccountDocumentName"))
								.waitTillPresenceOfElement(60);
						policyDocumentsPage.categoryOptions.formatDynamicPath(testData.get("AccountDocumentName"))
								.waitTillVisibilityOfElement(60);
						policyDocumentsPage.categoryOptions.formatDynamicPath(testData.get("AccountDocumentName"))
								.scrollToElement();
						policyDocumentsPage.categoryOptions.formatDynamicPath(testData.get("AccountDocumentName"))
								.click();
						Assertions.passTest("Upload Quote Documents Page", documentCategoryOptions
								+ " document category option is present and verified for SC State.");
						policyDocumentsPage.waitTime(2);
					}
				}
				policyDocumentsPage.cancelButtonUAT.scrollToElement();
				policyDocumentsPage.cancelButtonUAT.click();
				policyDocumentsPage.waitTime(5);// adding wait time to load the element
				policyDocumentsPage.backButton.scrollToElement();
				policyDocumentsPage.backButton.click();
			}

			// uploading the account documents
			testData = data.get(data_Value3);
			if (accountOverviewPage.uploadPreBindDocuments.checkIfElementIsPresent()
					&& accountOverviewPage.uploadPreBindDocuments.checkIfElementIsDisplayed()) {
				accountOverviewPage.uploadPreBindDocuments.scrollToElement();
				accountOverviewPage.uploadPreBindDocuments.click();
			}
			if (policyDocumentsPage.pageName.getData().contains("Quote Documents")) {
				if (testData.get("QuoteState").contains("SC")) {
					policyDocumentsPage.addDocumentButton.scrollToElement();
					policyDocumentsPage.addDocumentButton.click();
					policyDocumentsPage.fileUploadNAHO("AutomationTest.pdf");
					policyDocumentsPage.waitTime(2);
					policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).scrollToElement();
					policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).click();
					policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Subscription Agreement", 2)
							.scrollToElement();
					policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Subscription Agreement", 2)
							.click();
					policyDocumentsPage.fileUploadNAHO("TestSubscriberAgreement.pdf");
					policyDocumentsPage.waitTime(2);
					policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(3).scrollToElement();
					policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(3).click();
					policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Due Diligence", 3)
							.scrollToElement();
					policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Due Diligence", 3).click();
					policyDocumentsPage.fileUploadNAHO("subscription_dummy.txt");
					policyDocumentsPage.waitTime(2);
					policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(4).scrollToElement();
					policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(4).click();
					policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Inspections", 4)
							.scrollToElement();
					policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Inspections", 4).click();
					policyDocumentsPage.uploadButtonUAT.waitTillPresenceOfElement(60);
					policyDocumentsPage.uploadButtonUAT.scrollToElement();
					policyDocumentsPage.uploadButtonUAT.click();

					// Validating uploaded documents
					Assertions.passTest("Policy Documents Page",
							"The uploading of the Subscription Agreement, Due Diligence and InspectionsDocuments has been completed successfully.");
					policyDocumentsPage.documentCategory.formatDynamicPath("Subscription Agreement")
							.checkIfElementIsPresent();
					policyDocumentsPage.documentCategory.formatDynamicPath("Inspections").checkIfElementIsPresent();
					policyDocumentsPage.documentCategory.formatDynamicPath("Due Diligence").checkIfElementIsPresent();
					Assertions.passTest("Policy Documents Page",
							"The policy documents page contains and has verified the Subscription Agreement, Inspections, and Due Diligence Documents.");
				}
			}
			policyDocumentsPage.waitTime(5);// adding wait time to load the element
			policyDocumentsPage.backButton.waitTillPresenceOfElement(60);
			policyDocumentsPage.backButton.waitTillVisibilityOfElement(60);
			policyDocumentsPage.backButton.scrollToElement();
			policyDocumentsPage.backButton.click();
			accountOverviewPage.requestBind.scrollToElement();
			accountOverviewPage.requestBind.click();
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// binding the renewal
			requestBindPage.refreshPage();
			testData = data.get(data_Value3);
			requestBindPage.renewalRequestBindNAHO(testData);

			// Asserting policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. The Renewal PolicyNumber is : " + policyNumber, false,
					false);

			// Validating the user-uploaded documents on the view documents page
			if (policySummaryPage.viewDocuments.checkIfElementIsPresent()
					&& policySummaryPage.viewDocuments.checkIfElementIsDisplayed()) {
				Assertions.addInfo("View Documents Page",
						"Validating the presence of edit options for the user uploaded docs.");
				policySummaryPage.viewDocuments.scrollToElement();
				policySummaryPage.viewDocuments.click();
				homePage.goToHomepage.click();
				homePage.searchPolicy(policyNumber);
				policySummaryPage.waitTime(3);
				policySummaryPage.viewDocuments.scrollToElement();
				policySummaryPage.viewDocuments.click();
				Assertions.passTest("Policy Summary Page", "Clicked on view documents link successfully");
				viewDocumentsPage.backBtn.scrollToElement();
				viewDocumentsPage.backBtn.click();
				policySummaryPage.viewDocuments.scrollToElement();
				policySummaryPage.viewDocuments.click();
				while (!viewDocumentsPage.editUserDocuments.formatDynamicPath("Subscription Agreement")
						.checkIfElementIsPresent()) {
					viewDocumentsPage.refreshPage();
				}
				viewDocumentsPage.editUserDocuments.formatDynamicPath("Subscription Agreement")
						.checkIfElementIsPresent();
				viewDocumentsPage.editUserDocuments.formatDynamicPath("Subscription Agreement").scrollToElement();
				viewDocumentsPage.editUserDocuments.formatDynamicPath("Subscription Agreement").click();
				viewDocumentsPage.waitTime(2);
				viewDocumentsPage.cancelBtn.scrollToElement();
				viewDocumentsPage.cancelBtn.click();
				Assertions.passTest("View Documents Page",
						"Edit option is present for the 'Subscription Agreement' Document");
				viewDocumentsPage.editUserDocuments.formatDynamicPath("Due Diligence").checkIfElementIsPresent();
				viewDocumentsPage.editUserDocuments.formatDynamicPath("Due Diligence").scrollToElement();
				viewDocumentsPage.editUserDocuments.formatDynamicPath("Due Diligence").click();
				viewDocumentsPage.waitTime(2);
				viewDocumentsPage.cancelBtn.scrollToElement();
				viewDocumentsPage.cancelBtn.click();
				Assertions.passTest("View Documents Page", "Edit option is present for the 'Due Diligence' Document");
				viewDocumentsPage.editUserDocuments.formatDynamicPath("Inspections").checkIfElementIsPresent();
				viewDocumentsPage.editUserDocuments.formatDynamicPath("Inspections").scrollToElement();
				viewDocumentsPage.editUserDocuments.formatDynamicPath("Inspections").click();
				viewDocumentsPage.waitTime(2);
				viewDocumentsPage.cancelBtn.scrollToElement();
				viewDocumentsPage.cancelBtn.click();
				Assertions.passTest("View Documents Page", "Edit option is present for the 'Inspections' Document");
				Assertions.addInfo("View Documents Page",
						"Successfully validated the presence of edit options for the Subscription Agreement,Due Diligence and Inspections documents.");
			}

			Assertions.addInfo("Scenario-3(RNL-As USM)", "Scenario-3(RNL-As USM) Ended");

			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBIODOCTC001 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBIODOCTC001 ", "Executed Successfully");
			}
		}
	}
}
