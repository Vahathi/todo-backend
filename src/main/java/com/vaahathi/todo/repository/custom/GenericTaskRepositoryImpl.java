package com.vaahathi.todo.repository.custom;

import com.vaahathi.todo.models.misc.ScheduledTasksResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
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
        // Fully qualify the column names with table names
        String query = "SELECT a.scheduled_date as scheduledDate, a.id, a.is_scheduled, a.is_important, a.is_urgent, a.purpose, a.owner_id, 'appointment' as taskType " +
                "FROM appointment a WHERE a.owner_id = :ownerId AND a.category = :category AND a.is_scheduled = TRUE " +
                "UNION ALL " +
                "SELECT c.scheduled_date, c.id, c.is_scheduled, c.is_important, c.is_urgent, c.purpose, c.owner_id, 'call' as taskType " +
                "FROM call c WHERE c.owner_id = :ownerId AND c.category = :category AND c.is_scheduled = TRUE " +
                "UNION ALL " +
                "SELECT m.scheduled_date, m.id, m.is_scheduled, m.is_important, m.is_urgent, m.purpose, m.owner_id, 'mail' as taskType " +
                "FROM mail m WHERE m.owner_id = :ownerId AND m.category = :category AND m.is_scheduled = TRUE " +
                "UNION ALL " +
                "SELECT p.scheduled_date, p.id, p.is_scheduled, p.is_important, p.is_urgent, p.purpose, p.owner_id, 'payment' as taskType " +
                "FROM payment p WHERE p.owner_id = :ownerId AND p.category = :category AND p.is_scheduled = TRUE " +
                "UNION ALL " +
                "SELECT t.scheduled_date, t.id, t.is_scheduled, t.is_important, t.is_urgent, t.purpose, t.owner_id, 'to_do' as taskType " +
                "FROM to_do t WHERE t.owner_id = :ownerId AND t.category = :category AND t.is_scheduled = TRUE " +
                "UNION ALL " +
                "SELECT e.scheduled_date, e.id, e.is_scheduled, e.is_important, e.is_urgent, e.purpose, e.owner_id, 'event' as taskType " +
                "FROM event e WHERE e.owner_id = :ownerId AND e.category = :category AND e.is_scheduled = TRUE " +
                "UNION ALL " +
                "SELECT b.scheduled_date, b.id, b.is_scheduled, b.is_important, b.is_urgent, b.purpose, b.owner_id, 'buy_sell' as taskType " +
                "FROM buy_sell b WHERE b.owner_id = :ownerId AND b.category = :category AND b.is_scheduled = TRUE " +
                "ORDER BY scheduledDate ASC, is_urgent DESC, is_important DESC";

        // Create the native query using the entity manager
        Query nativeQuery = entityManager.createNativeQuery(query);

        // Set parameters for the query
        nativeQuery.setParameter("ownerId", ownerId);
        nativeQuery.setParameter("category", category);

        // Execute the query and get results
        List<Object[]> resultList = nativeQuery.getResultList();

        // Map result to ScheduledTasksResponse and group by date
        Map<LocalDate, List<Map<String, Object>>> groupedTasks = resultList.stream().map(obj -> Map.of(
                "scheduledDate", ((Timestamp) obj[0]).toLocalDateTime().toLocalDate(),
                "object", Map.of(
                        "id", obj[1].toString(),
                        "isScheduled", obj[2],
                        "scheduledDate", ((Timestamp) obj[0]).toLocalDateTime(),
                        "isImportant", obj[3],
                        "isUrgent", obj[4],
                        "purpose", obj[5],
                        "ownerId", obj[6].toString(),
                        "taskType", obj[7]  // Adding taskType here
                )
        )).collect(Collectors.groupingBy(
                task -> (LocalDate) task.get("scheduledDate")  // Group by LocalDate (ignoring time)
        ));

        // Convert grouped tasks to the desired list structure and sort tasks
        return groupedTasks.entrySet().stream()
                .map(entry -> {
                    // Prepare the tasks list for this date
                    List<Map<String, Object>> tasks = entry.getValue().stream()
                            .map(task -> (Map<String, Object>) task.get("object"))
                            .sorted(Comparator.comparing((Map<String, Object> task) -> (LocalDateTime) task.get("scheduledDate"),
                                            Comparator.nullsLast(Comparator.naturalOrder()))
                                    .thenComparing(task -> (Boolean) task.get("isUrgent"),
                                            Comparator.nullsLast(Comparator.reverseOrder()))
                                    .thenComparing(task -> (Boolean) task.get("isImportant"),
                                            Comparator.nullsLast(Comparator.reverseOrder()))
                            )
                            .collect(Collectors.toList());

                    // Return ScheduledTasksResponse for this date (converting LocalDate to LocalDateTime with T00:00:00)
                    return new ScheduledTasksResponse(
                            entry.getKey(),  // Convert LocalDate to LocalDateTime at T00:00:00
                            tasks  // List of tasks for this date
                    );
                })
                .collect(Collectors.toList());

    }
}