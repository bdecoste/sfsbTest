/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.jndiTest;

import javax.ejb.AccessTimeout;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.ConcurrencyManagementType;

//@Singleton
//@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class SingletonBean 
{
	
   public SingletonBean() {
	   init();
   }
   
//   @AccessTimeout(value = 240000)
   public void init(){
	 
   }
   
 //  @Schedule(hour = "*/1", minute="*/5", persistent=false)
 //  @AccessTimeout(value = 1200001)
   public void schedule() throws Exception {
	   System.out.println("Schedule firing ...");

	   Thread.sleep(60000);

	   System.out.println("Schedule finished");
   }
	
}
