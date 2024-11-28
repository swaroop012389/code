package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class ScheduledJobsAdmin extends BasePageControl{
	public HyperLink runJobWithOptionslink;
	public ButtonControl runJobButton;
	public TextFieldControl runDate;
	public ButtonControl runStateArrow;
	public ButtonControl runReportLOBOption;
	public ButtonControl reportOnlyArrow;
	public ButtonControl lineOfBusinessArrow;

	public ButtonControl checkCronButton;
	public HyperLink commercialRunWithOptionLink;
	public TextFieldControl commercialNumberofDays;
	public ButtonControl commercialRunJobButton;
	public BaseWebElementControl globalInfo;
	public TextFieldControl stateColonNumOfDays;
	public HyperLink nahoRenewalRunWithOptionsLink;
	public ButtonControl nahoRenewalRunJobButton;
	public TextFieldControl nahoNumberofDays;

	public ScheduledJobsAdmin() {
		PageObject pageobject = new PageObject("ScheduledJobsAdmin");
		runJobWithOptionslink = new HyperLink(By.xpath(pageobject.getXpath("xp_RunJobWithOptionslink")));
		runJobButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_RunJobButton")));
		runDate = new TextFieldControl(By.xpath(pageobject.getXpath("xp_RunDate")));
		runStateArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_RunStateArrow")));
		runReportLOBOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_RunReportLOBOption")));
		reportOnlyArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_ReportOnlyArrow")));
		lineOfBusinessArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_LineOfBusinessArrow")));

		checkCronButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_CheckCronButton")));
		commercialRunWithOptionLink = new HyperLink(By.xpath(pageobject.getXpath("xp_CommercialRunWithOptionLink")));
		commercialNumberofDays = new TextFieldControl(By.xpath(pageobject.getXpath("xp_CommercialNumberofDays")));
		commercialRunJobButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_CommercialRunJobButton")));
		globalInfo = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_globalInfo")));
		stateColonNumOfDays = new TextFieldControl(By.xpath(pageobject.getXpath("xp_StateColonNumOfDays")));

		nahoRenewalRunWithOptionsLink = new HyperLink(By.xpath(pageobject.getXpath("xp_NahoRenewalRunWithOptionsLink")));
		nahoRenewalRunJobButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_NahoRenewalRunJobButton")));
		nahoNumberofDays = new TextFieldControl(By.xpath(pageobject.getXpath("xp_NahoNumberofDays")));

	}
}
