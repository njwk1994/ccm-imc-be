
package com.ccm.document.notify.lifecycle;

import com.imc.common.core.exception.ServiceException;

/**
 * An interface is used to define the resource's close and shutdown, such as IO Connection and ThreadPool.
 *
 * @author kekai.huang
 */
public interface Closeable {
    
    /**
     * Shutdown the Resources, such as Thread Pool.
     *
     * @throws ServiceException exception.
     */
    void shutdown() throws ServiceException;
    
}
