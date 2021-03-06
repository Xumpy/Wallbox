package com.xumpy.wallbox;

import com.xumpy.wallbox.module.RestWallbox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WallboxController {
    @Autowired RestWallbox restWallbox;

    @GetMapping("/start/{chargerId}")
    @Cacheable("start")
    public String startCharging(@PathVariable Integer chargerId){
        restWallbox.refreshAuthenticateToken();
        restWallbox.startCharging(chargerId);

        return "success";
    }

    @GetMapping("/stop/{chargerId}")
    @Cacheable("stop")
    public String stopCharging(@PathVariable Integer chargerId){
        restWallbox.refreshAuthenticateToken();
        restWallbox.stopCharging(chargerId);

        return "success";
    }
}
