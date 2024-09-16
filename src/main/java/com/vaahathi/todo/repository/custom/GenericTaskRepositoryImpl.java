package com.vaahathi.todo.repository.custom;

import com.vaahathi.todo.models.misc.ScheduledTasksResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@Repository
public class GenericTaskRepositoryImpl implements GenericTaskRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ScheduledTasksResponse> GetScheduledTasks(UUID ownerId, String category) {
        // Updated query without JSON aggregation
        String query = "SELECT scheduled_date as scheduledDate, id, is_scheduled, is_important, is_urgent, purpose, owner_id " +
                "FROM (" +
                "    SELECT scheduled_date, id, is_scheduled, is_important, is_urgent, purpose, owner_id " +
                "    FROM appointment WHERE owner_id = :ownerId AND category = :category " +
                "    UNION ALL " +
                "    SELECT scheduled_date, id, is_scheduled, is_important, is_urgent, purpose, owner_id " +
                "    FROM call WHERE owner_id = :ownerId AND category = :category " +
                "    UNION ALL " +
                "    SELECT scheduled_date, id, is_scheduled, is_important, is_urgent, purpose, owner_id " +
                "    FROM mail WHERE owner_id = :ownerId AND category = :category " +
                "    UNION ALL " +
                "    SELECT scheduled_date, id, is_scheduled, is_important, is_urgent, purpose, owner_id " +
                "    FROM payment WHERE owner_id = :ownerId AND category = :category " +
                "    UNION ALL " +
                "    SELECT scheduled_date, id, is_scheduled, is_important, is_urgent, purpose, owner_id " +
                "    FROM to_do WHERE owner_id = :ownerId AND category = :category " +
                "    UNION ALL " +
                "    SELECT scheduled_date, id, is_scheduled, is_important, is_urgent, purpose, owner_id " +
                "    FROM event WHERE owner_id = :ownerId AND category = :category " +
                "    UNION ALL " +
                "    SELECT scheduled_date, id, is_scheduled, is_important, is_urgent, purpose, owner_id " +
                "    FROM buy_sell WHERE owner_id = :ownerId AND category = :category " +
                ") as tasks " +
                "ORDER BY scheduled_date DESC, is_urgent DESC, is_important DESC";

        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("ownerId", ownerId);
        nativeQuery.setParameter("category", category);

        // Get results from query
        List<Object[]> resultList = nativeQuery.getResultList();

        // Map result to ScheduledTasksResponse
        return resultList.stream().map(obj -> new ScheduledTasksResponse(
                ((Timestamp) obj[0]).toLocalDateTime(),  // scheduledDate
                // Create a map of task attributes from the query result
                Map.of(
                        "id", obj[1].toString(),
                        "isScheduled", obj[2],
                        "isImportant", obj[3],
                        "isUrgent", obj[4],
                        "purpose", obj[5],
                        "ownerId", obj[6].toString()
                )
        )).collect(Collectors.toList());
    }
}

