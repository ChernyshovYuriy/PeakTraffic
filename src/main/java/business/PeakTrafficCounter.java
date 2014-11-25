package business;

import util.Logger;
import vo.LogEntity;

import java.util.*;

/**
 * Created with Intellij IDEA.
 * Author: Chernyshov Yuriy - Mobile Development
 * Date: 24.11.14
 * Time: 14:13
 */

/**
 * {@link business.PeakTrafficCounter} is the main logic holder. It performs peak traffic calculation.
 * Big Thank You to the:
 * http://showme.physics.drexel.edu/usefulchem/Software/Drexel/Cheminformatics/Java/st2d/jgrapht-0.7.0/src/org/jgrapht/alg/BronKerboschCliqueFinder.java
 * and all contributors for the implementation of the Bron–Kerbosch algorithm (http://en.wikipedia.org/wiki/Bron%E2%80%93Kerbosch_algorithm)
 */
public class PeakTrafficCounter implements LogReaderListener {

    /**
     * Data structure to hold and process incoming data.
     */
    private UndirectedGraph graph = new UndirectedGraph();

    /**
     * Collection of the cliques.
     */
    private final Collection<TreeSet<String>> cliques = new ArrayList<>();

    /**
     * Minimum number of the users in the cluster
     */
    private static final int CLUSTER_MIN_COUNT = 3;

    @Override
    public void processLogRecord(final LogEntity record) {

        // Fill in graph
        graph.addNode(record.getUser(), record.getUserReceiver());
    }

    @Override
    public void onProcessComplete() {
        if (!Logger.isIsTestMode()) {
            Logger.printMessage("Start process data ...");
        }

        final StringBuilder stringBuilder = new StringBuilder();
        for (Set<String> set : getBiggestMaximalCliques()) {
            stringBuilder.setLength(0);
            for (String message: set) {
                stringBuilder.append(message);
                stringBuilder.append(", ");
            }
            stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length(), "");
            Logger.printMessage(stringBuilder.toString().trim());
        }

        if (!Logger.isIsTestMode()) {
            Logger.printMessage("Done.");
        }
    }

    /**
     * Finds all maximal cliques of the graph.  A clique is maximal if it is
     * impossible to enlarge it by adding another vertex from the graph. Note
     * that a maximal clique is not necessarily the biggest clique in the graph.
     *
     * @return Collection of cliques (each of which is represented as a Set of vertices)
     */
    private Collection<TreeSet<String>> getAllMaximalCliques() {
        cliques.clear();

        final List<String> potentialClique = new ArrayList<>();
        final List<String> candidates = new ArrayList<>();
        final List<String> alreadyFound = new ArrayList<>();

        candidates.addAll(graph.vertexSet());
        findCliques(potentialClique, candidates, alreadyFound);

        return cliques;
    }

    /**
     * This method allows to find the biggest maximal cliques of the graph.
     *
     * @return Collection of cliques (each of which is represented as a Set of vertices)
     */
    private Collection<TreeSet<String>> getBiggestMaximalCliques() {
        // first, find all cliques
        getAllMaximalCliques();

        int maximum = 0;
        final Collection<TreeSet<String>> biggestCliques = new ArrayList<>();
        for (TreeSet<String> clique : cliques) {
            if (maximum < clique.size()) {
                maximum = clique.size();
            }
        }
        for (TreeSet<String> clique : cliques) {
            if (maximum == clique.size()) {
                biggestCliques.add(clique);
            }
        }
        return biggestCliques;
    }

    /**
     * Find all clicks (http://en.wikipedia.org/wiki/Clique_(graph_theory)).
     *
     * @param potentialClique Collection of the potential cliques.
     * @param candidates      Collection of the candidates.
     * @param alreadyFound    Collection of the found cliques.
     */
    private void findCliques(final List<String> potentialClique,
                             final List<String> candidates,
                             final List<String> alreadyFound) {

        final List<String> candidatesArray = new ArrayList<>(candidates);

        if (!end(candidates, alreadyFound)) {
            // for each candidate_node in candidates do
            for (String candidate : candidatesArray) {
                List<String> newCandidates = new ArrayList<>();
                List<String> newAlreadyFound = new ArrayList<>();

                // move candidate node to potentialClique
                potentialClique.add(candidate);
                candidates.remove(candidate);

                // create newCandidates by removing nodes in candidates not
                // connected to candidate node
                for (String newCandidate : candidates) {
                    if (graph.containsEdge(candidate, newCandidate)) {
                        newCandidates.add(newCandidate);
                    }
                }

                // create newAlreadyFound by removing nodes in already_found
                // not connected to candidate node
                for (String newFound : alreadyFound) {
                    if (graph.containsEdge(candidate, newFound)) {
                        newAlreadyFound.add(newFound);
                    }
                }

                // if newCandidates and newAlreadyFound are empty
                if (newCandidates.isEmpty() && newAlreadyFound.isEmpty()) {
                    // potentialClique is maximal clique
                    if (potentialClique.size() >= CLUSTER_MIN_COUNT) {
                        cliques.add(new TreeSet<>(potentialClique));
                    }
                }
                else {
                    // recursive call
                    findCliques(
                            potentialClique,
                            newCandidates,
                            newAlreadyFound);
                }

                // move candidate_node from potentialClique to already_found;
                alreadyFound.add(candidate);
                potentialClique.remove(candidate);
            }
        }
    }

    /**
     * Find the end of the sub-graph.
     *
     * @param candidates   Collection of the clique candidates.
     * @param alreadyFound Collection of already founded cliques.
     * @return True if found, False - otherwise.
     */
    private boolean end(final List<String> candidates, final List<String> alreadyFound) {
        // if a node in alreadyFound is connected to all nodes in candidates
        boolean end = false;
        int edgeCounter;
        for (String found : alreadyFound) {
            edgeCounter = 0;
            for (String candidate : candidates) {
                if (graph.containsEdge(found, candidate)) {
                    edgeCounter++;
                }
            }
            if (edgeCounter == candidates.size()) {
                end = true;
            }
        }
        return end;
    }
}
