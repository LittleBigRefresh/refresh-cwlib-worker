package refresh.workers.cwlib;

import cwlib.CwlibConfiguration;

public class Main {
    public static void main(String[] args) {
        System.out.print(CwlibConfiguration.JAR_DIRECTORY.getAbsolutePath());
    }
}