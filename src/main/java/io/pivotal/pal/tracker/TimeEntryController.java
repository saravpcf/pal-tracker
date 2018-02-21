package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;
    public TimeEntryController( TimeEntryRepository repository) {
        this.timeEntryRepository = repository;
    }


    @PostMapping("/time-entries")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry entry) {
        TimeEntry createdEntry = timeEntryRepository.create(entry);
        return new ResponseEntity<TimeEntry>(createdEntry, HttpStatus.CREATED);

    }

    @GetMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable Long id) {
        TimeEntry entry = timeEntryRepository.find(id);
        if( entry != null ) {
            return new ResponseEntity<TimeEntry>(entry, HttpStatus.OK);
        } else {
            return new ResponseEntity<TimeEntry>(HttpStatus.NOT_FOUND);
        }


    }

    @PutMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable Long id,  @RequestBody TimeEntry updateEntry) {
        TimeEntry entry = timeEntryRepository.update(id, updateEntry);
        if( entry != null ) {
            return new ResponseEntity<TimeEntry>(entry, HttpStatus.OK);
        } else {
            return new ResponseEntity<TimeEntry>(entry, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable Long id) {
        TimeEntry entry = timeEntryRepository.delete(id);
        return new ResponseEntity<TimeEntry>(entry, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> entries = timeEntryRepository.list();
        return new ResponseEntity<List<TimeEntry>>(entries, HttpStatus.OK);

    }

}
