package com.a8tiyu.caizhan_kotlin.entity

import java.util.Date


data class JCZQMatch(

        var matchNum: String? = null,//比赛编码
        var phase: String? = null,//彩期号
        var officialNum: String? = null,//官方比赛时间
        var officialDate: Date? = null,//比赛编号
        var endSaleTime: Date? = null,//官方停售时间

        var homeId: String? = null,//主队ID
        var homeName: String? = null,//主队名称
        var homeLogo: String? = null,//主队Logo

        var awayId: String? = null,//客队ID
        var awayName: String? = null,//客队名称
        var awayLogo: String? = null,//客队Logo

        var leagueId: String? = null,//联赛ID
        var leagueName: String? = null,//联赛名称
        var leagueColor: String? = null,//联赛Logo

        var handicap: Int? = 0,//让球数
        var saleSpfStatus: Int? = 0,//胜平负过关玩法  销售状态  1：可销售 2：不可销售
        var saleRqspfStatus: Int? = 0,//让球胜平负过关玩法  销售状态  1：可销售 2：不可销售
        var saleBfStatus: Int? = 0,//比分过关玩法  销售状态 1：可销售 2：不可销售
        var saleJqsStatus: Int? = 0,//总进球过关玩法  销售状态  1：可销售 2：不可销售
        var saleBqcStatus: Int? = 0,//半全场过关玩法  销售状态  1：可销售 2：不可销售
        var dgSaleSpfStatus: Int? = 0,//胜平负单关玩法  销售状态  1：可销售 2：不可销售
        var dgSaleRqspfStatus: Int? = 0,//让球胜平负单关玩法  销售状态  1：可销售 2：不可销售
        var dgSaleBfStatus: Int? = 0,//比分单关玩法  销售状态  1：可销售 2：不可销售
        var dgSaleJqsStatus: Int? = 0,//总进球单关玩法  销售状态  1：可销售 2：不可销售
        var dgSaleBqcStatus: Int? = 0//半全场单关玩法  销售状态  1：可销售 2：不可销售
)