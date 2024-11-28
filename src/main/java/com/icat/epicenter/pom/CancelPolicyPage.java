
/** Program Description: Object Locators and methods defined in Cancel policy page
 *  Author			   : SMNetserv
 *  Date of Creation   : 30/10/2017
**/

package com.icat.epicenter.pom;

import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.CheckBoxControl;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.RadioButtonControl;
import com.NetServAutomationFramework.generic.TextAreaControl;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class CancelPolicyPage extends BasePageControl {
	public TextFieldControl daysBeforeNOC;
	public ButtonControl cancelReasonArrow;
	public ButtonControl cancelReasonOption;
	public BaseWebElementControl cancelReasonData;
	public TextAreaControl legalNoticeWording;
	public TextFieldControl cancellationEffectiveDate;
	public ButtonControl cancelButton;
	public ButtonControl nextButton;
	public TextAreaControl underwriterComment;
	public ButtonControl previousButton;
	public ButtonControl completeTransactionButton;
	public ButtonControl closeButton;

	public ButtonControl continueButton;
	public BaseWebElementControl pageName;

	public BaseWebElementControl cancellationSuccess;
	public BaseWebElementControl cancellationStatus;
	public RadioButtonControl cancelOption;
	public RadioButtonControl cancelOption1;

	public BaseWebElementControl reasonErrorMessage;
	public BaseWebElementControl reasonWarningMessage;

	public ButtonControl okButton;
	public ButtonControl deleteAndContinue;
	public BaseWebElementControl sltfData;

	public RadioButtonControl proRatedRadioBtn;
	public RadioButtonControl shortRatedRadioBtn;
	public BaseWebElementControl premiumValue;
	public BaseWebElementControl inspectionFee;
	public BaseWebElementControl inspectionFee1;
	public BaseWebElementControl policyFee;
	public BaseWebElementControl policyFeeNAHO;
	public BaseWebElementControl taxesAndFees;
	public BaseWebElementControl policyTotal;

	public TextFieldControl inspectionFeeEarned;
	public TextFieldControl policyFeeEarned;
	public BaseWebElementControl prorataFactor;
	public BaseWebElementControl earnedPremiumValue;
	public BaseWebElementControl returnedPremiumValue;
	public BaseWebElementControl surplusContributionVlaue;

	public BaseWebElementControl earnedPremiumTotal;
	public BaseWebElementControl earnedSurplusContribution;
	public BaseWebElementControl returnedPremiumTotal;
	public BaseWebElementControl returnedSurplusContribution;
	public RadioButtonControl proRatedMinEarnedRadioBtn;
	public ButtonControl lapseContinueButton;
	public BaseWebElementControl newPremium;
	public BaseWebElementControl newPremiumNAHO;
	public BaseWebElementControl newSLTF;
	public BaseWebElementControl globalError;
	public RadioButtonControl proRataRadioBtn;
	public BaseWebElementControl SLTF;
	public BaseWebElementControl surplusContributionVal;
	public BaseWebElementControl totalPremiumTaxesFees;
	public TextFieldControl newInspectionFee;
	public TextFieldControl newPolicyFee;
	public CheckBoxControl adminTransactionCheckBox;

	public BaseWebElementControl returnedPremium;
	public BaseWebElementControl returnedInspectionFee;
	public BaseWebElementControl returnedPolicyFee;
	public BaseWebElementControl returnedSLTF;
	public BaseWebElementControl warningMessage;
	public BaseWebElementControl legalNoticeWordingMandatoryMsg;
	public BaseWebElementControl nocMailDate;
	public BaseWebElementControl premium;
	public BaseWebElementControl loading;
	public BaseWebElementControl feesNegativeWarningMsg;
	public ButtonControl inspectionFeeField;
	public ButtonControl policyFeeField;
	public BaseWebElementControl policyEffectiveDate;

	public CancelPolicyPage() {
		PageObject pageobject = new PageObject("CancelPolicy");
		daysBeforeNOC = new TextFieldControl(By.xpath(pageobject.getXpath("xp_DaysBeforeNOC")));
		cancelReasonArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_CancelReasonArrow")));
		cancelReasonOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_CancelReasonOption")));
		cancelReasonData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CancelReasonData")));
		legalNoticeWording = new TextAreaControl(By.xpath(pageobject.getXpath("xp_LegalNoticeWording")));
		cancellationEffectiveDate = new TextFieldControl(By.xpath(pageobject.getXpath("xp_CancellationEffDate")));
		cancelButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_CancelButton")));
		nextButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_NextButton")));
		underwriterComment = new TextAreaControl(By.xpath(pageobject.getXpath("xp_UnderwriterComment")));
		previousButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_PreviousButton")));
		completeTransactionButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_CompleteTransaction")));
		closeButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_Close")));

		continueButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_Continuebutton")));
		pageName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Pagename")));

		cancellationSuccess = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CancellationSuccess")));
		cancellationStatus = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CancellationStatus")));
		cancelOption = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_CancelOption")));
		cancelOption1 = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_CancelOption1")));
		reasonErrorMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ReasonErrorMessage")));
		reasonWarningMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ReasonWarningMessage")));
		okButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_OkButton")));
		deleteAndContinue = new ButtonControl(By.xpath(pageobject.getXpath("xp_DeleteAndContinue")));
		sltfData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_sltfData")));
		proRatedRadioBtn = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_ProRatedRadioBtn")));
		shortRatedRadioBtn = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_ShortRatedRadioBtn")));
		premiumValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PremiumValue")));
		inspectionFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_InspectionFee")));
		inspectionFee1 = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_InspectionFeeNAHO")));
		policyFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolicyFee")));
		policyFeeNAHO = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolicyFeeNAHO")));
		taxesAndFees = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TaxesAndFees")));
		policyTotal = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolicyTotal")));

		inspectionFeeEarned = new TextFieldControl(By.xpath(pageobject.getXpath("xp_InspectionFeeEarned")));
		policyFeeEarned = new TextFieldControl(By.xpath(pageobject.getXpath("xp_PolicyFeeEarned")));
		prorataFactor = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ProrataFactor")));
		earnedPremiumValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EarnedPremiumValue")));
		returnedPremiumValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ReturnedPremiumValue")));
		surplusContributionVlaue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_SurplusContributionVlaue")));
		earnedPremiumTotal = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EarnedPremiumTotal")));
		earnedSurplusContribution = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_EarnedSurplusContribution")));
		returnedPremiumTotal = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ReturnedPremiumTotal")));
		returnedSurplusContribution = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_ReturnedSurplusContribution")));
		proRatedMinEarnedRadioBtn = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_ProRatedMinEarned")));
		lapseContinueButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_LapseContinueButton")));
		//Added New earned fee override and Returned fee override for SLTF Regression testcases
		newPremium=new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NewPremium")));
		newPremiumNAHO=new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NewPremiumNAHO")));
		newSLTF=new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NewSLTF")));
		globalError = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GlobalError")));
		proRataRadioBtn = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_ProRataRadioBtn")));
		SLTF=new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_SLTF")));
		surplusContributionVal = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_SurplusContributionVal")));
		totalPremiumTaxesFees=new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TotalPremiumTaxesFees")));
		newInspectionFee = new TextFieldControl(By.xpath(pageobject.getXpath("xp_NewInspectionFee")));
		newPolicyFee = new TextFieldControl(By.xpath(pageobject.getXpath("xp_NewPolicyFee")));
		adminTransactionCheckBox = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_AdminTransactionCheckBox")));
		returnedPremium=new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ReturnedPremium")));
		returnedInspectionFee=new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ReturnedInspectionFee")));
		returnedPolicyFee=new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ReturnedPoicyFee")));
		returnedSLTF=new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ReturnedSLTF")));
		warningMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_WarningMessage")));
		legalNoticeWordingMandatoryMsg = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_LegalNoticeWordingMandatoryMsg")));
		nocMailDate = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NocMailDate")));
		premium=new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Premium")));
		loading=new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Loading")));
		feesNegativeWarningMsg =new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_FeesNegativeWarningMsg")));
		inspectionFeeField = new ButtonControl(By.xpath(pageobject.getXpath("xp_NewInspectionFeeNAHO")));
		policyFeeField = new ButtonControl(By.xpath(pageobject.getXpath("xp_NewPolicyFeeNAHO")));
		policyEffectiveDate = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolicyEffectiveDate")));
	}

	public PolicySummaryPage enterCancellationDetails(Map<String, String> Data) {
		cancellationEffectiveDate.scrollToElement();
		cancelReasonArrow.scrollToElement();
		cancelReasonArrow.click();
		waitTime(3);
		cancelReasonOption.formatDynamicPath(Data.get("CancellationReason")).click();
		Assertions.passTest("Cancel Policy Page", "Cancellation Reason : " + cancelReasonData.getData());
		if (!Data.get("CancellationEffectiveDate").equals("")) {
			cancellationEffectiveDate.scrollToElement();
			cancellationEffectiveDate.setData(Data.get("CancellationEffectiveDate"));
			cancellationEffectiveDate.tab();
		}

		if (!Data.get("Cancellation_DaysBeforeNOC").equals("")) {
			daysBeforeNOC.scrollToElement();
			daysBeforeNOC.setData(Data.get("Cancellation_DaysBeforeNOC"));
		}
		if (!Data.get("Cancellation_LegalNoticeWording").equals("")) {
			legalNoticeWording.scrollToElement();
			legalNoticeWording.setData(Data.get("Cancellation_LegalNoticeWording"));
		}
		nextButton.scrollToElement();
		nextButton.click();
		if (pageName.getData().contains("Out Of Sequence")) {
			continueButton.scrollToElement();
			continueButton.click();
		}
		if (!Data.get("TransactionDescription").equals("")) {
			underwriterComment.scrollToElement();
			underwriterComment.setData(Data.get("TransactionDescription"));
		}

		completeTransactionButton.scrollToElement();
		completeTransactionButton.click();
		closeButton.scrollToElement();
		closeButton.click();
		return new PolicySummaryPage();
	}
}
