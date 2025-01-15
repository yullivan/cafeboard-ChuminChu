package cafeboard.user;

import cafeboard.AcceptanceTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest extends AcceptanceTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 사용자_생성() {
        UserResponse 사용자1 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new UserRequest("추민영", "chuchu", "123", "chu@naver.com"))
                .when()
                .post("/users/infos")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(UserResponse.class);

        assertThat(사용자1).isNotNull();
    }

    @Test
    void 사용자_프로필조회_이름만조회() {
        UserResponse 사용자1 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new UserRequest("추민영", "chuchu", "123", "chu@naver.com"))
                .when()
                .post("/users/infos")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(UserResponse.class);

        UserResponse 추민영 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new UserProfileRequest(사용자1.id(), 사용자1.userName()))
                .when()
                .get("/users")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(UserResponse.class);

        assertThat(추민영.userName()).isNotNull();
    }

    @Test
    void 사용자정보_전체조회() {
        UserResponse 사용자1 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new UserRequest("추민영", "chuchu", "123", "chu@naver.com"))
                .when()
                .post("/users/infos")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(UserResponse.class);

        UserResponse 사용자 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .queryParam("userId",사용자1.id())
                .when()
                .get("/users/profiles")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(UserResponse.class);

        assertThat(사용자.userId()).isEqualTo(사용자1.userId());
        assertThat(사용자.userName()).isEqualTo(사용자1.userName());
        assertThat(사용자.userPassword()).isEqualTo(사용자1.userPassword());
    }

    @Test
    void 사용자정보_수정() {
        String userName = "수정 전 이름";
        String updatedUserName = "수정 후 이름";

        UserResponse 수정전사용자 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new UserRequest(userName, "chuchu", "123", "chu@naver.com"))
                .when()
                .post("/users/infos")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(UserResponse.class);

        UserResponse 수정후사용자 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("userId", 수정전사용자.id())
                .body(new UserRequest(updatedUserName, "chuchu", "123", "chu@naver.com"))
                .when()
                .put("/users/{userId}")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(UserResponse.class);

        UserResponse 사용자 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .queryParam("userId", 수정후사용자.id())
                .when()
                .get("/users/profiles")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(UserResponse.class);

        assertThat(사용자.userName()).isEqualTo(updatedUserName);
    }

    @Test
    void 사용자정보_삭제() {
        UserResponse 삭제전사용자 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new UserRequest("추민영", "chuchu", "123", "chu@naver.com"))
                .when()
                .post("/users/infos")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(UserResponse.class);

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("userId", 삭제전사용자.id())
                .when()
                .delete("/users/{userId}")
                .then().log().all()
                .statusCode(200);

    }
}
