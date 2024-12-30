package pidroclassic;

import pidroclassic.exception.TeamIsFullException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Team implements Serializable {

    private final TeamName teamName;

    private final Player[] members;

    private final List<Integer> points;

    public Team(TeamName teamName) {
        this.teamName = teamName;
        this.members = new Player[2];
        this.points = new ArrayList<>();
    }

    public void addPlayer(Player p) throws TeamIsFullException {
        if (isFull())
            throw new TeamIsFullException();
        if(members[0] == null)
            members[0] = p;
        else
            members[1] = p;
    }

    public Player getPlayer(int i) {
        return members[i];
    }

    public boolean isFull() {
        return members[0] != null && members[1] != null;
    }

    @Override
    public String toString() {
        return teamName.toString().substring(0, 1) + teamName.toString().substring(1).toLowerCase();
    }
}
