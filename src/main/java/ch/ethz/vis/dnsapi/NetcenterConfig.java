package ch.ethz.vis.dnsapi;

import ch.ethz.vis.dnsapi.exceptions.InitializationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class NetcenterConfig {

    private static final Logger LOG = LogManager.getLogger(NetcenterConfig.class);

    private static final String NETCENTER_USERNAME_KEY = "ch.ethz.vis.dnsapi.netcenter.username";

    private static final String NETCENTER_PASSWORD_KEY = "ch.ethz.vis.dnsapi.netcenter.password";

    private static final String NETCENTER_ISGGROUP_KEY = "ch.ethz.vis.dnsapi.netcenter.isgGroup";

    private String username;

    private String password;

    private String isgGroup;

    public NetcenterConfig(Properties p) throws InitializationException {
        readProperties(p);
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

    private void readProperties(Properties p) throws InitializationException {
        copyPropertiesToFields(p);
        checkConfigurationValidity();
        printDebugInformation();
    }

    private void copyPropertiesToFields(Properties p) {
        this.username = p.getProperty(NETCENTER_USERNAME_KEY);
        this.password = p.getProperty(NETCENTER_PASSWORD_KEY);
        this.isgGroup = p.getProperty(NETCENTER_ISGGROUP_KEY);
    }

    private void checkConfigurationValidity() throws InitializationException {
        if (username == null) {
            throw new InitializationException("username must be provided");
        }
        if (password == null) {
            throw new InitializationException("password must be provided");
        }
        if (isgGroup == null) {
            throw new InitializationException("isgGroup must be provided");
        }
    }

    private void printDebugInformation() {
        LOG.debug("Username: " + username);
        LOG.debug("Password: " + "*".repeat(password.length()));
        LOG.debug("ISG Group: " + isgGroup);
    }
}
