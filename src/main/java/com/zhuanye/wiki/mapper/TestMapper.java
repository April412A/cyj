package com.zhuanye.wiki.mapper;

import org.apache.ibatis.io.ResolverUtil;

import java.util.List;

public interface TestMapper {
    public List<ResolverUtil.Test> list();
}
