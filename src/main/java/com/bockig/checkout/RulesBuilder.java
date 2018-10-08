package com.bockig.checkout;

import com.google.common.collect.Sets;

import java.util.Set;

class RulesBuilder {

    private Set<Rule> rules;

    private RulesBuilder(Set<Rule> rules) {
        this.rules = rules;
    }

    static RulesBuilder create() {
        return new RulesBuilder(Sets.newHashSet());
    }

    RulesBuilder add(Rule rule) {
        Set<Rule> newRules = Sets.newConcurrentHashSet(rules);
        newRules.add(rule);
        return new RulesBuilder(newRules);
    }

    Rules build() {
        return new Rules(rules);
    }
}
