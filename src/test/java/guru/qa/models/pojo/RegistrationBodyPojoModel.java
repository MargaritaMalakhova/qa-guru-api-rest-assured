package guru.qa.models.pojo;

public class RegistrationBodyPojoModel {
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*{
            "email": "eve.holt@reqres.in",
                "password": "pistol"
        }*/
    String email, password;


}
