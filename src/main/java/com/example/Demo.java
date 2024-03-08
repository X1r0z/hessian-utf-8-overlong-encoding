package com.example;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Demo {
    public static void main(String[] args) throws Exception {
        Person p = new Person();
        p.setName("xiaoming");
        p.setAge(18);

        byte[] data = hessian2Serialize(p);
        byte[] data_overlong = hessian2SerializeWithOverlongEncoding(p);

        Files.write(Paths.get("data.ser"), data);
        Files.write(Paths.get("data_overlong.ser"), data_overlong);

        System.out.println(new String(data));
        System.out.println(new String(data_overlong));

        Person pp = (Person) hessian2Unserialize(data);
        Person pp_overlong = (Person) hessian2Unserialize(data_overlong);

        System.out.println(pp);
        System.out.println(pp_overlong);
    }

    public static byte[] hessian2Serialize(Object o) throws Exception {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        Hessian2Output output = new Hessian2Output(bao);
        output.getSerializerFactory().setAllowNonSerializable(true);
        output.writeObject(o);
        output.flush();
        return bao.toByteArray();
    }

    public static byte[] hessian2SerializeWithOverlongEncoding(Object o) throws Exception {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        Hessian2OutputWithOverlongEncoding output = new Hessian2OutputWithOverlongEncoding(bao);
        output.getSerializerFactory().setAllowNonSerializable(true);
        output.writeObject(o);
        output.flush();
        return bao.toByteArray();
    }

    public static Object hessian2Unserialize(byte[] data) throws Exception {
        Hessian2Input input = new Hessian2Input(new ByteArrayInputStream(data));
        Object obj = input.readObject();
        return obj;
    }
}
