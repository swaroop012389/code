package com.icat.epicenter.pom;

import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.CheckBoxControl;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class ModifyForms {
	public CheckBoxControl overrideCoverageCheckbox;
	public CheckBoxControl earthMovement;
	public CheckBoxControl volcanicEruption;
	public CheckBoxControl eqPropertyWithinCondoUnits;
	public CheckBoxControl windPropertyWithinCondoUnits;
	public CheckBoxControl propertyWithinCondoUnitsEq;
	public CheckBoxControl extendedNoticeofCancellation60Days;
	public CheckBoxControl extendedNoticeofCancellation90Days;
	public ButtonControl override;

	public CheckBoxControl vacancypermitEntireTerm;
	public CheckBoxControl vacancypermitSeasonal;
	public TextFieldControl vacancypermitEffectiveDate;
	public TextFieldControl vacancypermitEndDate;
	public CheckBoxControl heatMaintain;
	public CheckBoxControl lockedAndSecured;
	public CheckBoxControl outdoorTrees;
	public CheckBoxControl fungusWetRot;
	public CheckBoxControl specialConditions;
	public CheckBoxControl specialConditionsAOP;

	// NAHO
	public CheckBoxControl animalExclusion;
	public CheckBoxControl aircraftExclusion;
	public CheckBoxControl actualCashValueRoof;
	public CheckBoxControl roofSurfacingExclusion;
	public CheckBoxControl modifiedFuncionalReplacement;
	public CheckBoxControl limitationOfSwimmingPoolLiability;
	public CheckBoxControl totalRoofExclusion;
	public CheckBoxControl premisesLiability;
	public CheckBoxControl waterDamageExclusionZero;
	public CheckBoxControl waterDamageExclusionTenThousand;
	public CheckBoxControl swimmingPoolLiabilityExclusion;

	public BaseWebElementControl specifyWaterDamageDeductibleText;
	public CheckBoxControl specifyWaterDamageDeductibleCheckBox;
	public BaseWebElementControl byPolicyText;
	public ButtonControl specifyWaterDamageDeductibleOption;
	public ButtonControl specifyWaterDamageDeductibleArrow;
	public BaseWebElementControl specifyWaterDamageDeductibleData;
	public CheckBoxControl specifyWaterDamageDeductibleCheck;

	public ModifyForms() {
		PageObject pageobject = new PageObject("ModifyForms");
		overrideCoverageCheckbox = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_CoverageOverride")));
		earthMovement = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_EarthMovement")));
		volcanicEruption = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_VolcanicEruption")));
		eqPropertyWithinCondoUnits = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_EQPropertyWithinCondoUnits")));
		windPropertyWithinCondoUnits = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_WindPropertyWithinCondoUnits")));
		propertyWithinCondoUnitsEq = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_PropertyWithinCondoUnitsEq")));
		extendedNoticeofCancellation60Days = new CheckBoxControl(
				By.xpath(pageobject.getXpath("xp_ExtendedNoticeofCancellation60Days")));
		extendedNoticeofCancellation90Days = new CheckBoxControl(
				By.xpath(pageobject.getXpath("xp_ExtendedNoticeofCancellation90Days")));
		override = new ButtonControl(By.xpath(pageobject.getXpath("xp_Override")));

		vacancypermitEntireTerm = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_VacancypermitEntireTerm")));
		vacancypermitSeasonal = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_VacancypermitSeasonal")));
		vacancypermitEffectiveDate = new TextFieldControl(
				By.xpath(pageobject.getXpath("xp_VacancypermitEffectiveDate")));
		vacancypermitEndDate = new TextFieldControl(By.xpath(pageobject.getXpath("xp_VacancypermitEndDate")));
		heatMaintain = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_HeatMaintain")));
		lockedAndSecured = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_LockedAndSecured")));
		outdoorTrees = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_OutdoorTrees")));
		fungusWetRot = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_FungusWetRot")));
		specialConditions = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_SpecialConditions")));
		specialConditionsAOP = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_SpecialConditionsAOP")));

		animalExclusion = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_TotalAnimalExclusion")));
		aircraftExclusion = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_AircraftDamageExclusion")));
		actualCashValueRoof = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_ActualCashValueRoof")));
		roofSurfacingExclusion = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_RoofSurfacingDamageExclusion")));
		modifiedFuncionalReplacement = new CheckBoxControl(
				By.xpath(pageobject.getXpath("xp_ModifiedFunctionalReplacement")));
		limitationOfSwimmingPoolLiability = new CheckBoxControl(
				By.xpath(pageobject.getXpath("xp_LimitationOfSwimmingPoolLiability")));
		totalRoofExclusion = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_TotalRoofExclusion")));
		premisesLiability = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_LimitedCoveragePremisesLiability")));
		waterDamageExclusionZero = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_WaterDamageExclusionZero")));
		waterDamageExclusionTenThousand = new CheckBoxControl(
				By.xpath(pageobject.getXpath("xp_WaterDamageExclusionTenThousand")));
		swimmingPoolLiabilityExclusion = new CheckBoxControl(
				By.xpath(pageobject.getXpath("xp_SwimmingPoolLiabilityExclusion")));

		specifyWaterDamageDeductibleText = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_SpecifyWaterDamageDeductibleText")));
		specifyWaterDamageDeductibleCheckBox = new CheckBoxControl(
				By.xpath(pageobject.getXpath("xp_SpecifyWaterDamageDeductibleCheckBox")));
		byPolicyText = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BtPolicyText")));
		specifyWaterDamageDeductibleOption = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_SpecifyWaterDamageDeductibleOption")));
		specifyWaterDamageDeductibleArrow = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_SpecifyWaterDamageDeductibleArrow")));
		specifyWaterDamageDeductibleData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_SpecifyWaterDamageDeductibleData")));
		specifyWaterDamageDeductibleCheck = new CheckBoxControl(
				By.xpath(pageobject.getXpath("xp_SpecifyWaterDamageDeductibleCheck")));
	}

	public void addForms(Map<String, String> Data) {
		if (Data.get("TotalAnimalExclusion").equals("Add")) {
			animalExclusion.scrollToElement();
			animalExclusion.select();
		}

		if (Data.get("AircraftDamageExclusion").equals("Add")) {
			aircraftExclusion.scrollToElement();
			aircraftExclusion.select();
		}

		if (Data.get("ActualCashValueforRoof").equals("Add")) {
			actualCashValueRoof.scrollToElement();
			actualCashValueRoof.select();
		}

		if (Data.get("RoofSurfacingDamageExclusion").equals("Add")) {
			roofSurfacingExclusion.scrollToElement();
			roofSurfacingExclusion.select();
		}
		if (Data.get("LimitedCoveragePremisesLiability").equals("Add")) {
			premisesLiability.scrollToElement();
			premisesLiability.select();
		}
		if (Data.get("ModifiedReplacementCostCoverage").equals("Add")) {
			modifiedFuncionalReplacement.scrollToElement();
			modifiedFuncionalReplacement.select();
		}
		if (Data.get("LimitationOfSwimmingPoolLiability").equals("Add")) {
			limitationOfSwimmingPoolLiability.scrollToElement();
			limitationOfSwimmingPoolLiability.select();
		}
		if (Data.get("TotalRoofExclusion").equals("Add")) {
			totalRoofExclusion.scrollToElement();
			totalRoofExclusion.select();
		}
		if (Data.get("WaterDamageExclusionZero") != null && Data.get("WaterDamageExclusionZero").equals("Add")) {
			waterDamageExclusionZero.scrollToElement();
			waterDamageExclusionZero.select();
		}

		if (Data.get("WaterDamageExclusionTenThousand") != null
				&& Data.get("WaterDamageExclusionTenThousand").equals("Add")) {
			waterDamageExclusionTenThousand.scrollToElement();
			waterDamageExclusionTenThousand.select();
		} else if (Data.get("WaterDamageExclusionTenThousand") != null
				&& Data.get("WaterDamageExclusionTenThousand").equals("Delete")) {
			waterDamageExclusionTenThousand.scrollToElement();
			waterDamageExclusionTenThousand.deSelect();
		}
		if (Data.get("SwimmingPoolLiabilityExclusion").equals("Add")) {
			swimmingPoolLiabilityExclusion.scrollToElement();
			swimmingPoolLiabilityExclusion.select();
		}
		if (override.checkIfElementIsPresent() && override.checkIfElementIsDisplayed()) {
			override.scrollToElement();
			override.click();
		}
	}

	public void addRemoveForms(Map<String, String> Data) {

		if (Data.get("Peril").equalsIgnoreCase("eq")) {
			if (Data.get("EarthMovement").equalsIgnoreCase("yes")) {
				earthMovement.select();
			} else if (Data.get("EarthMovement").equalsIgnoreCase("no")) {
				earthMovement.deSelect();
			}
			if (Data.get("VolcanicEruption").equalsIgnoreCase("yes")) {
				volcanicEruption.select();
			} else if (Data.get("VolcanicEruption").equalsIgnoreCase("no")) {
				volcanicEruption.deSelect();
			}
			if (Data.get("PropertyInCondos").equalsIgnoreCase("yes")) {
				eqPropertyWithinCondoUnits.select();
			} else if (Data.get("PropertyInCondos").equalsIgnoreCase("no")) {
				eqPropertyWithinCondoUnits.deSelect();
			}
			if (Data.get("ExtendedNotice600c").equalsIgnoreCase("yes")) {
				extendedNoticeofCancellation60Days.select();
			} else if (Data.get("ExtendedNotice600c").equalsIgnoreCase("no")) {
				extendedNoticeofCancellation60Days.deSelect();
			}
			if (Data.get("ExtendedNotice600d").equalsIgnoreCase("yes")) {
				extendedNoticeofCancellation90Days.select();
			} else if (Data.get("ExtendedNotice600d").equalsIgnoreCase("no")) {
				extendedNoticeofCancellation90Days.deSelect();
			}

		} else {

			if (Data.get("FloodWarranty?").equalsIgnoreCase("yes")) {
				specialConditionsAOP.select();
			} else if (Data.get("FloodWarranty?").equalsIgnoreCase("no")) {
				specialConditionsAOP.deSelect();
			}
			if (Data.get("VacancyPermitTerm").equalsIgnoreCase("yes")) {
				vacancypermitEntireTerm.select();
			} else if (Data.get("VacancyPermitTerm").equalsIgnoreCase("no")) {
				vacancypermitEntireTerm.deSelect();
			}
			if (Data.get("VacancyPermitSeasonal").equalsIgnoreCase("yes")) {
				vacancypermitSeasonal.select();
				vacancypermitEffectiveDate.waitTillVisibilityOfElement(10);
				vacancypermitEffectiveDate.setData(Data.get("VacancyPermitStart"));
				vacancypermitEndDate.setData(Data.get("VacancyPermitEnd"));
			} else if (Data.get("VacancyPermitSeasonal").equalsIgnoreCase("no")) {
				vacancypermitSeasonal.deSelect();
			}
			if (Data.get("HeatMaintained").equalsIgnoreCase("yes")) {
				heatMaintain.select();
			} else if (Data.get("HeatMaintained").equalsIgnoreCase("no")) {
				heatMaintain.deSelect();
			}
			if (Data.get("LockedAndSecured").equalsIgnoreCase("yes")) {
				lockedAndSecured.select();
			} else if (Data.get("LockedAndSecured").equalsIgnoreCase("no")) {
				lockedAndSecured.deSelect();
			}
			if (Data.get("OutdoorTreesPlantsShrubs").equalsIgnoreCase("yes")) {
				outdoorTrees.select();
			} else if (Data.get("OutdoorTreesPlantsShrubs").equalsIgnoreCase("no")) {
				outdoorTrees.deSelect();
			}
			if (Data.get("PropertyInCondos").equalsIgnoreCase("yes")) {
				windPropertyWithinCondoUnits.select();
			} else if (Data.get("PropertyInCondos").equalsIgnoreCase("no")) {
				windPropertyWithinCondoUnits.deSelect();
			}
			if (Data.get("FungusRotBacteria").equalsIgnoreCase("yes")) {
				fungusWetRot.select();
			} else if (Data.get("FungusRotBacteria").equalsIgnoreCase("no")) {
				fungusWetRot.deSelect();
			}
			if (!Data.get("WaterDamage").equalsIgnoreCase("") && !Data.get("WaterDamage").equalsIgnoreCase("none")) {
				specifyWaterDamageDeductibleCheckBox.select();
				specifyWaterDamageDeductibleArrow.waitTillVisibilityOfElement(5);
				specifyWaterDamageDeductibleArrow.scrollToElement();
				specifyWaterDamageDeductibleArrow.click();
				specifyWaterDamageDeductibleOption.formatDynamicPath(Data.get("WaterDamage")).click();
			} else if (Data.get("WaterDamage").equalsIgnoreCase("no")) {
				specifyWaterDamageDeductibleCheckBox.deSelect();
			}
		}

		override.click();
	}

	public void addRemoveFormsNew(Map<String, String> Data) {
		if (Data.get("Peril").equalsIgnoreCase("EQ")) {
			if (Data.get("EarthMovement").equalsIgnoreCase("yes")) {
				earthMovement.select();
			}
			if (Data.get("VolcanicEruption").equalsIgnoreCase("yes")) {
				volcanicEruption.select();
			}
			if (Data.get("PropertyInCondos").equalsIgnoreCase("yes")) {
				eqPropertyWithinCondoUnits.select();
			}
			if (Data.get("ExtendedNotice600c").equalsIgnoreCase("yes")) {
				extendedNoticeofCancellation60Days.select();
			} else if (Data.get("ExtendedNotice600c").equalsIgnoreCase("yes")) {
				extendedNoticeofCancellation90Days.select();
			}
		} else {
			if (Data.get("FloodWarranty?").equalsIgnoreCase("yes")) {
				if (Data.get("Peril").equalsIgnoreCase("wind")) {
					specialConditions.select();
//				}  else {
//					specialConditionsAOP.select();
				}
			}
			if (Data.get("VacancyPermitTerm").equalsIgnoreCase("yes")) {
				vacancypermitEntireTerm.select();
			}
			if (Data.get("VacancyPermitSeasonal").equalsIgnoreCase("yes")) {
				vacancypermitSeasonal.select();
				vacancypermitEffectiveDate.setData(Data.get("VacancyPermitStart"));
				vacancypermitEndDate.setData(Data.get("VacancyPermitEnd"));
			}
			if (Data.get("HeatMaintained").equalsIgnoreCase("yes")) {
				heatMaintain.select();
			}
			if (Data.get("LockedAndSecured").equalsIgnoreCase("yes")) {
				lockedAndSecured.select();
			}
			if (Data.get("OutdoorTreesPlantsShrubs").equalsIgnoreCase("yes")) {
				outdoorTrees.select();
			}
			if (Data.get("PropertyInCondos").equalsIgnoreCase("yes")) {
				windPropertyWithinCondoUnits.select();
			}
			if (Data.get("FungusRotBacteria").equalsIgnoreCase("yes")) {
				fungusWetRot.select();
			}
		}

		override.click();
	}
}
