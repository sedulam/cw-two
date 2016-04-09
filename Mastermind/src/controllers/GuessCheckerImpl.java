package controllers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import controllers.exception.InvalidGuessSizeInput;
import controllers.exception.NonExistingColourException;
import models.IPeg;

/**
 * Implementation for GuessChecker.
 * 
 * @author Pedro Gordo
 *
 */
class GuessCheckerImpl implements IGuessChecker
{
	private final IPeg BLACK_PEG;
	private final IPeg WHITE_PEG;

	private IPegGenerator pegGenerator;

	private String errorForInvalidGuessSize;

	/**
	 * Constructor requiring the secret code.
	 * 
	 * @param pegGenerator
	 * @param errorForInvalidGuessSize 
	 */
	@Inject
	public GuessCheckerImpl(IPegGenerator pegGenerator,
			@Named("errorForInvalidGuessSize") String errorForInvalidGuessSize)
	{
		this.pegGenerator = pegGenerator;
		this.errorForInvalidGuessSize = errorForInvalidGuessSize;
		
		this.BLACK_PEG = pegGenerator.getBlackPeg();
		this.WHITE_PEG = pegGenerator.getWhitePeg();
	}

	@Override
	public List<IPeg> getResult(List<IPeg> secretCode, String input)
			throws NonExistingColourException, InvalidGuessSizeInput
	{
		// parse the user input and return a list of pegs
		List<IPeg> pegList = parseUserInput(input);

		// Validate arguments.
		if (pegList.size() != secretCode.size()) 
			throw new InvalidGuessSizeInput(this.errorForInvalidGuessSize);

		// The answer list with black and white pegs.
		List<IPeg> finalList = new LinkedList<>();
		int blackPegs = 0;
		int whitePegs = 0;

		// First count all user guess pegs that match on secret code colours as
		// white pegs, then count how many user guess pegs match colour and 
		// index as black pegs. Subtract the black peg from the white peg count.

		// All matched colours regardless of index
		List<IPeg> matchedPegByColour = new LinkedList<>();
		for (IPeg secretPeg : secretCode)
		{
			// Look for user guess pegs that match this secret peg colour
			for (IPeg guessPeg : pegList)
			{
				if (guessPeg.getColour().equals(secretPeg.getColour()))
				{
					if ( !matchedPegByColour.contains(secretPeg) ) {
						matchedPegByColour.add(secretPeg);
						whitePegs++;
						break;
					}
				}
			}
		}

		// Subtract from white count the pegs that also match index.
		for (int index = 0; index < secretCode.size(); index++)
		{
			IPeg secretPeg = secretCode.get(index);
			IPeg guessPeg = pegList.get(index);

			// If index and colour match, it is a black peg and not white
			if (secretPeg.getColour().equals(guessPeg.getColour()))
			{
				blackPegs++;
				whitePegs--;
			}
		}

		// The number of white and black pegs expected in the result.
		for (int i = 0; i < blackPegs; i++)
		{
			finalList.add(this.BLACK_PEG);
		}
		for (int i = 0; i < whitePegs; i++)
		{
			finalList.add(this.WHITE_PEG);
		}

		return finalList;
	}

	@Override
	public List<IPeg> parseUserInput(String input)
			throws NonExistingColourException
	{
		// Parse user input and generate pegs
		List<String> parsedText = new ArrayList<>();
		for (char letter : input.toCharArray())
		{
			parsedText.add(Character.toString(letter));
		}

		List<IPeg> pegList = new ArrayList<>();
		for (int i = 0; i < parsedText.size(); i++)
		{
			String colour = parsedText.get(i);
			IPeg iPeg = this.pegGenerator.createPeg(colour);
			if (iPeg != null)
			{
				pegList.add(iPeg);
			}
		}
		return pegList;
	}

}
