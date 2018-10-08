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

    Rules rules = RulesBuilder.create()
            .add(new DiscountOnAmount(a, 3, 130))
            .add(new DiscountOnAmount(b, 2, 45))
            .build();

    Integer price(List<Item> items) {
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

        Assert.assertEquals(Integer.valueOf(160), price(Lists.newArrayList(a, a, a, b)));
        Assert.assertEquals(Integer.valueOf(175), price(Lists.newArrayList(a, a, a, b, b)));
        Assert.assertEquals(Integer.valueOf(190), price(Lists.newArrayList(a, a, a, b, b, d)));
        Assert.assertEquals(Integer.valueOf(190), price(Lists.newArrayList(d, a, b, a, b, a)));
    }


    @Test
    public void testIncremental() {
        Checkout checkout = new Checkout(rules);

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
}
