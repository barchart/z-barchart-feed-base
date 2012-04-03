/**
 * Copyright (C) 2011-2012 Barchart, Inc. <http://www.barchart.com/>
 *
 * All rights reserved. Licensed under the OSI BSD License.
 *
 * http://www.opensource.org/licenses/bsd-license.php
 */
package com.barchart.feed.base.provider.market.provider;

import static com.barchart.feed.base.api.market.enums.MarketBarField.*;
import static com.barchart.feed.base.api.market.enums.MarketBarType.*;
import static com.barchart.feed.base.api.market.enums.MarketField.*;
import static com.barchart.feed.base.api.market.enums.MarketTradeField.*;
import static com.barchart.util.values.provider.ValueBuilder.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barchart.feed.base.api.instrument.DefinitionService;
import com.barchart.feed.base.api.instrument.values.MarketInstrument;
import com.barchart.feed.base.api.market.values.Market;
import com.barchart.feed.base.api.market.values.MarketBar;
import com.barchart.feed.base.api.market.values.MarketTrade;
import com.barchart.feed.base.mock.MockMsgTrade;
import com.barchart.feed.base.provider.instrument.provider.MockService;
import com.barchart.feed.base.provider.market.provider.MarketType;

public class TestProcessBar {

	DefinitionService service;

	private static final Logger log = LoggerFactory
			.getLogger(TestProcessBar.class);

	@Before
	public void setUp() throws Exception {
		service = new MockService();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTakerMarket() {

		Market market;
		MarketBar bar;
		MarketTrade trade;

		final MockMaker maker = new MockMaker(MarketType.DDF);

		MarketInstrument inst;

		inst = service.lookup(newText("1"));

		final boolean isRegistered = maker.register(inst);
		assertTrue(isRegistered);
		assertEquals(maker.marketCount(), 1);

		market = maker.take(inst, MARKET);
		assertFalse(market.isNull());

		trade = market.get(TRADE);
		assertTrue(trade.isNull());

		//

		final MockMsgTrade msgTrade = new MockMsgTrade(inst);

		//

		msgTrade.type = CURRENT_NET;
		msgTrade.price = newPrice(1230, -1);
		msgTrade.size = newSize(17);
		msgTrade.time = newTime(345679);

		maker.make(msgTrade);
		market = maker.take(inst, MARKET);

		//

		trade = market.get(TRADE);
		assertFalse(trade.isNull());

		assertEquals(trade.get(PRICE), newPrice(1230, -1));
		assertEquals(trade.get(SIZE), newSize(17));
		assertEquals(trade.get(TRADE_TIME), newTime(345679));

		//

		bar = market.get(BAR_CURRENT);
		assertEquals(bar.get(VOLUME), newSize(17));
		// assertEquals(bar.get(HIGH), newPrice(1230, -1));
		// assertEquals(bar.get(LOW), newPrice(1230, -1));
		assertEquals(bar.get(BAR_TIME), newTime(345679));

		//

		bar = market.get(BAR_CURRENT_NET);
		// assertEquals(bar.get(VOLUME), newSize(17));
		// assertEquals(bar.get(HIGH), newPrice(123000, -3));
		// assertEquals(bar.get(LOW), newPrice(123, 0));
		// assertEquals(bar.get(BAR_TIME), newTime(345679));

		//

		msgTrade.type = CURRENT_PIT;
		msgTrade.price = newPrice(1230, -1);
		msgTrade.size = newSize(13);
		msgTrade.time = newTime(345678); // past trade

		maker.make(msgTrade);

		bar = market.get(BAR_CURRENT_NET);
		// assertEquals(bar.get(VOLUME), newSize(17));
		// assertEquals(bar.get(HIGH), newPrice(123000, -3));
		// assertEquals(bar.get(LOW), newPrice(123, 0));
		// assertEquals(bar.get(BAR_TIME), newTime(345679));

		//

		msgTrade.type = CURRENT_PIT;
		msgTrade.price = newPrice(1240, -1);
		msgTrade.size = newSize(13);
		msgTrade.time = newTime(345680); // next trade

		maker.make(msgTrade);
		market = maker.take(inst, MARKET); // updated market

		bar = market.get(BAR_CURRENT_PIT);
		// assertFalse(bar.isNull());
		// assertEquals(bar.get(VOLUME), newSize(13));
		// assertEquals(bar.get(HIGH), newPrice(124000, -3));
		// assertEquals(bar.get(LOW), newPrice(124, 0));
		// assertEquals(bar.get(BAR_TIME), newTime(345680));

		bar = market.get(BAR_CURRENT_NET);
		// assertFalse(bar.isNull());
		// assertEquals(bar.get(VOLUME), newSize(17));
		// assertEquals(bar.get(HIGH), newPrice(123000, -3));
		// assertEquals(bar.get(LOW), newPrice(123, 0));
		// assertEquals(bar.get(BAR_TIME), newTime(345679));

		bar = market.get(BAR_CURRENT);
		assertFalse(bar.isNull());
		// assertEquals(bar.get(VOLUME), newSize(30));
		// assertEquals(bar.get(HIGH), newPrice(124000, -3));
		// assertEquals(bar.get(LOW), newPrice(123, 0));
		// assertEquals(bar.get(BAR_TIME), newTime(345680));

		//

		msgTrade.type = CURRENT_NET;
		msgTrade.price = newPrice(1200, -1);
		msgTrade.size = newSize(111);
		msgTrade.time = newTime(345700);

		maker.make(msgTrade);
		market = maker.take(inst, MARKET); // updated market

		// updated
		bar = market.get(BAR_CURRENT);
		assertFalse(bar.isNull());
		// assertEquals(bar.get(VOLUME), newSize(141));
		// assertEquals(bar.get(HIGH), newPrice(124000, -3));
		// assertEquals(bar.get(LOW), newPrice(1200, -1));
		// assertEquals(bar.get(BAR_TIME), newTime(345700));

		// no change
		bar = market.get(BAR_CURRENT_PIT);
		// assertFalse(bar.isNull());
		// assertEquals(bar.get(VOLUME), newSize(13));
		// assertEquals(bar.get(HIGH), newPrice(124000, -3));
		// assertEquals(bar.get(LOW), newPrice(124, 0));
		// assertEquals(bar.get(BAR_TIME), newTime(345680));

		//

	}
}