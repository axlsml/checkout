package com.bockig.checkout;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class SeveralPiecesPricingRule implements PricingRule {

    private final Item item;
    private final Integer amount;
    private final Integer fixedTotal;

    SeveralPiecesPricingRule(Item item, Integer amount, Integer fixedTotal) {
        this.item = item;
        this.amount = amount;
        this.fixedTotal = fixedTotal;
    }

    @Override
    public Integer getPrice() {
        return fixedTotal;
    }

    private boolean canBeAppliedTo(ItemsWithPricingRules total) {
        return allRelevantItems(total).size() >= amount;
    }

    private List<Item> allRelevantItems(ItemsWithPricingRules total) {
        return total.getSingleItems()
                .stream()
                .filter(this::isItem).collect(Collectors.toList());
    }

    @Override
    public Optional<ItemsWithPricingRules> applyTo(ItemsWithPricingRules total) {
        if (!canBeAppliedTo(total)) {
            return Optional.empty();
        }
        List<Item> itemsForDiscount = allRelevantItems(total).subList(0, amount);
        AppliedPricingRule appliedRule = new AppliedPricingRule(itemsForDiscount, this);
        return Optional.of(total.with(appliedRule));
    }

    private boolean isItem(Item item) {
        return item.equals(this.item);
    }
}
