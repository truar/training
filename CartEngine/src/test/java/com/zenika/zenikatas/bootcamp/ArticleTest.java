package com.zenika.zenikatas.bootcamp;

import com.zenika.zenikatas.bootcamp.data.Article;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class ArticleTest {

    @Test
    public void shouldReturnTheTotalNoTaxPrice() {
        Article article = new Article(2);
        assertThat(article.getTotal()).isEqualTo(2);
        article = new Article(2, 2);
        assertThat(article.getTotal()).isEqualTo(4);
    }

}