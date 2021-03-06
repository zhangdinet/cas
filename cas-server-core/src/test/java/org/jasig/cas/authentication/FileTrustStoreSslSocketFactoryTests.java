/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jasig.cas.authentication;

import org.jasig.cas.util.SimpleHttpClient;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Tests for the <code>FileTrustStoreSslSocketFactory</code> class, checking for self-signed
 * and missing certificates via a local truststore.
 * @author Misagh Moayyed
 * @since 4.1
 */
public class FileTrustStoreSslSocketFactoryTests {

    @Test
     public void testTrustStoreLoadingSuccessfullyWithCertAvailable() throws Exception {
        final ClassPathResource resource = new ClassPathResource("truststore.jks");
        final FileTrustStoreSslSocketFactory factory = new FileTrustStoreSslSocketFactory(resource.getFile(), "changeit");
        final SimpleHttpClient client = new SimpleHttpClient(factory);
        assertTrue(client.isValidEndPoint("https://www.cacert.org"));
    }

    @Test
    public void testTrustStoreLoadingSuccessfullyWithCertAvailable2() throws Exception {
        final ClassPathResource resource = new ClassPathResource("truststore.jks");
        final FileTrustStoreSslSocketFactory factory = new FileTrustStoreSslSocketFactory(resource.getFile(), "changeit");
        final SimpleHttpClient client = new SimpleHttpClient(factory);
        assertTrue(client.isValidEndPoint("https://test.scaldingspoon.org/idp/shibboleth"));
    }


    @Test(expected = RuntimeException.class)
     public void testTrustStoreNotFound() throws Exception {
        new FileTrustStoreSslSocketFactory(new File("test.jks"), "changeit");
    }

    @Test(expected = RuntimeException.class)
    public void testTrustStoreBadPassword() throws Exception {
        final ClassPathResource resource = new ClassPathResource("truststore.jks");
        new FileTrustStoreSslSocketFactory(resource.getFile(), "invalid");
    }

    @Test
    public void testTrustStoreLoadingSuccessfullyForValidEndpointWithNoCert() throws Exception {
        final ClassPathResource resource = new ClassPathResource("truststore.jks");
        final FileTrustStoreSslSocketFactory factory = new FileTrustStoreSslSocketFactory(resource.getFile(), "changeit");
        final SimpleHttpClient client = new SimpleHttpClient(factory);
        assertTrue(client.isValidEndPoint("https://www.google.com"));
    }
    @Test
    public void testTrustStoreLoadingSuccessfullyWihInsecureEndpoint() throws Exception {
        final ClassPathResource resource = new ClassPathResource("truststore.jks");
        final FileTrustStoreSslSocketFactory factory = new FileTrustStoreSslSocketFactory(resource.getFile(), "changeit");
        final SimpleHttpClient client = new SimpleHttpClient(factory);
        assertTrue(client.isValidEndPoint("http://wikipedia.org"));
    }
}
