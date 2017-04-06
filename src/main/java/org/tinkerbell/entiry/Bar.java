package org.tinkerbell.entiry;

/**
 * Created by nn_liu on 2017/4/6.
 */
public class Bar {
    private String name;
    private int age;

    public Bar(){
        this.name = "default_bar";
        this.age = 0;
    }

    public Bar(int age) {
        this.name = "bar";
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
