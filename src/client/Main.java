package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import server.Biblioteca;

public class Main {
    public static void main(String[] args) {
        String host = "localhost";

        try {
            Registry registry = LocateRegistry.getRegistry(host);

            Biblioteca stub = (Biblioteca) registry.lookup("Biblioteca");

            System.out.println(stub.listarLivrosDisponiveis());
            System.out.println(stub.reservarLivro("A Torre Negra", "Murilo"));
            System.out.println(stub.reservarLivro("O Hobbit", "Murilo2"));
            System.out.println(stub.listarLivrosDisponiveis());
            System.out.println(stub.devolverLivro("A Torre Negra", "Murilo"));
            System.out.println(stub.listarLivrosDisponiveis());
        } catch (Exception e) {
            System.err.println("Erro no cliente: " + e.toString());
            e.printStackTrace();
        }
    }
}