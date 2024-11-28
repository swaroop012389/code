package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.PageObject;

public class RmsModelResultsPage extends BasePageControl{

	public ButtonControl closeButton;
	public BaseWebElementControl rmsModelResultValues;
	public BaseWebElementControl tivValue;

	public BaseWebElementControl guyCarpenterAAL;
	public BaseWebElementControl guyCarpenterELR;

	public BaseWebElementControl guyCarpenterAALLabel;
	public BaseWebElementControl guyCarpenterELRLabel;
	public BaseWebElementControl elrPremiumLabel;
	public BaseWebElementControl  gc17Label;
	public BaseWebElementControl  totalPremiumLabel;
	public BaseWebElementControl  perilDeductibleLabel;
	public BaseWebElementControl  perilLabel;
	public BaseWebElementControl  tivValueLabel;
	public BaseWebElementControl  rms21Label;
	public BaseWebElementControl rmsModelValues;

	public RmsModelResultsPage() {
		PageObject pageobject = new PageObject("RmsModelResults");
		closeButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_CloseButton")));
		rmsModelResultValues = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RmsModelResultValues")));
		rmsModelValues = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RmsModelValues")));
		tivValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TivValue")));

		guyCarpenterAAL = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GuyCarpenterAAL")));
        guyCarpenterELR = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GuyCarpenterELR")));

        guyCarpenterAALLabel = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GuyCarpenterAALLabel")));
        guyCarpenterELRLabel = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GuyCarpenterELRLabel")));
        elrPremiumLabel = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ELRPremiumLabel")));
        gc17Label = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GC17Label")));
        totalPremiumLabel = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TotalPremiumLabel")));
        perilDeductibleLabel = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PerilDeductibleLabel")));
        perilLabel = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PerilLabel")));
        tivValueLabel = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TIVValueLabel")));
        rms21Label = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RMS21")));
	}
}
