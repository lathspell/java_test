
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cli {

    private static final Logger log = LoggerFactory.getLogger(Cli.class);

    public static void main(String[] args) throws InterruptedException {
        log.info("cli started");
        for (int i=0; i<300; i++) {
            log.warn("cli running");
            Thread.sleep(1000*1);
        }
    }
}
