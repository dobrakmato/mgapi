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
package eu.matejkormuth.mgapi.slave.modules.communicaton;

import eu.matejkormuth.bmboot.Dependency;
import eu.matejkormuth.bmboot.internal.Module;
import eu.matejkormuth.mgapi.slave.api.MasterServer;
import eu.matejkormuth.mgapi.slave.api.SlaveServer;
import eu.matejkormuth.mgapi.slave.modules.configuration.ConfigurationsModule;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;

public class CommunicationModule extends Module {

    @Dependency
    private ConfigurationsModule configurationsModule;

    private Configuration masterConfiguration;
    private Configuration slaveConfiguration;

    private MasterServer masterServer;
    private SlaveServer slaveServer;
    private Communicator communicator;

    @Override
    public void onEnable() {
        masterConfiguration = configurationsModule.loadOrCreate("master", new YamlConfiguration());
        slaveConfiguration = configurationsModule.loadOrCreate("slave", new YamlConfiguration());

        // Create master server object.
        String ip = masterConfiguration.getString("ip", "127.0.0.1");
        int port = masterConfiguration.getInt("port", 80);
        String accessKey = masterConfiguration.getString("accessKey", "not_provided");

        this.masterServer = new MasterServer(ip, port, accessKey);

        // Create slave client object.
        String uuid = slaveConfiguration.getString("uuid", "not_provided");

        this.slaveServer = new SlaveServer(uuid);

        // Create comunicator.
        this.communicator = new Communicator(masterServer, slaveServer);
    }

    @Override
    public void onDisable() {
    }

    public Communicator getCommunicator() {
        return communicator;
    }

    public MasterServer getMasterServer() {
        return masterServer;
    }

    public SlaveServer getSlaveServer() {
        return slaveServer;
    }
}
