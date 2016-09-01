package com.loopj.android.http;

import android.os.SystemClock;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Iterator;
import javax.net.ssl.SSLHandshakeException;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HttpContext;

class RetryHandler implements HttpRequestRetryHandler {
    private static final int RETRY_SLEEP_TIME_MILLIS = 1500;
    private static HashSet<Class<?>> exceptionBlacklist = new HashSet();
    private static HashSet<Class<?>> exceptionWhitelist = new HashSet();
    private final int maxRetries;

    static {
        exceptionWhitelist.add(NoHttpResponseException.class);
        exceptionWhitelist.add(UnknownHostException.class);
        exceptionWhitelist.add(SocketException.class);
        exceptionBlacklist.add(InterruptedIOException.class);
        exceptionBlacklist.add(SSLHandshakeException.class);
    }

    public RetryHandler(int i) {
        this.maxRetries = i;
    }

    protected boolean isInList(HashSet<Class<?>> hashSet, Throwable th) {
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            if (((Class) it.next()).isInstance(th)) {
                return true;
            }
        }
        return false;
    }

    public boolean retryRequest(IOException iOException, int i, HttpContext httpContext) {
        Boolean bool = (Boolean) httpContext.getAttribute("http.request_sent");
        Object obj = (bool == null || !bool.booleanValue()) ? null : 1;
        boolean z = i > this.maxRetries ? false : isInList(exceptionBlacklist, iOException) ? false : isInList(exceptionWhitelist, iOException) ? true : obj == null ? true : true;
        if (z) {
            z = !((HttpUriRequest) httpContext.getAttribute("http.request")).getMethod().equals("POST");
        }
        if (z) {
            SystemClock.sleep(1500);
        } else {
            iOException.printStackTrace();
        }
        return z;
    }
}
