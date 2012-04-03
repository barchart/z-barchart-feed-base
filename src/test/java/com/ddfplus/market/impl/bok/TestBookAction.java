package com.ddfplus.market.impl.bok;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.barchart.util.math.MathExtra;
import com.ddfplus.feed.api.market.enums.MarketBookAction;

public class TestBookAction {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testOf() {

		for (final MarketBookAction act : MarketBookAction.values()) {
			final byte ord = MathExtra.castIntToByte(act.ordinal());
			assertEquals(act, MarketBookAction.fromOrd(ord));
		}

	}

}
