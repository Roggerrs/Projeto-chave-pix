package br.com.Roger.Roger.pix.pix;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.pix")
public record PixConfig(String clientId, String clientSecret, boolean sandbox,
                       boolean debug, String certificatePath) {
}
