public class SingletonMap {
	private static RedisCache instance = null;

	private SingletonMap() {
		// private constructor to prevent instantiation
	}

	public static synchronized RedisCache getInstance() {
		if (instance == null) {
			instance = new RedisCache();
		}
		return instance;
	}
}