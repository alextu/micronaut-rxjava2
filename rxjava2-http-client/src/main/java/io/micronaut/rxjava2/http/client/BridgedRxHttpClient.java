/*
 * Copyright 2017-2019 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.rxjava2.http.client;

import io.micronaut.core.annotation.Internal;
import io.micronaut.core.io.buffer.ByteBuffer;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.StreamingHttpClient;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;

import java.net.URL;
import java.util.Map;

/**
 * Internal bridge for the {@link HttpClient} and {@link StreamingHttpClient}.
 *
 * @author Sergio del Amo
 * @since 3.0.0
 */
@Internal
class BridgedRxHttpClient implements RxStreamingHttpClient {

    private final StreamingHttpClient streamingHttpClient;

    /**
     * Default constructor.
     * @param streamingHttpClient Streaming HTTP Client
     */
    BridgedRxHttpClient(StreamingHttpClient streamingHttpClient) {
        this.streamingHttpClient = streamingHttpClient;
    }

    @Override
    public BlockingHttpClient toBlocking() {
        return streamingHttpClient.toBlocking();
    }

    @Override
    public <I, O, E> Flowable<HttpResponse<O>> exchange(HttpRequest<I> request, Argument<O> bodyType, Argument<E> errorType) {
        return Flowable.fromPublisher(streamingHttpClient.exchange(request, bodyType, errorType));
    }

    @Override
    public <I, O> Flowable<HttpResponse<O>> exchange(HttpRequest<I> request, Argument<O> bodyType) {
        return Flowable.fromPublisher(streamingHttpClient.exchange(request, bodyType));
    }

    @Override
    public <I, O, E> Flowable<O> retrieve(HttpRequest<I> request, Argument<O> bodyType, Argument<E> errorType) {
        return Flowable.fromPublisher(streamingHttpClient.retrieve(request, bodyType));
    }

    @Override
    public <I> Flowable<HttpResponse<ByteBuffer>> exchange(HttpRequest<I> request) {
        return Flowable.fromPublisher(streamingHttpClient.exchange(request));
    }

    @Override
    public Flowable<HttpResponse<ByteBuffer>> exchange(String uri) {
        return Flowable.fromPublisher(streamingHttpClient.exchange(uri));
    }

    @Override
    public <O> Flowable<HttpResponse<O>> exchange(String uri, Class<O> bodyType) {
        return Flowable.fromPublisher(streamingHttpClient.exchange(uri, bodyType));
    }

    @Override
    public <I, O> Flowable<HttpResponse<O>> exchange(HttpRequest<I> request, Class<O> bodyType) {
        return Flowable.fromPublisher(streamingHttpClient.exchange(request, bodyType));
    }

    @Override
    public <I, O> Flowable<O> retrieve(HttpRequest<I> request, Argument<O> bodyType) {
        return Flowable.fromPublisher(streamingHttpClient.retrieve(request, bodyType));
    }

    @Override
    public <I, O> Flowable<O> retrieve(HttpRequest<I> request, Class<O> bodyType) {
        return Flowable.fromPublisher(streamingHttpClient.retrieve(request, bodyType));
    }

    @Override
    public <I> Flowable<String> retrieve(HttpRequest<I> request) {
        return Flowable.fromPublisher(streamingHttpClient.retrieve(request));
    }

    @Override
    public Flowable<String> retrieve(String uri) {
        return Flowable.fromPublisher(streamingHttpClient.retrieve(uri));
    }

    @Override
    public boolean isRunning() {
        return streamingHttpClient.isRunning();
    }

    @Override
    public <I> Flowable<ByteBuffer<?>> dataStream(HttpRequest<I> request) {
        return Flowable.fromPublisher(streamingHttpClient.dataStream(request));
    }

    @Override
    public <I> Flowable<HttpResponse<ByteBuffer<?>>> exchangeStream(HttpRequest<I> request) {
        return Flowable.fromPublisher(streamingHttpClient.exchangeStream(request));
    }

    @Override
    public <I> Publisher<Map<String, Object>> jsonStream(HttpRequest<I> request) {
        return Flowable.fromPublisher(streamingHttpClient.jsonStream(request));
    }

    @Override
    public <I, O> Flowable<O> jsonStream(HttpRequest<I> request, Argument<O> type) {
        return Flowable.fromPublisher(streamingHttpClient.jsonStream(request, type));
    }

    @Override
    public <I, O> Flowable<O> jsonStream(HttpRequest<I> request, Class<O> type) {
        return Flowable.fromPublisher(streamingHttpClient.jsonStream(request, type));
    }


}
