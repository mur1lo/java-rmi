package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import java.util.HashMap;

public class BibliotecaImpl extends UnicastRemoteObject implements Biblioteca {

    private List<Livro> livros;
    private Map<String, List<Livro>> reservas2;

    public BibliotecaImpl() throws RemoteException {
        super();
        this.livros = new ArrayList<>();
        this.reservas2 = new HashMap<>();
        livros.add(new Livro("1", "O Senhor dos Anéis"));
        livros.add(new Livro("2", "A Torre Negra"));
        livros.add(new Livro("3", "O Hobbit"));
    }

    @Override
    public List<String> listarLivrosDisponiveis() throws RemoteException {
        List<String> livrosReservados = reservas2.values().stream()
                .flatMap(List::stream)
                .map(Livro::getNome)
                .collect(Collectors.toList());

        return livros.stream()
                .filter(livro -> !livrosReservados.contains(livro.getNome()))
                .map(Livro::getNome)
                .collect(Collectors.toList());
    }

    @Override
    public boolean reservarLivro(String titulo, String usuario) throws RemoteException {
        if (livros.stream().noneMatch(l -> l.getNome().equals(titulo))) {
            return false;
        }

        String usuarioKey = usuario.toLowerCase();
        if (livroJaReservado(titulo)) {
            return false;
        }

        List<Livro> livrosDoUsuario = reservas2.computeIfAbsent(usuarioKey, k -> new ArrayList<>());

        Livro livro = livros.stream()
                .filter(l -> l.getNome().equals(titulo))
                .findFirst().get();
        livrosDoUsuario.add(livro);

        return true;
    }

    @Override
    public boolean devolverLivro(String titulo, String usuario) throws RemoteException {
        String usuarioKey = usuario.toLowerCase();

        if (reservas2.containsKey(usuarioKey)) {
            List<Livro> livrosDoUsuario = reservas2.get(usuarioKey);
            boolean removido = livrosDoUsuario.removeIf(livro -> livro.getNome().equals(titulo));

            if (livrosDoUsuario.isEmpty()) {
                reservas2.remove(usuarioKey);
            }

            return removido;
        }

        return false;
    }

    @Override
    public List<String> listarLivrosUsuario(String usuario) throws RemoteException {
        String usuarioKey = usuario.toLowerCase();

        if (reservas2.containsKey(usuarioKey)) {
            return reservas2.get(usuarioKey).stream()
                    .map(Livro::getNome)
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    private boolean livroJaReservado(String titulo) {
        return reservas2.values().stream()
                .flatMap(List::stream)
                .anyMatch(l -> l.getNome().equals(titulo));
    }
}