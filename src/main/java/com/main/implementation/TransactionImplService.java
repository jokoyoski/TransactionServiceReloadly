package com.main.implementation;



import com.main.api.HttpClient;
import com.main.contract.ITransactionService;
import com.main.model.ServerResponse;
import com.main.model.User;
import com.main.model.request.CreateSendEmail;
import com.main.model.request.CreateTransaction;
import com.main.model.response.TransactionBalance;
import com.repository.factories.GetBalanceInquiryQuery;
import com.repository.factories.GetUserQuery;
import com.repository.factories.InsertAmountQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.concurrent.CompletableFuture;

@Service
public class TransactionImplService implements ITransactionService {

    @Autowired
    GetBalanceInquiryQuery getBalanceInquiryQuery;

    @Autowired
    GetUserQuery getUserQuery;

    @Autowired
    HttpClient http;




    @Autowired
    InsertAmountQuery insertAmountQuery;
    @Override
    public ServerResponse<String> InsertAmount(CreateTransaction createTransaction) {
        var resp= new ServerResponse<String>();
        var user=getUserQuery.GetUser(createTransaction.getEmail());
        if(user==null){
            resp.setCode("400");
            resp.setDescription("Unknown  User");
            return resp;
        }
        createTransaction.setUserId(Integer.parseInt(user.getId()));
        var result=insertAmountQuery.InsertAmount(createTransaction);
        if(result!="0"){ //send email if saved successfully
            CompletableFuture<Void> completedJobCount = CompletableFuture.runAsync(() -> { // running the send email on diff thread
                CreateSendEmail createSendEmail= new CreateSendEmail();
                createSendEmail.setEmail(user.getEmail());
                createSendEmail.setAmount(createTransaction.getAmount());
                createSendEmail.setName(user.getFirstName());
                http.makeApiCall(createSendEmail);
            });
        }


        resp.setCode("200");
        resp.setDescription("Amount recorded");
        return resp;
    }

    @Override
    public TransactionBalance GetBalance(String email) {
        var resp= new TransactionBalance();
        var user=getUserQuery.GetUser(email);
        if(user==null){
            return resp;
        }
        resp= getBalanceInquiryQuery.GetBalance(Integer.parseInt(user.getId()));
        if(resp.getAmount()==null){
            resp.setAmount(0);
        }
        return resp;
    }
}
