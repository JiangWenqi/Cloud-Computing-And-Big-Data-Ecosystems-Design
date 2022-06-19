package es.upm.fi.cloud;

public class Test {

    public static void main(String[] args) {
        String line = "6,2022-03-16 06:03:41,2022-03-16 07:03:22,,14.66,,,265,263,0,51.17,0.0,0.5,0.0,0.0,0.3,51.97,,";
        String[] fields = line.split(",", -1);
        System.out.println(fields.length);
    }
}

