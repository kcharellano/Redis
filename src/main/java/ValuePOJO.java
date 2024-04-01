import java.util.HashMap;
import java.util.Map;

public class ValuePOJO {
	private String value;
	private long timeInit;
	private Map<String, Integer> opts;

	public ValuePOJO(String value, Map<String, Integer> opts) {
		setValue(value, opts);
	}

	public String getValue() {
		if (opts.get("px") != -1 && System.currentTimeMillis() - timeInit > opts.get("px")) {
			return null;
		}
		return value;
	}

	public void setValue(String value, Map<String, Integer> opts) {
		this.value = value;
		this.opts = new HashMap<>();
		if (opts != null) {
			this.opts.putAll(opts);
		} else {
			// default values
			this.opts.put("px", -1);
		}
		this.timeInit = System.currentTimeMillis();
	}
}
