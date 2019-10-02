package de.netcologne.fibreman.nokia.ams.demo.spring;

import javax.net.ssl.SSLContext;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

@Configuration
@Slf4j
public class FooConfiguration {

    @Bean
    public FooClient FooClient(WebServiceTemplate webServiceTemplate) {
        FooClient client = new FooClient();
        client.setWebServiceTemplate(webServiceTemplate);
        return client;
    }

    @Bean
    @SneakyThrows
    public WebServiceTemplate webServiceTemplate(
            @Value("${soap.url}") String soapUrl,
            @Value("${soap.user}") String soapUser,
            @Value("${soap.pass}") String soapPass,
            @Value("${soap.connectTimeoutMs}") int soapConnectTimeoutMs,
            @Value("${soap.requestTimeoutMs}") int soapRequestTimeoutMs,
            Jaxb2Marshaller marshaller) {

        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(soapUser, soapPass);

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, credentials);

        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(new TrustAllStrategy()).build();
        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(soapConnectTimeoutMs)
                .setSocketTimeout(soapRequestTimeoutMs)
                .build();

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .addInterceptorFirst(new HttpComponentsMessageSender.RemoveSoapHeadersInterceptor())
                .setSSLSocketFactory(sslSocketFactory)
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .setDefaultCredentialsProvider(credentialsProvider)
                .setDefaultRequestConfig(requestConfig)
                .build();

        HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
        messageSender.setHttpClient(httpClient);

        WebServiceTemplate wst = new WebServiceTemplate();
        wst.setMessageSender(messageSender);
        wst.setDefaultUri(soapUrl);
        wst.setMarshaller(marshaller);
        wst.setUnmarshaller(marshaller);

        return wst;
    }

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("org.example.foo"); // siehe <generatePackage> in pom.xml
        return marshaller;
    }
}

