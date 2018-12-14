package com.zenika.zenikatas.bootcamp;

import com.zenika.zenikatas.bootcamp.data.Article;
import com.zenika.zenikatas.bootcamp.tax.StatesTaxCalculator;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CartEngineTest {

    private CartEngine cartEngine = new CartEngine();

    @Test
    public void shouldCalculatePricesForCA() {
        StatesTaxCalculator country = StatesTaxCalculator.of("CA");
        Article article = new Article(300);
        assertThat(cartEngine.calculateTotalPrice(article, country)).isEqualTo(324.75);
        article = new Article(300, 2);
        assertThat(cartEngine.calculateTotalPrice(article, country)).isEqualTo(649.5);
    }

    @Test
    public void shouldCalculatePricesForTX() {
        StatesTaxCalculator country = StatesTaxCalculator.of("TX");
        Article article = new Article(23, 5);
        assertThat(cartEngine.calculateTotalPrice(article, country)).isEqualTo(122.19);
        article = new Article(2345, 22);
        assertThat(cartEngine.calculateTotalPrice(article, country)).isEqualTo(46592.22);
        article = new Article(334, 3);
        assertThat(cartEngine.calculateTotalPrice(article, country)).isEqualTo(1032.69);
    }

    @Test
    public void shouldCalculatePricesForNV() {
        StatesTaxCalculator country = StatesTaxCalculator.of("NV");
        Article article = new Article(21, 50);
        assertThat(cartEngine.calculateTotalPrice(article, country)).isEqualTo(1099.98);
    }

    @Test
    public void shouldCalculatePricesForUT() {
        StatesTaxCalculator country = StatesTaxCalculator.of("UT");
        Article article = new Article(1295, 44);
        assertThat(cartEngine.calculateTotalPrice(article, country)).isEqualTo(51750.66);
        article = new Article(2315, 9);
        assertThat(cartEngine.calculateTotalPrice(article, country)).isEqualTo(20035.98);
    }

    @Test
    public void shouldCalculatePricesForAL() {
        StatesTaxCalculator country = StatesTaxCalculator.of("AL");
        Article article = new Article(362, 27);
        assertThat(cartEngine.calculateTotalPrice(article, country)).isEqualTo(9453.41);
    }

}
