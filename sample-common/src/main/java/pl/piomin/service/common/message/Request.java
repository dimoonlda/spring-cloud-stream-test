package pl.piomin.service.common.message;

public class Request<T> {
    private String uuid;
    private String serverId;
    private T data;

    public String getUuid() {
        return uuid;
    }

    public Request<T> setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public T getData() {
        return data;
    }

    public Request<T> setData(T data) {
        this.data = data;
        return this;
    }

    public String getServerId() {
        return serverId;
    }

    public Request<T> setServerId(String serverId) {
        this.serverId = serverId;
        return this;
    }

    @Override
    public String toString() {
        return "Request{" +
                "uuid='" + uuid + '\'' +
                ", serverId='" + serverId + '\'' +
                ", data=" + data +
                '}';
    }
}
