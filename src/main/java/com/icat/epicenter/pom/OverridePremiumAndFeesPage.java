/** Program Description: Object Locators and methods defined in Override premium and fees page
 *  Author			   : SMNetserv
 *  Date of Creation   : 09/11/2017
**/

package com.icat.epicenter.pom;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;

import com.NetServAutomationFramework.config.EnvironmentDetails;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.CheckBoxControl;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.RadioButtonControl;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class OverridePremiumAndFeesPage extends BasePageControl {
    public TextFieldControl totalInspectionFee;
    public TextFieldControl policyFee;
    public CheckBoxControl orderInspection;
    public TextFieldControl feeOverrideJustification;
    public ButtonControl cancelButton;
    public ButtonControl overrideFeesButton;

    public TextFieldControl transactionInspectionFee;
    public TextFieldControl transactionPolicyFee;
    public CheckBoxControl transactionOrderInspection;
    public CheckBoxControl transactionWaivePremium;
    public ButtonControl saveAndClose;

    public BaseWebElementControl originalInspectionFee;
    public BaseWebElementControl inspectionFeePrevious;
    public BaseWebElementControl policyFeePrevious;
    public TextFieldControl newInspectionFee;
    public TextFieldControl newPolicyFee;
    public BaseWebElementControl originalPolicyFee;

    public TextFieldControl overridePremium;
    public BaseWebElementControl originalPremiumValue;
    public ButtonControl overridePremiumButton;
    public BaseWebElementControl premiumoverrideDropdownValue;
    public ButtonControl premiumOverrideArrow;
    public ButtonControl overridetoAdjustedValueButton;
    public BaseWebElementControl recalculatedPremiumAndFees;
    public BaseWebElementControl totalPremiumandFees;
    public TextFieldControl windPremiumOverride;
    public TextFieldControl aOPPremiumOverride;
    public TextFieldControl gLPremiumOverride;
    public BaseWebElementControl originalWindPremium;
    public BaseWebElementControl originalAopPremium;
    public BaseWebElementControl originalGlPremium;
    public BaseWebElementControl newTotalPremiumFees;

    public BaseWebElementControl eqPremiumData;
    public TextFieldControl eqPremium;
    public BaseWebElementControl overrideJustificationMsg;
    public TextFieldControl sinkholePremiumOverride;

    public RadioButtonControl NahoRadioButton;
    public TextFieldControl chooseFile;
    public TextFieldControl chooseDocument;
    public ButtonControl submit;
    public ButtonControl continueButton;
    public BaseWebElementControl currentPremium;
    public BaseWebElementControl newPremium;
    public BaseWebElementControl warningMessage;

    public OverridePremiumAndFeesPage() {
        PageObject pageobject = new PageObject("OverridePremiumAndFees");
        totalInspectionFee = new TextFieldControl(By.xpath(pageobject.getXpath("xp_TotalInspectionFees")));
        policyFee = new TextFieldControl(By.xpath(pageobject.getXpath("xp_PolicyFee")));
        orderInspection = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_OrderInspectionCheckbox")));
        feeOverrideJustification = new TextFieldControl(By.xpath(pageobject.getXpath("xp_FeeOverrideJustification")));
        cancelButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_CancelButton")));
        overrideFeesButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_OverrideFeesButton")));

        transactionInspectionFee = new TextFieldControl(By.xpath(pageobject.getXpath("xp_TransactionInspectionFee")));
        transactionPolicyFee = new TextFieldControl(By.xpath(pageobject.getXpath("xp_TransactionPolicyFee")));
        transactionOrderInspection = new CheckBoxControl(
                By.xpath(pageobject.getXpath("xp_OrderInspectionForTransaction")));
        transactionWaivePremium = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_WaivePremiumCheckbox")));
        saveAndClose = new ButtonControl(By.xpath(pageobject.getXpath("xp_SaveAndCloseButton")));

        originalInspectionFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OriginalInspectionFee")));
        inspectionFeePrevious = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_InspectionFeePrevious")));
        policyFeePrevious = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolicyFeePrevious")));
        newInspectionFee = new TextFieldControl(By.xpath(pageobject.getXpath("xp_NewInspectionFee")));
        newPolicyFee = new TextFieldControl(By.xpath(pageobject.getXpath("xp_NewPolicyFee")));
        originalPolicyFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OriginalPolicyFee")));

        overridePremium = new TextFieldControl(By.xpath(pageobject.getXpath("xp_OverridePremium")));
        originalPremiumValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OriginalPremiumValue")));
        overridePremiumButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_OverridePremiumButton")));
        premiumoverrideDropdownValue = new BaseWebElementControl(
                By.xpath(pageobject.getXpath("xp_PremiumoverrideDropdownValue")));
        premiumOverrideArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_PremiumOverrideArrow")));
        overridetoAdjustedValueButton = new ButtonControl(
                By.xpath(pageobject.getXpath("xp_OverridetoAdjustedValueButton")));
        recalculatedPremiumAndFees = new BaseWebElementControl(
                By.xpath(pageobject.getXpath("xp_RecalculatedPremiumAndFees")));
        totalPremiumandFees = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TotalPremiumandFees")));
        windPremiumOverride = new TextFieldControl(By.xpath(pageobject.getXpath("xp_WindPremiumOverride")));
        aOPPremiumOverride = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AOPPremiumOverride")));
        gLPremiumOverride = new TextFieldControl(By.xpath(pageobject.getXpath("xp_GLPremiumOverride")));
        originalWindPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OriginalWindPremium")));
        originalAopPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OriginalAopPremium")));
        originalGlPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OriginalGlPremium")));
        newTotalPremiumFees = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NewTotalPremiumFees")));

        sinkholePremiumOverride = new TextFieldControl(By.xpath(pageobject.getXpath("xp_SinkPremiumOverride")));
        eqPremiumData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EQPremiumData")));
        eqPremium = new TextFieldControl(By.xpath(pageobject.getXpath("xp_EQPremium")));
        overrideJustificationMsg = new BaseWebElementControl(
                By.xpath(pageobject.getXpath("xp_OverrideJustificationMsg")));

        NahoRadioButton = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_NahoRadioButton")));
        chooseDocument = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ChooseDocument")));
        chooseFile = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ChooseFile")));
        submit = new ButtonControl(By.xpath(pageobject.getXpath("xp_Submit")));
        continueButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ContinueButton")));
        currentPremium = new BaseWebElementControl(
                By.xpath(pageobject.getXpath("xp_CurrentPremium")));
        newPremium = new BaseWebElementControl(
                By.xpath(pageobject.getXpath("xp_NewPremium")));
        warningMessage = new BaseWebElementControl(
                By.xpath(pageobject.getXpath("xp_WarningMessage")));


    }

    public void enterOverrideFeesDetails(Map<String, String> testData) {
        Assertions.addInfo("Inspection Order Original value : " + 0, "");
        if (testData.get("OrderInspection").equalsIgnoreCase("yes")) {
            List<String> orderInspectionApplicability = Arrays
                    .asList(testData.get("OrderInspectionApplicability").split(","));
            for (int i = 0; i < orderInspectionApplicability.size(); i++) {
                String locationcount = orderInspectionApplicability.get(i).substring(1, 2);
                String dwellingcount = orderInspectionApplicability.get(i).substring(3, 4);
                orderInspection.formatDynamicPath(locationcount, dwellingcount).scrollToElement();
                orderInspection.formatDynamicPath(locationcount, dwellingcount).select();
                Assertions.passTest("Override Premium And Fees Page", "Inspection Order Latest value : " + 1);
            }
        }
        if (testData.get("PolicyFee") != null) {
            if (!testData.get("PolicyFee").equalsIgnoreCase("")) {
                policyFee.scrollToElement();
                policyFee.setData(testData.get("PolicyFee"));
            }
        }
        if (testData.get("InspectionFee") != null) {
            if (!testData.get("InspectionFee").equalsIgnoreCase("")) {
                transactionInspectionFee.scrollToElement();
                transactionInspectionFee.setData(testData.get("InspectionFee"));
            }
        }
        if (testData.get("PolicyFeeOverride") != null) {
            if (!testData.get("PolicyFeeOverride").equalsIgnoreCase("")) {
                policyFee.scrollToElement();
                policyFee.setData(testData.get("PolicyFeeOverride"));
            }
        }
        if (testData.get("InspectionFeeOverride") != null) {
            if (!testData.get("InspectionFeeOverride").equalsIgnoreCase("")) {
                transactionInspectionFee.scrollToElement();
                transactionInspectionFee.setData(testData.get("InspectionFeeOverride"));
            }
        }
        saveAndClose.scrollToElement();
        saveAndClose.click();
        saveAndClose.waitTillInVisibilityOfElement(60);
    }

    public void enterOverrideFeesCommercial(Map<String, String> testData) {

        if (testData.get("TransactionPolicyfee") != null) {
            if (!testData.get("TransactionPolicyfee").equalsIgnoreCase("")) {
                policyFee.scrollToElement();
                policyFee.setData(testData.get("TransactionPolicyfee"));
            }
        }
        if (testData.get("TransactionInspectionFee") != null) {
            if (!testData.get("TransactionInspectionFee").equalsIgnoreCase("")) {
                transactionInspectionFee.scrollToElement();
                transactionInspectionFee.setData(testData.get("TransactionInspectionFee"));
            }
        }
        saveAndClose.scrollToElement();
        saveAndClose.click();
        saveAndClose.waitTillInVisibilityOfElement(60);
    }

    //TF 10/31/24 - the ids for the fees are different between new business and endorsements.  try to set either with this method
    public void overridePremiumAndFeesDetails(Map<String, String> testData) {

        if (testData.get("TransactionPolicyfee") != null) {
            if (!testData.get("TransactionPolicyfee").equalsIgnoreCase("")) {
                if (newPolicyFee.checkIfElementIsPresent() && newPolicyFee.checkIfElementIsEnabled()) {
                    newPolicyFee.scrollToElement();
                    newPolicyFee.setData(testData.get("TransactionPolicyfee"));
                } else if (transactionPolicyFee.checkIfElementIsPresent() && transactionPolicyFee.checkIfElementIsEnabled()) {
                    transactionPolicyFee.scrollToElement();
                    transactionPolicyFee.setData(testData.get("TransactionPolicyfee"));
                }
            }
        }
        if (testData.get("TransactionInspectionFee") != null) {
            if (!testData.get("TransactionInspectionFee").equalsIgnoreCase("")) {
                if (newInspectionFee.checkIfElementIsPresent() && newInspectionFee.checkIfElementIsEnabled()) {
                    newInspectionFee.scrollToElement();
                    newInspectionFee.setData(testData.get("TransactionInspectionFee"));
                } else if (transactionInspectionFee.checkIfElementIsPresent() && transactionInspectionFee.checkIfElementIsEnabled()) {
                    transactionInspectionFee.scrollToElement();
                    transactionInspectionFee.setData(testData.get("TransactionInspectionFee"));
                }
            }
        }

        if (testData.get("OverridePremium") != null) {
            if (!testData.get("OverridePremium").equalsIgnoreCase("")) {
                overridePremium.scrollToElement();
                overridePremium.setData(testData.get("OverridePremium"));
            }
        }

        if (testData.get("WaivePremium") != null) {
            if (testData.get("WaivePremium").equalsIgnoreCase("yes")) {
                System.out.println("Waiving premium");
                transactionWaivePremium.scrollToElement();
                transactionWaivePremium.select();
            }
        }
        if (testData.get("OrderInspection") != null && testData.get("OrderInspection").equalsIgnoreCase("yes")) {
            for (int i = 0; i <= 10; i++) {
                if (orderInspection.formatDynamicPath(i).checkIfElementIsPresent() && orderInspection.formatDynamicPath(i).checkIfElementIsDisplayed()) {
                    orderInspection.formatDynamicPath(i).scrollToElement();
                    orderInspection.formatDynamicPath(i).select();
                }
            }
        }

        if (feeOverrideJustification.checkIfElementIsPresent() && feeOverrideJustification.checkIfElementIsDisplayed()) {
            feeOverrideJustification.scrollToElement();
            feeOverrideJustification.setData("Aftershock Testing");
        }

        if (saveAndClose.checkIfElementIsPresent() && saveAndClose.checkIfElementIsDisplayed()) {
            saveAndClose.scrollToElement();
            saveAndClose.click();
            saveAndClose.waitTillInVisibilityOfElement(60);
        } else if (overridePremiumButton.checkIfElementIsPresent() && overridePremiumButton.checkIfElementIsDisplayed()) {
            overridePremiumButton.scrollToElement();
            overridePremiumButton.click();
        }

        // TF 02/23/21 - need to accept the adjusted value if it's rounded
        if (overridetoAdjustedValueButton.checkIfElementIsPresent()) {
            overridetoAdjustedValueButton.click();
        }
    }

    public void enterFeesDetailsNAHO(Map<String, String> testData) {
        if (testData.get("WindPremiumOverride") != null && !testData.get("WindPremiumOverride").equals("")) {
            windPremiumOverride.scrollToElement();
            windPremiumOverride.setData(testData.get("WindPremiumOverride"));
            Assertions.passTest("Override Premium and Fees Page",
                    "Overridden Wind Premium : " + "$" + windPremiumOverride.getData());
        }
        if (testData.get("AOPPremiumOverride") != null && !testData.get("AOPPremiumOverride").equals("")) {
            aOPPremiumOverride.scrollToElement();
            aOPPremiumOverride.setData(testData.get("AOPPremiumOverride"));
            Assertions.passTest("Override Premium and Fees Page",
                    "Overridden AOP Premium : " + "$" + aOPPremiumOverride.getData());
        }
        if (testData.get("LiabilityPremiumOverride") != null && !testData.get("LiabilityPremiumOverride").equals("")) {
            gLPremiumOverride.scrollToElement();
            gLPremiumOverride.setData(testData.get("LiabilityPremiumOverride"));
            Assertions.passTest("Override Premium and Fees Page",
                    "Overridden Liability Premium : " + "$" + gLPremiumOverride.getData());
        }

        if (testData.get("EQPremiumOverride") != null && !testData.get("EQPremiumOverride").equals("")) {
            eqPremium.scrollToElement();
            eqPremium.setData(testData.get("EQPremiumOverride"));
            Assertions.passTest("Override Premium and Fees Page", "Overridden EQ Premium : " + eqPremium.getData());
        }
        if (testData.get("InspectionFeeOverride") != null && !testData.get("InspectionFeeOverride").equals("")) {
            totalInspectionFee.scrollToElement();
            totalInspectionFee.setData(testData.get("InspectionFeeOverride"));
            Assertions.passTest("Override Premium and Fees Page",
                    "Overridden Inspection Fee : " + "$" + totalInspectionFee.getData());
        }

        if (testData.get("PolicyFeeOverride") != null && !testData.get("PolicyFeeOverride").equals("")) {
            policyFee.scrollToElement();
            policyFee.setData(testData.get("PolicyFeeOverride"));
            Assertions.passTest("Override Premium and Fees Page",
                    "Overridden Policy Fee : " + "$" + policyFee.getData());
        }

        if (testData.get("FeeOverrideJustification") != null && !testData.get("FeeOverrideJustification").equals("")) {
            feeOverrideJustification.scrollToElement();
            feeOverrideJustification.setData(testData.get("FeeOverrideJustification"));
        }

        if (testData.get("OverridePremium") != null && !testData.get("OverridePremium").equals("")) {
            overridePremium.scrollToElement();
            overridePremium.setData(testData.get("OverridePremium"));
        }

        overrideFeesButton.scrollToElement();
        overrideFeesButton.click();
    }

    public void csvFileUpload(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            Assertions.failTest("Upload File", "Filename is blank");
        }
        String uploadFileDir = EnvironmentDetails.getEnvironmentDetails().getString("test.csv.uploadpath");
        waitTime(5);
        if (chooseDocument.checkIfElementIsPresent() && chooseDocument.checkIfElementIsDisplayed()) {
            chooseDocument.setData(new File(uploadFileDir + fileName).getAbsolutePath());
            waitTime(2);// Adding wait time to load the element
            System.out.println("Choose document");
        } else {
            waitTime(5);
            chooseFile.setData(new File(uploadFileDir + fileName).getAbsolutePath());
            waitTime(2);// Adding wait time to load the element

        }
    }
}
