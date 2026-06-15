package com.placetrack.controller;

import com.placetrack.model.Application;
import com.placetrack.model.Job;
import com.placetrack.model.Student;
import com.placetrack.service.ApplicationService;
import com.placetrack.service.JobService;
import com.placetrack.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired private StudentService studentService;
    @Autowired private JobService jobService;
    @Autowired private ApplicationService applicationService;

    // ─── Registration ───────────────────────────────────────────────────────
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("student", new Student());
        return "student/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Student student,
                           RedirectAttributes ra) {
        try {
            studentService.register(student);
            ra.addFlashAttribute("success", "Registration successful! Please login.");
            return "redirect:/student/login";
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/student/register";
        }
    }

    // ─── Login ───────────────────────────────────────────────────────────────
    @GetMapping("/login")
    public String loginPage() {
        return "student/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes ra) {
        Optional<Student> student = studentService.login(email, password);
        if (student.isPresent()) {
            session.setAttribute("studentId", student.get().getId());
            session.setAttribute("studentName", student.get().getFullName());
            return "redirect:/student/dashboard";
        }
        ra.addFlashAttribute("error", "Invalid email or password.");
        return "redirect:/student/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // ─── Dashboard ───────────────────────────────────────────────────────────
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Long studentId = (Long) session.getAttribute("studentId");
        if (studentId == null) return "redirect:/student/login";

        Student student = studentService.getById(studentId);
        List<Application> applications = applicationService.getByStudent(student);

        model.addAttribute("student", student);
        model.addAttribute("applicationCount", applications.size());
        model.addAttribute("applications", applications);
        return "student/dashboard";
    }

    // ─── Profile ─────────────────────────────────────────────────────────────
    @GetMapping("/profile")
    public String profilePage(HttpSession session, Model model) {
        Long studentId = (Long) session.getAttribute("studentId");
        if (studentId == null) return "redirect:/student/login";

        model.addAttribute("student", studentService.getById(studentId));
        return "student/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute Student student,
                                HttpSession session,
                                RedirectAttributes ra) {
        Long studentId = (Long) session.getAttribute("studentId");
        if (studentId == null) return "redirect:/student/login";

        student.setId(studentId);
        // Preserve password
        Student existing = studentService.getById(studentId);
        student.setPassword(existing.getPassword());
        student.setEmail(existing.getEmail());
        studentService.updateProfile(student);

        ra.addFlashAttribute("success", "Profile updated successfully!");
        return "redirect:/student/profile";
    }

    // ─── View Jobs ───────────────────────────────────────────────────────────
    @GetMapping("/jobs")
    public String viewJobs(HttpSession session, Model model) {
        Long studentId = (Long) session.getAttribute("studentId");
        if (studentId == null) return "redirect:/student/login";

        Student student = studentService.getById(studentId);
        List<Job> openJobs = jobService.getOpenJobs();

        // Build eligibility map: jobId -> boolean
        java.util.Map<Long, Boolean> eligibilityMap = new java.util.HashMap<>();
        java.util.Map<Long, Boolean> appliedMap = new java.util.HashMap<>();
        List<Application> myApplications = applicationService.getByStudent(student);

        for (Job job : openJobs) {
            eligibilityMap.put(job.getId(), jobService.isEligible(student, job));
            boolean applied = myApplications.stream()
                    .anyMatch(a -> a.getJob().getId().equals(job.getId()));
            appliedMap.put(job.getId(), applied);
        }

        model.addAttribute("student", student);
        model.addAttribute("jobs", openJobs);
        model.addAttribute("eligibilityMap", eligibilityMap);
        model.addAttribute("appliedMap", appliedMap);
        return "student/view-jobs";
    }

    // ─── Apply ───────────────────────────────────────────────────────────────
    @PostMapping("/apply/{jobId}")
    public String applyForJob(@PathVariable Long jobId,
                              HttpSession session,
                              RedirectAttributes ra) {
        Long studentId = (Long) session.getAttribute("studentId");
        if (studentId == null) return "redirect:/student/login";

        try {
            Student student = studentService.getById(studentId);
            Job job = jobService.getById(jobId);

            if (!jobService.isEligible(student, job)) {
                ra.addFlashAttribute("error", "You are not eligible for this job.");
                return "redirect:/student/jobs";
            }

            applicationService.apply(student, job);
            ra.addFlashAttribute("success", "Application submitted successfully!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/student/jobs";
    }
}
