package com.shattered;

/**
 * @author JTlr Frost <brradc@gmail.com> 6/13/2019
 */
public class ServerConstants {

    /**
     * Represents the Server Timeout Duration in seconds.
     */
    public static final int SERVER_TIMEOUT = 10;//TODO set 60 for actual stimulation (10 for testing)

    /**
     * Represents the Proxy Starting Port
     */
    public static final int PROXY_DEFAULT_PORT = 18550;

    /**
     * Represents the Central Starting Port
     */
    public static final int CENTRAL_DEFAULT_PORT = 18650;

    /**
     * Represents the Realm Starting Port
     */
    public static final int REALM_DEFAULT_PORT = 18750;

    /**
     * Represents the World Starting Port
     */
    public static final int WORLD_DEFAULT_PORT = 18850;

    /**
     * Represents the Channel Starting Port
     */
    public static final int CHANNEL_DEFAULT_PORT = 23450;


    /**
     * Represents if Live Hosting (Local Host)
     */
    public static boolean LIVE = false;

    /**
     * Represents if Live DB
     */
    public static boolean LIVE_DB = true;

    /**
     * Represents the AWS Central Host
     */
    public static String CENTRAL_HOST = LIVE ? "45.63.68.47" : "0.0.0.0";
}
