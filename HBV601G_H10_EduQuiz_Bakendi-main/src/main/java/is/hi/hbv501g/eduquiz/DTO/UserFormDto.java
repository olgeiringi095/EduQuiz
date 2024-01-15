package is.hi.hbv501g.eduquiz.DTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserFormDto {
    @Size(min = 3, max = 50, message = "Vinsamlegast fylltu notendanafn sem er á bilinu 3-50")
    @NotEmpty(message = "Notendanafn má ekki vera autt")
    private String username;

    @Size(min = 5, message = "Vinsamlegast fylltu lykilorð sem er að minnsta kosti 5 stafir")
    @NotEmpty(message = "Lykilorð má ekki vera tómt")
    private String password;

    @Size(min = 3, message = "Vinsamlegast fylltu netfang sem er að minnsta kosti 3 stafir")
    @NotEmpty(message = "Netfang má ekki vera tómt")
    private String email;

    public UserFormDto() {}

    public UserFormDto(@NotEmpty String username, @NotEmpty String password, @NotEmpty String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
