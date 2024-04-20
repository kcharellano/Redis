import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerInitializer {
	public static Logger init(Class clazz) {
		Logger logger = Logger.getLogger(clazz.getName());
		logger.setUseParentHandlers(false);
		logger.setLevel(Level.ALL);

		ConsoleHandler handler = new ConsoleHandler();
		handler.setFormatter(new SingleLineFormatter());
		logger.addHandler(handler);

		return logger;
	}
}
