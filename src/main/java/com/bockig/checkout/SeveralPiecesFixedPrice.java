package com.bockig.checkout;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class SeveralPiecesFixedPrice implements PricingRule {

    private final Item item;
    private final Integer amount;
    private final Integer fixedTotal;

    SeveralPiecesFixedPrice(Item item, Integer amount, Integer fixedTotal) {
        this.item = item;
        this.amount = amount;
        this.fixedTotal = fixedTotal;
    }

    @Override
    public Integer getPrice() {
        return fixedTotal;
    }

    @Override
    public Optional<ItmesAndPricingRules> applyTo(ItmesAndPricingRules total) {
        if (!canBeAppliedTo(total)) {
            return Optional.empty();
        }
        List<Item> pickedItems = relevantItems(total).subList(0, amount);
        AppliedPricingRule appliedRule = new AppliedPricingRule(this, pickedItems);
        return Optional.of(total.with(appliedRule));
    }

    private boolean canBeAppliedTo(ItmesAndPricingRules items) {
        return relevantItems(items).size() >= amount;
    }

    private List<Item> relevantItems(ItmesAndPricingRules items) {
        return items.getSingleItems()
                .stream()
                .filter(this::equalsThisItem)
                .collect(Collectors.toList());
    }

    private boolean equalsThisItem(Item item) {
        return item.equals(this.item);
    }
}
