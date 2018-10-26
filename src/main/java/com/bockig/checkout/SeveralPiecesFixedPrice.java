package com.bockig.checkout;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class SeveralPiecesFixedPrice implements PricingRule {

    private final Item item;
    private final Integer amount;
    private final Integer fixedTotal;

    SeveralPiecesFixedPrice(Item item, Integer amount, Integer fixedTotal) {
        if (amount <= 0) {
            throw new IllegalArgumentException("invalid amount: " + amount);
        }
        if (fixedTotal < 0) {
            throw new IllegalArgumentException("invalid total: " + fixedTotal);
        }
        this.item = item;
        this.amount = amount;
        this.fixedTotal = fixedTotal;
    }

    @Override
    public Integer getPrice() {
        return fixedTotal;
    }

    @Override
    public Optional<PricesContainer> optimize(PricesContainer container) {
        if (!canBeAppliedTo(container)) {
            return Optional.empty();
        }
        List<Item> pickedItems = relevantItems(container).subList(0, amount);
        AppliedPricingRule appliedRule = new AppliedPricingRule(this, pickedItems);
        return Optional.of(container.with(appliedRule));
    }

    boolean canBeAppliedTo(PricesContainer items) {
        return relevantItems(items).size() >= amount;
    }

    private List<Item> relevantItems(PricesContainer container) {
        List<Item> collect = container.getThingsWithPrice()
                .stream()
                .filter(this::equalsThisItem)
                .map(hasPrice -> (Item) hasPrice)
                .collect(Collectors.toList());
        return collect;
    }

    private boolean equalsThisItem(HasPrice hasPrice) {
        return hasPrice.equals(this.item);
    }
}
