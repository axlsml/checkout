package com.bockig.checkout;

import com.google.common.collect.Lists;

import java.util.List;

class PricesContainer {

    private final List<? extends HasPrice> itemsWithPrice;

    PricesContainer(List<? extends HasPrice> itemsWithPrice) {
        this.itemsWithPrice = itemsWithPrice;
    }

    Integer getTotal() {
        return itemsWithPrice.stream().mapToInt(HasPrice::getPrice).sum();
    }

    int compareByTotal(PricesContainer other) {
        return getTotal().compareTo(other.getTotal());
    }

    public List<? extends HasPrice> getItemsWithPrice() {
        return itemsWithPrice;
    }

    PricesContainer with(AppliedPricingRule appliedRule) {
        List<HasPrice> copy = Lists.newArrayList(itemsWithPrice);
        appliedRule.getIncluded().forEach(copy::remove);
        copy.add(appliedRule);
        return new PricesContainer(copy);

    }
}
