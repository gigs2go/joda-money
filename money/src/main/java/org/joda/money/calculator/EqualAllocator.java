package org.joda.money.calculator;

import org.joda.money.Money;

public interface EqualAllocator {
    Money[] allocate ( Money money, int len );
}
