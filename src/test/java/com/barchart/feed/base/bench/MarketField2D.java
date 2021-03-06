/**
 * Copyright (C) 2011-2012 Barchart, Inc. <http://www.barchart.com/>
 *
 * All rights reserved. Licensed under the OSI BSD License.
 *
 * http://www.opensource.org/licenses/bsd-license.php
 */
package com.barchart.feed.base.bench;

import static com.barchart.feed.base.instrument.api.InstrumentConst.*;
import static com.barchart.feed.base.provider.MarketConst.*;
import static com.barchart.util.values.provider.ValueConst.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barchart.feed.base.bar.api.MarketBar;
import com.barchart.feed.base.book.api.MarketBook;
import com.barchart.feed.base.book.api.MarketBookTop;
import com.barchart.feed.base.cuvol.api.MarketCuvol;
import com.barchart.feed.base.instrument.values.MarketInstrument;
import com.barchart.feed.base.market.api.Market;
import com.barchart.feed.base.trade.api.MarketTrade;
import com.barchart.util.anno.NotMutable;
import com.barchart.util.collections.BitSetEnum;
import com.barchart.util.enums.ParamEnumBase2D;
import com.barchart.util.values.api.PriceValue;
import com.barchart.util.values.api.SizeValue;
import com.barchart.util.values.api.TimeValue;
import com.barchart.util.values.api.Value;

@NotMutable
public class MarketField2D<V extends Value<V>> extends
		ParamEnumBase2D<V, MarketField2D<V>> implements
		BitSetEnum<MarketField2D<?>> {

	private static final Logger log = LoggerFactory
			.getLogger(MarketField2D.class);

	// keep first
	private static int ORDINAL;
	private static int ROW;
	private static int COL;
	//
	private static final int SIZE;
	private static final MarketField2D<?>[] VALUES;
	//
	static {
		SIZE = countEnumFields(MarketField2D.class);
		VALUES = new MarketField2D<?>[SIZE];
		log.info("market fields count : {}", SIZE);
	}

	private static final int enumSizeAll = SIZE;

	public static final int sizeAll() {
		return enumSizeAll; // 
	}

	private static final int enumSizeContaners = 1;

	private static final int enumSizeItems = enumSizeAll - enumSizeContaners;

	public static final int sizeItems() {
		return enumSizeItems; // 
	}

	//

	public static final MarketField2D<?>[] values() {
		// new instance
		return VALUES.clone();
	}

	@Deprecated
	public final static MarketField2D<?>[] valuesUnsafe() {
		// same instance
		return VALUES;
	}

	private final long mask;

	@Override
	public long mask() {
		return mask;
	}

	private MarketField2D(final V defVal, final int row, final int col) {
		super(VALUES, ORDINAL++, defVal, row, col);
		mask = ONE << ordinal();
	}

	private static final <X extends Value<X>> MarketField2D<X> NEW(
			final X defaultValue, final int row, final int col) {
		assert defaultValue != null;
		// log.info("### sequence={} value={}", sequence, value);
		return new MarketField2D<X>(defaultValue, row, col);
	}

	private static final void nextRow() {
		ROW++;
		COL = 0;
	}

	// ##################################

	// NOTE: items keep before containers

	//
	public static final MarketField2D<MarketInstrument> //
	INSTRUMENT = NEW(NULL_INSTRUMENT, ROW++, 0);

	// market depth
	public static final MarketField2D<MarketBook> //
	BOOK = NEW(NULL_BOOK, ROW++, 0);
	// proxy to market depth
	public static final MarketField2D<MarketBookTop> //
	BOOK_TOP = NEW(NULL_BOOK_TOP, ROW++, 0);

	// cumulative volume
	public static final MarketField2D<MarketCuvol> //
	CUVOL = NEW(NULL_CUVOL, ROW++, 0);

	// proxy to last trade
	public static final MarketField2D<MarketTrade> TRADE = NEW(NULL_TRADE, ROW,
			COL++);
	// last trade
	public static final MarketField2D<PriceValue> TRADE_PRICE = NEW(NULL_PRICE,
			ROW, COL++);
	public static final MarketField2D<SizeValue> TRADE_SIZE = NEW(NULL_SIZE,
			ROW, COL++);
	public static final MarketField2D<TimeValue> TRADE_TIME = NEW(NULL_TIME,
			ROW, COL++);

	static {
		nextRow();
	}

	// proxy to current bar
	public static final MarketField2D<MarketBar> BAR_CURRENT = NEW(NULL_BAR,
			ROW, COL++);
	// current bar
	public static final MarketField2D<PriceValue> OPEN = NEW(NULL_PRICE, ROW,
			COL++);
	public static final MarketField2D<PriceValue> HIGH = NEW(NULL_PRICE, ROW,
			COL++);
	public static final MarketField2D<PriceValue> LOW = NEW(NULL_PRICE, ROW,
			COL++);
	public static final MarketField2D<PriceValue> CLOSE = NEW(NULL_PRICE, ROW,
			COL++);
	public static final MarketField2D<PriceValue> SETTLE = NEW(NULL_PRICE, ROW,
			COL++);
	public static final MarketField2D<SizeValue> VOLUME = NEW(NULL_SIZE, ROW,
			COL++);
	public static final MarketField2D<SizeValue> INTEREST = NEW(NULL_SIZE, ROW,
			COL++);
	public static final MarketField2D<TimeValue> TIME = NEW(NULL_TIME, ROW,
			COL++);

	static {
		nextRow();
	}

	public static final MarketField2D<MarketBar> BAR_PREVIOUS = NEW(NULL_BAR,
			ROW, COL++);
	public static final MarketField2D<PriceValue> PREV_OPEN = NEW(NULL_PRICE,
			ROW, COL++);
	public static final MarketField2D<PriceValue> PREV_HIGH = NEW(NULL_PRICE,
			ROW, COL++);
	public static final MarketField2D<PriceValue> PREV_LOW = NEW(NULL_PRICE,
			ROW, COL++);
	public static final MarketField2D<PriceValue> PREV_CLOSE = NEW(NULL_PRICE,
			ROW, COL++);
	public static final MarketField2D<PriceValue> PREV_SETTLE = NEW(NULL_PRICE,
			ROW, COL++);
	public static final MarketField2D<SizeValue> PREV_VOLUME = NEW(NULL_SIZE,
			ROW, COL++);
	public static final MarketField2D<SizeValue> PREV_INTEREST = NEW(NULL_SIZE,
			ROW, COL++);
	public static final MarketField2D<TimeValue> PREV_TIME = NEW(NULL_TIME,
			ROW, COL++);

	static {
		nextRow();
	}

	// ##################################

	// NOTE: keep containers after items

	// market container with all above fields (self reference)
	public static final MarketField2D<Market> MARKET = NEW(NULL_MARKET, ROW,
			COL++);

	// ##################################

	/*
	 * NOTE: keep last; this hack is needed since MarketField and MarketConst
	 * are co-dependent
	 */
	// static {
	// MarketConst.initTask();
	// }

}
