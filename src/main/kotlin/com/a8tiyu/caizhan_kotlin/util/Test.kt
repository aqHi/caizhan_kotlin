package com.a8tiyu.caizhan_kotlin.util

import com.a8tiyu.caizhan_kotlin.entity.JCLQMatch
import com.a8tiyu.caizhan_kotlin.entity.JCZQMatch
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.util.JSONPObject
import java.util.stream.Stream

fun main(args: Array<String>) {
//    var data = List(990, { i -> i + 1 })

//    printFmt(data)
//
//    handleList(data, 3)


    var result = HttpUtilsTest()


    println("---")
    println(result)
    println("---")


//    println("状态码："+result.first)
//    println("响应内容："+result.second)


//    var objectMapper = ObjectMapper()
//    var map = objectMapper.readValue(result.second , HashMap::class.java)
//
//   var map = JsonUtils.fromJson(result.second , HashMap::class.java)
//
//    println(map.toString())


}

private fun HttpUtilsTest(): String {

    var objectMapper = ObjectMapper()
    val url = "http://query-api.8win.com/command/execute"
    var param = HashMap<String, String>()
    param.put("command", "400001")
    var result = HttpUtils.post(url, param, "utf-8")

    var jsonNode = JsonUtils.jackSonParse(result.second)
    var data = jsonNode.get("data")
    var matchList = data.get("matchList")
    for (i in matchList){
        var match = JsonUtils.fromJson(objectMapper.writeValueAsString(i), JCZQMatch::class.java)
        println("比赛编码:${match.matchNum},彩期号:${match.phase}")
    }


//    map = JsonUtils.fromJson(data, HashMap::class.java)
//    data = map.get("matchList") as String
//    var list = JsonUtils.fromJson(data, ArrayList::class.java)
//
//    for (i in matchList) {
//        var match = JsonUtils.fromJson(i.asText(), JCZQMatch::class.java)
//        println("比赛编码:${match.matchNum},彩期号:${match.phase}")
//    }

    return matchList.asText()
}

private fun printFmt(data: List<Int>) {
    for (i in data) {
        if (i % 99 == 0)
            println(i)
        else
            print("$i, ")
    }
}


fun handleList(data: List<Int>, threadNum: Int) {
    var length = data.size
    var t: Int = if (length / threadNum == 0) length / threadNum else length / threadNum + 1
    var i = 0
    while (i < threadNum) {
        val end = (i + 1) * t
        val thread = HandleListThread("线程[" + (i + 1) + "] ", data, i * t, if (end > length) length else end)
        thread.start()
        i++
    }
}


fun init() {


}

class HandleListThread : Thread {
    var threadName: String
    var data: List<Int>
    var start: Int
    var end: Int

    constructor(threadName: String, data: List<Int>, start: Int, end: Int) {
        this.threadName = threadName
        this.data = data
        this.start = start
        this.end = end
    }

    override fun run() {
        // TODO 这里处理数据
        val subList = data.subList(start, end)/*.add("^&*")*/
        for (i in subList) {
            println(i + 100)
        }
        println(threadName + "处理了" + subList.size + "条！")
    }


    fun add(data: List<Int>) {

    }
}
