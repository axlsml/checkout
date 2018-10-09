package com.bockig.checkout;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class CheckoutTest {

    private Item a = new Item("A", 50);
    private Item b = new Item("B", 30);
    private Item c = new Item("C", 20);
    private Item d = new Item("D", 15);

    private Rules distinctRules = RulesBuilder.create()
            .with(new SeveralPiecesFixedPrice(a, 3, 130))
            .with(new SeveralPiecesFixedPrice(b, 2, 45))
            .build();

    private Rules indistinctRules = RulesBuilder.create()
            .with(new SeveralPiecesFixedPrice(a, 7, 300)) // 1st rule on item a
            .with(new SeveralPiecesFixedPrice(a, 3, 130)) // 2nd rule on item a
            .with(new SeveralPiecesFixedPrice(b, 2, 45))
            .build();

    Integer price(List<Item> items) {
        return price(items, distinctRules);
    }

    Integer price(List<Item> items, Rules rules) {
        Checkout checkout = new Checkout(rules);
        items.forEach(checkout::scan);
        return checkout.total();
    }

    @Test
    public void testTotals() {
        Assert.assertEquals(Integer.valueOf(0), price(Lists.newArrayList()));
        Assert.assertEquals(Integer.valueOf(50), price(Lists.newArrayList(a)));
        Assert.assertEquals(Integer.valueOf(80), price(Lists.newArrayList(a, b)));
        Assert.assertEquals(Integer.valueOf(115), price(Lists.newArrayList(c, d, b, a)));

        Assert.assertEquals(Integer.valueOf(100), price(Lists.newArrayList(a, a)));
        Assert.assertEquals(Integer.valueOf(130), price(Lists.newArrayList(a, a, a)));
        Assert.assertEquals(Integer.valueOf(180), price(Lists.newArrayList(a, a, a, a)));
        Assert.assertEquals(Integer.valueOf(230), price(Lists.newArrayList(a, a, a, a, a)));
        Assert.assertEquals(Integer.valueOf(260), price(Lists.newArrayList(a, a, a, a, a, a)));
        Assert.assertEquals(Integer.valueOf(310), price(Lists.newArrayList(a, a, a, a, a, a, a)));

        Assert.assertEquals(Integer.valueOf(160), price(Lists.newArrayList(a, a, a, b)));
        Assert.assertEquals(Integer.valueOf(175), price(Lists.newArrayList(a, a, a, b, b)));
        Assert.assertEquals(Integer.valueOf(190), price(Lists.newArrayList(a, a, a, b, b, d)));
        Assert.assertEquals(Integer.valueOf(190), price(Lists.newArrayList(d, a, b, a, b, a)));
    }

    @Test
    public void testIncremental() {
        Checkout checkout = new Checkout(distinctRules);

        Assert.assertEquals(Integer.valueOf(0), checkout.total());

        checkout.scan(a);
        Assert.assertEquals(Integer.valueOf(50), checkout.total());

        checkout.scan(b);
        Assert.assertEquals(Integer.valueOf(80), checkout.total());

        checkout.scan(a);
        Assert.assertEquals(Integer.valueOf(130), checkout.total());

        checkout.scan(a);
        Assert.assertEquals(Integer.valueOf(160), checkout.total());

        checkout.scan(b);
        Assert.assertEquals(Integer.valueOf(175), checkout.total());
    }


    @Test
    public void testTotalsWithIndistinctRules() {
        Assert.assertEquals(Integer.valueOf(0), price(Lists.newArrayList(), indistinctRules)); // same behavior
        Assert.assertEquals(Integer.valueOf(130), price(Lists.newArrayList(a, a, a), indistinctRules)); // same behavior

        // in case of 7 times and the 'competingRules', there are two ways to calculate the total:
        // first: rule(3a) + rule(3a) + a = (130) + (130) + 50 = 310
        // second: rule(7a) = 300
        //
        // with the applied rule(7a) the total is lower so this one is chosen over two times rule(3a)
        Assert.assertEquals(Integer.valueOf(300), price(Lists.newArrayList(a, a, a, a, a, a, a), indistinctRules));
    }
}
