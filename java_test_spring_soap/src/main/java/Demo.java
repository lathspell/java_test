@SpringBootApplication
@Slf4j
public class FooClientSpringDemo {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(FooClientSpringDemo.class, args);
    }

    @Bean
    CommandLineRunner lookup(FooClient fooClient) {
        return (String[] args) -> {
            log.info("Args: {}", Arrays.deepToString(args));

            fooClient.getTemplateNames();
        };
    }

}

