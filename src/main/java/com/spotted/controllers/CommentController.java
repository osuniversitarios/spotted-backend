package com.spotted.controllers;

import com.spotted.models.Comment;
import com.spotted.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(value = "*")
public class CommentController {

    @Autowired
    CommentService commentService;

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Comment save(@PathVariable Comment comment) {
        return this.commentService.save(comment);
    }
}