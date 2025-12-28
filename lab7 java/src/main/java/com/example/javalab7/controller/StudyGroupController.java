package com.example.javalab7.controller;

import com.example.javalab7.dao.StudyGroupRepository;
import com.example.javalab7.entity.StudyGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Slf4j
@RestController
public class StudyGroupController {
    private final StudyGroupRepository studyGroupRepository;

    @Autowired
    public StudyGroupController(StudyGroupRepository studyGroupRepository) {
        this.studyGroupRepository = studyGroupRepository;
    }

    @GetMapping("/groups/list")
    public ModelAndView getAllGroups() {
        ModelAndView mav = new ModelAndView("list-study-groups");
        mav.addObject("groups", studyGroupRepository.findAll());
        return mav;
    }

    @GetMapping("/groups/addForm")
    public ModelAndView addGroupForm() {
        ModelAndView mav = new ModelAndView("add-study-group-form");
        StudyGroup group = new StudyGroup();
        mav.addObject("group", group);
        return mav;
    }

    @PostMapping("/groups/save")
    public RedirectView saveGroup(@ModelAttribute StudyGroup group) {
        studyGroupRepository.save(group);
        return new RedirectView("/groups/list");
    }

    @GetMapping("/groups/updateForm")
    public ModelAndView showUpdateForm(@RequestParam Long groupId) {
        ModelAndView mav = new ModelAndView("add-study-group-form");
        Optional<StudyGroup> optional = studyGroupRepository.findById(groupId);
        StudyGroup group = optional.orElse(new StudyGroup());
        mav.addObject("group", group);
        return mav;
    }

    @GetMapping("/groups/delete")
    public RedirectView deleteGroup(@RequestParam Long groupId) {
        studyGroupRepository.deleteById(groupId);
        return new RedirectView("/groups/list");
    }
}
