package com.service;

import com.dto.AutoLog;


public interface ElasticSearchService {
	
	Object addAutoLog(AutoLog post);
	
	Object findAllAutoLog();
	
	Object findByPage(int startPage, int pageSize);
    
}
