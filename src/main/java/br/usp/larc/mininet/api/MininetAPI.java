package br.usp.larc.mininet.api;

/**
 * Created by michael on 10/3/16.
 */
public interface MininetAPI {
    /**
     * @param hostName
     * @param deviceId
     */
    String addHostOnDevice(String hostName, String deviceId);

    /**
     * @param deviceId
     */
    String addDevice(String deviceId);

    /**
     * @param deviceId1
     * @param deviceId2
     */
    String addLink(String deviceId1, String deviceId2);

    /**
     * @param deviceId1
     * @param deviceId2
     */
    String removeLink(String deviceId1, String deviceId2);

    /**
     * @param serviceIpAddress
     */
    String removeService(String serviceIpAddress);
}