/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shuntingyard;

import java.util.HashMap;

/**
 *
 * @author thegoodhen
 */
public class NumberTokenFactory extends TokenFactory {//TODO: turn this into delegating factory

	HashMap<String, NumberToken> typeMap = new HashMap<>();

	//TODO: once a delagating factory, call the getShortName method of the NumberToken to get this list.
	public NumberTokenFactory() {
		typeMap.put("void", new ByteNumberToken("0"));//TODO: change to void
		typeMap.put("byte", new ByteNumberToken("0"));
		typeMap.put("int", new IntegerNumberToken("0"));
		typeMap.put("float", new FloatNumberToken("0"));
	}

	@Override
	public String getRegex() {
		return "[0-9][0-9]*\\.?[0-9]*[eE]?[0-9]*";
	}

	public String getTypeRegex()
	{
		StringBuilder sb=new StringBuilder();
		sb.append("(");
		for(String s:this.typeMap.keySet())
		{
			sb.append(s).append("|");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(")");
		return sb.toString();
	}
	
	public NumberToken generatePlaceHolder(String typeString) {

		return typeMap.get(typeString);
	}

	@Override
	public Token generateInstance(String tokenString) throws CompilerException{
		float value;
		try {

			value = Float.parseFloat(tokenString);

			if (tokenString.matches(".*[.eE].*"))//float
			{
				return new FloatNumberToken(tokenString);
			} else if (value <= 127 && value >= -128) {
				return new ByteNumberToken(tokenString);
			} else {
				return new IntegerNumberToken(tokenString);//TODO: handle float too
			}
		} catch (Exception e) {
			throw new CompilerException("Factory encountered a request to create a numeric token from a string, but the given string \"" + tokenString + "\" doesn't represent a valid numeric token");
		}
	}

}
