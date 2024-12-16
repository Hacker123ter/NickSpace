package org.dw363.nickspace.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TeamManager {

    private static final String TEAM_NAME = "Nickname";

    public static void createOrGetTeam() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getTeam(TEAM_NAME);
        if (team == null) {
            team = scoreboard.registerNewTeam(TEAM_NAME);
        }
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
    }

    public static void addPlayerToTeam(Player player) {
        Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(TEAM_NAME);
        if (team != null && !team.hasEntry(player.getName())) {
            team.addEntry(player.getName());
        }
    }
}