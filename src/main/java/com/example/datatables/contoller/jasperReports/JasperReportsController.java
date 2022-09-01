package com.example.datatables.contoller.jasperReports;

import com.example.datatables.dtos.ActorJasperDto;
import com.example.datatables.services.jasperReports.JasperReportsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.util.HashMap;

@Controller
@Log4j2
@RequestMapping("/jasper")
public class JasperReportsController {

    private final JasperReportsService jasperReportsService;

    @Autowired
    public JasperReportsController(JasperReportsService jasperReportsService) {
        this.jasperReportsService = jasperReportsService;
    }

    @GetMapping("/reports")
    public String reports(@RequestParam(name = "name", required = false, defaultValue = "Jasper Reports") String pageName, Model model) {
        model.addAttribute("pageName", pageName);
        return "jasperreport";
    }


    @PostMapping("/reports/actors")
    @ResponseBody
    public HashMap<String, Object> getActorsJasperReport(@Valid ActorJasperDto request/*, Model model*/) {
        String pageName = "";

        HashMap<String, Object> response = new HashMap<>();

        response.put("message", "Not Generated PDF");
        response.put("status", "error");

        try {

            Boolean aBoolean = jasperReportsService.generatePDF(request);
//            Boolean aBoolean = jasperReportsService.generateXLS(request);

            if (aBoolean) {
                response.put("message", "Generated PDF");
                response.put("status", "success");
            }

        } catch (Exception ex) {
            log.warn(ex);
        }

        return response;
    }

    @PostMapping("/reports/actorsPdfFile")
    public File getActorsPdfFile(@Valid ActorJasperDto request) {
        File file = null;
        try {
            file = jasperReportsService.generatePdfFile(request);
            return file;
        } catch (Exception ex) {
            log.warn(ex);
        }
        return file;
    }

}
