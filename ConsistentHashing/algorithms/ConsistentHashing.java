package SystemDesign.ConsistentHashing.algorithms;

import SystemDesign.ConsistentHashing.models.Node;
import SystemDesign.ConsistentHashing.models.Request;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

public class ConsistentHashing implements Router {

    //stores each node and list of hashes assigned to it
    //this can help to ensure that if a node is removed then, its key are removed as well
    private final Map<Node, List<Long>> nodePositions;

    //a sorted map to associate hash points to node
    //this is thread safe and effective look up
    private final ConcurrentSkipListMap<Long, Node> nodeMappings;

    //A function to computes hash values to string
    private final Function<String, Long> hashFunction;

    //determines how many virtual points are created for each physical node
    private final int pointMultiplier;

    public ConsistentHashing(Function<String, Long> hashFunction, int pointMultiplier) {
        if (pointMultiplier == 0) {
            return;
        }

        this.pointMultiplier = pointMultiplier;
        this.hashFunction = hashFunction;
        this.nodePositions = new ConcurrentHashMap<>();
        this.nodeMappings = new ConcurrentSkipListMap<>();
    }

    @Override
    public void addNode(Node node) {
        //copy no write Arraylist, for thread safe operations
        nodePositions.put(node, new CopyOnWriteArrayList<>());

        for (int i = 0; i < pointMultiplier; i++) {
            //for creating multiple virtual point for the node.
            for (int j = 0; j < node.getWeight(); j++) {
                //additionally, loops over the node's weight, allowing more points to assigned with higher weights
                final var point = hashFunction.apply((i * pointMultiplier + j) + node.getId());
                nodePositions.get(node).add(point);
                nodeMappings.put(point, node);

            }
        }
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'addNode'");
    }

    @Override
    public void removeNode(Node node) {
        for (final Long point : nodePositions.remove(node)) {
            nodeMappings.remove(point);
        }
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'removeNode'");
    }

    @Override
    public Node getAssignedNode(Request request) {
        //computes hash using the id
        final var key = hashFunction.apply(request.getId());
        
        //look for the first entry in sorted nodeMapping that has a greater hash point or equal to
        final var entry = nodeMappings.higherEntry(key);

        if (entry == null) {
            return nodeMappings.firstEntry().getValue();
        } else {
            return entry.getValue();
        }
        
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'getAssignedNode'");
    }

}
