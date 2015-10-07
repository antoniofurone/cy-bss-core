package org.cysoft.bss.core.web.rest;

import org.cysoft.bss.core.web.rest.annotation.CyBssService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CyBssService(name = "User")
public class UserWs {

}
