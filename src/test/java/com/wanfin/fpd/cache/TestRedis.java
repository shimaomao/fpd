package com.wanfin.fpd.cache;

import org.junit.Test;

import com.wanfin.fpd.common.utils.RedisUtils;

public class TestRedis {
	public void testMain() {
		RedisUtils redis = new RedisUtils("127.0.0.1", 6379);
		redis.append("ss111zz", "zz11");
	}
}
