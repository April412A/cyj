package com.zhuanye.wiki.mapper;

import com.zhuanye.wiki.domain.Test;
import org.apache.ibatis.io.ResolverUtil;

import java.util.List;

public interface TestMapper {
    public List<Test> list();
}
