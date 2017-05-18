/*
 * #%L
 * %%
 * Copyright (C) 2017 BMW Car IT GmbH
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package io.joynr.smrf;

import io.joynr.smrf.tests.TestMessage;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class RoundtripTest {

    @Test
    public void notCompressedNotSignedNotEncrypted() throws SecurityException, EncodingException,
                                                    UnsuppportedVersionException {
        run(false, false, false);
    }

    @Test
    public void compressedNotSignedNotEncrypted() throws SecurityException, EncodingException,
                                                 UnsuppportedVersionException {
        run(true, false, false);
    }

    private void run(boolean shouldBeCompressed, boolean isSigned, boolean isEncrypted) throws SecurityException,
                                                                                       EncodingException,
                                                                                       UnsuppportedVersionException {
        TestMessage expectedMessage = new TestMessage(shouldBeCompressed);
        MessageSerializer serializer = new MessageSerializerImpl();
        expectedMessage.initSerializer(serializer);
        byte[] serializedMessage = serializer.serialize();

        MessageDeserializer deserializer = new MessageDeserializerImpl(serializedMessage);
        TestMessage deserializedMessage = TestMessage.getFromDeserializer(deserializer);

        assertEquals(expectedMessage, deserializedMessage);
        assertEquals(serializedMessage.length, deserializer.getMessageSize());
    }
}