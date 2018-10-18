package com.spotted.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spotted.models.Spotted;
import com.spotted.services.SpottedService;


@RestController
@RequestMapping(value = "/api")
@CrossOrigin(value = "*")
public class SpottedController {

    @Autowired
    SpottedService spottedService; 

    @RequestMapping(value = "/spotted", method = RequestMethod.POST)
    public Spotted save(@RequestBody Spotted spotted) {
        return this.spottedService.save(spotted);
    }
}
