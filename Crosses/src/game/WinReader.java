package game;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;

enum Player {
	X,
	Y
}

public class WinReader {
	public static final Path path = Paths.get(System.getProperty("user.dir") + "\\src\\player_wins\\wins.txt");
	
	public static Hashtable<Player,Integer> getWins() {
		try { //was able to get file goes here
			Hashtable<Player,Integer> wins = new Hashtable<Player,Integer>();
			
			String rawFileText = new String(Files.readAllBytes(path));
			String[] rawWinValues = rawFileText.split("\\,");  //regex \\, designates a split by commas
			wins.put(Player.X,Integer.parseInt(rawWinValues[0]));
			wins.put(Player.Y,Integer.parseInt(rawWinValues[1]));
			
			return wins;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void writeWinsFor(Player player) {
		Player otherPlayer = player == Player.X ? Player.Y : Player.X;
		int otherWins = getWins().get(otherPlayer);
		int targetWins = getWins().get(player);
		targetWins++;
		
		String rawWriteData;
		if (player == Player.X) {
			rawWriteData = targetWins + "," + otherWins;
		} else {
			rawWriteData = otherWins + "," + targetWins;
		}
		byte[] writeData = rawWriteData.getBytes(StandardCharsets.UTF_8);
		try {
			Files.write(path, writeData);
			System.out.println("WROTE WIN");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void resetWins() {
		byte[] data = "0,0".getBytes(StandardCharsets.UTF_8);
		try {
			Files.write(path, data);
			System.out.println("RESET WINS");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
