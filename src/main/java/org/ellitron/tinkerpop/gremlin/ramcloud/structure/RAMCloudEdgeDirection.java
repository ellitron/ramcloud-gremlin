/*
 * Copyright 2015 Apache Software Foundation.
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
package org.ellitron.tinkerpop.gremlin.ramcloud.structure;

/**
 *
 * Direction categories for incident edges to a vertex.
 * 
 * @author Jonathan Ellithorpe <jde@cs.stanford.edu>
 */
public enum RAMCloudEdgeDirection {
    /**
     * Outgoing edge category.
     */
    DIRECTED_OUT,
    
    /**
     * Incoming edge category.
     */
    DIRECTED_IN,
    
    /**
     * Undirected edge category.
     */
    UNDIRECTED;
}
