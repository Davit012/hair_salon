package com.example.hairsalonrest.endpoint;


import com.example.hairsalonrest.dto.workerdtos.WorkerCreateDto;
import com.example.hairsalonrest.dto.workerdtos.WorkerDto;
import com.example.hairsalonrest.dto.workerdtos.WorkerPutDto;
import com.example.hairsalonrest.service.ServiceService;
import com.example.hairsalonrest.service.WorkerService;
import com.hairsaloncommon.model.Worker;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workers")
public class WorkerEndpoint {

    private final WorkerService workerService;
    private final ServiceService serviceService;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<WorkerDto>> getAllWorkers() {
        List<Worker> all = workerService.findAll();
        List<WorkerDto> workerDtos = new ArrayList<>();
        if (!all.isEmpty()) {
            for (Worker worker : all) {
                WorkerDto workerDto = mapper.map(worker, WorkerDto.class);
                workerDtos.add(workerDto);
            }
            return ResponseEntity.ok(workerDtos);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkerDto> getWorker(@PathVariable("id") int id) {
        Worker byId = workerService.findWorkerById(id);
        if (byId == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        return ResponseEntity.ok(mapper.map(byId, WorkerDto.class));
    }

    @PostMapping
    public ResponseEntity<WorkerDto> createWorker(@RequestBody @Valid WorkerCreateDto worker) {
        WorkerDto workerCheck = mapper.map(workerService.save(mapper.map(worker, Worker.class)), WorkerDto.class);
        if (workerCheck != null) {
            return ResponseEntity.ok(workerCheck);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkerDto> updateWorker(@PathVariable(name = "id") int id,
                                                  @RequestBody @Valid WorkerPutDto worker) {
        if (workerService.findWorkerById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        Worker workerFromDb = workerService.editWorker(id, mapper.map(worker, Worker.class));
        return ResponseEntity.ok(mapper.map(workerFromDb, WorkerDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        if (workerService.findWorkerById(id) != null) {
            workerService.deleteWorkerById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}

