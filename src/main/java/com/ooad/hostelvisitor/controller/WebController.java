package com.ooad.hostelvisitor.controller;

import com.ooad.hostelvisitor.dto.VisitRequestForm;
import com.ooad.hostelvisitor.model.IdType;
import com.ooad.hostelvisitor.model.Visit;
import com.ooad.hostelvisitor.model.VisitStatus;
import com.ooad.hostelvisitor.service.HostelSystemService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WebController {

    private final HostelSystemService hostelSystemService;

    public WebController(HostelSystemService hostelSystemService) {
        this.hostelSystemService = hostelSystemService;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("requestCount", hostelSystemService.metric("visit.requested"));
        model.addAttribute("approvedCount", hostelSystemService.metric("visit.approved"));
        model.addAttribute("entryCount", hostelSystemService.metric("visit.entry.allowed"));
        model.addAttribute("exitCount", hostelSystemService.metric("visit.exit.recorded"));
        return "index";
    }

    @GetMapping("/request")
    public String requestForm(Model model) {
        if (!model.containsAttribute("form")) {
            model.addAttribute("form", new VisitRequestForm());
        }
        model.addAttribute("idTypes", IdType.values());
        model.addAttribute("students", hostelSystemService.listStudents());
        model.addAttribute("guards", hostelSystemService.listGuards());
        return "request";
    }

    @PostMapping("/request")
    public String submitRequest(
            @Valid @ModelAttribute("form") VisitRequestForm form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("idTypes", IdType.values());
            model.addAttribute("students", hostelSystemService.listStudents());
            model.addAttribute("guards", hostelSystemService.listGuards());
            return "request";
        }

        try {
            Visit visit = hostelSystemService.registerVisitRequest(form);
            redirectAttributes.addFlashAttribute("success",
                    "Visit request created. Visit ID: " + visit.getVisitId() + " Status: " + visit.getStatus());
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/request";
    }

    @GetMapping("/approvals")
    public String approvals(Model model) {
        model.addAttribute("verifiedVisits", hostelSystemService.listVisitsByStatus(VisitStatus.VERIFIED));
        return "approvals";
    }

    @PostMapping("/approvals/{id}/approve")
    public String approve(@PathVariable("id") Long visitId, RedirectAttributes redirectAttributes) {
        try {
            hostelSystemService.approveVisit(visitId);
            redirectAttributes.addFlashAttribute("success", "Visit approved: " + visitId);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/approvals";
    }

    @PostMapping("/approvals/{id}/reject")
    public String reject(@PathVariable("id") Long visitId, RedirectAttributes redirectAttributes) {
        try {
            hostelSystemService.rejectVisitByStudent(visitId);
            redirectAttributes.addFlashAttribute("success", "Visit rejected: " + visitId);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/approvals";
    }

    @GetMapping("/entry")
    public String entry(Model model) {
        model.addAttribute("approvedVisits", hostelSystemService.listVisitsByStatus(VisitStatus.APPROVED));
        return "entry";
    }

    @PostMapping("/entry/{id}/allow")
    public String allowEntry(@PathVariable("id") Long visitId, RedirectAttributes redirectAttributes) {
        try {
            hostelSystemService.allowEntry(visitId);
            redirectAttributes.addFlashAttribute("success", "Entry allowed for visit: " + visitId);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/entry";
    }

    @GetMapping("/exit")
    public String exit(Model model) {
        model.addAttribute("insideVisits", hostelSystemService.listVisitsByStatus(VisitStatus.INSIDE_HOSTEL));
        return "exit";
    }

    @PostMapping("/exit/{id}/record")
    public String recordExit(@PathVariable("id") Long visitId, RedirectAttributes redirectAttributes) {
        try {
            hostelSystemService.recordExit(visitId);
            redirectAttributes.addFlashAttribute("success", "Exit recorded for visit: " + visitId);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/exit";
    }

    @GetMapping("/logs")
    public String logs(Model model) {
        model.addAttribute("visits", hostelSystemService.listAllVisits());
        return "logs";
    }

    @GetMapping("/reports")
    public String reports(Model model) {
        model.addAttribute("total", hostelSystemService.listAllVisits().size());
        model.addAttribute("approved", hostelSystemService.listVisitsByStatus(VisitStatus.APPROVED).size());
        model.addAttribute("inside", hostelSystemService.listVisitsByStatus(VisitStatus.INSIDE_HOSTEL).size());
        model.addAttribute("closed", hostelSystemService.listVisitsByStatus(VisitStatus.EXIT_RECORDED).size());
        model.addAttribute("invalid", hostelSystemService.listVisitsByStatus(VisitStatus.REJECTED_INVALID_ID).size());
        model.addAttribute("studentRejected", hostelSystemService.listVisitsByStatus(VisitStatus.REJECTED_BY_STUDENT).size());
        return "reports";
    }
}
