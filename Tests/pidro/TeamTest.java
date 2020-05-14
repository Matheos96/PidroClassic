package pidro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pidro.exception.TeamIsFullException;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    private Team team;

    @BeforeEach
    void setUp() {
        this.team = new Team(TeamName.WE);
    }

    @Test
    void addGetPlayer() throws TeamIsFullException {
        team.addPlayer(new Player("player1"));
        team.addPlayer(new Player("player2"));

        assertEquals("player1", team.getPlayer(0).toString());
        assertEquals("player2", team.getPlayer(1).toString());

        assertThrows(TeamIsFullException.class, () -> {
            team.addPlayer(new Player("player3"));
        });

    }

    @Test
    void isFull() throws TeamIsFullException {
        assertFalse(team.isFull());
        team.addPlayer(new Player("player1"));
        assertFalse(team.isFull());
        team.addPlayer(new Player("player2"));
        assertTrue(team.isFull());
    }

    @Test
    void testToString() {
        assertEquals("We", team.toString());
    }
}