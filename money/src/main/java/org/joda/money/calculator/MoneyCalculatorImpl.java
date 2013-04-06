package org.joda.money.calculator;

import org.joda.money.Money;
import org.testng.log4testng.Logger;

public class MoneyCalculatorImpl implements MoneyCalculator {
    private Logger log = Logger.getLogger( MoneyCalculatorImpl.class );

    private EqualAllocator equalAllocator = null;

    MoneyCalculatorImpl( EqualAllocator equalAllocator ) {
        this.equalAllocator = equalAllocator;
    }

    @Override
    public Money[] allocate ( Money money, int len ) {
        if ( !(len > 0) ) {
            throw new IllegalArgumentException( "len must be greater than zero : " + len );
        }
        if ( money == null ) {
            throw new IllegalArgumentException( "money must not be null" );
        }
        return equalAllocator.allocate( money, len );
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
