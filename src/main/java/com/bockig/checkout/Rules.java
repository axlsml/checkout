package com.bockig.checkout;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

class Rules {

    private Set<PricingRule> pricingRuleSet;

    Rules(Set<PricingRule> pricingRuleSet) {
        this.pricingRuleSet = pricingRuleSet;
    }

    PricesContainer applyTo(List<Item> items) {
        PricesContainer itemsAndPricingRules = new PricesContainer(items);

        Optional<PricesContainer> optimized;
        while ((optimized = applyBestRule(itemsAndPricingRules)).isPresent()) {
            itemsAndPricingRules = optimized.get();
        }

        return itemsAndPricingRules;
    }

    /**
     * Tries to apply any of the available pricing rules to the given object.
     * If multiple rules are applicable, the one with the biggest discount (smallest total) is chosen.
     *
     * @param items container of items with and without pricing rules applied
     * @return Optional.empty() if no rule could be applied, otherwise an Optional.of() with the new items container
     */
    private Optional<PricesContainer> applyBestRule(PricesContainer items) {
        Function<PricingRule, Optional<PricesContainer>> tryToApplyRule = rule -> rule.applyTo(items);
        return pricingRuleSet.stream()
                .map(tryToApplyRule)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .min(PricesContainer::compareByTotal);
    }
}
