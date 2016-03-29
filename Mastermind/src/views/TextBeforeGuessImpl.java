/**
 * 
 */
package views;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * @author Pedro Gordo
 *
 */
class TextBeforeGuessImpl implements ITextBeforeGuess
{
	private final String textBeforeGuess;

	/**
	 * @param textBeforeGuess
	 *            this text will be formatted with the number of plays
	 * @param numberOfPlays
	 */
	@Inject
	public TextBeforeGuessImpl(@Named("textBeforeGuess") String textBeforeGuess,
			@Named("numberOfPlays") int numberOfPlays)
	{
		super();
		this.textBeforeGuess = String.format(textBeforeGuess,
				new Integer(numberOfPlays));
	}

	@Override
	public void show()
	{
		System.out.print(this.textBeforeGuess);
	}

}
