package clientSide.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = { "classpath:settings/config.${myhost}.properties"},ignoreResourceNotFound = true)
public class SettingsConfiguraion {
}
