package com.mvnbook.account.email;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AccountEmailServiceTest {

	@Test
	public void testSendMail() throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("account-email.xml");
		AccountEmailService accountEmailService = (AccountEmailService) ctx.getBean("accountEmailService");

		String subject = "Test Subject";
		String htmlText = "<h3>TestMaven</h3>";
		accountEmailService.sendMail("auiferj@163.com", subject, htmlText);
	}

}
