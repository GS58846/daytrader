/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.geronimo.samples.daytrader.ejb;

import javax.ejb.EJBObject;
import java.rmi.Remote;
import java.rmi.RemoteException;

import org.apache.geronimo.samples.daytrader.*;

public interface Trade extends EJBObject, TradeServices, Remote {
	
   /**
	 * Publish to the QuoteChange Message topic when a stock
	 * price and volume are updated
	 * 
	 * This method is deployed as TXN REQUIRES NEW to avoid a 
	 * 2-phase commit transaction across the DB update and MDB access
	 * (i.e. a failure to publish will not cause the stock update to fail
	 *
	 * @param quoteData - the updated Quote
	 * @param oldPrice - the price of the Quote before the update
	 * @param sharesTraded - the quantity of sharesTraded
	 */
	public void publishQuotePriceChange(QuoteDataBean quoteData, java.math.BigDecimal oldPrice, java.math.BigDecimal changeFactor,  double sharesTraded) throws RemoteException;
	
	/**
	 * provides a simple session method with no database access to test performance of a simple
	 * path through a stateless session 
	 * @param investment amount
	 * @param NetValue current value
	 * @return return on investment as a percentage
	 */
	public double investmentReturn(double investment, double NetValue) throws RemoteException;	

	/**
	 * This method provides a ping test for a 2-phase commit operation
	 * 
	 * @param symbol to lookup
	 * @return quoteData after sending JMS message
	 */	
	public QuoteDataBean pingTwoPhase(String symbol) throws RemoteException;

}
