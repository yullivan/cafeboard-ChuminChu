package cafeboard.comment;

import cafeboard.AcceptanceTest;
import cafeboard.board.BoardRequest;
import cafeboard.board.BoardResponse;
import cafeboard.post.PostRequest;
import cafeboard.post.PostResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentTest extends AcceptanceTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 댓글생성테스트() {
        //게시판 하나 있어야하고
        BoardResponse 자유게시판 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new BoardRequest("자유게시판"))
                .when()
                .post("/boards")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(BoardResponse.class);

        assertThat(자유게시판.boardName()).isNotNull();

        //게시글도 하나 있어야 하고
        PostResponse 게시글 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new PostRequest("안녕하세요", "인사", 자유게시판.id()))
                .when()
                .post("/posts")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(PostResponse.class);


        CommentResponse commentResponse = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new CommentRequest("댓글내용", "이름", 게시글.id()))
                .when()
                .post("/comments")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(CommentResponse.class);

        assertThat(commentResponse.content()).isNotNull();
        assertThat(commentResponse.name()).isNotNull();
        assertThat(commentResponse.id()).isNotNull();
    }

        @Test
        void 댓글수정테스트() {
            BoardResponse 자유게시판 = RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .body(new BoardRequest("자유게시판"))
                    .when()
                    .post("/boards")
                    .then().log().all()
                    .statusCode(200)
                    .extract()
                    .as(BoardResponse.class);

            assertThat(자유게시판.boardName()).isNotNull();

            PostResponse 게시글 = RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .body(new PostRequest("안녕하세요", "인사", 자유게시판.id()))
                    .when()
                    .post("/posts")
                    .then().log().all()
                    .statusCode(200)
                    .extract()
                    .as(PostResponse.class);


            CommentResponse 댓글 = RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .body(new CommentRequest("댓글내용", "이름", 게시글.id()))
                    .when()
                    .post("/comments")
                    .then().log().all()
                    .statusCode(200)
                    .extract()
                    .as(CommentResponse.class);

            RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .body(new CommentRequest("수정된 댓글내용", "이름", 게시글.id()))
                    .pathParam("commentId", 댓글.id())
                    .when()
                    .put("/comments/{commentId}")
                    .then().log().all()
                    .statusCode(200)
                    .extract()
                    .as(CommentResponse.class);
    }

    @Test
    void 댓글삭제테스트() {
        BoardResponse 자유게시판 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new BoardRequest("자유게시판"))
                .when()
                .post("/boards")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(BoardResponse.class);

        assertThat(자유게시판.boardName()).isNotNull();

        PostResponse 게시글 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new PostRequest("안녕하세요", "인사", 자유게시판.id()))
                .when()
                .post("/posts")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(PostResponse.class);


        CommentResponse 댓글 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new CommentRequest("댓글내용", "이름", 게시글.id()))
                .when()
                .post("/comments")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(CommentResponse.class);

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("commentId", 댓글.id())
                .when()
                .delete("/comments/{commentId}")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(CommentResponse.class);

    }

    @Test
    void 댓글_예외처리() {
        BoardResponse 자유게시판 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new BoardRequest("자유게시판"))
                .when()
                .post("/boards")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(BoardResponse.class);

        PostResponse 게시글 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new PostRequest("안녕하세요", "인사", 자유게시판.id()))
                .when()
                .post("/posts")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(PostResponse.class);

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new CommentRequest(
                        "댓글내용이 300자를 넘어가면 에러나게 된다.                                                      댓글내용이 300자를 넘어가면 에러나게 된다.                                                      댓글내용이 300자를 넘어가면 에러나게 된다.                                                      댓글내용이 300자를 넘어가면 에러나게 된다.                                                      ",
                        "이름", 게시글.id()))
                .when()
                .post("/comments")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    void 댓글_예외처리_이름() {
        BoardResponse 자유게시판 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new BoardRequest("자유게시판"))
                .when()
                .post("/boards")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(BoardResponse.class);

        PostResponse 게시글 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new PostRequest("안녕하세요", "인사", 자유게시판.id()))
                .when()
                .post("/posts")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(PostResponse.class);


        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new CommentRequest(
                        "댓글내용이 300자를 넘어가면 에러나게 된다.",
                        "이름은 10글자를 넘으면 에러나게 된다. 10글자를 넘으면 안된다. 댓글의 이름은 10글자를 넘어가면 안됩니다.",
                        게시글.id()))
                .when()
                .post("/comments")
                .then().log().all()
                .statusCode(400);
    }
}
