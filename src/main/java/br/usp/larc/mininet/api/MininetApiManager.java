package br.usp.larc.mininet.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

/**
 * Created by michael on 10/3/16.
 */

public class MininetApiManager implements MininetAPI {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private Client client;


    public MininetApiManager() {
        this.client = ClientBuilder.newClient();
        MininetConf.getInstance();
    }

    @Override
    public String addHostOnDevice(String hostName, String deviceId) {
        Response response = client.target(getAddHostResource() + hostName + "/" + deviceId)
                .request().get();
        return response.readEntity(String.class).toString();
    }

    @Override
    public String addDevice(String deviceName) {
        Response response = client.target(getAddSwitchResource() + deviceName).request().get();
        return response.getEntity().toString();
    }

    @Override
    public String addLink(String device1, String device2) {
        Response response = client.target(getAddLinkResource() + device1 + "/" + device2)
                .request().get();
        return response.getEntity().toString();
    }

    @Override
    public String removeLink(String device1, String device2) {
        Response response = client.target(getRemoveLinkResource() + device1 + "/" + device2)
                .request().get();
        return response.getEntity().toString();
    }

    @Override
    public String removeService(String serviceAddress) {
        Response response = client.target(getRemoveServiceResource() + serviceAddress)
                .request().get();
        return response.getEntity().toString();
    }

    private String getAddHostResource() {
        return "http://" + getIpAddress() + ":" + getPort() + getAddHostUrl();
    }

    private String getAddLinkResource() {
        return "http://" + getIpAddress() + ":" + getPort() + getAddLinkUrl();
    }

    private String getAddSwitchResource() {
        return "http://" + getIpAddress() + ":" + getPort() + getAddSwitchUrl();
    }

    private String getRemoveLinkResource() {
        return "http://" + getIpAddress() + ":" + getPort() + getRemoveLinkUrl();
    }

    private String getRemoveServiceResource() {
        return "http://" + getIpAddress() + ":" + getPort() + getRemoveServiceUrl();
    }


    private String getIpAddress() {
        return MininetConf.getInstance().getIpAddress();
    }

    private String getPort() {
        return MininetConf.getInstance().getPort();
    }

    private String getAddHostUrl() {
        return MininetConf.getInstance().getAddHostUrl();
    }

    private String getAddLinkUrl() {
        return MininetConf.getInstance().getAddLinkUrl();
    }

    private String getAddSwitchUrl() {
        return MininetConf.getInstance().getAddSwitchUrl();
    }

    private String getRemoveLinkUrl() {
        return MininetConf.getInstance().getRemoveLinkUrl();
    }

    private String getRemoveServiceUrl() {
        return MininetConf.getInstance().getRemoveServiceUrl();
    }
}