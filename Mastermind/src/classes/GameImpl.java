package classes;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import controllers.ICodeGenerator;
import controllers.IColourLoader;
import controllers.IFlowController;
import controllers.IPegFactory;
import factories.CaptureUserGuessFactory;
import factories.CodeGeneratorFactory;
import factories.ColourLoaderFactory;
import factories.FlowControllerFactory;
import factories.PegGeneratorFactory;
import factories.StartTextFactory;
import views.ICaptureUserGuess;
import views.IStartText;
import views.ITextBeforeGuess;
import views.TextBeforeGuessFactory;

/**
 * @author Keith Mannock
 *
 */
public class GameImpl extends GameAbstractImpl
{
	@Inject
	private PegGeneratorFactory pegGeneratorFactory;
	@Inject
	private StartTextFactory startTextFactory;
	@Inject
	private TextBeforeGuessFactory textBeforeGuessFactory;

	@Inject
	private GameImpl(@Named("easy") boolean easy)
	{
		super(easy);
	}

	@Override
	public void runGames()
	{
		/*
		 * Initialise factories, load properties and colours configuration
		 */
		ColourLoaderFactory colourLoaderFactory = new ColourLoaderFactory();
		IColourLoader colourLoader = colourLoaderFactory.factoryMethod();
		IPegFactory pegFactory = pegGeneratorFactory
				.create(colourLoader.getColours());
		CodeGeneratorFactory codeGeneratorFactory = new CodeGeneratorFactory();
		FlowControllerFactory flowControllerFactory = new FlowControllerFactory();
		ICodeGenerator codeGenerator = codeGeneratorFactory
				.factoryMethod(pegFactory);
		IStartText startText = startTextFactory.factoryMethod();
		CaptureUserGuessFactory captureUserGuessFactory = new CaptureUserGuessFactory();

		/*
		 * Start the game run
		 */
		startText.show();
		codeGenerator.generateNewCode();
		IFlowController flowController = flowControllerFactory.factoryMethod();
		ICaptureUserGuess captureUserGuess = captureUserGuessFactory
				.factoryMethod();

		ITextBeforeGuess textBeforeGuess = textBeforeGuessFactory
				.create(codeGenerator.getCodeString());

		/*
		 * This variable is used to know if the number of tries has finished or
		 * if the secret code was guessed
		 */
		boolean keepGuessing = false;
		do
		{
			textBeforeGuess.show();
			String userGuess = captureUserGuess.captureGuess();
			System.out.println("#### CAPTURED: " + userGuess);

			keepGuessing = flowController.isGameFinished();
			// TODO change condition for loop
		} while (keepGuessing);
	}

}
