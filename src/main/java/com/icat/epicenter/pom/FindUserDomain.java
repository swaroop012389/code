/** Program Description: Find User/Domain Management
 *  Author			   : SMNetserv
 *  Date of Creation   : 05/27/2017
 **/

package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.CheckBoxControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class FindUserDomain extends BasePageControl {

	public ButtonControl findFilterSelect;
	public ButtonControl findFilterSelectOption;

	public TextFieldControl userLastName;
	public TextFieldControl userFirstName;
	public TextFieldControl findUserName;
	public TextFieldControl agentNumber;

	public CheckBoxControl typeUSM;
	public ButtonControl findButton;

	public HyperLink findUserLink;

	public ButtonControl user;
	public ButtonControl actionRights;
	public ButtonControl domainRights;
	public ButtonControl relationShips;

	public BaseWebElementControl nAHORenewals;
	public CheckBoxControl nAHORenewalsCheckBox;

	public ButtonControl saveUserButton;
	public ButtonControl cancelButton;

	public TextFieldControl producerNumber;
	public ButtonControl emailAddress;

	public FindUserDomain() {
		PageObject pageobject = new PageObject("FindUserDomain");
		findFilterSelect = new ButtonControl(By.xpath(pageobject.getXpath("xp_FindFilterSelect")));
		findFilterSelectOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_FindFilterSelectOption")));

		userLastName = new TextFieldControl(By.xpath(pageobject.getXpath("xp_UserLastName")));
		userFirstName = new TextFieldControl(By.xpath(pageobject.getXpath("xp_UserFirstName")));
		findUserName = new TextFieldControl(By.xpath(pageobject.getXpath("xp_FindUserName")));
		agentNumber = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AgentNumber")));

		typeUSM = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_TypeUSM")));
		findButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_FindButton")));

		findUserLink = new HyperLink(By.xpath(pageobject.getXpath("xp_FindUser")));

		user = new ButtonControl(By.xpath(pageobject.getXpath("xp_User")));
		actionRights = new ButtonControl(By.xpath(pageobject.getXpath("xp_ActionRights")));
		domainRights = new ButtonControl(By.xpath(pageobject.getXpath("xp_DomainRights")));
		relationShips = new ButtonControl(By.xpath(pageobject.getXpath("xp_RelationShips")));

		nAHORenewals = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NAHORenewals")));
		nAHORenewalsCheckBox = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_NAHORenewalsCheckBox")));

		saveUserButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_SaveUserButton")));
		cancelButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_CancelButton")));

		producerNumber = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ProducerNumber")));
		emailAddress = new ButtonControl(By.xpath(pageobject.getXpath("xp_EmailAddress")));
	}

}
