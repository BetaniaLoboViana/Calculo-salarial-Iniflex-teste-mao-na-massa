import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

class Pessoa {
    String nome;
    LocalDate dataNascimento;

    public Pessoa(String nome, LocalDate dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }
}

class Funcionario extends Pessoa {
    BigDecimal salario;
    String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }
}

class Main { 
    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>();

        // 3.1 - Adicionando funcionários
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloisa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        // 3.2 - Remover funcionário "João"
        funcionarios.removeIf(func -> func.nome.equals("João"));

        // 3.3 - Imprimir funcionários com formatação
        System.out.println("\nLista de Funcionários:");
        imprimirFuncionarios(funcionarios);

        // 3.4 - Aumento de 10% no salário
        funcionarios.forEach(f -> f.salario = f.salario.multiply(new BigDecimal("1.10")));

        // 3.5 - Agrupar funcionários por função
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
            .collect(Collectors.groupingBy(f -> f.funcao));

        // 3.6 - Imprimir funcionários agrupados por função
        System.out.println("\nFuncionários Agrupados por Função:");
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("\n" + funcao + ":");
            lista.forEach(f -> System.out.printf("%s - R$ %,.2f\n", f.nome, f.salario));
        });

        // 3.8 - Funcionários com aniversário em outubro e dezembro
        System.out.println("\nFuncionários que fazem aniversário em outubro e dezembro:");
        funcionarios.stream()
            .filter(f -> f.dataNascimento.getMonthValue() == 10 || f.dataNascimento.getMonthValue() == 12)
            .forEach(f -> System.out.println(f.nome + " - " + formatarData(f.dataNascimento)));

        // 3.9 - Funcionário com maior idade
        Funcionario maisVelho = funcionarios.stream()
            .min(Comparator.comparing(f -> f.dataNascimento))
            .orElse(null);

        if (maisVelho != null) {
            int idade = Period.between(maisVelho.dataNascimento, LocalDate.now()).getYears();
            System.out.println("\nFuncionário mais velho: " + maisVelho.nome + " - " + idade + " anos");
        }

        // 3.10 - Ordenar funcionários por nome
        funcionarios.sort(Comparator.comparing(f -> f.nome));
        System.out.println("\nFuncionários em ordem alfabética:");
        imprimirFuncionarios(funcionarios);

        // 3.11 - Total dos salários
        BigDecimal totalSalarios = funcionarios.stream()
            .map(f -> f.salario)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.printf("\nTotal dos salários: R$ %,.2f\n", totalSalarios);

        // 3.12 - Quantidade de salários mínimos
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("\nQuantidade de salários mínimos por funcionário:");
        funcionarios.forEach(f -> {
            BigDecimal qtdSalarios = f.salario.divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.printf("%s ganha %.2f salários mínimos\n", f.nome, qtdSalarios);
        });
    }

    // Método para formatar data
    public static String formatarData(LocalDate data) {
        return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    // Método para imprimir funcionários formatados
    public static void imprimirFuncionarios(List<Funcionario> funcionarios) {
        for (Funcionario f : funcionarios) {
            System.out.printf("%s - %s - R$ %,.2f - %s\n",
                f.nome, formatarData(f.dataNascimento), f.salario, f.funcao);
        }
    }
}
