/** Program Description: Object Locators and methods defined in Account Details page
 *  Author			   : John
 *  Date of Creation   : 10/22/2019
**/

package com.icat.epicenter.pom;

import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class ExpaccInfoPage {
	public TextFieldControl policyNumber;
	public TextFieldControl evalutaionPeriod;
	public TextFieldControl createDate;
	public TextFieldControl lastInspectionDate;
	public TextFieldControl mpsPolicy;
	public TextFieldControl rmsAI;
	public TextFieldControl imamAA;
	public TextFieldControl rmsEL;
	public TextFieldControl imamEL;
	public TextFieldControl rdsAmtNorth;
	public TextFieldControl rdsAmtCarol;
	public TextFieldControl rdsAmtMaimi;
	public TextFieldControl rdsAmtPinellas;
	public TextFieldControl rdsAmtMissisipi;
	public TextFieldControl rdsAmtTexas;
	public TextFieldControl rdsAmtLosAngeles;
	public TextFieldControl rdsAmtSanFran;
	public TextFieldControl rdsAmtSeattle;
	public TextFieldControl rdsAmtMadrid;
	public ButtonControl submit;

	public ExpaccInfoPage() {
		PageObject pageobject = new PageObject("ExpaccInfo");
		policyNumber = new TextFieldControl(By.xpath(pageobject.getXpath("xp_PolicyNumber")));
		evalutaionPeriod = new TextFieldControl(By.xpath(pageobject.getXpath("xp_EvaluationPeriod")));
		createDate = new TextFieldControl(By.xpath(pageobject.getXpath("xp_CreateDate")));
		lastInspectionDate = new TextFieldControl(By.xpath(pageobject.getXpath("xp_LastInspectionDate")));
		mpsPolicy = new TextFieldControl(By.xpath(pageobject.getXpath("xp_MPSPolicy")));
		rmsAI = new TextFieldControl(By.xpath(pageobject.getXpath("xp_RMSAI")));
		imamAA = new TextFieldControl(By.xpath(pageobject.getXpath("xp_IMAMAA")));
		rmsEL = new TextFieldControl(By.xpath(pageobject.getXpath("xp_RMSEL")));
		imamEL = new TextFieldControl(By.xpath(pageobject.getXpath("xp_IMAMEL")));
		rdsAmtNorth = new TextFieldControl(By.xpath(pageobject.getXpath("xp_RDSAmtNorth")));
		rdsAmtCarol = new TextFieldControl(By.xpath(pageobject.getXpath("xp_RDSAmtCarol")));
		rdsAmtMaimi = new TextFieldControl(By.xpath(pageobject.getXpath("xp_RDSAmtMaimi")));
		rdsAmtPinellas = new TextFieldControl(By.xpath(pageobject.getXpath("xp_RDSAmtPinellas")));
		rdsAmtMissisipi = new TextFieldControl(By.xpath(pageobject.getXpath("xp_RDSAmtMissisipi")));
		rdsAmtTexas = new TextFieldControl(By.xpath(pageobject.getXpath("xp_RDSAmtTexas")));
		rdsAmtLosAngeles = new TextFieldControl(By.xpath(pageobject.getXpath("xp_RDSAmtLosAngeles")));
		rdsAmtSanFran = new TextFieldControl(By.xpath(pageobject.getXpath("xp_RDSAmtSanFran")));
		rdsAmtSeattle = new TextFieldControl(By.xpath(pageobject.getXpath("xp_RDSAmtSeattle")));
		rdsAmtMadrid = new TextFieldControl(By.xpath(pageobject.getXpath("xp_RDSAmtMadrid")));
		submit = new ButtonControl(By.xpath(pageobject.getXpath("xp_Submit")));
	}

	public void enterExpaccInfo(Map<String, String> data, String PolicyNumber) {
		policyNumber.setData(PolicyNumber);

		if (data.get("EvaluationPeriod") != null && !data.get("EvaluationPeriod").equals("")
				&& evalutaionPeriod.checkIfElementIsPresent() && evalutaionPeriod.checkIfElementIsDisplayed()) {
			evalutaionPeriod.scrollToElement();
			evalutaionPeriod.setData(data.get("EvaluationPeriod"));
		}
		if (data.get("EpicenterCreateDate") != null && !data.get("EpicenterCreateDate").equals("")
				&& createDate.checkIfElementIsPresent() && createDate.checkIfElementIsDisplayed()) {
			createDate.scrollToElement();
			createDate.setData(data.get("EpicenterCreateDate"));
		}
		if (data.get("LastInspectionDate") != null && !data.get("LastInspectionDate").equals("")
				&& lastInspectionDate.checkIfElementIsPresent() && lastInspectionDate.checkIfElementIsDisplayed()) {
			lastInspectionDate.scrollToElement();
			lastInspectionDate.setData(data.get("LastInspectionDate"));
		}
		if (data.get("MpsPolicyTxnId") != null && !data.get("MpsPolicyTxnId").equals("")
				&& mpsPolicy.checkIfElementIsPresent() && mpsPolicy.checkIfElementIsDisplayed()) {
			mpsPolicy.scrollToElement();
			mpsPolicy.setData(data.get("MpsPolicyTxnId"));
		}
		if (data.get("RmsAalAmount") != null && !data.get("RmsAalAmount").equals("") && rmsAI.checkIfElementIsPresent()
				&& rmsAI.checkIfElementIsDisplayed()) {
			rmsAI.scrollToElement();
			rmsAI.setData(data.get("RmsAalAmount"));
		}
		if (data.get("ImamAalAmount") != null && !data.get("ImamAalAmount").equals("")
				&& imamAA.checkIfElementIsPresent() && imamAA.checkIfElementIsDisplayed()) {
			imamAA.scrollToElement();
			imamAA.setData(data.get("ImamAalAmount"));
		}
		if (data.get("RmsElrPercent") != null && !data.get("RmsElrPercent").equals("")
				&& rmsEL.checkIfElementIsPresent() && rmsEL.checkIfElementIsDisplayed()) {
			rmsEL.scrollToElement();
			rmsEL.setData(data.get("RmsElrPercent"));
		}
		if (data.get("ImamElrPercent") != null && !data.get("ImamElrPercent").equals("")
				&& imamEL.checkIfElementIsPresent() && imamEL.checkIfElementIsDisplayed()) {
			imamEL.scrollToElement();
			imamEL.setData(data.get("ImamElrPercent"));
		}
		if (data.get("RdsAmountHuNortheast") != null && !data.get("RdsAmountHuNortheast").equals("")
				&& rdsAmtNorth.checkIfElementIsPresent() && rdsAmtNorth.checkIfElementIsDisplayed()) {
			rdsAmtNorth.scrollToElement();
			rdsAmtNorth.setData(data.get("RdsAmountHuNortheast"));
		}
		if (data.get("RdsAmountHuNortheast") != null && !data.get("RdsAmountHuCarolinas").equals("")
				&& rdsAmtCarol.checkIfElementIsPresent() && rdsAmtCarol.checkIfElementIsDisplayed()) {
			rdsAmtCarol.scrollToElement();
			rdsAmtCarol.setData(data.get("RdsAmountHuCarolinas"));
		}
		if (data.get("RdsAmountHuMiami") != null && !data.get("RdsAmountHuMiami").equals("")
				&& rdsAmtMaimi.checkIfElementIsPresent() && rdsAmtMaimi.checkIfElementIsDisplayed()) {
			rdsAmtMaimi.scrollToElement();
			rdsAmtMaimi.setData(data.get("RdsAmountHuMiami"));
		}
		if (data.get("RdsAmountHuMiami") != null && !data.get("RdsAmountHuPinellas").equals("")
				&& rdsAmtPinellas.checkIfElementIsPresent() && rdsAmtPinellas.checkIfElementIsDisplayed()) {
			rdsAmtPinellas.scrollToElement();
			rdsAmtPinellas.setData(data.get("RdsAmountHuPinellas"));
		}
		if (data.get("RdsAmountHuMississippi") != null && !data.get("RdsAmountHuMississippi").equals("")
				&& rdsAmtMissisipi.checkIfElementIsPresent() && rdsAmtMissisipi.checkIfElementIsDisplayed()) {
			rdsAmtMissisipi.scrollToElement();
			rdsAmtMissisipi.setData(data.get("RdsAmountHuMississippi"));
		}
		if (data.get("RdsAmountHuTexas") != null && !data.get("RdsAmountHuTexas").equals("")
				&& rdsAmtTexas.checkIfElementIsPresent() && rdsAmtTexas.checkIfElementIsDisplayed()) {
			rdsAmtTexas.scrollToElement();
			rdsAmtTexas.setData(data.get("RdsAmountHuTexas"));
		}
		if (data.get("RdsAmountEqLosAngeles") != null && !data.get("RdsAmountEqLosAngeles").equals("")
				&& rdsAmtLosAngeles.checkIfElementIsPresent() && rdsAmtLosAngeles.checkIfElementIsDisplayed()) {
			rdsAmtLosAngeles.scrollToElement();
			rdsAmtLosAngeles.setData(data.get("RdsAmountEqLosAngeles"));
		}
		if (data.get("RdsAmountEqSanFrancisco") != null && !data.get("RdsAmountEqSanFrancisco").equals("")
				&& rdsAmtSanFran.checkIfElementIsPresent() && rdsAmtSanFran.checkIfElementIsDisplayed()) {
			rdsAmtSanFran.scrollToElement();
			rdsAmtSanFran.setData(data.get("RdsAmountEqSanFrancisco"));
		}
		if (data.get("RdsAmountEqSeattle") != null && !data.get("RdsAmountEqSeattle").equals("")
				&& rdsAmtSeattle.checkIfElementIsPresent() && rdsAmtSeattle.checkIfElementIsDisplayed()) {
			rdsAmtSeattle.scrollToElement();
			rdsAmtSeattle.setData(data.get("RdsAmountEqSeattle"));
		}
		if (data.get("RdsAmountEqNewMadrid") != null && !data.get("RdsAmountEqNewMadrid").equals("")
				&& rdsAmtMadrid.checkIfElementIsPresent() && rdsAmtMadrid.checkIfElementIsDisplayed()) {
			rdsAmtMadrid.scrollToElement();
			rdsAmtMadrid.setData(data.get("RdsAmountEqNewMadrid"));
		}
		submit.scrollToElement();
		submit.click();
	}
}
