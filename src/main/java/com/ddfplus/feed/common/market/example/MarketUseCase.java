package com.ddfplus.feed.common.market.example;

import static com.ddfplus.feed.api.market.enums.MarketBarField.*;
import static com.ddfplus.feed.api.market.enums.MarketEvent.*;
import static com.ddfplus.feed.api.market.enums.MarketField.*;

import com.barchart.util.values.api.PriceValue;
import com.barchart.util.values.api.SizeValue;
import com.ddfplus.feed.api.instrument.values.MarketInstrument;
import com.ddfplus.feed.api.market.MarketTaker;
import com.ddfplus.feed.api.market.enums.MarketBookSide;
import com.ddfplus.feed.api.market.enums.MarketEvent;
import com.ddfplus.feed.api.market.enums.MarketField;
import com.ddfplus.feed.api.market.enums.MarketTradeField;
import com.ddfplus.feed.api.market.provider.MarketMakerProvider;
import com.ddfplus.feed.api.market.values.Market;
import com.ddfplus.feed.api.market.values.MarketBar;
import com.ddfplus.feed.api.market.values.MarketBook;
import com.ddfplus.feed.api.market.values.MarketBookEntry;
import com.ddfplus.feed.api.market.values.MarketCuvol;
import com.ddfplus.feed.api.market.values.MarketTrade;
import com.ddfplus.feed.api.message.MarketMessage;

abstract class MarketUseCase {

	//

	// provider instance
	final MarketMakerProvider<MarketMessage> maker = null;

	{
		// new message came from transport
		MarketMessage message = null; // say, BidAsk

		maker.make(message);// update market and fire events

	}

	//

	// runs in the same thread as make()
	final MarketTaker<Market> marketTaker = new MarketTaker<Market>() {

		@Override
		public MarketField<Market> bindField() {
			return MARKET;
		}

		@Override
		public MarketEvent[] bindEvents() {
			return new MarketEvent[] { MARKET_UPDATED };
		}

		@Override
		public MarketInstrument[] bindInstruments() {
			return null; // some instruments
		}

		@SuppressWarnings("deprecation")
		@Override
		public void onMarketEvent(MarketEvent event,
				MarketInstrument instrument, Market market) {

			MarketBar bar = market.get(BAR_CURRENT);

			PriceValue priceHigh = bar.get(HIGH);
			float high = priceHigh.asFloat();

			PriceValue priceLast = bar.get(CLOSE);
			float last = priceLast.asFloat();

			float low = bar.get(LOW).asFloat();

			int volume = bar.get(VOLUME).asInt();

			MarketBook book = market.get(BOOK);
			MarketBookEntry[] bookBids = book.entries(MarketBookSide.BID);

			MarketCuvol cumVol = market.get(CUVOL);
			PriceValue priveFirst = cumVol.priceFirst();
			PriceValue priceStep = cumVol.priceStep();
			SizeValue[] cumVolEntires = cumVol.entries();

		}

	};

	{

		maker.register(marketTaker);

	}

	//

	// runs in the same thread as make()
	final MarketTaker<MarketBar> currentTaker = new MarketTaker<MarketBar>() {

		@Override
		public MarketField<MarketBar> bindField() {
			return BAR_CURRENT;
		}

		@Override
		public MarketEvent[] bindEvents() {
			return new MarketEvent[] { MARKET_OPENED, NEW_HIGH };
		}

		@Override
		public MarketInstrument[] bindInstruments() {
			return null; // some instrument
		}

		@SuppressWarnings("deprecation")
		@Override
		public void onMarketEvent(MarketEvent event,
				MarketInstrument instrument, MarketBar bar) {

			switch (event) {
			case MARKET_OPENED:
				break;
			case NEW_HIGH:
				break;
			}

			float high = bar.get(HIGH).asFloat();

		}

	};

	{

		maker.register(currentTaker);

	}

	//

	// runs in the same thread as make()
	final MarketTaker<MarketTrade> tradeTaker = new MarketTaker<MarketTrade>() {

		@Override
		public MarketEvent[] bindEvents() {
			return new MarketEvent[] { MARKET_OPENED, NEW_TRADE };
		}

		@Override
		public MarketField<MarketTrade> bindField() {
			return TRADE;
		}

		@Override
		public MarketInstrument[] bindInstruments() {
			return null; // some valid instruments
		}

		@Override
		public void onMarketEvent(MarketEvent event,
				MarketInstrument instrument, MarketTrade trade) {

			switch (event) {
			case NEW_TRADE:
				//
			}

			@SuppressWarnings("deprecation")
			float price = trade.get(MarketTradeField.PRICE).asFloat();

		}

	};

	{

		maker.register(tradeTaker);

	}

}
