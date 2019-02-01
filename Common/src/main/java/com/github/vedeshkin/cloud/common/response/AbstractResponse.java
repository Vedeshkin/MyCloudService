package com.github.vedeshkin.cloud.common.response;

import com.github.vedeshkin.cloud.common.response.ResponseType;

import java.io.Serializable;

/**
 * Created by Vedeshkin on 1/25/2019.
 * All right reserved.
 */
public abstract class  AbstractResponse implements Serializable {
    protected final ResponseType type;

    public ResponseType getType() {
        return type;
    }

    public AbstractResponse(ResponseType type) {
        this.type = type;
    }
}
