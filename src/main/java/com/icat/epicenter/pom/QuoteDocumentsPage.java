package com.icat.epicenter.pom;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import com.NetServAutomationFramework.config.EnvironmentDetails;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextFieldControl;

/**
 * A class for reading global messages.
 *
 * @author kmurray
 */
public class QuoteDocumentsPage extends BasePageControl {
	public BaseWebElementControl requiredDocsInfo;
	public ButtonControl addDocumentButton;
	public ButtonControl documentCategoryArrow;
	public ButtonControl documentCategoryOptions;
	public ButtonControl uploadButton;
	public TextFieldControl documentToUpload;
	public ButtonControl backButton;
	public ButtonControl continueBindButton;
	public BaseWebElementControl uploadingDocumentLoadMask;

	public QuoteDocumentsPage() {
		PageObject pageobject = new PageObject("QuoteDocuments");
		requiredDocsInfo = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RequiredDocsInfo")));
		addDocumentButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_AddDocumentButton")));
		documentCategoryArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_DocumentCategoryArrow")));
		documentCategoryOptions = new ButtonControl(By.xpath(pageobject.getXpath("xp_DocumentCategoryOptions")));
		uploadButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_UploadButton")));
		documentToUpload = new TextFieldControl(By.xpath(pageobject.getXpath("xp_DocumentToUpload")));
		backButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_BackButton")));
		continueBindButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ContinueBindButton")));
		uploadingDocumentLoadMask = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Loading")));
	}

	/**
	 * Upload the subscription agreement if the system is asking for it. Adding due
	 * diligence too
	 */
	public void uploadQuoteDocs(String type) {
		try {
			// click add document
			addDocumentButton.scrollToElement();
			addDocumentButton.click();

			// upload doc
			uploadFile(EnvironmentDetails.getEnvironmentDetails().getString("test.file.uploadpath")
					+ EnvironmentDetails.getEnvironmentDetails().getString("test.file.subscriptionAgreement"));

			// click document category arrow & click "Subscription Agreement"
			documentCategoryArrow.waitTillButtonIsClickable(60);
			documentCategoryArrow.click();
			ButtonControl docCatOpts = documentCategoryOptions.formatDynamicPath(type);
			docCatOpts.waitTillVisibilityOfElement(60);
			docCatOpts.scrollToElement();
			docCatOpts.click();

			// click the upload button
			uploadButton.waitTillVisibilityOfElement(60);
			uploadButton.scrollToElement();
			uploadButton.click();

			// wait for doc to upload
			uploadingDocumentLoadMask.waitTillInVisibilityOfElement(60);

			Assertions.passTest("Quote Documents Page", type + " File Uploaded successfully");

		} catch (NoSuchElementException ex) {
			// element is not preset; ignore
			ex.printStackTrace();
		}
	}

	/**
	 * Upload the file at the given path to the chooseDocument control.
	 *
	 * @param pathName the path & filename
	 */
	public void uploadFile(String pathName) {
		if (StringUtils.isBlank(pathName)) {
			Assertions.failTest("Quote Documents Page/Upload File", "pathName is blank");
		}
		if (documentToUpload.checkIfElementIsPresent()) {
			BaseWebElementControl uploadSpan = new BaseWebElementControl(By.className("defaulFileInputLabel"));
			uploadSpan.waitTillVisibilityOfElement(60);
			documentToUpload.setData(new File(pathName).getAbsolutePath());
		} else {
			Assertions.failTest("Quote Documents Page", "Unable to upload file");
		}
	}

}
