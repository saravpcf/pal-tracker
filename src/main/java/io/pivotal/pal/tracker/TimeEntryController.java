package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping(path = "/time-entries", produces = MediaType.APPLICATION_JSON_VALUE)
public class TimeEntryController {


    private TimeEntryRepository timeEntryRepository;
    private final CounterService counter;
    private final GaugeService gauge;

    public TimeEntryController(  TimeEntryRepository repository,CounterService counter,GaugeService gauge ) {
        this.timeEntryRepository = repository;
        this.counter = counter;
        this.gauge = gauge;
    }


    @PostMapping("")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry entry) {
        counter.increment("TimeEntry.created");
        gauge.submit("timeEntries.count", timeEntryRepository.list().size());
        TimeEntry createdEntry = timeEntryRepository.create(entry);
        return new ResponseEntity<TimeEntry>(createdEntry, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable Long id) {
        TimeEntry entry = timeEntryRepository.find(id);
        if( entry != null ) {
            counter.increment("TimeEntry.read");
            return new ResponseEntity<TimeEntry>(entry, HttpStatus.OK);
        } else {
            return new ResponseEntity<TimeEntry>(HttpStatus.NOT_FOUND);
        }


    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable Long id,  @RequestBody TimeEntry updateEntry) {
        TimeEntry entry = timeEntryRepository.update(id, updateEntry);
        if( entry != null ) {
            counter.increment("TimeEntry.updated");
            return new ResponseEntity<TimeEntry>(entry, HttpStatus.OK);
        } else {
            return new ResponseEntity<TimeEntry>(entry, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable Long id) {
        TimeEntry entry = timeEntryRepository.delete(id);
        counter.increment("TimeEntry.delete");
        return new ResponseEntity<TimeEntry>(entry, HttpStatus.NO_CONTENT);
    }

    @GetMapping("")
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> entries = timeEntryRepository.list();
        return new ResponseEntity<List<TimeEntry>>(entries, HttpStatus.OK);
    }

}
