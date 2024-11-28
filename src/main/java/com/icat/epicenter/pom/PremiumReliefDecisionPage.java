package com.icat.epicenter.pom;

import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextAreaControl;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class PremiumReliefDecisionPage extends BasePageControl {
	public TextFieldControl offeredOrApprovedPremium;
	public TextAreaControl internalUWComments;
	public TextAreaControl externalUWComments;
	public ButtonControl approveBtn;
	public ButtonControl denyBtn;
	public ButtonControl cancelBtn;
	public HyperLink viewQuoteDetails;
	public BaseWebElementControl pageName;
	public BaseWebElementControl requestedPremium;

	public TextFieldControl windPremium;
	public TextFieldControl aopPremium;
	public TextFieldControl liabilityPremium;

	public BaseWebElementControl orgAopPremium;
	public BaseWebElementControl orgGLPremium;
	public BaseWebElementControl orgWindPremium;

	public BaseWebElementControl earthquakePremiumLabel;
	public BaseWebElementControl earthquakePremiumValue;
	public BaseWebElementControl sinkholeCGCCPremiumLable;
	public BaseWebElementControl sinkholeCGCCPremiumValue;
	public BaseWebElementControl utilLinePremiumLable;
	public BaseWebElementControl utilLinePremiumValue;
	public BaseWebElementControl eqBreakPremiumLable;
	public BaseWebElementControl eqBreakPremiumValue;

	public PremiumReliefDecisionPage() {
		PageObject pageobject = new PageObject("PremiumReliefDecision");
		offeredOrApprovedPremium = new TextFieldControl(By.xpath(pageobject.getXpath("xp_OfferedOrApprovedPremium")));
		internalUWComments = new TextAreaControl(By.xpath(pageobject.getXpath("xp_InternalUWComments")));
		externalUWComments = new TextAreaControl(By.xpath(pageobject.getXpath("xp_ExternalUWComments")));
		approveBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_ApproveBtn")));
		denyBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_DenyBtn")));
		cancelBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_CancelBtn")));
		viewQuoteDetails = new HyperLink(By.xpath(pageobject.getXpath("xp_ViewQuoteDetails")));
		pageName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PageName")));
		requestedPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RequestedPremium")));

		windPremium = new TextFieldControl(By.xpath(pageobject.getXpath("xp_WindPremium")));
		aopPremium = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AOPPremium")));
		liabilityPremium = new TextFieldControl(By.xpath(pageobject.getXpath("xp_LiabilityPremium")));

		orgAopPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OrgAOPPremium")));
		orgGLPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OrgGLPremium")));
		orgWindPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OrgWindPremium")));
		earthquakePremiumLabel = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EarthquakePremiumLabel")));
		earthquakePremiumValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EarthquakePremiumValue")));

		sinkholeCGCCPremiumLable = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_SinkholeCGCCPremiumLable")));
		sinkholeCGCCPremiumValue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_SinkholeCGCCPremiumValue")));
		utilLinePremiumLable = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_UtilLinePremiumLable")));
		utilLinePremiumValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_UtilLinePremiumValue")));
		eqBreakPremiumLable = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EqBreakPremiumLable")));
		eqBreakPremiumValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EqBreakPremiumValue")));

	}

	public ReferralPage enterPremiumReliefDetails(Map<String, String> testData) {
		offeredOrApprovedPremium.waitTillVisibilityOfElement(60);

		if (testData.get("PremiumAdjustment_OfferedAmt") != null
				&& !testData.get("PremiumAdjustment_OfferedAmt").equals("")) {
			offeredOrApprovedPremium.setData(testData.get("PremiumAdjustment_OfferedAmt"));
			offeredOrApprovedPremium.tab();
			Assertions.passTest("Premium Relief Decision Page",
					"Offered/Approved premium is " + testData.get("PremiumAdjustment_OfferedAmt"));
		}
		if (testData.get("PremiumAdjustment_InternalComments") != null
				&& !testData.get("PremiumAdjustment_InternalComments").equals("")) {
			internalUWComments.scrollToElement();
			internalUWComments.setData(testData.get("PremiumAdjustment_InternalComments"));
		}

		if (testData.get("PremiumAdjustment_ExternalComments") != null
				&& !testData.get("PremiumAdjustment_ExternalComments").equals("")) {
			externalUWComments.scrollToElement();
			externalUWComments.setData(testData.get("PremiumAdjustment_ExternalComments"));
		}
		approveBtn.scrollToElement();
		approveBtn.click();
		Assertions.passTest("Premium Relief Decision Page", "Premium Change Request approved successfully");
		if (pageName.getData().contains("Request Bind")) {
			return new ReferralPage();
		}
		return null;
	}

	public ReferralPage enterPremiumReliefDetailsNAHO(Map<String, String> testData) {
		offeredOrApprovedPremium.waitTillVisibilityOfElement(60);

		if (!testData.get("OfferedPremiumRelief").equals("")) {
			offeredOrApprovedPremium.setData(testData.get("OfferedPremiumRelief"));
			offeredOrApprovedPremium.tab();
		}
		if (!testData.get("PRCInternalUWComments").equals("")) {
			internalUWComments.scrollToElement();
			internalUWComments.setData(testData.get("PRCInternalUWComments"));
		}

		if (!testData.get("PRCExternalUWComments").equals("")) {
			externalUWComments.scrollToElement();
			externalUWComments.setData(testData.get("PRCExternalUWComments"));
		}

		if (!testData.get("WindPremium").equals("")) {
			windPremium.scrollToElement();
			windPremium.setData(testData.get("WindPremium"));
		}
		windPremium.tab();

		if (!testData.get("AOPPremium").equals("")) {
			aopPremium.scrollToElement();
			aopPremium.setData(testData.get("AOPPremium"));
		}
		aopPremium.tab();

		if (!testData.get("LiabilityPremium").equals("")) {
			liabilityPremium.scrollToElement();
			liabilityPremium.setData(testData.get("LiabilityPremium"));
		}
		liabilityPremium.tab();
		if (pageName.getData().contains("Request Bind")) {
			return new ReferralPage();
		}
		return null;
	}
}
