package com.mvnbook.account.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class AccountEmailServiceImpl implements AccountEmailService {
	// 来自Spring Framework的帮助简化邮件发送的工具类库，邮件服务器配置信息通过spring在属性文件中读取，它相当于自己的邮箱
	private JavaMailSender javaMailSender;

	private String systemEmail;

	public void sendMail(String to, String subject, String htmlText) throws AccountEmailException {
		try {
			// msg对应了将要发送的邮件
			MimeMessage msg = javaMailSender.createMimeMessage();
			// MimeMessageHelper帮助设置该邮件的发送地址，收件地址，主题及内容
			MimeMessageHelper msgHelper = new MimeMessageHelper(msg);

			msgHelper.setFrom(systemEmail);
			msgHelper.setTo(to);
			msgHelper.setSubject(subject);
			msgHelper.setText(htmlText, true);// true表示邮件的内容为HTML格式

			javaMailSender.send(msg);
		} catch (MessagingException e) {
			throw new AccountEmailException("Faild to send mail.", e);
		}
	}

	// 帮助实现依赖注入
	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public String getSystemEmail() {
		return systemEmail;
	}

	public void setSystemEmail(String systemEmail) {
		this.systemEmail = systemEmail;
	}
}
