package com.bockig.checkout;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

class Rules {

    private Set<Rule> ruleSet;

    Rules(Set<Rule> ruleSet) {
        this.ruleSet = ruleSet;
    }

    Optional<TotalContainer> apply(TotalContainer total) {
        Predicate<Rule> canBeApplied = r -> r.canBeApplied(total);
        Optional<Rule> ruleToBeApplied = ruleSet.stream().filter(canBeApplied).findAny();
        return ruleToBeApplied.map(r -> r.applyTo(total));
    }
}
