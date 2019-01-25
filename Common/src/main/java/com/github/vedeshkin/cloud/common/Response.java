package com.github.vedeshkin.cloud.common;

import java.io.Serializable;

/**
 * Created by Vedeshkin on 1/25/2019.
 * All right reserved.
 */
public class Response implements Serializable {
    private Responses action;
    private Object data;

    public Responses getAction() {
        return action;
    }

    public void setAction(Responses action) {
        this.action = action;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Response(Responses action, Object data) {
        this.action = action;
        this.data = data;
    }
}
