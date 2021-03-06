package com.mvnbook.account.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mvnbook.account.captcha.AccountCaptchaService;

public class AccountServiceTest {

	private AccountService accountService;

	@Before
	public void prepare() throws Exception {
		String[] springConfigFiles = { "account-email.xml", "account-persist.xml", "account-captcha.xml",
				"account-service.xml" };

		ApplicationContext ctx = new ClassPathXmlApplicationContext(springConfigFiles);

		AccountCaptchaService accountCaptchaService = (AccountCaptchaService) ctx.getBean("accountCaptchaService");

		List<String> preDefinedTexts = new ArrayList<String>();
		preDefinedTexts.add("12345");
		preDefinedTexts.add("abcde");
		accountCaptchaService.setPreDefinedTexts(preDefinedTexts);

		accountService = (AccountService) ctx.getBean("accountService");

		File persistDataFile = new File("target/test-classes/persist-data.xml");
		if (persistDataFile.exists()) {
			persistDataFile.delete();
		}
	}

	@Test
	public void testAccountService() throws Exception {
		// 1. Get captcha
		String captchaKey = accountService.generateCaptchaKey();
		accountService.generateCaptchaImage(captchaKey);
		String captchaValue = "12345";

		// 2. Submit sign up Request
		SignUpRequest signUpRequest = new SignUpRequest();
		signUpRequest.setCaptchaKey(captchaKey);
		signUpRequest.setCaptchaValue(captchaValue);
		signUpRequest.setId("auifer");
		signUpRequest.setEmail("auiferj@163.com");
		signUpRequest.setName("xxf");
		signUpRequest.setPassword("xxf123");
		signUpRequest.setConfirmPassword("xxf123");
		signUpRequest.setActivateServiceUrl("http://localhost:8080/account/activate");
		accountService.signUp(signUpRequest);

		// 3. Read activation link
		/*由于使用163作为邮件服务器测试，邮件也发到了163邮箱，只能在邮箱中查看*/
		
		// 3a. Try login but not activated
		/*try {
			accountService.login("juven", "admin123");
			fail("Disabled account shouldn't be able to log in.");
		} catch (AccountServiceException e) {
		}*/

		// 4. Activate account
		/* 在邮箱中点击链接来激活 */
		
		// 5. Login with correct id and password
		/*accountService.login("xxf", "xxf123");*/

		// 5a. Login with incorrect password
		/*try {
			accountService.login("xxf", "xxf456");
			fail("Password is incorrect, shouldn't be able to login.");
		} catch (AccountServiceException e) {
		}*/

	}

	@After
	public void stopMailServer() throws Exception {
	}
}
