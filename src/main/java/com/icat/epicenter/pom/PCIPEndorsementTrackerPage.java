package com.icat.epicenter.pom;

import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.DropDownControl;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextAreaControl;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class PCIPEndorsementTrackerPage extends BasePageControl {
	public TextFieldControl policyNumber;
	public DropDownControl transactionType;
	public TextFieldControl transactionEffectiveDate;
	public TextFieldControl approvedBy;
	public DropDownControl cancellationReason;
	public TextFieldControl policyExpirationDate;
	public TextAreaControl transactionDescription;
	public TextFieldControl otherTransactionPremium;
	public TextFieldControl transactionPolicyFee;
	public TextFieldControl transactionInspectionFee;
	public TextFieldControl totalTransactionPremium;
	public TextFieldControl policyCoverageTotal;
	public TextFieldControl newAnnualPremium;
	public TextFieldControl prorataFactor;
	public ButtonControl saveButton;
	public ButtonControl cancelButton;
	public ButtonControl transactionTypeArrow;
	public ButtonControl transactionTypeOption;

	public PCIPEndorsementTrackerPage() {
		PageObject pageobject = new PageObject("PCIPEndorsementTracker");

		policyNumber = new TextFieldControl(By.xpath(pageobject.getXpath("xp_PolicyNumber")));
		transactionType = new DropDownControl(By.xpath(pageobject.getXpath("xp_TransactionType")));
		transactionEffectiveDate = new TextFieldControl(By.xpath(pageobject.getXpath("xp_TransactionEffectiveDate")));
		approvedBy = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ApprovedBy")));
		cancellationReason = new DropDownControl(By.xpath(pageobject.getXpath("xp_CancellationReason")));
		policyExpirationDate = new TextFieldControl(By.xpath(pageobject.getXpath("xp_PolicyExpirationDate")));
		transactionDescription = new TextAreaControl(By.xpath(pageobject.getXpath("xp_TransactionDescription")));
		otherTransactionPremium = new TextFieldControl(By.xpath(pageobject.getXpath("xp_OthertransactionPremium")));
		transactionPolicyFee = new TextFieldControl(By.xpath(pageobject.getXpath("xp_TransactionPolicyFee")));
		transactionInspectionFee = new TextFieldControl(By.xpath(pageobject.getXpath("xp_TransactionInspectionFee")));
		totalTransactionPremium = new TextFieldControl(By.xpath(pageobject.getXpath("xp_TotalTransactionPremium")));
		policyCoverageTotal = new TextFieldControl(By.xpath(pageobject.getXpath("xp_NewPolicyCoverage")));
		newAnnualPremium = new TextFieldControl(By.xpath(pageobject.getXpath("xp_NewAnnualPremium")));
		prorataFactor = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ProrataFactor")));
		saveButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_SaveButton")));
		cancelButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_CancelButton")));
		transactionTypeArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_TransactionTypeArrow")));
		transactionTypeOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_TransactionTypeOption")));
	}

	public void enterEndorsementDetails(Map<String, String> Data) {
		if (!Data.get("TransactionType").equals("") && transactionType.checkIfElementIsDisplayed()) {
			transactionType.scrollToElement();
			transactionType.selectByVisibleText(Data.get("TransactionType"));
		} else {
			if (!Data.get("TransactionType").equals("") && transactionTypeArrow.checkIfElementIsDisplayed()) {
				transactionTypeArrow.scrollToElement();
				transactionTypeArrow.click();
				transactionTypeOption.formatDynamicPath(Data.get("TransactionType")).scrollToElement();
				transactionTypeOption.formatDynamicPath(Data.get("TransactionType")).click();
			}
		}

		if (!Data.get("TransactionEffectiveDate").equals("")) {
			transactionEffectiveDate.scrollToElement();
			transactionEffectiveDate.setData(Data.get("TransactionEffectiveDate"));
		}

		if (!Data.get("CancellationReason").equals("")) {
			cancellationReason.scrollToElement();
			cancellationReason.selectByVisibleText(Data.get("CancellationReason"));
		}

		if (!Data.get("PolicyExpirationDate").equals("")) {
			policyExpirationDate.scrollToElement();
			policyExpirationDate.setData(Data.get("PolicyExpirationDate"));
		}

		if (!Data.get("TransactionDescription").equals("")) {
			transactionDescription.scrollToElement();
			transactionDescription.setData(Data.get("TransactionDescription"));
		}

		if (!Data.get("TransactionPolicyfee").equals("")) {
			transactionPolicyFee.scrollToElement();
			transactionPolicyFee.setData(Data.get("TransactionPolicyfee"));
		}

		if (!Data.get("TransactionInspectionFee").equals("")) {
			transactionInspectionFee.scrollToElement();
			transactionInspectionFee.setData(Data.get("TransactionInspectionFee"));
		}

		if (!Data.get("TotalTransactionPremium").equals("")) {
			totalTransactionPremium.scrollToElement();
			totalTransactionPremium.setData(Data.get("TotalTransactionPremium"));
			totalTransactionPremium.tab();
		}
		if (!Data.get("OtherTransactionPremium").equals("")) {
			otherTransactionPremium.scrollToElement();
			otherTransactionPremium.setData(Data.get("OtherTransactionPremium"));
			otherTransactionPremium.tab();
		}

		if (Data.get("PolicyCoverageTotal") != null) {
			if (!Data.get("PolicyCoverageTotal").equals("")) {
				policyCoverageTotal.scrollToElement();
				policyCoverageTotal.setData(Data.get("PolicyCoverageTotal"));
			}
		}
		if (Data.get("NewAnnualPremium") != null) {
			if (!Data.get("NewAnnualPremium").equals("")) {
				newAnnualPremium.scrollToElement();
				newAnnualPremium.setData(Data.get("NewAnnualPremium"));
			}
		}
		if (Data.get("ProrataFactor") != null) {
			if (!Data.get("ProrataFactor").equals("")) {
				prorataFactor.scrollToElement();
				prorataFactor.setData(Data.get("ProrataFactor"));
			}
		}
		saveButton.scrollToElement();
		saveButton.click();
	}
}
