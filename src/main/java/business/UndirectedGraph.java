package business;

import java.util.*;

/**
 * Created with Intellij IDEA.
 * Author: Chernyshov Yuriy - Mobile Development
 * Date: 25.11.14
 * Time: 11:23
 */

/**
 * {@link UndirectedGraph} is a data structure to hold and manipulate input data. This graph is undirected.
 */
public class UndirectedGraph {

    /**
     * This map holds association between concrete user (as users email) and all corresponded emails that are belongs
     * to this user. Corresponded emails are stored in the set to avoid duplicates.
     */
    private final Map<String, Set<String>> dataMap = new TreeMap<>();

    /**
     * Set of all vertexes in the graph.
     */
    private Set<String> vertexes = new TreeSet<>();

    /**
     * This method perform add operation. It adds single node to the graph (Vertex) if it does not exist and
     * add to it its edges (as a reference 'name' to the destination Vertex)
     *
     * @param vertex Vertex of the graph.
     * @param edge   Edge of the Vertex.
     */
    public void addNode(final String vertex, final String edge) {
        // Get set of the emails associated with concrete user
        Set<String> edges = dataMap.get(vertex);

        // If there is none - create it
        if (edges == null) {
            // Apply custom comparator in order to compare users emails as strings
            edges = new TreeSet<>(new EmailComparator());

            dataMap.put(vertex, edges);
        }

        // Put to the set next associated email
        edges.add(edge);
        vertexes.add(vertex);
    }

    /**
     * This method allows to calculate a result whether given vertexes has a common edge.
     *
     * @param sourceVertex Source Vertex.
     * @param targetVertex Destination Vertex
     * @return
     */
    public boolean containsEdge(final String sourceVertex, final String targetVertex) {
        return dataMap.get(sourceVertex).contains(targetVertex)
                && dataMap.get(targetVertex).contains(sourceVertex);
    }

    public Set<String> vertexSet() {
        return vertexes;
    }

    /**
     * {@link UndirectedGraph.EmailComparator} is a helper class which performs email natural compare.
     */
    private class EmailComparator implements Comparator<String> {

        @Override
        public int compare(final String str1, final String str2) {
            return str1.compareTo(str2);
        }
    }
}
