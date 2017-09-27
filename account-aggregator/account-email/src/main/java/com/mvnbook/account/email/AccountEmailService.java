package com.mvnbook.account.email;

public interface AccountEmailService {
	// to为接收地址，subject为邮件主题，htmlText为邮件内容
	void sendMail(String to, String subject, String htmlText) throws AccountEmailException;
}
