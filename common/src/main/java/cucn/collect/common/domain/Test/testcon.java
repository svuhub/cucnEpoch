package cucn.collect.common.domain.Test;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/testcon")
public interface testcon {
    @RequestMapping("/con")
    public String testcon();
}
