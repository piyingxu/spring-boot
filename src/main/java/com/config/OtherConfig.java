package com.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.Map;

@PropertySource("classpath:my.properties")//src/main/resources目录下
@ConfigurationProperties("piying.pay.sns") //以这个为前置开头
@Configuration
public class OtherConfig {

    private String accessKeyId;
    private String accessKeySecret;
    private String region;
    private List<Map<String, String>> consumers;

    private int corePoolSize;

    private int maxPoolSize;

    private long keepAliveTime;

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public long getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<Map<String, String>> getConsumers() {
        return consumers;
    }

    public void setConsumers(List<Map<String, String>> consumers) {
        this.consumers = consumers;
    }

}
