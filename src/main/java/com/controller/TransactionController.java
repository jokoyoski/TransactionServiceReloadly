package com.controller;

import com.main.contract.ITransactionService;
import com.main.model.ServerResponse;
import com.main.model.request.CreateTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
@PreAuthorize("isAuthenticated()")
public class TransactionController {


    @Autowired
    private ITransactionService transactionService;


    @PostMapping("/transaction")
    public ResponseEntity<?> AddUser(@RequestBody CreateTransaction createTransaction) throws Exception {
        ServerResponse<String> serverResponse = transactionService.InsertAmount(createTransaction);
        if(serverResponse==null ){
            ServerResponse<String> serverResponses=new ServerResponse<String>();
            serverResponses.setDescription("An error occurred, try again later");
            return new ResponseEntity<Object>(serverResponses, HttpStatus.EXPECTATION_FAILED);
        }
        if(!serverResponse.getCode().equals("201") ){
            return new ResponseEntity<Object>(serverResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>(serverResponse, HttpStatus.CREATED);
    }





    @GetMapping("transaction/{email}")
    public ResponseEntity<?> GetUser( @PathVariable String email ) throws Exception {
        if(email==null){
            return new ResponseEntity<Object>("Bad Request", HttpStatus.BAD_REQUEST);
        }
        var details=transactionService.GetBalance(email);
        if(details.getAmount()==null){
            return new ResponseEntity<Object>("Invalid user", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Object>(details, HttpStatus.OK);
    }





}
