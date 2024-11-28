package com.icat.epicenter.pom;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;

import com.NetServAutomationFramework.config.EnvironmentDetails;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.CheckBoxControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class PolicyDocumentsPage extends BasePageControl {

	public BaseWebElementControl dueDiligenceRequired;
	public BaseWebElementControl subscriptionAgreementRequired;
	public ButtonControl backButton;
	public ButtonControl addDocumentButton;
	public ButtonControl continueBindButton;

	public ButtonControl documentCategoryArrow;
	public ButtonControl documentCategoryOptions;
	public ButtonControl chooseDocumentButton;
	public ButtonControl uploadButton;
	public ButtonControl cancelButton;
	public HyperLink policyDocuments;
	public HyperLink policyDocuments1;

	public TextFieldControl chooseDocument;
	public TextFieldControl chooseFile;
	public BaseWebElementControl loading;
	public BaseWebElementControl documentCategoryData;
	public BaseWebElementControl documentCategoryDataNAHO;

	public ButtonControl documentCategoryArrowUAT;
	public ButtonControl documentCategoryOptionsUAT;
	public ButtonControl uploadButtonUAT;
	public ButtonControl cancelButtonUAT;
	public ButtonControl categoryOptions;

	public BaseWebElementControl pageName;
	public BaseWebElementControl documentCategory;
	public ButtonControl deleteIcon;
	public ButtonControl deleteButton;
	public ButtonControl restoreOption;
	public ButtonControl restoreButton;
	public CheckBoxControl externalCheckbox;
	public BaseWebElementControl categoryList;
	public BaseWebElementControl fileExtensionError;
	public BaseWebElementControl dueDiligenceFormMessage;
	public HyperLink nocDocLink;

	public PolicyDocumentsPage() {
		PageObject pageobject = new PageObject("PolicyDocuments");

		dueDiligenceRequired = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DueDiligenceRequired")));
		subscriptionAgreementRequired = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_SubscriptionAgreementRequired")));
		addDocumentButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_AddDocumentButton")));
		backButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_BackButton")));
		continueBindButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ContinueBindButton")));
		documentCategoryArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_DocumentCategoryArrow")));
		documentCategoryOptions = new ButtonControl(By.xpath(pageobject.getXpath("xp_DocumentCategoryOptions")));
		chooseDocumentButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ChooseDocumentButton")));
		uploadButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_UploadButton")));
		cancelButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_CancelButton")));
		policyDocuments = new HyperLink(By.xpath(pageobject.getXpath("xp_PolicyDocuments")));
		policyDocuments1 = new HyperLink(By.xpath(pageobject.getXpath("xp_PolicyDocuments1")));
		chooseDocument = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ChooseDocument")));
		chooseFile = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ChooseFile")));
		loading = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Loading")));
		documentCategoryData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DocumentCategoryData")));
		documentCategoryDataNAHO = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DocumentCategoryDataNAHO")));

		documentCategoryArrowUAT = new ButtonControl(By.xpath(pageobject.getXpath("xp_DocumentCategoryArrowUAT")));
		documentCategoryOptionsUAT = new ButtonControl(By.xpath(pageobject.getXpath("xp_DocumentCategoryOptionsUAT")));
		uploadButtonUAT = new ButtonControl(By.xpath(pageobject.getXpath("xp_UploadButtonUAT")));
		cancelButtonUAT = new ButtonControl(By.xpath(pageobject.getXpath("xp_CancelButtonUAT")));

		deleteIcon = new ButtonControl(By.xpath(pageobject.getXpath("xp_DeleteIcon")));
		deleteButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_DeleteButton")));
		restoreOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_RestoreOption")));
		restoreButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_RestoreButton")));
		externalCheckbox = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_ExternalCheckbox")));
		categoryList = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CategoryList")));
		documentCategory = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DocumentCategory")));
		categoryOptions = new ButtonControl(By.xpath(pageobject.getXpath("xp_CategoryOptions")));

		pageName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PageName")));
		fileExtensionError = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_FileExtensionError")));
		dueDiligenceFormMessage = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_DueDiligenceFormMessage")));
		nocDocLink = new HyperLink(By.xpath(pageobject.getXpath("xp_NocDocLink")));
	}

	// TODO - will need to update for new Account Docs functionality - and probably
	// add a due diligence option
	public void fileUpload(String fileName) {
		if (StringUtils.isBlank(fileName)) {
			Assertions.failTest("Upload File", "Filename is blank");
		}
		String uploadFileDir = EnvironmentDetails.getEnvironmentDetails().getString("test.file.uploadpath");
		waitTime(5);
		if (chooseDocument.checkIfElementIsPresent() && chooseDocument.checkIfElementIsDisplayed()) {
			chooseDocument.setData(new File(uploadFileDir + fileName).getAbsolutePath());
			waitTime(2);// Adding wait time to load the element
			System.out.println("Choose document");
		} else {
			chooseFile.setData(new File(uploadFileDir + fileName).getAbsolutePath());
			waitTime(2);// Adding wait time to load the element

		}
		if (documentCategoryArrow.checkIfElementIsPresent() && documentCategoryArrow.checkIfElementIsDisplayed()) {
			documentCategoryArrow.waitTillPresenceOfElement(10);
			documentCategoryArrow.scrollToElement();
			documentCategoryArrow.click();
			waitTime(2);// Adding wait time to load the element


			if (documentCategoryOptions.formatDynamicPath("Subscription Agreement").checkIfElementIsPresent() &&
					documentCategoryOptions.formatDynamicPath("Subscription Agreement").checkIfElementIsDisplayed()) {

				documentCategoryOptions.formatDynamicPath("Subscription Agreement").waitTillVisibilityOfElement(10);
				documentCategoryOptions.formatDynamicPath("Subscription Agreement").scrollToElement();
				documentCategoryOptions.formatDynamicPath("Subscription Agreement").click();
			} else {
				documentCategoryOptions.formatDynamicPath("Signed Quote Application").waitTillVisibilityOfElement(10);
				documentCategoryOptions.formatDynamicPath("Signed Quote Application").scrollToElement();
				documentCategoryOptions.formatDynamicPath("Signed Quote Application").click();
			}

			uploadButton.click();
			waitTime(2);

		}

	}

	public void fileUploadNAHO(String fileName) {
		if (fileName.equals("") || fileName == null) {
			Assertions.failTest("Upload File", "Filname is blank");
		}
		String uploadFileDir = null;
		uploadFileDir = EnvironmentDetails.getEnvironmentDetails().getString("test.file.uploadpath");
		waitTime(8);// Adding wait time to load the element
		if (chooseDocument.checkIfElementIsPresent() && chooseDocument.checkIfElementIsDisplayed()) {

			chooseDocument.setData(new File(uploadFileDir + fileName).getAbsolutePath());
			waitTime(5);// Adding wait time to load the element
		} else {
			chooseFile.setData(new File(uploadFileDir + fileName).getAbsolutePath());
			waitTime(5);// Adding wait time to load the element
		}

		if (uploadButton.checkIfElementIsPresent() && uploadButton.checkIfElementIsDisplayed()) {
			uploadButton.waitTillVisibilityOfElement(60);
			uploadButton.scrollToElement();
			uploadButton.click();
			waitTime(8);// Adding wait time to load the element
		}
	}

	public void fileUpload(String fileName, String typeOfDoc) {
		if (StringUtils.isBlank(fileName)) {
			Assertions.failTest("Upload File", "Filename is blank");
		}
		String uploadFileDir = EnvironmentDetails.getEnvironmentDetails().getString("test.file.uploadpath");
		waitTime(5);
		if (chooseDocument.checkIfElementIsPresent() && chooseDocument.checkIfElementIsDisplayed()) {
			chooseDocument.setData(new File(uploadFileDir + fileName).getAbsolutePath());
			waitTime(2);// Adding wait time to load the element
			System.out.println("Choose document");
		} else {
			chooseFile.setData(new File(uploadFileDir + fileName).getAbsolutePath());
			waitTime(2);// Adding wait time to load the element

		}
		if (documentCategoryArrow.checkIfElementIsPresent() && documentCategoryArrow.checkIfElementIsDisplayed()) {
			documentCategoryArrow.waitTillPresenceOfElement(10);
			documentCategoryArrow.scrollToElement();
			documentCategoryArrow.click();
			waitTime(2);// Adding wait time to load the element


			if (typeOfDoc.equalsIgnoreCase("due diligence")) {
				documentCategoryOptions.formatDynamicPath("Due Diligence").waitTillVisibilityOfElement(10);
				documentCategoryOptions.formatDynamicPath("Due Diligence").scrollToElement();
				documentCategoryOptions.formatDynamicPath("Due Diligence").click();
			} else if (typeOfDoc.equalsIgnoreCase("subscription agreement")) {
				documentCategoryOptions.formatDynamicPath("Subscription Agreement").waitTillVisibilityOfElement(10);
				documentCategoryOptions.formatDynamicPath("Subscription Agreement").scrollToElement();
				documentCategoryOptions.formatDynamicPath("Subscription Agreement").click();
			} else if(typeOfDoc.equalsIgnoreCase("signed quote application")) {
				documentCategoryOptions.formatDynamicPath("Signed Quote Application").waitTillVisibilityOfElement(10);
				documentCategoryOptions.formatDynamicPath("Signed Quote Application").scrollToElement();
				documentCategoryOptions.formatDynamicPath("Signed Quote Application").click();
			} else if(typeOfDoc.equalsIgnoreCase("correspondence")) {
				documentCategoryOptions.formatDynamicPath("Correspondence").waitTillVisibilityOfElement(10);
				documentCategoryOptions.formatDynamicPath("Correspondence").scrollToElement();
				documentCategoryOptions.formatDynamicPath("Correspondence").click();
			} else if(typeOfDoc.equalsIgnoreCase("inspections")) {
				documentCategoryOptions.formatDynamicPath("Inspections").waitTillVisibilityOfElement(10);
				documentCategoryOptions.formatDynamicPath("Inspections").scrollToElement();
				documentCategoryOptions.formatDynamicPath("Inspections").click();
			} else if(typeOfDoc.equalsIgnoreCase("statement of no damage or damage")) {
				documentCategoryOptions.formatDynamicPath("Statement of No Damage or Damage").waitTillVisibilityOfElement(10);
				documentCategoryOptions.formatDynamicPath("Statement of No Damage or Damage").scrollToElement();
				documentCategoryOptions.formatDynamicPath("Statement of No Damage or Damage").click();
			}

			uploadButton.click();
			waitTime(2);

		}
	}
}
