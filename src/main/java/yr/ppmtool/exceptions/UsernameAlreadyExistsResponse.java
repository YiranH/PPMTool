package yr.ppmtool.exceptions;

public class UsernameAlreadyExistsResponse {
    private String userName;

    public UsernameAlreadyExistsResponse(String username) {
        this.userName = username;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
