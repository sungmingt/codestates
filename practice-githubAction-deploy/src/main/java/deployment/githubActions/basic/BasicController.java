package deployment.githubActions.basic;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {

    @GetMapping("/sungmin")
    public String test(){
        return "sungmin?!";
    }
    @GetMapping("/changho")
    public String test2(){
        return "changho?!";
    }

}
