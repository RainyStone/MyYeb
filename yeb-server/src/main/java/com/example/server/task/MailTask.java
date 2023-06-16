package com.example.server.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.server.pojo.Employee;
import com.example.server.pojo.MailConstants;
import com.example.server.pojo.MailLog;
import com.example.server.service.IEmployeeService;
import com.example.server.service.IMailLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 邮件发送定时任务，消息可靠性保证
 * 当消息发送失败，该定时任务会自动读取数据库的消息日志
 * 尝试重新发送消息若干次
 */
@Slf4j
@Component
public class MailTask {
    @Autowired
    private IMailLogService mailLogService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private IEmployeeService employeeService;

    /**
     * 邮件发送定时任务
     * 10秒执行一次
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void mailTask(){
        //状态为0且重试时间小于当前时间的才需要重新发送
        List<MailLog> list = mailLogService.list(new QueryWrapper<MailLog>().eq("status", 0).lt("tryTime", LocalDateTime.now()));
        list.forEach(mailLog -> {
            //重试次数超过3次，状态直接更新为投递失败，不再重试
            if (3 <= mailLog.getCount()){
                mailLogService.update(new UpdateWrapper<MailLog>().set("status",2).eq("msgId",mailLog.getMsgId()));
            }
            //更新重试次数、更新时间、重试时间
            mailLogService.update(new UpdateWrapper<MailLog>().set("count",mailLog.getCount()+1).set("updateTime",LocalDateTime.now())
                    .set("tryTime",LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT)).eq("msgId",mailLog.getMsgId()));
            Employee employee = employeeService.getEmployee(mailLog.getEid()).get(0);
            log.info("MailTask: employee = " + employee );
            log.info("MailTask: msgId: " + mailLog.getMsgId());
            //重试发送消息
            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME,MailConstants.MAIL_ROUTING_KEY_NAME,employee,new CorrelationData(mailLog.getMsgId()));
        });
    }

}
