package video2.number.services.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import video2.number.models.Dummy;
import video2.number.repositories.DummyRepository;
import video2.number.services.DummyService;

import java.util.List;

@Service
public class DummyServiceImp implements DummyService {

    @Autowired
    private DummyRepository dummyRepository;

    @Override
    public List<Dummy> getDummyList() {
        return null;
    }

    @Override
    public Dummy getDummyId(Long id) {
        return null;
    }

    @Override
    public Dummy createDummy(Dummy dummy) {
        return null;
    }

    @Override
    public Dummy updateDummy(Dummy dummy) {
        return null;
    }

    @Override
    public void deleteDummy(Dummy dummy) {

    }
}
