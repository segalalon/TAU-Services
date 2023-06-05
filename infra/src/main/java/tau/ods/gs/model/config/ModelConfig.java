package tau.ods.gs.model.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource("classpath:environment.properties")
})
public class ModelConfig implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Bean
    EnvironmentVars environmentVars() {
        return new EnvironmentVars();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ModelConfig.applicationContext = applicationContext;
    }

    public static ApplicationContext getAppContext() {
        return ModelConfig.applicationContext;
    }
}
