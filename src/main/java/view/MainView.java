package view;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;


public class MainView {
    private static Scanner scanner;

    public static void main(String[] args) {
        API api = API.getInstance();
        api.setPriority(1);
        api.start();
    }

}
