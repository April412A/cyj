package com.zhuanye.wiki.controller;

import com.zhuanye.wiki.domain.Ebook;
import com.zhuanye.wiki.resp.CommonResp;
import com.zhuanye.wiki.resp.EbookQueryResp;
import com.zhuanye.wiki.resp.PageResp;
import com.zhuanye.wiki.service.EbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ebook")
public class EbookController {

  @Resource
  private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp list(@Valid EbookQueryResp req) {
        CommonResp<PageResp<EbookQueryResp>> resp = new CommonResp<>();
        PageResp<EbookQueryResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
}

}
