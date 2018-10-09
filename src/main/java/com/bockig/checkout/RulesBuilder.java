package com.bockig.checkout;

import com.google.common.collect.Sets;

import java.util.Set;

class RulesBuilder {

    private Set<PricingRule> pricingRules;

    private RulesBuilder(Set<PricingRule> pricingRules) {
        this.pricingRules = pricingRules;
    }

    static RulesBuilder create() {
        return new RulesBuilder(Sets.newHashSet());
    }

    RulesBuilder with(PricingRule pricingRule) {
        Set<PricingRule> newPricingRules = Sets.newConcurrentHashSet(pricingRules);
        newPricingRules.add(pricingRule);
        return new RulesBuilder(newPricingRules);
    }

    Rules build() {
        return new Rules(pricingRules);
    }
}
