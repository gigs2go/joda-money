/*
 *  Copyright 2009-2013 Stephen Colebourne
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.joda.money.calculator;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.math.BigDecimal;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.money.calculator.allocators.AllocatorStrategy;
import org.testng.annotations.Test;

/**
 * Test Calculator.
 */
public class TestCalculator {
    private static final CurrencyUnit GBP = CurrencyUnit.of( "GBP" );
    private static final CurrencyUnit JPY = CurrencyUnit.of( "JPY" );
    private static final BigDecimal BIGDEC_2_3 = new BigDecimal( "2.3" );
    private static final BigDecimal BIGDEC_2_34 = new BigDecimal( "2.34" );
    private static final BigDecimal BIGDEC_2_345 = new BigDecimal( "2.345" );
    private static final BigDecimal BIGDEC_M5_78 = new BigDecimal( "-5.78" );

    private static final Money GBP_0_00 = Money.parse( "GBP 0.00" );
    private static final Money GBP_1_23 = Money.parse( "GBP 1.23" );
    private static final Money GBP_2_33 = Money.parse( "GBP 2.33" );
    private static final Money GBP_3_33 = Money.parse( "GBP 3.33" );
    private static final Money GBP_3_34 = Money.parse( "GBP 3.34" );
    private static final Money GBP_5_00 = Money.parse( "GBP 5.00" );
    private static final Money GBP_10_00 = Money.parse( "GBP 10.00" );
    private static final Money GBP_10_01 = Money.parse( "GBP 10.01" );
    private static final Money GBP_10_02 = Money.parse( "GBP 10.02" );
    private static final Money GBP_1243_21 = Money.parse( "GBP 1243.29" );
    private static final Money GBP_95_63 = Money.parse( "GBP 95.63" );
    private static final Money GBP_95_64 = Money.parse( "GBP 95.64" );

    private static final Money JPY_1000 = Money.parse( "JPY 1000" );
    private static final Money JPY_333 = Money.parse( "JPY 333" );
    private static final Money JPY_334 = Money.parse( "JPY 334" );
    private static final Money JPY_500 = Money.parse( "JPY 500" );

    public MoneyCalculator getDefaultCalculator () {
        MoneyCalculatorBuilder builder = new MoneyCalculatorBuilder();
        MoneyCalculator calculator = builder.toCalculator();
        assertNotNull( calculator );
        return calculator;
    }

    public MoneyCalculator getBackLoadingCalculator () {
        MoneyCalculatorBuilder builder = new MoneyCalculatorBuilder();
        builder.setEqualAllocator( AllocatorStrategy.BACK_LOADING.getAllocator() );
        MoneyCalculator calculator = builder.toCalculator();
        assertNotNull( calculator );
        return calculator;
    }

    @Test( expectedExceptions = IllegalArgumentException.class )
    public void testAllocate1 () {
        MoneyCalculator calculator = getDefaultCalculator();
        Money[] money = calculator.allocate( null, 1 );
    }

    @Test( expectedExceptions = IllegalArgumentException.class )
    public void testAllocate2 () {
        MoneyCalculator calculator = getDefaultCalculator();
        Money[] money = calculator.allocate( GBP_0_00, 0 );
    }

    @Test( expectedExceptions = IllegalArgumentException.class )
    public void testAllocate3 () {
        MoneyCalculator calculator = getDefaultCalculator();
        Money[] money = calculator.allocate( GBP_0_00, -1 );
    }

    @Test
    public void testAllocate4 () {
        MoneyCalculator calculator = getDefaultCalculator();
        Money[] money = calculator.allocate( GBP_0_00, 1 );
        assertNotNull( money );
        assertEquals( money.length, 1 );
        assertEquals( money[0], GBP_0_00 );
    }

    @Test
    public void testAllocate5 () {
        MoneyCalculator calculator = getDefaultCalculator();
        Money[] money = calculator.allocate( GBP_10_00, 1 );
        assertNotNull( money );
        assertEquals( money.length, 1 );
        assertEquals( money[0], GBP_10_00 );
    }

    @Test
    public void testAllocate5a () {
        MoneyCalculator calculator = getDefaultCalculator();
        Money[] money = calculator.allocate( GBP_10_00, 2 );
        assertNotNull( money );
        assertEquals( money.length, 2 );
        assertEquals( money[0], GBP_5_00 );
        assertEquals( money[1], GBP_5_00 );
        assertEquals( Money.total( money ), GBP_10_00 );
    }

    @Test
    public void testAllocate5b () {
        MoneyCalculator calculator = getDefaultCalculator();
        Money[] money = calculator.allocate( GBP_10_00, 3 );
        assertNotNull( money );
        assertEquals( money.length, 3 );
        assertEquals( money[0], GBP_3_34 );
        assertEquals( money[1], GBP_3_33 );
        assertEquals( money[2], GBP_3_33 );
        assertEquals( Money.total( money ), GBP_10_00 );
    }

    @Test
    public void testAllocate6 () {
        MoneyCalculator calculator = getDefaultCalculator();
        Money[] money = calculator.allocate( GBP_10_02, 3 );
        assertNotNull( money );
        assertEquals( money.length, 3 );
        assertEquals( money[0], GBP_3_34 );
        assertEquals( money[1], GBP_3_34 );
        assertEquals( money[2], GBP_3_34 );
        assertEquals( Money.total( money ), GBP_10_02 );
    }

    @Test
    public void testAllocate7 () {
        MoneyCalculator calculator = getDefaultCalculator();
        Money[] money = calculator.allocate( GBP_1243_21, 13 );
        assertNotNull( money );
        assertEquals( money.length, 13 );
        assertEquals( money[0], GBP_95_64 );
        assertEquals( money[1], GBP_95_64 );
        assertEquals( money[2], GBP_95_64 );
        assertEquals( money[3], GBP_95_64 );
        assertEquals( money[4], GBP_95_64 );
        assertEquals( money[5], GBP_95_64 );
        assertEquals( money[6], GBP_95_64 );
        assertEquals( money[7], GBP_95_64 );
        assertEquals( money[8], GBP_95_64 );
        assertEquals( money[9], GBP_95_64 );
        assertEquals( money[10], GBP_95_63 );
        assertEquals( money[11], GBP_95_63 );
        assertEquals( money[12], GBP_95_63 );
        assertEquals( Money.total( money ), GBP_1243_21 );
    }

    @Test
    public void testAllocate10 () {
        MoneyCalculator calculator = getDefaultCalculator();
        Money[] money = calculator.allocate( JPY_1000, 3 );
        assertNotNull( money );
        assertEquals( money.length, 3 );
        assertEquals( money[0], JPY_334 );
        assertEquals( money[1], JPY_333 );
        assertEquals( money[2], JPY_333 );
        assertEquals( Money.total( money ), JPY_1000 );
    }

    @Test
    public void testAllocateBack () {
        MoneyCalculator calculator = getBackLoadingCalculator();
        Money[] money = calculator.allocate( GBP_10_00, 3 );
        assertNotNull( money );
        assertEquals( money.length, 3 );
        assertEquals( money[0], GBP_3_33 );
        assertEquals( money[1], GBP_3_33 );
        assertEquals( money[2], GBP_3_34 );
        assertEquals( Money.total( money ), GBP_10_00 );
    }

}
