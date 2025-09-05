package server;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
    public static void main(String[] args) {
        try {
            Biblioteca obj = new BibliotecaImpl();

            Registry registry = LocateRegistry.createRegistry(1099);

            registry.rebind("Biblioteca", obj);

            System.out.println("Servidor RMI pronto para aceitar conexões...");
        } catch (Exception e) {
            System.err.println("Erro no servidor: " + e.toString());
            e.printStackTrace();
        }
    }
}
