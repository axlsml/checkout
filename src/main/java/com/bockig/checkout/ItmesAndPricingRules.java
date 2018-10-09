package com.bockig.checkout;

import com.google.common.collect.Lists;

import java.util.List;

class ItmesAndPricingRules {

    private final List<Item> singleItems;
    private final List<AppliedPricingRule> itemsWithAppliedPricingRule;

    private ItmesAndPricingRules(List<Item> singleItems, List<AppliedPricingRule> itemsWithAppliedPricingRule) {
        this.singleItems = singleItems;
        this.itemsWithAppliedPricingRule = itemsWithAppliedPricingRule;
    }

    static ItmesAndPricingRules create(List<Item> items) {
        return new ItmesAndPricingRules(items, Lists.newArrayList());
    }

    Integer getTotal() {
        Integer singleItemTotal = singleItems.stream().mapToInt(Item::getUnitPrice).sum();
        Integer pricingRulesTotal = itemsWithAppliedPricingRule.stream().mapToInt(AppliedPricingRule::getPrice).sum();
        return singleItemTotal + pricingRulesTotal;
    }

    List<Item> getSingleItems() {
        return singleItems;
    }

    ItmesAndPricingRules with(AppliedPricingRule appliedRule) {
        List<Item> leftSingleItems = appliedRule.withoutIncludedItems(singleItems);
        List<AppliedPricingRule> addedDiscounts = appliedRule.plus(itemsWithAppliedPricingRule);
        return new ItmesAndPricingRules(leftSingleItems, addedDiscounts);
    }

    int compareByTotal(ItmesAndPricingRules other) {
        return getTotal().compareTo(other.getTotal());
    }
}
