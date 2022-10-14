package basic;

/**
 * @date 2022-10-14
 **/
public class SuperTest {

    static class Fruit {

        Fruit() {
        }

        Fruit(String color) {
        }
    }

    static class Apple extends Fruit {
        Apple() {
            super();
        }

        Apple(String color) {
            super(color);
        }
    }
}
