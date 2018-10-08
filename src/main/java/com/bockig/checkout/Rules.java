package com.bockig.checkout;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

class Rules {

    private Set<PricingRule> pricingRuleSet;

    Rules(Set<PricingRule> pricingRuleSet) {
        this.pricingRuleSet = pricingRuleSet;
    }

    /**
     * Tries to apply any of the included pricing rules to the given object.
     * If multiple rules are applicable, the one with the biggest discount (smallest total) will be chosen.
     *
     * @param items container of items with and without pricing rules applied
     * @return Optional.empty() if no rule could be applied, otherwise an Optional.of() with the new items container
     */
    Optional<ItemsWithPricingRules> apply(ItemsWithPricingRules items) {
        Function<PricingRule, Optional<ItemsWithPricingRules>> tryToApplyRule = rule -> rule.applyTo(items);
        return pricingRuleSet.stream()
                .map(tryToApplyRule)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .min(ItemsWithPricingRules::compareByTotal);
    }
}
