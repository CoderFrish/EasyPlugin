package extension;

import com.esotericsoftware.yamlbeans.YamlWriter;
import exception.PluginBuildException;
import extension.buildSettings.ServerType;
import org.gradle.api.Action;
import org.gradle.api.Project;
import org.jetbrains.annotations.ApiStatus;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class BuildSettings {
    public ServerType type = ServerType.PAPER;
    public String version = "1.20.6-R0.1-SNAPSHOT";
    public Dependencies dependencies = new Dependencies();
    public Repositories repositories = new Repositories();
    public Plugin plugin;
    public final Project project;

    public BuildSettings(Project project) {
        this.project = project;
    }

    public Dependencies dependencies(Action<Dependencies> pom) {
        pom.execute(dependencies);
        return dependencies;
    }

    public Repositories repositories(Action<Repositories> pom) {
        pom.execute(repositories);
        return repositories;
    }

    public Plugin create(String name, Action<Plugin> pom) {
        plugin = new Plugin(project, name);
        pom.execute(plugin);
        return plugin;
    }

    public static class Plugin {
        public final Map<String, Object> items = new HashMap<>();
        public final Project project;

        public final String name;
        public String version;
        public String main;
        public String author;
        public String description;
        public String apiVersion;
        public String website;

        public Plugin(Project project, String name) {
            this.project = project;
            this.name = name;
        }

        public void addItem(String key, Object value) {
            if (value != null) {
                if (value instanceof String str_value) {
                    if (str_value.isBlank() || str_value.isEmpty()) {
                        return;
                    }

                    items.put(key, value);
                } if (value instanceof Map<?, ?> collection) {
                    if (collection.isEmpty()) return;

                    items.put(key, value);
                } else if (value instanceof List<?> list) {
                    if (list.isEmpty()) return;

                    items.put(key, value);
                } else if (value instanceof Set<?> set) {
                    if (set.isEmpty()) return;

                    items.put(key, value);
                } else {
                    items.put(key, value);
                }
            }
        }

        public Map<String, Object> getItems() {
            return items;
        }

        @ApiStatus.Internal
        public void toYaml(File file) {
            try {
                YamlWriter writer = new YamlWriter(new FileWriter(file));
                writer.clearAnchors();
                writer.write(getItems());
                writer.close();
            } catch (IOException e) {
                throw new PluginBuildException(e.getMessage());
            }
        }

        public void setApiVersion(String apiVersion) {
            this.apiVersion = apiVersion;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public Map<String, Map<String, Object>> commands = new HashMap<>();

        public Command createCommand(String name, Action<Command> pom) {
            Command commandInstance = new Command(name);
            pom.execute(commandInstance);

            Map<String, Object> command = new HashMap<>();
            addItem(command, "description", commandInstance.description);
            addItem(command, "aliases", commandInstance.aliases);
            addItem(command, "permission", commandInstance.permission);
            addItem(command, "permission-message", commandInstance.permissionMessage);
            addItem(command, "usage", commandInstance.usage);

            commands.put(commandInstance.name, command);

            return commandInstance;
        }

        public static class Command {
            public final String name;
            public String description;
            public String[] aliases;
            public String permission;
            public String permissionMessage;
            public String usage;

            public Command(String name) {
                this.name = name;
            }

            public void setPermissionMessage(String permissionMessage) {
                this.permissionMessage = permissionMessage;
            }

            public void setPermission(String permission) {
                this.permission = permission;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public void setUsage(String usage) {
                this.usage = usage;
            }

            public void setAliases(String[] aliases) {
                this.aliases = aliases;
            }
        }

        public Map<String, Map<String, Object>> permissionss = new HashMap<>();

        public Permissions createPermissions(String name, Action<Permissions> pom) {
            Permissions permissionsInstance = new Permissions(name);
            pom.execute(permissionsInstance);

            Map<String, Object> permissions = new HashMap<>();
            addItem(permissions, "description", permissionsInstance.description);
            addItem(permissions, "default", permissionsInstance.defaults);
            addItem(permissions, "children", permissionsInstance.children);

            permissionss.put(permissionsInstance.name, permissions);

            return permissionsInstance;
        }

        public static class Permissions {
            public final String name;
            public String description;
            public String defaults;
            private final Map<String, Boolean> children = new HashMap<>();

            public Permissions(String name) {
                this.name = name;
            }

            public void addChildren(String name, Boolean enable) {
                children.put(name, enable);
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public void setDefaults(String defaults) {
                this.defaults = defaults;
            }
        }

        @ApiStatus.Internal
        private void addItem(Map<String, Object> map, String key, Object value) {
            if (value != null) {
                if (value instanceof String str_value) {
                    if (str_value.isEmpty() || str_value.isBlank()) return;

                    map.put(key, value);
                } else {
                    map.put(key, value);
                }
            }
        }
    }

    public static class Dependencies {
        public final List<String> dependencies = new ArrayList<>();

        public void pluginImplementation(String group, String name, String version) {
            dependencies.add(group + ":" + name + ":" + version);
        }

        public void pluginImplementation(String depend) {
            dependencies.add(depend);
        }
    }

    public static class Repositories {
        public final List<String> repositories = new ArrayList<>();

        public void maven(String url) {
            repositories.add(url);
        }
    }
}
