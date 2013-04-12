package me.Staartvin.SimpleSuffix.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Staartvin.SimpleSuffix.SimpleSuffix;

public class Commands {

	SimpleSuffix plugin;

	public Commands(SimpleSuffix instance) {
		plugin = instance;
	}

	public boolean checkCharacterLimit(String message, boolean prefix) {
		message = message.toLowerCase();
		message = message.replaceAll("&k", "").replaceAll("&l", "")
				.replaceAll("&m", "").replaceAll("&n", "").replaceAll("&o", "")
				.replaceAll("&r", "").replaceAll("&0", "").replaceAll("&1", "")
				.replaceAll("&2", "").replaceAll("&3", "").replaceAll("&4", "")
				.replaceAll("&5", "").replaceAll("&6", "").replaceAll("&7", "")
				.replaceAll("&8", "").replaceAll("&9", "").replaceAll("&a", "")
				.replaceAll("&b", "").replaceAll("&c", "").replaceAll("&d", "")
				.replaceAll("&e", "").replaceAll("&f", "");

		if (prefix) {
			if (message.length() > plugin.getConfig().getInt(
					"character limit prefix")) {
				return false;
			}
		} else {
			if (message.length() > plugin.getConfig().getInt(
					"character limit suffix")) {
				return false;
			}
		}
		return true;
	}

	public boolean hasCensoredWords(String message) {
		message = message.toLowerCase();
		List<String> censoredWords = plugin.config.getCensoredWords();

		for (String word : censoredWords) {
			if (message.contains(word))
				return true;
		}
		return false;
	}

	private String removeWorldName(String resultString, String worldName) {
		// /suffix Staartvin is cool world Lala
		resultString = resultString.replace(" world " + worldName, "");
		return resultString;
	}

	public boolean checkColours(String message, CommandSender sender) {
		if (message.contains("&0") || message.contains("&1")
				|| message.contains("&2") || message.contains("&3")
				|| message.contains("&4") || message.contains("&5")
				|| message.contains("&6") || message.contains("&7")
				|| message.contains("&8") || message.contains("&9")
				|| message.contains("&a") || message.contains("&b")
				|| message.contains("&c") || message.contains("&d")
				|| message.contains("&e") || message.contains("&f")) {
			if (sender.hasPermission("simplesuffix.colours")
					|| sender.hasPermission("simplesuffix.colors")) {
				return true;
			}
			sender.sendMessage(ChatColor.RED + "You may not use colours!");
			return false;
		}
		return true;
	}

	public String createResult(String[] args, int counter, int argsLength) {
		List<String> variableResult = new ArrayList<String>();
		String resultString = "";

		for (int i = 0; i < (argsLength); i++) {
			variableResult.add(args[counter]);
			++counter;
		}
		for (int i = 0; i < variableResult.size(); i++) {
			if (i == 0) {
				resultString = resultString.concat(variableResult.get(i));
			} else {
				resultString = resultString.concat(" " + variableResult.get(i));
			}
		}
		return resultString;
	}

	public World getWorld(CommandSender sender, String[] args, int counter,
			int argsLength) {
		// /prefix Staartvin is cool world WorldName
		List<String> suffix = new ArrayList<String>();
		World world;
		String worldName;

		for (int i = 0; i < argsLength; i++) {
			suffix.add(args[counter]);
			++counter;
		}

		for (int i = 0; i < suffix.size(); i++) {
			String argument = suffix.get(i);

			if (argument.equalsIgnoreCase("world")) {
				if ((i + 1) > (suffix.size() - 1)) {
					sender.sendMessage(ChatColor.RED + "Invalid world!");
					return null;
				}
				worldName = suffix.get(i + 1);
				if (plugin.getServer().getWorld(worldName) == null) {
					sender.sendMessage(ChatColor.RED + "World '" + worldName
							+ "' is not a valid world!");
					return null;
				}
				world = plugin.getServer().getWorld(worldName);
				return world;
			}
		}

		if (!(sender instanceof Player)) {
			return null;
		}
		return null;
	}

	public boolean checkForFormats(String message, CommandSender sender) {
		message = message.toLowerCase();
		// Random
		if (message.contains("&k")) {
			if (!sender.hasPermission("simplesuffix.format.random")) {
				sender.sendMessage(ChatColor.RED + "You may not use '&k'!");
				return false;
			}
		}
		// Bold
		else if (message.contains("&l")) {
			if (!sender.hasPermission("simplesuffix.format.bold")) {
				sender.sendMessage(ChatColor.RED + "You may not use '&l'!");
				return false;
			}
		}
		// Strike
		else if (message.contains("&m")) {
			if (!sender.hasPermission("simplesuffix.format.strike")) {
				sender.sendMessage(ChatColor.RED + "You may not use '&m'!");
				return false;
			}
		}
		// Underlined
		else if (message.contains("&n")) {
			if (!sender.hasPermission("simplesuffix.format.underlined")) {
				sender.sendMessage(ChatColor.RED + "You may not use '&n'!");
				return false;
			}
		}
		// Italic
		else if (message.contains("&o")) {
			if (!sender.hasPermission("simplesuffix.format.italic")) {
				sender.sendMessage(ChatColor.RED + "You may not use '&o'!");
				return false;
			}
		}
		// Reset
		else if (message.contains("&r")) {
			if (!sender.hasPermission("simplesuffix.format.reset")) {
				sender.sendMessage(ChatColor.RED + "You may not use '&r'!");
				return false;
			}
		}
		return true;
	}

