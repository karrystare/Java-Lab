package User;

public final class User{
    private final String username;
    private String password, firstName, lastName, phone, email;
    
    public User(String username, String password, String firstName, String lastName, String phone, String email){
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }
    
    public User(String[] info){
        this.username = info[0];
        this.password = info[1];
        this.firstName = info[2];
        this.lastName = info[3];
        this.phone = info[4];
        this.email = info[5];        
    }
    
    public User(String username){
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName(){
        return firstName + " " + lastName;
    }
    
    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public void printInfo(){
        System.out.println("Username: " + username);
        System.out.println("Name: " + this.getName());
        System.out.println("Phone Number: " + phone);
        System.out.println("Email: " + email);
    }
    
    public boolean validatePassword(String password){
        return this.password.equals(password);
    }
    
    public String[] getInfo(){
        String[] info = new String[5];
        info[0] = password;
        info[1] = firstName;
        info[2] = lastName;
        info[3] = phone;
        info[4] = email;
        return info;
    }
    
    public void updateInfo(String[] newInfo){
        password = newInfo[0];
        firstName = newInfo[1];
        lastName = newInfo[2];
        phone = newInfo[3];
        email = newInfo[4];
    }
    
    @Override
    public boolean equals(Object user){
        return this.username.equals(((User)user).username);
    }
    
    @Override
    public String toString(){
        return username + ";" + password + ";" + firstName + ";" + lastName + ";" + phone + ";" + email;
    }
}
