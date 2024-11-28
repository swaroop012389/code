/** Program Description: Object Locators and methods defined in Broker of record page
 *  Author			   : SMNetserv
 *  Date of Creation   : 29/10/2017
**/

package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.RadioButtonControl;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class BrokerOfRecordPage extends BasePageControl {
	public TextFieldControl newProducerNumber;
	public ButtonControl borStatusArrow;
	public HyperLink borStatusOption;
	public ButtonControl cancel;
	public ButtonControl processBOR;
	public ButtonControl closeBORPage;
	public TextFieldControl insuredEmail;
	public RadioButtonControl payplanRadioBtn;
	public BaseWebElementControl producerAuthorizationMsg;
	public BaseWebElementControl producerDetails;
	public BaseWebElementControl emailWarningMsg;
	public BaseWebElementControl paymentWarningMsg;
	public BaseWebElementControl oldProducerNumber;
	public BaseWebElementControl hardStopMessage;
	public ButtonControl viewIncumbentProducerLetterBtn;
	public ButtonControl viewNewProducerLetterBtn;
	public BaseWebElementControl updatedProducerNumber;
	public BaseWebElementControl emailSentSuccessfulMsg;





	public BrokerOfRecordPage() {
		PageObject pageobject = new PageObject("BrokerOfRecord");
		newProducerNumber = new TextFieldControl(By.xpath(pageobject.getXpath("xp_NewProducerNumber")));
		borStatusArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_BORStatusArrow")));
		borStatusOption = new HyperLink(By.xpath(pageobject.getXpath("xp_BORstatusOption")));
		cancel = new ButtonControl(By.xpath(pageobject.getXpath("xp_Cancel")));
		processBOR = new ButtonControl(By.xpath(pageobject.getXpath("xp_ProcessBOR")));
		closeBORPage = new ButtonControl(By.xpath(pageobject.getXpath("xp_CloseBOR")));
		insuredEmail = new TextFieldControl(By.xpath(pageobject.getXpath("xp_InsuredEmail")));
		payplanRadioBtn = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_PayplanRadioBtn")));
		producerAuthorizationMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ProducerAuthorizationMsg")));
		producerDetails = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ProducerDetails")));
		emailWarningMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EmailWarningMsg")));
		paymentWarningMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PaymentWarningMsg")));
		oldProducerNumber = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OldProducerNumber")));
		hardStopMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_HardStopMessage")));
		viewIncumbentProducerLetterBtn = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_ViewIncumbentProducerLetterBtn")));
		viewNewProducerLetterBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_ViewNewProducerLetterBtn")));
		updatedProducerNumber = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_UpdatedProducerNumber")));
		emailSentSuccessfulMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EmailSentSuccessfulMsg")));

	}
}
