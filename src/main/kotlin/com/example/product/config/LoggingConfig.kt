package com.example.product.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.CommonsRequestLoggingFilter

// リクエストのメソッド・パス・クエリを INFO レベルでログ出力する設定
@Configuration
class LoggingConfig {

    @Bean
    fun requestLoggingFilter(): CommonsRequestLoggingFilter =
        CommonsRequestLoggingFilter().apply {
            setIncludeQueryString(true)
            setIncludeClientInfo(false)
            setIncludeHeaders(false)
            setMaxPayloadLength(0)
        }
}
