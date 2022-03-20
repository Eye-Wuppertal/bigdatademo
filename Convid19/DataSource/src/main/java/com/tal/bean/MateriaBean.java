package com.tal.bean;
/* 
    @TODO: 用来封装防疫物资的JavaBean
    @Author tal
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MateriaBean {
    private String name;//物资名称
    private String from;//物质来源
    private Integer count;//物资数量
}

