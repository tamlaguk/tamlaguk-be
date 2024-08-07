package tamlagukbe.tamlagukbe.global.config;

import io.jsonwebtoken.lang.Assert;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomOpenAIConfig {
    @Value("${openai.api.key}")
    private String openAiKey;

    @Bean
    public OpenAiApi openAiApi() {
        Assert.hasText(openAiKey, "OpenAI API key must be set");
        return new OpenAiApi(openAiKey);
    }
}

