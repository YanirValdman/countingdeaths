package countingdeaths;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

public class countingdeaths extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("countingdeaths plugin enabled!");
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        updateScoreboard();
        announceLeader();
    }

    public void updateScoreboard() {
        Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
        Objective obj = board.getObjective("deaths");

        if (obj == null) {
            obj = board.registerNewObjective("deaths", "deathCount", "§c§lDeath Counter");
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            obj.getScore(p.getName()).setScore(p.getStatistic(Statistic.DEATHS));
        }
    }

    public void announceLeader() {
        Player leader = null;
        int maxDeaths = -1;

        for (Player p : Bukkit.getOnlinePlayers()) {
            int pDeaths = p.getStatistic(Statistic.DEATHS);
            if (pDeaths > maxDeaths) {
                maxDeaths = pDeaths;
                leader = p;
            }
        }

        if (leader != null && maxDeaths > 0) {
            Bukkit.broadcastMessage("§6§lLEADER: §e" + leader.getName() + " §7is a dumbass with an astonishing amount of" + maxDeaths + " §7deaths!");
        }
    }
}