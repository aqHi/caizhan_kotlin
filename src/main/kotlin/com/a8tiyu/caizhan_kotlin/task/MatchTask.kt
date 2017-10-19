package com.a8tiyu.caizhan_kotlin.task

import com.a8tiyu.caizhan_kotlin.service.MatchService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component


@Component
class MatchTask{


    private val log = LoggerFactory.getLogger(MatchTask::class.java)

    @Autowired
    lateinit var matchService : MatchService

    @Scheduled(cron = "0 0/1 8-20 * * ?")
    fun executeFileUpLoadTask() {

        val current = Thread.currentThread()

        var param = HashMap<String , String>()
        param.put("url","")
        println("定时任务1："+current.id)
        log.info("ScheduledTest.executeFileDownLoadTask 定时任务1:"+current.id+ ",name:"+current.name)
    }


    @Scheduled(cron = "0 0/1 8-20 * * ?")
    fun executeUploadTask() {

        // 间隔1分钟,执行工单上传任务
        val current = Thread.currentThread()
        println("定时任务2:" + current.id)
        log.info("ScheduledTest.executeUploadTask 定时任务2:" + current.id + ",name:" + current.name)
    }

    @Scheduled(cron = "0 0/3 5-23 * * ?")
    fun executeUploadBackTask() {

        // 间隔3分钟,执行工单上传任务
        val current = Thread.currentThread()
        println("定时任务3:" + current.id)
        log.info("ScheduledTest.executeUploadBackTask 定时任务3:" + current.id + ",name:" + current.name)
    }





}