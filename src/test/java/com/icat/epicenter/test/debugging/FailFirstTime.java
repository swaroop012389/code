package com.icat.epicenter.test.debugging;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

/**
 * For testing retry logic.  Test will fail the first time.
 */
public class FailFirstTime extends AbstractCommercialTest {
	private static boolean firstRun = true;
	
	public FailFirstTime() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/debugging/DummyData.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {
		Assertions.passTest("Step 1", "This is step one which passes.");
		Assertions.verify(isFirstRun(), false, "Step 2", "This is step 2, checking to see if this is the first run.", false, true);
		Assertions.passTest("Step 3", "The test has finished successfully.");
	}
	
	private synchronized boolean isFirstRun() {
		System.out.println("------------> firstRun = " + firstRun);
		if (firstRun) {
			firstRun = false;
			return true;
		}
		return false;
	}
}
