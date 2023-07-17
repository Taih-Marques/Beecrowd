public enum Teste {
    CARRO("Carro", 1),
    MOTO("Moto", 2);

    private String nome;
    private int id;

    private Teste(String nome, int id) {
        this.nome = nome;
        this.id = id;
    }
}