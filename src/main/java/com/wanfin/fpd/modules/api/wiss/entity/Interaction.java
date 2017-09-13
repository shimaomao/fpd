package com.wanfin.fpd.modules.api.wiss.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.wanfin.fpd.common.persistence.DataEntity;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Interaction extends DataEntity<Interaction>{
	private static final long serialVersionUID = 1L;
}
