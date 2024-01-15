package api.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true)
public class GetPlayerByIdPayload{

	@JsonProperty("playerId")
	private int playerId;

	@Override
	public String toString() {
		return "playerId=" + playerId;
	}
}