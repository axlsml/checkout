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
    public Optional<PricesContainer> applyTo(PricesContainer container) {
        if (!canBeAppliedTo(container)) {
            return Optional.empty();
        }
        List<HasPrice> pickedItems = relevantItems(container).subList(0, amount);
        AppliedPricingRule appliedRule = new AppliedPricingRule(this, pickedItems);
        return Optional.of(container.with(appliedRule));
    }

    private boolean canBeAppliedTo(PricesContainer items) {
        return relevantItems(items).size() >= amount;
    }

    private List<HasPrice> relevantItems(PricesContainer container) {
        return container.getItemsWithPrice()
                .stream()
                .filter(this::equalsThisItem)
                .collect(Collectors.toList());
    }

    private boolean equalsThisItem(HasPrice hasPrice) {
        return hasPrice.equals(this.item);
    }
}
