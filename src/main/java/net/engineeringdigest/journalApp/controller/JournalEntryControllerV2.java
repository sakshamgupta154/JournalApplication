package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("{username}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String username){
        User user = userService.findByUsername(username);
        List<JournalEntry> all =  user.getJournalEntries();
        if(all != null && !all.isEmpty()) return new ResponseEntity<>(all,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("{username}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry entry, @PathVariable String username)
    {
        try{
            entry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(entry,username);
            return new ResponseEntity<>(entry, HttpStatus.CREATED) ;
        }
        catch(Exception e){
            return new ResponseEntity<>(entry, HttpStatus.BAD_REQUEST) ;
        }

    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntry(@PathVariable ObjectId myId)
    {
        Optional<JournalEntry> entry = journalEntryService.findById(myId);
        if(entry.isPresent()) return new ResponseEntity<>(entry.get(), HttpStatus.OK) ;
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{username}/{myId}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable ObjectId myId, @PathVariable String username)
    {

        journalEntryService.deleteById(myId, username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{username}/{myId}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable ObjectId myId,
                                                @RequestBody JournalEntry entry,
                                                @PathVariable String username)
    {
        JournalEntry old = journalEntryService.findById(myId).orElse(null);
        if(old != null)
        {
            old.setTitle(entry.getTitle() != null && entry.getTitle() != ""?entry.getTitle(): old.getTitle());
            old.setContent(entry.getContent() != null && entry.getContent() != ""?entry.getContent():old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
