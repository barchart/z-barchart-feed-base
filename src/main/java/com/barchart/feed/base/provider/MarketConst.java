/**
 * Copyright (C) 2011-2012 Barchart, Inc. <http://www.barchart.com/>
 *
 * All rights reserved. Licensed under the OSI BSD License.
 *
 * http://www.opensource.org/licenses/bsd-license.php
 */
package com.barchart.feed.base.provider;

import static com.barchart.util.values.provider.ValueConst.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barchart.feed.base.bar.api.MarketBar;
import com.barchart.feed.base.book.api.MarketBook;
import com.barchart.feed.base.book.api.MarketBookEntry;
import com.barchart.feed.base.book.api.MarketBookTop;
import com.barchart.feed.base.cuvol.api.MarketCuvol;
import com.barchart.feed.base.cuvol.api.MarketCuvolEntry;
import com.barchart.feed.base.cuvol.map.api.MarketCuvolMap;
import com.barchart.feed.base.cuvol.map.provider.NulCuvolMap;
import com.barchart.feed.base.market.api.Market;
import com.barchart.feed.base.state.api.MarketState;
import com.barchart.feed.base.trade.api.MarketTrade;
import com.barchart.util.values.api.SizeValue;

public final class MarketConst {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory
			.getLogger(MarketConst.class);

	//

	public static final MarketCuvol NULL_CUVOL = //
	new NulCuvol().freeze();

	public static final MarketCuvolMap NULL_CUVOL_MAP = //
	new NulCuvolMap().freeze();
	//

	public static final MarketTrade NULL_TRADE = //
	new NulTrade().freeze();

	//

	public static final MarketBookEntry NULL_BOOK_ENTRY = //
	new DefBookEntry(null, null, null, 0, null, null).freeze();

	public static final MarketCuvolEntry NULL_CUVOL_ENTRY = //
	new DefCuvolEntry(0, NULL_PRICE, NULL_SIZE).freeze();

	public static final MarketBookEntry[] NULL_ENTRIES = //
	new MarketBookEntry[] { NULL_BOOK_ENTRY };

	public static final SizeValue[] NULL_SIZES = //
	new SizeValue[0];

	public static final MarketBook NULL_BOOK = //
	new DefBook(NULL_TIME, NULL_ENTRIES, NULL_ENTRIES, NULL_SIZES, NULL_SIZES)
			.freeze();

	public static final MarketBookTop NULL_BOOK_TOP = //
	new DefBookTop(NULL_TIME, NULL_BOOK_ENTRY, NULL_BOOK_ENTRY).freeze();

	//

	public static final MarketBar NULL_BAR = //
	new NulBar().freeze();

	public static final Market NULL_MARKET = //
	new NulMarket().freeze();

	public static final MarketState NULL_STATE = //
	new DefState().freeze();

	//

	static {

		// ValueConst.sizeReport(MarketConst.class);

	}

}
