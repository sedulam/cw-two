package views;

import java.util.List;

import models.IGuessHistory;
import models.IPeg;

/**
 * @author Pedro Gordo
 *
 */
public interface IPrintHistory
{
	/**
	 * Prints the current history of the played guesses.
	 * 
	 * @param guessHistory
	 */
	public void printProgressingHistory(IGuessHistory guessHistory);

	/**
	 * Prints the history when the user got the answer right.
	 * 
	 * @param history
	 * @param secretCode
	 */
	public void printSuccessHistory(IGuessHistory history, List<IPeg> secretCode);
}
