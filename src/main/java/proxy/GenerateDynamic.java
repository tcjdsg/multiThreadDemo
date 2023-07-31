package proxy;
import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 *GenerateDynamic
 * 生成代理类字节码的类
 *@author: tanggao
 *@date： ：2017-9-27 下午5:36:54
 *@version 1.0
 */
public class GenerateDynamic {

    public static void main(String[] args) {
       /* People p = new Student();

        DynamicProxyHandler handler = new DynamicProxyHandler();

        People proxy = (People) Proxy.newProxyInstance(handler.getClass()
                .getClassLoader(), p.getClass().getInterfaces(), handler);

        proxy.sayBye("tanggao");*/

        createProxyClassFile();
    }

    private static void createProxyClassFile() {
        String name = "ProxySubject";
        byte[] data = ProxyGenerator.generateProxyClass(name,
                new Class[] { SmsServiceImpl.class });
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(name + ".class");
            System.out.println((new File("hello")).getAbsolutePath());
            out.write(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (null != out)
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
