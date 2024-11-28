package com.icat.epicenter.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.NetServAutomationFramework.config.ConfigException;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.ReportsManager;
import com.NetServAutomationFramework.generic.WebDriverManager;
import com.NetServAutomationFramework.generic.excel.SheetMatchedAccessManager;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.config.TestType;
import com.icat.epicenter.utils.ExcelReportUtil;

public abstract class AbstractDataTest extends AbstractTest {
	private String testIdPrefix;
	private List<Map<String, String>> testSetup;

	public AbstractDataTest(TestType testType, LoginType loginType, String testIdPrefix) {
		super(testType, loginType);
		this.testIdPrefix = testIdPrefix;
	}

	/*@BeforeClass
	public void setup(ITestContext context) {
		parentTest = ReportsManager.startTest(context, this.getClass().getSimpleName(), "Running data tests");
	}

	@AfterClass
	public void cleanup() {
		ReportsManager.endTest(this.getClass().getName());
	}*/

	@DataProvider(name = "ExecutionData",parallel=true)
	public Object[] getTestsToRun() {
		try {
			// load data for tests
			testSetup = loadTestSetup();
			checkSetup(testSetup);
			List<String> testList = getTestsToRun(testSetup.get(getSetupColumnNumber()), System.getProperty("scenarios"));
			return testList.toArray();
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
		return new Object[0];
	}

	/**
	 * Entry point for test.  First, load the data associated with the test.
	 * Next, let the implementing class(es) do whatever setup they need.
	 * Set up reporting.
	 * Finally, tell the test to execute itself.
	 */
	@Test(dataProvider = "ExecutionData", timeOut = 600000)
	public void runTest(ITestContext context, String testName) {
		try {
			// data for the test
			String sheetName = getSheetName(testName, testSetup);
			List<Map<String, String>> testData = getTestData(sheetName, testName);

			// set up report manager
			ExcelReportUtil.initialize(getTestType());
			ReportsManager.startTest(context, testName, testData.get(0).get("TestDescription"));
			Assertions.passTest("Test Setup", testName + " execution started");
			//WebDriverManager.launchBrowser();

			System.out.println("testName = " + testName);

			// run the test
			execute(testData);
		}
		catch (ConfigException ex) {
			ex.printStackTrace();
			Assertions.failTest("Test Setup", ex.getMessage());
			Assert.fail(ex.getMessage());
		}
		catch (Throwable t) {
			t.printStackTrace();
			Assert.fail(t.getMessage());
		}
		finally {
			// test ended; stop report manager
			System.out.println(this.getClass().getName() + " execution ended for test " + testName);
			WebDriverManager.closeCurrentBrowser();
			ReportsManager.endTest(this.getClass().getName());
		}
	}

	protected abstract int getSetupColumnNumber();
	protected abstract String getSheetName(String testName, List<Map<String, String>> testSetup);
	protected abstract void execute(List<Map<String, String>> testData);

	protected List<Map<String, String>> loadTestSetup() {
		SheetMatchedAccessManager testSetupSheet = new SheetMatchedAccessManager(getDataFilePath(), "Setup");
		return testSetupSheet.readExcelColumnWise();
	}

	/**
	 * Load the data for the given test on the given sheet.
	 * @throws ConfigException if test data cannot be found
	 */
	public List<Map<String, String>> getTestData(String sheetName, String test) throws ConfigException {
		List<Map<String, String>> sheetData = new SheetMatchedAccessManager(getDataFilePath(), sheetName).readExcelColumnWise();
		List<Map<String, String>> testData = new ArrayList<>();
		for (int i = 0; i < sheetData.size(); i++) {
			if (sheetData.get(i).get("TCID").equalsIgnoreCase(test)) {
				testData.add(sheetData.get(i));
			}
		}
		if (testData.size() < 1) {
			throw new ConfigException("Data not found for test='" + test + "', sheetName='" + sheetName + "'");
		}

		// add 31% flag if not set
		for (Map<String,String> data : testData) {
			checkFor31Percent(data);
		}

		return testData;
	}

	/**
	 * Build a list of tests to run.
	 * @param testSetup the test setup
	 * @param scenarios a comma separated of list of tests or test ranges
	 * @return a list of tests to run
	 * @throws ConfigException if unable to parse tests
	 */
	private List<String> getTestsToRun(Map<String, String> testSetup, String scenarios) throws ConfigException {
		List<String> testList = new ArrayList<>();

		if (StringUtils.isNotBlank(scenarios)) {
			// build list from given scenarios
			String[] testRanges = scenarios.split(",");
			for (String testRange : testRanges) {
				try {
					if (testRange.contains("-")) {
						String[] testStartStop = testRange.split("-");
						if (testStartStop.length != 2) {
							throw new ConfigException("Unrecogized test range: " + testRange);
						}
						for (int testNum = Integer.parseInt(testStartStop[0]); testNum <= Integer.parseInt(testStartStop[1]); testNum++) {
							addTest(testNum, testList, testSetup);
						}
					}
					else {
						addTest(Integer.parseInt(testRange), testList, testSetup);
					}
				}
				catch (NumberFormatException ex) {
					throw new ConfigException("Unable to parse test range: " + testRange);
				}
			}
		}
		else {
			int testNum = 1;
			String test = testIdPrefix + testNum;
			while (testSetup.containsKey(test)) {
				if (testSetup.get(test).equalsIgnoreCase("yes")) {
					testList.add(test);
				}
				testNum++;
				test = testIdPrefix + testNum;
			}
		}
		return testList;
	}

	/**
	 * If the test is in the setup, add it to the list.
	 */
	private void addTest(int testNum, List<String> testList, Map<String, String> testSetup) {
		String test = testIdPrefix + testNum;
		if (testSetup.containsKey(test)) {
			testList.add(test);
		}
		else {
			System.err.println("Test '" + test + "' not found in test setup");
		}
	}

	/**
	 * Verify the data file is configured properly.
	 * @throws ConfigException If the data file isn't set up correctly.
	 */
	private void checkSetup(List<Map<String, String>> testSetup) throws ConfigException {
		if (testSetup == null) {
			throw new ConfigException("Unable to load test data from '" + getDataFilePath() + "'");
		}

	}

	/**
	 * Calculate > 31% if the spreadsheet doesn't have *-BuildingMorethan31%Occupied
	 */
	private void checkFor31Percent(Map<String,String> data) {
		int loc = 1;
		while (data.containsKey("L"+loc+"B1-PercentOccupied")) {
			int bld = 1;
			while (data.containsKey("L"+loc+"B"+bld+"-PercentOccupied")) {
				String key31 = "L"+loc+"B"+bld+"-BuildingMorethan31%Occupied";
				if (!data.containsKey(key31)) {
					String strVal = data.get("L"+loc+"B"+bld+"-PercentOccupied");
					try {
						int intVal = Integer.parseInt(strVal);
						data.put(key31, intVal>31?"Yes":"No");
					}
					catch (NumberFormatException nfe) {
						// just ignore it
					}
				}
				bld++;
			}
			loc++;
		}
	}
}
