package basic.serialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.io.*;

/**
 * @date 2022-9-25
 **/
public class SerializeTest {

    @Data
    @AllArgsConstructor
    static class Student implements Serializable {
        private Integer id;
        private String name;
        private transient String address;
    }

    @Test
    public void test1() throws IOException, ClassNotFoundException {
        Student student = new Student(1, "aaa", "bbb");

        // serialize
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(student);

        // deserialize
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        Student stu = (Student) ois.readObject();
        System.out.println(stu);
    }
}
