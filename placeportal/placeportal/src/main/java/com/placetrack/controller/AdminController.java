package com.placetrack.controller;

import com.placetrack.model.*;
import com.placetrack.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private AdminService adminService;
    @Autowired private CompanyService companyService;
    @Autowired private JobService jobService;
    @Autowired private ApplicationService applicationService;
    @Autowired private StudentService studentService;

    // ─── Login ───────────────────────────────────────────────────────────────
    @GetMapping("/login")
    public String loginPage() {
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes ra) {
        Optional<Admin> admin = adminService.login(username, password);
        if (admin.isPresent()) {
            session.setAttribute("adminId", admin.get().getId());
            session.setAttribute("adminName", admin.get().getFullName());
            return "redirect:/admin/dashboard";
        }
        ra.addFlashAttribute("error", "Invalid credentials.");
        return "redirect:/admin/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // ─── Dashboard ───────────────────────────────────────────────────────────
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (session.getAttribute("adminId") == null) return "redirect:/admin/login";

        model.addAttribute("totalStudents", studentService.getAllStudents().size());
        model.addAttribute("totalCompanies", companyService.getAllCompanies().size());
        model.addAttribute("totalJobs", jobService.getAllJobs().size());
        model.addAttribute("totalApplications", applicationService.getAllApplications().size());
        model.addAttribute("placedStudents", studentService.getPlacedStudents().size());
        return "admin/dashboard";
    }

    // ─── Company ─────────────────────────────────────────────────────────────
    @GetMapping("/companies")
    public String listCompanies(HttpSession session, Model model) {
        if (session.getAttribute("adminId") == null) return "redirect:/admin/login";
        model.addAttribute("companies", companyService.getAllCompanies());
        return "admin/companies";
    }

    @GetMapping("/companies/add")
    public String addCompanyPage(HttpSession session, Model model) {
        if (session.getAttribute("adminId") == null) return "redirect:/admin/login";
        model.addAttribute("company", new Company());
        return "admin/add-company";
    }

    @PostMapping("/companies/add")
    public String addCompany(@ModelAttribute Company company,
                             HttpSession session,
                             RedirectAttributes ra) {
        if (session.getAttribute("adminId") == null) return "redirect:/admin/login";
        companyService.addCompany(company);
        ra.addFlashAttribute("success", "Company added successfully!");
        return "redirect:/admin/companies";
    }

    @GetMapping("/companies/delete/{id}")
    public String deleteCompany(@PathVariable Long id,
                                HttpSession session,
                                RedirectAttributes ra) {
        if (session.getAttribute("adminId") == null) return "redirect:/admin/login";
        companyService.deleteCompany(id);
        ra.addFlashAttribute("success", "Company deleted.");
        return "redirect:/admin/companies";
    }

    // ─── Jobs ─────────────────────────────────────────────────────────────────
    @GetMapping("/jobs")
    public String listJobs(HttpSession session, Model model) {
        if (session.getAttribute("adminId") == null) return "redirect:/admin/login";
        model.addAttribute("jobs", jobService.getAllJobs());
        return "admin/jobs";
    }

    @GetMapping("/jobs/post")
    public String postJobPage(HttpSession session, Model model) {
        if (session.getAttribute("adminId") == null) return "redirect:/admin/login";
        model.addAttribute("job", new Job());
        model.addAttribute("companies", companyService.getAllCompanies());
        return "admin/post-job";
    }

    @PostMapping("/jobs/post")
    public String postJob(@ModelAttribute Job job,
                          @RequestParam Long companyId,
                          HttpSession session,
                          RedirectAttributes ra) {
        if (session.getAttribute("adminId") == null) return "redirect:/admin/login";
        Company company = companyService.getById(companyId);
        job.setCompany(company);
        jobService.postJob(job);
        ra.addFlashAttribute("success", "Job posted successfully!");
        return "redirect:/admin/jobs";
    }

    @GetMapping("/jobs/close/{id}")
    public String closeJob(@PathVariable Long id,
                           HttpSession session,
                           RedirectAttributes ra) {
        if (session.getAttribute("adminId") == null) return "redirect:/admin/login";
        jobService.updateJobStatus(id, Job.JobStatus.CLOSED);
        ra.addFlashAttribute("success", "Job closed.");
        return "redirect:/admin/jobs";
    }

    // ─── Applications ─────────────────────────────────────────────────────────
    @GetMapping("/applications")
    public String viewApplications(HttpSession session, Model model) {
        if (session.getAttribute("adminId") == null) return "redirect:/admin/login";
        model.addAttribute("applications", applicationService.getAllApplications());
        return "admin/applications";
    }

    @PostMapping("/applications/update/{id}")
    public String updateApplicationStatus(@PathVariable Long id,
                                          @RequestParam String status,
                                          HttpSession session,
                                          RedirectAttributes ra) {
        if (session.getAttribute("adminId") == null) return "redirect:/admin/login";
        applicationService.updateStatus(id, Application.ApplicationStatus.valueOf(status));
        ra.addFlashAttribute("success", "Application status updated.");
        return "redirect:/admin/applications";
    }

    // ─── Placed Students ──────────────────────────────────────────────────────
    @GetMapping("/placed-students")
    public String placedStudents(HttpSession session, Model model) {
        if (session.getAttribute("adminId") == null) return "redirect:/admin/login";
        model.addAttribute("students", studentService.getPlacedStudents());
        return "admin/placed-students";
    }

    // ─── All Students ─────────────────────────────────────────────────────────
    @GetMapping("/students")
    public String allStudents(HttpSession session, Model model) {
        if (session.getAttribute("adminId") == null) return "redirect:/admin/login";
        model.addAttribute("students", studentService.getAllStudents());
        return "admin/students";
    }
}
