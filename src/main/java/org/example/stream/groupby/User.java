package org.example.stream.groupby;

import lombok.Data;

import java.util.Objects;

@Data
public class User implements Comparable {
    private final int id;
    private final String name;
    private final String age;

    public User(Builder builder) {
        this.age = builder.age;
        this.name = builder.name;
        this.id = builder.id;
    }

    /*while overriding make sure u r in sync for both hashcode and equals method else it will be a problem while using has collection*/
    /*comparing based on name field only*/
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

    /*Comparing based on name field only*/
    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    /*This one for the sort method for custom class*/
    @Override
    public int compareTo(Object o) {
        User u = (User) o;
        return this.id - u.getId();
    }

    /*Custom builder for class*/
    public static class Builder {
        private int id;
        private String name;
        private String age;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAge(String age) {
            this.age = age;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
