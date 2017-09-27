package com.mvnbook.account.captcha;

import java.util.Random;
/**
 * 生成一个长度为8的字符串
 * nextInt返回一个大于等于0且小于n的整数，即range的字符的下标
 * charAt获得range中的一个字符
 * @author zhangwei
 *
 */
public class RandomGenerator {
	private static String range = "0123456789abcdefghijklmnopqrstuvwxyz";

	public static synchronized String getRandomString() {
		Random random = new Random();

		StringBuffer result = new StringBuffer();

		for (int i = 0; i < 8; i++) {
			result.append(range.charAt(random.nextInt(range.length())));
		}

		return result.toString();
	}
}
