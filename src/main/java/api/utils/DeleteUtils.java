package api.utils;

import api.payloads.DeletePlayerByIdPayload;
import api.services.PlayerApiService;

import java.util.ArrayList;

public class DeleteUtils {
    PlayerApiService playerApiService = new PlayerApiService();

    public void deleteCreatedByAutotestsPlayers(ArrayList<Integer> idsToBeDeleted) {

        for (Integer id : idsToBeDeleted) {
            DeletePlayerByIdPayload deletePlayerByIdPayload = new DeletePlayerByIdPayload().playerId(id);
            playerApiService.deletePlayer(deletePlayerByIdPayload);
        }
    }
}
