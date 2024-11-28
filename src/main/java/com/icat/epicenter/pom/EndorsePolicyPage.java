/** Program Description: Object Locators and methods defined in Endorse policy page
 *  Author			   : SMNetserv
 *  Date of Creation   : 11/11/2017
 **/

package com.icat.epicenter.pom;

import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.CheckBoxControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextAreaControl;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class EndorsePolicyPage extends BasePageControl {
	public TextFieldControl endorsementEffDate;
	public HyperLink changeNamedInsuredLink;
	public HyperLink changeInspectionContactLink;
	public HyperLink changeInspectionContactLinkNAHO;
	public HyperLink changeAIInformationLink;
	public HyperLink producerContactLink;
	public HyperLink changePaymentPlanLink;

	public HyperLink editLocOrBldgInformationLink;
	public HyperLink changeCoverageOptionsLink;
	public HyperLink feeOnlyEndorsement;
	public ButtonControl cancelButton;
	public TextAreaControl transactionComments;
	public ButtonControl saveButton;
	public ButtonControl nextButton;

	public ButtonControl viewRateTraceButton;
	public ButtonControl completeButton;
	public ButtonControl continueButton;
	public ButtonControl continueDiv;

	public BaseWebElementControl changeCharacteristicsPopup;
	public ButtonControl changeCharacteristicsButton;
	public ButtonControl cancelChangeCharacteristicsButton;
	public ButtonControl closeButton;
	public BaseWebElementControl pageName;
	public ButtonControl submitButton;
	public ButtonControl rollForwardBtn;
	public ButtonControl completeAndRollForwardBtn;

	public BaseWebElementControl namedHurricane;
	public BaseWebElementControl inspectionAreaCode;
	public BaseWebElementControl transactionPremium;
	public BaseWebElementControl totalTermPremium;

	public HyperLink noteBar;
	public BaseWebElementControl loading;

	public HyperLink processPCIPEndorsementLink;
	public ButtonControl makePolicyUnAutomated_Yes;
	public ButtonControl makePolicyUnAutomated_No;

	public HyperLink editPriorLoss;
	public HyperLink editGLInformationLink;
	public HyperLink changeExpirationDateLink;
	public TextFieldControl policyExpirationDate;
	public ButtonControl changeExpirationDate;
	public BaseWebElementControl policyUnAutomatedMessage;

	public HyperLink allOtherChanges;
	public BaseWebElementControl endorsementReferralMessage;
	public ButtonControl okButton;
	public HyperLink prodInspectionContactLink;
	public BaseWebElementControl oosInfoMessage;
	public HyperLink prodDeductiblesAndCoverageLink;
	public BaseWebElementControl changingEndorsementToRequireReviewMessage;
	public ButtonControl iNeedToChangeRiskButton;

	public BaseWebElementControl priorLossReferralMsg1;
	public BaseWebElementControl priorLossReferralMsg2;
	public BaseWebElementControl priorLossReferralMsg3;
	public BaseWebElementControl repairedQuestionNoMsg;

	public ButtonControl viewEndtQuoteButton;
	public ButtonControl pendButton;
	public ButtonControl pushToRMSButton;
	public ButtonControl runningStatus;
	public ButtonControl deleteExistingEndorsementButton;
	public BaseWebElementControl insuredValueMsg;
	public ButtonControl reviseButton;
	public ButtonControl releasetoProducerButton;
	public ButtonControl editExistingEnmtButton;
	public BaseWebElementControl endorsementEffDateErrorMsg;

	public BaseWebElementControl sltfData;

	public ButtonControl overrideFeesButton;
	public CheckBoxControl waivepremium;
	public ButtonControl saveAndCloseButton;
	public BaseWebElementControl premium;

	public BaseWebElementControl transactionPremiumFee;
	public BaseWebElementControl inspectionFee;
	public BaseWebElementControl policyFee;
	public BaseWebElementControl otherFees;
	public BaseWebElementControl totalTerm;
	public BaseWebElementControl endorsementSummary;

	public BaseWebElementControl globalWarning;
	public BaseWebElementControl producerEndorseWarningMessage;
	public BaseWebElementControl surplusContributionValue;
	public BaseWebElementControl surplusContributionValueNAHO;
	public BaseWebElementControl endorsementChanges;
	public ButtonControl editEndtQuoteButton;
	public ButtonControl viewModelResultsButton;
	public ButtonControl oKContinueButton;
	public BaseWebElementControl premiumDetails;
	public BaseWebElementControl paymentPlanError;
	public BaseWebElementControl conflictTxn;
	public BaseWebElementControl oosMsg;
	public ButtonControl viewEndorsementQuote;
	public BaseWebElementControl prorataFactor;
	public BaseWebElementControl endorsementWarningMsg;
	public ButtonControl deleteButton;
	public BaseWebElementControl effectiveDateWarningMsg;
	public BaseWebElementControl transactionPremiumDetails;
	public BaseWebElementControl aorWarningMessage;
	public BaseWebElementControl stripSinkholeWarning;
	public ButtonControl stripSinkholeButton;
	public ButtonControl stripSinkholeOverride;
	public BaseWebElementControl dedMinOverrideWarning;
	public ButtonControl dedMinOverrideButton;
	public BaseWebElementControl rnlWarningmsg;
	public BaseWebElementControl newPremium;
	public BaseWebElementControl newInspectionFee;
	public BaseWebElementControl newPolicyFee;
	public BaseWebElementControl newSLTF;
	public BaseWebElementControl newTotalValues;
	public ButtonControl leaveRenewalAsIsButton;
	public BaseWebElementControl expirationDateErrorMsg;
	public BaseWebElementControl viewModelResults;
	public ButtonControl viewModelCloseButton;
	public CheckBoxControl administrativeTransaction;

	public EndorsePolicyPage() {
		PageObject pageobject = new PageObject("EndorsePolicy");
		endorsementEffDate = new TextFieldControl(By.xpath(pageobject.getXpath("xp_EndorsementEffectiveDate")));
		changeNamedInsuredLink = new HyperLink(
				By.xpath(pageobject.getXpath("xp_ChangeNamedInsuredOrMailingAddressLink")));
		changeInspectionContactLink = new HyperLink(By.xpath(pageobject.getXpath("xp_ChangeInspectionContactLink")));
		changeInspectionContactLinkNAHO = new HyperLink(
				By.xpath(pageobject.getXpath("xp_ChangeInspectionContactLinkNAHO")));
		changeAIInformationLink = new HyperLink(By.xpath(pageobject.getXpath("xp_ChangeAIInformationLink")));
		producerContactLink = new HyperLink(By.xpath(pageobject.getXpath("xp_ProducerContact")));
		changePaymentPlanLink = new HyperLink(By.xpath(pageobject.getXpath("xp_ChangePaymentPlanLink")));

		editLocOrBldgInformationLink = new HyperLink(By.xpath(pageobject.getXpath("xp_EditLocOrBldgInformation")));
		changeCoverageOptionsLink = new HyperLink(By.xpath(pageobject.getXpath("xp_ChangeCoverageOptions")));
		feeOnlyEndorsement = new HyperLink(By.xpath(pageobject.getXpath("xp_FeeOnlyEndorsement")));
		cancelButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_CancelButton")));
		transactionComments = new TextAreaControl(By.xpath(pageobject.getXpath("xp_TransactionComments")));
		saveButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_Savebutton")));
		nextButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_NextButton")));

		viewRateTraceButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ViewRateTraceButton")));
		completeButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_CompleteButton")));
		continueButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_Continuebutton")));
		continueDiv = new ButtonControl(By.xpath(pageobject.getXpath("xp_Continue")));

		changeCharacteristicsPopup = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_ChangeCharacteristicsPopup")));
		changeCharacteristicsButton = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_ChangeCharacteristicsButton")));
		cancelChangeCharacteristicsButton = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_CancelChangeCharacteristicsButton")));
		closeButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_CloseButton")));
		pageName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Pagename")));
		submitButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_SubmitButton")));
		rollForwardBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_RollForwardBtn")));
		completeAndRollForwardBtn = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_CompleteandRollForwardBtntorenewal")));
		namedHurricane = new ButtonControl(By.xpath(pageobject.getXpath("xp_NamedHurricane")));
		inspectionAreaCode = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_InspectionAreaCode")));
		transactionPremium = new ButtonControl(By.xpath(pageobject.getXpath("xp_TransactionPremium")));
		totalTermPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TotalTermPremium")));
		noteBar = new HyperLink(By.xpath((pageobject.getXpath("xp_NoteBar"))));

		loading = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Loading")));

		processPCIPEndorsementLink = new HyperLink(By.xpath((pageobject.getXpath("xp_ProcessPCIPEndorsement"))));
		makePolicyUnAutomated_Yes = new ButtonControl(By.xpath(pageobject.getXpath("xp_PolicyUnAutomated_Yes")));
		makePolicyUnAutomated_No = new ButtonControl(By.xpath(pageobject.getXpath("xp_PolicyUnAutomated_No")));

		editPriorLoss = new HyperLink(By.xpath((pageobject.getXpath("xp_EditPriorLossLink"))));
		editGLInformationLink = new HyperLink(By.xpath((pageobject.getXpath("xp_EditGLInformationLink"))));
		changeExpirationDateLink = new HyperLink(By.xpath((pageobject.getXpath("xp_ChangeExpirationDateLink"))));
		policyExpirationDate = new TextFieldControl(By.xpath(pageobject.getXpath("xp_NewExpirationDate")));
		changeExpirationDate = new ButtonControl(By.xpath(pageobject.getXpath("xp_ChangeExpirationDate")));
		policyUnAutomatedMessage = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PolicyUnautomated_Message")));

		allOtherChanges = new HyperLink(By.xpath((pageobject.getXpath("xp_AllOtherChangesLink"))));
		endorsementReferralMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EndorsementReferral")));
		okButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_OkButton")));
		prodInspectionContactLink = new HyperLink(By.xpath(pageobject.getXpath("xp_InspectionContactLink")));
		oosInfoMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OOSInfoMessage")));
		prodDeductiblesAndCoverageLink = new HyperLink(By.xpath(pageobject.getXpath("xp_DeductiblesAndCoverageLink")));
		changingEndorsementToRequireReviewMessage = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_ChangingEndorsementToRequireReviewMessage")));
		iNeedToChangeRiskButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_INeedToChangeRiskButton")));
		priorLossReferralMsg1 = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PriorLossReferralMsg1")));
		priorLossReferralMsg2 = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PriorLossReferralMsg2")));
		priorLossReferralMsg3 = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PriorLossReferralMsg3")));
		repairedQuestionNoMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RepairedQuestionNoMsg")));
		viewEndtQuoteButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ViewEndtQuoteButton")));
		pendButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_PendButton")));
		pushToRMSButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_PushToRMSButton")));
		runningStatus = new ButtonControl(By.xpath(pageobject.getXpath("xp_RunningStatus")));
		deleteExistingEndorsementButton = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_DeleteExistingEndorsementButton")));
		insuredValueMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_InsuredValueMsg")));
		reviseButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ReviseButton")));
		releasetoProducerButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ReleasetoProducerButton")));
		editExistingEnmtButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_EditExistingEnmtButton")));
		endorsementEffDateErrorMsg = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_EndorsementEffDateErrorMsg")));
		sltfData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_sltfData")));

		overrideFeesButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_OverrideFeesButton")));
		waivepremium = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_WaivePremium")));
		saveAndCloseButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_SaveandClose")));
		premium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Premium")));

		transactionPremiumFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TransactionPremiumFee")));
		inspectionFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_InspectionFee")));
		policyFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolicyFee")));
		otherFees = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OtherFees")));
		totalTerm = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TotalTerm")));
		endorsementSummary = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EndorsementSummary")));

		globalWarning = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Globalwarning")));
		producerEndorseWarningMessage = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_ProducerEndorseWarningMessage")));
		surplusContributionValue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_SurplusContributionValue")));
		surplusContributionValueNAHO = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_SurplusContributionValueNAHO")));
		endorsementChanges = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EndorsementChanges")));
		editEndtQuoteButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_EditEndtQuoteButton")));
		viewModelResultsButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ViewModelResultsButton")));
		oKContinueButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_OkContinueButton")));
		premiumDetails = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PremiumDetails")));
		paymentPlanError = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PaymentPlanError")));
		conflictTxn = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ConflictTxn")));
		oosMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OosMsg")));
		viewEndorsementQuote = new ButtonControl(By.xpath(pageobject.getXpath("xp_ViewEndorsementQuote")));
		prorataFactor = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ProrataFactor")));
		endorsementWarningMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EndorsementWarningMsg")));
		deleteButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_DeleteButton")));
		effectiveDateWarningMsg = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_EffectiveDateWarningMsg")));
		transactionPremiumDetails = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_TransactionPremiumDetails")));
		aorWarningMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AorWarningMessage")));
		stripSinkholeWarning = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_StripSinkholeMsg")));
		stripSinkholeButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_StripSinkholeButton")));
		stripSinkholeOverride = new ButtonControl(By.xpath(pageobject.getXpath("xp_StripSinkholeOverride")));
		dedMinOverrideWarning = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DedMinOverrideMsg")));
		dedMinOverrideButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_DedMinOverride")));
		rnlWarningmsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RNLWarningmsg")));
		newPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NewPremium")));
		newInspectionFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NewInspectionFee")));
		newPolicyFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NewPolicyFee")));
		newSLTF = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NewSLTF")));
		newTotalValues = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NewTotalValues")));
		leaveRenewalAsIsButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_LeaveRenewalAsIsButton")));
		expirationDateErrorMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ExpirationDateErrorMsg")));
		viewModelResults = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ViewModelResults")));
		viewModelCloseButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ViewModelCloseButton")));
		administrativeTransaction = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_AdministrativeTransaction")));

	}

	public void enterEndorsement_Details(Map<String, String> testData) {
		ChangePaymentPlanPage changePaymentPlanPage = new ChangePaymentPlanPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		endorsementEffDate.scrollToElement();
		System.out.println(String.format("TCID=%s, transNum=%s, transEffDate=", testData.get("TCID"),
				testData.get("TransactionNum"), testData.get("TransactionEffectiveDate")));
		endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
		endorsementEffDate.tab();
		loading.waitTillInVisibilityOfElement(5);

		// term endorsement can only be done with no other changes
		if (testData.get("TransactionDescription").equalsIgnoreCase("change policy term")) {
			policyExpirationDate.setData(testData.get("PolicyExpirationDate"));
			changeExpirationDate.scrollToElement();
			changeExpirationDate.click();
			if (pageName.getData().contains("Out Of Sequence")) {
				continueButton.scrollToElement();
				continueButton.click();
			}

		} else {

			if (testData.get("TransactionType").contains("OOS")) {
				if (pageName.getData().contains("Out Of Sequence")) {
					continueButton.scrollToElement();
					continueButton.click();
				}
			}
			if (testData.get("ChangeNamedInsuredDetails").equalsIgnoreCase("yes")) {
				changeNamedInsuredLink.scrollToElement();
				changeNamedInsuredLink.click();
				Assertions.passTest("Change Named Insured Page", "Change Named Insured Page loaded successfully");
				ChangeNamedInsuredPage namedInsuredpage = new ChangeNamedInsuredPage();
				namedInsuredpage.enterInsuredAddressDetailPB(testData);
				Assertions.passTest("Change Named Insured Page", "Details entered successfully");
			}
			if (testData.get("ChangeInspectionContactDetails").equalsIgnoreCase("yes")) {
				changeInspectionContactLink.scrollToElement();
				changeInspectionContactLink.click();
				Assertions.passTest("Endorse Inspection Contact Page",
						"Endorse Inspection Contact Page loaded successfully");
				EndorseInspectionContactPage endorseInspectionContactPage = new EndorseInspectionContactPage();
				endorseInspectionContactPage.enterInspectionContactPB(testData);
				Assertions.passTest("Endorse Inspection Contact Page", "Details entered successfully");
			}
			if (testData.get("ChangeAdditionalInterestDetails").equalsIgnoreCase("yes")) {
				changeAIInformationLink.scrollToElement();
				changeAIInformationLink.click();
				Assertions.passTest("Endorse Additional Interset Page",
						"Endorse Additional Interset Page loaded successfully");
				EndorseAdditionalInterestsPage endorseAdditionalInterestsPage = new EndorseAdditionalInterestsPage();
				endorseAdditionalInterestsPage.enterEndorsementAdditionalInterestDetails(testData);
				Assertions.passTest("Endorse Additional Interset Page", "Details entered successfully");
			}
			if (testData.get("ChangeProducerContactDetails").equalsIgnoreCase("yes")) {
				producerContactLink.scrollToElement();
				producerContactLink.click();
				Assertions.passTest("Endorse Producer Contact Page",
						"Endorse Producer Contact Page loaded successfully");
				EndorseProducerContact endorseProducerContact = new EndorseProducerContact();
				endorseProducerContact.enterEndorseProducerContactDetails(testData);
				Assertions.passTest("Endorse Producer Contact Page", "Details entered successfully");
			}
			if (testData.containsKey("ChangePaymentPlanDetails")) {
				if (testData.get("ChangePaymentPlanDetails").equalsIgnoreCase("yes")) {
					changePaymentPlanLink.scrollToElement();
					changePaymentPlanLink.click();
					Assertions.passTest("Change Payment Plan Page", "Change Payment Plan Page loaded successfully");
					changePaymentPlanPage.enterChangePaymentPlanPB(testData);
					Assertions.passTest("Change Payment Plan Page", "Details entered successfully");
				}
			}
			if (testData.get("ChangeLocationOrBuildingDetails").equalsIgnoreCase("yes")) {
				editLocOrBldgInformationLink.scrollToElement();
				editLocOrBldgInformationLink.click();
				Assertions.passTest("Location/Dwelling Page", "Location/Dwelling Page loaded successfully");
				if (changeCharacteristicsButton.checkIfElementIsPresent()
						&& changeCharacteristicsButton.checkIfElementIsDisplayed()) {
					changeCharacteristicsButton.waitTillVisibilityOfElement(60);
					changeCharacteristicsButton.scrollToElement();
					changeCharacteristicsButton.click();
					changeCharacteristicsButton.waitTillInVisibilityOfElement(60);
				}

				LocationPage locationPage = new LocationPage();
				locationPage.enterEndorsementLocationDetails(testData);
				BuildingPage buildingPage = new BuildingPage();
				buildingPage.enterEndorsementBuildingDetails(testData);

				Assertions.passTest("Location/Dwelling Page", "Details entered successfully");
				buildingPage.continueButton.scrollToElement();
				buildingPage.continueButton.click();
				if (buildingPage.pageName.getData().contains("Endorse Inspection")) {
					EndorseInspectionContactPage endorseContactPage = new EndorseInspectionContactPage();
					endorseContactPage.okButton.scrollToElement();
					endorseContactPage.okButton.click();
				}
				if (buildingPage.pageName.getData().contains("Create a Quote")) {
					createQuotePage.enterQuoteDetailsCommercial(testData);
					if (dedMinOverrideWarning.checkIfElementIsPresent() && dedMinOverrideWarning.checkIfElementIsDisplayed()) {
						dedMinOverrideButton.scrollToElement();
						dedMinOverrideButton.click();
					}
					Assertions.passTest("Create Quote Page", "Details entered successfully");
				}
			}
			if (testData.get("ChangeCoverageOptions").equalsIgnoreCase("yes")) {
				changeCoverageOptionsLink.scrollToElement();
				changeCoverageOptionsLink.click();
				Assertions.passTest("Create Quote Page", "Create Quote Page loaded successfully");
				if (changeCharacteristicsButton.checkIfElementIsPresent()
						&& changeCharacteristicsButton.checkIfElementIsDisplayed()) {
					changeCharacteristicsButton.waitTillVisibilityOfElement(60);
					changeCharacteristicsButton.scrollToElement();
					changeCharacteristicsButton.click();
					changeCharacteristicsButton.waitTillInVisibilityOfElement(60);
				}
				createQuotePage.enterQuoteDetailsCommercial(testData);
				if (dedMinOverrideWarning.checkIfElementIsPresent() && dedMinOverrideWarning.checkIfElementIsDisplayed()) {
					dedMinOverrideButton.scrollToElement();
					dedMinOverrideButton.click();
				}
			}
		}

		if (nextButton.checkIfElementIsPresent() && nextButton.checkIfElementIsDisplayed()) {
			nextButton.scrollToElement();
			nextButton.click();
			nextButton.waitTillInVisibilityOfElement(60);
			if (pageName.getData().contains("Overrides Required")) {
				continueButton.scrollToElement();
				continueButton.click();
			}
			if (pageName.getData().contains("Review Adjustments")) {
				continueButton.scrollToElement();
				continueButton.click();
			}
			if (stripSinkholeWarning.checkIfElementIsPresent() && stripSinkholeWarning.checkIfElementIsDisplayed()) {
				stripSinkholeOverride.scrollToElement();
				stripSinkholeOverride.click();
			}
		}

		//waive premium, override fees, change premiums
		if (testData.get("WaivePremium").equalsIgnoreCase("yes") || testData.get("OverridePremium").equalsIgnoreCase("yes")
		|| !testData.get("TransactionInspectionFee").equals("") || !testData.get("TransactionPolicyfee").equals("") ) {
			OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();
			overrideFeesButton.scrollToElement();
			overrideFeesButton.click();
			overridePremiumAndFeesPage.overridePremiumAndFeesDetails(testData);

		}

		if (transactionComments.checkIfElementIsPresent() && transactionComments.checkIfElementIsDisplayed()) {
			transactionComments.scrollToElement();
			transactionComments.setData(testData.get("TransactionDescription"));
		}
		if (completeButton.checkIfElementIsPresent() && completeButton.checkIfElementIsDisplayed()) {
			completeButton.scrollToElement();
			completeButton.click();
		}

		if (pageName.getData().contains("Out Of Sequence")) {
			continueButton.scrollToElement();
			continueButton.click();
		}

		scrollToBottomPage();
		waitTime(3);
		closeButton.waitTillButtonIsClickable(60);
		closeButton.waitTillVisibilityOfElement(60);
		closeButton.scrollToElement();
		closeButton.click();
		closeButton.waitTillInVisibilityOfElement(60);
	}

	public void enterEndorsement_DetailsNew(Map<String, String> testData) {
		ChangePaymentPlanPage changePaymentPlanPage = new ChangePaymentPlanPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		endorsementEffDate.scrollToElement();
		System.out.println(String.format("TCID=%s, transNum=%s, transEffDate=", testData.get("TCID"),
				testData.get("TransactionNum"), testData.get("TransactionEffectiveDate")));
		endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
		endorsementEffDate.tab();

		// term endorsement can only be done with no other changes
		if (testData.get("TransactionDescription").equalsIgnoreCase("change policy term")) {
			policyExpirationDate.setData(testData.get("PolicyExpirationDate"));
			changeExpirationDate.scrollToElement();
			changeExpirationDate.click();
			if (pageName.getData().contains("Out Of Sequence")) {
				continueButton.scrollToElement();
				continueButton.click();
			}

		} else {

			if (testData.get("TransactionType").contains("OOS")) {
				if (pageName.getData().contains("Out Of Sequence")) {
					continueButton.scrollToElement();
					continueButton.click();
				}
			}
			if (testData.get("ChangeNamedInsuredDetails").equalsIgnoreCase("yes")) {
				changeNamedInsuredLink.scrollToElement();
				changeNamedInsuredLink.click();
				Assertions.passTest("Change Named Insured Page", "Change Named Insured Page loaded successfully");
				ChangeNamedInsuredPage namedInsuredpage = new ChangeNamedInsuredPage();
				namedInsuredpage.enterInsuredAddressDetailPB(testData);
				Assertions.passTest("Change Named Insured Page", "Details entered successfully");
			}
			if (testData.get("ChangeInspectionContactDetails").equalsIgnoreCase("yes")) {
				changeInspectionContactLink.scrollToElement();
				changeInspectionContactLink.click();
				Assertions.passTest("Endorse Inspection Contact Page",
						"Endorse Inspection Contact Page loaded successfully");
				EndorseInspectionContactPage endorseInspectionContactPage = new EndorseInspectionContactPage();
				endorseInspectionContactPage.enterInspectionContactPB(testData);
				Assertions.passTest("Endorse Inspection Contact Page", "Details entered successfully");
			}
			if (testData.get("ChangeAdditionalInterestDetails").equalsIgnoreCase("yes")) {
				changeAIInformationLink.scrollToElement();
				changeAIInformationLink.click();
				Assertions.passTest("Endorse Additional Interset Page",
						"Endorse Additional Interset Page loaded successfully");
				EndorseAdditionalInterestsPage endorseAdditionalInterestsPage = new EndorseAdditionalInterestsPage();
				endorseAdditionalInterestsPage.enterEndorsementAdditionalInterestDetails(testData);
				Assertions.passTest("Endorse Additional Interset Page", "Details entered successfully");
			}
			if (testData.get("ChangeProducerContactDetails").equalsIgnoreCase("yes")) {
				producerContactLink.scrollToElement();
				producerContactLink.click();
				Assertions.passTest("Endorse Producer Contact Page",
						"Endorse Producer Contact Page loaded successfully");
				EndorseProducerContact endorseProducerContact = new EndorseProducerContact();
				endorseProducerContact.enterEndorseProducerContactDetails(testData);
				Assertions.passTest("Endorse Producer Contact Page", "Details entered successfully");
			}
			if (testData.containsKey("ChangePaymentPlanDetails")) {
				if (testData.get("ChangePaymentPlanDetails").equalsIgnoreCase("yes")) {
					changePaymentPlanLink.scrollToElement();
					changePaymentPlanLink.click();
					Assertions.passTest("Change Payment Plan Page", "Change Payment Plan Page loaded successfully");
					changePaymentPlanPage.enterChangePaymentPlanPB(testData);
					Assertions.passTest("Change Payment Plan Page", "Details entered successfully");
				}
			}
			if (testData.get("ChangeLocationOrBuildingDetails").equalsIgnoreCase("yes")) {
				editLocOrBldgInformationLink.scrollToElement();
				editLocOrBldgInformationLink.click();
				Assertions.passTest("Location/Dwelling Page", "Location/Dwelling Page loaded successfully");
				if (changeCharacteristicsButton.checkIfElementIsPresent()
						&& changeCharacteristicsButton.checkIfElementIsDisplayed()) {
					changeCharacteristicsButton.waitTillVisibilityOfElement(60);
					changeCharacteristicsButton.scrollToElement();
					changeCharacteristicsButton.click();
					changeCharacteristicsButton.waitTillInVisibilityOfElement(60);
				}

				LocationPage locationPage = new LocationPage();
				locationPage.enterEndorsementLocationDetailsNew(testData);
				BuildingPage buildingPage = new BuildingPage();
				buildingPage.enterEndorsementBuildingDetailsNew(testData);

				Assertions.passTest("Location/Dwelling Page", "Details entered successfully");
				buildingPage.continueButton.scrollToElement();
				buildingPage.continueButton.click();
				if (buildingPage.pageName.getData().contains("Endorse Inspection")) {
					EndorseInspectionContactPage endorseContactPage = new EndorseInspectionContactPage();
					endorseContactPage.okButton.scrollToElement();
					endorseContactPage.okButton.click();
				}
				if (buildingPage.pageName.getData().contains("Create a Quote")) {
					createQuotePage.enterQuoteDetailsCommercialNew(testData);
					Assertions.passTest("Create Quote Page", "Details entered successfully");
				}
			}
			if (testData.get("ChangeCoverageOptions").equalsIgnoreCase("yes")) {
				changeCoverageOptionsLink.scrollToElement();
				changeCoverageOptionsLink.click();
				Assertions.passTest("Create Quote Page", "Create Quote Page loaded successfully");
				if (changeCharacteristicsButton.checkIfElementIsPresent()
						&& changeCharacteristicsButton.checkIfElementIsDisplayed()) {
					changeCharacteristicsButton.waitTillVisibilityOfElement(60);
					changeCharacteristicsButton.scrollToElement();
					changeCharacteristicsButton.click();
					changeCharacteristicsButton.waitTillInVisibilityOfElement(60);
				}
				// createQuotePage.enterQuoteDetailsforPNB(testData);
				createQuotePage.enterQuoteDetailsCommercialNew(testData);
			}
			if (!testData.get("OrderInspection").equalsIgnoreCase("")) {
				feeOnlyEndorsement.scrollToElement();
				feeOnlyEndorsement.click();
				Assertions.passTest("Override Endorsement Page", "Override Endorsement Page loaded successfully");
				OverridePremiumAndFeesPage overridePremium_andFees = new OverridePremiumAndFeesPage();
				overridePremium_andFees.enterOverrideFeesDetails(testData);
				Assertions.passTest("Override Endorsement Page", "Details entered successfully");
			}
		}

		if (nextButton.checkIfElementIsPresent() && nextButton.checkIfElementIsDisplayed()) {
			nextButton.scrollToElement();
			nextButton.click();
			nextButton.waitTillInVisibilityOfElement(60);
			if (pageName.getData().contains("Overrides Required")) {
				continueButton.scrollToElement();
				continueButton.click();
			}
			if (pageName.getData().contains("Review Adjustments")) {
				continueButton.scrollToElement();
				continueButton.click();
			}
		}
		if (transactionComments.checkIfElementIsPresent() && transactionComments.checkIfElementIsDisplayed()) {
			transactionComments.scrollToElement();
			transactionComments.setData(testData.get("TransactionDescription"));
		}
		if (completeButton.checkIfElementIsPresent() && completeButton.checkIfElementIsDisplayed()) {
			completeButton.scrollToElement();
			completeButton.click();
		}

		if (pageName.getData().contains("Out Of Sequence")) {
			continueButton.scrollToElement();
			continueButton.click();
		}

		scrollToBottomPage();
		waitTime(3);
		closeButton.waitTillButtonIsClickable(60);
		closeButton.waitTillVisibilityOfElement(60);
		closeButton.scrollToElement();
		closeButton.click();
		closeButton.waitTillInVisibilityOfElement(60);
	}

	public void enterEndorsement_DetailsNAHO(Map<String, String> testData, String productSelection) {
		ChangePaymentPlanPage changePaymentPlanPage = new ChangePaymentPlanPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		endorsementEffDate.scrollToElement();
		endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
		endorsementEffDate.tab();
		if (!testData.get("TransactionType").equals("")) {
			if (testData.get("TransactionType").contains("OOS")) {
				if (pageName.getData().contains("Out Of Sequence")) {
					continueButton.scrollToElement();
					continueButton.click();
				}
			}
		}
		if (!testData.get("ChangeInspectionContactDetails").equals("")) {
			if (testData.get("ChangeInspectionContactDetails").equalsIgnoreCase("yes")) {
				changeInspectionContactLinkNAHO.scrollToElement();
				changeInspectionContactLinkNAHO.click();
				Assertions.passTest("Endorse Inspection Contact Page",
						"Endorse Inspection Contact Page loaded successfully");
				EndorseInspectionContactPage endorseInspectionContactPage = new EndorseInspectionContactPage();
				endorseInspectionContactPage.enterInspectionContactPB(testData);
				Assertions.passTest("Endorse Inspection Contact Page", "Details entered successfully");
			}
		}
		if (!testData.get("ChangeAdditionalInterestDetails").equals("")) {
			if (testData.get("ChangeAdditionalInterestDetails").equalsIgnoreCase("yes")) {
				changeAIInformationLink.scrollToElement();
				changeAIInformationLink.click();
				Assertions.passTest("Endorse Additional Interset Page",
						"Endorse Additional Interset Page loaded successfully");
				EndorseAdditionalInterestsPage endorseAdditionalInterestsPage = new EndorseAdditionalInterestsPage();
				endorseAdditionalInterestsPage.enterEndorsementAdditionalInterestDetailsNAHO(testData,
						productSelection);
				Assertions.passTest("Endorse Additional Interset Page", "Details entered successfully");
			}
		}

		if (!testData.get("ChangeNamedInsuredAddress").equals("")) {
			if (testData.get("ChangeNamedInsuredAddress").equalsIgnoreCase("yes")) {
				changeNamedInsuredLink.scrollToElement();
				changeNamedInsuredLink.click();
				Assertions.passTest("Change Named Insured Page", "Change Named Insured Page loaded successfully");
				ChangeNamedInsuredPage namedInsuredpage = new ChangeNamedInsuredPage();
				namedInsuredpage.enterInsuredAddressDetailPB(testData);
				Assertions.passTest("Change Named Insured Page", "Details entered successfully");
			}
		}
		if (!testData.get("ChangeProducerContactDetails").equals("")) {
			if (testData.get("ChangeProducerContactDetails").equalsIgnoreCase("yes")) {
				producerContactLink.scrollToElement();
				producerContactLink.click();
				Assertions.passTest("Endorse Producer Contact Page",
						"Endorse Producer Contact Page loaded successfully");
				EndorseProducerContact endorseProducerContact = new EndorseProducerContact();
				endorseProducerContact.enterEndorseProducerContactDetails(testData);
				Assertions.passTest("Endorse Producer Contact Page", "Details entered successfully");
			}
		}
		if (!testData.get("ChangePaymentPlanDetails").equals("")) {
			if (testData.get("ChangePaymentPlanDetails").equalsIgnoreCase("yes")) {
				changePaymentPlanLink.scrollToElement();
				changePaymentPlanLink.click();
				Assertions.passTest("Change Payment Plan Page", "Change Payment Plan Page loaded successfully");
				changePaymentPlanPage.enterChangePaymentPlanPB(testData);
				Assertions.passTest("Change Payment Plan Page", "Details entered successfully");
			}
		}
		if (!testData.get("ChangeLocationOrDwellingDetails").equals("")) {
			if (testData.get("ChangeLocationOrDwellingDetails").equalsIgnoreCase("yes")) {
				editLocOrBldgInformationLink.scrollToElement();
				editLocOrBldgInformationLink.click();
				Assertions.passTest("Location/Dwelling Page", "Location/Dwelling Page loaded successfully");
				if (changeCharacteristicsButton.checkIfElementIsPresent()
						&& changeCharacteristicsButton.checkIfElementIsDisplayed()) {
					changeCharacteristicsButton.waitTillVisibilityOfElement(60);
					changeCharacteristicsButton.scrollToElement();
					changeCharacteristicsButton.click();
					changeCharacteristicsButton.waitTillInVisibilityOfElement(60);
				}
				if (testData.get("Discount55Years") != null && !testData.get("Discount55Years").equalsIgnoreCase("")) {
					AccountDetails accountDetailsPage = new AccountDetails();
					accountDetailsPage.enterDiscountDetails(testData);
				}
				LocationPage locationPage = new LocationPage();
				locationPage.enterEndorsementLocationDetailsNew(testData);
				DwellingPage dwellingPage = new DwellingPage();
				dwellingPage.enterEndorsementDwellingDetails(testData, productSelection);
				if (testData.get("AddLocation") != null && !testData.get("AddLocation").equals("")) {
					locationPage.addLocation(testData);
					dwellingPage.addDwellingForNewLocation(testData);
					Assertions.passTest("Location/Dwelling Page", "Details entered successfully");
				}
				Assertions.passTest("Location/Dwelling Page", "Details modified successfully");

				dwellingPage.continueButton.scrollToElement();
				dwellingPage.continueButton.click();

				if (dwellingPage.pageName.getData().contains("Endorse Inspection")) {
					EndorseInspectionContactPage endorseContactPage = new EndorseInspectionContactPage();
					endorseContactPage.okButton.scrollToElement();
					endorseContactPage.okButton.click();
				}
				if (dwellingPage.pageName.getData().contains("Create a Quote")
						&& !productSelection.equalsIgnoreCase("Residential Non-Admitted"))
					createQuotePage.enterQuoteDetailsforPNB(testData);
				else
					createQuotePage.enterQuoteDetailsNAHO(testData);
			}
		}
		// Adding this condition to handle deductible minimum warning
		if (createQuotePage.override.checkIfElementIsPresent()
				&& createQuotePage.override.checkIfElementIsDisplayed()) {
			createQuotePage.override.scrollToElement();
			createQuotePage.override.click();
		}
		if (!testData.get("ChangeCoverageOptions").equals("")) {
			if (testData.get("ChangeCoverageOptions").equalsIgnoreCase("yes")) {
				changeCoverageOptionsLink.scrollToElement();
				changeCoverageOptionsLink.click();
				Assertions.passTest("Create Quote Page", "Create Quote Page loaded successfully");
				if (changeCharacteristicsButton.checkIfElementIsPresent()
						&& changeCharacteristicsButton.checkIfElementIsDisplayed()) {
					changeCharacteristicsButton.waitTillVisibilityOfElement(60);
					changeCharacteristicsButton.scrollToElement();
					changeCharacteristicsButton.click();
					changeCharacteristicsButton.waitTillInVisibilityOfElement(60);
				}
				if (!productSelection.equalsIgnoreCase("Residential Non-Admitted"))
					createQuotePage.enterQuoteDetailsforPNB(testData);
				else
					createQuotePage.enterQuoteDetailsNAHO(testData);
				Assertions.passTest("Create Quote Page", "Details entered successfully");
			}
		}
		// Adding this condition to handle deductible minimum warning
		if (createQuotePage.override.checkIfElementIsPresent()
				&& createQuotePage.override.checkIfElementIsDisplayed()) {
			createQuotePage.override.scrollToElement();
			createQuotePage.override.click();
		}

		if (!testData.get("ChangePriorLossDetails").equalsIgnoreCase("")) {
			editPriorLoss.scrollToElement();
			editPriorLoss.click();
			Assertions.passTest("Prior Loss Page", "Prior Loss Page loaded successfully");
			if (!testData.get("PriorLoss1").equals(""))
				priorLossPage.selectPriorLossesInformation(testData);
			if (testData.get("PriorLossDelete") != null && !testData.get("PriorLossDelete").equals("")) {
				priorLossPage.deletePriorLoss(testData);
			}
		}
		if (!testData.get("OrderInspection").equalsIgnoreCase("")) {
			feeOnlyEndorsement.scrollToElement();
			feeOnlyEndorsement.click();
			Assertions.passTest("Override Endorsement Page", "Override Endorsement Page loaded successfully");
			OverridePremiumAndFeesPage overridePremium_andFees = new OverridePremiumAndFeesPage();
			overridePremium_andFees.enterOverrideFeesDetails(testData);
			Assertions.passTest("Override Endorsement Page", "Details entered successfully");
		}
		if (submitButton.checkIfElementIsPresent() && submitButton.checkIfElementIsDisplayed()) {
			submitButton.scrollToElement();
			submitButton.click();
			if (closeButton.checkIfElementIsPresent() && closeButton.checkIfElementIsDisplayed()) {
				scrollToTopPage();
				waitTime(3);
				closeButton.click();
			}
			ReferQuotePage referQuotePage = new ReferQuotePage();
			if (referQuotePage.referQuote.checkIfElementIsPresent()
					&& referQuotePage.referQuote.checkIfElementIsDisplayed()) {
				if (pageName.getData().contains("Refer Quote")) {

					if (!testData.get("ProducerName").equals("")) {
						referQuotePage.contactName.setData(testData.get("ProducerName"));
					}
					if (!testData.get("ProducerEmail").equals("")) {
						referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
					}
					referQuotePage.referQuote.scrollToElement();
					referQuotePage.referQuote.click();
				}
			}
		}
		// Adding this condition to handle deductible minimum warning
		if (createQuotePage.override.checkIfElementIsPresent()
				&& createQuotePage.override.checkIfElementIsDisplayed()) {
			createQuotePage.override.scrollToElement();
			createQuotePage.override.click();
		}
		if (nextButton.checkIfElementIsPresent() && nextButton.checkIfElementIsDisplayed()) {
			nextButton.scrollToElement();
			nextButton.click();
			nextButton.waitTillInVisibilityOfElement(60);
			if (pageName.getData().contains("Overrides Required")) {
				continueButton.scrollToElement();
				continueButton.click();
			}
			if (pageName.getData().contains("Review Adjustments")) {
				continueButton.scrollToElement();
				continueButton.click();
			}
		}
		if (transactionComments.checkIfElementIsPresent() && transactionComments.checkIfElementIsDisplayed()) {
			transactionComments.scrollToElement();
			transactionComments.setData(testData.get("TransactionDescription"));
		}
		if (completeButton.checkIfElementIsPresent() && completeButton.checkIfElementIsDisplayed()) {
			completeButton.scrollToElement();
			completeButton.click();
		}
		if (!testData.get("TransactionType").equals("")) {
			if (testData.get("TransactionType").contains("OOS")
					&& (testData.get("ChangeLocationOrDwellingDetails").equalsIgnoreCase("Yes")
							|| testData.get("ChangeCoverageOptions").equalsIgnoreCase("Yes"))) {
				if (pageName.getData().contains("Out Of Sequence")) {
					continueButton.scrollToElement();
					continueButton.click();
				}
			}
		}
		if (continueButton.checkIfElementIsPresent() && continueButton.checkIfElementIsDisplayed()) {
			continueButton.scrollToElement();
			continueButton.click();
		}
		scrollToBottomPage();
		waitTime(3);
		if (closeButton.checkIfElementIsPresent() && closeButton.checkIfElementIsDisplayed()) {
			closeButton.waitTillButtonIsClickable(60);
			closeButton.waitTillVisibilityOfElement(60);
			closeButton.scrollToElement();
			closeButton.click();
			closeButton.waitTillInVisibilityOfElement(60);
		}

	}
}
