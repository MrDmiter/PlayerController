package api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GetAllPlayersResponse {

    @JsonProperty("players")
    private List<GetAllPlayersItemResponse> players;

    public List<GetAllPlayersItemResponse> getPlayers() {
        return players;
    }
}