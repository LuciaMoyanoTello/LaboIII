package video2.number.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import video2.number.dtos.DummyDto;
import video2.number.models.Dummy;
import video2.number.services.DummyService;

import java.util.List;

@RestController
@RequestMapping("/dummy")
public class DummyController {

    @Autowired
    private DummyService dummyService;

    @GetMapping("")
    public ResponseEntity<DummyDto> getDummyList(){
        List<Dummy> dummyList = dummyService.getDummyList();
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DummyDto> getDummyId(@PathVariable Long id){
        Dummy dummy = dummyService.getDummyId(id);
        return null;
    }

    @PostMapping("")
    public ResponseEntity<DummyDto> createDummy(DummyDto dummyDto){
        Dummy dummy = dummyService.createDummy(null);
        return null;
    }

    @PutMapping("")
    public ResponseEntity<DummyDto> updateDummy(DummyDto dummyDto){
        Dummy dummy = dummyService.updateDummy(null);
        return null;
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteDummy(DummyDto dummyDto){
        dummyService.deleteDummy(null);
        return null;
    }
}
