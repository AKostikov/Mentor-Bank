package ru.mentorbank.backoffice.services.moneytransfer;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mentorbank.backoffice.services.moneytransfer.exceptions.TransferException;
import ru.mentorbank.backoffice.test.AbstractSpringTest;
import ru.mentorbank.backoffice.dao.stub.OperationDaoStub;
import ru.mentorbank.backoffice.model.stoplist.JuridicalStopListRequest;
import ru.mentorbank.backoffice.model.stoplist.PhysicalStopListRequest;
import ru.mentorbank.backoffice.model.stoplist.StopListInfo;
import ru.mentorbank.backoffice.model.stoplist.StopListStatus;
import ru.mentorbank.backoffice.model.transfer.AccountInfo;
import static org.mockito.Mockito.*;
import ru.mentorbank.backoffice.model.transfer.JuridicalAccountInfo;
import ru.mentorbank.backoffice.model.transfer.PhysicalAccountInfo;
import ru.mentorbank.backoffice.model.transfer.TransferRequest;
import ru.mentorbank.backoffice.services.accounts.AccountService;
import ru.mentorbank.backoffice.services.accounts.AccountServiceBean;
import ru.mentorbank.backoffice.services.stoplist.StopListService;
import ru.mentorbank.backoffice.services.stoplist.StopListServiceStub;
import ru.mentorbank.backoffice.dao.exception.OperationDaoException;

import ru.mentorbank.backoffice.model.Operation;

public class MoneyTransferServiceTest extends AbstractSpringTest {

	@Autowired
	private MoneyTransferService moneyTransferService;

	@Before
	public void setUp() {
	}

	@Test
	public void transfer() throws  TransferException, OperationDaoException {
		//fail("not implemented yet");
		// TODO: Необходимо протестировать, что для хорошего перевода всё
		// работает и вызываются все необходимые методы сервисов
		// Далее следует закоментированная закотовка
		// StopListService mockedStopListService =
		// mock(StopListServiceStub.class);
		// AccountService mockedAccountService = mock(AccountServiceBean.class);
		//
		// moneyTransferService.transfer(null);
		//
		// verify(mockedStopListService).getJuridicalStopListInfo(null);
		// verify(mockedAccountService).verifyBalance(null);
		
		StopListService mockedStopListService = mock(StopListServiceStub.class);
        AccountService mockedAccountService = mock(AccountServiceBean.class);
        OperationDaoStub mockedOperationDao = mock(OperationDaoStub.class);
        
        PhysicalAccountInfo srcAccount = new PhysicalAccountInfo();
        JuridicalAccountInfo dstAccount = new JuridicalAccountInfo();
        
        MoneyTransferServiceBean moneyTransferServiceBean = (MoneyTransferServiceBean)moneyTransferService;
        moneyTransferServiceBean.setAccountService(mockedAccountService);
        moneyTransferServiceBean.setStopListService(mockedStopListService);
        moneyTransferServiceBean.setOperationDao(mockedOperationDao);
        
        TransferRequest transferRequest = new TransferRequest();
        
        transferRequest.setSrcAccount(srcAccount);
        transferRequest.setDstAccount(dstAccount);
        
        StopListInfo stopListInfo = new StopListInfo();
        stopListInfo.setStatus(StopListStatus.OK);
        
        when(mockedAccountService.verifyBalance( srcAccount )).thenReturn(true);
        when(mockedStopListService.getJuridicalStopListInfo(any(JuridicalStopListRequest.class))).thenReturn(stopListInfo);
    	when(mockedStopListService.getPhysicalStopListInfo(any(PhysicalStopListRequest.class))).thenReturn(stopListInfo);
    	
        moneyTransferService.transfer(transferRequest);
        verify(mockedStopListService).getJuridicalStopListInfo(any( JuridicalStopListRequest.class ));
        verify(mockedAccountService).verifyBalance( any (AccountInfo.class) );
        verify(mockedOperationDao).saveOperation(any(Operation.class)); 
	}
}
