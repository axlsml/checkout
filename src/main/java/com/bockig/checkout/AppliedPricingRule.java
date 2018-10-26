package com.bockig.checkout;

import java.util.List;

class AppliedPricingRule implements HasPrice {

    private final PricingRule pricingRule;
    private final List<Item> included;

    AppliedPricingRule(PricingRule pricingRule, List<Item> included) {
        this.pricingRule = pricingRule;
        this.included = included;
    }

    @Override
    public Integer getPrice() {
        return pricingRule.getPrice();
    }

    List<Item> getIncluded() {
        return included;
    }
}
