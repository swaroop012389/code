/** Program Description: Object Locators and methods defined in Edit inspection contact page
 *  Author			   : SMNetserv
 *  Date of Creation   : 03/11/2017
**/

package com.icat.epicenter.pom;

import java.util.HashMap;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.CheckBoxControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.RadioButtonControl;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class EditInspectionContactPage extends BasePageControl {
	public TextFieldControl inspectionName;
	public TextFieldControl phoneNumberAreaCode;
	public TextFieldControl phoneNumberPrefix;
	public TextFieldControl phoneNumberEnd;
	public ButtonControl cancel;
	public ButtonControl update;
	public BaseWebElementControl deleteInspection;
	public RadioButtonControl assignInspContactByPolicy;
	public RadioButtonControl assignInspContactByLocation;
	public CheckBoxControl locationInspectionBox;
	public ButtonControl addInspectionSymbol;

	public HyperLink deselectAll;
	public HyperLink selectAll;

	public EditInspectionContactPage() {
		PageObject pageobject = new PageObject("EditInspectionContact");
		inspectionName = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Name")));
		phoneNumberAreaCode = new TextFieldControl(By.xpath(pageobject.getXpath("xp_PhoneNumberAreaCode")));
		phoneNumberPrefix = new TextFieldControl(By.xpath(pageobject.getXpath("xp_PhoneNumberPrefix")));
		phoneNumberEnd = new TextFieldControl(By.xpath(pageobject.getXpath("xp_PhoneNumberEnd")));
		cancel = new ButtonControl(By.xpath(pageobject.getXpath("xp_Cancel")));
		update = new ButtonControl(By.xpath(pageobject.getXpath("xp_Update")));
		deleteInspection = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Delete")));

		assignInspContactByPolicy = new RadioButtonControl(
				By.xpath(pageobject.getXpath("xp_AssignInspContact_ToEntirePolicy")));
		assignInspContactByLocation = new RadioButtonControl(
				By.xpath(pageobject.getXpath("xp_AssignInspContact_ByLocation")));
		locationInspectionBox = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_locationinspectionbox")));
		addInspectionSymbol = new ButtonControl(By.xpath(pageobject.getXpath("xp_AddInspection")));

		deselectAll = new HyperLink(By.xpath(pageobject.getXpath("xp_DeselectAll")));
		selectAll = new HyperLink(By.xpath(pageobject.getXpath("xp_SelectAll")));
	}

	public void enterInspectionContactDetails(HashMap<String, String> Data) {
		if (Data.get("InspectionAssign").equalsIgnoreCase("By Location")) {
			assignInspContactByLocation.scrollToElement();
			assignInspContactByLocation.click();
			inspectionName.scrollToElement();
			inspectionName.setData(Data.get("InspectionContact"));
			phoneNumberAreaCode.setData(Data.get("InspectionAreaCode"));
			phoneNumberPrefix.setData(Data.get("InspectionPrefix"));
			phoneNumberEnd.setData(Data.get("InspectionNumber"));
			deselectAll.waitTillVisibilityOfElement(60);
			String[] inspectionApplicability = Data.get("InspectionAssignApplicability").split(",");
			for (int i = 1; i <= inspectionApplicability.length; i++) {
				locationInspectionBox.formatDynamicPath(i).scrollToElement();
				locationInspectionBox.formatDynamicPath(i).select();
			}
		} else if (Data.get("InspectionAssign").equalsIgnoreCase("By Policy")) {
			assignInspContactByPolicy.scrollToElement();
			assignInspContactByPolicy.click();
			inspectionName.scrollToElement();
			inspectionName.setData(Data.get("InspectionContact"));
			phoneNumberAreaCode.setData(Data.get("InspectionAreaCode"));
			phoneNumberPrefix.setData(Data.get("InspectionPrefix"));
			phoneNumberEnd.setData(Data.get("InspectionNumber"));
		}
		update.scrollToElement();
		update.click();
		update.waitTillInVisibilityOfElement(60);
	}
}
