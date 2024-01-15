package api.services;

import api.assertions.AssertableResponse;
import api.dto.CreatePlayerResponse;
import api.payloads.DeletePlayerByIdPayload;
import api.payloads.GetPlayerByIdPayload;
import api.payloads.PatchPlayerByIdPayload;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashMap;


public class PlayerApiService extends ApiService {
    public ArrayList<Integer> playerIdsToBeDeleted = new ArrayList<>();
    private final String basePath = "/player";

    @Step("Create new player")
    public AssertableResponse createNewPlayer(HashMap<String, Object> createNewPlayerQueryParameters, String editor) {
        Response response = setup()
                .basePath(basePath)
                .queryParams(createNewPlayerQueryParameters)
                .pathParam("editor", editor)
                .when().get("/create/{editor}");
        putUserIntoDeletionQueue(response.getBody().as(CreatePlayerResponse.class).getId());
        return new AssertableResponse(response);
    }

    @Step("Get all players")
    public AssertableResponse getAllPlayers() {

        return new AssertableResponse(setup()
                .basePath(basePath)
                .when().get("/get/all"));
    }

    @Step("Get player by id {id}")
    public AssertableResponse getPlayerById(GetPlayerByIdPayload id) {

        return new AssertableResponse(setup()
                .basePath(basePath)
                .body(id)
                .post("/get"));
    }

    @Step("Delete player by id {id}")
    public AssertableResponse deletePlayer(DeletePlayerByIdPayload id) {

        return new AssertableResponse(setup()
                .basePath(basePath)
                .pathParam("editor", "supervisor")
                .body(id)
                .delete("/delete/{editor}"));
    }

    @Step("Patch player by id")
    public AssertableResponse patchPlayer(String editor, int id, PatchPlayerByIdPayload payload) {
        return new AssertableResponse(setup()
                .basePath(basePath)
                .pathParam("editor", editor)
                .pathParam("id", id)
                .body(payload)
                .when().patch("/update/{editor}/{id}"));
    }

    private void putUserIntoDeletionQueue(int id) {
        playerIdsToBeDeleted.add(id);
    }
}
