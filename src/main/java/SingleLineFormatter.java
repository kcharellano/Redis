import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class SingleLineFormatter extends Formatter {
	@Override
	public String format(LogRecord record) {
		String dateString = new Date(record.getMillis()).toString();
		String source = record.getSourceClassName() + " " +  record.getSourceMethodName();
		String actualLog = record.getLevel() + ": " + record.getMessage() + "\n";
		return String.join(" ", dateString, source, actualLog);
	}
}