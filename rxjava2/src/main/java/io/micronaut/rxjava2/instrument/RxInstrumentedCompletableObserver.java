/*
 * Copyright 2017-2021 original authors
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
package io.micronaut.rxjava2.instrument;

import io.micronaut.core.annotation.Internal;
import io.micronaut.scheduling.instrument.Instrumentation;
import io.micronaut.scheduling.instrument.InvocationInstrumenter;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;

/**
 * Inspired by code in Brave. Provides general instrumentation abstraction for RxJava2.
 * See https://github.com/openzipkin/brave/tree/master/context/rxjava2/src/main/java/brave/context/rxjava2/internal.
 *
 * @author graemerocher
 * @since 1.1
 */
@Internal
final class RxInstrumentedCompletableObserver implements CompletableObserver, RxInstrumentedComponent {
    private final CompletableObserver source;
    private final InvocationInstrumenter instrumenter;

    /**
     * Default constructor.
     *
     * @param source              downstream observer
     * @param instrumenterFactory The instrumenter factory
     */
    RxInstrumentedCompletableObserver(CompletableObserver source, RxInstrumenterFactory instrumenterFactory) {
        this.source = source;
        this.instrumenter = instrumenterFactory.create();
    }

    @Override
    public void onSubscribe(Disposable d) {
        try (Instrumentation ignored = instrumenter.newInstrumentation()) {
            source.onSubscribe(d);
        }
    }

    @Override
    public void onError(Throwable t) {
        try (Instrumentation ignored = instrumenter.newInstrumentation()) {
            source.onError(t);
        }
    }

    @Override
    public void onComplete() {
        try (Instrumentation ignored = instrumenter.newInstrumentation()) {
            source.onComplete();
        }
    }
}
