package com.lazydragonstudios.wuxiacraft.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class StatsUtil {

	public static String getShortHealthAmount(BigDecimal amount) {
		String value = "";
		if (amount.compareTo(BigDecimal.ZERO) < 0) {
			value += "-";
		}
		MathContext mc = new MathContext(3, RoundingMode.HALF_DOWN);
		amount = amount.abs(mc);
		if (amount.compareTo(new BigDecimal("1000")) < 0) {
			value += amount.setScale(0, RoundingMode.HALF_DOWN).toPlainString();
		} else if (amount.compareTo(new BigDecimal("10000")) < 0) {
			var mills = amount.divide(new BigDecimal("1000"), mc);
			value += String.format("%sk", mills.toPlainString());
		} else if (amount.compareTo(new BigDecimal("100000")) < 0) {
			var mills = amount.divide(new BigDecimal("10000"), mc);
			value += String.format("%sk", mills);
		} else if (amount.compareTo(new BigDecimal("1000000")) < 0) {
			var mills = amount.divide(new BigDecimal("100000"), mc);
			value += String.format("%sM", mills);
		} else if (amount.compareTo(new BigDecimal("10000000")) < 0) {
			var mills = amount.divide(new BigDecimal("1000000"), mc);
			value += String.format("%sM", mills);
		} else if (amount.compareTo(new BigDecimal("100000000")) < 0) {
			var mills = amount.divide(new BigDecimal("10000000"), mc);
			value += String.format("%sM", mills);
		} else if (amount.compareTo(new BigDecimal("1000000000")) < 0) {
			var mills = amount.divide(new BigDecimal("100000000"), mc);
			value += String.format("%sG", mills);
		} else if (amount.compareTo(new BigDecimal("10000000000")) < 0) {
			var mills = amount.divide(new BigDecimal("1000000000"), mc);
			value += String.format("%sG", mills);
		} else if (amount.compareTo(new BigDecimal("100000000000")) < 0) {
			var mills = amount.divide(new BigDecimal("10000000000"), mc);
			value += String.format("%sG", mills);
		} else if (amount.compareTo(new BigDecimal("1000000000000")) < 0) {
			var mills = amount.divide(new BigDecimal("100000000000"), mc);
			value += String.format("%sT", mills);
		} else if (amount.compareTo(new BigDecimal("10000000000000")) < 0) {
			var mills = amount.divide(new BigDecimal("1000000000000"), mc);
			value += String.format("%sT", mills);
		}
		return value;
	}
}
