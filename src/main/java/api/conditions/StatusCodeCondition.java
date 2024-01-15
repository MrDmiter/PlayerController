package api.conditions;

import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatusCodeCondition implements Condition {
    private final int statusCode;

    @Override
    public void check(Response response) {
        response.then().statusCode(statusCode);
    }

    @Override
    public String toString() {
        return "Status Code is " +
                statusCode;
    }
}
