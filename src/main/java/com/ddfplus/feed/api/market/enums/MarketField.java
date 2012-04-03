package com.ddfplus.feed.api.market.enums;

import static com.barchart.util.values.provider.ValueConst.*;
import static com.ddfplus.feed.common.instrument.provider.InstrumentConst.*;
import static com.ddfplus.feed.common.market.provider.MarketConst.*;

import com.barchart.util.anno.NotMutable;
import com.barchart.util.enums.DictEnum;
import com.barchart.util.enums.ParaEnumBase;
import com.barchart.util.values.api.TimeValue;
import com.barchart.util.values.api.Value;
import com.ddfplus.feed.api.instrument.values.MarketInstrument;
import com.ddfplus.feed.api.market.values.Market;
import com.ddfplus.feed.api.market.values.MarketBar;
import com.ddfplus.feed.api.market.values.MarketBook;
import com.ddfplus.feed.api.market.values.MarketBookEntry;
import com.ddfplus.feed.api.market.values.MarketBookTop;
import com.ddfplus.feed.api.market.values.MarketCuvol;
import com.ddfplus.feed.api.market.values.MarketCuvolEntry;
import com.ddfplus.feed.api.market.values.MarketState;
import com.ddfplus.feed.api.market.values.MarketTrade;
import com.ddfplus.feed.common.util.collections.BitSetEnum;

@NotMutable
public final class MarketField<V extends Value<V>> extends
		ParaEnumBase<V, MarketField<V>> implements BitSetEnum<MarketField<?>> {

	// ##################################

	/** primary key */
	public static final MarketField<MarketInstrument> INSTRUMENT = NEW(NULL_INSTRUMENT);

	/** last time any change of any market field */
	public static final MarketField<TimeValue> MARKET_TIME = NEW(NULL_TIME);

	/** last trade, any source */
	public static final MarketField<MarketTrade> TRADE = NEW(NULL_TRADE);

	/** market depth, a.k.a book */
	public static final MarketField<MarketBook> BOOK = NEW(NULL_BOOK);

	/** proxy to last updated entry */
	public static final MarketField<MarketBookEntry> BOOK_LAST = NEW(NULL_BOOK_ENTRY);

	/** proxy to market depth top bid & top ask */
	public static final MarketField<MarketBookTop> BOOK_TOP = NEW(NULL_BOOK_TOP);

	/** cumulative volume - price / size ladder */
	public static final MarketField<MarketCuvol> CUVOL = NEW(NULL_CUVOL);

	/** proxy to last updated entry in cumulative volume */
	public static final MarketField<MarketCuvolEntry> CUVOL_LAST = NEW(NULL_CUVOL_ENTRY);

	// market bars aka sessions

	/** current or default or combo = electronic + manual */
	public static final MarketField<MarketBar> BAR_CURRENT = NEW(NULL_BAR);

	/** electronic TODO remove */
	public static final MarketField<MarketBar> BAR_CURRENT_NET = NEW(NULL_BAR);

	/** manual / pit TODO remove */
	public static final MarketField<MarketBar> BAR_CURRENT_PIT = NEW(NULL_BAR);

	/** extra / stocks form t */
	public static final MarketField<MarketBar> BAR_CURRENT_EXT = NEW(NULL_BAR);

	/** past - previous day; default or combo */
	public static final MarketField<MarketBar> BAR_PREVIOUS = NEW(NULL_BAR);

	// MarketState
	public static final MarketField<MarketState> STATE = NEW(NULL_STATE);

	//

	/** market container with all market fields (self reference) */
	// NOTE: keep market last
	public static final MarketField<Market> MARKET = NEW(NULL_MARKET);

	// ##################################

	public static int size() {
		return values().length;
	}

	public static MarketField<?>[] values() {
		return DictEnum.valuesForType(MarketField.class);
	}

	//

	private final long mask;

	@Override
	public long mask() {
		return mask;
	}

	private MarketField() {
		super();
		mask = 0;
	}

	private MarketField(final V value) {
		super("", value);
		mask = ONE << ordinal();
	}

	private static final <X extends Value<X>> MarketField<X> NEW(final X value) {
		return new MarketField<X>(value);
	}

}
