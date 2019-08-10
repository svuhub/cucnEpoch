package cucn.collect.common.domain.Test;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class Contest implements testcon {
    @Override
    public String testcon() {
        return "hello";
    }
}
