package generalJavaQuestions.commonQuestions.desingPattern;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


@ToString
class User {

    private String name;
    private String email;
    private int age;
    private String city;
    private String role;
    private boolean active;

    private User(Builder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.age = builder.age;
        this.city = builder.city;
        this.role = builder.role;
        this.active = builder.active;
    }

    public static class Builder {

        private String name;
        private String email;
        private int age;
        private String city;
        private String role;
        private boolean active;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder role(String role) {
            this.role = role;
            return this;
        }
        public Builder active(boolean active){
            this.active =  active;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}

@Slf4j
class TestBuilder{
    public static void main(String[] args) {
        User builderInstance1 = new User.Builder()
                .name("John")
                .email("john@gmail.com")
                .age(25)
                .city("Bangalore")
                .role("Developer")
                .active(true)
                .build();

        log.info("The instance with all fields {}", builderInstance1);

        User builderInstance2 = new User.Builder()
                .name("John")
                .email("john@gmail.com")
                .age(25)
                .build();
        log.info("The instance with only 3 fields {}", builderInstance2);
    }
}
