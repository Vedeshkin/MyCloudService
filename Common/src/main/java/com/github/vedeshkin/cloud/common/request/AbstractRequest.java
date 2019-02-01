package com.github.vedeshkin.cloud.common.request;

import java.io.Serializable;

/**
 * Created by Vedeshkin on 1/25/2019.
 * All right reserved.
 */
public abstract class AbstractRequest implements Serializable {
    public AbstractRequest(RequestType type) {
        this.type = type;
    }

    protected final RequestType type;

    public RequestType getType() {
        return type;
    }

}
