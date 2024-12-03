package SystemDesign.ConsistentHashing.models;

public class Node {
    private final String id;
    private final int weight;
    private final String ipAddress;

    public String getId() {
        return id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getWeight() {
        return weight;
    }

    public Node(String id, int weight, String ipAddress) {
        this.id = id;
        this.weight = weight;
        this.ipAddress = ipAddress;
    }
}
