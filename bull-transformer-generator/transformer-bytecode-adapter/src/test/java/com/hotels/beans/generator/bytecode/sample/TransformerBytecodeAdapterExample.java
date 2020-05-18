/**
 * Copyright (C) 2019-2020 Expedia, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hotels.beans.generator.bytecode.sample;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.hotels.beans.generator.bytecode.TransformerBytecodeAdapter;
import com.hotels.beans.generator.core.Transformer;
import com.hotels.beans.generator.core.TransformerSpec;
import com.hotels.beans.generator.core.mapping.MappingCodeFactory;
import com.hotels.beans.generator.core.sample.javabean.Destination;
import com.hotels.beans.generator.core.sample.javabean.Source;

/**
 * Example of generating and using a Transformer at runtime.
 */
public final class TransformerBytecodeAdapterExample {

    private TransformerBytecodeAdapterExample() {
    }

    private static Destination getExpectedResult() {
        Destination destination = new Destination();
        destination.setABoolean(true);
        destination.setAString("hello");
        return destination;
    }

    /**
     * Entry point for manual testing.
     * Run this to compare the usage of a generated transformer with the above reference result.
     * @param args the args
     */
    public static void main(final String[] args) {
        // create a transformer model
        TransformerSpec spec = new TransformerSpec(MappingCodeFactory.getInstance());
        // create a bytecode adapter for the model
        TransformerBytecodeAdapter bytecode = TransformerBytecodeAdapter.builder()
                .spec(spec)
                .build();
        // generate and compile a new Transformer
        Transformer<Source, Destination> transformer = bytecode.newTransformer(Source.class, Destination.class);

        final int anInt = 42;
        Source source = new Source();
        source.setABoolean(true);
        source.setAnInt(anInt);
        source.setAString("hello");
        // use the generated Transformer
        Destination destination = transformer.transform(source);

        ToStringBuilder.setDefaultStyle(ToStringStyle.SHORT_PREFIX_STYLE);
        System.out.printf("Transformed: %s%n", ToStringBuilder.reflectionToString(destination));
        System.out.printf("Expected: %s%n", ToStringBuilder.reflectionToString(getExpectedResult()));
    }
}
