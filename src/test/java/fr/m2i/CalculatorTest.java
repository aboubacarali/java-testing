package fr.m2i;

import fr.m2i.test.Calculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CalculatorTest {

    @Test
    // Cette annotation rend le nom du test plus lisible dans les rapports.
    @DisplayName("Devrait retourner 5 quand on additionne 2 et 3")
    public void shouldReturn5_whenAdding2And3() {
        // Arrange : Préparer les objets et les données nécessaires.
        Calculator calculator = new Calculator();
        int a = 2;
        int b = 3;

        // Act : Appeler la méthode que l'on veut tester.
        int result = calculator.add(a, b);

        // Assert : Vérifier que le résultat est bien celui attendu.
        Assertions.assertEquals(5, result, "Le résultat de 2+3 devrait être 5");
    }

    @ParameterizedTest
// Chaque chaîne est un jeu de données : 'input1, input2, expectedResult'
    @CsvSource({
            "1, 1, 2",
            "5, 5, 10",
            "-1, 1, 0",
            "100, 200, 300"
    })
    @DisplayName("Additionne correctement plusieurs jeux de données")
    void shouldAddCorrectly_forMultipleValues(int a, int b, int expected) {
        Calculator calculator = new Calculator();
        int result = calculator.add(a, b);
        Assertions.assertEquals(expected, result);
    }
}
