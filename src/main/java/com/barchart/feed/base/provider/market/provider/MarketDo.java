/**
 * Copyright (C) 2011-2012 Barchart, Inc. <http://www.barchart.com/>
 *
 * All rights reserved. Licensed under the OSI BSD License.
 *
 * http://www.opensource.org/licenses/bsd-license.php
 */
package com.barchart.feed.base.provider.market.provider;

import java.util.List;
import java.util.Set;

import com.barchart.feed.base.api.instrument.values.MarketInstrument;
import com.barchart.feed.base.api.market.enums.MarketBarType;
import com.barchart.feed.base.api.market.enums.MarketEvent;
import com.barchart.feed.base.api.market.enums.MarketField;
import com.barchart.feed.base.api.market.enums.MarketStateEntry;
import com.barchart.feed.base.api.market.values.Market;
import com.barchart.feed.base.api.market.values.MarketBar;
import com.barchart.util.anno.Mutable;
import com.barchart.util.values.api.PriceValue;
import com.barchart.util.values.api.SizeValue;
import com.barchart.util.values.api.TimeValue;

@Mutable
public interface MarketDo extends Market {

	/* EVENTS */

	void fireEvents();

	void regAdd(RegTaker<?> regTaker);

	void regRemove(RegTaker<?> regTaker);

	boolean hasRegTakers();

	List<RegTaker<?>> regList();

	Set<MarketEvent> regEvents();

	/* VALUES */

	/** one time instrument initialization */
	void setInstrument(MarketInstrument symbol);

	/**  */
	void setBookUpdate(MarketDoBookEntry entry, TimeValue time);

	/**  */
	void setBookSnapshot(MarketDoBookEntry[] entries, TimeValue time);

	/**  */
	void setCuvolUpdate(MarketDoCuvolEntry entry, TimeValue time);

	/**  */
	void setCuvolSnapshot(MarketDoCuvolEntry[] entries, TimeValue time);

	/**  */
	void setBar(MarketBarType type, MarketDoBar bar);

	/**  */
	void setTrade(MarketBarType type, PriceValue price, SizeValue size,
			TimeValue time);

	//

	void setState(MarketStateEntry entry, boolean isOn);

	/* RUN SAFE */

	/** run task inside exclusive market context */
	<Result, Param> Result runSafe(MarketSafeRunner<Result, Param> task,
			Param param);

	//

	MarketDoBar loadBar(MarketField<MarketBar> barField);

}