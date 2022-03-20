package com.tal.bean

/*
    @TODO: 用于封装省份每一天的统计数据
    @Author tal
*/

case class StatisticsDataBean(
                               var dateId:String,                 // 时间
                               var provinceShortName:String,      // 省份简称
                               var locationId:Int,                // 位置ID
                               var confirmedCount:Int,            // 累积确诊
                               var currentConfirmedCount:Int,     // 当前确诊
                               var currentConfirmedIncr:Int,      // 当前确证新增
                               var confirmedIncr:Int,             // 确诊新增
                               var curedCount:Int,                // 治愈总数
                               var curedIncr:Int,                 // 治愈新增
                               var suspectedCountIncr:Int,        // 疑似新增
                               var suspectedCount:Int,            // 疑似总数
                               var deadCount:Int,                 // 死亡总数
                               var deadIncr:Int                   // 死亡新增
                             )
