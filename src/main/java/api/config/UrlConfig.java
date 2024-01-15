package api.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@Config.Sources({
        "classpath:urls/url.properties"
})
public interface UrlConfig extends Config {
    @Key("baseUri")
    String baseUri();

    static UrlConfig get() {
        return ConfigFactory.create(UrlConfig.class);
    }
}
