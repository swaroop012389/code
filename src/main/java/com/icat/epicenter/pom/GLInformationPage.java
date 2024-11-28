/** Program Description: Object Locators and methods defined in General Liability page
 *  Author			   : John
 *  Date of Creation   : 09/06/2019
**/
package com.icat.epicenter.pom;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextFieldControl;
import com.NetServAutomationFramework.generic.WebDriverManager;

public class GLInformationPage extends BasePageControl {
	public HyperLink locationClassArrow;
	public HyperLink locationClassOption;
	public BaseWebElementControl locationClassData;

	public TextFieldControl noOfUnits;
	public TextFieldControl noOfPools;
	public TextFieldControl noOfSpas;

	public HyperLink confirmPoolsArrow;
	public HyperLink confirmPoolsOption;
	public BaseWebElementControl hotTubsData;

	public TextFieldControl noOfSportsCourts;
	public TextFieldControl noOfIndoorFacilities;
	public TextFieldControl noOfParks;

	public HyperLink otherSportsFacilitiesArrow;
	public HyperLink otherSportsFacilitiesOption;
	public BaseWebElementControl sportsFacilitiesData;

	public TextFieldControl noOfBoatSlips;
	public TextFieldControl noOfSaunas;
	public TextFieldControl noOFClubHouses;

	public HyperLink nIOwnRoadsStreetsArrow;
	public HyperLink nIOwnRoadsStreetsOption;
	public BaseWebElementControl insuredOwnsStreetData;

	public HyperLink pondsLakesOnPropertyArrow;
	public HyperLink pondsLakesOnPropertyOption;
	public BaseWebElementControl pondslakesData;

	public HyperLink beachesOnPropertyArrow;
	public HyperLink beachesOnPropertyOption;
	public BaseWebElementControl beachesData;

	public HyperLink securityGuardsOnPropertyArrow;
	public HyperLink securityGuardsOnPropertyOption;
	public BaseWebElementControl guardsData;

	public HyperLink airportNearPropertyArrow;
	public HyperLink airportNearPropertyOption;
	public BaseWebElementControl privateAirportData;

	public HyperLink termLeaseLessThan6MonthsArrow;
	public HyperLink termLeaseLessThan6MonthsOption;
	public BaseWebElementControl leaseTermsData;

	public HyperLink students20percentArrow;
	public HyperLink students20percentOption;
	public BaseWebElementControl tenantsUGData;

	public HyperLink buildingViolationsArrow;
	public HyperLink buildingViolationsOption;
	public BaseWebElementControl buildingViolationsData;

	public HyperLink moreThan20PercentNonOwnersArrow;
	public HyperLink moreThan20PercentNonOwnersOption;
	public BaseWebElementControl nonOwnersData;

	public HyperLink moreThan10PercentOwnersArrow;
	public HyperLink moreThan10PercentOwnersOption;
	public BaseWebElementControl ownedByInsuredData;

	public HyperLink voluntaryMembersArrow;
	public HyperLink voluntaryMembersOption;
	public BaseWebElementControl membershipVoluntaryData;

	public ButtonControl previousButton;
	public ButtonControl continueButton;
	public ButtonControl continueEndorsementButton;

	public BaseWebElementControl pageName;

	static int data_Value1 = 0;

	public GLInformationPage() {
		PageObject pageobject = new PageObject("GLInformation");
		locationClassArrow = new HyperLink(By.xpath(pageobject.getXpath("xp_LocationClassArrow")));
		locationClassOption = new HyperLink(By.xpath(pageobject.getXpath("xp_LocationClassOption")));
		locationClassData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LocationClassData")));

		noOfUnits = new TextFieldControl(By.xpath(pageobject.getXpath("xp_NoOfUnits")));
		noOfPools = new TextFieldControl(By.xpath(pageobject.getXpath("xp_NoOfPools")));
		noOfSpas = new TextFieldControl(By.xpath(pageobject.getXpath("xp_NoOfSpas")));

		confirmPoolsArrow = new HyperLink(By.xpath(pageobject.getXpath("xp_ConfirmPoolsArrow")));
		confirmPoolsOption = new HyperLink(By.xpath(pageobject.getXpath("xp_ConfirmPoolsOption")));
		hotTubsData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_HotTubsData")));

		noOfSportsCourts = new TextFieldControl(By.xpath(pageobject.getXpath("xp_NoOfSportsCourts")));
		noOfIndoorFacilities = new TextFieldControl(By.xpath(pageobject.getXpath("xp_NoOfIndoorFacilities")));
		noOfParks = new TextFieldControl(By.xpath(pageobject.getXpath("xp_NoOfParks")));

		otherSportsFacilitiesArrow = new HyperLink(By.xpath(pageobject.getXpath("xp_OtherSportsFacilitiesArrow")));
		otherSportsFacilitiesOption = new HyperLink(By.xpath(pageobject.getXpath("xp_OtherSportsFacilitiesOption")));
		sportsFacilitiesData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_SportsFacilitiesData")));

		noOfBoatSlips = new TextFieldControl(By.xpath(pageobject.getXpath("xp_NoOfBoatSlips")));
		noOfSaunas = new TextFieldControl(By.xpath(pageobject.getXpath("xp_NoOfSaunas")));
		noOFClubHouses = new TextFieldControl(By.xpath(pageobject.getXpath("xp_NoOFClubHouses")));

		nIOwnRoadsStreetsArrow = new HyperLink(By.xpath(pageobject.getXpath("xp_NIOwnRoadsStreetsArrow")));
		nIOwnRoadsStreetsOption = new HyperLink(By.xpath(pageobject.getXpath("xp_NIOwnRoadsStreetsOption")));
		insuredOwnsStreetData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_InsuredOwnsStreetsData")));

		pondsLakesOnPropertyArrow = new HyperLink(By.xpath(pageobject.getXpath("xp_PondsLakesOnPropertyArrow")));
		pondsLakesOnPropertyOption = new HyperLink(By.xpath(pageobject.getXpath("xp_PondsLakesOnPropertyOption")));
		pondslakesData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PondsLakesInPropertyData")));

		beachesOnPropertyArrow = new HyperLink(By.xpath(pageobject.getXpath("xp_BeachesOnPropertyArrow")));
		beachesOnPropertyOption = new HyperLink(By.xpath(pageobject.getXpath("xp_BeachesOnPropertyOption")));
		beachesData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BeachesInPropertyData")));

		securityGuardsOnPropertyArrow = new HyperLink(
				By.xpath(pageobject.getXpath("xp_SecurityGuardsOnPropertyArrow")));
		securityGuardsOnPropertyOption = new HyperLink(
				By.xpath(pageobject.getXpath("xp_SecurityGuardsOnPropertyOption")));
		guardsData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_SecurityGuardsData")));

		airportNearPropertyArrow = new HyperLink(By.xpath(pageobject.getXpath("xp_AirportNearPropertyArrow")));
		airportNearPropertyOption = new HyperLink(By.xpath(pageobject.getXpath("xp_AirportNearPropertyOption")));
		privateAirportData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PrivateAirportData")));

		termLeaseLessThan6MonthsArrow = new HyperLink(
				By.xpath(pageobject.getXpath("xp_TermLeaseLessThan6MonthsArrow")));
		termLeaseLessThan6MonthsOption = new HyperLink(
				By.xpath(pageobject.getXpath("xp_TermLeaseLessThan6MonthsOption")));
		leaseTermsData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LeaseTermsData")));

		students20percentArrow = new HyperLink(By.xpath(pageobject.getXpath("xp_Students20percentArrow")));
		students20percentOption = new HyperLink(By.xpath(pageobject.getXpath("xp_Students20percentOption")));
		tenantsUGData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_20%TenantsUGData")));

		buildingViolationsArrow = new HyperLink(By.xpath(pageobject.getXpath("xp_BuildingViolationsArrow")));
		buildingViolationsOption = new HyperLink(By.xpath(pageobject.getXpath("xp_BuildingViolationsOption")));
		buildingViolationsData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BuildingViolationsData")));

		moreThan20PercentNonOwnersArrow = new HyperLink(
				By.xpath(pageobject.getXpath("xp_MoreThan20PercentNonOwnersArrow")));
		moreThan20PercentNonOwnersOption = new HyperLink(
				By.xpath(pageobject.getXpath("xp_MoreThan20PercentNonOwnersOption")));
		nonOwnersData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_20%NonOwnersData")));

		moreThan10PercentOwnersArrow = new HyperLink(By.xpath(pageobject.getXpath("xp_MoreThan10PercentOwnersArrow")));
		moreThan10PercentOwnersOption = new HyperLink(
				By.xpath(pageobject.getXpath("xp_MoreThan10PercentOwnersOption")));
		ownedByInsuredData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_10%OwnedByInsuredData")));

		voluntaryMembersArrow = new HyperLink(By.xpath(pageobject.getXpath("xp_VoluntaryMembersArrow")));
		voluntaryMembersOption = new HyperLink(By.xpath(pageobject.getXpath("xp_VoluntaryMembersOption")));
		membershipVoluntaryData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_AssociationMembershipData")));

		previousButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_PreviousButton")));
		continueButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ContinueButton")));
		continueEndorsementButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ContinueEndorsementButton")));

		pageName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PageName")));
	}

