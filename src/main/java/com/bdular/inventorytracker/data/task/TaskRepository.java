package com.bdular.inventorytracker.data.task;

import com.bdular.inventorytracker.data.task.data.Task;
import com.bdular.inventorytracker.data.task.data.TaskPriority;
import com.bdular.inventorytracker.data.task.data.TaskStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {

    List<Task> findFirst5ByPriorityGreaterThanAndStatusEqualsOrderByPriorityDescSubmittedDesc(@NotNull TaskPriority priority, @NotNull TaskStatus status);

    List<Task> findFirst5ByUploader_IDAndPriorityGreaterThanAndStatusEqualsOrderByPriorityDescSubmittedDesc(String uploader_ID,
                                                                                                            @NotNull TaskPriority priority, @NotNull TaskStatus status);

    //get all tasks by user ordered by priority
    List<Task> findAllByUploader_IDOrderByPriorityDesc(String uploader_ID);
}
