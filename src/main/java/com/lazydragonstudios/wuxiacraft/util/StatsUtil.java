package com.lazydragonstudios.wuxiacraft.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class StatsUtil {

	public static String getShortHealthAmount(BigDecimal amountBig) {
		float amount = amountBig.floatValue();
		String value = "";
		if(amount < 0) value += "-";
		amount = Math.abs(amount);
		if (amount < 1000f) {
			value += (int)amount;
		} else if (amount < 10000f) {
			float mills = amount / 1000f;
			value += String.format("%.1fk", mills);
		} else if (amount < 1000000f) {
			float mills = amount / 1000f;
			value += String.format("%.0fk", mills);
		} else if (amount < 10000000f) {
			float mills = amount / 1000000f;
			value += String.format("%.1fM", mills);
		} else if (amount < 1000000000f) {
			float mills = amount / 1000000f;
			value += String.format("%.0fM", mills);
		} else if (amount < 10000000000f) {
			float mills = amount / 1000000000f;
			value += String.format("%.1fB", mills);
		} else if (amount < 1000000000000f) {
			float mills = amount / 1000000000f;
			value += String.format("%.0fB", mills);
		} else if (amount < 10000000000000f) {
			float mills = amount / 1000000000000f;
			value += String.format("%.1fT", mills);
		} else if (amount < 1000000000000000f) {
			float mills = amount / 1000000000000f;
			value += String.format("%.0fT", mills);
		} else if (amount < 10000000000000000f) {
			float mills = amount / 1000000000000000f;
			value += String.format("%.1fP", mills);
		} else if (amount < 1000000000000000000f) {
			float mills = amount / 1000000000000000f;
			value += String.format("%.0fP", mills);
		} else if (amount < 10000000000000000000f) {
			float mills = amount / 1000000000000000000f;
			value += String.format("%.1fE", mills);
		} else if (amount < 1000000000000000000000f) {
			float mills = amount / 1000000000000000000f;
			value += String.format("%.0fE", mills);
		} else if (amount < 10000000000000000000000f) {
			float mills = amount / 1000000000000000000000f;
			value += String.format("%.1fZ", mills);
		} else if (amount < 1000000000000000000000000f) {
			float mills = amount / 1000000000000000000000f;
			value += String.format("%.0fZ", mills);
		}
		return value;
	}
}
