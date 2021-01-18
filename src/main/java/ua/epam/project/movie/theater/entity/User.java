package ua.epam.project.movie.theater.entity;

public class User implements InterfaceUser {
    private int id;
    private String email;
    private String password;
    private int roleId;

    private User (UserBuilder userBuilder) {
        this.id = userBuilder.id;
        this.email = userBuilder.email;
        this.password = userBuilder.password;
        this.roleId = userBuilder.roleId;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public int getRoleId() {
        return roleId;
    }

    static class UserBuilder {
        private int id;
        private String email;
        private String password;
        private int roleId;
        protected UserBuilder() {
        }

        public User build() {
            return new User(this);
        }
        public UserBuilder id(int id) {
            this.id = id;
            return this;
        }
        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }
        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }
        public UserBuilder roleId(int roleId) {
            this.roleId = roleId;
            return this;
        }
    }
}
