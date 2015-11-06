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

import edu.stanford.ramcloud.RAMCloud;
import edu.stanford.ramcloud.RAMCloudObject;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Element;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Property;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;
import org.apache.tinkerpop.gremlin.structure.util.ElementHelper;
import org.ellitron.tinkerpop.gremlin.ramcloud.structure.util.RAMCloudHelper;

/**
 *
 * @author Jonathan Ellithorpe <jde@cs.stanford.edu>
 */
public class RAMCloudVertex implements Vertex, Element {
    private final RAMCloudGraph graph;
    byte[] id;
    private String label;
    
    public RAMCloudVertex(final RAMCloudGraph graph, final byte[] id, final String label) {
        this.graph = graph;
        this.id = id;
        this.label = label;
    }

    @Override
    public BigInteger id() {
        return new BigInteger(id);
    }

    @Override
    public String label() {
        return label;
    }

    @Override
    public Graph graph() {
        return graph;
    }
    
    @Override
    public void remove() {
        graph.removeVertex(this);
    }

    @Override
    public Edge addEdge(String label, Vertex inVertex, Object... keyValues) {
        return graph.addEdge(this, (RAMCloudVertex) inVertex, label, RAMCloudEdge.Directionality.DIRECTED, keyValues);
    }
    
    public Edge addBidirectionalEdge(String label, Vertex otherVertex, Object... keyValues) {
        return graph.addEdge(this, (RAMCloudVertex) otherVertex, label, RAMCloudEdge.Directionality.UNDIRECTED, keyValues);
    }
    
    @Override
    public Iterator<Edge> edges(Direction direction, String... edgeLabels) {
        switch(direction) {
            case OUT:
                return graph.vertexEdges(this, EnumSet.of(RAMCloudEdgeDirection.DIRECTED_OUT), edgeLabels);
            case IN:
                return graph.vertexEdges(this, EnumSet.of(RAMCloudEdgeDirection.DIRECTED_IN), edgeLabels);
            case BOTH:
                return graph.vertexEdges(this, EnumSet.of(RAMCloudEdgeDirection.DIRECTED_OUT, RAMCloudEdgeDirection.DIRECTED_IN), edgeLabels);
            default:
                throw new UnsupportedOperationException("Unrecognized direction value: " + direction);
        }
    }
    
    public Iterator<Edge> edges(EnumSet<RAMCloudEdgeDirection> edgeDirections, String... edgeLabels) {
        return graph.vertexEdges(this, edgeDirections, edgeLabels);
    }

    @Override
    public Iterator<Vertex> vertices(Direction direction, String... edgeLabels) {
        switch(direction) {
            case OUT:
                return graph.vertexNeighbors(this, EnumSet.of(RAMCloudEdgeDirection.DIRECTED_OUT), edgeLabels);
            case IN:
                return graph.vertexNeighbors(this, EnumSet.of(RAMCloudEdgeDirection.DIRECTED_IN), edgeLabels);
            case BOTH:
                return graph.vertexNeighbors(this, EnumSet.of(RAMCloudEdgeDirection.DIRECTED_OUT, RAMCloudEdgeDirection.DIRECTED_IN), edgeLabels);
            default:
                throw new UnsupportedOperationException("Unrecognized direction value: " + direction);
        }
    }
        
    public Iterator<Vertex> vertices(EnumSet<RAMCloudEdgeDirection> edgeDirections, String... edgeLabels) {
        return graph.vertexNeighbors(this, edgeDirections, edgeLabels);
    }

    @Override
    public <V> Iterator<VertexProperty<V>> properties(String... propertyKeys) {
        return graph.getVertexProperties(this, propertyKeys);
    }

    @Override
    public <V> VertexProperty<V> property(String key, V value) {
        return property(VertexProperty.Cardinality.single, key, value);
    }
    
    @Override
    public <V> VertexProperty<V> property(VertexProperty.Cardinality cardinality, String key, V value, Object... keyValues) {
        return graph.setVertexProperty(this, cardinality, key, value, keyValues);
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof RAMCloudVertex) {
            return this.id().equals(((RAMCloudVertex) object).id());
        }
        
        return false;
    }
    
    @Override
    public int hashCode() {
        return ElementHelper.hashCode(this);
    }
    
}
