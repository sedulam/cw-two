package classes;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import controllers.ICodeGenerator;
import controllers.IColourLoader;
import controllers.IFlowController;
import controllers.IPegCreator;
import factories.CaptureUserGuessFactory;
import factories.CodeGeneratorFactory;
import factories.ColourLoaderFactory;
import factories.FlowControllerFactory;
import factories.PegCreatorFactory;
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
	private PegCreatorFactory pegCreatorFactory;
	@Inject
	private TextBeforeGuessFactory textBeforeGuessFactory;
	@Inject
	private CodeGeneratorFactory codeGeneratorFactory;

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
		IColourLoader colourLoader = ColourLoaderFactory.factoryMethod();
		IPegCreator pegCreator = this.pegCreatorFactory
				.create(colourLoader.getColours());
		ICodeGenerator codeGenerator = this.codeGeneratorFactory.create(pegCreator);
		IStartText startText = StartTextFactory.factoryMethod();

		/*
		 * Start the game run
		 */
		startText.show();
		codeGenerator.generateNewCode();
		IFlowController flowController = FlowControllerFactory.factoryMethod();
		ICaptureUserGuess captureUserGuess = CaptureUserGuessFactory
				.factoryMethod();

		ITextBeforeGuess textBeforeGuess = this.textBeforeGuessFactory
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

			keepGuessing = flowController.isGameFinished();
		} while (keepGuessing);
	}

}
