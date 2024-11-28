/** Program Description: Object Locators and methods defined in Referral page
 *  Author			   : SMNetserv
 *  Date of Creation   : 03/11/2017
 **/

package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.DropDownControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextAreaControl;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class ReferralPage extends BasePageControl {
	public ButtonControl reAssignButton;
	public ButtonControl approveOrDeclineRequest;
	public ButtonControl displayQuote;
	public ButtonControl close;

	public DropDownControl newAssignedUser;
	public TextFieldControl reAssignComments;
	public ButtonControl reAssignActivity;
	public ButtonControl pickUp;

	public BaseWebElementControl assignmentRow;

	public BaseWebElementControl quoteNumber2;
	public ButtonControl approveRequest;
	public BaseWebElementControl originalPremiumData;
	public BaseWebElementControl requestedPremiumData;
	public BaseWebElementControl referralCompleteMsg;
	public BaseWebElementControl referralReasonPriorLoss;
	public BaseWebElementControl pageName;
	public BaseWebElementControl referalReceivedMsg;
	public TextAreaControl internalComments;
	public TextAreaControl externalComments;

	public BaseWebElementControl referralReason;
	public BaseWebElementControl googleMaps;
	public ButtonControl approveOrDeclineRequest_Q2;
	RequestBindPage requestBindPage;
	public BaseWebElementControl assignedUser;
	public BaseWebElementControl producerCommentsProducerSection;
	public HyperLink noteBarLink;
	public BaseWebElementControl noteBarMessage;
	public ButtonControl viewPolicy;
	public BaseWebElementControl producerQuoteStatus;
	public BaseWebElementControl summaryDetails;

	public ReferralPage() {
		PageObject pageobject = new PageObject("Referral");
		reAssignButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_Reassign")));
		approveOrDeclineRequest = new ButtonControl(By.xpath(pageobject.getXpath("xp_DeclineRequest_Q1")));
		displayQuote = new ButtonControl(By.xpath(pageobject.getXpath("xp_DisplayQuote")));
		close = new ButtonControl(By.xpath(pageobject.getXpath("xp_Close")));

		newAssignedUser = new DropDownControl(By.xpath(pageobject.getXpath("xp_NewAssignedUser")));
		reAssignComments = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Comments")));
		reAssignActivity = new ButtonControl(By.xpath(pageobject.getXpath("xp_ReassignActivity")));
		pickUp = new ButtonControl(By.xpath(pageobject.getXpath("xp_PickUp")));
		assignmentRow = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AssignmentRow")));

		quoteNumber2 = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_QuoteNumber2")));
		approveRequest = new ButtonControl(By.xpath(pageobject.getXpath("xp_ApproveRequest")));
		originalPremiumData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OriginalPremium")));
		requestedPremiumData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RequestedPremium")));
		referralReasonPriorLoss = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_ReferralReasonPriorLoss")));
		referralCompleteMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ReferralCompleteMsg")));
		pageName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Pagename")));
		referalReceivedMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ReferalReceivedMsg")));
		approveOrDeclineRequest_Q2 = new ButtonControl(By.xpath(pageobject.getXpath("xp_DeclineRequest_Q2")));
		internalComments = new TextAreaControl(By.xpath(pageobject.getXpath("xp_InternalComments")));
		externalComments = new TextAreaControl(By.xpath(pageobject.getXpath("xp_ExternalComments")));

		referralReason = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ReferralReason")));
		googleMaps = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GoogleMaps")));
		viewPolicy = new ButtonControl(By.xpath(pageobject.getXpath("xp_ViewPolicy")));
		assignedUser =  new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AssignedUser")));
		producerCommentsProducerSection = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ProducerCommentsProducerSection")));
		noteBarLink = new HyperLink(By.xpath(pageobject.getXpath("xp_NoteBarLink")));
		noteBarMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NoteBarMessage")));
		producerQuoteStatus = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ProducerQuoteStatus")));
		summaryDetails = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_SummaryDetails")));
	}

	public RequestBindPage clickOnApprove() {
		if (pickUp.checkIfElementIsPresent() && pickUp.checkIfElementIsDisplayed()) {
			pickUp.scrollToElement();
			pickUp.click();
		}
		if (approveOrDeclineRequest.checkIfElementIsPresent() && approveOrDeclineRequest.checkIfElementIsDisplayed()) {
			Assertions.verify(approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Approve/Decline Request button displayed is verified ", false, false);
			approveOrDeclineRequest.scrollToElement();
			approveOrDeclineRequest.click();
			approveOrDeclineRequest.waitTillInVisibilityOfElement(60);
		} else if (approveOrDeclineRequest_Q2.checkIfElementIsPresent()
				&& approveOrDeclineRequest_Q2.checkIfElementIsDisplayed()) {
			Assertions.verify(approveOrDeclineRequest_Q2.checkIfElementIsDisplayed(), true, "Referral Page",
					"Approve/Decline Request button displayed is verified ", false, false);
			approveOrDeclineRequest_Q2.scrollToElement();
			approveOrDeclineRequest_Q2.click();
			approveOrDeclineRequest_Q2.waitTillInVisibilityOfElement(60);
		}
		if (pageName.getData().contains("Request Bind")) {
			return new RequestBindPage();
		}
		return null;
	}

	public void refreshUntilApproveOrDeclineRequest() {
		while (!approveOrDeclineRequest.checkIfElementIsPresent()
				|| !approveOrDeclineRequest_Q2.checkIfElementIsPresent()) {
			refreshPage();
			waitTime(3);
		}

		if (approveOrDeclineRequest.checkIfElementIsPresent() && approveOrDeclineRequest.checkIfElementIsDisplayed()) {
			approveOrDeclineRequest.waitTillVisibilityOfElement(60);
			approveOrDeclineRequest.click();
			approveOrDeclineRequest.waitTillInVisibilityOfElement(60);
		} else {
			approveOrDeclineRequest_Q2.scrollToElement();
			approveOrDeclineRequest_Q2.click();
			approveOrDeclineRequest_Q2.waitTillInVisibilityOfElement(60);
		}
	}
}
