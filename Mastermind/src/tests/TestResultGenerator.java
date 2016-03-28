/**
 * 
 */
package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controllers.IResultGenerator;
import controllers.ResultGeneratorImpl;
import models.IPeg;
import models.PegGenerationError;
import models.PegImpl;

/**
 * Test the class that will evaluate guesses.
 * 
 * @author Pedro Gordo
 *
 */
public class TestResultGenerator
{

	/**
	 * The Generated code.
	 */
	private List<IPeg> generatedCode = null;

	/**
	 * The White colour name
	 */
	private final String WHITE_COLOUR_NAME = "White";

	/**
	 * The White colour
	 */
	private final String WHITE_COLOUR = "W";

	/**
	 * The Black colour
	 */
	private final String BLACK_COLOUR = "B";

	/**
	 * The Black colour nameResultGenerator
	 */
	private final String BLACK_COLOUR_NAME = "Black";

	/**
	 * The result generator handler to test with.
	 */
	private IResultGenerator resultGenerator;

	private IPeg greenPeg = new PegImpl("G", "Green");
	private IPeg bluePeg = new PegImpl("B", "Blue");
	private IPeg yellowPeg = new PegImpl("Y", "Yellow");
	private IPeg redPeg = new PegImpl("R", "Red");

	/**
	 * Creates the secret code to which the guess will be matched against.
	 */
	@Before
	public void setUp()
	{
		generatedCode = new ArrayList<IPeg>();
		generatedCode.add(greenPeg);
		generatedCode.add(bluePeg);
		generatedCode.add(yellowPeg);
		generatedCode.add(redPeg);

		// TODO use Guice
		resultGenerator = new ResultGeneratorImpl();
		resultGenerator.setGeneratedCode(generatedCode);
	}

	/**
	 * We should get 2 white pegs as the result.
	 */
	@Test
	public void testTwoWhitePegs()
	{
		List<IPeg> guess = new ArrayList<IPeg>();
		guess.add(new PegImpl("G", "Green"));
		guess.add(new PegImpl("J", "Joker"));
		guess.add(new PegImpl("Y", "Yellow"));
		guess.add(new PegImpl("J", "Joker"));

		List<IPeg> resultSet = resultGenerator.getResult(guess);
		List<IPeg> expectedResult = new LinkedList<IPeg>();
		expectedResult.add(new PegImpl(WHITE_COLOUR, WHITE_COLOUR_NAME));
		expectedResult.add(new PegImpl(WHITE_COLOUR, WHITE_COLOUR_NAME));
		verify(expectedResult, resultSet);
	}

	/**
	 * We should get 2 black pegs as the result.
	 */
	@Test
	public void testTwoBlackPegs()
	{
		List<IPeg> guess = new ArrayList<IPeg>();
		guess.add(new PegImpl("G", "Green"));
		guess.add(new PegImpl("B", "Blue"));
		guess.add(new PegImpl("J", "Joker"));
		guess.add(new PegImpl("J", "Joker"));

		List<IPeg> resultSet = resultGenerator.getResult(guess);
		List<IPeg> expectedResult = new LinkedList<IPeg>();
		expectedResult.add(new PegImpl(BLACK_COLOUR, BLACK_COLOUR_NAME));
		expectedResult.add(new PegImpl(BLACK_COLOUR, BLACK_COLOUR_NAME));
		verify(expectedResult, resultSet);
	}

	/**
	 * We should get 2 white and 2 black pegs as the result.
	 */
	@Test
	public void testTwoWhiteTwoBlackPegs()
	{
		List<IPeg> guess = new ArrayList<IPeg>();
		guess.add(new PegImpl("G", "Green"));
		guess.add(new PegImpl("B", "Blue"));
		guess.add(new PegImpl("R", "Red"));
		guess.add(new PegImpl("Y", "Yellow"));

		List<IPeg> resultSet = resultGenerator.getResult(guess);
		List<IPeg> expectedResult = new LinkedList<IPeg>();
		expectedResult.add(new PegImpl(BLACK_COLOUR, BLACK_COLOUR_NAME));
		expectedResult.add(new PegImpl(BLACK_COLOUR, BLACK_COLOUR_NAME));
		expectedResult.add(new PegImpl(WHITE_COLOUR, WHITE_COLOUR_NAME));
		expectedResult.add(new PegImpl(WHITE_COLOUR, WHITE_COLOUR_NAME));
		verify(expectedResult, resultSet);
	}

