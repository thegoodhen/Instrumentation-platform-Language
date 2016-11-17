/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shuntingyard;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author thegoodhen
 */
public class StringLiteralTokenFactory extends TokenFactory {

	public StringLiteralTokenFactory() {

	}

	@Override
	public String getRegex() {
		return "\\\"(.*?)\\\"";
	}

	@Override
	public Token generateInstance(String tokenString) {
		Pattern p = Pattern.compile(this.getRegex());
		Matcher m = p.matcher(tokenString);
		if (m.find()) {
			return new StringLiteralToken(m.group(1));
		}
		return null;
	}

}
