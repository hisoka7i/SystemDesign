package SystemDesign.ConsistentHashing.models;

public class Request {
    private final String id;
    private final String serviceId;
    private final String method;

    public Request(String id, String method, String serviceId) {
        this.id = id;
        this.method = method;
        this.serviceId = serviceId;
    }

    public String getId() {
        return id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getMethod() {
        return method;
    }
}
