package com.haste.lv.faith.network.interceptor.logging;

/**
 * Created by lv on 18-12-11.
 */

public enum Level {
    /**
     * No logs.
     */
    NONE,
    /**
     * <p>Example:
     * <pre>{@code
     *  - URL
     *  - Method
     *  - Headers
     *  - Body
     * }</pre>
     */
    BASIC,
    /**
     * <p>Example:
     * <pre>{@code
     *  - URL
     *  - Method
     *  - Headers
     * }</pre>
     */
    HEADERS,
    /**
     * <p>Example:
     * <pre>{@code
     *  - URL
     *  - Method
     *  - Body
     * }</pre>
     */
    BODY
}
