package SystemDesign.ConsistentHashing.algorithms;

import SystemDesign.ConsistentHashing.models.Node;
import SystemDesign.ConsistentHashing.models.Request;

public interface Router {
    void addNode(Node node);
    void removeNode(Node node);
    Node getAssignedNode(Request request);
}
