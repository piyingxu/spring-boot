package com.dto;

import java.io.Serializable;
import com.alibaba.fastjson.JSONObject;

public class BaseEntity implements Serializable {
	
    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    @Override
    public int hashCode() {
        return this.toString().length();
    }

    @Override
    public boolean equals(Object obj) {
    	if (obj == null) {
    		return false;
    	}
        return this.toString().equals(obj.toString());
    }
}
