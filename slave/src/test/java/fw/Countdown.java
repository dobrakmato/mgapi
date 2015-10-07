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

import eu.matejkormuth.bmboot.facades.Container;
import eu.matejkormuth.mgapi.slave.api.Event;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents countdown.
 */
public class Countdown {

    private static final AtomicInteger counter = new AtomicInteger(0);

    /**
     * Ticks in second.
     */
    public static final int TICKS_IN_SECOND = 20;

    /**
     * Length of whole countdown.
     */
    private final long length;
    /**
     * Amount of ticks left.
     */
    private long timeLeft;
    /**
     * Length of one ticks.
     */
    private final long tickLength;

    private final String name;

    // Id of bukkit task.
    private int taskId;

    /**
     * Fired when countdown ends.
     */
    public final Event<Void> TimeUpEvent = new Event<>();

    /**
     * Creates a new countdown object with specified countdown length and with default name.
     *
     * @param time length of countdown
     */
    public Countdown(Time time) {
        this(time, "Countdown-" + counter.incrementAndGet());
    }

    /**
     * Creates a new countdown object with specified countdown length and specified name.
     *
     * @param time length of the countdown
     * @param name name of the countdown
     */
    public Countdown(Time time, String name) {
        this.name = name;
        this.length = time.toLongTicks();

        if (this.length >= TICKS_IN_SECOND) {
            this.tickLength = TICKS_IN_SECOND;
        } else {
            this.tickLength = this.length;
        }

        this.reset();
    }

    /**
     * Resets the countdown to initial time.
     */
    public void reset() {
        this.timeLeft = this.length;
    }

    // Ticks the countdown.
    private void tick() {
        this.timeLeft -= tickLength;

        if (this.timeLeft <= 0) {
            // Stop countdown.
            this.stop();
            // Call event.
            TimeUpEvent.call(null);
        }
    }

    /**
     * Stops the countdown.
     */
    public void stop() {
        Bukkit.getScheduler().cancelTask(this.taskId);
    }

    /**
     * Starts / restarts the countdown.
     */
    public void start() {
        JavaPlugin plugin = Container.get(JavaPlugin.class);
        this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::tick, 0, tickLength);
    }

    @Override
    public String toString() {
        return "Countdown{" +
                "name='" + name + '\'' +
                ", length=" + length +
                ", timeLeft=" + timeLeft +
                ", tickLength=" + tickLength +
                '}';
    }
}
