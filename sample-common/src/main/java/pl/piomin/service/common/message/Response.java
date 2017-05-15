package pl.piomin.service.common.message;

public class Response<T> {
    private String uuid;
    private String resultCode;
    private String resultMessage;
    private T data;

    public String getUuid() {
        return uuid;
    }

    public Response setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getResultCode() {
        return resultCode;
    }

    public Response setResultCode(String resultCode) {
        this.resultCode = resultCode;
        return this;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public Response setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
        return this;
    }

    public T getData() {
        return data;
    }

    public Response setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "Response{" +
                "uuid='" + uuid + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", resultMessage='" + resultMessage + '\'' +
                ", data=" + data +
                '}';
    }
}
