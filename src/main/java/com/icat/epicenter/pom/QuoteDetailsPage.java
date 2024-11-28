package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;

public class QuoteDetailsPage {
	public BaseWebElementControl deductibleData;
	public BaseWebElementControl coverage;
	public BaseWebElementControl coverageData;
	public ButtonControl requestBindButton;
	public ButtonControl goBackButton;
	public BaseWebElementControl priorLossInfo;
	public ButtonControl closeBtn;
	public BaseWebElementControl quoteNumber;
	public BaseWebElementControl diligentEffortFormText;
	public BaseWebElementControl dueDiligenceCertificate;
	public BaseWebElementControl dueDiligenceDetails;
	public HyperLink homePageLink;

	public BaseWebElementControl coverageType;
	public BaseWebElementControl termsConditionsWordings;

	public BaseWebElementControl yearRoofLastReplacedValue;
	public BaseWebElementControl yearRoofLastReplacedLabel;
	public BaseWebElementControl aiName;
	public BaseWebElementControl aiType;
	public BaseWebElementControl earnedPremiumCondition;
	public BaseWebElementControl cancelCondition;
	public BaseWebElementControl allFeeCondition;
	public BaseWebElementControl aitableHeaderName;

	public QuoteDetailsPage() {
		PageObject pageobject = new PageObject("QuoteDetails");
		deductibleData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DeductibleData")));
		coverage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_QuoteCoverage")));
		coverageData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CoverageData")));

		requestBindButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_RequestBindButton")));
		goBackButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_GoBackButton")));
		priorLossInfo = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PriorLossInfo")));
		closeBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_CloseBtn")));
		quoteNumber = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_QuoteNumber")));
		diligentEffortFormText = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DiligentEffortFormText")));
		dueDiligenceCertificate = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_DueDiligenceCertificate")));
		dueDiligenceDetails = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DueDiligenceDetails")));
		homePageLink = new HyperLink(By.xpath(pageobject.getXpath("xp_HomePageLink")));

		coverageType = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CoverageType")));
		termsConditionsWordings = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_TermsConditionsWordings")));
		yearRoofLastReplacedValue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_YearRoofLastReplacedValue")));
		yearRoofLastReplacedLabel = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_YearRoofLastReplacedLabel")));
		aiName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AIName")));
	    aiType = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AIType")));
	    earnedPremiumCondition = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EarnedPremiumCondition")));
	    cancelCondition = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CancelCondition")));
	    allFeeCondition = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AllFeeCondition")));
	    aitableHeaderName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AItableHeaderName")));

	}
}
