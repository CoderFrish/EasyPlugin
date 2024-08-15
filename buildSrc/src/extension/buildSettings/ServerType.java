package extension.buildSettings;

public enum ServerType {
    PAPER("io.papermc.paper:paper-api"),
    /*
    * @deprecated Since Bukkit's version is too old, it is recommended to use Paper and Spigot if you want to write a new version of the plugin.
    */
    @Deprecated
    BUKKIT("org.bukkit:bukkit"),
    SPIGOT("org.spigotmc:spigot-api"),
    /*
    * @deprecated The new server of the Paper team may be unstable due to the destruction of some of the original APIs, so we recommend using stable Paper and Spigot
    */
    @Deprecated
    FOLIA("dev.folia:folia-api");

    final String name;

    ServerType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