	public CreateQuotePage enterGLInformation(Map<String, String> data) {

		for (int i = 1; i <= 3; i++) {
			if (!(data.get("L" + i + "-GLLocationClass").equals(""))) {
				locationClassArrow.formatDynamicPath(i - 1).waitTillButtonIsClickable(60);
				locationClassArrow.formatDynamicPath(i - 1).waitTillVisibilityOfElement(60);
				locationClassArrow.formatDynamicPath(i - 1).scrollToElement();
				locationClassArrow.formatDynamicPath(i - 1).click();
				locationClassOption.formatDynamicPath(i - 1, data.get("L" + i + "-GLLocationClass"))
						.waitTillButtonIsClickable(60);
				locationClassOption.formatDynamicPath(i - 1, data.get("L" + i + "-GLLocationClass")).scrollToElement();
				locationClassOption.formatDynamicPath(i - 1, data.get("L" + i + "-GLLocationClass")).click();
				noOfUnits.formatDynamicPath(i - 1).waitTillPresenceOfElement(60);
				noOfUnits.formatDynamicPath(i - 1).waitTillVisibilityOfElement(60);
			}
			if (!(data.get("L" + i + "-GLNumberofUnits").equals(""))) {
				noOfUnits.formatDynamicPath(i - 1).scrollToElement();
				noOfUnits.formatDynamicPath(i - 1).clearData();
				noOfUnits.formatDynamicPath(i - 1).setData(data.get("L" + i + "-GLNumberofUnits"));
			}

			if (!(data.get("L" + i + "-GLNumberofPools").equals(""))) {
				noOfPools.formatDynamicPath(i - 1).scrollToElement();
				noOfPools.formatDynamicPath(i - 1).setData(data.get("L" + i + "-GLNumberofPools"));
			}

			if (!(data.get("L" + i + "-GLNumberofSpas").equals(""))) {
				noOfSpas.formatDynamicPath(i - 1).scrollToElement();
				noOfSpas.formatDynamicPath(i - 1).setData(data.get("L" + i + "-GLNumberofSpas"));
				// setting this value to something other than 0 isn't firing the javascript to
				// display the 'water safe' dropdown
				WebElement ele = WebDriverManager.getCurrentDriver()
						.findElement(By.xpath("//input[contains(@id,'NUMBER_OF_HOT_TUBS.value')]"));
				ele.sendKeys(Keys.TAB);
			}

			if (!(data.get("L" + i + "-GLPoolsOrHottubRequirements").equals(""))) {
				confirmPoolsArrow.formatDynamicPath(i - 1).waitTillButtonIsClickable(60);
				confirmPoolsArrow.formatDynamicPath(i - 1).waitTillVisibilityOfElement(60);
				confirmPoolsArrow.formatDynamicPath(i - 1).scrollToElement();
				confirmPoolsArrow.formatDynamicPath(i - 1).click();
				confirmPoolsOption.formatDynamicPath(i - 1, data.get("L" + i + "-GLPoolsOrHottubRequirements"))
						.waitTillVisibilityOfElement(60);
				confirmPoolsOption.formatDynamicPath(i - 1, data.get("L" + i + "-GLPoolsOrHottubRequirements"))
						.scrollToElement();
				confirmPoolsOption.formatDynamicPath(i - 1, data.get("L" + i + "-GLPoolsOrHottubRequirements")).click();
			}

			if (!(data.get("L" + i + "-GLNumberofSportsCourts").equals(""))) {
				noOfSportsCourts.formatDynamicPath(i - 1).scrollToElement();
				noOfSportsCourts.formatDynamicPath(i - 1).setData(data.get("L" + i + "-GLNumberofSportsCourts"));
			}

			if (!(data.get("L" + i + "-GLNumberofIndoorPhysicalFitness").equals(""))) {
				noOfIndoorFacilities.formatDynamicPath(i - 1).scrollToElement();
				noOfIndoorFacilities.formatDynamicPath(i - 1)
						.setData(data.get("L" + i + "-GLNumberofIndoorPhysicalFitness"));
			}

			if (!(data.get("L" + i + "-GLNumberofGrounds").equals(""))) {
				noOfParks.formatDynamicPath(i - 1).scrollToElement();
				noOfParks.formatDynamicPath(i - 1).setData(data.get("L" + i + "-GLNumberofGrounds"));
				// setting this value to something other than 0 isn't firing the javascript to
				// display the 'rec areas safe' dropdown
				WebElement ele = WebDriverManager.getCurrentDriver()
						.findElement(By.xpath("//input[contains(@id,'NUMBER_OF_PARKS.value')]"));
				ele.sendKeys(Keys.TAB);
			}

			if (!(data.get("L" + i + "-GLSportsFacilities").equals(""))) {
				otherSportsFacilitiesArrow.formatDynamicPath(i - 1).waitTillButtonIsClickable(60);
				otherSportsFacilitiesArrow.formatDynamicPath(i - 1).waitTillVisibilityOfElement(60);
				otherSportsFacilitiesArrow.formatDynamicPath(i - 1).scrollToElement();
				otherSportsFacilitiesArrow.formatDynamicPath(i - 1).click();
				otherSportsFacilitiesOption.formatDynamicPath(i - 1, data.get("L" + i + "-GLSportsFacilities"))
						.waitTillVisibilityOfElement(60);
				otherSportsFacilitiesOption.formatDynamicPath(i - 1, data.get("L" + i + "-GLSportsFacilities"))
						.scrollToElement();
				otherSportsFacilitiesOption.formatDynamicPath(i - 1, data.get("L" + i + "-GLSportsFacilities")).click();
			}

			if (!(data.get("L" + i + "-GLNumberofBoatSlips").equals(""))) {
				noOfBoatSlips.formatDynamicPath(i - 1).scrollToElement();
				noOfBoatSlips.formatDynamicPath(i - 1).setData(data.get("L" + i + "-GLNumberofBoatSlips"));
			}
			if (!(data.get("L" + i + "-GLNumberofSaunas").equals(""))) {
				noOfSaunas.formatDynamicPath(i - 1).scrollToElement();
				noOfSaunas.formatDynamicPath(i - 1).setData(data.get("L" + i + "-GLNumberofSaunas"));
			}
			if (!(data.get("L" + i + "-GLNumberofClubhouses").equals(""))) {
				noOFClubHouses.formatDynamicPath(i - 1).scrollToElement();
				noOFClubHouses.formatDynamicPath(i - 1).setData(data.get("L" + i + "-GLNumberofClubhouses"));
			}

			if (!(data.get("L" + i + "-NamedInsuredOwnRoadsorStreets").equals(""))) {
				nIOwnRoadsStreetsArrow.formatDynamicPath(i - 1).waitTillButtonIsClickable(60);
				nIOwnRoadsStreetsArrow.formatDynamicPath(i - 1).waitTillVisibilityOfElement(60);
				nIOwnRoadsStreetsArrow.formatDynamicPath(i - 1).scrollToElement();
				nIOwnRoadsStreetsArrow.formatDynamicPath(i - 1).click();
				nIOwnRoadsStreetsOption.formatDynamicPath(i - 1, data.get("L" + i + "-NamedInsuredOwnRoadsorStreets"))
						.waitTillVisibilityOfElement(60);
				nIOwnRoadsStreetsOption.formatDynamicPath(i - 1, data.get("L" + i + "-NamedInsuredOwnRoadsorStreets"))
						.scrollToElement();
				nIOwnRoadsStreetsOption.formatDynamicPath(i - 1, data.get("L" + i + "-NamedInsuredOwnRoadsorStreets"))
						.click();
			}

			if (!(data.get("L" + i + "-OwnsPondsorLakesInProperty").equals(""))) {
				pondsLakesOnPropertyArrow.formatDynamicPath(i - 1).waitTillButtonIsClickable(60);
				pondsLakesOnPropertyArrow.formatDynamicPath(i - 1).waitTillVisibilityOfElement(60);
				pondsLakesOnPropertyArrow.formatDynamicPath(i - 1).scrollToElement();
				pondsLakesOnPropertyArrow.formatDynamicPath(i - 1).click();
				pondsLakesOnPropertyOption.formatDynamicPath(i - 1, data.get("L" + i + "-OwnsPondsorLakesInProperty"))
						.waitTillVisibilityOfElement(60);
				pondsLakesOnPropertyOption.formatDynamicPath(i - 1, data.get("L" + i + "-OwnsPondsorLakesInProperty"))
						.scrollToElement();
				pondsLakesOnPropertyOption.formatDynamicPath(i - 1, data.get("L" + i + "-OwnsPondsorLakesInProperty"))
						.click();
			}
			if (!(data.get("L" + i + "-OwnsBeachesInProperty").equals(""))) {
				beachesOnPropertyArrow.formatDynamicPath(i - 1).waitTillButtonIsClickable(60);
				beachesOnPropertyArrow.formatDynamicPath(i - 1).waitTillVisibilityOfElement(60);
				beachesOnPropertyArrow.formatDynamicPath(i - 1).scrollToElement();
				beachesOnPropertyArrow.formatDynamicPath(i - 1).click();
				beachesOnPropertyOption.formatDynamicPath(i - 1, data.get("L" + i + "-OwnsBeachesInProperty"))
						.waitTillVisibilityOfElement(60);
				beachesOnPropertyOption.formatDynamicPath(i - 1, data.get("L" + i + "-OwnsBeachesInProperty"))
						.scrollToElement();
				beachesOnPropertyOption.formatDynamicPath(i - 1, data.get("L" + i + "-OwnsBeachesInProperty")).click();
			}
			if (!(data.get("L" + i + "-SecurityGuards?").equals(""))) {
				securityGuardsOnPropertyArrow.formatDynamicPath(i - 1).waitTillButtonIsClickable(60);
				securityGuardsOnPropertyArrow.formatDynamicPath(i - 1).waitTillVisibilityOfElement(60);
				securityGuardsOnPropertyArrow.formatDynamicPath(i - 1).scrollToElement();
				securityGuardsOnPropertyArrow.formatDynamicPath(i - 1).click();
				securityGuardsOnPropertyOption.formatDynamicPath(i - 1, data.get("L" + i + "-SecurityGuards?"))
						.waitTillVisibilityOfElement(60);
				securityGuardsOnPropertyOption.formatDynamicPath(i - 1, data.get("L" + i + "-SecurityGuards?"))
						.scrollToElement();
				securityGuardsOnPropertyOption.formatDynamicPath(i - 1, data.get("L" + i + "-SecurityGuards?")).click();
			}
			if (!(data.get("L" + i + "-PropertyhavePrivateAirport").equals(""))) {
				airportNearPropertyArrow.formatDynamicPath(i - 1).waitTillButtonIsClickable(60);
				airportNearPropertyArrow.formatDynamicPath(i - 1).waitTillVisibilityOfElement(60);
				airportNearPropertyArrow.formatDynamicPath(i - 1).scrollToElement();
				airportNearPropertyArrow.formatDynamicPath(i - 1).click();
				airportNearPropertyOption.formatDynamicPath(i - 1, data.get("L" + i + "-PropertyhavePrivateAirport"))
						.waitTillVisibilityOfElement(60);
				airportNearPropertyOption.formatDynamicPath(i - 1, data.get("L" + i + "-PropertyhavePrivateAirport"))
						.scrollToElement();
				airportNearPropertyOption.formatDynamicPath(i - 1, data.get("L" + i + "-PropertyhavePrivateAirport"))
						.click();
			}
			if (!(data.get("L" + i + "-LeaseTermsAllowed").equals(""))) {
				if (termLeaseLessThan6MonthsArrow.formatDynamicPath(i - 1).checkIfElementIsPresent()
						&& termLeaseLessThan6MonthsArrow.formatDynamicPath(i - 1).checkIfElementIsDisplayed()) {
					termLeaseLessThan6MonthsArrow.formatDynamicPath(i - 1).waitTillButtonIsClickable(60);
					termLeaseLessThan6MonthsArrow.formatDynamicPath(i - 1).waitTillVisibilityOfElement(60);
					termLeaseLessThan6MonthsArrow.formatDynamicPath(i - 1).scrollToElement();
					termLeaseLessThan6MonthsArrow.formatDynamicPath(i - 1).click();
					termLeaseLessThan6MonthsOption.formatDynamicPath(i - 1, data.get("L" + i + "-LeaseTermsAllowed"))
							.waitTillVisibilityOfElement(60);
					termLeaseLessThan6MonthsOption.formatDynamicPath(i - 1, data.get("L" + i + "-LeaseTermsAllowed"))
							.scrollToElement();
					termLeaseLessThan6MonthsOption.formatDynamicPath(i - 1, data.get("L" + i + "-LeaseTermsAllowed"))
							.click();
				}
			}
			if (!(data.get("L" + i + "-Morethan20%TenantsUGStudents").equals(""))) {
				if (students20percentArrow.formatDynamicPath(i - 1).checkIfElementIsPresent()
						&& students20percentArrow.formatDynamicPath(i - 1).checkIfElementIsDisplayed()) {
					students20percentArrow.formatDynamicPath(i - 1).waitTillButtonIsClickable(60);
					students20percentArrow.formatDynamicPath(i - 1).waitTillVisibilityOfElement(60);
					students20percentArrow.formatDynamicPath(i - 1).scrollToElement();
					students20percentArrow.formatDynamicPath(i - 1).click();
					students20percentOption
							.formatDynamicPath(i - 1, data.get("L" + i + "-Morethan20%TenantsUGStudents"))
							.waitTillVisibilityOfElement(60);
					students20percentOption
							.formatDynamicPath(i - 1, data.get("L" + i + "-Morethan20%TenantsUGStudents"))
							.scrollToElement();
					students20percentOption
							.formatDynamicPath(i - 1, data.get("L" + i + "-Morethan20%TenantsUGStudents")).click();
				}
			}
			if (!(data.get("L" + i + "-AnyOutstandingBuildingCodesViolation").equals(""))) {
				if (buildingViolationsArrow.formatDynamicPath(i - 1).checkIfElementIsPresent()
						&& buildingViolationsArrow.formatDynamicPath(i - 1).checkIfElementIsDisplayed()) {
					buildingViolationsArrow.formatDynamicPath(i - 1).waitTillButtonIsClickable(60);
					buildingViolationsArrow.formatDynamicPath(i - 1).waitTillVisibilityOfElement(60);
					buildingViolationsArrow.formatDynamicPath(i - 1).scrollToElement();
					buildingViolationsArrow.formatDynamicPath(i - 1).click();
					buildingViolationsOption
							.formatDynamicPath(i - 1, data.get("L" + i + "-AnyOutstandingBuildingCodesViolation"))
							.waitTillVisibilityOfElement(60);
					buildingViolationsOption
							.formatDynamicPath(i - 1, data.get("L" + i + "-AnyOutstandingBuildingCodesViolation"))
							.scrollToElement();
					buildingViolationsOption
							.formatDynamicPath(i - 1, data.get("L" + i + "-AnyOutstandingBuildingCodesViolation"))
							.click();
				}
			}
			if (!(data.get("L" + i + "-Morethan20%OwnedByNonOwners").equals(""))) {
				if (moreThan20PercentNonOwnersArrow.formatDynamicPath(i - 1).checkIfElementIsPresent()
						&& moreThan20PercentNonOwnersArrow.formatDynamicPath(i - 1).checkIfElementIsDisplayed()) {
					moreThan20PercentNonOwnersArrow.formatDynamicPath(i - 1).waitTillButtonIsClickable(60);
					moreThan20PercentNonOwnersArrow.formatDynamicPath(i - 1).waitTillVisibilityOfElement(60);
					moreThan20PercentNonOwnersArrow.formatDynamicPath(i - 1).scrollToElement();
					moreThan20PercentNonOwnersArrow.formatDynamicPath(i - 1).click();
					moreThan20PercentNonOwnersOption
							.formatDynamicPath(i - 1, data.get("L" + i + "-Morethan20%OwnedByNonOwners"))
							.scrollToElement();
					moreThan20PercentNonOwnersOption
							.formatDynamicPath(i - 1, data.get("L" + i + "-Morethan20%OwnedByNonOwners")).click();
				}
			}
			if (!(data.get("L" + i + "-10%UnitsUnitsOwnedByInsuredOrDeveloper").equals(""))) {
				if (moreThan10PercentOwnersArrow.formatDynamicPath(i - 1).checkIfElementIsPresent()
						&& moreThan10PercentOwnersArrow.formatDynamicPath(i - 1).checkIfElementIsDisplayed()) {
					moreThan10PercentOwnersArrow.formatDynamicPath(i - 1).waitTillButtonIsClickable(60);
					moreThan10PercentOwnersArrow.formatDynamicPath(i - 1).waitTillVisibilityOfElement(60);
					moreThan10PercentOwnersArrow.formatDynamicPath(i - 1).scrollToElement();
					moreThan10PercentOwnersArrow.formatDynamicPath(i - 1).click();
					moreThan10PercentOwnersOption
							.formatDynamicPath(i - 1, data.get("L" + i + "-10%UnitsUnitsOwnedByInsuredOrDeveloper"))
							.scrollToElement();
					moreThan10PercentOwnersOption
							.formatDynamicPath(i - 1, data.get("L" + i + "-10%UnitsUnitsOwnedByInsuredOrDeveloper"))
							.click();
				}
			}
			if (!(data.get("L" + i + "-AssociationMembershipsVoluntary").equals(""))) {
				if (voluntaryMembersArrow.formatDynamicPath(i - 1).checkIfElementIsPresent()
						&& voluntaryMembersArrow.formatDynamicPath(i - 1).checkIfElementIsDisplayed()) {
					voluntaryMembersArrow.formatDynamicPath(i - 1).waitTillButtonIsClickable(60);
					voluntaryMembersArrow.formatDynamicPath(i - 1).waitTillVisibilityOfElement(60);
					voluntaryMembersArrow.formatDynamicPath(i - 1).scrollToElement();
					voluntaryMembersArrow.formatDynamicPath(i - 1).click();
					voluntaryMembersOption
							.formatDynamicPath(i - 1, data.get("L" + i + "-AssociationMembershipsVoluntary"))
							.scrollToElement();
					voluntaryMembersOption
							.formatDynamicPath(i - 1, data.get("L" + i + "-AssociationMembershipsVoluntary")).click();
				}
			}
		}
		if (continueButton.checkIfElementIsPresent() && continueButton.checkIfElementIsDisplayed()) {
			continueButton.scrollToElement();
			continueButton.click();
		}

		if (pageName.getData().contains("Quote")) {
			return new CreateQuotePage();
		}
		return null;

	}

