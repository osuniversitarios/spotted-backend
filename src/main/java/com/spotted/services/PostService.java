package com.spotted.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spotted.enums.PostTypes;
import com.spotted.models.Comment;
import com.spotted.models.Post;
import com.spotted.repositories.PostRepository;

@Service
public class PostService {

	@Autowired
	PostRepository postRepository;
	
	@Autowired
	CommentService commentService;
	
	public Post save(Post post) {
		post.setDatetime(LocalDateTime.now(ZoneId.of("America/Recife")));
		return this.postRepository.save(post);
	}
	
	public List<Post> getAll() {
		List<Post> posts = this.postRepository.findAll();
		Collections.sort(posts);
		return posts;
	}
	
	public List<Post> getVisible(){
		return this.postRepository.findVisible();
	}
	
	public List<Post> getHidden(){
		return this.postRepository.findHidden();
	}

	public List<Post> searchByEmail(String email) {
		return this.postRepository.postsByEmail(email);
	}
	
	public Post searchById(Long id) {
		return this.postRepository.postsById(id);
	}

	public Post deleteById(Long id) {
		Post deleted = this.searchById(id);
		this.postRepository.deleteById(id);
		return deleted;
	}
	
	public Comment addComment(Long id, Comment comment) throws Exception {
		if (!this.postRepository.existsById(id)) {
			throw new Exception("There is not a post registered with this id in the system");
		}
		this.commentService.save(comment);
		Post post = this.postRepository.getOne(id);
		post.addComment(comment);
		this.postRepository.save(post);
		return comment;
	}
	
	public Set<Comment> getComments(Long id) {
		Post post = this.postRepository.findById(id).get();
		return post.getComments();
	}

	public Post update(Long id, Post post) {
		if (this.postRepository.existsById(id)) {
			this.postRepository.save(post);
		}
		return post;
	}

	public Comment updateComment(Long idPost, Long idComment, Comment newComment) throws Exception {
		if (!this.postRepository.existsById(idPost)) {
			throw new Exception("There is not a post registered with this id in the system");
		}
		Post post = this.postRepository.getOne(idPost);
		Comment comment = null;
		for (Comment comm: post.getComments()) {
			if (comm.getId() == idComment) {
				comment = comm;
			}
		}
		comment.setComment(newComment.getComment());
		comment.setUsersMentioned(newComment.getUsersMentioned());
		this.postRepository.save(post);
		return comment;
	}
	
	public Post setVisible(Long id) throws Exception {
		if (!this.postRepository.existsById(id)) {
			throw new Exception("This id is not registered in the system.");
		}
		Post post;
		post = this.postRepository.getOne(id);
		post.setNumberOfComplaints(post.getNumberOfComplaints() + 1);
		if (post.getNumberOfComplaints() == 5) {
			boolean visible = !post.isVisible();
			post.setVisible(visible);
		}
		return this.postRepository.save(post);
	}

	public List<Post> postsByType(String type) {
		PostTypes postType = PostTypes.valueOf(type);
		return this.postRepository.postsByType(postType);
	}
}
