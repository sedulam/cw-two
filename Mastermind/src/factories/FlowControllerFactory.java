package factories;

import com.google.inject.Guice;
import com.google.inject.Inject;

import controllers.IFlowController;
import guiceModules.ControllerModule;
import guiceModules.PropertiesModule;

public class FlowControllerFactory {
	
	@Inject
	public IFlowController factoryMethod() {
		return Guice.createInjector(new PropertiesModule(), new ControllerModule())
				.getInstance(IFlowController.class);
	}
}