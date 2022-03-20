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
    private Integer currentConfirmedCount;       //  当前确诊人数
    private Integer confirmedCount;              //  累计确诊人数
    private Integer suspectedCount;              //  疑似病例人数
    private Integer curedCount;                  //  治愈人数
    private Integer deadCount;                   //  死亡人数
    private Integer locationId;                  //  位置ID
    private Integer pid;
    private String statisticsData;              //  每天的统计数据
    private Integer highDangerCount;
    private Integer midDangerCount;
    private Integer detectOrgCount;
    private Integer vaccinationOrgCount;
    private String cities;                      //  下属城市
    private String datetime;
}
