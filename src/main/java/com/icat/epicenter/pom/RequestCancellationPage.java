package com.icat.epicenter.pom;

import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextAreaControl;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class RequestCancellationPage extends BasePageControl {

	public TextFieldControl insuredName;
	public TextFieldControl policyNumber;
	public TextFieldControl producerEmail;
	public TextFieldControl cancellationEffectiveDate;
	public ButtonControl cancellationReasonArrow;
	public ButtonControl cancellationReasonOption;
	public BaseWebElementControl cancellationReasonData;
	public TextFieldControl newEffectiveDate;
	public TextAreaControl comments;
	public ButtonControl addDocumentBtn;
	public ButtonControl cancelBtn;
	public ButtonControl submitBtn;
	public BaseWebElementControl cancellationRequestMsg;
	public HyperLink chooseFileLink;
	public BaseWebElementControl addDocumentVerbiage;
	PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();

	public RequestCancellationPage() {
		PageObject pageobject = new PageObject("RequestCancellation");
		insuredName = new TextFieldControl(By.xpath(pageobject.getXpath("xp_InsuredName")));
		policyNumber = new TextFieldControl(By.xpath(pageobject.getXpath("xp_PolicyNumber")));
		producerEmail = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ProducerEmail")));
		cancellationEffectiveDate = new TextFieldControl(By.xpath(pageobject.getXpath("xp_CancellationEffectiveDate")));
		cancellationReasonArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_CancellationReasonArrow")));
		cancellationReasonOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_CancellationReasonOption")));
		cancellationReasonData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CancellationReasonData")));
		newEffectiveDate = new TextFieldControl(By.xpath(pageobject.getXpath("xp_NewEffectiveDate")));
		comments = new TextAreaControl(By.xpath(pageobject.getXpath("xp_Comments")));
		addDocumentBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_AddDocumentBtn")));
		cancelBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_CancelBtn")));
		submitBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_SubmitBtn")));
		cancellationRequestMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CancellationRequestMsg")));
		chooseFileLink = new HyperLink(By.xpath(pageobject.getXpath("xp_ChooseFileLink")));
		addDocumentVerbiage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AddDocumentVerbiage")));
	}

	public void enterRequestCancellationDetails(Map<String, String> data) {
		Assertions.verify(insuredName.checkIfElementIsDisplayed(), true, "Request Cancellation Page",
				"Insured Name is " + insuredName.getData(), false, false);
		Assertions.verify(policyNumber.checkIfElementIsDisplayed(), true, "Request Cancellation Page",
				"Policy Number is " + policyNumber.getData(), false, false);
		if (producerEmail.checkIfElementIsPresent() && producerEmail.checkIfElementIsDisplayed()) {
			if (!data.get("ProducerEmail").equals("")) {
				producerEmail.scrollToElement();
				producerEmail.setData(data.get("ProducerEmail"));
				Assertions.passTest("Request Cancellation Page", "Producer Email is " + producerEmail.getData());
			}
		}
		if (cancellationEffectiveDate.checkIfElementIsPresent()
				&& cancellationEffectiveDate.checkIfElementIsDisplayed()) {
			if (!data.get("CancellationEffectiveDate").equals("")) {
				cancellationEffectiveDate.scrollToElement();
				cancellationEffectiveDate.setData(data.get("CancellationEffectiveDate"));
				Assertions.passTest("Request Cancellation Page",
						"Cancellation Effective Date is " + cancellationEffectiveDate.getData());
			}
		}
		if (cancellationReasonArrow.checkIfElementIsPresent() && cancellationReasonArrow.checkIfElementIsDisplayed()) {
			cancellationReasonArrow.scrollToElement();
			cancellationReasonArrow.click();
			if (!data.get("CancellationReason").equals("")) {
				cancellationReasonOption.formatDynamicPath(data.get("CancellationReason")).scrollToElement();
				cancellationReasonOption.formatDynamicPath(data.get("CancellationReason")).click();
				Assertions.passTest("Request Cancellation Page",
						"Cancellation Reason is " + cancellationReasonData.getData());
			}
		}
		if (newEffectiveDate.checkIfElementIsPresent() && newEffectiveDate.checkIfElementIsDisplayed()) {
			if (!data.get("PolicyEffDate").equals("")) {
				newEffectiveDate.scrollToElement();
				newEffectiveDate.setData(data.get("PolicyEffDate"));
				Assertions.passTest("Request Cancellation Page", "New Effective Date is " + newEffectiveDate.getData());
			}
		}
		if (comments.checkIfElementIsPresent() && comments.checkIfElementIsDisplayed()) {
			if (!data.get("TransactionDescription").equals("")) {
				comments.scrollToElement();
				comments.setData(data.get("TransactionDescription"));
				Assertions.passTest("Request Cancellation Page", "Comments added successfully");
			}
		}
		if (addDocumentBtn.checkIfElementIsPresent() && addDocumentBtn.checkIfElementIsDisplayed()) {
			Assertions.passTest("Request Cancellation Page", "Add Document Button is displayed");
			addDocumentBtn.scrollToElement();
			addDocumentBtn.click();
			if (chooseFileLink.checkIfElementIsPresent() && chooseFileLink.checkIfElementIsDisplayed()) {
				Assertions.passTest("Request Cancellation Page", "Choose File Button is displayed");
				if (!data.get("FileNameToUpload").equals("")) {
					policyDocumentsPage.fileUpload(data.get("FileNameToUpload"));
					Assertions.passTest("Request Cancellation Page", "File Uploaded successfully");
				}
			}
		}
		if (submitBtn.checkIfElementIsPresent() && submitBtn.checkIfElementIsDisplayed()) {
			submitBtn.scrollToElement();
			submitBtn.click();

		}
	}
}
