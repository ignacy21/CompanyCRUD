package Company.api.controllers;

import Company.database.entities.Manager;
import Company.services.ManagerService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/managers")
public class ManagerController {

    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping(value = "/getAllManagers", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Manager> getAllManagers() {
        return managerService.getAllManagers();
    }

    @GetMapping(value = "/find/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Manager getManagerById(@PathVariable Long id) {
        return managerService.getManagerById(id);
    }

    @PostMapping("/create")
    public Manager createManager(@RequestBody Manager team) {
        return managerService.createManager(team);
    }

    @PutMapping("/update/{id}")
    public Manager updateManager(@PathVariable Long id, @RequestBody Manager team) {
        return managerService.updateManager(id, team);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteManager(@PathVariable Long id) {
        managerService.deleteManager(id);
    }
}
