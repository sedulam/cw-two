/**
 * 
 */
package controllers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;

import controllers.pegGenerator.IPegGenerator;
import models.IPeg;

/**
 * @author pdeara01
 *
 */
public class CodeGeneratorImpl implements ICodeGenerator {

	private final IPegGenerator pegGenerator;
	private final int CODE_LENGTH;
	private Map<Integer, IPeg> generatedCodePegs;
	
	@Inject
	public CodeGeneratorImpl(int codeLength, IPegGenerator pegGenerator) {
		super();
		this.CODE_LENGTH = codeLength;
		this.pegGenerator = pegGenerator;
	}

	/* (non-Javadoc)
	 * @see controllers.ICodeGenerator#generateNewCode()
	 */
	@Override
	public void generateNewCode() {
		generatedCodePegs = new HashMap<>();
		
		for (int i = 0; i < CODE_LENGTH; i++) {
			IPeg peg = pegGenerator.getAPeg();
			if (peg != null) {
				generatedCodePegs.put(i, peg);
			}
		}
	}

	/* (non-Javadoc)
	 * @see controllers.ICodeGenerator#getCode()
	 */
	@Override
	public List<IPeg> getCode() {
		List<IPeg> finalList = new LinkedList<>();
		for(int index = 0; index < generatedCodePegs.size(); index++ ) {
			finalList.add(generatedCodePegs.get(index));
		}
		return finalList;
	}

	@Override
	public String getCodeString() {
		String codeString = "";
		
		for (IPeg peg : getCode()) {
			codeString += peg.getColour();
		}
		
		return codeString;
	}
}
