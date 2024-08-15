package tasks;

import extension.BuildSettings;
import org.gradle.api.DefaultTask;

public class BaseTask extends DefaultTask {
    public final BuildSettings settings;

    public BaseTask() {
        this.settings = getProject().getExtensions().getByType(BuildSettings.class);
        this.setGroup("plugin-userdev");
    }
}
