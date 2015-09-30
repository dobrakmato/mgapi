/**
 * mgslave - MGAPI - Slave
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
package fw;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TimeTest {

    @Test
    public void testOfMinutes() throws Exception {
        assertEquals(Time.ofMinutes(1).toTicks(), 20 * 60 * 1);
    }

    @Test
    public void testOfTicks() throws Exception {
        assertEquals(Time.ofTicks(85).toTicks(), 85);
    }

    @Test
    public void testOfSeconds() throws Exception {
        assertEquals(Time.ofSeconds(60).toTicks(), 20 * 60);
    }

    @Test
    public void testToMiliseconds() throws Exception {
        assertEquals(Time.ofSeconds(20).toMiliseconds(), 20L * 1000);
    }

    @Test
    public void testToSeconds() throws Exception {
        assertEquals(Time.ofMinutes(2).toSeconds(), 60L * 2);
    }

    @Test
    public void testToTicks() throws Exception {
        assertEquals(Time.ofTicks(24).toTicks(), 24);
    }

    @Test
    public void testToLongTicks() throws Exception {
        assertEquals(Time.ofTicks(50).toTicks(), 50L);
    }
}