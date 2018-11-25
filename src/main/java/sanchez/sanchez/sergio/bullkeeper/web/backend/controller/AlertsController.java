package sanchez.sanchez.sergio.bullkeeper.web.backend.controller;

import javax.annotation.PostConstruct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sanchez.sanchez.sergio.bullkeeper.domain.service.IAlertService;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AlertDTO;

/**
 *
 * @author sergio
 */
@Controller("AdminAlertController")
@RequestMapping("/backend/admin/alerts")
public class AlertsController {

    private final static String VIEW_NAME = "alerts";

    private final IAlertService alertService;

    public AlertsController(IAlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping(value = {"", "/"})
    public String findPaginated(Model model, @PageableDefault Pageable pageable) {
        Page<AlertDTO> resultPage = alertService.findPaginated(false, pageable);
        model.addAttribute("page", resultPage);
        return VIEW_NAME;
    }

    @PostConstruct
    protected void init() {
        Assert.notNull(alertService, "Alert Service cannot be null");
    }

}
