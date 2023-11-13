
package com.ccm.document.notify;

/**
 * This event share one event-queue.
 *
 * @author kekai.huang
 */
@SuppressWarnings("PMD.AbstractClassShouldStartWithAbstractNamingRule")
public abstract class SlowEvent extends Event {
    
    @Override
    public long sequence() {
        return 0;
    }
}