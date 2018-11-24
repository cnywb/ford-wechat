/*
 * Copyright 2016 Suraj Muraleedharan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.batch.admin.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Suraj Muraleedharan
 *
 */
public class BatchAdminLogger {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(BatchAdminLogger.class);

    /**
     * This constructor is made private because this is a utility class and
     * instance of a utility class must not be created.
     */
    private BatchAdminLogger() {
        super();
    }

    /**
     * Returns the logger object.
     *
     * @return the logger object
     */
    public static final Logger getLogger() {
        return LOG;

    }
}
