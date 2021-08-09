package com.bdular.inventorytracker.data.task.data;

import com.bdular.inventorytracker.data.user.seller.data.Seller;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Document(collection = "tasks")
public class Task {

    @MongoId
    private String id;
    @DBRef
    private Seller uploader;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    @NotNull
    private TaskStatus status;
    @NotNull
    private TaskPriority priority;
    private LocalDateTime submitted;
    private LocalDateTime finished;

    public Task(Seller uploader, String title, String content, TaskStatus status, TaskPriority priority) {
        this.uploader = uploader;
        this.title = title;
        this.content = content;
        this.status = status;
        this.priority = priority;
    }

    public Seller getUploader() {
        return uploader;
    }

    public void setUploader(Seller uploader) {
        this.uploader = uploader;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public LocalDateTime getSubmitted() {
        return submitted;
    }

    public void setSubmitted(LocalDateTime submitted) {
        this.submitted = submitted;
    }

    public LocalDateTime getFinished() {
        return finished;
    }

    public void setFinished(LocalDateTime finished) {
        this.finished = finished;
    }
}
