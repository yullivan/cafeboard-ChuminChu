package cafeboard.post;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/posts")
    public PostResponse createPost(@Valid @RequestBody PostRequest postRequest){
        return postService.save(postRequest);
    }

    @GetMapping("/posts")
    public List<PostResponse> getPosts(@RequestParam(required = false) Long boardId){
        return postService.findByBoardId(boardId);
    }

    @GetMapping("/posts/{postId}")
    public PostResponse getPost(@PathVariable Long postId){
        return postService.findById(postId);
    }

    @PutMapping("/posts/{postId}")
    public PostResponse putPost(@PathVariable Long postId, @Valid @RequestBody PostRequest postRequest){
        return postService.updatePost(postId,postRequest);
    }

    @DeleteMapping("/posts/{postId}")
    public void deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
    }
}
