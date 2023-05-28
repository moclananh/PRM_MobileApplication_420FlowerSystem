package fpt.edu.vn.a420flowershop.Models;

public class UserModel {
    String username;
    String email;
    String phone;
    String password;
    String re_password;

    public UserModel(){

    }

    public UserModel(String username, String email, String phone, String password, String re_password) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.re_password = re_password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRe_password() {
        return re_password;
    }

    public void setRe_password(String re_password) {
        this.re_password = re_password;
    }
}
