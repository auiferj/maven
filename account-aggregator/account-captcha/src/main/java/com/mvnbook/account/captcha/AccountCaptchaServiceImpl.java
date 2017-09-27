package com.mvnbook.account.captcha;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.InitializingBean;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

public class AccountCaptchaServiceImpl implements AccountCaptchaService, InitializingBean {
	private DefaultKaptcha producer;

	private Map<String, String> captchaMap = new HashMap<String, String>();

	private List<String> preDefinedTexts;

	private int textCount = 0;
	//该方法会被Spring Framework初始化对象的时候调用
	public void afterPropertiesSet() throws Exception {
		//初始化验证码生成器
		producer = new DefaultKaptcha();
		//为producer提供默认的配置
		producer.setConfig(new Config(new Properties()));
	}

	public String generateCaptchaKey() {
		//生成一个长度为8的随机字符串作为key
		String key = RandomGenerator.getRandomString();
		//生成验证码字符串
		String value = getCaptchaText();

		captchaMap.put(key, value);

		return key;
	}

	public List<String> getPreDefinedTexts() {
		return preDefinedTexts;
	}

	public void setPreDefinedTexts(List<String> preDefinedTexts) {
		this.preDefinedTexts = preDefinedTexts;
	}

	private String getCaptchaText() {
		if (preDefinedTexts != null && !preDefinedTexts.isEmpty()) {
			//顺序的循环该字符串的列表来读取其中的值
			String text = preDefinedTexts.get(textCount);

			textCount = (textCount + 1) % preDefinedTexts.size();

			return text;
		} else {
			//用验证码图片生成器创建一个随机的字符串
			return producer.createText();
		}
	}

	public byte[] generateCaptchaImage(String captchaKey) throws AccountCaptchaException {
		String text = captchaMap.get(captchaKey);

		if (text == null) {
			throw new AccountCaptchaException("Captch key '" + captchaKey + "' not found!");
		}
		//使用producer生成一个BufferedImage
		BufferedImage image = producer.createImage(text);
		//之后的代码将这个图片对象转换成JPG格式的字节数组并返回，有了该字节数组，用户就能随意地将其保存成文件，或者在网页上显示
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			ImageIO.write(image, "jpg", out);
		} catch (IOException e) {
			throw new AccountCaptchaException("Failed to write captcha stream!", e);
		}

		return out.toByteArray();
	}

	public boolean validateCaptcha(String captchaKey, String captchaValue) throws AccountCaptchaException {
		String text = captchaMap.get(captchaKey);

		if (text == null) {
			throw new AccountCaptchaException("Captch key '" + captchaKey + "' not found!");
		}

		if (text.equals(captchaValue)) {
			captchaMap.remove(captchaKey);

			return true;
		} else {
			return false;
		}
	}
}
