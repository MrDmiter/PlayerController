package api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GetPlayerByIdResponse{

	@JsonProperty("password")
	private String password;

	@JsonProperty("role")
	private String role;

	@JsonProperty("gender")
	private String gender;

	@JsonProperty("id")
	private int id;

	@JsonProperty("screenName")
	private String screenName;

	@JsonProperty("login")
	private String login;

	@JsonProperty("age")
	private int age;
}