package api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreatePlayerResponse {

	@JsonProperty("password")
	private Object password;

	@JsonProperty("role")
	private Object role;

	@JsonProperty("gender")
	private Object gender;

	@JsonProperty("id")
	private int id;

	@JsonProperty("screenName")
	private Object screenName;

	@JsonProperty("login")
	private String login;

	@JsonProperty("age")
	private Object age;
}