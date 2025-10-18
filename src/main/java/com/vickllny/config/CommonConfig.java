package com.vickllny.config;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CommonConfig {

    @Bean
    public RedisChatMemoryStore redisChatMemoryStore(){
        return new RedisChatMemoryStore();
    }

    @Bean
    public ChatMemoryProvider chatMemoryProvider(){
        return id -> MessageWindowChatMemory.builder()
                .chatMemoryStore(redisChatMemoryStore())
                .id(id)
                .maxMessages(20)
                .build();
    }

    @Bean
    public EmbeddingStore<TextSegment> embeddingStore(){
        //加载文档
        final List<Document> documents = ClassPathDocumentLoader.loadDocuments("content");
        //构建向量数据库
        final InMemoryEmbeddingStore<TextSegment> store = new InMemoryEmbeddingStore<>();
        //构建一个EmbeddingStoreIngestor对象，完成文本数据切割、向量化、存储
        final EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .embeddingStore(store)
                .build();
        ingestor.ingest(documents);
        return store;
    }

    /**
     * 构建向量数据库检索对象
     */
    @Bean
    public ContentRetriever contentRetriever(){
        final EmbeddingStoreContentRetriever retriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore())
                .minScore(0.5)
                .maxResults(3)
                .build();

        return retriever;
    }
}
