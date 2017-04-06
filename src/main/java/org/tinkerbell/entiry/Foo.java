package org.tinkerbell.entiry;

/**
 * Created by nn_liu on 2017/4/6.
 */
public class Foo {
    private String name;
    private int age;

    public Foo(){
        this.name = "default_foo";
        this.age = 0;
    }

    public Foo(String name) {
        this.name = name;
        this.age = 0;
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
