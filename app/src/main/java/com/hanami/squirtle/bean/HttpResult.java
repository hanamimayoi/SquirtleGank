package com.hanami.squirtle.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by hanami on 2017/4/14.
 */

public class HttpResult<T> implements Serializable {

    private static final long serialVersionUID = 2369780845535121572L;

    @Expose
    boolean error;

    @Expose
    T results;

    public HttpResult() {
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
