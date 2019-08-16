package ch.ethz.vis.dnsapi;

import ch.ethz.vis.dnsapi.exceptions.InitializationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class NetcenterConfig {
    private static final Logger LOGGER = LogManager.getLogger(NetcenterConfig.class);

    private static final String NETCENTER_USERNAME_KEY = "ch.ethz.vis.dnsapi.netcenter.username";
    private static final String NETCENTER_PASSWORD_KEY = "ch.ethz.vis.dnsapi.netcenter.password";
    private static final String NETCENTER_ISGGROUP_KEY = "ch.ethz.vis.dnsapi.netcenter.isgGroup";

    private String username;
    private String password;
    private String isgGroup;

    public NetcenterConfig(Properties p) throws InitializationException {
        readProperties(p);
    }

    private void readProperties(Properties p) throws InitializationException {
        this.username = p.getProperty(NETCENTER_USERNAME_KEY);
        this.password = p.getProperty(NETCENTER_PASSWORD_KEY);
        this.isgGroup = p.getProperty(NETCENTER_ISGGROUP_KEY);

        if (this.username == null || this.password == null || this.isgGroup == null) {
            LOGGER.error("Username, password and isgGroup must be provided!");
            throw new InitializationException("Username, password or isgGroup not set");
        }

        LOGGER.debug("Username: " + username);
        LOGGER.debug("Password: " + "*".repeat(password.length()));
        LOGGER.debug("ISG Group: " + isgGroup);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getIsgGroup() {
        return isgGroup;
    }
}
