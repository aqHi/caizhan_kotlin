package com.a8tiyu.caizhan_kotlin.service

import com.a8tiyu.caizhan_kotlin.entity.JCZQMatch
import com.a8tiyu.caizhan_kotlin.repository.JCZQMatchRepository
import com.a8tiyu.caizhan_kotlin.util.HttpUtils
import com.a8tiyu.caizhan_kotlin.util.JsonUtils
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class MatchService {


    @Autowired
    lateinit var jCZQMatchRepository : JCZQMatchRepository

    fun <T> getMatchList(map: Map<String, String>, type: Class<T>) {


        var objectMapper = ObjectMapper()
        val url = map["url"] ?: ""
        var command = map["command"] ?: ""

        var param = HashMap<String, String>()
        param.put("command", command)
        var result = HttpUtils.post(url, param, "utf-8")

        var jsonNode = JsonUtils.jackSonParse(result.second)
        var data = jsonNode.get("data")
        var matchList = data.get("matchList")
        for (i in matchList) {
            var match = JsonUtils.fromJson(objectMapper.writeValueAsString(i), JCZQMatch::class.java)
            jCZQMatchRepository.save(match)
            println("比赛编码:${match.matchNum},彩期号:${match.phase}")
        }
    }
}
