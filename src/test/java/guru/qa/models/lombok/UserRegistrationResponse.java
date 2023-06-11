package guru.qa.models.lombok;

import lombok.Data;

@Data
public class UserRegistrationResponse {

    String token;
    Integer id;
}
