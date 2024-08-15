package tasks;

import exception.PluginNameException;
import extension.BuildSettings;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskAction;
import org.jetbrains.annotations.ApiStatus;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class GenPluginConfigTasks extends BaseTask {
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @TaskAction
    public void genPluginConfigTasks() {
        BuildSettings settings = this.settings;
        BuildSettings.Plugin plugin = settings.plugin;
        addItem("name", plugin.name);
        addItem("version", plugin.version);
        addItem("main", plugin.main);
        addItem("author", plugin.author);
        addItem("description", plugin.description);
        addItem("api-version", plugin.apiVersion);
        addItem("website", plugin.website);

        addItem("commands", plugin.commands);
        addItem("permissions", plugin.permissionss);

        JavaPluginExtension java = getProject().getExtensions().getByType(JavaPluginExtension.class);
        File resourceDir = getProject().getLayout().getBuildDirectory().file("resources").get().getAsFile();
        for (SourceSet sourceSet : java.getSourceSets()) {
            if (!resourceDir.exists()) {
                resourceDir.mkdir();
            }
            File dir = new File(resourceDir, sourceSet.getName());
            if (!dir.exists()) {
                dir.mkdir();
            }

            File yaml = new File(dir, "plugin.yml");
            if (!yaml.exists()) {
                try {
                    yaml.createNewFile();
                } catch (IOException e) {
                    getProject().getLogger().lifecycle(e.getMessage());
                }
            }

            plugin.toYaml(yaml);
        }
    }

    @ApiStatus.Internal
    public void addItem(String key, Object value) {
        if (value != null) {
            if (value instanceof String str_value) {
                if (str_value.isBlank() || str_value.isEmpty()) {
                    return;
                }

                if (key.equals("name")) {
                    if (!Pattern.matches("^[a-zA-Z0-9_]+$", str_value)) {
                        throw new PluginNameException("The plugin name does not meet the requirements.");
                    }
                }

                this.settings.plugin.addItem(key, value);
            } if (value instanceof Map<?, ?> collection) {
                if (collection.isEmpty()) return;

                this.settings.plugin.addItem(key, value);
            } else if (value instanceof List<?> list) {
                if (list.isEmpty()) return;

                this.settings.plugin.addItem(key, value);
            } else if (value instanceof Set<?> set) {
                if (set.isEmpty()) return;

                this.settings.plugin.addItem(key, value);
            } else {
                this.settings.plugin.addItem(key, value);
            }
        }
    }
}
