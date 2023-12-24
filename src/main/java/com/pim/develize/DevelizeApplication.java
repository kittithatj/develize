package com.pim.develize;

import com.pim.develize.entity.Project;
import com.pim.develize.entity.Skill;
import com.pim.develize.exception.BaseException;
import com.pim.develize.model.MailModel;
import com.pim.develize.model.request.UserModel;
import com.pim.develize.repository.SkillRepository;
import com.pim.develize.service.MailService;
import com.pim.develize.service.ProjectService;
import com.pim.develize.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class DevelizeApplication {

    @Value("${config.set-initial-data}")
    private boolean setInit;

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;

    @Autowired
    MailService mailService;

    public static void main(String[] args) {
        SpringApplication.run(DevelizeApplication.class, args);
        System.out.println("Develize Started!");
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initialData() throws BaseException {
        Project p = new Project();
        p.setStartDate(new Date(2022,1,20));
        p.setEndDate(new Date(2022,10,8));
        MailModel mail = new MailModel();
        mail.setMessage("test");
        mail.setSubject("kuy");
        mailService.sendEmail("nano111atom@gmail.com",mail);
        //projectService.sendProjectAssignMail(p,"attid1987@gmail.com");
        if(setInit){
            System.out.println("Initial Data Processing...");
            //init Data Here
            List<Skill> skills = new ArrayList<>();
            String programmingLanguageSkill[] = {
                    "Java",
                    "Python",
                    "JavaScript",
                    "C++",
                    "C#",
                    "Ruby",
                    "Go",
                    "Swift",
                    "Kotlin",
                    "TypeScript",
                    "Rust",
                    "PHP",
                    "Shell",
                    "Objective-C",
                    "R",
                    "Scala",
                    "Perl",
                    "MATLAB",
                    "PL/SQL",
                    "Lua"
            };
            String toolSkills[] = {
                    "Visual Studio Code",
                    "GitHub",
                    "Git",
                    "Docker",
                    "PyCharm",
                    "IntelliJ IDEA",
                    "Eclipse",
                    "Visual Studio",
                    "Jupyter",
                    "Atom",
                    "Sublime Text",
                    "Postman",
                    "AWS",
                    "Azure",
                    "Google Cloud",
            };
            String databaseSkills[] = {
                    "MySQL",
                    "PostgreSQL",
                    "MongoDB",
                    "MariaDB",
                    "SQLite",
                    "Redis",
                    "Oracle Database",
                    "Microsoft SQL Server",
                    "Firebase Realtime Database",
                    "Cassandra",
                    "DynamoDB",
            };
            String frameworkSkill[] = {
                    "React",
                    "Angular",
                    "Vue.js",
                    "Express.js",
                    "Django",
                    "Ruby on Rails",
                    "Spring",
                    "Flask",
                    "Laravel",
                    "ASP.NET",
                    "Vue.js",
            };
            String librarySkill[] = {
                    "jQuery",
                    "Lodash",
                    "Underscore.js",
                    "React Native",
                    "Redux",
                    "RxJS",
                    "Moment.js",
                    "Socket.IO",
                    "Three.js",
                    "Chart.js",
            };

            for (String skillName : programmingLanguageSkill) {
                Skill s = new Skill();
                s.setSkillName(skillName);
                s.setSkillType("Programming Language");
                skills.add(s);
            }

            for (String skillName : toolSkills) {
                Skill s = new Skill();
                s.setSkillName(skillName);
                s.setSkillType("Tool");
                skills.add(s);
            }

            for (String skillName : frameworkSkill) {
                Skill s = new Skill();
                s.setSkillName(skillName);
                s.setSkillType("Framework");
                skills.add(s);
            }

            for (String skillName : librarySkill) {
                Skill s = new Skill();
                s.setSkillName(skillName);
                s.setSkillType("Library");
                skills.add(s);
            }

            for (String skillName : databaseSkills) {
                Skill s = new Skill();
                s.setSkillName(skillName);
                s.setSkillType("Database");
                skills.add(s);
            }

            UserModel admin = new UserModel();
            admin.setFirstName("[ADMIN]");
            admin.setLastName("[ADMIN]");
            admin.setUsername("admin");
            admin.setPassword("1234");
            admin.setRole("Administrator");

            skillRepository.saveAll(skills);
            userService.createUser(admin);

        }
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
