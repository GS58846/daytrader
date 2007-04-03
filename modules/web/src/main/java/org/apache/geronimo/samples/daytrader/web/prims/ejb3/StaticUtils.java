package org.apache.geronimo.samples.daytrader.web.prims.ejb3;

import java.math.BigDecimal;
import java.util.Random;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 */
public class StaticUtils {
    
	public static final String ITERATION_PARAM = "itrCount";                
        public static final String PREFIX_PARAM = "prefix";
	
	private static int MIN_ACCOUNT_ID = 0;
	private static int MAX_ACCOUNT_ID = 499;
        private static String UID_PREFIX = "uid:";
		
	/*
	 * Extract iteration info from a request
	 */
	public static String getQuotePrefix(HttpServletRequest req){
		String prefixString = req.getParameter(PREFIX_PARAM);
		if (prefixString == null){
			return "defaultprefix";
		} else {
			return prefixString;
		}
	}
	/*
	 * Extract quote prefix info from a request
	 */
	public static int getIterations(HttpServletRequest req){
		String itrString = req.getParameter(ITERATION_PARAM);
		if (itrString == null){
			return 1;
		} else {
			return Integer.parseInt(itrString);
		}
	}        
	
	
	
	private static Random cachedRandom = new Random();
	public static int getRandomAccountID(){
		return cachedRandom.nextInt((MAX_ACCOUNT_ID - MIN_ACCOUNT_ID) + 1) + MIN_ACCOUNT_ID;
	}
	public static String getRandomUserID(){
		return UID_PREFIX + (cachedRandom.nextInt((MAX_ACCOUNT_ID - MIN_ACCOUNT_ID) + 1) + MIN_ACCOUNT_ID);
	} 
        public static BigDecimal getRandomBigDecimal(int max){
                return new BigDecimal(cachedRandom.nextDouble() * max);
        }
        public static double getRandomDouble(int max){
                return cachedRandom.nextDouble() * max;
        }
    
}
