package com.bockig.checkout;

import java.util.Optional;

interface PricingRule {
    Integer getPrice();

    Optional<ItemsWithPricingRules> applyTo(ItemsWithPricingRules total);
}
