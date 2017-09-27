package com.mvnbook.account.captcha;

import java.util.List;

public interface AccountCaptchaService {
	//生成随机验证码主键，用来标识验证码图片
	String generateCaptchaKey() throws AccountCaptchaException;
	//生成验证码图片
	byte[] generateCaptchaImage(String captchaKey) throws AccountCaptchaException;
	//验证用户反馈
	boolean validateCaptcha(String captchaKey, String captchaValue) throws AccountCaptchaException;
	//以下一组方法预定义验证的内容
	List<String> getPreDefinedTexts();

	void setPreDefinedTexts(List<String> preDefinedTexts);
}
