/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shuntingyard;

import java.util.ArrayList;

/**
 *
 * @author thegoodhen
 */




public class LineParser extends AbstractParser {

	@Override
	public void parsingError(int tokenNumber) throws CompilerException {

		throw new CompilerException("Syntax error on line: "+(tokenNumber+1)+"! Parser exiting!");
	}

	public LineParser() {
		this.setFactory(new GeneralLineTokenFactory());
	}
	//TODO: reimplement
	/*public ArrayList<Token> Tokenize(String s) {
		ArrayList<Token> returnList = new ArrayList<>();
		String lines[] = s.split("\\r?\\n+");
		int lineNumber = 1;
		for (String s2 : lines) {
			s2 = s2.trim();
			for (Integer i : this.getAvailableTokenMap().keySet()) {
				Token t = getAvailableTokenMap().get(i);
				if (s2.matches(t.getRegex())) {
					GeneralLineTokenFactory gltf = new GeneralLineTokenFactory();
					Token t2 = gltf.create(s2, 0);
					if (t2 == null) {
						System.err.println("Unknown line token (syntax error) on line " + lineNumber + "! Parser exiting! Line: "+s2);
						break;
					}
					returnList.add(t2);
					break;
				}

			}
			lineNumber++;
		}
		return returnList;
	}*/

}
