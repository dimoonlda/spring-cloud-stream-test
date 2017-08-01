package pl.piomin.services.order.service;

import org.springframework.web.context.request.async.DeferredResult;
import pl.piomin.service.common.message.Request;
import pl.piomin.service.common.message.Response;

public interface RequestHandler<T, R> {
    DeferredResult<Response<R>> proceed(Request<T> request);
}
