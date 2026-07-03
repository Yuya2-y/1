package com.example.test2.repository;

import com.example.test2.dto.AgentUsageSummaryDto;
import com.example.test2.dto.ChatHistorySummaryDto;
import com.example.test2.entity.ChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {
    List<ChatHistory> findAllByOrderByCreatedAtDesc();

    List<ChatHistory> findByUserUsernameOrderByCreatedAtDesc(String username);

    ChatHistory findByIdAndUserUsername(Long id, String username);

    @Query("SELECT new com.example.test2.dto.ChatHistorySummaryDto(ch.user.username, COUNT(ch), AVG(ch.confidenceScore)) " +
           "FROM ChatHistory ch WHERE ch.user.username = :username GROUP BY ch.user.username")
    ChatHistorySummaryDto findSummaryByUsername(@Param("username") String username);

    @Query("SELECT new com.example.test2.dto.AgentUsageSummaryDto(ch.adoptedAgent, COUNT(ch)) " +
           "FROM ChatHistory ch WHERE ch.user.username = :username GROUP BY ch.adoptedAgent ORDER BY COUNT(ch) DESC")
    List<AgentUsageSummaryDto> summarizeAgentUsageByUsername(@Param("username") String username);
}

