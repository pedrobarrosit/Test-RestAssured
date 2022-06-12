//-pacote
package petstore;

// - biblioteca

import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

// - classe
public class Pet {
    // - Atributo
        String uri ="https://petstore.swagger.io/v2/pet";

    // -  Metodo é Função
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));

    }
     // incluir - Post
    @Test(priority = 1)  //indentificar um metodo ou função como um test para o testNg
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("db/pet1.json");

        // Sintax Gerkin
        // Dado , Quando, Então
        // Given , When , Then

       given()//Dado
               .contentType("application/json") //comun em PAI rest -antigo era "Txt/xml"
               .log() .all() //registrar
               .body(jsonBody)

       .when()//Quando
               .post(uri)

       .then()//Então
               .log().all()
               .statusCode(200)
               .body("name",is("tamara"))
               .body("category.name",is("XD1255596856"))
               .body("tags.name",contains("sta"))
       ;

    }
    @Test(priority = 2)
    public void consultaPet(){
        String petId = "1993200217";
        String token =
        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri +"/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("name",is("tamara"))
                .body("status",is("available"))
                .body("category.name",is("XD1255596856"))
                .body("tags.name",contains("sta"))
        .extract()
                .path("category.name")
        ;
            System.out.println("Token é " + token);
    }
    @Test(priority = 3)
    public void alterarPet() throws IOException {
        String jsonBody = lerJson("db/pet2.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .put(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name",is("tamara"))
                .body("status",is("sold"))
        ;
    }

    @Test(priority = 4)
    public void excluirPet(){
        String petId ="1993200217";


        given()
                .contentType("application/json")
                .log().all()
        .when()
                .delete(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("code",is(200))
                .body(  "type",is("unknown"))
                .body(  "message",is(petId))
        ;
    }
}
