package com.bockig.checkout;

import org.junit.Test;

public class SeveralPiecesFixedPriceTest {

    private PricesContainer container_aaab = new PricesContainer(TestData.items("AAAB"));

    @Test(expected = IllegalArgumentException.class)
    public void testInstantiateInvalidAmount() {
        rule("A", 0, 20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInstantiateInvalidTotal() {
        rule("A", 1, -1);
    }

    @Test
    public void testCanBeAppliedNotEnoughItems() {
        assert !rule("A", 4, 20).canBeAppliedTo(container_aaab);
    }

    @Test
    public void testCanBeAppliedDifferentArticle() {
        assert !rule("C", 1, 20).canBeAppliedTo(container_aaab);
    }

    @Test
    public void testCanBeAppliedSuccessful() {
        assert rule("A", 3, 20).canBeAppliedTo(container_aaab);
    }

    private SeveralPiecesFixedPrice rule(String name, int amount, int total) {
        return new SeveralPiecesFixedPrice(TestData.item(name), amount, total);
    }
}
