package com.a8tiyu.caizhan_kotlin.entity

import java.util.Date

data class JCLQMatch(

        var matchNum: String? = null, //比赛编码
        var phase: String? = null, //彩期号
        var officialNum: String? = null, //官方比赛时间
        var officialDate: Date? = null, //比赛编号
        var endSaleTime: Date? = null, //官方停售时间
        var homeName: String? = null, //主队名称
        var awayName: String? = null, //客队名称
        var leagueName: String? = null, //联赛名称
        var saleStatus: Int? = 0, //开售状态
        var saleSfStatus: Int? = 0, //胜负过关玩法 销售状态   1：可销售 2：不可销售
        var saleRfsfStatus: Int? = 0, //让分胜负过关玩法 销售状态   1：可销售 2：不可销售
        var saleSfcStatus: Int? = 0, //胜分差过关玩法 销售状态   1：可销售 2：不可销售
        var saleDxfStatus: Int? = 0, //大小分过关玩法 销售状态   1：可销售 2：不可销售
        var dgSaleSfStatus: Int? = 0, //胜负单关玩法 销售状态   1：可销售 2：不可销售
        var dgSaleRfsfStatus: Int? = 0, //让分胜负单关玩法 销售状态   1：可销售 2：不可销售
        var dgSaleSfcStatus: Int? = 0, //胜分差单关玩法 销售状态   1：可销售 2：不可销售
        var dgSaleDxfStatus: Int? = 0//大小分单关玩法 销售状态   1：可销售 2：不可销售

)