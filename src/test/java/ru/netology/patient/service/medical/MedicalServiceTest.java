package ru.netology.patient;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.medical.MedicalService;
import ru.netology.patient.service.medical.MedicalServiceImpl;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.mockito.Mockito.*;

public class TestMedicalService {

    static ArgumentCaptor<String> captor;
    static PatientInfo patientIntoForTests;
    static PatientInfoRepository repository;

    @BeforeAll
    static void beforeTests() {
        patientIntoForTests = new PatientInfo("id","Иван", "Петров", LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.6"), new BloodPressure(120, 80)));
        repository = mock(PatientInfoRepository.class);
        when(repository.getById(matches("id"))).thenReturn(patientIntoForTests);
        captor = ArgumentCaptor.forClass(String.class);
    }

    @Test
    void testCheckBloodPresure() {
        SendAlertService alertService = mock(SendAlertService.class);
        MedicalService service = new MedicalServiceImpl(repository, alertService);
        service.checkBloodPressure("id", new BloodPressure(60, 120));
        verify(alertService).send(captor.capture());
        Assertions.assertEquals("Warning, patient with id: id, need help", captor.getValue());
    }

    @Test
    void testCheckTemperature() {
        SendAlertService alertService = mock(SendAlertService.class);
        MedicalService service = new MedicalServiceImpl(repository, alertService);
        service.checkTemperature("id", new BigDecimal("38.9"));
        verify(alertService).send(captor.capture());
        Assertions.assertEquals("Warning, patient with id: id, need help", captor.getValue());
    }

    @Test
    void testCheckWhenTheIndicatorsAreNormal() {
        SendAlertService alertService = mock(SendAlertService.class);
        MedicalService service = new MedicalServiceImpl(repository, alertService);
        service.checkTemperature("id", new BigDecimal("37.9"));
        service.checkBloodPressure("id", new BloodPressure(120, 80));
        verifyNoMoreInteractions(alertService);
    }
}
