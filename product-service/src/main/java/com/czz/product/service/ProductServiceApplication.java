package com.czz.product.service;

import com.czz.product.api.service.IProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

@SpringBootApplication
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
        main();
    }

    private static void main() {
        IProductService productService = (IProductService)rpc(IProductService.class);
    }

    public static Object rpc(final Class clazz) {
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket = new Socket("127.0.0.1",8080);
                String apiClassName = clazz.getName();
                String methodName = method.getName();
                Class[] parameterTypes = method.getParameterTypes();

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeUTF(apiClassName);
                objectOutputStream.writeUTF(methodName);
                objectOutputStream.writeObject(parameterTypes);
                objectOutputStream.writeObject(args);
                objectOutputStream.flush();

                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Object o = objectInputStream.read();
                objectInputStream.close();
                objectOutputStream.close();
                socket.close();
                return o;
            }
        });
    }

}
