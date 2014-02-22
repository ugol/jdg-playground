package it.redhat.bankit;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;

import java.util.logging.Logger;

public class Node2 extends JDGNode {

    private static final long ENTRY_LIFESPAN = 60 * 1000; // 60 seconds
    private Logger log = Logger.getLogger(this.getClass().getName());

    private DefaultCacheManager manager;

    public void initCacheManager() {
        if (manager == null) {
            //log.info("\n\n DefaultCacheManager does not exist - constructing a new one\n\n");

            GlobalConfiguration glob = new GlobalConfigurationBuilder().clusteredDefault() // Builds a default clustered
                    // configuration
                    .transport().addProperty("configurationFile", "jgroups-udp.xml") // provide a specific JGroups configuration
                    .globalJmxStatistics().allowDuplicateDomains(true).enable() // This method enables the jmx statistics of
                            // the global configuration and allows for duplicate JMX domains
                    .build(); // Builds the GlobalConfiguration object
            Configuration loc = new ConfigurationBuilder().jmxStatistics().enable() // Enable JMX statistics
                    .clustering().cacheMode(CacheMode.DIST_SYNC) // Set Cache mode to DISTRIBUTED with SYNCHRONOUS replication
                    .hash().numOwners(1) // Keeps two copies of each key/value pair
                    //.expiration().lifespan(ENTRY_LIFESPAN) // Set expiration - cacheManager entries expire after some time (given by
                            // the lifespan parameter) and are removed from the cacheManager (cluster-wide).
                    .build();
            manager = new DefaultCacheManager(glob, loc, true);
        }
    }

    public DefaultCacheManager getManager() {
        return manager;
    }

    public String toString() {
        return "Node 2";
    }


    public static void main(String[] args) {

        Node2 node = new Node2();

                Cache<Object, Object> cache = node.getManager().getCache();
        System.out.println(cache.getCacheConfiguration().toString());
        cache.put("1", "ugol");
        System.out.println("Cache size: " + cache.size());
        System.out.println("" + cache.containsKey("1"));
        cache.remove("1");
        System.out.println("Cache size: " + cache.size());


    }

}
