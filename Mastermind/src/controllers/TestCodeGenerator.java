/**
 * 
 */
package controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import controllers.exception.NonExistingColourException;
import models.IPeg;
import models.PegFactory;

/**
 * Test the code generator controller.
 * 
 * @author Pedro Gordo
 */
public class TestCodeGenerator
{

	private static final int CODE_LENGTH = 4;
	private ICodeGenerator codeGenerator;

	/**
	 * List of known pegs.
	 */
	private IPeg greenPeg = PegFactory.create("G", "Green"); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The peg generator mock.
	 */
	@Mock
	private IPegGenerator pegGeneratorMock = Mockito.mock(IPegGenerator.class);

	/**
	 * The initial test setup.
	 * 
	 * @throws NonExistingColourException
	 */
	@Before
	public void setUp() throws NonExistingColourException
	{
		when(this.pegGeneratorMock.createRandomPeg()).thenReturn(this.greenPeg);
		this.codeGenerator = new CodeGeneratorImpl(CODE_LENGTH,
				this.pegGeneratorMock);
	}

	/**
	 * Makes sure that after the generateNewCode() is called, the code is not
	 * empty.
	 */
	@Test
	public void testIsNotNull()
	{
		assertNotNull(this.codeGenerator.generateNewCode());
	}

	/**
	 * Asserts that the generated code length is the same as the configurated
	 * value.
	 */
	@Test
	public void testGeneratedCodeSize()
	{
		int actual = this.codeGenerator.generateNewCode().size();
		assertEquals(CODE_LENGTH, actual);
	}
}
