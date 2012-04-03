package com.ddfplus.feed.common.market.provider;

import java.util.Collections;
import java.util.List;

import com.barchart.util.anno.NotThreadSafe;
import com.ddfplus.feed.common.util.collections.FastArrayList;

@NotThreadSafe
final class RegTakerList extends FastArrayList<RegTaker<?>> {

	static final List<RegTaker<?>> EMPTY = Collections
			.unmodifiableList(new RegTakerList());

	// final FieldSet fieldSet = new FieldSet();
	//
	// @Override
	// public boolean add(final RegTaker<?> regTaker) {
	// fieldSet.add(regTaker.field());
	// return super.add(regTaker);
	// }
	//
	// @Override
	// public boolean remove(final Object regTaker) {
	// final MarketField<?> field = ((RegTaker<?>) regTaker).field();
	// final boolean isRemoved = super.remove(regTaker);
	// final RegTaker<?>[] array = this.array;
	// int count = 0;
	// for (final RegTaker<?> rt : array) {
	// if (rt.field() == field) {
	// count++;
	// }
	// }
	// if (count == 0) {
	// fieldSet.remove(field);
	// }
	// return isRemoved;
	// }

}
