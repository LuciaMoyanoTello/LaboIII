package video2.number.services;

import org.springframework.stereotype.Service;
import video2.number.models.Dummy;

import java.util.List;

@Service
public interface DummyService {
    List<Dummy> getDummyList();
    Dummy getDummyId(Long id);
    Dummy createDummy(Dummy dummy);
    Dummy updateDummy(Dummy dummy);
    void deleteDummy(Dummy dummy);
}
