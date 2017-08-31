package sanchez.sanchez.sergio.backend.controller;

import javax.annotation.PostConstruct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sanchez.sanchez.sergio.dto.response.AlertDTO;
import sanchez.sanchez.sergio.service.IAlertService;

/**
 *
 * @author sergio
 */
@Controller("AdminAlertController")
@RequestMapping("/admin/alerts")
public class AlertsController {

    private final static String VIEW_NAME = "alerts";

    private final IAlertService alertService;

    public AlertsController(IAlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping(value = {"", "/"})
    public String findPaginated(Model model, @PageableDefault Pageable pageable) {
        Page<AlertDTO> resultPage = alertService.findPaginated(pageable);
        model.addAttribute("page", resultPage);
        return VIEW_NAME;
    }

    @PostConstruct
    protected void init() {
        Assert.notNull(alertService, "Alert Service cannot be null");
    }

}
