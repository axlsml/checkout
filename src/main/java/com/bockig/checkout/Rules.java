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

    ItmesAndPricingRules applyTo(List<Item> items) {
        ItmesAndPricingRules itemsAndPricingRules = ItmesAndPricingRules.create(items);

        Optional<ItmesAndPricingRules> optimized;
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
    private Optional<ItmesAndPricingRules> applyBestRule(ItmesAndPricingRules items) {
        Function<PricingRule, Optional<ItmesAndPricingRules>> tryToApplyRule = rule -> rule.applyTo(items);
        return pricingRuleSet.stream()
                .map(tryToApplyRule)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .min(ItmesAndPricingRules::compareByTotal);
    }
}
