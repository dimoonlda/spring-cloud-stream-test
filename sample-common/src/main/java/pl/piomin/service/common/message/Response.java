package pl.piomin.service.common.message;

public class Response<T> {
    private String uuid;
    private String resultCode;
    private String resultMessage;
    private String serverId;
    private T data;

    public String getUuid() {
        return uuid;
    }

    public Response<T> setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getResultCode() {
        return resultCode;
    }

    public Response<T> setResultCode(String resultCode) {
        this.resultCode = resultCode;
        return this;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public Response<T> setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
        return this;
    }

    public T getData() {
        return data;
    }

    public Response<T> setData(T data) {
        this.data = data;
        return this;
    }

    public String getServerId() {
        return serverId;
    }

    public Response<T> setServerId(String serverId) {
        this.serverId = serverId;
        return this;
    }

    @Override
    public String toString() {
        return "Response{" +
                "uuid='" + uuid + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", resultMessage='" + resultMessage + '\'' +
                ", serverId='" + serverId + '\'' +
                ", data=" + data +
                '}';
    }
}
