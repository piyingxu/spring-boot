package com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.AutoLog;
import com.enums.DataSourceEnum;
import com.service.ElasticSearchService;
import com.util.ElasticsearchUtils;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {
	
    @Autowired
	private ElasticsearchUtils esUtil;

	@Override
	public Object addAutoLog (AutoLog log) {
		return esUtil.addIndex(log, DataSourceEnum.MY_AULOG);
	}

	@Override
	public Object findAllAutoLog() {
		return esUtil.findAll(DataSourceEnum.LOG_AULOG);
	}

	public Object findByPage (int startPage, int pageSize) {
		//return esUtil.findByPage(DataSourceEnum.LOG_AULOG, startPage, pageSize);
		return esUtil.findByPageNew(DataSourceEnum.LOG_AULOG, startPage, pageSize);
		
	}

}
