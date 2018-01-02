package com.suo.notification;

import junit.framework.AssertionFailedError;

/**
 * Created by wuhongqi on 17/12/25.
 */

public class BaseAssert {
    /**
     * Fails a test with the given message.
     */
    static public void fail(String message) {
        throw new AssertionFailedError(message);
    }
}
