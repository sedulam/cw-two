package factories.modules;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * @author Pedro Gordo
 *
 */
public class PropertiesModule extends AbstractModule{

	@Override
	protected void configure() {
		try {
            Properties props = new Properties();
            //TODO replace by relative path
            props.load(new FileInputStream("/home/Pedro Gordo/git/cwtwo/Mastermind/resources/config.properties"));
            Names.bindProperties(binder(), props);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
	}

}
