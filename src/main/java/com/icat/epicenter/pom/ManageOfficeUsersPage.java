/** Program Description: Object Locators and methods defined in Manage Office Users Page
 *  Author			   : SMNetserv
 *  Date of Creation   : 03/22/2024
**/

package com.icat.epicenter.pom;

import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class ManageOfficeUsersPage extends BasePageControl {

	public ButtonControl inviteNewUser;
	public TextFieldControl inviteeFirstName;
	public TextFieldControl inviteeLastName;
	public TextFieldControl inviteeEmailAddress;
	public ButtonControl sendInvite;
	public ButtonControl cancelButton;
	public BaseWebElementControl globalMessage;
	public ButtonControl cancelReInvite;
	public ButtonControl confirmButton;

	public ManageOfficeUsersPage() {
		PageObject pageobject = new PageObject("ManageOfficeUsersPage");
		inviteNewUser = new ButtonControl(By.xpath(pageobject.getXpath("xp_InviteNewUser")));
		inviteeFirstName = new TextFieldControl(By.xpath(pageobject.getXpath("xp_InviteeFirstName")));
		inviteeLastName = new TextFieldControl(By.xpath(pageobject.getXpath("xp_InviteeLastName")));
		inviteeEmailAddress = new TextFieldControl(By.xpath(pageobject.getXpath("xp_InviteeEmailAddress")));
		sendInvite = new ButtonControl(By.xpath(pageobject.getXpath("xp_SendInvite")));
		cancelButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_CancelButton")));
		globalMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GlobalMessage")));
		cancelReInvite = new ButtonControl(By.xpath(pageobject.getXpath("xp_CancelReInvite")));
		confirmButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_Confirm")));

	}

	public void enterInviteNewUser(Map<String, String> data) {
		inviteeFirstName.scrollToElement();
		inviteeFirstName.appendData(data.get("InviteeFirstName"));
		inviteeLastName.scrollToElement();
		inviteeLastName.appendData(data.get("InviteeLastName"));
		inviteeEmailAddress.scrollToElement();
		inviteeEmailAddress.appendData(data.get("InviteeEmailAddress"));
		sendInvite.scrollToElement();
		sendInvite.click();
		waitTime(3);
	}
}
