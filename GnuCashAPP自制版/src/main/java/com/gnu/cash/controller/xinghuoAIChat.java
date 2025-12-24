package com.gnu.cash.controller;


import com.gnu.cash.config.SparkManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/api/ai")

public class xinghuoAIChat {

    @Resource
    private SparkManager sparkManager;

    @GetMapping("/chat")
    public String analyze(@RequestParam("data") String data,@RequestParam("query") String query) {
        String result = sparkManager.sendMesToAIUseXingHuo(data,query);
        return result;
    }



}
