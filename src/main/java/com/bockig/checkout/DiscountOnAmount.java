package com.bockig.checkout;

import java.util.List;
import java.util.stream.Collectors;

class DiscountOnAmount implements Rule {

    private final Item item;
    private final Integer amount;
    private final Integer fixedTotal;

    DiscountOnAmount(Item item, Integer amount, Integer fixedTotal) {
        this.item = item;
        this.amount = amount;
        this.fixedTotal = fixedTotal;
    }

    @Override
    public Integer getPrice() {
        return fixedTotal;
    }

    @Override
    public boolean canBeApplied(TotalContainer total) {
        return allRelevantItems(total).size() >= amount;
    }

    private List<Item> allRelevantItems(TotalContainer total) {
        return total.getSingleItems()
                .stream()
                .filter(this::isItem).collect(Collectors.toList());
    }

    @Override
    public TotalContainer applyTo(TotalContainer total) {
        List<Item> itemsForDiscount = allRelevantItems(total).subList(0, amount);
        RuleApplied appliedRule = new RuleApplied(itemsForDiscount, this);
        return total.with(appliedRule, itemsForDiscount);
    }

    private boolean isItem(Item item) {
        return item.equals(this.item);
    }
}
