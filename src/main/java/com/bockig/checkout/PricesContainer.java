package com.bockig.checkout;

import com.google.common.collect.Lists;

import java.util.List;

class PricesContainer {

    private final List<? extends HasPrice> thingsWithPrice;

    PricesContainer(List<? extends HasPrice> thingsWithPrice) {
        this.thingsWithPrice = thingsWithPrice;
    }

    Integer getTotal() {
        return thingsWithPrice.stream().mapToInt(HasPrice::getPrice).sum();
    }

    int compareByTotal(PricesContainer other) {
        return getTotal().compareTo(other.getTotal());
    }

    public List<? extends HasPrice> getThingsWithPrice() {
        return thingsWithPrice;
    }

    PricesContainer with(AppliedPricingRule appliedRule) {
        List<HasPrice> copy = Lists.newArrayList(thingsWithPrice);
        appliedRule.getIncluded().forEach(copy::remove);
        copy.add(appliedRule);
        return new PricesContainer(copy);

    }
}
