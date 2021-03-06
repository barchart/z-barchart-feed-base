/**
 * Copyright (C) 2011-2012 Barchart, Inc. <http://www.barchart.com/>
 *
 * All rights reserved. Licensed under the OSI BSD License.
 *
 * http://www.opensource.org/licenses/bsd-license.php
 */
package com.barchart.feed.base.market.provider;

import static com.barchart.util.values.provider.ValueBuilder.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.barchart.feed.base.instrument.MockDefinitionService;
import com.barchart.feed.base.instrument.api.DefinitionService;
import com.barchart.feed.base.instrument.values.MarketInstrument;
import com.barchart.feed.base.market.MockMaker;
import com.barchart.feed.base.market.MockMarketFactory;
import com.barchart.feed.base.market.api.Market;
import com.barchart.feed.base.market.api.MarketTaker;
import com.barchart.feed.base.market.enums.MarketEvent;
import com.barchart.feed.base.market.enums.MarketField;

public class TestMakerBase {

	DefinitionService service;

	@Before
	public void setUp() throws Exception {
		service = new MockDefinitionService();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRegisterMarketTaker() {

		final MockMaker maker = new MockMaker(new MockMarketFactory());

		final MarketInstrument inst;

		inst = service.lookup(newText("1"));

		maker.register(inst);
		assertEquals(maker.marketCount(), 1);

		{

			final MarketTaker<Market> taker = new MarketTaker<Market>() {
				@Override
				public MarketEvent[] bindEvents() {
					return null;
				}

				@Override
				public MarketField<Market> bindField() {
					return null;
				}

				@Override
				public MarketInstrument[] bindInstruments() {
					return null;
				}

				@Override
				public void onMarketEvent(final MarketEvent event,
						final MarketInstrument instrument, final Market value) {
				}
			};
			final boolean isAdded = maker.register(taker);
			assertFalse("should not register invalid taker", isAdded);

		}
		{

			final MarketTaker<Market> taker = new MarketTaker<Market>() {
				@Override
				public MarketEvent[] bindEvents() {
					return new MarketEvent[] {};
				}

				@Override
				public MarketField<Market> bindField() {
					return null;
				}

				@Override
				public MarketInstrument[] bindInstruments() {
					return null;
				}

				@Override
				public void onMarketEvent(final MarketEvent event,
						final MarketInstrument instrument, final Market value) {
				}
			};
			final boolean isAdded = maker.register(taker);
			assertFalse("should not register invalid taker", isAdded);

		}

		{

			final MarketTaker<Market> taker = new MarketTaker<Market>() {
				@Override
				public MarketEvent[] bindEvents() {
					return new MarketEvent[] { MarketEvent.MARKET_UPDATED };
				}

				@Override
				public MarketField<Market> bindField() {
					return null;
				}

				@Override
				public MarketInstrument[] bindInstruments() {
					return null;
				}

				@Override
				public void onMarketEvent(final MarketEvent event,
						final MarketInstrument instrument, final Market value) {
				}
			};
			final boolean isAdded = maker.register(taker);
			assertFalse(isAdded);

		}

		{
			final MarketTaker<Market> taker = new MarketTaker<Market>() {
				@Override
				public MarketEvent[] bindEvents() {
					return new MarketEvent[] { MarketEvent.MARKET_UPDATED };
				}

				@Override
				public MarketField<Market> bindField() {
					return MarketField.MARKET;
				}

				@Override
				public MarketInstrument[] bindInstruments() {
					return null;
				}

				@Override
				public void onMarketEvent(final MarketEvent event,
						final MarketInstrument instrument, final Market value) {
				}
			};
			final boolean isAdded = maker.register(taker);
			assertFalse("should not register invalid taker", isAdded);

		}

		{
			final MarketTaker<Market> taker = new MarketTaker<Market>() {
				@Override
				public MarketEvent[] bindEvents() {
					return new MarketEvent[] { MarketEvent.MARKET_UPDATED };
				}

				@Override
				public MarketField<Market> bindField() {
					return MarketField.MARKET;
				}

				@Override
				public MarketInstrument[] bindInstruments() {
					return new MarketInstrument[] {};
				}

				@Override
				public void onMarketEvent(final MarketEvent event,
						final MarketInstrument instrument, final Market value) {
				}
			};
			final boolean isAdded = maker.register(taker);
			assertTrue("ok to have non null but empty fields", isAdded);

		}

		{
			final MarketTaker<Market> taker = new MarketTaker<Market>() {
				@Override
				public MarketEvent[] bindEvents() {
					return new MarketEvent[] { MarketEvent.MARKET_UPDATED };
				}

				@Override
				public MarketField<Market> bindField() {
					return MarketField.MARKET;
				}

				@Override
				public MarketInstrument[] bindInstruments() {
					return new MarketInstrument[] { inst };
				}

				@Override
				public void onMarketEvent(final MarketEvent event,
						final MarketInstrument instrument, final Market value) {
				}
			};
			final boolean isAdded = maker.register(taker);
			assertTrue(isAdded);
		}

	}

	@Test
	public void testRegisterMarketInstrument() {

		final MockMaker maker = new MockMaker(new MockMarketFactory());

		MarketInstrument inst;

		inst = service.lookup(newText("1"));

		maker.register(inst);
		assertEquals(maker.marketCount(), 1);

		maker.register(inst);
		assertEquals(maker.marketCount(), 1);

		maker.register(inst);
		assertEquals(maker.marketCount(), 1);

		//

		maker.unregister(inst);
		assertEquals(maker.marketCount(), 0);

		maker.unregister(inst);
		assertEquals(maker.marketCount(), 0);

	}

}
