package com.divae_happyBirthday.myfirstrestapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;
    @GetMapping("/todo")
    public ResponseEntity<Todo> get (@RequestParam(value ="id") int id){

        // get todo from DB by id, return object or display Not_Found Message if ID is not present
        Optional<Todo> todoInDB = todoRepository.findById(id);
        if(todoInDB.isPresent()){
            return new ResponseEntity<Todo>(todoInDB.get(), HttpStatus.OK);
        }
        return new ResponseEntity("No todo found with id " + id, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/todo/all")
    public ResponseEntity<Iterable<Todo>> getAll(@RequestHeader("api-secret") String secret){

        var userBySecret = userRepository.findBySecret(secret);

        if(userBySecret.isPresent()){
            Iterable<Todo> allTodosInDB = todoRepository.findAllByUserId(userBySecret.get().getId());
            return new ResponseEntity<Iterable<Todo>>(allTodosInDB, HttpStatus.OK);
        }

        return new ResponseEntity("invalid secret", HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/todo")
    public ResponseEntity<Todo> create (@RequestBody Todo newTodo){
        todoRepository.save(newTodo);
        return new ResponseEntity<Todo>(newTodo, HttpStatus.OK);
    }

    @DeleteMapping("/todo")
    public ResponseEntity delete (@RequestParam(value ="id") int id){
        Optional<Todo> todoInDB = todoRepository.findById(id);

        if(todoInDB.isPresent()){
            todoRepository.deleteById(id);
            return new ResponseEntity("Todo deleted", HttpStatus.OK);
        }
        return new ResponseEntity("No todo found with id to delete with id of " + id, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/todo")
    public ResponseEntity<Todo> edit (@RequestBody Todo updatedTodo){
        Optional<Todo> todoInDB = todoRepository.findById(updatedTodo.getId());

        if(todoInDB.isPresent()){
           Todo savedTodo = todoRepository.save(updatedTodo);
            return new ResponseEntity<Todo>(savedTodo,HttpStatus.OK);
        }
        return new ResponseEntity("No todo found with id to update with id of " + updatedTodo.getId(), HttpStatus.NOT_FOUND);
    }

    //update single datapoint in db
    @PatchMapping("/todo/setDone")
    public ResponseEntity<Todo> setDone (@RequestParam(value = "Id") int id, @RequestParam (value="isDone") boolean isDone){
        Optional<Todo> todoInDB = todoRepository.findById(id);

        if(todoInDB.isPresent()){
            todoInDB.get().setIsDone(isDone);
            Todo savedToDo = todoRepository.save(todoInDB.get());
            return new ResponseEntity <Todo> (savedToDo, HttpStatus.OK);
        }
        return new ResponseEntity("No todo found with id to update status of " + id, HttpStatus.NOT_FOUND);
    }
}
