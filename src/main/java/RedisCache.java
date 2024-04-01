import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RedisCache {
	private final ConcurrentMap<String, ValuePOJO> hm;

	public RedisCache() {
		hm = new ConcurrentHashMap<>();
	}

	public void set(String key, String value, Map<String, Integer> opts) {
		hm.put(key, new ValuePOJO(value, opts));
	}

	public String get(String key) {
		if (hm.containsKey(key)){
			ValuePOJO vp = hm.get(key);
			if (vp.getValue() != null) {
				return vp.getValue();
			}
			hm.remove(key);
		}
		return null;
	}

}
