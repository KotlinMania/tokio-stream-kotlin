// port-lint: source macros.rs
package io.github.kotlinmania.tokiostream

/**
 * Feature flags for tokio-stream capabilities.
 */
public object StreamFeatures {
    public const val FEATURE_TIME: String = "time"
    public const val FEATURE_NET: String = "net"
    public const val FEATURE_FS: String = "fs"
    public const val FEATURE_IO_UTIL: String = "io-util"
    public const val FEATURE_SYNC: String = "sync"
    public const val FEATURE_SIGNAL: String = "signal"
}
