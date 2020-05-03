package pidro;

import pidro.deck.Deck;
import pidro.deck.card.Card;
import pidro.deck.card.Suit;
import pidro.exception.GameNotReadyException;
import pidro.exception.IllegalBidException;
import pidro.exception.LastMustBidException;
import pidro.exception.TeamIsFullException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Game {

    private final Team[] teams;

    private Deck deck;

    private int currentPlayerIdx = 0;

    private Player currentPlayer = null;

    private Player dealer = null;

    private Player winningBidPlayer = null;

    private int winningBid = 0, bidCount = 0;

    private Suit currentSuit;

    Game() {
        deck = new Deck();
        teams = new Team[]{new Team(TeamName.WE), new Team(TeamName.THEY)};
    }

    void joinTeam(String team, String playerName) throws TeamIsFullException {
        Player newPlayer = new Player(playerName);
        if(team.toLowerCase().equals("vi")) {
            teams[0].addPlayer(newPlayer);
            System.out.printf("%s joined the table into team %s!\n", newPlayer, teams[0]);
        }
        else {
            teams[1].addPlayer(newPlayer);
            System.out.printf("%s joined the table into team %s!\n", newPlayer, teams[1]);
        }

        if(currentPlayer == null)
            currentPlayer = newPlayer;

    }

    boolean isReady() {
        return teams[0].isFull() && teams[1].isFull();
    }

    boolean biddingIsDone(){return bidCount == 4;}

    /**
     * Decides in a classical way who should be the initial dealer.
     * Each person is dealt one card from the top of the pile. The person with the highest card starts as dealer.
     * In cases of ties, more cards are drawn between the people that tie until a winner is decided.
     * @throws GameNotReadyException if the game is not ready to start.
     */
    void findInitDealer() throws GameNotReadyException{
        if(!isReady())
            throw new GameNotReadyException();

        //One card per player is kept
        Card[] cardsPerPlayer = new Card[4];
        //initial indices
        int[] indices_arr = {0,1,2,3};
        List<Integer> indices = Arrays.stream(indices_arr).boxed().collect(Collectors.toList());
        Card max = null;
        boolean found = false;

        //Loop while we have not found a dealer
        while (!found) {
            max = null;
            //Loop over indices list and draw a card for each player in the list.
            for (Integer i : indices) {
                cardsPerPlayer[i] = deck.popTopCard();
                System.out.printf("%s drew %s\n",getPlayerByIdx(i), cardsPerPlayer[i]);
            }

            //Find the card with the highest value
            Card current = null;
            for (Integer i : indices) {
                current = cardsPerPlayer[i];
                if (max == null)
                    max = current;
                if (max.compareTo(current) < 0) {
                    max = current;
                }
            }

            //Loop through the cards again and check if multiple cards have the highest value, if so add them to next
            //iteration.
            List<Integer> temp = new ArrayList<>();
            for (Integer i : indices) {
                current = cardsPerPlayer[i];
                if (current.compareTo(max) == 0)
                    temp.add(i);
            }

            //if only one card has the highest value, stop looping
            if (temp.size() == 1)
                found = true;
            indices = new ArrayList<>(temp);
        }
        int foundPlayerIdx = indices.get(0);
        this.dealer = getPlayerByIdx(foundPlayerIdx);
        setCurrentPlayer(dealer);
        //this.currentPlayerIdx = foundPlayerIdx;
        refreshDeck(); //Refresh the deck
        System.out.printf("%s is the first dealer.\n", dealer);
    }

    void splitDeck(int amount) {
        this.deck.poke(amount);
    }

    void initialDeal() {
        //Deal to all 4 players, three times per player
        Player curr;
        for(int i = 0; i < 4*3; i++) {
            nextPlayer(); //Dealer deals to himself last
            curr = getCurrentPlayer();
            //Deal 3 cards at a time
            for(int j = 0; j < 3; j++)
                curr.giveCard(deck.popTopCard());
        }

        sortPlayerCards();
        nextPlayer(); //Player after dealer starts bidding
        System.out.printf("%s starts the bidding...\n", getCurrentPlayer());
    }

    /**
     * Takes a bid, makes sure the bid is legal, otherwise throws exception. Also makes sure that the last player will
     * bid if nothing has been bid yet. Finally sets the new leading bid player and changes to next player's turn or to
     * the final winning bid player if bidding is over.
     * @param bid amount that is bid for current player
     * @throws IllegalBidException Thrown if a bid less than 6 or higher than 14 is given (and not equal to zero). Also
     * thrown in the case of a bid lower than the winning bid is given.
     * @throws LastMustBidException Thrown when the bidding player is the last player to bid and no one has bid yet and
     * the he tries to pass.
     */
    void bidCurrentPlayerAndNext(int bid) throws IllegalBidException, LastMustBidException {
        //Illegal bid
        if ((bid != 0 && bid < 6) || bid > 14) {
            throw new IllegalBidException();
        }
        //Bid is below the leading
        else if (bid <= winningBid && bid != 0) {
            throw new IllegalBidException();
        }
        //New leading bid
        else if (bid > winningBid) {
            winningBid = bid;
            winningBidPlayer = currentPlayer;
            System.out.printf("%s bid %d\n", winningBidPlayer, winningBid);
        }
        //If it got this far and the current player is the last to bid. He has to bid.
        else if (currentPlayer == dealer && winningBid == 0) {
            throw new LastMustBidException();
        }
        //bid == 0 --> Pass
        else
            System.out.printf("%s passed.\n", currentPlayer);

        nextPlayer();
        bidCount++;
        if(biddingIsDone())
            setCurrentPlayer(winningBidPlayer);
    }

    void setCurrentSuit(Suit suit) {
        this.currentSuit = suit;
        System.out.printf("%s is playing in %s.\n", currentPlayer, suit);
    }

    private void refreshDeck() {
        this.deck = new Deck();
    }

    /*----------------------------------Player Management----------------------------------*/

    void sortPlayerCards() {
        for (int i = 0; i < 4 ; i++)
            getPlayerByIdx(i).sortCardsBySuit();
    }

    void popIrrelevantCards() {
        for(Team team : teams) {
            team.getPlayer(0).removeIrrelevantCards(currentSuit);
            team.getPlayer(1).removeIrrelevantCards(currentSuit);
        }
        //The current player set to the dealer because IRL he would not deal the second deal.
        setCurrentPlayer(dealer);
    }

    void nextPlayer() {
        currentPlayerIdx++;
        currentPlayerIdx = currentPlayerIdx == 4 ? 0 : currentPlayerIdx;
        currentPlayer = getPlayerByIdx(currentPlayerIdx);
    }

    Player peekNextPlayer() {
        int tempIdx = currentPlayerIdx;
        tempIdx++;
        tempIdx = tempIdx == 4 ? 0 : tempIdx;
        return getPlayerByIdx(tempIdx);
    }

    Player getPreviousPlayer() {
        int tempIdx = currentPlayerIdx;
        tempIdx--;
        tempIdx = tempIdx == -1 ? 3 : tempIdx;
        return getPlayerByIdx(tempIdx);
    }

    Player getCurrentPlayer() {
        return currentPlayer;
    }

    Player getWinningBidPlayer() {
        return winningBidPlayer;
    }

    private Player getPlayerByIdx(int playerN) {
        int i = 0, j = 0;
        switch (playerN) {
            case 0:
                i = j = 0;
                break;
            case 1:
                i = 1; j = 0;
                break;
            case 2:
                i = 0; j = 1;
                break;
            case 3:
                i = j = 1;
                break;
        }
        return teams[i].getPlayer(j);
    }

    private void setCurrentPlayer(Player p) {
        this.currentPlayer = p;
        for(int i = 0; i < 4; i++) {
            if (getPlayerByIdx(i) == p) {
                this.currentPlayerIdx = i;
                break;
            }
        }
    }

    /*---------------------------------/Player Management----------------------------------*/

    //Debug
    void printPlayerCards(){
        Player temp;
        for(int i = 0; i < 4; i++) {
            temp = getPlayerByIdx(i);
            System.out.println(String.format("%s cards: %s", temp, temp.cardsAsString()));
        }

    }
}
