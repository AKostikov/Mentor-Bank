package ru.mentorbank.backoffice.services.stoplist;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ru.mentorbank.backoffice.model.stoplist.JuridicalStopListRequest;
import ru.mentorbank.backoffice.model.stoplist.StopListInfo;
import ru.mentorbank.backoffice.model.stoplist.StopListStatus;
import ru.mentorbank.backoffice.test.AbstractSpringTest;
import ru.mentorbank.backoffice.model.stoplist.PhysicalStopListRequest;

public class StopListServiceStubTest extends AbstractSpringTest {

	@Autowired
	private StopListServiceStub stopListService;
	private JuridicalStopListRequest stopListRequest;
	private PhysicalStopListRequest stopListRequestPhys;
	

	@Before
	public void setUp() {
		stopListRequest = new JuridicalStopListRequest();
		stopListRequestPhys = new PhysicalStopListRequest();
	}

	@Test
	public void getJuridicalStopListInfo_OK() {
		// setup SUT
		stopListRequest.setInn(StopListServiceStub.INN_FOR_OK_STATUS); 
		// Call SUT
		StopListInfo info = stopListService
				.getJuridicalStopListInfo(stopListRequest);
		// Validate SUT
		assertNotNull("���������� ������ ���� ���������", info); 
		assertEquals(StopListStatus.OK, info.getStatus());
	}

	@Test
	public void getJuridicalStopListInfo_STOP() {
		stopListRequest.setInn(StopListServiceStub.INN_FOR_STOP_STATUS);
		StopListInfo info = stopListService
				.getJuridicalStopListInfo(stopListRequest);
		assertNotNull("���������� ������ ���� ���������", info);
		assertEquals(StopListStatus.STOP, info.getStatus());
	}
	
	
	// Tests for physical persons
	
	public String retrieveSerial(String ID){
		return ID.substring(0, ID.indexOf('-'));
	}
	
	public String retrieveNumber(String ID){
		return ID.substring(ID.indexOf('-')+1);
	}
	
	@Test
	public void getPhysicalStopListInfo_OK() {
		// setup SUT
		stopListRequestPhys.setDocumentSeries(retrieveSerial(StopListServiceStub.PERSON_OK_STATUS));
		stopListRequestPhys.setDocumentNumber(retrieveNumber(StopListServiceStub.PERSON_OK_STATUS));
		// Call SUT
		StopListInfo info = stopListService.getPhysicalStopListInfo(stopListRequestPhys);
		// Validate SUT
		assertNotNull("���������� ������ ���� ���������", info); 
		assertEquals(StopListStatus.OK, info.getStatus());
	}

	@Test
	public void getPhysicalStopListInfo_STOP() {
		// setup SUT
		stopListRequestPhys.setDocumentSeries(retrieveSerial(StopListServiceStub.PERSON_STOP_STATUS));
		stopListRequestPhys.setDocumentNumber(retrieveNumber(StopListServiceStub.PERSON_STOP_STATUS));
		// Call SUT
		StopListInfo info = stopListService.getPhysicalStopListInfo(stopListRequestPhys);
		// Validate SUT
		assertNotNull("���������� ������ ���� ���������", info);
		assertEquals(StopListStatus.STOP, info.getStatus());
	}
}
