package com.bockig.checkout;

interface Rule {
    Integer getPrice();

    boolean canBeApplied(TotalContainer total);

    TotalContainer applyTo(TotalContainer total);
}
