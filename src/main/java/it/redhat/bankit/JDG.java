/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.redhat.bankit;

import org.infinispan.Cache;
import org.infinispan.manager.EmbeddedCacheManager;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class JDG {

    public JDG connect(JDGNode node) {

        cacheManager = node.getManager();
        cache = cacheManager.getCache();
        log.info("Connected to cacheManager: " + cache.toString());
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

    public String info() {
        StringBuilder info = new StringBuilder();
        info.append("Status: ").append(cacheManager.getStatus()).append("\n");
        info.append("Address: ").append(cacheManager.getAddress()).append("\n");
        info.append("Cluster Name: ").append(cacheManager.getClusterName()).append("\n");
        info.append("Is Coordinator: ").append(cacheManager.isCoordinator()).append("\n");
        info.append("Coordinator address: ").append(cacheManager.getCoordinator()).append("\n");
        return info.toString();
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

    private Logger log = Logger.getLogger(this.getClass().getName());

}
