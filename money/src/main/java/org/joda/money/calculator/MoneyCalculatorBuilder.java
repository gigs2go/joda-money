package org.joda.money.calculator;

import org.joda.money.calculator.allocators.AllocatorStrategy;

public class MoneyCalculatorBuilder {

    EqualAllocator equalAllocator = AllocatorStrategy.FRONT_LOADING.getAllocator();

    public MoneyCalculator toCalculator () {
        return new MoneyCalculatorImpl( equalAllocator );
    }

    /**
     * @return the equalAllocator
     */
    public EqualAllocator getEqualAllocator () {
        return equalAllocator;
    }

    /**
     * @param equalAllocator
     *            the equalAllocator to set
     */
    public void setEqualAllocator ( EqualAllocator equalAllocator ) {
        this.equalAllocator = equalAllocator;
    }

}
