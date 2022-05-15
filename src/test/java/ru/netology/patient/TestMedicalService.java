import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.medical.MedicalService;
import ru.netology.patient.service.medical.MedicalServiceImpl;

public class TestMedicalService {

    @Test
    void testCheckBloodPresure() {
        PatientInfoRepository repository = Mockito.mock(PatientInfoRepository.class);
        SendAlertService alertService = Mockito.mock(SendAlertService.class);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        MedicalService service = new MedicalServiceImpl(repository, alertService);
        Mockito.verify(alertService).send(captor.capture());
        Assertions.assertEquals();
    }
}
