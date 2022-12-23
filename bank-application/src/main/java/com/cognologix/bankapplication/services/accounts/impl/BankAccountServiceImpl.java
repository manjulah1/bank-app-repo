package com.cognologix.bankapplication.services.accounts.impl;

import com.cognologix.bankapplication.constants.Constants;
import com.cognologix.bankapplication.constants.ExceptionMessageConstants;
import com.cognologix.bankapplication.dto.AccountBalanceResponse;
import com.cognologix.bankapplication.dto.AmountRequest;
import com.cognologix.bankapplication.dto.BankAccountCreationRequest;
import com.cognologix.bankapplication.dto.BankAccountCreationResponse;
import com.cognologix.bankapplication.dto.BankStatementResponse;
import com.cognologix.bankapplication.dto.BaseResponse;
import com.cognologix.bankapplication.dto.DepositAmountResponse;
import com.cognologix.bankapplication.dto.TransferAmountRequest;
import com.cognologix.bankapplication.dto.TransferAmountResponse;
import com.cognologix.bankapplication.dto.WithdrawAmountResponse;
import com.cognologix.bankapplication.exceptions.AccountDeletionException;
import com.cognologix.bankapplication.exceptions.InsufficientInitialBalanceException;
import com.cognologix.bankapplication.exceptions.NullArgumentException;
import com.cognologix.bankapplication.exceptions.ResourceNotFoundException;
import com.cognologix.bankapplication.exceptions.ZeroAmountException;
import com.cognologix.bankapplication.exceptions.handlers.BankAccountExceptionHandler;
import com.cognologix.bankapplication.mappers.BankAccountMapper;
import com.cognologix.bankapplication.models.BankAccount;
import com.cognologix.bankapplication.models.Transaction;
import com.cognologix.bankapplication.models.User;
import com.cognologix.bankapplication.repositories.BankAccountRepository;
import com.cognologix.bankapplication.repositories.TransactionRepository;
import com.cognologix.bankapplication.repositories.UserRepository;
import com.cognologix.bankapplication.services.accounts.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private final BankAccountExceptionHandler bankAccountExceptionHandler = new BankAccountExceptionHandler();
    private final BankAccountMapper bankAccountMapper = new BankAccountMapper();

    @Override
    public BankAccountCreationResponse createUsersBankAccount(BankAccountCreationRequest bankAccountCreationRequest) throws NullArgumentException {
        BankAccountCreationResponse bankAccountCreationResponse = new BankAccountCreationResponse(null, ExceptionMessageConstants.ACCOUNT_CREATION_FAILED.getError(), false);
        try {
            if (Boolean.TRUE.equals(bankAccountExceptionHandler.checkNullArgumentForCreateBankAccount(bankAccountCreationRequest))) {
                throw new NullArgumentException(ExceptionMessageConstants.NULL_ARGUMENT.getError());
            } else if (bankAccountCreationRequest.getInitialDepositAmount() < 1000) {
                throw new InsufficientInitialBalanceException(ExceptionMessageConstants.INSUFFCIENT_INITIAL_BALANCE.getError());
            } else {
                User user = userRepository.findByIdEquals(bankAccountCreationRequest.getCustomerId());
                if (user == null) {
                    throw new ResourceNotFoundException(ExceptionMessageConstants.RESOURCE_NOT_FOUND.getError());
                }
                if (Objects.equals(user.getUserStatus(), Constants.ACTIVE.name())) {
                    BankAccount bankAccount2 = bankAccountRepository.save(bankAccountMapper.maptoBankAccountEntity(bankAccountCreationRequest, user));
                    if (bankAccount2.getAccountNumber() == null) {
                        return bankAccountCreationResponse;
                    } else {
                        bankAccountCreationResponse.setGeneratedAccountNumber(bankAccount2.getAccountNumber());
                        bankAccountCreationResponse.setIsSuccess(true);
                        bankAccountCreationResponse.setMessage(Constants.ACCOUNT_CREATED.name());
                    }
                }
            }
        } catch (InsufficientInitialBalanceException ex) {
            throw new InsufficientInitialBalanceException(ex.getMessage());
        } catch (ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
        return bankAccountCreationResponse;
    }

    @Override
    public List<BankAccount> findByCustomerId(Integer customerId) {
        try {
            if (customerId == null) {
                throw new NullArgumentException(ExceptionMessageConstants.NULL_ARGUMENT.getError());
            }
        } catch (NullArgumentException ex) {
            throw new NullArgumentException(ex.getMessage());
        }
        return bankAccountRepository.findByCustomerIdEquals(customerId);
    }

    @Override
    public DepositAmountResponse depositAmount(AmountRequest amountRequest) {
        DepositAmountResponse depositAmountResponse = new DepositAmountResponse();
        try {
            if (amountRequest.getTransactionAmount() == 0) {
                throw new ZeroAmountException(ExceptionMessageConstants.ZERO_AMOUNT.getError());
            } else if (Boolean.TRUE.equals(bankAccountExceptionHandler.checkNullArgumentForAmountRequest(amountRequest))) {
                throw new NullArgumentException(ExceptionMessageConstants.NULL_ARGUMENT.getError());
            } else {
                BankAccount bankAccount1 = bankAccountRepository.findByAccountNumber(amountRequest.getAccountNumber());
                if (bankAccount1.getAccountStatus().equals(String.valueOf(Constants.INACTIVE))) {
                    throw new ResourceNotFoundException(ExceptionMessageConstants.RESOURCE_NOT_FOUND.getError());
                }
                Double amountAfterDeposition = bankAccount1.getAccountBalance() + amountRequest.getTransactionAmount();
                bankAccountRepository.updateAccountBalance(amountRequest.getAccountNumber(), amountAfterDeposition);
                depositAmountResponse.setAccountNumber(bankAccount1.getAccountNumber());
                depositAmountResponse.setDepositDate(String.valueOf(LocalDateTime.now()));
                depositAmountResponse.setDepositedAmount(amountAfterDeposition);
                if (depositAmountResponse.getAccountNumber() != null) {
                    depositAmountResponse.setIsSuccess(true);
                    depositAmountResponse.setMessage(String.valueOf(Constants.AMOUNT_DEPOSITED));
                    transactionRepository.save(bankAccountMapper.mapToTransactionEntity(amountRequest, amountAfterDeposition, Constants.DEPOSIT.name()));
                } else {
                    depositAmountResponse = new DepositAmountResponse(ExceptionMessageConstants.DEPOSITION_FAILED.getError(), false, amountRequest.getAccountNumber(), amountRequest.getTransactionAmount(), String.valueOf(LocalDateTime.now()));
                }
            }
        } catch (ZeroAmountException ex) {
            throw new ZeroAmountException(ex.getMessage());
        } catch (NullArgumentException ex) {
            throw new NullArgumentException(ex.getMessage());
        } catch (ResourceNotFoundException rnfe) {
            throw new ResourceNotFoundException(rnfe.getMessage());
        }
        return depositAmountResponse;
    }

    @Override
    public WithdrawAmountResponse withdrawAmount(AmountRequest amountRequest) {
        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(amountRequest.getAccountNumber());
        WithdrawAmountResponse withdrawAmountResponse = new WithdrawAmountResponse();
        try {
            if (amountRequest.getTransactionAmount() == 0) {
                throw new ZeroAmountException(ExceptionMessageConstants.ZERO_AMOUNT.getError());
            } else if (Boolean.TRUE.equals(bankAccountExceptionHandler.checkNullArgumentForAmountRequest(amountRequest))) {
                throw new NullArgumentException(ExceptionMessageConstants.NULL_ARGUMENT.getError());
            } else {
                Double amountAfterWithdrawal = bankAccount.getAccountBalance() - amountRequest.getTransactionAmount();
                if (amountAfterWithdrawal < 0) {
                    throw new InsufficientInitialBalanceException(ExceptionMessageConstants.INSUFFCIENT_INITIAL_BALANCE.getError());
                } else if (amountAfterWithdrawal >= 0) {
                    bankAccountRepository.updateAccountBalance(amountRequest.getAccountNumber(), amountAfterWithdrawal);
                    withdrawAmountResponse.setAccountNumber(amountRequest.getAccountNumber());
                    withdrawAmountResponse.setWithdrawalDate(String.valueOf(LocalDateTime.now()));
                    withdrawAmountResponse.setAccountBalance(amountAfterWithdrawal);
                    withdrawAmountResponse.setIsSuccess(true);
                    withdrawAmountResponse.setMessage(String.valueOf(Constants.AMOUNT_WITHDRAWAL));
                    transactionRepository.save(bankAccountMapper.mapToTransactionEntity(amountRequest, amountAfterWithdrawal, Constants.WITHDRAW.name()));
                } else {
                    withdrawAmountResponse = new WithdrawAmountResponse(ExceptionMessageConstants.WITHDRAW_FAILED.getError(), false, null, String.valueOf(LocalDateTime.now()), bankAccount.getAccountBalance());
                }
            }
        } catch (InsufficientInitialBalanceException ex) {
            throw new InsufficientInitialBalanceException(ex.getMessage());
        } catch (NullArgumentException ex) {
            throw new NullArgumentException(ex.getMessage());
        } catch (ZeroAmountException ex) {
            throw new ZeroAmountException(ex.getMessage());
        }
        return withdrawAmountResponse;
    }

    @Override
    public TransferAmountResponse transferAmount(TransferAmountRequest transferAmountRequest) {
        TransferAmountResponse transferAmountResponse = new TransferAmountResponse();
        try {
            if (transferAmountRequest.getTransactionAmount() == 0) {
                throw new ZeroAmountException(ExceptionMessageConstants.ZERO_AMOUNT.getError());
            } else if (Boolean.TRUE.equals(bankAccountExceptionHandler.checkNullArgumentForTransferAmountRequest(transferAmountRequest))) {
                throw new NullArgumentException(ExceptionMessageConstants.NULL_ARGUMENT.getError());
            } else {
                List<BankAccount> toAndFromAccounts = new ArrayList<>();
                BankAccount toBankAccount = bankAccountRepository.findByAccountNumber(transferAmountRequest.getToAccountNumber());
                BankAccount fromBankAccount = bankAccountRepository.findByAccountNumber(transferAmountRequest.getFromAccountNumber());
                if (fromBankAccount.getAccountStatus().equals(String.valueOf(Constants.INACTIVE)) || toBankAccount.getAccountStatus().equals(String.valueOf(Constants.INACTIVE))) {
                    throw new AccountDeletionException(ExceptionMessageConstants.ACCOUNT_DELETION.getError());
                }
                toAndFromAccounts.add(toBankAccount);
                toAndFromAccounts.add(fromBankAccount);
                double amountAfterWithdrawal = toAndFromAccounts.get(1).getAccountBalance() - transferAmountRequest.getTransactionAmount();
                if (amountAfterWithdrawal < 0) {
                    throw new InsufficientInitialBalanceException(ExceptionMessageConstants.INSUFFCIENT_INITIAL_BALANCE.getError());
                } else {
                    bankAccountRepository.updateAccountBalance(transferAmountRequest.getFromAccountNumber(), amountAfterWithdrawal);
                    Double amountAfterDeposition = toAndFromAccounts.get(0).getAccountBalance() + transferAmountRequest.getTransactionAmount();
                    bankAccountRepository.updateAccountBalance(transferAmountRequest.getToAccountNumber(), amountAfterDeposition);
                    transferAmountResponse.setTransactionAmount(transferAmountRequest.getTransactionAmount());
                    transferAmountResponse.setFromAccountNumber(toAndFromAccounts.get(1).getAccountNumber());
                    transferAmountResponse.setToAccountNumber((toAndFromAccounts.get(0).getAccountNumber()));
                    transferAmountResponse.setIsSuccess(true);
                    transferAmountResponse.setMessage(String.valueOf(Constants.TRANSACTION_SUCCESSFUL));

                    Transaction transaction = new Transaction();
                    transaction.setTransactionId(String.valueOf(UUID.randomUUID()));
                    transaction.setTransactionAmount(transferAmountRequest.getTransactionAmount());
                    transaction.setTransactionDate(String.valueOf(LocalDateTime.now()));
                    transaction.setToAccountNumber(toAndFromAccounts.get(0).getAccountNumber());
                    transaction.setFromAccountNumber(toAndFromAccounts.get(1).getAccountNumber());
                    transaction.setBalanceAfterDeposition(amountAfterDeposition);
                    transaction.setBalanceAfterWithdrawal(amountAfterWithdrawal);

                    transactionRepository.save(transaction);
                }
            }
        } catch (InsufficientInitialBalanceException ex) {
            throw new InsufficientInitialBalanceException(ex.getMessage());
        } catch (NullArgumentException ex) {
            throw new NullArgumentException(ex.getMessage());
        } catch (ZeroAmountException ex) {
            throw new ZeroAmountException(ex.getMessage());
        } catch (AccountDeletionException ex) {
            throw new AccountDeletionException(ex.getMessage());
        }
        return transferAmountResponse;
    }

    @Override
    public AccountBalanceResponse showAccountBalanceByAccountNumber(Long accountNumber) {
        try {
            if (accountNumber == null) {
                throw new NumberFormatException("Invalid type of data: Account number must be a number.");
            }
            BankAccount bankAccount = bankAccountRepository.findByAccountNumber(accountNumber);
            if (bankAccount.getAccountStatus().equals(Constants.INACTIVE.name())) {
                return new AccountBalanceResponse(Constants.SHOW_BALANCE_FAILED.name(), false, null);
            }
            return new AccountBalanceResponse(Constants.SHOW_BALANCE_SUCCESSFUL.name(), true, bankAccount.getAccountBalance());
        } catch (NumberFormatException ex) {
            throw new NumberFormatException(ex.getMessage());
        }
    }

    @Override
    public BaseResponse deleteBankAccount(Long accountNumber) {
        try {
            if (accountNumber == null) {
                throw new NumberFormatException("Invalid type of data: Account number must be a number.");
            }
            BankAccount bankAccount = bankAccountRepository.findByAccountNumber(accountNumber);
            if (bankAccount.getAccountStatus().equals(String.valueOf(Constants.INACTIVE))) {
                return new BaseResponse(Constants.BANK_ACCOUNT_DELETION_FAILED.name(), false);
            }
            bankAccount.setAccountStatus(String.valueOf(Constants.INACTIVE));
            bankAccountRepository.save(bankAccount);
            return new BaseResponse(Constants.BANK_ACCOUNT_DELETION_SUCCESSFUL.name(), bankAccount.getAccountStatus().equals(String.valueOf(Constants.INACTIVE)));
        } catch (NumberFormatException ex) {
            throw new NumberFormatException(ex.getMessage());
        }
    }

    @Override
    public List<BankStatementResponse> getBankStatement(Long accountNumber) {
        List<BankStatementResponse> bankStatementResponses = new ArrayList<>();
        try {
            if (accountNumber == null) {
                throw new NumberFormatException("Invalid type of data: Account number must be a number.");
            } else {
                List<Transaction> transactions = new ArrayList<>();
                List<Transaction> fromTransactions = transactionRepository.findByFromAccountNumber(accountNumber);
                List<Transaction> toTransactions = transactionRepository.findByToAccountNumber(accountNumber);
                transactions.addAll(fromTransactions);
                transactions.addAll(toTransactions);
                bankStatementResponses = transactions.stream()
                        .map(transaction -> {
                            BankStatementResponse bankStatementResponse = new BankStatementResponse();
                            if (Objects.equals(transaction.getFromAccountNumber(), accountNumber)) {
                                bankStatementResponse.setWithdrawn(transaction.getTransactionAmount());
                                bankStatementResponse.setBalance(transaction.getBalanceAfterWithdrawal());
                                bankStatementResponse.setDeposited(null);
                                bankStatementResponse.setTransactionId(transaction.getTransactionId());
                                bankStatementResponse.setTransactionDate(transaction.getTransactionDate());
                            } else if (Objects.equals(transaction.getToAccountNumber(), accountNumber)) {
                                bankStatementResponse.setDeposited(transaction.getTransactionAmount());
                                bankStatementResponse.setBalance(transaction.getBalanceAfterDeposition());
                                bankStatementResponse.setWithdrawn(null);
                                bankStatementResponse.setTransactionId(transaction.getTransactionId());
                                bankStatementResponse.setTransactionDate(transaction.getTransactionDate());
                            }
                            return bankStatementResponse;
                        }).collect(Collectors.toList());
            }
        } catch (NumberFormatException ex) {
            throw new NumberFormatException(ex.getMessage());
        }
        return bankStatementResponses;
    }

    @Override
    public List<BankAccount> getAllBankAccounts() {
        return bankAccountRepository.findAll();
    }
}
