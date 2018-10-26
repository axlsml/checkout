package com.bockig.checkout;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Map;

public class TestData {

    private static final Map<String, Integer> prices = Maps.newHashMap();

    static Rules distinctRules = RulesBuilder.create()
            .with(new SeveralPiecesFixedPrice(item("A"), 3, 130))
            .with(new SeveralPiecesFixedPrice(item("B"), 2, 45))
            .build();

    static Rules indistinctRules = RulesBuilder.create()
            .with(new SeveralPiecesFixedPrice(item("A"), 7, 300)) // 1st rule on item a
            .with(new SeveralPiecesFixedPrice(item("A"), 3, 130)) // 2nd rule on item a
            .with(new SeveralPiecesFixedPrice(item("B"), 2, 45))
            .build();

    static {
        prices.put("A", 50);
        prices.put("B", 30);
        prices.put("C", 20);
        prices.put("D", 15);
    }

    static Item item(String name) {
        return new Item(name, prices.get(name));
    }

    static Collection<Item> items(String names) {
        Collection<Item> items = Lists.newArrayList();
        for (int i = 0; i < names.length(); i++) {
            String current = names.substring(i, i + 1);
            items.add(TestData.item(current));
        }
        return items;
    }

}
