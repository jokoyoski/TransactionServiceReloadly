package com.main.contract;

import com.main.helpers.PaginatedListResponse;
import com.main.model.ServerResponse;
import com.main.model.User;
import com.main.model.request.CreateTransaction;
import com.main.model.response.TransactionBalance;

import java.util.Optional;

public interface ITransactionService {


    ServerResponse<String> InsertAmount(CreateTransaction createTransaction);

     TransactionBalance GetBalance(String email);

}
