package guru.qa.models.lombok;

import lombok.Data;

@Data
public class RegistrationBodyLombokModel {

    /*{
            "email": "eve.holt@reqres.in",
                "password": "pistol"
        }*/
    String email, password;
}
