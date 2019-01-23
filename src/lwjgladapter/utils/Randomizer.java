package lwjgladapter.utils;

import java.util.Random;

public class Randomizer {
	
	public static long RANDOM_SEED = System.currentTimeMillis();
	
	private static Random random = null;
	
	public static int getRNGNumber(int min, int max){
		if(random == null){
			random = new Random(RANDOM_SEED);
		}
		int diff = max - min;
		if(diff < 0){
			throw new IllegalArgumentException("Can't generate Random Number, because min(" + min + ") is bigger than max(" + max + ")!");
		}
		return min + random.nextInt(diff);
	}
	
	public static boolean getRNGBoolean(){
		if(random == null){
			random = new Random(RANDOM_SEED);
		}
		return random.nextBoolean();
	}

}
