package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;

public class ViewOrPrintRateTrace extends BasePageControl {

	public BaseWebElementControl baseRate;
	public BaseWebElementControl covAbaseRate;
	public BaseWebElementControl covBbaseRate;
	public BaseWebElementControl covCbaseRate;
	public BaseWebElementControl covDbaseRate;
	public BaseWebElementControl rateLessOptCov;

	public BaseWebElementControl windPremiumOverride;
	public BaseWebElementControl windMinPremium;
	public BaseWebElementControl windPremium;

	public BaseWebElementControl aopPremium;
	public BaseWebElementControl aopMinPremium;
	public BaseWebElementControl increasedSpecialLimitPremium;
	public BaseWebElementControl identityFraudPremium;
	public BaseWebElementControl increasedLimitBusinesssPremium;
	public BaseWebElementControl lossAssesmentPremium;
	public BaseWebElementControl moldPremium;
	public BaseWebElementControl limitedPoolPremium;
	public BaseWebElementControl limiteswaterBackupPremium;
	public BaseWebElementControl aopPremiumOverride;

	public BaseWebElementControl glPremium;
	public BaseWebElementControl covEbasePremium;
	public BaseWebElementControl covFbasePremium;
	public BaseWebElementControl persInjuryPremium;
	public BaseWebElementControl glPremiumOverride;
	public BaseWebElementControl serviceLinePremium;
	public BaseWebElementControl eqbPremium;

	public BaseWebElementControl discountCsba;
	public BaseWebElementControl discountCfsa;
	public BaseWebElementControl hardiplank;
	public BaseWebElementControl fullySprinkled;
	public BaseWebElementControl waterMitigation;
	public BaseWebElementControl gatedCommunity;
	public BaseWebElementControl companionPolicy;

	public BaseWebElementControl plumbingYearFactor;
	public BaseWebElementControl heatingAcYearFactor;
	public BaseWebElementControl electricalYearFactor;
	public BaseWebElementControl windMitigation;

	public BaseWebElementControl windPremiumOverrideValue;
	public BaseWebElementControl windMinPremiumValue;
	public BaseWebElementControl windPremiumVaue;
	public BaseWebElementControl aopPremiumValue;
	public BaseWebElementControl aopMinPremiumValue;
	public BaseWebElementControl increasedSpecialLimitValue;
	public BaseWebElementControl indentityFraudValue;
	public BaseWebElementControl increasedLimitBusinessValue;
	public BaseWebElementControl lossAssesmentValue;
	public BaseWebElementControl moldPremiumValue;
	public BaseWebElementControl limitedPoolValue;
	public BaseWebElementControl limitedwaterBackupPremiumValue;
	public BaseWebElementControl aopPremiumOverrideValue;
	public BaseWebElementControl glPremiumValue;
	public BaseWebElementControl covEbasePremiumValue;
	public BaseWebElementControl covFbasePremiumValue;
	public BaseWebElementControl persInjuryPremiumValue;
	public BaseWebElementControl serviceLinePremiumValue;
	public BaseWebElementControl eqbPremiumValue;
	public HyperLink backBtn;

	public BaseWebElementControl lapseNoSurcharge;
	public BaseWebElementControl lapseNoSurchargeData;
	public BaseWebElementControl gLMinPremiumValue;
	public BaseWebElementControl windDiscountPriorClaim;

	public BaseWebElementControl sinkholeOrCgccpremium;
	public BaseWebElementControl eqPremium;
	public BaseWebElementControl premiumandCoverageData;
	public BaseWebElementControl roofAgeFactorWBPTF;
	public BaseWebElementControl windGreenEndorsementFactor;
	public BaseWebElementControl aopGreenEndorsementFactor;
	public BaseWebElementControl windGreenEndorsementFactorValue;
	public BaseWebElementControl aopGreenEndorsementFactorValue;

	public BaseWebElementControl windDeductibleAOWHFactor;
	public BaseWebElementControl windDeductibleAOWHFactorValue;


