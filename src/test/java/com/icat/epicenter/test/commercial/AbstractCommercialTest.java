package com.icat.epicenter.test.commercial;

import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.config.TestType;
import com.icat.epicenter.test.AbstractNonDataTest;

public abstract class AbstractCommercialTest extends AbstractNonDataTest {

	public AbstractCommercialTest(LoginType loginType) {
		super(TestType.COMMERCIAL, loginType);
	}

	@Override
	protected String getReportName() {
		return "Commercial Tests Report";
	}
}
