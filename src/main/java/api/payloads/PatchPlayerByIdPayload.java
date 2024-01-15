package api.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true)
public class PatchPlayerByIdPayload{

	@JsonProperty("password")
	private String password;

	@JsonProperty("role")
	private String role;

	@JsonProperty("gender")
	private String gender;

	@JsonProperty("screenName")
	private String screenName;

	@JsonProperty("login")
	private String login;

	@JsonProperty("age")
	private int age;
}