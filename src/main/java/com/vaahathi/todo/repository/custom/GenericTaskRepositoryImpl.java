package com.vaahathi.todo.repository.custom;

import com.vaahathi.todo.models.misc.ScheduledTasksResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Repository
public class GenericTaskRepositoryImpl implements GenericTaskRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ScheduledTasksResponse> GetScheduledTasks(UUID ownerId, String category) {
        String query = "SELECT scheduled_date as scheduledDate, " +
                "json_agg(json_build_object(" +
                "'id', id, " +
                "'isScheduled', is_scheduled, " +
                "'isImportant', is_important, " +
                "'isUrgent', is_urgent, " +
                "'purpose', purpose, " +
                "'ownerId', owner_id " +
                ")) as tasks " +
                "FROM (" +
                "    SELECT id, is_scheduled, scheduled_date, is_important, is_urgent, purpose, owner_id " +
                "    FROM appointment WHERE owner_id = :ownerId AND category = :category " +
                "    UNION ALL " +
                "    SELECT id, is_scheduled, scheduled_date, is_important, is_urgent, purpose, owner_id " +
                "    FROM call WHERE owner_id = :ownerId AND category = :category " +
                "    UNION ALL " +
                "    SELECT id, is_scheduled, scheduled_date, is_important, is_urgent, purpose, owner_id " +
                "    FROM mail WHERE owner_id = :ownerId AND category = :category " +
                "    UNION ALL " +
                "    SELECT id, is_scheduled, scheduled_date, is_important, is_urgent, purpose, owner_id " +
                "    FROM payment WHERE owner_id = :ownerId AND category = :category " +
                "    UNION ALL " +
                "    SELECT id, is_scheduled, scheduled_date, is_important, is_urgent, purpose, owner_id " +
                "    FROM to_do WHERE owner_id = :ownerId AND category = :category " +
                "    UNION ALL " +
                "    SELECT id, is_scheduled, scheduled_date, is_important, is_urgent, purpose, owner_id " +
                "    FROM event WHERE owner_id = :ownerId AND category = :category " +
                "    UNION ALL " +
                "    SELECT id, is_scheduled, scheduled_date, is_important, is_urgent, purpose, owner_id " +
                "    FROM buy_sell WHERE owner_id = :ownerId AND category = :category " +
                "    ORDER BY is_urgent DESC, is_important DESC " +
                ") AS tasks " +
                "GROUP BY scheduled_date " +
                "ORDER BY scheduled_date DESC";


        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("ownerId", ownerId);
        nativeQuery.setParameter("category", category);


        // Use Jpa's result transformer to map result to TaskDTO
        List<Object[]> resultList = nativeQuery.getResultList();

        return resultList.stream().map(obj -> new ScheduledTasksResponse(

                ((Timestamp) obj[0]).toLocalDateTime(),            // scheduledDate
                (String) obj[1]                 // isImportant
        )).collect(Collectors.toList());

    }
}
