package cafeboard;

import cafeboard.board.BoardRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BoardTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 게시판생성테스트() {
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new BoardRequest("자유게시판"))
                .when()
                .post("/boards")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 게시판조회테스트() {
        RestAssured
                .given().log().all()
                .when()
                .get("/boards")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 게시판수정테스트() {
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new BoardRequest("지유게시판"))
                .when()
                .post("/boards")
                .then().log().all()
                .statusCode(200);

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new BoardRequest("자유게시판"))
                .pathParam("boardId", "3")
                .when()
                .put("/boards/{boardId}")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 게시판삭제테스트() {
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new BoardRequest("자유게시판"))
                .when()
                .post("/boards")
                .then().log().all()
                .statusCode(200);
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("boardId", "1")
                .when()
                .delete("/boards/{boardId}")
                .then().log().all()
                .statusCode(200);
    }
}
