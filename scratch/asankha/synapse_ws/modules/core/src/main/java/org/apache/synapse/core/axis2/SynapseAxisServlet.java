/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *   * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.synapse.core.axis2;

import java.lang.reflect.Field;

import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.transport.http.AxisServlet;
import org.apache.axis2.transport.http.ListingAgent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.ServerManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * Extends axis2 servlet functionality so that  avoid  starting listeners again
 */

public class SynapseAxisServlet extends AxisServlet {
    private final static Log log = LogFactory.getLog(SynapseAxisServlet.class);
    
    /**
     * Overrides init method so that avoid  starting listeners again
     *
     * @param config
     * @throws ServletException
     */
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        this.configContext = ServerManager.getInstance().getConfigurationContext();
        this.axisConfiguration = this.configContext.getAxisConfiguration();
        servletContext.setAttribute(this.getClass().getName(), this);
        this.servletConfig = config;
        
        // Initialize the agent field. Since it is declared private, we need to do
        // it using reflection.
        try {
            Field agentField = AxisServlet.class.getDeclaredField("agent");
            agentField.setAccessible(true);
            agentField.set(this, new ListingAgent(configContext));
        }
        catch (Throwable ex) {
        	log.warn("Unable to initialize AxisServlet#agent. Published WSDL documents may be inaccessible.");
        }
        
        initParams();
    }

    public void initContextRoot(HttpServletRequest req) {
        this.configContext.setContextRoot("/");
    }
}
