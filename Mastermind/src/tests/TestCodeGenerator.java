/**
 * 
 */
package tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test the code generator controller.
 *  
 * @author pdeara01
 */
public class TestCodeGenerator {

	private static final int CODE_LENGTH = 4;
	private ICodeGenerator codeGenerator;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		codeGenerator = new CodeGeneratorImpl(CODE_LENGTH);
	}
	
	@Test
	public void testIsNotNull(){
		codeGenerator.generateNewCode();
		assertNotNull(codeGenerator.getCode());
	}

	@Test
	public void testIsNull(){
		assertNull(codeGenerator.getCode());
	}
	
	@Test
	public void testGeneratedCode() {
		codeGenerator.generateNewCode();
		int actual = codeGenerator.getCode().getSize();
		assertEquals(CODE_LENGTH, actual);
	}
}
