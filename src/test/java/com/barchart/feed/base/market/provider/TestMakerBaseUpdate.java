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

import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barchart.feed.base.instrument.MockDefinitionService;
import com.barchart.feed.base.instrument.api.DefinitionService;
import com.barchart.feed.base.instrument.values.MarketInstrument;
import com.barchart.feed.base.market.MockMaker;
import com.barchart.feed.base.market.MockMarketFactory;
import com.barchart.feed.base.market.api.Market;
import com.barchart.feed.base.market.api.MarketDo;
import com.barchart.feed.base.market.api.MarketRegListener;
import com.barchart.feed.base.market.api.MarketTaker;
import com.barchart.feed.base.market.enums.MarketEvent;
import com.barchart.feed.base.market.enums.MarketField;

public class TestMakerBaseUpdate {

	static final Logger log = LoggerFactory
			.getLogger(TestMakerBaseUpdate.class);

	static String asString(final Set<?> set) {
		final StringBuilder text = new StringBuilder("{");
		String sep = "";
		for (final Object object : set) {
			text.append(sep).append(object.toString());
			sep = ",";
		}
		return text.append("}").toString();
	}

	private DefinitionService<MarketInstrument> service;

	private MarketRegListener regListenter;

	@Before
	public void setUp() throws Exception {

		service = new MockDefinitionService();

		regListenter = new MarketRegListener() {

			@Override
			public void onRegistrationChange(final MarketInstrument inst,
					final Set<MarketEvent> events) {

				log.debug("inst : {} ; events : {}", inst, asString(events));

			}

		};

	}

	@After
	public void tearDown() throws Exception {
	}

	private MarketField<?> field;
	private MarketEvent[] eventArray;
	private MarketInstrument[] instArray;

	private final MarketTaker<?> taker = new MarketTaker<Market>() {
		@Override
		public MarketEvent[] bindEvents() {
			return eventArray;
		}

		@SuppressWarnings("unchecked")
		@Override
		public MarketField<Market> bindField() {
			return (MarketField<Market>) field;
		}

		@Override
		public MarketInstrument[] bindInstruments() {
			return instArray;
		}

		@Override
		public void onMarketEvent(final MarketEvent event,
				final MarketInstrument inst, final Market value) {
		}
	};

	@Test
	public void testRegisterMarketTaker() {

		final MockMaker maker = new MockMaker(new MockMarketFactory());

		maker.add(regListenter);

		// 3 different instruments
		final MarketInstrument inst1 = service.lookup(newText("1"));
		final MarketInstrument inst2 = service.lookup(newText("2"));
		final MarketInstrument inst3 = service.lookup(newText("3"));

		//

		final MarketEvent event1 = MarketEvent.MARKET_CLOSED;
		final MarketEvent event2 = MarketEvent.MARKET_OPENED;

		//

		log.debug("step 1");

		field = MarketField.BAR_CURRENT;
		instArray = new MarketInstrument[] { inst1, inst2 }; // #2 is present
		eventArray = MarketEvent.in(event1);

		// original registration
		assertTrue(maker.register(taker));

		//

		final RegTaker<?> regTaker = maker.getRegTaker(taker);

		//

		final MarketDo market = maker.getMarket(inst2);

		final List<RegTaker<?>> regList = market.regList();

		assertTrue(regList.contains(regTaker));

		final Set<MarketEvent> regEvents1 = market.regEvents();

		assertTrue(regEvents1.contains(event1));
		assertEquals(regEvents1.size(), 1);

		//

		assertTrue(maker.isRegistered(inst1));
		assertTrue(maker.isRegistered(inst2));
		assertEquals(maker.marketCount(), 2);

		assertEquals(field, regTaker.getField());
		assertArrayEquals(instArray, regTaker.getInstruments());

		//

		log.debug("step 2");

		field = MarketField.BAR_PREVIOUS;
		instArray = new MarketInstrument[] { inst2, inst3 }; // #2 is present
		eventArray = MarketEvent.in(event2); // event1 != event2

		maker.update(taker); // XXX bind again

		assertTrue(maker.isRegistered(inst2));
		assertTrue(maker.isRegistered(inst3));
		assertEquals(maker.marketCount(), 2);

		assertEquals(field, regTaker.getField());
		assertArrayEquals(instArray, regTaker.getInstruments());

		final Set<MarketEvent> regEvents2 = market.regEvents();

		assertTrue(regEvents2.contains(event2));
		assertEquals(regEvents2.size(), 1);

		//

		log.debug("step 3");

		maker.unregister(taker);

		assertEquals(maker.marketCount(), 0);

	}

}