	public void editGLInformation(Map<String, String> data) {
		for (int i = 1; i <= 3; i++) {

			if (!(data.get("L" + i + "-GLLocationClass").equals(""))) {
				Assertions.addInfo("GL Information Page",
						"Location Class original Value : " + locationClassData.formatDynamicPath(i - 1).getData());
				locationClassArrow.formatDynamicPath(i - 1).waitTillButtonIsClickable(60);
				locationClassArrow.formatDynamicPath(i - 1).waitTillVisibilityOfElement(60);
				locationClassArrow.formatDynamicPath(i - 1).scrollToElement();
				locationClassArrow.formatDynamicPath(i - 1).click();
				locationClassOption.formatDynamicPath(i - 1, data.get("L" + i + "-GLLocationClass")).scrollToElement();
				locationClassOption.formatDynamicPath(i - 1, data.get("L" + i + "-GLLocationClass")).click();
				Assertions.passTest("GL Information Page",
						"Location Class Latest Value : " + locationClassData.formatDynamicPath(i - 1).getData());
			}
			if (!(data.get("L" + i + "-GLNumberofUnits").equals(""))) {
				Assertions.addInfo("GL Information Page",
						"Number of Units original Value : " + noOfUnits.formatDynamicPath(i - 1).getData());
				noOfUnits.formatDynamicPath(i - 1).scrollToElement();
				noOfUnits.formatDynamicPath(i - 1).clearData();
				noOfUnits.formatDynamicPath(i - 1).setData(data.get("L" + i + "-GLNumberofUnits"));
				Assertions.passTest("GL Information Page",
						"Number of Units Latest Value : " + noOfUnits.formatDynamicPath(i - 1).getData());
			}

			if (!(data.get("L" + i + "-GLNumberofPools").equals(""))) {
				Assertions.addInfo("GL Information Page",
						"Number of Pools original Value : " + noOfPools.formatDynamicPath(i - 1).getData());
				noOfPools.formatDynamicPath(i - 1).scrollToElement();
				noOfPools.formatDynamicPath(i - 1).setData(data.get("L" + i + "-GLNumberofPools"));
				Assertions.passTest("GL Information Page",
						"Number of Pools Latest Value : " + noOfPools.formatDynamicPath(i - 1).getData());
			}

			if (!(data.get("L" + i + "-GLNumberofSpas").equals(""))) {
				Assertions.addInfo("GL Information Page",
						"Number of Spas original Value : " + noOfSpas.formatDynamicPath(i - 1).getData());
				noOfSpas.formatDynamicPath(i - 1).scrollToElement();
				noOfSpas.formatDynamicPath(i - 1).setData(data.get("L" + i + "-GLNumberofSpas"));
				Assertions.addInfo("GL Information Page",
						"Number of Spas Latest Value : " + noOfSpas.formatDynamicPath(i - 1).getData());
			}

			if (!(data.get("L" + i + "-GLPoolsOrHottubRequirements").equals(""))) {
				Assertions.addInfo("GL Information Page", "Pools Or Hottub meet the Requirements original Value : "
						+ hotTubsData.formatDynamicPath(i - 1).getData());
				confirmPoolsArrow.formatDynamicPath(i - 1).waitTillButtonIsClickable(60);
				confirmPoolsArrow.formatDynamicPath(i - 1).waitTillVisibilityOfElement(60);
				confirmPoolsArrow.formatDynamicPath(i - 1).scrollToElement();
				confirmPoolsArrow.formatDynamicPath(i - 1).click();
				confirmPoolsOption.formatDynamicPath(i - 1, data.get("L" + i + "-GLPoolsOrHottubRequirements"))
						.waitTillVisibilityOfElement(60);
				confirmPoolsOption.formatDynamicPath(i - 1, data.get("L" + i + "-GLPoolsOrHottubRequirements"))
						.scrollToElement();
				confirmPoolsOption.formatDynamicPath(i - 1, data.get("L" + i + "-GLPoolsOrHottubRequirements")).click();
				Assertions.passTest("GL Information Page", "Pools Or Hottub meet the Requirements Latest Value : "
						+ hotTubsData.formatDynamicPath(i - 1).getData());
			}

			if (!(data.get("L" + i + "-GLNumberofSportsCourts").equals(""))) {
				Assertions.addInfo("GL Information Page", "Number of Sports Courts original Value : "
						+ noOfSportsCourts.formatDynamicPath(i - 1).getData());
				noOfSportsCourts.formatDynamicPath(i - 1).scrollToElement();
				noOfSportsCourts.formatDynamicPath(i - 1).setData(data.get("L" + i + "-GLNumberofSportsCourts"));
				Assertions.passTest("GL Information Page", "Number of Sports Courts Latest Value : "
						+ noOfSportsCourts.formatDynamicPath(i - 1).getData());
			}

			if (!(data.get("L" + i + "-GLNumberofIndoorPhysicalFitness").equals(""))) {
				Assertions.addInfo("GL Information Page", "Number of Indoor Physical Fitness original Value : "
						+ noOfIndoorFacilities.formatDynamicPath(i - 1).getData());
				noOfIndoorFacilities.formatDynamicPath(i - 1).scrollToElement();
				noOfIndoorFacilities.formatDynamicPath(i - 1)
						.setData(data.get("L" + i + "-GLNumberofIndoorPhysicalFitness"));
				Assertions.passTest("GL Information Page", "Number of Indoor Physical Fitness Latest Value : "
						+ noOfIndoorFacilities.formatDynamicPath(i - 1).getData());
			}

			if (!(data.get("L" + i + "-GLNumberofGrounds").equals(""))) {
				Assertions.addInfo("GL Information Page",
						"Number of Parks/Grounds original Value : " + noOfParks.formatDynamicPath(i - 1).getData());
				noOfParks.formatDynamicPath(i - 1).scrollToElement();
				noOfParks.formatDynamicPath(i - 1).setData(data.get("L" + i + "-GLNumberofGrounds"));
				Assertions.passTest("GL Information Page",
						"Number of Parks/Grounds Latest Value : " + noOfParks.formatDynamicPath(i - 1).getData());
			}

			if (!(data.get("L" + i + "-GLSportsFacilities").equals(""))) {
				Assertions.addInfo("GL Information Page", "Other Sports Facilities original Value : "
						+ sportsFacilitiesData.formatDynamicPath(i - 1).getData());
				otherSportsFacilitiesArrow.formatDynamicPath(i - 1).waitTillButtonIsClickable(60);
				otherSportsFacilitiesArrow.formatDynamicPath(i - 1).waitTillVisibilityOfElement(60);
				otherSportsFacilitiesArrow.formatDynamicPath(i - 1).scrollToElement();
				otherSportsFacilitiesArrow.formatDynamicPath(i - 1).click();
				otherSportsFacilitiesOption.formatDynamicPath(i - 1, data.get("L" + i + "-GLSportsFacilities"))
						.waitTillVisibilityOfElement(60);
				otherSportsFacilitiesOption.formatDynamicPath(i - 1, data.get("L" + i + "-GLSportsFacilities"))
						.scrollToElement();
				otherSportsFacilitiesOption.formatDynamicPath(i - 1, data.get("L" + i + "-GLSportsFacilities")).click();
				Assertions.passTest("GL Information Page", "Other Sports Facilities Latest Value : "
						+ sportsFacilitiesData.formatDynamicPath(i - 1).getData());
			}

			if (!(data.get("L" + i + "-GLNumberofBoatSlips").equals(""))) {
				Assertions.addInfo("GL Information Page",
						"Number of Boat Slips original Value : " + noOfBoatSlips.formatDynamicPath(i - 1).getData());
				noOfBoatSlips.formatDynamicPath(i - 1).scrollToElement();
				noOfBoatSlips.formatDynamicPath(i - 1).setData(data.get("L" + i + "-GLNumberofBoatSlips"));
				Assertions.passTest("GL Information Page",
						"Number of Boat Slips Latest Value : " + noOfBoatSlips.formatDynamicPath(i - 1).getData());
			}
			if (!(data.get("L" + i + "-GLNumberofSaunas").equals(""))) {
				Assertions.addInfo("GL Information Page",
						"Number of Saunas original Value : " + noOfSaunas.formatDynamicPath(i - 1).getData());
				noOfSaunas.formatDynamicPath(i - 1).scrollToElement();
				noOfSaunas.formatDynamicPath(i - 1).setData(data.get("L" + i + "-GLNumberofSaunas"));
				Assertions.passTest("GL Information Page",
						"Number of Saunas Latest Value : " + noOfSaunas.formatDynamicPath(i - 1).getData());
			}
			if (!(data.get("L" + i + "-GLNumberofClubhouses").equals(""))) {
				Assertions.addInfo("GL Information Page",
						"Number of Clubhouses original Value : " + noOFClubHouses.formatDynamicPath(i - 1).getData());
				noOFClubHouses.formatDynamicPath(i - 1).scrollToElement();
				noOFClubHouses.formatDynamicPath(i - 1).setData(data.get("L" + i + "-GLNumberofClubhouses"));
				Assertions.passTest("GL Information Page",
						"Number of Clubhouses Latest Value : " + noOFClubHouses.formatDynamicPath(i - 1).getData());
			}

			if (!(data.get("L" + i + "-NamedInsuredOwnRoadsorStreets").equals(""))) {
				Assertions.addInfo("GL Information Page", "Named Insured Own Roads or Streets original Value : "
						+ insuredOwnsStreetData.formatDynamicPath(i - 1).getData());
				nIOwnRoadsStreetsArrow.formatDynamicPath(i - 1).waitTillButtonIsClickable(60);
				nIOwnRoadsStreetsArrow.formatDynamicPath(i - 1).waitTillVisibilityOfElement(60);
				nIOwnRoadsStreetsArrow.formatDynamicPath(i - 1).scrollToElement();
				nIOwnRoadsStreetsArrow.formatDynamicPath(i - 1).click();
				nIOwnRoadsStreetsOption.formatDynamicPath(i - 1, data.get("L" + i + "-NamedInsuredOwnRoadsorStreets"))
						.waitTillVisibilityOfElement(60);
				nIOwnRoadsStreetsOption.formatDynamicPath(i - 1, data.get("L" + i + "-NamedInsuredOwnRoadsorStreets"))
						.scrollToElement();
				nIOwnRoadsStreetsOption.formatDynamicPath(i - 1, data.get("L" + i + "-NamedInsuredOwnRoadsorStreets"))
						.click();
				Assertions.passTest("GL Information Page", "Named Insured Own Roads or Streets Latest Value : "
						+ insuredOwnsStreetData.formatDynamicPath(i - 1).getData());
			}

			if (!(data.get("L" + i + "-OwnsPondsorLakesInProperty").equals(""))) {
				Assertions.addInfo("GL Information Page", "Are there Ponds or Lakes on Property original Value : "
						+ pondslakesData.formatDynamicPath(i - 1).getData());
				pondsLakesOnPropertyArrow.formatDynamicPath(i - 1).waitTillButtonIsClickable(60);
				pondsLakesOnPropertyArrow.formatDynamicPath(i - 1).waitTillVisibilityOfElement(60);
				pondsLakesOnPropertyArrow.formatDynamicPath(i - 1).scrollToElement();
				pondsLakesOnPropertyArrow.formatDynamicPath(i - 1).click();
				pondsLakesOnPropertyOption.formatDynamicPath(i - 1, data.get("L" + i + "-OwnsPondsorLakesInProperty"))
						.waitTillVisibilityOfElement(60);
				pondsLakesOnPropertyOption.formatDynamicPath(i - 1, data.get("L" + i + "-OwnsPondsorLakesInProperty"))
						.scrollToElement();
				pondsLakesOnPropertyOption.formatDynamicPath(i - 1, data.get("L" + i + "-OwnsPondsorLakesInProperty"))
						.click();
				Assertions.passTest("GL Information Page", "Are there Ponds or Lakes on Property Latest Value : "
						+ pondslakesData.formatDynamicPath(i - 1).getData());
			}
			if (!(data.get("L" + i + "-OwnsBeachesInProperty").equals(""))) {
				Assertions.addInfo("GL Information Page", "Are there Beaches on Property original Value : "
						+ beachesData.formatDynamicPath(i - 1).getData());
				beachesOnPropertyArrow.formatDynamicPath(i - 1).waitTillButtonIsClickable(60);
				beachesOnPropertyArrow.formatDynamicPath(i - 1).waitTillVisibilityOfElement(60);
				beachesOnPropertyArrow.formatDynamicPath(i - 1).scrollToElement();
				beachesOnPropertyArrow.formatDynamicPath(i - 1).click();
				beachesOnPropertyOption.formatDynamicPath(i - 1, data.get("L" + i + "-OwnsBeachesInProperty"))
						.waitTillVisibilityOfElement(60);
				beachesOnPropertyOption.formatDynamicPath(i - 1, data.get("L" + i + "-OwnsBeachesInProperty"))
						.scrollToElement();
				beachesOnPropertyOption.formatDynamicPath(i - 1, data.get("L" + i + "-OwnsBeachesInProperty")).click();
				Assertions.passTest("GL Information Page", "Are there Beaches on Property Latest Value : "
						+ beachesData.formatDynamicPath(i - 1).getData());
			}
			if (!(data.get("L" + i + "-SecurityGuards?").equals(""))) {
				Assertions.addInfo("GL Information Page",
						"Are there Security Guards original Value : " + guardsData.formatDynamicPath(i - 1).getData());
				securityGuardsOnPropertyArrow.formatDynamicPath(i - 1).waitTillButtonIsClickable(60);
				securityGuardsOnPropertyArrow.formatDynamicPath(i - 1).waitTillVisibilityOfElement(60);
				securityGuardsOnPropertyArrow.formatDynamicPath(i - 1).scrollToElement();
				securityGuardsOnPropertyArrow.formatDynamicPath(i - 1).click();
				securityGuardsOnPropertyOption.formatDynamicPath(i - 1, data.get("L" + i + "-SecurityGuards?"))
						.waitTillVisibilityOfElement(60);
				securityGuardsOnPropertyOption.formatDynamicPath(i - 1, data.get("L" + i + "-SecurityGuards?"))
						.scrollToElement();
				securityGuardsOnPropertyOption.formatDynamicPath(i - 1, data.get("L" + i + "-SecurityGuards?")).click();
				Assertions.passTest("GL Information Page",
						"Are there Security Guards Latest Value : " + guardsData.formatDynamicPath(i - 1).getData());
			}
			if (!(data.get("L" + i + "-PropertyhavePrivateAirport").equals(""))) {
				Assertions.addInfo("GL Information Page",
						"Does Property have Private Airport,Dump or Landfill original Value : "
								+ privateAirportData.formatDynamicPath(i - 1).getData());
				airportNearPropertyArrow.formatDynamicPath(i - 1).waitTillButtonIsClickable(60);
				airportNearPropertyArrow.formatDynamicPath(i - 1).waitTillVisibilityOfElement(60);
				airportNearPropertyArrow.formatDynamicPath(i - 1).scrollToElement();
				airportNearPropertyArrow.formatDynamicPath(i - 1).click();
				airportNearPropertyOption.formatDynamicPath(i - 1, data.get("L" + i + "-PropertyhavePrivateAirport"))
						.waitTillVisibilityOfElement(60);
				airportNearPropertyOption.formatDynamicPath(i - 1, data.get("L" + i + "-PropertyhavePrivateAirport"))
						.scrollToElement();
				airportNearPropertyOption.formatDynamicPath(i - 1, data.get("L" + i + "-PropertyhavePrivateAirport"))
						.click();
				Assertions.passTest("GL Information Page",
						"Does Property have Private Airport,Dump or Landfill Latest Value : "
								+ privateAirportData.formatDynamicPath(i - 1).getData());
			}
			if (!(data.get("L" + i + "-LeaseTermsAllowed").equals(""))) {
				Assertions.addInfo("GL Information Page",
						"Are lease terms allowed for less than 6 months to tenants original Value : "
								+ leaseTermsData.formatDynamicPath(i - 1).getData());
				if (termLeaseLessThan6MonthsArrow.formatDynamicPath(i - 1).checkIfElementIsPresent()
						&& termLeaseLessThan6MonthsArrow.formatDynamicPath(i - 1).checkIfElementIsDisplayed()) {
					termLeaseLessThan6MonthsArrow.formatDynamicPath(i - 1).waitTillButtonIsClickable(60);
					termLeaseLessThan6MonthsArrow.formatDynamicPath(i - 1).waitTillVisibilityOfElement(60);
					termLeaseLessThan6MonthsArrow.formatDynamicPath(i - 1).scrollToElement();
					termLeaseLessThan6MonthsArrow.formatDynamicPath(i - 1).click();
					termLeaseLessThan6MonthsOption.formatDynamicPath(i - 1, data.get("L" + i + "-LeaseTermsAllowed"))
							.waitTillVisibilityOfElement(60);
					termLeaseLessThan6MonthsOption.formatDynamicPath(i - 1, data.get("L" + i + "-LeaseTermsAllowed"))
							.scrollToElement();
					termLeaseLessThan6MonthsOption.formatDynamicPath(i - 1, data.get("L" + i + "-LeaseTermsAllowed"))
							.click();
					Assertions.passTest("GL Information Page",
							"Are lease terms allowed for less than 6 months to tenants Latest Value : "
									+ leaseTermsData.formatDynamicPath(i - 1).getData());
				}
			}
			if (!(data.get("L" + i + "-Morethan20%TenantsUGStudents").equals(""))) {
				Assertions.addInfo("GL Information Page",
						"Are more than 20% of the tenants undergraduate students original Value : "
								+ tenantsUGData.formatDynamicPath(i - 1).getData());
				if (students20percentArrow.formatDynamicPath(i - 1).checkIfElementIsPresent()
						&& students20percentArrow.formatDynamicPath(i - 1).checkIfElementIsDisplayed()) {
					students20percentArrow.formatDynamicPath(i - 1).waitTillButtonIsClickable(60);
					students20percentArrow.formatDynamicPath(i - 1).waitTillVisibilityOfElement(60);
					students20percentArrow.formatDynamicPath(i - 1).scrollToElement();
					students20percentArrow.formatDynamicPath(i - 1).click();
					students20percentOption
							.formatDynamicPath(i - 1, data.get("L" + i + "-Morethan20%TenantsUGStudents"))
							.scrollToElement();
					students20percentOption
							.formatDynamicPath(i - 1, data.get("L" + i + "-Morethan20%TenantsUGStudents")).click();
					Assertions.passTest("GL Information Page",
							"Are more than 20% of the tenants undergraduate students Latest Value : "
									+ tenantsUGData.formatDynamicPath(i - 1).getData());
				}
			}
			if (!(data.get("L" + i + "-AnyOutstandingBuildingCodesViolation").equals(""))) {
				Assertions.addInfo("GL Information Page",
						"Does the risk have any outstanding building codes violations original Value : "
								+ buildingViolationsData.formatDynamicPath(i - 1).getData());
				if (buildingViolationsArrow.formatDynamicPath(i - 1).checkIfElementIsPresent()
						&& buildingViolationsArrow.formatDynamicPath(i - 1).checkIfElementIsDisplayed()) {
					buildingViolationsArrow.formatDynamicPath(i - 1).waitTillButtonIsClickable(60);
					buildingViolationsArrow.formatDynamicPath(i - 1).waitTillVisibilityOfElement(60);
					buildingViolationsArrow.formatDynamicPath(i - 1).scrollToElement();
					buildingViolationsArrow.formatDynamicPath(i - 1).click();
					buildingViolationsOption
							.formatDynamicPath(i - 1, data.get("L" + i + "-AnyOutstandingBuildingCodesViolation"))
							.waitTillVisibilityOfElement(60);
					buildingViolationsOption
							.formatDynamicPath(i - 1, data.get("L" + i + "-AnyOutstandingBuildingCodesViolation"))
							.scrollToElement();
					buildingViolationsOption
							.formatDynamicPath(i - 1, data.get("L" + i + "-AnyOutstandingBuildingCodesViolation"))
							.click();
					Assertions.passTest("GL Information Page",
							"Does the risk have any outstanding building codes violations Latest Value : "
									+ buildingViolationsData.formatDynamicPath(i - 1).getData());
				}
			}
			if (!(data.get("L" + i + "-Morethan20%OwnedByNonOwners").equals(""))) {
				Assertions.addInfo("GL Information Page",
						"Are more than 20% of the units occupied by non-owners original Value : "
								+ nonOwnersData.formatDynamicPath(i - 1).getData());
				if (moreThan20PercentNonOwnersArrow.formatDynamicPath(i - 1).checkIfElementIsPresent()
						&& moreThan20PercentNonOwnersArrow.formatDynamicPath(i - 1).checkIfElementIsDisplayed()) {
					moreThan20PercentNonOwnersArrow.formatDynamicPath(i - 1).waitTillButtonIsClickable(60);
					moreThan20PercentNonOwnersArrow.formatDynamicPath(i - 1).waitTillVisibilityOfElement(60);
					moreThan20PercentNonOwnersArrow.formatDynamicPath(i - 1).scrollToElement();
					moreThan20PercentNonOwnersArrow.formatDynamicPath(i - 1).click();
					moreThan20PercentNonOwnersOption
							.formatDynamicPath(i - 1, data.get("L" + i + "-Morethan20%OwnedByNonOwners"))
							.scrollToElement();
					moreThan20PercentNonOwnersOption
							.formatDynamicPath(i - 1, data.get("L" + i + "-Morethan20%OwnedByNonOwners")).click();
					Assertions.passTest("GL Information Page",
							"Are more than 20% of the units occupied by non-owners Latest Value : "
									+ nonOwnersData.formatDynamicPath(i - 1).getData());
				}
			}
			if (!(data.get("L" + i + "-10%UnitsUnitsOwnedByInsuredOrDeveloper").equals(""))) {
				Assertions.addInfo("GL Information Page",
						"Are more than 10% of the units owned by the primary Named Insured or the developer original Value : "
								+ ownedByInsuredData.formatDynamicPath(i - 1).getData());
				if (moreThan10PercentOwnersArrow.formatDynamicPath(i - 1).checkIfElementIsPresent()
						&& moreThan10PercentOwnersArrow.formatDynamicPath(i - 1).checkIfElementIsDisplayed()) {
					moreThan10PercentOwnersArrow.formatDynamicPath(i - 1).waitTillButtonIsClickable(60);
					moreThan10PercentOwnersArrow.formatDynamicPath(i - 1).waitTillVisibilityOfElement(60);
					moreThan10PercentOwnersArrow.formatDynamicPath(i - 1).scrollToElement();
					moreThan10PercentOwnersArrow.formatDynamicPath(i - 1).click();
					moreThan10PercentOwnersOption
							.formatDynamicPath(i - 1, data.get("L" + i + "-10%UnitsUnitsOwnedByInsuredOrDeveloper"))
							.scrollToElement();
					moreThan10PercentOwnersOption
							.formatDynamicPath(i - 1, data.get("L" + i + "-10%UnitsUnitsOwnedByInsuredOrDeveloper"))
							.click();
					Assertions.passTest("GL Information Page",
							"Are more than 10% of the units owned by the primary Named Insured or the developer Latest Value : "
									+ ownedByInsuredData.formatDynamicPath(i - 1).getData());
				}
			}
			if (!(data.get("L" + i + "-AssociationMembershipsVoluntary").equals(""))) {
				Assertions.addInfo("GL Information Page", "Are association memberships voluntary original Value : "
						+ membershipVoluntaryData.formatDynamicPath(i - 1).getData());
				if (voluntaryMembersArrow.formatDynamicPath(i - 1).checkIfElementIsPresent()
						&& voluntaryMembersArrow.formatDynamicPath(i - 1).checkIfElementIsDisplayed()) {
					voluntaryMembersArrow.formatDynamicPath(i - 1).waitTillButtonIsClickable(60);
					voluntaryMembersArrow.formatDynamicPath(i - 1).waitTillVisibilityOfElement(60);
					voluntaryMembersArrow.formatDynamicPath(i - 1).scrollToElement();
					voluntaryMembersArrow.formatDynamicPath(i - 1).click();
					voluntaryMembersOption
							.formatDynamicPath(i - 1, data.get("L" + i + "-AssociationMembershipsVoluntary"))
							.scrollToElement();
					voluntaryMembersOption
							.formatDynamicPath(i - 1, data.get("L" + i + "-AssociationMembershipsVoluntary")).click();
					Assertions.passTest("GL Information Page", "Are association memberships voluntary Latest Value : "
							+ membershipVoluntaryData.formatDynamicPath(i - 1).getData());
				}
			}
		}
		if (continueEndorsementButton.checkIfElementIsPresent()
				&& continueEndorsementButton.checkIfElementIsDisplayed()) {
			continueEndorsementButton.scrollToElement();
			continueEndorsementButton.click();
		}

	}
}
