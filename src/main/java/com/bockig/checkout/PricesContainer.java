package com.bockig.checkout;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;

class PricesContainer {

    private final Collection<? extends HasPrice> thingsWithPrice;

    PricesContainer(Collection<? extends HasPrice> thingsWithPrice) {
        this.thingsWithPrice = thingsWithPrice;
    }

    Integer getTotal() {
        return thingsWithPrice.stream().mapToInt(HasPrice::getPrice).sum();
    }

    int compareByTotal(PricesContainer other) {
        return getTotal().compareTo(other.getTotal());
    }

    public Collection<? extends HasPrice> getThingsWithPrice() {
        return thingsWithPrice;
    }

    PricesContainer with(AppliedPricingRule appliedRule) {
        List<HasPrice> copy = Lists.newArrayList(thingsWithPrice);
        appliedRule.getIncluded().forEach(copy::remove);
        copy.add(appliedRule);
        return new PricesContainer(copy);

    }
}
