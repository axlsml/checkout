package com.bockig.checkout;

import java.util.Optional;

interface PricingRule extends HasPrice {

    /**
     * Applies this rule to the given PricesContainer, if possible.
     *
     * @param total the current container of items and already applied rules
     * @return Optional.empty() if this rule could not be applied, otherwise Optional.of() with this rule added
     */
    Optional<PricesContainer> applyTo(PricesContainer total);
}
