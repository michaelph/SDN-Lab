package br.usp.larc;

import br.usp.larc.mininet.api.MininetApiManager;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ImmutableSet;
import org.onlab.osgi.ServiceDirectory;
import org.onlab.packet.MacAddress;
import org.onosproject.net.HostId;
import org.onosproject.net.host.HostService;
import org.onosproject.ui.RequestHandler;
import org.onosproject.ui.UiConnection;
import org.onosproject.ui.UiMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * Created by michael on 10/3/16.
 */
public class SDNLabMessageHandler extends UiMessageHandler {

    private static final String ADD_DEVICE = "addDevice";
    private static final String ADD_HOST = "addHost";
    private static final String ADD_LINK = "addLink";
    private static final String REMOVE_LINK = "removeLink";
    private static final String REMOVE_HOST = "removeHost";
    private final Logger log = LoggerFactory.getLogger(getClass());
    private MininetApiManager mininetApiManager;
    private Integer deviceCount;
    private Integer hostCount;
    private HostService hostService;

    @Override
    public void init(UiConnection connection, ServiceDirectory directory) {
        super.init(connection, directory);

        mininetApiManager = new MininetApiManager();
        hostService = directory.get(HostService.class);
        deviceCount = 1;
        hostCount = 1;
    }

    @Override
    protected Collection<RequestHandler> createRequestHandlers() {
        return ImmutableSet.of(
                new SDNLabMessageHandler.AddLinkHandler(),
                new SDNLabMessageHandler.AddDeviceHandler(),
                new SDNLabMessageHandler.AddHostHandler(),
                new SDNLabMessageHandler.RemoveLinkHandler(),
                new SDNLabMessageHandler.RemoveServiceHandler()
        );
    }

    private final class AddLinkHandler extends RequestHandler {
        public AddLinkHandler() {
            super(ADD_LINK);
        }

        @Override
        public void process(ObjectNode payload) {
            String deviceId1 = string(payload, "deviceId1");
            String deviceId2 = string(payload, "deviceId2");
            log.info("Devices clicked" + deviceId1 + " : " + deviceId2);
            log.info("Response: " + mininetApiManager.addLink(deviceId1, deviceId2));

        }
    }

    private final class RemoveLinkHandler extends RequestHandler {
        public RemoveLinkHandler() {
            super(REMOVE_LINK);
        }

        @Override
        public void process(ObjectNode payload) {
            String deviceId1 = string(payload, "deviceId1");
            String deviceId2 = string(payload, "deviceId2");
            if (deviceId1.contains("/")) {
                deviceId1 = deviceId1.replace('/', '_');
            } else if (deviceId2.contains("/")) {
                deviceId2 = deviceId2.replace('/', '_');
            }

            log.info("Devices clicked: " + deviceId1 + " : " + deviceId2);
            log.info("Response: " + mininetApiManager.removeLink(deviceId1, deviceId2));

        }
    }

    private final class AddDeviceHandler extends RequestHandler {
        public AddDeviceHandler() {
            super(ADD_DEVICE);
        }

        @Override
        public void process(ObjectNode payload) {
            log.info("Add device");
            log.info("Response: " + mininetApiManager.addDevice("s" + deviceCount));
            deviceCount++;

        }
    }

    private final class RemoveServiceHandler extends RequestHandler {
        public RemoveServiceHandler() {
            super(REMOVE_HOST);
        }

        @Override
        public void process(ObjectNode payload) {
            String hostIpAddress = string(payload, "hostIpAddress");
            log.info("Remove host with ip address: " + hostIpAddress);
            log.info("Response: " + mininetApiManager.removeService(hostIpAddress));
            deviceCount++;

        }
    }

    private final class AddHostHandler extends RequestHandler {
        public AddHostHandler() {
            super(ADD_HOST);
        }

        @Override
        public void process(ObjectNode payload) {
            String hostName = string(payload, "containerName");
            String deviceId = string(payload, "deviceId");
            log.info("Add host " + hostName + " at " + deviceId);
            String hostMac = mininetApiManager.addHostOnDevice(hostName, deviceId);
            log.info("Host Mac:" + hostMac);
            HostId hostId = HostId.hostId(MacAddress.valueOf(hostMac));
            if (hostId != null) {
                hostService.getHost(hostId);
            } else {
                log.warn("Host id not found!");
            }

            hostCount++;

        }
    }
}