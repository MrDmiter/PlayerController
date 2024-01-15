import api.conditions.Conditions;
import api.dto.CreatePlayerResponse;
import api.dto.GetAllPlayersResponse;
import api.dto.GetPlayerByIdResponse;
import api.payloads.DeletePlayerByIdPayload;
import api.payloads.GetPlayerByIdPayload;
import api.payloads.PatchPlayerByIdPayload;
import api.utils.DeleteUtils;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import api.services.PlayerApiService;

import java.util.HashMap;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;

@Slf4j
public class PlayerControllerTests {
    private final PlayerApiService playerApiService = new PlayerApiService();
    private final DeleteUtils deleteUtils = new DeleteUtils();
    HashMap<String, Object> createPlayerQueryParameters = new HashMap<>();

    //Bug in app, response shouldnt contain null when there are existing values
    @Test(description = "Player with user role can be created")
    public void verifiesThatPlayerWithUserRoleCanBeCreatedTest() {
        playerApiService.createNewPlayer(queryParamForPlayerCreation(30, RandomStringUtils.randomAlphanumeric(10)), "supervisor")
                .shouldHave(Conditions.statusCode(200))
                .shouldHave(Conditions.bodyField("id", not(emptyOrNullString())))
                .shouldHave(Conditions.bodyField("login", not(emptyOrNullString())))
                .shouldHave(Conditions.bodyField("password", not(emptyOrNullString())))
                .shouldHave(Conditions.bodyField("screenName", not(emptyOrNullString())))
                .shouldHave(Conditions.bodyField("gender", not(emptyOrNullString())))
                .shouldHave(Conditions.bodyField("age", not(emptyOrNullString())))
                .shouldHave(Conditions.bodyField("role", not(emptyOrNullString())))
                .shouldHave(Conditions.bodyField("age", not(emptyOrNullString())));
    }

    //Its a bug in app, as per requirements password must contain latin letters and numbers (min 7 max 15 characters)
    @Test(description = "Player cant be created if password length is short")
    public void verifiesThatPlayerCantBeCreatedWithInvalidPasswordTest() {
        //Create new player and verifies status code and data
        playerApiService.createNewPlayer(queryParamForPlayerCreation(30, RandomStringUtils.randomAlphanumeric(2)), "supervisor")
                .shouldHave(Conditions.statusCode(400));
    }

    @Test(description = "All players can be fetched, including new")
    public void verifyThatGetAllEndpointReturnsAllPlayersTest() {
        //Get initial number of players
        GetAllPlayersResponse getFirstAllPlayersResponse = playerApiService.getAllPlayers()
                .shouldHave(Conditions.statusCode(200))
                .asPojo(GetAllPlayersResponse.class);
        int initialNumberOfPlayers = getFirstAllPlayersResponse.getPlayers().size();

        //Create new player
        playerApiService.createNewPlayer(queryParamForPlayerCreation(30, RandomStringUtils.randomAlphanumeric(10)),
                        "supervisor")
                .shouldHave(Conditions.statusCode(200));

        //Get all players after one is created
        GetAllPlayersResponse getSecondAllPlayersResponse = playerApiService.getAllPlayers()
                .shouldHave(Conditions.statusCode(200))
                .asPojo(GetAllPlayersResponse.class);
        int lastNumberOfPlayers = getSecondAllPlayersResponse.getPlayers().size();

        //Verifies that before creation and after creation the number of players in response is different
        Assert.assertNotEquals(initialNumberOfPlayers, lastNumberOfPlayers);
    }

    @Test(description = "One player can be fetched")
    public void verifiesPlayerWithId1IsSupervisorTest() {
        //Data
        GetPlayerByIdPayload getPlayerByIdPayload = new GetPlayerByIdPayload().playerId(1);

        //Get user with id 1
        GetPlayerByIdResponse response = playerApiService
                .getPlayerById(getPlayerByIdPayload)
                .shouldHave(Conditions.statusCode(200))
                .asPojo(GetPlayerByIdResponse.class);

        Assert.assertEquals(response.getRole(), "supervisor");
    }

    @Test(description = "Supervisor cant be deleted")
    public void verifiesSupervisorPlayerCantBeDeletedTest() {
        //Data
        DeletePlayerByIdPayload deletePlayerByIdPayload = new DeletePlayerByIdPayload().playerId(1);

        //Attempt to delete player with supervisor role
        playerApiService.deletePlayer(deletePlayerByIdPayload)
                .shouldHave(Conditions.statusCode(403));
    }

    //Bug, player cant be updated with age out of range 16 - 60, but response returns 200 OK when age is 2, and swagger shows that its updated
    @Test(description = "Player cant be updated with age out of valid range")
    public void verifiesThatPlayerCantBePatchedWithInvalidDataTest() {

        PatchPlayerByIdPayload patchPlayerByIdPayload = new PatchPlayerByIdPayload()
                .age(2)
                .login(RandomStringUtils.randomAlphanumeric(10))
                .role("user")
                .screenName("patch-test")
                .gender("male")
                .password(RandomStringUtils.randomAlphanumeric(10));

        CreatePlayerResponse response = playerApiService.createNewPlayer(queryParamForPlayerCreation(30, RandomStringUtils.randomAlphanumeric(10)),
                        "supervisor")
                .shouldHave(Conditions.statusCode(200)).asPojo(CreatePlayerResponse.class);
        int createdPlayerId = response.getId();

        playerApiService.patchPlayer("supervisor", createdPlayerId, patchPlayerByIdPayload)
                .shouldHave(Conditions.statusCode(400));
    }

    @AfterClass
    public void finishActivities() {
        deleteUtils.deleteCreatedByAutotestsPlayers(playerApiService.playerIdsToBeDeleted);
    }

    private HashMap<String, Object> queryParamForPlayerCreation(int age, String password) {
        createPlayerQueryParameters.put("age", age);
        createPlayerQueryParameters.put("gender", "male");
        createPlayerQueryParameters.put("login", RandomStringUtils.randomAlphanumeric(10));
        createPlayerQueryParameters.put("password", password);
        createPlayerQueryParameters.put("role", "user");
        createPlayerQueryParameters.put("screenName", "autotest");
        return createPlayerQueryParameters;
    }
}
