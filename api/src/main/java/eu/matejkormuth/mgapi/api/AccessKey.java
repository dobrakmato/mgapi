/**
 * api - Minigame API and master server.
 * Copyright (c) 2015, Matej Kormuth <http://www.github.com/dobrakmato>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package eu.matejkormuth.mgapi.api;

import java.time.Instant;

/**
 * Private key, that is used to authenticate to access some parts of API.
 */
public final class AccessKey {

    private final String accessKey;
    private final Instant expiresAt;
    private final Permissions permissions;

    /**
     * Creates new instance of AccessKey with specified key and expiration date.
     *
     * @param accessKey   actual access key
     * @param expiresAt   date of expiration, after which is access key no longer valid
     * @param permissions permissions of this access token
     */
    public AccessKey(String accessKey, Instant expiresAt, Permissions permissions) {
        this.accessKey = accessKey;
        this.expiresAt = expiresAt;
        this.permissions = permissions;
    }

    /**
     * Returns textual representation of this access key.
     *
     * @return this access key represented as string
     */
    public String getAccessKey() {
        return accessKey;
    }

    /**
     * Returns date of expiration of this access key.
     *
     * @return this access key expiration date
     */
    public Instant getExpiresAt() {
        return expiresAt;
    }

    /**
     * Returns permissions of this access key.
     *
     * @return permissions this access key has
     */
    public Permissions getPermissions() {
        return permissions;
    }
}
