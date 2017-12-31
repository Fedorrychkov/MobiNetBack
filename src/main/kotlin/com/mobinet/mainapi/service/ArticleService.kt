package com.mobinet.mainapi.service

import com.mobinet.mainapi.model.Article
import com.mobinet.mainapi.repository.ArticleRepository
import org.springframework.stereotype.Component

@Component
class ArticleService(private val articleRepository: ArticleRepository) {
    interface Resp {
        class Success(val msg: List<Article>): Resp
        class Error(): Resp
    }
    fun greet(name: String): Resp {
        return  if (name.equals("R31"))
            Resp.Error()
        else
            Resp.Success(articleList())
    }

    fun articleList(): List<Article> = articleRepository.findAll()

}