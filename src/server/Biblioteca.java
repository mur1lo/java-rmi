package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Biblioteca extends Remote {

    List<String> listarLivrosDisponiveis() throws RemoteException;

    boolean reservarLivro(String titulo, String usuario) throws RemoteException;

    boolean devolverLivro(String titulo, String usuario) throws RemoteException;

    List<String> listarLivrosUsuario(String usuario) throws RemoteException;

}
