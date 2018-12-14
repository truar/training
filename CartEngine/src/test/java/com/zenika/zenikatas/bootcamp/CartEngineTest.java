package com.zenika.zenikatas.bootcamp;

import com.zenika.zenikatas.bootcamp.data.Article;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CartEngineTest {

    private CartEngine cartEngine = new CartEngine();

    @Test
    public void shouldCalculatePricesForCA() {
        String country = "CA";
        Article article = new Article(300);
        assertThat(cartEngine.calculateTotalPrice(article, country)).isEqualTo(324.75);
        article = new Article(300, 2);
        assertThat(cartEngine.calculateTotalPrice(article, country)).isEqualTo(649.5);
    }

    @Test
    public void shouldCalculatePricesForTX() {
        String country = "TX";
        Article article = new Article(23, 5);
        assertThat(cartEngine.calculateTotalPrice(article, country)).isEqualTo(122.19);
        article = new Article(2345, 22);
        assertThat(cartEngine.calculateTotalPrice(article, country)).isEqualTo(46592.22);
        article = new Article(334, 3);
        assertThat(cartEngine.calculateTotalPrice(article, country)).isEqualTo(1032.69);
    }

    @Test
    public void shouldCalculatePricesForNV() {
        String country = "NV";
        Article article = new Article(21, 50);
        assertThat(cartEngine.calculateTotalPrice(article, country)).isEqualTo(1099.98);
    }

    @Test
    public void shouldCalculatePricesForUT() {
        String country = "UT";
        Article article = new Article(1295, 44);
        assertThat(cartEngine.calculateTotalPrice(article, country)).isEqualTo(51750.66);
        article = new Article(2315, 9);
        assertThat(cartEngine.calculateTotalPrice(article, country)).isEqualTo(20035.98);
    }

    @Test
    public void shouldCalculatePricesForAL() {
        String country = "AL";
        Article article = new Article(362, 27);
        assertThat(cartEngine.calculateTotalPrice(article, country)).isEqualTo(9453.41);
    }

}
