import extension.BuildSettings;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.jetbrains.annotations.NotNull;
import tasks.GenPluginConfigTasks;

import java.net.URI;

public class BuildPlugin implements Plugin<Project> {
    @Override
    public void apply(@NotNull Project target) {
        final Logger logger = target.getLogger();
        final String version = (String) (target.getVersion());

        logger.lifecycle("NuclearWastewater Ver." + version);

        BuildSettings settings = target.getExtensions().create("buildSettings", BuildSettings.class, target);
        target.afterEvaluate((project) -> {
            // Paper
            project.getRepositories().maven((mvn) -> mvn.setUrl(URI.create("https://repo.papermc.io/repository/maven-public/")));

            // Spigot
            project.getRepositories().maven((mvn) -> mvn.setUrl(URI.create("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")));
            project.getRepositories().maven((mvn) -> mvn.setUrl(URI.create("https://oss.sonatype.org/content/repositories/snapshots")));

            project.getRepositories().mavenCentral();
            project.getRepositories().mavenLocal();
            project.getRepositories().google();

            project.getDependencies().add("compileOnly", project.getDependencies().create(settings.type + ":" + settings.version));

            project.getTasks().create("genPluginYamlConfig", GenPluginConfigTasks.class);
            project.getTasks().getByName("compileJava").finalizedBy(
                    project.getTasks().getByName("genPluginYamlConfig")
            );
        });
    }
}