	/**
	 * We should get 4 black pegs as the result.
	 */
	@Test
	public void testAllBlackPegs()
	{
		List<IPeg> guess = new ArrayList<IPeg>();
		guess.add(new PegImpl("G", "Green"));
		guess.add(new PegImpl("B", "Blue"));
		guess.add(new PegImpl("Y", "Yellow"));
		guess.add(new PegImpl("R", "Red"));

		List<IPeg> resultSet = resultGenerator.getResult(guess);
		List<IPeg> expectedResult = new LinkedList<IPeg>();
		expectedResult.add(new PegImpl(BLACK_COLOUR, BLACK_COLOUR_NAME));
		expectedResult.add(new PegImpl(BLACK_COLOUR, BLACK_COLOUR_NAME));
		expectedResult.add(new PegImpl(BLACK_COLOUR, BLACK_COLOUR_NAME));
		expectedResult.add(new PegImpl(BLACK_COLOUR, BLACK_COLOUR_NAME));
		verify(expectedResult, resultSet);
	}

	/**
	 * We should get 4 white pegs as the result.
	 */
	@Test
	public void testAllWhitePegs()
	{
		List<IPeg> guess = new ArrayList<IPeg>();
		guess.add(new PegImpl("B", "Blue"));
		guess.add(new PegImpl("G", "Green"));
		guess.add(new PegImpl("R", "Red"));
		guess.add(new PegImpl("Y", "Yellow"));

		List<IPeg> resultSet = resultGenerator.getResult(guess);
		List<IPeg> expectedResult = new LinkedList<IPeg>();
		expectedResult.add(new PegImpl(WHITE_COLOUR, WHITE_COLOUR_NAME));
		expectedResult.add(new PegImpl(WHITE_COLOUR, WHITE_COLOUR_NAME));
		expectedResult.add(new PegImpl(WHITE_COLOUR, WHITE_COLOUR_NAME));
		expectedResult.add(new PegImpl(WHITE_COLOUR, WHITE_COLOUR_NAME));
		verify(expectedResult, resultSet);
	}

	/**
	 * These pegs don't exist, hence we should get an exception
	 */
	@Test(expected = PegGenerationError.class)
	public void testNonExistentPegs()
	{
		List<IPeg> guess = new ArrayList<IPeg>();
		guess.add(new PegImpl("H", "Hahah"));
		guess.add(new PegImpl("J", "Joker"));
		guess.add(new PegImpl("Y", "Yes"));
		guess.add(new PegImpl("N", "No"));

		List<IPeg> resultSet = resultGenerator.getResult(guess);
	}

	/**
	 * The guess size is of different size than the code length, hence we should
	 * get an IO Exception.
	 */
	@Test(expected = IOException.class)
	public void testWrongGuessSize()
	{
		List<IPeg> guess = new ArrayList<IPeg>();
		guess.add(new PegImpl("H", "Hahah"));
		guess.add(new PegImpl("J", "Joker"));
		guess.add(new PegImpl("Y", "Yes"));

		List<IPeg> resultSet = resultGenerator.getResult(guess);
	}

	/**
	 * The "Yes" colour doesn't exist, so we should get and exception.
	 */
	@Test(expected = PegGenerationError.class)
	public void testOneInvalidPeg()
	{
		List<IPeg> guess = new ArrayList<IPeg>();
		guess.add(new PegImpl("G", "Green"));
		guess.add(new PegImpl("B", "Blue"));
		guess.add(new PegImpl("Y", "Yes"));
		guess.add(new PegImpl("R", "Red"));

		List<IPeg> resultSet = resultGenerator.getResult(guess);
	}

	/**
	 * The size is wrong, so we should get an exception.
	 */
	@Test(expected = IOException.class)
	public void testOneExtraInvalidPeg()
	{
		List<IPeg> guess = new ArrayList<IPeg>();
		guess.add(new PegImpl("G", "Green"));
		guess.add(new PegImpl("B", "Blue"));
		guess.add(new PegImpl("Y", "Yes"));
		guess.add(new PegImpl("R", "Red"));
		guess.add(new PegImpl("Y", "Yellow"));

		List<IPeg> resultSet = resultGenerator.getResult(guess);
	}

	/**
	 * Verifying that the expected peg list matched the found one.
	 * 
	 * @param expected
	 *            List IPeg
	 * @param found
	 *            List IPeg
	 */
	private void verify(List<IPeg> expected, List<IPeg> found)
	{
		assertNotNull(found);
		assertTrue(expected.size() == found.size());
		int index = 0;
		for (IPeg expectedPeg : expected)
		{
			assertEquals(expectedPeg.getColourName(),
					found.get(index).getColourName());
			index++;
		}
	}

}
