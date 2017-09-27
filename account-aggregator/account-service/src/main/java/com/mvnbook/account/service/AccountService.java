package com.mvnbook.account.service;

public interface AccountService {
	//生成一个验证码的唯一标识符
	String generateCaptchaKey() throws AccountServiceException;
	//根据这个标识符生成验证码图片,图片以字节流的方式返回
	byte[] generateCaptchaImage(String captchaKey) throws AccountServiceException;
	//用户注册，成功后收到一个激活链接，链接包含一个激活码
	void signUp(SignUpRequest signUpRequest) throws AccountServiceException;
	//用户传入激活码，以验证账户
	void activate(String activationNumber) throws AccountServiceException;
	//登录
	void login(String id, String password) throws AccountServiceException;
}
