package com.bdular.inventorytracker.api;

import com.bdular.inventorytracker.data.task.data.Task;
import com.bdular.inventorytracker.data.user.customer.data.Customer;
import com.bdular.inventorytracker.services.TaskService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("api/tasks")
@Validated
@Api(value = "Task API")
public class TaskAPI {

    @Autowired
    TaskService taskService;

    @GetMapping(value = "/all", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get tasks from user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Task.class),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<List<Task>> getTasksFromUser(@ApiParam(required = true, value = "User ID") @RequestParam String userID) {
        return new ResponseEntity<>(taskService.getTasksFromUser(userID), OK);
    }

    @GetMapping(value = "/user-top-priority", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get tasks with top priority (high, urgent) from user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Task.class),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<List<Task>> getTopPriorityTasksFromUser(@ApiParam(required = true, value = "User ID") @RequestParam String userID) {
        return new ResponseEntity<>(taskService.getTopPriorityTasksFromUser(userID), OK);
    }

    @GetMapping(value = "/top-priority", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get top priority tasks")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Task.class),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<List<Task>> getTopPriorityTasks() {
        return new ResponseEntity<>(taskService.getTopPriorityTasks(), OK);
    }

    @GetMapping(value = "/task/{id}", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get task with ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Task.class),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<Task> getTaskWithID(@ApiParam(required = true, value = "Task ID") @PathVariable String id) {
        return new ResponseEntity<>(taskService.getTask(id), OK);
    }

    @PostMapping(value = "/new", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Add new task")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<String> addNewTask(@ApiParam(required = true, value = "Valid task") @Valid @RequestBody Task task) {
        taskService.addNewTask(task);
        return new ResponseEntity<>(CREATED);
    }

    @PutMapping(value = "/task/{id}", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Change priority of a Task")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Customer.class),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<String> changeTaskPriority(@ApiParam(required = true, value = "Task ID") @PathVariable String id,
                                                     @ApiParam(required = true, value = "Task priority") @RequestBody int priority) {
        taskService.changeTaskPriority(id, priority);
        return new ResponseEntity<>(OK);
    }

    @PutMapping(value = "/task/{id}/change-status", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Change status of a Task")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Customer.class),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<String> changeTaskStatus(@ApiParam(required = true, value = "Task ID") @PathVariable String id,
                                                   @ApiParam(required = true, value = "Task priority") @RequestBody int status) {
        taskService.changeTaskStatus(id, status);
        return new ResponseEntity<>(OK);
    }

    @PutMapping(value = "/task/{id}/finish", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Mark task as finished")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Customer.class),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<String> finishTask(@ApiParam(required = true, value = "Task ID") @PathVariable String id) {
        taskService.finishTask(id);
        return new ResponseEntity<>(OK);
    }

    @DeleteMapping(value = "/task/{id}/", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Delete task")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Customer.class),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<String> deleteTask(@ApiParam(required = true, value = "Task ID") @PathVariable String id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(OK);
    }
}
