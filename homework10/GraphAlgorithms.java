import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.HashSet;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Zhen Jiang
 * @version 1.0
 * @userid zjiang330
 * @GTID 903402987
 *
 * Collaborators: n/a
 *
 * Resources: n/a
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input
     *                                  is null, or if start doesn't exist in
     *                                  the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("The start vertex is null");
        } else if (graph == null) {
            throw new IllegalArgumentException("The graph is null.");
        } else if (!(graph.getVertices().contains(start))) {
            throw new IllegalArgumentException("The start vertex doesn't "
                    + "exist in the graph");
        }

        Set<Vertex<T>> visitedSet = new HashSet<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        List<Vertex<T>> returnList = new LinkedList<>();

        visitedSet.add(start);
        queue.add(start);
        returnList.add(start);

        while (!(queue.isEmpty())
                && visitedSet.size() != graph.getVertices().size()) {
            Vertex<T> removedVertex = queue.remove();

            for (VertexDistance<T> i : adjList.get(removedVertex)) {
                Vertex<T> v = i.getVertex();
                if (!(visitedSet.contains(v))) {
                    visitedSet.add(v);
                    returnList.add(v);
                    queue.add(v);
                }
            }
        }

        return returnList;
    }


    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input
     *                                  is null, or if start doesn't exist in
     *                                  the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("The start vertex is null");
        } else if (graph == null) {
            throw new IllegalArgumentException("The graph is null");
        } else if (!(graph.getVertices().contains(start))) {
            throw new IllegalArgumentException("The start doesn't "
                                                    + "exist in the graph");
        }

        Set<Vertex<T>> visitedSet = new HashSet<>();
        List<Vertex<T>> returnList = new LinkedList<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();

        dfsHelper(start, adjList, visitedSet, returnList);

        return returnList;
    }

    /**
     *
     * @param vertex refers to the current vertex
     * @param adjList refers to the adjacency list
     * @param visitedSet refers to the visited set
     * @param returnList refer to the list to return
     * @param <T> refer to the generic type
     */
    private static <T> void dfsHelper(Vertex<T> vertex,
                          Map<Vertex<T>, List<VertexDistance<T>>> adjList,
                          Set<Vertex<T>> visitedSet,
                          List<Vertex<T>> returnList) {
        if (!(visitedSet.contains(vertex))) {
            visitedSet.add(vertex);
            returnList.add(vertex);

            for (VertexDistance<T> i : adjList.get(vertex)) {
                Vertex<T> v = i.getVertex();
                if (!(visitedSet.contains(v))) {
                    dfsHelper(v, adjList, visitedSet, returnList);
                }
            }
        }
    }


    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("The start vertex is null");
        } else if (graph == null) {
            throw new IllegalArgumentException("The graph is null.");
        } else if (!(graph.getVertices().contains(start))) {
            throw new IllegalArgumentException("The start vertex doesn't "
                                            + "exist in the graph");
        }

        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        Set<Vertex<T>> visitedSet = new HashSet<>();
        PriorityQueue<VertexDistance<T>> pq = new PriorityQueue<>();

        Map<Vertex<T>, Integer> distanceMap = new HashMap<>();
        for (Vertex<T> vertex : graph.getVertices()) {
            if (vertex.equals(start)) {
                distanceMap.put(vertex, 0);
            } else {
                distanceMap.put(vertex, Integer.MAX_VALUE);
            }
        }

        pq.add(new VertexDistance<>(start, 0));

        while (!(pq.isEmpty())
                && graph.getVertices().size() != visitedSet.size()) {
            VertexDistance<T> removed = pq.remove();
            Vertex<T> vertex = removed.getVertex();
            int distance = removed.getDistance();

            if (!(visitedSet.contains(vertex))) {
                visitedSet.add(vertex);
                distanceMap.replace(vertex, distance);

                for (VertexDistance<T> i : adjList.get(vertex)) {
                    if (!(visitedSet.contains(i.getVertex()))) {
                        Vertex<T> v = i.getVertex();
                        int distanceToV = distance + i.getDistance();
                        pq.add(new VertexDistance<>(v, distanceToV));
                    }
                }
            }
        }
        return distanceMap;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     *
     * You should NOT allow self-loops or parallel edges into the MST.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("The graph is null");
        }

        DisjointSet<Vertex<T>> ds = new DisjointSet<>(graph.getVertices());
        int numVertices = graph.getVertices().size();
        Set<Edge<T>> mstEdgeSet = new HashSet<>();

        PriorityQueue<Edge<T>> pq = new PriorityQueue<>();
        for (Edge<T> edge : graph.getEdges()) {
            pq.add(edge);
        }

        while (!(pq.isEmpty())
                && mstEdgeSet.size() != 2 * (numVertices - 1)) {
            Edge<T> removedEdge = pq.remove();
            Vertex<T> u = removedEdge.getU();
            Vertex<T> v = removedEdge.getV();

            if (!(ds.find(u).equals(ds.find(v)))) {
                ds.union(u, v);
                mstEdgeSet.add(new Edge<>(u, v, removedEdge.getWeight()));
                mstEdgeSet.add(new Edge<>(v, u, removedEdge.getWeight()));
            }
        }

        if (mstEdgeSet.size() == 2 * (numVertices - 1)) {
            return mstEdgeSet;
        } else {
            return null;
        }
    }
}
