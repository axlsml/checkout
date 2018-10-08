package com.bockig.checkout;

import com.google.common.collect.Lists;

import java.util.List;

class AppliedPricingRule {

    private final List<Item> itemsIncluded;
    private final PricingRule pricingRule;

    AppliedPricingRule(List<Item> itemsIncluded, PricingRule pricingRule) {
        this.itemsIncluded = itemsIncluded;
        this.pricingRule = pricingRule;
    }

    int getPrice() {
        return pricingRule.getPrice();
    }

    List<Item> withoutIncludedItems(List<Item> items) {
        List<Item> left = Lists.newArrayList(items);
        itemsIncluded.forEach(left::remove);
        return left;
    }

    List<AppliedPricingRule> addTo(List<AppliedPricingRule> appliedPricingRules) {
        List<AppliedPricingRule> withThis = Lists.newArrayList(appliedPricingRules);
        withThis.add(this);
        return withThis;
    }
}
