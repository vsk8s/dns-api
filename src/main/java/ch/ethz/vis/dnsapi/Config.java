package ch.ethz.vis.dnsapi;

import ch.ethz.vis.dnsapi.exceptions.InitializationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class Config {

    private static final Logger LOG = LogManager.getLogger(Config.class);

    private static final String NETCENTER_USERNAME_KEY = "ch.ethz.vis.dnsapi.netcenter.username";

    private static final String NETCENTER_PASSWORD_KEY = "ch.ethz.vis.dnsapi.netcenter.password";

    private static final String NETCENTER_ISGGROUP_KEY = "ch.ethz.vis.dnsapi.netcenter.isgGroup";

    private static final String KEY_FILE_PATH = "ch.ethz.vis.dnsapi.keyFilePath";

    private static final String CERT_FILE_PATH = "ch.ethz.vis.dnsapi.certFilePath";

    private String username;

    private String password;

    private String isgGroup;

    private String keyFilePath;

    private String certFilePath;

    public Config(Properties p) throws InitializationException {
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

    public String getKeyFilePath() {
        return keyFilePath;
    }

    public String getCertFilePath() {
        return certFilePath;
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
        this.keyFilePath = p.getProperty(KEY_FILE_PATH);
        this.certFilePath = p.getProperty(CERT_FILE_PATH);
    }

    private void checkConfigurationValidity() throws InitializationException {
        checkConfigValue(username, "username");
        checkConfigValue(password, "password");
        checkConfigValue(isgGroup, "isgGroup");
        checkConfigValue(keyFilePath, "keyFilePath");
        checkConfigValue(certFilePath, "certFilePath");
    }

    private void checkConfigValue(Object value, String name) throws InitializationException {
        if (value == null) {
            throw new InitializationException(name + " must be provided");
        }
    }

    private void printDebugInformation() {
        LOG.debug("Username: " + username);
        LOG.debug("Password: " + "*".repeat(password.length()));
        LOG.debug("ISG Group: " + isgGroup);
        LOG.debug("TLS Key: " + keyFilePath);
        LOG.debug("TLS Cert: " + certFilePath);
    }
}