	public ViewOrPrintRateTrace() {

		PageObject pageobject = new PageObject("ViewOrPrintRateTrace");
		baseRate = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BaseRate")));
		covAbaseRate = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CovAbaseRate")));
		covBbaseRate = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CovBbaseRate")));
		covCbaseRate = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CovCbaseRate")));
		covDbaseRate = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CovDbaseRate")));
		rateLessOptCov = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RateLessOptCov")));
		windPremiumOverride = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_WindPremiumOverride")));
		windMinPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_WindMinPremium")));
		windPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_WindPremium")));
		aopPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AopPremium")));
		aopMinPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AopMinPremium")));
		increasedSpecialLimitPremium = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_IncreasedSpecialLimitPremium")));
		identityFraudPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_IdentityFraudPremium")));
		increasedLimitBusinesssPremium = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_IncreasedLimitBusinesssPremium")));
		lossAssesmentPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LossAssesmentPremium")));
		moldPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_MoldPremium")));
		limitedPoolPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LimitedPoolPremium")));
		limiteswaterBackupPremium = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_LimiteswaterBackupPremium")));
		aopPremiumOverride = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AopPremiumOverride")));
		glPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GlPremium")));
		covEbasePremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CovEbasePremium")));
		covFbasePremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CovFbasePremium")));
		persInjuryPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PersInjuryPremium")));
		glPremiumOverride = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GlPremiumOverride")));
		serviceLinePremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ServiceLinePremium")));
		eqbPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EqbPremium")));
		discountCsba = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DiscountCsba")));
		discountCfsa = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DiscountCfsa")));
		hardiplank = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Hardiplank")));
		fullySprinkled = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_FullySprinkled")));
		waterMitigation = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_WaterMitigation")));
		gatedCommunity = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GatedCommunity")));
		companionPolicy = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CompanionPolicy")));
		plumbingYearFactor = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PlumbingYearFactor")));
		heatingAcYearFactor = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_HeatingAcYearFactor")));
		electricalYearFactor = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ElectricalYearFactor")));
		windMitigation = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_WindMitigation")));

		windPremiumOverrideValue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_WindPremiumOverrideValue")));
		windMinPremiumValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_WindMinPremiumValue")));
		windPremiumVaue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_WindPremiumVaue")));
		aopPremiumValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AopPremiumValue")));
		aopMinPremiumValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AopMinPremiumValue")));
		increasedSpecialLimitValue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_IncreasedSpecialLimitValue")));
		indentityFraudValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_IndentityFraudValue")));
		increasedLimitBusinessValue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_IncreasedLimitBusinessValue")));
		lossAssesmentValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LossAssesmentValue")));
		moldPremiumValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_MoldPremiumValue")));
		limitedPoolValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LimitedPoolValue")));
		limitedwaterBackupPremiumValue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_LimitedwaterBackupPremiumValue")));
		aopPremiumOverrideValue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_AopPremiumOverrideValue")));
		glPremiumValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GlPremiumValue")));
		covEbasePremiumValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CovEbasePremiumValue")));
		covFbasePremiumValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CovFbasePremiumValue")));
		persInjuryPremiumValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PersInjuryPremiumValue")));
		serviceLinePremiumValue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_ServiceLinePremiumValue")));
		eqbPremiumValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EqbPremiumValue")));
		backBtn = new HyperLink(By.xpath(pageobject.getXpath("xp_BackBtn")));
		lapseNoSurcharge = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LapseNoSurcharge")));
		lapseNoSurchargeData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LapseNoSurchargeData")));
		gLMinPremiumValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GLMinPremiumValue")));
		windDiscountPriorClaim = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_WindDiscountPriorClaim")));

		sinkholeOrCgccpremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_SinkholePremium")));
		eqPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EQPremium")));
		premiumandCoverageData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PremiumandCoverageData")));
		roofAgeFactorWBPTF = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RoofAgeFactorWBPTF")));
		windGreenEndorsementFactor = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_WindGreenEndorsementFactor")));
		aopGreenEndorsementFactor = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AOPGreenEndorsementFactor")));
		windGreenEndorsementFactorValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_WindGreenEndorsementFactorValue")));
		aopGreenEndorsementFactorValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AOPGreenEndorsementFactorValue")));

		windDeductibleAOWHFactor = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_WindDedcutibleAOWHFactor")));
		windDeductibleAOWHFactorValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_WindDedcutibleAOWHFactorValue")));
	}
}
