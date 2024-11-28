/** Program Description: Object Locators and methods defined in Endorse producer contact page
 *  Author			   : SMNetserv
 *  Date of Creation   : 10/11/2017
**/

package com.icat.epicenter.pom;

import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class EndorseProducerContact {
	public TextFieldControl producerName;
	public TextFieldControl producerEmail;
	public ButtonControl okButton;
	public ButtonControl cancelButton;

	public EndorseProducerContact() {
		PageObject pageobject = new PageObject("EndorseProducerContact");
		producerName = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ProducerName")));
		producerEmail = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Email")));
		cancelButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_CancelButton")));
		okButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_OkButton")));
	}

	public EndorsePolicyPage enterEndorseProducerContactDetails(Map<String, String> Data) {
		if (!Data.get("ProducerName").equals("")) {
			producerName.scrollToElement();
			Assertions.addInfo("Endorse Producer Contact Page",
					"Producer Name original Value : " + producerName.getData());
			producerName.setData(Data.get("ProducerName"));
			Assertions.passTest("Endorse producer Contact Page",
					"Producer Name Latest Value : " + producerName.getData());
		}
		if (!Data.get("ProducerEmail").equals("")) {
			Assertions.addInfo("Endorse Producer Contact Page",
					"Producer Email original Value : " + producerEmail.getData());
			producerEmail.setData(Data.get("ProducerEmail"));
			Assertions.passTest("Endorse producer Contact Page",
					"Producer Email Latest Value : " + producerEmail.getData());
		}
		okButton.scrollToElement();
		okButton.click();
		return new EndorsePolicyPage();
	}
}
