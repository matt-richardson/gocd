/*************************GO-LICENSE-START*********************************
 * Copyright 2014 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *************************GO-LICENSE-END***********************************/

package com.thoughtworks.go.plugin.access.notification;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class NotificationPluginRegistry {
    private final Map<String, Set<String>> notificationNameToPluginsInterestedMap = new HashMap<String, Set<String>>();

    public void registerPluginInterests(String pluginId, List<String> notificationNames) {
        if (notificationNames != null && !notificationNames.isEmpty()) {
            for (String notificationName : notificationNames) {
                addToMap(notificationName, pluginId);
            }
        }
    }

    private void addToMap(String notificationName, String pluginId) {
        if (notificationNameToPluginsInterestedMap.get(notificationName) == null) {
            notificationNameToPluginsInterestedMap.put(notificationName, new HashSet<String>());
        }
        notificationNameToPluginsInterestedMap.get(notificationName).add(pluginId);
    }

    public void removePluginInterests(String pluginId) {
        for (String key : notificationNameToPluginsInterestedMap.keySet()) {
            if (notificationNameToPluginsInterestedMap.get(key).contains(pluginId)) {
                notificationNameToPluginsInterestedMap.get(key).remove(pluginId);
            }
        }
    }

    public Set<String> getPluginsInterestedIn(String notificationName) {
        Set<String> plugins = notificationNameToPluginsInterestedMap.get(notificationName);
        return plugins == null ? new HashSet<String>() : plugins;
    }

    public Set<String> getPluginInterests(String pluginId) {
        Set<String> pluginInterests = new HashSet<String>();
        for (String key : notificationNameToPluginsInterestedMap.keySet()) {
            if (notificationNameToPluginsInterestedMap.get(key).contains(pluginId)) {
                pluginInterests.add(key);
            }
        }
        return pluginInterests;
    }

    @Deprecated
    public void clear() {
        notificationNameToPluginsInterestedMap.clear();
    }
}
