package org.joda.money.calculator.allocators;

import org.joda.money.calculator.EqualAllocator;

public enum AllocatorStrategy {
    FRONT_LOADING( new FrontLoadedEqualAllocatorImpl() ), BACK_LOADING( new BackLoadedEqualAllocatorImpl() );

    private EqualAllocator allocator;

    AllocatorStrategy( EqualAllocator allocator ) {
        this.allocator = allocator;
    }

    /**
     * @return the allocator
     */
    public EqualAllocator getAllocator () {
        return allocator;
    }

}
