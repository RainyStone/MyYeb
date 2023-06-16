package com.example.mail;

import com.example.server.pojo.Employee;
import com.example.server.pojo.MailConstants;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;


/**
 * @Description 消息接收者
 */
@Component
public class MailReceiver {
    public static final Logger logger = LoggerFactory.getLogger(MailReceiver.class);

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailProperties mailProperties;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * rabbitmq消费者处理消息进行邮件发送，消费消息时保证幂等性，不会进行二次消费
     */
    @RabbitListener(queues = MailConstants.MAIL_QUEUE_NAME)
    public void handler(Message message, Channel channel) {
        Employee employee = (Employee) message.getPayload();
        logger.info("MailReceiver:  employee = " + employee);
        MessageHeaders headers = message.getHeaders();
        //消息序号
        long tag = (long) headers.get(AmqpHeaders.DELIVERY_TAG);
        logger.info("tag = " + tag);
        String msgId = (String) headers.get("spring_returned_message_correlation");
        logger.info("msgId = " + msgId);
        HashOperations hash = redisTemplate.opsForHash();
        try {
            /**
             * （简易实现）消费端处理消息时幂等性保证，消费端会将处理成功的消息指纹写入redis，
             * 在进行消息处理时，会先判断redis中是否有该消息指纹，存在，则说明消息已处理成功，
             * 不再重复处理该消息
             */
            if (hash.entries("mail_log").containsKey(msgId)) {
                //redis中包含key，说明消息已经被消费
                logger.info("消息已经被消费========>{}", msgId);
                /**
                 * 手动确认消息
                 * tag:消息序号
                 * multiple:是否确认多条
                 */
                channel.basicAck(tag, false);
                return;
            }
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg);
            helper.setFrom(mailProperties.getUsername());
            helper.setTo(employee.getEmail());
            helper.setSubject("入职邮件");
            helper.setSentDate(new Date());
            Context context = new Context();
            context.setVariable("name", employee.getName());
            context.setVariable("posName", employee.getPosition().getName());
            context.setVariable("joblevelName", employee.getJoblevel().getName());
            context.setVariable("departmentName", employee.getDepartment().getName());
            String mail = templateEngine.process("mail", context);
            helper.setText(mail, true);
            //发送邮件
            javaMailSender.send(msg);
            logger.info("邮件发送成功");
            //将消息id存入redis
            hash.put("mail_log", msgId, "OK");
            logger.info("MailReceiver: redis---> msgId = " + msgId);
            //手动确认消息
            channel.basicAck(tag, false);
        } catch (Exception e) {
            try {
                /**
                 * 手动确认消息
                 * tag:消息序号
                 * multiple:是否确认多条
                 * requeue:是否退回到队列
                 */
                channel.basicNack(tag, false, true);
            } catch (IOException ioException) {
                logger.error("消息确认失败=====>{}", ioException.getMessage());
            }
            logger.error("MailReceiver + 邮件发送失败========{}", e.getMessage());
        }
    }
}
