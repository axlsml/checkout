package com.bockig.checkout;

import com.google.common.collect.Lists;

import java.util.List;

class AppliedPricingRule {

    private final PricingRule pricingRule;
    private final List<Item> itemsIncluded;

    AppliedPricingRule(PricingRule pricingRule, List<Item> itemsIncluded) {
        this.pricingRule = pricingRule;
        this.itemsIncluded = itemsIncluded;
    }

    int getPrice() {
        return pricingRule.getPrice();
    }

    List<Item> withoutIncludedItems(List<Item> items) {
        List<Item> left = Lists.newArrayList(items);
        itemsIncluded.forEach(left::remove);
        return left;
    }

    List<AppliedPricingRule> plus(List<AppliedPricingRule> appliedPricingRules) {
        List<AppliedPricingRule> withThis = Lists.newArrayList(appliedPricingRules);
        withThis.add(this);
        return withThis;
    }
}
