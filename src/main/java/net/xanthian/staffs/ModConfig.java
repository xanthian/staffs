package net.xanthian.staffs;

import draylar.omegaconfig.api.Comment;
import draylar.omegaconfig.api.Config;

public class ModConfig implements Config {

    @Comment(value = "Hello!")
    boolean value = false;

    /**
     * Returns the name of this config, which is used for the name of the config file saved to disk, and syncing.
     *
     * <p>
     * The name returned by this method should generally follow Identifier conventions, but this is not enforced:
     * <ul>
     *     <li>Lowercase
     *     <li>No special characters ($, %, ^, etc.)
     *     <li>No spaces
     *
     * @return the name of this config, which is used for the name of the config file saved to disk.
     */
    @Override
    public String getName() {
        return "staffs-config";
    }
}
