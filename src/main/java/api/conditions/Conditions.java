package api.conditions;

import org.hamcrest.Matcher;

public class Conditions {
    public static StatusCodeCondition statusCode(int code) {
        return new StatusCodeCondition(code);
    }

    public static BodyFieldCondition bodyField(String jsonPath, Matcher matcher) {
        return new BodyFieldCondition(jsonPath, matcher);
    }
}
