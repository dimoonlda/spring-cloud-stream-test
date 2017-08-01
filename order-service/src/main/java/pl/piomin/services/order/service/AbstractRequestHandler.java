package pl.piomin.services.order.service;

import org.springframework.web.context.request.async.DeferredResult;
import pl.piomin.service.common.message.Order;
import pl.piomin.service.common.message.Request;
import pl.piomin.service.common.message.Response;
import pl.piomin.service.common.utils.ServiceInstanceUtil;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractRequestHandler<T, R> implements RequestHandler<T, R> {

    public static final String serverId = ServiceInstanceUtil.generateInstanceId("rest-front");
    protected Map<String, DeferredResult<Response<R>>> resultMap = new ConcurrentHashMap<>();

    @Override
    public DeferredResult<Response<R>> proceed(Request<T> request) {
        request.setServerId(serverId);
        request.setUuid(UUID.randomUUID().toString());

        final DeferredResult<Response<R>> responseDeferredResult = doHandle(request);
        resultMap.put(request.getUuid(), responseDeferredResult);
        return responseDeferredResult;
    }

    public abstract DeferredResult<Response<R>> doHandle(Request<T> request);

    protected void sendResponse(Response<R> response) {
        final DeferredResult<Response<R>> deferredResult = resultMap.get(response.getUuid());
        if (null != deferredResult) {
            deferredResult.setResult(response);
        }
    }
}
