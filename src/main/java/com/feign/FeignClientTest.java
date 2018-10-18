package com.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "provider-server")
public interface FeignClientTest {

	@PostMapping(value = "/api/info")
	public String info(@RequestParam("userName") String userName);
}
