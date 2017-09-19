/*
 * Copyright 2016-present Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.usp.larc;

import br.usp.larc.mininet.api.MininetConf;
import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.onosproject.cli.AbstractShellCommand;

/**
 * Sample Apache Karaf CLI command
 */
@Command(scope = "onos", name = "sdn-lab-set-port",
        description = "SDN-Lab configuration")
public class SDNLabSetPortCommand extends AbstractShellCommand {

    @Argument(name = "port", description = "Port of Mininet Server",
            required = true, multiValued = false)
    String port = null;

    @Override
    protected void execute() {
        MininetConf.getInstance().setPort(port);
        print("Port set to: %s", MininetConf.getInstance().getPort());
    }
}