package com.icat.epicenter.test.debugging;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

/**
 * A simple class that does next to nothing to use to test the framework code.
 */
public class DoVeryLittle  extends AbstractCommercialTest {

	public DoVeryLittle() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/debugging/DummyData.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {
		Assertions.passTest("My simple test", "Executed Successfully");
	}
}
