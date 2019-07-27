package wolky.web.backend;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import wolky.web.model.BlogEntry;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@Slf4j
public class BlogStorageClient {

    private final WebClient webClient;

    public BlogStorageClient(@Value("${storage.baseUrl}") String storageBaseUrl) {
        webClient = WebClient.create(storageBaseUrl);
        log.info("Created WebClient for {}", storageBaseUrl);
    }

    public List<BlogEntry> findAll() {
        log.info("Fetching all entries...");
        List<BlogEntry> list = webClient.get().uri(uriBuilder -> uriBuilder.path("/blog/entries/").build()).accept(APPLICATION_JSON)
                .retrieve().bodyToMono(new ParameterizedTypeReference<List<BlogEntry>>() {
                }).block();
        log.info("Found {} entries", list.size());
        return list;
    }
}
