package com.fillom.taskmanger.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;
    private String description;
    @Column(name = "CREATEDAT")
    private LocalDateTime createdAt;
    @Column(name = "ENDSAT")
    private LocalDateTime endsAt;
    private TaskPriority priority;

}
