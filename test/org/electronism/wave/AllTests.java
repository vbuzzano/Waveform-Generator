package org.electronism.wave;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.electronism.wave");
		//$JUnit-BEGIN$
		suite.addTestSuite(WaveDataTests.class);
		//$JUnit-END$
		return suite;
	}

}
