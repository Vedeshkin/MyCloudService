package com.github.vedeshkin.cloud.common;

import java.io.Serializable;

/**
 * Created by Vedeshkin on 1/25/2019.
 * All right reserved.
 */
public class Request implements Serializable {
    private Requests requests;
    private Object data;

    public Requests getRequests() {
        return requests;
    }

    public void setRequests(Requests requests) {
        this.requests = requests;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Request(Requests requests, Object data) {
        this.requests = requests;
        this.data = data;
    }
}
