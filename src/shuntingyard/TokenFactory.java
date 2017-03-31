/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shuntingyard;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author thegoodhen
 */
public abstract class TokenFactory {

    Pattern p;
    Matcher m;
    private int regexEnd;
    private String regexMatch;

    public abstract String getRegex();
    private static TokenFactoryComparator comp = null;

    /**
     * Create a Token based on a given String and position in it.
     *
     * @param s the string that contains the part, from which the token should
     * be generated
     * @param position
     * @return null if the Token cannot be created, or the created Token
     */
    public Token create(String s, int position) throws CompilerException {
	if (tryInitializingFromRegex(s, position)) {
	    return generateInstance(this.getRegexMatch());
	}
	return null;
    }

    /**
     *
     * Return a number, related to the position in waiting line this Factory
     * will have, once it's being decided which Factory should process a token;
     * the lower niceness, the sooner will the offer be made to the particular
     * TokenFactory. This number is NOT the position in the line; it can be any
     * number, even a negative one. The Factory with a lower Nineness will be
     * made the offer sooner than a Factory with a higher Niceness. The order in
     * which the offer for processing a token will be made to multiple Factories
     * of same Niceness is undefined. This number serves at least 2 purposes: 1)
     * optimizing the parsing speed. Statistical methods can be implemented to
     * see which tokens are the most frequent and then the parser can try
     * contacting their respective Factories first. 2) this can also be used to
     * solve the situation, where one Factory "thinks" it can create a certain
     * Token from a given String, because of the regex match, but there is
     * infact another factory, which can create a Token from the said String,
     * too, and the second one creating it is a preferred behavior. For
     * instance: in expression a==b, we might conclude that the tokens are "a",
     * "=", "=", "b", that is "a" variable, followed by two assignment
     * operators, followed by "b" variable. However, the required behavior would
     * be to parse the said expression as "a", "==", b, the "==" being an
     * equivalence check operator.
     *
     * Simply put, the "niceness" helps avoid tokenization collisions caused by
     * ambiguous regex patterns, and as such allows the used regexes to be
     * simpler.
     *
     * @return the niceness value of this factory
     *
     */
    public int getNiceness() {
	return 0;
    }

    /**
     * This is the actual creation method, that calls the contstructor of the
     * object and returns the result.
     *
     * @param tokenString the String to pass to the constructor of the Token.
     * @return
     */
    public abstract Token generateInstance(String tokenString) throws CompilerException;

    public static final Comparator getFactoryComparator() {
	if (comp == null) {
	    comp = new TokenFactoryComparator();
	}
	return comp;
    }

    /**
     * Compares Factories based on their Niceness. Note:this comparator imposes
     * orderings that are inconsistent with equals. It judges Factories based
     * solely on their niceness, not on their specific type.
     *
     * @param o1
     * @param o2
     */
    private static class TokenFactoryComparator implements Comparator<TokenFactory> {

	private TokenFactoryComparator() {
	    //part of the Singleton design pattern;
	}

	@Override
	public int compare(TokenFactory o1, TokenFactory o2) {
	    if (o1.getNiceness() < o2.getNiceness()) {
		return -1;
	    } else if (o1.getNiceness() == o2.getNiceness()) {
		return 0;
	    } else {
		return 1;
	    }
	}

    }

    /**
     * Calculates and returns the position in the string this Token was just
     * generating from, corresponding to the end of the textual representation
     * of the Token in question. For instance, if the implementation is supposed
     * to generate tokens of variables, given the text "chicken+5", it returns 7
     * TODO: Check if this is actually correct; may be off-by-one This method
     * might be overriden for cases where this is not the intended behavior;
     *
     * For instance, when textual context is necessary to determine the type of
     * the Token, that is, to determine the Token type, more text is needed than
     * the text that actually corresponds to the Token itself. One example of
     * that would be distinguishing function calls from variables. Parenthesis
     * following the function call are vital to discovering this is indeed a
     * function call, but are not a part of the function call token.
     *
     * @return the position of the Token end in the initialization text.
     *
     */
    public int calculateRegexMatchEnd(Matcher m) {
	return this.regexEnd = m.end();
    }

    public int getRegexEnd() {
	return this.regexEnd;
    }

    protected void setRegexEnd(int regexEnd) {
	this.regexEnd = regexEnd;
    }

    public String getRegexMatch() {
	return regexMatch;
    }

    /**
     * This method will see if the String s has a regex at a given index, if so,
     * returns true and stores the part of s, which matches the regex, otherwise
     * returns false. It also determines and sets the position, where the regex
     * ends, using the calculateRegexMatchEnd method. The said position can then
     * later be retrieved using the getRegexEnd() method. The part of the
     * original string, matching the regex, can be retrieved using
     * getRegexMatch() method.
     *
     * @see calculateRegexMatchEnd()
     * @see getRegexEnd()
     * @param s the String to search for the regex in
     * @param index where in the String should the search begin.
     * @return end index of the token, -1 if token not present at the beginning
     * of the string
     */
    public boolean tryInitializingFromRegex(String s, int index) {
	p = Pattern.compile(this.getRegex());
	m = p.matcher(s);
	if (m.find(index)) {
	    if (m.start() == index) {
		calculateRegexMatchEnd(m);
		this.regexMatch = s.substring(index, this.regexEnd);
		return true;
	    }
	}
	return false;
    }
}
