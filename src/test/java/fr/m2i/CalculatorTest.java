package fr.m2i;

import fr.m2i.test.Calculator;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

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
}
