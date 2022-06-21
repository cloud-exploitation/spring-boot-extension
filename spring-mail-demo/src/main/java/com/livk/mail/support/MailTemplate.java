package com.livk.mail.support;

import com.livk.common.Pair;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.util.Map;

/**
 * <p>
 * MailTemplate
 * </p>
 *
 * @author livk
 * @date 2022/6/21
 */
@Slf4j
@RequiredArgsConstructor
public class MailTemplate {
    @Getter
    private final Configuration configuration;
    @Getter
    private final JavaMailSender javaMailSender;

    /**
     * 发送邮件
     *
     * @param to      目标地址
     * @param from    发送地址
     * @param subject 主题
     * @param text    内容
     * @param isHtml  是否为Html
     * @param file    是否需要携带文件，不携带则为Null
     */
    public void send(Pair<String, String> from, String subject, String text, boolean isHtml, File file, String... to) {
        try {
            MimeMessage mimeMessage = MailMessageBuilder.builder(javaMailSender)
                    .from(from)
                    .to(to)
                    .subject(subject)
                    .text(text, isHtml)
                    .file(file)
                    .build();
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("send email error:{}", e.getMessage());
        }
    }

    public void send(MimeMessage mimeMessage) {
        try {
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("send email error:{}", e.getMessage());
        }
    }

    /**
     * 发送邮件
     *
     * @param to      目标地址
     * @param from    发送地址
     * @param subject 主题
     * @param text    内容
     * @param isHtml  是否为Html
     */
    public void send(Pair<String, String> from, String subject, String text, boolean isHtml, String... to) {
        send(from, subject, text, isHtml, null, to);
    }

    /**
     * 发送邮件
     *
     * @param to      目标地址
     * @param from    发送地址
     * @param subject 主题
     * @param text    内容
     * @param file    是否需要携带文件，不携带则为Null
     */
    public void send(Pair<String, String> from, String subject, String text, File file, String... to) {
        send(from, subject, text, false, file, to);
    }

    /**
     * 发送邮件
     *
     * @param to      目标地址
     * @param from    发送地址
     * @param subject 主题
     * @param text    内容
     */
    public void send(Pair<String, String> from, String subject, String text, String... to) {
        send(from, subject, text, false, null, to);
    }

    /**
     * 发送邮件
     *
     * @param to       目标地址
     * @param from     发送地址
     * @param subject  主题
     * @param template 模板
     * @param data     模板填充内容
     * @param file     是否需要携带文件，不携带则为Null
     */
    public void send(Pair<String, String> from, String subject, String template, Map<String, Object> data, File file, String... to) {
        try {
            Template tem = configuration.getTemplate(template);
            String templateStr = FreeMarkerTemplateUtils.processTemplateIntoString(tem, data);
            send(from, subject, templateStr, true, file, to);
        } catch (Exception e) {
            log.error("send email error:{}", e.getMessage());
        }
    }

    /**
     * 发送邮件
     *
     * @param to       目标地址
     * @param from     发送地址
     * @param subject  主题
     * @param template 模板
     * @param data     模板填充内容
     */
    public void send(Pair<String, String> from, String subject, String template, Map<String, Object> data, String... to) {
        send(from, subject, template, data, null, to);
    }
}