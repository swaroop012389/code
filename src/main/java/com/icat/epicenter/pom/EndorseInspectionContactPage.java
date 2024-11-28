/** Program Description: Object Locators and methods defined in Endorse inspection contact page
 *  Author			   : SMNetserv
 *  Date of Creation   : 07/11/2017
**/

package com.icat.epicenter.pom;

import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextAreaControl;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class EndorseInspectionContactPage {
	public TextFieldControl inspectionName;
	public TextFieldControl inspectionAreaCode;
	public TextFieldControl inspectionPrefix;
	public TextFieldControl inspectionNumberEnd;
	public HyperLink deleteInspectionContact;
	public ButtonControl cancelButton;
	public ButtonControl okButton;

	public EndorseInspectionContactPage() {
		PageObject pageObject = new PageObject("EndorseInspectionContact");
		inspectionName = new TextFieldControl(By.xpath(pageObject.getXpath("xp_InspectionContactName")));
		inspectionAreaCode = new TextAreaControl(By.xpath(pageObject.getXpath("xp_PhoneNumberAreaCode")));
		inspectionPrefix = new TextFieldControl(By.xpath(pageObject.getXpath("xp_PhoneNumberPrefix")));
		inspectionNumberEnd = new TextFieldControl(By.xpath(pageObject.getXpath("xp_PhoneNumberEnd")));
		deleteInspectionContact = new HyperLink(By.xpath(pageObject.getXpath("xp_DeleteInspectionContact")));
		cancelButton = new ButtonControl(By.xpath(pageObject.getXpath("xp_Cancelbutton")));
		okButton = new ButtonControl(By.xpath(pageObject.getXpath("xp_OkButton")));
	}

	public EndorsePolicyPage enterInspectionContactPB(Map<String, String> Data) {
		if (!Data.get("InspectionContact").equals("")) {
			inspectionName.scrollToElement();
			Assertions.addInfo("Endorse Inspection Contact Page",
					"Inspection Name original Value : " + inspectionName.getData());
			inspectionName.setData(Data.get("InspectionContact"));
			Assertions.passTest("Endorse Inspection Contact Page",
					"Inspection Name Latest Value : " + inspectionName.getData());
		}
		if (!Data.get("InspectionAreaCode").equals("")) {
			inspectionAreaCode.scrollToElement();
			String beforeAreaCode = inspectionAreaCode.getData();
			inspectionAreaCode.setData(Data.get("InspectionAreaCode"));
			String afterAreaCode = inspectionAreaCode.getData();
			String beforePrefix = inspectionPrefix.getData();
			inspectionPrefix.setData(Data.get("InspectionPrefix"));
			String afterPrefix = inspectionPrefix.getData();
			String beforePhonenoEnd = inspectionNumberEnd.getData();
			inspectionNumberEnd.setData(Data.get("InspectionNumber"));
			String afterPhonenoEnd = inspectionNumberEnd.getData();
			Assertions.addInfo("Endorse Inspection Contact Page", "Inspection phone Number original Value : "
					+ beforeAreaCode + "-" + beforePrefix + "-" + beforePhonenoEnd);
			Assertions.passTest("Endorse Inspection Contact Page", "Inspection phone Number Latest Value : "
					+ afterAreaCode + "-" + afterPrefix + "-" + afterPhonenoEnd);
		}
		okButton.scrollToElement();
		okButton.click();
		return new EndorsePolicyPage();
	}
}
