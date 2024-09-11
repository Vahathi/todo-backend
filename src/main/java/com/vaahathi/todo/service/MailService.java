package com.vaahathi.todo.service;

import com.vaahathi.todo.entity.Mail;
import com.vaahathi.todo.entity.TaskRelation;
import com.vaahathi.todo.models.mail.MailRequest;
import com.vaahathi.todo.models.mail.MailResponse;
import com.vaahathi.todo.repository.MailRepository;
import com.vaahathi.todo.repository.TaskRelationRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MailService {
    @Autowired
    TaskRelationRepository taskRelationRepository;

    @Autowired
    MailRepository mailRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public MailResponse CreateMailAndUpdateTaskRel(MailRequest mailRequest) throws Exception {
        if (mailRequest.getPid() == null) {
            throw new Exception("PID cannot be empty");
        } else {
            Mail mail = modelMapper.map(mailRequest, Mail.class);
            List<String> tempHierarcy = mailRequest.getParentHierarcy();
            tempHierarcy.add(mail.getPurpose());
            mail.setHierarchy(tempHierarcy);
            Mail savedMail = mailRepository.save(mail);
            // adding a record in task relation table
            TaskRelation taskRelation = new TaskRelation();
            taskRelation.setId(savedMail.getId());
            taskRelation.setPid(mailRequest.getPid());
            taskRelation.setCid(new ArrayList<UUID>());
            taskRelation.setRef("mail");
            taskRelationRepository.save(taskRelation);
            // updating parent record in task relation table.
            if (taskRelationRepository.existsById(mailRequest.getPid())) {
                TaskRelation parentRelation = taskRelationRepository.findById(mailRequest.getPid()).orElseThrow(() -> new Exception("can't find parent with given pid"));
                parentRelation.getCid().add(savedMail.getId());
                taskRelationRepository.save(parentRelation);
            } else {
                throw new Exception("cant find parent with pid");
            }

            return modelMapper.map(savedMail, MailResponse.class);
        }
    }
}
