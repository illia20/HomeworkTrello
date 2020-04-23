import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.IOException;

public class TrelloTest {
    String boardId;
    String listId;
    String cardId;

    @BeforeSuite
    public void checkCreateBoard() throws IOException {
        RetrofitBuilder retrofitBuilder = new RetrofitBuilder();

        Board board = new Board();
        String name = "New board";

        Board createdBoard = retrofitBuilder.getTrelloApi().createBoard(board, name).execute().body();
        boardId = createdBoard.getId();
        Assert.assertEquals(createdBoard.getName(), name);
    }
    @BeforeSuite
    public void createList() throws IOException {
        RetrofitBuilder retrofitBuilder = new RetrofitBuilder();

        List list = new List();
        String name = "Testing list";
        List createdList = retrofitBuilder.getTrelloApi().createList(list, name, boardId).execute().body();
        listId = createdList.getId();
        Assert.assertEquals(createdList.getName(), name);
    }
    @Test(priority = 1)
    public void checkCreateCard() throws IOException {
        RetrofitBuilder retrofitBuilder = new RetrofitBuilder();

        Card card = new Card();
        String name = "New card";
        Card createdCard = retrofitBuilder.getTrelloApi().createCard(card, listId, name).execute().body();
        cardId = createdCard.getId();
        Assert.assertEquals(createdCard.getName(), name);
    }
    @Test(priority = 2)
    public void checkGetCard() throws IOException {
        RetrofitBuilder retrofitBuilder = new RetrofitBuilder();

        Card card = new Card();
        int response = retrofitBuilder.getTrelloApi().getCard(cardId, card.getKey(), card.getToken()).execute().code();
        Assert.assertEquals(response, 200);
    }
    @Test(priority = 3)
    public void checkUpdateCard() throws IOException {
        RetrofitBuilder retrofitBuilder = new RetrofitBuilder();
        Card card = new Card();
        String updatedName = "Card up";
        Card updatedCard = retrofitBuilder.getTrelloApi().updateCard(card, cardId, updatedName).execute().body();
        Assert.assertEquals(updatedCard.getName(), updatedName);
    }
    @Test(priority = 4)
    public void checkDeleteCard() throws IOException {
        RetrofitBuilder retrofitBuilder = new RetrofitBuilder();
        Card card = new Card();
        int response = retrofitBuilder.getTrelloApi().deleteCard(cardId, card.getKey(), card.getToken()).execute().code();
        Assert.assertEquals(response, 200);
    }
}
