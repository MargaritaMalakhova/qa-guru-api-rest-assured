package guru.qa.models.pojo;

public class RegistrationResponsePojoModel {
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    String token;
    Integer id;


}
