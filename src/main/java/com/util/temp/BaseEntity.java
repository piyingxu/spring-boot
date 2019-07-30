package com.util.temp;

import com.alibaba.druid.support.json.JSONUtils;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/1/11 16:32
 */
public class BaseEntity {
    @Override
    public String toString() {
        return JSONUtils.toJSONString(this);
    }

    @Override
    public int hashCode() {
        return this.toString().length();
    }

    @Override
    public boolean equals(Object obj) {
        return this.toString().equals(obj.toString());
    }
}
