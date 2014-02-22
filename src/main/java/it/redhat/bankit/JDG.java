package it.redhat.bankit;

import org.infinispan.Cache;
import org.infinispan.manager.EmbeddedCacheManager;

import java.util.HashSet;
import java.util.Set;

public class JDG {

    public JDG connect(JDGNode node) {

        System.out.println("Using Server Infinispan configuration");
        cacheManager = node.getManager();

        cache = cacheManager.getCache();
        System.out.println("Connected to cacheManager: " + cache.toString());

        return this;
    }

    public TextUI attachUI(TextUI ui) {
        ui.setJdg(this);
        return ui;
    }

    public Set<String> keySet() {
        return valuesFromKeys(cache.keySet());
    }

    public Value get(long id) {
        return cache.get(id);
    }

    public Value put(long id, String value) {
        return cache.put(id, new Value(value));
    }

    public Value modify(long id, String value) {
        Value v = cache.get(id);
        return v.setVal(value);
    }

    public void clear() {
        cache.clear();
    }

    public void shutdown() {
        cacheManager.stop();
    }

    protected Set<String> valuesFromKeys(Set<Long> keys) {
        Set<String> values = new HashSet<String>();
        for (long l : keys) {
            values.add(l + "," + get(l));
        }
        return values;
    }

    protected EmbeddedCacheManager cacheManager;
    protected Cache<Long, Value> cache;

}
