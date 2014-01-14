package com.dre.managerxl.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.BlockIterator;

import com.dre.managerxl.P;

public class MUtility {
	public static String parseMessage(String[] args, int start) {
		String message = "";

		for (int i = start; i < args.length; i++) {
			message += args[i] + " ";
		}

		message = message.trim();

		return message;
	}

	public static int parseInt(String string) {
		return NumberUtils.toInt(string, 0);
	}

	public static int getStringTimeToInt(String time) {
		int result = 0;
		int index = 0;
		int lastIndex = 0;

		// Days
		index = time.indexOf("d");
		if (index > lastIndex) {
			result += parseInt(time.substring(lastIndex, index)) * 24 * 60 * 60;
		}

		// Hours
		lastIndex = index;
		index = time.indexOf("h");
		if (index > lastIndex) {
			result += parseInt(time.substring(lastIndex + 1, index)) * 60 * 60;
		}

		// Minutes
		lastIndex = index;
		index = time.indexOf("m");
		if (index > lastIndex) {
			result += parseInt(time.substring(lastIndex + 1, index)) * 60;
		}

		// Seconds
		lastIndex = index;
		index = time.indexOf("s");
		if (index > lastIndex) {
			result += parseInt(time.substring(lastIndex + 1, index));
		}

		return result * 1000; // Seconds to millisecs
	}

	public static String getIntTimeToString(long l) {
		String result = "";

		l = l / 1000; // Millisecs to seconds

		// Days
		int dayTime = 24 * 60 * 60;
		if (l >= dayTime) {
			result += ((int) (l / dayTime)) + " " + P.p.getLanguageReader().get("Format_Days") + " ";
			l = l % dayTime;
		}

		// Hours
		int hourTime = 60 * 60;
		if (l >= hourTime) {
			result += ((int) (l / hourTime)) + " " + P.p.getLanguageReader().get("Format_Hours") + " ";
			l = l % hourTime;
		}

		// Minute
		int minuteTime = 60;
		if (l >= minuteTime) {
			result += ((int) (l / minuteTime)) + " " + P.p.getLanguageReader().get("Format_Minutes") + " ";
			l = l % minuteTime;
		}

		// Seconds
		if (l >= 1) {
			result += l + " " + P.p.getLanguageReader().get("Format_Seconds") + " ";
			l = l % minuteTime;
		}

		return result.trim();
	}

	public static Location getNearestFreePosition(Location currentPosition) {
		Block block = currentPosition.getBlock();

		for (int y = 0; y < 256 - block.getY(); y++) {
			Block tmpBlock = block.getRelative(BlockFace.UP, y);

			if (tmpBlock.getType() == Material.AIR) {
				if(tmpBlock.getRelative(BlockFace.UP, 1).getType() == Material.AIR){
					return tmpBlock.getLocation();
				}
			}
		}

		return new Location(block.getWorld(), block.getX(), 256, block.getZ());
	}

	public static long getStringDateToLong(String date, String time) {
		SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		Date d = null;
		try {
			d = f.parse(date + " " + time);
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
		return d.getTime();
	}
	
	public static Block getLastBlockInSight(LivingEntity entity, int distance){
		Block block = null;
		
		if (entity != null) {
			BlockIterator bit = new BlockIterator(entity, distance);
			
			while(bit.hasNext())
			{
				block = bit.next();
				if (block.getType() != Material.AIR){
					break;
				}
			}
		}
		
		return block;
	}
}
