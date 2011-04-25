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
package org.apache.synapse.message.processors.sampler;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.message.processors.AbstractMessageProcessor;
import org.apache.synapse.message.processors.ScheduledMessageProcessor;
import org.quartz.JobDetail;

public class SamplingProcessor extends ScheduledMessageProcessor{
    private Log log = LogFactory.getLog(SamplingProcessor.class);

    public static final String CONCURRENCY = "concurrency";
    public static final String SEQUENCE = "sequence";

    @Override
    protected JobDetail getJobDetail() {
        JobDetail jobDetail = new JobDetail();
        jobDetail.setName(messageStore + "-job");
        jobDetail.setJobClass(SamplingJob.class);
        return jobDetail;
    }
}