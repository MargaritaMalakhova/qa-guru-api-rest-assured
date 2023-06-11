package guru.qa.models.lombok;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

        Integer id;
        String email;
        String first_name;
        String last_name;
        String avatar;
}
