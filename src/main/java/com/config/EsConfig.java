package com.config;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:es.properties")
public class EsConfig {
	
	private static Logger log = Logger.getLogger(EsConfig.class);
 
    @Value("${elasticsearch.host}")
    private String esHost;
 
    @Value("${elasticsearch.port}")
    private int esPort;
    
    @Value("${elasticsearch.cluster.name}")
    private String clusterName;
    
    @Bean 
    public Settings initSettings() {
    	Settings settings = Settings.builder()
		.put("cluster.name", clusterName)
        .put("client.transport.sniff", true) //探测集群中机器状态  
		.build(); 
    	return settings;
    }
    
    @Bean 
    public List<TransportAddress> initAddress() {
    	List<TransportAddress> addressList = new ArrayList<TransportAddress>();
    	//可以配置多个
		try {
			TransportAddress address = new TransportAddress(InetAddress.getByName(esHost), esPort);
			addressList.add(address);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return addressList;
    }
    
    @Bean
    public Client getTransportClient() {
    	TransportClient client = null;
    	try {
    		Settings settings = initSettings();
    		List<TransportAddress> addressList = initAddress();
    		/* 
             * 创建客户端，所有的操作都由客户端开始，这个就好像是JDBC的Connection对象 
             * 用完记得要关闭 
             * 注意client里面包含了连接池，对于client而言用完之后需要关闭，但是针对连接而言不需要关闭
             */  
	        client = new PreBuiltTransportClient(settings);
	        for (TransportAddress address:addressList) {
	        	client.addTransportAddress(address);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
    	log.info("=========>GetClient Success,Client=" + client);
    	return client; 
    }
    
    public Client getClient() {
    	TransportClient client = null;
    	try {
    		Settings settings = initSettings();
    		List<TransportAddress> addressList = initAddress();
    		/* 
             * 创建客户端，所有的操作都由客户端开始，这个就好像是JDBC的Connection对象 
             * 用完记得要关闭 
             * 注意client里面包含了连接池，对于client而言用完之后需要关闭，但是针对连接而言不需要关闭
             */  
	        client = new PreBuiltTransportClient(settings);
	        for (TransportAddress address:addressList) {
	        	client.addTransportAddress(address);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
    	log.info("=========>GetClient Success,Client=" + client);
    	return client; 
    }
	
	
}