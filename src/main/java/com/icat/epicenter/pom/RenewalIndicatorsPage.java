/** Program Description: Object Locators and methods defined in Renewal indicator page
 *  Author			   : SMNetserv
 *  Date of Creation   : 09/11/2017
**/

package com.icat.epicenter.pom;

import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.CheckBoxControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class RenewalIndicatorsPage {
	public CheckBoxControl nonRenewal;
	public CheckBoxControl underwritingReview;
	public CheckBoxControl coverageChange;
	public CheckBoxControl addNoteToRenewal;
	public CheckBoxControl reInspection;
	public ButtonControl updateButton;
	public ButtonControl cancelButton;
	public ButtonControl nonRenewalReasonArrow;
	public HyperLink nonRenewalReasonOption;
	public TextFieldControl nonRenewalLegalNoticeWording;
	public TextFieldControl nonRenewalInternalComments;
	public TextFieldControl underwritingReviewInternalComments;
	public TextFieldControl coverageChangeLegalNoticeWording;
	public TextFieldControl coverageChangeInternalComments;
	public TextFieldControl renewalNotes;
	public CheckBoxControl transferAllSubsequentRenewals;
	public TextFieldControl re_InspectionInternalComments;
	public BaseWebElementControl nonRenewalErrorMessage;

	public RenewalIndicatorsPage() {
		PageObject pageobject = new PageObject("RenewalIndicators");
		nonRenewal = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_NonRenewal")));
		underwritingReview = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_UnderwritingReview")));
		coverageChange = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_CoverageChange")));
		addNoteToRenewal = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_AddNoteToRenewal")));
		reInspection = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_Reinspection")));
		updateButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_Update")));
		cancelButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_Cancel")));
		nonRenewalReasonArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_NonRenewalReasonArrow")));
		nonRenewalReasonOption = new HyperLink(By.xpath(pageobject.getXpath("xp_NonRenewalReasonOption")));
		nonRenewalLegalNoticeWording = new TextFieldControl(
				By.xpath(pageobject.getXpath("xp_NonRenewalLegalNoticeWording")));
		nonRenewalInternalComments = new TextFieldControl(
				By.xpath(pageobject.getXpath("xp_NonRenewalInternalComments")));
		underwritingReviewInternalComments = new TextFieldControl(
				By.xpath(pageobject.getXpath("xp_UnderwriterReviewInternalComments")));
		coverageChangeLegalNoticeWording = new TextFieldControl(
				By.xpath(pageobject.getXpath("xp_CoverageChangeLegalNoticeWording")));
		coverageChangeInternalComments = new TextFieldControl(
				By.xpath(pageobject.getXpath("xp_CoverageChangeInternalComments")));
		renewalNotes = new TextFieldControl(By.xpath(pageobject.getXpath("xp_NoteToRenewalNotes")));
		transferAllSubsequentRenewals = new CheckBoxControl(
				By.xpath(pageobject.getXpath("xp_NoteToRenewalTransferSubsequentRNLCheckbox")));
		re_InspectionInternalComments = new TextFieldControl(
				By.xpath(pageobject.getXpath("xp_ReInspectionInternalComments")));
		nonRenewalErrorMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NonRenewalErrorMessage")));
	}

	public void enterNonRenewalDetails(Map<String, String> Data) {
		updateButton.waitTillVisibilityOfElement(60);
		// effectiveDate.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);

		if (nonRenewalReasonArrow.checkIfElementIsPresent() && nonRenewalReasonArrow.checkIfElementIsDisplayed()) {
			nonRenewalReasonArrow.scrollToElement();
			nonRenewalReasonArrow.click();
			nonRenewalReasonOption.formatDynamicPath(Data.get("NonRenewalReason")).scrollToElement();
			nonRenewalReasonOption.formatDynamicPath(Data.get("NonRenewalReason")).click();
		}
		if (nonRenewalLegalNoticeWording.checkIfElementIsPresent()
				&& nonRenewalLegalNoticeWording.checkIfElementIsDisplayed()) {
			nonRenewalLegalNoticeWording.scrollToElement();
			nonRenewalLegalNoticeWording.setData(Data.get("LegalNoticeWording"));

		}
		if (nonRenewalInternalComments.checkIfElementIsPresent()
				&& nonRenewalInternalComments.checkIfElementIsPresent()) {
			nonRenewalInternalComments.scrollToElement();
			nonRenewalInternalComments.setData(Data.get("ReviewInternalComments"));
		}
		updateButton.scrollToElement();
		updateButton.click();

	}
}
