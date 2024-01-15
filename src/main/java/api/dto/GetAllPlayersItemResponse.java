package api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GetAllPlayersItemResponse {

	@JsonProperty("gender")
	private String gender;

	@JsonProperty("id")
	private int id;

	@JsonProperty("screenName")
	private String screenName;

	@JsonProperty("age")
	private int age;
}