package me.fixeddev.hawkvips.commands;

import me.fixeddev.bcm.parametric.CommandClass;
import me.fixeddev.bcm.parametric.annotation.Command;
import me.fixeddev.hawkvips.crate.Crate;
import me.fixeddev.hawkvips.crate.CrateManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Optional;

public class CrateCommands implements CommandClass {

    private CrateManager crateManager;

    public CrateCommands(CrateManager crateManager) {
        this.crateManager = crateManager;
    }

    @Command(names = "crate create", usage = "/<command> <crate name>", min = 1, max = 1, permission = "hawkvips.crate.create")
    public boolean crateCreate(CommandSender sender, String crateName) {
        Optional<Crate> crateOptional = crateManager.getCrate(crateName);

        if (crateOptional.isPresent()) {
            sender.sendMessage(ChatColor.YELLOW + "La crate " + crateName + " ya existe!");
            return true;
        }

        crateManager.saveCrate(crateManager.createCrate(crateName));

        sender.sendMessage(ChatColor.YELLOW + "La crate " + crateName + " ha sido creada!");
        return true;
    }
}
