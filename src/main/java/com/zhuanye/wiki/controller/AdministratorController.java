package com.zhuanye.wiki.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhuanye.wiki.req.AdministratorLoginReq;
import com.zhuanye.wiki.req.AdministratorQueryReq;
import com.zhuanye.wiki.req.AdministratorResetPasswordReq;
import com.zhuanye.wiki.req.AdministratorSaveReq;
import com.zhuanye.wiki.resp.CommonResp;
import com.zhuanye.wiki.resp.PageResp;
import com.zhuanye.wiki.resp.AdministratorLoginResp;
import com.zhuanye.wiki.resp.AdministratorQueryResp;
import com.zhuanye.wiki.service.AdministratorService;
import com.zhuanye.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/administrator")
public class AdministratorController {

    private static final Logger LOG = LoggerFactory.getLogger(AdministratorController.class);

    @Resource
    private AdministratorService AdministratorService;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private RedisTemplate redisTemplate;

    @GetMapping("/list")
    public CommonResp list(@Valid AdministratorQueryReq req) {
        CommonResp<PageResp<AdministratorQueryResp>> resp = new CommonResp<>();
        PageResp<AdministratorQueryResp> list = AdministratorService.list(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody AdministratorSaveReq req) {
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp resp = new CommonResp<>();
        AdministratorService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id) {
        CommonResp resp = new CommonResp<>();
        AdministratorService.delete(id);
        return resp;
    }

    @PostMapping("/reset-password")
    public CommonResp resetPassword(@Valid @RequestBody AdministratorResetPasswordReq req) {
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp resp = new CommonResp<>();
        AdministratorService.resetPassword(req);
        return resp;
    }

    @PostMapping("/login")
    public CommonResp login(@Valid @RequestBody AdministratorLoginReq req) {
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp<AdministratorLoginResp> resp = new CommonResp<>();
        AdministratorLoginResp AdministratorLoginResp = AdministratorService.login(req);

        Long token = snowFlake.nextId();
        LOG.info("生成单点登录token：{}，并放入redis中", token);
        AdministratorLoginResp.setToken(token.toString());
        redisTemplate.opsForValue().set(token.toString(), JSONObject.toJSONString(AdministratorLoginResp), 3600 * 24, TimeUnit.SECONDS);

        resp.setContent(AdministratorLoginResp);
        return resp;
    }

    @GetMapping("/logout/{token}")
    public CommonResp logout(@PathVariable String token) {
        CommonResp resp = new CommonResp<>();
        redisTemplate.delete(token);
        LOG.info("从redis中删除token: {}", token);
        return resp;
    }
}
