package com.zhuanye.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhuanye.wiki.domain.Administrator;
import com.zhuanye.wiki.domain.AdministratorExample;
import com.zhuanye.wiki.exception.BusinessException;
import com.zhuanye.wiki.exception.BusinessExceptionCode;
import com.zhuanye.wiki.mapper.AdministratorMapper;
import com.zhuanye.wiki.req.AdministratorLoginReq;
import com.zhuanye.wiki.req.AdministratorQueryReq;
import com.zhuanye.wiki.req.AdministratorResetPasswordReq;
import com.zhuanye.wiki.req.AdministratorSaveReq;
import com.zhuanye.wiki.resp.PageResp;
import com.zhuanye.wiki.resp.AdministratorLoginResp;
import com.zhuanye.wiki.resp.AdministratorQueryResp;
import com.zhuanye.wiki.util.CopyUtil;
import com.zhuanye.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdministratorService {

    private static final Logger LOG = LoggerFactory.getLogger(AdministratorService.class);

    @Resource
    private AdministratorMapper AdministratorMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<AdministratorQueryResp> list(AdministratorQueryReq req) {
        AdministratorExample AdministratorExample = new AdministratorExample();
        AdministratorExample.Criteria criteria = AdministratorExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getLoginName())) {
            criteria.andLoginNameEqualTo(req.getLoginName());
        }
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Administrator> AdministratorList = AdministratorMapper.selectByExample(AdministratorExample);

        PageInfo<Administrator> pageInfo = new PageInfo<>(AdministratorList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        // List<AdministratorResp> respList = new ArrayList<>();
        // for (Administrator Administrator : AdministratorList) {
        //     // AdministratorResp AdministratorResp = new AdministratorResp();
        //     // BeanUtils.copyProperties(Administrator, AdministratorResp);
        //     // 对象复制
        //     AdministratorResp AdministratorResp = CopyUtil.copy(Administrator, AdministratorResp.class);
        //
        //     respList.add(AdministratorResp);
        // }

        // 列表复制
        List<AdministratorQueryResp> list = CopyUtil.copyList(AdministratorList, AdministratorQueryResp.class);

        PageResp<AdministratorQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);

        return pageResp;
    }

    /**
     * 保存
     */
    public void save(AdministratorSaveReq req) {
        Administrator Administrator = CopyUtil.copy(req, Administrator.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            Administrator AdministratorDB = selectByLoginName(req.getLoginName());
            if (ObjectUtils.isEmpty(AdministratorDB)) {
                // 新增
                Administrator.setId(snowFlake.nextId());
                AdministratorMapper.insert(Administrator);
            } else {
                // 用户名已存在
                throw new BusinessException(BusinessExceptionCode.Administrator_LOGIN_NAME_EXIST);
            }
        } else {
            // 更新
            Administrator.setLoginName(null);
            Administrator.setPassword(null);
            AdministratorMapper.updateByPrimaryKeySelective(Administrator);
        }
    }

    public void delete(Long id) {
        AdministratorMapper.deleteByPrimaryKey(id);
    }

    public Administrator selectByLoginName(String LoginName) {
        AdministratorExample AdministratorExample = new AdministratorExample();
        AdministratorExample.Criteria criteria = AdministratorExample.createCriteria();
        criteria.andLoginNameEqualTo(LoginName);
        List<Administrator> AdministratorList = AdministratorMapper.selectByExample(AdministratorExample);
        if (CollectionUtils.isEmpty(AdministratorList)) {
            return null;
        } else {
            return AdministratorList.get(0);
        }
    }

    /**
     * 修改密码
     */
    public void resetPassword(AdministratorResetPasswordReq req) {
        Administrator Administrator = CopyUtil.copy(req, Administrator.class);
        AdministratorMapper.updateByPrimaryKeySelective(Administrator);
    }

    /**
     * 登录
     */
    public AdministratorLoginResp login(AdministratorLoginReq req) {
        Administrator AdministratorDb = selectByLoginName(req.getLoginName());
        if (ObjectUtils.isEmpty(AdministratorDb)) {
            // 用户名不存在
            LOG.info("用户名不存在, {}", req.getLoginName());
            throw new BusinessException(BusinessExceptionCode.LOGIN_Administrator_ERROR);
        } else {
            if (AdministratorDb.getPassword().equals(req.getPassword())) {
                // 登录成功
                AdministratorLoginResp AdministratorLoginResp = CopyUtil.copy(AdministratorDb, AdministratorLoginResp.class);
                return AdministratorLoginResp;
            } else {
                // 密码不对
                LOG.info("密码不对, 输入密码：{}, 数据库密码：{}", req.getPassword(), AdministratorDb.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_Administrator_ERROR);
            }
        }
    }
}
