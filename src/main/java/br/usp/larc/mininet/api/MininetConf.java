package br.usp.larc.mininet.api;

/**
 * Created by michael on 10/10/16.
 */
public final class MininetConf {
    private static MininetConf mininetConf;
    private String port;
    private String ipAddress;
    private String addHostUrl;
    private String addSwitchUrl;
    private String addLinkUrl;
    private String removeLinkUrl;
    private String removeServiceUrl;

    public MininetConf(String ipAddress, String port, String addHostUrl, String addSwitchUrl, String addLinkUrl,
                       String removeLinkUrl, String removeServiceUrl) {
        this.port = port;
        this.ipAddress = ipAddress;
        this.addHostUrl = addHostUrl;
        this.addSwitchUrl = addSwitchUrl;
        this.addLinkUrl = addLinkUrl;
        this.removeLinkUrl = removeLinkUrl;
        this.removeServiceUrl = removeServiceUrl;
    }

    public static MininetConf getMininetConf() {
        return mininetConf;
    }

    public static void setMininetConf(MininetConf mininetConf) {
        MininetConf.mininetConf = mininetConf;
    }

    public static MininetConf getInstance() {
        if (mininetConf == null) {
            mininetConf = new MininetConf("172.20.5.44", "8090", "/cli/host/add/",
                    "/cli/switch/add/",
                    "/cli/link/add/",
                    "/cli/link/rm/", "/cli/host/rm/");
            return mininetConf;
        } else {
            return mininetConf;
        }
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getAddHostUrl() {
        return addHostUrl;
    }

    public void setAddHostUrl(String addHostUrl) {
        this.addHostUrl = addHostUrl;
    }

    public String getAddSwitchUrl() {
        return addSwitchUrl;
    }

    public void setAddSwitchUrl(String addSwitchUrl) {
        this.addSwitchUrl = addSwitchUrl;
    }

    public String getAddLinkUrl() {
        return addLinkUrl;
    }

    public void setAddLinkUrl(String addLinkUrl) {
        this.addLinkUrl = addLinkUrl;
    }

    public String getRemoveLinkUrl() {
        return removeLinkUrl;
    }

    public void setRemoveLinkUrl(String removeLinkUrl) {
        this.removeLinkUrl = removeLinkUrl;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getRemoveServiceUrl() {
        return removeServiceUrl;
    }

    public void setRemoveServiceUrl(String removeServiceUrl) {
        this.removeServiceUrl = removeServiceUrl;
    }
}