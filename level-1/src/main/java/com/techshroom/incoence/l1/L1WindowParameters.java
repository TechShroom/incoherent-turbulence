package com.techshroom.incoence.l1;

import static com.google.common.base.Preconditions.checkState;

import java.util.Optional;

import org.eclipse.collections.api.map.primitive.ImmutableIntIntMap;
import org.eclipse.collections.api.map.primitive.IntIntMap;
import org.eclipse.collections.api.map.primitive.MutableIntIntMap;
import org.eclipse.collections.impl.map.immutable.primitive.ImmutableIntIntMapFactoryImpl;
import org.eclipse.collections.impl.map.mutable.primitive.MutableIntIntMapFactoryImpl;
import org.lwjgl.system.MemoryUtil;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class L1WindowParameters {

    public static Builder builder() {
        return new AutoValue_L1WindowParameters.Builder()
                .monitor(MemoryUtil.NULL)
                .share(MemoryUtil.NULL)
                .windowHints(ImmutableIntIntMapFactoryImpl.INSTANCE.empty());
    }

    @AutoValue.Builder
    public abstract static class Builder {

        private MutableIntIntMap builder;

        public abstract Builder width(int width);

        public abstract Builder height(int height);

        public abstract Builder title(String title);

        public abstract Builder monitor(long monitor);
        
        public final Builder monitor(L1Monitor monitor) {
            return monitor(monitor.address());
        }

        public abstract Builder share(long share);

        abstract Builder windowHints(ImmutableIntIntMap windowHints);

        abstract Optional<ImmutableIntIntMap> windowHints();

        public final Builder windowHints(IntIntMap windowHints) {
            checkState(builder == null, "cannot set windowHints with builder");
            return windowHints(ImmutableIntIntMapFactoryImpl.INSTANCE.ofAll(windowHints));
        }

        public final MutableIntIntMap windowHintsBuilder() {
            if (builder == null) {
                builder = windowHints()
                        .map(MutableIntIntMapFactoryImpl.INSTANCE::ofAll)
                        .orElse(MutableIntIntMapFactoryImpl.INSTANCE.empty());
            }
            return builder;
        }

        public final Builder windowHint(int hint, int value) {
            windowHintsBuilder().put(hint, value);
            return this;
        }

        abstract L1WindowParameters autoBuild();

        public final L1WindowParameters build() {
            if (builder != null) {
                windowHints(ImmutableIntIntMapFactoryImpl.INSTANCE.ofAll(builder));
            }
            return autoBuild();
        }
    }

    L1WindowParameters() {
    }

    public abstract int width();

    public abstract int height();

    public abstract String title();

    public abstract long monitor();

    public abstract long share();

    public abstract ImmutableIntIntMap windowHints();

}
