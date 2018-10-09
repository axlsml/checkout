package com.bockig.checkout;

import java.util.Optional;

interface PricingRule {

    /**
     * @return the total for this pricing rule
     */
    Integer getPrice();

    /**
     * Applies this rule to the given ItmesAndPricingRules, if possible.
     *
     * @param total the current container of items and already applied rules
     * @return Optional.empty() if this rule could not be applied, otherwise Optional.of() with this rule added
     */
    Optional<ItmesAndPricingRules> applyTo(ItmesAndPricingRules total);
}
