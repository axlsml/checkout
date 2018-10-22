package com.bockig.checkout;

import java.util.List;

class AppliedPricingRule implements HasPrice {

    private final PricingRule pricingRule;
    private final List<HasPrice> included;

    AppliedPricingRule(PricingRule pricingRule, List<HasPrice> included) {
        this.pricingRule = pricingRule;
        this.included = included;
    }

    @Override
    public Integer getPrice() {
        return pricingRule.getPrice();
    }

    List<HasPrice> getIncluded() {
        return included;
    }
}
