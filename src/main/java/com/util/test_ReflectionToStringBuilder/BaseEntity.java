package com.util.test_ReflectionToStringBuilder;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/2/28 16:35
 */
public class BaseEntity {

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
    /*


    @Override
    public int hashCode() {
        return this.toString().length();
    }

    @Override
    public boolean equals(Object obj) {
        return this.toString().equals(obj.toString());
    }
    */
    /*
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
    */

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this,obj);
    }

}
