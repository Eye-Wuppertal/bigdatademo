package com.tal.bean;
/* 
    @TODO: 用于封装各省市疫情数据的JavaBean
    @Author tal
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CovidBean {
    private String provinceName;                //  省份名称
    private String provinceShortName;           //  省份短名
    private String cityName;
    private String currentConfirmedCount;       //  当前确诊人数
    private String confirmedCount;              //  累计确诊人数
    private String suspectedCount;              //  疑似病例人数
    private String curedCount;                  //  治愈人数
    private String deadCount;                   //  死亡人数
    private String locationId;                  //  位置ID
    private String pid;
    private String statisticsData;              //  每天的统计数据
    private String highDangerCount;
    private String midDangerCount;
    private String detectOrgCount;
    private String vaccinationOrgCount;
    private String cities;                      //  下属城市
    private String datetime;
}
