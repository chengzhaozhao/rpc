package com.czz.product.service;

import com.czz.product.api.service.IProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceApplicationTests {

    @Test
    public void contextLoads() {
        try {
            ServerSocket serverSocket = new ServerSocket(8088);
            while (true){
                Socket socket = serverSocket.accept();
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

                String apiClassName = objectInputStream.readUTF();
                String methodName = objectInputStream.readUTF();
                Class[] parameterTypes = (Class[]) objectInputStream.readObject();
                Object[] argsMethod = (Object[]) objectInputStream.readObject();

                Class clazz = null;

                if (apiClassName.equals(IProductService.class.getName())) {
                    clazz = IProductService.class;
                }

                Method method = clazz.getMethod(methodName,parameterTypes);

                Object invoke = method.invoke(clazz.newInstance(),argsMethod);

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(invoke);

                objectOutputStream.flush();
                objectOutputStream.close();
                objectInputStream.close();
                socket.close();
             }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }

}