	public boolean setPrefix(CommandSender sender, String args[], boolean self) {
		// /prefix other Staartvin 
		plugin.logger.debug("I pass test 1");
		if (self) {
			if (!plugin.hasPermission("simplesuffix.set.prefix.self", sender))
				return true;
		} else {
			if (!plugin.hasPermission("simplesuffix.set.prefix.other", sender))
				return true;
		}
		if (self) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED
						+ "Only players can perform this command!");
				return true;
			}
		}
		plugin.logger.debug("I pass test 2");
		String resultString;
		World world;

		if (self) {
			resultString = plugin.commands.createResult(args, 0, args.length);
		} else {
			resultString = plugin.commands.createResult(args, 2,
					(args.length - 2));
		}
		plugin.logger.debug("I pass test 3");
		Player player = null;

		if (self) {
			player = (Player) sender;
		} else {
			List<Player> matchedPlayers = plugin.getServer().matchPlayer(
					args[1]);

			if (matchedPlayers.size() == 0) {
				sender.sendMessage(ChatColor.RED + "Player '" + args[1]
						+ "' cannot be found!");
				return true;
			}

			if (matchedPlayers.size() > 1) {
				sender.sendMessage(ChatColor.RED
						+ "There are more than 1 players found that match your argument. Be more precise!");
				return true;
			}
			player = matchedPlayers.get(0);
		}
		plugin.logger.debug("I pass test 4");
		if (self) {
			if (resultString.contains("off")) {
				world = getWorld(sender, args, 0, args.length);
				resultString = "";
				if (world == null) {
					sender.sendMessage(ChatColor.GREEN
							+ "Your global prefix has been turned off.");
					plugin.permHandler.clearGlobalPrefix(player);	
				} else {
					sender.sendMessage(ChatColor.GREEN
							+ "Your prefix on world '" + world.getName() + "' has been turned off.");
					plugin.permHandler.clearWorldPrefix(player, world.getName());	
				}
				return true;
			}
		} else {
			if (resultString.contains("off")) {
				world = getWorld(sender, args, 2, (args.length - 2));
				resultString = "";
				if (world == null) {
					sender.sendMessage(ChatColor.GREEN + "The global prefix of "
							+ player.getName() + " has been turned off.");
					plugin.permHandler.clearGlobalPrefix(player);
				} else {
					sender.sendMessage(ChatColor.GREEN + "The prefix of "
							+ player.getName() + " on world '" + world.getName() + "' has been turned off.");
					plugin.permHandler.clearWorldPrefix(player, world.getName());
				}	
				return true;
			}
		}
		plugin.logger.debug("I pass test 5");

		if (self) {
			world = getWorld(sender, args, 0, args.length);

			if (world != null) {
				resultString = removeWorldName(resultString, world.getName());
			}
		} else {
			world = getWorld(sender, args, 2, (args.length - 2));

			if (world != null) {
				resultString = removeWorldName(resultString, world.getName());
			}
		}
		if (!plugin.commands.checkForFormats(resultString, sender))
			return true;
		if (!plugin.commands.checkColours(resultString, sender))
			return true;
		if (!plugin.commands.checkCharacterLimit(resultString, true)) {
			sender.sendMessage(ChatColor.RED + "Prefix cannot exceed "
					+ plugin.getConfig().getInt("character limit prefix")
					+ " characters!");
			return true;
		}
		if (!player.hasPermission("simplesuffix.prefix.bypass.censor")) {
			if (plugin.commands.hasCensoredWords(resultString)) {
				sender.sendMessage(ChatColor.RED
						+ "Prefix contains censored words!");
				return true;
			}
		}
		plugin.logger.debug("I pass test 6");
		if (self) {

			if (world == null) {
				plugin.permHandler.setGlobalPrefix(player, resultString);
				sender.sendMessage(ChatColor.GREEN
						+ "Your global prefix has been changed to '"
						+ resultString + "'.");
			} else {
				plugin.permHandler.setWorldPrefix(player, resultString, world);
				sender.sendMessage(ChatColor.GREEN + "Your prefix on world '"
						+ world.getName() + "' has been changed to '"
						+ resultString + "'.");
			}
			plugin.logger.debug("I've changed the prefix!!");
			return true;
		} else {

			if (world == null) {
				plugin.permHandler.setGlobalPrefix(player, resultString);
				sender.sendMessage(ChatColor.GREEN + "The global prefix of "
						+ player.getName() + " has been changed to '"
						+ resultString + "'.");
			} else {
				plugin.permHandler.setWorldPrefix(player, resultString, world);
				sender.sendMessage(ChatColor.GREEN + "The prefix of "
						+ player.getName() + " on world '" + world.getName()
						+ "' has been changed to '" + resultString + "'.");
			}
			plugin.logger.debug("I can change the prefix for "
					+ player.getName());
			return true;
		}
	}

	public boolean setSuffix(CommandSender sender, String args[], boolean self) {

		if (self) {
			if (!plugin.hasPermission("simplesuffix.set.suffix.self", sender))
				return true;
		} else {
			if (!plugin.hasPermission("simplesuffix.set.suffix.other", sender))
				return true;
		}
		if (self) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED
						+ "Only players can perform this command!");
				return true;
			}
		}
		String resultString;
		World world;

		if (self) {
			resultString = plugin.commands.createResult(args, 0, args.length);
		} else {
			resultString = plugin.commands.createResult(args, 2,
					(args.length - 2));
		}

		Player player = null;

		if (self) {
			player = (Player) sender;
		} else {
			List<Player> matchedPlayers = plugin.getServer().matchPlayer(
					args[1]);

			if (matchedPlayers.size() == 0) {
				sender.sendMessage(ChatColor.RED + "Player '" + args[1]
						+ "' cannot be found!");
				return true;
			}

			if (matchedPlayers.size() > 1) {
				sender.sendMessage(ChatColor.RED
						+ "There are more than 1 players found that match your argument. Be more precise!");
				return true;
			}
			player = matchedPlayers.get(0);
		}

		if (self) {
			if (resultString.contains("off")) {
				world = getWorld(sender, args, 0, args.length);
				resultString = "";
				if (world == null) {
					sender.sendMessage(ChatColor.GREEN
							+ "Your global suffix has been turned off.");
					plugin.permHandler.clearGlobalSuffix(player);	
				} else {
					sender.sendMessage(ChatColor.GREEN
							+ "Your suffix on world '" + world.getName() + "' has been turned off.");
					plugin.permHandler.clearWorldSuffix(player, world.getName());	
				}
				return true;
			}
		} else {
			if (resultString.contains("off")) {
				world = getWorld(sender, args, 2, (args.length - 2));
				resultString = "";
				if (world == null) {
					sender.sendMessage(ChatColor.GREEN + "The global suffix of "
							+ player.getName() + " has been turned off.");
					plugin.permHandler.clearGlobalSuffix(player);
				} else {
					sender.sendMessage(ChatColor.GREEN + "The suffix of "
							+ player.getName() + " on world '" + world.getName() + "' has been turned off.");
					plugin.permHandler.clearWorldSuffix(player, world.getName());
				}
				return true;
			}
		}
		if (self) {
			world = getWorld(sender, args, 0, args.length);

			if (world != null) {
				resultString = removeWorldName(resultString, world.getName());
			}
		} else {
			world = getWorld(sender, args, 2, (args.length - 2));

			if (world != null) {
				resultString = removeWorldName(resultString, world.getName());
			}
		}

		if (!plugin.commands.checkForFormats(resultString, sender))
			return true;
		if (!plugin.commands.checkColours(resultString, sender))
			return true;
		if (!plugin.commands.checkCharacterLimit(resultString, false)) {
			sender.sendMessage(ChatColor.RED + "Suffix cannot exceed "
					+ plugin.getConfig().getInt("character limit suffix")
					+ " characters!");
			return true;
		}
		if (!player.hasPermission("simplesuffix.suffix.bypass.censor")) {
			if (plugin.commands.hasCensoredWords(resultString)) {
				sender.sendMessage(ChatColor.RED
						+ "Suffix contains censored words!");
				return true;
			}
		}
		if (self) {

			if (world == null) {
				plugin.permHandler.setGlobalSuffix(player, resultString);
				sender.sendMessage(ChatColor.GREEN
						+ "Your global suffix has been changed to '"
						+ resultString + "'.");
			} else {
				plugin.permHandler.setWorldSuffix(player, resultString, world);
				sender.sendMessage(ChatColor.GREEN + "Your suffix on world '"
						+ world.getName() + "' has been changed to '"
						+ resultString + "'.");
			}
			return true;
		} else {

			if (world == null) {
				plugin.permHandler.setGlobalSuffix(player, resultString);
				sender.sendMessage(ChatColor.GREEN + "The global suffix of "
						+ player.getName() + " has been changed to '"
						+ resultString + "'.");
			} else {
				plugin.permHandler.setWorldSuffix(player, resultString, world);
				sender.sendMessage(ChatColor.GREEN + "The suffix of "
						+ player.getName() + " on world '" + world.getName()
						+ "' has been changed to '" + resultString + "'.");
			}
			return true;
		}
	}
}
