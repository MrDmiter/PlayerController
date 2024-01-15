package api.assertions;

import api.conditions.Condition;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class AssertableResponse {
    private final Response response;

    @Step("Api response should have {condition}")
    public AssertableResponse shouldHave(Condition condition) {
        log.info("About to check condition {}", condition);
        condition.check(response);
        return this;
    }

    public <T> T asPojo(Class<T> classMapper) {
        log.info("Received response {}", response.as(classMapper));
        return response.as(classMapper);
    }
}
