package it.redhat.bankit;

import org.infinispan.manager.DefaultCacheManager;

public abstract class JDGNode {

    public JDGNode() {
        initCacheManager();
    }

    public abstract void initCacheManager();
    public abstract DefaultCacheManager getManager();

}
